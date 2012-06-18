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
 * Filename: TimestampHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Handler to provide 'timestamp' placeholder in templates.
 *
 * Date: 6/18/12
 * Time: 7:00 AM
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Handler to provide 'timestamp' placeholder in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/18
 */
public class TimestampHandler
    extends AbstractFillHandler<String>
{
    /**
     * Retrieves the placeholder.
     *
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "timestamp";
    }

    /**
     * Retrieves the timestamp.
     *
     * @return the dynamic value.
     */
    @Override
    @NotNull
    public String getValue()
    {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US).format(new Date());
    }
}