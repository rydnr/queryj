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
 * Filename: QueryJChain.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the chain flow of QueryJ.
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.AbstractQueryJChain;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.CannotFindTemplatesException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.templates.packaging.handlers.FindTemplateDefsHandler;
import org.acmsl.queryj.templates.packaging.handlers.ParseTemplateDefsHandler;
import org.acmsl.queryj.templates.packaging.handlers.TemplatePackagingParameterValidationHandler;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.TemplateChainProvider;
import org.acmsl.queryj.tools.handlers.Log4JInitializerHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JDK classes.
 */
import java.util.ServiceLoader;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the steps performed by QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class TemplatePackagingChain<CH extends QueryJCommandHandler<QueryJCommand>>
    extends AbstractQueryJChain<CH>
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
     * @throws QueryJBuildException if the chain cannot be built successfully.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected Chain<CH> buildChain(@NotNull final Chain<CH> chain)
        throws QueryJBuildException
    {
        chain.add((CH) new TemplatePackagingParameterValidationHandler());

        chain.add((CH) new Log4JInitializerHandler());

        chain.add((CH) new FindTemplateDefsHandler());

        chain.add((CH) new ParseTemplateDefsHandler());

//        fillTemplateHandlers(chain);

        return chain;
    }

    /**
     * Fills given chain with external template bundles.
     * @param chain the chain.
     * @throws QueryJBuildException if the chain cannot be built successfully.
     */
    @SuppressWarnings("unchecked")
    protected void fillTemplateHandlers(@NotNull final Chain<CH> chain)
        throws QueryJBuildException
    {
        @NotNull final ServiceLoader<TemplateChainProvider> loader =
            ServiceLoader.load(TemplateChainProvider.class);

        if (loader.iterator().hasNext())
        {
            @NotNull final TemplateChainProvider<TemplateHandler> provider = loader.iterator().next();

            for (@Nullable final TemplateHandler handler : provider.getHandlers())
            {
                if (handler != null)
                {
                    chain.add((CH) handler);
                }
            }
        }
        else
        {
            throw new CannotFindTemplatesException(TemplateChainProvider.class);
        }
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
