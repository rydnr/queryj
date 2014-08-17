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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.QueryJCommandUtils;
import org.acmsl.queryj.api.exceptions.CannotRetrieveDatabaseMetadataException;
import org.acmsl.queryj.api.Template;
import org.acmsl.queryj.api.exceptions.DecoratorFactoryNotAvailableException;
import org.acmsl.queryj.api.exceptions.UnsupportedCharsetQueryjException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.exceptions.MissingConnectionAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingCustomSqlProviderAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingDataSourceJndiPathAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingOutputDirAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingProjectPackageAtRuntimeException;
import org.acmsl.queryj.tools.exceptions.MissingRepositoryNameAtRuntimeException;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
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
    implements  QueryJCommandHandler<QueryJCommand>,
                QueryJSettings
{
    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected File retrieveProjectOutputDir(@NotNull final QueryJCommand parameters)
    {
        @Nullable final File result = parameters.getFileSetting(ParameterValidationHandler.OUTPUT_DIR);

        if (result == null)
        {
            throw new MissingOutputDirAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     */
    @NotNull
    protected String retrieveProjectPackage(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result = parameters.getSetting(ParameterValidationHandler.PACKAGE_NAME);

        if (result == null)
        {
            throw new MissingProjectPackageAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws QueryJBuildException if the database metadata is not available.
     */
    @NotNull
    protected DatabaseMetaData retrieveDatabaseMetaData(@NotNull final QueryJCommand parameters)
      throws QueryJBuildException
    {
        @Nullable DatabaseMetaData result =
            new QueryJCommandWrapper<DatabaseMetaData>(parameters)
                .getSetting(DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);

        if (result == null)
        {
            @Nullable final Connection t_Connection =
                new QueryJCommandWrapper<Connection>(parameters)
                    .getSetting(JdbcConnectionOpeningHandler.JDBC_CONNECTION);

            if (t_Connection != null)
            {
                try
                {
                    result = t_Connection.getMetaData();
                }
                catch  (@NotNull final SQLException exception)
                {
                    throw new CannotRetrieveDatabaseMetadataException(exception);
                }
            }
            else
            {
                throw new CannotRetrieveDatabaseMetadataException();
            }
        }

        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final QueryJCommand parameters)
    {
        return retrieveMetadataManager(parameters, QueryJCommandUtils.getInstance());
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @param queryJCommandUtils the {@link QueryJCommandUtils} instance.
     * @return the manager.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(
        @NotNull final QueryJCommand parameters, @NotNull final QueryJCommandUtils queryJCommandUtils)
    {
        return queryJCommandUtils.retrieveMetadataManager(parameters);
    }

    /**
     * Retrieves the decorator factory.
     * @param command the command.
     * @return such instance.
     */
    @NotNull
    protected DecoratorFactory retrieveDecoratorFactory(@NotNull final QueryJCommand command)
    {
        @Nullable final DecoratorFactory result =
            new QueryJCommandWrapper<DecoratorFactory>(command).getSetting(DecoratorFactory.class.getName());

        if (result == null)
        {
            throw new DecoratorFactoryNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves whether to use sub folders or not.
     * @param command the command.
     * @return such flag.
     */
    protected boolean retrieveUseSubfoldersFlag(@SuppressWarnings("unused") @NotNull final QueryJCommand command)
    {
        return true;
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
     * Retrievesthe custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     */
    @NotNull
    public static CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final QueryJCommand parameters)
    {
        @Nullable final CustomSqlProvider result =
            new QueryJCommandWrapper<CustomSqlProvider>(parameters)
                .getSetting(CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);

        if (result == null)
        {
            throw new MissingCustomSqlProviderAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     */
    @NotNull
    protected String retrieveTableRepositoryName(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result = parameters.getSetting(ParameterValidationHandler.REPOSITORY);

        if (result == null)
        {
            throw new MissingRepositoryNameAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the header from the attribute map.
     * @param parameters the parameter map.
     * @return the header.
     */
    @Nullable
    protected String retrieveHeader(@NotNull final QueryJCommand parameters)
    {
        return parameters.getSetting(ParameterValidationHandler.HEADER);
    }

    /**
     * Retrieves whether to generate support for JMX or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveJmx(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.JMX, false);
    }

    /**
     * Retrieves whether to implement marker interfaces or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveImplementMarkerInterfaces(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.IMPLEMENT_MARKER_INTERFACES, false);
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
     * @throws QueryJBuildException if the connection is unavailable.
     */
    @NotNull
    protected Connection retrieveConnection(@NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        @Nullable final Connection result =
            new QueryJCommandWrapper<Connection>(parameters)
                .getSetting(JdbcConnectionOpeningHandler.JDBC_CONNECTION);

        if (result == null)
        {
            throw new MissingConnectionAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves the {@link Charset} instance.
     * @param parameters the parameter map.
     * @return such instance.
     * @throws QueryJBuildException if the charset is unavailable.
     */
    protected Charset retrieveCharset(@NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        @Nullable Charset result =
            new QueryJCommandWrapper<Charset>(parameters).getSetting(ParameterValidationHandler.CHARSET);

        @Nullable QueryJBuildException exceptionToThrow = null;

        if (result == null)
        {
            @Nullable final String encoding =
                parameters.getSetting(ParameterValidationHandler.ENCODING);

            if (encoding == null)
            {
                result = Charset.defaultCharset();
                new QueryJCommandWrapper<Charset>(parameters).setSetting(ParameterValidationHandler.CHARSET, result);
            }
            else
            {
                if (Charset.isSupported(encoding))
                {
                    try
                    {
                        result = Charset.forName(encoding);
                        new QueryJCommandWrapper<Charset>(parameters)
                            .setSetting(ParameterValidationHandler.CHARSET, result);
                    }
                    catch (final UnsupportedCharsetException unsupportedCharset)
                    {
                         // Should not happen since this has been checked beforehand.
                         exceptionToThrow =
                             new UnsupportedCharsetQueryjException(encoding, unsupportedCharset);
                    }
                    catch (final IllegalCharsetNameException illegalCharset)
                    {
                         exceptionToThrow =
                             new UnsupportedCharsetQueryjException(encoding, illegalCharset);
                    }
                }
                else
                {
                    exceptionToThrow = new UnsupportedCharsetQueryjException(encoding);
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
     * Retrieves the JNDI location from the attribute map.
     * @param parameters the parameter map.
     * @return the location.
     */
    @NotNull
    protected String retrieveJNDILocation(@NotNull final QueryJCommand parameters)
    {
        @Nullable final String result = parameters.getSetting(ParameterValidationHandler.JNDI_DATASOURCE);

        if (result == null)
        {
            throw new MissingDataSourceJndiPathAtRuntimeException();
        }

        return result;
    }

    /**
     * Retrieves whether to use template caching.
     * @param parameters the parameter map.
     * @return <code>true</code> in such case.
     */
    protected boolean retrieveCaching(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.CACHING, true);
    }

    /**
     * Retrieves the number of threads.
     * @param parameters the parameter map.
     * @return the number of threads to use.
     */
    protected int retrieveThreadCount(@NotNull final QueryJCommand parameters)
    {
        return parameters.getIntSetting(ParameterValidationHandler.THREAD_COUNT, 1);
    }

    /**
     * Retrieves whether to disable generation timestamps or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableGenerationTimestamps(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.DISABLE_TIMESTAMPS, false);
    }

    /**
     * Retrieves whether to disable NotNull annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableNotNullAnnotations(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.DISABLE_NOTNULL_ANNOTATIONS, false);
    }

    /**
     * Retrieves whether to disable checkthread.org annotations or not.
     * @param parameters the parameter map.
     * @return such condition.
     */
    protected boolean retrieveDisableCheckthreadAnnotations(@NotNull final QueryJCommand parameters)
    {
        return parameters.getBooleanSetting(ParameterValidationHandler.DISABLE_CHECKTHREAD_ANNOTATIONS, false);
    }

    /**
     * Annotates given list of generation tasks into the parameter map.
     * @param tasks the tasks to track.
     * @param parameters the parameter map.
     * @param <T> the template type.
     */
    @SuppressWarnings("unused")
    protected <T extends Template<?>> void annotateGenerationTasks(
        @NotNull final List<Future<T>> tasks, @NotNull final QueryJCommand parameters)
    {
        new QueryJCommandWrapper<List<Future<T>>>(parameters).setSetting(buildGenerationTasksKey(), tasks);
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
