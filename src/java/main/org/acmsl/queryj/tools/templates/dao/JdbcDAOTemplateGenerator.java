/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate JdbcDAO implementations.
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
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateFactory;
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
 * Is able to generate JdbcDAO implementations.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class JdbcDAOTemplateGenerator
    implements  JdbcDAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected JdbcDAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(JdbcDAOTemplateGenerator generator)
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
     * Retrieves a JdbcDAOTemplateGenerator instance.
     * @return such instance.
     */
    public static JdbcDAOTemplateGenerator getInstance()
    {
        JdbcDAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (JdbcDAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new JdbcDAOTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param jdbcDAOName the JDBC DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass()
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            result =
                t_MappingManager.getDefaultTemplateFactoryClass(
                    TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected JdbcDAOTemplateFactory getTemplateFactory()
        throws  QueryJException
    {
        JdbcDAOTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getDefaultTemplateFactoryClass(
                    TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY);
    
            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof JdbcDAOTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.jdbc.dao.template.factory");
                }
                else 
                {
                    result = (JdbcDAOTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a JDBC DAO template.
     * @param packageName the package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public JdbcDAOTemplate createJdbcDAOTemplate(
            String packageName)
        throws  QueryJException
    {
        JdbcDAOTemplate result = null;

        if  (packageName != null)
        {
            JdbcDAOTemplateFactory t_TemplateFactory =
                getTemplateFactory();

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createJdbcDAOTemplate(
                        packageName);
            }
            else 
            {
                result =
                    new JdbcDAOTemplate(packageName) {};
            }
        }

        return result;
    }

    /**
     * Writes a JDBC DAO template to disk.
     * @param jdbcDAOTemplate the JDBC DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            JdbcDAOTemplate jdbcDAOTemplate,
            File            outputDir)
        throws  IOException
    {
        if  (   (jdbcDAOTemplate != null)
             && (outputDir       != null))
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
                    + "JdbcDAO.java",
                    jdbcDAOTemplate.toString());
            }
        }
    }
}
