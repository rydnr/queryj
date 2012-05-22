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
 * Filename: ConfigurationPropertiesTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ConfigurationProperties templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplate;
import org.acmsl.queryj.tools.templates.dao.ConfigurationPropertiesTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes ConfigurationProperties templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ConfigurationPropertiesTemplateWritingHandler
    extends  BasePerRepositoryTemplateWritingHandler<
    ConfigurationPropertiesTemplate, ConfigurationPropertiesTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Creates a <code>ConfigurationPropertiesTemplateWritingHandler</code>
     * instance.
     */
    public ConfigurationPropertiesTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected ConfigurationPropertiesTemplateGenerator retrieveTemplateGenerator()
    {
        return ConfigurationPropertiesTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected ConfigurationPropertiesTemplate retrieveTemplate(@NotNull final Map parameters)
    {
        return
            (ConfigurationPropertiesTemplate)
                parameters.get(
                    TemplateMappingManager.CONFIGURATION_PROPERTIES_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return projectFolder;
    }
}
