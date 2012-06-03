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
 * Filename: TemplateFillAdapterHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Adapts fill handlers to be used as regular handlers in a
 *              chain-of-responsibility process flow.
 *
 * Date: 6/3/12
 * Time: 11:58 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.tools.templates.TemplateContext;
import org.acmsl.queryj.tools.templates.handlers.fillhandlers.TemplateContextFillHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Adapts fill handlers to be used as regular handlers in a chain-of-responsibility
 * process flow.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
public class TemplateContextFillAdapterHandler<C extends TemplateContext, F extends TemplateContextFillHandler<C,P>, P>
    implements QueryJCommandHandler
{
    /**
     * The fill handler.
     */
    @NotNull private F fillHandler;

    /**
     * Creates a {@link TemplateContextFillAdapterHandler instance} to adapt given
     * {@link TemplateContextFillHandler fill handler}.
     * @param fillHandler the fill handler to adapt.
     */
    public TemplateContextFillAdapterHandler(@NotNull final F fillHandler)
    {
        immutableSetFillHandler(fillHandler);
    }

    /**
     * Specifies the {@link TemplateContextFillHandler fill handler}.
     * @param handler the fill handler.
     */
    protected final void immutableSetFillHandler(@NotNull final F handler)
    {
        this.fillHandler = handler;
    }

    /**
     * Specifies the {@link TemplateContextFillHandler fill handler}.
     * @param handler the fill handler.
     */
    @SuppressWarnings("unused")
    protected void setFillHandler(@NotNull final F handler)
    {
        immutableSetFillHandler(handler);
    }

    /**
     * Retrieves the {@link TemplateContextFillHandler fill handler}.
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
     */
    public boolean handle(@NotNull final Command command)
    {
        boolean result = false;

        try
        {
            result = handle((QueryJCommand) command);
        }
        catch (@NotNull final QueryJBuildException templateError)
        {
            Log t_Log = UniqueLogFactory.getLog(TemplateContextFillAdapterHandler.class);

            if (t_Log != null)
            {
                t_Log.fatal("Cannot process template using " + getFillHandler(), templateError);
            }
        }

        return result;
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
     * @param fillHandler the {@link TemplateContextFillHandler fill handler}.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the process fails.
     */
    @SuppressWarnings("unchecked")
    public boolean handle(@NotNull final Map attributes, @NotNull final F fillHandler)
        throws QueryJBuildException
    {
        boolean result = false;

        @NotNull String t_strPlaceHolder = fillHandler.getPlaceHolder();
        @NotNull P t_Value = fillHandler.getValue();

        attributes.put(t_strPlaceHolder, t_Value);

        return result;
    }

    /**
     * Handles given command.
     * @param fillHandler the {@link TemplateContextFillHandler fill handler}.
     */
    protected void handle(@NotNull final F fillHandler)
    {

    }
}
