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
 * Filename: TableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the table templates.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TableTemplateGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Writes the table templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a TableTemplateWritingHandler.
     */
    public TableTemplateWritingHandler() {}

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                retrieveTableTemplates(parameters),
                retrieveOutputDir(parameters),
                retrieveCharset(parameters),
                TableTemplateGenerator.getInstance());
    }

    /**
     * Writes the Table templates.
     * @param templates the templates to write.
     * @param outputDir the output dir.
     * @param charset the file encoding.
     * @param generator the <code>TableTemplateGenerator</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(
        @NotNull final List<TableTemplate> templates,
        @Nullable final File outputDir,
        @NotNull final Charset charset,
        @NotNull final TableTemplateGenerator generator)
      throws  QueryJBuildException
    {
        boolean result = false;

        if (outputDir != null)
        {
            try
            {
                for (TableTemplate t_TableTemplate : templates)
                {
                    generator.write(
                        t_TableTemplate, outputDir, charset);
                }
            }
            catch  (@NotNull final IOException ioException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot write the template", ioException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the table templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template array.
     * @precondition parameters != null
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected List<TableTemplate> retrieveTableTemplates(@NotNull final Map parameters)
    {
        List<TableTemplate> result =
            (List<TableTemplate>)
                parameters.get(
                    TableTemplateBuildHandler.TABLE_TEMPLATES);

        if (result == null)
        {
            result = new ArrayList<TableTemplate>(0);
        }

        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    @Nullable
    protected File retrieveOutputDir(@NotNull final Map parameters)
    {
        return retrieveOutputDir(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    @Nullable
    protected File retrieveOutputDir(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveTableFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
