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
 * Filename: AbstractForeignKey.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides the common logic for foreign key implementations.
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Provides the common logic for foreign key implementations.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractForeignKey
    implements  ForeignKey
{
    /**
     * The source table name.
     */
    private String m__strSourceTableName;
    
    /**
     * The attributes.
     */
    private List m__lAttributes;
    
    /**
     * The target table name.
     */
    private String m__strTargetTableName;
    
    /**
     * Whether the foreign key allows null values.
     */
    private boolean m__bNullable;

    /**
     * Creates an <code>AbstractForeignKey</code> with given information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key can take null values.
     * @precondition sourceTableName the source table name.
     * @precondition attributes != null
     * @precondition targetTableName != null
     */
    protected AbstractForeignKey(
        final String sourceTableName,
        final List attributes,
        final String targetTableName,
        final boolean allowsNull)
    {
        immutableSetSourceTableName(sourceTableName);
        immutableSetAttributes(attributes);
        immutableSetTargetTableName(targetTableName);
        immutableIsNullable(allowsNull);
    }

    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    protected final void immutableSetSourceTableName(final String tableName)
    {
        m__strSourceTableName = tableName;
    }
    
    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    protected void setSourceTableName(final String tableName)
    {
        immutableSetSourceTableName(tableName);
    }
    
    /**
     * Retrieves the source table name.
     * @return such table name.
     */
    public String getSourceTableName()
    {
        return m__strSourceTableName;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes such attributes.
     */
    protected final void immutableSetAttributes(final List attributes)
    {
        m__lAttributes = attributes;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes the attributes.
     */
    protected void setAttributes(final List attributes)
    {
        immutableSetAttributes(attributes);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAttributes()
    {
        return m__lAttributes;
    }

    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    protected final void immutableSetTargetTableName(final String tableName)
    {
        m__strTargetTableName = tableName;
    }
    
    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    protected void setTargetTableName(final String tableName)
    {
        immutableSetTargetTableName(tableName);
    }
    
    /**
     * Retrieves the target table name.
     * @return such table name.
     */
    public String getTargetTableName()
    {
        return m__strTargetTableName;
    }

    /**
     * Specifies whether the foreign key can take null values.
     * @param allowsNull whether it allows null.
     */
    protected final void immutableIsNullable(final boolean allowsNull)
    {
        m__bNullable = allowsNull;
    }

    /**
     * Specifies whether the foreign key can take null values.
     * @param allowsNull whether it allows null.
     */
    @SuppressWarnings("unused")
    protected void isNullable(final boolean allowsNull)
    {
        immutableIsNullable(allowsNull);
    }

    /**
     * Retrieves whether the foreign key can take null values.
     * @return such information.
     */
    public boolean isNullable()
    {
        return m__bNullable;
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
                .append("sourceTableName", getSourceTableName())
                .append("attributes", getAttributes())
                .append("targetTableName", getTargetTableName())
                .append("nullable", isNullable())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006169, 996718193)
                .append(getSourceTableName())
                .append(getAttributes())
                .append(getTargetTableName())
                .append(isNullable())
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

        if  (object instanceof ForeignKey)
        {
            @NotNull final ForeignKey t_OtherInstance = (ForeignKey) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .append(
                        getSourceTableName(),
                        t_OtherInstance.getSourceTableName())
                    .append(
                        getAttributes(),
                        t_OtherInstance.getAttributes())
                    .append(
                        getTargetTableName(),
                        t_OtherInstance.getTargetTableName())
                    .append(
                        isNullable(),
                        t_OtherInstance.isNullable())
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

        if  (object instanceof ForeignKey)
        {
            @NotNull final ForeignKey t_OtherInstance = (ForeignKey) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getSourceTableName(),
                    t_OtherInstance.getSourceTableName())
                .append(
                    getAttributes(),
                    t_OtherInstance.getAttributes())
                .append(
                    getTargetTableName(),
                    t_OtherInstance.getTargetTableName())
                .append(
                    isNullable(),
                    t_OtherInstance.isNullable())
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
