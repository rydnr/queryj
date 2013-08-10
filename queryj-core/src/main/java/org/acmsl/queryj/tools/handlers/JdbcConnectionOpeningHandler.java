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
 * Filename: JdbcConnectionOpeningHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Opens a JDBC connection.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.CannotEstablishDatabaseConnectionException;
import org.acmsl.queryj.api.exceptions.JdbcDriverNotFoundException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some Commons-Logging classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Opens a JDBC connection.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class JdbcConnectionOpeningHandler
    extends  AbstractQueryJCommandHandler
    implements QueryJSettings
{
    /**
     * The JDBC connection attribute name.
     */
    public static final String JDBC_CONNECTION = "jdbc.connection";

    /**
     * Creates a <code>JdbcConnectionOpeningHandler</code> instance.
     */
    public JdbcConnectionOpeningHandler() {}

    /**
     * Handles given parameters.
     * @param command the command.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        @Nullable final Connection connection = openConnection(command);

        if (connection != null)
        {
            storeConnection(connection, command);
        }

        return false;
    }

    /**
     * Opens the JDBC connection using the information stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the JDBC connection.
     * @throws QueryJBuildException if the connection cannot be opened.
     */
    @Nullable
    protected Connection openConnection(@NotNull final QueryJCommand parameters)
        throws  QueryJBuildException
    {
        @Nullable final Connection result;

        @Nullable final String driver = parameters.getSetting(JDBC_DRIVER);
        @Nullable final String url = parameters.getSetting(JDBC_URL);
        @Nullable final String userName = parameters.getSetting(JDBC_USERNAME);
        @Nullable final String password = parameters.getSetting(JDBC_PASSWORD);

        if (driver == null)
        {
            // TODO
            throw new RuntimeException("TODO: Fix me");
        }
        else if (url == null)
        {
            // TODO
            throw new RuntimeException("TODO: Fix me");
        }
        else if (userName == null)
        {
            // TODO
            throw new RuntimeException("TODO: Fix me");
        }

        result = openConnection(driver, url, userName, password);

        return result;
    }

    /**
     * Opens the connection.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param password the password.
     * @return the JDBC connection.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     */
    @Nullable
    protected Connection openConnection(
        @NotNull final String driver,
        @NotNull final String url,
        @NotNull final String username,
        @Nullable final String password)
      throws  QueryJBuildException
    {
        @Nullable final Connection result;

        try 
        {
            Class.forName(driver);

            result = DriverManager.getConnection(url, username, password);
        }
        catch  (@NotNull final RuntimeException exception)
        {
            throw exception;
        }
        catch  (@NotNull final ClassNotFoundException classNotFoundException)
        {
            throw new JdbcDriverNotFoundException(driver, classNotFoundException);
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw new CannotEstablishDatabaseConnectionException(url, username, sqlException);
        }

        return result;
    }

    /**
     * Stores the JDBC connection in given attribute map.
     * @param connection the connection to store.
     * @param parameters the parameter map.
     */
    protected void storeConnection(
        @NotNull final Connection connection, @NotNull final QueryJCommand parameters)
    {
        parameters.setSetting(JDBC_CONNECTION, connection);
    }
}
