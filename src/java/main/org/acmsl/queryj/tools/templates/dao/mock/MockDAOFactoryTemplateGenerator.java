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
 * Description: Is able to generate Mock DAO factories.
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
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOFactoryTemplateFactory;
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
 * Is able to generate Mock DAO factories.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class MockDAOFactoryTemplateGenerator
    implements  MockDAOFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MockDAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        MockDAOFactoryTemplateGenerator generator)
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
     * Retrieves a MockDAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static MockDAOFactoryTemplateGenerator getInstance()
    {
        MockDAOFactoryTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (MockDAOFactoryTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new MockDAOFactoryTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param mockDAOFactoryName the Mock DAO factory name.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String mockDAOFactoryName,
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addDefaultTemplateFactoryClass(
                  TemplateMappingManager.MOCK_DAO_FACTORY_TEMPLATE_PREFIX
                + mockDAOFactoryName,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param mockDAOFactoryName the Mock DAO factory name.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        String mockDAOFactoryName)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            result =
                t_MappingManager.getDefaultTemplateFactoryClass(
                      TemplateMappingManager.MOCK_DAO_FACTORY_TEMPLATE_PREFIX
                    + mockDAOFactoryName);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param mockDAOFactoryName the Mock DAO factory name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected MockDAOFactoryTemplateFactory getTemplateFactory(
            String mockDAOFactoryName)
        throws  QueryJException
    {
        MockDAOFactoryTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getDefaultTemplateFactoryClass(
                      TemplateMappingManager.MOCK_DAO_FACTORY_TEMPLATE_PREFIX
                    + mockDAOFactoryName);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof MockDAOFactoryTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.mock.dao.factory.template.factory");
                }
                else 
                {
                    result = (MockDAOFactoryTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a Mock DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public MockDAOFactoryTemplate createMockDAOFactoryTemplate(
            TableTemplate tableTemplate,
            String        packageName,
            String        basePackageName)
        throws  QueryJException
    {
        MockDAOFactoryTemplate result = null;

        if  (   (tableTemplate   != null)
             && (packageName     != null)
             && (basePackageName != null))
        {
            result =
                new MockDAOFactoryTemplate(
                    tableTemplate,
                    packageName,
                    basePackageName) {};
        }

        return result;
    }

    /**
     * Writes a Mock DAO factory template to disk.
     * @param template the Mock DAO factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            MockDAOFactoryTemplate template,
            File                   outputDir)
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
                    + "Mock"
                    + t_StringUtils.capitalize(
                        template
                            .getTableTemplate().getTableName().toLowerCase(),
                          '_')
                    + "DAOFactory.java",
                    template.toString());
            }
        }
    }
}
