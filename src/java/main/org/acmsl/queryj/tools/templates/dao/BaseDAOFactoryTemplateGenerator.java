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
 * Description: Is able to generate base DAO factories.
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
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplateFactory;
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
 * Is able to generate base DAO factories.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class BaseDAOFactoryTemplateGenerator
    implements  BaseDAOFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected BaseDAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final BaseDAOFactoryTemplateGenerator generator)
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
     * Retrieves a BaseDAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static BaseDAOFactoryTemplateGenerator getInstance()
    {
        BaseDAOFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (BaseDAOFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new BaseDAOFactoryTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            baseDAOFactoryName,
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
              TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATE_PREFIX
            + baseDAOFactoryName,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition engineName != null
     */
    protected String getTemplateFactoryClass(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                baseDAOFactoryName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
    {
        return templateMappingManager.getTemplateFactoryClass(
              TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATE_PREFIX
            + baseDAOFactoryName,
            engineName,
            engineVersion);
    }

    /**
     * Retrieves the template factory instance.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected BaseDAOFactoryTemplateFactory getTemplateFactory(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion)
        throws  QueryJException
    {
        return
            getTemplateFactory(
                baseDAOFactoryName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param baseDAOFactoryName the base DAO factory name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    protected BaseDAOFactoryTemplateFactory getTemplateFactory(
        final String baseDAOFactoryName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
        throws  QueryJException
    {
        BaseDAOFactoryTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactoryClass(
                  TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATE_PREFIX
                + baseDAOFactoryName,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(  t_TemplateFactory
                   instanceof BaseDAOFactoryTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.base.dao.factory.template.factory");
            }
            else 
            {
                result = (BaseDAOFactoryTemplateFactory) t_TemplateFactory;
            }
        }
        else
        {
            throw
                new QueryJException(
                    "base.dao.factory.template.factory.not.found");
        }

        return result;
    }

    /**
     * Generates a base DAO factory template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param projectPackageName the project package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition projectPackageName != null
     */
    public BaseDAOFactoryTemplate createBaseDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String projectPackageName,
        final Project project,
        final Task task)
      throws  QueryJException
    {
        return
            new BaseDAOFactoryTemplate(
                tableTemplate,
                metaDataManager,
                packageName,
                projectPackageName,
                project,
                task);
    }

    /**
     * Writes a base DAO factory template to disk.
     * @param template the base DAO factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final BaseDAOFactoryTemplate template, final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a base DAO factory template to disk.
     * @param template the base DAO factory template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final BaseDAOFactoryTemplate template,
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
                    template.getTableTemplate().getTableName().toLowerCase()),
                '_')
            + "DAOFactory.java",
            template.generate());
    }
}
