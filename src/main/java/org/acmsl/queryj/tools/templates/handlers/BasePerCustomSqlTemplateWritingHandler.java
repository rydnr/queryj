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
 * Filename: BasePerCustomSqlTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-custom-sql templates.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplateGenerator;
import org.jetbrains.annotations.NotNull;

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
 * Writes <i>per-custom-sql</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerCustomSqlTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerCustomSqlTemplateWritingHandler</code> instance.
     */
    public BasePerCustomSqlTemplateWritingHandler() {}

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
        writeTemplate(parameters, retrieveDatabaseMetaData(parameters));

        return false;
    }

    /**
     * Writes the template.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void writeTemplate(
        @NotNull final Map parameters, @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        try
        {
            writeTemplate(parameters, metaData.getDatabaseProductName());
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                      "Cannot retrieve database product name, "
                    + "version or quote string",
                    sqlException);
        }
    }

    /**
     * Writes the template.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected void writeTemplate(
        @NotNull final Map parameters, final String engineName)
      throws  QueryJBuildException
    {
        writeTemplate(
            retrieveTemplate(parameters),
            retrieveOutputDir(engineName, parameters),
            retrieveCharset(parameters),
            retrieveTemplateGenerator());
    }
            
    /**
     * Writes the templates.
     * @param template the template.
     * @param outputDir the output dir.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition templateGenerator != null
     */
    protected void writeTemplate(
        final BasePerCustomSqlTemplate template,
        final File outputDir,
        final Charset charset,
        @NotNull final BasePerCustomSqlTemplateGenerator templateGenerator)
      throws  QueryJBuildException
    {
        try 
        {
            templateGenerator.write(template, outputDir, charset);
        }
        catch  (@NotNull final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the template", ioException);
        }
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    protected abstract BasePerCustomSqlTemplateGenerator retrieveTemplateGenerator();

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    protected abstract BasePerCustomSqlTemplate retrieveTemplate(
        final Map parameters);

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    @NotNull
    protected File retrieveOutputDir(
        final String engineName, @NotNull final Map parameters)
    {
        return
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils);
}
