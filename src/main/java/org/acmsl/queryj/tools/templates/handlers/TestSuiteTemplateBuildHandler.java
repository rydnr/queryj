//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: TestSuiteTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a test suite template.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestSuiteTemplate;
import org.acmsl.queryj.tools.templates.TestSuiteTemplateFactory;
import org.acmsl.queryj.tools.templates.TestSuiteTemplateGenerator;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a test suite template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TestSuiteTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The test suite template attribute name.
     */
    public static final String TEST_SUITE_TEMPLATE = "test.suite.templates";

    /**
     * Creates a TestSuiteTemplateBuildHandler.
     */
    public TestSuiteTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    public boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                retrieveTestTemplates(parameters),
                retrieveProjectPackage(parameters),
                retrieveHeader(parameters),
                retrieveUseSubfoldersFlag(parameters),
                TestSuiteTemplateGenerator.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param testTemplates the test templates.
     * @param projectPackage the project package.
     * @param header the header.
     * @param subFolders whether to use subfolders or not.
     * @param templateFactory the template factory.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition testTemplates != null
     * @precondition projectPackage != null
     * @precondition templateFactory != null
     * @precondition stringUtils != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        @Nullable final Collection testTemplates,
        final String projectPackage,
        final String header,
        final boolean subFolders,
        @NotNull final TestSuiteTemplateFactory templateFactory,
        @NotNull final StringUtils stringUtils)
      throws  QueryJBuildException
    {
        boolean result = false;

        @NotNull TestSuiteTemplate t_TestSuiteTemplate =
            templateFactory.createTestSuiteTemplate(
                projectPackage,
                stringUtils.extractPackageName(projectPackage),
                header,
                subFolders);

        @Nullable Iterator t_itTestTemplates =
            (testTemplates != null) ? testTemplates.iterator() : null;

        if  (t_itTestTemplates != null)
        {
            TestTemplate t_TestTemplate;

            while  (t_itTestTemplates.hasNext())
            {
                t_TestTemplate = (TestTemplate) t_itTestTemplates.next();

                if  (t_TestTemplate != null)
                {
                    t_TestSuiteTemplate.addTestCase(t_TestTemplate);
                }
            }
        }
                
        storeTestSuiteTemplate(t_TestSuiteTemplate, parameters);
        
        return result;
    }
    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition parameters != null
     */
    @NotNull
    protected Collection retrieveTestTemplates(@NotNull final Map  parameters)
    {
        return
            (Collection)
                parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores a new test suite template.
     * @param testSuiteTemplate the test suite template.
     * @param parameters the parameter map.
     * @precondition testSuiteTemplate != null
     * @precondition parameters != null
     */
    protected void storeTestSuiteTemplate(
        final TestSuiteTemplate testSuiteTemplate, @NotNull final Map parameters)
    {
        parameters.put(TEST_SUITE_TEMPLATE, testSuiteTemplate);
    }
}
