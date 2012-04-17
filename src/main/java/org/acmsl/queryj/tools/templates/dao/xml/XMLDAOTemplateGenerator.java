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
 * Filename: XMLDAOTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate XML DAO implementations according to
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
 * Is able to generate XML DAO implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLDAOTemplateGenerator<T extends XMLDAOTemplate>
    extends AbstractTemplateGenerator<T>
    implements  XMLDAOTemplateFactory
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class XMLDAOTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final XMLDAOTemplateGenerator SINGLETON =
            new XMLDAOTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLDAOTemplateGenerator() {}

    /**
     * Retrieves a XMLDAOTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static XMLDAOTemplateGenerator getInstance()
    {
        return XMLDAOTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a XML DAO template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param header the header.
     * @return a template.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     */
    @NotNull
    public XMLDAOTemplate createXMLDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        return
            new XMLDAOTemplate(
                tableTemplate,
                metadataManager,
                header,
                getDecoratorFactory(),
                packageName,
                basePackageName,
                repositoryName);
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
             + "DAO.java";
    }
}
