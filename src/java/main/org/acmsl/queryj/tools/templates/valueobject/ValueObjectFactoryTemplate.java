/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate value object factories according to
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
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
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
 * Is able to generate value object factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class ValueObjectFactoryTemplate
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
        + "    version 2.1 of the License, or (at your option) any later "
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
        + "                    Urb. Valdecabañas\n"
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
        + " * Description: Is able to create {0} value objects according to\n"
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
        + " * Is able to create {0} value objects according to\n"
        + " * information in the persistence domain.\n" // table
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
           "public abstract class {0}ValueObjectFactory\n"; // table

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
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected {0}ValueObjectFactory() '{' '}';\n\n" // table
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param table the table instance to use.\n"
        + "     */\n"
        + "    protected static void setReference({0}ValueObjectFactory factory)\n" // table
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
        + "     * Retrieves a {0}ValueObjectFactory instance.\n"
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
        + "            result = new {0}ValueObjectFactory() '{' '}';\n\n"
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
        + "        {0}ValueObject result = null;\n\n"
        + "        result =\n"
        + "            new {0}ValueObject("
        + "{3}) '{' '}';\n\n"  // factory method value object build.
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default factory method field Javadoc.
     */
    public static final String DEFAULT_FACTORY_METHOD_FIELD_JAVADOC =
        "     * @param {0} the {1} information.\n"; // field - FIELD;

    /**
     * The default factory method field declaration.
     */
    public static final String DEFAULT_FACTORY_METHOD_FIELD_DECLARATION =
        "\n        {0} {1}"; // field type - field;

    /**
     * The default factory method value object build.
     */
    public static final String DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD =
        "\n                {1}"; // Field - field;

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
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

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
     * The factory method field javadoc.
     */
    private String m__strFactoryMethodFieldJavadoc;

    /**
     * The factory method field definition.
     */
    private String m__strFactoryMethodFieldDefinition;

    /**
     * The factory method value object build.
     */
    private String m__strFactoryMethodValueObjectBuild;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a ValueObjectFactoryTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param factoryMethodFieldJavadoc the factory method field Javadoc.
     * @param factoryMethodFieldDefinition the constructor field definition.
     * @param factoryMethodValueObjectBuild the factory method value object build.
     * @param classEnd the class end.
     */
    public ValueObjectFactoryTemplate(
        String                  header,
        String                  packageDeclaration,
        String                  packageName,
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  projectImports,
        String                  jdkImports,
        String                  javadoc,
        String                  classDefinition,
        String                  classStart,
        String                  singletonBody,
        String                  factoryMethod,
        String                  factoryMethodFieldJavadoc,
        String                  factoryMethodFieldDefinition,
        String                  factoryMethodValueObjectBuild,
        String                  classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetTableTemplate(tableTemplate);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetProjectImports(projectImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetFactoryMethod(factoryMethod);
        inmutableSetFactoryMethodFieldJavadoc(factoryMethodFieldJavadoc);
        inmutableSetFactoryMethodFieldDefinition(factoryMethodFieldDefinition);
        inmutableSetFactoryMethodValueObjectBuild(factoryMethodValueObjectBuild);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a ValueObjectFactoryTemplate using given information.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     */
    public ValueObjectFactoryTemplate(
        String                  packageName,
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            tableTemplate,
            metaDataManager,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_FACTORY_METHOD_FIELD_JAVADOC,
            DEFAULT_FACTORY_METHOD_FIELD_DECLARATION,
            DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD,
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
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void inmutableSetProjectImports(String acmslImports)
    {
        m__strProjectImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setProjectImports(String acmslImports)
    {
        inmutableSetProjectImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
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
     * Specifies the factory method field Javadoc.
     * @param fieldJavadoc the new factory method field Javadoc.
     */
    private void inmutableSetFactoryMethodFieldJavadoc(String fieldJavadoc)
    {
        m__strFactoryMethodFieldJavadoc = fieldJavadoc;
    }

    /**
     * Specifies the factory method field Javadoc.
     * @param fieldJavadoc the new factory method field Javadoc.
     */
    protected void setFactoryMethodFieldJavadoc(String fieldJavadoc)
    {
        inmutableSetFactoryMethodFieldJavadoc(fieldJavadoc);
    }

    /**
     * Retrieves the factory method field Javadoc.
     * @return such information.
     */
    public String getFactoryMethodFieldJavadoc() 
    {
        return m__strFactoryMethodFieldJavadoc;
    }

    /**
     * Specifies the factory method field definition.
     * @param fieldDefinition the new factory method field definition.
     */
    private void inmutableSetFactoryMethodFieldDefinition(String fieldDefinition)
    {
        m__strFactoryMethodFieldDefinition = fieldDefinition;
    }

    /**
     * Specifies the factory method field definition.
     * @param fieldDefinition the new factory method field definition.
     */
    protected void setFactoryMethodFieldDefinition(String fieldDefinition)
    {
        inmutableSetFactoryMethodFieldDefinition(fieldDefinition);
    }

    /**
     * Retrieves the factory method field definition.
     * @return such information.
     */
    public String getFactoryMethodFieldDefinition() 
    {
        return m__strFactoryMethodFieldDefinition;
    }

    /**
     * Specifies the factory method value object build.
     * @param valueValueObjectBuild the new factory method value object build.
     */
    private void inmutableSetFactoryMethodValueObjectBuild(
        String valueObjectBuild)
    {
        m__strFactoryMethodValueObjectBuild = valueObjectBuild;
    }

    /**
     * Specifies the factory method value object build.
     * @param valueValueObjectBuild the new factory method value object build.
     */
    protected void setFactoryMethodValueObjectBuild(String valueObjectBuild)
    {
        inmutableSetFactoryMethodValueObjectBuild(valueObjectBuild);
    }

    /**
     * Retrieves the factory method value object build.
     * @return such information.
     */
    public String getFactoryMethodValueObjectBuild() 
    {
        return m__strFactoryMethodValueObjectBuild;
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

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_MetaDataManager != null))
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
                        getPackageName(),
                        t_StringUtils.capitalize(
                            t_TableTemplate.getTableName().toLowerCase(),
                            '_')
                    }));

            t_sbResult.append(getJdkImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_TableTemplate.getTableName().toLowerCase(),
                            '_')
                    }));

            t_sbResult.append(getClassStart());

            MessageFormat t_SingletonBodyFormatter =
                new MessageFormat(getSingletonBody());

            t_sbResult.append(
                t_SingletonBodyFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_TableTemplate.getTableName().toLowerCase(),
                            '_')
                    }));

            List t_lFields = t_TableTemplate.getFields();

            MessageFormat t_FactoryMethodFormatter =
                    new MessageFormat(getFactoryMethod());

            if  (t_lFields != null)
            {
                Iterator t_itFields = t_lFields.iterator();

                StringBuffer t_sbFactoryMethodFieldJavadoc = new StringBuffer();

                MessageFormat t_FactoryMethodFieldJavadocFormatter =
                    new MessageFormat(getFactoryMethodFieldJavadoc());

                StringBuffer t_sbFactoryMethodFieldDefinition = new StringBuffer();

                MessageFormat t_FactoryMethodFieldDefinitionFormatter =
                    new MessageFormat(getFactoryMethodFieldDefinition());

                StringBuffer t_sbFactoryMethodValueObjectBuild = new StringBuffer();

                MessageFormat t_FactoryMethodValueObjectBuildFormatter =
                    new MessageFormat(getFactoryMethodValueObjectBuild());

                while  (t_itFields.hasNext()) 
                {
                    String t_strField = (String) t_itFields.next();

                    String t_strFieldType =
                        t_MetaDataUtils.getNativeType(
                            t_MetaDataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_strField));

                    t_sbFactoryMethodFieldJavadoc.append(
                        t_FactoryMethodFieldJavadocFormatter.format(
                            new Object[]
                            {
                                t_strField.toLowerCase(),
                                t_strField
                            }));

                    t_sbFactoryMethodFieldDefinition.append(
                        t_FactoryMethodFieldDefinitionFormatter.format(
                            new Object[]
                            {
                                t_strFieldType,
                                t_strField.toLowerCase()
                            }));

                    t_sbFactoryMethodValueObjectBuild.append(
                        t_FactoryMethodValueObjectBuildFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                                t_strField.toLowerCase()
                            }));

                    if  (t_itFields.hasNext())
                    {
                        t_sbFactoryMethodFieldDefinition.append(",");
                        t_sbFactoryMethodValueObjectBuild.append(",");
                    }
                }

                t_sbResult.append(
                    t_FactoryMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_TableTemplate.getTableName().toLowerCase(),
                                    '_'),
                            t_sbFactoryMethodFieldJavadoc.toString(),
                            t_sbFactoryMethodFieldDefinition.toString(),
                            t_sbFactoryMethodValueObjectBuild.toString()
                        }));
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
