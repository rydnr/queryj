//;-*- mode: java -*-
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
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Adds a simple caching mechanism while decorating <code>ResultElement</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
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
    private Collection m__cCachedProperties;

    /**
     * The cached LOB properties.
     */
    private Collection m__cCachedLobProperties;

    /**
     * Creates a <code>CachingResultElementDecorator</code> with given instance.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
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
        final String value)
    {
        m__strCachedIdNormalized = value;
    }

    /**
     * Specifies the cached normalized id.
     * @param value the value to cache.
     */
    protected void setCachedIdNormalized(final String value)
    {
        immutableSetCachedIdNormalized(value);
    }

    /**
     * Retrieves the cached normalized id.
     * @return such value.
     */
    public String getCachedIdNormalized()
    {
        return m__strCachedIdNormalized;
    }
    /**
     * Retrieves the id, normalized.
     * @return such information.
     */
    public String getIdNormalized()
    {
        String result = getCachedIdNormalized();

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
        final String value)
    {
        m__strCachedIdCapitalized = value;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected void setCachedIdCapitalized(final String value)
    {
        immutableSetCachedIdCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized id.
     * @return such value.
     */
    public String getCachedIdCapitalized()
    {
        return m__strCachedIdCapitalized;
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    public String getIdCapitalized()
    {
        String result = getCachedIdCapitalized();

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
        final String value)
    {
        m__strCachedIdNormalizedUppercased = value;
    }

    /**
     * Specifies the cached normalized uppercased id.
     * @param value the value to cache.
     */
    protected void setCachedIdNormalizedUppercased(final String value)
    {
        immutableSetCachedIdNormalizedUppercased(value);
    }

    /**
     * Retrieves the cached normalized uppercased id.
     * @return such value.
     */
    public String getCachedIdNormalizedUppercased()
    {
        return m__strCachedIdNormalizedUppercased;
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    public String getIdNormalizedUppercased()
    {
        String result = getCachedIdNormalizedUppercased();

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
    protected final void immutableSetCachedMultiple(final Boolean multiple)
    {
        m__bCachedMultiple = multiple;
    }

    /**
     * Specifies the cached <i>multiple</i> info.
     * @param multiple such information.
     */
    protected void setCachedMultiple(final Boolean multiple)
    {
        immutableSetCachedMultiple(multiple);
    }

    /**
     * Retrieves the cached <i>multiple</i> info.
     * @return such information.
     */
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
            result = super.isMultiple() ? Boolean.TRUE : Boolean.FALSE;
            setCachedMultiple(result);
        }

        return result.booleanValue();
    }
    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedProperties(
        final Collection value)
    {
        m__cCachedProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedProperties(final Collection value)
    {
        immutableSetCachedProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    public Collection getCachedProperties()
    {
        return m__cCachedProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    public Collection getProperties()
    {
        Collection result = getCachedProperties();

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
        final Collection value)
    {
        m__cCachedLobProperties = value;
    }

    /**
     * Specifies the cached properties.
     * @param value the value to cache.
     */
    protected void setCachedLobProperties(final Collection value)
    {
        immutableSetCachedLobProperties(value);
    }

    /**
     * Retrieves the cached properties.
     * @return such value.
     */
    public Collection getCachedLobProperties()
    {
        return m__cCachedLobProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    public Collection getLobProperties()
    {
        Collection result = getCachedLobProperties();

        if  (result == null)
        {
            result = super.getLobProperties();
            setCachedLobProperties(result);
        }

        return result;
    }
}
