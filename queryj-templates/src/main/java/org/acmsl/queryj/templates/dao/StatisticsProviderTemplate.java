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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: StatisticsProviderTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create StatisticsProvider sources.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.templates.FillTemplateChain;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

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
 * Is able to create StatisticsProvider sources.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 * @since 2012/07/03 (recovered)
 */
@ThreadSafe
public class StatisticsProviderTemplate
    extends AbstractBasePerRepositoryTemplate<BasePerRepositoryTemplateContext>
{

    private static final long serialVersionUID = -8232097256823745148L;

    /**
     * Builds a {@link StatisticsProviderTemplate} using given information.
     * @param context the {@link BasePerRepositoryTemplateContext} instance.
     */
    public StatisticsProviderTemplate(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected Object buildTemplateCacheKey()
    {
        return "//StatisticsProviderTemplate//";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "StatisticsProvider".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "StatisticsProvider";
    }

    /**
     * Builds the correct chain.
     * @param context the context.
     * @param relevantOnly whether to include relevant-only placeholders.
     * @return the specific {@link FillTemplateChain}.
     */
    @NotNull
    @Override
    public FillTemplateChain<BasePerRepositoryTemplateContext> buildFillTemplateChain(
        @NotNull final BasePerRepositoryTemplateContext context, final boolean relevantOnly)
    {
        // TODO
        return null;
    }
}
