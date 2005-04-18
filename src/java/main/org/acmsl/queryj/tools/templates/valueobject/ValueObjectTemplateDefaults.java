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
 * Description: Defines the default subtemplates used to generate value
 *              objects according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate value objects according
 * to database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface ValueObjectTemplateDefaults
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
        + " * Description: Represents the \"{0}\" information stored in the\n"
        + " *              persistence domain.\n"
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
        + " * Represents the <i>{0}</i>\n"
        + " * information in the persistence domain.\n"
        + " * Important note: Compile me with JavacExt or AntLang in order to "
        + " * get complete equals(), hashCode() and toString() support.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class {0}ValueObject\n" // table
        + "    extends  Abstract{0}ValueObject\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{\n";

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CONSTRUCTOR =
          "    /**\n"
        + "     * Creates a {0}ValueObject with given information.\n"
        + "{1}" // constructor field javadoc
        + "     */\n"
        + "    public {0}ValueObject(" // table
        + "{2})\n" // constructor field declaration
        + "    '{'\n"
        + "        super("
        + "{3});\n"  // constructor field value setter.
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
          "\n            {0}";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
