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
 * Filename: TableListDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ListDecorators or PartialListDecorators associated to a table.
 *
 * Date: 2014/06/15
 * Time: 10:05
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link ListDecorator}s or {@link PartialListDecorator}s associated to a {@link TableDecorator table}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/15 10:05
 */
@ThreadSafe
public interface TableListDecorator
{
    /**
     * Retrieves the table decorator.
     * @return such {@link TableDecorator instance}.
     */
    @NotNull
    TableDecorator getTable();
}
