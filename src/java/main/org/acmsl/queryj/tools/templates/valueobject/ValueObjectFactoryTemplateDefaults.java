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
 * Description: Defines the default subtemplates used to generate value object
 *              factories according to database metadata.
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
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate value object
 * factories according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public interface ValueObjectFactoryTemplateDefaults
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
        + " * Description: Is able to create \"{0}\" value objects\n"
        + " *              according to the information stored in the\n"
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
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing some project classes.\n"
        + " */\n"
        + "import {0}.{1}ValueObject;\n\n"; // package - Table

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Is able to create <i>{0}</i> value objects according to\n"
        + " * information in the persistence domain.\n" // table
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
           "public class {0}ValueObjectFactory\n"; // table

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
        + "     */\n"
        + "    public {0}ValueObjectFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param factory the factory instance to use.\n"
        + "     */\n"
        + "    protected static void setReference(\n"
        + "        final {0}ValueObjectFactory factory)\n" // table
        + "    '{'\n"
        + "        singleton = new WeakReference(factory);\n"
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
        + "     * Retrieves a <code>{0}ValueObjectFactory</code> instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}ValueObjectFactory getInstance()\n"
        + "    '{'\n"
        + "        {0}ValueObjectFactory result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}ValueObjectFactory) reference.get();\n"
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}ValueObjectFactory();\n\n"
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates a {0} value object.\n"
        + "{1}" // constructor field javadoc
        + "     */\n"
        + "    public {0}ValueObject create{0}(" // table
        + "{2})\n" // factory method field declaration
        + "    '{'\n"
        + "        return\n"
        + "            new {0}ValueObject("
        + "{3});\n"  // factory method value object build.
        + "    '}'\n\n";

    /**
     * The default factory method field Javadoc.
     */
    public static final String DEFAULT_FACTORY_METHOD_FIELD_JAVADOC =
        "     * @param {0} the <i>{1}</i> information.\n"; // field - FIELD;

    /**
     * The default factory method field declaration.
     */
    public static final String DEFAULT_FACTORY_METHOD_FIELD_DECLARATION =
        "\n        final {0} {1}"; // field type - field;

    /**
     * The default factory method value object build.
     */
    public static final String DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD =
        "\n                {1}"; // field;

    /**
     * The factory alias method.
     */
    public static final String DEFAULT_FACTORY_ALIAS_METHOD =
          "    /**\n"
        + "     * Creates a <i>{0}</i> value object.\n"
        + "{1}" // constructor field javadoc
        + "     */\n"
        + "    public {0}ValueObject create{0}ValueObject(" // table
        + "{2})\n" // factory method field declaration
        + "    '{'\n"
        + "        return\n"
        + "            create{0}("
        + "{3});\n\n"  // factory method value object build.
        + "    '}'\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
