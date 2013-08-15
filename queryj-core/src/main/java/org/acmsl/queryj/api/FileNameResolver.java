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
 * Filename: FileNameResolver.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Implementations are able to retrieve the file name for each
 *              template.
 *
 * Date: 2013/08/06
 * Time: 17:07
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Implementations are able to retrieve the file name for each
 * template.
 * @since 3.0
 */
public interface FileNameResolver<C extends QueryJTemplateContext>
{
    /**
     * Retrieves given template's file name.
     * @param context the template context.
     * @return such name.
     */
    @NotNull
    String retrieveTemplateFileName(@NotNull final C context);
}
