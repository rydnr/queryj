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
 * Description: Is able to generate tables according to database
 *              metadata.
 *
<<<<<<< TableTemplateDefaults.java
=======
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
>>>>>>> 1.6
 */
package org.acmsl.queryj.tools.templates;

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

/**
 * Defines the default subtemplates used to generate tables according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
<<<<<<< TableTemplateDefaults.java
=======
 * @version $Revision$
>>>>>>> 1.6
 */
public interface TableTemplateDefaults
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
        + " * Description: Represents the \"{0}\" table\n"
        + " *              in the persistence domain.\n"
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
    public static final String DEFAULT_PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.queryj.BigDecimalField;\n"
        + "import org.acmsl.queryj.CalendarField;\n"
        + "import org.acmsl.queryj.DateField;\n"
        + "import org.acmsl.queryj.DoubleField;\n"
        + "import org.acmsl.queryj.Field;\n"
        + "import org.acmsl.queryj.IntField;\n"
        + "import org.acmsl.queryj.LongField;\n"
        + "import org.acmsl.queryj.StringField;\n"
        + "import org.acmsl.queryj.Table;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Represents the <i>{0}</i> table in\n"
        + " * the persistence domain.\n" // table
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
           "public class {0}\n" // table
         + "    extends  Table\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * Singleton implemented as a weak reference.\n"
        + "     */\n"
        + "    private static WeakReference singleton;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected {0}Table(final String alias)\n" // table
        + "    '{'\n"
        + "        super(\"{1}\", alias);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected {0}Table()\n" // table
        + "    '{'\n"
        + "        this(null);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param table the table instance to use.\n"
        + "     */\n"
        + "    protected static void setReference(final {0}Table table)\n" // table
        + "    '{'\n"
        + "        singleton = new WeakReference(table);\n"
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
        + "     * Retrieves a {0}Table instance.\n"
        + "     * @param alias the desired table alias.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Table getInstance(final String alias)\n"
        + "    '{'\n"
        + "        {0}Table result = null;\n\n"
        + "        if  (alias != null)\n"
        + "        '{'\n"
        + "            result = new {0}Table(alias);\n"
        + "        '}'\n"
        + "        else\n"
        + "        '{'\n"
        + "            result = getInstance();\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a {0}Table instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Table getInstance()\n"
        + "    '{'\n"
        + "        {0}Table result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}Table) reference.get();\n"
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}Table();\n\n"
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default field Javadoc.
     */
    public static final String DEFAULT_FIELD_JAVADOC =
          "    /**\n"
        + "     * The {0}''s table {1} field.\n" // table - field
        + "     */\n";

    /**
     * The field definition.
     */
    public static final String DEFAULT_FIELD_DEFINITION =
          "    public {0} {1} =\n"
        + "        new {0}(\"{2}\", this) '{'}';\n\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected {0}Table()\n"
        + "    '{'\n"
        + "        super(NAME);\n"
        + "    '}'\n\n";

    /**
     * The getTableName method.
     */
    public static final String DEFAULT_GETTABLENAME_METHOD =
          "    /**\n"
        + "     * Retrieves the table name.\n"
        + "     * @return such name.\n"
        + "     */\n"
        + "    public String getTableName()\n"
        + "    '{'\n"
        + "        return \"{0}\";\n" // table
        + "    '}'\n\n";

    /**
     * The empty getAll method.
     */
    public static final String DEFAULT_EMPTY_GETALL_METHOD =
          "    /**\n"
        + "     * Retrieves <code>all</code> fields. "
        + "It's equivalent to a star in a query.\n"
        + "     * @return such fields.\n"
        + "     */\n"
        + "    public Field[] getAll()\n"
        + "    {\n"
        + "        return new Field[0];\n"
        + "    }\n";

    /**
     * The getAll method start.
     */
    public static final String DEFAULT_GETALL_METHOD_START =
          "    /**\n"
        + "     * Retrieves <code>all</code> fields. "
        + "It's equivalent to a star in a query.\n"
        + "     * @return such fields.\n"
        + "     */\n"
        + "    public Field[] getAll()\n"
        + "    {\n"
        + "        return\n"
        + "            new Field[]\n"
        + "            {\n"
        + "                ";

    /**
     * The "getAll" method field separator.
     */
    public static final String DEFAULT_GETALL_METHOD_FIELD_SEPARATOR =
        ",\n                ";

    /**
     * The "getAll" method end.
     */
    public static final String DEFAULT_GETALL_METHOD_END =
          "\n"
        + "            };\n"
        + "    }\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
