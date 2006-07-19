//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Allows executing QueryJ from the command-line.
 *
 */
package org.acmsl.queryj.tools.cli;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.cli.QueryJCLIHelper;
import org.acmsl.queryj.tools.cli.QueryJCLIOptions;
import org.acmsl.queryj.tools.logging.QueryJCLILog;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJChain;
import org.acmsl.queryj.tools.QueryJCommand;

/*
 * Importing some Apache Commons CLI classes.
 */
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/*
 * Importing some JDK classes.
 */
import java.util.Properties;

/**
 * Allows executing QueryJ from the command-line.
 * @since Thu Jul 13 18:21:23 2006
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 * @version $Revision: $
 */
public final class QueryJCLI
    implements  QueryJCLIOptions
{
    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
     */
    public static void main(final String[] args)
    {
        executeQueryJ(args, QueryJCLIHelper.getInstance());
    }

    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
     * @param helper the <code>QueryJCLIHelper</code> instance.
     * @precondition helper != null
     */
    protected static void executeQueryJ(
        final String[] args, final QueryJCLIHelper helper)
    {
        executeQueryJ(
            args,
            helper.createConfigurationOption(),
            helper.createConfigurationLongOption(),
            helper.createCustomSqlOption(),
            helper.createCustomSqlLongOption(),
            helper.createVerbosityOptions(),
            helper.createHelpOption(),
            helper.createHelpLongOption(),
            helper);
    }

    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
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
     * @param helper the <code>QueryJCLIHelper</code> instance.
     * @precondition configurationOption != null
     * @precondition configurationLongOption != null
     * @precondition customSqlOption != null
     * @precondition customSqlLongOption != null
     * @precondition verbosityOptions != null
     * @precondition helpOption != null
     * @precondition helpLongOption != null
     * @precondition helper != null
     */
    protected static void executeQueryJ(
        final String[] args,
        final Option configurationOption,
        final Option configurationLongOption,
        final Option customSqlOption,
        final Option customSqlLongOption,
        final Option[] verbosityOptions,
        final Option helpOption,
        final Option helpLongOption,
        final QueryJCLIHelper helper)
    {
        Options t_Options = null;

        CommandLine t_CommandLine = null;

        try
        {
            t_Options =
                helper.createOptions(
                    configurationLongOption, 
                    customSqlLongOption,
                    verbosityOptions,
                    helpLongOption);

            t_CommandLine = new GnuParser().parse(t_Options, args);
        }
        catch  (final ParseException parseException)
        {
            try
            {
                 t_Options =
                     helper.createOptions(
                         configurationOption, 
                         customSqlOption,
                         verbosityOptions,
                         helpOption);

                t_CommandLine = new PosixParser().parse(t_Options, args);
            }
            catch  (final ParseException anotherParseException)
            {
                helper.printError(
                        parseException.getMessage() + "\n"
                      + anotherParseException.getMessage(),
                      System.err);

                helper.printUsage(
                    configurationOption,
                    configurationLongOption,
                    customSqlOption,
                    customSqlLongOption,
                    verbosityOptions,
                    helpOption,
                    helpLongOption,
                    System.err);
            }
        }

        if  (t_CommandLine != null)
        {
            if  (requestsHelp(t_CommandLine))
            {
                helper.printUsage(
                    configurationOption,
                    configurationLongOption,
                    customSqlOption,
                    customSqlLongOption,
                    verbosityOptions,
                    helpOption,
                    helpLongOption,
                    System.err);
            }
            else
            {
                String t_strConfigurationFileName =
                    t_CommandLine.getOptionValue(
                        CONFIGURATION_PROPERTIES_OPTION);

                if  (t_strConfigurationFileName == null)
                {
                    t_strConfigurationFileName =
                        t_CommandLine.getOptionValue(
                            CONFIGURATION_PROPERTIES_LONG_OPTION);
                }

                String t_strCustomSqlFileName =
                    t_CommandLine.getOptionValue(CUSTOM_SQL_OPTION);

                if  (t_strCustomSqlFileName == null)
                {
                    t_strCustomSqlFileName =
                        t_CommandLine.getOptionValue(CUSTOM_SQL_LONG_OPTION);
                }

                Properties t_ConfigurationSettings =
                    helper.readConfigurationSettings(
                        t_strConfigurationFileName);

                if  (t_ConfigurationSettings == null)
                {
                    helper.printError(
                        "Invalid configuration properties", System.err);
                }
                // Custom sql file is validated as part of QueryJ chain.
                else
                {
                    try
                    {
                        executeQueryJ(
                            t_ConfigurationSettings,
                            retrieveLogThreshold(t_CommandLine),
                            t_strCustomSqlFileName);
                    }
                    catch  (final QueryJBuildException buildException)
                    {
                        helper.printError(
                            "QueryJ failed. Reason: "
                            + buildException.getMessage(),
                            System.err);
                    }
                }
            }
        }
    }

    /**
     * Executes QueryJ using given options.
     * @param configurationSettings the configuration settings.
     * @param logThreshold the log threshold.
     * @param customSqlFileName the name of the custom SQL file.
     * @throws QueryJBuildException if QueryJ fails.
     * @precondition configurationFileName != null
     * @precondition customSqlFileName != null
     */
    protected static void executeQueryJ(
        final Properties configurationSettings,
        final int logThreshold,
        final String customSqlFileName)
      throws  QueryJBuildException
    {
        new CLIQueryJChain(configurationSettings, logThreshold).process();
    }

    /**
     * Customizes <code>QueryJChain</code> to get parameters from Ant.
     * @author <a href="mailto:chous@acm-sl.org"
               >Jose San Leandro</a>
     */
    protected static class CLIQueryJChain
        extends  QueryJChain
    {
        /**
         * The log threshold.
         */
        private int m__iLogThreshold;

        /**
         * Creates an <code>CLIQueryJChain</code> instance.
         * @param settings the settings.
         * @param logThreshold the log threshold.
         */
        public CLIQueryJChain(
            final Properties settings, final int logThreshold)
        {
            super(settings);
            immutableSetLogThreshold(logThreshold);
        }

        /**
         * Specifies the log threshold.
         * @param threshold such threshold.
         */
        protected final void immutableSetLogThreshold(final int threshold)
        {
            m__iLogThreshold = threshold;
        }

        /**
         * Specifies the log threshold.
         * @param threshold such threshold.
         */
        protected void setLogThreshold(final int threshold)
        {
            immutableSetLogThreshold(threshold);
        }

        /**
         * Retrieves the log threshold.
         * @return such information.
         */
        public int getLogThreshold()
        {
            return m__iLogThreshold;
        }

        /**
         * Builds the command.
         * @return the initialized command.
         */
        protected QueryJCommand buildCommand()
        {
            return
                buildCommand(
                    new QueryJCommand(
                        new QueryJCLILog(
                            getLogThreshold(), System.err)));
        }
    }

    /**
     * Retrieves the log threshold.
     * @param commandLine the command line.
     * @returh such threshold.
     * @precondition commandLine != null
     */
    protected static int retrieveLogThreshold(final CommandLine commandLine)
    {
        int result = QueryJLog.ERROR;

        String t_strOption =
            commandLine.getOptionValue(TRACE_VERBOSITY_OPTION);

        if  (t_strOption != null)
        {
            result = QueryJLog.TRACE;
        }
        else
        {
            t_strOption =
                commandLine.getOptionValue(DEBUG_VERBOSITY_OPTION);

            if  (t_strOption != null)
            {
                result = QueryJLog.DEBUG;
            }
            else
            {
                t_strOption =
                    commandLine.getOptionValue(INFO_VERBOSITY_OPTION);
            }
        }

        return result;
    }

    /**
     * Checks whether the user has requested the help message.
     * @param commandLine the command line.
     * @return <code>true</code> in such case.
     * @precondition commandLine != null
     */
    protected static boolean requestsHelp(final CommandLine commandLine)
    {
        boolean result = commandLine.hasOption(HELP_OPTION);

        if  (!result)
        {
            result = commandLine.hasOption(HELP_LONG_OPTION);
        }

        return result;
    }
}
