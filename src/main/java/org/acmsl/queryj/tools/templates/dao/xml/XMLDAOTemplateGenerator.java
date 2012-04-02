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
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate XML DAO implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLDAOTemplateGenerator
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
    protected XMLDAOTemplateGenerator() {};

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
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTemplate the XML DAO template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        @NotNull final XMLDAOTemplate xmlDAOTemplate,
        @NotNull final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            xmlDAOTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTemplate the XML DAO template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils}
     * instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        @NotNull final XMLDAOTemplate xmlDAOTemplate,
        @NotNull final File outputDir,
        final Charset charset,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final FileUtils fileUtils)
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
                + "XML"
                + stringUtils.capitalize(
                    englishGrammarUtils.getSingular(
                        xmlDAOTemplate
                        .getTableTemplate()
                        .getTableName().toLowerCase()),
                    '_')
                + "DAO.java",
                xmlDAOTemplate.generate(),
                charset);
        }
    }
}
