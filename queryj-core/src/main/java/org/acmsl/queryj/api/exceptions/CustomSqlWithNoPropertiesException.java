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
 * Filename: CustomSqlWithNoPropertiesException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents the situation in which a custom {@link Sql} cannot
 *              be validated since it has no explicit or implicit properties.
 *
 * Date: 2014/03/16
 * Time: 07:46
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing QueryJ Core classes.
 */
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
 * Represents the situation in which a custom {@link Sql} cannot be validated
 * since it has no explicit or implicit {@link org.acmsl.queryj.customsql.Property properties}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 07:46
 */
@ThreadSafe
public class CustomSqlWithNoPropertiesException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -6182400513070979781L;

    /**
     * Creates an instance blaming the wrong {@link Sql}.
     * @param sql the custom SQL.
     */
    public CustomSqlWithNoPropertiesException(@NotNull final Sql<String> sql)
    {
        super("custom.sql.with.no.properties", new Object[] { sql.getId() });
    }
}
