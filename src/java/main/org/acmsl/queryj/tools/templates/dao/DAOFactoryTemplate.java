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
 * Description: Is able to generate DAO factories according to+
 *              database metadata.
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
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class DAOFactoryTemplate
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
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected {0}{1}DAOFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param factory the new factory instance.\n"
        + "     */\n"
        + "    protected static void setReference({0}{1}DAOFactory factory)\n" // table
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
        + "            result = new {0}{1}DAOFactory() '{' '}';\n\n"
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
       + "        catch  (NamingException namingException)\n"
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
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The quote.
     */
    private String m__strQuote;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The datasource's JNDI location.
     */
    private String m__strJNDIDataSource;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The extension import statements.
     */
    private String m__strExtensionImports;

    /**
     * The commons-logging import statements.
     */
    private String m__strCommonsLoggingImports;

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
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The factory method.
     */
    private String m__strFactoryMethod;

    /**
     * The data source retrieval method.
     */
    private String m__strDataSourceRetrievalMethod;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a DAOFactoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param extensionImports the extension imports.
     * @param commonsLoggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param dataSourceRetrievalMethod the data source retrieval method.
     * @param classEnd the class end.
     */
    public DAOFactoryTemplate(
        String                  header,
        String                  packageDeclaration,
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  packageName,
        String                  engineName,
        String                  engineVersion,
        String                  quote,
        String                  basePackageName,
        String                  jndiDataSource,
        String                  projectImports,
        String                  jdkImports,
        String                  extensionImports,
        String                  commonsLoggingImports,
        String                  javadoc,
        String                  classDefinition,
        String                  classStart,
        String                  singletonBody,
        String                  factoryMethod,
        String                  dataSourceRetrievalMethod,
        String                  classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetTableTemplate(tableTemplate);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetPackageName(packageName);
        inmutableSetEngineName(engineName);
        inmutableSetEngineVersion(engineVersion);
        inmutableSetQuote(quote);
        inmutableSetBasePackageName(basePackageName);
        inmutableSetJNDIDataSource(jndiDataSource);
        inmutableSetProjectImports(projectImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetExtensionImports(extensionImports);
        inmutableSetCommonsLoggingImports(commonsLoggingImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetFactoryMethod(factoryMethod);
        inmutableSetDataSourceRetrievalMethod(dataSourceRetrievalMethod);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a DAOFactoryTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     */
    public DAOFactoryTemplate(
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  packageName,
        String                  engineName,
        String                  engineVersion,
        String                  quote,
        String                  basePackageName,
        String                  jndiDataSource)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            tableTemplate,
            metaDataManager,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            jndiDataSource,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_EXTENSION_IMPORTS,
            DEFAULT_COMMONS_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_DATA_SOURCE_RETRIEVAL_METHOD,
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
     * Specifies the table template.
     * @param tableTemplate the new table template.
     */
    private void inmutableSetTableTemplate(TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the new table template.
     */
    protected void setTableTemplate(TableTemplate tableTemplate)
    {
        inmutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such information.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the new metadata manager.
     */
    private void inmutableSetMetaDataManager(
        DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the new metadata manager.
     */
    protected void setMetaDataManager(
        DatabaseMetaDataManager metaDataManager)
    {
        inmutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such information.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
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
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void inmutableSetEngineName(String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(String engineName)
    {
        inmutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    public String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void inmutableSetEngineVersion(String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(String engineVersion)
    {
        inmutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    public String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    private void inmutableSetQuote(String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void setQuote(String quote)
    {
        inmutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    public String getQuote()
    {
        return m__strQuote;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void inmutableSetBasePackageName(String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(String basePackageName)
    {
        inmutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the JNDI data source.
     * @param jndiDataSource the new JNDI data source.
     */
    private void inmutableSetJNDIDataSource(String jndiDataSource)
    {
        m__strJNDIDataSource = jndiDataSource;
    }

    /**
     * Specifies the JNDI data source.
     * @param jndiDataSource the new JNDI data source.
     */
    protected void setJNDIDataSource(String jndiDataSource)
    {
        inmutableSetJNDIDataSource(jndiDataSource);
    }

    /**
     * Retrieves the JNDI data source.
     * @return such information.
     */
    public String getJNDIDataSource() 
    {
        return m__strJNDIDataSource;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void inmutableSetProjectImports(String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(String projectImports)
    {
        inmutableSetProjectImports(projectImports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void inmutableSetJdkImports(String jdkImports)
    {
        m__strJdkImports = jdkImports;
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
        return m__strJdkImports;
    }

    /**
     * Specifies the extension imports.
     * @param extensionImports the new extension imports.
     */
    private void inmutableSetExtensionImports(String extensionImports)
    {
        m__strExtensionImports = extensionImports;
    }

    /**
     * Specifies the extension imports.
     * @param extensionImports the new extension imports.
     */
    protected void setExtensionImports(String extensionImports)
    {
        inmutableSetExtensionImports(extensionImports);
    }

    /**
     * Retrieves the extension imports.
     * @return such information.
     */
    public String getExtensionImports() 
    {
        return m__strExtensionImports;
    }

    /**
     * Specifies the commons-logging imports.
     * @param commonsLoggingImports the new commons-logging imports.
     */
    private void inmutableSetCommonsLoggingImports(String commonsLoggingImports)
    {
        m__strCommonsLoggingImports = commonsLoggingImports;
    }

    /**
     * Specifies the commons-logging imports.
     * @param commonsLoggingImports the new commons-logging imports.
     */
    protected void setCommonsLoggingImports(String commonsLoggingImports)
    {
        inmutableSetCommonsLoggingImports(commonsLoggingImports);
    }

    /**
     * Retrieves the commons-logging imports.
     * @return such information.
     */
    public String getCommonsLoggingImports() 
    {
        return m__strCommonsLoggingImports;
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
     * Specifies the singleton body.
     * @param singletonBody the singleton body.
     */
    private void inmutableSetSingletonBody(String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the singleton body.
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
     * Specifies the factory method.
     * @param factoryMethod such source code.
     */
    private void inmutableSetFactoryMethod(String factoryMethod)
    {
        m__strFactoryMethod = factoryMethod;
    }

    /**
     * Specifies the factory method.
     * @param factoryMethod such source code.
     */
    protected void setFactoryMethod(String factoryMethod)
    {
        inmutableSetFactoryMethod(factoryMethod);
    }

    /**
     * Retrieves the factory method.
     * @return such source code.
     */
    public String getFactoryMethod()
    {
        return m__strFactoryMethod;
    }

    /**
     * Specifies the data source retrieval method.
     * @param dataSourceRetrievalMethod such source code.
     */
    private void inmutableSetDataSourceRetrievalMethod(String dataSourceRetrievalMethod)
    {
        m__strDataSourceRetrievalMethod = dataSourceRetrievalMethod;
    }

    /**
     * Specifies the data source retrieval method.
     * @param dataSourceRetrievalMethod such source code.
     */
    protected void setDataSourceRetrievalMethod(String dataSourceRetrievalMethod)
    {
        inmutableSetDataSourceRetrievalMethod(dataSourceRetrievalMethod);
    }

    /**
     * Retrieves the data source retrieval method.
     * @return such source code.
     */
    public String getDataSourceRetrievalMethod()
    {
        return m__strDataSourceRetrievalMethod;
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
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_MetaDataManager != null)
             && (t_PackageUtils    != null))
        {
            MessageFormat t_Formatter = new MessageFormat(getHeader());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getPackageDeclaration());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName()
                    }));

            t_Formatter = new MessageFormat(getProjectImports());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_PackageUtils.retrieveBaseDAOPackage(
                            getBasePackageName()),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_'),
                        t_PackageUtils.retrieveBaseDAOFactoryPackage(
                            getBasePackageName()),
                        t_PackageUtils.retrieveDAOPackage(
                            getBasePackageName(),
                            getEngineName()),
                        getEngineName()
                    }));

            t_sbResult.append(getJdkImports());
            t_sbResult.append(getExtensionImports());
            t_sbResult.append(getCommonsLoggingImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getEngineName(),
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getEngineName(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            t_Formatter = new MessageFormat(getClassStart());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getJNDIDataSource()
                    }));


            MessageFormat t_SingletonBodyFormatter =
                new MessageFormat(getSingletonBody());

            t_sbResult.append(
                t_SingletonBodyFormatter.format(
                    new Object[]
                    {
                        getEngineName(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            List t_lFields = t_TableTemplate.getFields();

            MessageFormat t_FactoryMethodFormatter =
                    new MessageFormat(getFactoryMethod());

            t_sbResult.append(
                t_FactoryMethodFormatter.format(
                    new Object[]
                    {
                        getEngineName(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_'),
                    }));

            t_sbResult.append(getDataSourceRetrievalMethod());

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
