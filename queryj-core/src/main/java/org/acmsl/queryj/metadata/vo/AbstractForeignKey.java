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
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.apache.commons.lang.builder.CompareToBuilder;

/*
 * Importing JDK classes.
 */
import java.util.Arrays;
import java.util.List;

/**
 * Provides the common logic for foreign key implementations.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractForeignKey<V>
    implements ForeignKey<V>
{
    private static final long serialVersionUID = -671217258820687779L;

    /**
     * (Optional) the foreign key name.
     */
    private V m__strFkName;

    /**
     * The source table name.
     */
    private V m__strSourceTableName;
    
    /**
     * The attributes.
     */
    private List<Attribute<V>> m__lAttributes;
    
    /**
     * The target table name.
     */
    private V m__strTargetTableName;
    
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
     */
    protected AbstractForeignKey(
        @NotNull final V sourceTableName,
        @NotNull final List<Attribute<V>> attributes,
        @NotNull final V targetTableName,
        final boolean allowsNull)
    {
        immutableSetSourceTableName(sourceTableName);
        immutableSetAttributes(attributes);
        immutableSetTargetTableName(targetTableName);
        immutableIsNullable(allowsNull);
    }

    /**
     * Creates an <code>AbstractForeignKey</code> with given information.
     * @param fkName the name of the foreign key.
     * @param sourceTableName the source table name.
     * @param targetTableName the target table name.
     */
    protected AbstractForeignKey(
        @NotNull final V fkName,
        @NotNull final V sourceTableName,
        @NotNull final V targetTableName)
    {
        immutableSetFkName(fkName);
        immutableSetSourceTableName(sourceTableName);
        immutableSetTargetTableName(targetTableName);
    }

    /**
     * Specifies the foreign key name.
     * @param name such name.
     */
    protected final void immutableSetFkName(@NotNull final V name)
    {
        this.m__strFkName = name;
    }

    /**
     * Specifies the foreign key name.
     * @param name such name.
     */
    @SuppressWarnings("unused")
    protected void setFkName(@NotNull final V name)
    {
        immutableSetFkName(name);
    }

    /**
     * Retrieves the foreign key name.
     * @return such name.
     */
    @SuppressWarnings("unused")
    @Nullable
    public V getFkName()
    {
        return m__strFkName;
    }

    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    protected final void immutableSetSourceTableName(@NotNull final V tableName)
    {
        m__strSourceTableName = tableName;
    }
    
    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    @SuppressWarnings("unused")
    protected void setSourceTableName(@NotNull final V tableName)
    {
        immutableSetSourceTableName(tableName);
    }
    
    /**
     * Retrieves the source table name.
     * @return such table name.
     */
    @NotNull
    public V getSourceTableName()
    {
        return m__strSourceTableName;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes such attributes.
     */
    protected final void immutableSetAttributes(@NotNull final List<Attribute<V>> attributes)
    {
        m__lAttributes = attributes;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes the attributes.
     */
    protected void setAttributes(@NotNull final List<Attribute<V>> attributes)
    {
        immutableSetAttributes(attributes);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Attribute<V>> getAttributes()
    {
        return m__lAttributes;
    }

    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    protected final void immutableSetTargetTableName(@NotNull final V tableName)
    {
        m__strTargetTableName = tableName;
    }
    
    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    @SuppressWarnings("unused")
    protected void setTargetTableName(@NotNull final V tableName)
    {
        immutableSetTargetTableName(tableName);
    }
    
    /**
     * Retrieves the target table name.
     * @return such table name.
     */
    @NotNull
    @Override
    public V getTargetTableName()
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
    @Override
    public boolean isNullable()
    {
        return m__bNullable;
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
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
    @Override
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
    @Override
    public int compareTo(@Nullable final ForeignKey<V> object)
    {
        int result = 1;

        if (object != null)
        {
            result = compareThem(this, object);
        }

        return result;
    }

    /**
     * Compares given {@link ForeignKey foreign keys}.
     * @param first the first.
     * @param second the second.
     * @return a positive number if the first is considered 'greater' than the second;
     * 0 if they are equal; a negative number otherwise.
     */
    @SuppressWarnings("unchecked")
    protected int compareThem(@NotNull final ForeignKey<V> first, @NotNull final ForeignKey<V> second)
    {
        final int result;

        @NotNull final CompareToBuilder comparator = new CompareToBuilder();

        comparator.append(
            first.getSourceTableName(),
            second.getSourceTableName());

        comparator.append(
            first.getTargetTableName(),
            second.getTargetTableName());

        comparator.append(
            first.isNullable(),
            second.isNullable());

        final List<Attribute<V>> t_lFirstAttributes = first.getAttributes();
        final List<Attribute<V>> t_lSecondAttributes = second.getAttributes();

        final Attribute<V>[] t_aFirstAttributes = (Attribute<V>[]) new Attribute[t_lFirstAttributes.size()];
        t_lFirstAttributes.toArray(t_aFirstAttributes);
        final Attribute<V>[] t_aSecondAttributes = (Attribute<V>[]) new Attribute[t_lSecondAttributes.size()];
        t_lSecondAttributes.toArray(t_aSecondAttributes);

        for (int t_iIndex = 0; t_iIndex < t_aFirstAttributes.length; t_iIndex++)
        {
            if (t_iIndex < t_aSecondAttributes.length)
            {
                comparator.append(t_aFirstAttributes[t_iIndex], t_aSecondAttributes[t_iIndex]);
            }
        }

        result = comparator.toComparison();

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractForeignKey.class.getName() + '"'
            + ", \"nullable\": " + m__bNullable
            + ", \"fkName\": \"" + m__strFkName + '"'
            + ", \"sourceTableName\": \"" + m__strSourceTableName + '"'
            + ", \"attributes\": [" + Arrays.toString(m__lAttributes.toArray(new Attribute[m__lAttributes.size()])) + ']'
            + ", \"targetTableName\": \"" + m__strTargetTableName + "\" }";
    }
}
