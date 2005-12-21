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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate system function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.functions.FunctionsTemplate;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Is able to generate system function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class SystemFunctionsTemplate
    extends  FunctionsTemplate
{
    /**
     * The capitalized words.
     */
    protected static final String[] CAPITALIZED_WORDS =
        new String[]
        {
            "user",
            "insert",
            "id"
        };

    /**
     * The field types.
     */
    public static final String[] FIELD_TYPES =
        new String[]
        {
            "String",
            "Long"
        };

    /**
     * Builds a SystemFunctionsTemplate using given information.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public SystemFunctionsTemplate(
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote)
    {
        super(
            "system",
            "System",
            packageName,
            engineName,
            engineVersion,
            quote);
    }
}
