/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate Mock DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public class MockDAOTestTemplateGenerator
    implements  MockDAOTestTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MockDAOTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final MockDAOTestTemplateGenerator generator)
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
     * Retrieves a MockDAOTestTemplateGenerator instance.
     * @return such instance.
     */
    public static MockDAOTestTemplateGenerator getInstance()
    {
        MockDAOTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (MockDAOTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new MockDAOTestTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param templateFactoryClass the template factory.
     * @precondition daoName != null
     * @precondition templateFactoryClass != null
     * @precondition TemplateMappingManager.getInstance() != null
     */
    public void addTemplateFactoryClass(
        final String daoName,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            daoName,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition daoName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    public void addTemplateFactoryClass(
        final String daoName,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.MOCK_DAO_TEST_TEMPLATE_PREFIX + daoName,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @precondition daoName != null
     */
    protected String getTemplateFactoryClass(final String daoName)
    {
        return
            getTemplateFactoryClass(
                daoName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition daoName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String daoName,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.MOCK_DAO_TEST_TEMPLATE_PREFIX
                + daoName);
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     * @precondition TemplateMappingManager.getInstance() != null
     */
    protected MockDAOTestTemplateFactory getTemplateFactory(final String daoName)
        throws  QueryJException
    {
        return getTemplateFactory(daoName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     * @precondition templateMappingManager != null
     */
    protected MockDAOTestTemplateFactory getTemplateFactory(
        final String daoName,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        MockDAOTestTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.MOCK_DAO_TEST_TEMPLATE_PREFIX
                + daoName);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof MockDAOTestTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.mock.dao.test.template.factory");
            }
            else 
            {
                result = (MockDAOTestTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a DAO test template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition daoPackageName != null
     * @precondition valueObjectPackageName != null
     */
    public MockDAOTestTemplate createMockDAOTestTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String daoPackageName,
        final String valueObjectPackageName,
        final Project project,
        final Task task)
      throws  QueryJException
    {
        MockDAOTestTemplate result = null;

        MockDAOTestTemplateFactory t_TemplateFactory =
            getTemplateFactory(tableTemplate.getTableName());

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createMockDAOTestTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    daoPackageName,
                    valueObjectPackageName,
                    project,
                    task);
        }
        else 
        {
            result =
                new MockDAOTestTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    daoPackageName,
                    valueObjectPackageName,
                    project,
                    task);
        }

        return result;
    }

    /**
     * Writes a Mock DAO template to disk.
     * @param mockDAOTestTemplate the Mock DAO test template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final MockDAOTestTemplate mockDAOTestTemplate,
        final File outputDir,
        final Project project,
        final Task task)
      throws  IOException
    {
        write(
            mockDAOTestTemplate,
            outputDir,
            project,
            task,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a Mock DAO template to disk.
     * @param mockDAOTestTemplate the Mock DAO test template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
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
        final MockDAOTestTemplate mockDAOTestTemplate,
        final File outputDir,
        final Project project,
        final Task task,
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
                      mockDAOTestTemplate
                          .getTableTemplate()
                              .getTableName().toLowerCase()),
                '_')
              + "DAOTest.java",
              mockDAOTestTemplate.generate());
    }
}
