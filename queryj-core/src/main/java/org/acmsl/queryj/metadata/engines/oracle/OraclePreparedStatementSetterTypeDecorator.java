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
 * Filename: OraclePreparedStatementSetterTypeDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/02/22
 * Time: 20:22
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.lang.reflect.Method;

/**
 * Type resolution when setting properties in {@link java.sql.PreparedStatement}s, for Oracle databases.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/22 20:22
 */
@ThreadSafe
public class OraclePreparedStatementSetterTypeDecorator
    extends JdbcTypeManager
{
    /**
     * Retrieves the setter method for given type.
     * @param type the type.
     * @return such method.
     */
    @NotNull
    public Method getSetterMethod(@NotNull final Class<?> type)
    {
        @NotNull final Method result;

        @Nullable final Method aux = PREPARED_STATEMENT_METHODS.get(getJdbcType(type));

        if (aux == null)
        {
            result = SET_OBJECT_METHOD;
        }
        else
        {
            result = aux;
        }

        return result;
    }

}
