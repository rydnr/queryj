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
 * Filename: DefaultTemplateChainProviderTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is responsible of writing the content generated by
 *              DefaultTemplateChainProviderTemplates.
 *
 * Date: 2013/09/14
 * Time: 12:25
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing QueryJ-Template-Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplateChainProviderTemplate;
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;

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
 * Is responsible of writing the content generated by {@link DefaultTemplateChainProviderTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/14 12:25
 */
@ThreadSafe
public class DefaultTemplateChainProviderTemplateWritingHandler
    extends TemplatePackagingWritingHandler<DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>, TemplatePackagingTemplateGenerator<DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>, DefaultTemplatePackagingContext>, DefaultTemplatePackagingContext>
{
    /**
     * Retrieves the template generator.
     * @param caching whether to enable template caching.
     * @param threadCount the number of threads to use.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplatePackagingTemplateGenerator<DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>, DefaultTemplatePackagingContext> retrieveTemplateGenerator(final boolean caching, final int threadCount)
    {
        return
            new TemplatePackagingTemplateGenerator
                <DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>,
                 DefaultTemplatePackagingContext>(caching, threadCount);
    }

    /**
     * Retrieves the templates from the command.
     * @param parameters the parameters.
     * @return the template.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException
     *          if the template retrieval process fails.
     */
    @Nullable
    @Override
    protected List<DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>> retrieveTemplates(
        @NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        return
            new QueryJCommandWrapper
                <DefaultTemplateChainProviderTemplate<DefaultTemplatePackagingContext>>(parameters)
                .getListSetting(DEFAULT_TEMPLATE_CHAIN_PROVIDER_TEMPLATE);
    }
}
