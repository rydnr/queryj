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
 * Filename: AbstractRow.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract logic-less implementation of Row interface.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * importing JDK classes.
 */
import java.util.List;

/**
 * Abstract logic-less implementation of <code>Row</code> interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <V> the type.
 */
@ThreadSafe
public abstract class AbstractRow<V>
    implements Row<V>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 2448751869446686328L;

    /**
     * The name.
     */
    private V m__strName;
    
    /**
     * The table name.
     */
    private V m__strTableName;

    /**
     * The attributes.
     */
    private List<Attribute<V>> m__lAttributes;

    /**
     * Creates an <code>AbstractRow</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     */
    protected AbstractRow(
        @NotNull final V name,
        @NotNull final V tableName,
        @NotNull final List<Attribute<V>> attributes)
    {
        immutableSetName(name);
        immutableSetTableName(tableName);
        immutableSetAttributes(attributes);
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(@NotNull final V name)
    {
        m__strName = name;
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
     * Retrieves the attribute name.
     * @return such name.
     */
    @Override
    @NotNull
    public V getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    protected final void immutableSetTableName(@NotNull final V tableName)
    {
        m__strTableName = tableName;
    }
    
    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    @SuppressWarnings("unused")
    protected void setTableName(@NotNull final V tableName)
    {
        immutableSetTableName(tableName);
    }
    
    /**
     * Retrieves the table name.
     * @return such information.
     */
    @NotNull
    public V getTableName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the attributes.
     * @param attributes such information.
     */
    protected final void immutableSetAttributes(@NotNull final List<Attribute<V>> attributes)
    {
        m__lAttributes = attributes;
    }

    /**
     * Specifies the attributes.
     * @param attributes such information.
     */
    @SuppressWarnings("unused")
    protected void setAttributes(@NotNull final List<Attribute<V>> attributes)
    {
        immutableSetAttributes(attributes);
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Attribute<V>> getAttributes()
    {
        return m__lAttributes;
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006155, 1067893523)
                .append(getName())
                .append(getTableName())
                .append(getAttributes())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if  (object instanceof Row<?>)
        {
            @NotNull final Row<?> t_OtherInstance = (Row<?>) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .append(
                        getName(),
                        t_OtherInstance.getName())
                    .append(
                        getTableName(),
                        t_OtherInstance.getTableName())
                    .append(
                        getAttributes(),
                        t_OtherInstance.getAttributes())
                .isEquals();
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public int compareTo(@Nullable final Row<V> object)
        throws  ClassCastException
    {
        int result = 1;

        if  (object != null)
        {
            result = compareThem(this, object);
        }

        return result;
    }

    /**
     * Compares both {@link Row rows}.
     * @param first the first.
     * @param second the second.
     * @return a positive number of the first is considered 'greater'
     * than the second; 0 if they're equal; a negative number otherwise.
     */
    protected int compareThem(@NotNull final Row<V> first, @NotNull final Row<?> second)
    {
        return
            new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    first.getName(),
                    second.getName())
                .append(
                    first.getTableName(),
                    second.getTableName())
                .toComparison();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"name\": \"" + this.m__strName + '"'
            + ", \"table\": \"" + this.m__strTableName + '"'
            + ", \"attributes\": \"" + this.m__lAttributes + '"'
            + ", \"class\": \"AbstractRow\""
            + ", \"package\": \"org.acmsl.queryj.metadata.vo\" }";
    }
}

