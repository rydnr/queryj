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
 * Description: Is able to generate base DAO implementations according to
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
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.BaseDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Ant classes.
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
 * Is able to generate base DAO implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class BaseDAOTemplateGenerator
    implements  BaseDAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected BaseDAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final BaseDAOTemplateGenerator generator)
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
     * Retrieves a DAOTemplateGenerator instance.
     * @return such instance.
     */
    public static BaseDAOTemplateGenerator getInstance()
    {
        BaseDAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (BaseDAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new BaseDAOTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param baseDAOName the base DAO name.
     * @param templateFactoryClass the template factory.
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String baseDAOName,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            baseDAOName,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param baseDAOName the base DAO name.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String baseDAOName,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.BASE_DAO_TEMPLATE_PREFIX + baseDAOName,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param baseDAOName the base DAO name.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        final String baseDAOName)
    {
        return
            getTemplateFactoryClass(
                baseDAOName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param baseDAOName the base DAO name.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @return the template factory class name.
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String baseDAOName,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.BASE_DAO_TEMPLATE_PREFIX
                + baseDAOName);
    }

    /**
     * Retrieves the template factory instance.
     * @param baseDAOName the base DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the input values are invalid.
     */
    protected BaseDAOTemplateFactory getTemplateFactory(
        final String baseDAOName)
      throws  QueryJException
    {
        return
            getTemplateFactory(
                baseDAOName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param baseDAOName the base DAO name.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @return the template factory class name.
     * @throws QueryJException if the input values are invalid.
     * @precondition templateMappingManager != null
     */
    protected BaseDAOTemplateFactory getTemplateFactory(
        final String baseDAOName,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        BaseDAOTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.BASE_DAO_TEMPLATE_PREFIX
                + baseDAOName);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof BaseDAOTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.base.dao.template.factory");
            }
            else 
            {
                result = (BaseDAOTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a base DAO template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition valueObjectPackageName != null
     */
    public BaseDAOTemplate createBaseDAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String valueObjectPackageName,
        final Project project,
        final Task task)
      throws  QueryJException
    {
        BaseDAOTemplate result = null;

        BaseDAOTemplateFactory t_TemplateFactory =
            getTemplateFactory(tableTemplate.getTableName());

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createBaseDAOTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    valueObjectPackageName,
                    project,
                    task);
        }
        else 
        {
            result =
                new BaseDAOTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    valueObjectPackageName,
                    project,
                    task);
        }

        return result;
    }

    /**
     * Writes a base DAO template to disk.
     * @param baseDAOTemplate the base DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition baseDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final BaseDAOTemplate baseDAOTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            baseDAOTemplate,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a base DAO template to disk.
     * @param baseDAOTemplate the base DAO template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @throws IOException if the file cannot be created.
     * @precondition baseDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final BaseDAOTemplate baseDAOTemplate,
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
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    baseDAOTemplate
                        .getTableTemplate().getTableName().toLowerCase()),
                '_')
            + "DAO.java",
            baseDAOTemplate.generate());
    }
}
