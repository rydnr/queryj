/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
                        chous@acm-sl.org

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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: SystemFunctionsTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a system functions template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateFactory;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a system functions template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class SystemFunctionsTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The system functions template attribute name.
     */
    public static final String SYSTEM_FUNCTIONS_TEMPLATE =
        "system.functions.template";

    /**
     * Creates a SystemFunctionsTemplateBuildHandler.
     */
    public SystemFunctionsTemplateBuildHandler() {};

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

                    String t_strPackage = retrievePackage(attributes);

                    SystemFunctionsTemplateGenerator
                        t_SystemFunctionsTemplateGenerator =
                            SystemFunctionsTemplateGenerator.getInstance();

                    StringUtils t_StringUtils = StringUtils.getInstance();

                    if  (   (t_MetaData                         != null)
                         && (t_StringUtils                      != null)
                         && (t_SystemFunctionsTemplateGenerator != null))
                    {
                        SystemFunctionsTemplate t_SystemFunctionsTemplate =
                            t_SystemFunctionsTemplateGenerator
                                .createSystemFunctionsTemplate(
                                    t_strPackage,
                                    retrieveDatabaseProductName(attributes),
                                    retrieveDatabaseProductVersion(attributes),
                                    retrieveDatabaseIdentifierQuoteString(attributes));

                        Collection t_cFunctions =
                            t_StringUtils.tokenize(
                                t_MetaData.getSystemFunctions(),
                                ",");

                        if  (t_cFunctions != null) 
                        {
                            Iterator t_itFunctions = t_cFunctions.iterator();

                            while  (   (t_itFunctions != null)
                                    && (t_itFunctions.hasNext()))
                            {
                                String t_strFunction =
                                    (String) t_itFunctions.next();

                                t_SystemFunctionsTemplate.addFunction(
                                    t_strFunction);
                            }
                        }

                        storeSystemFunctionsTemplate(
                            t_SystemFunctionsTemplate, attributes);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (QueryJException queryjException)
            {
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
     * @param systemFunctionsTemplate the template to store.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for some
     * reason.
     */
    protected void storeSystemFunctionsTemplate(
            SystemFunctionsTemplate template,
            Map                   parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
        {
            parameters.put(SYSTEM_FUNCTIONS_TEMPLATE, template);
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
