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
 * Importing some project-specific classes.
 */
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
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.QueryJBuildException;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base logic for all per-table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerTableTemplate
    extends  AbstractBasePerTableTemplate
{
    /**
     * Builds a {@link BasePerTableTemplate} using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
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
        final boolean implementMarkerInterfaces)
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
            implementMarkerInterfaces);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    protected abstract StringTemplateGroup retrieveGroup();

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @return such code.
     * @throws InvalidTemplateException if the template cannot be processed.
     */
    protected String generateOutput(final String header)
        throws InvalidTemplateException
    {
        return generateOutput(header, getMetadataManager());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @param metadataManager the metadata manager.
     * @return such code.
     * @throws InvalidTemplateException if the generation process fails.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, @NotNull final MetadataManager metadataManager)
        throws InvalidTemplateException
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
                getImplementMarkerInterfaces(),
                getDecoratorFactory(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
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
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the {@link DefaultThemeUtils} instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @param daoTemplateUtils the DAOTemplateUtils instance.
     * @param templateUtils the {@link TemplateUtils} instance.
     * @param metaLanguageUtils the {@link MetaLanguageUtils} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return such code.
     * @throws InvalidTemplateException if the generation process fails.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition daoTemplateUtils != null
     * @precondition metaLanguageUtils != null
     * @precondition metadataUtils != null
     */
    protected String generateOutput(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        @NotNull final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final String header,
        final boolean implementMarkerInterfaces,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final StringUtils stringUtils,
        @NotNull final DefaultThemeUtils defaultThemeUtils,
        @NotNull final PackageUtils packageUtils,
        final StringValidator stringValidator,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils,
        final MetaLanguageUtils metaLanguageUtils,
        @NotNull final MetadataUtils metadataUtils)
      throws InvalidTemplateException
    {
        String result = "";

        String t_strSingularName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase()),
                '_');

        @Nullable StringTemplateGroup t_Group = retrieveGroup();

        @Nullable StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');

        String t_strRepositoryName =
            stringUtils.capitalize(repositoryName, '_');

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                tableName.toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        String[] t_astrPrimaryKeys =
            metadataManager.getPrimaryKey(tableName);

        int t_iPrimaryKeysLength =
            (t_astrPrimaryKeys != null) ? t_astrPrimaryKeys.length : 0;

        @Nullable String t_strStaticAttributeName =
            retrieveStaticAttribute(tableName, metadataManager);

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
        @NotNull Collection t_cPrimaryKeyAttributes =
            metadataUtils.retrievePrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cNonPrimaryKeyAttributes =
            metadataUtils.retrieveNonPrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cForeignKeyAttributes =
            metadataUtils.retrieveForeignKeyAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        // A map of "fk_"referringTableName -> foreign_keys (list of lists)
        @NotNull Map t_mReferringKeys =
            metadataUtils.retrieveReferringKeys(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cAttributes =
            metadataUtils.retrieveAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cExternallyManagedAttributes =
            metadataUtils.retrieveExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cAllButExternallyManagedAttributes =
            metadataUtils.retrieveAllButExternallyManagedAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cLobAttributes =
            metadataUtils.retrieveLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        @NotNull Collection t_cAllButLobAttributes =
            metadataUtils.retrieveAllButLobAttributes(
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);

        ForeignKey[] t_aForeignKeys =
            metadataUtils.retrieveForeignKeys(
                tableName,
                metadataManager,
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
        @Nullable Collection t_cCustomSelects =
            retrieveCustomSelects(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        @Nullable Collection t_cCustomUpdatesOrInserts =
            retrieveCustomUpdatesOrInserts(
                tableName,
                customSqlProvider,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        @Nullable Collection t_cCustomSelectsForUpdate =
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
        @Nullable Collection t_cCustomResults =
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
                Integer.valueOf(retrieveCurrentYear())
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
            t_aForeignKeys,
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
            implementMarkerInterfaces,
            decoratorFactory,
            metadataUtils,
            stringUtils);

        try
        {
            result = t_Template.toString();
        }
        catch (@NotNull final IllegalArgumentException invalidTemplate)
        {
            throw
                new InvalidTemplateException(
                    "invalid.per.table.template",
                    new Object[]
                    {
                        t_Template.getName(),
                        t_Group.getName(),
                        tableName
                    },

                    invalidTemplate);
        }

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
     * @param referringKeys the foreign keys of other tables pointing
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
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @param stringUtils the {@link StringUtils} instance.
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
        @NotNull final Map input,
        @NotNull final StringTemplate template,
        final Integer[] copyrightYears,
        @NotNull final String tableName,
        @NotNull final String voName,
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
        @NotNull final Map referringKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final Collection allButLobAttributes,
        final ForeignKey[] foreignKeys,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        @Nullable final String staticAttributeName,
        final String staticAttributeType,
        @NotNull final String tableRepositoryName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final String header,
        final boolean implementMarkerInterfaces,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final MetadataUtils metadataUtils,
        final StringUtils stringUtils)
    {
        template.setAttribute("input", input);

        fillCommonParameters(
            input,
            tableName,
            engineName,
            engineVersion,
            implementMarkerInterfaces,
            metadataManager,
            decoratorFactory);

        fillJavaHeaderParameters(
            input, header, copyrightYears, timestamp, metadataManager);

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
            referringKeys,
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
        if  (   (metadataManager.requiresCustomLobHandling())
             && (containsLobs(
                     tableName, metadataManager, metadataTypeManager)))
        {
            input.put("lobHandling", metadataManager.getName());
        }
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
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
        @NotNull final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion,
        final boolean implementMarkerInterfaces,
        final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        input.put("table_name",  tableName);
        input.put(
            "table",
            decorate(tableName, metadataManager, decoratorFactory));
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put(
            "implement_markers",
            (implementMarkerInterfaces) ? Boolean.TRUE : Boolean.FALSE);
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
        @NotNull final Map input,
        @Nullable final String header,
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
        @NotNull final Map input,
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
        @NotNull final Map input,
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
     * @param referringKeys the foreign keys of other tables pointing
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
        @NotNull final Map input,
        @NotNull final String voName,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final boolean staticTable,
        @NotNull final String tableRepositoryName,
        @NotNull final String tableName,
        final Collection pkAttributes,
        final Collection nonPkAttributes,
        final Collection fkAttributes,
        @NotNull final Map referringKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final Collection allButLobAttributes,
        final ForeignKey[] foreignKeys,
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
        input.put("foreign_keys_by_table", referringKeys);
        Collection t_cReferringTables = referringKeys.keySet();
        input.put("referring_tables", t_cReferringTables);
        input.put("referring_vo_names", toVoNames(t_cReferringTables));
        input.put(
            "own_foreign_keys",
            buildOwnForeignKeyList(
                tableName, t_cReferringTables, referringKeys));
        //debugReferringKeys(t_cReferringTables, referringKeys, foreignKeys);
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
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition input != null
     * @precondition staticAttributeName != null
     * @precondition staticAttributeType != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillStaticTableParameters(
        @NotNull final Map input,
        final String staticAttributeName,
        final String staticAttributeType,
        final String tableName,
        final boolean managedExternally,
        final boolean allowsNull,
        final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory)
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
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     * @precondition templateUtils != null
     */
    @Nullable
    protected Collection retrieveCustomSelects(
        final String tableName,
        @Nullable final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        @Nullable Collection result = null;

        if  (customSqlProvider != null)
        {
            result =
                templateUtils.retrieveCustomSelects(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);
        }

        if  (result == null)
        {
            result = new ArrayList();
        }

        return result;
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
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    @Nullable
    protected Collection retrieveCustomUpdatesOrInserts(
        final String tableName,
        @Nullable final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        @Nullable Collection result = null;

        if  (customSqlProvider != null)
        {
            result =
                templateUtils.retrieveCustomUpdatesOrInserts(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);
        }

        if  (result == null)
        {
            result = new ArrayList();
        }

        return result;
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
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    @Nullable
    protected Collection retrieveCustomSelectsForUpdate(
        final String tableName,
        @Nullable final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        @Nullable Collection result = null;

        if  (customSqlProvider != null)
        {
            result =
                templateUtils.retrieveCustomSelectsForUpdate(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    metadataTypeManager,
                    decoratorFactory,
                    daoTemplateUtils);
        }

        if  (result == null)
        {
            result = new ArrayList();
        }

        return result;
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
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition daoTemplateUtils != null
     * @precondition templateUtils != null
     */
    @Nullable
    protected Collection retrieveCustomResults(
        final String tableName,
        @Nullable final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        @Nullable Collection result = null;

        if  (customSqlProvider != null)
        {
            result =
                templateUtils.retrieveCustomResults(
                    tableName,
                    customSqlProvider,
                    metadataManager,
                    decoratorFactory,
                    daoTemplateUtils);
        }

        if  (result == null)
        {
            result = new ArrayList();
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
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return such output.
     * @precondition value != null
     */
    protected String capitalize(@NotNull final String value)
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
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Checks whether given table contains Lob attributes or not.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected boolean containsLobs(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        String[] t_astrColumnNames = metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (metadataTypeManager.isLob(
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
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager);
    }

    /**
     * Retrieves the static attribute.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such attribute, from the table's comment (@static keyword).
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @Nullable
    protected String retrieveStaticAttribute(
        final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            retrieveStaticAttribute(
                tableName,
                metadataManager,
                MetaLanguageUtils.getInstance());
    }

    /**
     * Retrieves the static attribute.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such attribute, from the table's comment (@static keyword).
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metaLanguageUtils != null
     */
    @Nullable
    protected String retrieveStaticAttribute(
        final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetaLanguageUtils metaLanguageUtils)
    {
        return
            metaLanguageUtils.retrieveStaticAttribute(
                tableName, metadataManager);
    }

    /**
     * Converts given values to their VO names.
     * @param collection the values to convert.
     * @return such converted values.
     * @precondition collection != null
     */
    @NotNull
    protected Collection toVoNames(final Collection collection)
    {
        return
            toVoNames(
                collection,
                DecorationUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Converts given values to their VO names.
     * @param collection the values to convert.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such converted values.
     * @precondition collection != null
     * @precondition decorationUtils != null
     * @precondition englishGrammarUtils != null
     */
    @NotNull
    protected Collection toVoNames(
        @Nullable final Collection collection,
        @NotNull final DecorationUtils decorationUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        @NotNull Collection result = new ArrayList();

        @Nullable Iterator t_Iterator =
            (collection != null) ? collection.iterator() : null;

        if  (t_Iterator != null)
        {
            while  (t_Iterator.hasNext())
            {
                result.add(
                    toVoName(
                        "" + t_Iterator.next(),
                        decorationUtils,
                        englishGrammarUtils));
            }
        }

        return result;
    }

    /**
     * Converts given value to its VO name.
     * @param value the value to convert.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such converted value.
     * @precondition value != null
     * @precondition decorationUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String toVoName(
        @NotNull final String value,
        @NotNull final DecorationUtils decorationUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            decorationUtils.capitalize(
                englishGrammarUtils.getSingular(value.toLowerCase()));
    }

    /**
     * Builds the list of the foreign keys starting and ending on given table.
     * @param tableName the table name.
     * @param referringTables the referring tables.
     * @param referringKeys the referring keys.
     * @return such map.
     * @precondition foreignKeys != null
     * @precondition tableName != null
     */
    @NotNull
    protected List buildOwnForeignKeyList(
        final String tableName,
        @Nullable final Collection referringTables,
        @NotNull final Map referringKeys)
    {
        @NotNull List result = new ArrayList();

        @Nullable Iterator t_Iterator =
            (referringTables != null) ? referringTables.iterator() : null;

        if  (t_Iterator != null)
        {
            String t_strReferringTable;
            ForeignKey[] t_aFks;
            int t_iCount;
            boolean t_bOwn;

            while  (t_Iterator.hasNext())
            {
                t_strReferringTable = "" + t_Iterator.next();

                t_aFks =
                    (ForeignKey[]) referringKeys.get(t_strReferringTable);

                t_bOwn = t_strReferringTable.equals(tableName);

                t_iCount = (t_aFks != null) ? t_aFks.length : 0;

                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    result.add((t_bOwn) ? Boolean.TRUE : null);
                }
            }
        }

        return result;
    }

    /**
     * Debugs the referringTable->foreignKeys map.
     * @param referringTables the referring tables.
     * @param map the map.
     * @param foreignKeys the foreign keys.
     * @precondition referringTables != null
     * @precondition map != null
     */
    protected void debugReferringKeys(
        @Nullable final Collection referringTables,
        @NotNull final Map map,
        @Nullable final ForeignKey[] foreignKeys)
    {
        org.apache.commons.logging.Log t_Log =
            org.acmsl.commons.logging.UniqueLogFactory.getLog(
                BasePerTableTemplate.class);

        @Nullable Iterator t_Iterator =
            (referringTables != null)
            ? referringTables.iterator() : null;

        StringBuffer t_sbMessage;

        if  (t_Iterator != null)
        {
            String t_strReferringTable;
            ForeignKey[] t_aForeignKeys;
            t_sbMessage = new StringBuffer();

            while  (t_Iterator.hasNext())
            {
                t_strReferringTable = "" + t_Iterator.next();
                t_aForeignKeys = (ForeignKey[]) map.get(t_strReferringTable);

                t_sbMessage.append(t_strReferringTable);
                t_sbMessage.append("->");

                for  (int t_iIndex = 0; t_iIndex < t_aForeignKeys.length; t_iIndex++)
                {
                    t_sbMessage.append("[");
                    t_sbMessage.append(
                        concat(t_aForeignKeys[t_iIndex].getAttributes(), ","));

                    t_sbMessage.append("]@");
                    t_sbMessage.append(
                        "" + t_aForeignKeys[t_iIndex].hashCode());
                }
            }
            t_Log.info("Referring keys: " + t_sbMessage);
        }

        if  (foreignKeys != null)
        {
            t_sbMessage = new StringBuffer();

            for  (int t_iIndex = 0; t_iIndex < foreignKeys.length; t_iIndex++)
            {
                t_sbMessage.append("[");
                t_sbMessage.append(
                    concat(foreignKeys[t_iIndex].getAttributes(), ","));

                t_sbMessage.append("]@");
                t_sbMessage.append(
                    "" + foreignKeys[t_iIndex].hashCode());
            }
            t_Log.info("Foreign keys: " + t_sbMessage);
        }
    }

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @precondition list != null
     */
    protected String concat(final Collection list, final String separator)
    {
        return concat(list, separator, StringUtils.getInstance());
    }

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition list != null
     * @precondition stringUtils != null
     */
    protected String concat(
        final Collection list,
        final String separator,
        @NotNull final StringUtils stringUtils)
    {
        return stringUtils.concatenate(list, separator);
    }
}
