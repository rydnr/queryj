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
 * Description: Is able to generate DAO implementations according to
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
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.DAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplateFactory;

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
 * Is able to generate value object implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ValueObjectTemplateGenerator
    implements  ValueObjectTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ValueObjectTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(ValueObjectTemplateGenerator generator)
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
     * Retrieves a ValueObjectTemplateGenerator instance.
     * @return such instance.
     */
    public static ValueObjectTemplateGenerator getInstance()
    {
        ValueObjectTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ValueObjectTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new ValueObjectTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String valueObjectName,
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
                  TemplateMappingManager.VALUE_OBJECT_TEMPLATE_PREFIX
		+ valueObjectName,
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
        String valueObjectName,
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
                      TemplateMappingManager.VALUE_OBJECT_TEMPLATE_PREFIX
                    + valueObjectName,
                    engineName,
                    engineVersion);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected ValueObjectTemplateFactory getTemplateFactory(
            String valueObjectName,
            String engineName,
            String engineVersion)
        throws  QueryJException
    {
        ValueObjectTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactoryClass(
                      TemplateMappingManager.VALUE_OBJECT_TEMPLATE_PREFIX
                    + valueObjectName,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(  t_TemplateFactory
                       instanceof ValueObjectTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.value.object.template.factory");
                }
                else 
                {
                    result = (ValueObjectTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a value object template.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public ValueObjectTemplate createValueObjectTemplate(
            String                  packageName,
            TableTemplate           tableTemplate,
            DatabaseMetaDataManager metaDataManager)
        throws  QueryJException
    {
        ValueObjectTemplate result = null;

        if  (   (tableTemplate   != null)
             && (metaDataManager != null)
             && (packageName     != null))
        {
            result =
                new ValueObjectTemplate(
                    packageName,
                    tableTemplate,
                    metaDataManager) {};
        }

        return result;
    }

    /**
     * Writes a value object template to disk.
     * @param valueObjectTemplate the value object template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            ValueObjectTemplate valueObjectTemplate,
            File                outputDir)
        throws  IOException
    {
        if  (   (valueObjectTemplate != null)
             && (outputDir           != null))
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
                          valueObjectTemplate
                              .getTableTemplate().getTableName().toLowerCase(),
                          '_')
                    + "ValueObject.java",
                    valueObjectTemplate.toString());
            }
        }
    }
}
