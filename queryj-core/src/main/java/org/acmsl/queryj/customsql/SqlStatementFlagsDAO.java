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
 * Filename: SqlStatementFlagsDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: DAO for StatementFlags.
 *
 * Date: 7/6/12
 * Time: 4:55 PM
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * DAO for {@link StatementFlags}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/06
 */
public interface SqlStatementFlagsDAO
{
    /**
     * Finds the {@link java.sql.Statement} flags for given primary key operation.
     * @return such information.
     */
    @Nullable
    List<StatementFlags> findByPrimaryKeyOperation();
}
