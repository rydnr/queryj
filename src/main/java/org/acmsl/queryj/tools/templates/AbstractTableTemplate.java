//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: AbstractTableTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains the required subtemplates used to generate tables
 *              according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the required subtemplates used to generate tables according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractTableTemplate
    extends  AbstractTemplate
{
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
     * Builds a <code>AbstractTableTemplate</code> using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
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
    protected AbstractTableTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageDeclaration,
        final String packageName,
        final String tableName,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String fieldJavadoc,
        final String fieldDefinition,
        final String classConstructor,
        final String getTableNameMethod,
        final String emptyGetAllMethod,
        final String getAllMethodStart,
        final String getAllMethodFieldSeparator,
        final String getAllMethodEnd,
        final String classEnd)
    {
        super(header, decoratorFactory);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetTableName(tableName);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetSingletonBody(singletonBody);
        immutableSetFieldJavadoc(fieldJavadoc);
        immutableSetFieldDefinition(fieldDefinition);
        immutableSetClassConstructor(classConstructor);
        immutableSetGetTableNameMethod(getTableNameMethod);
        immutableSetEmptyGetAllMethod(emptyGetAllMethod);
        immutableSetGetAllMethodStart(getAllMethodStart);
        immutableSetGetAllMethodFieldSeparator(getAllMethodFieldSeparator);
        immutableSetGetAllMethodEnd(getAllMethodEnd);
        immutableSetClassEnd(classEnd);
        immutableSetFields(new ArrayList());
        immutableSetFieldTypes(new HashMap());
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
     * Specifies the table name.
     * @param tableName the new table name.
     */
    private void immutableSetTableName(final String tableName)
    {
        m__strTableName = tableName;
    }

    /**
     * Specifies the table name.
     * @param tableName the new table name.
     */
    protected void setTableName(final String tableName)
    {
        immutableSetTableName(tableName);
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
    private void immutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        immutableSetAcmslImports(acmslImports);
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
     * @param singletonBody the new singleton body.
     */
    private void immutableSetSingletonBody(final String singletonBody)
    {
        m__strSingletonBody = singletonBody;
    }

    /**
     * Specifies the singleton body.
     * @param singletonBody the new singleton body.
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
     * Specifies the field Javadoc.
     * @param fieldJavadoc the new field Javadoc.
     */
    private void immutableSetFieldJavadoc(final String fieldJavadoc)
    {
        m__strFieldJavadoc = fieldJavadoc;
    }

    /**
     * Specifies the field Javadoc.
     * @param fieldJavadoc the new field Javadoc.
     */
    protected void setFieldJavadoc(final String fieldJavadoc)
    {
        immutableSetFieldJavadoc(fieldJavadoc);
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
    private void immutableSetFieldDefinition(final String fieldDefinition)
    {
        m__strFieldDefinition = fieldDefinition;
    }

    /**
     * Specifies the field definition.
     * @param fieldDefinition the new field definition.
     */
    protected void setFieldDefinition(final String fieldDefinition)
    {
        immutableSetFieldDefinition(fieldDefinition);
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
    private void immutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        immutableSetClassConstructor(constructor);
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
    private void immutableSetGetTableNameMethod(final String method)
    {
        m__strGetTableNameMethod = method;
    }

    /**
     * Specifies the "getTableName" method.
     * @param method such method.
     */
    protected void setGetTableNameMethod(final String method)
    {
        immutableSetGetTableNameMethod(method);
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
    private void immutableSetEmptyGetAllMethod(final String method)
    {
        m__strEmptyGetAllMethod = method;
    }

    /**
     * Specifies the empty "getAll" method.
     * @param method such method.
     */
    protected void setEmptyGetAllMethod(final String method)
    {
        immutableSetEmptyGetAllMethod(method);
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
    private void immutableSetGetAllMethodStart(final String start)
    {
        m__strGetAllMethodStart = start;
    }

    /**
     * Specifies the "getAll" method start.
     * @param start such start.
     */
    protected void setGetAllMethodStart(final String start)
    {
        immutableSetGetAllMethodStart(start);
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
    private void immutableSetGetAllMethodFieldSeparator(
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
        immutableSetGetAllMethodFieldSeparator(separator);
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
    private void immutableSetGetAllMethodEnd(final String end)
    {
        m__strGetAllMethodEnd = end;
    }

    /**
     * Specifies the "getAll" method end.
     * @param end such end.
     */
    protected void setGetAllMethodEnd(final String end)
    {
        immutableSetGetAllMethodEnd(end);
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
     * Specifies the fields.
     * @param fields the fields.
     */
    private void immutableSetFields(final List fields)
    {
        m__lFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected void setFields(final List fields)
    {
        immutableSetFields(fields);
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
    public void addField(final String field)
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
    private void immutableSetFieldTypes(final Map fieldTypes)
    {
        m__mFieldTypes = fieldTypes;
    }

    /**
     * Specifies the field types.
     * @param fieldTypes the field type.
     */
    protected void setFieldTypes(final Map fieldTypes)
    {
        immutableSetFieldTypes(fieldTypes);
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
    public void addFieldType(final String field, String type)
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
    public String getFieldType(final String field)
    {
        String result = "Field";

        Map t_mFieldTypes = getFieldTypes();

        if  (t_mFieldTypes != null) 
        {
            result = (String) t_mFieldTypes.get(field);
        }

        return result;
    }
}
