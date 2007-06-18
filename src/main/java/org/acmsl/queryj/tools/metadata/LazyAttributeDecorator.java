//;-*- mode: java -*-
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: AbstractAttributeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'Attribute' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractAttribute;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/**
 * Decorates <code>Attribute</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class LazyAttributeDecorator
    extends  AbstractAttribute
    implements  AttributeDecorator
{
    /**
     * The cached type.
     */
    private Integer m__CachedType;

    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates a <code>LazyAttributeDecorator</code> with some minimal information.
     * @param tableName the table name.
     * @param name the attribute name.
     * @param metadataManager the metadata manager.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    public LazyAttributeDecorator(
        final String tableName,
        final String name,
        final MetadataManager metadataManager)
    {
        super(tableName, name);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected void setMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    protected final void immutableSetCachedType(final Integer type)
    {
        m__CachedType = type;
    }

    /**
     * Specifies the cached type.
     * @param type the type.
     */
    protected void setCachedType(final Integer type)
    {
        immutableSetCachedType(type);
    }

    /**
     * Retrieves the cached type.
     * @return such information.
     */
    protected Integer getCachedType()
    {
        return m__CachedType;
    }

    /**
     * Retrieves the attribute type.
     * @return its type.
     */
    public int getType()
    {
        Integer result = getCachedType();

        if  (result == null)
        {
            result =
                new Integer(
                    retrieveType(
                        getTableName(),
                        getName(),
                        getMetadataManager()));

            setCachedType(result);
        }

        return result.intValue();
    }

    /**
     * Retrieves the type.
     * @param tableName the table name.
     * @param name the name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition tableName != null
     * @precondition name != null
     * @precondition metadataManager != null
     */
    protected int retrieveType(
        final String tableName,
        final String name,
        final MetadataManager metadataManager)
    {
        return metadataManager.getColumnType(tableName, name);
    }
}
