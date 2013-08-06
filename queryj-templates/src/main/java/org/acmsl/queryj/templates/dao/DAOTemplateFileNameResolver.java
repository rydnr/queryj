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
 * Filename: DAOTemplateFileNameResolver.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: File name resolver for DAO templates.
 *
 * Date: 2013/08/06
 * Time: 17:22
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.FileNameResolver;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.Locale;

/**
 * File name resolver for DAO templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 */
@ThreadSafe
public class DAOTemplateFileNameResolver
    implements FileNameResolver<PerTableTemplateContext>
{
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final PerTableTemplateContext context)
    {
        return
            retrieveTemplateFileName(
                context, context.getMetadataManager(), StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext} instance.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final PerTableTemplateContext context,
        @NotNull final MetadataManager metadataManager,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            stringUtils.capitalize(metadataManager.getEngineName())
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    context.getTableName().toLowerCase(Locale.US)),
                '_')
            + "DAO.java";
    }

}
