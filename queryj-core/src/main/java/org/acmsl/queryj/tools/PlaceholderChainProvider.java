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
 * Filename: PlaceholderChainProvider.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides the chain required to resolve placeholders in
 *              templates.
 *
 * Date: 6/6/13
 * Time: 10:30 PM
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Provides the chain required to resolve placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/06
 */
@SuppressWarnings("unused")
public interface PlaceholderChainProvider<FH extends FillHandler<?>>
{
    /**
     * Retrieves the custom placeholder handlers.
     * @return such handlers.
     */
    @NotNull
    public List<FH> getHandlers();
}
