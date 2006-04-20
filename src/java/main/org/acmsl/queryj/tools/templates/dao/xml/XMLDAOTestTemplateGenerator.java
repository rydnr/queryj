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
 * Description: Is able to generate XML DAO test implementations according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplateFactory;
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
 * Is able to generate XML DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLDAOTestTemplateGenerator
    implements  XMLDAOTestTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLDAOTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final XMLDAOTestTemplateGenerator generator)
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
     * Retrieves a XMLDAOTestTemplateGenerator instance.
     * @return such instance.
     */
    public static XMLDAOTestTemplateGenerator getInstance()
    {
        XMLDAOTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (XMLDAOTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new XMLDAOTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param templateFactoryClass the template factory.
     * @precondition daoName != null
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String daoName, final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            daoName, templateFactoryClass, TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition daoName != null
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    protected void addTemplateFactoryClass(
        final String daoName,
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.XML_DAO_TEST_TEMPLATE_PREFIX + daoName,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @precondition daoName != null
     */
    protected String getTemplateFactoryClass(final String daoName)
    {
        return
            getTemplateFactoryClass(
                daoName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @precondition daoName != null
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final String daoName,
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.XML_DAO_TEST_TEMPLATE_PREFIX
                + daoName);
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     */
    protected XMLDAOTestTemplateFactory getTemplateFactory(final String daoName)
        throws  QueryJException
    {
        return
            getTemplateFactory(daoName, TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     * @precondition templateMappingManager != null
     */
    protected XMLDAOTestTemplateFactory getTemplateFactory(
        final String daoName,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        XMLDAOTestTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getDefaultTemplateFactoryClass(
                  TemplateMappingManager.XML_DAO_TEST_TEMPLATE_PREFIX
                + daoName);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof XMLDAOTestTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.xml.dao.test.template.factory");
            }
            else 
            {
                result = (XMLDAOTestTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a DAO test template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition daoPackageName != null
     * @precondition valueObjectPackageName != null
     */
    public XMLDAOTestTemplate createXMLDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String daoPackageName,
        final String valueObjectPackageName)
      throws  QueryJException
    {
        XMLDAOTestTemplate result = null;

        XMLDAOTestTemplateFactory t_TemplateFactory =
            getTemplateFactory(tableTemplate.getTableName());

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createXMLDAOTestTemplate(
                    tableTemplate,
                    metadataManager,
                    packageName,
                    daoPackageName,
                    valueObjectPackageName);
        }
        else 
        {
            result =
                new XMLDAOTestTemplate(
                    tableTemplate,
                    metadataManager,
                    getDecoratorFactory(),
                    packageName,
                    daoPackageName,
                    valueObjectPackageName);
        }

        return result;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTestTemplate the XML DAO test template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final XMLDAOTestTemplate xmlDAOTestTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            xmlDAOTestTemplate,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTestTemplate the XML DAO test template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final XMLDAOTestTemplate xmlDAOTestTemplate,
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
            + "XML"
            + stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    xmlDAOTestTemplate
                        .getTableTemplate()
                            .getTableName().toLowerCase()),
                      '_')
            + "DAOTest.java",
            xmlDAOTestTemplate.generate());
    }
}
