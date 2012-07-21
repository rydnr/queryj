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
 * Filename: BasePerTableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-table templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerTableTemplate;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.BasePerTableTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Writes <i>per-table</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerTableTemplateWritingHandler
    <T extends BasePerTableTemplate<C>, TG extends BasePerTableTemplateGenerator<T,C>, C extends BasePerTableTemplateContext>
    extends    AbstractTemplateWritingHandler<T, TG, C>
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
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @Override
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final String engineName,
        @NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return retrieveOutputDir(context.getTableName(), rootDir, engineName, parameters);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param tableName the table name.
     * @param rootDir the root dir.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String tableName,
        @NotNull final File rootDir,
        @NotNull final String engineName,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                rootDir,
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                tableName,
                engineName,
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
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubFolders,
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final Map parameters,
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
