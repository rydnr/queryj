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
 * Filename: TestSuiteTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to generate a JUnit
 *              suite for a set of test cases.
 *
 */
package org.acmsl.queryj.tools.templates;

/**
 * Defines the default subtemplates used to generate a JUnit
 * suite for a set of test cases.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface TestSuiteTemplateDefaults
    extends  JavaTemplateDefaults
{
    /**
     * The test suite.
     */
    public static final String TEST_SUITE_TEMPLATE =
          "{0}"
        + "/*****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: Executes all defined test cases for\n"
        + "                {1} package.\n"
          // package.
        + " */\n"
        + "package {2};\n\n" // test package.
        + "/*\n"
        + " * Importing some project classes.\n"
        + " */\n"
        + "{3}" // test cases / suites import.
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
        + " * {1} package.\n" // package
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n"
        + "public class {4}Suite\n"
        + "    extends  TestSuite\n"
        + "'{'\n"
        + "    /**\n"
        + "     * Default constructor.\n"
        + "     */\n"
        + "    public {4}Suite()\n" // suite name
        + "    '{'\n"
        + "        addTest(suite());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Executes the tests from the command line.\n"
        + "     * @param args the command-line arguments (not used).\n"
        + "     */\n"
        + "    public static void main(String[] args)\n"
        + "    '{'\n"
        + "        TestRunner.run({4}Suite.class);\n" // suite name
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the set of tests included in this suite.\n"
        + "     * @return the test collection.\n"
        + "     */\n"
        + "    public static Test suite()\n"
        + "    '{'\n"
        + "        TestSuite result = new TestSuite(\"{4} suite\");\n\n" // suite name
        + "{5}\n" // adding test case to suite
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
}
