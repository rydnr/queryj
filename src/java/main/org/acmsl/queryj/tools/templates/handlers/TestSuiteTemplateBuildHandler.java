/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
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
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a test suite template.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TestSuiteTemplateBuildHandler
    extends    AbstractAntCommandHandler
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
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return
            handle(
                command.getAttributeMap(),
                command.getProject(),
                command.getTask(),
                TestSuiteTemplateGenerator.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @param templateFactory the template factory.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition templateFactory != null
     * @precondition stringUtils != null
     */
    protected boolean handle(
        final Map parameters,
        final Project project,
        final Task task,
        final TestSuiteTemplateFactory templateFactory,
        final StringUtils stringUtils)
      throws  BuildException
    {
        boolean result = false;

        String t_strPackage = retrievePackage(parameters);

        TestSuiteTemplate t_TestSuiteTemplate =
            templateFactory.createTestSuiteTemplate(
                t_strPackage,
                stringUtils.extractPackageName(t_strPackage),
                project,
                task);

        Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates != null)
        {
            Iterator t_itTestTemplates = t_cTestTemplates.iterator();

            while  (   (t_itTestTemplates != null)
                    && (t_itTestTemplates.hasNext()))
            {
                TestTemplate t_TestTemplate =
                    (TestTemplate) t_itTestTemplates.next();

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
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected Collection retrieveTestTemplates(final Map  parameters)
        throws  BuildException
    {
        return
            (Collection)
                parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores a new test suite template.
     * @param testSuiteTemplate the test suite template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition testSuiteTemplate != null
     * @precondition parameters != null
     */
    protected void storeTestSuiteTemplate(
        final TestSuiteTemplate testSuiteTemplate, final Map parameters)
      throws  BuildException
    {
        parameters.put(TEST_SUITE_TEMPLATE, testSuiteTemplate);
    }
}
