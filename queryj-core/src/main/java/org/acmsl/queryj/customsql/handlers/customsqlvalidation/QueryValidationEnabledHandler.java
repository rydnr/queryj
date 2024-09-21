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
 * Filename: QueryValidationEnabledHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Checks whether the current SQL has "validation" flag enabled.
 *
 * Date: 2014/03/15
 * Time: 07:06
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Checks whether the current SQL has "validation" flag enabled.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 07:06
 */
@ThreadSafe
public class QueryValidationEnabledHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        final boolean result;

        @Nullable final Sql<String> sql = retrieveCurrentSql(command);

        if (sql != null)
        {
            result = !sql.isValidate();
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Retrieves the current SQL.
     * @param command the command.
     * @return the {@link Sql}.
     */
    @Nullable
    protected Sql<String> retrieveCurrentSql(final QueryJCommand command)
    {
        return new QueryJCommandWrapper<Sql<String>>(command).getSetting(RetrieveQueryHandler.CURRENT_SQL);
    }
}
