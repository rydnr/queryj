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
 * Filename: DomainCLIHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods for executing QueryJ for Domain
 *              sources from the command-line.
 *
 */
package org.acmsl.queryj.tools.cli;

/*
 * Importing some Apache Commons CLI classes.
 */
import org.acmsl.queryj.Literals;
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
 * Provides some useful methods for executing QueryJ for Domain sources from the command-line.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DomainCLIHelper
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
    private static class DomainCLIHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DomainCLIHelper SINGLETON = new DomainCLIHelper();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DomainCLIHelper() {};

    /**
     * Retrieves a <code>DomainCLIHelper</code> instance.
     * @return such instance.
     */
    @NotNull
    public static DomainCLIHelper getInstance()
    {
        return DomainCLIHelperSingletonContainer.SINGLETON;
    }

    /**
     * Creates the command-line option for the <i>configuration properties</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createConfigurationOption()
    {
        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription(CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION);

        return OptionBuilder.create(CONFIGURATION_PROPERTIES_OPTION);
    }

    /**
     * Creates the command-line long option for the
     * <i>configuration properties</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createConfigurationLongOption()
    {
        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription(CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION);
        return OptionBuilder.create(CONFIGURATION_PROPERTIES_LONG_OPTION);
    }

    /**
     * Creates the command-line option for the verbosity levels.
     * @return such <code>Option</code> instances.
     */
    @NotNull
    public Option[] createVerbosityOptions()
    {
        @NotNull final Collection<Option> t_cResult = new ArrayList<>();

        OptionBuilder.withArgName("v");
        OptionBuilder.withDescription(INFO_VERBOSITY_OPTION_DESCRIPTION);
        OptionBuilder.isRequired(false);
        @NotNull Option t_Option = OptionBuilder.create(INFO_VERBOSITY_OPTION);
        t_cResult.add(t_Option);

        OptionBuilder.withArgName("vv");
        OptionBuilder.withDescription(DEBUG_VERBOSITY_OPTION_DESCRIPTION);
        OptionBuilder.isRequired(false);
        t_Option = OptionBuilder.create(DEBUG_VERBOSITY_OPTION);;

        t_cResult.add(t_Option);

        OptionBuilder.withArgName("vvv");
        OptionBuilder.withDescription(TRACE_VERBOSITY_OPTION_DESCRIPTION);
        OptionBuilder.isRequired(false);
        t_Option = OptionBuilder.create(TRACE_VERBOSITY_OPTION);
        t_cResult.add(t_Option);

        return t_cResult.toArray(EMPTY_OPTION_ARRAY);
    }

    /**
     * Creates the command-line option for the <i>help</i>.
     * @return such <code>Option</code> instance.
     */
    @NotNull
    public Option createHelpOption()
    {
        OptionBuilder.withDescription(HELP_OPTION_DESCRIPTION);
        OptionBuilder.isRequired(false);
        return OptionBuilder.create(HELP_OPTION);
    }

    /**
     * Creates the command-line long option for the <i>help</i>.
     * @return such <code>Option</code> instance.
     */
    @NotNull
    public Option createHelpLongOption()
    {
        OptionBuilder.withDescription(HELP_OPTION_DESCRIPTION);
        OptionBuilder.withLongOpt(HELP_LONG_OPTION);
        OptionBuilder.isRequired(false);
        return OptionBuilder.create(HELP_LONG_OPTION);
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
        @NotNull final Option[] verbosityOptions,
        @NotNull final Option helpOption)
    {
        @NotNull final Options result = new Options();

        addOptions(
            result,
            configurationOption,
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
        @Nullable final Option[] verbosityOptions,
        @NotNull final Option helpOption)
    {
        options.addOption(configurationOption);

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
    public void printError(@NotNull final String message, @NotNull final PrintStream printStream)
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
        @NotNull final Options options, @NotNull final PrintStream printStream)
    {
        new HelpFormatter().printHelp(Literals.QUERY_J, options, true);
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
        @NotNull final Option configurationOption,
        @NotNull final Option configurationLongOption,
        @NotNull final Option[] verbosityOptions,
        @NotNull final Option helpOption,
        @NotNull final Option helpLongOption,
        @NotNull final PrintStream printStream)
    {
        @NotNull final Options t_Options = new Options();

        addOptions(
            t_Options,
            configurationOption, 
            verbosityOptions,
            helpOption);

        addOptions(
            t_Options,
            configurationLongOption, 
            verbosityOptions,
            helpLongOption);

        new HelpFormatter().printHelp(Literals.QUERY_J, t_Options);
    }

    /**
     * Validates the configuration properties specified as command-line
     * option.
     * @param configurationFile the file to validate.
     * @return the <code>Properties</code> instance with the configuration
     * settings.
     */
    @SuppressWarnings("unused")
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
                    stream = DomainCLIHelper.class.getResourceAsStream(configurationFile);
                    result = readConfigurationSettings(stream, true);

                    if  (result != null)
                    {
                        @Nullable final Log t_Log =
                            UniqueLogFactory.getLog(DomainCLIHelper.class);

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
                    UniqueLogFactory.getLog(DomainCLIHelper.class);

                if  (t_Log != null)
                {
                    t_Log.info(
                        Literals.CANNOT_READ_THE_CONFIGURATION_PROPERTIES_FILE,
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
                            UniqueLogFactory.getLog(DomainCLIHelper.class);

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
                    UniqueLogFactory.getLog(DomainCLIHelper.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.CANNOT_READ_THE_CONFIGURATION_PROPERTIES_FILE,
                        ioException);
                }
            }
        }

        return result;
    }
}
