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
 * Filename: BasePerCustomResultTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-custom-result templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerCustomResultTemplateGenerator;
import org.acmsl.queryj.api.PerCustomResultTemplate;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.exceptions.CannotRetrieveMetadataManagerException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes <i>per-custom-result</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplateWritingHandler
    <T extends PerCustomResultTemplate<C>,
        C extends PerCustomResultTemplateContext,
        TG extends PerCustomResultTemplateGenerator<T>>
    extends    AbstractTemplateWritingHandler<T, TG, C>
    implements TemplateWritingHandler
{
    /**
     * Creates a {@link BasePerCustomResultTemplateWritingHandler}
     * instance.
     */
    public BasePerCustomResultTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final String engineName,
        @NotNull final Map<String, ?> parameters)
      throws QueryJBuildException
    {
        final File result;

        @Nullable final MetadataManager t_MetadataManager =
            retrieveMetadataManager((Map <String, MetadataManager>) parameters);

        if (t_MetadataManager != null)
        {
            result =
                retrieveOutputDir(
                    context.getResult(),
                    retrieveCustomSqlProvider((Map<String, CustomSqlProvider>) parameters),
                    t_MetadataManager,
                    engineName,
                    parameters);
        }
        else
        {
            throw new CannotRetrieveMetadataManagerException();
        }

        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected File retrieveOutputDir(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final Map<String, ?> parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                resultElement,
                customSqlProvider,
                metadataManager,
                retrieveProjectOutputDir((Map<String, File>) parameters),
                retrieveProjectPackage((Map<String, String>) parameters),
                retrieveUseSubfoldersFlag((Map<String, Boolean>) parameters),
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubFolders whether to use subFolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubFolders,
        @NotNull final String engineName,
        @NotNull final Map<String, ?> parameters,
        @NotNull final PackageUtils packageUtils)
      throws  QueryJBuildException;


    /**
     * Displays useful information about this handler.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return "Writer:" + getClass().getSimpleName();
    }
}
