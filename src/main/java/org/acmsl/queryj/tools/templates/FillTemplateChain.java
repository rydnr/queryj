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

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

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
     *
     * @param buildException the error that triggers this clean-up.
     * @param command        the command.
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
    public void process()
        throws QueryJBuildException
    {
        super.process();
    }
}
