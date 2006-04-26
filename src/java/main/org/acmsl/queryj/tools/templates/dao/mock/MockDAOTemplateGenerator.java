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
 * Description: Is able to generate Mock DAO implementations according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate Mock DAO implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MockDAOTemplateGenerator
    implements  MockDAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MockDAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final MockDAOTemplateGenerator generator)
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
     * Retrieves a MockDAOTemplateGenerator instance.
     * @return such instance.
     */
    public static MockDAOTemplateGenerator getInstance()
    {
        MockDAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (MockDAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new MockDAOTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a Mock DAO template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     */
    public MockDAOTemplate createMockDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String header)
      throws  QueryJException
    {
        return
            new MockDAOTemplate(
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
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a Mock DAO template to disk.
     * @param mockDAOTemplate the Mock DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final MockDAOTemplate mockDAOTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            mockDAOTemplate,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a Mock DAO template to disk.
     * @param mockDAOTemplate the Mock DAO template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final MockDAOTemplate mockDAOTemplate,
        final File outputDir,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
            outputDir.getAbsolutePath()
            + File.separator
            + "Mock"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    mockDAOTemplate
                        .getTableTemplate()
                            .getTableName().toLowerCase()),
                '_')
            + "DAO.java",
            mockDAOTemplate.generate());
    }
}
