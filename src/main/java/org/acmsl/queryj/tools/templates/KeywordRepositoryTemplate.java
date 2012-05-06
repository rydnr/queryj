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
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate Keyword repositories according to database types.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class KeywordRepositoryTemplate
    extends  BasePerRepositoryTemplate
{
    private static final long serialVersionUID = 543188035734451487L;
    /**
     * The keywords list.
     */
    private List m__lKeywords;

    /**
     * The keyword types.
     */
    private Map m__mKeywordTypes;

    /**
     * Builds a <code>KeywordRepositoryTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param keywords the keywords.
     */
    public KeywordRepositoryTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName)
    {
        super(
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            new ArrayList());

        immutableSetKeywords(new ArrayList());
        immutableSetKeywordTypes(new HashMap());
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
     * @precondition input != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition decoratorFactory != null
     * @precondition subpackageName != null
     * @precondition basePackageName != null
     * @precondition tableRepositoryName != null
     * @precondition tables != null
     * @precondition timestamp != null
     * @precondition stringUtils != null
     */
    protected void fillCoreParameters(
        @NotNull final Map input,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        final String subpackageName,
        final String basePackageName,
        final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final Collection tables,
        final String timestamp,
        final StringUtils stringUtils)
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
    private void immutableSetKeywords(final List keywords)
    {
        m__lKeywords = keywords;
    }

    /**
     * Specifies the keywords.
     * @param keywords the keywords.
     */
    protected void setKeywords(final List keywords)
    {
        immutableSetKeywords(keywords);
    }

    /**
     * Retrieves the keywords.
     * @return such collection.
     */
    public List getKeywords()
    {
        return m__lKeywords;
    }

    /**
     * Adds a new keyword types.
     * @param keyword the keyword.
     * @param fieldType the field type.
     * @precondition keyword != null
     * @precondition fieldType != null
     */
    public void addKeyword(
        final String keyword, final String fieldType)
    {
        List t_lKeywords = getKeywords();

        if  (t_lKeywords != null)
        {
            t_lKeywords.add(keyword);
        }

        Map t_mKeywordTypes = getKeywordTypes();

        if  (t_mKeywordTypes != null)
        {
            t_mKeywordTypes.put(buildKey(keyword), fieldType);
        }
    }

    /**
     * Retrieves the field type of given keyword.
     * @param keyword the keyword.
     * @return the field type.
     */
    @NotNull
    protected String getKeywordFieldType(@Nullable final String keyword)
    {
        @NotNull String result = "";

        if  (keyword != null)
        {
            Map t_mKeywordtypes = getKeywordTypes();

            if  (t_mKeywordtypes != null)
            {
                result =
                      ""
                    + t_mKeywordtypes.get(buildKey(keyword));
            }
        }

        return result;
    }

    /**
     * Specifies the keyword types.
     * @param keywordTypes the keyword types.
     */
    private void immutableSetKeywordTypes(final Map keywordTypes)
    {
        m__mKeywordTypes = keywordTypes;
    }

    /**
     * Specifies the keyword types.
     * @param keywordTypes the keyword types.
     */
    protected void setKeywordTypes(final Map keywordTypes)
    {
        immutableSetKeywordTypes(keywordTypes);
    }

    /**
     * Retrieves the keyword types.
     * @return such types.
     */
    protected Map getKeywordTypes()
    {
        return m__mKeywordTypes;
    }

    /**
     * Builds the key based on the keyword.
     * @param keyword the keyword.
     * @return the associated key.
     */
    @Nullable
    protected Object buildKey(@Nullable final String keyword)
    {
        @Nullable Object result = keyword;

        if  (keyword != null)
        {
            result = "keyword." + keyword;
        }

        return result;
    }

    /**
     * Retrieves the keyword types.
     * @return such information.
     */
    @NotNull
    protected Collection retrieveKeywordTypes()
    {
        return retrieveKeywordTypes(getKeywords());
    }

    /**
     * Retrieves the keyword types.
     * @param keywords the keywords.
     * @return such information.
     */
    @NotNull
    protected Collection retrieveKeywordTypes(@Nullable final Collection keywords)
    {
        @NotNull Collection result = new ArrayList();

        @Nullable Iterator t_Iterator = (keywords != null) ? keywords.iterator() : null;

        if  (t_Iterator != null)
        {
            while  (t_Iterator.hasNext())
            {
                result.add(getKeywordFieldType((String) t_Iterator.next()));
            }
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
     * @precondition decorationUtils != null
     */
    @NotNull
    protected Collection retrieveKeywordsUncapitalized(
        @Nullable final Collection keywords, @NotNull final DecorationUtils decorationUtils)
    {
        @NotNull Collection result = new ArrayList();

        @Nullable Iterator t_Iterator = (keywords != null) ? keywords.iterator() : null;

        if  (t_Iterator != null)
        {
            while  (t_Iterator.hasNext())
            {
                result.add(uncapitalize((String) t_Iterator.next(), decorationUtils));
            }
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
