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
 * Description: Is able to generate value object factories according to
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
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.valueobject
    .ValueObjectFactoryTemplateFactory;

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
 * Is able to generate value object factories according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ValueObjectFactoryTemplateGenerator
    implements  ValueObjectFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ValueObjectFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        ValueObjectFactoryTemplateGenerator generator)
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
     * Retrieves a ValueObjectFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static ValueObjectFactoryTemplateGenerator getInstance()
    {
        ValueObjectFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ValueObjectFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new ValueObjectFactoryTemplateGenerator() {};

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
                  TemplateMappingManager.VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX
                + valueObjectName,
                engineName,
                engineVersion,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param valueObjectName the value object name.
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
    protected ValueObjectFactoryTemplateFactory getTemplateFactory(
            String valueObjectName,
            String engineName,
            String engineVersion)
        throws  QueryJException
    {
        ValueObjectFactoryTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactoryClass(
                      TemplateMappingManager
                          .VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX
                    + valueObjectName,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(  t_TemplateFactory
                       instanceof ValueObjectFactoryTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.value.object.factory.template.factory");
                }
                else 
                {
                    result =
                        (ValueObjectFactoryTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a value object factory template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public ValueObjectFactoryTemplate createValueObjectFactoryTemplate(
            String                  packageName,
            TableTemplate           tableTemplate,
            DatabaseMetaDataManager metaDataManager)
        throws  QueryJException
    {
        ValueObjectFactoryTemplate result = null;

        if  (   (tableTemplate   != null)
             && (metaDataManager != null)
             && (packageName     != null))
        {
            result =
                new ValueObjectFactoryTemplate(
                    packageName,
                    tableTemplate,
                    metaDataManager) {};
        }

        return result;
    }

    /**
     * Writes a value object factory template to disk.
     * @param template the value object factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            ValueObjectFactoryTemplate template,
            File                       outputDir)
        throws  IOException
    {
        if  (   (template  != null)
             && (outputDir != null))
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
                          template
                              .getTableTemplate().getTableName().toLowerCase(),
                          '_')
                    + "ValueObjectFactory.java",
                    template.toString());
            }
        }
    }
}
