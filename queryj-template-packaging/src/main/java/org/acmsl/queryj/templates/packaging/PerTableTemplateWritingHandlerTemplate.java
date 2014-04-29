/*
                        QueryJ Template Packaging

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
 * Filename: PerTableTemplateWritingHandlerTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents table-specific template writing handler templates.
 *
 * Date: 2014/04/29
 * Time: 06:01
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
 * Represents table-specific template writing handler templates.
 * @param <C> the context type.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/29 06:01
 */
@ThreadSafe
public class PerTableTemplateWritingHandlerTemplate<C extends TemplatePackagingContext>
    extends TemplateBuildHandlerTemplate<C>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -910691293894728629L;

    /**
     * Creates a new instance using given context.
     * @param context the context.
     */
    public PerTableTemplateWritingHandlerTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return "PerTableTemplateWritingHandler".
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.PER_TABLE_TEMPLATE_WRITING_HANDLER;
    }
}
