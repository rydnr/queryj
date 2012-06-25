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
 * Filename: JavaTemplateDefaults.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains default subtemplates for all templates.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing JDK classes.
 */
import java.util.Calendar;

/**
 * Contains default subtemplates for all templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface JavaTemplateDefaults
{
    /**
     * The ACM-SL header.
     */
    public static final String ACMSL_HEADER =
          "/*\n"
        + "                        QueryJ\n"
        + "\n"
        + "    Copyright (C) 2002-today "
        + " - Jose San Leandro Armendariz\n"
        + "                         chous@acm-sl.org\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later "
        + "version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be "
        + "useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free "
        + "Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "    Contact info: jose.sanleandro@acm-sl.com\n"
        + "\n";

    /**
     * The Ventura24 header.
     */
    public static final String VENTURA24_HEADER =
          "/*\n"
        + "                 File automatically generated by QueryJ !!\n"
        + "                 Edit at your own risk.\n"
        + "\n"
        + "    Copyright (C) "
        + Calendar.getInstance().get(Calendar.YEAR)
        + " Ventura24 S.L.\n"
        + "\n"
        + "    Copyright and license details are included in Ventura24 license file.\n"
        + "\n";

    /**
     * The default header.
     */
    public static final String HEADER =
        VENTURA24_HEADER;
}
