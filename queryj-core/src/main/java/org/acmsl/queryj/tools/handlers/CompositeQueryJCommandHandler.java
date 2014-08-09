//;-*- mode: java -*-
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
 * Filename: CompositeQueryJCommandHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: General-purpose AntCommandHandler composed of an arbitrary
 *              number of handlers, to which the logic is delegated to,
 *              following GoF's Composite pattern.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * General-purpose AntCommandHandler composed of an arbitrary number
 * of handlers, to which the logic is delegated to, following GoF's
 * <b>Composite pattern</b>.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <C> the command type.
 * @param <CH> the command handler type.
 */
@ThreadSafe
public class CompositeQueryJCommandHandler<C extends QueryJCommand, CH extends QueryJCommandHandler<C>>
    implements  QueryJCommandHandler<C>
{
    /**
     * The handler system property prefix.
     */
    protected static final String HANDLER_SYSTEM_PROPERTY_PREFIX = QueryJSettings.PREFIX;

    /**
     * The handler system property suffix.
     */
    protected static final String HANDLER_SYSTEM_PROPERTY_SUFFIX = ".enabled";

    /**
     * The wildcard system property.
     */
    protected static final String WILDCARD_SYSTEM_PROPERTY =
        HANDLER_SYSTEM_PROPERTY_PREFIX + "all_handlers" + HANDLER_SYSTEM_PROPERTY_SUFFIX;

    /**
     * The handler collection.
     */
    private Collection<CH> m__cHandlers;

    /**
     * Builds a {@link CompositeQueryJCommandHandler} instance.
     */
    public CompositeQueryJCommandHandler()
    {
        immutableSetHandlerCollection(new ArrayList<CH>());
    }

    /**
     * Specifies the handler collection.
     * @param collection such collection.
     */
    private void immutableSetHandlerCollection(
        @NotNull final Collection<CH> collection)
    {
        m__cHandlers = collection;
    }

    /**
     * Specifies the handler collection.
     * @param collection such collection.
     */
    @SuppressWarnings("unused")
    protected void setHandlerCollection(
        @NotNull final Collection<CH> collection)
    {
        immutableSetHandlerCollection(collection);
    }

    /**
     * Retrieves the template build handler.
     * @return such handler.
     */
    @NotNull
    protected Collection<CH> getHandlerCollection()
    {
        return m__cHandlers;
    }

    /**
     * Adds a new handler to the collection.
     * @param handler the new handler to add.
     */
    protected void addHandler(final CH handler)
    {
        addHandler(handler, getHandlerCollection());
    }

    /**
     * Adds a new handler to the collection.
     * @param handler the new handler to add.
     * @param handlerCollection the handler collection.
     */
    protected void addHandler(
        @Nullable final CH handler,
        @Nullable final Collection<CH> handlerCollection)
    {
        if  (   (handler != null)
             && (handlerCollection != null))
        {
            handlerCollection.add(handler);
        }
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final C command)
        throws QueryJBuildException
    {
        return handle(command, getHandlerCollection());
    }

    /**
     * Handles given command.
     * @param command the command.
     * @param handlerCollection the handler collection.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the wrapped handlers fail.
     */
    protected boolean handle(
        @NotNull final C command,
        @Nullable final Collection<CH> handlerCollection)
      throws  QueryJBuildException
    {
        boolean result = false;

        @Nullable final Iterator<CH> t_itHandlerIterator =
            (handlerCollection != null)
            ?  handlerCollection.iterator()
            :  null;

        if  (t_itHandlerIterator != null)
        {
            while  (t_itHandlerIterator.hasNext())
            {
                @NotNull final CH t_Handler = t_itHandlerIterator.next();

                result = handle(command, t_Handler);

                if  (result)
                {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Handles given command.
     * @param command the command.
     * @param handler the concrete handler.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the handler fail.
     */
    protected boolean handle(
        @NotNull final C command, @NotNull final CH handler)
      throws  QueryJBuildException
    {
        return handler.handle(command);
    }

    /**
     * Checks whether this handle is allowed to proceed or not.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean canProceed()
    {
        boolean result = true;

        @Nullable final String t_strWildcardProperty = System.getProperty(WILDCARD_SYSTEM_PROPERTY);

        if (!Boolean.valueOf(t_strWildcardProperty))
        {
            result = false;
        }

        if (!result)
        {
            @Nullable final String t_strHandlerProperty =
                System.getProperty(
                      HANDLER_SYSTEM_PROPERTY_PREFIX
                    + getClass().getName()
                    + HANDLER_SYSTEM_PROPERTY_SUFFIX);

            result = "true".equalsIgnoreCase(t_strHandlerProperty);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m__cHandlers).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
    {
        final boolean result;

        if (obj == null)
        {
            result = false;
        }
        else if (getClass() != obj.getClass())
        {
            result = false;
        }
        else
        {
            @SuppressWarnings("unchecked")
            final CompositeQueryJCommandHandler<C, CH> other = (CompositeQueryJCommandHandler<C, CH>) obj;

            result = new EqualsBuilder().append(this.m__cHandlers, other.m__cHandlers).isEquals();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return getHandlerCollection().toString();
    }
}
