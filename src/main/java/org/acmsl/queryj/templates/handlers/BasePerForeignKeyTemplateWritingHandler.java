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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.templates.TemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Writes <i>per-fk</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerForeignKeyTemplateWritingHandler
    <T extends BasePerForeignKeyTemplate<C>,
     C extends BasePerForeignKeyTemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerForeignKeyTemplateWritingHandler</code> instance.
     */
    public BasePerForeignKeyTemplateWritingHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        boolean result = true;

        MetadataManager t_MetadataManager = retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            writeTemplates(
                retrieveTemplates(parameters),
                retrieveTemplateGenerator(),
                t_MetadataManager.getEngineName(),
                retrieveCharset(parameters),
                parameters);

            result = false;
        }

        return result;
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param templateGenerator the template generator.
     * @param engineName the engine name.
     * @param charset the file encoding.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void writeTemplates(
        @NotNull final List<T> templates,
        @NotNull final TemplateGenerator<T, C> templateGenerator,
        @NotNull final String engineName,
        @NotNull final Charset charset,
        @NotNull final Map parameters)
        throws  QueryJBuildException
    {
        try
        {
            for  (T t_Template : templates)
            {
                if  (t_Template != null)
                {
                    @NotNull C t_Context = t_Template.getTemplateContext();
                    @NotNull ForeignKey t_ForeignKey  = t_Context.getForeignKey();

                    File t_OutputDir =
                        retrieveOutputDir(
                            engineName,
                            t_ForeignKey.getSourceTableName(),
                            parameters);

                    templateGenerator.write(
                        t_Template, t_OutputDir, charset);
                }
            }
        }
        catch  (@NotNull final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the templates", ioException);
        }
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws QueryJBuildException if the template retrieval process if faulty.
     */
    @NotNull
    protected abstract List<T> retrieveTemplates(@NotNull final Map parameters)
        throws  QueryJBuildException;

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    protected abstract TemplateGenerator<T,C> retrieveTemplateGenerator();

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
}
