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
 * Filename: AbstractQueryJCommandHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Inside a Chain Of Responsibility, these are the chain links.
 *              This means they perform specific actions when receiving the
 *              command.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Map;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inside a Chain Of Responsibility, these are the chain links.
 * This means they perform specific actions when receiving the command.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractQueryJCommandHandler
    implements  QueryJCommandHandler
{
    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition command != null
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof QueryJCommand)
        {
            @NotNull QueryJCommand t_Command = (QueryJCommand) command;
            
            Log t_OtherLog =
                UniqueLogFactory.getLog(
                    AbstractQueryJCommandHandler.class);

            if  (t_OtherLog != null)
            {
                t_OtherLog.info("Executing handler: " + this);
            }

            try 
            {
                result = handle(t_Command);
            }
            catch  (@NotNull final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        AbstractQueryJCommandHandler.class);

                if  (t_Log != null)
                {
                    t_Log.error("Chain step failed.", buildException);
                }
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected abstract boolean handle(final Map parameters)
        throws  QueryJBuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    @NotNull
    protected File retrieveProjectOutputDir(@NotNull final Map parameters)
    {
        return
            (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters !0 null
     */
    @NotNull
    protected String retrieveProjectPackage(@NotNull final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @precondition parameters != null
     */
    @NotNull
    protected DatabaseMetaData retrieveDatabaseMetaData(
        @NotNull final Map parameters)
    {
        return
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @precondition parameters != null
     */
    @Nullable
    protected MetadataManager retrieveMetadataManager(
        @NotNull final Map parameters)
    {
        return
            (MetadataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);
    }

    /**
     * Retrieves whether to use subfolders or not.
     * @param parameters the parameters.
     * @return such flag.
     * @precondition parameters != null
     */
    protected boolean retrieveUseSubfoldersFlag(@NotNull final Map parameters)
    {
        boolean result = false;

        Object t_Flag =
            parameters.get(ParameterValidationHandler.OUTPUT_DIR_SUBFOLDERS);

        if  (   (t_Flag != null)
             && (t_Flag instanceof Boolean))
        {
            result = (Boolean) t_Flag;
        }

        return result;
    }

    /**
     * Retrieves the JDBC driver from the attribute map.
     * @param parameters the parameter map.
     * @return the driver name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveJdbcDriver(@NotNull final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_DRIVER);
    }

    /**
     * Retrieves the JDBC url from the attribute map.
     * @param parameters the parameter map.
     * @return the url name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveJdbcUrl(@NotNull final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_URL);
    }

    /**
     * Retrieves the JDBC username from the attribute map.
     * @param parameters the parameter map.
     * @return the username name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveJdbcUsername(@NotNull final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_USERNAME);
    }

    /**
     * Retrieves the JDBC password from the attribute map.
     * @param parameters the parameter map.
     * @return the password name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveJdbcPassword(@NotNull final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_PASSWORD);
    }

    /**
     * Fixes given quote character.
     * @param quote the quote.
     * @return the correct one.
     */
    public static String fixQuote(final String quote)
    {
        String result = quote;

        if  (result == null)
        {
            result = "\"";
        }

        if  (result.equals("\""))
        {
            result = "\\\"";
        }

        return result;
    }

    /**
     * Retrieves the custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     * @precondition parameters != null
     */
    @NotNull
    public static CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final Map parameters)
    {
        return
            (CustomSqlProvider)
                parameters.get(
                    CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveTableRepositoryName(@NotNull final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the header from the attribute map.
     * @param parameters the parameter map.
     * @return the header.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveHeader(@NotNull final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.HEADER);
    }

    /**
     * Retrieves whether to generate support for JMX or not.
     * @param parameters the parameter map.
     * @return such condition.
     * @precondition parameters != null
     */
    protected boolean retrieveJmx(@NotNull final Map parameters)
    {
        boolean result = false;

        Boolean jmx = (Boolean) parameters.get(ParameterValidationHandler.JMX);

        if (jmx != null)
        {
            result = jmx;
        }

        return result;
    }

    /**
     * Retrieves whether to implement marker interfaces or not.
     * @param parameters the parameter map.
     * @return such condition.
     * @precondition parameters != null
     */
    protected boolean retrieveImplementMarkerInterfaces(
        @NotNull final Map parameters)
    {
        Boolean result =
            (Boolean)
                parameters.get(
                    ParameterValidationHandler.IMPLEMENT_MARKER_INTERFACES);

        if  (result == null)
        {
            result = Boolean.FALSE;
        }

        return result;
    }

    /**
     * Retrieves the database product name.
     * @param metadata the database metadata.
     * @return such information, or null if the vendor complains.
     * @precondition metadata != null
     */
    @Nullable
    protected String retrieveDatabaseProductName(
            @NotNull final DatabaseMetaData metadata)
    {
        @Nullable String result = null;

        try
        {
            result = metadata.getDatabaseProductName();
        }
        catch  (@NotNull final Throwable throwable)
        {
            Log t_Log =
                    UniqueLogFactory.getLog(AbstractQueryJCommandHandler.class);

            if  (t_Log != null)
            {
                t_Log.info(
                        "Database vendor complained when queried about "
                      + "its version via "
                      + "java.sql.DatabaseMetaData.getDatabaseProductName()",
                      throwable);
            }
        }

        return result;
    }

    /**
     * Retrieves the database product version.
     * @param metadata the database metadata.
     * @return such information, or null if the vendor complains.
     * @precondition metadata != null
     */
    @Nullable
    protected String retrieveDatabaseProductVersion(
        @NotNull final DatabaseMetaData metadata)
    {
        @Nullable String result = null;

        try
        {
            result = metadata.getDatabaseProductVersion();
        }
        catch  (@NotNull final Throwable throwable)
        {
            Log t_Log =
                UniqueLogFactory.getLog(AbstractQueryJCommandHandler.class);

            if  (t_Log != null)
            {
                t_Log.info(
                      "Database vendor complained when queried about "
                    + "its version via "
                    + "java.sql.DatabaseMetaData.getDatabaseProductVersion()",
                    throwable);
            }
        }

        return result;
    }

    /**
     * Retrieves the database identifier quote string.
     * @param metadata the {@link DatabaseMetaData} instance.
     * @return such information.
     * @precondition parameters != null
     */
    @Nullable
    protected String retrieveDatabaseIdentifierQuoteString(
        @NotNull final DatabaseMetaData metadata)
    {
        @Nullable String result = null;

        try
        {
            result = metadata.getIdentifierQuoteString();
        }
        catch  (@NotNull final Throwable throwable)
        {
            Log t_Log =
                    UniqueLogFactory.getLog(AbstractQueryJCommandHandler.class);

            if  (t_Log != null)
            {
                t_Log.info(
                        "Database vendor complained when queried about "
                      + "its version via "
                      + "java.sql.DatabaseMetaData.getIdentifierQuoteString()",
                      throwable);
            }
        }

        return result;
    }

    /**
     * Retrieves the {@link Connection} instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws QueryJBuildException if the provider cannot be stored for
     * any reason.
     * @precondition parameters != null
     */
    @NotNull
    protected Connection retrieveConnection(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        return
            (Connection)
                parameters.get(
                    JdbcConnectionOpeningHandler.JDBC_CONNECTION);
    }

    /**
     * Retrieves the {@link Charset} instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws QueryJBuildException if the charset is not valid.
     * @precondition parameters != null
     */
    @SuppressWarnings("unchecked")
    protected Charset retrieveCharset(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        Charset result =
            (Charset) parameters.get(ParameterValidationHandler.CHARSET);

        @Nullable QueryJBuildException exceptionToThrow = null;

        if (result == null)
        {
            @NotNull String encoding =
                (String) parameters.get(ParameterValidationHandler.ENCODING);

            if (encoding == null)
            {
                result = Charset.defaultCharset();
                parameters.put(ParameterValidationHandler.CHARSET, result);
            }
            else
            {
                if (Charset.isSupported(encoding))
                {
                    try
                    {
                        result = Charset.forName(encoding);
                        parameters.put(ParameterValidationHandler.CHARSET, result);
                    }
                    catch (@NotNull final IllegalArgumentException nullCharset)
                    {
                        // should not happen since encoding is optional anyway.
                        exceptionToThrow =
                            new QueryJBuildException(
                                "encoding is null", nullCharset);
                    }
                    // catch (final UnsupportedCharsetException unsupportedCharset)
                    // {
                    //     // Should not happen since this has been checked beforehand.
                    //     exceptionToThrow =
                    //         new QueryJBuildException(
                    //             ParameterValidationHandler.UNSUPPORTED_ENCODING,
                    //             unsupportedCharset);
                    // }
                    // catch (final IllegalCharsetNameException illegalCharset)
                    // {
                    //     exceptionToThrow =
                    //         new QueryJBuildException(
                    //             ParameterValidationHandler.ILLEGAL_ENCODING,
                    //             illegalCharset);
                    // }
                }
                else
                {
                    exceptionToThrow =
                        new QueryJBuildException(
                            ParameterValidationHandler.UNSUPPORTED_ENCODING);
                }
            }
        }

        if (exceptionToThrow != null)
        {
            throw exceptionToThrow;
        }

        return result;
    }
}
