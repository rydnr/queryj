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
 * Description: Is able to generate DAOChooser class according
 *              to database metadata.
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
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Is able to generate DAOChooser class according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class DAOChooserTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
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
        + "    Contact info: jsanleandro@yahoo.es\n"
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
        + " * retrieving DAO instances via DataAccessManager\n"
        + " * or DAO factory interfaces.\n"
        + " * @author <a href=\"http://maven.acm-sl.org/queryj\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String DEFAULT_CLASS_DEFINITION =
         "public abstract class DAOChooser\n"; // repository

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
        + "     * Used to retrieve the value of \"{1}.dao\" from the\n"
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
       + "    private static final String PROPERTIES_FILE = \"{0}.properties\";\n\n"
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
        + "    protected static void setReference(DAOChooser chooser)\n" // table
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
        + "            result = new DAOChooser() {};\n\n"
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
       + "    private void inmutableSetProperties(Properties properties)\n"
       + "    {\n"
       + "        m__Properties = properties;\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Specifies the properties.\n"
       + "     * @param properties the new properties.\n"
       + "     */\n"
       + "    protected void setProperties(Properties properties)\n"
       + "    {\n"
       + "        inmutableSetProperties(properties);\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Retrieves the configuration settings.\n"
       + "     * @return the properties.\n"
       + "     */\n"
       + "    private Properties inmutableGetProperties()\n"
       + "    {\n"
       + "        Properties result = getProperties();\n\n"
       + "        if  (result == null)\n"
       + "        {\n"
       + "            result = new Properties();\n\n"
       + "            loadProperties(result);\n"
       + "            inmutableSetProperties(result);\n"
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
       + "    private String getProperty(String key)\n"
       + "    {\n"
       + "        // This is safe since inmutableGetProperties() is private and ensures\n"
       + "        // a new object if the instance's is null.\n"
       + "        return inmutableGetProperties().getProperty(key);\n"
       + "    }\n\n"
       + "    /**\n"
       + "     * Loads the configuration from a property file.\n"
       + "     * @param properties where to store the settings.\n"
       + "     */\n"
       + "    private synchronized void loadProperties(Properties properties)\n"
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
       + "            catch  (Exception exception)\n"
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
    public static final String DEFAULT_CLASS_END = "}\n";

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The project import Javadoc.
     */
    private String m__strProjectImportsJavadoc;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJDKImports;

    /**
     * The Logging import statements.
     */
    private String m__strLoggingImports;

    /**
     * The class Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The properties keys.
     */
    private String m__strPropertiesKeys;

    /**
     * The properties reference
     */
    private String m__strPropertiesReference;

    /**
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The Properties accessors.
     */
    private String m__strPropertiesAccessors;

    /**
     * The helper methods.
     */
    private String m__strHelperMethods;

    /**
     * The getDAOFactory methods.
     */
    private String m__strGetDAOFactoryMethods;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds a DAOChooserTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param jdkImports the JDK imports.
     * @param loggingImports the logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param propertiesKeys the properties' keys template.
     * @param propertiesReference the properties reference template.
     * @param singletonBody the singleton body.
     * @param propertiesAccessors the properties accessors template.
     * @param helperMethods the helper methods.
     * @param getDAOFactoryMethods the getDAOFactory methods.
     * @param classEnd the class end.
     */
    public DAOChooserTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String repository,
        String jdkImports,
        String loggingImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String propertiesKeys,
        String propertiesReference,
        String singletonBody,
        String propertiesAccessors,
        String helperMethods,
        String getDAOFactoryMethods,
        String classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetRepository(repository);
        inmutableSetJdkImports(jdkImports);
        inmutableSetLoggingImports(loggingImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetPropertiesKeys(propertiesKeys);
        inmutableSetPropertiesReference(propertiesReference);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetPropertiesAccessors(propertiesAccessors);
        inmutableSetHelperMethods(helperMethods);
        inmutableSetGetDAOFactoryMethods(getDAOFactoryMethods);
        inmutableSetClassEnd(classEnd);
        inmutableSetTables(new ArrayList());
    }

    /**
     * Builds a DAOChooserTemplate using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public DAOChooserTemplate(
        String packageName,
        String repository)
    {
        this(
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_PROPERTIES_KEYS,
            DEFAULT_PROPERTIES_REFERENCE,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_PROPERTIES_ACCESSORS,
            DEFAULT_HELPER_METHODS,
            DEFAULT_GETDAOFACTORY_METHOD,
            DEFAULT_CLASS_END);
    }


    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(String header)
    {
        inmutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void inmutableSetPackageDeclaration(String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(String packageDeclaration)
    {
        inmutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void inmutableSetPackageName(String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(String packageName)
    {
        inmutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    private void inmutableSetRepository(String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(String repository)
    {
        inmutableSetRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository() 
    {
        return m__strRepository;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(String jdkImports)
    {
        m__strJDKImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(String jdkImports)
    {
        inmutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJDKImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    private void inmutableSetLoggingImports(String loggingImports)
    {
        m__strLoggingImports = loggingImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    protected void setLoggingImports(String loggingImports)
    {
        inmutableSetLoggingImports(loggingImports);
    }

    /**
     * Retrieves the logging imports.
     * @return such information.
     */
    public String getLoggingImports() 
    {
        return m__strLoggingImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void inmutableSetJavadoc(String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(String javadoc)
    {
        inmutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void inmutableSetClassDefinition(String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(String classDefinition)
    {
        inmutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void inmutableSetClassStart(String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(String classStart)
    {
        inmutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the properties keys.
     * @param propertiesKeys the new properties keys.
     */
    private void inmutableSetPropertiesKeys(String propertiesKeys)
    {
        m__strPropertiesKeys = propertiesKeys;
    }

    /**
     * Specifies the properties keys.
     * @param propertiesKeys the new properties keys.
     */
    protected void setPropertiesKeys(String propertiesKeys)
    {
        inmutableSetPropertiesKeys(propertiesKeys);
    }

    /**
     * Retrieves the properties keys.
     * @return such information.
     */
    public String getPropertiesKeys() 
    {
        return m__strPropertiesKeys;
    }

    /**
     * Specifies the properties reference.
     * @param propertiesReference the new properties reference.
     */
    private void inmutableSetPropertiesReference(String propertiesReference)
    {
        m__strPropertiesReference = propertiesReference;
    }

    /**
     * Specifies the properties reference.
     * @param propertiesReference the new properties reference.
     */
    protected void setPropertiesReference(String propertiesReference)
    {
        inmutableSetPropertiesReference(propertiesReference);
    }

    /**
     * Retrieves the properties reference.
     * @return such information.
     */
    public String getPropertiesReference() 
    {
        return m__strPropertiesReference;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    private void inmutableSetSingletonBody(String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
     */
    protected void setSingletonBody(String singletonBody)
    {
        inmutableSetSingletonBody(singletonBody);
    }

    /**
     * Retrieves the singleton body.
     * @return such information.
     */
    public String getSingletonBody() 
    {
        return m__strSingletonBody;
    }

    /**
     * Specifies the properties accessors.
     * @param propertiesAccessors the new properties accessors.
     */
    private void inmutableSetPropertiesAccessors(String propertiesAccessors)
    {
        m__strPropertiesAccessors = propertiesAccessors;
    }

    /**
     * Specifies the properties accessors.
     * @param propertiesAccessors the new properties accessors.
     */
    protected void setPropertiesAccessors(String propertiesAccessors)
    {
        inmutableSetPropertiesAccessors(propertiesAccessors);
    }

    /**
     * Retrieves the properties accessors.
     * @return such information.
     */
    public String getPropertiesAccessors() 
    {
        return m__strPropertiesAccessors;
    }

    /**
     * Specifies the helper methods.
     * @param helperMethods the new helper methods.
     */
    private void inmutableSetHelperMethods(String helperMethods)
    {
        m__strHelperMethods = helperMethods;
    }

    /**
     * Specifies the helper methods.
     * @param helperMethods the new helper methods.
     */
    protected void setHelperMethods(String helperMethods)
    {
        inmutableSetHelperMethods(helperMethods);
    }

    /**
     * Retrieves the helper methods.
     * @return such information.
     */
    public String getHelperMethods() 
    {
        return m__strHelperMethods;
    }

    /**
     * Specifies the getDAOFactory methods.
     * @param getDAOFactoryMethods the new getDAOFactory methods.
     */
    private void inmutableSetGetDAOFactoryMethods(String getDAOFactoryMethods)
    {
        m__strGetDAOFactoryMethods = getDAOFactoryMethods;
    }

    /**
     * Specifies the getDAOFactory methods.
     * @param getDAOFactoryMethods the new getDAOFactory methods.
     */
    protected void setGetDAOFactoryMethods(String getDAOFactoryMethods)
    {
        inmutableSetGetDAOFactoryMethods(getDAOFactoryMethods);
    }

    /**
     * Retrieves the getDAOFactory methods.
     * @return such information.
     */
    public String getGetDAOFactoryMethods() 
    {
        return m__strGetDAOFactoryMethods;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void inmutableSetClassEnd(String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(String classEnd)
    {
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    private void inmutableSetTables(List tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(List tables)
    {
        inmutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such collection.
     */
    public List getTables()
    {
        return m__lTables;
    }

    /**
     * Adds a new table.
     * @param table the new table.
     */
    public void addTable(String table)
    {
        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            t_lTables.add(table);
        }
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            String t_strRepository =
                t_StringUtils.normalize(getRepository(), '_');

            t_sbResult.append(getHeader());

            MessageFormat t_Formatter =
                new MessageFormat(getPackageDeclaration());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName()
                    }));

            t_sbResult.append(getJdkImports());
            t_sbResult.append(getLoggingImports());
            t_sbResult.append(getJavadoc());
            t_sbResult.append(getClassDefinition());


            t_sbResult.append(getClassStart());

            StringBuffer t_sbPropertiesKeys = new StringBuffer();
            MessageFormat t_PropertiesKeysFormatter =
                new MessageFormat(getPropertiesKeys());

            StringBuffer t_sbGetDAOFactoryMethods = new StringBuffer();
            MessageFormat t_GetDAOFactoryMethodFormatter =
                new MessageFormat(getGetDAOFactoryMethods());

            List t_lTables = getTables();

            if  (t_lTables != null)
            {
                Iterator t_itTables = t_lTables.iterator();

                while  (t_itTables.hasNext()) 
                {
                    String t_strTable = (String) t_itTables.next();

                    if  (t_strTable != null)
                    {
                        String t_strCapitalizedTable =
                            t_StringUtils.capitalize(
                                t_strTable,
                                '_');

                        String t_strUpperCaseTable =
                            (  ("" + getRepository()).toUpperCase()
                             + "_" + t_strCapitalizedTable).toUpperCase();

                        String t_strDottedTable =
                            (  ("" + getRepository()).toLowerCase()
                             + "." + t_strCapitalizedTable).toLowerCase();

                        t_sbPropertiesKeys.append(
                            t_PropertiesKeysFormatter.format(
                                new Object[]
                                {
                                    t_strUpperCaseTable,
                                    t_strDottedTable
                                }));

                        t_sbGetDAOFactoryMethods.append(
                            t_GetDAOFactoryMethodFormatter.format(
                                new Object[]
                                {
                                    t_strCapitalizedTable,
                                    t_strUpperCaseTable,
                                    t_StringUtils.capitalize(
                                        t_strTable.toLowerCase(),
                                        '_')
                                }));
                    }
                }
            }

            t_sbResult.append(t_sbPropertiesKeys);

            t_Formatter = new MessageFormat(getPropertiesReference());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        ("" + getRepository()).toLowerCase()
                    }));

            t_sbResult.append(getSingletonBody());

            t_sbResult.append(getPropertiesAccessors());

            t_sbResult.append(getHelperMethods());

            t_sbResult.append(t_sbGetDAOFactoryMethods);

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
