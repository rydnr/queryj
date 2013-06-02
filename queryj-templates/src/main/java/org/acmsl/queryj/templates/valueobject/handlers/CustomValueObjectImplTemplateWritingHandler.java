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
 * Filename: CustomValueObjectImplTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ValueObject templates.
 *
 */
package org.acmsl.queryj.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.handlers.BasePerCustomResultTemplateWritingHandler;
import org.acmsl.queryj.templates.valueobject.CustomValueObjectImplTemplate;
import org.acmsl.queryj.templates.valueobject.CustomValueObjectImplTemplateGenerator;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

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
 * Writes ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomValueObjectImplTemplateWritingHandler
    extends BasePerCustomResultTemplateWritingHandler
                <CustomValueObjectImplTemplate,
                    BasePerCustomResultTemplateContext,
                    CustomValueObjectImplTemplateGenerator>
{
    /**
     * Creates a CustomValueObjectImplTemplateWritingHandler.
     */
    public CustomValueObjectImplTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected CustomValueObjectImplTemplateGenerator retrieveTemplateGenerator(
        final boolean caching, final int threadCount)
    {
        return new CustomValueObjectImplTemplateGenerator(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<CustomValueObjectImplTemplate> retrieveTemplates(
        @NotNull final Map parameters)
        throws QueryJBuildException
    {
        return
            (List<CustomValueObjectImplTemplate>)
                parameters.get(
                    TemplateMappingManager.CUSTOM_VALUE_OBJECT_IMPL_TEMPLATES);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final Result result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String engineName,
        @NotNull final Map<String, ?> parameters,
        @NotNull final PackageUtils packageUtils)
        throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                projectFolder,
                projectPackage,
                useSubfolders,
                packageUtils);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param useSubfolders whether to use subfolders or not.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process fails.
     * @precondition projectOutputDir != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final File projectOutputDir,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final PackageUtils packageUtils)
        throws  QueryJBuildException
    {
        return
            packageUtils.retrieveValueObjectFolder(
                projectOutputDir,
                projectPackage,
                useSubfolders);
    }
}
