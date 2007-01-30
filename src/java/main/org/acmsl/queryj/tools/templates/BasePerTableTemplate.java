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
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
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
        final String repositoryName)
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
            repositoryName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected abstract StringTemplateGroup retrieveGroup();

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
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param singularPluralFormConverter the SingularPluralFormConverter instance.
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
            stringUtils.capitalize(
                singularPluralFormConverter.getSingular(
                    tableName.toLowerCase()),
                '_');

        StringTemplateGroup t_Group = retrieveGroup();

        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');

        String t_strRepositoryName =
            stringUtils.capitalize(repositoryName, '_');

        String t_strValueObjectName =
            singularPluralFormConverter.getSingular(
                tableName.toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        String[] t_astrPrimaryKeys =
            metadataManager.getPrimaryKey(tableName);

        int t_iPrimaryKeysLength =
            (t_astrPrimaryKeys != null) ? t_astrPrimaryKeys.length : 0;

        String t_strTableComment =
            metadataManager.getTableComment(tableName);

        String t_strStaticAttributeName =
            metaLanguageUtils.retrieveStaticAttribute(t_strTableComment);

        String t_strStaticAttributeType =
            metadataTypeManager.getFieldType(
                metadataManager.getColumnType(
                    tableName, t_strStaticAttributeName));

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
            metadataUtils.retrievePrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cNonPrimaryKeyAttributes =
            metadataUtils.retrieveNonPrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cForeignKeyAttributes =
            metadataUtils.retrieveForeignKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        // A map of "fk_"referringTableName -> foreign_keys (list of lists)
        Map t_mReferringKeys =
            metadataUtils.retrieveReferingKeys(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cAttributes =
            metadataUtils.retrieveAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cExternallyManagedAttributes =
            metadataUtils.retrieveExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cAllButExternallyManagedAttributes =
            metadataUtils.retrieveAllButExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cLobAttributes =
            metadataUtils.retrieveLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cAllButLobAttributes =
            metadataUtils.retrieveAllButLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        Collection t_cForeignKeys =
            metadataUtils.retrieveForeignKeys(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

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
            t_cNonPrimaryKeyAttributes,
            t_cForeignKeyAttributes,
            t_mReferringKeys,
            t_cAttributes,
            t_cExternallyManagedAttributes,
            t_cAllButExternallyManagedAttributes,
            t_cLobAttributes,
            t_cAllButLobAttributes,
            t_cForeignKeys,
            t_cCustomSelects,
            t_cCustomUpdatesOrInserts,
            t_cCustomSelectsForUpdate,
            t_cCustomResults,
            t_strStaticAttributeName,
            t_strStaticAttributeType,
            t_strRepositoryName,
            metadataManager,
            metadataTypeManager,
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
     * @param tableRepositoryName the table repository.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
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
        final Collection nonPrimaryKeyAttributes,
        final Collection foreignKeyAttributes,
        final Map referingKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final Collection allButLobAttributes,
        final Collection foreignKeys,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final String staticAttributeName,
        final String staticAttributeType,
        final String tableRepositoryName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
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
            customSelects,
            customUpdatesOrInserts,
            customSelectsForUpdate,
            customResults,
            metadataManager);

        if  (staticAttributeName != null)
        {
            fillStaticTableParameters(
                input,
                staticAttributeName,
                staticAttributeType,
                tableName,
                metadataUtils.contain(
                    externallyManagedAttributes,
                    staticAttributeName,
                    tableName),
                metadataManager.getAllowNull(
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
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        input.put("table_name",  tableName);
        input.put(
            "table",
            decorate(tableName, metadataManager, decoratorFactory));
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
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
     * @param customSelects the custom selects.
     * @param customUpdatesOrInserts the custom updates and inserts.
     * @param customSelectsForUpdate the custom selects for update.
     * @param customResults the custom results.
     * @param metadataManager the database metadata manager.
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
        final Collection allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final Collection allButLobAttributes,
        final Collection foreignKeys,
        final String staticAttributeName,
        final String staticAttributeType,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final MetadataManager metadataManager)
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
        input.put("fk_attributes", fkAttributes);
        input.put("attributes", attributes);
        input.put(
            "externally_managed_attributes", externallyManagedAttributes);
        input.put(
            "all_but_externally_managed_attributes",
            allButExternallyManagedAttributes);
        input.put("lob_attributes", lobAttributes);
        input.put("all_but_lob_attributes", allButLobAttributes);
        input.put("foreign_keys", foreignKeys);
        input.put("foreign_keys_by_table", referingKeys);
        input.put("custom_selects", customSelects);
        input.put("custom_updates_or_inserts", customUpdatesOrInserts);
        input.put("custom_selects_for_update", customSelectsForUpdate);
        input.put("custom_results", customResults);
    }

    /**
     * Provides the parameters required by
     * <code>static_table</code> rule.
     * @param input the input.
     * @param staticAttributeName the static attribute name.
     * @param staticAttributeType the static attribute type.
     * @param tableName the table name.
     * @param managedExternally whether the attribute is managed
     * externally.
     * @param allowsNull if the attribute allows nulls.
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
        final boolean managedExternally,
        final boolean allowsNull,
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
                    managedExternally,
                    allowsNull,
                    null),
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
        return
            templateUtils.retrieveCustomSelects(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils);
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
        return
            templateUtils.retrieveCustomUpdatesOrInserts(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils);
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
        return
            templateUtils.retrieveCustomSelectsForUpdate(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils);
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
        return
            templateUtils.retrieveCustomResults(
                tableName,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
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
}
