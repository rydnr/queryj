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
 * Description: Builds a base DAO template using database metadata.
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
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;


/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.Map;

/**
 * Builds a base DAO template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class BaseDAOTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a BaseDAOTemplateBuildHandler.
     */
    public BaseDAOTemplateBuildHandler() {};

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

                BaseDAOTemplateGenerator t_BaseDAOTemplateGenerator =
                    BaseDAOTemplateGenerator.getInstance();

                if  (   (t_MetaData             != null)
                     && (t_MetaDataManager      != null)
                     && (t_BaseDAOTemplateGenerator != null))
                {
                    TableTemplate[] t_aTableTemplates = retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        BaseDAOTemplate[] t_aBaseDAOTemplates =
                            new BaseDAOTemplate[t_aTableTemplates.length];

                        for  (int t_iBaseDAOIndex = 0;
                                  t_iBaseDAOIndex < t_aBaseDAOTemplates.length;
                                  t_iBaseDAOIndex++) 
                        {
                            t_aBaseDAOTemplates[t_iBaseDAOIndex] =
                                t_BaseDAOTemplateGenerator.createBaseDAOTemplate(
                                    t_aTableTemplates[t_iBaseDAOIndex],
                                    t_MetaDataManager,
                                    t_strPackage);
                        }

                        storeBaseDAOTemplates(t_aBaseDAOTemplates, attributes);
                    }
                }
            }
            catch  (QueryJException queryjException)
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
            Map parameters)
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
            Map parameters)
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
    protected String retrievePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveBaseDAOPackage(
                    (String)
                        parameters.get(ParameterValidationHandler.PACKAGE));
        }
        
        return result;
    }

    /**
     * Stores the base DAO template collection in given attribute map.
     * @param baseDAOTemplates the base DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeBaseDAOTemplates(
            BaseDAOTemplate[] baseDAOTemplates,
            Map               parameters)
        throws  BuildException
    {
        if  (   (baseDAOTemplates != null)
             && (parameters       != null))
        {
            parameters.put(
                TemplateMappingManager.BASE_DAO_TEMPLATES, baseDAOTemplates);
        }
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected TableTemplate[] retrieveTableTemplates(
            Map parameters)
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
