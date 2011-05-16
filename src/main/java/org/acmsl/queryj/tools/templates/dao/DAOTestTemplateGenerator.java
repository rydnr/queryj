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
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTestTemplateGenerator
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
    protected DAOTestTemplateGenerator() {};

    /**
     * Retrieves a {@link DAOTestTemplateGenerator} instance.
     * @return such instance.
     */
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
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a DAO test template to disk.
     * @param daoTestTemplate the DAO test template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition daoTestTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final DAOTestTemplate daoTestTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            daoTestTemplate,
            outputDir,
            charset,
            FileUtils.getInstance(),
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance());
    }

    /**
     * Writes a DAO test template to disk.
     * @param daoTestTemplate the DAO test template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils}
     * instance.
     * @throws IOException if the file cannot be created.
     * @precondition daoTestTemplate != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected void write(
        final DAOTestTemplate daoTestTemplate,
        final File outputDir,
        final Charset charset,
        final FileUtils fileUtils,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
      throws  IOException
    {
        boolean folderCreated = outputDir.mkdirs();

        if (   (!folderCreated)
            && (!outputDir.exists()))
        {
            throw
                new IOException("Cannot create output dir: " + outputDir);
        }
        else
        {
            fileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + daoTestTemplate.getEngineName()
                + stringUtils.capitalize(
                    englishGrammarUtils.getSingular(
                        daoTestTemplate
                        .getTableTemplate().getTableName().toLowerCase()),
                    '_')
                + "DAOTest.java",
                daoTestTemplate.generate(),
                charset);
        }
    }
}
