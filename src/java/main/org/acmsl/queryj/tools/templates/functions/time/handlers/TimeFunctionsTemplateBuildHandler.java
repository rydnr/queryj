/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Builds a time functions template using database metadata.
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
package org.acmsl.queryj.tools.templates.functions.time.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.time.TimeFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a time functions template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TimeFunctionsTemplateBuildHandler
    extends    AntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The time functions template attribute name.
     */
    public static final String TIME_FUNCTIONS_TEMPLATE =
       "time.functions.template";

    /**
     * Creates a TimeFunctionsTemplateBuildHandler.
     */
    public TimeFunctionsTemplateBuildHandler() {};

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

                boolean t_bExtractFunctions =
                    retrieveExtractFunctions(attributes);

                if  (t_bExtractFunctions)
                {
                    DatabaseMetaData t_MetaData =
                        retrieveDatabaseMetaData(attributes);

                    TimeFunctionsTemplateGenerator t_TimeFunctionsTemplateGenerator =
                        TimeFunctionsTemplateGenerator.getInstance();

                    String t_strPackage = retrievePackage(attributes);

                    StringUtils t_StringUtils = StringUtils.getInstance();

                    if  (   (t_MetaData                       != null)
                         && (t_StringUtils                    != null)
                         && (t_TimeFunctionsTemplateGenerator != null))
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

                        TimeFunctionsTemplate t_TimeFunctionsTemplate =
                            t_TimeFunctionsTemplateGenerator
                                .createTimeFunctionsTemplate(
                                    t_strPackage,
                                    t_MetaData.getDatabaseProductName(),
                                    t_MetaData.getDatabaseProductVersion(),
                                    t_strQuote,
                                    command.getProject(),
                                    command.getTask());

                        Collection t_cFunctions =
                            t_StringUtils.tokenize(
                                t_MetaData.getTimeDateFunctions(),
                                ",");

                        if  (t_cFunctions != null)
                        {
                            Iterator t_itFunctions = t_cFunctions.iterator();

                            while  (   (t_itFunctions != null)
                                    && (t_itFunctions.hasNext()))
                            {
                                String t_strFunction =
                                    (String) t_itFunctions.next();

                                t_TimeFunctionsTemplate.addFunction(
                                    t_strFunction);
                            }
                        }

                        storeTimeFunctionsTemplate(
                            t_TimeFunctionsTemplate, attributes);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                Project t_Project = command.getProject();

                if  (t_Project != null)
                {
                    t_Project.log(
                        command.getTask(),
                        sqlException.getMessage(),
                        Project.MSG_WARN);
                }
                
                throw new BuildException(sqlException);
            }
            catch  (QueryJException queryjException)
            {
                Project t_Project = command.getProject();

                if  (t_Project != null)
                {
                    t_Project.log(
                        command.getTask(),
                        queryjException.getMessage(),
                        Project.MSG_WARN);
                }
                
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves whether the functions should be extracted or not.
     * @param parameters the parameter map.
     * @return such information.
     * @throws BuildException if such condition cannot be retrieved.
     */
    protected boolean retrieveExtractFunctions(
            Map parameters)
        throws  BuildException
    {
        boolean result = true;

        if  (parameters != null)
        {
            Boolean t_bResult =
                (Boolean)
                    parameters.get(
                        ParameterValidationHandler.EXTRACT_FUNCTIONS);

            if  (t_bResult != null)
            {
                result = t_bResult.booleanValue();
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
     * Stores given template.
     * @param timeFunctionsTemplate the template to store.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for some
     * reason.
     */
    protected void storeTimeFunctionsTemplate(
            TimeFunctionsTemplate template,
            Map                   parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
        {
            parameters.put(TIME_FUNCTIONS_TEMPLATE, template);
        }
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
                t_PackageUtils.retrieveFunctionsPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }
}
