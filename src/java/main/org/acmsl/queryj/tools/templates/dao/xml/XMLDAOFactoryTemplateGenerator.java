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
 * Description: Is able to generate XML DAO factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

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
 * Is able to generate XML DAO factories.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public class XMLDAOFactoryTemplateGenerator
    implements  XMLDAOFactoryTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLDAOFactoryTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final XMLDAOFactoryTemplateGenerator generator)
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
     * Retrieves a XMLDAOFactoryTemplateGenerator instance.
     * @return such instance.
     */
    public static XMLDAOFactoryTemplateGenerator getInstance()
    {
        XMLDAOFactoryTemplateGenerator result = null;

        WeakReference t_Reference = getReference();

        if  (t_Reference != null) 
        {
            result = (XMLDAOFactoryTemplateGenerator) t_Reference.get();
        }

        if  (result == null) 
        {
            result = new XMLDAOFactoryTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param xmlDAOFactoryName the XML DAO factory name.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        final String xmlDAOFactoryName,
        final String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addDefaultTemplateFactoryClass(
                  TemplateMappingManager.XML_DAO_FACTORY_TEMPLATE_PREFIX
                + xmlDAOFactoryName,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param xmlDAOFactoryName the XML DAO factory name.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        final String xmlDAOFactoryName)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            result =
                t_MappingManager.getDefaultTemplateFactoryClass(
                      TemplateMappingManager.XML_DAO_FACTORY_TEMPLATE_PREFIX
                    + xmlDAOFactoryName);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param xmlDAOFactoryName the XML DAO factory name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected XMLDAOFactoryTemplateFactory getTemplateFactory(
        final String xmlDAOFactoryName)
      throws  QueryJException
    {
        XMLDAOFactoryTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getDefaultTemplateFactoryClass(
                      TemplateMappingManager.XML_DAO_FACTORY_TEMPLATE_PREFIX
                    + xmlDAOFactoryName);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof XMLDAOFactoryTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.xml.dao.factory.template.factory");
                }
                else 
                {
                    result = (XMLDAOFactoryTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a XML DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public XMLDAOFactoryTemplate createXMLDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String        packageName,
        final String        basePackageName)
      throws  QueryJException
    {
        XMLDAOFactoryTemplate result = null;

        if  (   (tableTemplate   != null)
             && (packageName     != null)
             && (basePackageName != null))
        {
            result =
                new XMLDAOFactoryTemplate(
                    tableTemplate,
                    packageName,
                    basePackageName) {};
        }

        return result;
    }

    /**
     * Writes a XML DAO factory template to disk.
     * @param template the XML DAO factory template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        final XMLDAOFactoryTemplate template,
        final File                   outputDir)
      throws  IOException
    {
        if  (   (template  != null)
             && (outputDir != null))
        {
            EnglishGrammarUtils t_EnglishGrammarUtils =
                EnglishGrammarUtils.getInstance();
            StringUtils t_StringUtils = StringUtils.getInstance();
            FileUtils t_FileUtils = FileUtils.getInstance();

            if  (   (t_StringUtils != null)
                 && (t_FileUtils   != null))
            {
                outputDir.mkdirs();

                t_FileUtils.writeFile(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + "XML"
                    + t_StringUtils.capitalize(
                          t_EnglishGrammarUtils.getSingular(
                              template
                                  .getTableTemplate().getTableName()
                                      .toLowerCase()),
                          '_')
                    + "DAOFactory.java",
                    template.toString());
            }
        }
    }
}
