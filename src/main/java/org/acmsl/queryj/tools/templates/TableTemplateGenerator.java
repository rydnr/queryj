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
 * Filename: TableTemplateGenerator.java
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
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TableTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateGenerator
    implements  TableTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TableTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableTemplateGenerator SINGLETON =
            new TableTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableTemplateGenerator() {};

    /**
     * Retrieves a <code>TableTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static TableTemplateGenerator getInstance()
    {
        return TableTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a table template.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param implementMarkerInterfaces whether to implement marker
     * interfaces.
     * @return a template.
     */
    public TableTemplate createTableTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean implementMarkerInterfaces)
    {
        return
            new TableTemplate(
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
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a table template to disk.
     * @param tableTemplate the table template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition tableTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final TableTemplate tableTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            tableTemplate,
            outputDir,
            charset,
            FileUtils.getInstance(),
            TableTemplateUtils.getInstance());
    }

    /**
     * Writes a table template to disk.
     * @param tableTemplate the table template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @param tableTemplateUtils the <code>TableTemplateUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition tableTemplate != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition tableTemplateUtils != null
     */
    protected void write(
        final TableTemplate tableTemplate,
        final File outputDir,
        final Charset charset,
        final FileUtils fileUtils,
        final TableTemplateUtils tableTemplateUtils)
      throws  IOException
    {
        if (outputDir.mkdirs())
        {
            fileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + tableTemplateUtils.retrieveTableClassName(
                    tableTemplate.getTableName())
                + ".java",
                tableTemplate.generate(),
                charset);
        }
        else
        {
            throw
                new IOException(
                    "Cannot create output dir: " + outputDir);
        }
    }
}
