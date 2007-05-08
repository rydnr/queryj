//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: DataAccessContextLocalTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate dataAccessContext-local.xml templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplate;
import org.acmsl.queryj.tools.templates.dao
    .DataAccessContextLocalTemplateFactory;

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
import java.util.Collection;

/**
 * Is able to generate dataAccessContext-local.xml templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DataAccessContextLocalTemplateGenerator
    implements  DataAccessContextLocalTemplateFactory,
                BasePerRepositoryTemplateGenerator,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DataAccessContextLocalTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DataAccessContextLocalTemplateGenerator SINGLETON =
            new DataAccessContextLocalTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DataAccessContextLocalTemplateGenerator() {};

    /**
     * Retrieves a DataAccessContextLocalTemplateGenerator instance.
     * @return such instance.
     */
    public static DataAccessContextLocalTemplateGenerator getInstance()
    {
        return DataAccessContextLocalTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a <code>DataAccessContextLocal</code> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param engineName the engine name.
     * @param jndiLocation the JNDI location.
     * @param tables the table names.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     * @precondition engineName != null
     * @precondition jndiLocation != null
     * @precondition tables != null
     */
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final String jndiLocation,
        final Collection tables,
        final String header,
        final boolean jmx)
      throws  QueryJException
    {
        return
            new DataAccessContextLocalTemplate(
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
                jndiLocation,
                tables);
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
     * Writes a per-repository template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final BasePerRepositoryTemplate template, final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            FileUtils.getInstance());
    }

    /**
     * Writes a <code>dataAccessContext-local.xml</code> to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     */
    protected void write(
        final BasePerRepositoryTemplate template,
        final File outputDir,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "dataAccessContext-local.xml.sample",
            template.generate());
    }
}
