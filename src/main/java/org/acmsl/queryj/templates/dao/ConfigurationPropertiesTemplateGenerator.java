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
 * Filename: ConfigurationPropertiesTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate ConfigurationProperties implementations
 *              according to database metadata.
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
import java.util.Locale;

/**
 * Is able to generate ConfigurationProperties implementations according
 * to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ConfigurationPropertiesTemplateGenerator
    extends AbstractTemplateGenerator<ConfigurationPropertiesTemplate, BasePerRepositoryTemplateContext>
    implements  BasePerRepositoryTemplateGenerator<ConfigurationPropertiesTemplate, BasePerRepositoryTemplateContext>,
                BasePerRepositoryTemplateFactory<ConfigurationPropertiesTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class ConfigurationPropertiesTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ConfigurationPropertiesTemplateGenerator SINGLETON =
            new ConfigurationPropertiesTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ConfigurationPropertiesTemplateGenerator() {}

    /**
     * Retrieves a {@link ConfigurationPropertiesTemplateGenerator}
     * instance.
     * @return such instance.
     */
    @NotNull
    public static ConfigurationPropertiesTemplateGenerator getInstance()
    {
        return ConfigurationPropertiesTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    @NotNull
    @Override
    public ConfigurationPropertiesTemplate createTemplate(
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
            new ConfigurationPropertiesTemplate(
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
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return retrieveTemplateFileName(context, DAOChooserTemplateUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     * @param utils the {@link DAOChooserTemplateUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final BasePerRepositoryTemplateContext context, @NotNull final DAOChooserTemplateUtils utils)
    {
        return
            utils.retrievePropertiesFileName(
                context.getRepositoryName().toLowerCase(Locale.US));
    }
}
