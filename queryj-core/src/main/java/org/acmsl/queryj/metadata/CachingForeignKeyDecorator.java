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
 * Filename: CachingForeignKeyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating 'ForeignKey'
 * instances.
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
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adds a simple caching mechanism while decorating <code>ForeignKey</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
@ThreadSafe
public class CachingForeignKeyDecorator
    extends  AbstractForeignKeyDecorator
{

    private static final long serialVersionUID = -34906355516994108L;
    /**
     * The cached source table.
     */
    private Table m__CachedSource;

    /**
     * The cached target table.
     */
    private Table m__CachedTarget;

    /**
     * Creates a <code>CachingForeignKeyDecorator</code> with the
     * <code>ForeignKey</code> information to decorate.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    @SuppressWarnings("unused")
    public CachingForeignKeyDecorator(
        @NotNull final ForeignKey foreignKey,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        super(foreignKey, metadataManager, decoratorFactory, customSqlProvider);
    }

    /**
     * Creates a <code>CachingForeignKeyDecorator</code> with the following
     * information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key allows null values.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} implementation.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     */
    @SuppressWarnings("unused")
     protected CachingForeignKeyDecorator(
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

    /**
     * Specifies the cached source table.
     * @param source such table.
     */
    protected final void immutableSetCachedSource(@NotNull final Table source)
    {
        m__CachedSource = source;
    }

    /**
     * Specifies the cached source table.
     * @param source such table.
     */
    protected void setCachedSource(@NotNull final Table source)
    {
        immutableSetCachedSource(source);
    }

    /**
     * Retrieves the cached source table.
     * @return such table.
     */
    @Nullable
    protected Table getCachedSource()
    {
        return m__CachedSource;
    }

    /**
     * Retrieves the source table.
     *
     * @return such information.
     */
    @Override
    public Table getSource()
    {
        @Nullable Table result = getCachedSource();

        if (result == null)
        {
            result = super.getSource();

            if (result != null)
            {
                setCachedSource(result);
            }
        }

        return result;
    }


    /**
     * Specifies the cached target table.
     * @param target such table.
     */
    protected final void immutableSetCachedTarget(@NotNull final Table target)
    {
        m__CachedTarget = target;
    }

    /**
     * Specifies the cached target table.
     * @param target such table.
     */
    protected void setCachedTarget(@NotNull final Table target)
    {
        immutableSetCachedTarget(target);
    }

    /**
     * Retrieves the cached target table.
     * @return such table.
     */
    @Nullable
    protected Table getCachedTarget()
    {
        return m__CachedTarget;
    }

    /**
     * Retrieves the target table.
     *
     * @return such information.
     */
    @Override
    public Table getTarget()
    {
        @Nullable Table result = getCachedTarget();

        if (result == null)
        {
            result = super.getTarget();

            if (result != null)
            {
                setCachedTarget(result);
            }
        }

        return result;
    }
}
