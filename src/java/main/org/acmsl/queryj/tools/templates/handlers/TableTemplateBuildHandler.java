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
 * Description: Builds a table template using database metadata.
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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TableTemplateGenerator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a table template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TableTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The table templates attribute name.
     */
    public static final String TABLE_TEMPLATES = "table.templates";

    /**
     * The table names attribute name.
     */
    public static final String TABLE_NAMES = "table.names";

    /**
     * Creates a TableTemplateBuildHandler.
     */
    public TableTemplateBuildHandler() {};

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
            Map attributes = command.getAttributeMap();

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(attributes);

            String t_strPackage = retrieveTablePackage(attributes);

            TableTemplateGenerator t_TableTemplateGenerator =
                TableTemplateGenerator.getInstance();

            MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

            if  (   (t_MetaDataManager        != null)
                 && (t_TableTemplateGenerator != null)
                 && (t_MetaDataUtils          != null))
            {
                String[] t_astrTableNames = t_MetaDataManager.getTableNames();

                String[] t_astrColumnNames = null;

                int t_iColumnType = -1;

                if  (t_astrTableNames != null) 
                {
                    TableTemplate[] t_aTableTemplates =
                        new TableTemplate[t_astrTableNames.length];

                    for  (int t_iTableIndex = 0;
                              t_iTableIndex < t_astrTableNames.length;
                              t_iTableIndex++) 
                    {
                        t_aTableTemplates[t_iTableIndex] =
                            t_TableTemplateGenerator.createTableTemplate(
                                t_strPackage, t_astrTableNames[t_iTableIndex]);

                        t_astrColumnNames =
                            t_MetaDataManager.getColumnNames(
                                t_astrTableNames[t_iTableIndex]);

                        if  (t_astrColumnNames != null) 
                        {
                            for  (int t_iColumnIndex = 0;
                                      t_iColumnIndex < t_astrColumnNames.length;
                                      t_iColumnIndex++) 
                            {
                                t_aTableTemplates[t_iTableIndex].addField(
                                    t_astrColumnNames[t_iColumnIndex]);

                                t_iColumnType =
                                    t_MetaDataManager.getColumnType(
                                        t_astrTableNames[t_iTableIndex],
                                        t_astrColumnNames[t_iColumnIndex]);

                                t_aTableTemplates[t_iTableIndex].addFieldType(
                                    t_astrColumnNames[t_iColumnIndex],
                                    t_MetaDataUtils.getQueryJFieldType(t_iColumnType));
                            }
                        }
                    }

                    storeTableNames(t_astrTableNames, attributes);
                    storeTableTemplates(t_aTableTemplates, attributes);
                }
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager instance.
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
    protected String retrieveProjectPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Retrieves the table package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveTablePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveTablePackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Stores the table name collection in given attribute map.
     * @param tableNames the table names.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeTableNames(
            String[] tableNames,
            Map      parameters)
        throws  BuildException
    {
        if  (   (tableNames != null)
             && (parameters != null))
        {
            parameters.put(TABLE_NAMES, tableNames);
        }
    }

    /**
     * Stores the table template collection in given attribute map.
     * @param tableTemplates the table templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeTableTemplates(
            TableTemplate[] tableTemplates,
            Map             parameters)
        throws  BuildException
    {
        if  (   (tableTemplates != null)
             && (parameters     != null))
        {
            parameters.put(TABLE_TEMPLATES, tableTemplates);
        }
    }
}
