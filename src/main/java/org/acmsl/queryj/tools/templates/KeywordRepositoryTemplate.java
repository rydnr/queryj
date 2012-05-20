//;-*- mode: java -*-
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
 * Filename: KeywordRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate keyword repositories according to database
 *              types.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate Keyword repositories according to database types.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class KeywordRepositoryTemplate
    extends  BasePerRepositoryTemplate<BasePerRepositoryTemplateContext>
{
    private static final long serialVersionUID = 543188035734451487L;
    /**
     * The keywords list.
     */
    private List<String> m__lKeywords;

    /**
     * The keyword types.
     */
    private Map<String,String> m__mKeywordTypes;

    /**
     * Builds a <code>KeywordRepositoryTemplate</code> using given
     * information.
     * @param context the {@link BasePerRepositoryTemplateContext} instance.
     */
    public KeywordRepositoryTemplate(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);

        immutableSetKeywords(new ArrayList<String>());
        immutableSetKeywordTypes(new HashMap<String,String>());
    }

    /**
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void fillCoreParameters(
        @NotNull final Map input,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String subpackageName,
        @NotNull final String basePackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final String timestamp,
        @NotNull final StringUtils stringUtils)
    {
        super.fillCoreParameters(
            input,
            metadataManager,
            customSqlProvider,
            decoratorFactory,
            subpackageName,
            basePackageName,
            tableRepositoryName,
            engineName,
            tables,
            timestamp,
            stringUtils);

        input.put("keywords", getKeywords());
        input.put("keyword_types", retrieveKeywordTypes());
        input.put("keywords_uncapitalized", retrieveKeywordsUncapitalized());
    }

    /**
     * Specifies the keywords.
     * @param keywords the keywords.
     */
    protected final void immutableSetKeywords(@NotNull final List<String> keywords)
    {
        m__lKeywords = keywords;
    }

    /**
     * Specifies the keywords.
     * @param keywords the keywords.
     */
    @SuppressWarnings("unused")
    protected void setKeywords(@NotNull final List<String> keywords)
    {
        immutableSetKeywords(keywords);
    }

    /**
     * Retrieves the keywords.
     * @return such collection.
     */
    @NotNull
    public List<String> getKeywords()
    {
        return m__lKeywords;
    }

    /**
     * Adds a new keyword types.
     * @param keyword the keyword.
     * @param fieldType the field type.
     */
    public void addKeyword(
        @NotNull final String keyword, @NotNull final String fieldType)
    {
        List<String> t_lKeywords = getKeywords();

        t_lKeywords.add(keyword);

        Map<String,String> t_mKeywordTypes = getKeywordTypes();

        t_mKeywordTypes.put(buildKey(keyword), fieldType);
    }

    /**
     * Retrieves the field type of given keyword.
     * @param keyword the keyword.
     * @return the field type.
     */
    @NotNull
    protected String getKeywordFieldType(@NotNull final String keyword)
    {
        @Nullable String result;

        Map<String,String> t_mKeywordtypes = getKeywordTypes();

        result = t_mKeywordtypes.get(buildKey(keyword));

        if (result == null)
        {
            result = "";
        }

        return result;
    }

    /**
     * Specifies the keyword types.
     * @param keywordTypes the keyword types.
     */
    private void immutableSetKeywordTypes(final Map<String,String> keywordTypes)
    {
        m__mKeywordTypes = keywordTypes;
    }

    /**
     * Specifies the keyword types.
     * @param keywordTypes the keyword types.
     */
    @SuppressWarnings("unused")
    protected void setKeywordTypes(final Map<String,String> keywordTypes)
    {
        immutableSetKeywordTypes(keywordTypes);
    }

    /**
     * Retrieves the keyword types.
     * @return such types.
     */
    protected Map<String,String> getKeywordTypes()
    {
        return m__mKeywordTypes;
    }

    /**
     * Builds the key based on the keyword.
     * @param keyword the keyword.
     * @return the associated key.
     */
    @NotNull
    protected String buildKey(@Nullable final String keyword)
    {
        @Nullable String result = keyword;

        if  (result == null)
        {
            result = "(unknown)";
        }

        result = "keyword." + result;

        return result;
    }

    /**
     * Retrieves the keyword types.
     * @return such information.
     */
    @NotNull
    protected Collection<String> retrieveKeywordTypes()
    {
        return retrieveKeywordTypes(getKeywords());
    }

    /**
     * Retrieves the keyword types.
     * @param keywords the keywords.
     * @return such information.
     */
    @NotNull
    protected Collection<String> retrieveKeywordTypes(@NotNull final Collection<String> keywords)
    {
        @NotNull Collection<String> result = new ArrayList<String>();

        for (String t_strKeyword : keywords)
        {
            result.add(getKeywordFieldType(t_strKeyword));
        }

        return result;
    }

    /**
     * Retrieves the uncapitalized keywords.
     * @return such information.
     */
    @NotNull
    protected Collection retrieveKeywordsUncapitalized()
    {
        return
            retrieveKeywordsUncapitalized(getKeywords(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the uncapitalized keywords.
     * @param keywords the keywords.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     */
    @NotNull
    protected Collection<String> retrieveKeywordsUncapitalized(
        @NotNull final Collection<String> keywords, @NotNull final DecorationUtils decorationUtils)
    {
        @NotNull Collection<String> result = new ArrayList<String>();

        for (String t_strKeyword: keywords)
        {
            result.add(uncapitalize(t_strKeyword, decorationUtils));
        }

        return result;
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the uncapitalzed value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(decorationUtils.capitalize(value));
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/sql/KeywordRepository.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "Keyword repository";
    }
}
