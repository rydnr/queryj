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
 * Description: Defines the default subtemplates used to generate DAO
 *              factories according to database metadata.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public interface DAOFactoryTemplateDefaults
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
        + " * Description: Is able to create {0} DAOs according to\n"
        + " *              the information stored in the persistence domain.\n"
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
        + "import {0}.{1}DAO;\n"
         // DAO package - Engine - Table
        + "import {2}.{1}DAOFactory;\n"
         // DAO factory package - Engine - Table
        + "import {3}.{4}{1}DAO;\n\n";
         // DAO implementation package - Engine - Table

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n\n";

    /**
     * The extension imports.
     */
    public static final String DEFAULT_EXTENSION_IMPORTS =
          "/*\n"
        + " * Importing some extension classes.\n"
        + " */\n"
        + "import javax.sql.DataSource;\n"
        + "import javax.naming.InitialContext;\n"
        + "import javax.naming.NamingException;\n\n";

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
        + " * Is able to create {1} DAOs according to\n"
        + " * information in the {0} persistence domain.\n" // table
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class {0}{1}DAOFactory\n"
        + "    extends  {1}DAOFactory\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * The data source JNDI location.\n"
        + "     */\n"
        + "    public static final String JNDI_LOCATION = \"{0}\";\n\n"
        + "    /**\n"
        + "     * Singleton implemented as a weak reference.\n"
        + "     */\n"
        + "    private static WeakReference singleton;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Public constructor to allow reflective instantiation.\n"
        + "     */\n"
        + "    public {0}{1}DAOFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param factory the new factory instance.\n"
        + "     */\n"
        + "    protected static void setReference(final {0}{1}DAOFactory factory)\n" // table
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
        + "     * Retrieves a {0}{1}DAOFactory instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}{1}DAOFactory get{0}Instance()\n"
        + "    '{'\n"
        + "        {0}{1}DAOFactory result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}{1}DAOFactory) reference.get();\n"
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}{1}DAOFactory();\n\n"
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates {0}-specific {1} DAO.\n"
        + "     * @return such DAO.\n"
        + "     */\n"
        + "    public {1}DAO create{1}DAO()\n" // table
        + "    '{'\n"
        + "        {0}{1}DAO result = null;\n\n"
        + "        DataSource t_DataSource = getDataSource();\n\n"
        + "        if  (t_DataSource != null)\n"
        + "        '{'\n"
        + "            result =\n"
        + "                new {0}{1}DAO(t_DataSource) '{'}';\n"
        + "        '}'\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default data source retrieval method.
     */
    public static final String DEFAULT_DATA_SOURCE_RETRIEVAL_METHOD =
         "    /**\n"
       + "     * Retrieves the data source.\n"
       + "     * @return such data source.\n"
       + "     */\n"
       + "    protected DataSource getDataSource()\n"
       + "    {\n"
       + "        DataSource result = null;\n"
       + "        try\n"
       + "        {\n"
       + "            InitialContext t_InitialContext = new InitialContext();\n"
       + "            result = (DataSource) t_InitialContext.lookup(JNDI_LOCATION);\n"
       + "        }\n"
       + "        catch  (final NamingException namingException)\n"
       + "        {\n"
       + "            LogFactory.getLog(getClass()).error(\n"
       + "                \"cannot.retrieve.data.source\", namingException);\n"
       + "        }\n\n"
       + "        return result;\n"
       + "   }\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
