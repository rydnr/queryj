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
 * Filename: AbstractTable.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract logicless implementation of Table interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing project classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
 
/**
 * Abstract logicless implementation of <code>Table</code> interface.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTable
    implements Table
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The comment.
     */
    private String m__strComment;

    /**
     * The primary key attributes.
     */
    private List<Attribute> m__lPrimaryKey;

    /**
     * The attribute list.
     */
    private List<Attribute> m__lAttributes;

    /**
     * The parent table, if any.
     */
    private Table m__ParentTable;

    /**
     * The foreign keys.
     */
    private List<ForeignKey> m__lForeignKeys;

    /**
     * Flag indicating whether the table is static.
     */
    private boolean m__bStatic;

    /**
     * Whether the value-object for the table is decorated.
     */
    private boolean m__bVoDecorated;

    /**
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     * @param comment the comment.
     * is decorated.
     */
    protected AbstractTable(
        @NotNull final String name, @Nullable final String comment)
    {
        immutableSetName(name);
        immutableSetComment(comment);
    }

    /**
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     * @param comment the comment.
     * @param primaryKey the primary key attributes.
     * @param attributes the attributes.
     * @param parentTable the parent table, if any.
     * @param isStatic whether the table is static.
     * @param voDecorated whether the value-object for the table
     * is decorated.
     */
    protected AbstractTable(
        @NotNull final String name,
        @Nullable final String comment,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final List<Attribute> attributes,
        @NotNull final List<ForeignKey> foreignKeys,
        @Nullable final Table parentTable,
        final boolean isStatic,
        final boolean voDecorated)
    {
        immutableSetName(name);
        immutableSetComment(comment);
        immutableSetPrimaryKey(primaryKey);
        immutableSetAttributes(attributes);
        immutableSetForeignKeys(foreignKeys);
        immutableSetParentTable(parentTable);
        immutableSetStatic(isStatic);
        immutableSetVoDecorated(voDecorated);
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strName = name;
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }
    
    /**
     * Retrieves the table name.
     * @return such name.
     */
    @NotNull
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table comment.
     * @param comment the comment.
     */
    protected final void immutableSetComment(@Nullable final String comment)
    {
        m__strComment = comment;
    }

    /**
     * Specifies the table comment.
     * @param comment the comment.
     */
    @SuppressWarnings("unused")
    protected void setComment(@Nullable final String comment)
    {
        immutableSetComment(comment);
    }

    /**
     * Retrieves the table comment.
     * @return such information.
     */
    @Nullable
    public String getComment()
    {
        return m__strComment;
    }

    /**
     * Specifies the primary key attributes.
     * @param attrs the primary key attributes.
     */
    protected final void immutableSetPrimaryKey(@NotNull final List<Attribute> attrs)
    {
        m__lPrimaryKey = attrs;
    }

    /**
     * Specifies the primary key attributes.
     * @param attrs the primary key attributes.
     */
    @SuppressWarnings("unused")
    protected void setPrimaryKey(@NotNull final List<Attribute> attrs)
    {
        immutableSetPrimaryKey(attrs);
    }

    /**
     * Retrieves the primary key attributes.
     * @return such list.
     */
    @Nullable
    @SuppressWarnings("unused")
    protected final List<Attribute> immutableGetPrimaryKey()
    {
        return m__lPrimaryKey;
    }

    /**
     * Retrieves the primary key attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getPrimaryKey()
    {
        List<Attribute> result = immutableGetAttributes();

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
            setPrimaryKey(result);
        }

        return result;
    }

    /**
     * Specifies the attributes.
     * @param attrs the attributes.
     */
    protected final void immutableSetAttributes(final List<Attribute> attrs)
    {
        m__lAttributes = attrs;
    }

    /**
     * Specifies the attributes.
     * @param attrs the attributes.
     */
    protected void setAttributes(final List<Attribute> attrs)
    {
        immutableSetAttributes(attrs);
    }

    /**
     * Retrieves the attributes.
     * @return such list.
     */
    @Nullable
    protected final List<Attribute> immutableGetAttributes()
    {
        return m__lAttributes;
    }

    /**
     * Retrieves the attributes.
     * @return such list.
     */
    @NotNull
    public List<Attribute> getAttributes()
    {
        List<Attribute> result = immutableGetAttributes();

        if (result == null)
        {
            result = new ArrayList<Attribute>(0);
            setAttributes(result);
        }

        return result;
    }

    /**
     * Specifies the foreign keys.
     * @param fks the List of {@link ForeignKey}s.
     */
    protected final void immutableSetForeignKeys(@NotNull final List<ForeignKey> fks)
    {
        m__lForeignKeys = fks;
    }

    /**
     * Specifies the foreign keys.
     * @param fks the List of {@link ForeignKey foreign keys}.
     */
    protected void setForeignKeys(@NotNull final List<ForeignKey> fks)
    {
        immutableSetForeignKeys(fks);
    }

    /**
     * Retrieves the foreign keys.
     * @return such information.
     */
    @Nullable
    protected final List<ForeignKey> immutableGetForeignKeys()
    {
        return m__lForeignKeys;
    }

    /**
     * Retrieves the foreign keys.
     * @return such information.
     */
    @NotNull
    @SuppressWarnings("unused")
    public List<ForeignKey> getForeignKeys()
    {
        List<ForeignKey> result = immutableGetForeignKeys();

        if (result == null)
        {
            result = new ArrayList<ForeignKey>(0);
            setForeignKeys(result);
        }

        return result;
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    protected final void immutableSetParentTable(@Nullable final Table parentTable)
    {
        m__ParentTable = parentTable;
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    @SuppressWarnings("unused")
    protected void setParentTable(@NotNull final Table parentTable)
    {
        immutableSetParentTable(parentTable);
    }

    /**
     * Retrieves the parent table.
     * @return such table.
     */
    @Nullable
    public Table getParentTable()
    {
        return m__ParentTable;
    }

    /**
     * Specifies whether the table is static or not.
     * @param flag such flag.
     */
    protected final void immutableSetStatic(final boolean flag)
    {
        m__bStatic = flag;
    }

    /**
     * Specifies whether the table is static or not.
     * @param flag such flag.
     */
    @SuppressWarnings("unused")
    protected void setStatic(final boolean flag)
    {
        immutableSetStatic(flag);
    }

    /**
     * Retrieves whether the table is static or not.
     * @return <tt>true</tt> in such case.
     */
    @SuppressWarnings("unused")
    public boolean isStatic()
    {
        return m__bStatic;
    }

    /**
     * Specifies whether the value-object for the table
     * is decorated.
     * @param flag such flag.
     */
    protected final void immutableSetVoDecorated(final boolean flag)
    {
        m__bVoDecorated = flag;
    }

    /**
     * Specifies whether the value-object for the table
     * is decorated.
     * @param flag such flag.
     */
    @SuppressWarnings("unused")
    protected void setVoDecorated(final boolean flag)
    {
        immutableSetVoDecorated(flag);
    }

    /**
     * Retrieves whether the value-object for the table is decorated.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isVoDecorated()
    {
        return m__bVoDecorated;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m__strName).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AbstractTable other = (AbstractTable) obj;
        return new EqualsBuilder().append(this.m__strName, other.m__strName).isEquals();
    }
}
