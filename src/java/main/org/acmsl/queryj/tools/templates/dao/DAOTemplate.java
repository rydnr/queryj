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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create engine-specific DAO interfaces for each
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
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.AbstractDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateDefaults;
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
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create engine-specific DAO interfaces for each
 * table in the persistence model.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTemplate
    extends  AbstractDAOTemplate
    implements  DAOTemplateDefaults
{
    /**
     * Builds a <code>DAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public DAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            tableTemplate,
            metaDataManager,
            customSqlProvider,
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_FOREIGN_DAO_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_ADDITIONAL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JDK_EXTENSION_IMPORTS,
            DEFAULT_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_CLASS_CONSTANTS,
            DEFAULT_CLASS_CONSTRUCTOR,
            DEFAULT_PK_JAVADOC,
            DEFAULT_ATTRIBUTE_JAVADOC,
            DEFAULT_ATTRIBUTE_DECLARATION,
            DEFAULT_ATTRIBUTE_FILTER,
            DEFAULT_STATEMENT_SETTER_CALL,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_SPECIFICATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_SPECIFICATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_CUSTOM_SELECT,
            DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_SELECT_PARAMETER_VALUES,
            DEFAULT_CUSTOM_SELECT_RESULT_PROPERTIES,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_VALUES,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_QUERY_LINE,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_VALUES,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_RESULT_PROPERTIES,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    public String toString()
    {
        return
            toString(
                getTableTemplate(),
                getMetaDataManager(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
                DAOTemplateUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metaDataManager the MetaDataManager instance.
     * @param metaDataUtils the MetaDataUtils instance.
     * @param stringUtils the StringUtils instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @param daoTemplateUtils the DAOTemplateUtils instance.
     * @return such code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition daoTemplateUtils != null
     */
    protected String toString(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils,
        final DAOTemplateUtils daoTemplateUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strRepositoryName =
            stringUtils.capitalize(
                getRepositoryName(),
                '_');

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                tableTemplate.getTableName().toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        MessageFormat t_HeaderFormatter = new MessageFormat(getHeader());

        MessageFormat t_PackageDeclarationFormatter =
            new MessageFormat(getPackageDeclaration());

        MessageFormat t_ProjectImportFormatter =
            new MessageFormat(getProjectImports());

        MessageFormat t_ForeignDAOImportsFormatter =
            new MessageFormat(getForeignDAOImports());

        MessageFormat t_JavadocFormatter = new MessageFormat(getJavadoc());

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(getClassDefinition());

        MessageFormat t_ClassConstantsFormatter =
            new MessageFormat(getClassConstants());

        MessageFormat t_ClassConstructorFormatter =
            new MessageFormat(getClassConstructor());

        MessageFormat t_PkJavadocFormatter =
            new MessageFormat(getPkJavadoc());

        MessageFormat t_AttributeJavadocFormatter =
            new MessageFormat(getAttributeJavadoc());

        MessageFormat t_AttributeDeclarationFormatter =
            new MessageFormat(getAttributeDeclaration());

        MessageFormat t_AttributeFilterFormatter =
            new MessageFormat(getAttributeFilter());

        MessageFormat t_StatementSetterCallFormatter =
            new MessageFormat(getStatementSetterCall());

        MessageFormat t_FindByPrimaryKeyFormatter =
            new MessageFormat(getFindByPrimaryKeyMethod());

        MessageFormat t_InsertMethodFormatter =
            new MessageFormat(getInsertMethod());

        MessageFormat t_InsertParametersSpecificationFormatter =
            new MessageFormat(getInsertParametersSpecification());

        MessageFormat t_UpdateMethodFormatter =
            new MessageFormat(getUpdateMethod());

        MessageFormat t_UpdateParametersSpecificationFormatter =
            new MessageFormat(getUpdateParametersSpecification());

        MessageFormat t_DeleteMethodFormatter =
            new MessageFormat(getDeleteMethod());

        StringBuffer t_sbForeignDAOImports = new StringBuffer();
        StringBuffer t_sbAttributesJavadoc = new StringBuffer();
        StringBuffer t_sbPkJavadoc = new StringBuffer();
        StringBuffer t_sbNonPkJavadoc = new StringBuffer();
        StringBuffer t_sbAttributesDeclaration = new StringBuffer();
        StringBuffer t_sbPkDeclaration = new StringBuffer();
        StringBuffer t_sbNonPkDeclaration = new StringBuffer();
        StringBuffer t_sbPkFilter = new StringBuffer();
        StringBuffer t_sbNonPkFilter = new StringBuffer();
        StringBuffer t_sbPkStatementSetterCall = new StringBuffer();
        StringBuffer t_sbAttributesStatementSetterCall = new StringBuffer();
        StringBuffer t_sbDeleteMethod = new StringBuffer();

        StringBuffer t_sbInsertParametersSpecification = new StringBuffer();
        StringBuffer t_sbUpdateParametersSpecification = new StringBuffer();

        CustomSqlProvider t_CustomSqlProvider = getCustomSqlProvider();

        boolean t_bForeignKeys = false;

        String[] t_astrReferredTables =
            metaDataManager.getReferredTables(
                tableTemplate.getTableName());

        if  (t_astrReferredTables != null)
        {
            for  (int t_iRefTableIndex = 0;
                      t_iRefTableIndex < t_astrReferredTables.length;
                      t_iRefTableIndex++)
            {
                t_bForeignKeys = true;

                String t_strReferredTableName =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_astrReferredTables[t_iRefTableIndex]),
                        '_');

                String t_strFkName =
                    metaDataManager.getReferredKey(
                        tableTemplate.getTableName(),
                        t_astrReferredTables[t_iRefTableIndex]);

                t_sbForeignDAOImports.append(
                    t_ForeignDAOImportsFormatter.format(
                        new Object[]
                        {
                            packageUtils.retrieveBaseDAOPackage(
                                getBasePackageName()),
                            t_strReferredTableName
                                
                        }));
            }
        }

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[]
                {
                    getEngineName(),
                    getEngineVersion(),
                    t_strValueObjectName
                }));

        t_sbResult.append(
            t_PackageDeclarationFormatter.format(
                new Object[]{getPackageName()}));

        t_sbResult.append(
            t_ProjectImportFormatter.format(
                new Object[]
                {
                    packageUtils.retrieveJdbcOperationsPackage(
                        getBasePackageName(),
                        getEngineName(),
                        t_strValueObjectName),
                    t_strCapitalizedValueObjectName,
                    packageUtils.retrieveValueObjectPackage(
                        getBasePackageName()),
                    packageUtils.retrieveBaseDAOPackage(
                        getBasePackageName()),
                    packageUtils.retrieveRdbPackage(
                        getBasePackageName()),
                    packageUtils.retrieveTableRepositoryPackage(
                        getBasePackageName()),
                    t_strRepositoryName,
                    packageUtils.retrieveDataAccessManagerPackage(
                        getBasePackageName()),
                    t_sbForeignDAOImports
                }));

        t_sbResult.append(getAcmslImports());
        t_sbResult.append(getAdditionalImports());
        t_sbResult.append(getJdkImports());
        t_sbResult.append(getJdkExtensionImports());
        t_sbResult.append(getLoggingImports());

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    getEngineName(),
                    getEngineVersion(),
                    t_strValueObjectName
                }));

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    getEngineName(),
                    t_strCapitalizedValueObjectName
                }));

        t_sbResult.append(getClassStart());

        t_sbResult.append(
            t_ClassConstantsFormatter.format(
                new Object[]
                {
                    t_strValueObjectName,
                    t_strValueObjectName.toUpperCase(),
                    t_strCapitalizedValueObjectName
                }));

        t_sbResult.append(
            t_ClassConstructorFormatter.format(
                new Object[]
                {
                    getEngineName(),
                    t_strCapitalizedValueObjectName
                }));

        String[] t_astrPrimaryKeys =
            metaDataManager.getPrimaryKeys(tableTemplate.getTableName());

        if  (t_astrPrimaryKeys != null)
        {
            for  (int t_iPkIndex = 0;
                      t_iPkIndex < t_astrPrimaryKeys.length;
                      t_iPkIndex++)
            {
                t_sbPkFilter.append(
                    t_AttributeFilterFormatter.format(
                        new Object[]
                        {
                            t_strRepositoryName,
                            tableTemplate.getTableName().toUpperCase(),
                            t_astrPrimaryKeys[t_iPkIndex].toUpperCase(),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                t_sbPkJavadoc.append(
                    t_PkJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                            t_astrPrimaryKeys[t_iPkIndex]
                        }));

                if  (t_iPkIndex > 0)
                {
                    t_sbPkDeclaration.append(",");
                }

                t_sbPkDeclaration.append(
                    t_AttributeDeclarationFormatter.format(
                        new Object[]
                        {
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_astrPrimaryKeys[t_iPkIndex])),
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));

                if  (t_iPkIndex > 0)
                {
                    t_sbPkStatementSetterCall.append(",");
                }

                t_sbPkStatementSetterCall.append(
                    t_StatementSetterCallFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        }));
            }
        }

        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(tableTemplate.getTableName());

        if  (t_astrColumnNames != null)
        {
            for  (int t_iColumnIndex = 0;
                      t_iColumnIndex < t_astrColumnNames.length;
                      t_iColumnIndex++)
            {
                if  (!metaDataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    t_sbAttributesJavadoc.append(
                        t_AttributeJavadocFormatter.format(
                            new Object[]
                            {
                                t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                t_astrColumnNames[t_iColumnIndex]
                            }));

                    t_sbAttributesDeclaration.append(
                        t_AttributeDeclarationFormatter.format(
                            new Object[]
                            {
                                metaDataUtils.getNativeType(
                                    metaDataManager.getColumnType(
                                        tableTemplate.getTableName(),
                                        t_astrColumnNames[t_iColumnIndex])),
                                t_astrColumnNames[t_iColumnIndex].toLowerCase()
                            }));

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbAttributesDeclaration.append(",");
                    }

                    t_sbInsertParametersSpecification.append(
                        t_InsertParametersSpecificationFormatter.format(
                            new Object[]
                            {
                                t_strRepositoryName,
                                tableTemplate.getTableName().toUpperCase(),
                                t_astrColumnNames[t_iColumnIndex].toUpperCase(),
                                t_astrColumnNames[t_iColumnIndex].toLowerCase()
                            }));

                    t_sbAttributesStatementSetterCall.append(
                        t_StatementSetterCallFormatter.format(
                            new Object[]
                            {
                                t_astrColumnNames[t_iColumnIndex].toLowerCase()
                            }));

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbAttributesStatementSetterCall.append(",");
                    }

                    t_sbUpdateParametersSpecification.append(
                        t_UpdateParametersSpecificationFormatter.format(
                            new Object[]
                            {
                                t_strRepositoryName,
                                tableTemplate.getTableName().toUpperCase(),
                                t_astrColumnNames[t_iColumnIndex].toUpperCase()
                            }));
                }

                if  (!metaDataManager.isPrimaryKey(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    t_sbNonPkJavadoc.append(
                        t_AttributeJavadocFormatter.format(
                            new Object[]
                            {
                                t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                t_astrColumnNames[t_iColumnIndex]
                            }));

                    t_sbNonPkFilter.append(
                        t_AttributeFilterFormatter.format(
                            new Object[]
                            {
                                t_strRepositoryName,
                                tableTemplate.getTableName().toUpperCase(),
                                t_astrColumnNames[t_iColumnIndex].toUpperCase()
                            }));

                }
            }

            t_sbResult.append(
                t_FindByPrimaryKeyFormatter.format(
                    new Object[]
                    {
                        t_strValueObjectName.toLowerCase(),
                        t_strRepositoryName,
                        tableTemplate.getTableName().toUpperCase(),
                        t_sbPkFilter,
                        t_strValueObjectName,
                        t_sbPkJavadoc,
                        t_strCapitalizedValueObjectName,
                        t_sbPkDeclaration,
                        t_sbPkStatementSetterCall,
                        t_strValueObjectName.toUpperCase()
                    }));

            t_sbResult.append(
                t_InsertMethodFormatter.format(
                    new Object[]
                    {
                        t_strCapitalizedValueObjectName,
                        t_strRepositoryName,
                        tableTemplate.getTableName().toUpperCase(),
                        t_sbInsertParametersSpecification,
                        t_sbAttributesJavadoc,
                        t_sbAttributesDeclaration,
                        t_sbAttributesStatementSetterCall
                    }));

            t_sbResult.append(
                t_UpdateMethodFormatter.format(
                    new Object[]
                    {
                        t_strCapitalizedValueObjectName,
                        t_strRepositoryName,
                        tableTemplate.getTableName().toUpperCase(),
                        t_sbUpdateParametersSpecification,
                        t_sbPkFilter,
                        t_sbAttributesJavadoc,
                        t_sbAttributesDeclaration,
                        t_sbAttributesStatementSetterCall
                    }));
                
            t_sbDeleteMethod.append(
                t_DeleteMethodFormatter.format(
                    new Object[]
                    {
                        t_strCapitalizedValueObjectName,
                        t_strRepositoryName,
                        tableTemplate.getTableName().toUpperCase(),
                        t_sbPkFilter,
                        t_sbPkJavadoc,
                        t_sbPkDeclaration,
                        t_sbPkStatementSetterCall
                    }));

            t_sbResult.append(t_sbDeleteMethod);
        }

        t_sbResult.append(buildCustomSql());

        t_sbResult.append(getClassEnd());

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
            result.append(buildCustomSelects(provider));
            result.append(buildCustomUpdates(provider));
            result.append(buildCustomInserts(provider));
            result.append(buildCustomDeletes(provider));
            result.append(buildCustomSelectForUpdates(provider));
        }

        return result.toString();
    }

    /**
     * Builds the custom selects.
     * @param provider the CustomSqlProvider instance.
     * @return such generated code.
     * @precondition provider != null
     */
    protected String buildCustomSelects(final CustomSqlProvider provider)
    {
        return
            buildCustomSelects(
                provider,
                getCustomSelect(),
                getCustomSelectParameterJavadoc(),
                getCustomSelectParameterDeclaration(),
                getCustomSelectParameterValues(),
                getCustomSelectResultPropertyValues(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom selects.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param customSelect the custom select.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterValues the parameter values.
     * @param resultPropertyValues the result property values.
     * @param stringUtils the StringUtils isntance.
     * @param stringValidator the StringValidator instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelects(
        final CustomSqlProvider customSqlProvider,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String resultPropertyValues,
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

                    if  (t_SqlElement.SELECT.equals(t_SqlElement.getType()))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                parameterValues,
                                stringUtils,
                                stringValidator);

                        String t_strResultPropertyValues =
                            buildResultPropertyValues(
                                customSqlProvider,
                                t_SqlElement.getResultRef(),
                                resultPropertyValues);

                        result.append(
                            buildCustomSelect(
                                customSqlProvider,
                                t_SqlElement,
                                customSelect,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                t_astrParameterTemplates[2],
                                t_strResultPropertyValues,
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
     * @param parameterValues the parameter values.
     * @param stringUtils the StringUtils instance.
     * @param stringValidator the StringValidator instance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String[] buildParameterTemplates(
        final CustomSqlProvider provider,
        final Collection parameterRefs,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        String[] result = new String[3];

        StringBuffer t_sbParameterJavadoc = new StringBuffer();
        StringBuffer t_sbParameterDeclaration = new StringBuffer();
        StringBuffer t_sbParameterValues = new StringBuffer();

        if  (parameterRefs != null)
        {
            MessageFormat t_ParameterJavadocFormatter =
                new MessageFormat(parameterJavadoc);

            MessageFormat t_ParameterDeclarationFormatter =
                new MessageFormat(parameterDeclaration);

            MessageFormat t_ParameterValuesFormatter =
                new MessageFormat(parameterValues);

            Iterator t_itParameterRefs =
                parameterRefs.iterator();

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
                        t_sbParameterValues.append(
                            t_ParameterValuesFormatter.format(
                                new Object[]
                                {
                                    stringUtils.capitalize(
                                        t_Parameter.getType(), '_'),
                                    (   (t_Parameter.getIndex() > 0)
                                     ?  ("" + t_Parameter.getIndex())
                                     :  "\"" + t_Parameter.getColumnName() + "\""),
                                    t_strName
                                }));
                    }
                }
            }
        }

        result[0] = t_sbParameterJavadoc.toString();
        result[1] = t_sbParameterDeclaration.toString();
        result[2] = t_sbParameterValues.toString();

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
     * @param parameterValues the generared parameter values.
     * @param resultPropertyValues the generated result property values.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelect(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String resultPropertyValues,
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
                    (   (t_Result != null)
                     ?  t_Result.getClassValue()
                     :  "-no-result-type-defined-"),
                    parameterJavadoc,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration,
                    sqlElement.getValue(),
                    parameterValues,
                    (   (t_Result != null)
                     ?  stringUtils.extractPackageName(
                            t_Result.getClassValue())
                     :  "-no-result-type-defined-"),
                    resultPropertyValues
                });

        return result;
    }

    /**
     * Builds the custom updates.
     * @param provider the CustomSqlProvider instance.
     * @return such generated code.
     * @throws RegexpEngineNotFoundException if the defined
     * regexp engine cannot be found.
     * @throws RegexpPluginMisconfiguredException if
     * RegexpPlugin cannot be configured properly.
     * @precondition provider != null
     */
    protected String buildCustomUpdates(final CustomSqlProvider provider)
        throws  RegexpEngineNotFoundException,
                RegexpPluginMisconfiguredException
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                SqlElement.UPDATE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterValues(),
                getCustomUpdateOrInsertQueryLine(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom update or insert.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param type the type of operation.
     * @param customTemplate the custom template.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterValues the parameter values.
     * @param queryLine the query line.
     * @param stringUtils the StringUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param helper the Helper instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition type != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @preoncition queryLine != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     * @precondition helper != null
     */
    protected String buildCustomUpdatesOrInserts(
        final CustomSqlProvider customSqlProvider,
        final String type,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String queryLine,
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

                    if  (type.equals(t_SqlElement.getType()))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                parameterValues,
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
                                queryLine,
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
     * @param parameterValues the generared parameter values.
     * @param queryLine the query line.
     * @param stringUtils the StringUtils isntance.
     * @param helper the Helper instance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition queryLine != null
     * @precondition stringUtils != null
     * @precondition helper != null
     */
    protected String buildCustomUpdateOrInsert(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String queryLine,
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
                    sqlElement.getName(),
                    parameterJavadoc,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration,
                    stringUtils.applyToEachLine(
                        stringUtils.removeFirstAndLastBlankLines(
                            helper.replaceAll(
                                sqlElement.getValue(),
                                "\"",
                                "\\\"")),
                        queryLine),
                    parameterValues,
                    (   (t_Result != null)
                     ?  stringUtils.extractPackageName(
                            t_Result.getClassValue())
                     :  "-no-result-type-defined-")
                });

        return result;
    }

    /**
     * Builds the custom inserts.
     * @param provider the CustomSqlProvider instance.
     * @return such generated code.
     * @precondition provider != null
     */
    protected String buildCustomInserts(final CustomSqlProvider provider)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                SqlElement.INSERT,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterValues(),
                getCustomUpdateOrInsertQueryLine(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom deletes.
     * @param provider the CustomSqlProvider instance.
     * @return such generated code.
     * @precondition provider != null
     */
    protected String buildCustomDeletes(final CustomSqlProvider provider)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                SqlElement.DELETE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterValues(),
                getCustomUpdateOrInsertQueryLine(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom select-for-update operationss.
     * @param provider the CustomSqlProvider instance.
     * @return such generated code.
     * @precondition provider != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider provider)
    {
        return
            buildCustomSelectForUpdates(
                provider,
                getCustomSelectForUpdate(),
                getCustomSelectForUpdateParameterJavadoc(),
                getCustomSelectForUpdateParameterDeclaration(),
                getCustomSelectForUpdateParameterValues(),
                getCustomSelectForUpdateResultPropertyValues(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom select-for-update operations.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param customSelectForUpdate the custom select-for-update.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterValues the parameter values.
     * @param resultPropertyValues the result property values.
     * @param stringUtils the StringUtils isntance.
     * @param stringValidator the StringValidator instance.
     * @return such generated code.
     * @precondition customSqlProvider != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider customSqlProvider,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String resultPropertyValues,
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

                    if  (t_SqlElement.SELECT_FOR_UPDATE.equals(
                             t_SqlElement.getType()))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                parameterValues,
                                stringUtils,
                                stringValidator);

                        String t_strResultPropertyValues =
                            buildResultPropertyValues(
                                customSqlProvider,
                                t_SqlElement.getResultRef(),
                                resultPropertyValues);

                        result.append(
                            buildCustomSelectForUpdate(
                                customSqlProvider,
                                t_SqlElement,
                                customSelectForUpdate,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                t_astrParameterTemplates[2],
                                t_strResultPropertyValues,
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
     * @param parameterDeclaration the generated parameter declaration.
     * @param parameterValues the generared parameter values.
     * @param resultPropertyValues the generated result property values.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterValues != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelectForUpdate(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterValues,
        final String resultPropertyValues,
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

        result =
            t_CustomSelectForUpdateFormatter.format(
                new Object[]
                {
                    (   (t_Result != null)
                     ?  t_Result.getClassValue()
                     :  "-no-result-type-defined-"),
                    parameterJavadoc,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration,
                    sqlElement.getValue(),
                    parameterValues,
                    (   (t_Result != null)
                     ?  stringUtils.extractPackageName(
                            t_Result.getClassValue())
                     :  "-no-result-type-defined-"),
                    resultPropertyValues
                });

        return result;
    }

    /**
     * Builds the subtemplate for <i>process connection flags</i>.
     * @param connectionFlags the connection flags.
     * @param processConnectionFlags the subtemplate.
     * @return the generated code.
     * @precondition processConnectionFlags != null
     */
    protected String buildProcessConnectionFlagsSubtemplate(
        final String[] connectionFlags,
        final String processConnectionFlags)
    {
        StringBuffer t_sbResult = new StringBuffer();

        if  (   (connectionFlags != null)
             && (connectionFlags.length > 0))
        {
            MessageFormat t_ProcessConnectionFlagsFormatter =
                new MessageFormat(processConnectionFlags);

            for  (int t_iIndex = 0;
                      t_iIndex < connectionFlags.length;
                      t_iIndex++)
            {
                t_sbResult.append(
                    t_ProcessConnectionFlagsFormatter.format(
                        new Object[]{connectionFlags[t_iIndex]}));
            }
        }

        return t_sbResult.toString();
    }


    /**
     * Builds the subtemplate for <i>process statement flags</i>.
     * @param statementFlags the statement flags.
     * @param processStatementFlags the subtemplate.
     * @param statementFlagsSetters the setter methods for statement flags.
     * @param stringValidator the StringValidator instance.
     * @return the generated code.
     * @precondition processStatementFlags != null
     * @precondition statementFlagsSetters != null
     * @precondition stringValidator != null
     */
    protected String buildProcessStatementFlagsSubtemplate(
        final String[] statementFlags,
        final String processStatementFlags,
        final String[] statementFlagsSetters,
        final StringValidator stringValidator)
    {
        StringBuffer t_sbResult = new StringBuffer();

        if  (   (statementFlags != null)
             && (statementFlags.length > 0))
        {
            MessageFormat t_ProcessStatementFlagsFormatter =
                new MessageFormat(processStatementFlags);

            for  (int t_iIndex = 0;
                      t_iIndex < statementFlags.length;
                      t_iIndex++)
            {
                if  (   (!stringValidator.isEmpty(statementFlags[t_iIndex]))
                     && (!stringValidator.isEmpty(
                             statementFlagsSetters[t_iIndex])))
                {
                    MessageFormat t_CurrentSetterFormatter =
                        new MessageFormat(statementFlagsSetters[t_iIndex]);

                    t_sbResult.append(
                        t_ProcessStatementFlagsFormatter.format(
                            new Object[]
                            {
                                t_CurrentSetterFormatter.format(
                                    new Object[]
                                    {
                                        statementFlags[t_iIndex]
                                    })
                            }));
                }
            }
        }

        return t_sbResult.toString();
    }

    /**
     * Builds the subtemplate for <i>process resultset flags</i>.
     * @param resultSetFlags the resultset flags.
     * @param processResultSetFlags the template.
     * @param processResultSetFlagSubtemplate the subtemplate.
     * @param stringValidator the StringValidator instance.
     * @return the generated code.
     * @precondition processResultSetFlags != null
     * @precondition processResultSetFlagSubtemplate != null
     * @precondition stringValidator != null
     */
    protected String buildProcessResultSetFlagsSubtemplate(
        final String[] resultSetFlags,
        final String processResultSetFlags,
        final String processResultSetFlagSubtemplate,
        final StringValidator stringValidator)
    {
        String result = null;

        if  (   (resultSetFlags != null)
             && (resultSetFlags.length > 0))
        {
            MessageFormat t_ProcessResultSetFlagsFormatter =
                new MessageFormat(processResultSetFlags);

            MessageFormat t_ProcessResultSetFlagSubtemplateFormatter =
                new MessageFormat(processResultSetFlagSubtemplate);

            String[] t_astrResultSetFlags =
                new String[resultSetFlags.length];

            for  (int t_iIndex = 0;
                      t_iIndex < t_astrResultSetFlags.length;
                      t_iIndex++)
            {
                String t_strCurrentFlag = resultSetFlags[t_iIndex];

                if  (!stringValidator.isEmpty(t_strCurrentFlag))
                {
                    t_strCurrentFlag =
                        t_ProcessResultSetFlagSubtemplateFormatter.format(
                            new Object[]
                            {
                                t_strCurrentFlag
                            });
                }

                t_astrResultSetFlags[t_iIndex] = t_strCurrentFlag;
            }

            result =
                t_ProcessResultSetFlagsFormatter.format(
                    t_astrResultSetFlags);
        }

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
