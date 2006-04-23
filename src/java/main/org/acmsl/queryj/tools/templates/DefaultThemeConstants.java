/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Description: Defines some constants for the default theme.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing JDK classes.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Defines some constants for the default theme.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface DefaultThemeConstants
{
    /**
     * The starting year.
     */
    public static final Integer STARTING_YEAR = new Integer(2002);

    /**
     * The timestamp formatter.
     */
    public static final DateFormat TIMESTAMP_FORMATTER =
        new SimpleDateFormat("HH:mm:ss.SSS yyyy/MM/dd");

    /**
     * The template name.
     */
    public static final String TEMPLATE_NAME = "source";
}
