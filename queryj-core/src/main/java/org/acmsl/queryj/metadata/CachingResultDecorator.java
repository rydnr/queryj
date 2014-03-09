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
 * Filename: CachingResultDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating <result>
 *              instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adds a simple caching mechanism while decorating <code>ResultElement</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingResultDecorator
    extends  AbstractResultDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -381471036464860260L;

    /**
     * The cached properties.
     */
    private List<Property<DecoratedString>> m__lCachedProperties;

    /**
     * The cached LOB properties.
     */
    private List<Property<DecoratedString>> m__lCachedLobProperties;

    /**
     * The cached 'implicit' flag.
     */
    private Boolean m__bCachedImplicit;

    /**
     * The cached implicit properties.
     */
    private List<Property<DecoratedString>> m__lCachedImplicitProperties;

    /**
     * The cached information about whether the result is wrapping a single property.
     */
    private Boolean m__bCachedWrappingASingleProperty;

    /**
     * Creates a <code>CachingResultElementDecorator</code> with given instance.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the <code>MetadataManager</code> instance.
     */
    public CachingResultDecorator(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(result, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedProperties(
        @NotNull final List<Property<DecoratedString>> value)
    {
        m__lCachedProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedProperties(@NotNull final List<Property<DecoratedString>> value)
    {
        immutableSetCachedProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property<DecoratedString>> getCachedProperties()
    {
        return m__lCachedProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Property<DecoratedString>> getProperties()
    {
        List<Property<DecoratedString>> result = getCachedProperties();

        if  (result == null)
        {
            result = super.getProperties();
            setCachedProperties(result);
        }

        return result;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedLobProperties(
        @NotNull final List<Property<DecoratedString>> value)
    {
        m__lCachedLobProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedLobProperties(@NotNull final List<Property<DecoratedString>> value)
    {
        immutableSetCachedLobProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property<DecoratedString>> getCachedLobProperties()
    {
        return m__lCachedLobProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getLobProperties()
    {
        List<Property<DecoratedString>> result = getCachedLobProperties();

        if  (result == null)
        {
            result = super.getLobProperties();
            setCachedLobProperties(result);
        }

        return result;
    }

    /**
     * Specifies whether the result is implicit or not.
     * @param flag such condition.
     */
    protected final void immutableSetCachedImplicit(final boolean flag)
    {
        m__bCachedImplicit = flag;
    }

    /**
     * Specifies whether the result is implicit or not.
     * @param flag such condition.
     */
    protected void setCachedImplicit(final boolean flag)
    {
        immutableSetCachedImplicit(flag);
    }

    /**
     * Retrieves whether the result is implicit or not.
     * @return such information.
     */
    @Nullable
    protected Boolean getCachedImplicit()
    {
        return m__bCachedImplicit;
    }

    /**
     * Checks whether the result is 'implicit' (associated to a table) or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isImplicit()
    {
        @Nullable Boolean result = getCachedImplicit();

        if (result == null)
        {
            result = super.isImplicit();
            setCachedImplicit(result.booleanValue());
        }

        return result;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedImplicitProperties(
        @NotNull final List<Property<DecoratedString>> value)
    {
        m__lCachedImplicitProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedImplicitProperties(@NotNull final List<Property<DecoratedString>> value)
    {
        immutableSetCachedImplicitProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property<DecoratedString>> getCachedImplicitProperties()
    {
        return m__lCachedImplicitProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getImplicitProperties()
    {
        List<Property<DecoratedString>> result = getCachedImplicitProperties();

        if  (result == null)
        {
            result = super.getImplicitProperties();
            setCachedImplicitProperties(result);
        }

        return result;
    }

    /**
     * Annotates in the cache whether this result is wrapping a single property.
     * @param flag such flag.
     */
    protected final void immutableSetCachedWrappingASingleProperty(@NotNull final Boolean flag)
    {
        this.m__bCachedWrappingASingleProperty = flag;
    }

    /**
     * Annotates in the cache whether this result is wrapping a single property.
     * @param flag such flag.
     */
    protected void setCachedWrappingASingleProperty(@NotNull final Boolean flag)
    {
        immutableSetCachedWrappingASingleProperty(flag);
    }

    /**
     * Retrieves whether the information about if this result is wrapping single property, is already cached or not.
     * @return such information.
     */
    protected Boolean getCachedWrappingASingleProperty()
    {
        return this.m__bCachedWrappingASingleProperty;
    }

    /**
     * Retrieves whether this {@link org.acmsl.queryj.customsql.Result} is wrapping a single {@link Property}.
         * @return <code>true</code> in such case.
     */
    @Override
    public boolean isWrappingASingleProperty()
    {
        @Nullable Boolean result = getCachedWrappingASingleProperty();

        if (result == null)
        {
            result = super.isWrappingASingleProperty();
            setCachedWrappingASingleProperty(result);
        }

        return result;
    }
}
