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
 * Filename: FkStatementSetterTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes FkStatementSetter templates.
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
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers.FkStatementSetterTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

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
 * Writes FkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>FkStatementSetterTemplateWritingHandler</code>
     * instance.
     */
    public FkStatementSetterTemplateWritingHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(final Map parameters)
      throws  QueryJBuildException
    {
        writeTemplates(
            parameters,
            retrieveDatabaseMetaData(parameters),
            retrieveCharset(parameters));

        return false;
    }

    /**
     * Writes the <code>FkStatementSetter</code> templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @param charset the file encoding.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void writeTemplates(
        final Map parameters,
        final DatabaseMetaData metaData,
        final Charset charset)
      throws  QueryJBuildException
    {
        try
        {
            writeTemplates(
                parameters,
                metaData.getDatabaseProductName(),
                charset,
                retrieveTemplates(parameters),
                FkStatementSetterTemplateGenerator.getInstance());
        }
        catch  (final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot retrieve database product name", sqlException);
        }
    }

    /**
     * Writes the <code>FkStatementSetter</code> templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param charset the file encoding.
     * @param templates the templates.
     * @param templateGenerator the template generator.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition templates != null
     * @precondition templateGenerator != null
     */
    protected void writeTemplates(
        final Map parameters,
        final String engineName,
        final Charset charset,
        final FkStatementSetterTemplate[] templates,
        final FkStatementSetterTemplateGenerator templateGenerator)
      throws  QueryJBuildException
    {
        try 
        {
            int t_iLength = (templates != null) ? templates.length : 0;

            FkStatementSetterTemplate t_Template;

            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_Template = templates[t_iIndex];

                if  (t_Template != null)
                {
                    templateGenerator.write(
                        t_Template,
                        retrieveOutputDir(
                            engineName,
                            t_Template.getForeignKey().getSourceTableName(),
                            parameters),
                        charset);
                }
            }
        }
        catch  (final IOException ioException)
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
     * @precondition parameters != null
     */
    protected FkStatementSetterTemplate[] retrieveTemplates(
        final Map parameters)
    {
        return
            (FkStatementSetterTemplate[])
                parameters.get(
                    TemplateMappingManager.FK_STATEMENT_SETTER_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(
        final String engineName, final String tableName, final Map parameters)
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
     * @precondition engineName != null
     * @precondition projectOutputDir != null
     * @precondition projectPackage != null
     * @precondition tableName != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final String engineName,
        final File projectOutputDir,
        final String projectPackage,
        final String tableName,
        final boolean subFolders,
        final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFkStatementSetterFolder(
                projectOutputDir,
                projectPackage,
                engineName,
                tableName,
                subFolders);
    }
}
