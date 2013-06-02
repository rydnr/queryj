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
 * Filename: QueryJCLIHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods for executing QueryJ from the
 *              command-line
 *
 */
package org.acmsl.queryj.tools.cli;

/*
 * Importing some Apache Commons CLI classes.
 */
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing JetBrains annotations,
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful methods for executing QueryJ from the command-line.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class QueryJCLIHelper
    implements  QueryJCLIOptions,
                Singleton,
                Utils
{
    /**
     * A cached empty <code>Option</code> array.
     */
    public static final Option[] EMPTY_OPTION_ARRAY = new Option[0];

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class QueryJCLIHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final QueryJCLIHelper SINGLETON = new QueryJCLIHelper();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected QueryJCLIHelper() {};

    /**
     * Retrieves a <code>QueryJCLIHelper</code> instance.
     * @return such instance.
     */
    @NotNull
    public static QueryJCLIHelper getInstance()
    {
        return QueryJCLIHelperSingletonContainer.SINGLETON;
    }

    /**
     * Creates the command-line option for the <i>configuration properties</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createConfigurationOption()
    {
        return
            OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription(CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION)
                .create(CONFIGURATION_PROPERTIES_OPTION);
    }

    /**
     * Creates the command-line long option for the
     * <i>configuration properties</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createConfigurationLongOption()
    {
        return
            OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription(CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION)
                .create(CONFIGURATION_PROPERTIES_LONG_OPTION);
    }

    /**
     * Creates the command-line option for the <i>custom SQL</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createCustomSqlOption()
    {
        Option result =
            OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription(CUSTOM_SQL_OPTION_DESCRIPTION)
                .withLongOpt(CUSTOM_SQL_LONG_OPTION)
                .isRequired(false)
                .create(CUSTOM_SQL_OPTION);

        return result;
    }

    /**
     * Creates the command-line long option for the <i>custom SQL</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createCustomSqlLongOption()
    {
        Option result =
            OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription(CUSTOM_SQL_OPTION_DESCRIPTION)
                .isRequired(false)
                .create(CUSTOM_SQL_LONG_OPTION);

        return result;
    }

    /**
     * Creates the command-line option for the verbosity levels.
     * @return such <code>Option</code> instances.
     */
    @NotNull
    public Option[] createVerbosityOptions()
    {
        @NotNull final Collection<Option> t_cResult = new ArrayList<Option>();

        Option t_Option =
            OptionBuilder
                .withArgName("v")
                .withDescription(INFO_VERBOSITY_OPTION_DESCRIPTION)
                .isRequired(false)
                .create(INFO_VERBOSITY_OPTION);
        t_cResult.add(t_Option);

        t_Option =
            OptionBuilder
                .withArgName("vv")
                .withDescription(DEBUG_VERBOSITY_OPTION_DESCRIPTION)
                .isRequired(false)
                .create(DEBUG_VERBOSITY_OPTION);

        t_cResult.add(t_Option);

        t_Option =
            OptionBuilder
                .withArgName("vvv")
                .withDescription(TRACE_VERBOSITY_OPTION_DESCRIPTION)
                .isRequired(false)
                .create(TRACE_VERBOSITY_OPTION);
        t_cResult.add(t_Option);

        return t_cResult.toArray(EMPTY_OPTION_ARRAY);
    }

    /**
     * Creates the command-line option for the <i>help</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createHelpOption()
    {
        Option result =
            OptionBuilder
                .withDescription(HELP_OPTION_DESCRIPTION)
                .isRequired(false)
                .create(HELP_OPTION);

        return result;
    }

    /**
     * Creates the command-line long option for the <i>help</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createHelpLongOption()
    {
        Option result =
            OptionBuilder
                .withDescription(HELP_OPTION_DESCRIPTION)
                .withLongOpt(HELP_LONG_OPTION)
                .isRequired(false)
                .create(HELP_LONG_OPTION);

        return result;
    }

    /**
     * Creates the <code>Options</code> instance for given 
     * <code>Option</code>s.
     * @param configurationOption the option specifying the configuration
     * properties file.
     * @param customSqlOption the option specifying the custom SQL file.
     * @param verbosityOptions the options specifying the verbosity.
     * @param helpOption the option specifying the help.
     * @return the <code>Options</code> instance.
     */
    @NotNull
    public Options createOptions(
        @NotNull final Option configurationOption,
        @NotNull final Option customSqlOption,
        @NotNull final Option[] verbosityOptions,
        @NotNull final Option helpOption)
    {
        @NotNull final Options result = new Options();

        addOptions(
            result,
            configurationOption,
            customSqlOption,
            verbosityOptions,
            helpOption);

        return result;
    }

    /**
     * Creates the <code>Options</code> instance for given 
     * <code>Option</code>s.
     * @param options the <code>Options</code> instance to update.
     * @param configurationOption the option specifying the configuration
     * properties file.
     * @param customSqlOption the option specifying the custom SQL file.
     * @param verbosityOptions the options specifying the verbosity.
     * @param helpOption the option specifying the help.
     */
    protected void addOptions(
        @NotNull final Options options,
        @NotNull final Option configurationOption,
        @NotNull final Option customSqlOption,
        @Nullable final Option[] verbosityOptions,
        @NotNull final Option helpOption)
    {
        options.addOption(configurationOption);
        options.addOption(customSqlOption);

        final int t_iCount =
            (verbosityOptions != null) ? verbosityOptions.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {            
            options.addOption(verbosityOptions[t_iIndex]);
        }

        options.addOption(helpOption);
    }

    /**
     * Prints an error message.
     * @param message the message.
     * @param printStream where to print the message to.
     */
    public void printError(final String message, @NotNull final PrintStream printStream)
    {
        printStream.println("Error: " + message);
    }

    /**
     * Prints the <i>Usage</i> message.
     * @param options the command-line options.
     * @param printStream where to print the message to.
     */
    @SuppressWarnings("unused")
    public void printUsage(
        final Options options, final PrintStream printStream)
    {
        new HelpFormatter().printHelp("QueryJ", options, true);
    }

    /**
     * Prints the <i>Usage</i> message.
     * @param configurationOption the option specifying the configuration
     * properties file.
     * @param configurationLongOption the long option specifying the
     * configuration properties file.
     * @param customSqlOption the option specifying the custom SQL file.
     * @param customSqlLongOption the long option specifying the custom SQL
     * file.
     * @param verbosityOptions the options specifying the verbosity.
     * @param helpOption the option specifying the help.
     * @param helpLongOption the long option specifying the help.
     * @param printStream where to print the message to.
     */
    @SuppressWarnings("unused")
    public void printUsage(
        final Option configurationOption,
        final Option configurationLongOption,
        final Option customSqlOption,
        final Option customSqlLongOption,
        final Option[] verbosityOptions,
        final Option helpOption,
        final Option helpLongOption,
        final PrintStream printStream)
    {
        @NotNull final Options t_Options = new Options();

        addOptions(
            t_Options,
            configurationOption, 
            customSqlOption,
            verbosityOptions,
            helpOption);

        addOptions(
            t_Options,
            configurationLongOption, 
            customSqlLongOption,
            verbosityOptions,
            helpLongOption);

        new HelpFormatter().printHelp("QueryJ", t_Options);
    }

    /**
     * Validates the configuration properties specified as command-line
     * option.
     * @param configurationFile the file to validate.
     * @return the <code>Properties</code> instance with the configuration
     * settings.
     */
    @Nullable
    public Properties readConfigurationSettings(
        @Nullable final String configurationFile)
    {
        @Nullable Properties result = null;

        if  (configurationFile != null)
        {
            @Nullable InputStream stream = null;

            try
            {
                @NotNull final File t_File = new File(configurationFile);

                if  (   (t_File.exists())
                     && (t_File.canRead()))
                {
                    stream = new FileInputStream(t_File);

                    result = readConfigurationSettings(stream, false);
                }

                if  (result == null)
                {
                    stream = QueryJCLIHelper.class.getResourceAsStream(configurationFile);
                    result = readConfigurationSettings(stream, true);

                    if  (result != null)
                    {
                        @Nullable final Log t_Log =
                            UniqueLogFactory.getLog(QueryJCLIHelper.class);

                        if  (t_Log != null)
                        {
                            t_Log.info(
                                  "Configuration properties have been read "
                                + "from classpath");
                        }
                    }
                }
            }
            catch  (@NotNull final IOException ioException)
            {
                @Nullable final Log t_Log =
                    UniqueLogFactory.getLog(QueryJCLIHelper.class);

                if  (t_Log != null)
                {
                    t_Log.info(
                        "Cannot read the configuration properties file.",
                        ioException);
                }
            }
            finally
            {
                if  (stream != null)
                {
                    try
                    {
                        stream.close();
                    }
                    catch  (@NotNull final IOException ioException)
                    {
                        @Nullable final Log t_Log =
                            UniqueLogFactory.getLog(QueryJCLIHelper.class);

                        if  (t_Log != null)
                        {
                            t_Log.info(
                                  "Cannot close the stream opened to read "
                                + "the configuration properties.",
                                ioException);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Reads the configuration properties specified as a command-line
     * option.
     * @param stream the input stream of the properties file.
     * @param log whether to log errors or not.
     * @return the <code>Properties</code> instance with the configuration
     * settings.
     */
    @Nullable
    public Properties readConfigurationSettings(
        @NotNull final InputStream stream, final boolean log)
    {
        @Nullable Properties result = new Properties();

        try
        {
            result.load(stream);
        }
        catch  (@NotNull final IOException ioException)
        {
            result = null;

            if  (log)
            {
                @Nullable final Log t_Log =
                    UniqueLogFactory.getLog(QueryJCLIHelper.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot read the configuration properties file.",
                        ioException);
                }
            }
        }

        return result;
    }
}
