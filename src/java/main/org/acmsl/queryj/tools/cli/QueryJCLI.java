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

/*
 * Importing some Apache Commons CLI classes.
 */
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
     * Creates a new <code>QueryJCLI</code> instance.
     *
     */
    private QueryJCLI()
    {
    }

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
            helper.createCustomSqlOption(),
            helper);
    }

    /**
     * Executes <b>QueryJ</b> from the command line.
     * @param args the command-line arguments.
     * @param configurationOption the option specifying the configuration
     * properties file.
     * @param customSqlOption the option specifying the custom SQL file.
     * @param helper the <code>QueryJCLIHelper</code> instance.
     * @precondition configurationOption != null
     * @precondition customSqlOption != null
     * @precondition helper != null
     */
    protected static void executeQueryJ(
        final String[] args,
        final Option configurationOption,
        final Option customSqlOption,
        final QueryJCLIHelper helper)
    {
        Options t_Options = new Options();

        t_Options.addOption(configurationOption);
        t_Options.addOption(customSqlOption);

        CommandLineParser t_Parser = new GnuParser();
        CommandLine t_CommandLine = null;

        try
        {
            t_CommandLine = t_Parser.parse(t_Options, args);
        }
        catch  (final ParseException parseException)
        {
            helper.printError(parseException.getMessage(), System.err);
            helper.printUsage(t_Options, System.err);
        }

        if  (t_CommandLine != null)
        {
            String t_strConfigurationFileName =
                t_CommandLine.getOptionValue(CONFIGURATION_PROPERTIES_OPTION);

            String t_strCustomSqlFileName =
                t_CommandLine.getOptionValue(CUSTOM_SQL_OPTION);

            if  (!helper.validateConfigurationOption(
                     t_strConfigurationFileName))
            {
                helper.printError(
                    "Invalid configuration properties", System.err);
            }
            // Custom sql file is validated as part of QueryJ chain.
            else
            {
                executeQueryJ(
                    t_strConfigurationFileName, t_strCustomSqlFileName);
            }
        }
    }

    /**
     * Executes QueryJ using given options.
     * @param configurationFileName the name of the configuration file.
     * @param customSqlFileName the name of the custom SQL file.
     * @precondition configurationFileName != null
     * @precondition customSqlFileName != null
     */
    protected static void executeQueryJ(
        final String configurationFileName, final String customSqlFileName)
    {
        // TODO
    }
}
