/*
                        QueryJ

    Copyright (C) 2002-today Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: BaseResultSetExtractorTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate BaseResultSetExtractor
 *              implementations.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.DefaultBasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.RepositoryDAOTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * Is able to generate BaseResultSetExtractor implementations.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class BaseResultSetExtractorTemplateGenerator
    implements  DefaultBasePerRepositoryTemplateFactory,
                BasePerRepositoryTemplateGenerator,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class BaseResultSetExtractorTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final BaseResultSetExtractorTemplateGenerator SINGLETON =
            new BaseResultSetExtractorTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected BaseResultSetExtractorTemplateGenerator() {};

    /**
     * Retrieves a {@link BaseResultSetExtractorTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static BaseResultSetExtractorTemplateGenerator getInstance()
    {
        return BaseResultSetExtractorTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a <i>PreparedStatementCreator</i> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param engineName the engine name.
     * @param repositoryName the name of the repository.
     * @param tables the tables.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
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
    //      throws  QueryJException
    {
        return
            new BaseResultSetExtractorTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                jmx,
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
     * @param charset the charset.
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
            DecorationUtils.getInstance(),
            FileUtils.getInstance());
    }
            
    /**
     * Writes a table repository template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param charset the charset.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition decorationUtils != null
     * @precondition fileUtils != null
     */
    public void write(
        @NotNull final BasePerRepositoryTemplate template,
        @NotNull final File outputDir,
        final Charset charset,
        final DecorationUtils decorationUtils,
        @NotNull final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + template.getRepositoryName()
            + "ResultSetExtractor.java",
            template.generate(),
            charset);
    }
}
