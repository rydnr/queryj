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
 * Filename: TestSuiteTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate a JUnit suite for the generated
 *              test cases.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.AbstractTestSuiteTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

/**
 * Is able to generate a JUnit suite for the generated test cases.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TestSuiteTemplate
    extends  AbstractTestSuiteTemplate
    implements  TestSuiteTemplateDefaults
{
    /**
     * Builds a TestSuiteTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the tested package name.
     * @param testPackageName the test package name.
     * @param suiteName the suite name.
     */
    public TestSuiteTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String testPackageName,
        final String suiteName)
    {
        super(
            (header != null) ? header : ACMSL_HEADER,
            decoratorFactory,
            packageName,
            testPackageName,
            suiteName,
            TEST_SUITE_TEMPLATE,
            TEST_CASE_IMPORT_STATEMENT,
            ADDING_TEST_CASE_TO_SUITE_STATEMENT,
            ADDING_TEST_SUITE_TO_SUITE_STATEMENT);
    }

    /**
     * Retrieves the source code of the test suite.
     * @param header the header.
     * @return such Java source code.
     */
    protected String generateOutput(final String header)
    {
        return
            generateOutput(
                header,
                getPackageName(),
                getTestPackageName(),
                getSuiteName(),
                getTestSuiteTemplate(),
                getTestCaseImportStatement(),
                getAddingTestCaseToSuiteStatement(),
                getAddingTestSuiteToSuiteStatement(),
                getTestCases());
    }

    /**
     * Retrieves the source code of the test suite.
     * @param header the header.
     * @param packageName the tested package name.
     * @param testPackageName the test package name.
     * @param suiteName the suite name.
     * @param testSuiteTemplate the template.
     * @param testCaseImportStatement the test case import statement.
     * @param addingTestCaseToSuiteStatement the statement for adding
     * test case to the suite.
     * @param addingTestSuiteToSuiteStatement the statement for adding
     * test suites to the suite.
     * @param testCases the test cases.
     * @return such Java source code.
     * @precondition testCases != null
     */
    protected String generateOutput(
        final String header,
        final String packageName,
        final String testPackageName,
        final String suiteName,
        final String testSuiteTemplate,
        final String testCaseImportStatement,
        final String addingTestCaseToSuiteStatement,
        final String addingTestSuiteToSuiteStatement,
        final Collection testCases)
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringBuffer t_sbTestCaseImportStatements =
            new StringBuffer();

        StringBuffer t_sbAddingTestCasesToSuiteStatements =
            new StringBuffer();

        Iterator t_itTestCases = testCases.iterator();

        TestTemplate t_TestCase = null;

        MessageFormat t_ImportFormatter =
            new MessageFormat(testCaseImportStatement);

        MessageFormat t_TestCaseFormatter =
            new MessageFormat(addingTestCaseToSuiteStatement);

        MessageFormat t_TestSuiteFormatter =
            new MessageFormat(addingTestSuiteToSuiteStatement);

        while  (   (t_itTestCases != null)
                && (t_itTestCases.hasNext()))
        {
            t_TestCase = (TestTemplate) t_itTestCases.next();

            if  (t_TestCase != null)
            {
                t_sbTestCaseImportStatements.append(
                    t_ImportFormatter.format(
                        new Object[]
                        {
                            t_TestCase.getPackageName(),
                            t_TestCase.getTestName()
                        }));

                MessageFormat t_Formatter = t_TestCaseFormatter;

                if  (t_TestCase.isSuite())
                {
                    t_Formatter = t_TestSuiteFormatter;
                }

                t_sbAddingTestCasesToSuiteStatements.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_TestCase.getTestName()
                        }));
            }
        }

        MessageFormat t_Formatter =
            new MessageFormat(testSuiteTemplate);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    header,
                    packageName,
                    testPackageName,
                    t_sbTestCaseImportStatements,
                    suiteName,
                    t_sbAddingTestCasesToSuiteStatements
                }));

        return t_sbResult.toString();
    }
}
