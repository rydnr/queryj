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
 * Filename: PkStatementSetterTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes PkStatementSetter templates.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.templates.dao.PkStatementSetterTemplate;
import org.acmsl.queryj.templates.dao.PkStatementSetterTemplateGenerator;
import org.acmsl.queryj.api.handlers.BasePerTableTemplateWritingHandler;
import org.acmsl.queryj.api.TemplateMappingManager;
import org.apache.tools.ant.BuildException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Writes PkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class PkStatementSetterTemplateWritingHandler
    extends  BasePerTableTemplateWritingHandler
                 <PkStatementSetterTemplate, PkStatementSetterTemplateGenerator, PerTableTemplateContext>
{
    /**
     * Creates a PkStatementSetterTemplateWritingHandler.
     */
    public PkStatementSetterTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected PkStatementSetterTemplateGenerator retrieveTemplateGenerator(final boolean caching, final int threadCount)
    {
        return new PkStatementSetterTemplateGenerator(caching, threadCount);
    }

    /**
     * Retrieves the templates from the attribute map.
     *
     * @param parameters the parameter map.
     * @return the template.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<PkStatementSetterTemplate> retrieveTemplates(
        @NotNull final Map parameters) throws BuildException
    {
        return
            (List<PkStatementSetterTemplate>)
                parameters.get(
                    TemplateMappingManager.PK_STATEMENT_SETTER_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrievePkStatementSetterFolder(
                projectFolder,
                projectPackage,
                engineName,
                tableName,
                useSubfolders);
    }
}
