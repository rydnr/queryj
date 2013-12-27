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
 * Filename: CachingRowDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating 'Row'
 *              instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
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
 * Adds a simple caching mechanism while decorating <code>Row</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingRowDecorator
    extends  AbstractRowDecorator
{
    private static final long serialVersionUID = 7506582704616898824L;

    /**
     * The cached decorated attributes.
     */
    private List<Attribute<DecoratedString>> m__lCachedAttributes;

    /**
     * Creates a <code>CachingRowDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param row the row.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    @SuppressWarnings("unused")
    public CachingRowDecorator(
        @NotNull final Row<String> row,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(row, metadataManager, decoratorFactory);
    }

    /**
     * Creates a <code>CachingRowDecorator</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    public CachingRowDecorator(
        @NotNull final String name,
        @NotNull final String tableName,
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(name, tableName, attributes, metadataManager, decoratorFactory);
    }


    /**
     * Specifies the cached list of decorated attributes.
     * @param list the list.
     */
    protected final void immutableSetCachedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        m__lCachedAttributes = list;
    }

    /**
     * Specifies the cached list of decorated attributes.
     * @param list the list.
     */
    protected void setCachedAttributes(@NotNull final List<Attribute<DecoratedString>> list)
    {
        immutableSetCachedAttributes(list);
    }

    /**
     * Retrieves the cached list of decorated attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute<DecoratedString>> getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Attribute<DecoratedString>> getAttributes()
    {
        List<Attribute<DecoratedString>> result = getCachedAttributes();

        if (result == null)
        {
            result = super.getAttributes();
            setCachedAttributes(result);
        }

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + CachingRowDecorator.class.getName() + '"'
            + ", \"cachedAttributes\": \"" + m__lCachedAttributes + "\" }";
    }
}
