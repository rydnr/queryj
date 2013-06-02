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
 * Filename: CurrentYearHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Used to fill the 'current_year' placeholder in templates.
 *
 * Date: 5/13/12
 * Time: 6:08 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.templates.NonRelevantFillHandler;

/*
 * Importing checkthreads.org annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Used to fill the 'current_year' placeholder in templates.
 * @author <a href="mailto:jose@acm-sl.org">Jose San Leandro</a>
 * @since 2012/05/13
 */
@ThreadSafe
@SuppressWarnings("unused")
public class CurrentYearHandler
    extends AbstractFillHandler<String>
    implements NonRelevantFillHandler
{

    private static final long serialVersionUID = 3601964012509602628L;

    /**
     * Returns "current_year".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "current_year";
    }

    /**
     * Retrieves the template value for that placeholder.
     * @return the dynamic value.
     */
    @NotNull
    @Override
    public String getValue()
    {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
}
