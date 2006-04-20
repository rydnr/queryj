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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains the subtemplates used to create xml DAO
 *              implementations for each table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the subtemplates used to create xml DAO implementations for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public abstract class AbstractXMLDAOTemplate
    extends  AbstractTemplate
{
    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private MetadataManager m__MetadataManager;

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
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The foreign DAO imports.
     */
    private String m__strForeignDAOImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The extra import statements.
     */
    private String m__strExtraImports;

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
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The class' internal methods.
     */
    private String m__strClassInternalMethods;

    /**
     * The build-key pk javadoc.
     */
    private String m__strBuildKeyPkJavadoc;

    /**
     * The build-key pk declaration.
     */
    private String m__strBuildKeyPkDeclaration;

    /**
     * The build-key pk values.
     */
    private String m__strBuildKeyPkValues;

    /**
     * The process method's pk attributes.
     */
    private String m__strProcessPkAttributes;

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
     * The find-by-primary-key pk filter values.
     */
    private String m__strFindByPrimaryKeyPkFilterValues;

    /**
     * The build-value-object method.
     */
    private String m__strBuildValueObjectMethod;

    /**
     * The build-value-object value retrieval.
     */
    private String m__strBuildValueObjectValueRetrieval;

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
     * The delete method subtemplate.
     */
    private String m__strDeleteMethodSubtemplate;

    /**
     * The delete PK javadoc.
     */
    private String m__strDeletePkJavadoc;

    /**
     * The delete PK declaration.
     */
    private String m__strDeletePkDeclaration;

    /**
     * The delete PK filter declaration.
     */
    private String m__strDeleteFilterDeclaration;

    /**
     * The delete with FK method.
     */
    private String m__strDeleteNoFkMethod;

    /**
     * The delete with FK PK javadoc.
     */
    private String m__strDeleteWithFkPkJavadoc;

    /**
     * The delete with FK PK declaration.
     */
    private String m__strDeleteWithFkPkDeclaration;

    /**
     * The delete with FK PK DAO delete request..
     */
    private String m__strDeleteWithFkDAODeleteRequest;

    /**
     * The delete with FK DAO FK values.
     */
    private String m__strDeleteWithFkPkValues;

    /**
     * The delete by fk method.
     */
    private String m__strDeleteByFkMethod;

    /**
     * The persist method.
     */
    private String m__strPersistMethod;

    /**
     * The Undigester property rules.
     */
    private String m__strUndigesterPropertyRules;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds an <code>AbstractXMLDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param extraImports the extra imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param classInternalMethods the class' internal methods.
     * @param buildKeyPkJavadoc the <i>buildKey</i> key pk javadoc.
     * @param buildKeyPkDeclaration the <i>buildKey</i> pk declaration.
     * @param buildKeyPkValues the <i>buildKey</i>  values.
     * @param processPkAttributes the <i>process</i> pk attributes.
     * @param findByStaticFieldMethod the find-by-static-field method.
     * @param findByStaticFieldJavadoc the field javadoc for
     * find-by-static-field method.
     * @param findByStaticFieldDeclaration the field declaration for
     * find-by-static-field method.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param findByPrimaryKeyPkFilterValues the find by primary key pk
     * filter values.
     * @param buildValueObjectMethod the build value object method.
     * @param buildValueObjectValueRetrieval the value retrieval in
     * buildValueObject method.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethodSubtemplate the delete method subtemplate.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteFilterDeclaration the delete filter declaration.
     * @param deleteNoFkMethod the delete method with no foreign keys.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param deleteByFkMethod the deleteByFk method.
     * @param persistMethod the persist method.
     * @param undigesterPropertyRules the Undigester property rules.
     * @param classEnd the class end.
     */
    protected AbstractXMLDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String projectImports,
        final String foreignDAOImports,
        final String acmslImports,
        final String jdkImports,
        final String extraImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String classInternalMethods,
        final String buildKeyPkJavadoc,
        final String buildKeyPkDeclaration,
        final String buildKeyPkValues,
        final String processPkAttributes,
        final String findByStaticFieldMethod,
        final String findByStaticFieldJavadoc,
        final String findByStaticFieldDeclaration,
        final String findByPrimaryKeyMethod,
        final String findByPrimaryKeyPkJavadoc,
        final String findByPrimaryKeyPkDeclaration,
        final String findByPrimaryKeyPkFilterValues,
        final String buildValueObjectMethod,
        final String buildValueObjectValueRetrieval,
        final String insertMethod,
        final String insertParametersJavadoc,
        final String insertParametersDeclaration,
        final String updateMethod,
        final String updateParametersJavadoc,
        final String updateParametersDeclaration,
        final String deleteMethodSubtemplate,
        final String deletePkJavadoc,
        final String deletePkDeclaration,
        final String deleteNoFkMethod,
        final String deleteWithFkPkJavadoc,
        final String deleteWithFkPkDeclaration,
        final String deleteWithFkDAODeleteRequest,
        final String deleteWithFkPkValues,
        final String deleteByFkMethod,
        final String persistMethod,
        final String undigesterPropertyRules,
        final String classEnd)
    {
        super(decoratorFactory);

        immutableSetTableTemplate(
            tableTemplate);

        immutableSetMetadataManager(
            metadataManager);

        immutableSetHeader(
            header);

        immutableSetPackageDeclaration(
            packageDeclaration);

        immutableSetPackageName(
            packageName);

        immutableSetBasePackageName(
            basePackageName);

        immutableSetRepositoryName(
            repositoryName);

        immutableSetProjectImports(
            projectImports);

        immutableSetForeignDAOImports(
            foreignDAOImports);

        immutableSetAcmslImports(
            acmslImports);

        immutableSetJdkImports(
            jdkImports);

        immutableSetExtraImports(
            extraImports);

        immutableSetJavadoc(
            javadoc);

        immutableSetClassDefinition(
            classDefinition);

        immutableSetClassStart(
            classStart);

        immutableSetClassConstructor(
            classConstructor);

        immutableSetClassInternalMethods(
            classInternalMethods);

        immutableSetBuildKeyPkJavadoc(
            buildKeyPkJavadoc);

        immutableSetBuildKeyPkDeclaration(
            buildKeyPkDeclaration);

        immutableSetBuildKeyPkValues(
            buildKeyPkValues);

        immutableSetProcessPkAttributes(
            processPkAttributes);

        immutableSetFindByStaticFieldMethod(findByStaticFieldMethod);
        immutableSetFindByStaticFieldJavadoc(findByStaticFieldJavadoc);
        immutableSetFindByStaticFieldDeclaration(findByStaticFieldDeclaration);

        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);

        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);

        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);

        immutableSetFindByPrimaryKeyPkFilterValues(
            findByPrimaryKeyPkFilterValues);

        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);

        immutableSetBuildValueObjectValueRetrieval(
            buildValueObjectValueRetrieval);

        immutableSetInsertMethod(
            insertMethod);

        immutableSetInsertParametersJavadoc(
            insertParametersJavadoc);

        immutableSetInsertParametersDeclaration(
            insertParametersDeclaration);

        immutableSetUpdateMethod(
            updateMethod);

        immutableSetUpdateParametersJavadoc(
            updateParametersJavadoc);

        immutableSetUpdateParametersDeclaration(
            updateParametersDeclaration);

        immutableSetDeleteMethodSubtemplate(
            deleteMethodSubtemplate);

        immutableSetDeletePkJavadoc(
            deletePkJavadoc);

        immutableSetDeletePkDeclaration(
            deletePkDeclaration);

        immutableSetDeleteNoFkMethod(
            deleteNoFkMethod);

        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);

        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);

        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);

        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);

        immutableSetDeleteByFkMethod(
            deleteByFkMethod);

        immutableSetPersistMethod(
            persistMethod);

        immutableSetUndigesterPropertyRules(
            undigesterPropertyRules);

        immutableSetClassEnd(
            classEnd);
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
     * @param metadataManager the metadata manager.
     */
    private void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
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
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    public String getRepositoryName()
    {
        return m__strRepositoryName;
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
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    private void immutableSetForeignDAOImports(final String foreignDAOImports)
    {
        m__strForeignDAOImports = foreignDAOImports;
    }

    /**
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    protected void setForeignDAOImports(final String foreignDAOImports)
    {
        immutableSetForeignDAOImports(foreignDAOImports);
    }

    /**
     * Retrieves the foreign DAO imports.
     * @return such information.
     */
    public String getForeignDAOImports() 
    {
        return m__strForeignDAOImports;
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
     * Specifies the extra imports.
     * @param extraImports the new extra imports.
     */
    private void immutableSetExtraImports(final String extraImports)
    {
        m__strExtraImports = extraImports;
    }

    /**
     * Specifies the extra imports.
     * @param extraImports the new extra imports.
     */
    protected void setExtraImports(final String extraImports)
    {
        immutableSetExtraImports(extraImports);
    }

    /**
     * Retrieves the extra imports.
     * @return such information.
     */
    public String getExtraImports() 
    {
        return m__strExtraImports;
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
     * @return such source code.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the class' internal methods.
     * @param internalMethods such source code.
     */
    private void immutableSetClassInternalMethods(final String internalMethods)
    {
        m__strClassInternalMethods = internalMethods;
    }

    /**
     * Specifies the class' internal methods.
     * @param internalMethods such source code.
     */
    protected void setClassInternalMethods(final String internalMethods)
    {
        immutableSetClassInternalMethods(internalMethods);
    }

    /**
     * Retrieves the class' internal methods.
     * @return such source code.
     */
    public String getClassInternalMethods()
    {
        return m__strClassInternalMethods;
    }

    /**
     * Specifies the build-key pk Javadoc.
     * @param buildKeyPkJavadoc such Javadoc.
     */
    private void immutableSetBuildKeyPkJavadoc(
        String buildKeyPkJavadoc)
    {
        m__strBuildKeyPkJavadoc = buildKeyPkJavadoc;
    }

    /**
     * Specifies the build-key pk Javadoc.
     * @param buildKeyPkJavadoc such Javadoc.
     */
    protected void setBuildKeyPkJavadoc(
        String buildKeyPkJavadoc)
    {
        immutableSetBuildKeyPkJavadoc(
            buildKeyPkJavadoc);
    }

    /**
     * Retrieves the build-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getBuildKeyPkJavadoc()
    {
        return m__strBuildKeyPkJavadoc;
    }
    
    /**
     * Specifies the build-key pk declaration.
     * @param buildKeyPkDeclaration such declaration.
     */
    private void immutableSetBuildKeyPkDeclaration(
        String buildKeyPkDeclaration)
    {
        m__strBuildKeyPkDeclaration =
            buildKeyPkDeclaration;
    }

    /**
     * Specifies the build-key pk declaration.
     * @param buildKeyPkdeclaration such declaration.
     */
    protected void setBuildKeyPkdeclaration(
        String buildKeyPkDeclaration)
    {
        immutableSetBuildKeyPkDeclaration(
            buildKeyPkDeclaration);
    }

    /**
     * Retrieves the build-key pk declaration.
     * @return such declaration.
     */
    public String getBuildKeyPkDeclaration()
    {
        return m__strBuildKeyPkDeclaration;
    }
    
    /**
     * Specifies the build-key pk values.
     * @param buildKeyPkValues such values.
     */
    private void immutableSetBuildKeyPkValues(
        String buildKeyPkValues)
    {
        m__strBuildKeyPkValues = buildKeyPkValues;
    }

    /**
     * Specifies the build-key pk values.
     * @param buildKeyPkValues such values.
     */
    protected void setBuildKeyPkValues(
        String buildKeyPkValues)
    {
        immutableSetBuildKeyPkValues(
            buildKeyPkValues);
    }

    /**
     * Retrieves the build-key pk values.
     * @return such values.
     */
    public String getBuildKeyPkValues()
    {
        return m__strBuildKeyPkValues;
    }

    /**
     * Specifies the process pk attributes.
     * @param processPkAttributes such attributes.
     */
    private void immutableSetProcessPkAttributes(
        String processPkAttributes)
    {
        m__strProcessPkAttributes = processPkAttributes;
    }

    /**
     * Specifies the process pk attributes.
     * @param processPkAttributes such attributes.
     */
    protected void setProcessPkAttributes(
        String processPkAttributes)
    {
        immutableSetProcessPkAttributes(
            processPkAttributes);
    }

    /**
     * Retrieves the process pk attributes.
     * @return such attributes.
     */
    public String getProcessPkAttributes()
    {
        return m__strProcessPkAttributes;
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
        String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
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
        String findByPrimaryKeyPkJavadoc)
    {
        m__strFindByPrimaryKeyPkJavadoc = findByPrimaryKeyPkJavadoc;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    protected void setFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
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
        String findByPrimaryKeyPkDeclaration)
    {
        m__strFindByPrimaryKeyPkDeclaration =
            findByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkdeclaration such declaration.
     */
    protected void setFindByPrimaryKeyPkdeclaration(
        String findByPrimaryKeyPkDeclaration)
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
     * Specifies the find-by-primary-key pk filter values.
     * @param findByPrimaryKeyPkFilterValues such filter values.
     */
    private void immutableSetFindByPrimaryKeyPkFilterValues(
        String findByPrimaryKeyPkFilterValues)
    {
        m__strFindByPrimaryKeyPkFilterValues =
            findByPrimaryKeyPkFilterValues;
    }

    /**
     * Specifies the find-by-primary-key pk filter values.
     * @param findByPrimaryKeyPkfilterValues such filter values.
     */
    protected void setFindByPrimaryKeyPkfilterValues(
        String findByPrimaryKeyPkFilterValues)
    {
        immutableSetFindByPrimaryKeyPkFilterValues(
            findByPrimaryKeyPkFilterValues);
    }

    /**
     * Retrieves the find-by-primary-key pk filter values.
     * @return such filter values.
     */
    public String getFindByPrimaryKeyPkFilterValues()
    {
        return m__strFindByPrimaryKeyPkFilterValues;
    }

    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    private void immutableSetBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        m__strBuildValueObjectMethod = buildValueObjectMethod;
    }

    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    protected void setBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);
    }

    /**
     * Retrieves the build-value-object method.
     * @return such method.
     */
    public String getBuildValueObjectMethod()
    {
        return m__strBuildValueObjectMethod;
    }

    /**
     * Specifies the value retrieval in build-value-object method.
     * @param buildValueObjectValueRetrieval such value.
     */
    private void immutableSetBuildValueObjectValueRetrieval(
        String buildValueObjectValueRetrieval)
    {
        m__strBuildValueObjectValueRetrieval = buildValueObjectValueRetrieval;
    }

    /**
     * Specifies the value retrieval in build-value-object method.
     * @param buildValueObjectValueRetrieval such value.
     */
    protected void setBuildValueObjectValueRetrieval(
        String buildValueObjectValueRetrieval)
    {
        immutableSetBuildValueObjectValueRetrieval(
            buildValueObjectValueRetrieval);
    }

    /**
     * Retrieves the value retrieval in build-value-object method.
     * @return such value.
     */
    public String getBuildValueObjectValueRetrieval()
    {
        return m__strBuildValueObjectValueRetrieval;
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
     * Specifies the delete method subtemplate.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteMethodSubtemplate(
        String deleteMethod)
    {
        m__strDeleteMethodSubtemplate = deleteMethod;
    }

    /**
     * Specifies the delete method subtemplate.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethodSubtemplate(
        String deleteMethod)
    {
        immutableSetDeleteMethodSubtemplate(
            deleteMethod);
    }

    /**
     * Retrieves the delete method subtemplate.
     * @return such method.
     */
    public String getDeleteMethodSubtemplate()
    {
        return m__strDeleteMethodSubtemplate;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    private void immutableSetDeletePkJavadoc(
        String deletePkJavadoc)
    {
        m__strDeletePkJavadoc = deletePkJavadoc;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    protected void setDeletePkJavadoc(
        String deletePkJavadoc)
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
        String deletePkDeclaration)
    {
        m__strDeletePkDeclaration =
            deletePkDeclaration;
    }

    /**
     * Specifies the delete pk declaration.
     * @param deletePkdeclaration such declaration.
     */
    protected void setDeletePkdeclaration(
        String deletePkDeclaration)
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
     * Specifies the delete no FK method.
     * @param deleteNoFkMethod such method.
     */
    private void immutableSetDeleteNoFkMethod(
        String deleteNoFkMethod)
    {
        m__strDeleteNoFkMethod = deleteNoFkMethod;
    }

    /**
     * Specifies the delete no FK method.
     * @param deleteNoFkMethod such method.
     */
    protected void setDeleteNoFkMethod(
        String deleteNoFkMethod)
    {
        immutableSetDeleteNoFkMethod(
            deleteNoFkMethod);
    }

    /**
     * Retrieves the delete no FK method.
     * @return such method.
     */
    public String getDeleteNoFkMethod()
    {
        return m__strDeleteNoFkMethod;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    private void immutableSetDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        m__strDeleteWithFkPkJavadoc = deleteWithFkPkJavadoc;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    protected void setDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);
    }

    /**
     * Retrieves the delete with FK PK Javadoc.
     * @return such Javadoc.
     */
    public String getDeleteWithFkPkJavadoc()
    {
        return m__strDeleteWithFkPkJavadoc;
    }
    
    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkDeclaration such declaration.
     */
    private void immutableSetDeleteWithFkPkDeclaration(
        String deleteWithFkPkDeclaration)
    {
        m__strDeleteWithFkPkDeclaration =
            deleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkdeclaration such declaration.
     */
    protected void setDeleteWithFkPkdeclaration(
        String deleteWithFkPkDeclaration)
    {
        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);
    }

    /**
     * Retrieves the delete with FK PK declaration.
     * @return such declaration.
     */
    public String getDeleteWithFkPkDeclaration()
    {
        return m__strDeleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    private void immutableSetDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        m__strDeleteWithFkDAODeleteRequest =
            deleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    protected void setDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);
    }

    /**
     * Retrieves the delete with FK DAO delete request.
     * @return such request.
     */
    public String getDeleteWithFkDAODeleteRequest()
    {
        return m__strDeleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    private void immutableSetDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        m__strDeleteWithFkPkValues =
            deleteWithFkPkValues;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    protected void setDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);
    }

    /**
     * Retrieves the delete with FK PK values.
     * @return such values.
     */
    public String getDeleteWithFkPkValues()
    {
        return m__strDeleteWithFkPkValues;
    }

    /**
     * Specifies the delete-by-fk method.
     * @param method such method.
     */
    protected final void immutableSetDeleteByFkMethod(
        final String method)
    {
        m__strDeleteByFkMethod = method;
    }

    /**
     * Specifies the delete-by-fk method.
     * @param method such method.
     */
    protected void setDeleteByFkMethod(
        final String method)
    {
        immutableSetDeleteByFkMethod(method);
    }

    /**
     * Retrieves the delete-by-fk method.
     * @return such method.
     */
    public String getDeleteByFkMethod()
    {
        return m__strDeleteByFkMethod;
    }

    /**
     * Specifies the persist method.
     * @param persistMethod such method.
     */
    private void immutableSetPersistMethod(final String persistMethod)
    {
        m__strPersistMethod = persistMethod;
    }

    /**
     * Specifies the persist method.
     * @param persistMethod such method.
     */
    protected void setPersistMethod(final String persistMethod)
    {
        immutableSetPersistMethod(persistMethod);
    }

    /**
     * Retrieves the persist method.
     * @return such method.
     */
    public String getPersistMethod()
    {
        return m__strPersistMethod;
    }

    /**
     * Specifies the Undigester's property rules.
     * @param undigesterPropertyRules such rules.
     */
    private void immutableSetUndigesterPropertyRules(
        final String undigesterPropertyRules)
    {
        m__strUndigesterPropertyRules = undigesterPropertyRules;
    }

    /**
     * Specifies the Undigester's property rules.
     * @param undigesterPropertyRules such rules.
     */
    protected void setUndigesterPropertyRules(
        final String undigesterPropertyRules)
    {
        immutableSetUndigesterPropertyRules(undigesterPropertyRules);
    }

    /**
     * Retrieves the Undigester's property rules.
     * @return such rules.
     */
    public String getUndigesterPropertyRules()
    {
        return m__strUndigesterPropertyRules;
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
     * @precondition tableTemplate the table template.
     * @return such header.
     * @precondition tableTemplate != null
     */
    protected String buildHeader(final TableTemplate tableTemplate)
    {
        return "Generating XMLDAO for " + tableTemplate.getTableName() + ".";
    }
}
