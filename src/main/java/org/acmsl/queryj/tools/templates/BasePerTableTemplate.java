//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: BasePerTableTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-table templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.CachingResultDecorator;
import org.acmsl.queryj.tools.metadata.CachingSqlDecorator;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.ResultDecorator;
import org.acmsl.queryj.tools.metadata.SqlDecorator;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.metadata.TableDecoratorHelper;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
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
import org.apache.commons.logging.Log;

/**
 * Base logic for all per-table templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerTableTemplate
    extends  AbstractBasePerTableTemplate
{
    /**
     * Builds a <code>BasePerTableTemplate</code> using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param jmx whether to support JMX.
     * @param jndiLocation the location of the datasource in JNDI.
     */
    public BasePerTableTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean jmx,
        final String jndiLocation)
    {
        super(
            tableName,
            metadataManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName,
            jmx,
            jndiLocation);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected abstract StringTemplateGroup retrieveGroup();

    /**
     * Retrieves the template cache.
     * @return such instance.
     */
    private TemplateCache getOwnTemplateCache()
    {
        return
            super.immutableGetTemplateCache(
                buildOwnTemplateCacheKey());
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    protected Object buildTemplateCacheKey()
    {
        return buildOwnTemplateCacheKey();
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    private Object buildOwnTemplateCacheKey()
    {
        return "//BasePerTableTemplate//";
    }

    /**
     * Retrieves an item from the cache.
     * @param key the item key.
     * @return the item itself.
     * @precondition key != null
     */
    protected Object getFromCache(final Object key)
    {
        Object result = null;

        TemplateCache t_TemplateCache = getTemplateCache();

        if  (t_TemplateCache != null)
        {
            result = t_TemplateCache.get(key);
        }

        if  (result == null)
        {
            t_TemplateCache = getOwnTemplateCache();

            if  (t_TemplateCache != null)
            {
                result = t_TemplateCache.get(key);
            }
        }

        return result;
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
     * @param header the header.
     * @param metadataManager the metadata manager.
     * @return such code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getTableName(),
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                getCustomSqlProvider(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getRepositoryName(),
                getJmx(),
                header,
                getDecoratorFactory(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                SingularPluralFormConverter.getInstance(),
                DAOTemplateUtils.getInstance(),
                TemplateUtils.getInstance(),
                MetaLanguageUtils.getInstance(),
                MetadataUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param jmx whether to support JMX.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param singularPluralFormConverter the 
     * <code>SingularPluralFormConverter</code> instance.
     * @param daoTemplateUtils the DAOTemplateUtils instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such code.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition singularPluralFormConverter != null
     * @precondition daoTemplateUtils != null
     * @precondition metaLanguageUtils != null
     * @precondition metadataUtils != null
     */
    protected String generateOutput(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean jmx,
        final String header,
        final DecoratorFactory decoratorFactory,
        final StringUtils stringUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils singularPluralFormConverter,
        final DAOTemplateUtils daoTemplateUtils,
        final TemplateUtils templateUtils,
        final MetaLanguageUtils metaLanguageUtils,
        final MetadataUtils metadataUtils)
    {
        String result = "";

        String t_strSingularName =
            getSingularTableName(
                tableName, singularPluralFormConverter, stringUtils);

        StringTemplateGroup t_Group = retrieveGroup();

        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            getCapitalizedEngine(engineName, stringUtils);

        String t_strRepositoryName =
            getCapitalizedRepository(repositoryName, stringUtils);

        String t_strValueObjectName =
            getValueObjectName(tableName, singularPluralFormConverter);

        String t_strCapitalizedValueObjectName =
            getCapitalizedValueObjectName(
                t_strValueObjectName, tableName, stringUtils);

        String[] t_astrPrimaryKey =
            getPrimaryKey(tableName, metadataManager);

        int t_iPrimaryKeyLength =
            (t_astrPrimaryKey != null) ? t_astrPrimaryKey.length : 0;

        String t_strTableComment =
            getTableComment(tableName, metadataManager);

        String t_strStaticAttributeName =
            getStaticAttributeName(
                t_strTableComment, tableName, metaLanguageUtils);

        String t_strStaticAttributeType =
            getStaticAttributeType(
                t_strStaticAttributeName,
                tableName,
                metadataManager,
                metadataTypeManager);

        String t_strStaticAttributeComment =
            getStaticAttributeComment(
                t_strStaticAttributeName, tableName, metadataManager);

        String t_strParentTableName =
            getParentTableName(
                t_strTableComment, tableName, metaLanguageUtils);

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
            getPrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        List t_lNonPrimaryKeyAttributes =
            getNonPrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        Collection t_cForeignKeyAttributes =
            getForeignKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        // A map of "fk_"referringTableName -> foreign_keys (list of lists)
        Map t_mReferringKeys =
            getReferringKeys(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        List t_lAttributes =
            getAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        Collection t_cExternallyManagedAttributes =
            getExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        List t_lAllButExternallyManagedAttributes =
            getAllButExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        Collection t_cLobAttributes =
            getLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        List t_cAllButLobAttributes =
            getAllButLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

        Collection t_cForeignKeys =
            getForeignKeys(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                metadataUtils);

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
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        Collection t_cCustomUpdatesOrInserts =
            retrieveCustomUpdatesOrInserts(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        Collection t_cCustomSelectsForUpdate =
            retrieveCustomSelectsForUpdate(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        // items must contain
        // getId()
        // getIdNormalized()
        // getIdNormalizedUppercased()
        Collection t_cCustomResults =
            retrieveCustomResults(
                tableName,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        fillParameters(
            new HashMap(),
            t_Template,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            tableName,
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
            t_lNonPrimaryKeyAttributes,
            t_cForeignKeyAttributes,
            t_mReferringKeys,
            t_lAttributes,
            t_cExternallyManagedAttributes,
            t_lAllButExternallyManagedAttributes,
            t_cLobAttributes,
            t_cAllButLobAttributes,
            t_cForeignKeys,
            t_cCustomSelects,
            t_cCustomUpdatesOrInserts,
            t_cCustomSelectsForUpdate,
            t_cCustomResults,
            t_strStaticAttributeName,
            t_strStaticAttributeType,
            t_strStaticAttributeComment,
            t_strParentTableName,
            t_strRepositoryName,
            metadataManager,
            metadataTypeManager,
            jmx,
            header,
            decoratorFactory,
            metadataUtils,
            stringUtils);

        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
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
     * @param allButExternallyManagedAttributes all but the attributes which
     * are managed externally.
     * @param allButLobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param customSelects the custom selects.
     * @param customUpdatesOrInserts the custom updates or inserts.
     * @param customSelectsForUpdate the custom selects for update.
     * @param customResults the custom results.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param parentTable the parent table, or <code>null</code> if not
     * part of an ISA relationship.
     * @param tableRepositoryName the table repository.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param jmx whether to support JMX.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
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
     * @precondition allButExternallyManagedAttributes != null
     * @precondition allButLobAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customUpdatesOrInserts != null
     * @precondition customSelectsForUpdate != null
     * @precondition customResults != null
     * @precondition tableRepositoryName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final Map input,
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
        final List nonPrimaryKeyAttributes,
        final Collection foreignKeyAttributes,
        final Map referingKeys,
        final List attributes,
        final Collection externallyManagedAttributes,
        final List allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final List allButLobAttributes,
        final Collection foreignKeys,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final String staticAttributeName,
        final String staticAttributeType,
        final String staticAttributeComment,
        final String parentTable,
        final String tableRepositoryName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final boolean jmx,
        final String header,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils,
        final StringUtils stringUtils)
    {
        template.setAttribute("input", input);

        fillCommonParameters(
            input,
            tableName,
            engineName,
            engineVersion,
            jmx,
            metadataManager,
            decoratorFactory);

        fillJavaHeaderParameters(
            input,header,  copyrightYears, timestamp, metadataManager);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName, metadataManager);

        fillProjectImportsParameters(
            input,
            basePackageName,
            subpackageName,
            tableName,
            customResults,
            voName,
            foreignKeyAttributes,
            metadataManager);

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
            allButExternallyManagedAttributes,
            lobAttributes,
            allButLobAttributes,
            foreignKeys,
            staticAttributeName,
            staticAttributeType,
            parentTable,
            customSelects,
            customUpdatesOrInserts,
            customSelectsForUpdate,
            customResults,
            metadataManager,
            decoratorFactory);

        if  (staticAttributeName != null)
        {
            fillStaticTableParameters(
                input,
                staticAttributeName,
                staticAttributeType,
                tableName,
                staticAttributeComment,
                metadataUtils.contain(
                    externallyManagedAttributes,
                    staticAttributeName,
                    tableName),
                metadataManager.getAllowNull(
                    tableName, staticAttributeName),
                metadataManager.isReadOnly(
                    tableName, staticAttributeName),
                metadataManager.isBoolean(
                    tableName, staticAttributeName),
                metadataManager.getBooleanTrue(
                    tableName, staticAttributeName),
                metadataManager.getBooleanFalse(
                    tableName, staticAttributeName),
                metadataManager.getBooleanNull(
                    tableName, staticAttributeName),
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
        }

        input.put("class_name", className);

        input.put("base_dao_class_name", baseDAOClassName);
        input.put("base_dao_package_name",  baseDAOPackageName);

        // Check for CLOB stuff.
        if  (   (metadataManager.requiresCustomClobHandling())
             && (containsClobs(
                     tableName, metadataManager, metadataTypeManager)))
        {
            input.put("clobHandling", metadataManager.getName());
        }
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param jmx whether to support JMX.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillCommonParameters(
        final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion,
        final boolean jmx,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        input.put("table_name",  tableName);
        input.put(
            "table",
            decorate(tableName, metadataManager, decoratorFactory));
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("jmx", jmx ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Fills the parameters for <code>java_header</code> rule.
     * @param input the input.
     * @param header the header.
     * @param copyrightYears the copyright years.
     * @param timestamp the timestamp.
     * @param metadataManager the database metadata manager.
     * @precondition input != null
     * @precondition copyrightYears != null
     * @precondition timestamp != null
     * @precondition metadataManager != null
     */
    protected void fillJavaHeaderParameters(
        final Map input,
        final String header,
        final Integer[] copyrightYears,
        final String timestamp,
        final MetadataManager metadataManager)
    {
        input.put("copyright_years", copyrightYears);
        input.put("timestamp", timestamp);

        if  (   (header != null)
             && (!input.containsKey("header")))
        {
            input.put("header", processHeader(input, header));
        }
    }

    /**
     * Fills the parameters for <code>package_declaration</code> rule.
     * @param input the input.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param metadataManager the database metadata manager.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition metadataManager != null
     */
    protected void fillPackageDeclarationParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName,
        final MetadataManager metadataManager)
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
     * @param metadataManager the database metadata manager.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition tableName != null
     * @precondition customResults != null
     * @precondition voName != null
     * @precondition fkAttributes != null
     * @precondition metadataManager != null
     */
    protected void fillProjectImportsParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName,
        final String tableName,
        final Collection customResults,
        final String voName,
        final Collection fkAttributes,
        final MetadataManager metadataManager)
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
     * @param allButExternallyManagedAttributes all but the attributes which
     * are managed externally.
     * @param lobAttributes all attributes whose type is Clob or Blob.
     * @param allButLobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param parentTable the parent table, or <code>null</code> if not
     * part of an ISA relationship.
     * @param customSelects the custom selects.
     * @param customUpdatesOrInserts the custom updates and inserts.
     * @param customSelectsForUpdate the custom selects for update.
     * @param customResults the custom results.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
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
     * @precondition allButExternallyManagedAttributes != null
     * @precondition lobAttributes != null
     * @precondition allButLobAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customUpdatesOrInserts != null
     * @precondition customSelectsForUpdate != null
     * @precondition customResults != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
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
        final List nonPkAttributes,
        final Collection fkAttributes,
        final Map referingKeys,
        final List attributes,
        final Collection externallyManagedAttributes,
        final List allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final List allButLobAttributes,
        final Collection foreignKeys,
        final String staticAttributeName,
        final String staticAttributeType,
        final String parentTable,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        input.put("vo_name", voName);
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("timestamp", timestamp);
        input.put("vo_name_uppercased", voName.toUpperCase());
        input.put("vo_name_lowercased", voName.toLowerCase());

        if  (staticTable)
        {
            input.put("static_table", Boolean.TRUE);
        }

        Table t_Table = decorate(tableName, metadataManager, decoratorFactory);

        input.put("table", t_Table);

        input.put("tr_name", tableRepositoryName);
        input.put("tr_name_capitalized", capitalize(tableRepositoryName));
        input.put("tr_name_normalized", normalize(tableRepositoryName));

        input.put("table_name", tableName);
        input.put("table_name_uppercased", tableName.toUpperCase());
        input.put(
            "table_name_normalized_lowercased",
            normalizeLowercase(tableName, DecorationUtils.getInstance()));
        input.put(
            "table_name_capitalized",
            capitalize(tableName, DecorationUtils.getInstance()));

        input.put("pk_attributes", pkAttributes);
        input.put("nonpk_attributes", nonPkAttributes);
        input.put(
            "nonpk_non_readonly_attributes",
            removeReadOnly(nonPkAttributes));
        input.put("fk_attributes", fkAttributes);
        input.put("attributes", attributes);
        input.put("non_readonly_attributes", removeReadOnly(attributes));
        input.put(
            "externally_managed_attributes", externallyManagedAttributes);
        input.put(
            "all_but_externally_managed_attributes",
            allButExternallyManagedAttributes);
        input.put(
            "all_non_readonly_but_externally_managed_attributes",
            removeReadOnly(allButExternallyManagedAttributes));
        input.put("lob_attributes", lobAttributes);
        input.put("all_but_lob_attributes", allButLobAttributes);
        input.put(
            "all_non_readonly_but_lob_attributes",
            removeReadOnly(allButLobAttributes));
        input.put("foreign_keys", foreignKeys);
        input.put("foreign_keys_by_table", referingKeys);
        input.put("custom_selects", customSelects);
        input.put("custom_updates_or_inserts", customUpdatesOrInserts);
        input.put("custom_selects_for_update", customSelectsForUpdate);
        input.put("custom_results", customResults);

        input.put(
            "dynamic_queries",
            (   (containsDynamicSql(customSelects))
             || (containsDynamicSql(customUpdatesOrInserts))
             || (containsDynamicSql(customSelectsForUpdate)))
            ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Provides the parameters required by
     * <code>static_table</code> rule.
     * @param input the input.
     * @param staticAttributeName the static attribute's name.
     * @param staticAttributeType the static attribute's type.
     * @param tableName the table name.
     * @param staticAttributeComment the static attribute's comment.
     * @param managedExternally whether the attribute is managed
     * externally.
     * @param allowsNull if the attribute allows nulls.
     * @param readOnly whether the attribute is read-only.
     * @param isBool whether the attribute is marked as boolean.
     * @param booleanTrue the symbol for <code>true</code> values in boolean attributes.
     * @param booleanFalse the symbol for <code>false</code> values in boolean attributes.
     * @param booleanNull the symbol for <code>null</code> values in boolean attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition input != null
     * @precondition staticAttributeName != null
     * @precondition staticAttributeType != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillStaticTableParameters(
        final Map input,
        final String staticAttributeName,
        final String staticAttributeType,
        final String tableName,
        final String staticAttributeComment,
        final boolean managedExternally,
        final boolean allowsNull,
        final boolean readOnly,
        final boolean isBool,
        final String booleanTrue,
        final String booleanFalse,
        final String booleanNull,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        input.put(
            "static_attribute",
            decoratorFactory.createDecorator(
                new AttributeValueObject(
                    staticAttributeName,
                    metadataTypeManager.getJavaType(staticAttributeType),
                    staticAttributeType,
                    staticAttributeType,
                    tableName,
                    staticAttributeComment,
                    managedExternally,
                    allowsNull,
                    null,
                    readOnly,
                    isBool,
                    booleanTrue,
                    booleanFalse,
                    booleanNull),
                metadataManager));
    }

    /**
     * Retrieves the custom selects.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom selects.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     * @precondition templateUtils != null
     */
    protected Collection retrieveCustomSelects(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final DAOTemplateUtils daoTemplateUtils,
        final TemplateUtils templateUtils)
    {
        Collection result = null;

        Object t_Key = buildCustomSelectsKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                templateUtils.retrieveCustomSelects(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the custom selects.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildCustomSelectsKey(final String tableName)
    {
        return "//BasePerTableTemplate//customSelects--" + tableName;
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom sql.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    protected Collection retrieveCustomUpdatesOrInserts(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final DAOTemplateUtils daoTemplateUtils,
        final TemplateUtils templateUtils)
    {
        Collection result = null;

        Object t_Key = buildCustomUpdatesOrInsertsKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                templateUtils.retrieveCustomUpdatesOrInserts(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the custom updates or inserts.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildCustomUpdatesOrInsertsKey(final String tableName)
    {
        return "//BasePerTableTemplate//customUpdatesOrInserts--" + tableName;
    }

    /**
     * Retrieves the custom selects.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom selects.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    protected Collection retrieveCustomSelectsForUpdate(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final DAOTemplateUtils daoTemplateUtils,
        final TemplateUtils templateUtils)
    {
        Collection result = null;

        Object t_Key = buildCustomSelectsForUpdateKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                templateUtils.retrieveCustomSelectsForUpdate(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the custom selects-for-update.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildCustomSelectsForUpdateKey(final String tableName)
    {
        return "//BasePerTableTemplate//customSelectsForUpdate--" + tableName;
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom results.
     * @precondition tableName != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    protected Collection retrieveCustomResults(
        final String tableName,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final DAOTemplateUtils daoTemplateUtils,
        final TemplateUtils templateUtils)
    {
        Collection result = null;

        Object t_Key = buildCustomResultsKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                templateUtils.retrieveCustomResults(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    decoratorFactory,
                    daoTemplateUtils);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the custom results.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildCustomResultsKey(final String tableName)
    {
        return "//BasePerTableTemplate//customResults--" + tableName;
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
        return decorationUtils.normalizeLowercase(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return such output.
     * @precondition value != null
     */
    protected String capitalize(final String value)
    {
        return capitalize(value, DecorationUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Checks whether given table contains Clob attributes or not.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected boolean containsClobs(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        String[] t_astrColumnNames = metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (metadataTypeManager.isClob(
                     metadataManager.getColumnType(
                         tableName,
                         t_astrColumnNames[t_iIndex])))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated table.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected TableDecorator decorate(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager);
    }

    /**
     * Removes the read-only attributes.
     * @param attributes the attributes.
     * @return the updated list.
     * @precondition attributes != null
     */
    protected List removeReadOnly(final List attributes)
    {
        return removeReadOnly(attributes, TableDecoratorHelper.getInstance());
    }

    /**
     * Removes the read-only attributes.
     * @param attributes the attributes.
     * @param tableDecoratorHelper the <code>TableDecoratorHelper</code> instance.
     * @return the updated list.
     * @precondition attributes != null
     * @precondition tableDecoratorHelper != null
     */
    protected List removeReadOnly(
        final List attributes, final TableDecoratorHelper tableDecoratorHelper)
    {
        return tableDecoratorHelper.removeReadOnly(attributes);
    }

    /**
     * Retrieves the singular table name.
     * @param tableName the table name.
     * @param singularPluralFormConverter the
     * <code>SingularPluralFormConverter</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the singular table name.
     * @precondition tableName != null
     * @precondition singularPluralFormConverter != null
     * @precondition stringUtils != null
     */
    protected String getSingularTableName(
        final String tableName,
        final EnglishGrammarUtils singularPluralFormConverter,
        final StringUtils stringUtils)
    {
        String result = null;

        Object t_Key = buildSingularTableNameKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                stringUtils.capitalize(
                    singularPluralFormConverter.getSingular(
                        tableName.toLowerCase()),
                '_');

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the singular table name.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildSingularTableNameKey(final String tableName)
    {
        return "//BasePerTableTemplate//--" + tableName;
    }

    /**
     * Retrieves the capitalized engine.
     * @param engineName the engine name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such text.
     * @precondition engineName != null
     * @precondition stringUtils != null
     */
    protected String getCapitalizedEngine(
        final String engineName, final StringUtils stringUtils)
    {
        String result = null;

        Object t_Key = buildCapitalizedEngineKey();

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result = stringUtils.capitalize(engineName, '_');

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the capitalized engine.
     * @return such key.
     */
    protected Object buildCapitalizedEngineKey()
    {
        return "//BasePerTableTemplate//capitalizedEngine--";
    }

    /**
     * Retrieves the capitalized repository.
     * @param repositoryName the repository name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such text.
     * @precondition repositoryName != null
     * @precondition stringUtils != null
     */
    protected String getCapitalizedRepository(
        final String repositoryName, final StringUtils stringUtils)
    {
        String result = null;

        Object t_Key = buildCapitalizedRepositoryKey();

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result = stringUtils.capitalize(repositoryName, '_');

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the capitalized repository.
     * @return such key.
     */
    protected Object buildCapitalizedRepositoryKey()
    {
        return "//BasePerTableTemplate//capitalizedRepository--";
    }

    /**
     * Retrieves the value object name.
     * @param tableName the table name.
     * @param singularPluralFormConverter the
     * <code>SingularPluralFormConverter</code> instance.
     * @return such text.
     * @precondition tableName != null
     * @precondition singularPluralFormConverter != null
     */
    protected String getValueObjectName(
        final String tableName,
        final EnglishGrammarUtils singularPluralFormConverter)
    {
        String result = null;

        Object t_Key = buildValueObjectNameKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                singularPluralFormConverter.getSingular(
                    tableName.toLowerCase());

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the value object name.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildValueObjectNameKey(final String tableName)
    {
        return "//BasePerTableTemplate//valueObjectName--" + tableName;
        
    }

    /**
     * Retrieves the capitalized value object name.
     * @param valueObjectName the value object name.
     * @param tableName the table name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such text.
     * @precondition valueObjectName != null
     * @precondition tableName != null
     * @precondition stringUtils != null
     */
    protected String getCapitalizedValueObjectName(
        final String valueObjectName,
        final String tableName,
        final StringUtils stringUtils)
    {
        String result = null;

        Object t_Key = buildCapitalizedValueObjectNameKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result = stringUtils.capitalize(valueObjectName);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the capitalized value object name.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildCapitalizedValueObjectNameKey(
        final String tableName)
    {
        return
            "//BasePerTableTemplate//capitalizedValueObjectName--" + tableName;
    }

    /**
     * Retrieves the primary key.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    protected String[] getPrimaryKey(
        final String tableName, final MetadataManager metadataManager)
    {
        String[] result = null;

        Object t_Key = buildPrimaryKeyKey(tableName);

        result = (String[]) getFromCache(t_Key);

        if  (result == null)
        {
            result = metadataManager.getPrimaryKey(tableName);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the primary key.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildPrimaryKeyKey(final String tableName)
    {
        return "//BasePerTableTemplate//primaryKey--" + tableName;
        
    }

    /**
     * Retrieves the table comment.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such text.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    protected String getTableComment(
        final String tableName, final MetadataManager metadataManager)
    {
        String result = null;

        Object t_Key = buildTableCommentKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result = metadataManager.getTableComment(tableName);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the table comment.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildTableCommentKey(final String tableName)
    {
        return "//BasePerTableTemplate//tableComment--" + tableName;
        
    }

    /**
     * Retrieves the static attribute name, if any.
     * @param tableComment the table comment.
     * @param tableName the table name.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such text.
     * @precondition tableName != null
     * @precondition metaLanguageUtils != null
     */
    protected String getStaticAttributeName(
        final String tableComment,
        final String tableName,
        final MetaLanguageUtils metaLanguageUtils)
    {
        String result = null;

        Object t_Key = buildStaticAttributeNameKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metaLanguageUtils.retrieveStaticAttribute(tableComment);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the static attribute name.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildStaticAttributeNameKey(final String tableName)
    {
        return "//BasePerTableTemplate//staticAttributeName--" + tableName;
        
    }

    /**
     * Retrieves the static attribute type, if any.
     * @param staticAttributeName the static attribute name.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such text.
     * @precondition tableType != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected String getStaticAttributeType(
        final String staticAttributeName,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        String result = null;

        Object t_Key = buildStaticAttributeTypeKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataTypeManager.getFieldType(
                    metadataManager.getColumnType(
                        tableName, staticAttributeName));

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the static attribute type.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildStaticAttributeTypeKey(final String tableName)
    {
        return "//BasePerTableTemplate//staticAttributeType--" + tableName;
        
    }

    /**
     * Retrieves the static attribute comment, if any.
     * @param staticAttributeName the static attribute name.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such text.
     * @precondition tableComment != null
     * @precondition metadataManager != null
     */
    protected String getStaticAttributeComment(
        final String staticAttributeName,
        final String tableName,
        final MetadataManager metadataManager)
    {
        String result = null;

        Object t_Key = buildStaticAttributeCommentKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataManager.getColumnComment(
                    tableName, staticAttributeName);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the static attribute comment.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildStaticAttributeCommentKey(final String tableName)
    {
        return "//BasePerTableTemplate//staticAttributeComment--" + tableName;
        
    }

    /**
     * Retrieves the parent table name, if any.
     * @param tableComment the table comment.
     * @param tableName the table name.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such text.
     * @precondition tableName != null
     * @precondition metaLanguageUtils != null
     */
    protected String getParentTableName(
        final String tableComment,
        final String tableName,
        final MetaLanguageUtils metaLanguageUtils)
    {
        String result = null;

        Object t_Key = buildParentTableNameKey(tableName);

        result = (String) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metaLanguageUtils.retrieveDeclaredParent(tableComment);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the parent table name.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildParentTableNameKey(final String tableName)
    {
        return "//BasePerTableTemplate//parentTableName--" + tableName;
    }

    /**
     * Retrieves the primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Collection getPrimaryKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Collection result = null;

        Object t_Key = buildPrimaryKeyAttributesKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrievePrimaryKeyAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the primary key attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildPrimaryKeyAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//primaryKeyAttributes--" + tableName;
    }

    /**
     * Retrieves the non-primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected List getNonPrimaryKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        List result = null;

        Object t_Key = buildNonPrimaryKeyAttributesKey(tableName);

        result = (List) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveNonPrimaryKeyAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the non primary key attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildNonPrimaryKeyAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//nonPrimaryKeyAttributes--" + tableName;
    }

    /**
     * Retrieves the foreign key attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Collection getForeignKeyAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Collection result = null;

        Object t_Key = buildForeignKeyAttributesKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveForeignKeyAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the foreign key attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildForeignKeyAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//foreignKeyAttributes--" + tableName;
    }

    /**
     * Retrieves the referring keys.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Map getReferringKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Map result = null;

        Object t_Key = buildReferringKeysKey(tableName);

        result = (Map) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveReferingKeys(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the keys for the referring key.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildReferringKeysKey(final String tableName)
    {
        return "//BasePerTableTemplate//referringKeys--" + tableName;
    }

    /**
     * Retrieves the attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected List getAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        List result = null;

        Object t_Key = buildAttributesKey(tableName);

        result = (List) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//attributes--" + tableName + "--" + getClass();
    }

    /**
     * Retrieves the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Collection getExternallyManagedAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Collection result = null;

        Object t_Key = buildExternallyManagedAttributesKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveExternallyManagedAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the externally-managed attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildExternallyManagedAttributesKey(
        final String tableName)
    {
        return
             "//BasePerTableTemplate//externallyManagedAttributes--"
            + tableName + "--" + getClass();
    }

    /**
     * Retrieves all but the externally-managed attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected List getAllButExternallyManagedAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        List result = null;

        Object t_Key = buildAllButExternallyManagedAttributesKey(tableName);

        result = (List) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveAllButExternallyManagedAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the all but the externally-managed attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildAllButExternallyManagedAttributesKey(
        final String tableName)
    {
        return
             "//BasePerTableTemplate//allButExternallyManagedAttributes--"
            + tableName + "--" + getClass();
    }

    /**
     * Retrieves the lob attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Collection getLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Collection result = null;

        Object t_Key = buildLobAttributesKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveLobAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the lob attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildLobAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//lobAttributes--" + tableName + "--" + getClass();
    }

    /**
     * Retrieves all but the lob attributes.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such attributes.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected List getAllButLobAttributes(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        List result = null;

        Object t_Key = buildAllButLobAttributesKey(tableName);

        result = (List) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveAllButLobAttributes(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for all but the lob attributes.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildAllButLobAttributesKey(final String tableName)
    {
        return "//BasePerTableTemplate//allButLobAttributes--" + tableName + "--" + getClass();
    }

    /**
     * Retrieves the foreign keys.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such foreign keys.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     */
    protected Collection getForeignKeys(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils)
    {
        Collection result = null;

        Object t_Key = buildForeignKeysKey(tableName);

        result = (Collection) getFromCache(t_Key);

        if  (result == null)
        {
            result =
                metadataUtils.retrieveForeignKeys(
                    tableName,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory);

            putInCache(t_Key, result);
        }

        return result;
    }

    /**
     * Builds the key for the foreign keys.
     * @param tableName the table name.
     * @return such key.
     * @precondition tableName != null
     */
    protected Object buildForeignKeysKey(final String tableName)
    {
        return "//BasePerTableTemplate//foreignKeys--" + tableName + "--" + getClass();
    }

    /**
     * Checks whether given list contains dynamic SQL or not.
     * @param queries the query list.
     * @return <tt>true</tt> in such case.
     * @precondition queries != null
     */
    protected boolean containsDynamicSql(final Collection queries)
    {
        boolean result = false;

        Iterator t_Iterator = (queries != null) ? queries.iterator() : null;

        if  (t_Iterator != null)
        {
            Object item;

            while  (t_Iterator.hasNext())
            {
                item = t_Iterator.next();

                if  (   (item instanceof Sql)
                     && (((Sql) item).isDynamic()))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
