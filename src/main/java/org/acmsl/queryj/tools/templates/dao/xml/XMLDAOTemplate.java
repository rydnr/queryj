//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Description: Is able to create xml DAO implementations for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;

/**
 * Is able to create xml DAO implementations for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class XMLDAOTemplate
    extends  AbstractXMLDAOTemplate
    implements  XMLDAOTemplateDefaults
{
    /**
     * Builds a <code>XMLDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public XMLDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            tableTemplate,
            metadataManager,
//            (header != null) ? header : DEFAULT_HEADER,
            DEFAULT_HEADER,
            decoratorFactory,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            basePackageName,
            repositoryName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_FOREIGN_DAO_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_EXTRA_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_CLASS_CONSTRUCTOR,
            DEFAULT_CLASS_INTERNAL_METHODS,
            DEFAULT_BUILD_KEY_PK_JAVADOC,
            DEFAULT_BUILD_KEY_PK_DECLARATION,
            DEFAULT_BUILD_KEY_PK_VALUES,
            DEFAULT_PROCESS_PK_ATTRIBUTES,
            DEFAULT_FIND_BY_STATIC_FIELD_METHOD,
            DEFAULT_FIND_BY_STATIC_FIELD_JAVADOC,
            DEFAULT_FIND_BY_STATIC_FIELD_DECLARATION,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES,
            DEFAULT_BUILD_VALUE_OBJECT_METHOD,
            DEFAULT_BUILD_VALUE_OBJECT_VALUE_RETRIEVAL,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD_SUBTEMPLATE,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_DELETE_NO_FK_METHOD,
            DEFAULT_DELETE_WITH_FK_PK_JAVADOC,
            DEFAULT_DELETE_WITH_FK_PK_DECLARATION,
            DEFAULT_DELETE_WITH_FK_DAO_DELETE_REQUEST,
            DEFAULT_DELETE_WITH_FK_PK_VALUES,
            DEFAULT_DELETE_BY_FK_METHOD,
            DEFAULT_PERSIST_METHOD,
            DEFAULT_UNDIGESTER_PROPERTY_RULES,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @return such code.
     */
    protected String generateOutput(final String header)
    {
        return generateOutput(header, getMetadataManager());
    }
    
    /**
     * Retrieves the source code generated by this template.
     * @param metadataManager the metadata manager.
     * @return such code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getTableTemplate(),
                metadataManager,
                header,
                getPackageDeclaration(),
                getPackageName(),
                getBasePackageName(),
                getRepositoryName(),
                getProjectImports(),
                getForeignDAOImports(),
                getAcmslImports(),
                getJdkImports(),
                getExtraImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getClassConstructor(),
                getClassInternalMethods(),
                getBuildKeyPkJavadoc(),
                getBuildKeyPkDeclaration(),
                getBuildKeyPkValues(),
                getProcessPkAttributes(),
                getFindByStaticFieldMethod(),
                getFindByStaticFieldJavadoc(),
                getFindByStaticFieldDeclaration(),
                getFindByPrimaryKeyMethod(),
                getFindByPrimaryKeyPkJavadoc(),
                getFindByPrimaryKeyPkDeclaration(),
                getFindByPrimaryKeyPkFilterValues(),
                getBuildValueObjectMethod(),
                getBuildValueObjectValueRetrieval(),
                getInsertMethod(),
                getInsertParametersJavadoc(),
                getInsertParametersDeclaration(),
                getUpdateMethod(),
                getUpdateParametersJavadoc(),
                getUpdateParametersDeclaration(),
                getDeleteMethodSubtemplate(),
                getDeletePkJavadoc(),
                getDeletePkDeclaration(),
                getDeleteNoFkMethod(),
                getDeleteWithFkPkJavadoc(),
                getDeleteWithFkPkDeclaration(),
                getDeleteWithFkDAODeleteRequest(),
                getDeleteWithFkPkValues(),
                getDeleteByFkMethod(),
                getPersistMethod(),
                getUndigesterPropertyRules(),
                getClassEnd(),
                metadataManager.getMetadataTypeManager(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
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
     * @param buildValueObjectValueRetrieval the value retrieval inside
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
     * @param deleteNoFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param deleteByFkMethod the delete-by-fk method.
     * @param persistMethod the persist method.
     * @param undigesterPropertyRules the Undigester property rules.
     * @param classEnd the class end.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such code.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
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
        final String classEnd,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils,
        final PackageUtils packageUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strTableName = tableTemplate.getTableName();

        MessageFormat t_HeaderFormatter = new MessageFormat(header);

        MessageFormat t_PackageDeclarationFormatter =
            new MessageFormat(packageDeclaration);

        MessageFormat t_ProjectImportFormatter =
            new MessageFormat(projectImports);

        MessageFormat t_ForeignDAOImportsFormatter =
            new MessageFormat(foreignDAOImports);

        MessageFormat t_JavadocFormatter = new MessageFormat(javadoc);

        MessageFormat t_ClassStartFormatter =
            new MessageFormat(classStart);

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(classDefinition);

        MessageFormat t_ClassConstructorFormatter =
            new MessageFormat(classConstructor);

        MessageFormat t_ClassInternalMethodsFormatter =
            new MessageFormat(classInternalMethods);

        MessageFormat t_BuildKeyPkDeclarationFormatter =
            new MessageFormat(buildKeyPkDeclaration);

        MessageFormat t_ProcessPkAttributesFormatter =
            new MessageFormat(processPkAttributes);

        MessageFormat t_FindByPrimaryKeyFormatter =
            new MessageFormat(findByPrimaryKeyMethod);

        MessageFormat t_FindByPrimaryKeyPkJavadocFormatter =
            new MessageFormat(findByPrimaryKeyPkJavadoc);

        MessageFormat t_FindByPrimaryKeyPkDeclarationFormatter =
            new MessageFormat(findByPrimaryKeyPkDeclaration);

        MessageFormat t_FindByPrimaryKeyPkFilterValuesFormatter =
            new MessageFormat(findByPrimaryKeyPkFilterValues);

        MessageFormat t_BuildValueObjectMethodFormatter =
            new MessageFormat(buildValueObjectMethod);

        MessageFormat t_BuildValueObjectValueRetrieval =
            new MessageFormat(buildValueObjectValueRetrieval);

        MessageFormat t_InsertMethodFormatter =
            new MessageFormat(insertMethod);

        MessageFormat t_InsertParametersJavadocFormatter =
            new MessageFormat(insertParametersJavadoc);

        MessageFormat t_InsertParametersDeclarationFormatter =
            new MessageFormat(insertParametersDeclaration);

        MessageFormat t_UpdateMethodFormatter =
            new MessageFormat(updateMethod);

        MessageFormat t_UpdateParametersJavadocFormatter =
            new MessageFormat(updateParametersJavadoc);

        MessageFormat t_UpdateParametersDeclarationFormatter =
            new MessageFormat(updateParametersDeclaration);

        MessageFormat t_DeleteMethodFormatter =
            new MessageFormat(deleteMethodSubtemplate);

        MessageFormat t_DeleteNoFkMethodFormatter =
            new MessageFormat(deleteNoFkMethod);

        MessageFormat t_DeleteWithFkPkJavadocFormatter =
            new MessageFormat(deleteWithFkPkJavadoc);

        MessageFormat t_DeleteWithFkPkDeclarationFormatter =
            new MessageFormat(deleteWithFkPkDeclaration);

        MessageFormat t_DeleteWithFkDAODeleteRequestFormatter =
            new MessageFormat(deleteWithFkDAODeleteRequest);

        MessageFormat t_DeleteWithFkPkValuesFormatter =
            new MessageFormat(deleteWithFkPkValues);

        MessageFormat t_DeleteByFkMethodFormatter =
            new MessageFormat(deleteByFkMethod);

        MessageFormat t_PersistMethodFormatter =
            new MessageFormat(persistMethod);

        MessageFormat t_UndigesterPropertyRulesFormatter =
            new MessageFormat(undigesterPropertyRules);

        StringBuffer t_sbForeignDAOImports = new StringBuffer();
        StringBuffer t_sbPkJavadoc = new StringBuffer();
        StringBuffer t_sbPkDeclaration = new StringBuffer();
        StringBuffer t_sbBuildKeyPkDeclaration = new StringBuffer();
        StringBuffer t_sbBuildKeyValues = new StringBuffer();
        StringBuffer t_sbProcessPkAttributes = new StringBuffer();
        StringBuffer t_sbPkFilterValues = new StringBuffer();
        StringBuffer t_sbDeleteMethod = new StringBuffer();
        StringBuffer t_sbDeleteByFkMethod = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkValues = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkValuesDeleteRequest = new StringBuffer();

        StringBuffer t_sbFkJavadoc = new StringBuffer();
        StringBuffer t_sbFkDeclaration;
        StringBuffer t_sbDeleteNoFkMethod = new StringBuffer();
        StringBuffer t_sbDeleteWithFkDAODeleteRequest = new StringBuffer();

        StringBuffer t_sbBuildValueObjectParametersDeclaration =
            new StringBuffer();

        StringBuffer t_sbBuildValueObjectValueRetrieval = new StringBuffer();
        StringBuffer t_sbInsertParametersJavadoc        = new StringBuffer();
        StringBuffer t_sbInsertParametersDeclaration    = new StringBuffer();
        StringBuffer t_sbUpdateParametersJavadoc        = new StringBuffer();
        StringBuffer t_sbUpdateParametersDeclaration    = new StringBuffer();

        StringBuffer t_sbPersistMethod = new StringBuffer();
        StringBuffer t_sbUndigesterPropertyRules = new StringBuffer();

        String t_strDeleteMethodModifier = "public";
        String t_strDeleteMethodSuffix = "";

        boolean t_bForeignKeys = false;

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(
                t_strTableName);

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                tableTemplate.getTableName().toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        if  (t_astrReferredTables != null)
        {
            for  (int t_iRefTableIndex = 0;
                      t_iRefTableIndex < t_astrReferredTables.length;
                      t_iRefTableIndex++)
            {
                t_bForeignKeys = true;

                t_strDeleteMethodModifier = "protected";
                t_strDeleteMethodSuffix = 
                    stringUtils.capitalize(
                        t_strTableName,
                        '_');

                String t_strReferredTableName =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_astrReferredTables[t_iRefTableIndex].toLowerCase()),
                        '_');

                String[] t_astrFkNames =
                    metadataManager.getReferredKeys(
                        t_strTableName,
                        t_astrReferredTables[t_iRefTableIndex])[0];

                t_sbFkDeclaration = new StringBuffer();

                int t_iLength = (t_astrFkNames != null) ? t_astrFkNames.length : 0;

                for  (int t_iColumnIndex = 0;
                          t_iColumnIndex < t_iLength;
                          t_iColumnIndex++)
                {
                    t_strDeleteMethodSuffix = 
                        stringUtils.capitalize(
                            t_astrReferredTables[t_iRefTableIndex],
                            '_');

                    t_sbDeleteWithFkDAODeleteRequest.append(
                        t_DeleteWithFkDAODeleteRequestFormatter.format(
                            new Object[]
                            {
                                t_strReferredTableName,
                                t_strTableName.toLowerCase(),
                                stringUtils.capitalize(
                                    t_astrFkNames[t_iColumnIndex],
                                    '_').toLowerCase()
                        }));

                    t_sbFkJavadoc.append(
                        t_DeleteWithFkPkJavadocFormatter.format(
                            new Object[]
                            {
                                t_astrFkNames[t_iColumnIndex].toLowerCase(),
                                t_astrFkNames[t_iColumnIndex]
                            }));

                    t_sbFkDeclaration.append(
                        t_DeleteWithFkPkDeclarationFormatter.format(
                            new Object[]
                            {
                                metadataTypeManager.getNativeType(
                                    metadataManager.getColumnType(
                                        tableTemplate.getTableName(),
                                        t_astrFkNames[t_iColumnIndex])),
                                t_astrFkNames[t_iColumnIndex].toLowerCase()
                            }));

                    if  (t_iColumnIndex < t_astrFkNames.length - 1)
                    {
                        t_sbFkDeclaration.append(",");
                    }
                }

                t_sbDeleteByFkMethod.append(
                    t_DeleteByFkMethodFormatter.format(
                        new Object[]
                        {
                            t_strTableName,
                            t_sbPkJavadoc,
                            t_strDeleteMethodModifier,
                            t_strDeleteMethodSuffix,
                            t_sbPkDeclaration,
                            stringUtils.capitalize(
                                englishGrammarUtils.getSingular(
                                    t_strTableName.toLowerCase()),
                                '_'),
                            t_sbPkFilterValues,
                            t_sbDeleteWithFkPkValues,
                            t_strTableName.toLowerCase(),
                            t_sbDeleteWithFkDAODeleteRequest,
                            t_sbDeleteWithFkPkValuesDeleteRequest,
                            t_sbFkJavadoc,
                            t_strReferredTableName,
                            t_sbFkDeclaration,
                            t_strReferredTableName
                        }));

                t_sbForeignDAOImports.append(
                    t_ForeignDAOImportsFormatter.format(
                        new Object[]
                        {
                            packageUtils.retrieveBaseDAOPackage(
                                basePackageName),
                            t_strReferredTableName
                        }));
            }
        }

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[]
                {
                    t_strTableName
                }));

        t_sbResult.append(
            t_PackageDeclarationFormatter.format(
                new Object[]{packageName}));

        t_sbResult.append(
            t_ProjectImportFormatter.format(
                new Object[]
                {
                    packageName,
                    packageUtils.retrieveValueObjectPackage(
                        basePackageName),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    packageUtils.retrieveValueObjectFactoryPackage(
                        basePackageName),
                    packageUtils.retrieveBaseDAOFactoryPackage(
                        basePackageName),
                    packageUtils.retrieveDataAccessManagerPackage(
                        basePackageName),
                    t_sbForeignDAOImports
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);
        t_sbResult.append(extraImports);

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    t_strTableName
                }));

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_')
                }));

        t_sbResult.append(
            t_ClassStartFormatter.format(
                new Object[]
                {
                    t_strTableName.toLowerCase(),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_')
                }));


        t_sbResult.append(
            t_ClassConstructorFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_')
                }));

        String[] t_astrPrimaryKeys =
            metadataManager.getPrimaryKey(t_strTableName);

        if  (t_astrPrimaryKeys != null)
        {
            for  (int t_iPkIndex = 0;
                  t_iPkIndex < t_astrPrimaryKeys.length;
                  t_iPkIndex++)
            {
                t_sbPkJavadoc.append(
                    t_FindByPrimaryKeyPkJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                            t_astrPrimaryKeys[t_iPkIndex]
                        }));

                t_sbPkDeclaration.append(
                    t_FindByPrimaryKeyPkDeclarationFormatter.format(
                        new Object[]
                        {
                            metadataTypeManager.getNativeType(
                                metadataManager.getColumnType(
                                    t_strTableName,
                                    t_astrPrimaryKeys[t_iPkIndex])),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                t_sbBuildKeyPkDeclaration.append(
                    t_BuildKeyPkDeclarationFormatter.format(
                        new Object[]
                        {
                            metadataTypeManager.getNativeType(
                                metadataManager.getColumnType(
                                    t_strTableName,
                                    t_astrPrimaryKeys[t_iPkIndex])),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                String t_strPks =
                    t_FindByPrimaryKeyPkFilterValuesFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        });

                t_sbPkFilterValues.append(t_strPks);
                t_sbBuildKeyValues.append(t_strPks);

                t_sbProcessPkAttributes.append(
                    t_ProcessPkAttributesFormatter.format(
                        new Object[]
                        {
                            stringUtils.capitalize(
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                '_')
                        }));

                t_sbDeleteWithFkPkValues.append(
                    t_DeleteWithFkPkValuesFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbDeleteWithFkPkValues.append(",");
                    t_sbPkDeclaration.append(",");
                    t_sbPkFilterValues.append(", ");
                    t_sbBuildKeyPkDeclaration.append(", ");
                    t_sbBuildKeyValues.append(" + ");
                    t_sbProcessPkAttributes.append(",");
                }

                t_sbDeleteWithFkPkValuesDeleteRequest.append(
                    stringUtils.capitalize(
                        t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                        '_'));
            }
        }

        String t_strTableComment =
            metadataManager.getTableComment(
                tableTemplate.getTableName());

        if  (t_strTableComment != null)
        {
            int t_iKeyIndex = t_strTableComment.lastIndexOf("@static");

            if  (t_iKeyIndex >= 0)
            {
                MessageFormat t_FindByStaticFieldMethodFormatter =
                    new MessageFormat(findByStaticFieldMethod);

                MessageFormat t_FindByStaticFieldJavadocFormatter =
                    new MessageFormat(findByStaticFieldJavadoc);

                MessageFormat t_FindByStaticFieldDeclarationFormatter =
                    new MessageFormat(findByStaticFieldDeclaration);

                String t_strDescriptionColumn =
                    t_strTableComment.substring(
                        t_iKeyIndex + "@static".length()).trim();

                String t_strFindByStaticFieldJavadoc =
                    t_FindByStaticFieldJavadocFormatter.format(
                        new Object[]
                        {
                            t_strDescriptionColumn.toLowerCase(),
                            t_strDescriptionColumn
                        });

                String t_strFindByStaticFieldDeclaration =
                    t_FindByStaticFieldDeclarationFormatter.format(
                        new Object[]
                        {
                            metadataTypeManager.getNativeType(
                                metadataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_strDescriptionColumn)),
                            t_strDescriptionColumn.toLowerCase()
                        });

                t_sbResult.append(
                    t_FindByStaticFieldMethodFormatter.format(
                        new Object[]
                        {
                            tableTemplate.getTableName(),
                            t_strDescriptionColumn.toLowerCase(),
                            t_strFindByStaticFieldJavadoc,
                            t_strCapitalizedValueObjectName,
                            stringUtils.capitalize(
                                t_strDescriptionColumn.toLowerCase(), '_'),
                            t_strFindByStaticFieldDeclaration
                        }));
            }
        }

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(t_strTableName);

        int t_iColumnLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        boolean t_bIsPrimaryKey = false;
        boolean t_bManagedExternally = false;
        boolean t_bPks = false;
        
        int t_iNonPkCount = 0;

        for  (int t_iColumnIndex = 0;
                  t_iColumnIndex < t_iColumnLength;
                  t_iColumnIndex++)
        {
            t_bIsPrimaryKey =
                metadataManager.isPartOfPrimaryKey(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);

            t_bManagedExternally =
               metadataManager.isManagedExternally(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);

            if  (   (!t_bPks)
                 && (t_bIsPrimaryKey))
            {
                t_bPks = true;
            }

            if  (   (!t_bIsPrimaryKey)
                 && (!t_bManagedExternally))
            {
                t_iNonPkCount++;
            }
        }

        boolean t_bLastColumn = false;
        int t_iNonPkIndex = 0;
        boolean t_bUpdatePkSeparatorPrinted = false;
        int t_iColumnType;
        boolean t_bAllowsNull;
        String t_strFieldType;
        
        for  (int t_iColumnIndex = 0;
                  t_iColumnIndex < t_iColumnLength;
                  t_iColumnIndex++)
        {
            t_bLastColumn = (t_iColumnIndex == t_iColumnLength - 1);

            t_iColumnType =
                metadataManager.getColumnType(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);

            t_bAllowsNull =
                metadataManager.allowsNull(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);

            t_strFieldType =
                metadataTypeManager.getNativeType(t_iColumnType, t_bAllowsNull);

            t_bIsPrimaryKey =
                metadataManager.isPartOfPrimaryKey(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);
                
            t_bManagedExternally =
                metadataManager.isManagedExternally(
                    t_strTableName,
                    t_astrColumnNames[t_iColumnIndex]);

            if  (   (!t_bIsPrimaryKey)
                 && (!t_bManagedExternally))
            {
                t_iNonPkIndex++;
            }

            t_sbBuildValueObjectValueRetrieval.append(
                t_BuildValueObjectValueRetrieval.format(
                    new Object[]
                    {
                        t_astrColumnNames[t_iColumnIndex].toLowerCase()
                    }));

            t_sbUndigesterPropertyRules.append(
                t_UndigesterPropertyRulesFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_strTableName.toLowerCase()),
                            '_'),
                        stringUtils.unCapitalizeStart(
                            stringUtils.capitalize(
                                t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                '_')),
                    }));

            if  (!t_bManagedExternally)
            {
                if  (t_bAllowsNull)
                {
                    t_strFieldType =
                        metadataTypeManager.getSmartObjectType(t_iColumnType);
                }

                String t_strParameterDeclaration =
                    t_InsertParametersDeclarationFormatter.format(
                        new Object[]
                        {
                            t_strFieldType,
                            t_astrColumnNames[t_iColumnIndex].toLowerCase()
                        });

                t_sbBuildValueObjectParametersDeclaration.append(
                    t_strParameterDeclaration);

                t_sbInsertParametersDeclaration.append(
                    t_strParameterDeclaration);

                if  (!t_bLastColumn)
                {
                    t_sbBuildValueObjectParametersDeclaration.append(", ");
                    t_sbInsertParametersDeclaration.append(", ");
                }
            }
            else 
            {
                String t_strValue =
                    metadataManager.getKeyword(
                        t_strTableName,
                        t_astrColumnNames[t_iColumnIndex]);

                if  (stringValidator.isEmpty(t_strValue))
                {
                    t_strValue =
                        t_astrColumnNames[t_iColumnIndex].toLowerCase();
                }
                else 
                {
                    t_strValue = stringUtils.normalize(t_strValue, '_');
                }
            }

            if  (!t_bIsPrimaryKey)
            {
                t_sbInsertParametersJavadoc.append(
                    t_InsertParametersJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                            t_astrColumnNames[t_iColumnIndex]
                        }));

                t_sbUpdateParametersJavadoc.append(
                    t_UpdateParametersJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                            t_astrColumnNames[t_iColumnIndex]
                        }));

                if  (    (t_bPks)
                      && (!t_bUpdatePkSeparatorPrinted))
                {
                    t_sbUpdateParametersDeclaration.append(",");
                    t_bUpdatePkSeparatorPrinted = true;
                }

                t_sbUpdateParametersDeclaration.append(
                    t_UpdateParametersDeclarationFormatter.format(
                        new Object[]
                        {
                            t_strFieldType,
                            t_astrColumnNames[t_iColumnIndex].toLowerCase()
                        }));

                if  (!t_bLastColumn)
                //if  (t_iNonPkIndex < t_iNonPkCount - 1)
                {
                    t_sbUpdateParametersDeclaration.append(",");
                }

            }

            if  (!t_bLastColumn)
            {
                t_sbBuildValueObjectValueRetrieval.append(",");
            }
        }

        String t_strCapitalizedTableName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        t_sbResult.append(
            t_ClassInternalMethodsFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName,
                    t_sbPkJavadoc,
                    t_sbBuildKeyPkDeclaration,
                    t_sbBuildKeyValues,
                    stringUtils.unCapitalize(
                        t_strCapitalizedTableName, "-"),
                    packageUtils.retrieveValueObjectPackage(
                        basePackageName),
                    t_sbProcessPkAttributes
                }));

        t_sbResult.append(
            t_FindByPrimaryKeyFormatter.format(
                new Object[]
                {
                    t_strTableName,
                    t_sbPkJavadoc,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkDeclaration,
                    t_sbPkFilterValues
                }));

        t_sbResult.append(
            t_BuildValueObjectMethodFormatter.format(
                new Object[]
                {
                    t_strTableName.toUpperCase(),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkJavadoc,
                    t_sbInsertParametersJavadoc,
                    t_sbBuildValueObjectParametersDeclaration,
                    t_sbBuildValueObjectValueRetrieval
                }));

        t_sbResult.append(
            t_InsertMethodFormatter.format(
                new Object[]
                {
                    t_strTableName.toUpperCase(),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkJavadoc,
                    t_sbInsertParametersJavadoc,
                    t_sbInsertParametersDeclaration,
                    t_sbPkFilterValues,
                    t_sbBuildValueObjectValueRetrieval
                }));

        t_sbResult.append(
            t_UpdateMethodFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkJavadoc.toString(),
                    t_sbUpdateParametersJavadoc,
                    t_sbPkDeclaration,
                    t_sbUpdateParametersDeclaration,
                    t_sbPkFilterValues,
                    t_sbBuildValueObjectValueRetrieval
                }));

        t_strDeleteMethodSuffix = "";

        if  (t_bForeignKeys)
        {
            t_strDeleteMethodSuffix = "NoFk";
        }

        t_sbDeleteNoFkMethod.append(
            t_DeleteNoFkMethodFormatter.format(
                new Object[]
                {
                    t_strTableName,
                    t_sbPkJavadoc,
                    t_strDeleteMethodModifier,
                    t_strDeleteMethodSuffix,
                    t_sbPkDeclaration,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkFilterValues,
                    t_sbDeleteWithFkPkValues,
                    t_strTableName.toLowerCase()
                }));

        t_sbResult.append(t_sbDeleteNoFkMethod);

        t_sbDeleteMethod.append(
            t_DeleteMethodFormatter.format(
                new Object[]
                {
                    t_strTableName,
                    t_sbPkJavadoc,
                    t_strDeleteMethodModifier,
                    t_strDeleteMethodSuffix,
                    t_sbPkDeclaration,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    t_sbPkFilterValues,
                    t_sbDeleteWithFkPkValues,
                    t_strTableName.toLowerCase()
                }));

        t_sbResult.append(t_sbDeleteMethod);

        if  (t_bForeignKeys)
        {
            t_sbResult.append(t_sbDeleteByFkMethod);
        }

        t_sbPersistMethod.append(
            t_PersistMethodFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    englishGrammarUtils.getSingular(
                        t_strTableName.toLowerCase()),
                    t_sbUndigesterPropertyRules
                }));

        t_sbResult.append(t_sbPersistMethod);

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
