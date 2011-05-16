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
 * Filename: MockDAOTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate Mock DAO test implementations according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplateFactory;
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
 * Is able to generate Mock DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MockDAOTestTemplateGenerator
    implements  MockDAOTestTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MockDAOTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MockDAOTestTemplateGenerator SINGLETON =
            new MockDAOTestTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MockDAOTestTemplateGenerator() {};

    /**
     * Retrieves a {@link MockDAOTestTemplateGenerator} instance.
     * @return such instance.
     */
    public static MockDAOTestTemplateGenerator getInstance()
    {
        return MockDAOTestTemplateGeneratorSingletonContainer.SINGLETON;
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
     * @param header the header.
     * @return a template.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition daoPackageName != null
     * @precondition valueObjectPackageName != null
     */
    public MockDAOTestTemplate createMockDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String daoPackageName,
        final String valueObjectPackageName,
        final String header)
    {
        return
            new MockDAOTestTemplate(
                tableTemplate,
                metadataManager,
                header,
                getDecoratorFactory(),
                packageName,
                daoPackageName,
                valueObjectPackageName);
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
     * Writes a Mock DAO template to disk.
     * @param mockDAOTestTemplate the Mock DAO test template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final MockDAOTestTemplate mockDAOTestTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            mockDAOTestTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a Mock DAO template to disk.
     * @param mockDAOTestTemplate the Mock DAO test template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils}
     * instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final MockDAOTestTemplate mockDAOTestTemplate,
        final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final FileUtils fileUtils)
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
                + "Mock"
                + stringUtils.capitalize(
                    englishGrammarUtils.getSingular(
                        mockDAOTestTemplate
                        .getTableTemplate()
                        .getTableName().toLowerCase()),
                    '_')
                + "DAOTest.java",
                mockDAOTestTemplate.generate(),
                charset);
        }
    }
}
