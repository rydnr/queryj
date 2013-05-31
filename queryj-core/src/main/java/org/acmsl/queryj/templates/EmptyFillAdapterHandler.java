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
 * Filename: EmptyFillAdapterHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Wraps a FillHandler to make its placeholders give empty
 *              strings.
 *
 * Date: 7/12/12
 * Time: 6:40 PM
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.templates.handlers.FillAdapterHandler;
import org.acmsl.queryj.templates.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Wraps a {@link FillHandler} to make its placeholders give empty strings.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/12
 */
@ThreadSafe
public class EmptyFillAdapterHandler<F extends FillHandler<P>, P>
    extends FillAdapterHandler<F, P>
{

    private static final long serialVersionUID = 4606530594124602364L;

    /**
     * Creates an empty fill adapter handler.
     * @param handler the actual handler.
     */
    public EmptyFillAdapterHandler(final F handler)
    {
        super(handler);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.templates.handlers.fillhandlers.TemplateContextFillHandler fill handler}.
     *
     * @return such instance.
     */
    @NotNull
    @Override
    public F getFillHandler()
    {
        return wrap(super.getFillHandler());
    }

    /**
     * Wraps given handler.
     * @param handler the handler.
     * @return the wrapped handler.
     */
    @SuppressWarnings("unchecked")
    protected F wrap(final F handler)
    {
        return (F) new EmptyFillHandler(handler);
    }

    protected static class EmptyFillHandler<F extends FillHandler<P>, P>
        implements FillHandler<P>
    {

        private static final long serialVersionUID = -1540206214719043458L;
        /**
         * The fill handler to wrap.
         */
        private F fillHandler;

        /**
         * Creates a new wrapper for given handler.
         * @param handler the handler to wrap.
         */
        public EmptyFillHandler(@NotNull final F handler)
        {
            immutableSetFillHandler(handler);
        }

        /**
         * Specifies the wrapped handler.
         * @param handler the handler.
         */
        protected final void immutableSetFillHandler(@NotNull final F handler)
        {
            this.fillHandler = handler;
        }

        /**
         * Specifies the wrapped handler.
         * @param handler the handler.
         */
        @SuppressWarnings("unused")
        protected void setFillHandler(@NotNull final F handler)
        {
            immutableSetFillHandler(handler);
        }

        /**
         * Retrieves the wrapped handler.
         * @return such handler.
         */
        @NotNull
        public F getFillHandler()
        {
            return this.fillHandler;
        }

        /**
         * Retrieves the placeholder.
         * @return such placeholder.
         */
        @NotNull
        @Override
        public String getPlaceHolder()
        {
            return getPlaceHolder(getFillHandler());
        }

        /**
         * Retrieves the placeholder.
         * @param fillHandler the fill handler.
         * @return such placeholder.
         */
        @NotNull
        protected String getPlaceHolder(@NotNull final F fillHandler)
        {
            return fillHandler.getPlaceHolder();
        }

        /**
         * Retrieves the template value for that placeholder.
         * @return the dynamic value.
         */
        @Override
        public P getValue() throws QueryJBuildException
        {
            return getFillHandler().getValue();
        }

        /**
         * Handles given command.
         *
         * @param command the command.
         * @return <code>true</code> to avoid further processing of such command
         *         by different handlers.
         * @throws org.acmsl.queryj.tools.QueryJBuildException
         *          if the build process cannot be performed.
         */
        @Override
        public boolean handle(@NotNull final QueryJCommand command)
            throws QueryJBuildException
        {
            return false;
        }

        @NotNull
        @Override
        public String toString()
        {
            return "EmptyFillHandler{ fillHandler=" + fillHandler + '}';
        }
    }
}
