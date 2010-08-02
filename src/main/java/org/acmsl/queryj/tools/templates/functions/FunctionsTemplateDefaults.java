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
 * Description: Defines the basic pieces to build function templates.
 */
package org.acmsl.queryj.tools.templates.functions;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the basic pieces to build function templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface FunctionsTemplateDefaults
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
        + " * Description: Contains the {2} functions supported by "
         // function description
        + "{0} {1}.\n"
         // engine name - driver version
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION =
        "package {0};\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.BigDecimalField;\n"
        + "import org.acmsl.queryj.CalendarField;\n"
        + "import org.acmsl.queryj.DoubleField;\n"
        + "import org.acmsl.queryj.Field;\n"
        + "import org.acmsl.queryj.IntField;\n"
        + "import org.acmsl.queryj.LongField;\n"
        + "import org.acmsl.queryj.QueryUtils;\n"
        + "import org.acmsl.queryj.StringField;\n"
        + "import org.acmsl.queryj.Table;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Contains the {2} functions supported by {0} {1}.\n"
       // function description - engine name - driver version
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
        "public abstract class {0}Functions\n"; // class name prefix

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n";

    /**
     * The class singleton logic.
     */
    public static final String SINGLETON_BODY =
          "    /**\n"
        + "     * Singleton implemented to avoid the double-checked locking.\n"
        + "     */\n"
        + "    private static class {0}FunctionsSingletonContainer\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * The actual singleton.\n"
        + "         */\n"
        + "        public static final {0}Function SINGLETON =\n"
        + "            new {0}Functions();\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a <code>{0}Functions</code> instance.\n"
         // class name prefix
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Functions getInstance()\n"
        + "    '{'\n"
        + "        return {0}FunctionsSingletonContainer.SINGLETON;\n\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField) or
     * [Any]Field [method](Calendar).
     */
    public static final String ANY__CALENDAR_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @return the wrapping field.\n"
        + "     */\n"
        + "    public {2}Field {3}(CalendarField field)\n" // field type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // field type - function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the calendar value.\n"
        + "     * @return the wrapping field.\n"
        + "     */\n"
        + "    public {2}Field {3}(Calendar value)\n"
         // field type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (value != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n" // field type
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField, IntField) or
     * [Any]Field [method](CalendarField, int) or
     * [Any]Field [method](Calendar, IntField) or
     * [Any]Field [method](Calendar, int).
     */
    public static final String ANY__CALENDAR_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(CalendarField field, IntField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param int the argument value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(CalendarField field, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value)'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the calendar value.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(Calendar value, IntField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value != null)\n"
        + "             && (field != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param calendar the calendar value.\n"
        + "     * @param value the argument value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(Calendar calendar, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (calendar != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'calendar, new Integer(value)'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField, CalendarField) or
     * [Any]Field [method](CalendarField, Calendar) or
     * [Any]Field [method](Calendar, CalendarField) or
     * [Any]Field [method](Calendar, Calendar).
     */
    public static final String ANY__CALENDAR_CALENDAR_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public CalendarField {3}(CalendarField field, CalendarField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        CalendarField result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _CalendarFieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param date the date argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public CalendarField {3}(CalendarField field, Calendar date)\n"
         // capitalized function
        + "    '{'\n"
        + "        CalendarField result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _CalendarFieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'date'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param calendar the calendar value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public CalendarField {3}(Calendar calendar, CalendarField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        CalendarField result = null;\n\n"
        + "        if  (   (calendar != null)\n"
        + "             && (field    != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _CalendarFieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'calendar, field'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param calendar the calendar value.\n"
        + "     * @param date the date argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public CalendarField {3}(Calendar calendar, Calendar date)\n"
         // capitalized function
        + "    '{'\n"
        + "        CalendarField result = null;\n\n"
        + "        if  (   (calendar != null)\n"
        + "             && (date     != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _CalendarFieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'calendar, date'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](LongField) or
     * [Any]Field [method](long).
     */
    public static final String ANY__LONG_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the long field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(LongField field)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the long value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(long value)\n" // capitalized function
        + "    '{'\n"
        + "        return new _{2}FieldWrapper(value, \"{4}\");\n" // function
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](LongField, StringField) or
     * [Any]Field [method](LongField, String) or
     * [Any]Field [method](long, StringField) or
     * [Any]Field [method](long, String).
     */
    public static final String ANY__LONG_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the long field.\n"
        + "     * @param otherField the String field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(LongField field, StringField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field      != null)\n"
        + "             && (otherField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the long field.\n"
        + "     * @param otherField the String field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(LongField field, String otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the long value.\n"
        + "     * @param field the String field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(long value, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Long(value), field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the long value.\n"
        + "     * @param text the String value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(long value, String text)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (text != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Long(value), text'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](IntField) or
     * [Any]Field [method](int).
     */
    public static final String ANY__INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the int field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(IntField field)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(int value)\n" // capitalized function
        + "    '{'\n"
        + "        return new _{2}FieldWrapper(value, \"{4}\");\n" // function
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField) or
     * [Any]Field [method](String).
     */
    public static final String ANY__STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField field)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String value)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n"
        + "        if  (value != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(value, \"{4}\");\n" // function
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField[]) or
     * [Any]Field [method](String[]).
     */
    public static final String ANY__ASTRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param fields the text fields.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField[] fields)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (fields != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(null, \"{4}\", (Object[]) fields);\n" // function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param values the text values.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String[] values)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n"
        + "        if  (values != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(null, \"{4}\", (Object[]) values);\n" // function
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](Field).
     */
    public static final String ANY__FIELD_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(Field field)\n" // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](IntField, IntField) or
     * [Any]Field [method](IntField, int) or
     * [Any]Field [method](int, IntField) or
     * [Any]Field [method](int, int).
     */
    public static final String ANY__INT_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstField the first int field.\n"
        + "     * @param secondField the second int field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(IntField firstField, IntField secondField)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (   (firstField  != null)\n"
        + "             && (secondField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
         // return type
        + "                    firstField,\n"
        + "                    \"{4}\",\n"
         // quote
        + "                    new Object[]'{'secondField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the int field.\n"
        + "     * @param value the int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(IntField field, int value)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field, \"{4}\", new Object[]'{'new Integer(value)'}');\n"
         // return type - quote
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the int value.\n"
        + "     * @param field the int field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(int value, IntField field)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null, \"{4}\", new Object[]'{'new Integer(value), field'}');\n"
         // return type - quote
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstValue the first int value.\n"
        + "     * @param secondValue the second int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(int firstValue, int secondValue)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n"
        + "                null,\n"
        + "                \"{4}\",\n"
        + "                new Object[]\n"
        + "                '{'\n"
        + "                    new Integer(firstValue),\n"
        + "                    new Integer(secondValue)\n"
        + "                '}');\n"
         // return type - quote
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](LongField, LongField) or
     * [Any]Field [method](LongField, long) or
     * [Any]Field [method](long, LongField) or
     * [Any]Field [method](long, long).
     */
    public static final String ANY__LONG_LONG_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstField the first long field.\n"
        + "     * @param secondField the second long field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(LongField firstField, LongField secondField)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (   (firstField  != null)\n"
        + "             && (secondField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
         // return type
        + "                    firstField,\n"
        + "                    \"{4}\",\n"
         // quote
        + "                    new Object[]'{'secondField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the long field.\n"
        + "     * @param value the long value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(LongField field, long value)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field, \"{4}\", new Object[]'{'new Long(value)'}');\n"
         // return type - quote
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the long value.\n"
        + "     * @param field the long field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(long value, LongField field)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
         // return type
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null, \"{4}\", new Object[]'{'new Long(value), field'}');\n"
         // return type - quote
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstValue the first long value.\n"
        + "     * @param secondValue the second long value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(long firstValue, long secondValue)\n"
         // return type - capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n"
        + "                null,\n"
        + "                \"{4}\",\n"
        + "                new Object[]\n"
        + "                '{'\n"
        + "                    new Long(firstValue),\n"
        + "                    new Long(secondValue)\n"
        + "                '}');\n"
         // return type - quote
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField, StringField) or
     * [Any]Field [method](CalendarField, String) or
     * [Any]Field [method](Calendar, StringField) or
     * [Any]Field [method](Calendar, String).
     */
    public static final String ANY__CALENDAR_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(CalendarField field, StringField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the calendar field.\n"
        + "     * @param value the argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(CalendarField field, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param calendar the calendar value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(Calendar calendar, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (calendar != null)\n"
        + "             && (field    != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'calendar, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param calendar the calendar value.\n"
        + "     * @param value the String argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(Calendar calendar, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (calendar != null)\n"
        + "             && (value    != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'calendar, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField, StringField) or
     * [Any]Field [method](StringField, String) or
     * [Any]Field [method](String, StringField) or
     * [Any]Field [method](String, String).
     */
    public static final String ANY__STRING_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField field, StringField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @param value the argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField field, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String value, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value != null)\n"
        + "             && (field    != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param text the text value.\n"
        + "     * @param value the String argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String text, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (text  != null)\n"
        + "             && (value != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'text, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField, IntField) or
     * [Any]Field [method](StringField, int) or
     * [Any]Field [method](String, IntField) or
     * [Any]Field [method](String, int).
     */
    public static final String ANY__STRING_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField field, IntField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the text field.\n"
        + "     * @param value the argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(StringField field, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String value, IntField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value != null)\n"
        + "             && (field != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param text the text value.\n"
        + "     * @param value the int argument.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(String text, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (text  != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'text, new Integer(value)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField, StringField, StringField) or
     * [Any]Field [method](CalendarField, StringField, String) or
     * [Any]Field [method](CalendarField, String, StringField) or
     * [Any]Field [method](CalendarField, String, String) or
     * [Any]Field [method](Calendar, StringField, StringField) or
     * [Any]Field [method](Calendar, StringField, String) or
     * [Any]Field [method](Calendar, String, StringField) or
     * [Any]Field [method](Calendar, String, String).
     */
    public static final String ANY__CALENDAR_STRING_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        CalendarField field1, StringField field2, StringField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the text value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        CalendarField field1, StringField field2, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (value  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the text value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        CalendarField field1, String value, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (value  != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        CalendarField field, String value1, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value1 != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the calendar value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        Calendar value, StringField field1, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value  != null)\n"
        + "             && (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        Calendar value1, StringField field, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, field, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        Calendar value1, String value2, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (value2 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        Calendar value1, String value2, String value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1  != null)\n"
        + "             && (value2 != null)\n"
        + "             && (value3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, value3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField, StringField, StringField) or
     * [Any]Field [method](StringField, StringField, String) or
     * [Any]Field [method](StringField, String, StringField) or
     * [Any]Field [method](StringField, String, String) or
     * [Any]Field [method](String, StringField, StringField) or
     * [Any]Field [method](String, StringField, String) or
     * [Any]Field [method](String, String, StringField) or
     * [Any]Field [method](String, String, String).
     */
    public static final String ANY__STRING_STRING_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, StringField field2, StringField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the text value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, StringField field2, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (value  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the text value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, String value, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (value  != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field, String value1, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value1 != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value, StringField field1, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value  != null)\n"
        + "             && (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, StringField field, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, field, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, String value2, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (value2 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, String value2, String value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1  != null)\n"
        + "             && (value2 != null)\n"
        + "             && (value3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, value3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](IntField, StringField, StringField) or
     * [Any]Field [method](IntField, StringField, String) or
     * [Any]Field [method](IntField, String, StringField) or
     * [Any]Field [method](IntField, String, String) or
     * [Any]Field [method](int, StringField, StringField) or
     * [Any]Field [method](int, StringField, String) or
     * [Any]Field [method](int, String, StringField) or
     * [Any]Field [method](int, String, String).
     */
    public static final String ANY__INT_STRING_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        IntField field1, StringField field2, StringField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the text value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        IntField field1, StringField field2, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (value  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the text value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        IntField field1, String value, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (value  != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        IntField field, String value1, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value1 != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the int value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        int value, StringField field1, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value), field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        int value1, StringField field, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value1), field, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        int value1, String value2, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value2 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value1), value2, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        int value1, String value2, String value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value2 != null)\n"
        + "             && (value3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value1), value2, value3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField, IntField, StringField) or
     * [Any]Field [method](StringField, IntField, String) or
     * [Any]Field [method](StringField, int, StringField) or
     * [Any]Field [method](StringField, int, String) or
     * [Any]Field [method](String, IntField, StringField) or
     * [Any]Field [method](String, IntField, String) or
     * [Any]Field [method](String, int, StringField) or
     * [Any]Field [method](String, int, String).
     */
    public static final String ANY__STRING_INT_STRING_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, IntField field2, StringField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the text value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, IntField field2, String value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (value  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, value'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the int value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, int value, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value), field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field, int value1, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value1), value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value, IntField field1, StringField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value  != null)\n"
        + "             && (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, IntField field, String value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, field, value2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, int value2, StringField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, new Integer(value2), field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, int value2, String value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (value3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, new Integer(value2), value3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](StringField, StringField, IntField) or
     * [Any]Field [method](StringField, StringField, int) or
     * [Any]Field [method](StringField, String, IntField) or
     * [Any]Field [method](StringField, String, int) or
     * [Any]Field [method](String, StringField, IntField) or
     * [Any]Field [method](String, StringField, int) or
     * [Any]Field [method](String, String, IntField) or
     * [Any]Field [method](String, String, int).
     */
    public static final String ANY__STRING_STRING_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, StringField field2, IntField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, StringField field2, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, new Integer(value)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the text value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, String value, IntField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (value  != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field, String value1, int value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field  != null)\n"
        + "             && (value1 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, new Integer(value2)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value, StringField field1, IntField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value  != null)\n"
        + "             && (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, StringField field, int value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, field, new Integer(value2)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, String value2, IntField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (value2 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, String value2, int value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1  != null)\n"
        + "             && (value2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, value2, new Integer(value3)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";


    /**
     * A method whose signature is
     * [Any]Field [method](StringField, IntField, IntField) or
     * [Any]Field [method](StringField, IntField, int) or
     * [Any]Field [method](StringField, int, IntField) or
     * [Any]Field [method](StringField, int, int) or
     * [Any]Field [method](String, IntField, IntField) or
     * [Any]Field [method](String, IntField, int) or
     * [Any]Field [method](String, int, IntField) or
     * [Any]Field [method](String, int, int).
     */
    public static final String ANY__STRING_INT_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param field3 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, IntField field2, IntField field3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null)\n"
        + "             && (field3 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, field3'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param field2 the second field.\n"
        + "     * @param value the int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, IntField field2, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'field2, new Integer(value)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field1 the first field.\n"
        + "     * @param value the int value.\n"
        + "     * @param field2 the second field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field1, int value, IntField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field1,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value), field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        StringField field, int value1, int value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field  != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value1), new Integer(value2)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the text value.\n"
        + "     * @param field1 the second field.\n"
        + "     * @param field2 the third field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value, IntField field1, IntField field2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value  != null)\n"
        + "             && (field1 != null)\n"
        + "             && (field2 != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value, field1, field2'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param field the field.\n"
        + "     * @param value2 the second value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, IntField field, int value2)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, field, new Integer(value2)'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param field the field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, int value2, IntField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (value1 != null)\n"
        + "             && (field  != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'value1, new Integer(value2), field'}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value1 the first value.\n"
        + "     * @param value2 the second value.\n"
        + "     * @param value3 the third value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(\n"
        + "        String value1, int value2, int value3)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (value1 != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]\n"
        + "                    '{'\n"
        + "                         value1,\n"
        + "                         new Integer(value2),\n"
        + "                         new Integer(value3)\n"
        + "                    '}');\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is [Any]Field [method]().
     */
    public static final String ANY__FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}()\n" // capitalized function
        + "    '{'\n"
        + "        return new _{2}FieldWrapper(\"{4}\");\n" // function
        + "    '}'\n\n";

    /**
     * A method whose signature is [Any]Field [method]() and doesn't represent functions.
     */
    public static final String ANY__NOFUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + ".\n" // driver - function
        + "     * @return the corresponding call.\n"
        + "     */\n"
        + "    public {2}Field {3}()\n" // capitalized function
        + "    '{'\n"
        + "        return new _{2}FieldWrapper(\"{4}\", true);\n" // function
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](DoubleField) or
     * [Any]Field [method](double).
     */
    public static final String ANY__DOUBLE_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the field.\n"
        + "     * @return the wrapping field.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field)\n" // field type - capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result = new _{2}FieldWrapper(field, \"{4}\");\n" // field type - function
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the calendar value.\n"
        + "     * @return the wrapping field.\n"
        + "     */\n"
        + "    public {2}Field {3}(double value)\n" // field type - capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n" // field type
        + "                null,\n"
        + "                \"{4}\",\n" // function
        + "                new Object[]'{'new Double(value)'}');\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](DoubleField, IntField) or
     * [Any]Field [method](DoubleField, int) or
     * [Any]Field [method](double, IntField) or
     * [Any]Field [method](double, int).
     */
    public static final String ANY__DOUBLE_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, IntField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field      != null)\n"
        + "             && (otherField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param value the argument value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, int value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Integer(value)'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the double value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double value, IntField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Double(value), field'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param arg1 the double value.\n"
        + "     * @param arg2 the int value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double arg1, int arg2)\n"
         // capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n"
        + "                null,\n"
        + "                \"{4}\",\n" // function
        + "                new Object[]\n"
        + "                '{'\n"
        + "                    new Double(arg1),\n"
        + "                    new Integer(value)\n"
        + "                '}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](DoubleField, DoubleField) or
     * [Any]Field [method](DoubleField, double) or
     * [Any]Field [method](double, DoubleField) or
     * [Any]Field [method](double, double).
     */
    public static final String ANY__DOUBLE_DOUBLE_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, DoubleField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field      != null)\n"
        + "             && (otherField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param value the double value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, double value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Double(value)'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the double value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double value, DoubleField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Double(value), field'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param arg1 the first double value.\n"
        + "     * @param arg2 the second double value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double arg1, double arg2)\n"
         // capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n"
        + "                null,\n"
        + "                \"{4}\",\n" // function
        + "                new Object[]'{'new Double(arg1), new Double(arg2)'}');\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](DoubleField, LongField) or
     * [Any]Field [method](DoubleField, long) or
     * [Any]Field [method](double, LongField) or
     * [Any]Field [method](double, long).
     */
    public static final String ANY__DOUBLE_LONG_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param otherField the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, LongField otherField)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (   (field      != null)\n"
        + "             && (otherField != null))\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'otherField'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param field the double field.\n"
        + "     * @param value the long value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(DoubleField field, long value)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    field,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Long(value)'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param value the double value.\n"
        + "     * @param field the other field.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double value, LongField field)\n"
         // capitalized function
        + "    '{'\n"
        + "        {2}Field result = null;\n\n"
        + "        if  (field != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new _{2}FieldWrapper(\n"
        + "                    null,\n"
        + "                    \"{4}\",\n" // function
        + "                    new Object[]'{'new Double(value), field'}');\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param arg1 the first double value.\n"
        + "     * @param arg2 the second long value.\n"
        + "     * @return the corresponding function call.\n"
        + "     */\n"
        + "    public {2}Field {3}(double arg1, long arg2)\n"
         // capitalized function
        + "    '{'\n"
        + "        return\n"
        + "            new _{2}FieldWrapper(\n"
        + "                null,\n"
        + "                \"{4}\",\n" // function
        + "                new Object[]'{'new Double(arg1), new Long(arg2)'}');\n"
        + "    '}'\n\n";


    /**
     * The class constructor.
     */
    public static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Public constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    public {0}Functions() {};\n\n"; // class prefix

    /**
     * A concrete inner class.
     */
    public static final String INNER_CLASS =
          "    /**\n"
        + "     * Wraps a field to call a function.\n"
        + "     * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + "     */\n"
        + "    private final class _{0}FieldWrapper\n" // field type
        + "        extends  {0}Field\n" // field type
        + "    '{'\n"
        + "        /**\n"
        + "         * The field to wrap.\n"
        + "         */\n"
        + "        private Field m__WrappedField;\n\n"
        + "        /**\n"
        + "         * The function.\n"
        + "         */\n"
        + "        private String m__strFunction;\n\n"
        + "        /**\n"
        + "         * The additional arguments.\n"
        + "         */\n"
        + "        private Object[] m__aParameters;\n\n"
        + "        /**\n"
        + "         * No function flag.\n"
        + "         */\n"
        + "        private boolean m__bNoFunction;\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given field.\n"
        + "         * @param field the field to wrap.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(final Field field, final String function)\n" // field type
        + "        '{'\n"
        + "            // Example of when ? operator cannot be subsituted by if-else.\n"
        + "            super(\n"
        + "                ((field == null) ? \"\" : field.getName()),\n"
        + "                ((field == null) ? null : field.getTable()));\n"
        + "            inmutableSetWrappedField(field);\n"
        + "            inmutableSetFunction(function);\n"
        + "            inmutableSetNoFunction(false);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given field and parameters.\n"
        + "         * @param field the field to wrap.\n"
        + "         * @param function the function.\n"
        + "         * @param parameters the additional parameters.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(\n"
        + "            final Field field, final String function, final Object[] parameters)\n"
        + "        '{'\n"
        + "            this(field, function);\n"
        + "            inmutableSetParameters(parameters);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given persistence function or "
        + "variable.\n"
        + "         * @param function the variable or function.\n"
        + "         * @param variable <code>true</code> if it is a variable.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(final String function, final boolean variable)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetNoFunction(true);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given field and parameters.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(final String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given value.\n"
        + "         * @param value the value to wrap.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(final long value, final String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetParameters(new Object[]'{'new Long(value)'}');\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given value.\n"
        + "         * @param value the value to wrap.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(final String value, final String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetParameters(new Object[]'{'value'}');\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the wrapped field.\n"
        + "         * @param field the field to wrap.\n"
        + "         */\n"
        + "        private void inmutableSetWrappedField(final Field field)\n"
        + "        '{'\n"
        + "            m__WrappedField = field;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the wrapped field.\n"
        + "         * @param field the field to wrap.\n"
        + "         */\n"
        + "        public void setWrappedField(final Field field)\n"
        + "        '{'\n"
        + "            inmutableSetWrappedField(field);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the wrapped field.\n"
        + "         * @return such field.\n"
        + "         */\n"
        + "        public Field getWrappedField()\n"
        + "        '{'\n"
        + "            return m__WrappedField;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the function.\n"
        + "         * @param function such function.\n"
        + "         */\n"
        + "        private void inmutableSetFunction(String function)\n"
        + "        '{'\n"
        + "            m__strFunction = function;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the function.\n"
        + "         * @param function such function.\n"
        + "         */\n"
        + "        public void setFunction(String function)\n"
        + "        '{'\n"
        + "            inmutableSetFunction(function);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the function.\n"
        + "         * @return such information.\n"
        + "         */\n"
        + "        public String getFunction()\n"
        + "        '{'\n"
        + "            return m__strFunction;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the parameter list.\n"
        + "         * @param parameters the parameters.\n"
        + "         */\n"
        + "        private void inmutableSetParameters(Object[] parameters)\n"
        + "        '{'\n"
        + "            m__aParameters = parameters;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the parameter list.\n"
        + "         * @param parameters the parameters.\n"
        + "         */\n"
        + "        public void setParameters(Object[] parameters)\n"
        + "        '{'\n"
        + "            inmutableSetParameters(parameters);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the parameter list.\n"
        + "         * @return the parameters.\n"
        + "         */\n"
        + "        public Object[] getParameters()\n"
        + "        '{'\n"
        + "            return m__aParameters;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies whether the operator is a function or a variable.\n"
        + "         * @param variable such flag.\n"
        + "         */\n"
        + "        private void inmutableSetNoFunction(boolean function)\n"
        + "        '{'\n"
        + "            m__bNoFunction = function;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies whether the operator is a function or a variable.\n"
        + "         * @param variable such flag.\n"
        + "         */\n"
        + "        public void setNoFunction(boolean variable)\n"
        + "        '{'\n"
        + "            inmutableSetNoFunction(variable);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves whether the operator is a function or a variable.\n"
        + "         * @return such information.\n"
        + "         */\n"
        + "        public boolean isNotAFunction()\n"
        + "        '{'\n"
        + "            return m__bNoFunction;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a text representation of this object.\n"
        + "         * @return this object in text format.\n"
        + "         */\n"
        + "        public String toString()\n"
        + "        '{'\n"
        + "            StringBuffer t_sbResult = new StringBuffer();\n\n"
        + "            QueryUtils t_QueryUtils = QueryUtils.getInstance();\n"
        + "            boolean t_bVariable = isNotAFunction();\n"
        + "            String t_strFunction = getFunction();\n"
        + "            if  (t_strFunction != null)\n"
        + "            '{'\n"
        + "                t_sbResult.append(t_strFunction);\n"
        + "            '}'\n\n"
        + "            if  (!t_bVariable)\n"
        + "            '{'\n"
        + "                t_sbResult.append(\"(\");\n"
        + "                Field t_strWrappedField = getWrappedField();\n"
        + "                if  (t_strWrappedField != null)\n"
        + "                '{'\n"
        + "                    t_sbResult.append(t_strWrappedField.toString());\n\n"
        + "                '}'\n\n"
        + "                Object[] t_aArgs = getParameters();\n\n"
        + "                if  (   (t_aArgs != null)\n"
        + "                     && (t_aArgs.length > 0))\n"
        + "                '{'\n"
        + "                    if  (t_strWrappedField != null)\n"
        + "                    '{'\n"
        + "                        t_sbResult.append(\",\");\n\n"
        + "                    '}'\n"
        + "                    if  (   (t_QueryUtils != null)\n"
        + "                         && (t_QueryUtils.shouldBeEscaped(t_aArgs[0])))\n"
        + "                    '{'\n"
        + "                        t_sbResult.append(\"{1}\");\n"
        + "                    '}'\n"
        + "                    t_sbResult.append(t_aArgs[0]);\n\n"
        + "                    if  (   (t_QueryUtils != null)\n"
        + "                         && (t_QueryUtils.shouldBeEscaped(t_aArgs[0])))\n"
        + "                    '{'\n"
        + "                        t_sbResult.append(\"{1}\");\n"
        + "                    '}'\n"
        + "                    boolean t_bQuoted = false;\n"
        + "                    for  (int t_iIndex = 1; t_iIndex < t_aArgs.length; "
        + "t_iIndex++)\n"
        + "                    '{'\n"
        + "                        t_sbResult.append(\",\");\n"
        + "                        t_bQuoted = (   (t_QueryUtils != null)\n"
        + "                                     && (t_QueryUtils.shouldBeEscaped("
        + "t_aArgs[t_iIndex])));\n"
        + "                        if  (t_bQuoted)\n"
        + "                        '{'\n"
        + "                            t_sbResult.append(\"{1}\");\n"
        + "                        '}'\n"
        + "                        t_sbResult.append(t_aArgs[t_iIndex]);\n"
        + "                        if  (   (t_bQuoted)\n"
        + "                             && (t_iIndex < t_aArgs.length - 1))\n"
        + "                        '{'\n"
        + "                            t_sbResult.append(\"{1}\");\n"
        + "                        '}'\n"
        + "                    '}'\n"
        + "                '}'\n\n"
        + "                if  (   (t_strFunction != null)\n"
        + "                     && (t_aArgs != null)\n"
        + "                     && (t_aArgs.length > 1)\n"
        + "                     && (t_QueryUtils != null)\n"
        + "                     && (t_QueryUtils.shouldBeEscaped(\n"
        + "                            t_aArgs[t_aArgs.length - 1])))\n"
        + "                '{'\n"
        + "                    t_sbResult.append(\"{1}\");\n\n"
        + "                '}'\n"
        + "                t_sbResult.append(\")\");\n"
        + "            '}'\n\n"
        + "            return t_sbResult.toString();\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Checks whether this instance is equal to given one.\n"
        + "         * @param other the other instance to compare to.\n"
        + "         * @return <code>true</code> if both instances represent\n"
        + "         * the same entity.\n"
        + "         */\n"
        + "        public boolean equals(final Object other)\n"
        + "        '{'\n"
        + "            boolean result = false;\n\n"
        + "            if  (other != null)\n"
        + "            '{'\n"
        + "                String t_strThisToString = toString();\n\n"
        + "                String t_strOtherToString = other.toString();\n\n"
        + "                if  (t_strThisToString == null)\n"
        + "                '{'\n"
        + "                    result = (t_strOtherToString == null);\n"
        + "                '}'\n"
        + "                else\n"
        + "                '{'\n"
        + "                    result = t_strThisToString.equals(t_strOtherToString);\n"
        + "                '}'\n"
        + "            '}'\n\n"
        + "            return result;\n"
        + "        '}'\n"
        + "    '}'\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
