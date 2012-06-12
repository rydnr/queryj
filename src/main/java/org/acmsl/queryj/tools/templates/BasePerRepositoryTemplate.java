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
 * Filename: BasePerRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base logic for all per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerRepositoryTemplate<C extends BasePerRepositoryTemplateContext>
    extends AbstractTemplate<C>
{
    /**
     * Builds a <code>BasePerRepositoryTemplate</code> using given
     * information.
     * @param context the {@link org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext} instance.
     */
    public BasePerRepositoryTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @return such code.
     */
    @Override
    @NotNull
    protected String generateOutput(@NotNull final String header, @NotNull final C context)
    {
        return generateOutput(header, context, context.getMetadataManager());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @param context the {@link BasePerRepositoryTemplateContext} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such code.
     */
    @NotNull
    protected String generateOutput(
        @NotNull final String header, @NotNull final C context, @NotNull final MetadataManager metadataManager)
    {
        return
            generateOutput(
                header,
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                context.getCustomSqlProvider(),
                context.getDecoratorFactory(),
                context.getPackageName(),
                context.getBasePackageName(),
                context.getRepositoryName(),
                metadataManager.getEngineName(),
                context.getTableNames(),
                StringUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param stringUtils the StringUtils instance.
     * @param packageUtils the PackageUtils instance.
     * @return such code.
     */
    @SuppressWarnings("unused")
    protected String generateOutput(
        @Nullable final String header,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String subpackageName,
        @NotNull String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final StringUtils stringUtils,
        @NotNull final PackageUtils packageUtils)
    {
        String result;

        @Nullable StringTemplateGroup t_Group = retrieveGroup();

        @Nullable StringTemplate t_Template = retrieveTemplate(t_Group);

        fillParameters(
            new HashMap(),
            t_Template,
            header,
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            decoratorFactory,
            subpackageName,
            basePackageName,
            repositoryName,
            engineName,
            tables,
            createTimestamp(),
            new Integer[]
            {
                STARTING_YEAR,
                retrieveCurrentYear()
            },
            stringUtils);

        try
        {
            result = t_Template.toString();
        }
        catch (@NotNull final IllegalArgumentException invalidTemplate)
        {
            throw
                new InvalidTemplateException(
                    "invalid.per.repository.template",
                    new Object[]
                    {
                        t_Template.getName(),
                        getTemplateName(),
                        repositoryName
                    },
                    invalidTemplate);
        }

        return result;
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository.
     * @param engineName the engine name.
     * @param tables the table names.
     * @param timestamp the timestamp.
     * @param copyrightYears the copyright years.
     * @param stringUtils the <code>StringUtils</code> instance.
     */
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
    {
        template.setAttribute("input", input);

        fillHeaderParameters(input, header, copyrightYears, timestamp);

        fillCoreParameters(
            input,
            metadataManager,
            customSqlProvider,
            decoratorFactory,
            subpackageName,
            basePackageName,
            tableRepositoryName,
            engineName,
            tables,
            timestamp,
            stringUtils);
    }

    /**
     * Fills the parameters for <code>java_header</code> rule.
     * @param input the input.
     * @param header the header.
     * @param copyrightYears the copyright years.
     * @param timestamp the timestamp.
     */
    @SuppressWarnings("unchecked")
    protected void fillHeaderParameters(
        @NotNull final Map input,
        @NotNull final String header,
        @NotNull final Integer[] copyrightYears,
        @NotNull final String timestamp)
    {
        input.put("copyright_years", copyrightYears);
        input.put("timestamp", timestamp);

        input.put("header", processHeader(input, header));
    }


    /**
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     */
    @SuppressWarnings("unchecked")
    protected void fillCoreParameters(
        @NotNull final Map input,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String subpackageName,
        @NotNull final String basePackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final String timestamp,
        @NotNull final StringUtils stringUtils)
    {
        input.put("tr_name", tableRepositoryName);

        input.put("tr_name_capitalized", capitalize(tableRepositoryName));
        input.put("tr_name_normalized", normalize(tableRepositoryName));

        input.put("base_package_name", basePackageName);

        input.put("engine_name", engineName);

        input.put(
            "engine_name_lowercased",
            lowercase(engineName, DecorationUtils.getInstance()));

        input.put(
            "dao_subpackage_name",
            retrieveDAOSubpackageName(
                basePackageName, engineName, PackageUtils.getInstance()));

        input.put(
            "tables",
            decorateTables(tables, metadataManager, decoratorFactory));

        input.put(
            "repository_dao",
            definesRepositoryScopedSql(customSqlProvider)
            ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Retrieves the DAO subpackage name.
     * @param basePackageName the base package name.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such information.
     */
    protected String retrieveDAOSubpackageName(
        final String basePackageName,
        @NotNull final String engineName,
        @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.retrieveDAOPackage(basePackageName, engineName);
    }

    /**
     * Decorates the tables.
     * @param tables the tables.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated tables.
     * @precondition tables != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    @NotNull
    protected List<TableDecorator> decorateTables(
        @NotNull final List<String> tables,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<TableDecorator> result = new ArrayList<TableDecorator>(tables.size());

        for (String t_strTable: tables)
        {
            result.add(decorate(t_strTable, metadataManager,decoratorFactory));
        }

        return result;
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated table.
     */
    @NotNull
    protected TableDecorator decorate(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager);
    }

    /**
     * Checks whether given custom SQL provider defines any repository-scope
     * SQL or not.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return <code>true</code> in such case.
     */
    protected boolean definesRepositoryScopedSql(
        @Nullable final CustomSqlProvider customSqlProvider)
    {
        boolean result = false;

        @Nullable Collection t_cContents =
            (customSqlProvider != null)
            ?  customSqlProvider.getCollection() : null;

        if  (t_cContents != null)
        {
            @Nullable Object t_Content;
            @Nullable Sql t_Sql;
            String t_strDao;

            Iterator t_itContentIterator = t_cContents.iterator();

            if  (t_itContentIterator != null)
            {
                while  (t_itContentIterator.hasNext())
                {
                    t_Content = t_itContentIterator.next();

                    if  (t_Content instanceof Sql)
                    {
                        t_Sql = (Sql) t_Content;

                        t_strDao = t_Sql.getDao();

                        if  (t_strDao == null)
                        {
                            result =
                                (t_Sql.getRepositoryScope() != null);

                            if  (result)
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
