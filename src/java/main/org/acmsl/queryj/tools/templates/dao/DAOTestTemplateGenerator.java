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
 * Description: Is able to generate DAO test implementations according to
 *              database metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateFactory;
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
 * Is able to generate DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTestTemplateGenerator
    implements  DAOTestTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final DAOTestTemplateGenerator generator)
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
     * Retrieves a <code>DAOTestTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static DAOTestTemplateGenerator getInstance()
    {
        DAOTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DAOTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new DAOTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String daoName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            daoName,
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager<(code>
     * instance.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String daoName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {

        templateMappingManager.addTemplateFactoryClass(
            TemplateMappingManager.DAO_TEST_TEMPLATE_PREFIX + daoName,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition engineName != null
     */
    protected String getTemplateFactoryClass(
        final String daoName,
        final String engineName,
        final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                daoName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String daoName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getTemplateFactoryClass(
                TemplateMappingManager.DAO_TEST_TEMPLATE_PREFIX + daoName,
                engineName,
                engineVersion);
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected DAOTestTemplateFactory getTemplateFactory(
        final String daoName,
        final String engineName,
        final String engineVersion)
      throws  QueryJException
    {
        return
            getTemplateFactory(
                daoName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    protected DAOTestTemplateFactory getTemplateFactory(
        final String daoName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        DAOTestTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactoryClass(
                TemplateMappingManager.DAO_TEST_TEMPLATE_PREFIX + daoName,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof DAOTestTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.dao.template.factory");
            }
            else 
            {
                result = (DAOTestTemplateFactory) t_TemplateFactory;
            }
        }
        else
        {
            throw
                new QueryJException(
                    "dao.template.factory.not.found");
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
     * @param jdbcDriver the JDBC driver.
     * @param jdbcUrl the JDBC URL.
     * @param jdbcUsername the JDBC username.
     * @param jdbcPassword the JDBC password.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition quote != null
     * @precondition daoPackageName != null
     * @precondition valueObjectPackageName != null
     * @precondition jdbcDriver != null
     * @precondition jdbcUrl != null
     * @precondition jdbcUserName != null
     * @precondition jdbcPassword != null
     */
    public DAOTestTemplate createDAOTestTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String daoPackageName,
        final String valueObjectPackageName,
        final String jdbcDriver,
        final String jdbcUrl,
        final String jdbcUsername,
        final String jdbcPassword,
        final Project project,
        final Task task)
      throws  QueryJException
    {
        DAOTestTemplate result = null;

        DAOTestTemplateFactory t_TemplateFactory =
            getTemplateFactory(
                tableTemplate.getTableName(), engineName, engineVersion);

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createDAOTestTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    engineName,
                    engineVersion,
                    quote,
                    daoPackageName,
                    valueObjectPackageName,
                    jdbcDriver,
                    jdbcUrl,
                    jdbcUsername,
                    jdbcPassword,
                    project,
                    task);
        }
        else 
        {
            result =
                new DAOTestTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    engineName,
                    engineVersion,
                    quote,
                    daoPackageName,
                    valueObjectPackageName,
                    jdbcDriver,
                    jdbcUrl,
                    jdbcUsername,
                    jdbcPassword,
                    project,
                    task) {};
        }

        return result;
    }

    /**
     * Writes a DAO test template to disk.
     * @param daoTestTemplate the DAO test template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition daoTestTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final DAOTestTemplate daoTestTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            daoTestTemplate,
            outputDir,
            FileUtils.getInstance(),
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance());
    }

    /**
     * Writes a DAO test template to disk.
     * @param daoTestTemplate the DAO test template to write.
     * @param outputDir the output folder.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @throws IOException if the file cannot be created.
     * @precondition daoTestTemplate != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected void write(
        final DAOTestTemplate daoTestTemplate,
        final File outputDir,
        final FileUtils fileUtils,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + daoTestTemplate.getEngineName()
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    daoTestTemplate
                        .getTableTemplate().getTableName().toLowerCase()),
                '_')
            + "DAOTest.java",
            daoTestTemplate.toString());
    }
}
