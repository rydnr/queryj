//;-*- mode: java -*-
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: LazyAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 'Attribute' implementation which retrieves its information
 *              lazily.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractAttribute;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/**
 * <code>Attribute</code> implementation which retrieves its information
 * lazily.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class LazyAttribute
    extends  AbstractAttribute
{
    /**
     * The cached type.
     */
    private Integer m__CachedType;

    /**
     * The cached native type.
     */
    private String m__CachedNativeType;

    /**
     * The cached field type.
     */
    private String m__strCachedFieldType;

    /**
     * The cached comment.
     */
    private String m__strCachedComment;

    /**
     * Whether the attribute is managed externally or not.
     */
    private Boolean m__CachedManagedExternally;

    /**
     * Whether the attribute allows null values or not.
     */
    private Boolean m__CachedAllowsNull;

    /**
     * Whether the attribute is marked as read-only.
     */
    private Boolean m__CachedReadOnly;

    /**
     * Whether the attribute is marked as boolean.
     */
    private Boolean m__CachedBoolean;

    /**
     * The cached symbol for <code>true</code> values in boolean attributes.
     */
    private String m__strCachedBooleanTrue;

    /**
     * The cached symbol for <code>false</code> values in boolean attributes.
     */
    private String m__strCachedBooleanFalse;

    /**
     * The cached symbol for <code>null</code> values in boolean attributes.
     */
    private String m__strCachedBooleanNull;

    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates a <code>LazyAttributeDecorator</code> with some minimal information.
     * @param tableName the table name.
     * @param name the attribute name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    public LazyAttribute(
        final String tableName,
        final String name,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        super(tableName, name);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected void setMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    protected final void immutableSetCachedType(final Integer type)
    {
        m__CachedType = type;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    protected void setCachedType(final Integer type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached type.
     * @return such information.
     */
    protected Integer getCachedType()
    {
        return m__CachedType;
    }

    /**
     * Retrieves the attribute type.
     * @return its type.
     */
    public int getType()
    {
        Integer result = getCachedType();

        if  (result == null)
        {
            result =
                new Integer(
                    retrieveType(
                        getTableName(),
                        getName(),
                        getMetadataManager()));

            setCachedType(result);
        }

        return result.intValue();
    }

    /**
     * Retrieves the type.
     * @param tableName the table name.
     * @param name the name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition name != null
     * @precondition metadataManager != null
     */
    protected int retrieveType(
        final String tableName,
        final String name,
        final MetadataManager metadataManager)
    {
        return metadataManager.getColumnType(tableName, name);
    }

    /**
     * Specifies the cached native type.
     * @param type such type.
     */
    protected final void immutableSetCachedNativeType(final String type)
    {
        m__CachedNativeType = type;
    }

    /**
     * Specifies the cached native type.
     * @param type such type.
     */
    protected void setCachedNativeType(final String type)
    {
        immutableSetCachedNativeType(type);
    }

    /**
     * Retrieves the cached native type.
     * @return such type.
     */
    protected String getCachedNativeType()
    {
        return m__CachedNativeType;
    }

    /**
     * Retrieves the native type.
     * @return such information.
     */
    public String getNativeType()
    {
        String result = getCachedNativeType();

        if  (result == null)
        {
            result =
                retrieveNativeType(
                    getType(),
                    getMetadataTypeManager());

            setCachedNativeType(result);
        }

        return result;
    }

    /**
     * Retrieves the native type.
     * @param type the column type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected String retrieveNativeType(
        final int type,
        final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getNativeType(type);
    }

    /**
     * Specifies the cached field type.
     * @param type such type.
     */
    protected final void immutableSetCachedFieldType(final String type)
    {
        m__strCachedFieldType = type;
    }

    /**
     * Specifies the cached field type.
     * @param type such type.
     */
    protected void setCachedFieldType(final String type)
    {
        immutableSetCachedFieldType(type);
    }

    /**
     * Retrieves the cached field type.
     * @return such type.
     */
    protected String getCachedFieldType()
    {
        return m__strCachedFieldType;
    }

    /**
     * Retrieves the field type.
     * @return such information.
     */
    public String getFieldType()
    {
        String result = getCachedFieldType();

        if  (result == null)
        {
            result =
                retrieveFieldType(
                    getType(),
                    getMetadataTypeManager());

            setCachedFieldType(result);
        }

        return result;
    }

    /**
     * Retrieves the field type.
     * @param type the column type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected String retrieveFieldType(
        final int type,
        final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getFieldType(type);
    }

    /**
     * Specifies the cached comment.
     * @param comment such comment.
     */
    protected final void immutableSetCachedComment(final String comment)
    {
        m__strCachedComment = comment;
    }

    /**
     * Specifies the cached comment.
     * @param comment such comment.
     */
    protected void setCachedComment(final String comment)
    {
        immutableSetCachedComment(comment);
    }

    /**
     * Retrieves the cached comment.
     * @return such information.
     */
    protected String getCachedComment()
    {
        return m__strCachedComment;
    }

    /**
     * Retrieves the comment.
     * @return such information.
     */
    public String getComment()
    {
        String result = getCachedComment();

        if  (result == null)
        {
            result =
                retrieveComment(
                    getTableName(), getName(), getMetadataManager());

            setCachedComment(result);
        }

        return result;
    }

    /**
     * Retrieves the comment.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected String retrieveComment(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.getColumnComment(tableName, columnName);
    }

    /**
     * Caches whether the attribute is managed externally or not.
     * @param flag such flag.
     */
    protected final void immutableSetCachedManagedExternally(final Boolean flag)
    {
        m__CachedManagedExternally = flag;
    }

    /**
     * Caches whether the attribute is managed externally or not.
     * @param flag such flag.
     */
    protected void setCachedManagedExternally(final Boolean flag)
    {
        immutableSetCachedManagedExternally(flag);
    }

    /**
     * Retrieves the cache of whether the attribute is managed externally or not.
     * @return such flag.
     */
    protected Boolean getCachedManagedExternally()
    {
        return m__CachedManagedExternally;
    }

    /**
     * Retrieves whether the attribute is managed externally or not.
     * @return such information.
     */
    public boolean getManagedExternally()
    {
        Boolean result = getCachedManagedExternally();
        
        if  (result == null)
        {
            result =
                (retrieveManagedExternally(
                     getTableName(),
                     getName(),
                     getMetadataManager()))
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedManagedExternally(result);
        }

        return result.booleanValue();
    }

    /**
     * Finds out whether the attribute is managed externally or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected boolean retrieveManagedExternally(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.isManagedExternally(tableName, columnName);
    }


    /**
     * Caches whether the attribute allows null or not.
     * @param flag such flag.
     */
    protected final void immutableSetCachedAllowsNull(final Boolean flag)
    {
        m__CachedAllowsNull = flag;
    }

    /**
     * Caches whether the attribute allows null or not.
     * @param flag such flag.
     */
    protected void setCachedAllowsNull(final Boolean flag)
    {
        immutableSetCachedAllowsNull(flag);
    }

    /**
     * Retrieves the cache of whether the attribute allows null or not.
     * @return such flag.
     */
    protected Boolean getCachedAllowsNull()
    {
        return m__CachedAllowsNull;
    }

    /**
     * Retrieves whether the attribute allows null or not.
     * @return such information.
     */
    public boolean getAllowsNull()
    {
        Boolean result = getCachedAllowsNull();
        
        if  (result == null)
        {
            result =
                (retrieveAllowsNull(
                     getTableName(),
                     getName(),
                     getMetadataManager()))
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedAllowsNull(result);
        }

        return result.booleanValue();
    }

    /**
     * Finds out whether the attribute allows null or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected boolean retrieveAllowsNull(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.allowsNull(tableName, columnName);
    }


    /**
     * Caches whether the attribute is read-only or not.
     * @param flag such flag.
     */
    protected final void immutableSetCachedReadOnly(final Boolean flag)
    {
        m__CachedReadOnly = flag;
    }

    /**
     * Caches whether the attribute is read-only or not.
     * @param flag such flag.
     */
    protected void setCachedReadOnly(final Boolean flag)
    {
        immutableSetCachedReadOnly(flag);
    }

    /**
     * Retrieves the cache of whether the attribute is read-only or not.
     * @return such flag.
     */
    protected Boolean getCachedReadOnly()
    {
        return m__CachedReadOnly;
    }

    /**
     * Retrieves whether the attribute is read-only or not.
     * @return such information.
     */
    public boolean getReadOnly()
    {
        Boolean result = getCachedReadOnly();
        
        if  (result == null)
        {
            result =
                (retrieveReadOnly(
                     getTableName(),
                     getName(),
                     getMetadataManager()))
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedReadOnly(result);
        }

        return result.booleanValue();
    }

    /**
     * Finds out whether the attribute is read-only or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != only
     * @precondition columnName != only
     * @precondition metadataManager != only
     */
    protected boolean retrieveReadOnly(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.isReadOnly(tableName, columnName);
    }


    /**
     * Caches whether the attribute is marked as boolean or not.
     * @param flag such flag.
     */
    protected final void immutableSetCachedBoolean(final Boolean flag)
    {
        m__CachedBoolean = flag;
    }

    /**
     * Caches whether the attribute is marked as boolean or not.
     * @param flag such flag.
     */
    protected void setCachedBoolean(final Boolean flag)
    {
        immutableSetCachedBoolean(flag);
    }

    /**
     * Retrieves the cache of whether the attribute is marked as boolean or not.
     * @return such flag.
     */
    protected Boolean getCachedBoolean()
    {
        return m__CachedBoolean;
    }

    /**
     * Retrieves whether the attribute is marked as boolean or not.
     * @return such information.
     */
    public boolean getBoolean()
    {
        Boolean result = getCachedBoolean();
        
        if  (result == null)
        {
            result =
                (retrieveBoolean(
                     getTableName(),
                     getName(),
                     getMetadataManager()))
                ?  Boolean.TRUE
                :  Boolean.FALSE;

            setCachedBoolean(result);
        }

        return result.booleanValue();
    }

    /**
     * Finds out whether the attribute is marked as boolean or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != 
     * @precondition columnName != 
     * @precondition metadataManager != 
     */
    protected boolean retrieveBoolean(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.isBoolean(tableName, columnName);
    }

    /**
     * Specifies the cached true symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanTrue(final String symbol)
    {
        m__strCachedBooleanTrue = symbol;
    }

    /**
     * Specifies the cached true symbol for boolean values.
     * @param flag such flag.
     */
    protected void setCachedBooleanTrue(final String symbol)
    {
        immutableSetCachedBooleanTrue(symbol);
    }

    /**
     * Retrieves the cached true symbol for boolean values.
     * @return such true.
     */
    protected String getCachedBooleanTrue()
    {
        return m__strCachedBooleanTrue;
    }

    /**
     * Retrieves the true symbol for boolean values.
     * @return such information.
     */
    public String getBooleanTrue()
    {
        String result = getCachedBooleanTrue();

        if  (result == null)
        {
            result =
                retrieveBooleanTrue(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            setCachedBooleanTrue(result);
        }

        return result;
    }

    /**
     * Retrieves the true symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected String retrieveBooleanTrue(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.getBooleanTrue(tableName, columnName);
    }

    /**
     * Specifies the cached false symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanFalse(final String symbol)
    {
        m__strCachedBooleanFalse = symbol;
    }

    /**
     * Specifies the cached false symbol for boolean values.
     * @param flag such flag.
     */
    protected void setCachedBooleanFalse(final String symbol)
    {
        immutableSetCachedBooleanFalse(symbol);
    }

    /**
     * Retrieves the cached false symbol for boolean values.
     * @return such false.
     */
    protected String getCachedBooleanFalse()
    {
        return m__strCachedBooleanFalse;
    }

    /**
     * Retrieves the false symbol for boolean values.
     * @return such information.
     */
    public String getBooleanFalse()
    {
        String result = getCachedBooleanFalse();

        if  (result == null)
        {
            result =
                retrieveBooleanFalse(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            setCachedBooleanFalse(result);
        }

        return result;
    }

    /**
     * Retrieves the false symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected String retrieveBooleanFalse(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.getBooleanFalse(tableName, columnName);
    }


    /**
     * Specifies the cached null symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanNull(final String symbol)
    {
        m__strCachedBooleanNull = symbol;
    }

    /**
     * Specifies the cached null symbol for boolean values.
     * @param flag such flag.
     */
    protected void setCachedBooleanNull(final String symbol)
    {
        immutableSetCachedBooleanNull(symbol);
    }

    /**
     * Retrieves the cached null symbol for boolean values.
     * @return such null.
     */
    protected String getCachedBooleanNull()
    {
        return m__strCachedBooleanNull;
    }

    /**
     * Retrieves the null symbol for boolean values.
     * @return such information.
     */
    public String getBooleanNull()
    {
        String result = getCachedBooleanNull();

        if  (result == null)
        {
            result =
                retrieveBooleanNull(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            setCachedBooleanNull(result);
        }

        return result;
    }

    /**
     * Retrieves the null symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition columnName != null
     * @precondition metadataManager != null
     */
    protected String retrieveBooleanNull(
        final String tableName,
        final String columnName,
        final MetadataManager metadataManager)
    {
        return metadataManager.getBooleanNull(tableName, columnName);
    }
}
