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
 * Description: Defines the default subtemplates used to generate DAOChooser
 *              class according to database metadata.
 *
<<<<<<< DAOChooserTemplateDefaults.java
=======
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
>>>>>>> 1.4
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.JavaTemplateDefaults;

/**
 * Defines the default subtemplates used to generate DAOChooser
 * class according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
<<<<<<< DAOChooserTemplateDefaults.java
=======
 * @version $Revision$
>>>>>>> 1.4
 */
public interface DAOChooserTemplateDefaults
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
        + " * Description: Manages the configuration of which concrete DAO\n"
        + " *              implementations are actually used when\n"
        + " *              retrieving DAO instances via DataAccessManager\n"
        + " *              or DAO factory interfaces.\n"
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
     * The JDK imports.
     */
    public static final String DEFAULT_JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.io.InputStream;\n"
        + "import java.lang.ref.WeakReference;\n"
        + "import java.util.Properties;\n\n";

    /**
     * The Commons-Logging imports.
     */
    public static final String DEFAULT_LOGGING_IMPORTS =
          "/*\n"
        + " * Importing some Commons-Logging classes.\n"
        + " */\n"
        + "import org.apache.commons.logging.LogFactory;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Manages the configuration of which concrete DAO\n"
        + " * implementations are actually used when\n"
        + " * retrieving DAO instances via <code>DataAccessManager</code>\n"
        + " * or DAO factory interfaces.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
         "public class DAOChooser\n"; // repository

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
     * The properties' keys.
     */
    public static final String DEFAULT_PROPERTIES_KEYS =
          "    /**\n"
        + "     * Used to retrieve the value of <i>{1}.dao</i> from the\n"
        + "     * configuration.\n"
        + "     */\n"
        + "    public static final String {0} =\n"
        + "        \"{1}.dao\";\n\n";

    /**
     * The properties reference.
     */
    public static final String DEFAULT_PROPERTIES_REFERENCE =
         "    /**\n"
       + "     * The configuration settings.\n"
       + "     */\n"
       + "    private static final String PROPERTIES_FILE = \"{0}\";\n\n"
       + "    /**\n"
       + "     * The configuration settings.\n"
       + "     */\n"
       + "    private Properties m__Properties;\n\n";

    /**
     * The class singleton logic.
     */
    public static final String DEFAULT_SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected DAOChooser() {};\n\n"
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param manager the new manager instance.\n"
        + "     */\n"
        + "    protected static void setReference(final DAOChooser chooser)\n" // table
        + "    {\n"
        + "        singleton = new WeakReference(chooser);\n"
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
        + "     * Retrieves a DAOChooser instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static DAOChooser getInstance()\n"
        + "    {\n"
        + "        DAOChooser result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        {\n"
        + "            result = (DAOChooser) reference.get();\n"
        + "        }\n\n"
        + "        if  (result == null) \n"
        + "        {\n"
        + "            result = new DAOChooser();\n\n"
        + "            setReference(result);\n"
        + "        }\n\n"
        + "        return result;\n"
        + "    }\n\n";

    /**
     * The properties accessors.
     */
    public static final String DEFAULT_PROPERTIES_ACCESSORS =
         "    /**\n"
       + "     * Specifies the properties.\n"
       + "     * @param properties the new properties.\n"
       + "     */\n"
       + "    private void immutableSetProperties(final Properties properties)\n"
       + "    {\n"
       + "        m__Properties = properties;\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Specifies the properties.\n"
       + "     * @param properties the new properties.\n"
       + "     */\n"
       + "    protected void setProperties(final Properties properties)\n"
       + "    {\n"
       + "        immutableSetProperties(properties);\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Retrieves the configuration settings.\n"
       + "     * @return the properties.\n"
       + "     */\n"
       + "    private Properties immutableGetProperties()\n"
       + "    {\n"
       + "        Properties result = getProperties();\n\n"
       + "        if  (result == null)\n"
       + "        {\n"
       + "            result = new Properties();\n\n"
       + "            loadProperties(result);\n"
       + "            immutableSetProperties(result);\n"
       + "        }\n\n"
       + "        return result;\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Retrieves the properties.\n"
       + "     * @return such information.\n"
       + "     */\n"
       + "    protected Properties getProperties()\n"
       + "    {\n"
       + "        return m__Properties;\n"
       + "    }\n\n";

    /**
     * The helper methods.
     */
    public static final String DEFAULT_HELPER_METHODS =
         "    /**\n"
       + "     * Retrieves a concrete property.\n"
       + "     * @param key the property key.\n"
       + "     * @return the configuration value associated to such setting.\n"
       + "     */\n"
       + "    private String getProperty(final String key)\n"
       + "    {\n"
       + "        // This is safe since immutableGetProperties() is private and ensures\n"
       + "        // a new object if the instance's is null.\n"
       + "        return immutableGetProperties().getProperty(key);\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Loads the configuration from a property file.\n"
       + "     * @param properties where to store the settings.\n"
       + "     */\n"
       + "    private synchronized void loadProperties(final Properties properties)\n"
       + "    {\n"
       + "        if  (properties != null)\n"
       + "        {\n"
       + "            InputStream t_isProperties = null;\n\n"
       + "            // Loading properties\n"
       + "            try\n"
       + "            {\n"
       + "                // try classpath\n"
       + "                t_isProperties =\n"
       + "                    getClass().getResourceAsStream(\"/\" + PROPERTIES_FILE);\n\n"
       + "                if  (t_isProperties == null)\n"
       + "                {\n"
       + "                    // try local\n"
       + "                    t_isProperties =\n"
       + "                        getClass().getResourceAsStream(PROPERTIES_FILE);\n"
       + "                }\n\n"
       + "                if  (t_isProperties != null)\n"
       + "                {\n"
       + "                    properties.load(t_isProperties);\n"
       + "                }\n"
       + "                else\n"
       + "                {\n"
       + "                     LogFactory.getLog(getClass()).warn(\n"
       + "                           \"Couldn't find the property file: \"\n"
       + "                         + PROPERTIES_FILE);\n"
       + "                }\n"
       + "            }\n"
       + "            catch  (final Exception exception)\n"
       + "            {\n"
       + "                LogFactory.getLog(getClass()).warn(\n"
       + "                      \"Couldn't find the property file: \"\n"
       + "                    + PROPERTIES_FILE,\n"
       + "                    exception);\n"
       + "            }\n"
       + "        }\n"
       + "        else\n"
       + "        {\n"
       + "            LogFactory.getLog(getClass()).fatal(\n"
       + "                \"Properties object not valid!\");\n"
       + "        }\n"
       + "    }\n\n";

    /**
     * The DAO methods.
     */
    public static final String DEFAULT_GETDAOFACTORY_METHOD =
         "    /**\n"
       + "     * Retrieves the {0} {2} DAO factory class name.\n"
       + "     * @return such class name.\n"
       + "     */\n"
       + "    public String get{2}DAOFactoryClassName()\n"
       + "    '{'\n"
       + "        return getProperty({1});\n"
       + "    '}'\n\n";

    /**
     * The default class end.
     */
    public static final String DEFAULT_CLASS_END = "}";
}
