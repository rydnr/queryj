/*
                        queryj

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
 * Filename: TemplateTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: A template used to generate QueryJ template classes.
 *
 * Date: 2013/08/15
 * Time: 07:49
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.Literals;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * A template used to generate QueryJ {@link org.acmsl.queryj.api.Template} classes.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/15 07:49
 */
@ThreadSafe
public class TemplateTemplate<C extends TemplatePackagingContext>
    extends AbstractTemplatePackagingTemplate<C>
{
    private static final long serialVersionUID = -392473631653215353L;

    /**
     * Builds an <code>TemplateTemplate</code> using given
     * information.
     * @param context the {@link TemplatePackagingContext} instance.
     */
    protected TemplateTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.TEMPLATE;
    }
}
