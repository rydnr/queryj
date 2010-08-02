//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes QueryPreparedStatementCreator templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.QueryPreparedStatementCreatorTemplate;
import org.acmsl.queryj.tools.templates.dao.QueryPreparedStatementCreatorTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers.QueryPreparedStatementCreatorTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes QueryPreparedStatementCreator templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class QueryPreparedStatementCreatorTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a
     * <code>QueryPreparedStatementCreatorTemplateWritingHandler</code>
     * instance.
     */
    public QueryPreparedStatementCreatorTemplateWritingHandler() {};

    /**
     * Handles given information.
     * @param parameters the information to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        writeTemplate(
            retrieveTemplate(parameters),
            retrieveOutputDir(parameters),
            QueryPreparedStatementCreatorTemplateGenerator.getInstance());

        return false;
    }
                
    /**
     * Handles given information.
     * @param template the template.
     * @param outputDir the output dir.
     * @param generator the generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected void writeTemplate(
        final QueryPreparedStatementCreatorTemplate template,
        final File outputDir,
        final QueryPreparedStatementCreatorTemplateGenerator generator)
      throws  QueryJBuildException
    {
        try 
        {
            generator.write(template, outputDir);
        }
        catch  (final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the template", ioException);
        }
    }

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @precondition parameters != null
     */
    protected QueryPreparedStatementCreatorTemplate retrieveTemplate(
        final Map parameters)
    {
        return
            (QueryPreparedStatementCreatorTemplate)
                parameters.get(
                    TemplateMappingManager
                        .QUERY_PREPARED_STATEMENT_CREATOR_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(final Map parameters)
    {
        return
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param subFolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition projectOutputDir != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final File projectOutputDir,
        final String projectPackage,
        final boolean subFolders,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveQueryPreparedStatementCreatorFolder(
                projectOutputDir,
                projectPackage,
                subFolders);
    }
}
