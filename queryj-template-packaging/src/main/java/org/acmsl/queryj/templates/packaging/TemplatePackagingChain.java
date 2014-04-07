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
 * Filename: QueryJChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the chain flow of QueryJ.
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing some QueryJ-Core classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.packaging.handlers.DefaultTemplateChainProviderTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.PerTableTemplatesFeatureTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.PerTableTemplatesTestTemplateHandlerBundle;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.handlers.Log4JInitializerHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing some QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.handlers.FindTemplateDefsHandler;
import org.acmsl.queryj.templates.packaging.handlers.ParseTemplateDefsHandler;
import org.acmsl.queryj.templates.packaging.handlers.TemplateBuildHandlerTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.TemplateFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.TemplateGeneratorTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.TemplateHandlerBundleTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.TemplatePackagingParameterValidationHandler;
import org.acmsl.queryj.templates.packaging.handlers.TemplateTemplateHandlerBundle;
import org.acmsl.queryj.templates.packaging.handlers.TemplateWritingHandlerTemplateHandlerBundle;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the steps performed by QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 3.0
 */
@ThreadSafe
public class TemplatePackagingChain<CH extends QueryJCommandHandler<QueryJCommand>>
    extends AbstractQueryJChain<QueryJCommand, CH>
    implements TemplatePackagingSettings
{
    /**
     * Creates a {@link QueryJChain} with given information.
     */
    public TemplatePackagingChain()
    {
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected Chain<QueryJCommand, QueryJBuildException, CH> buildChain(
        @NotNull final Chain<QueryJCommand, QueryJBuildException, CH> chain)
        throws QueryJBuildException
    {
        chain.add((CH) new TemplatePackagingParameterValidationHandler());

        chain.add((CH) new Log4JInitializerHandler());

        chain.add((CH) new FindTemplateDefsHandler());

        chain.add((CH) new ParseTemplateDefsHandler());

        chain.add((CH) new TemplateBuildHandlerTemplateHandlerBundle());

        chain.add((CH) new TemplateFactoryTemplateHandlerBundle());

        chain.add((CH) new TemplateGeneratorTemplateHandlerBundle());

        chain.add((CH) new TemplateHandlerBundleTemplateHandlerBundle());

        chain.add((CH) new TemplateTemplateHandlerBundle());

        chain.add((CH) new TemplateWritingHandlerTemplateHandlerBundle());

        chain.add((CH) new PerTableTemplatesTestTemplateHandlerBundle());

//        chain.add((CH) new PerTableTemplatesFeatureTemplateHandlerBundle());

        chain.add((CH) new DefaultTemplateChainProviderTemplateHandlerBundle());

        return chain;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    @Override
    protected void cleanUpOnError(
        @NotNull final QueryJBuildException buildException, @NotNull final QueryJCommand command)
    {
    }
}
