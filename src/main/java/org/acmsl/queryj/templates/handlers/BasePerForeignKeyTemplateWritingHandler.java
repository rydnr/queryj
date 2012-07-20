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
 * Filename: BasePerForeignKeyTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-fk templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateGenerator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;

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
 * Writes <i>per-fk</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplateWritingHandler
    <T extends BasePerForeignKeyTemplate<C>,
     C extends BasePerForeignKeyTemplateContext,
     TG extends BasePerForeignKeyTemplateGenerator<T,C>>
    extends    AbstractTemplateWritingHandler<T, TG, C>
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerForeignKeyTemplateWritingHandler</code> instance.
     */
    public BasePerForeignKeyTemplateWritingHandler() {}

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @Override
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final C context, @NotNull final String engineName, @NotNull final Map parameters)
    {
        return retrieveOutputDir(engineName, context.getForeignKey().getSourceTableName(), parameters);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String engineName, @NotNull final String tableName, @NotNull final Map parameters)
    {
        return
            retrieveOutputDir(
                engineName,
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                tableName,
                retrieveUseSubfoldersFlag(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param tableName the table name.
     * @param subFolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final String engineName,
        @NotNull final File projectOutputDir,
        final String projectPackage,
        @NotNull final String tableName,
        final boolean subFolders,
        @NotNull final PackageUtils packageUtils);

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
