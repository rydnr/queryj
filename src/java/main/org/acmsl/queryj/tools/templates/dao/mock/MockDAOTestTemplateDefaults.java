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
 * Description: Defines the default subtemplates for creating JUnit tests to
 *              ensure generated Mock DAOs are working correctly.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/**
 * Defines the default subTemplates for creating JUnit tests to ensure
 * generated Mock DAOs are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface MockDAOTestTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002-2005  Jose San Leandro Armendariz\n"
        + "                        chous@acm-sl.org\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
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
        + "    Contact info: chous@acm-sl.org\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabanas\n"
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
        + " * Description: Executes JUnit tests to ensure Mock{0}DAO works as\n"
         // table
        + " *              expected.\n"
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " *\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

    /**
     * The project-specific imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project classes.\n"
        + " */\n"
        + "import {0}.Mock{1}DAO;\n"
    // DAO package - engine name - table name
        + "import {2}.{1}ValueObject;\n\n";
    // package - table name

    /**
     * The ACMSL imports.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=503">503</a>
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.dao.MockDataSource;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n"
        + "import java.util.Iterator;\n"
        + "import javax.sql.DataSource;\n\n";

    /**
     * The JUnit imports.
     */
    public static final String DEFAULT_JUNIT_IMPORTS =
          "/*\n"
        + " * Importing some JUnit classes.\n"
        + " */\n"
        + "import junit.framework.Test;\n"
        + "import junit.framework.TestCase;\n"
        + "import junit.framework.TestSuite;\n\n";
    
    /**
     * The default class Javadoc.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=504">504</a>
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Executes JUnit tests to ensure Mock{1}DAO works as\n"
         // table
        + " * expected and connections are correctly managed.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " * @see {0}.Mock{1}DAO\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
          "public class Mock{0}DAOTest\n"
         // Table
        + "    extends  TestSuite\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * {0} information used just for testing.\n"
         // value object name.
        + "     */\n"
        + "    private static {0}ValueObject m__ValueObject;\n\n"
         // value object name.
        + "    /**\n"
        + "     * The tested instance.\n"
        + "     */\n"
        + "    private static Mock{0}DAO m__TestedInstance;\n\n";
         // DAO name.

    /**
     * Default test values.
     */
    public static final String DEFAULT_TEST_VALUES =
          "    /**\n"
        + "     * A test calendar.\n"
        + "     */\n"
        + "    public static final Calendar CALENDAR_VALUE =\n"
        + "        Calendar.getInstance();\n\n"
        + "    /**\n"
        + "     * A test date.\n"
        + "     */\n"
        + "    public static final Date DATE_VALUE =\n"
        + "        CALENDAR_VALUE.getTime();\n\n"
        + "    /**\n"
        + "     * A test integer.\n"
        + "     */\n"
        + "    public static final int INT_VALUE =\n"
        + "        (int) Math.round(Math.random() * 1000000.0);\n\n"
        + "    /**\n"
        + "     * A test long.\n"
        + "     */\n"
        + "    public static final long LONG_VALUE =\n"
        + "        Math.round(Math.random() * (double) INT_VALUE);\n\n"
        + "    /**\n"
        + "     * A test double.\n"
        + "     */\n"
        + "    public static final double DOUBLE_VALUE =\n"
        + "        Math.random() * (double) LONG_VALUE;\n\n"
        + "    /**\n"
        + "     * A test big decimal.\n"
        + "     */\n"
        + "    public static final BigDecimal BIGDECIMAL_VALUE =\n"
        + "        new BigDecimal(Math.random() * DOUBLE_VALUE);\n"
        + "    /**\n"
        + "     * A test object.\n"
        + "     */\n"
        + "    public static final Object OBJECT_VALUE =\n"
        + "        \"Test object-\" + DOUBLE_VALUE;\n\n"
        + "    /**\n"
        + "     * A test text.\n"
        + "     */\n"
        + "    public static final String STRING_VALUE =\n"
        + "        \"testing-\" + DOUBLE_VALUE;\n\n";

    /**
     * Default test updated values.
     */
    public static final String DEFAULT_TEST_UPDATED_VALUES =
          "    /**\n"
        + "     * A test text for updates.\n"
        + "     */\n"
        + "    public static final String UPDATED_STRING_VALUE = \"updated \" + STRING_VALUE;\n\n"
        + "    /**\n"
        + "     * A test calendar for updates.\n"
        + "     */\n"
        + "    public static final Calendar UPDATED_CALENDAR_VALUE =\n"
        + "        Calendar.getInstance();\n\n"
        + "    /**\n"
        + "     * A test date for updates.\n"
        + "     */\n"
        + "    public static final Date UPDATED_DATE_VALUE =\n"
        + "        UPDATED_CALENDAR_VALUE.getTime();\n\n"
        + "    /**\n"
        + "     * A test integer for updates.\n"
        + "     */\n"
        + "    public static final int UPDATED_INT_VALUE = INT_VALUE + 1;\n\n"
        + "    /**\n"
        + "     * A test long for updates.\n"
        + "     */\n"
        + "    public static final long UPDATED_LONG_VALUE = LONG_VALUE + 1;\n\n"
        + "    /**\n"
        + "     * A test double for updates.\n"
        + "     */\n"
        + "    public static final double UPDATED_DOUBLE_VALUE = DOUBLE_VALUE + 1;\n\n"
        + "    /**\n"
        + "     * A test big decimal for updates.\n"
        + "     */\n"
        + "    public static final BigDecimal UPDATED_BIGDECIMAL_VALUE =\n"
        + "        BIGDECIMAL_VALUE.add(new BigDecimal(Math.random() * DOUBLE_VALUE));\n"
        + "    /**\n"
        + "     * A test object for updates.\n"
        + "     */\n"
        + "    public static final Object UPDATED_OBJECT_VALUE =\n"
        + "        \"Updated test object\";\n\n";

    /**
     * The default constructor.
     */
    public static final String DEFAULT_CONSTRUCTOR =
          "    /**\n"
        + "     * Constructs a Mock{0}DAOTest with given name.\n"
         // engine - DAO name.
        + "     * @param name the test name.\n"
        + "     */\n"
        + "    public Mock{0}DAOTest(final String name)\n"
        + "    '{'\n"
        + "        super(name);\n"
        + "    '}'\n\n";

    /**
     * The inner methods.
     */
    public static final String DEFAULT_INNER_METHODS = "";

    /**
     * The initialization methods.
     */
    public static final String DEFAULT_INIT_METHODS =
          "    /**\n"
        + "     * Specifies the DAO to test.\n"
        + "     * @param dao the DAO to test.\n"
        + "     */\n"
        + "    private static void setTestedInstance(final Mock{0}DAO dao)\n"
         // engine name - DAO name.
        + "    '{'\n"
        + "        m__TestedInstance = dao;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the DAO under test.\n"
        + "     * @return such DAO.\n"
        + "     */\n"
        + "    public static Mock{0}DAO getTestedInstance()\n"
         // engine name - DAO name.
        + "    '{'\n"
        + "        return m__TestedInstance;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the {0} information used for testing.\n"
         // Value Object Name
        + "     * @param {1} such information.\n"
         // value object name
        + "     */\n"
        + "    private static void set{0}({0}ValueObject {1})\n"
         // Value Object Name - Value Object Name - value object name
        + "    '{'\n"
        + "        m__ValueObject = {1};\n"
         // value object name
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the {0} information used for testing.\n"
         // Value Object Name
        + "     * @return such information.\n"
        + "     */\n"
        + "    public static {0}ValueObject get{0}()\n"
         // Value Object Name - Value Object Name
        + "    '{'\n"
        + "        return m__ValueObject;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Sets up the mock objects.\n"
        + "     */\n"
        + "    protected static void init()\n"
        + "    '{'\n"
        + "        setTestedInstance(\n"
        + "            new Mock{0}DAO() '{' '}');\n"
         // DAO name.
        + "    '}'\n\n";

    /**
     * The test suite.
     */
    public static final String DEFAULT_TEST_SUITE =
          "    /**\n"
        + "     * Executes the tests.\n"
        + "     * @return the ordered global test.\n"
        + "     */\n"
        + "    public static Test suite()\n"
        + "    '{'\n"
        + "        TestSuite result = new TestSuite(\"Mock{0}DAOTest - test suite\");\n\n"
         // DAO name.
        + "        init();\n\n"
        + "        result.addTest(new InsertMock{0}Test(\"testInsert\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTest(new FindByPrimaryKeyMock{0}Test(\"testFindByPrimaryKey\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTest(new UpdateMock{0}Test(\"testUpdate\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTest(new DeleteMock{0}Test(\"testDelete\"));\n"
         // Value Object Name - Value Object Name.
        + "        return result;\n"
        + "    '}'\n\n";
 
    /**
     * Store test.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=504">504</a>
     */
    public static final String DEFAULT_STORE_TEST =
          "    /**\n"
        + "     * Tests Mock{1}DAO.insert() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
        + "     * @see {0}.Mock{1}DAO#insert({2},org.acmsl.queryj.dao.TransactionToken)\n"
         // method parameter type declaration
        + "     */\n"
        + "    public static class InsertMock{1}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a InsertMock{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public InsertMock{1}Test(final String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the insert method.\n"
        + "         */\n"
        + "        public void testInsert()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            getTestedInstance()\n"
        + "                .insert("
        + "{3}"
         // store invokaton with test parameters
        + "\n                        null); // transactionless\n"
        + "        '}'\n\n"
        + "    '}'\n\n";

    /**
     * Test parameters' values.
     */
    public static final String DEFAULT_TEST_PARAMETERS_VALUES =
        "\n                        {0}_VALUE,";

    /**
     * Load test.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=504">504</a>
     */
    public static final String DEFAULT_LOAD_TEST =
          "    /**\n"
        + "     * Tests Mock{1}DAO.findByPrimaryKey()} method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
        + "     * @see {0}.Mock{1}DAO#findByPrimaryKey({2},org.acmsl.queryj.dao.TransactionToken)\n"
        + "     */\n"
        + "    public static class FindByPrimaryKeyMock{1}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a FindByPrimaryKeyMock{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public FindByPrimaryKeyMock{1}Test(final String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the findByPrimaryKey() method.\n"
        + "         */\n"
        + "        public void testFindByPrimaryKey()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            {1}ValueObject t_ValueObject =\n"
        + "                getTestedInstance()\n"
        + "                    .findByPrimaryKey("
        + "{3}"
         // load invokaton with test parameters
        + "\n                        null); // transactionless\n"
        + "            assertNotNull(t_ValueObject);\n"
        + "            set{1}(t_ValueObject);\n"
        + "        '}'\n\n"
        + "    '}'\n\n";
     
    /**
     * Update test.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=504">504</a>
     */
    public static final String DEFAULT_UPDATE_TEST =
          "    /**\n"
        + "     * Tests Mock{1}DAO.update() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
        + "     * @see {0}.Mock{1}DAO#update({2},org.acmsl.queryj.dao.TransactionToken)\n"
         // method parameter type declaration
        + "     */\n"
        + "    public static class UpdateMock{1}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a UpdateMock{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public UpdateMock{1}Test(final String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the update method.\n"
        + "         */\n"
        + "        public void testUpdate()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            assertNotNull(get{1}());\n\n"
        + "            getTestedInstance()\n"
        + "                .update("
        + "{3}"
         // primary key values.
        + "{4}"
         // update test parameters.
        + "\n                    null);  // no transactions\n"
        + "        '}'\n\n"
        + "    '}'\n\n";

    /**
     * Update filter values.
     */
    public static final String DEFAULT_UPDATE_FILTER_VALUES =
        "\n                    get{0}().get{1}(),";
        
    /**
     * Test parameters' updated values.
     */
    public static final String DEFAULT_TEST_PARAMETERS_UPDATED_VALUES =
        "\n                    UPDATED_{0}_VALUE,";

    /**
     * Remove test.
     * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=504">504</a>
     */
    public static final String DEFAULT_REMOVE_TEST =
          "    /**\n"
        + "     * Tests Mock{1}DAO.delete() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
        + "     * @see {0}.Mock{1}DAO#delete({2},org.acmsl.queryj.dao.TransactionToken)\n"
        + "     */\n"
        + "    public static class DeleteMock{1}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a DeleteMock{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public DeleteMock{1}Test(final String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the delete method.\n"
        + "         */\n"
        + "        public void testDelete()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            assertNotNull(get{1}());\n\n"
        + "            assertTrue(\n"
        + "                getTestedInstance()\n"
        + "                    .delete("
        + "{3}"
        + "\n                        null));  // no transactions\n"
         // remove invokaton with test pks.
        + "        '}'\n\n"
        + "    '}'\n";

    /**
     * Remove filter values.
     */
    public static final String DEFAULT_REMOVE_FILTER_VALUES =
        "\n                        get{0}().get{1}(),";
        
    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
