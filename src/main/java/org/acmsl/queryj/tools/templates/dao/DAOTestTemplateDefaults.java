//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subTemplates for creating JUnit tests to
 *              ensure generated DAOs are working fine and the connection
 *              correctly managed.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates for creating JUnit tests to ensure
 * generated DAOs are working fine and the connection correctly managed.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface DAOTestTemplateDefaults
    extends  JavaTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          HEADER
        + " *****************************************************************"
        + "*************\n"
        + " *\n"
        + " * Filename: $" + "RCSfile: $\n"
        + " *\n"
        + " * Author: QueryJ\n"
        + " *\n"
        + " * Description: Executes JUnit tests to ensure {0}DAO works as\n"
         // table
        + " *              expected and connections are correctly managed.\n"
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
        + "import {0}.{1}{2}DAO;\n"
    // DAO package - engine name - table name
        + "import {3}.{2};\n\n";
    // package - table name

    /**
     * The ACMSL imports.
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
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Executes JUnit tests to ensure {1}{2}DAO works as\n"
         // table
        + " * expected and connections are correctly managed.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @see {0}.{1}{2}DAO\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
          "public class {1}{2}DAOTest\n"
         // Table
        + "    extends  TestSuite\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * Driver class name.\n"
        + "     */\n"
        + "    public static final String DRIVER_CLASS_NAME =\n"
        + "        \"{0}\";\n\n"
         // the driver class name.
        + "    /**\n"
        + "     * The database connection''s URL.\n"
        + "     */\n"
        + "    public static final String URL_CONNECTION =\n"
        + "        \"{1}\";\n\n"
         // the database url.
        + "    /**\n"
        + "     * The database connection''s username.\n"
        + "     */\n"
        + "    public static final String USER_NAME =\n"
        + "        \"{2}\";\n\n"
         // the jdbc username.
        + "    /**\n"
        + "     * The database connection''s password.\n"
        + "     */\n"
        + "    public static final String PASSWORD =\n"
        + "        \"{3}\";\n\n"
         // the jdbc password.
        + "    /**\n"
        + "     * The mock data source.\n"
        + "     */\n"
        + "    private static MockDataSource m__DataSource;\n\n"
        + "    /**\n"
        + "     * {4} information used just for testing.\n"
         // value object name.
        + "     */\n"
        + "    private static {4} m__ValueObject;\n\n"
         // value object name.
        + "    /**\n"
        + "     * The tested instance.\n"
        + "     */\n"
        + "    private static {5}{4}DAO m__TestedInstance;\n\n";
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
        + "     * Constructs a {0}{1}DAOTest with given name.\n"
         // engine - DAO name.
        + "     * @param name the test name.\n"
        + "     */\n"
        + "    public {0}{1}DAOTest(String name)\n"
        + "    '{'\n"
        + "        super(name);\n"
        + "    '}'\n\n";

    /**
     * The inner methods.
     */
    public static final String DEFAULT_INNER_METHODS =
          "    /**\n"
        + "     * Specifies the data source.\n"
        + "     * @param dataSource the data source to use.\n"
        + "     */\n"
        + "    private static void setDataSource(MockDataSource dataSource)\n"
        + "    {\n"
        + "        m__DataSource = dataSource;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the data source.\n"
        + "     * @return such data source.\n"
        + "     */\n"
        + "    public static MockDataSource getDataSource()\n"
        + "    {\n"
        + "        return m__DataSource;\n"
        + "    }\n\n";

    /**
     * The initialization methods.
     */
    public static final String DEFAULT_INIT_METHODS =
          "    /**\n"
        + "     * Specifies the DAO to test.\n"
        + "     * @param dao the DAO to test.\n"
        + "     */\n"
        + "    private static void setTestedInstance({0}{1}DAO dao)\n"
         // engine name - DAO name.
        + "    '{'\n"
        + "        m__TestedInstance = dao;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the DAO under test.\n"
        + "     * @return such DAO.\n"
        + "     */\n"
        + "    public static {0}{1}DAO getTestedInstance()\n"
         // engine name - DAO name.
        + "    '{'\n"
        + "        return m__TestedInstance;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the {1} information used for testing.\n"
         // Value Object Name
        + "     * @param {2} such information.\n"
         // value object name
        + "     */\n"
        + "    private static void set{1}({1} {2})\n"
         // Value Object Name - Value Object Name - value object name
        + "    '{'\n"
        + "        m__ValueObject = {2};\n"
         // value object name
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the {1} information used for testing.\n"
         // Value Object Name
        + "     * @return such information.\n"
        + "     */\n"
        + "    public static {1} get{1}()\n"
         // Value Object Name - Value Object Name
        + "    '{'\n"
        + "        return m__ValueObject;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Sets up the mock objects.\n"
        + "     */\n"
        + "    protected static void init()\n"
        + "    '{'\n"
        + "        setDataSource(\n"
        + "            new MockDataSource(\n"
        + "                DRIVER_CLASS_NAME,\n"
        + "                URL_CONNECTION,\n"
        + "                USER_NAME,\n"
        + "                PASSWORD));\n\n"
        + "        setTestedInstance(\n"
        + "            new {0}{1}DAO(getDataSource()) '{' '}');\n"
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
        + "        TestSuite result = new TestSuite(\"{0}{1}DAOTest - test suite\");\n\n"
         // DAO name.
        + "        init();\n\n"
        + "        result.addTest(new ConstructorTest(\"testConstructor\"));\n"
        + "        result.addTestSuite(ConnectionTest.class);\n"
        + "        result.addTest(new Insert{1}Test(\"testInsert\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTestSuite(ConnectionTest.class);\n"
        + "        result.addTest(new FindByPrimaryKey{1}Test(\"testFindByPrimaryKey\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTestSuite(ConnectionTest.class);\n"
        + "        result.addTest(new Update{1}Test(\"testUpdate\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTestSuite(ConnectionTest.class);\n"
        + "        result.addTest(new Delete{1}Test(\"testDelete\"));\n"
         // Value Object Name - Value Object Name.
        + "        result.addTestSuite(ConnectionTest.class);\n\n"
        + "        return result;\n"
        + "    '}'\n\n";
 
    /**
     * The constructor test.
     */
    public static final String DEFAULT_CONSTRUCTOR_TEST =
          "    /**\n"
        + "     * Tests {0}{1}DAO constructor.\n"
         // engine name - DAO name.
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     */\n"
        + "    public static class ConstructorTest\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a ConstructorTest with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public ConstructorTest(String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the constructor.\n"
        + "         */\n"
        + "        public void testConstructor()\n"
        + "        '{'\n"
        + "            {0}{1}DAO t_TestedInstance = getTestedInstance();\n"
         // engine name - DAO name.
        + "            assertTrue(t_TestedInstance != null);\n\n"
        + "            DataSource t_DataSource = t_TestedInstance.getDataSource();\n"
        + "            assertNotNull(t_DataSource);\n\n"
        + "            assertTrue(t_DataSource == getDataSource());\n"
        + "            assertTrue(t_DataSource.equals(getDataSource()));\n"
        + "        '}'\n\n"
        + "    '}'\n\n";

    /**
     * Connection test.
     */
    public static final String DEFAULT_CONNECTION_TEST =
          "    /**\n"
        + "     * Tests whether the connection is managed correctly.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     */\n"
        + "    public static class ConnectionTest\n"
        + "        extends  TestCase\n"
        + "    {\n"
        + "        /**\n"
        + "         * Constructs a ConnectionTest with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public ConnectionTest(String name)\n"
        + "        {\n"
        + "            super(name);\n"
        + "        }\n\n"
        + "        /**\n"
        + "         * Tests if the <code>close()</code> method is invoked for each opened\n"
        + "         * connection.\n"
        + "         */\n"
        + "        public void testConnectionClosed()\n"
        + "        {\n"
        + "            assertTrue(\n"
        + "                   getDataSource().getOpenedConnections()\n"
        + "                == getDataSource().getCloseMethodCalls());\n"
        + "        }\n\n"
        + "        /**\n"
        + "         * Tests if there's been any rollback.\n"
        + "         */\n"
        + "        public void testNotRollbacks()\n"
        + "        {\n"
        + "            assertTrue(\n"
        + "               getDataSource().getRollbackMethodCalls() == 0);\n"
        + "        }\n\n"
        + "        /**\n"
        + "         * Tests if there's been any exception.\n"
        + "         */\n"
        + "        public void testNotExceptions()\n"
        + "        {\n"
        + "            Iterator t_ExceptionIterator = getDataSource().exceptionIterator();\n\n"
        + "            assertTrue(\n"
        + "                (   (t_ExceptionIterator == null)\n"
        + "                 || (!t_ExceptionIterator.hasNext())));\n"
        + "        }\n\n"
        + "    }\n\n";

    /**
     * Store test.
     */
    public static final String DEFAULT_STORE_TEST =
          "    /**\n"
        + "     * Tests {1}{2}DAO.insert() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @see {0}.{1}{2}DAO#insert({3},org.acmsl.queryj.dao.TransactionToken)\n"
         // method parameter type declaration
        + "     */\n"
        + "    public static class Insert{2}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a Insert{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public Insert{2}Test(final String name)\n"
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
        + "{4});\n"
         // store invokaton with test parameters
        + "        '}'\n\n"
        + "    '}'\n\n";

    /**
     * Test parameters' values.
     */
    public static final String DEFAULT_TEST_PARAMETERS_VALUES =
        "\n                        {0}_VALUE";

    /**
     * Test nullable parameters' values.
     */
    public static final String DEFAULT_TEST_NULLABLE_PARAMETERS_VALUES =
        "\n                        new {1}({0}_VALUE)";

    /**
     * Load test.
     */
    public static final String DEFAULT_LOAD_TEST =
          "    /**\n"
        + "     * Tests {1}{2}DAO.findByPrimaryKey()} method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @see {0}.{1}{2}DAO#findByPrimaryKey({3},org.acmsl.queryj.dao.TransactionToken)\n"
        + "     */\n"
        + "    public static class FindByPrimaryKey{2}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a FindByPrimaryKey{2}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public FindByPrimaryKey{2}Test(String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the findByPrimaryKey() method.\n"
        + "         */\n"
        + "        public void testFindByPrimaryKey()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            {2} t_ValueObject =\n"
        + "                getTestedInstance()\n"
        + "                    .findByPrimaryKey("
        + "{4});\n"
         // load invokaton with test parameters
        + "            assertNotNull(t_ValueObject);\n"
        + "            set{2}(t_ValueObject);\n"
        + "        '}'\n\n"
        + "    '}'\n\n";
     
    /**
     * Update test.
     */
    public static final String DEFAULT_UPDATE_TEST =
          "    /**\n"
        + "     * Tests {1}{2}DAO.update() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @see {0}.{1}{2}DAO#update({3},org.acmsl.queryj.dao.TransactionToken)\n"
         // method parameter type declaration
        + "     */\n"
        + "    public static class Update{2}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a Update{1}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public Update{2}Test(String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the update method.\n"
        + "         */\n"
        + "        public void testUpdate()\n"
        + "        '{'\n"
        + "            assertNotNull(getTestedInstance());\n\n"
        + "            assertNotNull(get{2}());\n\n"
        + "            /*\n"
        + "            getTestedInstance()\n"
        + "                .update("
        + "{4}"
         // primary key values.
        + "{5});\n"
         // update test parameters.
        + "            */\n"
        + "        '}'\n\n"
        + "    '}'\n\n";

    /**
     * Update filter values.
     */
    public static final String DEFAULT_UPDATE_FILTER_VALUES =
        "\n                    get{0}().get{1}()";
        
    /**
     * Test parameters' updated values.
     */
    public static final String DEFAULT_TEST_PARAMETERS_UPDATED_VALUES =
        "\n                    UPDATED_{0}_VALUE";

    /**
     * Remove test.
     */
    public static final String DEFAULT_REMOVE_TEST =
          "    /**\n"
        + "     * Tests {1}{2}DAO.delete() method.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @see {0}.{1}{2}DAO#delete({3},org.acmsl.queryj.dao.TransactionToken)\n"
        + "     */\n"
        + "    public static class Delete{2}Test\n"
        + "        extends  TestCase\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * Constructs a Delete{2}Test with given name.\n"
        + "         * @param name the test name.\n"
        + "         */\n"
        + "        public Delete{2}Test(String name)\n"
        + "        '{'\n"
        + "            super(name);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Tests the delete method.\n"
        + "         */\n"
        + "        public void testDelete()\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                assertNotNull(getTestedInstance());\n\n"
        + "                assertNotNull(get{2}());\n\n"
        + "                getTestedInstance()\n"
        + "                    .delete("
        + "{4});\n"
        + "            '}'\n"
        + "            catch  (final Throwable throwable)\n"
        + "            '{'\n"
        + "                fail(throwable.getMessage());\n"
        + "            '}'\n"
         // remove invokaton with test pks.
        + "        '}'\n\n"
        + "    '}'\n";

    /**
     * Remove filter values.
     */
    public static final String DEFAULT_REMOVE_FILTER_VALUES =
        "\n                        get{0}().get{1}()";
        
    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
