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
     * Retrieves the complete collection of items.
     * @param parser the {@link SqlXmlParser} implementation.
     * @return such collection.
     */
    @NotNull
    protected List<IdentifiableElement<String>> getCollection(@NotNull final SqlXmlParser parser)
    {
        @NotNull final List<Sql<String>> queries = parser.getQueries();

        @NotNull final List<IdentifiableElement<String>> result =
            new ArrayList<>(toIdentifiable(queries));

        result.addAll(parser.getResults());
        result.addAll(parser.getProperties());
        result.addAll(parser.getPropertyRefs());
        result.addAll(parser.getParameters());
        result.addAll(parser.getParameterRefs());

        return result;
    }

    /**
     * Converts given list to its base type.
     * @param queries the queries.
     * @return the base list.
     */
    @NotNull
    protected List<IdentifiableElement<String>> toIdentifiable(@NotNull final List<Sql<String>> queries)
    {
        @NotNull final List<IdentifiableElement<String>> result = new ArrayList<>(queries.size());

        for (@Nullable final Sql<String> query : queries)
        {
            if (query != null)
            {
                result.add(query);
            }
        }

        return result;
    }

    /**
     * Retrieves the element matching given id.
     * @param id the id.
     * @param type the type.
     * @param collection the collection.
     * @return the element, if any.
     * @param <T> the element type.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected <T extends IdentifiableElement<String>> T findById(
        @NotNull final String id,
        @NotNull final Class<?> type,
        @NotNull final List<T> collection)
    {
        @Nullable T result = null;

        for (@Nullable final T t_Item : collection)
        {
            if  (   (t_Item != null)
                 && (type.isAssignableFrom(t_Item.getClass()))
                 && (t_Item.getId().equals(id)))
            {
                result = t_Item;
                break;
            }
        }

        return result;

    }

    /**
     * Retrieves all elements of a given type.
     * @param parser the parser.
     * @param type the type.
     * @return such list.
     * @param <T> the element type.
     */
    @NotNull
    protected <T> List<T> findAll(@NotNull final SqlXmlParser parser, @NotNull final Class<?> type)
    {
        return findAll(type, getCollection(parser));
    }

    /**
     * Retrieves all elements of a given type.
     * @param type the type.
     * @param collection the collection.
     * @return such list.
     * @param <T> the element type.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <T> List<T> findAll(
        @NotNull final Class<?> type,
        @NotNull final List<IdentifiableElement<String>> collection)
    {

        @NotNull final List<T> result = new ArrayList<>();

        for (@Nullable final IdentifiableElement<String> t_Item : collection)
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
     * @return the filtered items.
     * @param <I> the element type.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <I extends IdentifiableElement<String>> List<I> filterItems(
        @NotNull final List<? extends IdentifiableElement<String>> contents,
        @NotNull final Class<?> itemClass,
        @Nullable final String idFilter)
    {
        @NotNull final List<I> result = new ArrayList<>(contents.size());

        for (@Nullable final IdentifiableElement<String> t_CurrentItem : contents)
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
     */
    protected boolean filterById(
        @NotNull final IdentifiableElement<String> element, @NotNull final String idFilter)
    {
        return idFilter.equalsIgnoreCase(element.getId());
    }

    @Override
    public String toString()
    {
        return "AbstractSqlXmlParserDAO{" +
               "parser=" + parser +
               '}';
    }
}
