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
 * Description: Defines the names of the command-line options.
 *
 */
package org.acmsl.queryj.tools.cli;

/**
 * Defines the names of the command-line options.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 * @version $Revision: $
 */
public interface QueryJCLIOptions
{
    /**
     * The <i>configuration properties</i> option.
     */
    public static final String CONFIGURATION_PROPERTIES_OPTION = "config";

    /**
     * The <i>configuration properties</i> option description.
     */
    public static final String CONFIGURATION_PROPERTIES_OPTION_DESCRIPTION =
        "The properties file with QueryJ configuration settings";

    /**
     * The <i>custom SQL</i> option.
     */
    public static final String CUSTOM_SQL_OPTION = "sql";

    /**
     * The <i>custom SQL</i> option description.
     */
    public static final String CUSTOM_SQL_OPTION_DESCRIPTION =
        "The XML file with all custom SQL queries.";
}
