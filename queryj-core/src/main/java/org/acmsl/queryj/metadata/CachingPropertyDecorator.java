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
 * Filename: CachingPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
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
    implements Comparable<Property>
{
    private static final long serialVersionUID = -3484708648329474308L;

    /**
     * The cached Java type.
     */
    private String m__strCachedType;

    /**
     * The cached object type.
     */
    private String m__strCachedObjectType;

    /**
     * The cached name uppercased.
     */
    private String m__strCachedNameUppercased;

    /**
     * The cached name lowercased.
     */
    private String m__strCachedNameLowercased;

    /**
     * Whether the type refers to a number smaller than int.
     */
    private Boolean m__bCachedNumberSmallerThanInt;

    /**
     * The cached capitalized name.
     */
    private String m__strCachedNameCapitalized;

    /**
     * The cached normalized/capitalized column name.
     */
    private String m__strCachedColumnNameNormalizedCapitalized;

    /**
     * The cached capitalized column name.
     */
    private String m__strCachedColumnNameCapitalized;

    /**
     * The cached normalized/uncapitalized column name.
     */
    private String m__strCachedColumnNameNormalizedUncapitalized;

    /**
     * The cached isDate value.
     */
    private Boolean m__bCachedDate;

    /**
     * Creates a <code>CachingPropertyDecorator</code> to decorate given
     * property.
     * @param property the property to decorate.
     */
    public CachingPropertyDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
    {
        super(property, metadataManager);
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    protected final void immutableSetCachedType(@NotNull final String type)
    {
        m__strCachedType = type;
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    @SuppressWarnings("unused")
    protected void setCachedType(@NotNull final String type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached Java type.
     * @return such type.
     */
    @Nullable
    public String getCachedType()
    {
        return m__strCachedType;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    @NotNull
    public String getType()
    {
        @Nullable String result = getCachedType();

        if  (result == null)
        {
            result = super.getType();
            setCachedType(result);
        }

        return result;
    }

    /**
     * Specifies the cached name lowercased.
     * @param nameLowercased such value.
     */
    protected final void immutableSetCachedNameLowercased(
        @NotNull final String nameLowercased)
    {
        m__strCachedNameLowercased = nameLowercased;
    }

    /**
     * Specifies the cached name lowercased.
     * @param nameLowercased such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedNameLowercased(
        @NotNull final String nameLowercased)
    {
        immutableSetCachedNameLowercased(nameLowercased);
    }

    /**
     * Retrieves the cached name lowercased.
     * @return such value.
     */
    @Nullable
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    @NotNull
    public String getNameLowercased()
    {
        @Nullable String result = getCachedNameLowercased();

        if  (result == null)
        {
            result = super.getNameLowercased();
            setCachedNameLowercased(result);
        }

        return result;
    }

    /**
     * Specifies the cached name uppercased.
     * @param nameUppercased such value.
     */
    protected final void immutableSetCachedNameUppercased(
        @NotNull final String nameUppercased)
    {
        m__strCachedNameUppercased = nameUppercased;
    }

    /**
     * Specifies the cached name uppercased.
     * @param nameUppercased such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedNameUppercased(
        @NotNull final String nameUppercased)
    {
        immutableSetCachedNameUppercased(nameUppercased);
    }

    /**
     * Retrieves the cached name uppercased.
     * @return such value.
     */
    @Nullable
    public String getCachedNameUppercased()
    {
        return m__strCachedNameUppercased;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such information.
     */
    @NotNull
    public String getNameUppercased()
    {
        @Nullable String result = getCachedNameUppercased();

        if  (result == null)
        {
            result = super.getNameUppercased();
            setCachedNameUppercased(result);
        }

        return result;
    }

    /**
     * Specifies the cached name capitalized.
     * @param nameCapitalized such value.
     */
    protected final void immutableSetCachedNameCapitalized(
        @NotNull final String nameCapitalized)
    {
        m__strCachedNameCapitalized = nameCapitalized;
    }

    /**
     * Specifies the cached name capitalized.
     * @param nameCapitalized such value.
     */
    @SuppressWarnings("unused")
    protected void setCachedNameCapitalized(
        @NotNull final String nameCapitalized)
    {
        immutableSetCachedNameCapitalized(nameCapitalized);
    }

    /**
     * Retrieves the cached name capitalized.
     * @return such value.
     */
    @Nullable
    public String getCachedNameCapitalized()
    {
        return m__strCachedNameCapitalized;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    @NotNull
    public String getNameCapitalized()
    {
        @Nullable String result = getCachedNameCapitalized();

        if  (result == null)
        {
            result = super.getNameCapitalized();
            setCachedNameCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedObjectType(@Nullable final String value)
    {
        m__strCachedObjectType = value;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    @SuppressWarnings("unused")
    protected void setCachedObjectType(@Nullable final String value)
    {
        immutableSetCachedObjectType(value);
    }

    /**
     * Retrieves the cached object type.
     * @return such value.
     */
    @Nullable
    public String getCachedObjectType()
    {
        return m__strCachedObjectType;
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @NotNull
    public String getObjectType()
    {
        @Nullable String result = getCachedObjectType();

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
     * Specifies the cached capitalized normalized column name.
     * @param name such name.
     */
    protected final void immutableSetCachedColumnNameNormalizedCapitalized(
        @NotNull final String name)
    {
        m__strCachedColumnNameNormalizedCapitalized = name;
    }

    /**
     * Specifies the cached capitalized normalized column name.
     * @param name such name.
     */
    protected void setCachedColumnNameNormalizedCapitalized(
        @NotNull final String name)
    {
        immutableSetCachedColumnNameNormalizedCapitalized(name);
    }

    /**
     * Retrieves the cached capitalized normalized column name.
     * @return such name.
     */
    @Nullable
    public String getCachedColumnNameNormalizedCapitalized()
    {
        return m__strCachedColumnNameNormalizedCapitalized;
    }

    /**
     * Retrieves the capitalized normalized column name.
     * @return such name.
     */
    @NotNull
    public String getColumnNameNormalizedCapitalized()
    {
        @Nullable String result = getCachedColumnNameNormalizedCapitalized();

        if  (result == null)
        {
            result = super.getColumnNameNormalizedCapitalized();
            setCachedColumnNameNormalizedCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached capitalized column name.
     * @param name such name.
     */
    protected final void immutableSetCachedColumnNameCapitalized(
        @NotNull final String name)
    {
        m__strCachedColumnNameCapitalized = name;
    }

    /**
     * Specifies the cached capitalized column name.
     * @param name such name.
     */
    @SuppressWarnings("unused")
    protected void setCachedColumnNameCapitalized(
        @NotNull final String name)
    {
        immutableSetCachedColumnNameCapitalized(name);
    }

    /**
     * Retrieves the cached capitalized column name.
     * @return such name.
     */
    @Nullable
    public String getCachedColumnNameCapitalized()
    {
        return m__strCachedColumnNameCapitalized;
    }

    /**
     * Retrieves the capitalized column name.
     * @return such name.
     */
    @NotNull
    public String getColumnNameCapitalized()
    {
        @Nullable String result = getCachedColumnNameCapitalized();

        if  (result == null)
        {
            result = super.getColumnNameCapitalized();
            setCachedColumnNameCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached column name normalized uncapitalized.
     * @param name such information.
     */
    protected final void immutableSetCachedColumnNameNormalizedUncapitalized(
        @NotNull final String name)
    {
        m__strCachedColumnNameNormalizedUncapitalized = name;
    }

    /**
     * Specifies the cached column name normalized uncapitalized.
     * @param name such information.
     */
    @SuppressWarnings("unused")
    protected void setCachedColumnNameNormalizedUncapitalized(
        @NotNull final String name)
    {
        immutableSetCachedColumnNameNormalizedUncapitalized(name);
    }

    /**
     * Retrieves the cached column name normalized uncapitalized.
     * @return such information.
     */
    @Nullable
    public String getCachedColumnNameNormalizedUncapitalized()
    {
        return m__strCachedColumnNameNormalizedUncapitalized;
    }

    /**
     * Retrieves the column name normalized uncapitalized.
     * @return such information.
     */
    @NotNull
    public String getColumnNameNormalizedUncapitalized()
    {
        @Nullable String result = getCachedColumnNameNormalizedUncapitalized();

        if  (result == null)
        {
            result = super.getColumnNameNormalizedUncapitalized();
            setCachedColumnNameNormalizedUncapitalized(result);
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

    @Override
    @NotNull
    public String toString()
    {
        return "CachingPropertyDecorator{" +
               "cachedDate=" + m__bCachedDate +
               ", cachedType='" + m__strCachedType + '\'' +
               ", cachedObjectType='" + m__strCachedObjectType + '\'' +
               ", cachedNameUppercased='" + m__strCachedNameUppercased + '\'' +
               ", cachedNameLowercased='" + m__strCachedNameLowercased + '\'' +
               ", cachedNumberSmallerThanInt=" + m__bCachedNumberSmallerThanInt +
               ", cachedNameCapitalized='" + m__strCachedNameCapitalized + '\'' +
               ", cachedColumnNameNormalizedCapitalized='" + m__strCachedColumnNameNormalizedCapitalized + '\'' +
               ", cachedColumnNameCapitalized='" + m__strCachedColumnNameCapitalized + '\'' +
               ", cachedColumnNameNormalizedUncapitalized='" + m__strCachedColumnNameNormalizedUncapitalized + '\'' +
               '}';
    }
}
