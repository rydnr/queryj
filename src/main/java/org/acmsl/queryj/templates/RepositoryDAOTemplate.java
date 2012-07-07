//;-*- mode: java -*-
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

 ******************************************************************************
 *
 * Filename: RepositoryDAOTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO repository implementations according
 *              to database metadata.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.templates.dao.DAOTemplateUtils;

/*
 * Importing some StringTemplate classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Is able to generate DAO repository implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class RepositoryDAOTemplate<C extends BasePerRepositoryTemplateContext>
    extends  BasePerRepositoryTemplate<C>
{
    private static final long serialVersionUID = -2154000859171671282L;

    /**
     * Builds a <code>RepositoryDAOTemplate</code> using given
     * information.
     * @param context the {@link BasePerRepositoryTemplateContext} information.
     */
    public RepositoryDAOTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository.
     * @param engineName the engine name.
     * @param tables the table names.
     * @param timestamp the timestamp.
     * @param copyrightYears the copyright years.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @SuppressWarnings("unchecked,unused")
    protected void fillParameters(
        @NotNull final Map input,
        @NotNull final StringTemplate template,
        @NotNull final String header,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String subpackageName,
        @NotNull final String basePackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final String timestamp,
        @NotNull final Integer[] copyrightYears,
        @NotNull final StringUtils stringUtils)
      throws QueryJBuildException
    {
        fillParameters(
            input,
            template,
            header,
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            decoratorFactory,
            subpackageName,
            basePackageName,
            tableRepositoryName,
            engineName,
            tables,
            timestamp,
            copyrightYears,
            stringUtils,
            DAOTemplateUtils.getInstance(),
            TemplateUtils.getInstance());
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository.
     * @param engineName the engine name.
     * @param tables the table names.
     * @param timestamp the timestamp.
     * @param copyrightYears the copyright years.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @SuppressWarnings("unused,unchecked")
    protected void fillParameters(
        @NotNull final Map input,
        @NotNull final StringTemplate template,
        @NotNull final String header,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String subpackageName,
        @NotNull final String basePackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final String timestamp,
        @NotNull final Integer[] copyrightYears,
        @NotNull final StringUtils stringUtils,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
      throws QueryJBuildException
    {
        @Nullable List<Sql> t_cCustomSelects =
            retrieveCustomSelects(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        input.put("custom_selects", t_cCustomSelects);

        @Nullable List<Sql> t_cCustomUpdatesOrInserts =
            retrieveCustomUpdatesOrInserts(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        input.put("custom_updates_or_inserts", t_cCustomUpdatesOrInserts);

        @Nullable List<Sql> t_cCustomSelectsForUpdate =
            retrieveCustomSelectsForUpdate(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        input.put("custom_selects_for_update", t_cCustomSelectsForUpdate);

        @Nullable List<Result> t_cCustomResults =
            retrieveCustomResults(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils,
                templateUtils);

        input.put("custom_results", t_cCustomResults);
    }

    /**
     * Retrieves the custom selects.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom selects.
     */
    @NotNull
    protected List<Sql> retrieveCustomSelects(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        return
            templateUtils.retrieveCustomSelects(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom sql.
     */
    @NotNull
    protected List<Sql> retrieveCustomUpdatesOrInserts(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        return
            templateUtils.retrieveCustomUpdatesOrInserts(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom selects.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom selects.
     */
    @NotNull
    protected List<Sql> retrieveCustomSelectsForUpdate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
    {
        return
            templateUtils.retrieveCustomSelectsForUpdate(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom results.
     * @param customSqlProvider the provider.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param templateUtils the <code>TemplateUtils</code> instance.
     * @return the custom results.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @NotNull
    protected List<Result> retrieveCustomResults(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils,
        @NotNull final TemplateUtils templateUtils)
      throws QueryJBuildException
    {
        return
            templateUtils.retrieveCustomResults(
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/RepositoryDAO.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "Repository DAO";
    }
}
