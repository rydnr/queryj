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
 * Filename: CachingPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Property;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingPropertyDecorator
    extends  AbstractPropertyDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3484708648329474308L;

    /**
     * The cached Java type.
     */
    private DecoratedString m__strCachedType;

    /**
     * The cached object type.
     */
    private DecoratedString m__strCachedObjectType;

    /**
     * Whether the type refers to a number smaller than int.
     */
    private Boolean m__bCachedNumberSmallerThanInt;

    /**
     * The cached isDate value.
     */
    private Boolean m__bCachedDate;

    /**
     * Creates a <code>CachingPropertyDecorator</code> to decorate given
     * property.
     * @param property the property to decorate.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    public CachingPropertyDecorator(
        @NotNull final Property<String> property, @NotNull final MetadataManager metadataManager)
    {
        super(property, metadataManager);
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    protected final void immutableSetCachedType(@NotNull final DecoratedString type)
    {
        m__strCachedType = type;
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    @SuppressWarnings("unused")
    protected void setCachedType(@NotNull final DecoratedString type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached Java type.
     * @return such type.
     */
    @Nullable
    public DecoratedString getCachedType()
    {
        return m__strCachedType;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    @NotNull
    public DecoratedString getType()
    {
        @Nullable DecoratedString result = getCachedType();

        if  (result == null)
        {
            result = super.getType();
            setCachedType(result);
        }

        return result;
    }


    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedObjectType(@Nullable final DecoratedString value)
    {
        m__strCachedObjectType = value;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedObjectType(@Nullable final DecoratedString value)
    {
        immutableSetCachedObjectType(value);
    }

    /**
     * Retrieves the cached object type.
     * @return such value.
     */
    @Nullable
    public DecoratedString getCachedObjectType()
    {
        return m__strCachedObjectType;
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @NotNull
    public DecoratedString getObjectType()
    {
        @Nullable DecoratedString result = getCachedObjectType();

        if  (result == null)
        {
            result = super.getObjectType();
            setCachedObjectType(result);
        }

        return result;
    }

    /**
     * Specifies whether the type means the attribute is a
     * number smaller than an int.
     * @param flag such condition.
     */
    protected final void immutableSetCachedNumberSmallerThanInt(
        @NotNull final Boolean flag)
    {
        m__bCachedNumberSmallerThanInt = flag;
    }

    /**
     * Specifies whether the type means the attribute is a
     * number smaller than an int.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setCachedNumberSmallerThanInt(
        @NotNull final Boolean flag)
    {
        immutableSetCachedNumberSmallerThanInt(flag);
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    @Nullable
    protected Boolean getCachedNumberSmallerThanInt()
    {
        return m__bCachedNumberSmallerThanInt;
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    @SuppressWarnings("unused")
    public boolean getNumberSmallerThanInt()
    {
        Boolean result = getCachedNumberSmallerThanInt();

        if  (result == null)
        {
            result = super.isNumberSmallerThanInt();

            setCachedNumberSmallerThanInt(result);
        }

        return result;
    }

    /**
     * Caches whether the property is a Date or not.
     * @param flag such flag.
     */
    protected final void immutableSetCachedDate(@NotNull final Boolean flag)
    {
        m__bCachedDate = flag;
    }

    /**
     * Caches whether the property is a Date or not.
     * @param flag such flag.
     */
    protected void setCachedDate(@NotNull final Boolean flag)
    {
        immutableSetCachedDate(flag);
    }

    /**
     * Retrieves the cached flag indicating whether the property is a
     * Date or not.
     * @return such information.
     */
    @Nullable
    protected Boolean getCachedDate()
    {
        return m__bCachedDate;
    }

    /**
     * Retrieves whether the property is a Date or not.
     * @return such information.
     */
    @Override
    public boolean isDate()
    {
        Boolean result = getCachedDate();

        if  (result == null)
        {
            result = super.isDate();

            setCachedDate(result);
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
        return "CachingPropertyDecorator{" +
               "cachedDate=" + m__bCachedDate +
               ", cachedType='" + m__strCachedType + '\'' +
               ", cachedObjectType='" + m__strCachedObjectType + '\'' +
               ", cachedNumberSmallerThanInt=" + m__bCachedNumberSmallerThanInt +
               '}';
    }
}
