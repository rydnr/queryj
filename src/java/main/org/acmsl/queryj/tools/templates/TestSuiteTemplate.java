/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate a JUnit suite for the generated
 *              test cases.
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
package org.acmsl.queryj.tools.templates;

/*
 * Importing project-specific classes.
 */
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
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class TestSuiteTemplate
{
    /**
     * The test suite.
     */
    public static final String TEST_SUITE_TEMPLATE =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or (at your option) any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: jsanleandro@yahoo.es\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabañas\n"
        + "                    Boadilla del monte\n"
        + "                    28660 Madrid\n"
        + "                    Spain\n"
        + "\n"
        + " *****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: Executes all defined test cases for\n"
        + "                {0} package.\n"
          // package.
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " *\n"
        + " */\n"
        + "package {1};\n\n" // test package.
        + "/*\n"
        + " * Importing some project classes.\n"
        + " */\n"
        + "{2}" // test cases / suites import.
        + "\n"
        + "/*\n"
        + " * Importing some JUnit classes.\n"
        + " */\n"
        + "import junit.framework.Test;\n"
        + "import junit.framework.TestSuite;\n"
         // + "import junit.swingui.TestRunner;\n\n"
        + "import junit.textui.TestRunner;\n\n"
        + "/**\n"
        + " * Executes all defined test cases for\n"
        + " * {0} package.\n" // package
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n"
        + "public class {3}Suite\n"
        + "    extends  TestSuite\n"
        + "'{'\n"
        + "    /**\n"
        + "     * Default constructor.\n"
        + "     */\n"
        + "    public {3}Suite()\n" // suite name
        + "    '{'\n"
        + "        addTest(suite());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Executes the tests from the command line.\n"
        + "     * @param args the command-line arguments (not used).\n"
        + "     */\n"
        + "    public static void main(String[] args)\n"
        + "    '{'\n"
        + "        TestRunner.run({3}Suite.class);\n" // suite name
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the set of tests included in this suite.\n"
        + "     * @return the test collection.\n"
        + "     */\n"
        + "    public static Test suite()\n"
        + "    '{'\n"
        + "        TestSuite result = new TestSuite(\"{3} suite\");\n\n" // suite name
        + "{4}\n" // adding test case to suite
        + "        return result;\n"
        + "    '}'\n"
        + "'}'";


    /**
     * Test case import statement.
     */
    public static final String TEST_CASE_IMPORT_STATEMENT =
        "import {0}.{1};\n";
          
    /**
     * Adding test case to suite statement.
     */
    public static final String ADDING_TEST_CASE_TO_SUITE_STATEMENT =
        "        result.addTestSuite({0}.class);\n";

    /**
     * Adding test suite to suite statement.
     */
    public static final String ADDING_TEST_SUITE_TO_SUITE_STATEMENT =
        "        result.addTest({0}.suite());\n";

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
     * Builds a TestSuiteTemplate using given information.
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
    public TestSuiteTemplate(
        String packageName,
        String testPackageName,
        String suiteName,
        String testSuiteTemplate,
        String testCaseImportStatement,
        String addingTestCaseToSuiteStatement,
        String addingTestSuiteToSuiteStatement)
    {
        inmutableSetPackageName(packageName);
        inmutableSetTestPackageName(testPackageName);
        inmutableSetSuiteName(suiteName);
        inmutableSetTestSuiteTemplate(testSuiteTemplate);
        inmutableSetTestCaseImportStatement(testCaseImportStatement);
        inmutableSetAddingTestCaseToSuiteStatement(
            addingTestCaseToSuiteStatement);
        inmutableSetAddingTestSuiteToSuiteStatement(
            addingTestSuiteToSuiteStatement);
        inmutableSetTestCases(new ArrayList());
    }

    /**
     * Builds a TestSuiteTemplate using given information.
     * @param packageName the tested package name.
     * @param testPackageName the test package name.
     * @param suiteName the suite name.
     */
    public TestSuiteTemplate(
        String packageName,
        String testPackageName,
        String suiteName)
    {
        this(
            packageName,
            testPackageName,
            suiteName,
            TEST_SUITE_TEMPLATE,
            TEST_CASE_IMPORT_STATEMENT,
            ADDING_TEST_CASE_TO_SUITE_STATEMENT,
            ADDING_TEST_SUITE_TO_SUITE_STATEMENT);
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    private void inmutableSetPackageName(String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    protected void setPackageName(String packageName)
    {
        inmutableSetPackageName(packageName);
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
    private void inmutableSetTestPackageName(String testPackageName)
    {
        m__strTestPackageName = testPackageName;
    }

    /**
     * Specifies the test package name.
     * @param testPackageName the test package name.
     */
    protected void setTestPackageName(String testPackageName)
    {
        inmutableSetTestPackageName(testPackageName);
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
    private void inmutableSetSuiteName(String suiteName)
    {
        m__strSuiteName = suiteName;
    }

    /**
     * Specifies the suite name.
     * @param suiteName the suite name.
     */
    protected void setSuiteName(String suiteName)
    {
        inmutableSetSuiteName(suiteName);
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
    private void inmutableSetTestSuiteTemplate(String testSuiteTemplate)
    {
        m__strTestSuiteTemplate = testSuiteTemplate;
    }

    /**
     * Specifies the test suite template.
     * @param testSuiteTemplate the test suite template.
     */
    protected void setTestSuiteTemplate(String testSuiteTemplate)
    {
        inmutableSetTestSuiteTemplate(testSuiteTemplate);
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
    private void inmutableSetTestCaseImportStatement(
        String testCaseImportStatement)
    {
        m__strTestCaseImportStatement = testCaseImportStatement;
    }

    /**
     * Specifies the test case import statement.
     * @param testCaseImportStatement the test case import statement.
     */
    protected void setTestCaseImportStatement(String testCaseImportStatement)
    {
        inmutableSetTestCaseImportStatement(testCaseImportStatement);
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
    private void inmutableSetAddingTestCaseToSuiteStatement(
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
        inmutableSetAddingTestCaseToSuiteStatement(
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
    private void inmutableSetAddingTestSuiteToSuiteStatement(
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
        inmutableSetAddingTestSuiteToSuiteStatement(
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
    private void inmutableSetTestCases(Collection testCases)
    {
        m__cTestCases = testCases;
    }

    /**
     * Specifies the test case collection.
     * @param testCases the test cases.
     */
    protected void setTestCases(Collection testCases)
    {
        inmutableSetTestCases(testCases);
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
    public void addTestCase(TestTemplate testCase)
    {
        Collection t_cTestCases = getTestCases();

        if  (t_cTestCases != null) 
        {
            t_cTestCases.add(testCase);
        }
    }

    /**
     * Retrieves the source code of the test suite.
     * @return such Java source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringBuffer t_sbTestCaseImportStatements =
            new StringBuffer();

        StringBuffer t_sbAddingTestCasesToSuiteStatements =
            new StringBuffer();

        String t_strTestCaseImportStatement = getTestCaseImportStatement();

        String t_strAddingTestCaseToSuiteStatement =
            getAddingTestCaseToSuiteStatement();

        String t_strAddingTestSuiteToSuiteStatement =
            getAddingTestSuiteToSuiteStatement();

        if  (   (t_strTestCaseImportStatement         != null)
             && (t_strAddingTestCaseToSuiteStatement  != null)
             && (t_strAddingTestSuiteToSuiteStatement != null))
        {
            Collection t_cTestCases = getTestCases();

            if  (t_cTestCases != null) 
            {
                Iterator t_itTestCases = t_cTestCases.iterator();

                TestTemplate t_TestCase = null;

                MessageFormat t_ImportFormatter =
                    new MessageFormat(t_strTestCaseImportStatement);

                MessageFormat t_TestCaseFormatter =
                    new MessageFormat(t_strAddingTestCaseToSuiteStatement);

                MessageFormat t_TestSuiteFormatter =
                    new MessageFormat(t_strAddingTestSuiteToSuiteStatement);

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
            }
        }

        String t_strTestSuiteTemplate = getTestSuiteTemplate();

        if  (t_strTestSuiteTemplate != null) 
        {
            MessageFormat t_Formatter =
                new MessageFormat(t_strTestSuiteTemplate);

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName(),
                        getTestPackageName(),
                        t_sbTestCaseImportStatements,
                        getSuiteName(),
                        t_sbAddingTestCasesToSuiteStatements
                    }));
        }

        return t_sbResult.toString();
    }
}
