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
 * Description: Is able to generate value objects according to database
 *              metadata.
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
 * Is able to generate value objects according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class ValueObjectTemplate
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
        + " * Description: Represents the {0} information stored in the\n"
        + " *              persistence domain.\n"
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
    public static final String ACMSL_IMPORTS =
          "/*\n"
        + " * Importing some ACM-SL classes.\n"
        + " */\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.math.BigDecimal;\n"
        + "import java.util.Calendar;\n"
        + "import java.util.Date;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Represents the {0} information in the persistence domain.\n" // table
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
           "public abstract class {0}ValueObject\n"; // table

    /**
     * The class start.
     */
    public static final String DEFAULT_CLASS_START = "{\n";

    /**
     * The field declaration.
     */
    public static final String DEFAULT_FIELD_DECLARATION =
          "    /**\n"
        + "     * The {0} information.\n" // field
        + "     */\n"
        + "    private {1} {2};\n\n"; // field type - field

    /**
     * The class constructor.
     */
    public static final String DEFAULT_CONSTRUCTOR =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "{1}" // constructor field javadoc
        + "     */\n"
        + "    protected {0}ValueObject(" // table
        + "{2})\n" // constructor field declaration
        + "    '{'\n"
        + "{3}\n"  // constructor field value setter.
        + "    '}'\n\n";

    /**
     * The default constructor field Javadoc.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_JAVADOC =
        "     * @param {0} the {1} information.\n"; // field - FIELD;

    /**
     * The default constructor field declaration.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_DECLARATION =
        "\n        {0} {1}"; // field type - field;

    /**
     * The default constructor field value setter.
     */
    public static final String DEFAULT_CONSTRUCTOR_FIELD_VALUE_SETTER =
        "\n        inmutableSet{0}({1});"; // Field - field;

    /**
     * The default field setter method.
     */
    public static final String DEFAULT_FIELD_VALUE_SETTER_METHOD =
          "    /**\n"
        + "     * Specifies the {0} information.\n" // field
        + "     * @param {0} the new {0} value.\n"
        + "     */\n"
        + "    private void inmutableSet{2}({1} {0})\n" // capitalized field - field type
        + "    '{'\n"
        + "        this.{0} = {0};\n" // field
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies the {0} information.\n" // field
        + "     * @param {0} the new {0} value.\n"
        + "     */\n"
        + "    protected void set{2}({1} {0})\n" // capitalized field - field type
        + "    '{'\n"
        + "        inmutableSet{2}({0});\n" // field
        + "    '}'\n\n";

    /**
     * The default field getter method.
     */
    public static final String DEFAULT_FIELD_VALUE_GETTER_METHOD =
          "    /**\n"
        + "     * Retrieves the {0} information.\n" // field
        + "     * @return such value.\n"
        + "     */\n"
        + "    public {1} get{2}()\n" // field type - capitalized field
        + "    '{'\n"
        + "        return {0};\n" // field
        + "    '}'\n";

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
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

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
     * The field declaration.
     */
    private String m__strFieldDeclaration;

    /**
     * The constructor.
     */
    private String m__strConstructor;

    /**
     * The constructor field javadoc.
     */
    private String m__strConstructorFieldJavadoc;

    /**
     * The constructor field definition.
     */
    private String m__strConstructorFieldDefinition;

    /**
     * The constructor field value setter.
     */
    private String m__strConstructorFieldValueSetter;

    /**
     * The field value setter method.
     */
    private String m__strFieldValueSetterMethod;

    /**
     * The field value getter method.
     */
    private String m__strFieldValueGetterMethod;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a ValueObjectTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param fieldDeclaration the field declaration.
     * @param constructor the constructor.
     * @param constructorFieldJavadoc the constructor field Javadoc.
     * @param constructorFieldDefinition the constructor field definition.
     * @param constructorFieldValueSetter the constructor field value setter.
     * @param fieldValueSetterMethod the field value setter method.
     * @param fieldValueGetterMethod the field value getter method.
     * @param classEnd the class end.
     */
    public ValueObjectTemplate(
        String                  header,
        String                  packageDeclaration,
        String                  packageName,
        TableTemplate           tableTemplate,
        DatabaseMetaDataManager metaDataManager,
        String                  acmslImports,
        String                  jdkImports,
        String                  javadoc,
        String                  classDefinition,
        String                  classStart,
        String                  fieldDeclaration,
        String                  constructor,
        String                  constructorFieldJavadoc,
        String                  constructorFieldDefinition,
        String                  constructorFieldValueSetter,
        String                  fieldValueSetterMethod,
        String                  fieldValueGetterMethod,
        String                  classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetTableTemplate(tableTemplate);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetMetaDataManager(metaDataManager);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetFieldDeclaration(fieldDeclaration);
        inmutableSetConstructor(constructor);
        inmutableSetConstructorFieldJavadoc(constructorFieldJavadoc);
        inmutableSetConstructorFieldDefinition(constructorFieldDefinition);
        inmutableSetConstructorFieldValueSetter(constructorFieldValueSetter);
        inmutableSetFieldValueSetterMethod(fieldValueSetterMethod);
        inmutableSetFieldValueGetterMethod(fieldValueGetterMethod);
        inmutableSetClassEnd(classEnd);
    }

    /**
     * Builds a ValueObjectTemplate using given information.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     */
    public ValueObjectTemplate(
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
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_FIELD_DECLARATION,
            DEFAULT_CONSTRUCTOR,
            DEFAULT_CONSTRUCTOR_FIELD_JAVADOC,
            DEFAULT_CONSTRUCTOR_FIELD_DECLARATION,
            DEFAULT_CONSTRUCTOR_FIELD_VALUE_SETTER,
            DEFAULT_FIELD_VALUE_SETTER_METHOD,
            DEFAULT_FIELD_VALUE_GETTER_METHOD,
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
    private void inmutableSetAcmslImports(String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(String acmslImports)
    {
        inmutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
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
     * Specifies the field declaration.
     * @param fieldDeclaration the new field declaration.
     */
    private void inmutableSetFieldDeclaration(String fieldDeclaration)
    {
        m__strFieldDeclaration = fieldDeclaration;
    }

    /**
     * Specifies the field declaration.
     * @param fieldDeclaration the new field declaration.
     */
    protected void setFieldDeclaration(String fieldDeclaration)
    {
        inmutableSetFieldDeclaration(fieldDeclaration);
    }

    /**
     * Retrieves the field declaration.
     * @return such information.
     */
    public String getFieldDeclaration()
    {
        return m__strFieldDeclaration;
    }


    /**
     * Specifies the class constructor.
     * @param constructor such source code.
     */
    private void inmutableSetConstructor(String constructor)
    {
        m__strConstructor = constructor;
    }

    /**
     * Specifies the class constructor.
     * @param constructor such source code.
     */
    protected void setConstructor(String constructor)
    {
        inmutableSetConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    public String getConstructor()
    {
        return m__strConstructor;
    }

    /**
     * Specifies the constructor field Javadoc.
     * @param fieldJavadoc the new constructor field Javadoc.
     */
    private void inmutableSetConstructorFieldJavadoc(String fieldJavadoc)
    {
        m__strConstructorFieldJavadoc = fieldJavadoc;
    }

    /**
     * Specifies the constructor field Javadoc.
     * @param fieldJavadoc the new constructor field Javadoc.
     */
    protected void setConstructorFieldJavadoc(String fieldJavadoc)
    {
        inmutableSetConstructorFieldJavadoc(fieldJavadoc);
    }

    /**
     * Retrieves the constructor field Javadoc.
     * @return such information.
     */
    public String getConstructorFieldJavadoc() 
    {
        return m__strConstructorFieldJavadoc;
    }

    /**
     * Specifies the constructor field definition.
     * @param fieldDefinition the new constructor field definition.
     */
    private void inmutableSetConstructorFieldDefinition(String fieldDefinition)
    {
        m__strConstructorFieldDefinition = fieldDefinition;
    }

    /**
     * Specifies the constructor field definition.
     * @param fieldDefinition the new constructor field definition.
     */
    protected void setConstructorFieldDefinition(String fieldDefinition)
    {
        inmutableSetConstructorFieldDefinition(fieldDefinition);
    }

    /**
     * Retrieves the constructor field definition.
     * @return such information.
     */
    public String getConstructorFieldDefinition() 
    {
        return m__strConstructorFieldDefinition;
    }

    /**
     * Specifies the constructor field value setter.
     * @param fieldValueSetter the new constructor field value setter.
     */
    private void inmutableSetConstructorFieldValueSetter(String fieldValueSetter)
    {
        m__strConstructorFieldValueSetter = fieldValueSetter;
    }

    /**
     * Specifies the constructor field value setter.
     * @param fieldValueSetter the new constructor field value setter.
     */
    protected void setConstructorFieldValueSetter(String fieldValueSetter)
    {
        inmutableSetConstructorFieldValueSetter(fieldValueSetter);
    }

    /**
     * Retrieves the constructor field value setter.
     * @return such information.
     */
    public String getConstructorFieldValueSetter() 
    {
        return m__strConstructorFieldValueSetter;
    }

    /**
     * Specifies the field value setter method.
     * @param method such method.
     */
    private void inmutableSetFieldValueSetterMethod(String method)
    {
        m__strFieldValueSetterMethod = method;
    }

    /**
     * Specifies the field value setter method.
     * @param method such method.
     */
    protected void setFieldValueSetterMethod(String method)
    {
        inmutableSetFieldValueSetterMethod(method);
    }

    /**
     * Retrieves the field value setter method.
     * @return such method.
     */
    public String getFieldValueSetterMethod()
    {
        return m__strFieldValueSetterMethod;
    }

    /**
     * Specifies the field value getter method.
     * @param method such method.
     */
    private void inmutableSetFieldValueGetterMethod(String method)
    {
        m__strFieldValueGetterMethod = method;
    }

    /**
     * Specifies the field value getter method.
     * @param method such method.
     */
    protected void setFieldValueGetterMethod(String method)
    {
        inmutableSetFieldValueGetterMethod(method);
    }

    /**
     * Retrieves the field value getter method.
     * @return such method.
     */
    public String getFieldValueGetterMethod()
    {
        return m__strFieldValueGetterMethod;
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

            //t_sbResult.append(getAcmslImports());
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

            List t_lFields = t_TableTemplate.getFields();

            MessageFormat t_ConstructorFormatter =
                    new MessageFormat(getConstructor());

            if  (t_lFields != null)
            {
                Iterator t_itFields = t_lFields.iterator();

                MessageFormat t_DeclarationFormatter =
                    new MessageFormat(getFieldDeclaration());

                StringBuffer t_sbConstructorFieldJavadoc = new StringBuffer();

                MessageFormat t_ConstructorFieldJavadocFormatter =
                    new MessageFormat(getConstructorFieldJavadoc());

                StringBuffer t_sbConstructorFieldDefinition = new StringBuffer();

                MessageFormat t_ConstructorFieldDefinitionFormatter =
                    new MessageFormat(getConstructorFieldDefinition());

                StringBuffer t_sbConstructorFieldValueSetter = new StringBuffer();

                MessageFormat t_ConstructorFieldValueSetterFormatter =
                    new MessageFormat(getConstructorFieldValueSetter());

                StringBuffer t_sbFieldValueSetterMethod = new StringBuffer();

                MessageFormat t_FieldValueSetterMethodFormatter =
                    new MessageFormat(getFieldValueSetterMethod());

                StringBuffer t_sbFieldValueGetterMethod = new StringBuffer();

                MessageFormat t_FieldValueGetterMethodFormatter =
                    new MessageFormat(getFieldValueGetterMethod());

                while  (t_itFields.hasNext()) 
                {
                    String t_strField = (String) t_itFields.next();

                    String t_strFieldType =
                        t_MetaDataUtils.getNativeType(
                            t_MetaDataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_strField));

                    t_sbResult.append(
                        t_DeclarationFormatter.format(
                            new Object[]
                            {
                                t_strField,
                                t_MetaDataUtils.getNativeType(
                                    t_MetaDataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_strField)),
                                t_strField.toLowerCase()
                            }));

                    t_sbConstructorFieldJavadoc.append(
                        t_ConstructorFieldJavadocFormatter.format(
                            new Object[]
                            {
                                t_strField.toLowerCase(),
                                t_strField
                            }));

                    t_sbConstructorFieldDefinition.append(
                        t_ConstructorFieldDefinitionFormatter.format(
                            new Object[]
                            {
                                t_strFieldType,
                                t_strField.toLowerCase()
                            }));

                    if  (t_itFields.hasNext())
                    {
                        t_sbConstructorFieldDefinition.append(",");
                    }
                    
                    t_sbConstructorFieldValueSetter.append(
                        t_ConstructorFieldValueSetterFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                                t_strField.toLowerCase()
                            }));

                    t_sbFieldValueSetterMethod.append(
                        t_FieldValueSetterMethodFormatter.format(
                            new Object[]
                            {
                                t_strField.toLowerCase(),
                                t_strFieldType,
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                            }));

                    t_sbFieldValueGetterMethod.append(
                        t_FieldValueGetterMethodFormatter.format(
                            new Object[]
                            {
                                t_strField.toLowerCase(),
                                t_strFieldType,
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                            }));
                }

                t_sbResult.append(
                    t_ConstructorFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_TableTemplate.getTableName().toLowerCase(),
                                    '_'),
                            t_sbConstructorFieldJavadoc.toString(),
                            t_sbConstructorFieldDefinition.toString(),
                            t_sbConstructorFieldValueSetter.toString()
                        }));

                t_sbResult.append(t_sbFieldValueSetterMethod);
                t_sbResult.append(t_sbFieldValueGetterMethod);
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
