//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create engine-specific DAO interfaces for each
 *              table in the persistence model.
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
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.ResultDecorator;
import org.acmsl.queryj.tools.metadata.SqlDecorator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.AbstractDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
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
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
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
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOTemplate
    extends  AbstractDAOTemplate
    implements  DAOTemplateDefaults
{
    /**
     * An empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

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
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAO.stg");
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
                getCustomSqlProvider(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getRepositoryName(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
                DAOTemplateUtils.getInstance(),
                MetaLanguageUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param metaDataUtils the MetaDataUtils instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @param daoTemplateUtils the DAOTemplateUtils instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition daoTemplateUtils != null
     * @precondition metaLanguageUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils,
        final DAOTemplateUtils daoTemplateUtils,
        final MetaLanguageUtils metaLanguageUtils)
    {
        String result = "";

        String t_strTableName = tableTemplate.getTableName();

        String t_strSingularName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        StringTemplateGroup t_Group = retrieveGroup();
        
        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');
        
        String t_strRepositoryName =
            stringUtils.capitalize(repositoryName, '_');

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                tableTemplate.getTableName().toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        String[] t_astrPrimaryKeys =
            metaDataManager.getPrimaryKey(tableTemplate.getTableName());

        int t_iPrimaryKeysLength =
            (t_astrPrimaryKeys != null) ? t_astrPrimaryKeys.length : 0;

        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(t_strTableName);

        String t_strTableComment =
            metaDataManager.getTableComment(t_strTableName);

        String t_strStaticAttributeName =
            metaLanguageUtils.retrieveStaticAttribute(t_strTableComment);

        String t_strStaticAttributeType = null;
        
        // items have to include the following methods:
        // getName()
        // getNameUppercased()
        // getNameLowercased()
        // getNameCapitalized()
        // getType()
        // getNativeType()
        // getTableName()
        // getUncapitalizedTableName()
        // getAllowsNull()
        Collection t_cPrimaryKeyAttributes =
            retrievePrimaryKeyAttributes(
                t_strTableName, metaDataManager, metaDataUtils);
        
        Collection t_cNonPrimaryKeyAttributes =
            retrieveNonPrimaryKeyAttributes(
                t_strTableName, metaDataManager, metaDataUtils);
        
        Collection t_cForeignKeyAttributes =
            retrieveForeignKeyAttributes(
                t_strTableName, metaDataManager, metaDataUtils);

        // A map of "fk_"referringTableName -> foreign_keys (list of lists)
        Map t_mReferringKeys =
            retrieveReferingKeys(
                t_strTableName, metaDataManager, metaDataUtils);

        Collection t_cAttributes =
            retrieveAttributes(
                t_strTableName, metaDataManager, metaDataUtils);

        Collection t_cExternallyManagedAttributes =
            retrieveExternallyManagedAttributes(
                t_strTableName, metaDataManager, metaDataUtils);
        
        Collection t_cForeignKeys =
            retrieveForeignKeys(
                t_strTableName, metaDataManager, metaDataUtils);

        // items have to include the following methods:
        // getId()
        // getIdAsConstant()
        // getDescription()
        // getName()
        // getResultClass()
        // getNameNormalized()
        // getType()
        // getParams() : Collection of items supporting:
        //   getObjectType())
        //   getName()
        //   getType()
        //   getSqlType() // the java.sql.Types constant
        Collection t_cCustomSelects =
            retrieveCustomSelects(
                t_strTableName, customSqlProvider, daoTemplateUtils);

        // items must contain
        // getId()
        // getIdNormalized()
        // getIdNormalizedUppercased()
        Collection t_cCustomResults =
            retrieveCustomResults(
                t_strTableName, customSqlProvider, daoTemplateUtils);

        fillParameters(
            t_Template,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            t_strTableName,
            t_strCapitalizedValueObjectName,
            engineName,
            engineVersion,
            basePackageName,
            packageUtils.retrieveDAOSubpackage(engineName),
            createTimestamp(),
            defaultThemeUtils.buildDAOImplementationClassName(
                t_strCapitalizedEngine, t_strSingularName),
            defaultThemeUtils.buildDAOClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOPackage(basePackageName),
            t_cPrimaryKeyAttributes,
            t_cNonPrimaryKeyAttributes,
            t_cForeignKeyAttributes,
            t_mReferringKeys,
            t_cAttributes,
            t_cExternallyManagedAttributes,
            t_cForeignKeys,
            t_cCustomSelects,
            t_cCustomResults,
            t_strStaticAttributeName,
            t_strStaticAttributeType,
            t_strRepositoryName,
            stringUtils);
        
        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param tableName the table name.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param timestamp the timestamp.
     * @param className the class name of the DAO.
     * @param baseDAOClassName the class name of the DAO interface.
     * @param baseDAOPackageName the DAO interface package.
     * @param primaryKeyAttributes the primary key attributes.
     * @param nonPrimaryKeyAttributes the ones not part of the primary
     * key..
     * @param foreignKeyAttributes the foreign key attributes.
     * @param referingKeys the foreign keys of other tables pointing
     * to this one. It's expected to be
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @param attributes the attributes.
     * @param externallyManagedAttributes the attributes which are
     * managed externally.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param customSelects the custom selects.
     * @param customResults the custom results.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param tableRepositoryName the table repository.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition tableName != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition timestamp != null
     * @precondition className != null
     * @precondition baseDAOClassName != null
     * @precondition baseDAOPackageName != null
     * @precondition primaryKeyAttributes != null
     * @precondition nonPrimaryKeyAttributes != null
     * @precondition foreignKeyAttributes != null
     * @precondition attributes != null
     * @precondition externallyManagedAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customResults != null
     * @precondition tableRepositoryName != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final StringTemplate template,
        final Integer[] copyrightYears,
        final String tableName,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String subpackageName,
        final String timestamp,
        final String className,
        final String baseDAOClassName,
        final String baseDAOPackageName,
        final Collection primaryKeyAttributes,
        final Collection nonPrimaryKeyAttributes,
        final Collection foreignKeyAttributes,
        final Map referingKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection foreignKeys,
        final Collection customSelects,
        final Collection customResults,
        final String staticAttributeName,
        final String staticAttributeType,
        final String tableRepositoryName,
        final StringUtils stringUtils)
    {
        Map input = new HashMap();

        template.setAttribute("input", input);

        fillCommonParameters(input, tableName, engineName, engineVersion);

        fillJavaHeaderParameters(input, copyrightYears, timestamp);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName);

        fillProjectImportsParameters(
            input,
            basePackageName,
            subpackageName,
            tableName,
            customResults,
            voName,
            foreignKeyAttributes);

        fillClassParameters(
            input,
            voName,
            engineName,
            engineVersion,
            timestamp,
            (staticAttributeName != null),
            tableRepositoryName,
            tableName,
            primaryKeyAttributes,
            nonPrimaryKeyAttributes,
            foreignKeyAttributes,
            referingKeys,
            attributes,
            externallyManagedAttributes,
            foreignKeys,
            customSelects,
            customResults);

        if  (staticAttributeName != null)
        {
            fillStaticTableParameters(
                input,
                voName,
                staticAttributeName,
                staticAttributeType,
                stringUtils);
        }

        input.put("class_name", className);

        input.put("base_dao_class_name", baseDAOClassName);
        input.put("base_dao_package_name",  baseDAOPackageName);
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     */
    protected void fillCommonParameters(
        final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion)
    {
        input.put("table_name",  tableName);
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
    }

    /**
     * Fills the parameters for <code>java_header</code> rule.
     * @param input the input.
     * @param copyrightYears the copyright years.
     * @param timestamp the timestamp.
     * @precondition input != null
     * @precondition copyrightYears != null
     * @precondition timestamp != null
     */
    protected void fillJavaHeaderParameters(
        final Map input,
        final Integer[] copyrightYears,
        final String timestamp)
    {
        input.put("copyright_years", copyrightYears);
        input.put("timestamp", timestamp);
    }

    /**
     * Fills the parameters for <code>package_declaration</code> rule.
     * @param input the input.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     */
    protected void fillPackageDeclarationParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
    }

    /**
     * Fills the parameters for the <code>project_imports</code> rule.
     * @param input the input.
     * @param basePackageName the base package.
     * @param subpackageName the name of the subpackage.
     * @param tableName the table name.
     * @param customResults the custom results.
     * @param voName the name of the value object.
     * @param fkAttributes the foreign-key attributes.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition tableName != null
     * @precondition customResults != null
     * @precondition voName != null
     * @precondition fkAttributes != null
     */
    protected void fillProjectImportsParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName,
        final String tableName,
        final Collection customResults,
        final String voName,
        final Collection fkAttributes)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
        input.put("table_name", tableName);
        input.put("custom_results", customResults);
        input.put("vo_name", voName);
        input.put("fk_attributes", fkAttributes);
    }

    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param customResults the custom results.
     * @param staticTable whether the table is static or not.
     * @param tableRepositoryName the table repository name.
     * @param tableName the table name.
     * @param pkAttributes the primary key attributes.
     * @param nonPkAttributes the ones not part of the primary key.
     * @param fkAttributes the foreign key attributes.
     * @param referingKeys the foreign keys of other tables pointing
     * to this one. It's expected to be
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @param attributes the attributes.
     * @param externallyManagedAttributes the attributes which are
     * managed externally.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param customSelects the custom selects.
     * @precondition input != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     * @precondition tableName != null
     * @precondition pkAttributes != null
     * @precondition nonPkAttributes != null
     * @precondition fkAttributes != null
     * @precondition attributes != null
     * @precondition externallyManagedAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customResults != null
     */
    protected void fillClassParameters(
        final Map input,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final boolean staticTable,
        final String tableRepositoryName,
        final String tableName,
        final Collection pkAttributes,
        final Collection nonPkAttributes,
        final Collection fkAttributes,
        final Map referingKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection foreignKeys,
        final Collection customSelects,
        final Collection customResults)
    {
        input.put("vo_name", voName);
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("timestamp", timestamp);
        input.put("vo_name_uppercased", voName.toUpperCase());

        if  (staticTable)
        {
            input.put("static_table", Boolean.TRUE);
        }

        input.put("tr_name", tableRepositoryName);
        input.put("table_name", tableName);
        input.put("table_name_uppercased", tableName.toUpperCase());
        input.put(
            "table_name_normalized_lowercased",
            normalizeLowercase(tableName, DecorationUtils.getInstance()));

        input.put("pk_attributes", pkAttributes);
        input.put("nonpk_attributes", nonPkAttributes);
        input.put("fk_attributes", fkAttributes);
        input.put("attributes", attributes);
        input.put("foreign_keys", foreignKeys);
        input.put("foreign_keys_by_table", referingKeys);
        input.put("custom_selects", customSelects);
        input.put("custom_results", customResults);
    }

    /**
     * Provides the parameters required by
     * <code>static_table</code> rule.
     * @param input the input.
     * @param voName the value object name.
     * @param staticAttributeName the static attribute name.
     * @param staticAttributeType the static attribute type.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition voName != null
     * @precondition staticAttributeName != null
     * @precondition staticAttributeType != null
     * @precondition stringUtils != null
     */
    protected void fillStaticTableParameters(
        final Map input,
        final String voName,
        final String staticAttributeName,
        final String staticAttributeType,
        final StringUtils stringUtils)
    {
        input.put("vo_name", voName);
        input.put("static_attribute_name", staticAttributeName);
        input.put(
            "static_attribute_name_lowercased",
            staticAttributeName.toLowerCase());
        input.put(
            "static_attribute_name_capitalized",
            stringUtils.capitalize(staticAttributeName, '_'));
        input.put("static_attribute_type", staticAttributeType);
    }

    /**
     * Retrieves the primary key attributes.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the collection of attributes participating in the primary key.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrievePrimaryKeyAttributes(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        return
            buildAttributes(
                metaDataManager.getPrimaryKey(tableName),
                tableName,
                metaDataManager,
                metaDataUtils);
    }
    
    /**
     * Retrieves the non-primary key attributes.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the collection of attributes not participating in the primary
     * key.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrieveNonPrimaryKeyAttributes(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Collection t_cNonPkNames = new ArrayList();
        
        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (!metaDataManager.isPartOfPrimaryKey(
                     tableName, t_astrColumnNames[t_iIndex]))
            {
                t_cNonPkNames.add(t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[]) t_cNonPkNames.toArray(EMPTY_STRING_ARRAY),
                tableName,
                metaDataManager,
                metaDataUtils);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the foreign key attributes.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrieveForeignKeyAttributes(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Collection result = new ArrayList();

        String[][] t_aastrForeignKeys =
            metaDataManager.getForeignKeys(tableName);

        int t_iLength =
            (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            result.add(
                buildAttributes(
                    t_aastrForeignKeys[t_iIndex],
                    tableName,
                    metaDataManager,
                    metaDataUtils));
        }

        return result;
    }

    /**
     * Retrieves the complete attribute list.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the attributes.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrieveAttributes(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        return
            buildAttributes(
                metaDataManager.getColumnNames(tableName),
                tableName,
                metaDataManager,
                metaDataUtils);
    }

    /**
     * Retrieves the externally-managed attributes.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the externally-managed attributes.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrieveExternallyManagedAttributes(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Collection t_cExternallyManagedAttributeNames = new ArrayList();
        
        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (!metaDataManager.isManagedExternally(
                     tableName, t_astrColumnNames[t_iIndex]))
            {
                t_cExternallyManagedAttributeNames.add(
                    t_astrColumnNames[t_iIndex]);
            }
        }

        return
            buildAttributes(
                (String[])
                    t_cExternallyManagedAttributeNames.toArray(
                        EMPTY_STRING_ARRAY),
                tableName,
                metaDataManager,
                metaDataUtils);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the foreign key attributes (a list of attribute lists,
     * grouped by referred tables.
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection retrieveForeignKeys(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Collection result = new ArrayList();

        String[] t_astrReferredTables =
            metaDataManager.getReferredTables(tableName);

        String[] t_astrReferredColumns = null;

        int t_iLength =
            (t_astrReferredTables != null) ? t_astrReferredTables.length : 0;

        Collection t_cCurrentForeignKey = null;

        String t_strReferredTable = null;

        for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_iLength;
                  t_iRefTableIndex++)
        {
            t_strReferredTable =
                t_astrReferredTables[t_iRefTableIndex];

            String[][] t_aastrForeignKeys =
                metaDataManager.getForeignKeys(
                    t_strReferredTable, tableName);

            int t_iFkLength =
                (t_aastrForeignKeys != null) ? t_aastrForeignKeys.length : 0;

            for  (int t_iIndex = 0; t_iIndex < t_iFkLength; t_iIndex++)
            {
                t_cCurrentForeignKey =
                    buildAttributes(
                        t_aastrForeignKeys[t_iIndex],
                        t_strReferredTable,
                    (metaDataManager.allowsNull(
                        t_strReferredTable, t_astrReferredColumns)
                     ?  Boolean.TRUE : Boolean.FALSE),
                    metaDataManager,
                    metaDataUtils);

                // Note: 'result' contains a list of lists.
                result.add(t_cCurrentForeignKey);

                t_cCurrentForeignKey = null;
            }
        }

        return result;
    }

    /**
     * Retrieves the refering keys.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the foreign keys of other tables pointing
     * to this one: 
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Map retrieveReferingKeys(
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Map result = new HashMap();

        String[] t_astrReferingTables =
            metaDataManager.getReferingTables(tableName);

        String[][] t_aastrReferingColumns = null;

        int t_iLength =
            (t_astrReferingTables != null) ? t_astrReferingTables.length : 0;

        Collection t_cReferingFks = null;

        Collection t_cCurrentForeignKey = null;

        String t_strReferingTable = null;

        for  (int t_iRefTableIndex = 0;
                  t_iRefTableIndex < t_iLength;
                  t_iRefTableIndex++)
        {
            t_cReferingFks = new ArrayList();

            t_strReferingTable =
                t_astrReferingTables[t_iRefTableIndex];

            t_aastrReferingColumns =
                metaDataManager.getForeignKeys(
                    t_strReferingTable, tableName);

            int t_iFkCount =
                (t_aastrReferingColumns != null)
                ?  t_aastrReferingColumns.length
                :  0;

            for  (int t_iFk = 0; t_iFk < t_iFkCount; t_iFk++)
            {
                t_cCurrentForeignKey =
                    buildAttributes(
                        t_aastrReferingColumns[t_iFk],
                        t_strReferingTable,
                        (metaDataManager.allowsNull(
                            t_strReferingTable,
                            t_aastrReferingColumns[t_iFk])
                         ?  Boolean.TRUE : Boolean.FALSE),
                        metaDataManager,
                        metaDataUtils);

                // Note: 't_cReferingFks' contains a list of lists.
                t_cReferingFks.add(t_cCurrentForeignKey);

                t_cCurrentForeignKey = null;
            }

            result.put(t_strReferingTable, t_cReferingFks);
        }

        return result;
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        return
            buildAttributes(
                columnNames,
                tableName,
                null,
                metaDataManager,
                metaDataUtils);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param allowsNullAsAWhole whether given column names can be null
     * as a whole or not.
     * @param metaDataManager the <code>DatabaseMetaDataManager</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final DatabaseMetaDataManager metaDataManager,
        final MetaDataUtils metaDataUtils)
    {
        Collection result = new ArrayList();
        
        int t_iLength = (columnNames != null) ? columnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            int t_iType =
                metaDataManager.getColumnType(
                    tableName, columnNames[t_iIndex]);

            String t_strNativeType = metaDataUtils.getNativeType(t_iType);

            boolean t_bAllowsNull = false;

            if  (allowsNullAsAWhole != null)
            {
                t_bAllowsNull = allowsNullAsAWhole.booleanValue();
            }
            else
            {
                t_bAllowsNull =
                    metaDataManager.allowsNull(
                        tableName, columnNames[t_iIndex]);
            }

            String t_strFieldType =
                metaDataUtils.getFieldType(t_iType, t_bAllowsNull);

            boolean t_bManagedExternally =
                metaDataManager.isManagedExternally(
                    tableName, columnNames[t_iIndex]);

            result.add(
                new AttributeDecorator(
                    columnNames[t_iIndex],
                    t_iType,
                    t_strNativeType,
                    t_strFieldType,
                    tableName,
                    t_bManagedExternally,
                    t_bAllowsNull));
        }
        
        return result;
    }

    /**
     * Retrieves the custom selects.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @return the custom selects.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition daoTemplateUtils != null
     */
    protected Collection retrieveCustomSelects(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final DAOTemplateUtils daoTemplateUtils)
    {
        Collection result = new ArrayList();

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            if  (t_itContentIterator != null)
            {
                while  (t_itContentIterator.hasNext())
                {
                    Object t_Content = t_itContentIterator.next();

                    if  (t_Content instanceof SqlElement)
                    {
                        SqlElement t_SqlElement = (SqlElement) t_Content;

                        if  (   (SqlElement.SELECT.equals(
                                     t_SqlElement.getType()))
                             && (daoTemplateUtils.matches(
                                     tableName, t_SqlElement.getDao())))
                        {
                            result.add(
                                new SqlDecorator(
                                    t_SqlElement, customSqlProvider));
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @return the custom results.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition daoTemplateUtils != null
     */
    protected Collection retrieveCustomResults(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final DAOTemplateUtils daoTemplateUtils)
    {
        Collection result = new ArrayList();

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Collection t_cSql = new ArrayList();

            Object t_Content = null;
            SqlElement t_SqlElement = null;
            ResultRefElement t_ResultRefElement = null;
            ResultElement t_ResultElement = null;

            Iterator t_itContentIterator = t_cContents.iterator();

            if  (t_itContentIterator != null)
            {
                while  (t_itContentIterator.hasNext())
                {
                    t_Content = t_itContentIterator.next();

                    if  (t_Content instanceof SqlElement)
                    {
                        t_SqlElement = (SqlElement) t_Content;

                        if  (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao()))
                        {
                            t_ResultRefElement = t_SqlElement.getResultRef();

                            if  (t_ResultRefElement != null)
                            {
                                t_ResultElement =
                                    customSqlProvider.resolveReference(
                                        t_ResultRefElement);

                                if  (t_ResultElement != null)
                                {
                                    result.add(
                                        new ResultDecorator(
                                            t_ResultElement,
                                            customSqlProvider));
                                }
                                else
                                {
                                    try
                                    {
                                        // todo throw something.
                                        LogFactory.getLog("custom-sql").warn(
                                              "Referenced result not found:"
                                            + t_ResultRefElement.getId());
                                    }
                                    catch  (final Throwable throwable)
                                    {
                                        // class-loading problem.
                                    }
                                }
                            }
                            else
                            {
                                try
                                {
                                    // todo throw something.
                                    LogFactory.getLog("custom-sql").warn(
                                          "Referenced result not found:"
                                        + t_ResultRefElement.getId());
                                }
                                catch  (final Throwable throwable)
                                {
                                    // class-loading problem.
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Normalizes given value, in lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }
}
