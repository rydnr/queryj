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
 * Filename: FillAdapterHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Adapts non-TemplateContext-specific fill handlers to be used
 *              within QueryJ Chain-of-Responsibility processes.
 *
 * Date: 6/3/12
 * Time: 1:37 PM
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Imporing project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.templates.handlers.fillhandlers.FillHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adapts non-{@link org.acmsl.queryj.templates.TemplateContext}-specific
 * fill handlers to be used within QueryJ Chain-of-Responsibility processes.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@ThreadSafe
public class FillAdapterHandler<F extends FillHandler<P>, P>
    implements QueryJCommandHandler<QueryJCommand>,
               FillHandler<P>
{

    private static final long serialVersionUID = 6058212934620381875L;
    /**
     * The fill handler.
     */
    @NotNull
    private F fillHandler;

    /**
     * Creates a {@link FillAdapterHandler instance} to adapt given
     * fill handler.
     * @param fillHandler the fill handler to adapt.
     */
    public FillAdapterHandler(@NotNull final F fillHandler)
    {
        immutableSetFillHandler(fillHandler);
    }

    /**
     * Specifies the {@link org.acmsl.queryj.templates.handlers.fillhandlers.TemplateContextFillHandler fill handler}.
     * @param handler the fill handler.
     */
    protected final void immutableSetFillHandler(@NotNull final F handler)
    {
        this.fillHandler = handler;
    }

    /**
     * Specifies the {@link org.acmsl.queryj.templates.handlers.fillhandlers.TemplateContextFillHandler fill handler}.
     * @param handler the fill handler.
     */
    @SuppressWarnings("unused")
    protected void setFillHandler(@NotNull final F handler)
    {
        immutableSetFillHandler(handler);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.templates.handlers.fillhandlers.TemplateContextFillHandler fill handler}.
     * @return such instance.
     */
    @NotNull
    public F getFillHandler()
    {
        return this.fillHandler;
    }

    /**
     * Handles given command.
     * @param command the command.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the process fails.
     */
    @SuppressWarnings("unchecked")
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return handle(command.getAttributeMap(), getFillHandler());
    }

    /**
     * Handles given command.
     * @param attributes the attribute map.
     * @param fillHandler the {@link FillHandler fill handler}.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the process fails.
     */
    @SuppressWarnings("unchecked")
    public boolean handle(@NotNull final Map attributes, @NotNull final FillHandler<P> fillHandler)
        throws QueryJBuildException
    {
        final boolean result = false;

        @NotNull final String t_strPlaceHolder = fillHandler.getPlaceHolder();
        @Nullable final P t_Value = fillHandler.getValue();

        if (t_Value != null)
        {
            attributes.put(t_strPlaceHolder, t_Value);
        }

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "FillAdapterHandler{ fillHandler=" + fillHandler + '}';
    }

    /**
     * Retrieves the placeholder.
     *
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return getFillHandler().getPlaceHolder();
    }

    /**
     * Retrieves the template value for that placeholder.
     *
     * @return the dynamic value.
     */
    @Nullable
    @Override
    public P getValue() throws QueryJBuildException
    {
        return getFillHandler().getValue();
    }
}
