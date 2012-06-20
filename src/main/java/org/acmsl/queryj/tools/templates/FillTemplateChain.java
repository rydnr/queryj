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
 * Filename: FillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain to provide all placeholder replacements in templates.
 *
 * Date: 6/3/12
 * Time: 12:34 PM
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.AbstractQueryJChain;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.templates.handlers.FillAdapterHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateContextFillAdapterHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.CopyrightYearsHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.CurrentYearHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.DAOSubpackageNameHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.DatabaseEngineNameHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.DatabaseEngineVersionHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.DecoratedString;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.HeaderHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.IsRepositoryDAOHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.ProjectPackageHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.RepositoryNameHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.SerialVersionUIDHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.SubPackageNameHandler;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.TimestampHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Sets up the chain to provide all placeholder replacements in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
public abstract class FillTemplateChain<C extends TemplateContext>
    extends AbstractQueryJChain
{
    /**
     * The template context.
     */
    private C templateContext;

    /**
     * Creates a new {@link FillTemplateChain} associated to given
     * {@link TemplateContext}.
     * @param context the template.
     */
    protected FillTemplateChain(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    protected final void immutableSetTemplateContext(@NotNull final C context)
    {
        this.templateContext = context;
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    @SuppressWarnings("unused")
    protected void setTemplateContext(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Retrieves the template context.
     * @return such information.
     */
    @NotNull
    public C getTemplateContext()
    {
        return templateContext;
    }

    /**
     * Builds the command.
     *
     * @param command the command to be initialized.
     * @return the initialized command.
     */
    @NotNull
    @Override
    protected QueryJCommand buildCommand(final QueryJCommand command)
    {
        return command;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    @Override
    protected void cleanUpOnError(final QueryJBuildException buildException, final QueryJCommand command)
    {
        // nothing required.
    }

    /**
     * Performs the required processing.
     * @throws QueryJBuildException if the process fails.
     */
    @SuppressWarnings("unchecked")
    public Map providePlaceholders()
        throws QueryJBuildException
    {
        Map result;

        @NotNull QueryJCommand t_Command = buildCommand();

        super.process(buildChain(getChain()), t_Command);

        result = t_Command.getAttributeMap();

        return result;
    }

    /**
     * Builds the chain.
     *
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    @Override
    @NotNull
    protected final Chain buildChain(@NotNull final Chain chain)
    {
        return buildChain(chain, getTemplateContext());
    }

    /**
     * Builds the chain.
     *
     * @param chain the chain to be configured.
     * @param context the context.
     * @return the updated chain.
     */
    @NotNull
    protected final Chain buildChain(@NotNull final Chain chain, @NotNull final C context)
    {
        chain.add(
            new FillAdapterHandler<CopyrightYearsHandler,Integer[]>(new CopyrightYearsHandler()));

        chain.add(
            new FillAdapterHandler<CurrentYearHandler,String>(new CurrentYearHandler()));

        chain.add(
            new FillAdapterHandler<TimestampHandler,String>(new TimestampHandler()));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,DAOSubpackageNameHandler,String>(
                new DAOSubpackageNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,DatabaseEngineNameHandler,DecoratedString>(
                new DatabaseEngineNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,DatabaseEngineVersionHandler,DecoratedString>(
                new DatabaseEngineVersionHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,HeaderHandler,DecoratedString>(
                new HeaderHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,IsRepositoryDAOHandler,Boolean>(
                new IsRepositoryDAOHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,ProjectPackageHandler,DecoratedString>(
                new ProjectPackageHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,RepositoryNameHandler,DecoratedString>(
                new RepositoryNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,SubPackageNameHandler,DecoratedString>(
                new SubPackageNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<TemplateContext,SerialVersionUIDHandler,Long>(
                new SerialVersionUIDHandler(context)));

        addHandlers(chain, context);

        return chain;
    }

    /**
     * Adds additional handlers.
     * @param chain the chain.
     * @param context the {@link TemplateContext context}.
     */
    protected abstract void addHandlers(@NotNull final Chain chain, @NotNull final C context);
}
