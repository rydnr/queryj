/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Template for creating JUnit tests associated to Database's
 *              functions.
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
package org.acmsl.queryj.tools.templates.functions;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.templates.functions.FunctionsTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
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
 * Template for creating JUnit tests associated to Database's
 * functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class FunctionsTestTemplate
    extends     FunctionsTemplate
    implements  TestTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
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
        + "    Contact info: jsanleandro@yahoo.es\n"
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
        + " * Description: Executes JUnit tests for the {2} functions "
         // function description
        + "supported by {0} {1}.\n"
         // engine name - engine version
        + " *\n"
        + " * Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + " *\n"
        + " * File version: $" + "Revision: $\n"
        + " *\n"
        + " * Project version: $" + "Name: $\n"
        + " *\n"
        + " * $" + "Id: $\n"
        + " */\n";

    /**
     * The project-specific imports.
     */
    public static final String PROJECT_IMPORTS =
          "/*\n"
        + " * Importing project classes.\n"
        + " */\n"
        + "import {0}.{1}Functions;\n\n";
    // package - class prefix

    /**
     * The JUnit imports.
     */
    public static final String JUNIT_IMPORTS =
          "/*\n"
        + " * Importing some JUnit classes.\n"
        + " */\n"
        + "import junit.framework.TestCase;\n\n";
    
    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Executes JUnit tests for the {2} functions "
         // function description
        + "supported by {0} {1}.\n"
         // engine name - engine version
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class {0}FunctionsTest\n"
         // class prefix
        + "    extends  TestCase\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * The tested instance.\n"
        + "     */\n"
        + "    private {0}Functions m__TestedInstance;\n\n"
         // Class prefix
        + "    /**\n"
        + "     * The test table.\n"
        + "     */\n"
        + "    public static final UsersTable USERS = new UsersTable();\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](DoubleField) or [Any]FIeld [method](double)
     */
    public static final String ANY__DOUBLE_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Double()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.MARK);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
        + "     * @see {1}.{5}Functions#{0}(double)\n"
          // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}double()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(8.9D);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(8.9)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](DoubleField, IntField) or
     * [Any]Field [method](DoubleField, int) or
     * [Any]Field [method](double, IntField) or
     * [Any]Field [method](double, int).
     */
    public static final String ANY__DOUBLE_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}DoubleInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.MARK, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Doubleint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.MARK, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}doubleInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix

        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                8.9D, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}8.9{4},USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}doubleint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(8.9D, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}8.9{4},2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](IntField, IntField) or [Any]FIeld [method](IntField, int)
     * or [Any]Field [method](int, IntField) or [Any]Field [method](int, int)
     */
    public static final String ANY__INT_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}IntInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.LICENSE_EXPIRATION, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Intint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.LICENSE_EXPIRATION, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix

        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                3, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(3,USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(3, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(3,2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](LongField, LongField) or [Any]FIeld [method](LongField, long)
     * or [Any]Field [method](long, LongField) or [Any]Field [method](long, long)
     */
    public static final String ANY__LONG_LONG_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.LongField, "
        + " org.acmsl.queryj.LongField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}LongLong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.MONEY_AMOUNT, USERS.MONEY_AMOUNT);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MONEY_AMOUNT,USERS.MONEY_AMOUNT)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.LongField, long)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Longlong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.MONEY_AMOUNT, 2L);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MONEY_AMOUNT,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(long, "
        + " org.acmsl.queryj.LongField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}longLong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix

        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                3L, USERS.MONEY_AMOUNT);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(3,USERS.MONEY_AMOUNT)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(long, long)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}longlong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(3L, 2L);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(3,2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](DoubleField, LongField) or [Any]FIeld [method](DoubleField, long)
     * or [Any]Field [method](double, LongField) or [Any]Field [method](double, long)
     */
    public static final String ANY__DOUBLE_LONG_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // package name - class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField, "
        + " org.acmsl.queryj.LongField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}DoubleLong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.MARK, USERS.MONEY_AMOUNT);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,USERS.MONEY_AMOUNT)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField, long)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Doublelong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.MARK, 2L);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,2)\");\n"
          // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, "
         // package name - class prefix - Java method function
        + " org.acmsl.queryj.LongField)\n"
        + "     */\n"
        + "    public void {2}doubleLong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                8.9D, USERS.MONEY_AMOUNT);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(8.9,USERS.MONEY_AMOUNT)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, long)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}doublelong()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(8.9D, 2L);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(8.9,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](CalendarField) or [Any]Field [method](Calendar)
     */
    public static final String ANY__CALENDAR_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Calendar()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.CREATED);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar)\n"
          // package name - Java method function
        + "     */\n"
        + "    public void {2}calendar()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field = t_Functions.{0}(t_Now);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4})\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](IntField) or [Any]Field [method](int)
     */
    public static final String ANY__INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Int()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int)\n"
          // package name - Java method function
        + "     */\n"
        + "    public void {2}int()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}(2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](LongField) or [Any]Field [method](long)
     */
    public static final String ANY__LONG_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.LongField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Long()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.MONEY_AMOUNT);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MONEY_AMOUNT)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(long)\n"
          // package name - Java method function
        + "     */\n"
        + "    public void {2}long()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(2L);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}(2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField) or [Any]Field [method](String)
     */
    public static final String ANY__STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}String()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String)\n"
          // package name - Java method function
        + "     */\n"
        + "    public void {2}string()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}({4}queryj{4})\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField[]) or [Any]Field [method](String[])
     */
    public static final String ANY__ASTRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField[])\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}AString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(new StringField[]'{'USERS.NAME, USERS.NAME'}');\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String[])\n"
          // package name - Java method function
        + "     */\n"
        + "    public void {2}astring()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(new String[]'{'\"queryj\",\"acmsl\"'}');\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}({4}queryj{4},{4}acmsl{4})\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](Field)
     */
    public static final String ANY__FIELD_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.Field)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Field()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME)\");\n" // function
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](CalendarField, IntField) or [Any]FIeld [method](CalendarField, int)
     * or [Any]Field [method](Calendar, IntField) or [Any]Field [method](Calendar, int)
     */
    public static final String ANY__CALENDAR_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.CREATED, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Calendarint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.CREATED, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                t_Now, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},USERS.LICENSE_EXPIRATION)\");\n"
          // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, int)\n"
          // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarint()\n"
          // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field = t_Functions.{0}(t_Now, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},2)\");\n"
          // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](CalendarField, CalendarField) or
     * [Any]FIeld [method](CalendarField, Calendar) or
     * [Any]Field [method](Calendar, CalendarField) or
     * [Any]Field [method](Calendar, Calendar)
     */
    public static final String ANY__CALENDAR_CALENDAR_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // Java method function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + " org.acmsl.queryj.CalendarField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarCalendar()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.CREATED, USERS.UPDATED);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,USERS.UPDATED)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + "java.util.Calendar)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Calendarcalendar()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.CREATED, t_Now);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,{4}\" + t_Now + \"{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, "
        + " org.acmsl.queryj.CalendarField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarCalendar()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                t_Now, USERS.UPDATED);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},USERS.UPDATED)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, java.util.Calendar)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarcalendar()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(t_Now, t_Now);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},{4}\" + t_Now + \"{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](LongField, StringField) or
     * [Any]FIeld [method](LongField, String) or
     * [Any]Field [method](String, StringField) or
     * [Any]Field [method](String, String)
     */
    public static final String ANY__LONG_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.LongField, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}LongString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.MONEY_AMOUNT, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MONEY_AMOUNT,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.LongField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Longstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.MONEY_AMOUNT, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MONEY_AMOUNT,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(long, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}longString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(45L, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(45,USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(long, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}longstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(45L, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(45,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](CalendarField, StringField) or
     * [Any]FIeld [method](CalendarField, String) or
     * [Any]Field [method](Calendar, StringField) or
     * [Any]Field [method](Calendar, String)
     */
    public static final String ANY__CALENDAR_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.CREATED, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Calendarstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.CREATED, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                t_Now, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n"
        + "        Field t_Field = t_Functions.{0}(t_Now, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](DoubleField, DoubleField) or
     * [Ant]Field [method](DoubleField, double) or
     * [Any]Field [method](double, DoubleField) or
     * [Any]Field [method](double, double)
     */
    public static final String ANY__DOUBLE_DOUBLE_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField"
         // package name - class prefix - Java method function
        + ", org.acmsl.queryj.DoubleField)\n"
        + "     */\n"
        + "    public void {2}DoubleDouble()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.MARK, USERS.MARK);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,USERS.MARK)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.DoubleField, double)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Doubledouble()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.MARK, 8.9D);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.MARK,8.9)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, org.acmsl.queryj.DoubleField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}doubleDouble()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(9.8D, USERS.MARK);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(9.8,USERS.MARK)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(double, double)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}doubledouble()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(9.8D, 8.9D);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(9.8,8.9)\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, StringField) or
     * [Any]FIeld [method](StringField, String) or
     * [Any]Field [method](String, StringField) or
     * [Any]Field [method](String, String)
     */
    public static final String ANY__STRING_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, StringField, StringField) or
     * [Any]FIeld [method](StringField, StringField, String) or
     * [Any]Field [method](StringField, String, StringField) or
     * [Any]Field [method](StringField, String, String) or
     * [Any]Field [method](String, StringField, StringField) or
     * [Any]FIeld [method](String, StringField, String) or
     * [Any]Field [method](String, String, StringField) or
     * [Any]Field [method](String, String, String).
     */
    public static final String ANY__STRING_STRING_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME, \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\"acmsl\", USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"acmsl\", \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"gpl\", \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}gpl{4},{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](CalendarField, StringField, StringField) or
     * [Any]FIeld [method](CalendarField, StringField, String) or
     * [Any]Field [method](CalendarField, String, StringField) or
     * [Any]Field [method](CalendarField, String, String) or
     * [Any]Field [method](Calenar, StringField, StringField) or
     * [Any]FIeld [method](Calendar, StringField, String) or
     * [Any]Field [method](Calendar, String, StringField) or
     * [Any]Field [method](Calendar, String, String).
     */
    public static final String ANY__CALENDAR_STRING_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.CREATED, USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.CREATED, USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}CalendarstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.CREATED, \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.CalendarField, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Calendarstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.CREATED, \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.CREATED,{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                t_Now, USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(t_Now, USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                t_Now, \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(java.util.Calendar, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}calendarstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Calendar t_Now = Calendar.getInstance();\n\n"
        + "        Field t_Field = t_Functions.{0}(t_Now, \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}\" + t_Now + \"{4},{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](IntField, StringField, StringField) or
     * [Any]FIeld [method](IntField, StringField, String) or
     * [Any]Field [method](IntField, String, StringField) or
     * [Any]Field [method](IntField, String, String) or
     * [Any]Field [method](int, StringField, StringField) or
     * [Any]FIeld [method](int, StringField, String) or
     * [Any]Field [method](int, String, StringField) or
     * [Any]Field [method](int, String, String).
     */
    public static final String ANY__INT_STRING_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}IntStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.LICENSE_EXPIRATION, USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}IntStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.LICENSE_EXPIRATION, USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}IntstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.LICENSE_EXPIRATION, \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.IntField, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Intstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.LICENSE_EXPIRATION, \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.LICENSE_EXPIRATION,{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intStringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                2, USERS.NAME, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(2,USERS.NAME,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, "
        + "org.acmsl.queryj.StringField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intStringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(2, USERS.NAME, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(2,USERS.NAME,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, String, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intstringString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                2, \"queryj\", USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(2,{4}queryj{4},USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(int, String, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}intstringstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(2, \"acmsl\", \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(2,{4}acmsl{4},{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, IntField, StringField) or
     * [Any]FIeld [method](StringField, IntField, String) or
     * [Any]Field [method](StringField, int, StringField) or
     * [Any]Field [method](StringField, int, String) or
     * [Any]Field [method](String, IntField, StringField) or
     * [Any]FIeld [method](String, Intield, String) or
     * [Any]Field [method](String, int, StringField) or
     * [Any]Field [method](String, int, String).
     */
    public static final String ANY__STRING_INT_STRING_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.IntField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringIntString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, USERS.LICENSE_EXPIRATION, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.LICENSE_EXPIRATION,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + "org.acmsl.queryj.IntField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringIntstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, USERS.LICENSE_EXPIRATION, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.LICENSE_EXPIRATION,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, int, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringintString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, 2, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,2,USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, int, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringintstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME, 2, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,2,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.IntField, org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringIntString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.LICENSE_EXPIRATION, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.LICENSE_EXPIRATION,USERS.NAME)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + "org.acmsl.queryj.IntField, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringIntstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\"acmsl\", USERS.LICENSE_EXPIRATION, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},USERS.LICENSE_EXPIRATION,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, int, "
        + " org.acmsl.queryj.StringField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringintString()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", 2, USERS.NAME);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},2,USERS.NAME)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, int, String)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringintstring()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"acmsl\", 2, \"queryj\");\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},2,{4}queryj{4})\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, IntField) or
     * [Any]FIeld [method](StringField, int) or
     * [Any]Field [method](String, IntField) or
     * [Any]Field [method](String, int)
     */
    public static final String ANY__STRING_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"queryj\", 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},2)\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, StringField, IntField) or
     * [Any]FIeld [method](StringField, StringField, int) or
     * [Any]Field [method](StringField, String, IntField) or
     * [Any]Field [method](StringField, String, int) or
     * [Any]Field [method](String, StringField, IntField) or
     * [Any]FIeld [method](String, StringField, int) or
     * [Any]Field [method](String, String, IntField) or
     * [Any]Field [method](String, String, int).
     */
    public static final String ANY__STRING_STRING_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringStringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, USERS.NAME, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + "org.acmsl.queryj.StringField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringStringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, USERS.NAME, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.NAME,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, String, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringstringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, \"queryj\", USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,{4}queryj{4},USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, String, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringstringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME, \"queryj\", 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,{4}queryj{4},2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.StringField, org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringStringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.NAME, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.NAME,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + "org.acmsl.queryj.StringField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringStringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\"queryj\", USERS.NAME, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.NAME,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, String, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringstringInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"acmsl\", \"queryj\", USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},{4}queryj{4},USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, String, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringstringint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"acmsl\", \"queryj\", 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},{4}queryj{4},2)\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches
     * [Any]Field [method](StringField, IntField, IntField) or
     * [Any]FIeld [method](StringField, IntField, int) or
     * [Any]Field [method](StringField, int, IntField) or
     * [Any]Field [method](StringField, int, int) or
     * [Any]Field [method](String, IntField, IntField) or
     * [Any]FIeld [method](String, IntField, int) or
     * [Any]Field [method](String, int, IntField) or
     * [Any]Field [method](String, int, int).
     */
    public static final String ANY__STRING_INT_INT_FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + " org.acmsl.queryj.IntField, org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringIntInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME,\n"
        + "                USERS.LICENSE_EXPIRATION,\n"
        + "                USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(\n"
        + "            t_Field.toString(),\n"
        + "              \"{3}(USERS.NAME,\"\n"
        + "            + \"USERS.LICENSE_EXPIRATION,\"\n"
        + "            + \"USERS.LICENSE_EXPIRATION)\");\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, "
        + "org.acmsl.queryj.IntField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringIntint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(USERS.NAME, USERS.LICENSE_EXPIRATION, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,USERS.LICENSE_EXPIRATION,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, int, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}StringintInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                USERS.NAME, 2, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,2,USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(org.acmsl.queryj.StringField, int, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}Stringintint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(USERS.NAME, 2, 3);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}(USERS.NAME,2,3)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" //
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + " org.acmsl.queryj.IntField, org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringIntInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"queryj\", USERS.LICENSE_EXPIRATION, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.LICENSE_EXPIRATION,USERS.LICENSE_EXPIRATION)\");\n" // function
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n" // function
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, "
        + "org.acmsl.queryj.IntField, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringIntint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\"queryj\", USERS.LICENSE_EXPIRATION, 2);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},USERS.LICENSE_EXPIRATION,2)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, int, "
        + " org.acmsl.queryj.IntField)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringintInt()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field =\n"
        + "            t_Functions.{0}(\n"
        + "                \"acmsl\", 2, USERS.LICENSE_EXPIRATION);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}acmsl{4},2,USERS.LICENSE_EXPIRATION)\");\n"
         // function - separator - separator
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}(String, int, int)\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}stringintint()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}(\"queryj\", 2, 3);\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), "
        + "\"{3}({4}queryj{4},2,3)\");\n"
         // function - separator - separator
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches [Any]Field [method]().
     */
    public static final String ANY__FUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}()\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}void()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}();\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}()\");\n" // function
        + "    '}'\n\n";

    /**
     * Template for testing methods whose signature matches [Any]Field [method](),
     * and represent database variables instead of functions.
     */
    public static final String ANY__NOFUNCTION_TEST =
          "    /**\n"
        + "     * Tests {5}Functions''s {0}() function.\n"
         // class prefix - Java method function
        + "     * @see {1}.{5}Functions#{0}()\n"
         // package name - class prefix - Java method function
        + "     */\n"
        + "    public void {2}()\n"
         // Java method test function
        + "    '{'\n"
        + "        {5}Functions t_Functions = getTestedInstance();\n"
         // class prefix
        + "        assertNotNull(t_Functions);\n\n"
        + "        Field t_Field = t_Functions.{0}();\n"
        + "        assertNotNull(t_Field);\n"
        + "        assertEquals(t_Field.toString(), \"{3}\");\n" // function
        + "    '}'\n\n";

    /**
     * The class constructor.
     */
    public static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Constructs a test case with given name.\n"
        + "     * @param name the test case name.\n"
        + "     */\n"
        + "    public {0}FunctionsTest(String name)\n"
         // class prefix
        + "    '{'\n"
        + "        super(name);\n"
        + "    '}'\n\n";

    /**
     * The attributes' accessors.
     */
    public static final String MEMBER_ACCESSORS =
          "    /**\n"
        + "     * Specifies the tested instance.\n"
        + "     * @param instance the instance to test.\n"
        + "     */\n"
        + "    protected void setTestedInstance({0}Functions instance)\n"
         // class prefix
        + "    '{'\n"
        + "        m__TestedInstance = instance;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the tested instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    protected {0}Functions getTestedInstance()\n"
         // class prefix
        + "    '{'\n"
        + "        return m__TestedInstance;\n"
        + "    '}'\n\n";

    /**
     * The setup-teardown methods.
     */
    public static final String SETUP_TEARDOWN_METHODS =
          "    /**\n"
        + "     * Sets up the test fixture (called before every test method).\n"
        + "     */\n"
        + "    protected void setUp()\n"
        + "    '{'\n"
        + "        setTestedInstance({0}Functions.getInstance());\n"
         // class prefix
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Tears fown the test fixture (Called after every test method).\n"
        + "     */\n"
        + "    protected void tearDown()\n"
        + "    '{'\n"
        + "        setTestedInstance(null);\n"
        + "    '}'\n\n";

    /**
     *  The main method for the concrete test case.
     */
    public static final String MAIN_METHOD =
          "    /**\n"
        + "     * Executes this concrete test case from the command line.\n"
        + "     * @param args the command-line arguments. [Not used]\n"
        + "     */\n"
        + "    public static void main(String args[])\n"
        + "    '{'\n"
        + "        junit.textui.TestRunner.run({0}FunctionsTest.class);\n"
        + "    '}'\n\n";

    /**
     *  The getInstance method for the concrete test case.
     */
    public static final String GET_INSTANCE_TEST =
          "    /**\n"
        + "     * Tests {1}Functions''s getInstance() function.\n"
         // class prefix
        + "     * @see {0}.{1}Functions#getInstance()\n"
         // package name - class prefix
        + "     */\n"
        + "    public void testGetInstance()\n"
        + "    '{'\n"
        + "        assertNotNull(getTestedInstance());\n\n"
        + "    '}'\n\n";

    /**
     * The test table.
     */
    public static final String INNER_TABLE =
          "    /**\n"
        + "     * A test table.\n"
        + "     * @author <a href=\"maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
        + "     */\n"
        + "    public static final class UsersTable\n"
        + "        extends  Table\n"
        + "    {\n"
        + "        /**\n"
        + "         * The USERS table CREATED field.\n"
        + "         */\n"
        + "        public CalendarField CREATED =\n"
        + "            new CalendarField(\"CREATED\", this) {};\n\n"
        + "        /**\n"
        + "         * The USERS table LICENSE_EXPIRATION field.\n"
        + "         */\n"
        + "        public IntField LICENSE_EXPIRATION =\n"
        + "            new IntField(\"LICENSE_EXPIRATION\", this) {};\n\n"
        + "        /**\n"
        + "         * The USERS table UPDATED field.\n"
        + "         */\n"
        + "        public CalendarField UPDATED =\n"
        + "            new CalendarField(\"UPDATED\", this) {};\n\n"
        + "        /**\n"
        + "         * The USERS table MONEY_AMOUNT field.\n"
        + "         */\n"
        + "        public LongField MONEY_AMOUNT =\n"
        + "            new LongField(\"MONEY_AMOUNT\", this) {};\n\n"
        + "        /**\n"
        + "         * The USERS table NAME field.\n"
        + "         */\n"
        + "        public StringField NAME =\n"
        + "            new StringField(\"NAME\", this) {};\n\n"
        + "        /**\n"
        + "         * The USERS table MARK field.\n"
        + "         */\n"
        + "        public DoubleField MARK =\n"
        + "            new DoubleField(\"MARK\", this) {};\n\n"
        + "        /**\n"
        + "         * Creates a USERS table with given alias.\n"
        + "         * @param alias the table alias.\n"
        + "         */\n"
        + "        protected UsersTable(String alias)\n"
        + "        {\n"
        + "            super(\"USERS\", alias);\n"
        + "        }\n\n"
        + "        /**\n"
        + "         * Creates a USERS table.\n"
        + "         */\n"
        + "        protected UsersTable()\n"
        + "        {\n"
        + "            this(null);\n"
        + "        }\n\n"
        + "        /**\n"
        + "         * Retrieves <code>all</code> fields. It's equivalent to \n"
        + "         * a star in a query.\n"
        + "         * @return such fields.\n"
        + "         */\n"
        + "        public Field[] getAll()\n"
        + "        {\n"
        + "            return\n"
        + "                new Field[]\n"
        + "                {\n"
        + "                    CREATED,\n"
        + "                    LICENSE_EXPIRATION,\n"
        + "                    UPDATED,\n"
        + "                    MONEY_AMOUNT,\n"
        + "                    MARK\n"
        + "                };\n"
        + "        }\n"
        + "    }\n";

    /**
     * The tested package name.
     */
    private String m__strTestedPackageName;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJUnitImports;

    /**
     * The test function method.
     */
    private String m__strTestFunctionMethod;

    /**
     * The member accessors.
     */
    private String m__strMemberAccessors;

    /**
     * The setUp-tearDown methods.
     */
    private String m__strSetUpTearDownMethods;

    /**
     * The main method.
     */
    private String m__strMainMethod;

    /**
     * The getInstance test.
     */
    private String m__strGetInstanceTest;

    /**
     * The inner table.
     */
    private String m__strInnerTable;

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param projectImports the JDK imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     */
    public FunctionsTestTemplate(
        String classDescription,
        String classPrefix,
        String header,
        String packageDeclaration,
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote,
        String projectImports,
        String acmslImports,
        String jdkImports,
        String junitImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String classConstructor,
        String memberAccessors,
        String setUpTearDownMethods,
        String mainMethod,
        String getInstanceTest,
        String innerClass,
        String innerTable,
        String classEnd)
    {
        super(
            classDescription,
            classPrefix,
            header,
            packageDeclaration,
            packageName,
            engineName,
            engineVersion,
            quote,
            acmslImports,
            jdkImports,
            javadoc,
            classDefinition,
            classStart,
            null, // singleton body
            classConstructor,
            innerClass,
            classEnd);

        inmutableSetTestedPackageName(testedPackageName);
        inmutableSetProjectImports(projectImports);
        inmutableSetJUnitImports(junitImports);
        inmutableSetMemberAccessors(memberAccessors);
        inmutableSetSetUpTearDownMethods(setUpTearDownMethods);
        inmutableSetMainMethod(mainMethod);
        inmutableSetGetInstanceTest(getInstanceTest);
        inmutableSetInnerTable(innerTable);
    }

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param projectImports the JDK imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public FunctionsTestTemplate(
        String  classDescription,
        String  classPrefix,
        String  header,
        String  packageDeclaration,
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        String  projectImports,
        String  acmslImports,
        String  jdkImports,
        String  junitImports,
        String  javadoc,
        String  classDefinition,
        String  classStart,
        String  classConstructor,
        String  memberAccessors,
        String  setUpTearDownMethods,
        String  mainMethod,
        String  getInstanceTest,
        String  innerClass,
        String  innerTable,
        String  classEnd,
        Project project,
        Task    task)
    {
        super(
            classDescription,
            classPrefix,
            header,
            packageDeclaration,
            packageName,
            engineName,
            engineVersion,
            quote,
            acmslImports,
            jdkImports,
            javadoc,
            classDefinition,
            classStart,
            null, // singleton body
            classConstructor,
            innerClass,
            classEnd,
            project,
            task);

        inmutableSetTestedPackageName(testedPackageName);
        inmutableSetProjectImports(projectImports);
        inmutableSetJUnitImports(junitImports);
        inmutableSetMemberAccessors(memberAccessors);
        inmutableSetSetUpTearDownMethods(setUpTearDownMethods);
        inmutableSetMainMethod(mainMethod);
        inmutableSetGetInstanceTest(getInstanceTest);
        inmutableSetInnerTable(innerTable);
    }

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public FunctionsTestTemplate(
        String classDescription,
        String classPrefix,
        String packageName,
        String testedPackageName,
        String engineName,
        String engineVersion,
        String quote)
    {
        this(
            classDescription,
            classPrefix,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            MEMBER_ACCESSORS,
            SETUP_TEARDOWN_METHODS,
            MAIN_METHOD,
            GET_INSTANCE_TEST,
            INNER_TABLE,
            INNER_CLASS,
            DEFAULT_CLASS_END);
    }

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public FunctionsTestTemplate(
        String  classDescription,
        String  classPrefix,
        String  packageName,
        String  testedPackageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
    {
        this(
            classDescription,
            classPrefix,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            MEMBER_ACCESSORS,
            SETUP_TEARDOWN_METHODS,
            MAIN_METHOD,
            GET_INSTANCE_TEST,
            INNER_TABLE,
            INNER_CLASS,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Specifies the tested package name.
     * @param packageName the tested package name.
     */
    private void inmutableSetTestedPackageName(String packageName)
    {
        m__strTestedPackageName = packageName;
    }

    /**
     * Specifies the tested package name.
     * @param packageName the tested package name.
     */
    protected void setTestedPackageName(String packageName)
    {
        inmutableSetTestedPackageName(packageName);
    }

    /**
     * Retrieves the tested package name.
     * @return such package name.
     */
    public String getTestedPackageName()
    {
        return m__strTestedPackageName;
    }

    /**
     * Specifies the project imports.
     * @param imports the new imports.
     */
    private void inmutableSetProjectImports(String imports)
    {
        m__strProjectImports = imports;
    }

    /**
     * Specifies the project imports.
     * @param imports the new imports.
     */
    protected void setProjectImports(String imports)
    {
        inmutableSetProjectImports(imports);
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
     * Specifies the JUnit imports.
     * @param imports the new imports.
     */
    private void inmutableSetJUnitImports(String imports)
    {
        m__strJUnitImports = imports;
    }

    /**
     * Specifies the JUnit imports.
     * @param imports the new imports.
     */
    protected void setJUnitImports(String imports)
    {
        inmutableSetJUnitImports(imports);
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
     * Specifies the member accessors.
     * @param memberAccessors the member accessors.
     */
    private void inmutableSetMemberAccessors(String memberAccessors)
    {
        m__strMemberAccessors = memberAccessors;
    }

    /**
     * Specifies the member accessors.
     * @param memberAccessors the member accessors.
     */
    protected void setMemberAccessors(String memberAccessors)
    {
        inmutableSetMemberAccessors(memberAccessors);
    }

    /**
     * Retrieves the member accessors.
     * @return such information.
     */
    public String getMemberAccessors()
    {
        return m__strMemberAccessors;
    }

    /**
     * Specifies the setUp-TearDown methods.
     * @param setUpTearDownMethods the setUp-TearDown methods.
     */
    private void inmutableSetSetUpTearDownMethods(String setUpTearDownMethods)
    {
        m__strSetUpTearDownMethods = setUpTearDownMethods;
    }

    /**
     * Specifies the setUp-TearDown methods.
     * @param setUpTearDownMethods the setUp-TearDown methods.
     */
    protected void setSetUpTearDownMethods(String setUpTearDownMethods)
    {
        inmutableSetSetUpTearDownMethods(setUpTearDownMethods);
    }

    /**
     * Retrieves the setUp-TearDown methods.
     * @return such information.
     */
    public String getSetUpTearDownMethods()
    {
        return m__strSetUpTearDownMethods;
    }

    /**
     * Specifies the main method.
     * @param mainMethod the main method.
     */
    private void inmutableSetMainMethod(String mainMethod)
    {
        m__strMainMethod = mainMethod;
    }

    /**
     * Specifies the main method.
     * @param mainMethod the main method.
     */
    protected void setMainMethod(String mainMethod)
    {
        inmutableSetMainMethod(mainMethod);
    }

    /**
     * Retrieves the main method.
     * @return such information.
     */
    public String getMainMethod()
    {
        return m__strMainMethod;
    }

    /**
     * Specifies the getInstance test.
     * @param getInstanceTest the getInstance test.
     */
    private void inmutableSetGetInstanceTest(String getInstanceTest)
    {
        m__strGetInstanceTest = getInstanceTest;
    }

    /**
     * Specifies the getInstance test.
     * @param getInstanceTest the getInstance test.
     */
    protected void setGetInstanceTest(String getInstanceTest)
    {
        inmutableSetGetInstanceTest(getInstanceTest);
    }

    /**
     * Retrieves the getInstance test.
     * @return such information.
     */
    public String getGetInstanceTest()
    {
        return m__strGetInstanceTest;
    }

    /**
     * Specifies the inner table.
     * @param innerTable the inner table.
     */
    private void inmutableSetInnerTable(String innerTable)
    {
        m__strInnerTable = innerTable;
    }

    /**
     * Specifies the inner table.
     * @param innerTable the inner table.
     */
    protected void setInnerTable(String innerTable)
    {
        inmutableSetInnerTable(innerTable);
    }

    /**
     * Retrieves the inner table.
     * @return such information.
     */
    public String getInnerTable()
    {
        return m__strInnerTable;
    }

    /**
     * Indicates if the test should be considered a suite or not.
     * @return such information.
     */
    public boolean isSuite()
    {
        return false;
    }

    /**
     * Builds the mappings.
     * @param mappings the initial mapping collection.
     * @return the updated collection.
     */
    protected abstract Map fillUpMappings(Map mappings);

    /**
     * Creates the special function mappings.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected abstract Map fillUpSpecialMappings(Map mappings);

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappingsReturnTypes(Map mappings)
    {
        return mappings;
    }

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @return the field type.
     */
    protected String getSpecialMappingReturnType(String function)
    {
        // Not used for unit test generation, since the comparision is done
        // via unspecific assertEquals on Field class, not derived ones.
        return null;
    }

    /**
     * Retrieves the source code of the generated numeric functions test.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            Object t_aEngine =
                new Object[]
                {
                    getEngineName(),
                    getEngineVersion(),
                    getClassDescription()
                };

            Object[] t_aPackageName = new Object[]{getPackageName()};

            Object[] t_aClassPrefix = new Object[]{getClassPrefix()};

            MessageFormat t_Formatter = null;

            String t_strHeader = getHeader();

            if  (t_strHeader != null) 
            {
                t_Formatter = new MessageFormat(t_strHeader);
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strPackageDeclaration = getPackageDeclaration();

            if  (t_strPackageDeclaration != null) 
            {
                t_Formatter = new MessageFormat(t_strPackageDeclaration);
                t_sbResult.append(t_Formatter.format(t_aPackageName));
            }

            String t_strProjectImports = getProjectImports();
            String t_strTestedPackageName = getTestedPackageName();

            if  (   (t_strProjectImports    != null)
                 && (t_strTestedPackageName != null))
            {
                t_Formatter = new MessageFormat(t_strProjectImports);
                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strTestedPackageName,
                            getClassPrefix()
                        }));
            }
            
            String t_strAcmslImports = getAcmslImports();

            if  (t_strAcmslImports != null) 
            {
                t_sbResult.append(t_strAcmslImports);
            }

            String t_strJdkImports = getJdkImports();

            if  (t_strJdkImports != null) 
            {
                t_sbResult.append(t_strJdkImports);
            }

            String t_strJUnitImports = getJUnitImports();

            if  (t_strJUnitImports != null) 
            {
                t_sbResult.append(t_strJUnitImports);
            }

            String t_strJavadoc = getJavadoc();

            if  (t_strJavadoc != null) 
            {
                t_Formatter = new MessageFormat(t_strJavadoc);
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strClassDefinition = getClassDefinition();

            if  (t_strClassDefinition != null) 
            {
                t_Formatter = new MessageFormat(t_strClassDefinition);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strClassStart = getClassStart();

            if  (t_strClassStart != null) 
            {
                t_Formatter = new MessageFormat(t_strClassStart);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strClassConstructor = getClassConstructor();

            if  (t_strClassConstructor != null) 
            {
                t_Formatter = new MessageFormat(t_strClassConstructor);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strMemberAccessors = getMemberAccessors();

            if  (t_strMemberAccessors != null) 
            {
                t_Formatter = new MessageFormat(t_strMemberAccessors);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strSetUpTearDownMethods = getSetUpTearDownMethods();

            if  (t_strSetUpTearDownMethods != null) 
            {
                t_Formatter = new MessageFormat(t_strSetUpTearDownMethods);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strMainMethod = getMainMethod();

            if  (t_strMainMethod != null) 
            {
                t_Formatter = new MessageFormat(t_strMainMethod);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strGetInstanceTest = getGetInstanceTest();

            if  (t_strGetInstanceTest != null) 
            {
                t_Formatter = new MessageFormat(t_strGetInstanceTest);
                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strTestedPackageName,
                            getClassPrefix()
                        }));
            }

            String t_strDefaultFunctionMethodTest = getDefaultFunctionMethod();

            if  (t_strDefaultFunctionMethodTest != null) 
            {
                List t_lFunctions = getFunctions();

                if  (t_lFunctions != null)
                {
                    Iterator t_itFunctions = t_lFunctions.iterator();

                    MessageFormat t_TestMethodFormatter =
                        new MessageFormat(t_strDefaultFunctionMethodTest);

                    while  (t_itFunctions.hasNext()) 
                    {
                        String t_strFunction = (String) t_itFunctions.next();

                        String t_strMapping = getMapping(t_strFunction);

                        if  (t_strMapping == null) 
                        {
                            t_strMapping = getSpecialMapping(t_strFunction);

                            if  (t_strMapping == null) 
                            {
                                if  (generateWarning(t_strMapping)) 
                                {
                                    logWarn(
                                        "No mapping for " + t_strFunction);
                                }

                                continue;
                            }
                            else 
                            {
                                t_TestMethodFormatter =
                                    new MessageFormat(t_strMapping);
                            }
                        }

                        String t_strJavaMethod =
                            t_StringUtils.toJavaMethod(
                                t_strFunction.toLowerCase(),
                                '_',
                                getCapitalizedWords());

                        String t_strTestJavaMethod =
                            "test" + t_StringUtils.capitalize(t_strJavaMethod, '_');

                        t_sbResult.append(
                            t_TestMethodFormatter.format(
                                new Object[]
                                {
                                    t_strJavaMethod,
                                    getTestedPackageName(),
                                    t_strTestJavaMethod,
                                    t_strFunction,
                                    getQuote(),
                                    getClassPrefix()
                                }));
                    }
                }
            }

            String t_strInnerTable = getInnerTable();

            if  (t_strInnerTable != null) 
            {
                t_sbResult.append(t_strInnerTable);
            }

            t_sbResult.append(innerClassesToString());

            String t_strClassEnd = getClassEnd();

            if  (t_strClassEnd != null) 
            {
                t_sbResult.append(t_strClassEnd);
            }
        }

        return t_sbResult.toString();
    }
}
