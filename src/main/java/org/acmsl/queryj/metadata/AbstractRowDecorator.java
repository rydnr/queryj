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
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.acmsl.queryj.metadata.vo.AbstractRow;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Row;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.Collection;
import java.util.List;

/**
 * Decorates <code>Row</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractRowDecorator
    extends AbstractRow
{
    /**
     * The decorated row.
     */
    private Row m__Row;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * Creates an <code>AbstractRowDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param row the row.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    public AbstractRowDecorator(
        @NotNull final Row row,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        this(
            row.getName(),
            row.getTableName(),
            row.getAttributes(),
            metadataManager,
            decoratorFactory);

        immutableSetRow(row);
    }

    /**
     * Creates an <code>AbstractRowDecorator</code> with the following
     * information.
     * @param name the name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    protected AbstractRowDecorator(
        @NotNull final String name,
        @NotNull final String tableName,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(name, tableName, attributes);

        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(metadataManager.getMetadataTypeManager());
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Specifies the row to decorate.
     * @param row the row.
     */
    protected final void immutableSetRow(@NotNull final Row row)
    {
        m__Row = row;
    }
    
    /**
     * Specifies the row to decorate.
     * @param row the row.
     */
    @SuppressWarnings("unused")
    protected void setRow(@NotNull final Row row)
    {
        immutableSetRow(row);
    }

    /**
     * Retrieves the decorated row.
     * @return such row.
     */
    @NotNull
    public Row getRow()
    {
        return m__Row;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
    }

    /**
     * Specifies the decorator factory to use.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    protected final void immutableSetDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }

    /**
     * Specifies the decorator factory to use.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     */
    @SuppressWarnings("unused")
    protected void setDecoratorFactory(@NotNull final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Retrieves the decorator factory to use.
     * @return the {@link DecoratorFactory} instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Retrieves the attributes.
     * @return such attributes.
     */
    @Override
    @NotNull
    public List<Attribute> getAttributes()
    {
        return getAttributes(getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Retrieves the attributes.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute> getAttributes(
        @NotNull final MetadataManager metadataManager, @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(super.getAttributes(), metadataManager);
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    @NotNull
    public String getNameNormalizedUppercased()
    {
        return normalizeUppercase(getName(), DecorationUtils.getInstance());
    }
    
    /**
     * Normalizes given value, in upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     */
    @NotNull
    protected String normalizeUppercase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.softNormalizeUppercase(value);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return toString(getRow());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param row the decorated row.
     * @return such information.
     */
    @NotNull
    protected String toString(@NotNull final Row row)
    {
        return "" + row;
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getRow());
    }

    /**
     * Retrieves the hash code associated to given row.
     * @param row the row.
     * @return such information.
     */
    protected int hashCode(@NotNull final Row row)
    {
        return row.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if (object instanceof Row)
        {
            result = equals(getRow(), object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param row the row.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @precondition row != null
     */
    protected boolean equals(@NotNull final Row row, @NotNull final Object object)
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
    @Override
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
     */
    @SuppressWarnings("unchecked")
    protected int compareTo(@NotNull final Row row, final Object object)
        throws  ClassCastException
    {
        return row.compareTo(object);
    }
}
