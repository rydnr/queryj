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
 * Filename: DAOTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO test implementations according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

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
 * Is able to generate DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTestTemplateGenerator<T extends DAOTestTemplate>
    extends AbstractTemplateGenerator<T>
    implements  DAOTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DAOTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOTestTemplateGenerator SINGLETON =
            new DAOTestTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOTestTemplateGenerator() {}

    /**
     * Retrieves a {@link DAOTestTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static DAOTestTemplateGenerator getInstance()
    {
        return DAOTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a DAO test template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC URL.
     * @param jdbcUsername the JDBC username.
     * @param jdbcPassword the JDBC password.
     * @param header the header.
     * @return a template.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition quote != null
     * @precondition daoPackageName != null
     * @precondition valueObjectPackageName != null
     * @precondition jdbcDriver != null
     * @precondition jdbcUrl != null
     * @precondition jdbcUserName != null
     * @precondition jdbcPassword != null
     */
    @NotNull
    public DAOTestTemplate createDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String daoPackageName,
        final String valueObjectPackageName,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUsername,
        final String jdbcPassword,
        final String header)
    {
        return
            new DAOTestTemplate(
                tableTemplate,
                metadataManager,
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                quote,
                daoPackageName,
                valueObjectPackageName,
                jdbcDriver,
                jdbcUrl,
                jdbcUsername,
                jdbcPassword);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull T template)
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
              template.getEngineName()
            + stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      template.getTableTemplate().getTableName().toLowerCase(Locale.US)),
                  '_')
            + "DAOTest.java";
    }
}
