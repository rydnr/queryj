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
 * Description: Is able to generate ValueObject implementations according to
 *              database metadata.
 *
<<<<<<< ValueObjectTemplateGenerator.java
=======
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
>>>>>>> 1.5
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
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
<<<<<<< ValueObjectTemplateGenerator.java
=======
 * @version $Revision$
>>>>>>> 1.5
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
    protected static void setReference(
        final ValueObjectTemplateGenerator generator)
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
            result = new ValueObjectTemplateGenerator();

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
              TemplateMappingManager.VALUE_OBJECT_TEMPLATE_PREFIX
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
     * @precondition valueObjectName != null
     * @precondition engineName != null
     */
    protected ValueObjectTemplateFactory getTemplateFactory(
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
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition valueObjectName != null
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected ValueObjectTemplateFactory getTemplateFactory(
        final String valueObjectName,
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        ValueObjectTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactoryClass(
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
    public ValueObjectTemplate createValueObjectTemplate(
        final String packageName,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final Project project,
        final Task task)
        throws  QueryJException
    {
        return
            new ValueObjectTemplate(
                packageName,
                tableTemplate,
                metaDataManager,
                project,
                task);
    }

    /**
     * Writes a value object template to disk.
     * @param valueObjectTemplate the value object template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition valueObjectTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final ValueObjectTemplate valueObjectTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            valueObjectTemplate,
            outputDir, 
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a value object template to disk.
     * @param valueObjectTemplate the value object template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition valueObjectTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final ValueObjectTemplate valueObjectTemplate,
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
                    valueObjectTemplate
                        .getTableTemplate()
                            .getTableName()
                                .toLowerCase()),
                '_')
            + "ValueObject.java",
            valueObjectTemplate.generate());
    }
}
