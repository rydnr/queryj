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
 * Filename: QueryJCommandWrapper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps a QueryJCommand to provide type-safe access to its
 * settings.
 *
 * Date: 2013/08/10
 * Time: 10/15
 *
 */
package org.acmsl.queryj;

/**
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wraps a {@link QueryJCommand} to provide type-safe access
 * to its settings.
 * @param <T> the type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 */
@ThreadSafe
public class QueryJCommandWrapper<T>
{
    /**
     * The underlying command.
     */
    @NotNull
    private QueryJCommand m__Command;

    /**
     * Creates a wrapper for given command.
     * @param command the {@link QueryJCommand}.
     */
    public QueryJCommandWrapper(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Specifies the command.
     * @param command such command.
     */
    protected final void immutableSetCommand(@NotNull final QueryJCommand command)
    {
        this.m__Command = command;
    }

    /**
     * Specifies the command.
     * @param command such command.
     */
    @SuppressWarnings("unused")
    protected void setCommand(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Retrieves the command.
     * @return such command.
     */
    @NotNull
    protected QueryJCommand getCommand()
    {
        return this.m__Command;
    }

    /**
     * Retrieves a T setting from the command.
     * @param key the key.
     * @return the setting.
     */
    @Nullable
    public T getSetting(@NotNull final String key)
    {
        return getSetting(key, getCommand());
    }

    /**
     * Retrieves a T setting from the command.
     * @param key the key.
     * @return the setting.
     */
    @Nullable
    @SuppressWarnings("cast,unchecked")
    protected T getSetting(@NotNull final String key, @NotNull final QueryJCommand command)
    {
        return command.getObjectSetting(key);
    }

    /**
     * Retrieves a T setting from the command.
     * @param key the key.
     * @return the setting.
     */
    @Nullable
    public List<T> getListSetting(@NotNull final String key)
    {
        return getListSetting(key, getCommand());
    }

    /**
     * Retrieves a T setting from the command.
     * @param key the key.
     * @return the setting.
     */
    @Nullable
    @SuppressWarnings("cast,unchecked")
    protected List<T> getListSetting(@NotNull final String key, @NotNull final QueryJCommand command)
    {
        @Nullable final List<T> result;

        @Nullable final Object single = command.getObjectSetting(key);

        if (single == null)
        {
            result = (List<T>) command.getList(key);
        }
        else if (single.getClass().isArray())
        {
            result = Arrays.asList((T[]) single);
        }
        else if (List.class.isAssignableFrom(single.getClass()))
        {
            result = new ArrayList<>();
            result.addAll((List<T>) single);
        }
        else
        {
            result = new ArrayList<>(1);
            result.add((T) single);
        }

        return result;
    }

    /**
     * Specifies a new setting.
     * @param key the key.
     * @param value the value.
     */
    public void setSetting(@NotNull final String key, @Nullable final T value)
    {
        setSetting(key, value, getCommand());
    }

    /**
     * Specifies a new setting.
     * @param key the key.
     * @param value the value.
     * @param command the {@link QueryJCommand}.
     */
    protected void setSetting(@NotNull final String key, @Nullable final T value, @NotNull final QueryJCommand command)
    {
        command.setSetting(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"command\": " + m__Command
            + ", \"class\": \"QueryJCommandWrapper\", \"package\": \"org.acmsl.queryj\" }";
    }
}
