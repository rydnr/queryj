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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate value object factories according to
 *              database metadata.
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
 * Is able to generate value object factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
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
        final ValueObjectFactoryTemplateGenerator generator)
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
            result = new ValueObjectFactoryTemplateGenerator();

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
     * @precondition valueObjectName != null
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String valueObjectName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            valueObjectName,
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition valueObjectName != null
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String valueObjectName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
              TemplateMappingManager.VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX
            + valueObjectName,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition valueObjectName != null
     * @precondition engineName != null
     */
    protected String getTemplateFactoryClass(
        final String valueObjectName,
        final String engineName,
        final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                valueObjectName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition valueObjectName != null
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String valueObjectName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getTemplateFactoryClass(
                  TemplateMappingManager.VALUE_OBJECT_TEMPLATE_PREFIX
                + valueObjectName,
                engineName,
                engineVersion);
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
        final String valueObjectName,
        final String engineName,
        final String engineVersion)
      throws  QueryJException
    {
        return
            getTemplateFactory(
                valueObjectName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param valueObjectName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code> instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    protected ValueObjectFactoryTemplateFactory getTemplateFactory(
        final String valueObjectName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        ValueObjectFactoryTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactoryClass(
                  TemplateMappingManager.VALUE_OBJECT_FACTORY_TEMPLATE_PREFIX
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

        return result;
    }

    /**
     * Generates a value object factory template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     */
    public ValueObjectFactoryTemplate createValueObjectFactoryTemplate(
        final String packageName,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager)
      throws  QueryJException
    {
        return
            new ValueObjectFactoryTemplate(
                packageName,
                tableTemplate,
                metaDataManager);
    }

    /**
     * Writes a value object factory template to disk.
     * @param template the value object factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     */
    public void write(
        final ValueObjectFactoryTemplate template,
        final File outputDir)
      throws  IOException
    {
        write(
            template,
            outputDir,
            EnglishGrammarUtils.getInstance(),
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a value object factory template to disk.
     * @param template the value object factory template to write.
     * @param outputDir the output folder.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final ValueObjectFactoryTemplate template,
        final File outputDir,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils,
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
            + "ValueObjectFactory.java",
            template.generate());
    }
}
