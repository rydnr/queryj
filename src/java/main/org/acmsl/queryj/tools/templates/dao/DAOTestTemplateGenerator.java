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
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

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
    protected static void setReference(DAOTestTemplateGenerator generator)
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
     * Retrieves a DAOTestTemplateGenerator instance.
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
     */
    public void addTemplateFactoryClass(
        String daoName,
        String engineName,
        String engineVersion,
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addTemplateFactoryClass(
                TemplateMappingManager.DAO_TEST_TEMPLATE_PREFIX + daoName,
                engineName,
                engineVersion,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        String daoName,
        String engineName,
        String engineVersion)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager != null)
             && (engineName       != null))
        {
            result =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.DAO_TEST_TEMPLATE_PREFIX + daoName,
                    engineName,
                    engineVersion);
        }

        return result;
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
            String daoName,
            String engineName,
            String engineVersion)
        throws  QueryJException
    {
        DAOTestTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactoryClass(
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
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public DAOTestTemplate createDAOTestTemplate(
            TableTemplate           tableTemplate,
            DatabaseMetaDataManager metaDataManager,
            String                  packageName,
            String                  engineName,
            String                  engineVersion,
            String                  quote,
            String                  daoPackageName,
            String                  valueObjectPackageName,
            String                  jdbcDriver,
            String                  jdbcUrl,
            String                  jdbcUsername,
            String                  jdbcPassword)
        throws  QueryJException
    {
        DAOTestTemplate result = null;

        if  (   (tableTemplate          != null)
             && (metaDataManager        != null)
             && (packageName            != null)
             && (engineName             != null)
             && (engineVersion          != null)
             && (quote                  != null)
             && (daoPackageName         != null)
             && (valueObjectPackageName != null))
        {
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
                        jdbcPassword);
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
                        jdbcPassword) {};
            }
        }

        return result;
    }

    /**
     * Writes a DAO test template to disk.
     * @param daoTestTemplate the DAO test template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            DAOTestTemplate daoTestTemplate,
            File        outputDir)
        throws  IOException
    {
        if  (   (daoTestTemplate != null)
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
                    + daoTestTemplate.getEngineName()
                    + t_StringUtils.capitalize(
                          daoTestTemplate.getTableTemplate().getTableName().toLowerCase(),
                          '_')
                    + "DAOTest.java",
                    daoTestTemplate.toString());
            }
        }
    }
}
