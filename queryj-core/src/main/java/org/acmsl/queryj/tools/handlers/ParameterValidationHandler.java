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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates the parameters of an Ant task.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.exceptions.CannotReadCustomSqlXmlFileException;
import org.acmsl.queryj.api.exceptions.CustomSqlXmlFileDoesNotExistException;
import org.acmsl.queryj.api.exceptions.NullCharsetException;
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
import org.acmsl.queryj.api.exceptions.UnsupportedCustomSqlFileFormatException;
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.tools.logging.QueryJAntLog;
import org.acmsl.queryj.tools.logging.QueryJLog;

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
import java.util.*;

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
{
    /**
     * The JDBC driver attribute name.
     */
    public static final String JDBC_DRIVER = "jdbc.driver";

    /**
     * The JDBC URL attribute name.
     */
    public static final String JDBC_URL = "jdbc.url";

    /**
     * The JDBC username attribute name.
     */
    public static final String JDBC_USERNAME = "jdbc.username";

    /**
     * The JDBC password attribute name.
     */
    public static final String JDBC_PASSWORD = "jdbc.password";

    /**
     * The missing password error message.
     */
    @SuppressWarnings("unused")
    public static final String PASSWORD_MISSING = "JDBC password is missing.";

    /**
     * The JDBC catalog attribute name.
     */
    public static final String JDBC_CATALOG = "jdbc.catalog";

    /**
     * The missing catalog error message.
     */
    @SuppressWarnings("unused")
    public static final String CATALOG_MISSING = "JDBC catalog is missing.";

    /**
     * The JDBC schema attribute name.
     */
    public static final String JDBC_SCHEMA = "jdbc.schema";

    /**
     * The repository attribute name.
     */
    public static final String REPOSITORY = "repository";

    /**
     * The package attribute name.
     */
    public static final String PACKAGE = "package";

    /**
     * The classpath attribute name.
     */
    public static final String CLASSPATH = "classpath";

    /**
     * The output-dir attribute name.
     */
    public static final String OUTPUT_DIR = "outputdir";

    /**
     * The header-file attribute name.
     */
    public static final String HEADER_FILE = "header-file";

    /**
     * The header attribute name.
     */
    public static final String HEADER = "header";

    /**
     * The output-dir-subfolders attribute name.
     */
    public static final String OUTPUT_DIR_SUBFOLDERS = "outputdirsubfolders";

    /**
     * The extract-tables attribute name.
     */
    public static final String EXTRACT_TABLES = "extract.tables";

    /**
     * The extract-functions attribute name.
     */
    public static final String EXTRACT_FUNCTIONS = "extract.functions";

    /**
     * The extract-procedures attribute name.
     */
    public static final String EXTRACT_PROCEDURES = "extract.procedures";

    /**
     * The JNDI location for data sources  attribute name.
     */
    public static final String JNDI_DATASOURCES = "jndi.datasources";

    /**
     * The generate-mock-dao attribute name.
     */
    public static final String GENERATE_MOCK_DAO = "generate.mock.dao";

    /**
     * The generate-xml-dao attribute name.
     */
    public static final String GENERATE_XML_DAO = "generate.xml.dao";

    /**
     * The generate-tests attribute name.
     */
    public static final String GENERATE_TESTS = "generate.tests";

    /**
     * The allow-empty-repository-dao attribute name.
     */
    public static final String ALLOW_EMPTY_REPOSITORY_DAO =
        "allow.empty.repository.dao";

    /**
     * The implement-marker-interfaces attribute name.
     */
    public static final String IMPLEMENT_MARKER_INTERFACES =
        "implement.marker.interfaces";

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
     * The custom-sql model.
     */
    public static final String CUSTOM_SQL_MODEL = "customSqlModel";

    /**
     * Whether to disable custom sql validation.
     */
    public static final String DISABLE_CUSTOM_SQL_VALIDATION =
        "disableCustomSqlValidation";

    /**
     * The sql.xml file.
     */
    public static final String SQL_XML_FILE = "sqlXmlFile";

    /**
     * The custom-sql XML model.
     */
    public static final String CUSTOM_SQL_MODEL_XML = "xml";

    /**
     * The grammar folder.
     */
    public static final String GRAMMAR_FOLDER = "grammarFolder";

    /**
     * The grammar bundle name.
     */
    public static final String GRAMMAR_NAME = "grammarName";

    /**
     * The grammar suffix.
     */
    public static final String GRAMMAR_SUFFIX = "grammarSuffix";

    /**
     * The file encoding.
     */
    public static final String ENCODING = "encoding";

    /**
     * The charset associated to given encoding.
     */
    public static final String CHARSET = "charset";

    /**
     * Whether to generate JMX support or not.
     */
    public static final String JMX = "jmx";

    /**
     * Whether to enable caching.
     */
    public static final String CACHING = "caching";

    /**
     * The thread count.
     */
    public static final String THREAD_COUNT = "threads";

    /**
     * Whether to disable generation timestamps or not.
     */
    public static final String DISABLE_TIMESTAMPS = "timestamps";

    /**
     * Whether to disable NotNull annotations or not.
     */
    public static final String DISABLE_NOTNULL_ANNOTATIONS = "notNullAnnotations";

    /**
     * Whether to disable checkthread.org annotations or not.
     */
    public static final String DISABLE_CHECKTHREAD_ANNOTATIONS = "checkthreadAnnotations";

    /**
     * Creates a {@link ParameterValidationHandler} instance.
     */
    public ParameterValidationHandler() {}

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    public boolean handle(@NotNull final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap(), command.getLog());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param log the log.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map parameters, @Nullable final QueryJLog log)
        throws  QueryJBuildException
    {
        validateParameters(parameters, (log instanceof QueryJAntLog));

        return false;
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(@NotNull final Map<String, ?> parameters)
        throws  QueryJBuildException
    {
        validateParameters(parameters, false);

        return false;
    }

    /**
     * Validates the parameters.
     * @param parameters the parameter map.
     * @param usingAnt whether QueryJ is executed within Ant.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @SuppressWarnings("unchecked")
    public void validateParameters(
        @NotNull final Map parameters, final boolean usingAnt)
      throws  QueryJBuildException
    {
        validateParameters(
            (String) parameters.get(JDBC_DRIVER),
            (String) parameters.get(JDBC_URL),
            (String) parameters.get(JDBC_USERNAME),
            (String) parameters.get(JDBC_PASSWORD),
            (String) parameters.get(JDBC_CATALOG),
            (String) parameters.get(JDBC_SCHEMA),
            (String) parameters.get(REPOSITORY),
            (String) parameters.get(PACKAGE),
            (File) parameters.get(OUTPUT_DIR),
            (File) parameters.get(HEADER_FILE),
            (Boolean) parameters.get(OUTPUT_DIR_SUBFOLDERS),
            (Boolean) parameters.get(EXTRACT_PROCEDURES),
            (Boolean) parameters.get(EXTRACT_FUNCTIONS),
            (String) parameters.get(JNDI_DATASOURCES),
            (Boolean) parameters.get(GENERATE_MOCK_DAO),
            (String) parameters.get(CUSTOM_SQL_MODEL),
            (File) parameters.get(SQL_XML_FILE),
            (File) parameters.get(GRAMMAR_FOLDER),
            (String) parameters.get(GRAMMAR_NAME),
            (String) parameters.get(GRAMMAR_SUFFIX),
            (String) parameters.get(ENCODING),
            (Boolean) parameters.get(CACHING),
            (Integer) parameters.get(THREAD_COUNT),
            (Boolean) parameters.get(DISABLE_TIMESTAMPS),
            (Boolean) parameters.get(DISABLE_NOTNULL_ANNOTATIONS),
            (Boolean) parameters.get(DISABLE_CHECKTHREAD_ANNOTATIONS),
            parameters,
            usingAnt);

        if  (usingAnt)
        {
            validateAntParameters(
                (AntTablesElement) parameters.get(TABLES),
                (AntExternallyManagedFieldsElement)
                    parameters.get(EXTERNALLY_MANAGED_FIELDS),
                (Path) parameters.get(CLASSPATH),
                parameters);
        }
    }

    /**
     * Validates the parameters.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param password the password.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param repository the repository.
     * @param packageName the package name.
     * @param outputDir the output folder.
     * @param header the header.
     * @param outputDirSubFolders whether to use subFolders.
     * @param extractProcedures the extract-procedures setting.
     * @param extractFunctions the extract-functions setting.
     * @param jndiDataSources the JNDI location for data sources.
     * @param generateMockDAO the generate-mock-dao-implementation setting.
     * @param customSqlModel the model for custom-sql information.
     * @param sqlXmlFile the sql.xml file.
     * @param grammarFolder the grammar folder.
     * @param grammarBundleName the grammar bundle name.
     * @param grammarSuffix the grammar suffix.
     * @param encoding the file encoding.
     * @param caching whether to use template caching or not.
     * @param threadCount the number of threads.
     * @param disableGenerationTimestamps whether to disable timestamps in generated files.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable Checkthread.org annotations.
     * @param parameters the parameter map, to store processed information
     * such as the header contents.
     * @param usingAnt whether QueryJ is executed within Ant.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     */
    @SuppressWarnings("unused,unchecked")
    protected void validateParameters(
        @Nullable final String driver,
        @Nullable final String url,
        @Nullable final String username,
        @Nullable final String password,
        @Nullable final String catalog,
        @Nullable final String schema,
        @Nullable final String repository,
        @Nullable final String packageName,
        @Nullable final File outputDir,
        @Nullable final File header,
        @NotNull final Boolean outputDirSubFolders,
        @NotNull final Boolean extractProcedures,
        @NotNull final Boolean extractFunctions,
        @Nullable final String jndiDataSources,
        @NotNull final Boolean generateMockDAO,
        @NotNull final String customSqlModel,
        @Nullable final File sqlXmlFile,
        @Nullable final File grammarFolder,
        @Nullable final String grammarBundleName,
        @Nullable final String grammarSuffix,
        @Nullable final String encoding,
        final boolean caching,
        final int threadCount,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final Map<String, String> parameters,
        final boolean usingAnt)
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
                parameters.put(HEADER, readFile(header, Charset.forName(encoding)));
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

        /* Not mandatory
        if  (customSqlModel == null)
        {
            throw new QueryJBuildException(CUSTOM_SQL_MODEL_MISSING);
        }
        */
        if  (!CUSTOM_SQL_MODEL_XML.equals(customSqlModel))
        {
            throw new UnsupportedCustomSqlFileFormatException(customSqlModel);
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
     * @param parameters the parameter map, to store processed information
     * such as the header contents.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     */
    @SuppressWarnings("unused,unchecked")
    protected void validateAntParameters(
        @Nullable final AntTablesElement tables,
        @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
        @Nullable final Path classpath,
        @NotNull final Map parameters)
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
     * @return the file contents.
     * @throws FileNotFoundException if the file is not found.
     * @throws SecurityException if the environment prevents
     * reading the file.
     * @throws IOException if the file cannot be read for any
     * other reason.
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
     * @param fileUtils the {@link FileUtils} instance.
     * @return the file contents.
     * @throws FileNotFoundException if the file is not found.
     * @throws SecurityException if the environment prevents
     * reading the file.
     * @throws IOException if the file cannot be read for any
     * other reason.
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
