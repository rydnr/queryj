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
 * Description: Is able to generate XML DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
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
 * Is able to generate XML DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class XMLDAOFactoryTemplate
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
        + " * @version $" + "Revision: $\n"
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
        + "            LogFactory.getLog(getClass()).warn(\n"
        + "                  \"Could not create temporary file.\"\n"
        + "                + \"Serialization disabled.\",\n"
        + "                ioException);\n"
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
        + "                LogFactory.getLog(getClass()).error(\n"
        + "                    \"no {0} information found at \" + inputFilePath,\n"
        + "                    fileNotFoundException);\n"
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
        + "                LogFactory.getLog(getClass()).error(\n"
        + "                    \"No {0} information can be written to \" + outputFilePath\n"
        + "                    + \". Disabling persistent serialization.\",\n"
        + "                    fileNotFoundException);\n"
        + "            '}'\n"
        + "            result = new XML{0}DAO(t_isInput, t_osOutput) '{' '}';\n" // table
        + "        '}'\n\n"
        + "        return result;\n"
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
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

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
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a XMLDAOFactoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param commonsLoggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param classEnd the class end.
     */
    public XMLDAOFactoryTemplate(
        final String                  header,
        final String                  packageDeclaration,
        final TableTemplate           tableTemplate,
        final String                  packageName,
        final String                  basePackageName,
        final String                  projectImports,
        final String                  jdkImports,
        final String                  commonsLoggingImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  singletonBody,
        final String                  factoryMethod,
        final String                  classEnd)
    {
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetTableTemplate(tableTemplate);
        immutableSetPackageName(packageName);
        immutableSetBasePackageName(basePackageName);
        immutableSetProjectImports(projectImports);
        immutableSetJdkImports(jdkImports);
        immutableSetCommonsLoggingImports(commonsLoggingImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetSingletonBody(singletonBody);
        immutableSetFactoryMethod(factoryMethod);
        immutableSetClassEnd(classEnd);
    }

    /**
     * Builds a XMLDAOFactoryTemplate using given information.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     */
    public XMLDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String        packageName,
        final String        basePackageName)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            tableTemplate,
            packageName,
            basePackageName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_COMMONS_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_CLASS_END);
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
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
    private void immutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        immutableSetPackageDeclaration(packageDeclaration);
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
    private void immutableSetTableTemplate(TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the new table template.
     */
    protected void setTableTemplate(TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
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
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
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
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
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
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void immutableSetProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(final String projectImports)
    {
        immutableSetProjectImports(projectImports);
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
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        immutableSetJdkImports(jdkImports);
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
     * Specifies the commons-logging imports.
     * @param commonsLoggingImports the new commons-logging imports.
     */
    private void immutableSetCommonsLoggingImports(final String commonsLoggingImports)
    {
        m__strCommonsLoggingImports = commonsLoggingImports;
    }

    /**
     * Specifies the commons-logging imports.
     * @param commonsLoggingImports the new commons-logging imports.
     */
    protected void setCommonsLoggingImports(final String commonsLoggingImports)
    {
        immutableSetCommonsLoggingImports(commonsLoggingImports);
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
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        immutableSetJavadoc(javadoc);
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
    private void immutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        immutableSetClassDefinition(classDefinition);
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
    private void immutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        immutableSetClassStart(classStart);
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
    private void immutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the singleton body.
     */
    protected void setSingletonBody(final String singletonBody)
    {
        immutableSetSingletonBody(singletonBody);
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
    private void immutableSetFactoryMethod(final String factoryMethod)
    {
        m__strFactoryMethod = factoryMethod;
    }

    /**
     * Specifies the factory method.
     * @param factoryMethod such source code.
     */
    protected void setFactoryMethod(final String factoryMethod)
    {
        immutableSetFactoryMethod(factoryMethod);
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
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void immutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        immutableSetClassEnd(classEnd);
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

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_PackageUtils    != null))
        {
            Object[] t_aCapitalizedTableName =
                new Object[]
                {
                    t_StringUtils.capitalize(
                        t_EnglishGrammarUtils.getSingular(
                            t_TableTemplate.getTableName().toLowerCase()),
                        '_'),
                };

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
                        t_aCapitalizedTableName[0],
                        t_PackageUtils.retrieveBaseDAOFactoryPackage(
                            getBasePackageName()),
                        t_PackageUtils.retrieveXMLDAOPackage(
                            getBasePackageName())
                    }));

            t_sbResult.append(getJdkImports());
            t_sbResult.append(getCommonsLoggingImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(t_Formatter.format(t_aCapitalizedTableName));

            t_Formatter = new MessageFormat(getClassStart());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName().toLowerCase()
                    }));

            t_Formatter = new MessageFormat(getSingletonBody());

            t_sbResult.append(t_Formatter.format(t_aCapitalizedTableName));

            t_Formatter = new MessageFormat(getFactoryMethod());

            t_sbResult.append(t_Formatter.format(t_aCapitalizedTableName));

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
