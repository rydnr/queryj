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
 * Description: Defines the default subtemplates used to generate
 *              DataAccessManager facade class according to database metadata.
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate DataAccessManager
 * facade class according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public interface DataAccessManagerTemplateDefaults
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
        + " * Description: Facade to facilitate access to all DAOs in\n"
        + " *              {0} model.\n"
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
     * The package declaration.
     */
    public static final String DEFAULT_PACKAGE_DECLARATION =
        "package {0};\n\n"; // package

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS_JAVADOC =
          "/*\n"
        + " * Importing project-specific classes.\n"
        + " */\n";

    /**
     * The project imports.
     */
    public static final String DEFAULT_PROJECT_IMPORTS =
          "import {0}.{1}DAO;\n" // package - table
        + "import {0}.{1}DAOFactory;\n";

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
        + " * Facade to facilitate access to all DAOs in {0} model.\n" // repository
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
         "public abstract class DataAccessManager\n"; // repository

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
     * The DAO reference.
     */
    public static final String DEFAULT_DAO_REFERENCE =
         "    /**\n"
       + "     * The {0} DAO reference (cached whereas Manager instance is not\n"
       + "     * garbage collected).\n"
       + "     */\n"
       + "    private {0}DAO m__{0}DAO;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected DataAccessManager() {};\n\n"
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param manager the new manager instance.\n"
        + "     */\n"
        + "    protected static void setReference(DataAccessManager manager)\n" // table
        + "    {\n"
        + "        singleton = new WeakReference(manager);\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves the weak reference.\n"
        + "     * @return such reference.\n"
        + "     */\n"
        + "    protected static WeakReference getReference()\n"
        + "    {\n"
        + "        return singleton;\n"
        + "    }\n\n"
        + "    /**\n"
        + "     * Retrieves a DataAccessManager instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static DataAccessManager getInstance()\n"
        + "    {\n"
        + "        DataAccessManager result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        {\n"
        + "            result = (DataAccessManager) reference.get();\n"
        + "        }\n\n"
        + "        if  (result == null) \n"
        + "        {\n"
        + "            result = new DataAccessManager() {};\n\n"
        + "            setReference(result);\n"
        + "        }\n\n"
        + "        return result;\n"
        + "    }\n\n";

    /**
     * The DAO methods.
     */
    public static final String DEFAULT_DAO_METHODS =
         "    /**\n"
       + "     * Specifies the new {0} DAO reference to keep.\n"
       + "     * @param dao the new DAO.\n"
       + "     */\n"
       + "    protected void set{0}DAOReference({0}DAO dao)\n"
       + "    '{'\n"
       + "        m__{0}DAO = dao;\n"
       + "    '}'\n\n"
       + "    /**\n"
       + "     * Retrieves the existing {0} DAO reference.\n"
       + "     * @return a DAO instance for accessing {0} information.\n"
       + "     */\n"
       + "    protected {0}DAO get{0}DAOReference()\n"
       + "    '{'\n"
       + "        return m__{0}DAO;\n"
       + "    '}'\n\n"
       + "    /**\n"
       + "     * Retrieves a {0} DAO.\n"
       + "     * @return a DAO instance for accessing {0} information.\n"
       + "     */\n"
       + "    public {0}DAO get{0}DAO()\n"
       + "    '{'\n"
       + "        {0}DAO result = get{0}DAOReference();\n\n"
       + "        if  (result == null)\n"
       + "        '{'\n"
       + "            {0}DAOFactory t_{0}DAOFactory =\n"
       + "                {0}DAOFactory.getInstance();\n\n"
       + "            if  (t_{0}DAOFactory != null)\n"
       + "            '{'\n"
       + "                result = t_{0}DAOFactory.create{0}DAO();\n"
       + "                set{0}DAOReference(result);\n"
       + "            '}'\n"
       + "        '}'\n\n"
       + "        return result;\n"
       + "    '}'\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
