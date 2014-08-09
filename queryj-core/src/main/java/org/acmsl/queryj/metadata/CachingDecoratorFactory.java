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
 * Importing QueryJ Core classes.
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
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <V> Attribute<DecoratedString> createDecorator(
        @NotNull final Attribute<V> attribute, @NotNull final MetadataManager metadataManager)
    {
        @NotNull final Attribute<DecoratedString> result;

        if (attribute.getName() instanceof String)
        {
            result = new CachingAttributeDecorator((Attribute<String>) attribute, metadataManager);
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ResultDecorator<DecoratedString> createDecorator(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return
            new CachingResultDecorator(
                result, customSqlProvider, metadataManager, this);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ParameterDecorator<?> createDecorator(
        @NotNull final Parameter<String, ?> parameter, @NotNull final MetadataManager metadataManager)
    {
        return new CachingParameterDecorator<>(parameter, metadataManager.getMetadataTypeManager());
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <V> PropertyDecorator createDecorator(
        @NotNull final Property<String> property,
        @NotNull final Result<V> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return new CachingPropertyDecorator(property, metadataManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public TableDecorator createTableDecorator(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return createTableDecorator(table, metadataManager, metadataManager.getTableDAO(), customSqlProvider);
    }

    /**
     * Creates a {@link TableDecorator}.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param tableDAO the {@link TableDAO} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated table for the concrete template.
     */
    @Nullable
    public TableDecorator createTableDecorator(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TableDAO tableDAO,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        @Nullable TableDecorator result = null;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            tableDAO.findByName(table);

        if (t_Table != null)
        {
            result = new CachingTableDecorator(t_Table, metadataManager, this, customSqlProvider);
        }

        return result;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * @throws ClassCastException if both objects are incompatible.
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
