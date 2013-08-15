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
 * Importing project classes.
 */
import org.acmsl.queryj.templates.packaging.exceptions.InvalidTemplatePackagingTemplateException;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

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
    /**
     * The template packaging group.
     */
    public static final String TEMPLATE_PACKAGING_GROUP = "org/acmsl/queryj/templates/packaging/";

    /**
     * Builds an <code>TemplateTemplate</code> using given
     * information.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext} instance.
     */
    protected TemplateTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    @Override
    protected String buildHeader()
    {
        return buildHeader(getTemplateName());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(@NotNull final String templateName)
    {
        return GENERATING + templateName;
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return TEMPLATE_LITERAL;
    }

    /**
     * Builds a context-specific exception.
     * @param context the context.
     * @param template the {@link ST} instance.
     * @return the specific {@link InvalidTemplateException} for the template.
     */
    @Override
    @NotNull
    public InvalidTemplateException buildInvalidTemplateException(
        @NotNull final C context,
        @NotNull final ST template,
        @NotNull final Throwable actualException)
    {
        return
            new InvalidTemplatePackagingTemplateException(
                getTemplateName(),
                actualException);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public STGroup retrieveGroup()
    {
        return retrieveGroup(TEMPLATE_PACKAGING_GROUP + getTemplateName() + ".stg");
    }
}
