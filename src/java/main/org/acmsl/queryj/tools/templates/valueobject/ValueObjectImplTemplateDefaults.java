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
 * Description: Defines the default subtemplates used to generate basic value
 *              objects implementation according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate basic value
 * objects implementation according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public interface ValueObjectImplTemplateDefaults
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
        + " * Description: Abstract representation of the \"{0}\"\n"
        + " * information stored in the persistence domain.\n"
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
    public static final String PACKAGE_DECLARATION = "package {0};\n\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Abstract implementation of the <i>{0}</i>\n"
        + " * information stored in the persistence domain.\n" // table
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public abstract class Abstract{0}ValueObject\n" // table
        + "    implements {0}\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{\n";

    /**
     * The field declaration.
     */
    public static final String DEFAULT_FIELD_DECLARATION =
          "    /**\n"
        + "     * The <i>{0}</i> information.\n" // field
        + "     */\n"
        + "    private {1} {2};\n\n"; // field type - field

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CONSTRUCTOR =
          "    /**\n"
        + "     * Creates an <code>Abstract{0}ValueObject</code>\n"
        + "     *  with given information.\n"
        + "{1}" // constructor field javadoc
        + "     */\n"
        + "    protected Abstract{0}ValueObject(" // table
        + "{2})\n" // constructor field declaration
        + "    '{'"
        + "{3}\n"  // constructor field value setter.
        + "    '}'\n";

    /**
     * The default constructor field Javadoc.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_JAVADOC =
        "     * @param {0} the <i>{1}</i> information.\n"; // field - FIELD;

    /**
     * The default constructor field declaration.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_DECLARATION =
        "\n        final {0} {1}"; // field type - field;

    /**
     * The default constructor field value setter.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_VALUE_SETTER =
          "\n        immutableSet{0}(\n"
        + "            {1});"; // Field - field;

    /**
     * The default field setter method.
     */
    public static final String DEFAULT_FIELD_VALUE_SETTER_METHOD =
          "\n"
        + "    /**\n"
        + "     * Specifies the <i>{0}</i> information.\n" // field
        + "     * @param {0} the <i>{0}</i> value.\n"
        + "     */\n"
        + "    private void immutableSet{2}(\n" // capitalized field
        + "        final {1} {0})\n" // field type - field name
        + "    '{'\n"
        + "        this.{0} = {0};\n" // field
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the <i>{0}</i> information.\n" // field
        + "     * @param {0} the new <i>{0}</i> value.\n"
        + "     */\n"
        + "    protected void set{2}(\n" // capitalized field
        + "        final {1} {0})\n" // field type - field name
        + "    '{'\n"
        + "        immutableSet{2}({0});\n" // field
        + "    '}'\n";

    /**
     * The default field getter method.
     */
    public static final String DEFAULT_FIELD_VALUE_GETTER_METHOD =
          "\n"
        + "    /**\n"
        + "     * Retrieves the <i>{0}</i> information.\n" // field
        + "     * @return such value.\n"
        + "     */\n"
        + "    public {1} get{2}()\n" // field type - capitalized field
        + "    '{'\n"
        + "        return {0};\n" // field
        + "    '}'\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
