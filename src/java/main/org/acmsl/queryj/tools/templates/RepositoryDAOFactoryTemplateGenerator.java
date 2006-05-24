//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the repository DAO factory implementation.
 *
 */
package org.acmsl.queryj.tools.templates;

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
import org.acmsl.queryj.tools.templates.RepositoryDAOTemplate;
import org.acmsl.queryj.tools.templates.RepositoryDAOFactoryTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;

/**
 * Is able to generate the repository DAO factory implementation.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class RepositoryDAOFactoryTemplateGenerator
    implements  RepositoryDAOFactoryTemplateFactory,
                BasePerRepositoryTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected RepositoryDAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final RepositoryDAOFactoryTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a RepositoryDAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static RepositoryDAOFactoryTemplateGenerator getInstance()
    {
        RepositoryDAOFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result =
                (RepositoryDAOFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new RepositoryDAOFactoryTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a <i>per-repository</i> template.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param engineName the engine name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param tables the tables.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public BasePerRepositoryTemplate createTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final String jndiDataSource,
        final Collection tables,
        final String header)
      throws  QueryJException
    {
        return
            new RepositoryDAOFactoryTemplate(
                metadataManager,
                metadataTypeManager,
                customSqlProvider,
                header,
                getDecoratorFactory(),
                packageName,
                basePackageName,
                repositoryName,
                engineName,
                jndiDataSource,
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
     * Writes a table repository template to disk.
     * @param template the table repository template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final BasePerRepositoryTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            DecorationUtils.getInstance(),
            FileUtils.getInstance());
    }
            
    /**
     * Writes a table repository template to disk.
     * @param template the template to write.
     * @param outputDir the output folder.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition decorationUtils != null
     * @precondition fileUtils != null
     */
    public void write(
        final BasePerRepositoryTemplate template,
        final File outputDir,
        final DecorationUtils decorationUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + template.getEngineName()
            + decorationUtils.capitalize(template.getRepositoryName())
            + "DAOFactory.java",
            template.generate());
    }
}
