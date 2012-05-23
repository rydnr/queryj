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
 * Filename: BaseDAOTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate base DAO implementations according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
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

/**
 * Is able to generate base DAO implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseDAOTemplateGenerator
    extends AbstractTemplateGenerator<BaseDAOTemplate, BasePerTableTemplateContext>
    implements  BasePerTableTemplateFactory<BaseDAOTemplate>,
                BasePerTableTemplateGenerator<BaseDAOTemplate, BasePerTableTemplateContext>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class BaseDAOTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final BaseDAOTemplateGenerator SINGLETON =
            new BaseDAOTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected BaseDAOTemplateGenerator() {}

    /**
     * Retrieves a {@link BaseDAOTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static BaseDAOTemplateGenerator getInstance()
    {
        return BaseDAOTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a BaseDAO template.
     * @param metadataManager the metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param tableName the table name.
     * @return a template.
     */
    @NotNull
    public BaseDAOTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String header,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        @NotNull final String tableName,
        @Nullable List<Row> staticContents)
    {
        return
            new BaseDAOTemplate(
                new BasePerTableTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    basePackageName,
                    repositoryName,
                    implementMarkerInterfaces,
                    jmx,
                    jndiLocation,
                    tableName,
                    staticContents));
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @Override
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return BaseDAODecoratorFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerTableTemplateContext context)
    {
        return
            retrieveTemplateFileName(
                context, StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final BasePerTableTemplateContext context,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    context.getTableName().toLowerCase()),
                '_')
                + "DAO.java";
    }
}
