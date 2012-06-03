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
import org.acmsl.queryj.tools.templates.TemplateContext;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Base implementation of {@link TemplateFillHandler}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/19
 */
public abstract class AbstractTemplateContextFillHandler<C extends TemplateContext,P>
    extends AbstractFillHandler<P>
    implements TemplateContextFillHandler<C,P>
{
    /**
     * The template context.
     */
    private C templateContext;

    /**
     * Creates a new {@link AbstractTemplateContextFillHandler} associated to given
     * {@link TemplateContext}.
     * @param context the template.
     */
    protected AbstractTemplateContextFillHandler(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    protected final void immutableSetTemplateContext(@NotNull final C context)
    {
        this.templateContext = context;
    }

    /**
     * Specifies the template context.
     * @param context the context.
     */
    @SuppressWarnings("unused")
    protected void setTemplateContext(@NotNull final C context)
    {
        immutableSetTemplateContext(context);
    }

    /**
     * Retrieves the template context.
     * @return such information.
     */
    @Override
    @NotNull
    public C getTemplateContext()
    {
        return templateContext;
    }

    /**
     * Retrieves the template value for that placeholder.
     *
     * @return the dynamic value.
     */
    @NotNull
    @Override
    public P getValue()
    {
        return getValue(getTemplateContext());
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    protected abstract P getValue(@NotNull final C context);

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.templateContext).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AbstractTemplateContextFillHandler other = (AbstractTemplateContextFillHandler) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.templateContext, other.templateContext)
            .isEquals();
    }
}