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
 * Filename: TemplateFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/19/12
 * Time: 6:45 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.Template;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Placeholder processors that require access to the {@link Template} instance.
 * @param <T> the template
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 */
@SuppressWarnings("unused")
public interface TemplateFillHandler<T extends Template, P>
    extends FillHandler<P>
{
    /**
     * Retrieves the associated template.
     * @return such template.
     */
    @NotNull
    T getTemplate();
}
