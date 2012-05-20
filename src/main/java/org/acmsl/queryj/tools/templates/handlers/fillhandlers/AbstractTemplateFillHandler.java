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
 * Filename: AbstractTemplateFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/19/12
 * Time: 6:53 PM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.Template;
import org.jetbrains.annotations.NotNull;

/**
 * Base implementation of {@link TemplateFillHandler}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/19
 */
public abstract class AbstractTemplateFillHandler<T extends Template,P>
    extends AbstractFillHandler<P>
    implements TemplateFillHandler<T,P>
{
    /**
     * The template
     */
    private T template;

    /**
     * Creates a new {@link AbstractTemplateFillHandler} associated to given
     * {@link Template}.
     * @param template the template.
     */
    protected AbstractTemplateFillHandler(@NotNull final T template)
    {
        immutableSetTemplate(template);
    }

    /**
     * Specifies the template.
     * @param templ the template.
     */
    protected final void immutableSetTemplate(@NotNull final T templ)
    {
        this.template = templ;
    }

    /**
     * Specifies the template.
     * @param templ the template.
     */
    @SuppressWarnings("unused")
    protected void setTemplate(@NotNull final T templ)
    {
        immutableSetTemplate(templ);
    }

    /**
     * Retrieves the template.
     * @return such information.
     */
    @Override
    @NotNull
    public T getTemplate()
    {
        return template;
    }
}
