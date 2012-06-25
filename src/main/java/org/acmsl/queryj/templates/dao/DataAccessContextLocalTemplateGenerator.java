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
 * Filename: DataAccessContextLocalTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate dataAccessContext-local.xml templates.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateGenerator;

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
 * Is able to generate dataAccessContext-local.xml templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DataAccessContextLocalTemplateGenerator
    extends AbstractTemplateGenerator<DataAccessContextLocalTemplate, BasePerRepositoryTemplateContext>
    implements BasePerRepositoryTemplateFactory<DataAccessContextLocalTemplate>,
                BasePerRepositoryTemplateGenerator<DataAccessContextLocalTemplate, BasePerRepositoryTemplateContext>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DataAccessContextLocalTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DataAccessContextLocalTemplateGenerator SINGLETON =
            new DataAccessContextLocalTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DataAccessContextLocalTemplateGenerator() {}

    /**
     * Retrieves a DataAccessContextLocalTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static DataAccessContextLocalTemplateGenerator getInstance()
    {
        return DataAccessContextLocalTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unused")
    public DataAccessContextLocalTemplate createTemplate(
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
            new DataAccessContextLocalTemplate(
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
     * Returns "dataAccessContext-local.xml.sample".
     * @return such file name.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return "dataAccessContext-local.xml.sample";
    }
}
