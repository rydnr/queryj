/*
                        QueryJ Template Packaging

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
 * Filename: TemplateDefOutput.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The supported outputs in template defs.
 *
 * Date: 2013/08/14
 * Time: 09:16
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnumUtils;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The supported outputs in template defs.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/14
 */
@ThreadSafe
public enum TemplateDefOutput
{
    /**
     * Java flavour.
     */
    JAVA,

    /**
     * Cucumber flavour.
     */
    CUCUMBER,

    /**
     * Properties files.
     */
    PROPERTIES,

    /**
     * DBSD files.
     */
    DBSD;

    /**
     * Retrieves the template def output for given value.
     * @param type the value.
     * @return the enum.
     */
    public static TemplateDefOutput getEnumFromString(@NotNull final String type)
    {
        return EnumUtils.getInstance().getEnumFromString(TemplateDefOutput.class, type);
    }
}
