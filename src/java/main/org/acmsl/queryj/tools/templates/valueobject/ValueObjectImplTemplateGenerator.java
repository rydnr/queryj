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
 * Description: Is able to generate ValueObjectImpl implementations according to
 *              database metadata.
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
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectImplTemplate;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectImplTemplateFactory;

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
 * Is able to generate value object implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public class ValueObjectImplTemplateGenerator
    implements  ValueObjectImplTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ValueObjectImplTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final ValueObjectImplTemplateGenerator generator)
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
     * Retrieves a ValueObjectImplTemplateGenerator instance.
     * @return such instance.
     */
    public static ValueObjectImplTemplateGenerator getInstance()
    {
        ValueObjectImplTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (ValueObjectImplTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new ValueObjectImplTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            valueObjectImplName,
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
              TemplateMappingManager.VALUE_OBJECT_IMPL_TEMPLATE_PREFIX
            + valueObjectImplName,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     */
    protected String getTemplateFactoryClass(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                valueObjectImplName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getTemplateFactoryClass(
                  TemplateMappingManager.VALUE_OBJECT_IMPL_TEMPLATE_PREFIX
                + valueObjectImplName,
                engineName,
                engineVersion);
    }

    /**
     * Retrieves the template factory instance.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     */
    protected ValueObjectImplTemplateFactory getTemplateFactory(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion)
      throws  QueryJException
    {
        return
            getTemplateFactory(
                valueObjectImplName,
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param valueObjectImplName the value object name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition valueObjectImplName != null
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected ValueObjectImplTemplateFactory getTemplateFactory(
        final String valueObjectImplName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        ValueObjectImplTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactoryClass(
                  TemplateMappingManager.VALUE_OBJECT_IMPL_TEMPLATE_PREFIX
                + valueObjectImplName,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(  t_TemplateFactory
                     instanceof ValueObjectImplTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.value.object.template.impl.factory");
            }
            else 
            {
                result = (ValueObjectImplTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a value object template.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     */
    public ValueObjectImplTemplate createValueObjectImplTemplate(
        final String packageName,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final Project project,
        final Task task)
        throws  QueryJException
    {
        return
            new ValueObjectImplTemplate(
                packageName,
                tableTemplate,
                metaDataManager,
                project,
                task);
    }

    /**
     * Writes a value object template to disk.
     * @param valueObjectImplTemplate the value object template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition valueObjectImplTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final ValueObjectImplTemplate valueObjectImplTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            valueObjectImplTemplate,
            outputDir, 
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a value object template to disk.
     * @param valueObjectImplTemplate the value object template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition valueObjectImplTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final ValueObjectImplTemplate valueObjectImplTemplate,
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
            + "Abstract"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    valueObjectImplTemplate
                        .getTableTemplate()
                            .getTableName()
                                .toLowerCase()),
                '_')
            + "ValueObject.java",
            valueObjectImplTemplate.generate());
    }
}
