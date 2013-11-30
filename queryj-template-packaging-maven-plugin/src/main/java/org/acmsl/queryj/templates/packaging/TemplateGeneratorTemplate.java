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
 * Filename: TemplateGeneratorTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template to build QueryJ's TemplateGenerator sources.
 *
 * Date: 2013/08/16
 * Time: 09:26
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Template to build QueryJ's TemplateGenerator sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/16 09/26
 */
@ThreadSafe
public class TemplateGeneratorTemplate<C extends TemplatePackagingContext>
    extends AbstractTemplatePackagingTemplate<C>
{
    private static final long serialVersionUID = 6495367163153844355L;

    /**
     * Creates a TemplateGeneratorTemplate instance.
     * @param context the {@link TemplatePackagingContext context}.
     */
    public TemplateGeneratorTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     *
     * @return such name.
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return "TemplateGenerator";
    }
}
