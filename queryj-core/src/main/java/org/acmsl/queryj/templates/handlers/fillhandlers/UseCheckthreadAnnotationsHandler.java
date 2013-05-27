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
 * Filename: UseNotNullAnnotationsHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "use_checkthread_annotations" placeholders in
 *              templates.
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to resolve "checkthread?" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2013/03/18
 */
@SuppressWarnings("unused")
@ThreadSafe
public class UseCheckthreadAnnotationsHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, Boolean>
{
    /**
     * Creates a {@link UseCheckthreadAnnotationsHandler} to resolve placeholders
     * using given {@link TemplateContext context}.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public UseCheckthreadAnnotationsHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "checkthread_annotations_enabled".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "checkthread_annotations_enabled";
    }

    /**
     * Retrieves whether to use Checkthread.org annotations in templates
     * @param context the {@link TemplateContext}.
     * @return such information.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final TemplateContext context)
    {
        return !context.getDisableCheckthreadAnnotations();
    }
}
