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
package org.acmsl.queryj.metadata.vo;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * <code>Attribute</code> implementation which retrieves its information
 * lazily.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 */
@ThreadSafe
public class LazyAttribute
    extends  AbstractAttribute<String>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3002626986582985895L;

    /**
     * The cached type.
     */
    private Integer m__CachedTypeId;

    /**
     * The cached type.
     */
    private String m__CachedType;

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
     */
    public LazyAttribute(
        @NotNull final String tableName,
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
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
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    protected final void immutableSetCachedTypeId(@NotNull final Integer type)
    {
        m__CachedTypeId = type;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    @SuppressWarnings("unused")
    protected void setCachedTypeId(@NotNull final Integer type)
    {
        immutableSetCachedTypeId(type);
    }

    /**
     * Retrieves the cached type.
     * @return such information.
     */
    @Nullable
    protected Integer getCachedTypeId()
    {
        return m__CachedTypeId;
    }

    /**
     * Retrieves the attribute type.
     * @return its type.
     */
    public int getTypeId()
    {
        @Nullable Integer result = getCachedTypeId();

        if  (result == null)
        {
            result =
                retrieveTypeId(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            setCachedTypeId(result);
        }

        return result;
    }

    /**
     * Retrieves the type id.
     * @param tableName the table name.
     * @param name the name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected int retrieveTypeId(
        @NotNull final String tableName,
        @NotNull final String name,
        @NotNull final MetadataManager metadataManager)
    {
        int result = -1;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, name, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.getTypeId();
        }

        return result;
    }

    /**
     * Specifies the cached type.
     * @param type such type.
     */
    protected final void immutableSetCachedType(final String type)
    {
        m__CachedType = type;
    }

    /**
     * Specifies the cached type.
     * @param type such type.
     */
    protected void setCachedType(final String type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached type.
     * @return such type.
     */
    protected String getCachedType()
    {
        return m__CachedType;
    }

    /**
     * Retrieves the native type.
     * @return such information.
     */
    @NotNull
    public String getType()
    {
        String result = getCachedType();

        if  (result == null)
        {
            result =
                retrieveNativeType(
                    getTypeId(),
                    getMetadataTypeManager());

            setCachedType(result);
        }

        return result;
    }

    /**
     * Retrieves the type.
     * @param type the column type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such information.
     */
    protected String retrieveNativeType(
        final int type,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getNativeType(type);
    }

    /**
     * Specifies the cached comment.
     * @param comment such comment.
     */
    protected final void immutableSetCachedComment(@NotNull final String comment)
    {
        m__strCachedComment = comment;
    }

    /**
     * Specifies the cached comment.
     * @param comment such comment.
     */
    protected void setCachedComment(@NotNull final String comment)
    {
        immutableSetCachedComment(comment);
    }

    /**
     * Retrieves the cached comment.
     * @return such information.
     */
    @Nullable
    protected String getCachedComment()
    {
        return m__strCachedComment;
    }

    /**
     * Retrieves the comment.
     * @return such information.
     */
    @Nullable
    public String getComment()
    {
        @Nullable String result = getCachedComment();

        if  (result == null)
        {
            result =
                retrieveComment(
                    getTableName(), getName(), getMetadataManager());

            if (result != null)
            {
                setCachedComment(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the comment.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected String retrieveComment(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = null;

        @Nullable final Attribute<String> t_Attribute =
            retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.getComment();
        }

        return result;
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
    @SuppressWarnings("unused")
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

        return result;
    }

    /**
     * Finds out whether the attribute is managed externally or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected boolean retrieveManagedExternally(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.isExternallyManaged();
        }

        return result;
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
    @SuppressWarnings("unused")
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

        return result;
    }

    /**
     * Finds out whether the attribute allows null or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected boolean retrieveAllowsNull(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.isNullable();
        }

        return result;
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
    @SuppressWarnings("unused")
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

        return result;
    }

    /**
     * Finds out whether the attribute is read-only or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected boolean retrieveReadOnly(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.isReadOnly();
        }

        return result;
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
    @SuppressWarnings("unused")
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

        return result;
    }

    /**
     * Finds out whether the attribute is marked as boolean or not.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected boolean retrieveBoolean(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        boolean result = false;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.isBoolean();
        }

        return result;
    }

    /**
     * Specifies the cached true symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanTrue(@NotNull final String symbol)
    {
        m__strCachedBooleanTrue = symbol;
    }

    /**
     * Specifies the cached true symbol for boolean values.
     * @param symbol such flag.
     */
    protected void setCachedBooleanTrue(@NotNull final String symbol)
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
    @Nullable
    public String getBooleanTrue()
    {
        @Nullable String result = getCachedBooleanTrue();

        if  (result == null)
        {
            result =
                retrieveBooleanTrue(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            if (result != null)
            {
                setCachedBooleanTrue(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the true symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected String retrieveBooleanTrue(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = null;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.getBooleanTrue();
        }

        return result;
    }

    /**
     * Retrieves the attribute.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the attribute.
     */
    @Nullable
    protected Attribute<String> retrieveAttribute(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable Attribute<String> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            for (@Nullable final Attribute<String> t_Attribute : t_Table.getAttributes())
            {
                if (   (t_Attribute != null)
                    && (t_Attribute.getName().equalsIgnoreCase(columnName)))
                {
                    result = t_Attribute;
                }
            }
        }

        return result;
    }

    /**
     * Specifies the cached false symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanFalse(@NotNull final String symbol)
    {
        m__strCachedBooleanFalse = symbol;
    }

    /**
     * Specifies the cached false symbol for boolean values.
     * @param symbol such flag.
     */
    protected void setCachedBooleanFalse(@NotNull final String symbol)
    {
        immutableSetCachedBooleanFalse(symbol);
    }

    /**
     * Retrieves the cached false symbol for boolean values.
     * @return such false.
     */
    @Nullable
    protected String getCachedBooleanFalse()
    {
        return m__strCachedBooleanFalse;
    }

    /**
     * Retrieves the false symbol for boolean values.
     * @return such information.
     */
    @Nullable
    public String getBooleanFalse()
    {
        @Nullable String result = getCachedBooleanFalse();

        if  (result == null)
        {
            result =
                retrieveBooleanFalse(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            if (result != null)
            {
                setCachedBooleanFalse(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the false symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected String retrieveBooleanFalse(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = null;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.getBooleanFalse();
        }

        return result;
    }

    /**
     * Specifies the cached null symbol for boolean values.
     * @param symbol such symbol.
     */
    protected final void immutableSetCachedBooleanNull(@NotNull final String symbol)
    {
        m__strCachedBooleanNull = symbol;
    }

    /**
     * Specifies the cached null symbol for boolean values.
     * @param symbol such flag.
     */
    protected void setCachedBooleanNull(@NotNull final String symbol)
    {
        immutableSetCachedBooleanNull(symbol);
    }

    /**
     * Retrieves the cached null symbol for boolean values.
     * @return such null.
     */
    @Nullable
    protected String getCachedBooleanNull()
    {
        return m__strCachedBooleanNull;
    }

    /**
     * Retrieves the null symbol for boolean values.
     * @return such information.
     */
    @Nullable
    public String getBooleanNull()
    {
        @Nullable String result = getCachedBooleanNull();

        if  (result == null)
        {
            result =
                retrieveBooleanNull(
                    getTableName(),
                    getName(),
                    getMetadataManager());

            if (result != null)
            {
                setCachedBooleanNull(result);
            }
        }

        return result;
    }

    /**
     * Retrieves the null symbol for boolean values.
     * @param tableName the table name.
     * @param columnName the column name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @Nullable
    protected String retrieveBooleanNull(
        @NotNull final String tableName,
        @NotNull final String columnName,
        @NotNull final MetadataManager metadataManager)
    {
        @Nullable String result = null;

        @Nullable final Attribute<String> t_Attribute = retrieveAttribute(tableName, columnName, metadataManager);

        if (t_Attribute != null)
        {
            result = t_Attribute.getBooleanNull();
        }

        return result;
    }

    /**
     * Retrieves whether the attribute value is null
     * or not.
     *
     * @return <code>true</code> if the value is null.
     */
    @SuppressWarnings("unused")
    public boolean isValueNull()
    {
        return getValue() == null;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     *                            from being compared to this object.
     */
    @Override
    public int compareTo(@Nullable final Attribute<String> o)
    {
        int result = 1;

        if (   (this == o)
            || (this.equals(o)))
        {
            result = 0;
        }
        else if (o != null)
        {
            @NotNull final String t_strMyTable = getTableName();
            @NotNull final String t_strHisTable = o.getTableName();

            if (t_strMyTable.equals(t_strHisTable))
            {
                final @NotNull String t_strMyName = getName();
                final String t_strHisValue = o.getValue();

                result = 1;

                if (t_strHisValue != null)
                {
                    result = t_strMyName.hashCode() - t_strHisValue.hashCode();
                }
            }
        }

        return result;
    }

    @Override
    public boolean equals(@Nullable final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        @NotNull final LazyAttribute that = (LazyAttribute) o;

        if (m__CachedAllowsNull != null ? !m__CachedAllowsNull.equals(that.m__CachedAllowsNull)
                                        : that.m__CachedAllowsNull != null)
        {
            return false;
        }
        if (m__CachedBoolean != null ? !m__CachedBoolean.equals(that.m__CachedBoolean) : that.m__CachedBoolean != null)
        {
            return false;
        }
        if (m__CachedManagedExternally != null ? !m__CachedManagedExternally.equals(that.m__CachedManagedExternally)
                                               : that.m__CachedManagedExternally != null)
        {
            return false;
        }
        if (m__CachedType != null ? !m__CachedType.equals(that.m__CachedType)
                                        : that.m__CachedType != null)
        {
            return false;
        }
        if (m__CachedReadOnly != null ? !m__CachedReadOnly.equals(that.m__CachedReadOnly)
                                      : that.m__CachedReadOnly != null)
        {
            return false;
        }
        if (m__CachedType != null ? !m__CachedType.equals(that.m__CachedType) : that.m__CachedType != null)
        {
            return false;
        }
        if (m__MetadataManager != null ? !m__MetadataManager.equals(that.m__MetadataManager)
                                       : that.m__MetadataManager != null)
        {
            return false;
        }
        if (m__MetadataTypeManager != null ? !m__MetadataTypeManager.equals(that.m__MetadataTypeManager)
                                           : that.m__MetadataTypeManager != null)
        {
            return false;
        }
        if (m__strCachedBooleanFalse != null ? !m__strCachedBooleanFalse.equals(that.m__strCachedBooleanFalse)
                                             : that.m__strCachedBooleanFalse != null)
        {
            return false;
        }
        if (m__strCachedBooleanNull != null ? !m__strCachedBooleanNull.equals(that.m__strCachedBooleanNull)
                                            : that.m__strCachedBooleanNull != null)
        {
            return false;
        }
        if (m__strCachedBooleanTrue != null ? !m__strCachedBooleanTrue.equals(that.m__strCachedBooleanTrue)
                                            : that.m__strCachedBooleanTrue != null)
        {
            return false;
        }
        return !(m__strCachedComment != null ? !m__strCachedComment.equals(that.m__strCachedComment)
                                               : that.m__strCachedComment != null);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (m__CachedTypeId != null ? m__CachedTypeId.hashCode() : 0);
        result = 31 * result + (m__CachedType != null ? m__CachedType.hashCode() : 0);
        result = 31 * result + (m__strCachedComment != null ? m__strCachedComment.hashCode() : 0);
        result = 31 * result + (m__CachedManagedExternally != null ? m__CachedManagedExternally.hashCode() : 0);
        result = 31 * result + (m__CachedAllowsNull != null ? m__CachedAllowsNull.hashCode() : 0);
        result = 31 * result + (m__CachedReadOnly != null ? m__CachedReadOnly.hashCode() : 0);
        result = 31 * result + (m__CachedBoolean != null ? m__CachedBoolean.hashCode() : 0);
        result = 31 * result + (m__strCachedBooleanTrue != null ? m__strCachedBooleanTrue.hashCode() : 0);
        result = 31 * result + (m__strCachedBooleanFalse != null ? m__strCachedBooleanFalse.hashCode() : 0);
        result = 31 * result + (m__strCachedBooleanNull != null ? m__strCachedBooleanNull.hashCode() : 0);
        result = 31 * result + (m__MetadataManager != null ? m__MetadataManager.hashCode() : 0);
        result = 31 * result + (m__MetadataTypeManager != null ? m__MetadataTypeManager.hashCode() : 0);
        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + LazyAttribute.class.getName() + '"'
            + ", \"cachedAllowsNull\": " + m__CachedAllowsNull
            + ", \"cachedTypeId\": " + m__CachedTypeId
            + ", \"cachedType\": \"" + m__CachedType + '"'
            + ", \"cachedComment\": \"" + m__strCachedComment + '"'
            + ", \"cachedManagedExternally\": " + m__CachedManagedExternally
            + ", \"cachedReadOnly\": " + m__CachedReadOnly
            + ", \"cachedBoolean\": " + m__CachedBoolean
            + ", \"cachedBooleanTrue\": \"" + m__strCachedBooleanTrue + '"'
            + ", \"cachedBooleanFalse\": \"" + m__strCachedBooleanFalse + '"'
            + ", \"cachedBooleanNull\": \"" + m__strCachedBooleanNull + '"'
            + ", \"metadataManager\": " + m__MetadataManager.hashCode()
            + ", \"metadataTypeManager\": " + m__MetadataTypeManager.hashCode()
            + " }";
    }
}
