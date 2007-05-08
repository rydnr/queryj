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
 * Filename: TimeFunctionsTestTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a time functions test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a time functions test template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class TimeFunctionsTestTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a TimeFunctionsTestTemplateBuildHandler.
     */
    public TimeFunctionsTestTemplateBuildHandler() {};

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

                    String t_strTestedPackage = retrieveTestedPackage(attributes);

                    TimeFunctionsTestTemplateGenerator
                        t_TimeFunctionsTestTemplateGenerator =
                            TimeFunctionsTestTemplateGenerator.getInstance();

                    StringUtils t_StringUtils = StringUtils.getInstance();

                    if  (   (t_MetaData                           != null)
                         && (t_StringUtils                        != null)
                         && (t_TimeFunctionsTestTemplateGenerator != null))
                    {
                        TimeFunctionsTestTemplate t_TimeFunctionsTestTemplate =
                            t_TimeFunctionsTestTemplateGenerator
                                .createTimeFunctionsTestTemplate(
                                    t_strPackage,
                                    t_strTestedPackage,
                                    retrieveDatabaseProductName(attributes),
                                    retrieveDatabaseProductVersion(attributes),
                                    retrieveDatabaseIdentifierQuoteString(attributes));

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
                                String t_strFunctions =
                                    (String) t_itFunctions.next();

                                t_TimeFunctionsTestTemplate.addFunction(
                                    t_strFunctions);
                            }
                        }

                        storeTimeFunctionsTestTemplate(
                            t_TimeFunctionsTestTemplate, attributes);
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
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveTestFunctionsPackage(
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the tested package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveTestedPackage(final Map parameters)
        throws  BuildException
    {
        return
            retrieveTestedPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the tested package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveTestedPackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveFunctionsPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected Collection retrieveTestTemplates(final Map parameters)
        throws  BuildException
    {
        return
            (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the time functions test template.
     * @param template the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTimeFunctionsTestTemplate(
        final TimeFunctionsTestTemplate template,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.TIME_FUNCTIONS_TEST_TEMPLATE,
            template);

        storeTestTemplate(template, parameters);
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTestTemplates(
        final Collection templates, final Map parameters)
      throws  BuildException
    {
        parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTestTemplate(
        final TestTemplate template, final Map parameters)
      throws  BuildException
    {
        Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates == null) 
        {
            t_cTestTemplates = new ArrayList();
            storeTestTemplates(t_cTestTemplates, parameters);
        }

        t_cTestTemplates.add(template);
    }
}
