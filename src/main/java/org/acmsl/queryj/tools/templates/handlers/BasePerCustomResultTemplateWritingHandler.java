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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.TemplateGenerator;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes <i>per-custom-result</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomResultTemplateWritingHandler<T extends BasePerCustomResultTemplate>
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a {@link BasePerCustomResultTemplateWritingHandler}
     * instance.
     */
    public BasePerCustomResultTemplateWritingHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        DatabaseMetaData t_Metadata = retrieveDatabaseMetaData(parameters);

        if (t_Metadata != null)
        {
            writeTemplates(parameters, t_Metadata);
        }

        return false;
    }

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void writeTemplates(
        @NotNull final Map parameters, @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        try
        {
            writeTemplates(parameters, metaData.getDatabaseProductName());
        }
        catch  (@NotNull final SQLException resultException)
        {
            throw
                new QueryJBuildException(
                    "Cannot retrieve database product name", resultException);
        }
    }

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected void writeTemplates(
        @NotNull final Map parameters, final String engineName)
      throws  QueryJBuildException
    {
        writeTemplates(
            retrieveTemplates(parameters),
            retrieveTemplateGenerator(),
            retrieveCustomSqlProvider(parameters),
            retrieveMetadataManager(parameters),
            engineName,
            retrieveCharset(parameters),
            parameters);
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param templateGenerator the template generator.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param engineName the engine name.
     * @param charset the file encoding.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition template != null
     * @precondition templateGenerator != null
     * @precondition engineName != null
     * @precondition parameters != null
     */
    protected void writeTemplates(
        @Nullable final T[] templates,
        @NotNull final TemplateGenerator<T> templateGenerator,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final Charset charset,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        int t_iCount = (templates != null) ? templates.length : 0;

        @Nullable T t_Template;

        try
        {
            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                assert templates != null;
                t_Template = templates[t_iIndex];

                if  (t_Template != null)
                {
                    Result t_Result = t_Template.getResult();

                    if (t_Result != null)
                    {
                        File t_OutputDir =
                            retrieveOutputDir(
                                t_Result,
                                customSqlProvider,
                                metadataManager,
                                engineName,
                                parameters);

                        if (t_OutputDir != null)
                        {
                            templateGenerator.write(
                                t_Template,
                                t_OutputDir,
                                charset);
                        }
                    }
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
     * Retrieves the template generator.
     * @return such instance.
     */
    protected abstract TemplateGenerator<T> retrieveTemplateGenerator();

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws QueryJBuildException if the template retrieval process if faulty.
     */
    @NotNull
    protected abstract T[] retrieveTemplates(
        final Map parameters)
      throws  QueryJBuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     * @precondition resultElement != null
     */
    @Nullable
    protected File retrieveOutputDir(
        final Result resultElement,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            retrieveOutputDir(
                resultElement,
                customSqlProvider,
                metadataManager,
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
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
    @Nullable
    protected abstract File retrieveOutputDir(
        final Result resultElement,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final File projectFolder,
        final String projectPackage,
        final boolean useSubFolders,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  QueryJBuildException;
}
