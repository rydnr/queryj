/*
                        QueryJ Core

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
 * Filename: AbstractFillTemplateChainWrapper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps a given chain to add generic, stateless placeholders.
 *
 * Date: 2013/08/25
 * Time: 16:17
 *
 */
package org.acmsl.queryj.api.placeholders;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.api.AbstractFillTemplateChain;
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.NonRelevantFillHandler;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.Configuration;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Wraps a given chain to add generic, stateless placeholders.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/25 16:17
 */
@ThreadSafe
public abstract class AbstractFillTemplateChainWrapper<C extends TemplateContext>
    extends AbstractFillTemplateChain<C>
{
    /**
     * The wrapped chain.
     */
    private FillTemplateChain<C> m__Chain;

    /**
     * Creates a new wrapper for given chain.
     * @param chain the chain to wrap, in order to provide stateless placeholder
     * providers as well.
     */
    public AbstractFillTemplateChainWrapper(@NotNull final FillTemplateChain<C> chain)
    {
        super(chain.getTemplateContext());
        immutableSetWrappedChain(chain);
    }

    /**
     * Specifies the wrapped chain.
     * @param chain the chain to wrap.
     */
    protected final void immutableSetWrappedChain(@NotNull final FillTemplateChain<C> chain)
    {
        this.m__Chain = chain;
    }

    /**
     * Specifies the wrapped chain.
     * @param chain the chain to wrap.
     */
    @SuppressWarnings("unused")
    protected void setWrappedChain(@NotNull final FillTemplateChain<C> chain)
    {
        immutableSetWrappedChain(chain);
    }

    /**
     * Retrieves the wrapped chain.
     * @return such chain.
     */
    public FillTemplateChain<C> getWrappedChain()
    {
        return this.m__Chain;
    }

    /**
     * Retrieves the template context.
     * @return such information.
     */
    @NotNull
    @Override
    public C getTemplateContext()
    {
        return getWrappedChain().getTemplateContext();
    }

    /**
     * Performs the required processing.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually,
     * generation timestamps,
     * documentation, etc. can be considered not relevant.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public QueryJCommand providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException
    {
        // Don't know how to fix the generics warnings
        @NotNull final List t_lHandlers = getWrappedChain().getHandlers();

        t_lHandlers.addAll(getHandlers());

        return providePlaceholders(relevantOnly, t_lHandlers);
    }

    /**
     * Provides the template placeholders within a {@link QueryJCommand}.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually,
     * generation timestamps, documentation, etc. can be considered not relevant.
     * @param handlers the {@link FillHandler}s.
     * @return the {@link QueryJCommand}.
     * @throws QueryJBuildException if the placeholders are unavailable.
     */
    @NotNull
    protected QueryJCommand providePlaceholders(
        final boolean relevantOnly,
        @NotNull final List<FillHandler<?>> handlers)
        throws QueryJBuildException
    {
        @NotNull final QueryJCommand result;

        @NotNull final Configuration t_Configuration = new SerializablePropertiesConfiguration();

        for (@NotNull final FillHandler<?> handler : handlers)
        {
            if (   (!relevantOnly)
                || (!(handler instanceof NonRelevantFillHandler)))
            {
                t_Configuration.setProperty(handler.getPlaceHolder(), handler.getValue());
            }
            else
            {
                t_Configuration.setProperty(handler.getPlaceHolder(), "");
            }
        }

        result =
            new ConfigurationQueryJCommandImpl(
                t_Configuration, UniqueLogFactory.getLog(AbstractFillTemplateChainWrapper.class));

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractFillTemplateChainWrapper.class.getName() + "\""
            + ", \"chain\": \"" + m__Chain + "\" }";
    }
}
