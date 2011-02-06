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
 * Filename: AbstractRowDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'Row' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractRow;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.acmsl.queryj.tools.metadata.DecorationUtils;

/*
 * Importing JDK classes.
 */
import java.util.Collection;

/**
 * Decorates <code>Row</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractRowDecorator
    extends AbstractRow
    implements java.lang.Comparable
{
    /**
     * The decorated row.
     */
    private Row m__Row;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates an <code>AbstractRowDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param row the row.
     * @param metadataManager the metadata manager.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    public AbstractRowDecorator(
        final Row row, final MetadataManager metadataManager)
    {
        this(
            row.getName(),
            row.getTableName(),
            row.getAttributes(),
            metadataManager.getMetadataTypeManager());

        immutableSetRow(row);
    }

    /**
     * Creates an <code>AbstractRowDecorator</code> with the following
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
    protected AbstractRowDecorator(
        final String name,
        final String tableName,
        final Collection attributes,
        final MetadataTypeManager metadataTypeManager)
    {
        super(name, tableName, attributes);

        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the row to decorate.
     * @param row the row.
     */
    protected final void immutableSetRow(final Row row)
    {
        m__Row = row;
    }
    
    /**
     * Specifies the row to decorate.
     * @param row the row.
     */
    protected void setRow(final Row row)
    {
        immutableSetRow(row);
    }

    /**
     * Retrieves the decorated row.
     * @return such row.
     */
    public Row getRow()
    {
        return m__Row;
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
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    public String getNameNormalizedUppercased()
    {
        return normalizeUppercase(getName(), DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value, in upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeUppercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.softNormalizeUppercase(value);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    public String toString()
    {
        return toString(getRow());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param row the decorated row.
     * @return such information.
     * @precondition row != null
     */
    protected String toString(final Row row)
    {
        return "" + row;
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getRow());
    }

    /**
     * Retrieves the hash code associated to given row.
     * @param row the row.
     * @return such information.
     * @precondition row != null
     */
    protected int hashCode(final Row row)
    {
        return row.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        return equals(getRow(), object);
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param row the row.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @precondition row != null
     */
    protected boolean equals(final Row row, final Object object)
    {
        return row.equals(object);
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
        return compareTo(getRow(), object);
    }

    /**
     * Compares given object with given instance.
     * @param row the decorated row.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     * @precondition row != null
     */
    protected int compareTo(final Row row, final Object object)
        throws  ClassCastException
    {
        return row.compareTo(object);
    }
}
