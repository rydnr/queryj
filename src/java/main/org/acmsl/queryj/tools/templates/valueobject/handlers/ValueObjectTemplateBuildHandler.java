/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a value object template using database metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject
    .ValueObjectTemplateGenerator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Builds a value object template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ValueObjectTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a ValueObjectTemplateBuildHandler.
     */
    public ValueObjectTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            try
            {
                Map attributes = command.getAttributeMap();

                DatabaseMetaData t_MetaData =
                    retrieveDatabaseMetaData(attributes);

                DatabaseMetaDataManager t_MetaDataManager =
                    retrieveDatabaseMetaDataManager(attributes);

                String t_strPackage = retrievePackage(attributes);

                ValueObjectTemplateGenerator t_ValueObjectTemplateGenerator =
                    ValueObjectTemplateGenerator.getInstance();

                if  (   (t_MetaData                     != null)
                     && (t_MetaDataManager              != null)
                     && (t_ValueObjectTemplateGenerator != null))
                {
                    TableTemplate[] t_aTableTemplates = retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        ValueObjectTemplate[] t_aValueObjectTemplates =
                            new ValueObjectTemplate[t_aTableTemplates.length];

                        for  (int t_iValueObjectIndex = 0;
                                  t_iValueObjectIndex < t_aValueObjectTemplates.length;
                                  t_iValueObjectIndex++) 
                        {
                            String t_strQuote = t_MetaData.getIdentifierQuoteString();

                            if  (t_strQuote == null)
                            {
                                t_strQuote = "\"";
                            }

                            if  (t_strQuote.equals("\""))
                            {
                                t_strQuote = "\\\"";
                            }

                            t_aValueObjectTemplates[t_iValueObjectIndex] =
                                t_ValueObjectTemplateGenerator.createValueObjectTemplate(
                                    t_strPackage,
                                    t_aTableTemplates[t_iValueObjectIndex],
                                    t_MetaDataManager);
                        }

                        storeValueObjectTemplates(t_aValueObjectTemplates, attributes);
                    }
                }
            }
            catch  (final SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (final QueryJException queryjException)
            {
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
        final Map parameters)
      throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaData)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
        final Map parameters)
      throws  BuildException
    {
        DatabaseMetaDataManager result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaDataManager)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    public static String retrievePackage(final Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveValueObjectFactoryPackage(
                    (String)
                        parameters.get(ParameterValidationHandler.PACKAGE));
        }
        
        return result;
    }

    /**
     * Stores the value object template collection in given attribute map.
     * @param valueObjectTemplates the value object templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeValueObjectTemplates(
        final ValueObjectTemplate[] valueObjectTemplates,
        final Map           parameters)
      throws  BuildException
    {
        if  (   (valueObjectTemplates != null)
             && (parameters           != null))
        {
            parameters.put(
                TemplateMappingManager.VALUE_OBJECT_TEMPLATES,
                valueObjectTemplates);
        }
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
      throws  BuildException
    {
        TableTemplate[] result = EMPTY_TABLE_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (TableTemplate[])
                    parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
        }

        return result;
    }
}
