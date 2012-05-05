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
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Parameter;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

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
import java.util.List;

/**
 * Abstract factory for template-specific decorators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CachingDecoratorFactory
    implements  DecoratorFactory,
                Serializable,
                Singleton
{
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
     */
    @NotNull
    public AttributeDecorator createDecorator(
        final Attribute attribute, final MetadataManager metadataManager)
    {
        return new CachingAttributeDecorator(attribute, metadataManager);
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the property.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    @NotNull
    public PropertyDecorator createDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
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
    public ResultDecorator createDecorator(
        @NotNull final Result result,
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
    public SqlDecorator createDecorator(
        @NotNull final Sql sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return
            new CachingSqlDecorator(
                sql, customSqlProvider, metadataManager);
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
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
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
    public boolean equals(final Object object)
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
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
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

    /**
     * Creates an <code>ParameterDecorator</code> for given
     * parameter instance.
     *
     * @param parameter       the parameter.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated attribute for the concrete template.
     */
    public ParameterDecorator createDecorator(final Parameter parameter, final MetadataManager metadataManager)
    {
        // TODO
        return new CachingParameterDecorator(parameter, metadataManager.getMetadataTypeManager());
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
    public PropertyDecorator createDecorator(final Property property, final Result result, final CustomSqlProvider
        customSqlProvider, final MetadataManager metadataManager)
    {
        // TODO
        return new CachingPropertyDecorator(property, metadataManager);
    }

    /**
     * Creates a <code>TableDecorator</code>.
     *
     * @param table           the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated table for the concrete template.
     */
    public TableDecorator createTableDecorator(final String table, final MetadataManager metadataManager)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves the decorated list of attributes of given table.
     *
     * @param table           the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the attribute list
     */
    public List decorateAttributes(final String table, final MetadataManager metadataManager)
    {
        // TODO
        return null;
    }

    /**
     * Retrieves the decorated list of attributes of given table.
     *
     * @param table           the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the attribute list
     */
    public List decoratePrimaryKey(final String table, final MetadataManager metadataManager)
    {
        // TODO
        return null;
    }

    /**
     * Creates a <code>ForeignKeyDecorator</code>.
     *
     * @param sourceTableName the name of the source table.
     * @param attributes      the foreign key attributes.
     * @param targetTableName the name of the target table.
     * @param allowsNull      whether the fk can be null as a whole.
     * @return the decorator instance.
     */
    @NotNull
    public ForeignKeyDecorator createDecorator(final String sourceTableName, final List attributes, final String
        targetTableName, final boolean allowsNull)
    {
        return new ForeignKeyDecorator(sourceTableName, attributes, targetTableName, allowsNull);
    }
}
