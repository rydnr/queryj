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
 * Filename: BasePerTableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-table templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerTableTemplate;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.PerTableTemplateGenerator;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/**
 * Writes <i>per-table</i> templates.
 * @param <T> the template type.
 * @param <C> the template context type.
 * @param <TG> the template generator type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerTableTemplateWritingHandler
    <T extends PerTableTemplate<C>,
     C extends PerTableTemplateContext,
     TG extends PerTableTemplateGenerator<T, C>>
    extends    AbstractQueryJTemplateWritingHandler<T, C, TG>
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerTableTemplateWritingHandler</code> instance.
     */
    public BasePerTableTemplateWritingHandler() {}

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param rootDir the root dir.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @Override
    @NotNull
    public File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                context.getTableName(),
                rootDir,
                retrieveMetadataManager(parameters).getEngine(),
                parameters);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param tableName the table name.
     * @param rootDir the root dir.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output dir is unavailable.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String tableName,
        @NotNull final File rootDir,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                rootDir,
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                tableName,
                engine,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubFolders whether to use sub-folders for tests, or
     * using a different package naming scheme.
     * @param tableName the table name.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws QueryJBuildException if the output dir is unavailable.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubFolders,
        @NotNull final String tableName,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters,
        @NotNull final PackageUtils packageUtils)
      throws  QueryJBuildException;
}
