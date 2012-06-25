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
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;
import org.acmsl.queryj.templates.TemplateGenerator;
import org.acmsl.queryj.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.templates.dao.FkStatementSetterTemplateGenerator;
import org.acmsl.queryj.templates.handlers.BasePerForeignKeyTemplateWritingHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Writes FkStatementSetter templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateWritingHandler
    extends BasePerForeignKeyTemplateWritingHandler<FkStatementSetterTemplate, BasePerForeignKeyTemplateContext>
{
    /**
     * Creates a <code>FkStatementSetterTemplateWritingHandler</code>
     * instance.
     */
    public FkStatementSetterTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplateGenerator<FkStatementSetterTemplate, BasePerForeignKeyTemplateContext> retrieveTemplateGenerator()
    {
        return FkStatementSetterTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    protected List<FkStatementSetterTemplate> retrieveTemplates(
        @NotNull final Map parameters)
    {
        return
            (List<FkStatementSetterTemplate>)
                parameters.get(
                    TemplateMappingManager.FK_STATEMENT_SETTER_TEMPLATES);
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
    @Override
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final String engineName,
        @NotNull final File projectOutputDir,
        final String projectPackage,
        @NotNull final String tableName,
        final boolean subFolders,
        @NotNull final PackageUtils packageUtils)
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
