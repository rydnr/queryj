/*
                        QueryJ Core

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
 * Filename: ListDecorator.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Defines the API for lists used in templates.
 *
 * Date: 12/29/13
 * Time: 12:06 PM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Defines the API for lists used in templates.
 * @param <T> the item type.
 * @author <a href="queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 29/12/2013
 */
public interface ListDecorator<T>
    extends List<T>
{
    /**
     * Retrieves the list of items.
     * @return such items.
     */
    @NotNull
    List<T> getItems();

    /**
     * Adds another set of items.
     * @return a partial decorator expecting a new set, and would then represent
     * the union of both list.
     */
    @NotNull
    @SuppressWarnings("unused")
    PartialListDecorator getPlus();

    /**
     * Removes given items from the list.
     * @return a partial decorator expecting a new set, and would then represent
     * the items not found in given list.
     */
    @NotNull
    @SuppressWarnings("unused")
    PartialListDecorator getMinus();

    /**
     * Retains certain items from the list.
     * @return a partial decorator expecting a new set, and would then represent
     * the items retained in given list.
     */
    @NotNull
    @SuppressWarnings("unused")
    PartialListDecorator getOnly();

    /**
     * Removes duplicates from the list.
     * @return the list, with no duplicates.
     */
    @NotNull
    @SuppressWarnings("unused")
    ListDecorator<T> getDifferent();

    /**
     * The "invalid operation" message.
     */
    @NotNull
    String INVALID_OPERATION = "invalid operation";
}
