//;-*- mode: java -*-
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
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.RowDecorator;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Adds a simple caching mechanism while decorating <code>Row</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CachingRowDecorator
    extends  AbstractRowDecorator
{
    /**
     * The cached normalized uppercased name.
     */
    private String m__strCachedNameNormalizedUppercased;

    /**
     * Creates a <code>CachingRowDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param row the row.
     * @param metadataManager the metadata manager.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    public CachingRowDecorator(
        @NotNull final Row row, @NotNull final MetadataManager metadataManager)
    {
        super(row, metadataManager);
    }

    /**
     * Creates a <code>CachingRowDecorator</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @param metadataTypeManager the metadata type manager.
     * @precondition name != null
     * @precondition tableName != null
     * @precondition attributes != null
     * @precondition metadataTypeManager != null
     */
    public CachingRowDecorator(
        final String name,
        final String tableName,
        final Collection attributes,
        final MetadataTypeManager metadataTypeManager)
    {
        super(name, tableName, attributes, metadataTypeManager);
    }

    /**
     * Specifies the cached normalized uppercased name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameNormalizedUppercased(
        final String value)
    {
        m__strCachedNameNormalizedUppercased = value;
    }
    
    /**
     * Specifies the cached normalized uppercased name.
     * @param value the value to cache.
     */
    protected void setCachedNameNormalizedUppercased(final String value)
    {
        immutableSetCachedNameNormalizedUppercased(value);
    }

    /**
     * Retrieves the cached normalized uppercased name.
     * @return such value.
     */
    public String getCachedNameNormalizedUppercased()
    {
        return m__strCachedNameNormalizedUppercased;
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
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
}
