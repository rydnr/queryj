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
 * Filename: TableRepositoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.TableRepositoryTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableRepositoryTemplateGenerator
    implements  DefaultBasePerRepositoryTemplateFactory,
                BasePerRepositoryTemplateGenerator,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TableRepositoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableRepositoryTemplateGenerator SINGLETON =
            new TableRepositoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableRepositoryTemplateGenerator() {};

    /**
     * Retrieves a {@link TableRepositoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static TableRepositoryTemplateGenerator getInstance()
    {
        return TableRepositoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a <i>per-repository</i> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param engineName the engine name.
     * @param repositoryName the name of the repository.
     * @param tables the tables.
     * @param header the header.
     * @oaram jmx whether to support JMX.
     * @return a template.
     */
    @NotNull
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables,
        final String header,
        final boolean jmx)
    {
        return
            new TableRepositoryTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                basePackageName,
                repositoryName,
                engineName,
                tables);
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
     * Writes a table repository template to disk.
     * @param template the table repository template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        @NotNull final BasePerRepositoryTemplate template,
        @NotNull final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            template,
            outputDir,
            charset,
            FileUtils.getInstance(),
            TableRepositoryTemplateUtils.getInstance());
    }
            
    /**
     * Writes a table repository template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @param tableRepositoryTemplateUtils the
     * {@link TableRepositoryTemplateUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition tableRepositoryTemplateUtils != null
     */
    public void write(
        @NotNull final BasePerRepositoryTemplate template,
        @NotNull final File outputDir,
        final Charset charset,
        @NotNull final FileUtils fileUtils,
        @NotNull final TableRepositoryTemplateUtils tableRepositoryTemplateUtils)
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
                + tableRepositoryTemplateUtils.retrieveTableRepositoryClassName(
                    template.getRepositoryName())
                + ".java",
                template.generate(),
                charset);
        }
    }
}
