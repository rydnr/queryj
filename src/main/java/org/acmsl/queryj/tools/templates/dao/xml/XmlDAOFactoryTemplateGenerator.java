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
 * Filename: XmlDAOFactoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate Xml DAO factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;

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

/**
 * Is able to generate Xml DAO factories.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XmlDAOFactoryTemplateGenerator
    extends AbstractTemplateGenerator<XmlDAOFactoryTemplate>
    implements  BasePerTableTemplateFactory<XmlDAOFactoryTemplate>,
                BasePerTableTemplateGenerator<XmlDAOFactoryTemplate>,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class XmlDAOFactoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final XmlDAOFactoryTemplateGenerator SINGLETON =
            new XmlDAOFactoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XmlDAOFactoryTemplateGenerator() {}

    /**
     * Retrieves a {@link XmlDAOFactoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static XmlDAOFactoryTemplateGenerator getInstance()
    {
        return XmlDAOFactoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Creates a {@link XmlDAOFactoryTemplate} using given
     * information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @return the fresh new template.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    @NotNull
    public XmlDAOFactoryTemplate createTemplate(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces)
    {
        return
            new XmlDAOFactoryTemplate(
                tableName,
                metadataManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                quote,
                basePackageName,
                repositoryName,
                implementMarkerInterfaces);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final XmlDAOFactoryTemplate template)
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
        @NotNull final XmlDAOFactoryTemplate template,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return
              "Xml"
            + stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      template.getTableName().toLowerCase()),
                  '_')
            + "DAOFactory.java";
    }
}
