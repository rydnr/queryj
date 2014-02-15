/*
                        QueryJ-Core

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
 * Filename: CachingParameterDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating <parameter>
 *              instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Parameter;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adds a simple caching mechanism while decorating <code>Parameter</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingParameterDecorator<V>
    extends  AbstractParameterDecorator<V>
{
    private static final long serialVersionUID = -598058048217257284L;
    /**
     * The cached SQL type.
     */
    private DecoratedString m__strCachedSqlType;

    /**
     * The cached object type.
     */
    private DecoratedString m__strCachedObjectType;

    /**
     * The cached <code>isObject</code> value.
     */
    private Boolean m__bCachedIsObject;

    /**
     * The cached field type.
     */
    private DecoratedString m__strCachedFieldType;

    /**
     * The cached <code>isClob</code> value.
     */
    private Boolean m__bCachedIsClob;

    /**
     * Creates a <code>CachingParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @param metadataTypeManager the metadata type manager.
     */
    public CachingParameterDecorator(
        @NotNull final Parameter<String, V> parameter,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        super(parameter, metadataTypeManager);
    }

    /**
     * Specifies the cached SQL type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSqlType(
        final DecoratedString value)
    {
        m__strCachedSqlType = value;
    }
    
    /**
     * Specifies the cached SQL type.
     * @param value the value to cache.
     */
    protected void setCachedSqlType(final DecoratedString value)
    {
        immutableSetCachedSqlType(value);
    }

    /**
     * Retrieves the cached SQL type.
     * @return such value.
     */
    public DecoratedString getCachedSqlType()
    {
        return m__strCachedSqlType;
    }

    /**
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    public DecoratedString getSqlType()
    {
        @Nullable DecoratedString result = getCachedSqlType();
        
        if  (result == null)
        {
            result = super.getSqlType();
            setCachedSqlType(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedObjectType(
        final DecoratedString value)
    {
        m__strCachedObjectType = value;
    }
    
    /**
     * Specifies the cached object type.
     * @param value the value to cache.
     */
    protected void setCachedObjectType(final DecoratedString value)
    {
        immutableSetCachedObjectType(value);
    }

    /**
     * Retrieves the cached object type.
     * @return such value.
     */
    public DecoratedString getCachedObjectType()
    {
        return m__strCachedObjectType;
    }

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
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
     * Specifies the cached <code>isObject</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsObject(
        final Boolean value)
    {
        m__bCachedIsObject = value;
    }
    
    /**
     * Specifies the cached <code>isObject</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsObject(final Boolean value)
    {
        immutableSetCachedIsObject(value);
    }

    /**
     * Retrieves the cached <code>isObject</code> value.
     * @return such value.
     */
    public Boolean getCachedIsObject()
    {
        return m__bCachedIsObject;
    }
    /**
     * Retrieves whether the parameter type is a class or not.
     * @return such information.
     */
    @Override
    public boolean isObject()
    {
        Boolean result = getCachedIsObject();
        
        if  (result == null)
        {
            result = super.isObject() ? Boolean.TRUE : Boolean.FALSE;
            setCachedIsObject(result);
        }
        
        return result.booleanValue();
    }

    /**
     * Specifies the cached field type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedFieldType(
        final DecoratedString value)
    {
        m__strCachedFieldType = value;
    }
    
    /**
     * Specifies the cached field type.
     * @param value the value to cache.
     */
    protected void setCachedFieldType(final DecoratedString value)
    {
        immutableSetCachedFieldType(value);
    }

    /**
     * Retrieves the cached field type.
     * @return such value.
     */
    public DecoratedString getCachedFieldType()
    {
        return m__strCachedFieldType;
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    @Override
    @NotNull
    public DecoratedString getFieldType()
    {
        DecoratedString result = getCachedFieldType();
        
        if  (result == null)
        {
            result = super.getFieldType();
            setCachedFieldType(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached <code>isClob</code> value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIsClob(
        final Boolean value)
    {
        m__bCachedIsClob = value;
    }
    
    /**
     * Specifies the cached <code>isClob</code> value.
     * @param value the value to cache.
     */
    protected void setCachedIsClob(final Boolean value)
    {
        immutableSetCachedIsClob(value);
    }

    /**
     * Retrieves the cached <code>isClob</code> value.
     * @return such value.
     */
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
        
        return result.booleanValue();
    }

    @NotNull
    @Override
    public String toString()
    {
        return "CachingParameterDecorator{" +
               "m__bCachedIsClob=" + m__bCachedIsClob +
               ", m__strCachedSqlType=" + m__strCachedSqlType +
               ", m__strCachedObjectType=" + m__strCachedObjectType +
               ", m__bCachedIsObject=" + m__bCachedIsObject +
               ", m__strCachedFieldType=" + m__strCachedFieldType +
               '}';
    }
}
