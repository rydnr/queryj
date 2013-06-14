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
 * Filename: CucumberFeatureTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to generate Cucumber feature files.
 *
 * Date: 5/23/13
 * Time: 5:57 PM
 *
 */
package org.acmsl.queryj.templates.other;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.PerRepositoryTemplateGenerator;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate Cucumber feature files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2013/05/23
 */
@ThreadSafe
public class CucumberFeatureTemplateGenerator
    extends AbstractTemplateGenerator<CucumberFeatureTemplate, PerRepositoryTemplateContext>
    implements PerRepositoryTemplateGenerator<CucumberFeatureTemplate, PerRepositoryTemplateContext>
{
    /**
     * Creates a new {@link CucumberFeatureTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads.
     */
    public CucumberFeatureTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String retrieveTemplateFileName(@NotNull final PerRepositoryTemplateContext context)
    {
        return retrieveTemplateFileName(context, DecorationUtils.getInstance());
    }

    /**
     * Retrieves the template's file name.
     * @param context the template.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the template's file name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final PerRepositoryTemplateContext context, @NotNull final DecorationUtils decorationUtils)
    {
        return
            decorationUtils.capitalize(context.getRepositoryName())
            + "-tables.feature";
    }
}
