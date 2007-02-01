/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: BaseDAOFactoryTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the default subtemplates used to generate base DAO
 *              factories according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate base DAO factories
 * according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface BaseDAOFactoryTemplateDefaults
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
        + " * Description: Is able to create \"{0}\" DAO instances.\n"
        + " *              (Abstract Factory pattern)\n"
        + " *\n"
        + " */\n";

    /**
     * The package declaration.
     */
    public static final String PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

    /**
     * The ACM-SL imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "/*\n"
        + " * Importing some project classes.\n"
        + " */\n"
        + "import {0}.{1}DAO;\n" // package - Table
        + "import {2}.DAOChooser;\n\n"; // DAOChooser package

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.IllegalAccessException;\n"
        + "import java.lang.InstantiationException;\n\n";

    /**
     * The commons-logging imports.
     */
    public static final String DEFAULT_COMMONS_LOGGING_IMPORTS =
          "/*\n"
        + " * Importing some commons-logging classes.\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Is able to create <i>{0}</i> DAO instances (Abstract Factory pattern).\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
           "public abstract class {0}DAOFactory\n"; // table

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
        "{\n";

    /**
     * The class getInstance() method.
     */
    public static final String DEFAULT_GET_INSTANCE_METHOD =
          "    /**\n"
        + "     * Retrieves a {0}DAOFactory instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}DAOFactory getInstance()\n"
        + "    '{'\n"
        + "        return getInstance(DAOChooser.getInstance());\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a {0}DAOFactory instance.\n"
        + "     * @param daoChooser the DAOChooser instance.\n"
        + "     * @return such instance.\n"
        + "     * @precondition daoChooser != null\n"
        + "     */\n"
        + "    protected static {0}DAOFactory getInstance(final DAOChooser daoChooser)\n"
        + "    '{'\n"
        + "        {0}DAOFactory result = null;\n\n"
        + "        String t_str{0}DAOFactoryClassName =\n"
        + "            daoChooser.get{0}DAOFactoryClassName();\n\n"
        + "        if  (t_str{0}DAOFactoryClassName != null)\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                Class t_FactoryClass =\n"
        + "                    Class.forName(\n"
        + "                        t_str{0}DAOFactoryClassName);\n\n"
        + "                result =\n"
        + "                    ({0}DAOFactory) t_FactoryClass.newInstance();\n"
        + "            '}'\n"
        + "            catch  (final ClassNotFoundException classNotFoundException)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog({0}DAOFactory.class).error(\n"
        + "                        \"Cannot find {0} DAO Factory implementation class\",\n"
        + "                        classNotFoundException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n\n"
        + "            '}'\n"
        + "            catch  (final InstantiationException instantiationException)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog({0}DAOFactory.class).error(\n"
        + "                        \"Cannot instantiate {0} DAO Factory implementation\",\n"
        + "                        instantiationException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n\n"
        + "            '}'\n"
        + "            catch  (final IllegalAccessException illegalAccessException)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog({0}DAOFactory.class).error(\n"
        + "                        \"Cannot access {0} DAO Factory implementation\",\n"
        + "                        illegalAccessException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        else\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                LogFactory.getLog({0}DAOFactory.class).error(\n"
        + "                    \"{0} DAO Factory implementation not specified\");\n"
        + "            '}'\n"
        + "            catch  (final Throwable throwable)\n"
        + "            '{'\n"
        + "                // class-loading problem.\n"
        + "            '}'\n\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates an <i>{0}</i> DAO.\n"
        + "     * @return such DAO.\n"
        + "     */\n"
        + "    public abstract {0}DAO create{0}DAO();\n"; // table

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
