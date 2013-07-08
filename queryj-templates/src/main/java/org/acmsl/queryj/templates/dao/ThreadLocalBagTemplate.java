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
 * Filename: ThreadLocalBagTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create ThreadLocalBag sources.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.api.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;

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
 * Is able to create ThreadLocalBag sources.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 * @since 2012/07/02 (recovered)
 */
@ThreadSafe
public class ThreadLocalBagTemplate
    extends AbstractBasePerRepositoryTemplate<PerRepositoryTemplateContext>
{

    private static final long serialVersionUID = 1171121698062677984L;

    /**
     * Builds a {@link org.acmsl.queryj.templates.dao.ThreadLocalBagTemplate} using given information.
     * @param context the {@link org.acmsl.queryj.api.PerRepositoryTemplateContext} instance.
     */
    public ThreadLocalBagTemplate(@NotNull final PerRepositoryTemplateContext context)
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
        return "//ThreadLocalBagTemplate//";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public STGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "ThreadLocalBag".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "ThreadLocalBag";
    }
}
