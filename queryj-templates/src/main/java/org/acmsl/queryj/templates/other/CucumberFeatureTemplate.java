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
 * Filename: CucumberTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/23/13
 * Time: 5:25 PM
 *
 */
package org.acmsl.queryj.templates.other;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.STGroup;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate Cucumber tests according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2013/05/23
 */
@ThreadSafe
public class CucumberFeatureTemplate
    extends AbstractBasePerRepositoryTemplate<PerRepositoryTemplateContext>
{
    private static final long serialVersionUID = 5236574342911947317L;

    /**
     * Builds a {@link CucumberFeatureTemplate} using given
     * information.
     * @param context the {@link org.acmsl.queryj.api.PerRepositoryTemplateContext context}.
     */
    public CucumberFeatureTemplate(@NotNull final PerRepositoryTemplateContext context)
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
        return retrieveGroup("/org/acmsl/queryj/CucumberFeature.stg");
    }

    /**
     * Returns "CucumberFeatureTemplate".
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "CucumberFeature";
    }
}
