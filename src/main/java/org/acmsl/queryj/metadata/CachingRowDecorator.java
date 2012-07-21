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
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
     * The cached normalized uppercased name.
     */
    private String m__strCachedNameNormalizedUppercased;

    /**
     * The cached lowercased version of the name.
     */
    private String m__strCachedNameLowercased;

    /**
     * The cached capitalized version of the name.
     */
    private String m__strCachedNameCapitalized;

    /**
     * The cached decorated attributes.
     */
    private List<Attribute> m__lCachedAttributes;

    /**
     * Creates a <code>CachingRowDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param row the row.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    @SuppressWarnings("unused")
    public CachingRowDecorator(
        @NotNull final Row row,
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
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(name, tableName, attributes, metadataManager, decoratorFactory);
    }

    /**
     * Specifies the cached normalized uppercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalizedUppercased(
        @NotNull final String value)
    {
        m__strCachedNameNormalizedUppercased = value;
    }
    
    /**
     * Specifies the cached normalized uppercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalizedUppercased(@NotNull final String value)
    {
        immutableSetCachedNameNormalizedUppercased(value);
    }

    /**
     * Retrieves the cached normalized uppercased name.
     * @return such value.
     */
    @Nullable
    public String getCachedNameNormalizedUppercased()
    {
        return m__strCachedNameNormalizedUppercased;
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    @NotNull
    public String getNameNormalizedUppercased()
    {
        String result = getCachedNameNormalizedUppercased();
        
        if  (result == null)
        {
            result = super.getNameNormalizedUppercased();
            setCachedNameNormalizedUppercased(result);
        }
        
        return result;
    }

    /**
     * Specifies the cached lowercased version of the name.
     * @param value the value.
     */
    protected final void immutableSetCachedNameLowercased(@NotNull final String value)
    {
        m__strCachedNameLowercased = value;
    }

    /**
     * Specifies the cached lowercased version of the name.
     * @param value the value.
     */
    @SuppressWarnings("unused")
    protected void setCachedNameLowercased(@NotNull final String value)
    {
        immutableSetCachedNameLowercased(value);
    }

    /**
     * Retrieves the cached lowercased version of the name.
     * @return such value.
     */
    @NotNull
    public String getCachedNameLowercased()
    {
        return m__strCachedNameLowercased;
    }

    /**
     * Retrieves the lowercased version of the name.
     * @return such value.
     */
    @NotNull
    public String getNameLowercased()
    {
        @Nullable String result = getCachedNameLowercased();

        if (result == null)
        {
            result = super.getNameLowercased();
            setCachedNameLowercased(result);
        }

        return result;
    }

    /**
     * Specifies the cached capitalized version of the name.
     * @param value the value.
     */
    protected final void immutableSetCachedNameCapitalized(@NotNull final String value)
    {
        m__strCachedNameCapitalized = value;
    }

    /**
     * Specifies the cached capitalized version of the name.
     * @param value the value.
     */
    @SuppressWarnings("unused")
    protected void setCachedNameCapitalized(@NotNull final String value)
    {
        immutableSetCachedNameCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized version of the name.
     * @return the value.
     */
    @NotNull
    protected String getCachedNameCapitalized()
    {
        return m__strCachedNameCapitalized;
    }

    /**
     * Retrieves the name, capitalized.
     * @return such value.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getNameCapitalized()
    {
        @Nullable String result = getCachedNameCapitalized();

        if (result == null)
        {
            result = super.getNameCapitalized();
            setCachedNameCapitalized(result);
        }

        return result;
    }
    /**
     * Specifies the cached list of decorated attributes.
     * @param list the list.
     */
    protected final void immutableSetCachedAttributes(@NotNull final List<Attribute> list)
    {
        m__lCachedAttributes = list;
    }

    /**
     * Specifies the cached list of decorated attributes.
     * @param list the list.
     */
    protected void setCachedAttributes(@NotNull final List<Attribute> list)
    {
        immutableSetCachedAttributes(list);
    }

    /**
     * Retrieves the cached list of decorated attributes.
     * @return such list.
     */
    @Nullable
    protected List<Attribute> getCachedAttributes()
    {
        return m__lCachedAttributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public List<Attribute> getAttributes()
    {
        List<Attribute> result = getCachedAttributes();

        if (result == null)
        {
            result = super.getAttributes();
            setCachedAttributes(result);
        }

        return result;
    }
}
