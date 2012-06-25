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
 * Filename: DAOImplementationClassNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "class_name" placeholders for DAO implementations.
 *
 * Date: 5/24/12
 * Time: 5:12 AM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.DefaultThemeUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Resolves "class_name" placeholders for DAO implementations.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
public class DAOImplementationClassNameHandler
    extends AbstractDecoratedStringHandler<BasePerTableTemplateContext>
{
    /**
     * Creates a new {@link DAOImplementationClassNameHandler} using
     * given {@link BasePerTableTemplateContext context}.
     * @param context the {@link BasePerTableTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public DAOImplementationClassNameHandler(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "class_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "dao_implementation_class_name";
    }

    /**
     * Resolves the actual value using given {@link BasePerTableTemplateContext context}.
     * @param context the {@link BasePerTableTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final BasePerTableTemplateContext context)
    {
        return
            retrieveClassName(
                context.getTableName(),
                context.getMetadataManager(),
                DefaultThemeUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the class name.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param defaultThemeUtils the {@link DefaultThemeUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such class name.
     */
    @NotNull
    protected String retrieveClassName(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DefaultThemeUtils defaultThemeUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final StringUtils stringUtils)
    {
        String t_strCapitalizedEngine = stringUtils.capitalize(metadataManager.getEngineName());

        String t_strSingularName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableName.toLowerCase()));

        return
            defaultThemeUtils.buildDAOImplementationClassName(
                t_strCapitalizedEngine, t_strSingularName);
    }
}
