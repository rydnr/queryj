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
 * Description: Is able to generate XML DAO test implementations according to
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
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
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
 * Importing some Ant classes.
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
 * Is able to generate XML DAO test implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
     * @precondition TemplateMappingManager.getInstance() != null
     */
    public void addTemplateFactoryClass(
        final String daoName,
        final String templateFactoryClass)
    {
        TemplateMappingManager.getInstance().addDefaultTemplateFactoryClass(
            TemplateMappingManager.XML_DAO_TEST_TEMPLATE_PREFIX + daoName,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @precondition daoName != null
     * @precondition TemplateMappingManager.getInstance() != null
     */
    protected String getTemplateFactoryClass(final String daoName)
    {
        return
            TemplateMappingManager.getInstance()
                .getDefaultTemplateFactoryClass(
                      TemplateMappingManager.XML_DAO_TEST_TEMPLATE_PREFIX
                    + daoName);
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition daoName != null
     * @precondition TemplateMappingManager.getInstance() != null
     */
    protected XMLDAOTestTemplateFactory getTemplateFactory(final String daoName)
        throws  QueryJException
    {
        XMLDAOTestTemplateFactory result = null;

        Object t_TemplateFactory =
            TemplateMappingManager.getInstance()
                .getDefaultTemplateFactoryClass(
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
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public XMLDAOTestTemplate createXMLDAOTestTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  packageName,
        final String                  daoPackageName,
        final String                  valueObjectPackageName)
      throws  QueryJException
    {
        XMLDAOTestTemplate result = null;

        if  (   (tableTemplate          != null)
             && (metaDataManager        != null)
             && (packageName            != null)
             && (daoPackageName         != null)
             && (valueObjectPackageName != null))
        {
            XMLDAOTestTemplateFactory t_TemplateFactory =
                getTemplateFactory(tableTemplate.getTableName());

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createXMLDAOTestTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName,
                        daoPackageName,
                        valueObjectPackageName);
            }
            else 
            {
                result =
                    new XMLDAOTestTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName,
                        daoPackageName,
                        valueObjectPackageName) {};
            }
        }

        return result;
    }

    /**
     * Writes a XML DAO template to disk.
     * @param xmlDAOTestTemplate the XML DAO test template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws IOException if the file cannot be created.
     * @precondition xmlDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final XMLDAOTestTemplate xmlDAOTestTemplate,
        final File                outputDir,
        final Project             project,
        final Task                task)
      throws  IOException
    {
        StringUtils t_StringUtils = StringUtils.getInstance();
        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();
        FileUtils t_FileUtils     = FileUtils.getInstance();

        if  (   (t_StringUtils != null)
             && (t_FileUtils   != null))
        {
            outputDir.mkdirs();

            if  (project != null)
            {
                project.log(
                    task,
                    "Writing "
                    + outputDir.getAbsolutePath()
                    + File.separator
                    + "XML"
                    + t_StringUtils.capitalize(
                          t_EnglishGrammarUtils.getSingular(
                              xmlDAOTestTemplate
                                  .getTableTemplate()
                                      .getTableName().toLowerCase()),
                        '_')
                    + "DAOTest.java",
                    Project.MSG_VERBOSE);
            }
            
            t_FileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + "XML"
                + t_StringUtils.capitalize(
                      t_EnglishGrammarUtils.getSingular(
                          xmlDAOTestTemplate
                              .getTableTemplate()
                                  .getTableName().toLowerCase()),
                      '_')
                + "DAOTest.java",
                xmlDAOTestTemplate.toString());
        }
    }
}