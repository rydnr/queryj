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
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing some JetBrains annotations.
 */
import org.apache.commons.logging.Log;
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
    private static final long serialVersionUID = -381471036464860260L;
    /**
     * The cached normalized id.
     */
    private String m__strCachedIdNormalized;

    /**
     * The cached capitalized id.
     */
    private String m__strCachedIdCapitalized;

    /**
     * The cached <i>multiple</i> information.
     */
    private Boolean m__bCachedMultiple;

    /**
     * The cached normalized uppercased id.
     */
    private String m__strCachedIdNormalizedUppercased;

    /**
     * The cached properties.
     */
    private List<Property> m__lCachedProperties;

    /**
     * The cached LOB properties.
     */
    private List<Property> m__lCachedLobProperties;

    /**
     * The cached 'implicit' flag.
     */
    private Boolean m__bCachedImplicit;

    /**
     * The cached implicit properties.
     */
    private List<Property> m__lCachedImplicitProperties;

    /**
     * The cached ValueObject-formatted name.
     */
    private String m__strCachedVoName;

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
        @NotNull final Result result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(result, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Specifies the cached normalized id.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdNormalized(
        @NotNull final String value)
    {
        m__strCachedIdNormalized = value;
    }

    /**
     * Specifies the cached normalized id.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedIdNormalized(@NotNull final String value)
    {
        immutableSetCachedIdNormalized(value);
    }

    /**
     * Retrieves the cached normalized id.
     * @return such value.
     */
    @Nullable
    public String getCachedIdNormalized()
    {
        return m__strCachedIdNormalized;
    }
    /**
     * Retrieves the id, normalized.
     * @return such information.
     */
    @NotNull
    public String getIdNormalized()
    {
        @Nullable String result = getCachedIdNormalized();

        if  (result == null)
        {
            result = super.getIdNormalized();
            setCachedIdNormalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdCapitalized(
        @NotNull final String value)
    {
        m__strCachedIdCapitalized = value;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedIdCapitalized(@NotNull final String value)
    {
        immutableSetCachedIdCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized id.
     * @return such value.
     */
    @Nullable
    public String getCachedIdCapitalized()
    {
        return m__strCachedIdCapitalized;
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    @NotNull
    public String getIdCapitalized()
    {
        @Nullable String result = getCachedIdCapitalized();

        if  (result == null)
        {
            result = super.getIdCapitalized();
            setCachedIdCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached normalized uppercased id.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdNormalizedUppercased(
        @NotNull final String value)
    {
        m__strCachedIdNormalizedUppercased = value;
    }

    /**
     * Specifies the cached normalized uppercased id.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedIdNormalizedUppercased(@NotNull final String value)
    {
        immutableSetCachedIdNormalizedUppercased(value);
    }

    /**
     * Retrieves the cached normalized uppercased id.
     * @return such value.
     */
    @Nullable
    public String getCachedIdNormalizedUppercased()
    {
        return m__strCachedIdNormalizedUppercased;
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    @NotNull
    public String getIdNormalizedUppercased()
    {
        @Nullable String result = getCachedIdNormalizedUppercased();

        if  (result == null)
        {
            result = super.getIdNormalizedUppercased();
            setCachedIdNormalizedUppercased(result);
        }

        return result;
    }

    /**
     * Specifies the cached <i>multiple</i> info.
     * @param multiple such information.
     */
    protected final void immutableSetCachedMultiple(@NotNull final Boolean multiple)
    {
        m__bCachedMultiple = multiple;
    }

    /**
     * Specifies the cached <i>multiple</i> info.
     * @param multiple such information.
     */
    @SuppressWarnings("unused")
    protected void setCachedMultiple(@NotNull final Boolean multiple)
    {
        immutableSetCachedMultiple(multiple);
    }

    /**
     * Retrieves the cached <i>multiple</i> info.
     * @return such information.
     */
    @Nullable
    protected Boolean getCachedMultiple()
    {
        return m__bCachedMultiple;
    }

    /**
     * Retrieves whether the result matches a single entity or expects
     * a set of them.
     * @return such information.
     */
    public boolean isMultiple()
    {
        Boolean result = getCachedMultiple();

        if  (result == null)
        {
            result = super.isMultiple();
            setCachedMultiple(result);
        }

        return result;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedProperties(
        @NotNull final List<Property> value)
    {
        m__lCachedProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedProperties(@NotNull final List<Property> value)
    {
        immutableSetCachedProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property> getCachedProperties()
    {
        return m__lCachedProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Property> getProperties()
    {
        List<Property> result = getCachedProperties();

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
        @NotNull final List<Property> value)
    {
        m__lCachedLobProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedLobProperties(@NotNull final List<Property> value)
    {
        immutableSetCachedLobProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property> getCachedLobProperties()
    {
        return m__lCachedLobProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    public List<Property> getLobProperties()
    {
        List<Property> result = getCachedLobProperties();

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
    protected final void immutableSetCachedImplicit(@NotNull final Boolean flag)
    {
        m__bCachedImplicit = flag;
    }

    /**
     * Specifies whether the result is implicit or not.
     * @param flag such condition.
     */
    protected void setCachedImplicit(@NotNull final Boolean flag)
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
        Boolean result = getCachedImplicit();

        if (result == null)
        {
            result = super.isImplicit();
            setCachedImplicit(result);
        }

        return result;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedImplicitProperties(
        @NotNull final List<Property> value)
    {
        m__lCachedImplicitProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedImplicitProperties(@NotNull final List<Property> value)
    {
        immutableSetCachedImplicitProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    @Nullable
    public List<Property> getCachedImplicitProperties()
    {
        return m__lCachedImplicitProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    public List<Property> getImplicitProperties()
    {
        List<Property> result = getCachedImplicitProperties();

        if  (result == null)
        {
            result = super.getImplicitProperties();
            setCachedImplicitProperties(result);
        }

        return result;
    }

    /**
     * Specifies the cached ValueObject-formatted name.
     * @param name the name.
     */
    protected final void immutableSetCachedVoName(@NotNull final String name)
    {
        this.m__strCachedVoName = name;
    }

    /**
     * Specifies the cached ValueObject-formatted name.
     * @param name the name.
     */
    protected void setCachedVoName(@NotNull final String name)
    {
        immutableSetCachedVoName(name);
    }

    /**
     * Retrieves the cached ValueObject-formatted name.
     * @return such information.
     */
    @Nullable
    protected String getCachedVoName()
    {
        return this.m__strCachedVoName;
    }

    /**
     * Retrieves the value-object name.
     * @return such value.
     */
    @NotNull
    @Override
    public String getVoName()
    {
        String result = getCachedVoName();

        if (result == null)
        {
            try
            {
                result = super.getVoName();
            }
            catch (final Throwable throwable)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(CachingResultDecorator.class);

                if (t_Log != null)
                {
                    t_Log.fatal(
                        "Error in Result.getVoName()", throwable);
                }
            }
            setCachedVoName(result);
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
        Boolean result = getCachedWrappingASingleProperty();

        if (result == null)
        {
            result = super.isWrappingASingleProperty();
            setCachedWrappingASingleProperty(result);
        }

        return result;
    }
}
