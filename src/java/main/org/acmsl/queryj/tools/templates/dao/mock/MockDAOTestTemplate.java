/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
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
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Template for creating JUnit tests to ensure generated Mock
 *              DAOs are working correctly.
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
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Template for creating JUnit tests to ensure generated Mock DAOs
 * are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class MockDAOTestTemplate
    implements  TestTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendáriz\n"
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
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " * @see {0}.{1}DAO\n"
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
        + "     * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
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
        + "     * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
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
        + "     * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
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
        + "     * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
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

    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The DAO package name.
     */
    private String m__strDAOPackageName;

    /**
     * The value object's package name.
     */
    private String m__strValueObjectPackageName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The ACM-SL imports.
     */
    private String m__strAcmslImports;

    /**
     * The JDK imports.
     */
    private String m__strJdkImports;

    /**
     * The JUnit imports.
     */
    private String m__strJUnitImports;

    /**
     * The Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The test values.
     */
    private String m__strTestValues;

    /**
     * The test updated values.
     */
    private String m__strTestUpdatedValues;

    /**
     * The constructor.
     */
    private String m__strConstructor;

    /**
     * The inner methods.
     */
    private String m__strInnerMethods;

    /**
     * The init methods.
     */
    private String m__strInitMethods;

    /**
     * The test suite.
     */
    private String m__strTestSuite;

    /**
     * The store test.
     */
    private String m__strStoreTest;

    /**
     * The test values subtemplate.
     */
    private String m__strTestParametersValues;

    /**
     * The load test.
     */
    private String m__strLoadTest;

    /**
     * The update test.
     */
    private String m__strUpdateTest;

    /**
     * The update filter values subtemplate.
     */
    private String m__strUpdateFilterValues;

    /**
     * The test updated values subtemplate.
     */
    private String m__strTestParametersUpdatedValues;

    /**
     * The remove test.
     */
    private String m__strRemoveTest;

    /**
     * The remove filter values subtemplate.
     */
    private String m__strRemoveFilterValues;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a DAOTestTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JUnit imports.
     * @param javadoc the class javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param testValues the test values.
     * @param testUpdatedValues the test updated values.
     * @param constructor the class constructor.
     * @param innerMethods the inner methods.
     * @param initMethods the init methods.
     * @param testSuite the test suite.
     * @param storeTest the store test.
     * @param testParametersValues the test values
     * subtemplate.
     * @param loadTest the load test.
     * @param updateTest the update test.
     * @param updateFilterValues the update filter values
     * subtemplate.
     * @param testParametersUpdatedValues the test updated
     * values subtemplate.
     * @param removeTest the remove test.
     * @param removeFilterValues the remove filter values
     * subtemplate.
     * @param classEnd the class end.
     */
    public MockDAOTestTemplate(
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  header,
        String                  packageDeclaration,
        String                  packageName,
        String                  daoPackageName,
        String                  valueObjectPackageName,
        String                  projectImports,
        String                  acmslImports,
        String                  jdkImports,
        String                  junitImports,
        String                  javadoc,
        String                  classDefinition,
        String                  classStart,
        String                  testValues,
        String                  testUpdatedValues,
        String                  constructor,
        String                  innerMethods,
        String                  initMethods,
        String                  testSuite,
        String                  storeTest,
        String                  testParametersValues,
        String                  loadTest,
        String                  updateTest,
        String                  updateFilterValues,
        String                  testParametersUpdatedValues,
        String                  removeTest,
        String                  removeFilterValues,
        String                  classEnd)
    {
        inmutableSetTableTemplate(tableTemplate);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetDAOPackageName(daoPackageName);
        inmutableSetValueObjectPackageName(valueObjectPackageName);
        inmutableSetProjectImports(projectImports);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJUnitImports(junitImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetTestValues(testValues);
        inmutableSetTestUpdatedValues(testUpdatedValues);
        inmutableSetConstructor(constructor);
        inmutableSetInnerMethods(innerMethods);
        inmutableSetInitMethods(initMethods);
        inmutableSetTestSuite(testSuite);
        inmutableSetStoreTest(storeTest);
        inmutableSetTestParametersValues(testParametersValues);
        inmutableSetLoadTest(loadTest);
        inmutableSetUpdateTest(updateTest);
        inmutableSetUpdateFilterValues(updateFilterValues);
        inmutableSetTestParametersUpdatedValues(testParametersUpdatedValues);
        inmutableSetRemoveTest(removeTest);
        inmutableSetRemoveFilterValues(removeFilterValues);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a DAOTestTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectsPackageName the value objects' package name.
     */
    public MockDAOTestTemplate(
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  packageName,
        String                  daoPackageName,
        String                  valueObjectsPackageName)
    {
        this(
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            daoPackageName,
            valueObjectsPackageName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_TEST_VALUES,
            DEFAULT_TEST_UPDATED_VALUES,
            DEFAULT_CONSTRUCTOR,
            DEFAULT_INNER_METHODS,
            DEFAULT_INIT_METHODS,
            DEFAULT_TEST_SUITE,
            DEFAULT_STORE_TEST,
            DEFAULT_TEST_PARAMETERS_VALUES,
            DEFAULT_LOAD_TEST,
            DEFAULT_UPDATE_TEST,
            DEFAULT_UPDATE_FILTER_VALUES,
            DEFAULT_TEST_PARAMETERS_UPDATED_VALUES,
            DEFAULT_REMOVE_TEST,
            DEFAULT_REMOVE_FILTER_VALUES,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void inmutableSetTableTemplate(TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(TableTemplate tableTemplate)
    {
        inmutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such template.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }


    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    private void inmutableSetMetaDataManager(DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    protected void setMetaDataManager(DatabaseMetaDataManager metaDataManager)
    {
        inmutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(String header)
    {
        inmutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void inmutableSetPackageDeclaration(String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(String packageDeclaration)
    {
        inmutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void inmutableSetPackageName(String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
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
     * Specifies the DAO package name.
     * @return such package.
     */
    private void inmutableSetDAOPackageName(String value)
    {
        m__strDAOPackageName = value;
    }

    /**
     * Specifies the DAO package name.
     * @param value such package.
     */
    protected void setDAOPackageName(String value)
    {
        inmutableSetDAOPackageName(value);
    }

    /**
     * Retrieves the DAO package name
     * @return such package.
     */
    public String getDAOPackageName()
    {
        return m__strDAOPackageName;
    }

    /**
     * Specifies the value object's package name.
     * @return such package.
     */
    private void inmutableSetValueObjectPackageName(String value)
    {
        m__strValueObjectPackageName = value;
    }

    /**
     * Specifies the value object's package name.
     * @param value such package.
     */
    protected void setValueObjectPackageName(String value)
    {
        inmutableSetValueObjectPackageName(value);
    }

    /**
     * Retrieves the value object's package name
     * @return such package.
     */
    public String getValueObjectPackageName()
    {
        return m__strValueObjectPackageName;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void inmutableSetProjectImports(String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(String projectImports)
    {
        inmutableSetProjectImports(projectImports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void inmutableSetAcmslImports(String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(String acmslImports)
    {
        inmutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(String jdkImports)
    {
        inmutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK Imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the JUnit imports.
     * @param junitImports the new JUnit imports.
     */
    private void inmutableSetJUnitImports(String junitImports)
    {
        m__strJUnitImports = junitImports;
    }

    /**
     * Specifies the JUnit imports.
     * @param junitImports the new JUnit imports.
     */
    protected void setJUnitImports(String junitImports)
    {
        inmutableSetJUnitImports(junitImports);
    }

    /**
     * Retrieves the JUnit imports.
     * @return such information.
     */
    public String getJUnitImports() 
    {
        return m__strJUnitImports;
    }

    /**
     * Specifies the Javadoc.
     * @param javadoc the new Javadoc.
     */
    private void inmutableSetJavadoc(String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the Javadoc.
     * @param javadoc the new Javadoc.
     */
    protected void setJavadoc(String javadoc)
    {
        inmutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void inmutableSetClassDefinition(String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(String classDefinition)
    {
        inmutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void inmutableSetClassStart(String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(String classStart)
    {
        inmutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the test values.
     * @param testValues the new test values.
     */
    private void inmutableSetTestValues(String testValues)
    {
        m__strTestValues = testValues;
    }

    /**
     * Specifies the test values.
     * @param testValues the new test values.
     */
    protected void setTestValues(String testValues)
    {
        inmutableSetTestValues(testValues);
    }

    /**
     * Retrieves the test values.
     * @return such information.
     */
    public String getTestValues() 
    {
        return m__strTestValues;
    }

    /**
     * Specifies the test updated values.
     * @param testUpdatedValues the new test updated values.
     */
    private void inmutableSetTestUpdatedValues(String testUpdatedValues)
    {
        m__strTestUpdatedValues = testUpdatedValues;
    }

    /**
     * Specifies the test updated values.
     * @param testUpdatedValues the new test updated values.
     */
    protected void setTestUpdatedValues(String testUpdatedValues)
    {
        inmutableSetTestUpdatedValues(testUpdatedValues);
    }

    /**
     * Retrieves the test updated values.
     * @return such information.
     */
    public String getTestUpdatedValues() 
    {
        return m__strTestUpdatedValues;
    }

    /**
     * Specifies the constructor.
     * @param constructor the new constructor.
     */
    private void inmutableSetConstructor(String constructor)
    {
        m__strConstructor = constructor;
    }

    /**
     * Specifies the constructor.
     * @param constructor the new constructor.
     */
    protected void setConstructor(String constructor)
    {
        inmutableSetConstructor(constructor);
    }

    /**
     * Retrieves the constructor.
     * @return such information.
     */
    public String getConstructor() 
    {
        return m__strConstructor;
    }

    /**
     * Specifies the inner methods.
     * @param innerMethods the new inner methods.
     */
    private void inmutableSetInnerMethods(String innerMethods)
    {
        m__strInnerMethods = innerMethods;
    }

    /**
     * Specifies the inner methods.
     * @param innerMethods the new inner methods.
     */
    protected void setInnerMethods(String innerMethods)
    {
        inmutableSetInnerMethods(innerMethods);
    }

    /**
     * Retrieves the inner methods.
     * @return such information.
     */
    public String getInnerMethods() 
    {
        return m__strInnerMethods;
    }

    /**
     * Specifies the init methods.
     * @param initMethods the new init methods.
     */
    private void inmutableSetInitMethods(String initMethods)
    {
        m__strInitMethods = initMethods;
    }

    /**
     * Specifies the init methods.
     * @param initMethods the new init methods.
     */
    protected void setInitMethods(String initMethods)
    {
        inmutableSetInitMethods(initMethods);
    }

    /**
     * Retrieves the init methods.
     * @return such information.
     */
    public String getInitMethods() 
    {
        return m__strInitMethods;
    }

    /**
     * Specifies the test suite.
     * @param testSuite the new test suite.
     */
    private void inmutableSetTestSuite(String testSuite)
    {
        m__strTestSuite = testSuite;
    }

    /**
     * Specifies the test suite.
     * @param testSuite the new test suite.
     */
    protected void setTestSuite(String testSuite)
    {
        inmutableSetTestSuite(testSuite);
    }

    /**
     * Retrieves the test suite.
     * @return such information.
     */
    public String getTestSuite() 
    {
        return m__strTestSuite;
    }

    /**
     * Specifies the store test.
     * @param storeTest the new store test.
     */
    private void inmutableSetStoreTest(String storeTest)
    {
        m__strStoreTest = storeTest;
    }

    /**
     * Specifies the store test.
     * @param storeTest the new store test.
     */
    protected void setStoreTest(String storeTest)
    {
        inmutableSetStoreTest(storeTest);
    }

    /**
     * Retrieves the store test.
     * @return such information.
     */
    public String getStoreTest() 
    {
        return m__strStoreTest;
    }

    /**
     * Specifies the test values subtemplate.
     * @param template the template.
     */
    private void inmutableSetTestParametersValues(String template)
    {
        m__strTestParametersValues = template;
    }

    /**
     * Specifies the test values subtemplate.
     * @param template the template.
     */
    protected void setTestParametersValues(String template)
    {
        inmutableSetTestParametersValues(template);
    }

    /**
     * Retrieves the test values subtemplate.
     * @return such source code template.
     */
    public String getTestParametersValues()
    {
        return m__strTestParametersValues;
    }

    /**
     * Specifies the load test.
     * @param loadTest the new load test.
     */
    private void inmutableSetLoadTest(String loadTest)
    {
        m__strLoadTest = loadTest;
    }

    /**
     * Specifies the load test.
     * @param loadTest the new load test.
     */
    protected void setLoadTest(String loadTest)
    {
        inmutableSetLoadTest(loadTest);
    }

    /**
     * Retrieves the load test.
     * @return such information.
     */
    public String getLoadTest() 
    {
        return m__strLoadTest;
    }

    /**
     * Specifies the update test.
     * @param updateTest the new update test.
     */
    private void inmutableSetUpdateTest(String updateTest)
    {
        m__strUpdateTest = updateTest;
    }

    /**
     * Specifies the update test.
     * @param updateTest the new update test.
     */
    protected void setUpdateTest(String updateTest)
    {
        inmutableSetUpdateTest(updateTest);
    }

    /**
     * Retrieves the update test.
     * @return such information.
     */
    public String getUpdateTest() 
    {
        return m__strUpdateTest;
    }


    /**
     * Specifies the test updated values subtemplate.
     * @param template the template.
     */
    private void inmutableSetTestParametersUpdatedValues(String template)
    {
        m__strTestParametersUpdatedValues = template;
    }

    /**
     * Specifies the test updated values subtemplate.
     * @param template the template.
     */
    protected void setTestParametersUpdatedValues(String template)
    {
        inmutableSetTestParametersUpdatedValues(template);
    }

    /**
     * Retrieves the test updated values subtemplate.
     * @return such source code template.
     */
    public String getTestParametersUpdatedValues()
    {
        return m__strTestParametersUpdatedValues;
    }

    /**
     * Specifies the update filter values.
     * @param updateFilterValues the new update filter values.
     */
    private void inmutableSetUpdateFilterValues(String updateFilterValues)
    {
        m__strUpdateFilterValues = updateFilterValues;
    }

    /**
     * Specifies the update filter values.
     * @param updateFilterValues the new update filter values.
     */
    protected void setUpdateFilterValues(String updateFilterValues)
    {
        inmutableSetUpdateFilterValues(updateFilterValues);
    }

    /**
     * Retrieves the update filter values.
     * @return such information.
     */
    public String getUpdateFilterValues()
    {
        return m__strUpdateFilterValues;
    }

    /**
     * Specifies the remove test.
     * @param removeTest the new remove test.
     */
    private void inmutableSetRemoveTest(String removeTest)
    {
        m__strRemoveTest = removeTest;
    }

    /**
     * Specifies the remove test.
     * @param removeTest the new remove test.
     */
    protected void setRemoveTest(String removeTest)
    {
        inmutableSetRemoveTest(removeTest);
    }

    /**
     * Retrieves the removeTest.
     * @return such information.
     */
    public String getRemoveTest() 
    {
        return m__strRemoveTest;
    }

    /**
     * Specifies the remove filter values.
     * @param removeFilterValues the new remove filter values.
     */
    private void inmutableSetRemoveFilterValues(String removeFilterValues)
    {
        m__strRemoveFilterValues = removeFilterValues;
    }

    /**
     * Specifies the remove filter values.
     * @param removeFilterValues the new remove filter values.
     */
    protected void setRemoveFilterValues(String removeFilterValues)
    {
        inmutableSetRemoveFilterValues(removeFilterValues);
    }

    /**
     * Retrieves the remove filter values.
     * @return such information.
     */
    public String getRemoveFilterValues()
    {
        return m__strRemoveFilterValues;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void inmutableSetClassEnd(String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(String classEnd)
    {
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Retrieves the test name.
     * @return such name.
     */
    public String getTestName()
    {
        return
              "Mock"
            + StringUtils.getInstance().capitalize(
                  getTableTemplate().getTableName().toLowerCase(),
                  '_')
            + "DAOTest";
    }


    /**
     * Indicates if the test should be considered a suite or not.
     * @return such information.
     */
    public boolean isSuite()
    {
        return true;
    }

    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        if  (   (t_TableTemplate                 != null)
             && (t_MetaDataManager               != null)
             && (t_MetaDataUtils                 != null)
             && (t_MetaDataManager.getMetaData() != null)
             && (t_StringUtils                   != null))
        {
            String t_strCapitalizedTableName =
                t_StringUtils.capitalize(
                    t_TableTemplate.getTableName().toLowerCase(),
                    '_');
            /*
            try 
            */
            {
                DatabaseMetaData t_MetaData = t_MetaDataManager.getMetaData();

                MessageFormat t_Formatter = new MessageFormat(getHeader());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[] { t_TableTemplate.getTableName() }));

                t_Formatter = new MessageFormat(getPackageDeclaration());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]{getPackageName()}));

                t_Formatter = new MessageFormat(getProjectImports());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName,
                            getValueObjectPackageName()
                        }));

                t_sbResult.append(getAcmslImports());
                t_sbResult.append(getJdkImports());
                t_sbResult.append(getJUnitImports());

                t_Formatter = new MessageFormat(getJavadoc());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName
                        }));

                t_Formatter = new MessageFormat(getClassDefinition());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName
                        }));

                t_Formatter = new MessageFormat(getClassStart());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName
                        }));

                t_sbResult.append(getTestValues());

                t_sbResult.append(getTestUpdatedValues());

                t_Formatter = new MessageFormat(getConstructor());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName
                        }));

                t_sbResult.append(getInnerMethods());

                t_Formatter = new MessageFormat(getInitMethods());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName,
                              t_strCapitalizedTableName
                                  .substring(0,1).toLowerCase()
                            + t_strCapitalizedTableName.substring(1)
                        }));

                t_Formatter = new MessageFormat(getTestSuite());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName
                        }));

                t_Formatter = new MessageFormat(getTestParametersValues());

                MessageFormat t_UpdateFilterValuesFormatter =
                    new MessageFormat(getUpdateFilterValues());

                StringBuffer t_sbUpdateFilterValues =
                    new StringBuffer();

                String[] t_astrPrimaryKeys =
                    t_MetaDataManager.getPrimaryKeys(
                        t_TableTemplate.getTableName());

                StringBuffer t_sbFindByPrimaryKeyTestParametersValues =
                    new StringBuffer();

                StringBuffer t_sbFindByPrimaryKeyParametersTypes =
                    new StringBuffer();

                StringBuffer t_sbUpdateParametersTypes =
                    new StringBuffer();

                MessageFormat t_RemoveFilterValuesFormatter =
                    new MessageFormat(getRemoveFilterValues());

                StringBuffer t_sbRemoveFilterValues =
                    new StringBuffer();

                if  (t_astrPrimaryKeys != null)
                {

                    for  (int t_iPkIndex = 0;
                              t_iPkIndex < t_astrPrimaryKeys.length;
                              t_iPkIndex++) 
                    {
                        t_sbFindByPrimaryKeyTestParametersValues.append(
                            t_Formatter.format(
                                new Object[]
                                {
                                    t_MetaDataUtils.getNativeType(
                                        t_MetaDataManager.getColumnType(
                                            t_TableTemplate.getTableName(),
                                            t_astrPrimaryKeys[t_iPkIndex]))
                                        .toUpperCase()
                                }));

                        t_sbUpdateFilterValues.append(
                            t_UpdateFilterValuesFormatter.format(
                                new Object[]
                                {
                                    t_strCapitalizedTableName,
                                    t_StringUtils.capitalize(
                                        t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                        '_')
                                }));

                        t_sbRemoveFilterValues.append(
                            t_RemoveFilterValuesFormatter.format(
                                new Object[]
                                {
                                    t_strCapitalizedTableName,
                                    t_StringUtils.capitalize(
                                        t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                        '_')
                                }));

                        t_sbFindByPrimaryKeyParametersTypes.append(
                            t_MetaDataUtils.getNativeType(
                                t_MetaDataManager.getColumnType(
                                    t_TableTemplate.getTableName(),
                                    t_astrPrimaryKeys[t_iPkIndex])));

                        if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                        {
                            t_sbFindByPrimaryKeyParametersTypes.append(",");
                        }

                        t_sbUpdateParametersTypes.append(
                            t_MetaDataUtils.getNativeType(
                                t_MetaDataManager.getColumnType(
                                    t_TableTemplate.getTableName(),
                                    t_astrPrimaryKeys[t_iPkIndex])));

                        t_sbUpdateParametersTypes.append(",");
                    }
                }

                String[] t_astrColumnNames =
                    t_MetaDataManager.getColumnNames(
                        t_TableTemplate.getTableName());

                StringBuffer t_sbInsertTestParametersValues =
                    new StringBuffer();

                StringBuffer t_sbInsertParametersTypes =
                    new StringBuffer();

                MessageFormat t_TestParametersUpdatedValuesFormatter =
                    new MessageFormat(getTestParametersUpdatedValues());

                StringBuffer t_sbTestParametersUpdatedValues =
                    new StringBuffer();

                if  (t_astrColumnNames != null)
                {
                    for  (int t_iColumnIndex = 0;
                              t_iColumnIndex < t_astrColumnNames.length;
                              t_iColumnIndex++)
                    {
                        if  (!t_MetaDataManager.isManagedExternally(
                                 t_TableTemplate.getTableName(),
                                 t_astrColumnNames[t_iColumnIndex]))
                        {
                            t_sbInsertTestParametersValues.append(
                                t_Formatter.format(
                                    new Object[]
                                    {
                                        t_MetaDataUtils.getNativeType(
                                            t_MetaDataManager.getColumnType(
                                                t_TableTemplate.getTableName(),
                                                t_astrColumnNames[
                                                    t_iColumnIndex]))
                                        .toUpperCase()
                                }));

                            t_sbInsertParametersTypes.append(
                                t_MetaDataUtils.getNativeType(
                                    t_MetaDataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_astrColumnNames[t_iColumnIndex])));

                            if  (!t_MetaDataManager.isPrimaryKey(
                                     t_TableTemplate.getTableName(),
                                     t_astrColumnNames[t_iColumnIndex]))
                            {
                                t_sbTestParametersUpdatedValues.append(
                                    t_TestParametersUpdatedValuesFormatter.format(
                                        new Object[]
                                        {
                                            t_MetaDataUtils.getNativeType(
                                                t_MetaDataManager.getColumnType(
                                                    t_TableTemplate.getTableName(),
                                                    t_astrColumnNames[
                                                        t_iColumnIndex]))
                                            .toUpperCase()
                                        }));

                                t_sbUpdateParametersTypes.append(
                                    t_MetaDataUtils.getNativeType(
                                        t_MetaDataManager.getColumnType(
                                            t_TableTemplate.getTableName(),
                                            t_astrColumnNames[
                                                t_iColumnIndex])));

                                if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                                {
                                    t_sbUpdateParametersTypes.append(",");
                                }
                            }

                            if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                            {
                                    t_sbInsertParametersTypes.append(",");
                            }
                        }
                    }
                }

                t_Formatter = new MessageFormat(getStoreTest());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName,
                            t_sbInsertParametersTypes,
                            t_sbInsertTestParametersValues
                        }));

                t_Formatter = new MessageFormat(getLoadTest());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName,
                            t_sbFindByPrimaryKeyParametersTypes,
                            t_sbFindByPrimaryKeyTestParametersValues
                        }));

                t_Formatter = new MessageFormat(getUpdateTest());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName,
                            t_sbUpdateParametersTypes,
                            t_sbUpdateFilterValues,
                            t_sbTestParametersUpdatedValues
                        }));

                t_Formatter = new MessageFormat(getRemoveTest());

                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            getDAOPackageName(),
                            t_strCapitalizedTableName,
                            t_sbFindByPrimaryKeyParametersTypes,
                            t_sbRemoveFilterValues
                        }));

                t_sbResult.append(getClassEnd());
            }
            /*
            catch  (SQLException sqlException)
            {
                LogFactory.getLog(getClass()).error(
                    "database.meta.data.error",
                    sqlException);
            }
            */
        }

        return t_sbResult.toString();
    }
}
