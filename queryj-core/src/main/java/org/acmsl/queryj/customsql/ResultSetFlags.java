package org.acmsl.queryj.customsql;/*
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
 * Filename: ResultSetFlags.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 7/6/12
 * Time: 5:42 PM
 *
 */

import org.jetbrains.annotations.Nullable;

public interface ResultSetFlags
    extends IdentifiableElement
{
    /**
     * The <b>TYPE_FORWARD_ONLY</b> value for
     * <i>type</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String TYPE_FORWARD_ONLY = "TYPE_FORWARD_ONLY";

    /**
     * The <b>TYPE_SCROLL_INSENSITIVE</b> value for
     * <i>type</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String TYPE_SCROLL_INSENSITIVE = "TYPE_SCROLL_INSENSITIVE";

    /**
     * The <b>TYPE_SCROLL_SENSITIVE</b> value for
     * <i>type</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String TYPE_SCROLL_SENSITIVE = "TYPE_SCROLL_SENSITIVE";

    /**
     * The <b>CONCUR_READ_ONLY</b> value for
     * <i>concurrency</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String CONCUR_READ_ONLY = "CONCUR_READ_ONLY";

    /**
     * The <b>CONCUR_UPDATABLE</b> value for
     * <i>concurrency</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String CONCUR_UPDATABLE = "CONCUR_UPDATABLE";

    /**
     * The <b>HOLD_CURSORS_OVER_COMMIT</b> value for
     * <i>holdability</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String HOLD_CURSORS_OVER_COMMIT = "HOLD_CURSORS_OVER_COMMIT";

    /**
     * The <b>CLOSE_CURSORS_AT_COMMIT</b> value for
     * <i>holdability</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String CLOSE_CURSORS_AT_COMMIT = "CLOSE_CURSORS_AT_COMMIT";

    /**
     * Retrieves the type of result set flag.
     * @return such value.
     */
    @Nullable
    String getType();

    /**
     * Retrieves the concurrency flag.
     * @return such value.
     */
    @Nullable
    String getConcurrency();

    /**
     * Retrieves the <i>holdability</i> attribute.
     * @return such value.
     */
    @Nullable
    String getHoldability();
}
