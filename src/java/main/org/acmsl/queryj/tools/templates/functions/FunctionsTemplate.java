/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Protected
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Protected License for more details.

    You should have received a copy of the GNU General Protected
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
 * Description: Contains all common behaviour of function templates.
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
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

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
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Contains all common behaviour of function templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class FunctionsTemplate
{
    /**
     * Cached empty string array.
     */
    protected static final String[] EMPTY_STRING_ARRAY =
        new String[0];

    /**
     * The default header.
     */
    protected static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendáriz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Protected\n"
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
        + "    General Protected License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Protected\n"
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
        + " * Description: Contains the {2} functions supported by "
         // function description
        + "{0} {1}.\n"
         // engine name - driver version
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
    protected static final String PACKAGE_DECLARATION =
        "package {0};\n"; // package

    /**
     * The ACM-SL imports.
     */
    protected static final String ACMSL_IMPORTS =
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
    protected static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n\n";

    /**
     * The default class Javadoc.
     */
    protected static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Contains the {2} functions supported by {0} {1}.\n"
       // function description - engine name - driver version
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    protected static final String CLASS_DEFINITION =
        "public abstract class {0}Functions\n"; // class name prefix

    /**
     * The class start.
     */
    protected static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * Singleton implemented as a weak reference.\n"
        + "     */\n"
        + "    private static WeakReference singleton;\n\n";

    /**
     * The class singleton logic.
     */
    protected static final String SINGLETON_BODY =
          "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param functions the instance to use.\n"
        + "     */\n"
        + "    protected static void setReference({0}Functions functions)\n"
         // class name prefix
        + "    '{'\n"
        + "        singleton = new WeakReference(functions);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves the weak reference.\n"
        + "     * @return such reference.\n"
        + "     */\n"
        + "    protected static WeakReference getReference()\n"
        + "    '{'\n"
        + "        return singleton;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a {0}Functions instance.\n"
         // class name prefix
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Functions getInstance()\n"
        + "    '{'\n"
        + "        {0}Functions result = null;\n\n"
         // class name prefix 
       + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}Functions) reference.get();\n"
         // class name prefix
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}Functions() '{' '}';\n\n"
         // class name prefix
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * A method whose signature is
     * [Any]Field [method](CalendarField) or
     * [Any]Field [method](Calendar).
     */
    protected static final String ANY__CALENDAR_FUNCTION =
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
    protected static final String ANY__CALENDAR_INT_FUNCTION =
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
    protected static final String ANY__CALENDAR_CALENDAR_FUNCTION =
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
    protected static final String ANY__LONG_FUNCTION =
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
    protected static final String ANY__LONG_STRING_FUNCTION =
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
    protected static final String ANY__INT_FUNCTION =
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
    protected static final String ANY__STRING_FUNCTION =
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
    protected static final String ANY__ASTRING_FUNCTION =
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
    protected static final String ANY__FIELD_FUNCTION =
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
    protected static final String ANY__INT_INT_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstIield the first int field.\n"
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
    protected static final String ANY__LONG_LONG_FUNCTION =
          "    /**\n"
        + "     * Creates the field represented by the usage of {0}''s {1} "
        + "function.\n" // driver - function
        + "     * @param firstIield the first long field.\n"
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
    protected static final String ANY__CALENDAR_STRING_FUNCTION =
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
    protected static final String ANY__STRING_STRING_FUNCTION =
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
    protected static final String ANY__STRING_INT_FUNCTION =
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
    protected static final String ANY__CALENDAR_STRING_STRING_FUNCTION =
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
    protected static final String ANY__STRING_STRING_STRING_FUNCTION =
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
    protected static final String ANY__INT_STRING_STRING_FUNCTION =
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
    protected static final String ANY__STRING_INT_STRING_FUNCTION =
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
    protected static final String ANY__STRING_STRING_INT_FUNCTION =
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
    protected static final String ANY__STRING_INT_INT_FUNCTION =
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
    protected static final String ANY__FUNCTION =
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
    protected static final String ANY__NOFUNCTION =
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
    protected static final String ANY__DOUBLE_FUNCTION =
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
    protected static final String ANY__DOUBLE_INT_FUNCTION =
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
    protected static final String ANY__DOUBLE_DOUBLE_FUNCTION =
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
    protected static final String ANY__DOUBLE_LONG_FUNCTION =
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
    protected static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Public constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    public {0}Functions() {};\n\n"; // class prefix

    /**
     * A concrete inner class.
     */
    protected static final String INNER_CLASS =
          "    /**\n"
        + "     * Wraps a field to call a function.\n"
        + "     * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + "     * @version $" + "Revision: $\n"
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
        + "        private _{0}FieldWrapper(Field field, String function)\n" // field type
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
        + "            Field field, String function, Object[] parameters)\n"
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
        + "        private _{0}FieldWrapper(String function, boolean variable)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetNoFunction(true);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given field and parameters.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given value.\n"
        + "         * @param value the value to wrap.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(long value, String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetParameters(new Object[]'{'new Long(value)'}');\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Builds a FieldWrapper for given value.\n"
        + "         * @param value the value to wrap.\n"
        + "         * @param function the function.\n"
        + "         */\n"
        + "        private _{0}FieldWrapper(String value, String function)\n"
        + "        '{'\n"
        + "            this((Field) null, function);\n"
        + "            inmutableSetParameters(new Object[]'{'value'}');\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the wrapped field.\n"
        + "         * @param field the field to wrap.\n"
        + "         */\n"
        + "        private void inmutableSetWrappedField(Field field)\n"
        + "        '{'\n"
        + "            m__WrappedField = field;\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Specifies the wrapped field.\n"
        + "         * @param field the field to wrap.\n"
        + "         */\n"
        + "        protected void setWrappedField(Field field)\n"
        + "        '{'\n"
        + "            inmutableSetWrappedField(field);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the wrapped field.\n"
        + "         * @return such field.\n"
        + "         */\n"
        + "        protected Field getWrappedField()\n"
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
        + "        protected void setFunction(String function)\n"
        + "        '{'\n"
        + "            inmutableSetFunction(function);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the function.\n"
        + "         * @return such information.\n"
        + "         */\n"
        + "        protected String getFunction()\n"
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
        + "        protected void setParameters(Object[] parameters)\n"
        + "        '{'\n"
        + "            inmutableSetParameters(parameters);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves the parameter list.\n"
        + "         * @return the parameters.\n"
        + "         */\n"
        + "        protected Object[] getParameters()\n"
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
        + "        protected void setNoFunction(boolean variable)\n"
        + "        '{'\n"
        + "            inmutableSetNoFunction(variable);\n"
        + "        '}'\n\n"
        + "        /**\n"
        + "         * Retrieves whether the operator is a function or a variable.\n"
        + "         * @return such information.\n"
        + "         */\n"
        + "        protected boolean isNotAFunction()\n"
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
        + "        '}'\n"
        + "    '}'\n";

    /**
     * The default class end.
     */
    protected static final String DEFAULT_CLASS_END = "}\n";

    /**
     * The function class description.
     */
    private String m__strClassDescription;

    /**
     * The function class prefix.
     */
    private String m__strClassPrefix;

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
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The quote.
     */
    private String m__strQuote;

    /**
     * The function list.
     */
    private List m__lFunctions;

    /**
     * The field types used.
     */
    private List m__lFieldTypes;

    /**
     * The function - field type mapping.
     */
    private static Map m__mMappings;

    /**
     * The special function - code mapping.
     */
    private static Map m__mSpecialMappings;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The class Javadoc.
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
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The inner class.
     */
    private String m__strInnerClass;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The reference to the project, for logging purposes.
     */
    private Project m__Project;

    /**
     * The reference to the task, for logging purposes.
     */
    private Task m__Task;

    /**
     * Builds a FunctionsTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param classConstructor the class constructor.
     * @param innerClass the inner class.
     * @param classEnd the class end.
     */
    protected FunctionsTemplate(
        String classDescription,
        String classPrefix,
        String header,
        String packageDeclaration,
        String packageName,
        String engineName,
        String engineVersion,
        String quote,
        String acmslImports,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String singletonBody,
        String classConstructor,
        String innerClass,
        String classEnd)
    {
        inmutableSetClassDescription(classDescription);
        inmutableSetClassPrefix(classPrefix);
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetEngineName(engineName);
        inmutableSetEngineVersion(engineVersion);
        inmutableSetQuote(quote);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetClassConstructor(classConstructor);
        inmutableSetInnerClass(innerClass);
        inmutableSetClassEnd(classEnd);
        inmutableSetFunctions(new ArrayList());

    }

    /**
     * Builds a FunctionsTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param classConstructor the class constructor.
     * @param innerClass the inner class.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    protected FunctionsTemplate(
        String  classDescription,
        String  classPrefix,
        String  header,
        String  packageDeclaration,
        String  packageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        String  acmslImports,
        String  jdkImports,
        String  javadoc,
        String  classDefinition,
        String  classStart,
        String  singletonBody,
        String  classConstructor,
        String  innerClass,
        String  classEnd,
        Project project,
        Task    task)
    {
        this(
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
            singletonBody,
            classConstructor,
            innerClass,
            classEnd);

        inmutableSetProject(project);
        inmutableSetTask(task);
    }

    /**
     * Builds a FunctionsTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    protected FunctionsTemplate(
        String classDescription,
        String classPrefix,
        String packageName,
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
            engineName,
            engineVersion,
            quote,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            SINGLETON_BODY,
            CLASS_CONSTRUCTOR,
            INNER_CLASS,
            DEFAULT_CLASS_END);
    }

    /**
     * Builds a FunctionsTemplate using given information.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    protected FunctionsTemplate(
        String  classDescription,
        String  classPrefix,
        String  packageName,
        String  engineName,
        String  engineVersion,
        String  quote,
        Project project,
        Task    task)
    {
        this(
            classDescription,
            classPrefix,
            packageName,
            engineName,
            engineVersion,
            quote);

        inmutableSetProject(project);
        inmutableSetTask(task);
    }

    /**
     * Specifies the class description.
     * @param description the description.
     */
    private void inmutableSetClassDescription(String description)
    {
        m__strClassDescription = description;
    }

    /**
     * Specifies the class description.
     * @param description the description.
     */
    protected void setClassDescription(String description)
    {
        inmutableSetClassDescription(description);
    }

    /**
     * Retrieves the class description.
     * @return such description.
     */
    protected String getClassDescription()
    {
        return m__strClassDescription;
    }

    /**
     * Specifies the class prefix.
     * @param prefix the prefix.
     */
    private void inmutableSetClassPrefix(String prefix)
    {
        m__strClassPrefix = prefix;
    }

    /**
     * Specifies the class prefix.
     * @param prefix the prefix.
     */
    protected void setClassPrefix(String prefix)
    {
        inmutableSetClassPrefix(prefix);
    }

    /**
     * Retrieves the class prefix.
     * @return such prefix.
     */
    protected String getClassPrefix()
    {
        return m__strClassPrefix;
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
    protected String getHeader() 
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
    protected String getPackageDeclaration() 
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
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void inmutableSetEngineName(String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(String engineName)
    {
        inmutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    protected String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void inmutableSetEngineVersion(String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(String engineVersion)
    {
        inmutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    protected String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    private void inmutableSetQuote(String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void setQuote(String quote)
    {
        inmutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    protected String getQuote()
    {
        return m__strQuote;
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
    protected String getAcmslImports() 
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
     * Retrieves the JDK imports.
     * @return such information.
     */
    protected String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void inmutableSetJavadoc(String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(String javadoc)
    {
        inmutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    protected String getJavadoc() 
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
    protected String getClassDefinition() 
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
    protected String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    private void inmutableSetSingletonBody(String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(String singletonBody)
    {
        inmutableSetSingletonBody(singletonBody);
    }

    /**
     * Retrieves the singleton body.
     * @return such information.
     */
    protected String getSingletonBody()
    {
        return m__strSingletonBody;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void inmutableSetClassConstructor(String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(String constructor)
    {
        inmutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    protected String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the inner class.
     * @param innerClass the new inner class.
     */
    private void inmutableSetInnerClass(String innerClass)
    {
        m__strInnerClass = innerClass;
    }

    /**
     * Specifies the inner class.
     * @param innerClass the new inner class.
     */
    protected void setInnerClass(String innerClass)
    {
        inmutableSetInnerClass(innerClass);
    }

    /**
     * Retrieves the inner classes.
     * @return such information.
     */
    protected String getInnerClass() 
    {
        return m__strInnerClass;
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
    protected String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Specifies the functions.
     * @param functions the function collection.
     */
    private void inmutableSetFunctions(List functions)
    {
        m__lFunctions = functions;
    }

    /**
     * Specifies the functions.
     * @param functions the function collection.
     */
    protected void setFunctions(List functions)
    {
        inmutableSetFunctions(functions);
    }

    /**
     * Retrieves the functions.
     * @return such collection.
     */
    protected List getFunctions()
    {
        return m__lFunctions;
    }

    /**
     * Adds a new function.
     * @param function the new function.
     */
    public void addFunction(String function)
    {
        if  (function != null) 
        {
            List t_lFunctions = getFunctions();

            if  (t_lFunctions != null) 
            {
                t_lFunctions.add(function.trim());
            }
        }
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    protected abstract String getMapping(String function);

    /**
     * Retrieves the special mapping for given function.
     * @param function the function.
     * @return the field type.
     */
    protected abstract String getSpecialMapping(String function);

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @return the field type.
     */
    protected abstract String getSpecialMappingReturnType(String function);

    /**
     * Retrieves the default function method.
     * @return such method template.
     */
    protected abstract String getDefaultFunctionMethod();

    /**
     * Retrieves the capitalized words.
     * @return such words.
     */
    protected abstract String[] getCapitalizedWords();

    /**
     * Retrieves the field types.
     * @return such array.
     */
    protected abstract String[] getFieldTypes();

    /**
     * Creates a key for function mappings.
     * @param function the function.
     * @param templateClass the template class.
     * @return the key.
     */
    protected static String buildKey(String function, Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + "."
               : "queryj.")
            + function;
    }

    /**
     * Creates a key for special mappings.
     * @param function the function.
     * @param templateClass the template class.
     * @return the special key.
     */
    protected static String buildSpecialKey(
        String function, Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + ".special."
               : "queryj.special.")
            + function;
    }

    /**
     * Creates a key for special mappings' return type.
     * @param function the function.
     * @param templateClass the template class.
     * @return the special key.
     */
    protected static String buildSpecialKeyReturnType(
        String function, Class templateClass)
    {
        return
              (  (templateClass != null)
               ? templateClass.getName() + ".special.return."
               : "queryj.special.return.")
            + function;
    }


    /**
     * Specifies the mappings.
     * @param mappings the mappings.
     */
    protected void setMappings(Map mappings)
    {
        m__mMappings = mappings;
    }

    /**
     * Retrieves the mappings.
     * @return such map.
     */
    public Map getMappings()
    {
        Map result = m__mMappings;

        if  (result == null)
        {
            result = new HashMap();
        }

        if  (!isFilledIn(result))
        {
            synchronized (result)
            {
                result = fillUpMappings(result);
                result = fillUpSpecialMappings(result);
                result = fillUpSpecialMappingsReturnTypes(result);
                setMappings(result);
            }
        }

        return result;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    private void inmutableSetProject(Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    protected void setProject(Project project)
    {
        inmutableSetProject(project);
    }

    /**
     * Retrieves the project.
     * @return such instance.
     */
    public Project getProject()
    {
        return m__Project;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    private void inmutableSetTask(Task task)
    {
        m__Task = task;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    protected void setTask(Task task)
    {
        inmutableSetTask(task);
    }

    /**
     * Retrieves the task.
     * @return such instance.
     */
    public Task getTask()
    {
        return m__Task;
    }

    /**
     * Checks whether given map is already filled with specific
     * template mappings.
     * @param mapping the mapping table.
     * @return <code>true</code> if the map contains the specific
     * mapping entries for this template.
     */
    protected abstract boolean isFilledIn(Map mappings);

    /**
     * Checks whether a concrete mapping can be omitted without generating a
     * warning message.
     * @param mapping the concrete mapping.
     * @return <code>true</code> to generate the warning message.
     */
    protected boolean generateWarning(String mapping)
    {
        return true;
    }

    /**
     * Retrieves the mapping for given function.
     * @param function the function.
     * @param templateClass the template class.
     * @return the field type.
     */
    protected String getMapping(String function, Class templateClass)
    {
        String result = null;

        if  (   (function      != null)
             && (templateClass != null))
        {
            result =
                getMapping(
                    getMappings(),
                    buildKey(function, templateClass));
        }

        return result;
    }

    /**
     * Retrieves the mapping for given special function.
     * @param function the function.
     * @return the field type.
     */
    public String getSpecialMapping(String function, Class templateClass)
    {
        String result = null;

        if  (   (function      != null)
             && (templateClass != null))
        {
            result =
                getMapping(
                    getMappings(),
                    buildSpecialKey(function, templateClass));
        }
        
        return result;
    }

    /**
     * Retrieves the mapping for given key.
     * @param mappings the mappings.
     * @param key the key.
     * @return the field type.
     */
    protected String getMapping(Map mappings, String key)
    {
        String result = null;

        if  (   (mappings != null)
             && (key      != null))
        {
            result = (String) mappings.get(key);
        }

        return result;
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
    protected abstract Map fillUpSpecialMappingsReturnTypes(Map mappings);

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @param templateClass the template class.
     * @return the field type.
     */
    public String getSpecialMappingReturnType(
        String function, Class templateClass)
    {
        String result = null;

        if  (function != null)
        {
            result =
                getMapping(
                    getMappings(),
                    buildSpecialKeyReturnType(
                        function,
                        templateClass));
        }
        
        return result;
    }

    /**
     * Outputs the inner classes.
     * @return such classes.
     */
    protected String innerClassesToString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strInnerClass = getInnerClass();

        if  (t_strInnerClass != null) 
        {
            String[] t_astrFieldTypes = getFieldTypes();

            if  (t_astrFieldTypes != null) 
            {
                MessageFormat t_InnerClassFormatter = null;

                for  (int t_iFieldIndex = 0;
                          t_iFieldIndex < t_astrFieldTypes.length;
                          t_iFieldIndex++)
                {
                    t_InnerClassFormatter =
                        new MessageFormat(t_strInnerClass);
                
                    t_sbResult.append(
                        t_InnerClassFormatter.format(
                            new Object[]
                            {
                                t_astrFieldTypes[t_iFieldIndex],
                                getQuote()
                            }));
                }
            }
        }

        return t_sbResult.toString();
    }

    /**
     * Logs a warning message.
     * @param message the message.
     */
    protected void logWarn(String message)
    {
        Project t_Project = getProject();

        if  (t_Project != null)
        {
            t_Project.log(
                getTask(),
                message,
                Project.MSG_WARN);
        }
        else 
        {
            LogFactory.getLog(getClass()).warn(message);
        }
    }

    /**
     * Retrieves the source code of the generated field tableName.
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
                t_Formatter = new MessageFormat(getHeader());
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strPackageDeclaration = getPackageDeclaration();

            if  (t_strPackageDeclaration != null) 
            {
                t_Formatter = new MessageFormat(getPackageDeclaration());
                t_sbResult.append(t_Formatter.format(t_aPackageName));
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
                t_sbResult.append(t_strClassStart);
            }

            String t_strSingletonBody = getSingletonBody();

            if  (t_strSingletonBody != null) 
            {
                t_Formatter = new MessageFormat(t_strSingletonBody);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strFunctionMethod = getDefaultFunctionMethod();

            if  (t_strFunctionMethod != null) 
            {
                List t_lFunctions = getFunctions();

                if  (t_lFunctions != null)
                {
                    Iterator t_itFunctions = t_lFunctions.iterator();

                    MessageFormat t_MethodFormatter =
                        new MessageFormat(t_strFunctionMethod);

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
                                t_MethodFormatter =
                                    new MessageFormat(t_strMapping);

                                t_strMapping =
                                    getSpecialMappingReturnType(t_strFunction);
                            }
                        }

                        t_sbResult.append(
                            t_MethodFormatter.format(
                                new Object[]
                                {
                                    getEngineName(),
                                    t_strFunction,
                                    t_strMapping,
                                    t_StringUtils.toJavaMethod(
                                        t_strFunction.toLowerCase(),
                                        '_',
                                        getCapitalizedWords()),
                                    t_strFunction
                                }));
                    }
                }
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
