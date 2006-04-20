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
 * Description: Is able to generate XML DAO implementations according to
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
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTemplateFactory;
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
 * Is able to generate XML DAO implementations according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLDAOTemplateGenerator
    implements  XMLDAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected XMLDAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final XMLDAOTemplateGenerator generator)
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
     * Retrieves a XMLDAOTemplateGenerator instance.
     * @return such instance.
     */
    public static XMLDAOTemplateGenerator getInstance()
    {
        XMLDAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (XMLDAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new XMLDAOTemplateGenerator();

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
        final String daoName,
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            daoName,
            templateFactoryClass,
            TemplateMappingManager.getInstance());
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
            TemplateMappingManager.XML_DAO_TEMPLATE_PREFIX + daoName,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @precondition daoName != null
     * @precondition TemplateMappingManager.getInstance() != null
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
                TemplateMappingManager.XML_DAO_TEMPLATE_PREFIX + daoName);
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     * @precondition TemplateMappingManager.getInstance() != null
     */
    protected XMLDAOTemplateFactory getTemplateFactory(final String daoName)
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
    protected XMLDAOTemplateFactory getTemplateFactory(
        final String daoName,
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        XMLDAOTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getDefaultTemplateFactoryClass(
                TemplateMappingManager.XML_DAO_TEMPLATE_PREFIX + daoName);

        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof XMLDAOTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.xml.dao.template.factory");
            }
            else 
            {
                result = (XMLDAOTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a XML DAO template.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     */
    public XMLDAOTemplate createXMLDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName)
      throws  QueryJException
    {
        XMLDAOTemplate result = null;

        XMLDAOTemplateFactory t_TemplateFactory =
            getTemplateFactory(
                tableTemplate.getTableName());

        if  (t_TemplateFactory != null)
        {
            result =
                t_TemplateFactory.createXMLDAOTemplate(
                    tableTemplate,
                    metadataManager,
                    packageName,
                    basePackageName,
                    repositoryName);
        }
        else 
        {
            result =
                new XMLDAOTemplate(
                    tableTemplate,
                    metadataManager,
                    getDecoratorFactory(),
                    packageName,
                    basePackageName,
                    repositoryName);
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
     * @param xmlDAOTemplate the XML DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final XMLDAOTemplate xmlDAOTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            xmlDAOTemplate,
            outputDir,
            StringUtils.getInstance(),
            EnglishGrammarUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTemplate the XML DAO template to write.
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
        final XMLDAOTemplate xmlDAOTemplate,
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
                    xmlDAOTemplate
                        .getTableTemplate()
                            .getTableName().toLowerCase()),
                '_')
            + "DAO.java",
            xmlDAOTemplate.generate());
    }
}
