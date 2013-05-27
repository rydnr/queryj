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
 * Filename: BaseResultSetExtractorTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create BaseResultSetExtractor sources.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;

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
 * Is able to create BaseResultSetExtractor sources.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
@ThreadSafe
public class BaseResultSetExtractorTemplate
    extends  BasePerRepositoryTemplate<BasePerRepositoryTemplateContext>
{
    private static final long serialVersionUID = 1432792006131595106L;

    /**
     * Builds a {@link BaseResultSetExtractorTemplate} using given information.
     * @param context the {@link BasePerRepositoryTemplateContext} instance.
     */
    public BaseResultSetExtractorTemplate(@NotNull final BasePerRepositoryTemplateContext context)
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
        return "//BaseResultSetExtractorTemplate//";
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
     * Returns "BaseResultSetExtractor".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "BaseResultSetExtractor";
    }
}
