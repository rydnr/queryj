//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: AbstractRow.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract logicless implementation of Row interface.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * importing JDK classes.
 */
import java.util.Collection;

/**
 * Abstract logicless implementation of <code>Row</code> interface.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractRow
    implements Row
{
    /**
     * The name.
     */
    private String m__strName;
    
    /**
     * The table name.
     */
    private String m__strTableName;

    /**
     * The attributes.
     */
    private Collection m__cAttributes;

    /**
     * Creates an <code>AbstractRow</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @precondition name != null
     * @precondition tableName != null
     * @precondition attributes != null
     */
    protected AbstractRow(
        final String name,
        final String tableName,
        final Collection attributes)
    {
        immutableSetName(name);
        immutableSetTableName(tableName);
        immutableSetAttributes(attributes);
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strName = name;
    }
    
    /**
     * Specifies the name.
     * @param name such name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
    }
    
    /**
     * Retrieves the attribute name.
     * @return such name.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    protected final void immutableSetTableName(final String tableName)
    {
        m__strTableName = tableName;
    }
    
    /**
     * Specifies the table name.
     * @param tableName such information.
     */
    protected void setTableName(final String tableName)
    {
        immutableSetTableName(tableName);
    }
    
    /**
     * Retrieves the table name.
     * @return such information.
     */
    public String getTableName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the attributes.
     * @param attributes such information.
     */
    protected final void immutableSetAttributes(final Collection attributes)
    {
        m__cAttributes = attributes;
    }

    /**
     * Specifies the attributes.
     * @param attributes such information.
     */
    protected void setAttributes(final Collection attributes)
    {
        immutableSetAttributes(attributes);
    }

    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public Collection getAttributes()
    {
        return m__cAttributes;
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
                .append("name", getName())
                .append("tableName", getTableName())
                .append("attributes", getAttributes())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
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
    public boolean equals(final Object object)
    {
        boolean result = false;

        if  (object instanceof Row)
        {
            @NotNull final Row t_OtherInstance = (Row) object;

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
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

        @Nullable ClassCastException exceptionToThrow = null;

        if  (object instanceof Row)
        {
            @NotNull final Row t_OtherInstance = (Row) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getName(),
                    t_OtherInstance.getName())
                .append(
                    getTableName(),
                    t_OtherInstance.getTableName())
                .append(
                    getAttributes(),
                    t_OtherInstance.getAttributes())
                .toComparison();
        }
        else
        {
            exceptionToThrow =
                new ClassCastException(
                      "Cannot compare "
                    + object
                    + " with "
                    + toString());
        }

        if  (exceptionToThrow != null)
        {
            throw  exceptionToThrow;
        }

        return result;
    }
}

