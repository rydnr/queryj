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
 * Filename: XMLValueObjectFactoryTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate XML-specific value object factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/**
 * Is able to generate value object factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision: 1659 $ at $Date: 2007-01-25 21:18:08 +0100 (Thu, 25 Jan 2007) $ by $Author: chous $
 */
public interface XMLValueObjectFactoryTemplateDefaults
    extends  JavaTemplateDefaults,
             Singleton
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
        + " * Description: Is able to create {0} value objects according to\n"
        + " *              the information stored in the persistence domain.\n"
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
        + "import {0}.{1}ValueObject;\n" // value object package - Table
        + "import {0}.{1}ValueObjectFactory;\n\n"; // value object package - Table

    /**
     * The ACM-SL imports.
     */
    public static final String DEFAULT_ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n"
        + "import org.acmsl.commons.utils.ConversionUtils;\n\n";

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The extra imports.
     */
    public static final String DEFAULT_EXTRA_IMPORTS =
          "/*\n"
        + " * Importing some additional classes.\n"
        + " */\n"
        + "import org.apache.commons.digester.Digester;\n"
        + "import org.apache.commons.digester.ObjectCreationFactory;\n"
        + "import org.xml.sax.Attributes;\n"
        + "import org.xml.sax.SAXException;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Adds support for creating {0} value objects from\n" // table
        + " * SAX attributes (as required by Digester-based parsing).\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class XML{0}ValueObjectFactory\n"
        + "    extends {0}ValueObjectFactory\n"
        + "    implements ObjectCreationFactory\n"; // table

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "{\n"
        + "    /**\n"
        + "     * Temporary Digester reference.\n"
        + "     */\n"
        + "    private Digester m__Digester;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Singleton implemented to avoid the double-checked locking.\n"
        + "     */\n"
        + "    private static class XML{0}ValueObjectFactorySingletonContainer\n"
        + "    '{'\n"
        + "        /**\n"
        + "         * The actual singleton.\n"
        + "         */\n"
        + "        public static final XML{0}ValueObjectFactory SINGLETON =\n"
        + "             new XML{0}ValueObjectFactory();\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected XML{0}ValueObjectFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Retrieves a <code>XML{0}ValueObjectFactory</code> instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}ValueObjectFactory getInstance()\n"
        + "    '{'\n"
        + "        return getXMLInstance();\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a <code>XML{0}ValueObjectFactory</code> instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static XML{0}ValueObjectFactory getXMLInstance()\n"
        + "    '{'\n"
        + "        return XML{0}ValueObjectFactorySingletonContainer.SINGLETON;\n"
        + "    '}'\n\n";

    /**
     * The extra methods.
     */
    public static final String DEFAULT_EXTRA_METHODS =
          "    /**\n"
        + "     * Specifies a new Digester instance.\n"
        + "     * @param digester such instance.\n"
        + "     */\n"
        + "    public void setDigester(final Digester digester)\n"
        + "    {\n"
        + "        m__Digester = digester;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the Digester instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public Digester getDigester()\n"
        + "    {\n"
        + "        return m__Digester;\n"
        + "    }\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates a {0} value object from given SAX attributes.\n"
        + "     * @param attributes the attributes.\n"
        + "     * @return the {0} information.\n"
        + "     * @throws SAXException if the attributes are not valid.\n"
        + "     * @precondition attributes != null\n"
        + "     */\n"
        + "    public Object createObject(final Attributes attributes)\n"
        + "      throws SAXException\n"
        + "    '{'\n"
        + "        return createObject(attributes, getDigester(), ConversionUtils.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates a {0} value object from given SAX attributes.\n"
        + "     * @param attributes the attributes.\n"
        + "     * @param digester the Digester instance.\n"
        + "     * @param conversionUtils the ConversionUtils instance.\n"
        + "     * @return the {0} information.\n"
        + "     * @throws SAXException if the attributes are not valid.\n"
        + "     * @precondition attributes != null\n"
        + "     * @precondition digester != null\n"
        + "     * @precondition conversionUtils != null\n"
        + "     */\n"
        + "    public Object createObject(\n"
        + "        final Attributes attributes,\n"
        + "        final Digester digester,\n"
        + "        final ConversionUtils conversionUtils)\n"
        + "      throws SAXException\n"
        + "    '{'\n"
        + "        {0}ValueObject result = null;\n"
        + "{1}\n" // attribute build
        + "        result =\n"
        + "            create{0}("
        + "{2});\n\n"  // factory method value object build.
        + "        //digester.push(result);\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default attribute build.
     */
    public static final String DEFAULT_FACTORY_METHOD_ATTRIBUTE_BUILD =
          "\n        {0} {1} =\n" // field type, field name
        + "            conversionUtils.to{2}{3}(attributes.getValue(\"{4}\"));\n";
        // field type, capitalized field type, uncapitalized field name,

    /**
     * The conversion method suffix for nullable values.
     */
    public static final String DEFAULT_CONVERSION_METHOD_SUFFIX_FOR_NULLABLE_VALUES =
        "IfNotNull";

    /**
     * The default factory method value object build.
     */
    public static final String DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD =
        "\n                {1}"; // Field - field;

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
