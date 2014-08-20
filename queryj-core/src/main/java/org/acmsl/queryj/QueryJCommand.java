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
 * Filename: QueryJCommand.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents QueryJ workflow commands.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL Java Commons classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.util.List;

/**
 * Represents QueryJ workflow commands.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public interface QueryJCommand
    extends  Command,
             QueryJSettings
{
    /**
     * Retrieves the log instance.
     * @return such instance.
     */
    @NotNull
    public Log getLog();

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Nullable
    public String getStringSetting(@NotNull final String key);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param <T> the object type.
     * @return the value for such key.
     */
    @Nullable
    public <T> T getSetting(@NotNull final String key);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Nullable
    public List<?> getList(@NotNull final String key);

    /**
     * Specifies the setting for given key.
     * @param key the key.
     * @param value the value for such key.
     * @param <T> the object type.
     */
    public <T> void setSetting(@NotNull final String key, @Nullable final T value);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Nullable
    public File getFileSetting(@NotNull final String key);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @return the value for such key.
     */
    public boolean getBooleanSetting(@NotNull final String key, final boolean defaultValue);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @return the value for such key.
     */
    public int getIntSetting(@NotNull final String key, final int defaultValue);

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param <T> the object type.
     * @return the value for such key.
     */
    @Nullable
    public <T> T getObjectSetting(@NotNull final String key);

    /**
     * Retrieves all keys.
     * @return such keys
     */
    @NotNull
    public Iterable<String> getKeys();
}
