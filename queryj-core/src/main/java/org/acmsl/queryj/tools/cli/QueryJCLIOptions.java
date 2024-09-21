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
 * Filename: QueryJCLIOptions.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the names of the command-line options.
 *
 */
package org.acmsl.queryj.tools.cli;

/**
 * Defines the names of the command-line options.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface QueryJCLIOptions
{
    /**
     * The <i>help</i> option.
     */
    public static final String HELP_OPTION = "h";

    /**
     * The <i>help</i> long option.
     */
    public static final String HELP_LONG_OPTION = "help";

    /**
     * The <i>help</i> option description.
     */
    public static final String HELP_OPTION_DESCRIPTION = "This help";

    /**
     * The <i>configuration properties</i> option.
     */
    public static final String CONFIGURATION_PROPERTIES_OPTION = "c";

    /**
     * The <i>configuration properties</i> long option.
     */
    public static final String CONFIGURATION_PROPERTIES_LONG_OPTION = "config";

    /**
     * The <i>configuration properties</i> option description.
     */
    public static final String CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION =
        "The properties file with QueryJ configuration settings";

    /**
     * The <i>custom SQL</i> option.
     */
    public static final String CUSTOM_SQL_OPTION = "s";

    /**
     * The <i>custom SQL</i> long option.
     */
    public static final String CUSTOM_SQL_LONG_OPTION = "sql";

    /**
     * The <i>custom SQL</i> option description.
     */
    public static final String CUSTOM_SQL_OPTION_DESCRIPTION =
        "The XML file with all custom SQL queries.";

    /**
     * The <i>verbosity: trace</i> option.
     */
    public static final String TRACE_VERBOSITY_OPTION = "vvv";

    /**
     * The <i>verbosity: trace</i> option description.
     */
    public static final String TRACE_VERBOSITY_OPTION_DESCRIPTION =
        "Verbosity level set to 'trace'";

    /**
     * The <i>verbosity: debug</i> option.
     */
    public static final String DEBUG_VERBOSITY_OPTION = "vv";

    /**
     * The <i>verbosity: debug</i> option description.
     */
    public static final String DEBUG_VERBOSITY_OPTION_DESCRIPTION =
        "Verbosity level set to 'debug'";

    /**
     * The <i>verbosity: info</i> option.
     */
    public static final String INFO_VERBOSITY_OPTION = "v";

    /**
     * The <i>verbosity: info</i> option description.
     */
    public static final String INFO_VERBOSITY_OPTION_DESCRIPTION =
        "Verbosity level set to 'info'";
}
