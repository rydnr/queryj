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

 *****************************************************************************
 *
 * Filename: CachingAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating Attribute
 *              instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;

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
 * Adds a simple caching mechanism while decorating <code>Attribute</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingAttributeDecorator
    extends  AbstractAttributeDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 7937414826592045025L;

    /**
     * The cached <code>isPrimitive</code> value.
     */
    private Boolean m__bCachedIsPrimitive;

    /**
     * The cached object type.
     */
    private String m__strCachedObjectType;

    /**
     * The cached <code>isClob</code> value.
     */
    private Boolean m__bCachedIsClob;

    /**
     * The cached <code>isString</code> value.
     */
    private Boolean m__bCachedIsString;

    /**
     * The cached <code>isDate</code> value.
     */
    private Boolean m__bCachedIsDate;

    /**
     * The cached query.
     */
    private String m__strCachedQuery;

    /**
     * The cached QueryJ field type.
     */
    private String m__strCachedQueryJFieldType;

    /**
     * The cached <code>StatementSetter</code> field type.
     */
    private String m__strCachedStatementSetterFieldType;

    /**
     * Whether the type refers to a number smaller than int.
     */
    private Boolean m__bCachedNumberSmallerThanInt;

    /**
     * The cached Java type.
     */
    private DecoratedString m__strCachedType;

    /**
     * Whether is a primitive wrapper.
     */
    private Boolean m__bCachedIsPrimitiveWrapper;

    /**
     * Creates a <code>CachingAttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     */
    public CachingAttributeDecorator(
        @NotNull final Attribute<String> attribute,
        @NotNull final MetadataManager metadataManager)
    {
        super(attribute, metadataManager);
    }

    /**
     * Specifies the cached <code>isPrimitive</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsPrimitive(
        final Boolean value)
    {
        m__bCachedIsPrimitive = value;
    }

    /**
     * Specifies the cached <code>isPrimitive</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsPrimitive(final Boolean value)
    {
        immutableSetCachedIsPrimitive(value);
    }

    /**
     * Retrieves the cached <code>isPrimitive</code> value.
     * @return such value.
     */
    public Boolean getCachedIsPrimitive()
    {
        return m__bCachedIsPrimitive;
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    @Override
    public boolean isPrimitive()
    {
        Boolean result = getCachedIsPrimitive();

        if  (result == null)
        {
            result = super.isPrimitive();
            setCachedIsPrimitive(result);
        }

        return result;
    }


    /**
     * Specifies the cached <code>isPrimitive</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsPrimitiveWrapper(
        final Boolean value)
    {
        m__bCachedIsPrimitiveWrapper = value;
    }

    /**
     * Specifies the cached <code>isPrimitive</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsPrimitiveWrapper(final Boolean value)
    {
        immutableSetCachedIsPrimitiveWrapper(value);
    }

    /**
     * Retrieves the cached <code>isPrimitive</code> value.
     * @return such value.
     */
    public Boolean getCachedIsPrimitiveWrapper()
    {
        return m__bCachedIsPrimitiveWrapper;
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    @Override
    public boolean isPrimitiveWrapper()
    {
        Boolean result = getCachedIsPrimitiveWrapper();

        if  (result == null)
        {
            result = super.isPrimitiveWrapper();
            setCachedIsPrimitiveWrapper(result);
        }

        return result;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedObjectType(@NotNull final String value)
    {
        m__strCachedObjectType = value;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected void setCachedObjectType(@NotNull final String value)
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
    @Override
    @NotNull
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
     * Specifies the cached <code>isClob</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsClob(@NotNull final Boolean value)
    {
        m__bCachedIsClob = value;
    }

    /**
     * Specifies the cached <code>isClob</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsClob(@NotNull final Boolean value)
    {
        immutableSetCachedIsClob(value);
    }

    /**
     * Retrieves the cached <code>isClob</code> value.
     * @return such value.
     */
    @Nullable
    public Boolean getCachedIsClob()
    {
        return m__bCachedIsClob;
    }
    /**
     * Retrieves whether the attribute is a clob or not.
     * return such information.
     */
    @Override
    public boolean isClob()
    {
        Boolean result = getCachedIsClob();

        if  (result == null)
        {
            result = super.isClob() ? Boolean.TRUE : Boolean.FALSE;
            setCachedIsClob(result);
        }

        return result;
    }

    /**
     * Specifies the cached <code>isString</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsString(@NotNull final Boolean value)
    {
        m__bCachedIsString = value;
    }

    /**
     * Specifies the cached <code>isString</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsString(@NotNull final Boolean value)
    {
        immutableSetCachedIsString(value);
    }

    /**
     * Retrieves the cached <code>isString</code> value.
     * @return such value.
     */
    @Nullable
    public Boolean getCachedIsString()
    {
        return m__bCachedIsString;
    }

    /**
     * Retrieves whether the attribute is a string or not.
     * return such information.
     */
    @Override
    public boolean isString()
    {
        Boolean result = getCachedIsString();

        if  (result == null)
        {
            result = super.isString() ? Boolean.TRUE : Boolean.FALSE;
            setCachedIsString(result);
        }

        return result;
    }

    /**
     * Specifies the cached <code>isDate</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsDate(@NotNull final Boolean value)
    {
        m__bCachedIsDate = value;
    }

    /**
     * Specifies the cached <code>isDate</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsDate(@NotNull final Boolean value)
    {
        immutableSetCachedIsDate(value);
    }

    /**
     * Retrieves the cached <code>isDate</code> value.
     * @return such value.
     */
    @Nullable
    public Boolean getCachedIsDate()
    {
        return m__bCachedIsDate;
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * return such information.
     */
    @Override
    public boolean isDate()
    {
        Boolean result = getCachedIsDate();

        if  (result == null)
        {
            result = super.isDate() ? Boolean.TRUE : Boolean.FALSE;
            setCachedIsDate(result);
        }

        return result;
    }

    /**
     * Specifies the cached query.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedQuery(@NotNull final String value)
    {
        m__strCachedQuery = value;
    }

    /**
     * Specifies the cached query.
     * @param value the value to cache.
     */
    protected void setCachedQuery(@NotNull final String value)
    {
        immutableSetCachedQuery(value);
    }

    /**
     * Retrieves the cached query.
     * @return such value.
     */
    @Nullable
    public String getCachedQuery()
    {
        return m__strCachedQuery;
    }
    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @return such information.
     */
    @Override
    @Nullable
    public String getQuery()
    {
        String result = getCachedQuery();

        if  (result == null)
        {
            result = super.getQuery();
            if (result != null)
            {
                setCachedQuery(result);
            }
        }

        return result;
    }

    /**
     * Specifies the cached QueryJ field type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedQueryJFieldType(
        @NotNull final String value)
    {
        m__strCachedQueryJFieldType = value;
    }

    /**
     * Specifies the cached QueryJ field type.
     * @param value the value to cache.
     */
    protected void setCachedQueryJFieldType(@NotNull final String value)
    {
        immutableSetCachedQueryJFieldType(value);
    }

    /**
     * Retrieves the cached QueryJ field type.
     * @return such value.
     */
    @Nullable
    public String getCachedQueryJFieldType()
    {
        return m__strCachedQueryJFieldType;
    }

    /**
     * Retrieves the QueryJ type.
     * @return the QueryJ type.
     */
    @Override
    @NotNull
    public String getQueryJFieldType()
    {
        String result = getCachedQueryJFieldType();

        if  (result == null)
        {
            result = super.getQueryJFieldType();
            setCachedQueryJFieldType(result);
        }

        return result;
    }

    /**
     * Specifies the cached <code>StatementSetter</code> field type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedStatementSetterFieldType(
        @NotNull final String value)
    {
        m__strCachedStatementSetterFieldType = value;
    }

    /**
     * Specifies the cached <code>StatementSetter</code> field type.
     * @param value the value to cache.
     */
    protected void setCachedStatementSetterFieldType(@NotNull final String value)
    {
        immutableSetCachedStatementSetterFieldType(value);
    }

    /**
     * Retrieves the cached <code>StatementSetter</code> field type.
     * @return such value.
     */
    @Nullable
    public String getCachedStatementSetterFieldType()
    {
        return m__strCachedStatementSetterFieldType;
    }
    /**
     * Retrieves the QueryJ type for statement setters.
     * @return the QueryJ type.
     */
    @Override
    @NotNull
    public String getStatementSetterFieldType()
    {
        String result = getCachedStatementSetterFieldType();

        if  (result == null)
        {
            result = super.getStatementSetterFieldType();
            setCachedStatementSetterFieldType(result);
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
            result =
                (super.isNumberSmallerThanInt())
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedNumberSmallerThanInt(result);
        }

        return result;
    }

    /**
     * Specifies the cached type.
     * @param type such type.
     */
    protected final void immutableSetCachedType(@NotNull final DecoratedString type)
    {
        m__strCachedType = type;
    }

    /**
     * Specifies the cached type.
     * @param type such type.
     */
    protected void setCachedType(@NotNull final DecoratedString type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached type.
     * @return such type.
     */
    @Nullable
    public DecoratedString getCachedType()
    {
        return m__strCachedType;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + CachingAttributeDecorator.class.getSimpleName() + '"'
            + ", \"super\": " + super.toString()
            + ", \"cachedIsClob\": \"" + m__bCachedIsClob + '"'
            + ", \"cachedIsPrimitive\" : " + m__bCachedIsPrimitive
            + ", \"cachedObjectType\" : " + m__strCachedObjectType
            + ", \"cachedIsString\" : " + m__bCachedIsString
            + ", \"cachedIsDate\" : " + m__bCachedIsDate
            + ", \"cachedQuery\" : \"" + m__strCachedQuery + '"'
            + ", \"cachedQueryJFieldType\" : \"" + m__strCachedQueryJFieldType + '"'
            + ", \"cachedStatementSetterFieldType\" : \"" + m__strCachedStatementSetterFieldType + '"'
            + ", \"cachedNumberSmallerThanInt\" : " + m__bCachedNumberSmallerThanInt
            + ", \"cachedType\" : \"" + m__strCachedType + '"'
            + ", \"cachedPrimitiveWrapper\" : \"" + m__bCachedIsPrimitiveWrapper + '"'
            + ", \"package\" : \"" + CachingAttributeDecorator.class.getPackage().getName() + '"'
            + " }";
    }
}
