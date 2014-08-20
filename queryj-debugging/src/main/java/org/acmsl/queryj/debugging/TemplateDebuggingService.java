/*
                        QueryJ Core

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
 * Filename: TemplateDebuggingService.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Services able to debug templates.
 *
 * Date: 2014/06/25
 * Time: 12:31
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.DevelopmentModeException;
import org.acmsl.queryj.api.TemplateContext;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Services able to debug templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @param <C> the template context.
 * @since 3.0
 * Created: 2014/06/25 12:31
 */
@ThreadSafe
public interface TemplateDebuggingService<C extends TemplateContext>
{
    /**
     * Debugs given template.
     * @param template the template to debug.
     * @param context the context.
     * @param output the current template output.
     * @return the {@link TemplateDebuggingCommand}.
     * @throws DevelopmentModeException if the debug session must stop.
     */
    @NotNull
    TemplateDebuggingCommand debugTemplate(
        @NotNull final ST template, @NotNull final C context, @NotNull final String output)
        throws DevelopmentModeException;
}
