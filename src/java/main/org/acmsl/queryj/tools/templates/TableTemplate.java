/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
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
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Is able to generate tables according to database
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
package org.acmsl.queryj.tools.templates;

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
 * Is able to generate tables according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class TableTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002  Jose San Leandro Armendáriz\n"
        + "                        jsanleandro@yahoo.es\n"
        + "                        chousz@yahoo.com\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or (at your option) any later "
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
        + " * Description: Represents the {0} table in the persistence domain.\n"
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
        + " */\n"
        + "import org.acmsl.queryj.BigDecimalField;\n"
        + "import org.acmsl.queryj.CalendarField;\n"
        + "import org.acmsl.queryj.DateField;\n"
        + "import org.acmsl.queryj.DoubleField;\n"
        + "import org.acmsl.queryj.Field;\n"
        + "import org.acmsl.queryj.IntField;\n"
        + "import org.acmsl.queryj.LongField;\n"
        + "import org.acmsl.queryj.StringField;\n"
        + "import org.acmsl.queryj.Table;\n\n";

    /**
     * The JDK imports.
     */
    public static final String JDK_IMPORTS =
          "/*\n"
        + " * Importing some JDK classes.\n"
        + " */\n"
        + "import java.lang.ref.WeakReference;\n\n";

    /**
     * The default class Javadoc.
     */
    public static final String DEFAULT_JAVADOC =
          "/**\n"
        + " * Represents the {0} table in the persistence domain.\n" // table
        + " * @author <a href=\"http://queryj.sourceforge.net\">QueryJ</a>\n"
        + " * @version $" + "Revision: $\n"
        + " */\n\n";

    /**
     * The class definition.
     */
    public static final String CLASS_DEFINITION =
           "public abstract class {0}Table\n" // table
         + "    extends  Table\n";

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
    public static final String SINGLETON_BODY =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     * @param alias the table alias.\n"
        + "     */\n"
        + "    protected {0}Table(String alias)\n" // table
        + "    '{'\n"
        + "        super(\"{1}\", alias);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected {0}Table()\n" // table
        + "    '{'\n"
        + "        this(null);\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Specifies a new weak reference.\n"
        + "     * @param table the table instance to use.\n"
        + "     */\n"
        + "    protected static void setReference({0}Table table)\n" // table
        + "    '{'\n"
        + "        singleton = new WeakReference(table);\n"
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
        + "     * Retrieves a {0}Table instance.\n"
        + "     * @param alias the desired table alias.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Table getInstance(String alias)\n"
        + "    '{'\n"
        + "        {0}Table result = null;\n\n"
        + "        if  (alias != null)\n"
        + "        '{'\n"
        + "            result = new {0}Table(alias) '{' '}';\n"
        + "        '}'\n"
        + "        else\n"
        + "        '{'\n"
        + "            result = getInstance();\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n"
        + "    /**\n"
        + "     * Retrieves a {0}Table instance.\n"
        + "     * @return such instance.\n"
        + "     */\n"
        + "    public static {0}Table getInstance()\n"
        + "    '{'\n"
        + "        {0}Table result = null;\n\n"
        + "        WeakReference reference = getReference();\n\n"
        + "        if  (reference != null) \n"
        + "        '{'\n"
        + "            result = ({0}Table) reference.get();\n"
        + "        '}'\n\n"
        + "        if  (result == null) \n"
        + "        '{'\n"
        + "            result = new {0}Table() '{' '}';\n\n"
        + "            setReference(result);\n"
        + "        '}'\n\n"
        + "        return result;\n"
        + "    '}'\n\n";

    /**
     * The default field Javadoc.
     */
    public static final String DEFAULT_FIELD_JAVADOC =
          "    /**\n"
        + "     * The {0}''s table {1} field.\n" // table - field
        + "     */\n";

    /**
     * The field definition.
     */
    public static final String FIELD_DEFINITION =
          "    public {0} {1} =\n"
        + "        new {0}(\"{2}\", this) '{'}';\n\n";

    /**
     * The class constructor.
     */
    public static final String CLASS_CONSTRUCTOR =
          "    /**\n"
        + "     * Protected constructor to avoid accidental instantiation.\n"
        + "     */\n"
        + "    protected {0}Table()\n"
        + "    '{'\n"
        + "        super(NAME);\n"
        + "    '}'\n\n";

    /**
     * The getTableName method.
     */
    public static final String GETTABLENAME_METHOD =
          "    /**\n"
        + "     * Retrieves the table name.\n"
        + "     * @return such name.\n"
        + "     */\n"
        + "    public String getTableName()\n"
        + "    '{'\n"
        + "        return \"{0}\";\n" // table
        + "    '}'\n\n";

    /**
     * The empty getAll method.
     */
    public static final String EMPTY_GETALL_METHOD =
          "    /**\n"
        + "     * Retrieves <code>all</code> fields. "
        + "It's equivalent to a star in a query.\n"
        + "     * @return such fields.\n"
        + "     */\n"
        + "    public Field[] getAll()\n"
        + "    {\n"
        + "        return new Field[0];\n"
        + "    }\n";

    /**
     * The getAll method start.
     */
    public static final String GETALL_METHOD_START =
          "    /**\n"
        + "     * Retrieves <code>all</code> fields. "
        + "It's equivalent to a star in a query.\n"
        + "     * @return such fields.\n"
        + "     */\n"
        + "    public Field[] getAll()\n"
        + "    {\n"
        + "        return\n"
        + "            new Field[]\n"
        + "            {\n"
        + "                ";

    /**
     * The "getAll" method field separator.
     */
    public static final String GETALL_METHOD_FIELD_SEPARATOR =
        ",\n                ";

    /**
     * The "getAll" method end.
     */
    public static final String GETALL_METHOD_END =
          "\n"
        + "            };\n"
        + "    }\n";

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
     * The table name.
     */
    private String m__strTableName;

    /**
     * The field list.
     */
    private List m__lFields;

    /**
     * The field types.
     */
    private Map m__mFieldTypes;

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
     * The singleton body.
     */
    private String m__strSingletonBody;

    /**
     * The field javadoc.
     */
    private String m__strFieldJavadoc;

    /**
     * The field definition.
     */
    private String m__strFieldDefinition;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The "getTableName" method.
     */
    private String m__strGetTableNameMethod;

    /**
     * The empty "getAll" method.
     */
    private String m__strEmptyGetAllMethod;

    /**
     * The "getAll" method start.
     */
    private String m__strGetAllMethodStart;

    /**
     * The "getAll" method separator.
     */
    private String m__strGetAllMethodFieldSeparator;

    /**
     * The "getAll" end.
     */
    private String m__strGetAllMethodEnd;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a TableTemplate using given information.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param tableName the table name.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param fieldJavadoc the field Javadoc.
     * @param fieldDefinition the field definition.
     * @param classConstructor the class constructor.
     * @param getTableNameMethod the "getTableName" method.
     * @param emptyGetAllMethod the empty "getAll" method.
     * @param getAllMethodStart the "getAll" method start.
     * @param getAllMethodFieldSeparator the "getAll" method field
     * separator.
     * @param getAllMethodEnd the "getAll" method end.
     * @param classEnd the class end.
     */
    public TableTemplate(
        String header,
        String packageDeclaration,
        String packageName,
        String tableName,
        String acmslImports,
        String jdkImports,
        String javadoc,
        String classDefinition,
        String classStart,
        String singletonBody,
        String fieldJavadoc,
        String fieldDefinition,
        String classConstructor,
        String getTableNameMethod,
        String emptyGetAllMethod,
        String getAllMethodStart,
        String getAllMethodFieldSeparator,
        String getAllMethodEnd,
        String classEnd)
    {
        inmutableSetHeader(header);
        inmutableSetPackageDeclaration(packageDeclaration);
        inmutableSetPackageName(packageName);
        inmutableSetTableName(tableName);
        inmutableSetAcmslImports(acmslImports);
        inmutableSetJdkImports(jdkImports);
        inmutableSetJavadoc(javadoc);
        inmutableSetClassDefinition(classDefinition);
        inmutableSetClassStart(classStart);
        inmutableSetSingletonBody(singletonBody);
        inmutableSetFieldJavadoc(fieldJavadoc);
        inmutableSetFieldDefinition(fieldDefinition);
        inmutableSetClassConstructor(classConstructor);
        inmutableSetGetTableNameMethod(getTableNameMethod);
        inmutableSetEmptyGetAllMethod(emptyGetAllMethod);
        inmutableSetGetAllMethodStart(getAllMethodStart);
        inmutableSetGetAllMethodFieldSeparator(getAllMethodFieldSeparator);
        inmutableSetGetAllMethodEnd(getAllMethodEnd);
        inmutableSetClassEnd(classEnd);
        inmutableSetFields(new ArrayList());
        inmutableSetFieldTypes(new HashMap());
    }

    /**
     * Builds a TableTemplate using given information.
     * @param packageName the package name.
     * @param tableName the table name.
     */
    public TableTemplate(
        String packageName,
        String tableName)
    {
        this(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            tableName,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            SINGLETON_BODY,
            DEFAULT_FIELD_JAVADOC,
            FIELD_DEFINITION,
            CLASS_CONSTRUCTOR,
            GETTABLENAME_METHOD,
            EMPTY_GETALL_METHOD,
            GETALL_METHOD_START,
            GETALL_METHOD_FIELD_SEPARATOR,
            GETALL_METHOD_END,
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
     * Specifies the table name.
     * @param tableName the new table name.
     */
    private void inmutableSetTableName(String tableName)
    {
        m__strTableName = tableName;
    }

    /**
     * Specifies the table name.
     * @param tableName the new table name.
     */
    protected void setTableName(String tableName)
    {
        inmutableSetTableName(tableName);
    }

    /**
     * Retrieves the table name.
     * @return such information.
     */
    public String getTableName() 
    {
        return m__strTableName;
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
     * Specifies the field Javadoc.
     * @param fieldJavadoc the new field Javadoc.
     */
    private void inmutableSetFieldJavadoc(String fieldJavadoc)
    {
        m__strFieldJavadoc = fieldJavadoc;
    }

    /**
     * Specifies the field Javadoc.
     * @param fieldJavadoc the new field Javadoc.
     */
    protected void setFieldJavadoc(String fieldJavadoc)
    {
        inmutableSetFieldJavadoc(fieldJavadoc);
    }

    /**
     * Retrieves the field Javadoc.
     * @return such information.
     */
    public String getFieldJavadoc() 
    {
        return m__strFieldJavadoc;
    }

    /**
     * Specifies the field definition.
     * @param fieldDefinition the new field definition.
     */
    private void inmutableSetFieldDefinition(String fieldDefinition)
    {
        m__strFieldDefinition = fieldDefinition;
    }

    /**
     * Specifies the field definition.
     * @param fieldDefinition the new field definition.
     */
    protected void setFieldDefinition(String fieldDefinition)
    {
        inmutableSetFieldDefinition(fieldDefinition);
    }

    /**
     * Retrieves the field definition.
     * @return such information.
     */
    public String getFieldDefinition() 
    {
        return m__strFieldDefinition;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void inmutableSetClassConstructor(String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(String constructor)
    {
        inmutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code..
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the "getTableName" method.
     * @param method such method.
     */
    private void inmutableSetGetTableNameMethod(String method)
    {
        m__strGetTableNameMethod = method;
    }

    /**
     * Specifies the "getTableName" method.
     * @param method such method.
     */
    protected void setGetTableNameMethod(String method)
    {
        inmutableSetGetTableNameMethod(method);
    }

    /**
     * Retrieves the "getTableName" method.
     * @return such method.
     */
    public String getGetTableNameMethod()
    {
        return m__strGetTableNameMethod;
    }

    /**
     * Specifies the empty "getAll" method.
     * @param method such method.
     */
    private void inmutableSetEmptyGetAllMethod(String method)
    {
        m__strEmptyGetAllMethod = method;
    }

    /**
     * Specifies the empty "getAll" method.
     * @param method such method.
     */
    protected void setEmptyGetAllMethod(String method)
    {
        inmutableSetEmptyGetAllMethod(method);
    }

    /**
     * Retrieves the empty "getAll" method.
     * @return such method.
     */
    public String getEmptyGetAllMethod()
    {
        return m__strEmptyGetAllMethod;
    }

    /**
     * Specifies the "getAll" method start.
     * @param start such start.
     */
    private void inmutableSetGetAllMethodStart(String start)
    {
        m__strGetAllMethodStart = start;
    }

    /**
     * Specifies the "getAll" method start.
     * @param start such start.
     */
    protected void setGetAllMethodStart(String start)
    {
        inmutableSetGetAllMethodStart(start);
    }

    /**
     * Retrieves the "getAll" method start.
     * @return such start.
     */
    public String getGetAllMethodStart()
    {
        return m__strGetAllMethodStart;
    }

    /**
     * Specifies the "getAll" method field separator.
     * @param separator such separator.
     */
    private void inmutableSetGetAllMethodFieldSeparator(
        String separator)
    {
        m__strGetAllMethodFieldSeparator = separator;
    }

    /**
     * Specifies the "getAll" method field separator.
     * @param separator such separator.
     */
    protected void setGetAllMethodFieldSeparator(
        String separator)
    {
        inmutableSetGetAllMethodFieldSeparator(separator);
    }

    /**
     * Retrieves the "getAll" method field separator.
     * @return such separator.
     */
    public String getGetAllMethodFieldSeparator()
    {
        return m__strGetAllMethodFieldSeparator;
    }

    /**
     * Specifies the "getAll" method end.
     * @param end such end.
     */
    private void inmutableSetGetAllMethodEnd(String end)
    {
        m__strGetAllMethodEnd = end;
    }

    /**
     * Specifies the "getAll" method end.
     * @param end such end.
     */
    protected void setGetAllMethodEnd(String end)
    {
        inmutableSetGetAllMethodEnd(end);
    }

    /**
     * Retrieves the "getAll" end.
     * @return such end.
     */
    public String getGetAllMethodEnd()
    {
        return m__strGetAllMethodEnd;
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
     * Specifies the fields.
     * @param fields the fields.
     */
    private void inmutableSetFields(List fields)
    {
        m__lFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected void setFields(List fields)
    {
        inmutableSetFields(fields);
    }

    /**
     * Retrieves the fields.
     * @return such collection.
     */
    public List getFields()
    {
        return m__lFields;
    }

    /**
     * Adds a new field.
     * @param field the new field.
     */
    public void addField(String field)
    {
        List t_lFields = getFields();

        if  (t_lFields != null) 
        {
            t_lFields.add(field);
        }
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    private void inmutableSetFieldTypes(Map fieldTypes)
    {
        m__mFieldTypes = fieldTypes;
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    protected void setFieldTypes(Map fieldTypes)
    {
        inmutableSetFieldTypes(fieldTypes);
    }

    /**
     * Retrieves the field types.
     * @return such collection.
     */
    public Map getFieldTypes()
    {
        return m__mFieldTypes;
    }

    /**
     * Adds a new field type.
     * @param field the field.
     * @param type the field type.
     */
    public void addFieldType(String field, String type)
    {
        Map t_mFieldTypes = getFieldTypes();

        if  (t_mFieldTypes != null) 
        {
            t_mFieldTypes.put(field, type);
        }
    }

    /**
     * Retrieves the type of given field.
     * @param field the field.
     * @return the field type.
     */
    public String getFieldType(String field)
    {
        String result = "Field";

        Map t_mFieldTypes = getFieldTypes();

        if  (t_mFieldTypes != null) 
        {
            result = (String) t_mFieldTypes.get(field);
        }

        return result;
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            Object[] t_aTableName =
                new Object[]
                {
                    t_StringUtils.capitalize(getTableName().toLowerCase(), '_')
                };

            Object[] t_aPackageName = new Object[]{getPackageName()};

            MessageFormat t_Formatter = new MessageFormat(getHeader());
            t_sbResult.append(t_Formatter.format(t_aTableName));

            t_Formatter = new MessageFormat(getPackageDeclaration());
            t_sbResult.append(t_Formatter.format(t_aPackageName));

            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(t_Formatter.format(t_aTableName));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(t_Formatter.format(t_aTableName));

            t_sbResult.append(getClassStart());

            List t_lFields = getFields();

            if  (t_lFields != null)
            {
                Iterator t_itFields = t_lFields.iterator();

                MessageFormat t_JavadocFormatter =
                    new MessageFormat(getFieldJavadoc());

                MessageFormat t_DefinitionFormatter =
                    new MessageFormat(getFieldDefinition());

                while  (t_itFields.hasNext()) 
                {
                    String t_strField = (String) t_itFields.next();

                    t_sbResult.append(
                        t_JavadocFormatter.format(
                            new Object[]{
                                getTableName(),
                                t_strField}));

                    t_sbResult.append(
                        t_DefinitionFormatter.format(
                            new Object[]{
                                getFieldType(t_strField),
                                t_strField.toUpperCase(),
                                t_strField}));
                }
            }

            t_Formatter = new MessageFormat(getSingletonBody());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(getTableName().toLowerCase(), '_'),
                        getTableName()
                    }));

            t_Formatter = new MessageFormat(getGetTableNameMethod());
            t_sbResult.append(t_Formatter.format(new Object[]{getTableName()}));

            if  (t_lFields != null)
            {
                if  (t_lFields.size() == 0)
                {
                    t_sbResult.append(getEmptyGetAllMethod());
                }
                else 
                {
                    Iterator t_itFields = t_lFields.iterator();

                    if  (t_itFields.hasNext()) 
                    {
                        t_sbResult.append(getGetAllMethodStart());

                        t_sbResult.append(
                            ((String) t_itFields.next()).toUpperCase());
                    }
                
                    while  (t_itFields.hasNext()) 
                    {
                        t_sbResult.append(getGetAllMethodFieldSeparator());
                        t_sbResult.append(
                            ((String) t_itFields.next()).toUpperCase());
                    }

                    t_sbResult.append(getGetAllMethodEnd());
                }
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
