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
 * Description: Contains the default subtemplates for building XML-based DAO
 *              factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/**
 * Contains the default subtemplates for building XML-based DAO factories.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface XMLDAOFactoryTemplateDefaults
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002-2005  Jose San Leandro Armendariz\n"
        + "                        chous@acm-sl.org\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: chous@acm-sl.org\n"
        + "    Postal Address: c/Playa de Lagoa, 1\n"
        + "                    Urb. Valdecabanas\n"
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
        + " * Description: Is able to create {0} XML DAOs according to\n"
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
        + "import {0}.{1}DAO;\n"
         // DAO package - Table
        + "import {2}.{1}DAOFactory;\n"
         // DAO factory package - Table
        + "//import {3}.XML{1}DAO;\n\n";
         // XML DAO package - Table

    /**
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.io.File;\n"
        + "import java.io.FileInputStream;\n"
        + "import java.io.FileNotFoundException;\n"
        + "import java.io.FileOutputStream;\n"
        + "import java.io.InputStream;\n"
        + "import java.io.IOException;\n"
        + "import java.io.OutputStream;\n"
        + "import java.lang.ref.WeakReference;\n\n";

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
        + " * Is able to create XML {0} DAOs.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
          "public class XML{0}DAOFactory\n"
        + "    extends  {0}DAOFactory\n";

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START =
          "'{'\n"
        + "    /**\n"
        + "     * The default file location.\n"
        + "     */\n"
        + "    protected static final String FILE_PATH = \"{0}.xml\";\n\n"
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
        + "    protected XML{0}DAOFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param factory the new factory instance.\n"
        + "     */\n"
        + "    protected static void setReference(final XML{0}DAOFactory factory)\n" // table
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
        + "     * Retrieves a XML{0}DAOFactory instance.\n" // table
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static XML{0}DAOFactory getXMLInstance()\n" // table
        + "    '{'\n"
        + "        XML{0}DAOFactory result = null;\n\n" // table
        + "        WeakReference t_Reference = getReference();\n\n"
        + "        if  (t_Reference != null) \n"
        + "        '{'\n"
        + "            result = (XML{0}DAOFactory) t_Reference.get();\n" // table
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new XML{0}DAOFactory() '{' '}';\n\n" // table
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The factory method.
     */
    public static final String DEFAULT_FACTORY_METHOD =
          "    /**\n"
        + "     * Creates a XML {0} DAO.\n" // table
        + "     * @return such DAO.\n"
        + "     */\n"
        + "    public {0}DAO create{0}DAO()\n" // table
        + "    '{'\n"
        + "        {0}DAO result = null;\n\n"
        + "        String t_strOutput = null;\n"
        + "        try\n"
        + "        '{'\n"
        + "            t_strOutput =\n"
        + "                File.createTempFile(FILE_PATH, \"tmp\")\n"
        + "                    .getAbsolutePath();\n"
        + "        '}'\n"
        + "        catch  (final IOException ioException)\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                LogFactory.getLog(getClass()).warn(\n"
        + "                      \"Could not create temporary file.\"\n"
        + "                    + \"Serialization disabled.\",\n"
        + "                    ioException);\n"
        + "            '}'\n"
        + "            catch  (final Throwable throwable)\n"
        + "            '{'\n"
        + "                // class-loading problem.\n"
        + "            '}'\n"
        + "        '}'\n"
        + "        result = create{0}DAO(FILE_PATH, t_strOutput);\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Creates a XML {0} DAO.\n" // table
        + "     * @param inputFilePath the file path (retrieved via Class.getResourceAsStream() or\n"
        + "     * new FileInputStream())).\n"
        + "     * @param outputFilePath (<b>optional</b>) where to save the changes to.\n"
        + "     * @return such DAO.\n"
        + "     * @precondition inputFilePath != null\n"
        + "     */\n"
        + "    public {0}DAO create{0}DAO(\n"
        + "        final String inputFilePath, final String outputFilePath)\n" // table
        + "    '{'\n"
        + "        XML{0}DAO result = null;\n\n" // table
        + "        InputStream t_isInput = getClass().getResourceAsStream(inputFilePath);\n\n"
        + "        if  (t_isInput == null)\n"
        + "        '{'\n"
        + "            t_isInput = getClass().getResourceAsStream(\"/\" + inputFilePath);\n"
        + "        '}'\n\n"
        + "        if  (t_isInput == null)\n"
        + "        '{'\n"
        + "            try\n"
        + "            '{'\n"
        + "                t_isInput = new FileInputStream(inputFilePath);\n"
        + "            '}'\n"
        + "            catch  (final FileNotFoundException fileNotFoundException)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog(getClass()).error(\n"
        + "                        \"no {0} information found at \" + inputFilePath,\n"
        + "                        fileNotFoundException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n"
        + "            '}'\n"
        + "        '}'\n\n"
        + "        if  (t_isInput == null)\n"
        + "        '{'\n"
        + "            LogFactory.getLog(getClass()).error(\n"
        + "                \"No {0} information found at \" + inputFilePath);\n"
        + "        '}'\n"
        + "        else\n"
        + "        '{'\n"
        + "            OutputStream t_osOutput = null;\n"
        + "            try\n"
        + "            '{'\n"
        + "                t_osOutput = new FileOutputStream(outputFilePath);\n"
        + "            '}'\n"
        + "            catch  (final FileNotFoundException fileNotFoundException)\n"
        + "            '{'\n"
        + "                try\n"
        + "                '{'\n"
        + "                    LogFactory.getLog(getClass()).error(\n"
        + "                          \"No {0} information can be written to \" + outputFilePath\n"
        + "                        + \". Disabling persistent serialization.\",\n"
        + "                        fileNotFoundException);\n"
        + "                '}'\n"
        + "                catch  (final Throwable throwable)\n"
        + "                '{'\n"
        + "                    // class-loading problem.\n"
        + "                '}'\n"
        + "            '}'\n"
        + "            result = new XML{0}DAO(t_isInput, t_osOutput) '{' '}';\n" // table
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}\n";
}
