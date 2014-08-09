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

 ******************************************************************************
 *
 * Filename: DecoratorFactory.java
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
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Abstract factory for template-specific decorators.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 */
public interface DecoratorFactory
    extends  Factory
{
    /**
     * Creates an {@link AttributeDecorator} for given
     * attribute instance.
     * @param attribute the {@link Attribute attribute}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated attribute for the concrete template.
     * @param <V> the value type.
     */
    @NotNull
    public <V> Attribute<DecoratedString> createDecorator(
        @NotNull final Attribute<V> attribute, @NotNull final MetadataManager metadataManager);

    /**
     * Creates an {@link ParameterDecorator} for given
     * parameter instance.
     * @param parameter the {@link Parameter parameter}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated attribute for the concrete template.
     */
    @SuppressWarnings("unused")
    @NotNull
    public ParameterDecorator createDecorator(
        @NotNull final Parameter<String, ?> parameter, @NotNull final MetadataManager metadataManager);

    /**
     * Creates a {@link PropertyDecorator} for given
     * property instance.
     * @param <V> the result type.
     * @param property the {@link Property property}.
     * @param result the {@link Result result}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated property for the concrete template.
     * @param <V> the type.
     */
    @NotNull
    public <V> PropertyDecorator createDecorator(
        @NotNull final Property<String> property,
        @NotNull final Result<V> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager);

    /**
     * Creates a {@link ResultDecorator} for given
     * result instance.
     * @param result the {@link Result custom result}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated result for the concrete template.
     */
    @SuppressWarnings("unused")
    @NotNull
    public ResultDecorator<DecoratedString> createDecorator(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager);

    /**
     * Creates a {@link SqlDecorator}.
     * @param sql the {@link Sql custom sql}.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated sql for the concrete template.
     */
    @NotNull
    public SqlDecorator createDecorator(
        @NotNull final Sql<String> sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager);

    /**
     * Creates a {@link TableDecorator}.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated table for the concrete template.
     */
    @Nullable
    public TableDecorator createTableDecorator(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider);

    /**
     * Retrieves the decorated list of {@link Attribute attributes} of given table.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the attribute list
     */
    @NotNull
    public List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final String table, @NotNull final MetadataManager metadataManager);

    /**
     * Retrieves the decorated list of {@link Attribute attributes}.
     * @param attributes the {@link Attribute attributes}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated version of the attribute list.
     * @param <V> the type.
     */
    @NotNull
    public <V> List<Attribute<DecoratedString>> decorateAttributes(
        @NotNull final List<Attribute<V>> attributes, @NotNull final MetadataManager metadataManager);

    /**
     * Retrieves the decorated list of {@link Attribute attributes} of given table.
     * @param table the table.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the attribute list.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Attribute<DecoratedString>> decoratePrimaryKey(
        @NotNull final String table, @NotNull final MetadataManager metadataManager);

    /**
     * Creates a {@link ForeignKeyDecorator}.
     * @param sourceTableName the name of the source table.
     * @param attributes the foreign key attributes.
     * @param targetTableName the name of the target table.
     * @param allowsNull whether the fk can be null as a whole.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorator instance.
     */
    @SuppressWarnings("unused")
    @NotNull
    public ForeignKeyDecorator createDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider);
}
