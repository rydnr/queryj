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
 * Description: Is able to create mock DAO implementations for each
 *              table in the persistence model.
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
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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
 * Is able to create mock DAO implementations for each
 * table in the persistence model.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class MockDAOTemplate
    extends  AbstractMockDAOTemplate
    implements  MockDAOTemplateDefaults
{
    /**
     * Builds a <code>MockDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public MockDAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final Project project,
        final Task task)
    {
        super(
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            basePackageName,
            repositoryName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_FOREIGN_DAO_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            CLASS_CONSTRUCTOR,
            DEFAULT_CLASS_INTERNAL_METHODS,
            DEFAULT_BUILD_KEY_PK_JAVADOC,
            DEFAULT_BUILD_KEY_PK_DECLARATION,
            DEFAULT_BUILD_KEY_PK_VALUES,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_FILTER_VALUES,
            BUILD_VALUE_OBJECT_METHOD,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_DELETE_WITH_FK_METHOD,
            DEFAULT_DELETE_WITH_FK_PK_JAVADOC,
            DEFAULT_DELETE_WITH_FK_PK_DECLARATION,
            DEFAULT_DELETE_WITH_FK_DAO_DELETE_REQUEST,
            DEFAULT_DELETE_WITH_FK_PK_VALUES,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    protected String generateOutput()
      throws  InvalidTemplateException
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getBasePackageName(),
                getRepositoryName(),
                getProjectImports(),
                getForeignDAOImports(),
                getAcmslImports(),
                getJdkImports(),
                getLoggingImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getClassConstructor(),
                getClassInternalMethods(),
                getBuildKeyPkJavadoc(),
                getBuildKeyPkDeclaration(),
                getBuildKeyPkValues(),
                getFindByPrimaryKeyMethod(),
                getFindByPrimaryKeyPkJavadoc(),
                getFindByPrimaryKeyPkDeclaration(),
                getFindByPrimaryKeyPkFilterValues(),
                getBuildValueObjectMethod(),
                getInsertMethod(),
                getInsertParametersJavadoc(),
                getInsertParametersDeclaration(),
                getUpdateMethod(),
                getUpdateParametersJavadoc(),
                getUpdateParametersDeclaration(),
                getDeleteMethod(),
                getDeletePkJavadoc(),
                getDeletePkDeclaration(),
                getDeleteWithFkMethod(),
                getDeleteWithFkPkJavadoc(),
                getDeleteWithFkPkDeclaration(),
                getDeleteWithFkDAODeleteRequest(),
                getDeleteWithFkPkValues(),
                getClassEnd(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                PackageUtils.getInstance(),
                MetaDataUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param classInternalMethods the class' internal methods.
     * @param buildKeyPkJavadoc the <i>buildKey</i> key pk javadoc.
     * @param buildKeyPkDeclaration the <i>buildKey</i> pk declaration.
     * @param buildKeyPkValues the <i>buildKey</i>  values.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param findByPrimaryKeyPkFilterValues the find by primary key pk
     * filter values.
     * @param buildValueObjectMethod the build value object method.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteFilterDeclaration the delete filter declaration.
     * @param deleteWithFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param classEnd the class end.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return such code.
     * @throws InvalidTemplateException if the template cannot be generated.
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String projectImports,
        final String foreignDAOImports,
        final String acmslImports,
        final String jdkImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String classInternalMethods,
        final String buildKeyPkJavadoc,
        final String buildKeyPkDeclaration,
        final String buildKeyPkValues,
        final String findByPrimaryKeyMethod,
        final String findByPrimaryKeyPkJavadoc,
        final String findByPrimaryKeyPkDeclaration,
        final String findByPrimaryKeyPkFilterValues,
        final String buildValueObjectMethod,
        final String insertMethod,
        final String insertParametersJavadoc,
        final String insertParametersDeclaration,
        final String updateMethod,
        final String updateParametersJavadoc,
        final String updateParametersDeclaration,
        final String deleteMethod,
        final String deletePkJavadoc,
        final String deletePkDeclaration,
        final String deleteWithFkMethod,
        final String deleteWithFkPkJavadoc,
        final String deleteWithFkPkDeclaration,
        final String deleteWithFkDAODeleteRequest,
        final String deleteWithFkPkValues,
        final String classEnd,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator,
        final PackageUtils packageUtils,
        final MetaDataUtils metaDataUtils)
      throws  InvalidTemplateException
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strRepositoryName =
            stringUtils.capitalize(
                repositoryName,
                '_');

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
            new MessageFormat(deleteMethod);

        MessageFormat t_DeleteWithFkMethodFormatter =
            new MessageFormat(deleteWithFkMethod);

        MessageFormat t_DeleteWithFkPkJavadocFormatter =
            new MessageFormat(deleteWithFkPkJavadoc);

        MessageFormat t_DeleteWithFkPkDeclarationFormatter =
            new MessageFormat(deleteWithFkPkDeclaration);

        MessageFormat t_DeleteWithFkDAODeleteRequestFormatter =
            new MessageFormat(deleteWithFkDAODeleteRequest);

        MessageFormat t_DeleteWithFkPkValuesFormatter =
            new MessageFormat(deleteWithFkPkValues);

        StringBuffer t_sbForeignDAOImports = new StringBuffer();
        StringBuffer t_sbPkJavadoc = new StringBuffer();
        StringBuffer t_sbPkDeclaration = new StringBuffer();
        StringBuffer t_sbBuildKeyPkDeclaration = new StringBuffer();
        StringBuffer t_sbBuildKeyValues = new StringBuffer();
        StringBuffer t_sbPkFilterValues = new StringBuffer();
        StringBuffer t_sbUpdateFilter = new StringBuffer();
        StringBuffer t_sbDeleteMethod = new StringBuffer();
        StringBuffer t_sbSelectFields = new StringBuffer();
        StringBuffer t_sbFilterDeclaration = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkValues = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkValuesDeleteRequest = new StringBuffer();

        StringBuffer t_sbDeleteWithFkMethod = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkJavadoc = new StringBuffer();
        StringBuffer t_sbDeleteWithFkPkDeclaration = new StringBuffer();
        StringBuffer t_sbDeleteWithFkDAODeleteRequest = new StringBuffer();
        StringBuffer t_sbDeleteWithFkDAOFkValues = new StringBuffer();

        StringBuffer t_sbBuildValueObjectParametersDeclaration =
            new StringBuffer();

        StringBuffer t_sbBuildValueObjectRetrieval     = new StringBuffer();
        StringBuffer t_sbInsertParametersJavadoc       = new StringBuffer();
        StringBuffer t_sbInsertParametersDeclaration   = new StringBuffer();
        StringBuffer t_sbUpdateParametersJavadoc       = new StringBuffer();
        StringBuffer t_sbUpdateParametersDeclaration   = new StringBuffer();
        StringBuffer t_sbUpdateParametersSpecification = new StringBuffer();

        String t_strDeleteMethodModifier = "public";
        String t_strDeleteMethodSuffix = "";

        boolean t_bForeignKeys = false;

        String[] t_astrReferredTables =
            metaDataManager.getReferredTables(
                t_strTableName);

        if  (t_astrReferredTables != null)
        {
            for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_astrReferredTables.length;
                  t_iRefTableIndex++)
            {
                t_bForeignKeys = true;

                String t_strReferredTableName =
                    stringUtils.capitalize(
                        t_astrReferredTables[t_iRefTableIndex],
                        '_');

                String t_strFkName =
                    metaDataManager.getReferredKey(
                        t_strTableName,
                        t_astrReferredTables[t_iRefTableIndex]);

                t_sbDeleteWithFkDAODeleteRequest.append(
                    t_DeleteWithFkDAODeleteRequestFormatter.format(
                        new Object[]
                        {
                            t_strReferredTableName,
                            t_strTableName.toLowerCase(),
                            stringUtils.capitalize(
                                metaDataManager.getForeignKey(
                                    t_strTableName,
                                    t_astrReferredTables[t_iRefTableIndex])
                                .toLowerCase(),
                                '_')
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
                    packageUtils.retrieveJdbcDAOPackage(
                        basePackageName),
                    packageUtils.retrieveValueObjectPackage(
                        basePackageName),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTableName.toLowerCase()),
                        '_'),
                    packageUtils.retrieveBaseDAOPackage(
                        basePackageName),
                    packageUtils.retrieveBaseDAOFactoryPackage(
                        basePackageName),
                    packageUtils.retrieveDataAccessManagerPackage(
                        basePackageName),
                    t_sbForeignDAOImports
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);
        t_sbResult.append(loggingImports);

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
            metaDataManager.getPrimaryKeys(t_strTableName);

        if  (   (t_astrPrimaryKeys == null)
             || (t_astrPrimaryKeys.length == 0))
        {
            throw
                new InvalidTemplateException(
                      "cannot.generate.mock.dao.template.no.primary.key",
                      new Object[] { t_strTableName });
        }
        else
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
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    t_strTableName,
                                    t_astrPrimaryKeys[t_iPkIndex])),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                t_sbBuildKeyPkDeclaration.append(
                    t_BuildKeyPkDeclarationFormatter.format(
                        new Object[]
                        {
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
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

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbPkDeclaration.append(", ");
                    t_sbPkFilterValues.append(", ");
                    t_sbBuildKeyPkDeclaration.append(", ");
                    t_sbBuildKeyValues.append(" + ");
                }

                t_sbDeleteWithFkPkValues.append(
                    t_DeleteWithFkPkValuesFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                t_sbDeleteWithFkPkValuesDeleteRequest.append(
                    stringUtils.capitalize(
                        t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                        '_'));
            }
        }

        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(t_strTableName);

        if  (t_astrColumnNames != null)
        {
            for  (int t_iColumnIndex = 0;
                  t_iColumnIndex < t_astrColumnNames.length;
                  t_iColumnIndex++)
            {
                t_sbBuildValueObjectRetrieval.append(
                    t_astrColumnNames[t_iColumnIndex].toLowerCase());

                if  (!metaDataManager.isManagedExternally(
                         t_strTableName,
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    String t_strParameterDeclaration =
                        t_InsertParametersDeclarationFormatter.format(
                            new Object[]
                            {
                                metaDataUtils.getNativeType(
                                    metaDataManager.getColumnType(
                                        t_strTableName,
                                        t_astrColumnNames[t_iColumnIndex])),
                                t_astrColumnNames[t_iColumnIndex].toLowerCase()
                            });

                    t_sbBuildValueObjectParametersDeclaration.append(
                        t_strParameterDeclaration);

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbBuildValueObjectParametersDeclaration.append(", ");
                    }

                    t_sbInsertParametersDeclaration.append(
                        t_strParameterDeclaration);
                    t_sbInsertParametersDeclaration.append(", ");
                }
                else 
                {
                    String t_strValue =
                        metaDataManager.getKeyword(
                            t_strTableName,
                            t_astrColumnNames[t_iColumnIndex]);

                    if  (stringValidator.isEmpty(t_strValue))
                    {
                        t_strValue =
                            t_astrColumnNames[t_iColumnIndex].toLowerCase();
                    }
                    else 
                    {
                        t_strValue =
                            stringUtils.normalize(t_strValue, '_');

                    }
                }

                if  (!metaDataManager.isPrimaryKey(
                         t_strTableName,
                         t_astrColumnNames[t_iColumnIndex]))
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

                    t_sbUpdateParametersDeclaration.append(
                        t_UpdateParametersDeclarationFormatter.format(
                            new Object[]
                            {
                                metaDataUtils.getNativeType(
                                    metaDataManager.getColumnType(
                                        t_strTableName,
                                        t_astrColumnNames[t_iColumnIndex])),
                                t_astrColumnNames[t_iColumnIndex].toLowerCase()
                            }));
                }

                if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                {
                    t_sbBuildValueObjectRetrieval.append(", ");
                }
            }

            t_sbResult.append(
                t_ClassInternalMethodsFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_strTableName
                                .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc,
                        t_sbBuildKeyPkDeclaration,
                        t_sbBuildKeyValues
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
                                t_strTableName
                                .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc,
                        t_sbInsertParametersJavadoc,
                        t_sbBuildValueObjectParametersDeclaration,
                        t_sbBuildValueObjectRetrieval
                    }));

            t_sbResult.append(
                t_InsertMethodFormatter.format(
                    new Object[]
                    {
                        t_strTableName.toUpperCase(),
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_strTableName
                                .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc,
                        t_sbInsertParametersJavadoc,
                        t_sbInsertParametersDeclaration,
                        t_sbPkFilterValues,
                        t_sbBuildValueObjectRetrieval
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
                        t_sbBuildValueObjectRetrieval
                    }));


            if  (t_bForeignKeys)
            {
                t_strDeleteMethodModifier = "protected";
                t_strDeleteMethodSuffix = 
                    stringUtils.capitalize(
                        t_strTableName,
                        '_');

                t_sbDeleteWithFkMethod.append(
                    t_DeleteWithFkMethodFormatter.format(
                        new Object[]
                        {
                            stringUtils.capitalize(
                                englishGrammarUtils.getSingular(
                                    t_strTableName),
                                '_'),
                            t_sbPkJavadoc,
                            t_sbPkDeclaration,
                            t_sbDeleteWithFkPkValues,
                            t_strTableName.toLowerCase(),
                            t_sbDeleteWithFkDAODeleteRequest,
                            t_sbDeleteWithFkPkValuesDeleteRequest
                        }));

                t_sbResult.append(t_sbDeleteWithFkMethod);
            }
                
            t_sbDeleteMethod.append(
                t_DeleteMethodFormatter.format(
                    new Object[]
                    {
                        t_strTableName,
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_strTableName
                                .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc,
                        t_sbPkDeclaration,
                        t_strDeleteMethodModifier,
                        t_strDeleteMethodSuffix,
                        t_sbPkFilterValues
                    }));

            t_sbResult.append(t_sbDeleteMethod);
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
