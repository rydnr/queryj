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
 * Filename: BaseDAOFactoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate base DAO factories.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.BasePerTableTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to generate base DAO factories.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class BaseDAOFactoryTemplateGenerator
    extends AbstractTemplateGenerator<BaseDAOFactoryTemplate, BasePerTableTemplateContext>
    implements  BasePerTableTemplateGenerator<BaseDAOFactoryTemplate, BasePerTableTemplateContext>
{
    /**
     * Creates a new {@link BaseDAOFactoryTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads to use.
     */
    public BaseDAOFactoryTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerTableTemplateContext context)
    {
        return retrieveTemplateFileName(context, StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final BasePerTableTemplateContext context,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    context.getTableName().toLowerCase(Locale.US)),
                '_')
                + "DAOFactory.java";
    }
}
