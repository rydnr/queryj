/*
                  QueryJ's Template Packaging

    Copyright (C) 2013-today Jose San Leandro Armendariz
                              queryj@acm-sl.org

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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Author: QueryJ's Template Packaging
 *
 * Filename: PerTableTemplatesTestTemplateWritingHandler.java
 *
 * Description: Writing handler for PerTablesTemplatesTestTemplates.
 *
 * Generated initially by QueryJ Template Packaging's
 * org/acmsl/templates/packaging/TemplateWritingHandler.stg
 * at timestamp: 2013/11/10 17:48
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.PerTableTemplatesTestTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;

/*
 * Importing some JetBrains annotations.
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
import java.util.ArrayList;
import java.util.List;

/**
 * Writing handler for {@link PerTableTemplatesTestTemplate}s.
 * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ's Template Packaging</a>
 * Generation template: org/acmsl/templates/packaging/TemplateWritingHandler.stg
 * @since 3.0
 * Created: 2013/11/10 17:48
 */
@ThreadSafe
public class PerTableTemplatesTestTemplateWritingHandler
    extends TemplatePackagingWritingHandler
                <PerTableTemplatesTestTemplate,
                    TemplatePackagingTemplateGenerator<PerTableTemplatesTestTemplate,
                        GlobalTemplateContext>,
                    GlobalTemplateContext>
{
    /**
     * Creates a new writing handler for {@link PerTableTemplatesTestTemplate templates}.
     */
    public PerTableTemplatesTestTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @param caching whether to enable template caching.
     * @param threadCount the number of threads to use.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplatePackagingTemplateGenerator<PerTableTemplatesTestTemplate, GlobalTemplateContext> retrieveTemplateGenerator(
        final boolean caching, final int threadCount)
    {
        return
            new TemplatePackagingTemplateGenerator
                <PerTableTemplatesTestTemplate, GlobalTemplateContext>(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected List<PerTableTemplatesTestTemplate> retrieveTemplates(@NotNull final QueryJCommand parameters)
    {
        @NotNull final List<PerTableTemplatesTestTemplate> result;

        @Nullable final List<PerTableTemplatesTestTemplate> aux =
            new QueryJCommandWrapper<PerTableTemplatesTestTemplate>(parameters)
                .getListSetting(PerTableTemplatesTestTemplateBuildHandler.TEMPLATES_KEY);

        if (aux == null)
        {
            result = new ArrayList<PerTableTemplatesTestTemplate>(0);
        }
        else
        {
            result = aux;
        }

        return result;
    }
}
