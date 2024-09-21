/*
                        QueryJ Core

    Copyright (C) 2002-today Jose San Leandro Armendariz
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
 * Description: Abstract logic-less implementation of Table interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing Apache Commons Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract logic-less implementation of <code>Table</code> interface.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @param <V> the value type.
 * @param <A> the attribute type.
 * @param <L> the list type.
 */
@ThreadSafe
public abstract class AbstractTable<V, A extends Attribute<V>, L extends List<A>>
    implements Table<V, A, L>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 2693158501095404586L;

    /**
     * The name.
     */
    private V m__Name;

    /**
     * The comment.
     */
    private V m__Comment;

    /**
     * The parent table, if any.
     */
    private Table<V, A, L> m__ParentTable;

    /**
     * The primary key.
     */
    private L m__lPrimaryKey;

    /**
     * The attributes.
     */
    private L m__lAttributes;

    /**
     * The foreign keys.
     */
    private List<ForeignKey<V>> m__lForeignKeys;

    /**
     * The attribute used to label static rows.
     */
    private A m__Static;

    /**
     * Whether the value-object for the table is decorated.
     */
    private boolean m__bVoDecorated;

    /**
     * Whether the table represents a relationship.
     */
    private boolean m__bRelationship;

    /**
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     * @param comment the comment.
     * is decorated.
     */
    protected AbstractTable(@NotNull final V name, @Nullable final V comment)
    {
        immutableSetName(name);
        immutableSetComment(comment);
    }

    /**
     * Creates an <code>AbstractTable</code> with the following
     * information.
     * @param name the name.
     * @param comment the comment.
     * @param parentTable the parent table, if any.
     * @param staticAttribute the attribute used to label static rows.
     * @param voDecorated whether the value-object for the table
     * is decorated.
     * @param isRelationship whether the table identifies a relationship.
     */
    protected AbstractTable(
        @NotNull final V name,
        @Nullable final V comment,
        @Nullable final Table<V, A, L> parentTable,
        @Nullable final A staticAttribute,
        final boolean voDecorated,
        final boolean isRelationship)
    {
        immutableSetName(name);
        immutableSetComment(comment);
        immutableSetParentTable(parentTable);
        if (staticAttribute != null)
        {
            immutableSetStaticAttribute(staticAttribute);
        }
        immutableSetVoDecorated(voDecorated);
        immutableSetRelationship(isRelationship);
    }

    /**
     * Creates a new instance.
     * @param name the name.
     * @param comment the comment.
     * @param primaryKey the primary key.
     * @param attributes the attributes.
     * @param foreignKeys the foreign keys.
     * @param parentTable the parent table.
     * @param staticAttribute the attribute used to label static rows.
     * @param voDecorated whether it's decorated.
     * @param isRelationship whether the table identifies a relationship.
     */
    protected AbstractTable(
        @NotNull final V name,
        @Nullable final V comment,
        @NotNull final L primaryKey,
        @NotNull final L attributes,
        @NotNull final List<ForeignKey<V>> foreignKeys,
        @Nullable final Table<V, A, L> parentTable,
        @Nullable final A staticAttribute,
        final boolean voDecorated,
        final boolean isRelationship)
    {
        this(name, comment, parentTable, staticAttribute, voDecorated, isRelationship);
        immutableSetPrimaryKey(primaryKey);
        immutableSetAttributes(attributes);
        immutableSetForeignKeys(foreignKeys);
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(@NotNull final V name)
    {
        m__Name = name;
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(@NotNull final V name)
    {
        immutableSetName(name);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public V getName()
    {
        return m__Name;
    }

    /**
     * Specifies the table comment.
     * @param comment the comment.
     */
    protected final void immutableSetComment(@Nullable final V comment)
    {
        m__Comment = comment;
    }

    /**
     * Specifies the table comment.
     * @param comment the comment.
     */
    @SuppressWarnings("unused")
    protected void setComment(@Nullable final V comment)
    {
        immutableSetComment(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public V getComment()
    {
        return m__Comment;
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    protected final void immutableSetParentTable(@Nullable final Table<V, A, L> parentTable)
    {
        m__ParentTable = parentTable;
    }

    /**
     * Specifies the parent table.
     * @param parentTable the parent table.
     */
    @SuppressWarnings("unused")
    protected void setParentTable(@NotNull final Table<V, A, L> parentTable)
    {
        immutableSetParentTable(parentTable);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public Table<V, A, L> getParentTable()
    {
        return m__ParentTable;
    }

    /**
     * Specifies the primary key.
     * @param pk the primary key.
     */
    protected final void immutableSetPrimaryKey(@NotNull final L pk)
    {
        this.m__lPrimaryKey = pk;
    }

    /**
     * Specifies the primary key.
     * @param pk the primary key.
     */
    protected void setPrimaryKey(@NotNull final L pk)
    {
        immutableSetPrimaryKey(pk);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public L getPrimaryKey()
    {
        return this.m__lPrimaryKey;
    }

    /**
     * Specifies the attributes.
     * @param attrs such attributes.
     */
    protected final void immutableSetAttributes(@NotNull final L attrs)
    {
        this.m__lAttributes = attrs;
    }

    /**
     * Specifies the attributes.
     * @param attrs such attributes.
     */
    protected void setAttributes(@NotNull final L attrs)
    {
        immutableSetAttributes(attrs);
    }

    /**
     * Retrieves the attributes.
     * @return attributes.
     */
    @NotNull
    protected final L immutableGetAttributes()
    {
        return this.m__lAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public L getAttributes()
    {
        return immutableGetAttributes();
    }

    /**
     * Specifies the foreign keys.
     * @param fks the foreign keys.
     */
    @SuppressWarnings("unused")
    protected final void immutableSetForeignKeys(@NotNull final List<ForeignKey<V>> fks)
    {
        this.m__lForeignKeys = fks;
    }

    /**
     * Specifies the foreign keys.
     * @param fks the foreign keys.
     */
    protected void setForeignKeys(@NotNull final List<ForeignKey<V>> fks)
    {
        immutableSetForeignKeys(fks);
    }

    /**
     * Retrieves the foreign keys.
     * @return the foreign keys.
     */
    @Nullable
    protected final List<ForeignKey<V>> immutableGetForeignKeys()
    {
        return this.m__lForeignKeys;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<ForeignKey<V>> getForeignKeys()
    {
        @NotNull final List<ForeignKey<V>> result;

        @Nullable final List<ForeignKey<V>> aux = immutableGetForeignKeys();

        if (aux == null)
        {
            result = new ArrayList<>(0);
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Specifies the attribute used to label static contents.
     * @param attribute such attribute.
     */
    protected final void immutableSetStaticAttribute(@NotNull final A attribute)
    {
        m__Static = attribute;
    }

    /**
     * Specifies the attribute used to label static contents.
     * @param attribute such attribute.
     */
    @SuppressWarnings("unused")
    protected void setStaticAttribute(@NotNull final A attribute)
    {
        immutableSetStaticAttribute(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public A getStaticAttribute()
    {
        return m__Static;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    @Override
    public boolean isStatic()
    {
        return m__Static != null;
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
     * {@inheritDoc}
     */
    @Override
    public boolean isVoDecorated()
    {
        return m__bVoDecorated;
    }

    /**
     * Specifies whether the table identifies a relationship.
     * @param flag such condition.
     */
    protected final void immutableSetRelationship(final boolean flag)
    {
        this.m__bRelationship = flag;
    }

    /**
     * Specifies whether the table identifies a relationship.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setRelationship(final boolean flag)
    {
        immutableSetRelationship(flag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRelationship()
    {
        return this.m__bRelationship;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m__Name).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
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
        @SuppressWarnings("unchecked")
        final AbstractTable<V, A, L> other = (AbstractTable<V, A, L>) obj;
        return new EqualsBuilder().append(this.m__Name, other.m__Name).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@NotNull final Table<V, A, L> o)
    {
        return compareThem(this, o);
    }

    /**
     * Compares given {@link Table} instances.
     * @param first the first.
     * @param second the second.
     * @return a positive number if the name of first table is considered
     * 'greater' than the second's; 0 if they are equal; a negative number
     * otherwise.
     */
    protected int compareThem(
        @NotNull final Table<V, A, L> first, @NotNull final Table<V, A, L> second)
    {
        return ("" + first.getName()).compareToIgnoreCase("" + second.getName());
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"name\": \"" + m__Name + '"'
            + ", \"comment\": \"" + m__Comment + '"'
            + ", \"parentTable\": " + m__ParentTable
//            + ", \"primaryKey\": " + ToStringUtils.getInstance().toJson(m__lPrimaryKey)
//            + ", \"attributes\": " + ToStringUtils.getInstance().toJson(m__lAttributes)
//            + ", \"foreignKeys\": " + ToStringUtils.getInstance().toJson(m__lForeignKeys)
            + ", \"static\": " + m__Static
            + ", \"voDecorated\": " + m__bVoDecorated
            + ", \"relationship\": " + m__bRelationship
            + ", \"class\": \"" + AbstractTable.class.getSimpleName()
            + ", \"package\": \"org.acmsl.queryj.metadata.vo\" }";
    }
}
