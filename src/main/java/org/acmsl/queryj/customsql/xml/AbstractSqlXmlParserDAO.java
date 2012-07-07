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
 * Filename: AbstractSqlXmlParserDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Generic class for SqlXmlParser-based DAOs.
 *
 * Date: 7/6/12
 * Time: 5:01 PM
 *
 */
package org.acmsl.queryj.customsql.xml;

import org.acmsl.queryj.customsql.IdentifiableElement;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.customsql.Sql;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Generic class for {@link SqlXmlParser}-based DAOs.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/06
 */
public abstract class AbstractSqlXmlParserDAO
{
    /**
     * The {@link SqlXmlParser} instance.
     */
    private SqlXmlParser parser;

    /**
     * Creates a new {@link AbstractSqlXmlParserDAO} with given {@link SqlXmlParser parser}.
     * @param parser the parser.
     */
    protected AbstractSqlXmlParserDAO(@NotNull final SqlXmlParser parser)
    {
        immutableSetSqlXmlParser(parser);
    }

    /**
     * Specifies the {@link SqlXmlParser} instance.
     * @param xmlParser such instance.
     */
    protected final void immutableSetSqlXmlParser(@NotNull final SqlXmlParser xmlParser)
    {
        this.parser = xmlParser;
    }

    /**
     * Specifies the {@link SqlXmlParser} instance.
     * @param xmlParser such instance.
     */
    @SuppressWarnings("unused")
    protected void setSqlXmlParser(@NotNull final SqlXmlParser xmlParser)
    {
        immutableSetSqlXmlParser(xmlParser);
    }

    /**
     * Retrieves the {@link SqlXmlParser} instance.
     * @return such instance.
     */
    @NotNull
    public SqlXmlParser getSqlXmlParser()
    {
        return this.parser;
    }

    /**
     * Retrieves the element matching given id.
     * @param id the id.
     * @return the element, if any.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected <T> T findById(@NotNull final String id, @NotNull final Class<T> type)
    {
        @Nullable T result = null;

        for (@Nullable IdentifiableElement t_Item : parser.getCollection())
        {
            if  (   (t_Item != null)
                 && (type.isAssignableFrom(t_Item.getClass()))
                 && (t_Item.getId().equals(id)))
            {
                result = (T) t_Item;
                break;
            }
        }

        return result;

    }

    /**
     * Retrieves all elements of a given type.
     * @param parser the parser.
     * @return such list.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <T> List<T> findAll(@NotNull final SqlXmlParser parser, @NotNull final Class<T> type)
    {
        @NotNull List<T> result = new ArrayList<T>();

        for (@Nullable Object t_Item : parser.getCollection())
        {
            if  (   (t_Item != null)
                 && (type.isAssignableFrom(t_Item.getClass())))
            {
                result.add((T) t_Item);
            }
        }

        return result;
    }

    /**
     * Filters given CustomSqlProvider contents according to given class name
     * and filter (optional).
     * @param contents such contents.
     * @param itemClass the class to filter.
     * @param idFilter the id filter (optional).
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <I extends IdentifiableElement> List<I> filterItems(
        @NotNull final List<? extends IdentifiableElement> contents,
        @NotNull final Class<I> itemClass,
        @Nullable final String idFilter)
    {
        @NotNull List<I> result = new ArrayList<I>(contents.size());

        for (final IdentifiableElement t_CurrentItem : contents)
        {
            if (   (t_CurrentItem != null)
                && (itemClass.isAssignableFrom(t_CurrentItem.getClass())))
            {
                if (   (idFilter == null)
                    || (filterById(t_CurrentItem, idFilter)))
                {
                    result.add((I) t_CurrentItem);
                }
            }
        }

        return result;
    }

    /**
     * Filters given element by its id.
     * @param element the element.
     * @param idFilter the filter.
     * @return <code>true</code> if both identifiers match.
     * @precondition element != null
     * @precondition idFilters != null
     */
    protected boolean filterById(
        @NotNull final IdentifiableElement element, @NotNull final String idFilter)
    {
        return idFilter.equalsIgnoreCase(element.getId());
    }
}
