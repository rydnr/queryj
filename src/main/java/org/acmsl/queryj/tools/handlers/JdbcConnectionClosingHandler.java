//;-*- mode: java -*-
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
 * Filename: JdbcConnectionClosingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Closes a JDBC connection.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Closes a JDBC connection.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class JdbcConnectionClosingHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates a <code>JdbcConnectionClosingHandler</code> instance.
     */
    public JdbcConnectionClosingHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        closeConnection(parameters);

        removeConnection(parameters);

        return false;
    }

    /**
     * Closes the JDBC connection stored in the attribute map.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the connection cannot be closed.
     * @precondition parameters != null
     */
    protected void closeConnection(final Map parameters)
        throws  QueryJBuildException
    {
        closeConnection(
            (Connection)
                parameters.get(
                    JdbcConnectionOpeningHandler.JDBC_CONNECTION));
    }

    /**
     * Closes the connection.
     * @param connection the JDBC connection.
     * @throws QueryJBuildException whenever the required
     * connection is not present or valid.
     */
    protected void closeConnection(final Connection connection)
        throws  QueryJBuildException
    {
        if  (connection != null)
        {
            try
            {
                connection.close();
            }
            catch  (final SQLException sqlException)
            {
                throw
                    new QueryJBuildException(
                        "Cannot close the database connection", sqlException);
            }
        }
    }

    /**
     * Removes the JDBC connection in given attribute map.
     * @param parameters the parameter map.
     * @throws QueryJBuildException if the connection cannot be removed for
     * any reason.
     * @precondition parameters != null
     */
    protected void removeConnection(final Map parameters)
        throws  QueryJBuildException
    {
        parameters.remove(JdbcConnectionOpeningHandler.JDBC_CONNECTION);
    }
}
