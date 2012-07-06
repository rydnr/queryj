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
 * Filename: TableRepositoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableRepositoryTemplateGenerator
    extends  AbstractTemplateGenerator<TableRepositoryTemplate, BasePerRepositoryTemplateContext>
    implements  BasePerRepositoryTemplateGenerator<TableRepositoryTemplate, BasePerRepositoryTemplateContext>,
                BasePerRepositoryTemplateFactory<TableRepositoryTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TableRepositoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableRepositoryTemplateGenerator SINGLETON =
            new TableRepositoryTemplateGenerator();
    }

    /**
     * Default constructor.
     */
    public TableRepositoryTemplateGenerator() {}

    /**
     * Retrieves a {@link TableRepositoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static TableRepositoryTemplateGenerator getInstance()
    {
        return TableRepositoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public TableRepositoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String packageName,
        @NotNull final String projectPackage,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation)
    {
        return
            new TableRepositoryTemplate(
                new BasePerRepositoryTemplateContext(
                    metadataManager,
                    customSqlProvider,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    projectPackage,
                    repository,
                    implementMarkerInterfaces,
                    jmx,
                    tableNames,
                    jndiLocation));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return retrieveTemplateFileName(context, TableRepositoryTemplateUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     * @param tableRepositoryTemplateUtils the {@link TableRepositoryTemplateUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final BasePerRepositoryTemplateContext context,
        @NotNull final TableRepositoryTemplateUtils tableRepositoryTemplateUtils)
    {
        return
            tableRepositoryTemplateUtils.retrieveTableRepositoryClassName(
                context.getRepositoryName())
            + ".java";
    }
}