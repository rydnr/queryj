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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate numeric function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateFactory;
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
 * Is able to generate numeric function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class NumericFunctionsTemplateGenerator
    implements  NumericFunctionsTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public NumericFunctionsTemplateGenerator()
    {
        TemplateMappingManager t_TemplateMappingManager =
            TemplateMappingManager.getInstance();

        if  (t_TemplateMappingManager != null)
        {
            t_TemplateMappingManager.addDefaultTemplateFactoryClass(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                this.getClass().getName());
        }
    }

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final NumericFunctionsTemplateGenerator generator)
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
     * Retrieves a NumericFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    public static NumericFunctionsTemplateGenerator getInstance()
    {
        NumericFunctionsTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (NumericFunctionsTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new NumericFunctionsTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            engineName,
            engineVersion,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition engineName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addTemplateFactoryClass(
            TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
            engineName,
            engineVersion,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition engineName != null
     */
    protected String getTemplateFactoryClass(
        final String engineName, final String engineVersion)
    {
        return
            getTemplateFactoryClass(
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition engineName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getTemplateFactoryClass(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected NumericFunctionsTemplateFactory getTemplateFactory(
        final String engineName, final String engineVersion)
      throws  QueryJException
    {
        return
            getTemplateFactory(
                engineName,
                engineVersion,
                TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    protected NumericFunctionsTemplateFactory getTemplateFactory(
        final String engineName,
        final String engineVersion,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        NumericFunctionsTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getTemplateFactory(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEMPLATE,
                engineName,
                engineVersion);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof NumericFunctionsTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.numeric.function.template.factory");
            }
            else 
            {
                result = (NumericFunctionsTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  QueryJException
    {
        NumericFunctionsTemplate result = null;

        NumericFunctionsTemplateFactory t_TemplateFactory =
            getTemplateFactory(engineName, engineVersion);

        if  (   (t_TemplateFactory != null)
             && (!t_TemplateFactory.getClass().equals(getClass())))
        {
            result =
                t_TemplateFactory.createNumericFunctionsTemplate(
                    packageName,
                    engineName,
                    engineVersion,
                    quote);
        }
        else 
        {
            throw
                new QueryJException(
                      "Cannot find numeric functions' "
                    + "template factory for "
                    + engineName + "\n"
                    + "Disable extractfunctions setting.");
        }

        return result;
    }

    /**
     * Writes a numeric functions template to disk.
     * @param numericFunctionsTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition numericFunctionsTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final NumericFunctionsTemplate numericFunctionsTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            numericFunctionsTemplate,
            outputDir,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a numeric functions template to disk.
     * @param numericFunctionsTemplate the numeric functions template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition numericFunctionsTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final NumericFunctionsTemplate numericFunctionsTemplate,
        final File outputDir,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "NumericFunctions.java",
            numericFunctionsTemplate.generate());
    }
}
