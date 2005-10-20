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
 * Description: Contains the required subtemplates to create DAO interfaces.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the required subtemplates to create DAO interfaces.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractBaseDAOTemplate
    extends  AbstractTemplate
{
    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The custom-sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

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
     * The value object package name.
     */
    private String m__strValueObjectPackageName;

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
     * The project import statements.
     */
    private String m__strProjectImports;

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

    /*
     * The constant record.
     */
    private String m__strConstantRecord;

    /**
     * The constant array.
     */
    private String m__strConstantArray;

    /**
     * The constant array entry.
     */
    private String m__strConstantArrayEntry;

    /**
     * The find-by-static-field method.
     */
    private String m__strFindByStaticFieldMethod;

    /**
     * The find-by-static-field  javadoc.
     */
    private String m__strFindByStaticFieldJavadoc;

    /**
     * The find-by-static-field  declaration.
     */
    private String m__strFindByStaticFieldDeclaration;

    /**
     * The find-by-primary-key method.
     */
    private String m__strFindByPrimaryKeyMethod;

    /**
     * The find-by-primary-key pk javadoc.
     */
    private String m__strFindByPrimaryKeyPkJavadoc;

    /**
     * The find-by-primary-key pk declaration.
     */
    private String m__strFindByPrimaryKeyPkDeclaration;

    /**
     * The insert method.
     */
    private String m__strInsertMethod;

    /**
     * The insert parameters Javadoc.
     */
    private String m__strInsertParametersJavadoc;

    /**
     * The insert parameters declaration.
     */
    private String m__strInsertParametersDeclaration;

    /**
     * The create method.
     */
    private String m__strCreateMethod;

    /**
     * The create parameters Javadoc.
     */
    private String m__strCreateParametersJavadoc;

    /**
     * The create parameters declaration.
     */
    private String m__strCreateParametersDeclaration;

    /**
     * The update method.
     */
    private String m__strUpdateMethod;

    /**
     * The update parameters Javadoc.
     */
    private String m__strUpdateParametersJavadoc;

    /**
     * The update parameters declaration.
     */
    private String m__strUpdateParametersDeclaration;

    /**
     * The delete method.
     */
    private String m__strDeleteMethod;

    /**
     * The delete PK javadoc.
     */
    private String m__strDeletePkJavadoc;

    /**
     * The delete PK declaration.
     */
    private String m__strDeletePkDeclaration;

    /**
     * The delete method by fk.
     */
    private String m__strDeleteByFkMethod;

    /**
     * The delete FK javadoc.
     */
    private String m__strDeleteFkJavadoc;

    /**
     * The delete FK declaration.
     */
    private String m__strDeleteFkDeclaration;

    /**
     * The custom select.
     */
    private String m__strCustomSelect;

    /**
     * The custom select parameter javadoc.
     */
    private String m__strCustomSelectParameterJavadoc;

    /**
     * The custom select parameter declaration.
     */
    private String m__strCustomSelectParameterDeclaration;

    /**
     * The custom update or insert.
     */
    private String m__strCustomUpdateOrInsert;

    /**
     * The custom update or insert parameter javadoc.
     */
    private String m__strCustomUpdateOrInsertParameterJavadoc;

    /**
     * The custom update or insert parameter declaration.
     */
    private String m__strCustomUpdateOrInsertParameterDeclaration;

    /**
     * The custom update or insert parameter type specification.
     */
    private String m__strCustomUpdateOrInsertParameterTypeSpecification;

    /**
     * The custom update or insert parameter values.
     */
    private String m__strCustomUpdateOrInsertParameterValues;

    /**
     * The custom select-for-update.
     */
    private String m__strCustomSelectForUpdate;

    /**
     * The custom select-for-update parameter javadoc.
     */
    private String m__strCustomSelectForUpdateParameterJavadoc;

    /**
     * The custom select-for-update return javadoc.
     */
    private String m__strCustomSelectForUpdateReturnJavadoc;

    /**
     * The custom select-for-update parameter declaration.
     */
    private String m__strCustomSelectForUpdateParameterDeclaration;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an <code>AbstractBaseDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param constantRecord the constant record subtemplate.
     * @param constantArray the constant array.
     * @param constantArrayEntry the constant array entry.
     * @param findByStaticFieldMethod the find-by-static-field method.
     * @param findByStaticFieldJavadoc the field javadoc for
     * find-by-static-field method.
     * @param findByStaticFieldDeclaration the field declaration for
     * find-by-static-field method.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param createMethod the create method.
     * @param createParametersJavadoc the javadoc of the create method's parameters.
     * @param createParametersDeclaration the declaration of the create method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteByFkMethod the delete method.
     * @param deleteFkJavadoc the delete FK javadoc.
     * @param deleteFkDeclaration the delete FK declaration.
     * @param customSelect the custom select template.
     * @param customSelectParameterJavadoc the Javadoc for the parameters of
     * the custom selects.
     * @param customSelectParameterDeclaration the parameter declaration of the
     * custom selects.
     * @param customUpdateOrInsert the custom update template.
     * @param customUpdateOrInsertParameterJavadoc the Javadoc for the
     * parameters of the custom updates or inserts.
     * @param customUpdateOrInsertParameterDeclaration the parameter
     * declaration of the custom updates or inserts.
     * @param customUpdateOrInsertParameterTypeSpecification the parameter type
     * specification subtemplate of the custom updates or inserts.
     * @param customUpdateOrInsertParameterValues the parameter values of
     * the custom updates or inserts.
     * @param customSelectForUpdate the custom-select-for-update template.
     * @param customSelectForUpdateParameterJavadoc the Javadoc for the
     * parameters of the custom-select-for-update operations.
     * @param customSelectForUpdateReturnJavadoc the Javadoc for the
     * return of the custom-select-for-update operations.
     * @param customSelectForUpdateParameterDeclaration the parameter
     * declaration of the custom-select-for-update operations.
     * @param classEnd the class end.
     */
    protected AbstractBaseDAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String valueObjectPackageName,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String constantRecord,
        final String constantArray,
        final String constantArrayEntry,
        final String findByStaticFieldMethod,
        final String findByStaticFieldJavadoc,
        final String findByStaticFieldDeclaration,
        final String findByPrimaryKeyMethod,
        final String findByPrimaryKeyPkJavadoc,
        final String findByPrimaryKeyPkDeclaration,
        final String insertMethod,
        final String insertParametersJavadoc,
        final String insertParametersDeclaration,
        final String createMethod,
        final String createParametersJavadoc,
        final String createParametersDeclaration,
        final String updateMethod,
        final String updateParametersJavadoc,
        final String updateParametersDeclaration,
        final String deleteMethod,
        final String deletePkJavadoc,
        final String deletePkDeclaration,
        final String deleteByFkMethod,
        final String deleteFkJavadoc,
        final String deleteFkDeclaration,
        final String customSelect,
        final String customSelectParameterJavadoc,
        final String customSelectParameterDeclaration,
        final String customUpdateOrInsert,
        final String customUpdateOrInsertParameterJavadoc,
        final String customUpdateOrInsertParameterDeclaration,
        final String customSelectForUpdate,
        final String customSelectForUpdateParameterJavadoc,
        final String customSelectForUpdateReturnJavadoc,
        final String customSelectForUpdateParameterDeclaration,
        final String classEnd)
    {
        immutableSetTableTemplate(tableTemplate);
        immutableSetMetaDataManager(metaDataManager);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetValueObjectPackageName(valueObjectPackageName);
        immutableSetProjectImports(projectImports);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetConstantRecord(constantRecord);
        immutableSetConstantArray(constantArray);
        immutableSetConstantArrayEntry(constantArrayEntry);
        immutableSetFindByStaticFieldMethod(findByStaticFieldMethod);
        immutableSetFindByStaticFieldJavadoc(findByStaticFieldJavadoc);
        immutableSetFindByStaticFieldDeclaration(findByStaticFieldDeclaration);
        immutableSetFindByPrimaryKeyMethod(findByPrimaryKeyMethod);
        immutableSetFindByPrimaryKeyPkJavadoc(findByPrimaryKeyPkJavadoc);
        immutableSetFindByPrimaryKeyPkDeclaration(findByPrimaryKeyPkDeclaration);
        immutableSetInsertMethod(insertMethod);
        immutableSetInsertParametersJavadoc(insertParametersJavadoc);
        immutableSetInsertParametersDeclaration(insertParametersDeclaration);
        immutableSetCreateMethod(createMethod);
        immutableSetCreateParametersJavadoc(createParametersJavadoc);
        immutableSetCreateParametersDeclaration(createParametersDeclaration);
        immutableSetUpdateMethod(updateMethod);
        immutableSetUpdateParametersJavadoc(updateParametersJavadoc);
        immutableSetUpdateParametersDeclaration(updateParametersDeclaration);
        immutableSetDeleteMethod(deleteMethod);
        immutableSetDeletePkJavadoc(deletePkJavadoc);
        immutableSetDeletePkDeclaration(deletePkDeclaration);
        immutableSetDeleteByFkMethod(deleteByFkMethod);
        immutableSetDeleteFkJavadoc(deleteFkJavadoc);
        immutableSetDeleteFkDeclaration(deleteFkDeclaration);
        immutableSetCustomSelect(
            customSelect);
        immutableSetCustomSelectParameterJavadoc(
            customSelectParameterJavadoc);
        immutableSetCustomSelectParameterDeclaration(
            customSelectParameterDeclaration);
        immutableSetCustomUpdateOrInsert(
            customUpdateOrInsert);
        immutableSetCustomUpdateOrInsertParameterJavadoc(
            customUpdateOrInsertParameterJavadoc);
        immutableSetCustomUpdateOrInsertParameterDeclaration(
            customUpdateOrInsertParameterDeclaration);
        immutableSetCustomSelectForUpdate(
            customSelectForUpdate);
        immutableSetCustomSelectForUpdateParameterJavadoc(
            customSelectForUpdateParameterJavadoc);
        immutableSetCustomSelectForUpdateReturnJavadoc(
            customSelectForUpdateReturnJavadoc);
        immutableSetCustomSelectForUpdateParameterDeclaration(
            customSelectForUpdateParameterDeclaration);
        immutableSetClassEnd(classEnd);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void immutableSetTableTemplate(final TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(final TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such template.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }


    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    private void immutableSetMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    protected void setMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        immutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
    }

    /**
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    private void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom-sql provider.
     * @return such provider.
     */
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
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
     * Specifies the value object package name.
     * @param packageName the new package name.
     */
    private void immutableSetValueObjectPackageName(final String packageName)
    {
        m__strValueObjectPackageName = packageName;
    }

    /**
     * Specifies the value object package name.
     * @param packageName the new package name.
     */
    protected void setValueObjectPackageName(final String packageName)
    {
        immutableSetValueObjectPackageName(packageName);
    }

    /**
     * Retrieves the value object package name.
     * @return such information.
     */
    public String getValueObjectPackageName() 
    {
        return m__strValueObjectPackageName;
    }

    /**
     * Specifies the project imports.
     * @param jdkImports the new project imports.
     */
    private void immutableSetProjectImports(final String jdkImports)
    {
        m__strProjectImports = jdkImports;
    }

    /**
     * Specifies the project imports.
     * @param jdkImports the new project imports.
     */
    protected void setProjectImports(final String jdkImports)
    {
        immutableSetProjectImports(jdkImports);
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
     * Specifies the constant record.
     * @param constantRecord the new constant record.
     */
    private void immutableSetConstantRecord(final String constantRecord)
    {
        m__strConstantRecord = constantRecord;
    }

    /**
     * Specifies the constant record.
     * @param constantRecord the new constant record.
     */
    protected void setConstantRecord(final String constantRecord)
    {
        immutableSetConstantRecord(constantRecord);
    }

    /**
     * Retrieves the constant record.
     * @return such information.
     */
    public String getConstantRecord() 
    {
        return m__strConstantRecord;
    }

    /**
     * Specifies the constant array.
     * @param constantArray the new constant array.
     */
    private void immutableSetConstantArray(final String constantArray)
    {
        m__strConstantArray = constantArray;
    }

    /**
     * Specifies the constant array.
     * @param constantArray the new constant array.
     */
    protected void setConstantArray(final String constantArray)
    {
        immutableSetConstantArray(constantArray);
    }

    /**
     * Retrieves the constant array.
     * @return such information.
     */
    public String getConstantArray() 
    {
        return m__strConstantArray;
    }

    /**
     * Specifies the constant array entry.
     * @param constantArrayEntry the new constant array entry.
     */
    private void immutableSetConstantArrayEntry(final String constantArrayEntry)
    {
        m__strConstantArrayEntry = constantArrayEntry;
    }

    /**
     * Specifies the constant array entry.
     * @param constantArrayEntry the new constant array entry.
     */
    protected void setConstantArrayEntry(final String constantArrayEntry)
    {
        immutableSetConstantArrayEntry(constantArrayEntry);
    }

    /**
     * Retrieves the constant array entry.
     * @return such information.
     */
    public String getConstantArrayEntry() 
    {
        return m__strConstantArrayEntry;
    }

    /**
     * Specifies the find-by-static-field method.
     * @param findByStaticFieldMethod such method.
     */
    private void immutableSetFindByStaticFieldMethod(
        final String findByStaticFieldMethod)
    {
        m__strFindByStaticFieldMethod = findByStaticFieldMethod;
    }

    /**
     * Specifies the find-by-static-field method.
     * @param findByStaticFieldMethod such method.
     */
    protected void setFindByStaticFieldMethod(
        final String findByStaticFieldMethod)
    {
        immutableSetFindByStaticFieldMethod(
            findByStaticFieldMethod);
    }

    /**
     * Retrieves the find-by-static-field method.
     * @return such method.
     */
    public String getFindByStaticFieldMethod()
    {
        return m__strFindByStaticFieldMethod;
    }

    /**
     * Specifies the find-by-static-field  Javadoc.
     * @param findByStaticFieldJavadoc such Javadoc.
     */
    private void immutableSetFindByStaticFieldJavadoc(
        final String findByStaticFieldJavadoc)
    {
        m__strFindByStaticFieldJavadoc = findByStaticFieldJavadoc;
    }

    /**
     * Specifies the find-by-static-field  Javadoc.
     * @param findByStaticFieldJavadoc such Javadoc.
     */
    protected void setFindByStaticFieldJavadoc(
        final String findByStaticFieldJavadoc)
    {
        immutableSetFindByStaticFieldJavadoc(
            findByStaticFieldJavadoc);
    }

    /**
     * Retrieves the find-by-static-field  Javadoc.
     * @return such Javadoc.
     */
    public String getFindByStaticFieldJavadoc()
    {
        return m__strFindByStaticFieldJavadoc;
    }
    
    /**
     * Specifies the find-by-static-field  declaration.
     * @param findByStaticFieldDeclaration such declaration.
     */
    private void immutableSetFindByStaticFieldDeclaration(
        final String findByStaticFieldDeclaration)
    {
        m__strFindByStaticFieldDeclaration =
            findByStaticFieldDeclaration;
    }

    /**
     * Specifies the find-by-static-field  declaration.
     * @param findByStaticFieldDeclaration such declaration.
     */
    protected void setFindByStaticFielddeclaration(
        final String findByStaticFieldDeclaration)
    {
        immutableSetFindByStaticFieldDeclaration(
            findByStaticFieldDeclaration);
    }

    /**
     * Retrieves the find-by-static-field  declaration.
     * @return such declaration.
     */
    public String getFindByStaticFieldDeclaration()
    {
        return m__strFindByStaticFieldDeclaration;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    private void immutableSetFindByPrimaryKeyMethod(
        final String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        final String findByPrimaryKeyMethod)
    {
        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);
    }

    /**
     * Retrieves the find-by-primary-key method.
     * @return such method.
     */
    public String getFindByPrimaryKeyMethod()
    {
        return m__strFindByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    private void immutableSetFindByPrimaryKeyPkJavadoc(
        final String findByPrimaryKeyPkJavadoc)
    {
        m__strFindByPrimaryKeyPkJavadoc = findByPrimaryKeyPkJavadoc;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    protected void setFindByPrimaryKeyPkJavadoc(
        final String findByPrimaryKeyPkJavadoc)
    {
        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);
    }

    /**
     * Retrieves the find-by-primary-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getFindByPrimaryKeyPkJavadoc()
    {
        return m__strFindByPrimaryKeyPkJavadoc;
    }
    
    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    private void immutableSetFindByPrimaryKeyPkDeclaration(
        final String findByPrimaryKeyPkDeclaration)
    {
        m__strFindByPrimaryKeyPkDeclaration =
            findByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    protected void setFindByPrimaryKeyPkdeclaration(
        final String findByPrimaryKeyPkDeclaration)
    {
        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);
    }

    /**
     * Retrieves the find-by-primary-key pk declaration.
     * @return such declaration.
     */
    public String getFindByPrimaryKeyPkDeclaration()
    {
        return m__strFindByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    private void immutableSetInsertMethod(final String insertMethod)
    {
        m__strInsertMethod = insertMethod;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    protected void setInsertMethod(final String insertMethod)
    {
        immutableSetInsertMethod(insertMethod);
    }

    /**
     * Retrieves the insert method.
     * @return such method.
     */
    public String getInsertMethod()
    {
        return m__strInsertMethod;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetInsertParametersJavadoc(final String javadoc)
    {
        m__strInsertParametersJavadoc = javadoc;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setInsertParametersJavadoc(final String javadoc)
    {
        immutableSetInsertParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the insert parameters javadoc.
     * @return such information.
     */
    public String getInsertParametersJavadoc()
    {
        return m__strInsertParametersJavadoc;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetInsertParametersDeclaration(final String declaration)
    {
        m__strInsertParametersDeclaration = declaration;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setInsertParametersDeclaration(final String declaration)
    {
        immutableSetInsertParametersDeclaration(declaration);
    }

    /**
     * Retrieves the insert parameters declaration.
     * @return such information.
     */
    public String getInsertParametersDeclaration()
    {
        return m__strInsertParametersDeclaration;
    }

    /**
     * Specifies the create method.
     * @param createMethod such method.
     */
    private void immutableSetCreateMethod(final String createMethod)
    {
        m__strCreateMethod = createMethod;
    }

    /**
     * Specifies the create method.
     * @param createMethod such method.
     */
    protected void setCreateMethod(final String createMethod)
    {
        immutableSetCreateMethod(createMethod);
    }

    /**
     * Retrieves the create method.
     * @return such method.
     */
    public String getCreateMethod()
    {
        return m__strCreateMethod;
    }

    /**
     * Specifies the create parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetCreateParametersJavadoc(final String javadoc)
    {
        m__strCreateParametersJavadoc = javadoc;
    }

    /**
     * Specifies the create parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setCreateParametersJavadoc(final String javadoc)
    {
        immutableSetCreateParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the create parameters javadoc.
     * @return such information.
     */
    public String getCreateParametersJavadoc()
    {
        return m__strCreateParametersJavadoc;
    }

    /**
     * Specifies the create parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetCreateParametersDeclaration(final String declaration)
    {
        m__strCreateParametersDeclaration = declaration;
    }

    /**
     * Specifies the create parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setCreateParametersDeclaration(final String declaration)
    {
        immutableSetCreateParametersDeclaration(declaration);
    }

    /**
     * Retrieves the create parameters declaration.
     * @return such information.
     */
    public String getCreateParametersDeclaration()
    {
        return m__strCreateParametersDeclaration;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    private void immutableSetUpdateMethod(final String updateMethod)
    {
        m__strUpdateMethod = updateMethod;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    protected void setUpdateMethod(final String updateMethod)
    {
        immutableSetUpdateMethod(updateMethod);
    }

    /**
     * Retrieves the update method.
     * @return such method.
     */
    public String getUpdateMethod()
    {
        return m__strUpdateMethod;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetUpdateParametersJavadoc(final String javadoc)
    {
        m__strUpdateParametersJavadoc = javadoc;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setUpdateParametersJavadoc(final String javadoc)
    {
        immutableSetUpdateParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the update parameters javadoc.
     * @return such information.
     */
    public String getUpdateParametersJavadoc()
    {
        return m__strUpdateParametersJavadoc;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetUpdateParametersDeclaration(final String declaration)
    {
        m__strUpdateParametersDeclaration = declaration;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setUpdateParametersDeclaration(final String declaration)
    {
        immutableSetUpdateParametersDeclaration(declaration);
    }

    /**
     * Retrieves the update parameters declaration.
     * @return such information.
     */
    public String getUpdateParametersDeclaration()
    {
        return m__strUpdateParametersDeclaration;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteMethod(
        final String deleteMethod)
    {
        m__strDeleteMethod = deleteMethod;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethod(
        final String deleteMethod)
    {
        immutableSetDeleteMethod(
            deleteMethod);
    }

    /**
     * Retrieves the delete method.
     * @return such method.
     */
    public String getDeleteMethod()
    {
        return m__strDeleteMethod;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    private void immutableSetDeletePkJavadoc(
        final String deletePkJavadoc)
    {
        m__strDeletePkJavadoc = deletePkJavadoc;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    protected void setDeletePkJavadoc(
        final String deletePkJavadoc)
    {
        immutableSetDeletePkJavadoc(
            deletePkJavadoc);
    }

    /**
     * Retrieves the delete pk Javadoc.
     * @return such Javadoc.
     */
    public String getDeletePkJavadoc()
    {
        return m__strDeletePkJavadoc;
    }
    
    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    private void immutableSetDeletePkDeclaration(
        final String deletePkDeclaration)
    {
        m__strDeletePkDeclaration =
            deletePkDeclaration;
    }

    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    protected void setDeletePkdeclaration(
        final String deletePkDeclaration)
    {
        immutableSetDeletePkDeclaration(
            deletePkDeclaration);
    }

    /**
     * Retrieves the delete pk declaration.
     * @return such declaration.
     */
    public String getDeletePkDeclaration()
    {
        return m__strDeletePkDeclaration;
    }

    /**
     * Specifies the delete by fk method.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteByFkMethod(
        final String deleteMethod)
    {
        m__strDeleteByFkMethod = deleteMethod;
    }

    /**
     * Specifies the delete by fk method.
     * @param deleteMethod such method.
     */
    protected void setDeleteByFkMethod(
        final String deleteMethod)
    {
        immutableSetDeleteByFkMethod(
            deleteMethod);
    }

    /**
     * Retrieves the delete by fk method.
     * @return such method.
     */
    public String getDeleteByFkMethod()
    {
        return m__strDeleteByFkMethod;
    }

    /**
     * Specifies the delete fk Javadoc.
     * @param deleteFkJavadoc such Javadoc.
     */
    private void immutableSetDeleteFkJavadoc(
        final String deleteFkJavadoc)
    {
        m__strDeleteFkJavadoc = deleteFkJavadoc;
    }

    /**
     * Specifies the delete fk Javadoc.
     * @param deleteFkJavadoc such Javadoc.
     */
    protected void setDeleteFkJavadoc(
        final String deleteFkJavadoc)
    {
        immutableSetDeleteFkJavadoc(
            deleteFkJavadoc);
    }

    /**
     * Retrieves the delete fk Javadoc.
     * @return such Javadoc.
     */
    public String getDeleteFkJavadoc()
    {
        return m__strDeleteFkJavadoc;
    }
    
    /**
     * Specifies the delete fk declaration.
     * @param deleteFkDeclaration such declaration.
     */
    private void immutableSetDeleteFkDeclaration(
        final String deleteFkDeclaration)
    {
        m__strDeleteFkDeclaration =
            deleteFkDeclaration;
    }

    /**
     * Specifies the delete fk declaration.
     * @param deleteFkDeclaration such declaration.
     */
    protected void setDeleteFkdeclaration(
        final String deleteFkDeclaration)
    {
        immutableSetDeleteFkDeclaration(
            deleteFkDeclaration);
    }

    /**
     * Retrieves the delete fk declaration.
     * @return such declaration.
     */
    public String getDeleteFkDeclaration()
    {
        return m__strDeleteFkDeclaration;
    }

    /**
     * Specifies the custom select template.
     * @param select such template.
     */
    private void immutableSetCustomSelect(
        final String select)
    {
        m__strCustomSelect = select;
    }

    /**
     * Specifies the custom select template.
     * @param select such template.
     */
    protected void setCustomSelect(
        final String select)
    {
        immutableSetCustomSelect(select);
    }

    /**
     * Retrieves the custom select template.
     * @return such template.
     */
    public String getCustomSelect()
    {
        return m__strCustomSelect;
    }

    /**
     * Specifies the custom select parameter Javadoc template.
     * @param select such template.
     */
    private void immutableSetCustomSelectParameterJavadoc(
        final String template)
    {
        m__strCustomSelectParameterJavadoc = template;
    }

    /**
     * Specifies the custom select parameter Javadoc template.
     * @param select such template.
     */
    protected void setCustomSelectParameterJavadoc(
        final String select)
    {
        immutableSetCustomSelectParameterJavadoc(select);
    }

    /**
     * Retrieves the custom select parameter Javadoc template.
     * @return such template.
     */
    public String getCustomSelectParameterJavadoc()
    {
        return m__strCustomSelectParameterJavadoc;
    }

    /**
     * Specifies the custom select parameter declaration template.
     * @param select such template.
     */
    private void immutableSetCustomSelectParameterDeclaration(
        final String template)
    {
        m__strCustomSelectParameterDeclaration = template;
    }

    /**
     * Specifies the custom select parameter declaration template.
     * @param select such template.
     */
    protected void setCustomSelectParameterDeclaration(
        final String select)
    {
        immutableSetCustomSelectParameterDeclaration(select);
    }

    /**
     * Retrieves the custom select parameter declaration template.
     * @return such template.
     */
    public String getCustomSelectParameterDeclaration()
    {
        return m__strCustomSelectParameterDeclaration;
    }

    /**
     * Specifies the custom update or insert template.
     * @param updateOrInsert such template.
     */
    private void immutableSetCustomUpdateOrInsert(
        final String updateOrInsert)
    {
        m__strCustomUpdateOrInsert = updateOrInsert;
    }

    /**
     * Specifies the custom update or insert template.
     * @param updateOrInsert such template.
     */
    protected void setCustomUpdateOrInsert(
        final String updateOrInsert)
    {
        immutableSetCustomUpdateOrInsert(updateOrInsert);
    }

    /**
     * Retrieves the custom update or insert template.
     * @return such template.
     */
    public String getCustomUpdateOrInsert()
    {
        return m__strCustomUpdateOrInsert;
    }

    /**
     * Specifies the custom update or insert parameter Javadoc template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterJavadoc(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterJavadoc = template;
    }

    /**
     * Specifies the custom update or insert parameter Javadoc template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterJavadoc(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterJavadoc(update);
    }

    /**
     * Retrieves the custom update or insert parameter Javadoc template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterJavadoc()
    {
        return m__strCustomUpdateOrInsertParameterJavadoc;
    }

    /**
     * Specifies the custom update or insert parameter declaration template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterDeclaration(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterDeclaration = template;
    }

    /**
     * Specifies the custom update or insert parameter declaration template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterDeclaration(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterDeclaration(update);
    }

    /**
     * Retrieves the custom update or insert parameter declaration template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterDeclaration()
    {
        return m__strCustomUpdateOrInsertParameterDeclaration;
    }

    /**
     * Specifies the subtemplate to specify the types of the 
     * custom update or insert parameters.
     * @param template the template.
     */
    private void immutableSetCustomUpdateOrInsertParameterTypeSpecification(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterTypeSpecification = template;
    }

    /**
     * Specifies the subtemplate to specify the types of the 
     * custom update or insert parameters.
     * @param template the template.
     */
    protected void setCustomUpdateOrInsertParameterTypeSpecification(
        final String template)
    {
        immutableSetCustomUpdateOrInsertParameterTypeSpecification(template);
    }

    /**
     * Retrieves the subtemplate to specify the types of the custom update or insert
     * parameters.
     * @return such subtemplate.
     */
    public String getCustomUpdateOrInsertParameterTypeSpecification()
    {
        return m__strCustomUpdateOrInsertParameterTypeSpecification;
    }

    /**
     * Specifies the custom update or insert parameter values template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterValues(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterValues = template;
    }

    /**
     * Specifies the custom update or insert parameter values template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterValues(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterValues(update);
    }

    /**
     * Retrieves the custom update or insert parameter values template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterValues()
    {
        return m__strCustomUpdateOrInsertParameterValues;
    }

//
    /**
     * Specifies the custom select-for-update template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdate(
        final String select)
    {
        m__strCustomSelectForUpdate = select;
    }

    /**
     * Specifies the custom select-for-update template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdate(
        final String select)
    {
        immutableSetCustomSelectForUpdate(select);
    }

    /**
     * Retrieves the custom select-for-update template.
     * @return such template.
     */
    public String getCustomSelectForUpdate()
    {
        return m__strCustomSelectForUpdate;
    }

    /**
     * Specifies the custom select-for-update parameter Javadoc template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateParameterJavadoc(
        final String template)
    {
        m__strCustomSelectForUpdateParameterJavadoc = template;
    }

    /**
     * Specifies the custom select-for-update parameter Javadoc template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateParameterJavadoc(
        final String select)
    {
        immutableSetCustomSelectForUpdateParameterJavadoc(select);
    }

    /**
     * Retrieves the custom select-for-update parameter Javadoc template.
     * @return such template.
     */
    public String getCustomSelectForUpdateParameterJavadoc()
    {
        return m__strCustomSelectForUpdateParameterJavadoc;
    }

    /**
     * Specifies the custom select-for-update return Javadoc template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateReturnJavadoc(
        final String template)
    {
        m__strCustomSelectForUpdateReturnJavadoc = template;
    }

    /**
     * Specifies the custom select-for-update return Javadoc template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateReturnJavadoc(
        final String select)
    {
        immutableSetCustomSelectForUpdateReturnJavadoc(select);
    }

    /**
     * Retrieves the custom select-for-update return Javadoc template.
     * @return such template.
     */
    public String getCustomSelectForUpdateReturnJavadoc()
    {
        return m__strCustomSelectForUpdateReturnJavadoc;
    }

    /**
     * Specifies the custom select-for-update parameter declaration template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateParameterDeclaration(
        final String template)
    {
        m__strCustomSelectForUpdateParameterDeclaration = template;
    }

    /**
     * Specifies the custom select-for-update parameter declaration template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateParameterDeclaration(
        final String select)
    {
        immutableSetCustomSelectForUpdateParameterDeclaration(select);
    }

    /**
     * Retrieves the custom select-for-update parameter declaration template.
     * @return such template.
     */
    public String getCustomSelectForUpdateParameterDeclaration()
    {
        return m__strCustomSelectForUpdateParameterDeclaration;
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
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getTableTemplate());
    }

    /**
     * Builds the header for logging purposes.
     * @param tableTemplate the table template.
     * @return such header.
     * @precondition tableTemplate != null
     */
    protected String buildHeader(final TableTemplate tableTemplate)
    {
        return
              "Generating BaseDAO for "
            + tableTemplate.getTableName() + ".";
    }
}
