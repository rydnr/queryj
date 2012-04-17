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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.DefaultBasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Locale;

/**
 * Is able to generate ConfigurationProperties implementations according
 * to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ConfigurationPropertiesTemplateGenerator<T extends ConfigurationPropertiesTemplate>
    extends AbstractTemplateGenerator<T>
    implements  DefaultBasePerRepositoryTemplateFactory,
                BasePerRepositoryTemplateGenerator<T>,
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
     * Generates a {@link ConfigurationPropertiesTemplate} template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param engineName the engine name.
     * @param tables the table names.
     * @param header the header.
     * @param jmx whether to support JMX.
     * @return a template.
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     * @precondition engineName != null
     * @precondition tables != null
     */
    @NotNull
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables,
        final String header,
        final boolean jmx)
    {
        return
            new ConfigurationPropertiesTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                basePackageName,
                repositoryName,
                engineName,
                tables);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return retrieveTemplateFileName(template, DAOChooserTemplateUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param template the template.
     * @param utils the {@link DAOChooserTemplateUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(@NotNull final T template, @NotNull final DAOChooserTemplateUtils utils)
    {
        return
            utils.retrievePropertiesFileName(
                template.getRepositoryName().toLowerCase(Locale.US));
    }
}
