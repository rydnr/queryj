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
 * Filename: RepositoryDAOFactoryTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the repository DAO factory implementation.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.templates.dao.RepositoryDAOFactoryTemplate;
import org.acmsl.queryj.templates.dao.RepositoryDAOFactoryTemplateGenerator;
import org.acmsl.queryj.api.handlers.BasePerRepositoryTemplateWritingHandler;
import org.acmsl.queryj.api.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Writes the repository DAO factory implementation.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class RepositoryDAOFactoryTemplateWritingHandler
    extends BasePerRepositoryTemplateWritingHandler
                <RepositoryDAOFactoryTemplate, RepositoryDAOFactoryTemplateGenerator, PerRepositoryTemplateContext>
{
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected RepositoryDAOFactoryTemplateGenerator retrieveTemplateGenerator(
        final boolean caching, final int threadCount)
    {
        return new RepositoryDAOFactoryTemplateGenerator(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected List<RepositoryDAOFactoryTemplate> retrieveTemplates(@NotNull final Map parameters)
    {
        return
            (List<RepositoryDAOFactoryTemplate>)
                parameters.get(
                    TemplateMappingManager.REPOSITORY_DAO_FACTORY_TEMPLATE);
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
        return
            packageUtils.retrieveRepositoryDAOFolder(
                projectFolder,
                projectPackage,
                engineName,
                useSubfolders);
    }
}
