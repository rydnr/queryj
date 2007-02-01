/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

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
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.ParameterDecorator;

/**
 * Adds a simple caching mechanism while decorating <code>Parameter</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingParameterDecorator
    extends  ParameterDecorator
{
    /**
     * The cached SQL type.
     */
    private String m__strCachedSqlType;

    /**
     * The cached object type.
     */
    private String m__strCachedObjectType;

    /**
     * The cached <code>isObject</code> value.
     */
    private Boolean m__bCachedIsObject;

    /**
     * The cached field type.
     */
    private String m__strCachedFieldType;

    /**
     * The cached lowercased name.
     */
    private String m__strCachedNameLowercased;

    /**
     * The cached <code>isClob</code> value.
     */
    private Boolean m__bCachedIsClob;

    /**
     * Creates a <code>CachingParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @param metadataTypeManager the metadata type manager.
     * @precondition parameter != null
     * @precondition metadataTypeManager != null
     */
    public CachingParameterDecorator(
        final ParameterElement parameter,
        final MetadataTypeManager metadataTypeManager)
    {
        super(parameter, metadataTypeManager);
    }

    /**
     * Specifies the cached SQL type.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSqlType(
        final String value)
    {
        m__strCachedSqlType = value;
    }
    
    /**
     * Specifies the cached SQL type.
     * @param value the value to cache.
     */
    protected void setCachedSqlType(final String value)
    {
        immutableSetCachedSqlType(value);
    }

    /**
     * Retrieves the cached SQL type.
     * @return such value.
     */
    public String getCachedSqlType()
    {
        return m__strCachedSqlType;
    }

    /**
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    public String getSqlType()
    {
        String result = getCachedSqlType();
        
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
     * Retrieves the object type of the parameter.
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
        final String value)
    {
        m__strCachedFieldType = value;
    }
    
    /**
     * Specifies the cached field type.
     * @param value the value to cache.
     */
    protected void setCachedFieldType(final String value)
    {
        immutableSetCachedFieldType(value);
    }

    /**
     * Retrieves the cached field type.
     * @return such value.
     */
    public String getCachedFieldType()
    {
        return m__strCachedFieldType;
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    public String getFieldType()
    {
        String result = getCachedFieldType();
        
        if  (result == null)
        {
            result = super.getFieldType();
            setCachedFieldType(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameLowercased(
        final String value)
    {
        m__strCachedNameLowercased = value;
    }
    
    /**
     * Specifies the cached lowercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameLowercased(final String value)
    {
        immutableSetCachedNameLowercased(value);
    }

    /**
     * Retrieves the cached lowercased name.
     * @return such value.
     */
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
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
}
