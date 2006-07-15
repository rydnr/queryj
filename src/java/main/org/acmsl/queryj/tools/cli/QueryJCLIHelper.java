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
import java.util.Properties;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Provides some useful methods for executing QueryJ from the command-line.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class QueryJCLIHelper
    implements  QueryJCLIOptions,
                Singleton,
                Utils
{
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
     * Creates the command-line option for the <i>custom SQL</i>.
     * @return such <code>Option</code> instance.
     */
    public Option createCustomSqlOption()
    {
        return
            OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription(CUSTOM_SQL_OPTION_DESCRIPTION)
                .create(CUSTOM_SQL_OPTION);
    }

    /**
     * Prints an error message.
     * @param message the message.
     * @param printStream where to print the message to.
     * @precondition message != null
     * @precondition printStream != null
     */
    public void printError(final String message, final PrintStream printStream)
    {
        printStream.println("Error: " + message);
    }

    /**
     * Prints the <i>Usage</i> message.
     * @param options the command-line options.
     * @param printStream where to print the message to.
     * @precondition options != null
     * @precondition printStream != null
     */
    public void printUsage(
        final Options options, final PrintStream printStream)
    {
        new HelpFormatter().printHelp("QueryJ", options);
    }

    /**
     * Validates the configuration properties specified as command-line
     * option.
     * @param configurationFile the file to validate.
     * @return the <code>Properties</code> instance with the configuration
     * settings.
     */
    public Properties readConfigurationSettings(
        final String configurationFile)
    {
        Properties result = null;

        if  (configurationFile != null)
        {
            InputStream stream = null;

            try
            {
                File t_File = new File(configurationFile);

                if  (   (t_File.exists())
                        && (t_File.canRead()))
                {
                    stream = new FileInputStream(t_File);
                    result = readConfigurationSettings(stream, false);
                }

                if  (result == null)
                {
                    stream = getClass().getResourceAsStream(configurationFile);
                    result = readConfigurationSettings(stream, true);

                    if  (result != null)
                    {
                        Log t_Log =
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
            catch  (final IOException ioException)
            {
                Log t_Log =
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
                    catch  (final IOException ioException)
                    {
                        Log t_Log =
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
     * @precondition stream != null
     */
    public Properties readConfigurationSettings(
        final InputStream stream, final boolean log)
    {
        Properties result = new Properties();

        try
        {
            result.load(stream);
        }
        catch  (final IOException ioException)
        {
            result = null;

            if  (log)
            {
                Log t_Log =
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
