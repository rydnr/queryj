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
 * Filename: QueryJCLI.java
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
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.tools.logging.QueryJCLILog;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJChain;

/*
 * Importing some Apache Commons CLI classes.
 */
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/*
 * Importing Apache Commons Configuration classes,
 */
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing JetBrains annotations,
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Allows executing QueryJ from the command-line.
 * @since Thu Jul 13 18:21:23 2006
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public final class QueryJCLI
    implements  QueryJCLIOptions
{
    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
     */
    public static void main(@NotNull final String[] args)
    {
        executeQueryJ(args, QueryJCLIHelper.getInstance());
    }

    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
     * @param helper the <code>QueryJCLIHelper</code> instance.
     */
    protected static void executeQueryJ(
        final String[] args, @NotNull final QueryJCLIHelper helper)
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
     */
    protected static void executeQueryJ(
        @NotNull final String[] args,
        @NotNull final Option configurationOption,
        @NotNull final Option configurationLongOption,
        @NotNull final Option customSqlOption,
        @NotNull final Option customSqlLongOption,
        @NotNull final Option[] verbosityOptions,
        @NotNull final Option helpOption,
        @NotNull final Option helpLongOption,
        @NotNull final QueryJCLIHelper helper)
    {
        @Nullable Options t_Options;

        @Nullable CommandLine t_CommandLine = null;

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
        catch  (@NotNull final ParseException parseException)
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
            catch  (@NotNull final ParseException anotherParseException)
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

                try
                {
                    @NotNull final Configuration t_ConfigurationSettings =
                        new PropertiesConfiguration(t_strConfigurationFileName);

                    // Custom sql file is validated as part of QueryJ chain.
                    try
                    {
                        executeQueryJ(
                            t_ConfigurationSettings,
                            retrieveLogThreshold(t_CommandLine),
                            t_strCustomSqlFileName);
                    }
                    catch  (@NotNull final QueryJBuildException buildException)
                    {
                        helper.printError(
                            "QueryJ failed. Reason: "
                            + buildException.getMessage(),
                            System.err);
                    }
                }
                catch (@NotNull final ConfigurationException invalidSettings)
                {
                    helper.printError(
                        "Invalid configuration properties", System.err);
                }
            }
        }
    }

    /**
     * Executes QueryJ using given options.
     * @param configurationSettings the configuration settings.
     * @param logThreshold the log threshold.
     * @throws QueryJBuildException if QueryJ fails.
     */
    @SuppressWarnings("unused")
    protected static void executeQueryJ(
        @NotNull final Configuration configurationSettings,
        final int logThreshold,
        @NotNull final String customSqlFile)
      throws  QueryJBuildException
    {
        new QueryJChain<QueryJCommandHandler<QueryJCommand>>()
            .process(
                new ConfigurationQueryJCommandImpl(configurationSettings, new QueryJCLILog(logThreshold, System.err)));
    }

    /**
     * Retrieves the log threshold.
     * @param commandLine the command line.
     * @return such threshold.
     */
    protected static int retrieveLogThreshold(@NotNull final CommandLine commandLine)
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

                if  (t_strOption != null)
                {
                    result = QueryJLog.INFO;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether the user has requested the help message.
     * @param commandLine the command line.
     * @return <code>true</code> in such case.
     */
    protected static boolean requestsHelp(@NotNull final CommandLine commandLine)
    {
        boolean result = commandLine.hasOption(HELP_OPTION);

        if  (!result)
        {
            result = commandLine.hasOption(HELP_LONG_OPTION);
        }

        return result;
    }
}
