/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerCustomResultTemplateGenerator;
import org.acmsl.queryj.api.PerCustomResultTemplate;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.Engine;

/*
 * Importing Jetbrains annotations.
 */
import org.acmsl.queryj.tools.DebugUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/**
 * Writes <i>per-custom-result</i> templates.
 * @param <T> the template type.
 * @param <C> the template context type.
 * @param <TG> the template generator type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerCustomResultTemplateWritingHandler
    <T extends PerCustomResultTemplate<C>,
        C extends PerCustomResultTemplateContext,
        TG extends PerCustomResultTemplateGenerator<T, C>>
    extends    AbstractQueryJTemplateWritingHandler<T, C, TG>
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
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
      throws QueryJBuildException
    {
        final File result;

        if (DebugUtils.getInstance().debugEnabledForResultId(context.getResult().getId()))
        {
            int a = 0;
        }
         @NotNull final MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        result =
            retrieveOutputDir(
                context.getResult(),
                retrieveCustomSqlProvider(parameters),
                t_MetadataManager,
                parameters);

        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output dir is unavailable.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected File retrieveOutputDir(
        @NotNull final Result<String> resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                resultElement,
                customSqlProvider,
                metadataManager,
                retrieveProjectOutputDir(parameters),
                retrieveMetadataManager(parameters).getEngine(),
                parameters);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param projectFolder the project folder.
     * using a different package naming scheme.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final Result<String> resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final File projectFolder,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters);
}
