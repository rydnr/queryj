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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate XML value object factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
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

/**
 * Is able to generate XML value object factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLValueObjectFactoryTemplateGenerator<T extends XMLValueObjectFactoryTemplate>
    extends AbstractTemplateGenerator<T>
    implements  XMLValueObjectFactoryTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class XMLValueObjectFactoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final XMLValueObjectFactoryTemplateGenerator SINGLETON =
            new XMLValueObjectFactoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLValueObjectFactoryTemplateGenerator() {}

    /**
     * Retrieves a {@link XMLValueObjectFactoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static XMLValueObjectFactoryTemplateGenerator getInstance()
    {
        return XMLValueObjectFactoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a value object factory template.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param header the header.
     * @return a template.
     * @precondition packageName != null
     * @precondition valueObjectPackageName != null
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     */
    @NotNull
    public XMLValueObjectFactoryTemplate createXMLValueObjectFactoryTemplate(
        final String packageName,
        final String valueObjectPackageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header)
    {
        return
            new XMLValueObjectFactoryTemplate(
                packageName,
                valueObjectPackageName,
                tableTemplate,
                metadataManager,
                header,
                getDecoratorFactory());
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return
            retrieveTemplateFileName(
                template, StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param template the template.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return such name.
     */
    @NotNull
    protected String retrieveTemplateFileName(
        @NotNull final T template,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
              "XML"
            + stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      template.getTableTemplate().getTableName().toLowerCase(Locale.US)),
                  '_')
            + "ValueObjectFactory.java";
    }
}
