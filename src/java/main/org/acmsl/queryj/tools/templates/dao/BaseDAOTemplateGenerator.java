/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
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
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

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
    protected static void setReference(BaseDAOTemplateGenerator generator)
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
            result = new BaseDAOTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param baseDAOName the base DAO name.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String baseDAOName,
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.BASE_DAO_TEMPLATE_PREFIX + baseDAOName,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param baseDAOName the base DAO name.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        String baseDAOName)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            result =
                t_MappingManager.getDefaultTemplateFactoryClass(
                      TemplateMappingManager.BASE_DAO_TEMPLATE_PREFIX
            + baseDAOName);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param baseDAOName the base DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the input values are invalid.
     */
    protected BaseDAOTemplateFactory getTemplateFactory(
        String baseDAOName)
        throws  QueryJException
    {
        BaseDAOTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getDefaultTemplateFactoryClass(
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
        }

        return result;
    }

    /**
     * Generates a base DAO template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public BaseDAOTemplate createBaseDAOTemplate(
            TableTemplate           tableTemplate,
            DatabaseMetaDataManager metaDataManager,
            String                  packageName)
        throws  QueryJException
    {
        BaseDAOTemplate result = null;

        if  (   (tableTemplate   != null)
             && (metaDataManager != null)
             && (packageName     != null))
        {
            BaseDAOTemplateFactory t_TemplateFactory =
                getTemplateFactory(
                    tableTemplate.getTableName());

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createBaseDAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName);
            }
            else 
            {
                result =
                    new BaseDAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName) {};
            }
        }

        return result;
    }

    /**
     * Writes a base DAO template to disk.
     * @param baseDAOTemplate the base DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            BaseDAOTemplate baseDAOTemplate,
            File            outputDir)
        throws  IOException
    {
        if  (   (baseDAOTemplate != null)
             && (outputDir   != null))
        {
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + t_StringUtils.capitalize(
                          baseDAOTemplate
                              .getTableTemplate().getTableName().toLowerCase(),
                          '_')
                    + "DAO.java",
                    baseDAOTemplate.toString());
            }
        }
    }
}
