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
 * Description: Is able to generate XML DAO implementations according to
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
 * Is able to generate XML DAO implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
            result = new XMLDAOTemplateGenerator() {};

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
            TemplateMappingManager.XML_DAO_TEMPLATE_PREFIX + daoName,
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
        XMLDAOTemplateFactory result = null;

        Object t_TemplateFactory =
            TemplateMappingManager.getInstance()
                .getDefaultTemplateFactoryClass(
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
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param classInternalMethods the class' internal methods.
     * @param buildKeyPkJavadoc the <i>buildKey</i> key pk javadoc.
     * @param buildKeyPkDeclaration the <i>buildKey</i> pk declaration.
     * @param buildKeyPkValues the <i>buildKey</i>  values.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     *        declaration.
     * @param findByPrimaryKeyPkFilterValues the find by primary key pk
     *        filter values.
     * @param buildValueObjectMethod the build value object method.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteWithFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param classEnd the class end.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     */
    public XMLDAOTemplate createXMLDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  header,
        final String                  packageDeclaration,
        final String                  packageName,
        final String                  basePackageName,
        final String                  repositoryName,
        final String                  projectImports,
        final String                  foreignDAOImports,
        final String                  acmslImports,
        final String                  jdkImports,
        final String                  loggingImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  classConstructor,
        final String                  classInternalMethods,
        final String                  buildKeyPkJavadoc,
        final String                  buildKeyPkDeclaration,
        final String                  buildKeyPkValues,
        final String                  findByPrimaryKeyMethod,
        final String                  findByPrimaryKeyPkJavadoc,
        final String                  findByPrimaryKeyPkDeclaration,
        final String                  findByPrimaryKeyPkFilterValues,
        final String                  buildValueObjectMethod,
        final String                  insertMethod,
        final String                  insertParametersJavadoc,
        final String                  insertParametersDeclaration,
        final String                  updateMethod,
        final String                  updateParametersJavadoc,
        final String                  updateParametersDeclaration,
        final String                  deleteMethod,
        final String                  deletePkJavadoc,
        final String                  deletePkDeclaration,
        final String                  deleteWithFkMethod,
        final String                  deleteWithFkPkJavadoc,
        final String                  deleteWithFkPkDeclaration,
        final String                  deleteWithFkDAODeleteRequest,
        final String                  deleteWithFkPkValues,
        final String                  classEnd)
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
                    metaDataManager,
                    header,
                    packageDeclaration,
                    packageName,
                    basePackageName,
                    repositoryName,
                    projectImports,
                    foreignDAOImports,
                    acmslImports,
                    jdkImports,
                    loggingImports,
                    javadoc,
                    classDefinition,
                    classStart,
                    classConstructor,
                    classInternalMethods,
                    buildKeyPkJavadoc,
                    buildKeyPkDeclaration,
                    buildKeyPkValues,
                    findByPrimaryKeyMethod,
                    findByPrimaryKeyPkJavadoc,
                    findByPrimaryKeyPkDeclaration,
                    findByPrimaryKeyPkFilterValues,
                    buildValueObjectMethod,
                    insertMethod,
                    insertParametersJavadoc,
                    insertParametersDeclaration,
                    updateMethod,
                    updateParametersJavadoc,
                    updateParametersDeclaration,
                    deleteMethod,
                    deletePkJavadoc,
                    deletePkDeclaration,
                    deleteWithFkMethod,
                    deleteWithFkPkJavadoc,
                    deleteWithFkPkDeclaration,
                    deleteWithFkDAODeleteRequest,
                    deleteWithFkPkValues,
                    classEnd);
        }
        else 
        {
            result =
                new XMLDAOTemplate(
                    tableTemplate,
                    metaDataManager,
                    header,
                    packageDeclaration,
                    packageName,
                    basePackageName,
                    repositoryName,
                    projectImports,
                    foreignDAOImports,
                    acmslImports,
                    jdkImports,
                    loggingImports,
                    javadoc,
                    classDefinition,
                    classStart,
                    classConstructor,
                    classInternalMethods,
                    buildKeyPkJavadoc,
                    buildKeyPkDeclaration,
                    buildKeyPkValues,
                    findByPrimaryKeyMethod,
                    findByPrimaryKeyPkJavadoc,
                    findByPrimaryKeyPkDeclaration,
                    findByPrimaryKeyPkFilterValues,
                    buildValueObjectMethod,
                    insertMethod,
                    insertParametersJavadoc,
                    insertParametersDeclaration,
                    updateMethod,
                    updateParametersJavadoc,
                    updateParametersDeclaration,
                    deleteMethod,
                    deletePkJavadoc,
                    deletePkDeclaration,
                    deleteWithFkMethod,
                    deleteWithFkPkJavadoc,
                    deleteWithFkPkDeclaration,
                    deleteWithFkDAODeleteRequest,
                    deleteWithFkPkValues,
                    classEnd) {};
        }

        return result;
    }

    /**
     * Generates a XML DAO template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     */
    public XMLDAOTemplate createXMLDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  packageName,
        final String                  basePackageName,
        final String                  repositoryName)
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
                    metaDataManager,
                    packageName,
                    basePackageName,
                    repositoryName);
        }
        else 
        {
            result =
                new XMLDAOTemplate(
                    tableTemplate,
                    metaDataManager,
                    packageName,
                    basePackageName,
                    repositoryName) {};
        }

        return result;
    }

    /**
     * Writes a XML DAO template to disk.
     * @param mockDAOTemplate the XML DAO template to write.
     * @param outputDir the output folder.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @throws IOException if the file cannot be created.
     * @precondition mockDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final XMLDAOTemplate mockDAOTemplate,
        final File           outputDir,
        final Project        project,
        final Task           task)
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
                              mockDAOTemplate
                                  .getTableTemplate()
                                      .getTableName().toLowerCase()),
                        '_')
                    + "DAO.java",
                    Project.MSG_VERBOSE);
            }
            
            t_FileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + "XML"
                + t_StringUtils.capitalize(
                      t_EnglishGrammarUtils.getSingular(
                          mockDAOTemplate
                              .getTableTemplate()
                                  .getTableName().toLowerCase()),
                      '_')
                + "DAO.java",
                mockDAOTemplate.toString());
        }
    }
}
