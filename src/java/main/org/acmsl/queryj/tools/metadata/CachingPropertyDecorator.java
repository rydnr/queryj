//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.metadata.AbstractPropertyDecorator;
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingPropertyDecorator
    extends  AbstractPropertyDecorator
{
    /**
     * The cached Java type.
     */
    private String m__strCachedJavaType;

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
     * @precondition property != null
     * @precondition metadataManager != null
     */
    public CachingPropertyDecorator(
        final Property property, final MetadataManager metadataManager)
    {
        super(property, metadataManager);
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    protected final void immutableSetCachedJavaType(final String type)
    {
        m__strCachedJavaType = type;
    }

    /**
     * Specifies the cached Java type.
     * @param type such type.
     */
    protected void setCachedJavaType(final String type)
    {
        immutableSetCachedJavaType(type);
    }

    /**
     * Retrieves the cached Java type.
     * @return such type.
     */
    public String getCachedJavaType()
    {
        return m__strCachedJavaType;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    public String getJavaType()
    {
        String result = getCachedJavaType();

        if  (result == null)
        {
            result = super.getJavaType();
            setCachedJavaType(result);
        }

        return result;
    }

    /**
     * Specifies the cached name lowercased.
     * @param nameLowercased such value.
     */
    protected final void immutableSetCachedNameLowercased(
        final String nameLowercased)
    {
        m__strCachedNameLowercased = nameLowercased;
    }

    /**
     * Specifies the cached name lowercased.
     * @param nameLowercased such value.
     */
    protected void setCachedNameLowercased(
        final String nameLowercased)
    {
        immutableSetCachedNameLowercased(nameLowercased);
    }

    /**
     * Retrieves the cached name lowercased.
     * @return such value.
     */
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    public String getNameLowercased()
    {
        String result = getCachedNameLowercased();

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
        final String nameUppercased)
    {
        m__strCachedNameUppercased = nameUppercased;
    }

    /**
     * Specifies the cached name uppercased.
     * @param nameUppercased such value.
     */
    protected void setCachedNameUppercased(
        final String nameUppercased)
    {
        immutableSetCachedNameUppercased(nameUppercased);
    }

    /**
     * Retrieves the cached name uppercased.
     * @return such value.
     */
    public String getCachedNameUppercased()
    {
        return m__strCachedNameUppercased;
    }

    /**
     * Retrieves the name, in upper case.
     * @return such information.
     */
    public String getNameUppercased()
    {
        String result = getCachedNameUppercased();

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
        final String nameCapitalized)
    {
        m__strCachedNameCapitalized = nameCapitalized;
    }

    /**
     * Specifies the cached name capitalized.
     * @param nameCapitalized such value.
     */
    protected void setCachedNameCapitalized(
        final String nameCapitalized)
    {
        immutableSetCachedNameCapitalized(nameCapitalized);
    }

    /**
     * Retrieves the cached name capitalized.
     * @return such value.
     */
    public String getCachedNameCapitalized()
    {
        return m__strCachedNameCapitalized;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    public String getNameCapitalized()
    {
        String result = getCachedNameCapitalized();

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
    protected final void immutableSetCachedObjectType(
        final String value)
    {
        m__strCachedObjectType = value;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected void setCachedObjectType(final String value)
    {
        immutableSetCachedObjectType(value);
    }

    /**
     * Retrieves the cached object type.
     * @return such value.
     */
    public String getCachedObjectType()
    {
        return m__strCachedObjectType;
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    public String getObjectType()
    {
        String result = getCachedObjectType();

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
        final Boolean flag)
    {
        m__bCachedNumberSmallerThanInt = flag;
    }

    /**
     * Specifies whether the type means the attribute is a
     * number smaller than an int.
     * @param flag such condition.
     */
    protected void setCachedNumberSmallerThanInt(
        final Boolean flag)
    {
        immutableSetCachedNumberSmallerThanInt(flag);
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    protected Boolean getCachedNumberSmallerThanInt()
    {
        return m__bCachedNumberSmallerThanInt;
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    public boolean getNumberSmallerThanInt()
    {
        Boolean result = getCachedNumberSmallerThanInt();

        if  (result == null)
        {
            result =
                (super.isNumberSmallerThanInt())
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedNumberSmallerThanInt(result);
        }

        return result.booleanValue();
    }

    /**
     * Specifies the cached capitalized normalized column name.
     * @param name such name.
     */
    protected final void immutableSetCachedColumnNameNormalizedCapitalized(
        final String name)
    {
        m__strCachedColumnNameNormalizedCapitalized = name;
    }

    /**
     * Specifies the cached capitalized normalized column name.
     * @param name such name.
     */
    protected void setCachedColumnNameNormalizedCapitalized(
        final String name)
    {
        immutableSetCachedColumnNameNormalizedCapitalized(name);
    }

    /**
     * Retrieves the cached capitalized normalized column name.
     * @return such name.
     */
    public String getCachedColumnNameNormalizedCapitalized()
    {
        return m__strCachedColumnNameNormalizedCapitalized;
    }

    /**
     * Retrieves the capitalized normalized column name.
     * @return such name.
     */
    public String getColumnNameNormalizedCapitalized()
    {
        String result = getCachedColumnNameNormalizedCapitalized();

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
        final String name)
    {
        m__strCachedColumnNameCapitalized = name;
    }

    /**
     * Specifies the cached capitalized column name.
     * @param name such name.
     */
    protected void setCachedColumnNameCapitalized(
        final String name)
    {
        immutableSetCachedColumnNameCapitalized(name);
    }

    /**
     * Retrieves the cached capitalized column name.
     * @return such name.
     */
    public String getCachedColumnNameCapitalized()
    {
        return m__strCachedColumnNameCapitalized;
    }

    /**
     * Retrieves the capitalized column name.
     * @return such name.
     */
    public String getColumnNameCapitalized()
    {
        String result = getCachedColumnNameCapitalized();

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
        final String name)
    {
        m__strCachedColumnNameNormalizedUncapitalized = name;
    }

    /**
     * Specifies the cached column name normalized uncapitalized.
     * @param name such information.
     */
    protected void setCachedColumnNameNormalizedUncapitalized(
        final String name)
    {
        immutableSetCachedColumnNameNormalizedUncapitalized(name);
    }

    /**
     * Retrieves the cached column name normalized uncapitalized.
     * @return such information.
     */
    public String getCachedColumnNameNormalizedUncapitalized()
    {
        return m__strCachedColumnNameNormalizedUncapitalized;
    }

    /**
     * Retrieves the column name normalized uncapitalized.
     * @return such information.
     */
    public String getColumnNameNormalizedUncapitalized()
    {
        String result = getCachedColumnNameNormalizedUncapitalized();

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
    protected final void immutableSetCachedDate(final Boolean flag)
    {
        m__bCachedDate = flag;
    }

    /**
     * Caches whether the property is a Date or not.
     * @param flag such flag.
     */
    protected void setCachedDate(final Boolean flag)
    {
        immutableSetCachedDate(flag);
    }

    /**
     * Retrieves the cached flag indicating whether the property is a
     * Date or not.
     * @return such information.
     */
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
            result = super.isDate() ? Boolean.TRUE : Boolean.FALSE;

            setCachedDate(result);
        }

        return result.booleanValue();
    }


    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("cachedJavaType", getCachedJavaType())
                .append("cachedObjectType", getCachedObjectType())
                .append("cachedNameUppercased", getCachedNameUppercased())
                .append("cachedNameLowercased", getCachedNameLowercased())
                .append("cachedNumberSmallerThanInt", getCachedNumberSmallerThanInt())
                .append("cachedNameCapitalized", getCachedNameCapitalized())
                .append("cachedColumnNameNormalizedCapitalized", getCachedColumnNameNormalizedCapitalized())
                .append("cachedColumnNameCapitalized", getCachedColumnNameCapitalized())
                .append("cachedColumnNameNormalizedUncapitalized", getCachedColumnNameNormalizedUncapitalized())
                .append("cachedDate", getCachedDate())
                .toString();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        boolean result = false;

        if  (object instanceof CachingPropertyDecorator)
        {
            final CachingPropertyDecorator t_OtherInstance =
                (CachingPropertyDecorator) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getCachedJavaType(),
                        t_OtherInstance.getCachedJavaType())
                    .append(
                        getJavaType(),
                        t_OtherInstance.getJavaType())
                    .append(
                        getCachedNameLowercased(),
                        t_OtherInstance.getCachedNameLowercased())
                    .append(
                        getNameLowercased(),
                        t_OtherInstance.getNameLowercased())
                    .append(
                        getCachedNameUppercased(),
                        t_OtherInstance.getCachedNameUppercased())
                    .append(
                        getNameUppercased(),
                        t_OtherInstance.getNameUppercased())
                    .append(
                        getCachedNameCapitalized(),
                        t_OtherInstance.getCachedNameCapitalized())
                    .append(
                        getNameCapitalized(),
                        t_OtherInstance.getNameCapitalized())
                    .append(
                        getCachedObjectType(),
                        t_OtherInstance.getCachedObjectType())
                    .append(
                        getObjectType(),
                        t_OtherInstance.getObjectType())
                    .append(
                        getCachedNumberSmallerThanInt(),
                        t_OtherInstance.getCachedNumberSmallerThanInt())
                    .append(
                        getNumberSmallerThanInt(),
                        t_OtherInstance.getNumberSmallerThanInt())
                    .append(
                        getCachedColumnNameNormalizedCapitalized(),
                        t_OtherInstance.getCachedColumnNameNormalizedCapitalized())
                    .append(
                        getColumnNameNormalizedCapitalized(),
                        t_OtherInstance.getColumnNameNormalizedCapitalized())
                    .append(
                        getCachedColumnNameCapitalized(),
                        t_OtherInstance.getCachedColumnNameCapitalized())
                    .append(
                        getColumnNameCapitalized(),
                        t_OtherInstance.getColumnNameCapitalized())
                    .append(
                        getCachedColumnNameNormalizedUncapitalized(),
                        t_OtherInstance.getCachedColumnNameNormalizedUncapitalized())
                    .append(
                        getColumnNameNormalizedUncapitalized(),
                        t_OtherInstance.getColumnNameNormalizedUncapitalized())
                    .append(
                        getCachedDate(),
                        t_OtherInstance.getCachedDate())
                    .append(
                        isDate(),
                        t_OtherInstance.isDate())
                .isEquals();
        }
        else
        {
            result = super.equals(object);
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

        if  (object instanceof CachingPropertyDecorator)
        {
            final CachingPropertyDecorator t_OtherInstance =
                (CachingPropertyDecorator) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getCachedJavaType(),
                    t_OtherInstance.getCachedJavaType())
                .append(
                    getJavaType(),
                    t_OtherInstance.getJavaType())
                .append(
                    getCachedNameLowercased(),
                    t_OtherInstance.getCachedNameLowercased())
                .append(
                    getNameLowercased(),
                    t_OtherInstance.getNameLowercased())
                .append(
                    getCachedNameUppercased(),
                    t_OtherInstance.getCachedNameUppercased())
                .append(
                    getNameUppercased(),
                    t_OtherInstance.getNameUppercased())
                .append(
                    getCachedNameCapitalized(),
                    t_OtherInstance.getCachedNameCapitalized())
                .append(
                    getNameCapitalized(),
                    t_OtherInstance.getNameCapitalized())
                .append(
                    getCachedObjectType(),
                    t_OtherInstance.getCachedObjectType())
                .append(
                    getObjectType(),
                    t_OtherInstance.getObjectType())
                .append(
                    getCachedNumberSmallerThanInt(),
                    t_OtherInstance.getCachedNumberSmallerThanInt())
                .append(
                    getNumberSmallerThanInt(),
                    t_OtherInstance.getNumberSmallerThanInt())
                .append(
                    getCachedColumnNameNormalizedCapitalized(),
                    t_OtherInstance.getCachedColumnNameNormalizedCapitalized())
                .append(
                    getColumnNameNormalizedCapitalized(),
                    t_OtherInstance.getColumnNameNormalizedCapitalized())
                .append(
                    getCachedColumnNameCapitalized(),
                    t_OtherInstance.getCachedColumnNameCapitalized())
                .append(
                    getColumnNameCapitalized(),
                    t_OtherInstance.getColumnNameCapitalized())
                .append(
                    getCachedColumnNameNormalizedUncapitalized(),
                    t_OtherInstance.getCachedColumnNameNormalizedUncapitalized())
                .append(
                    getColumnNameNormalizedUncapitalized(),
                    t_OtherInstance.getColumnNameNormalizedUncapitalized())
                .append(
                    getCachedDate(),
                    t_OtherInstance.getCachedDate())
                .append(
                    isDate(),
                    t_OtherInstance.isDate())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
