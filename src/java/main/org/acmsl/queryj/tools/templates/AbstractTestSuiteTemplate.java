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
 * Description: Contains the subtemplates required to generate a JUnit suite
 *              for a set of test cases.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains the subtemplates required to generate a JUnit suite
 * for a set of test cases.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTestSuiteTemplate
    extends  AbstractTemplate
{
    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The test package name.
     */
    private String m__strTestPackageName;

    /**
     * The suite name.
     */
    private String m__strSuiteName;

    /**
     * The test suite template.
     */
    private String m__strTestSuiteTemplate;

    /**
     * The test case import statement.
     */
    private String m__strTestCaseImportStatement;

    /**
     * Adding test case to suite statement.
     */
    private String m__strAddingTestCaseToSuiteStatement;

    /**
     * Adding test suite to suite statement.
     */
    private String m__strAddingTestSuiteToSuiteStatement;

    /**
     * The test case collection.
     */
    private Collection m__cTestCases;

    /**
     * Builds an <code>AbstractTestSuiteTemplate</code> using
     * given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the tested package name.
     * @param testPackageName the test package name.
     * @param suiteName the suite name.
     * @param testSuiteTemplate the template.
     * @param testCaseImportStatement the test case import statement.
     * @param addingTestCaseToSuiteStatement the statement for adding
     * test case to the suite.
     * @param addingTestSuiteToSuiteStatement the statement for adding
     * test suites to the suite.
     */
    protected AbstractTestSuiteTemplate(
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String testPackageName,
        final String suiteName,
        final String testSuiteTemplate,
        final String testCaseImportStatement,
        final String addingTestCaseToSuiteStatement,
        final String addingTestSuiteToSuiteStatement)
    {
        super(decoratorFactory);
        immutableSetPackageName(packageName);
        immutableSetTestPackageName(testPackageName);
        immutableSetSuiteName(suiteName);
        immutableSetTestSuiteTemplate(testSuiteTemplate);
        immutableSetTestCaseImportStatement(testCaseImportStatement);
        immutableSetAddingTestCaseToSuiteStatement(
            addingTestCaseToSuiteStatement);
        immutableSetAddingTestSuiteToSuiteStatement(
            addingTestSuiteToSuiteStatement);
        immutableSetTestCases(new ArrayList());
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName()
    {
        return m__strPackageName;
    }

    /**
     * Specifies the test package name.
     * @param testPackageName the test package name.
     */
    private void immutableSetTestPackageName(final String testPackageName)
    {
        m__strTestPackageName = testPackageName;
    }

    /**
     * Specifies the test package name.
     * @param testPackageName the test package name.
     */
    protected void setTestPackageName(final String testPackageName)
    {
        immutableSetTestPackageName(testPackageName);
    }

    /**
     * Retrieves the test package name.
     * @return such information.
     */
    public String getTestPackageName()
    {
        return m__strTestPackageName;
    }

    /**
     * Specifies the suite name.
     * @param suiteName the suite name.
     */
    private void immutableSetSuiteName(final String suiteName)
    {
        m__strSuiteName = suiteName;
    }

    /**
     * Specifies the suite name.
     * @param suiteName the suite name.
     */
    protected void setSuiteName(final String suiteName)
    {
        immutableSetSuiteName(suiteName);
    }

    /**
     * Retrieves the suite name.
     * @return such information.
     */
    public String getSuiteName()
    {
        return m__strSuiteName;
    }

    /**
     * Specifies the test suite template.
     * @param testSuiteTemplate the test suite template.
     */
    private void immutableSetTestSuiteTemplate(final String testSuiteTemplate)
    {
        m__strTestSuiteTemplate = testSuiteTemplate;
    }

    /**
     * Specifies the test suite template.
     * @param testSuiteTemplate the test suite template.
     */
    protected void setTestSuiteTemplate(final String testSuiteTemplate)
    {
        immutableSetTestSuiteTemplate(testSuiteTemplate);
    }

    /**
     * Retrieves the test suite template.
     * @return such information.
     */
    public String getTestSuiteTemplate()
    {
        return m__strTestSuiteTemplate;
    }

    /**
     * Specifies the test case import statement.
     * @param testCaseImportStatement the test case import statement.
     */
    private void immutableSetTestCaseImportStatement(
        String testCaseImportStatement)
    {
        m__strTestCaseImportStatement = testCaseImportStatement;
    }

    /**
     * Specifies the test case import statement.
     * @param testCaseImportStatement the test case import statement.
     */
    protected void setTestCaseImportStatement(final String testCaseImportStatement)
    {
        immutableSetTestCaseImportStatement(testCaseImportStatement);
    }

    /**
     * Retrieves the test case import statement.
     * @return such information.
     */
    public String getTestCaseImportStatement()
    {
        return m__strTestCaseImportStatement;
    }

    /**
     * Specifies the statement to add test cases to suite.
     * @param addingTestCaseToSuiteStatement the statement.
     */
    private void immutableSetAddingTestCaseToSuiteStatement(
        String addingTestCaseToSuiteStatement)
    {
        m__strAddingTestCaseToSuiteStatement = addingTestCaseToSuiteStatement;
    }

    /**
     * Specifies the statement to add test cases to suite.
     * @param addingTestCaseToSuiteStatement the statement.
     */
    protected void setAddingTestCaseToSuiteStatement(
        String addingTestCaseToSuiteStatement)
    {
        immutableSetAddingTestCaseToSuiteStatement(
            addingTestCaseToSuiteStatement);
    }

    /**
     * Retrieves the statement to add test cases to suite.
     * @return such information.
     */
    public String getAddingTestCaseToSuiteStatement()
    {
        return m__strAddingTestCaseToSuiteStatement;
    }

    /**
     * Specifies the statement to add test suites to suite.
     * @param addingTestSuiteToSuiteStatement the statement.
     */
    private void immutableSetAddingTestSuiteToSuiteStatement(
        String addingTestSuiteToSuiteStatement)
    {
        m__strAddingTestSuiteToSuiteStatement = addingTestSuiteToSuiteStatement;
    }

    /**
     * Specifies the statement to add test suites to suite.
     * @param addingTestSuiteToSuiteStatement the statement.
     */
    protected void setAddingTestSuiteToSuiteStatement(
        String addingTestSuiteToSuiteStatement)
    {
        immutableSetAddingTestSuiteToSuiteStatement(
            addingTestSuiteToSuiteStatement);
    }

    /**
     * Retrieves the statement to add test suites to suite.
     * @return such information.
     */
    public String getAddingTestSuiteToSuiteStatement()
    {
        return m__strAddingTestSuiteToSuiteStatement;
    }

    /**
     * Specifies the test case collection.
     * @param testCases the test cases.
     */
    private void immutableSetTestCases(final Collection testCases)
    {
        m__cTestCases = testCases;
    }

    /**
     * Specifies the test case collection.
     * @param testCases the test cases.
     */
    protected void setTestCases(final Collection testCases)
    {
        immutableSetTestCases(testCases);
    }

    /**
     * Retrieves the test cases.
     * @return such collection.
     */
    public Collection getTestCases()
    {
        return m__cTestCases;
    }

    /**
     * Adds a new test case.
     * @param testCase the new test template.
     */
    public void addTestCase(final TestTemplate testCase)
    {
        Collection t_cTestCases = getTestCases();

        if  (t_cTestCases != null) 
        {
            t_cTestCases.add(testCase);
        }
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getTestPackageName());
    }

    /**
     * Builds the header for logging purposes.
     * @param testPackageName the test package name.
     * @return such header.
     * @precondition testPackageName != null
     */
    protected String buildHeader(final String testPackageName)
    {
        return "Generating TestSuiteTemplate for package " + testPackageName;
    }
}
