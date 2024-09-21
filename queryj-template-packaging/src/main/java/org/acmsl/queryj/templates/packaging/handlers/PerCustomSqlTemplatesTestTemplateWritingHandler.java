/*
                        QueryJ Template Packaging

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
 * Filename: PerCustomSqlTemplatesTestTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writing handler for PerCustomSqlTemplatesTestTemplates.
 *
 * Date: 2014/04/17
 * Time: 10:15
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.PerCustomSqlTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;
import org.acmsl.queryj.templates.packaging.exceptions.MissingTemplatesException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Writing handler for {@link PerCustomSqlTemplatesTestTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 10:15
 */
@ThreadSafe
public class PerCustomSqlTemplatesTestTemplateWritingHandler
    extends TemplatePackagingTestWritingHandler
                <PerCustomSqlTemplatesTestTemplate,
                    GlobalTemplateContext,
                    TemplatePackagingTemplateGenerator<PerCustomSqlTemplatesTestTemplate, GlobalTemplateContext>>
{
    /**
     * Creates a new writing handler for {@link PerCustomSqlTemplatesTestTemplate templates}.
     */
    public PerCustomSqlTemplatesTestTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @param caching whether to enable template caching.
     * @param threadCount the number of threads to use.
     * @return such instance.
     */
    @NotNull
    @Override
    public TemplatePackagingTemplateGenerator<PerCustomSqlTemplatesTestTemplate, GlobalTemplateContext> retrieveTemplateGenerator(
        final boolean caching, final int threadCount)
    {
        return new TemplatePackagingTemplateGenerator<>(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<PerCustomSqlTemplatesTestTemplate> retrieveTemplates(@NotNull final QueryJCommand parameters)
    {
        @NotNull final List<PerCustomSqlTemplatesTestTemplate> result;

        @Nullable final List<PerCustomSqlTemplatesTestTemplate> aux =
            new QueryJCommandWrapper<PerCustomSqlTemplatesTestTemplate>(parameters)
                .getListSetting(PerCustomSqlTemplatesTestTemplateBuildHandler.TEMPLATES_KEY);

        if (aux == null)
        {
            throw new MissingTemplatesException("per-sql-templates-test");
        }
        else
        {
            result = aux;
        }

        return result;
    }
}