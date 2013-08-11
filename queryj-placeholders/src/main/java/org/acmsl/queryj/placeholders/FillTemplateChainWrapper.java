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
 * Filename: FillTemplateChainWrapper.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 6/11/13
 * Time: 6:31 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ-core classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.NonRelevantFillHandler;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing Apache Commons Configuration.
 */
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a given chain to add generic, stateless placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/11
 */
public class FillTemplateChainWrapper<C extends TemplateContext>
    implements FillTemplateChain<C>
{
    /**
     * The wrapped chain.
     */
    private FillTemplateChain<C> chain;

    /**
     * Creates a new wrapper for given chain.
     * @param chain the chain to wrap, in order to provide stateless placeholder
     * providers as well.
     */
    public FillTemplateChainWrapper(@NotNull final FillTemplateChain<C> chain)
    {
        immutableSetWrappedChain(chain);
    }

    /**
     * Specifies the wrapped chain.
     * @param chain the chain to wrap.
     */
    protected final void immutableSetWrappedChain(@NotNull final FillTemplateChain<C> chain)
    {
        this.chain = chain;
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
        return this.chain;
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
     * @throws QueryJBuildException if the process fails.
     */
    @NotNull
    @Override
    public QueryJCommand providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException
    {
        return providePlaceholders(relevantOnly, getTemplateContext());
    }

    /**
     * Provides the template placeholders within a {@link QueryJCommand}.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually,
     * generation timestamps, documentation, etc. can be considered not relevant.
     * @param templateContext the template context.
     * @return the {@link QueryJCommand}.
     * @throws QueryJBuildException if the process fails.
     */
    @NotNull
    protected QueryJCommand providePlaceholders(final boolean relevantOnly, @NotNull final C templateContext)
        throws QueryJBuildException
    {
        return providePlaceholders(relevantOnly, getHandlers(templateContext));
    }

    /**
     * Provides the template placeholders within a {@link QueryJCommand}.
     * @param relevantOnly to include only the relevant ones: the ones that are necessary to
     * be able to find out if two template realizations are equivalent. Usually,
     * generation timestamps, documentation, etc. can be considered not relevant.
     * @param handlers the {@link FillHandler}s.
     * @return the {@link QueryJCommand}.
     * @throws QueryJBuildException if the process fails.
     */
    @NotNull
    protected QueryJCommand providePlaceholders(
        final boolean relevantOnly,
        @NotNull final List<FillHandler> handlers)
        throws QueryJBuildException
    {
        @NotNull final QueryJCommand result;

        @NotNull final Configuration t_Configuration = new PropertiesConfiguration();

        for (@NotNull final FillHandler handler : handlers)
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
                t_Configuration, UniqueLogFactory.getLog(FillTemplateChainWrapper.class));

        return result;
    }

    /**
     * Retrieves the list of generic placeholder handlers.
     * @return such list.
     */
    @SuppressWarnings("unchecked")
    protected List<FillHandler> getHandlers(@NotNull final C context)
    {
        @NotNull final List result = new ArrayList();

        result.add(new AreTimestampsAllowedHandler(context));
        result.add(new ClassNameHandler<C>(context));
        result.add(new CopyrightYearsHandler());
        result.add(new CurrentYearHandler());
        result.add(new DAOChooserPropertiesFileNameHandler(context));
        result.add(new DAOSubpackageNameHandler(context));
        result.add(new DatabaseEngineNameHandler(context));
        result.add(new DatabaseEngineVersionHandler(context));
        result.add(new FileNameHandler(context));
        result.add(new HeaderHandler(context));
        result.add(new IsRepositoryDAOHandler(context));
        result.add(new JndiLocationFillHandler(context));
        result.add(new LobHandlingFlavorHandler(context));
        result.add(new LobHandlingRepositoryCheckHandler(context));
        result.add(new PackageNameHandler(context));
        result.add(new ProjectPackageHandler(context));
        result.add(new RepositoryNameHandler(context));
        result.add(new SerialVersionUIDHandler(context));
        result.add(new TimestampHandler());
        result.add(new TemplateNameHandler(context));
        result.add(new UseCheckthreadAnnotationsHandler(context));
        result.add(new UseNotNullAnnotationsHandler(context));

        return result;
    }


    @Override
    public String toString()
    {
        return "FillTemplateChainWrapper{" +
               "chain=" + chain +
               '}';
    }
}
