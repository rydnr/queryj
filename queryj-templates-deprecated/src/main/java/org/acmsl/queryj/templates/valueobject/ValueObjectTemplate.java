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
 * Filename: ValueObjectTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate ValueObject sources.
 *
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.api.AbstractBasePerTableTemplate;
import org.acmsl.queryj.api.PerTableTemplateContext;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.STGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to generate <i>ValueObject</i> sources.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ValueObjectTemplate
    extends AbstractBasePerTableTemplate<PerTableTemplateContext>
{
    private static final long serialVersionUID = -3590003720847495760L;

    /**
     * Builds a <code>ValueObjectTemplate</code> using given
     * information.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     */
    public ValueObjectTemplate(@NotNull final PerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public STGroup retrieveGroup()
    {
        return retrieveGroup("org/acmsl/queryj/vo/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "ValueObject".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "ValueObject";
    }
}
