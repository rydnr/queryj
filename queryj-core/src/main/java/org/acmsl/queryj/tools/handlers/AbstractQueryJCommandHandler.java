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
import org.acmsl.queryj.templates.Template;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inside a Chain Of Responsibility, these are the chain links.
 * This means they perform specific actions when receiving the command.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractQueryJCommandHandler
    implements  QueryJCommandHandler<QueryJCommand>
{
    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected abstract boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected File retrieveProjectOutputDir(@NotNull final Map<String, File> parameters)
    {
        return parameters.get(ParameterValidationHandler.OUTPUT_DIR);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrieveProjectPackage(@NotNull final Map<String, String> parameters)
    {
        return parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws QueryJBuildException if the database metadata is not accessible.
     */
    @NotNull
    protected DatabaseMetaData retrieveDatabaseMetaData(@NotNull final Map<String, ?> parameters)
      throws QueryJBuildException
    {
        @Nullable DatabaseMetaData result =
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);

        if (result == null)
        {
            @Nullable final Connection t_Connection =
                (Connection) parameters.get(
                    JdbcConnectionOpeningHandler.JDBC_CONNECTION);

            if (t_Connection != null)
            {
                try
                {
                    result = t_Connection.getMetaData();
                }
                catch  (@NotNull final SQLException exception)
                {
                    throw
                        new QueryJBuildException(
                            "cannot.retrieve.database.metadata",
                            exception);
                }
            }
            else
            {
                throw
                    new QueryJBuildException("cannot.retrieve.database.metadata");
            }
        }

        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     */
    @Nullable
    protected MetadataManager retrieveMetadataManager(@NotNull final Map<String, MetadataManager> parameters)
    {
        return parameters.get(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);
    }

    /**
     * Retrieves whether to use sub folders or not.
     * @param parameters the parameters.
     * @return such flag.
     */
    protected boolean retrieveUseSubfoldersFlag(@NotNull final Map<String, Boolean> parameters)
    {
        boolean result = false;

        @Nullable final Boolean t_Flag =
            parameters.get(ParameterValidationHandler.OUTPUT_DIR_SUBFOLDERS);

        if  (t_Flag != null)
        {
            result = t_Flag;
        }

        return result;
    }

    /**
     * Fixes given quote character.
     * @param quote the quote.
     * @return the correct one.
     */
    @NotNull
    public static String fixQuote(@Nullable final String quote)
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
     */
    @NotNull
    public static CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final Map<String, CustomSqlProvider> parameters)
    {
        return
            parameters.get(CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     */
    @NotNull
    protected String retrieveTableRepositoryName(@NotNull final Map<String, String> parameters)
    {
        return parameters.get(ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the header from the attribute map.
     * @param parameters the parameter map.
     * @return the header.
     */
    @Nullable
    protected String retrieveHeader(@NotNull final Map<String, String> parameters)
    {
        return parameters.get(ParameterValidationHandler.HEADER);
    }

    /**
     * Retrieves whether to generate support for JMX or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveJmx(@NotNull final Map<String, Boolean> parameters)
    {
        boolean result = false;

        @Nullable final Boolean jmx = parameters.get(ParameterValidationHandler.JMX);

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
     */
    protected boolean retrieveImplementMarkerInterfaces(@NotNull final Map<String, Boolean> parameters)
    {
        final boolean result;

        @Nullable final Boolean aux =
            parameters.get(
                ParameterValidationHandler.IMPLEMENT_MARKER_INTERFACES);

        if  (aux == null)
        {
            result = Boolean.FALSE;
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the database product version.
     * @param metadata the database metadata.
     * @return such information, or null if the vendor complains.
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
            @Nullable final Log t_Log =
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
     * Retrieves the {@link Connection} instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws QueryJBuildException if the provider cannot be stored for
     * any reason.
     */
    @NotNull
    protected Connection retrieveConnection(@NotNull final Map<String, Connection> parameters)
      throws  QueryJBuildException
    {
        return parameters.get(JdbcConnectionOpeningHandler.JDBC_CONNECTION);
    }

    /**
     * Retrieves the {@link Charset} instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws QueryJBuildException if the charset is not valid.
     */
    @SuppressWarnings("unchecked")
    protected Charset retrieveCharset(@NotNull final Map<String, ?> parameters)
      throws  QueryJBuildException
    {
        Charset result =
            (Charset) parameters.get(ParameterValidationHandler.CHARSET);

        @Nullable QueryJBuildException exceptionToThrow = null;

        if (result == null)
        {
            @Nullable final String encoding =
                (String) parameters.get(ParameterValidationHandler.ENCODING);

            if (encoding == null)
            {
                result = Charset.defaultCharset();
                ((Map <String, Charset>) parameters).put(ParameterValidationHandler.CHARSET, result);
            }
            else
            {
                if (Charset.isSupported(encoding))
                {
                    try
                    {
                        result = Charset.forName(encoding);
                        ((Map <String, Charset>) parameters).put(ParameterValidationHandler.CHARSET, result);
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


    /**
     * Retrieves whether to disable generation timestamps or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveBoolean(@NotNull final Map<String, Boolean> parameters, @NotNull final String key)
    {
        final boolean result;

        @Nullable final Boolean aux = parameters.get(key);

        if  (aux == null)
        {
            result = Boolean.FALSE;
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves whether to disable generation timestamps or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected int retrieveInteger(@NotNull final Map<String, Integer> parameters, @NotNull final String key)
    {
        final int result;

        @Nullable final Integer aux = parameters.get(key);

        if  (aux == null)
        {
            result = -1;
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the JNDI location from the attribute map.
     * @param parameters the parameter map.
     * @return the location.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected String retrieveJNDILocation(@NotNull final Map<String, String> parameters)
    {
        @Nullable String result = parameters.get(ParameterValidationHandler.JNDI_DATASOURCES);

        if (result == null)
        {
            result = "";
        }

        return result;
    }


    /**
     * Retrieves whether to use template caching.
     * @param parameters the parameter map.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unchecked")
    protected boolean retrieveCaching(@NotNull final Map<String, Boolean> parameters)
    {
        return retrieveBoolean(parameters, ParameterValidationHandler.CACHING);
    }

    /**
     * Retrieves the number of threads.
     * @param parameters the parameter map.
     * @return the number of threads to use.
     */
    @SuppressWarnings("unchecked")
    protected int retrieveThreadCount(@NotNull final Map<String, Integer> parameters)
    {
        return parameters.get(ParameterValidationHandler.THREAD_COUNT);
    }

    /**
     * Retrieves whether to disable generation timestamps or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableGenerationTimestamps(@NotNull final Map<String, Boolean> parameters)
    {
        return retrieveBoolean(parameters, ParameterValidationHandler.DISABLE_TIMESTAMPS);
    }

    /**
     * Retrieves whether to disable NotNull annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableNotNullAnnotations(@NotNull final Map<String, Boolean> parameters)
    {
        return retrieveBoolean(parameters, ParameterValidationHandler.DISABLE_NOTNULL_ANNOTATIONS);
    }

    /**
     * Retrieves whether to disable checkthread.org annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableCheckthreadAnnotations(@NotNull final Map<String, Boolean> parameters)
    {
        return retrieveBoolean(parameters, ParameterValidationHandler.DISABLE_CHECKTHREAD_ANNOTATIONS);
    }

    /**
     * Annotates given list of generation tasks into the parameter map.
     * @param tasks the tasks to track.
     * @param parameters the parameter map.
     */
    protected <T extends Template> void annotateGenerationTasks(
        @NotNull final List<Future<T>> tasks, @NotNull final Map<String, List<Future<T>>> parameters)
    {
        parameters.put(buildGenerationTasksKey(), tasks);
    }

    /**
     * Retrieves the generation tasks.
     * @param parameters the parameter map.
     * @return such list.
     */
    @NotNull
    protected <W> List<W> retrieveGenerationTasks(
        @NotNull final Map<String, List<W>> parameters)
    {
        List<W> result = parameters.get(buildGenerationTasksKey());

        if (result == null)
        {
            result = new ArrayList<W>(0);
        }

        return result;
    }

    /**
     * Retrieves the key used to store the generation task list.
     * @return such key.
     */
    @NotNull
    protected String buildGenerationTasksKey()
    {
        return ">:generation_tasks:<";
    }

    /**
     * Displays the role of this handler.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }

}
