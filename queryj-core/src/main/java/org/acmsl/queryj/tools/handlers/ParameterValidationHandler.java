//;-*- mode: java -*-
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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates the parameters of an Ant task.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.CannotReadCustomSqlXmlFileException;
import org.acmsl.queryj.api.exceptions.NullCharsetException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.UnsupportedCharsetQueryjException;
import org.acmsl.queryj.api.exceptions.CannotReadHeaderFileException;
import org.acmsl.queryj.api.exceptions.GrammarBundleDoesNotExistException;
import org.acmsl.queryj.api.exceptions.GrammarFolderDoesNotExistException;
import org.acmsl.queryj.api.exceptions.IllegalThreadCountException;
import org.acmsl.queryj.api.exceptions.InvalidJndiLocationException;
import org.acmsl.queryj.api.exceptions.MissingClasspathException;
import org.acmsl.queryj.api.exceptions.MissingCustomSqlXmlFileException;
import org.acmsl.queryj.api.exceptions.MissingExternallyManagedFieldsException;
import org.acmsl.queryj.api.exceptions.MissingJdbcDriverException;
import org.acmsl.queryj.api.exceptions.MissingJdbcSchemaException;
import org.acmsl.queryj.api.exceptions.MissingJdbcUrlException;
import org.acmsl.queryj.api.exceptions.MissingJdbcUsernameException;
import org.acmsl.queryj.api.exceptions.MissingJndiLocationException;
import org.acmsl.queryj.api.exceptions.MissingOutputFolderException;
import org.acmsl.queryj.api.exceptions.MissingPackageException;
import org.acmsl.queryj.api.exceptions.MissingRepositoryException;
import org.acmsl.queryj.api.exceptions.MissingTablesException;
import org.acmsl.queryj.api.exceptions.OutputDirIsNotAFolderException;
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.logging.QueryJAntLog;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.types.Path;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Validates the parameters of an Ant task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ParameterValidationHandler
    extends  AbstractQueryJCommandHandler
    implements QueryJSettings
{
    /**
     * The missing password error message.
     */
    @SuppressWarnings("unused")
    public static final String PASSWORD_MISSING = "JDBC password is missing.";

    /**
     * The missing catalog error message.
     */
    @SuppressWarnings("unused")
    public static final String CATALOG_MISSING = "JDBC catalog is missing.";

    /**
     * The classpath attribute name.
     */
    public static final String CLASSPATH = "classpath";

    /**
     * The header-contents attribute name.
     */
    public static final String HEADER = "header_contents";

    /**
     * The tables element name.
     */
    public static final String TABLES = "tables";

    /**
     * The tables element name.
     */
    public static final String EXPLICIT_TABLES = "explicit-tables";

    /**
     * The externally-managed-fields element name.
     */
    public static final String EXTERNALLY_MANAGED_FIELDS =
        "externally.managed.fields";

    /**
     * The charset associated to given encoding.
     */
    public static final String CHARSET = "charset";

    /**
     * Creates a {@link ParameterValidationHandler} instance.
     */
    public ParameterValidationHandler() {}

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return handle(command, command.getLog());
    }

    /**
     * Handles given parameters.
     * @param command the command to handle.
     * @param log the log.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if any parameter fails to validate.
     */
    protected boolean handle(@NotNull final QueryJCommand command, @Nullable final Log log)
        throws  QueryJBuildException
    {
        validateParameters(command, (log instanceof QueryJAntLog));

        return false;
    }

    /**
     * Validates the parameters.
     * @param command the parameter map.
     * @param usingAnt whether QueryJ is executed within Ant.
     * @throws QueryJBuildException if any parameter fails to validate.
     */
    public void validateParameters(
        @NotNull final QueryJCommand command, final boolean usingAnt)
      throws  QueryJBuildException
    {
        validateParameters(
            command.getStringSetting(JDBC_DRIVER),
            command.getStringSetting(JDBC_URL),
            command.getStringSetting(JDBC_USERNAME),
            command.getStringSetting(JDBC_SCHEMA),
            command.getStringSetting(REPOSITORY),
            command.getStringSetting(PACKAGE_NAME),
            command.getFileSetting(OUTPUT_DIR),
            command.getFileSetting(HEADER_FILE),
            command.getStringSetting(JNDI_DATASOURCE),
            command.getFileSetting(SQL_XML_FILE),
            command.getFileSetting(GRAMMAR_FOLDER),
            command.getStringSetting(GRAMMAR_NAME),
            command.getStringSetting(GRAMMAR_SUFFIX),
            command.getStringSetting(ENCODING),
            command.getIntSetting(THREAD_COUNT, Runtime.getRuntime().availableProcessors()),
            command);

        if  (usingAnt)
        {
            validateAntParameters(
                new QueryJCommandWrapper<AntTablesElement>(command).getSetting(TABLES),
                new QueryJCommandWrapper<AntExternallyManagedFieldsElement>(command).getSetting(
                    EXTERNALLY_MANAGED_FIELDS),
                new QueryJCommandWrapper<Path>(command).getSetting(CLASSPATH));
        }
    }

    /**
     * Validates the parameters.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param schema the schema.
     * @param repository the repository.
     * @param packageName the package name.
     * @param outputDir the output folder.
     * @param header the header.
     * @param jndiDataSources the JNDI location for data sources.
     * @param sqlXmlFile the sql.xml file.
     * @param grammarFolder the grammar folder.
     * @param grammarBundleName the grammar bundle name.
     * @param grammarSuffix the grammar suffix.
     * @param encoding the file encoding.
     * @param threadCount the number of threads.
     * @param command the command, to store processed information.
     * such as the header contents.
     * @throws QueryJBuildException if any parameter fails to validate.
     */
    protected void validateParameters(
        @Nullable final String driver,
        @Nullable final String url,
        @Nullable final String username,
        @Nullable final String schema,
        @Nullable final String repository,
        @Nullable final String packageName,
        @Nullable final File outputDir,
        @Nullable final File header,
        @Nullable final String jndiDataSources,
        @Nullable final File sqlXmlFile,
        @Nullable final File grammarFolder,
        @Nullable final String grammarBundleName,
        @Nullable final String grammarSuffix,
        @Nullable final String encoding,
        final int threadCount,
        @NotNull final QueryJCommand command)
      throws  QueryJBuildException
    {
        @Nullable final Log t_Log =
            UniqueLogFactory.getLog(ParameterValidationHandler.class);
                
        if  (driver == null) 
        {
            throw new MissingJdbcDriverException();
        }

        if  (url == null) 
        {
            throw new MissingJdbcUrlException();
        }

        if  (username == null) 
        {
            throw new MissingJdbcUsernameException();
        }

        /* Not mandatory.
        if  (password == null)
        {
            throw new QueryJBuildException(PASSWORD_MISSING);
        }
        */

        /* Not mandatory.
        if  (catalog == null) 
        {
            throw new QueryJBuildException(CATALOG_MISSING);
        }
        */

        if  (schema == null) 
        {
            throw new MissingJdbcSchemaException();
        }

        if  (repository == null) 
        {
            throw new MissingRepositoryException();
        }

        if  (packageName == null) 
        {
            throw new MissingPackageException();
        }

        if  (outputDir == null)
        {
            throw new MissingOutputFolderException();
        }

        if  (header == null)
        {
            if  (t_Log != null)
            {
                t_Log.info(
                      "No header specified. Using "
                    + "GPLed QueryJ's instead.");
            }
        }
        else
        {
            try
            {
                command.setSetting(HEADER, readFile(header, Charset.forName(encoding)));
            }
            catch  (@NotNull final FileNotFoundException fileNotFoundException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Header file not found.",
                        fileNotFoundException);
                }
                throw new CannotReadHeaderFileException(header, fileNotFoundException);
            }
            catch  (@NotNull final SecurityException securityException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "No permission to read header file.",
                        securityException);
                }
                throw new CannotReadHeaderFileException(header, securityException);
            }
            catch  (@NotNull final IOException ioException)
            {
                if  (t_Log != null)
                {
                    t_Log.warn(
                        "Could not read header file.",
                        ioException);
                }
                throw new CannotReadHeaderFileException(header, ioException);
            }
        }
        
        if  (!outputDir.isDirectory())
        {
            throw new OutputDirIsNotAFolderException(outputDir);
        }

        if  (jndiDataSources == null)
        {
            throw new MissingJndiLocationException();
        }

        if  (   (jndiDataSources.contains("\""))
             || (jndiDataSources.contains("\n")))
        {
            throw new InvalidJndiLocationException(jndiDataSources);
        }

        if (sqlXmlFile == null)
        {
            throw new MissingCustomSqlXmlFileException();
        }

        if (!sqlXmlFile.exists())
        {
            throw new CannotReadCustomSqlXmlFileException(sqlXmlFile);
        }

        if (!sqlXmlFile.canRead())
        {
            throw new MissingCustomSqlXmlFileException();
        }

        // Not mandatory
        if  (grammarFolder != null)
        {
            if (!grammarFolder.exists())
            {
                throw new GrammarFolderDoesNotExistException(grammarFolder);
            }

            String suffix = grammarSuffix;

            if (grammarSuffix == null)
            {
                suffix = "";
            }

            File file =
                new File(
                      grammarFolder + File.separator
                    + grammarBundleName
                    + "_"
                    + Locale.US.getLanguage().toLowerCase(Locale.US)
                    + suffix);

            if (!file.exists())
            {
                file =
                    new File(
                          grammarFolder + File.separator
                        + grammarBundleName
                        + suffix);
            }

            if (!file.exists())
            {
                throw new GrammarBundleDoesNotExistException(grammarBundleName, grammarFolder);
            }
        }

        if (encoding != null)
        {
            if (!Charset.isSupported(encoding))
            {
                throw new UnsupportedCharsetQueryjException(encoding);
            }
            else
            {
                try
                {
                    Charset.forName(encoding);
                }
                catch (@NotNull final IllegalCharsetNameException illegalCharset)
                {
                    throw new UnsupportedCharsetQueryjException(encoding, illegalCharset);
                }
                catch (@NotNull final IllegalArgumentException nullCharset)
                {
                    // should not happen since encoding is optional anyway.
                    throw new NullCharsetException(nullCharset);
                }
                // catch (final UnsupportedCharsetException unsupportedCharset)
                // {
                //     // Should not happen since this has been checked beforehand.
                //     throw new QueryJBuildException(UNSUPPORTED_ENCODING);
                // }
            }
        }

        if (threadCount <= 0)
        {
            throw new IllegalThreadCountException(threadCount);
        }
    }

    /**
     * Validates the parameters explicitly coming from Ant.
     * @param tables the table information.
     * @param externallyManagedFields the externally-managed fields
     * information.
     * @param classpath the classpath.
     * such as the header contents.
     * @throws QueryJBuildException if any parameter fails to validate.
     */
    protected void validateAntParameters(
        @Nullable final AntTablesElement tables,
        @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
        @Nullable final Path classpath)
      throws  QueryJBuildException
    {
        if  (classpath == null) 
        {
            throw new MissingClasspathException();
        }

        if  (   (tables != null)
             && (   (tables.getTables() == null)
                 || (tables.getTables().size() == 0)))
        {
            throw new MissingTablesException();
        }

        if  (   (externallyManagedFields != null)
             && (externallyManagedFields.getFields().size() == 0))
        {
            throw new MissingExternallyManagedFieldsException();
        }
    }

    /**
     * Reads the contents of given file.
     * @param file the file.
     * @param charset the charset.
     * @return the file contents.
     * @throws SecurityException if reading the file is not permitted.
     * @throws IOException if the file cannot be read.
     */
    @NotNull
    protected String readFile(@NotNull final File file, @NotNull final Charset charset)
        throws  SecurityException,
                IOException
    {
        return readFile(file, charset, FileUtils.getInstance());
    }

    /**
     * Reads the contents of given file.
     * @param file the file.
     * @param charset the charset.
     * @param fileUtils the {@link FileUtils} instance.
     * @return the file contents.
     * @throws SecurityException if reading the file is not permitted.
     * @throws IOException if the file cannot be read.
     */
    @NotNull
    protected String readFile(
        @NotNull final File file, @NotNull final Charset charset, @NotNull final FileUtils fileUtils)
        throws  SecurityException,
                IOException
    {
        return fileUtils.readFile(file, charset);
    }
}
