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
 * Filename: CustomResultWithInvalidNumberOfColumnsException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is triggered when the custom Result has fewer properties
 * declared than the actual columns retrieved.
 *
 * Date: 6/12/13
 * Time: 10:11 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is triggered when the custom {@link org.acmsl.queryj.customsql.Result} has
 * fewer properties declared than the actual columns retrieved.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/12
 */
@ThreadSafe
public class CustomResultWithInvalidNumberOfColumnsException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 136368637865054986L;

    /**
     * Builds a QueryJ build exception with a certain message.
     * @param columnCount the number of columns.
     * @param propertyCount the number of properties.
     */
    public CustomResultWithInvalidNumberOfColumnsException(
        final int columnCount, final int propertyCount)
    {
        super("invalid.number.of.columns.in.custom-result", new Object[] { columnCount, propertyCount });
    }
}
