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
 * Filename: CopyrightYearsHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves the copyright years placeholder.
 *
 * Date: 5/19/12
 * Time: 6:24 PM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Calendar;

/**
 * Resolves the copyright years placeholder.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/19
 */
@SuppressWarnings("unused")
public class CopyrightYearsHandler
    extends AbstractFillHandler<Integer[]>
{
    /**
     * The starting year.
     */
    public static final int STARTING_YEAR = 2002;

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "copyright_years";
    }

    /**
     * Retrieves the copyright years.
     * @return the dynamic values.
     */
    @NotNull
    @Override
    public Integer[] getValue()
    {
        return
            new Integer[]
            {
                STARTING_YEAR,
                Calendar.getInstance().get(Calendar.YEAR)
        };
    }
}
