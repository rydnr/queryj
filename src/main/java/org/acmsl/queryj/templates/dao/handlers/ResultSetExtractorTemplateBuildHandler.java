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
 * Filename: ResultSetExtractorTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ResultSetExtractor template.
 *
 */
package org.acmsl.queryj.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.templates.dao.ResultSetExtractorTemplate;
import org.acmsl.queryj.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Builds a ResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ResultSetExtractorTemplateBuildHandler
    extends BasePerTableTemplateBuildHandler<ResultSetExtractorTemplate, ResultSetExtractorTemplateGenerator>
{
    /**
     * Creates a <code>PkStatementSetterTemplateBuildHandler</code>
     * instance.
     */
    public ResultSetExtractorTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @NotNull
    protected ResultSetExtractorTemplateGenerator retrieveTemplateFactory()
    {
        return ResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected String retrievePackage(
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
        throws QueryJBuildException
    {
        return
            packageUtils.retrieveResultSetExtractorPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<ResultSetExtractorTemplate> templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.RESULTSET_EXTRACTOR_TEMPLATES,
            templates);
    }
}
