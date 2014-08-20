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
 * Filename: ConfigurationQueryJCommandImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Apache Commons Configuration-based implementation of QueryJCommand.
 *
 * Date: 2013/08/09
 * Time: 22:56
 *
 */
package org.acmsl.queryj;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.Configuration;

/**
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

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
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Apache Commons Configuration-based implementation of {@link QueryJCommand}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/09
 */
@ThreadSafe
public class ConfigurationQueryJCommandImpl
    implements QueryJCommand,
               Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3568357454519731023L;

    /**
     * The attribute collection.
     */
    private Configuration m__Configuration;

    /**
     * The reference to the log instance.
     */
    private transient Log m__Log;

    /**
     * Constructs a command.
     * @param configuration the {@link Configuration}.
     */
    public ConfigurationQueryJCommandImpl(@NotNull final Configuration configuration)
    {
        immutableSetConfiguration(configuration);
    }

    /**
     * Constructs a command.
     * @param configuration the {@link Configuration}.
     * @param log the log instance.
     */
    @SuppressWarnings("unused")
    public ConfigurationQueryJCommandImpl(
        @NotNull final Configuration configuration, @Nullable final Log log)
    {
        this(configuration);
        if (log != null)
        {
            immutableSetLog(log);
        }
    }

    /**
     * Specifies the configuration.
     * @param configuration such configuration.
     */
    protected final void immutableSetConfiguration(@NotNull final Configuration configuration)
    {
        this.m__Configuration = configuration;
    }

    /**
     * Specifies the configuration.
     * @param configuration such configuration.
     */
    @SuppressWarnings("unused")
    public void setConfiguration(@NotNull final Configuration configuration)
    {
        immutableSetConfiguration(configuration);
    }

    /**
     * Retrieves the configuration.
     * @return such {@link Configuration} instance.
     */
    @NotNull
    public Configuration getConfiguration()
    {
        return m__Configuration;
    }

    /**
     * Specifies the log instance.
     * @param log the log instance.
     */
    protected final void immutableSetLog(@NotNull final Log log)
    {
        m__Log = log;
    }

    /**
     * Specifies the log instance.
     * @param log the log instance.
     */
    @SuppressWarnings("unused")
    protected void setLog(@NotNull final Log log)
    {
        immutableSetLog(log);
    }

    /**
     * Retrieves the log instance.
     * @return such instance.
     */
    @NotNull
    @Override
    public Log getLog()
    {
        return m__Log;
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Override
    @Nullable
    public String getStringSetting(@NotNull final String key)
    {
        return getSetting(key, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param <T> the type.
     * @return the value for such key.
     */
    @Override
    @Nullable
    public <T> T getSetting(@NotNull final String key)
    {
        return getSetting(key, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param configuration the {@link Configuration configuration} settings.
     * @param <T> the type.
     * @return the value for such key.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> T getSetting(@NotNull final String key, @NotNull final Configuration configuration)
    {
        return (T) configuration.getProperty(key);
    }

    /**
     * Specifies the setting for given key.
     * @param key the key.
     * @param value the value for such key.
     * @param <T> the type.
     */
    @Override
    public <T> void setSetting(@NotNull final String key, @Nullable final T value)
    {
        setSetting(key, value, getConfiguration());
    }

    /**
     * Specifies the setting for given key.
     * @param key the key.
     * @param value the value for such key.
     * @param configuration the {@link Configuration configuration} settings.
     * @param <T> the type.
     */
    protected <T> void setSetting(
        @NotNull final String key, @Nullable final T value, @NotNull final Configuration configuration)
    {
        configuration.setProperty(key, value);
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Nullable
    @Override
    public List<?> getList(@NotNull final String key)
    {
        return getList(key, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     * @param configuration the {@link Configuration configuration} settings.
     */
    @Nullable
    protected List<?> getList(@NotNull final String key, @NotNull final Configuration configuration)
    {
        return configuration.getList(key);
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @return the value for such key.
     */
    @Nullable
    @Override
    public File getFileSetting(@NotNull final String key)
    {
        return getSetting(key);
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @return the value for such key.
     */
    @Override
    public boolean getBooleanSetting(@NotNull final String key, final boolean defaultValue)
    {
        return getBooleanSetting(key, defaultValue, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @param settings the settings.
     * @return the value for such key.
     */
    protected boolean getBooleanSetting(
        @NotNull final String key, final boolean defaultValue, @NotNull final Configuration settings)
    {
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @return the value for such key.
     */
    @Override
    public int getIntSetting(@NotNull final String key, final int defaultValue)
    {
        return getIntSetting(key, defaultValue, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param defaultValue the default value.
     * @param settings the settings.
     * @return the value for such key.
     */
    protected int getIntSetting(
        @NotNull final String key, final int defaultValue, @NotNull final Configuration settings)
    {
        return settings.getInt(key, defaultValue);
    }

    /**
     * Retrieves all keys.
     * @return such keys.
     */
    @NotNull
    @Override
    public Iterable<String> getKeys()
    {
        return getKeys(getConfiguration());
    }

    /**
     * Retrieves all keys.
     * @param configuration the {@link Configuration}.
     * @return such keys.
     */
    @NotNull
    protected Iterable<String> getKeys(@NotNull final Configuration configuration)
    {
        return new Iterable<String>()
        {
            /**
             * {@inheritDoc}
             */
            @Override
            public Iterator<String> iterator()
            {
                return configuration.getKeys();
            }
        };
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param <T> the type.
     * @return the value for such key.
     */
    @Override
    public <T> T getObjectSetting(@NotNull final String key)
    {
        return getObjectSetting(key, getConfiguration());
    }

    /**
     * Retrieves the setting for given key.
     * @param key the key.
     * @param configuration the configuration.
     * @param <T> the type.
     * @return the value for such key.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getObjectSetting(@NotNull final String key, @NotNull final Configuration configuration)
    {
        return (T) configuration.getProperty(key);
    }

    /**
     * Returns a JSON representation of given {@link Configuration}.
     * @param conf the instance to represent.
     * @return the JSON string.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected String confToString(@NotNull final Configuration conf)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        @NotNull final Iterator<String> t_itKey = conf.getKeys();

        while (t_itKey.hasNext())
        {
            @NotNull final String t_strKey = t_itKey.next();
            result.append(", \"");
            result.append(t_strKey);
            result.append("\": \"");
            result.append(conf.getProperty(t_strKey));
            result.append('"');
        }

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"configuration\": "
            +   "{ \"class\": \"" + m__Configuration.getClass().getSimpleName()
            +    ", \"package\": \"" + m__Configuration.getClass().getPackage().getName() + '"'
//            +    confToString(m__Configuration)
            + '}'
            + ", \"log\": \"" + m__Log + '"'
            + ", \"class\": \"ConfigurationQueryJCommandImpl\""
            + ", \"package\": \"org.acmsl.queryj\""
            + "}";
    }
}
