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
 * Description: Is able to create DAO interfaces for each table in the
 *              persistence model.
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
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.regexpplugin.Helper;
import org.acmsl.commons.regexpplugin.RegexpEngine;
import org.acmsl.commons.regexpplugin.RegexpEngineNotFoundException;
import org.acmsl.commons.regexpplugin.RegexpManager;
import org.acmsl.commons.regexpplugin.RegexpPluginMisconfiguredException;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create DAO interfaces for each table in the
 * persistence model.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class BaseDAOTemplate
    extends     AbstractBaseDAOTemplate
    implements  BaseDAOTemplateDefaults
{
    /**
     * Builds a <code>BaseDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public BaseDAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String valueObjectPackageName,
        final Project project,
        final Task task)
    {
        super(
            tableTemplate,
            metaDataManager,
            customSqlProvider,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            valueObjectPackageName,
            DEFAULT_PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_DELETE_BY_FK_METHOD,
            DEFAULT_DELETE_FK_JAVADOC,
            DEFAULT_DELETE_FK_DECLARATION,
            DEFAULT_CUSTOM_SELECT,
            DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_RETURN_JAVADOC,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_DECLARATION,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getValueObjectPackageName(),
                getProjectImports(),
                getAcmslImports(),
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getFindByPrimaryKeyMethod(),
                getFindByPrimaryKeyPkJavadoc(),
                getFindByPrimaryKeyPkDeclaration(),
                getInsertMethod(),
                getInsertParametersJavadoc(),
                getInsertParametersDeclaration(),
                getUpdateMethod(),
                getUpdateParametersJavadoc(),
                getUpdateParametersDeclaration(),
                getDeleteMethod(),
                getDeletePkJavadoc(),
                getDeletePkDeclaration(),
                getDeleteByFkMethod(),
                getDeleteFkJavadoc(),
                getDeleteFkDeclaration(),
                getCustomSelect(),
                getCustomSelectParameterJavadoc(),
                getCustomSelectParameterDeclaration(),
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomSelectForUpdate(),
                getCustomSelectForUpdateParameterJavadoc(),
                getCustomSelectForUpdateReturnJavadoc(),
                getCustomSelectForUpdateParameterDeclaration(),
                getClassEnd(),
                MetaDataUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
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
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteByFkMethod the delete by fk method.
     * @param deleteFkJavadoc the delete FK javadoc.
     * @param deleteFkDeclaration the delete FK declaration.
     * @param customSelect the custom select template.
     * @param customSelectParameterJavadoc the Javadoc for the parameters of
     * the custom selects.
     * @param customSelectParameterDeclaration the parameter declaration of the
     * custom selects.
     * @param customSelectResultPropertyValues the properties of the result
     * set for custom selects.
     * @param customUpdateOrInsert the custom update template.
     * @param customUpdateOrInsertParameterJavadoc the Javadoc for the
     * parameters of the custom updates or inserts.
     * @param customUpdateOrInsertParameterDeclaration the parameter
     * declaration of the custom updates or inserts.
     * @param customSelectForUpdate the custom-select-for-update template.
     * @param customSelectForUpdateParameterJavadoc the Javadoc for the
     * parameters of the custom-select-for-update operations.
     * @param customSelectForUpdateReturnJavadoc the Javadoc for the
     * return of the custom-select-for-update operations.
     * @param customSelectForUpdateParameterDeclaration the parameter
     * declaration of the custom-select-for-update operations.
     * @param classEnd the class end.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
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
        final String findByPrimaryKeyMethod,
        final String findByPrimaryKeyPkJavadoc,
        final String findByPrimaryKeyPkDeclaration,
        final String insertMethod,
        final String insertParametersJavadoc,
        final String insertParametersDeclaration,
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
        final String classEnd,
        final MetaDataUtils metaDataUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        MessageFormat t_HeaderFormatter = new MessageFormat(header);

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        MessageFormat t_PackageDeclarationFormatter =
            new MessageFormat(packageDeclaration);

        t_sbResult.append(
            t_PackageDeclarationFormatter.format(
                new Object[]{packageName}));

        MessageFormat t_ProjectImportsFormatter =
            new MessageFormat(projectImports);

        t_sbResult.append(
            t_ProjectImportsFormatter.format(
                new Object[]
                {
                    valueObjectPackageName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);

        MessageFormat t_JavadocFormatter = new MessageFormat(javadoc);

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(classDefinition);

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(classStart);

        MessageFormat t_FindByPrimaryKeyFormatter =
            new MessageFormat(findByPrimaryKeyMethod);

        MessageFormat t_FindByPrimaryKeyPkJavadocFormatter =
            new MessageFormat(findByPrimaryKeyPkJavadoc);

        MessageFormat t_FindByPrimaryKeyPkDeclarationFormatter =
            new MessageFormat(findByPrimaryKeyPkDeclaration);

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

        String[] t_astrPrimaryKeys =
            metaDataManager.getPrimaryKeys(tableTemplate.getTableName());

        MessageFormat t_DeleteMethodFormatter =
            new MessageFormat(deleteMethod);

        MessageFormat t_DeleteByFkMethodFormatter =
            new MessageFormat(deleteByFkMethod);

        MessageFormat t_FkJavadocFormatter =
            new MessageFormat(deleteFkJavadoc);
        MessageFormat t_FkDeclarationFormatter =
            new MessageFormat(deleteFkDeclaration);

        StringBuffer t_sbDeleteMethod = new StringBuffer();
        StringBuffer t_sbPkJavadoc = new StringBuffer();
        StringBuffer t_sbPkDeclaration = new StringBuffer();
        StringBuffer t_sbDeleteByFkMethod = new StringBuffer();
        
        StringBuffer t_sbInsertPkJavadoc = new StringBuffer();
        StringBuffer t_sbInsertPkDeclaration = new StringBuffer();

        if  (t_astrPrimaryKeys != null)
        {
            StringBuffer t_sbSelectFields = new StringBuffer();
            StringBuffer t_sbFilterDeclaration = new StringBuffer();
            StringBuffer t_sbFilterValues = new StringBuffer();

            for  (int t_iPkIndex = 0;
                      t_iPkIndex < t_astrPrimaryKeys.length;
                      t_iPkIndex++)
            {
                String t_strPkJavadoc =
                    t_FindByPrimaryKeyPkJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                            t_astrPrimaryKeys[t_iPkIndex]
                        });

                String t_strPkDeclaration =
                    t_FindByPrimaryKeyPkDeclarationFormatter.format(
                        new Object[]
                        {
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_astrPrimaryKeys[t_iPkIndex])),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        });

                t_sbPkJavadoc.append(t_strPkJavadoc);

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbPkDeclaration.append(",");
                }
                t_sbPkDeclaration.append(t_strPkDeclaration);

                if  (!metaDataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrPrimaryKeys[t_iPkIndex]))
                {
                    t_sbInsertPkJavadoc.append(t_strPkJavadoc);
                    t_sbInsertPkDeclaration.append(t_strPkDeclaration);
                }
            }

            t_sbResult.append(
                t_FindByPrimaryKeyFormatter.format(
                    new Object[]
                    {
                        tableTemplate.getTableName(),
                        t_sbPkJavadoc,
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                .toLowerCase()),
                            '_'),
                        t_sbPkDeclaration
                    }));

            t_sbDeleteMethod.append(
                t_DeleteMethodFormatter.format(
                    new Object[]
                    {
                        tableTemplate.getTableName(),
                        t_sbPkJavadoc,
                        t_sbPkDeclaration
                    }));
        }

        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(tableTemplate.getTableName());

        if  (t_astrColumnNames != null)
        {
            StringBuffer t_sbBuildValueObjectRetrieval     =
                new StringBuffer();
            StringBuffer t_sbInsertParametersJavadoc       =
                new StringBuffer();
            StringBuffer t_sbInsertParametersDeclaration   =
                new StringBuffer();
            StringBuffer t_sbInsertParametersSpecification =
                new StringBuffer();
            StringBuffer t_sbUpdateParametersJavadoc       =
                new StringBuffer();
            StringBuffer t_sbUpdateParametersDeclaration   =
                new StringBuffer();
            StringBuffer t_sbUpdateParametersSpecification =
                new StringBuffer();

            for  (int t_iColumnIndex = 0;
                      t_iColumnIndex < t_astrColumnNames.length;
                      t_iColumnIndex++)
            {
                if  (!metaDataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    if  (!metaDataManager.isPrimaryKey(
                             tableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        t_sbInsertParametersJavadoc.append(
                            t_InsertParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbUpdateParametersJavadoc.append(
                            t_UpdateParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbInsertParametersDeclaration.append(
                            t_InsertParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    metaDataUtils.getNativeType(
                                        metaDataManager.getColumnType(
                                            tableTemplate.getTableName(),
                                            t_astrColumnNames[
                                                t_iColumnIndex])),
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        t_sbUpdateParametersDeclaration.append(
                            t_UpdateParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    metaDataUtils.getNativeType(
                                        metaDataManager.getColumnType(
                                            tableTemplate.getTableName(),
                                            t_astrColumnNames[
                                                t_iColumnIndex])),
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                        {
                            t_sbInsertParametersDeclaration.append(",");
                        }
                    }
                    else
                    {
                        t_sbInsertParametersDeclaration.append(",");
                    }
                }
            }

            t_sbResult.append(
                t_InsertMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                    .toLowerCase()),
                            '_'),
                        t_sbInsertPkJavadoc.toString(),
                        t_sbInsertParametersJavadoc,
                        t_sbInsertPkDeclaration,
                        t_sbInsertParametersDeclaration
                    }));

            t_sbResult.append(
                t_UpdateMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                    .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc.toString(),
                        t_sbUpdateParametersJavadoc,
                        t_sbPkDeclaration,
                        t_sbUpdateParametersDeclaration
                    }));

        }

        t_sbResult.append(t_sbDeleteMethod);

        String t_strReferredColumn = null;

        String[] t_astrReferredTables =
            metaDataManager.getReferredTables(
                tableTemplate.getTableName());

        if  (   (t_astrReferredTables != null)
             && (t_astrReferredTables.length > 0))
        {
            for  (int t_iRefTableIndex = 0;
                      t_iRefTableIndex < t_astrReferredTables.length;
                      t_iRefTableIndex++)
            {
                t_strReferredColumn =
                    metaDataManager.getForeignKey(
                        tableTemplate.getTableName(),
                        t_astrReferredTables[t_iRefTableIndex]);

                String t_strReferredTableName =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_astrReferredTables[t_iRefTableIndex]
                                .toLowerCase()),
                        '_');

                String t_strFkJavadoc =
                    t_FkJavadocFormatter.format(
                        new Object[]
                        {
                            t_strReferredColumn.toLowerCase(),
                            t_strReferredColumn
                        });

                String t_strFkDeclaration =
                    t_FkDeclarationFormatter.format(
                        new Object[]
                        {
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_strReferredColumn)),
                            t_strReferredColumn.toLowerCase()
                        });

                t_sbDeleteByFkMethod.append(
                    t_DeleteByFkMethodFormatter.format(
                        new Object[]
                        {
                            stringUtils.capitalize(
                                englishGrammarUtils.getSingular(
                                    t_astrReferredTables[t_iRefTableIndex]
                                        .toLowerCase()),
                                '_'),
                            t_strFkJavadoc,
                            t_strReferredTableName,
                            t_strFkDeclaration
                        }));

            }
        }

        t_sbResult.append(t_sbDeleteByFkMethod);

        t_sbResult.append(buildCustomSql());

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }

    /**
     * Builds the custom templates.
     * @return such generated code.
     */
    protected String buildCustomSql()
    {
        StringBuffer result = new StringBuffer();

        CustomSqlProvider provider = getCustomSqlProvider();

        if  (provider != null)
        {
            result.append(buildCustomSelects(provider, getTableTemplate()));
            result.append(buildCustomUpdates(provider, getTableTemplate()));
            result.append(buildCustomInserts(provider, getTableTemplate()));
            result.append(buildCustomDeletes(provider, getTableTemplate()));
            result.append(
                buildCustomSelectForUpdates(
                    provider, getTableTemplate()));
        }

        return result.toString();
    }

    /**
     * Builds the custom selects.
     * @param provider the <code>CustomSqlProvider</code> instance.
     * @param tableTemplate the table template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomSelects(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate)
    {
        return
            buildCustomSelects(
                provider,
                tableTemplate.getTableName(),
                getCustomSelect(),
                getCustomSelectParameterJavadoc(),
                getCustomSelectParameterDeclaration(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom selects.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param tableName the table name.
     * @param customSelect the custom select.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition tableName != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition daoTemplateUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelects(
        final CustomSqlProvider customSqlProvider,
        final String tableName,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final DAOTemplateUtils daoTemplateUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (t_SqlElement.SELECT.equals(t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metaDataUtils,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomSelect(
                                customSqlProvider,
                                t_SqlElement,
                                customSelect,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                stringUtils));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the parameter templates with given parameters.
     * @param provider the CustomSqlProvider instance.
     * @param parameterRefs the parameter references.
     * @param parameterJavadoc the template.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterSpecification the parameter specification.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition parameterSpecification != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String[] buildParameterTemplates(
        final CustomSqlProvider provider,
        final Collection parameterRefs,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterSpecification,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        String[] result = new String[5];

        StringBuffer t_sbParameterJavadoc = new StringBuffer();
        StringBuffer t_sbParameterDeclaration = new StringBuffer();
        StringBuffer t_sbParameterTypeSpecification = new StringBuffer();
        StringBuffer t_sbParameterValues = new StringBuffer();
        StringBuffer t_sbParameterSpecification = new StringBuffer();

        if  (parameterRefs != null)
        {
            MessageFormat t_ParameterJavadocFormatter =
                new MessageFormat(parameterJavadoc);

            MessageFormat t_ParameterDeclarationFormatter =
                new MessageFormat(parameterDeclaration);

            MessageFormat t_ParameterSpecificationFormatter =
                new MessageFormat(parameterSpecification);

            Iterator t_itParameterRefs =
                parameterRefs.iterator();

            boolean t_bFirstParameter = true;

            while  (t_itParameterRefs.hasNext())
            {
                ParameterRefElement t_ParameterRef =
                    (ParameterRefElement) t_itParameterRefs.next();

                if  (t_ParameterRef != null)
                {
                    ParameterElement t_Parameter =
                        provider.resolveReference(t_ParameterRef);

                    if  (t_Parameter == null)
                    {
                        LogFactory.getLog("custom-sql").warn(
                              "Referenced parameter not found:"
                            + t_ParameterRef.getId());
                    }
                    else
                    {
                        String t_strName = t_Parameter.getName();

                        if  (stringValidator.isEmpty(t_strName))
                        {
                            t_strName =
                                stringUtils.toJavaMethod(
                                    t_Parameter.getId(), '-');
                        }

                        if  (!t_bFirstParameter)
                        {
                            t_sbParameterDeclaration.append(",");
                            t_sbParameterValues.append(",");
                            t_sbParameterSpecification.append(",");
                        }

                        t_sbParameterJavadoc.append(
                            t_ParameterJavadocFormatter.format(
                                new Object[]
                                {
                                    t_strName
                                }));
                        t_sbParameterDeclaration.append(
                            t_ParameterDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_Parameter.getType(),
                                    t_strName
                                }));
                        t_sbParameterSpecification.append(
                            t_ParameterSpecificationFormatter.format(
                                new Object[]
                                {
                                    t_strName
                                }));
                    }
                }

                t_bFirstParameter = false;
            }
        }

        result[0] = t_sbParameterJavadoc.toString();
        result[1] = t_sbParameterDeclaration.toString();
        result[2] = t_sbParameterTypeSpecification.toString();
        result[3] = t_sbParameterValues.toString();
        result[4] = t_sbParameterSpecification.toString();

        return result;
    }

    /**
     * Builds the result property values with given input.
     * @param provider the CustomSqlProvider instance.
     * @param resultRef the result reference.
     * @param resultPropertyValues the template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition resultPropertyValues != null
     */
    protected String buildResultPropertyValues(
        final CustomSqlProvider provider,
        final ResultRefElement resultRef,
        final String resultPropertyValues)
    {
        String result = "";

        if  (resultRef != null)
        {
            ResultElement t_Result = provider.resolveReference(resultRef);

            if  (t_Result == null)
            {
                LogFactory.getLog("custom-sql").warn(
                    "Referenced result not found:"
                    + resultRef.getId());
            }
            else
            {
                result =
                    buildResultPropertyValues(
                        provider,
                        t_Result.getPropertyRefs(),
                        resultPropertyValues,
                        StringUtils.getInstance());
            }
        }

        return result;
    }

    /**
     * Builds the result property values with given input.
     * @param provider the CustomSqlProvider instance.
     * @param propertyRefs the property references.
     * @param resultPropertyValues the template.
     * @param stringUtils the StringUtils instance.
     * @return such generated code.
     * @precondition provider != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     */
    protected String buildResultPropertyValues(
        final CustomSqlProvider provider,
        final Collection propertyRefs,
        final String resultPropertyValues,
        final StringUtils stringUtils)
    {
        StringBuffer result = new StringBuffer();

        if  (propertyRefs != null)
        {
            MessageFormat t_ResultPropertyValuesFormatter =
                new MessageFormat(resultPropertyValues);

            Iterator t_itPropertyRefs = propertyRefs.iterator();

            boolean t_bPreviousWasTheFirst = true;

            while  (t_itPropertyRefs.hasNext())
            {
                PropertyRefElement t_PropertyRef =
                    (PropertyRefElement) t_itPropertyRefs.next();

                if  (t_PropertyRef != null)
                {
                    PropertyElement t_Property =
                        provider.resolveReference(t_PropertyRef);

                    if  (t_Property == null)
                    {
                        LogFactory.getLog("custom-sql").warn(
                              "Referenced property not found:"
                            + t_PropertyRef.getId());
                    }
                    else
                    {
                        result.append(
                            t_ResultPropertyValuesFormatter.format(
                                new Object[]
                                {
                                    stringUtils.capitalize(
                                        t_Property.getType(), '_'),
                                    (   (t_Property.getIndex() > 0)
                                     ?  ("" + t_Property.getIndex())
                                     :  "\"" + t_Property.getColumnName() + "\""),
                                    t_Property.getName()
                                }));

                        if  (t_itPropertyRefs.hasNext())
                        {
                            result.append(",");
                        }
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom select.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customSelect the custom select template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelect(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final StringUtils stringUtils)
    {
        String result = "";

        MessageFormat t_CustomSelectFormatter =
            new MessageFormat(customSelect);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }

        result =
            t_CustomSelectFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    (   (t_Result != null)
                     ?  t_Result.getClassValue()
                     :  "-no-result-type-defined-"),
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom updates.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @return such generated code.
     * @throws RegexpEngineNotFoundException if the defined
     * regexp engine cannot be found.
     * @throws RegexpPluginMisconfiguredException if
     * RegexpPlugin cannot be configured properly.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomUpdates(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                SqlElement.UPDATE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom update or insert.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param tableName the table name.
     * @param type the type of operation.
     * @param customTemplate the custom template.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterTypeSpecification the parameter type specification.
     * @param parameterValues the parameter values.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param helper the Helper instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition tableName != null
     * @precondition type != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition daoTemplateUtils != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     * @precondition helper != null
     */
    protected String buildCustomUpdatesOrInserts(
        final CustomSqlProvider customSqlProvider,
        final String tableName,
        final String type,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterTypeSpecification,
        final String parameterValues,
        final DAOTemplateUtils daoTemplateUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator,
        final Helper helper)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (type.equals(t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metaDataUtils,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomUpdateOrInsert(
                                customSqlProvider,
                                t_SqlElement,
                                customTemplate,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                t_astrParameterTemplates[2],
                                t_astrParameterTemplates[3],
                                stringUtils,
                                helper));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom update or insert.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customTemplate the custom template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param parameterTypeSpecification the parameter type specification.
     * @param parameterValues the generared parameter values.
     * @param stringUtils the StringUtils isntance.
     * @param helper the Helper instance.
     * @return the generated code.
     * @precondition provider != null
     * @param tableName != null
     * @precondition sqlElement != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition stringUtils != null
     * @precondition helper != null
     */
    protected String buildCustomUpdateOrInsert(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterTypeSpecification,
        final String parameterValues,
        final StringUtils stringUtils,
        final Helper helper)
    {
        String result = "";

        MessageFormat t_CustomUpdateOrInsertFormatter =
            new MessageFormat(customTemplate);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }

        result =
            t_CustomUpdateOrInsertFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom inserts.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomInserts(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                SqlElement.INSERT,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom deletes.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomDeletes(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                SqlElement.DELETE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom select-for-update operationss.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate)
    {
        return
            buildCustomSelectForUpdates(
                provider,
                tableTemplate.getTableName(),
                getCustomSelectForUpdate(),
                getCustomSelectForUpdateParameterJavadoc(),
                getCustomSelectForUpdateReturnJavadoc(),
                getCustomSelectForUpdateParameterDeclaration(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom select-for-update operations.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param tableName the table name.
     * @param customSelectForUpdate the custom-select-for-update template.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param customSelectForUpdateReturnJavadoc the Javadoc template
     * of the return.
     * @param parameterDeclaration the parameter declaration.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition tableName != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition daoTemplateUtils != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider customSqlProvider,
        final String tableName,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String customSelectForUpdateReturnJavadoc,
        final String parameterDeclaration,
        final DAOTemplateUtils daoTemplateUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (t_SqlElement.SELECT_FOR_UPDATE.equals(
                                 t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metaDataUtils,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomSelectForUpdate(
                                customSqlProvider,
                                t_SqlElement,
                                customSelectForUpdate,
                                t_astrParameterTemplates[0],
                                customSelectForUpdateReturnJavadoc,
                                t_astrParameterTemplates[1],
                                stringUtils));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom select.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customSelectForUpdate the custom select-for-update template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param returnJavadoc the generated return Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition returnJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelectForUpdate(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String returnJavadoc,
        final String parameterDeclaration,
        final StringUtils stringUtils)
    {
        String result = "";

        MessageFormat t_CustomSelectForUpdateFormatter =
            new MessageFormat(customSelectForUpdate);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }

        String t_strCustomSelectForUpdateSubtemplate = "";

        String t_strReturn = "void";

        String t_strConditionalReturn = "";

        String t_strReturnJavadoc = "";

        result =
            t_CustomSelectForUpdateFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    t_strReturnJavadoc,
                    t_strReturn,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom select-for-update template.
     * @param template the template.
     * @param subtemplate the subtemplate.
     * @param queryObjectName the query object name.
     * @param sqlQuery the sql query.
     * @param resultType the result type.
     * @return the generated code.
     * @precondition template != null
     * @precondition subtemplate != null
     * @precondition customSelectForUpdateWithNoReturn != null
     * @precondition queryObjectName != null
     * @precondition sqlQuery != null
     * @precondition resultType != null
     */
    protected String buildCustomSelectForUpdate(
        final String template,
        final String subtemplate,
        final String queryObjectName,
        final String sqlQuery,
        final String resultType)
    {
        String result = "";

        MessageFormat t_Formatter =
            new MessageFormat(template);

        result =
            t_Formatter.format(
                new Object[]
                {
                    queryObjectName,
                    sqlQuery,
                    resultType,
                    subtemplate
                });

        return result;
    }


    /**
     * Requests a regexp helper to given RegexpManager instance.
     * @param regexpManager the RegexpManager instance.
     * @return the regexp helper.
     * @throws RegexpEngineNotFoundException if the defined
     * regexp engine cannot be found.
     * @throws RegexpPluginMisconfiguredException if
     * RegexpPlugin cannot be configured properly.
     * @precondition regexpManager != null
     */
    protected synchronized Helper createHelper(
        final RegexpManager regexpManager)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return createHelper(regexpManager.getEngine());
    }

    /**
     * Requests a regexp helper to given RegexpEngine instance.
     * @param regexpEngine the RegexpEngine instance.
     * @return the regexp helper.
     * @precondition regexpEngine != null
     */
    protected Helper createHelper(
        final RegexpEngine regexpEngine)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return regexpEngine.createHelper();
    }
}
