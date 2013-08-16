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
 * Filename: TemplateGeneratorTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template factory used in the process to build QueryJ's
 *              TemplateGenerator sources.
 *
 * Date: 2013/08/16
 * Time: 11:15
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
 * Template factory used in the process to build QueryJ's TemplateGenerator sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/16 11/15
 */
@ThreadSafe
public class TemplateGeneratorTemplateFactory
    implements TemplatePackagingTemplateFactory<TemplateGeneratorTemplate<TemplatePackagingContext>, TemplatePackagingContext>
{
    /**
     * Generates a template.
     *
     * @param context the context.
     * @return such template.
     */
    @NotNull
    @Override
    public TemplateGeneratorTemplate<TemplatePackagingContext> createTemplate(
        @NotNull final TemplatePackagingContext context)
    {
        return new TemplateGeneratorTemplate<TemplatePackagingContext>(context);
    }
}
