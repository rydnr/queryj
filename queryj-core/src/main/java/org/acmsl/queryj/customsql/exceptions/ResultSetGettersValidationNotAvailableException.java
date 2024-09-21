/*
                        queryj

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
 * Filename: ResultSetGettersValidationNotAvailableException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents the error when trying to retrieve the validation
 *              outcome (just the one involving using the getters on the
 *              ResultSet) for a Sql not validated yet.
 *
 * Date: 2014/03/16
 * Time: 15:20
 *
 */
package org.acmsl.queryj.customsql.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when trying to retrieve the validation outcome (just the
 * one involving using the getters on the {@link java.sql.ResultSet}) for a
 * {@link Sql} not validated yet.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 15:20
 */
@ThreadSafe
public class ResultSetGettersValidationNotAvailableException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3837851587789458404L;

    /**
     * Creates a new instance for given {@link Sql}.
     * @param sql the SQL.
     */
    public ResultSetGettersValidationNotAvailableException(@NotNull final Sql<String> sql)
    {
        super("ResultSetGetters.validation.not.available", new String[] { sql.getId() });
    }
}
