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
 * Filename: DAOFactoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOFactoryTemplateFactory;
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
 * Is able to generate DAO factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOFactoryTemplateGenerator
    implements  DAOFactoryTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DAOFactoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOFactoryTemplateGenerator SINGLETON =
            new DAOFactoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOFactoryTemplateGenerator() {};

    /**
     * Retrieves a {@link DAOFactoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static DAOFactoryTemplateGenerator getInstance()
    {
        return DAOFactoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param header the header.
     * @return a template.
     * @precondition tableTemplate != null
     * @precondition packageName != null
     * @precondition engineName != null
     */
    @NotNull
    public DAOFactoryTemplate createDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String packageName,
        final String engineName,
        final String basePackageName,
        final String jndiDataSource,
        final String header)
    {
        return
            new DAOFactoryTemplate(
                header,
                getDecoratorFactory(),
                tableTemplate,
                packageName,
                engineName,
                basePackageName,
                jndiDataSource);
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
     * Writes a DAO factory template to disk.
     * @param template the DAO factory template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        @NotNull final DAOFactoryTemplate template,
        @NotNull final File outputDir,
        final Charset charset)
        throws  IOException
    {
        write(
            template,
            outputDir,
            charset,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a DAO factory template to disk.
     * @param template the DAO factory template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils}
     * instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        @NotNull final DAOFactoryTemplate template,
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
                + template.getEngineName()
                + stringUtils.capitalize(
                    englishGrammarUtils.getSingular(
                        template
                        .getTableTemplate().getTableName()
                        .toLowerCase()),
                    '_')
                + "DAOFactory.java",
                template.generate(),
                charset);
        }
    }
}
