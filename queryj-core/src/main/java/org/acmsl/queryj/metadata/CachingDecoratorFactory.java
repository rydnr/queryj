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

 ******************************************************************************
 *
 * Filename: CachingDecoratorFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract factory for template-specific decorators.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Abstract factory for template-specific decorators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingDecoratorFactory
    implements  DecoratorFactory,
                Serializable,
                Singleton
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 8153087339399077188L;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CachingDecoratorFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CachingDecoratorFactory SINGLETON =
            new CachingDecoratorFactory();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CachingDecoratorFactory() {}

    /**
     * Retrieves a <code>CachingDecoratorFactory</code> instance.
     * @return such instance.
     */
    @NotNull
    public static CachingDecoratorFactory getInstance()
    {
        return CachingDecoratorFactorySingletonContainer.SINGLETON;
    }

    /**
     * Creates an <code>AttributeDecorator</code> for given
     * attribute instance.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated attribute for the concrete template.
     * @param <V> the value type.
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <V, DecoratedString> Attribute<DecoratedString> createDecorator(
        @NotNull final Attribute<V> attribute, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final Attribute<DecoratedString> result;

        if (attribute.getName() instanceof String)
        {
            result = (Attribute<DecoratedString>) new CachingAttributeDecorator((Attribute<String>) attribute, metadataManager);
        }
        else
        {
            result = (Attribute<DecoratedString>) attribute;
        }

        return result;
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the property.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    @SuppressWarnings("unused")
    @NotNull
    public PropertyDecorator createDecorator(
        @NotNull final Property<String> property, @NotNull final MetadataManager metadataManager)
    {
        return new CachingPropertyDecorator(property, metadataManager);
    }

    /**
     * Creates a <code>ResultDecorator</code> for given
     * result instance.
     * @param result the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated result for the concrete template.
     */
    @NotNull
    @Override
    public ResultDecorator createDecorator(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return
            new CachingResultDecorator(
                result, customSqlProvider, metadataManager, this);
    }

    /**
     * Creates a <code>SqlDecorator</code>.
     * @param sql the custom sql.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated sql for the concrete template.
     */
    @NotNull
    @Override
    public SqlDecorator createDecorator(
        @NotNull final Sql<String> sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return
            new CachingSqlDecorator(
                sql, customSqlProvider, metadataManager);
    }

    /**
     * Creates an <code>ParameterDecorator</code> for given
     * parameter instance.
     *
     * @param parameter       the parameter.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated attribute for the concrete template.
     */
    @NotNull
    @Override
    public ParameterDecorator<?> createDecorator(
        @NotNull final Parameter<String, ?> parameter, @NotNull final MetadataManager metadataManager)
    {
        return new CachingParameterDecorator<>(parameter, metadataManager.getMetadataTypeManager());
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     *
     * @param property          the property.
     * @param result            the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager   the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    @NotNull
    @Override
    public PropertyDecorator createDecorator(
        @NotNull final Property<String> property,
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return new CachingPropertyDecorator(property, metadataManager);
    }

    /**
     * Creates a <code>TableDecorator</code>.
     *
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated table for the concrete template.
     */
    @Override
    @Nullable
    public TableDecorator createTableDecorator(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @Nullable TableDecorator result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(table);

        if (t_Table != null)
        {
            result = new CachingTableDecorator(t_Table, metadataManager, this, customSqlProvider);
        }

        return result;
    }

    /**
     * Retrieves the decorated list of attributes.
     * @param attributes the attributes to decorate.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated version of the attribute list.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <V> List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final List<Attribute<V>> attributes, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Attribute<DecoratedString>> result = new ArrayList<>(attributes.size());

        for (@Nullable final Attribute<V> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                if (t_Attribute.getName() instanceof String)
                {
                    result.add(
                        new CachingAttributeDecorator((Attribute<String>) t_Attribute, metadataManager));
                }
                else
                {
                    result.add((Attribute<DecoratedString>) t_Attribute);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the decorated list of attributes of given table.
     * @param table the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the attribute list
     */
    @NotNull
    @Override
    public List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final String table, @NotNull final MetadataManager metadataManager)
    {
        @Nullable List<Attribute<DecoratedString>> result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(table);

        if (t_Table != null)
        {
            result = decorateAttributes(t_Table.getAttributes(), metadataManager);
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the decorated list of attributes composing the primary key of given table.
     * @param table the table.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the primary key.
     */
    @NotNull
    @Override
    public List<Attribute<DecoratedString>> decoratePrimaryKey(
        @NotNull final String table, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Attribute<DecoratedString>> result;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(table);

        if (t_Table != null)
        {
            @NotNull final List<Attribute<String>> t_lPrimaryKey = t_Table.getPrimaryKey();

            result = new ArrayList<>(t_lPrimaryKey.size());

            for (@Nullable final Attribute<String> t_Attribute : t_lPrimaryKey)
            {
                if (t_Attribute != null)
                {
                    result.add(
                        new CachingAttributeDecorator(t_Attribute, metadataManager));
                }
            }
        }
        else
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Creates a <code>ForeignKeyDecorator</code>.
     * @param sourceTableName the name of the source table.
     * @param attributes the foreign key attributes.
     * @param targetTableName the name of the target table.
     * @param allowsNull whether the fk can be null as a whole.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorator instance.
     */
    @NotNull
    @Override
    public ForeignKeyDecorator createDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return
            new CachingForeignKeyDecorator(
                sourceTableName,
                attributes,
                targetTableName,
                allowsNull,
                metadataManager,
                this,
                customSqlProvider);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006173, 772040777)
                .appendSuper(super.hashCode())
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

        if  (object instanceof DecoratorFactory)
        {
            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(object))
                .isEquals();
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * object prevents it from being compared to this Object.
     */
    public int compareTo(@Nullable final Object object)
        throws  ClassCastException
    {
        int result = 1;

        @Nullable ClassCastException exceptionToThrow = null;

        if  (object instanceof DecoratorFactory)
        {
            result = 0;
        }
        else
        {
            exceptionToThrow =
                new ClassCastException(
                      Literals.CANNOT_COMPARE
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
