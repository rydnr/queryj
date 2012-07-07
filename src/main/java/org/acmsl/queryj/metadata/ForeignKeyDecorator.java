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
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: ForeignKeyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'ForeignKey' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some ACM-SL Commons classes.
 */

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Decorates <code>ForeignKey</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ForeignKeyDecorator
    extends AbstractForeignKeyDecorator
{
    /**
     * Creates a <code>ForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    @SuppressWarnings("unused")
    public ForeignKeyDecorator(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        this(
            foreignKey.getSourceTableName(),
            foreignKey.getAttributes(),
            foreignKey.getTargetTableName(),
            foreignKey.isNullable(),
            metadataManager,
            decoratorFactory,
            customSqlProvider);
    }

    /**
     * Creates a <code>ForeignKeyDecorator</code> with the following
     * information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    public ForeignKeyDecorator(
        @NotNull final String sourceTableName,
        @NotNull final List<Attribute> attributes,
        @NotNull final String targetTableName,
        final boolean allowsNull,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(
            sourceTableName,
            attributes,
            targetTableName,
            allowsNull,
            metadataManager,
            decoratorFactory,
            customSqlProvider);
    }
}
