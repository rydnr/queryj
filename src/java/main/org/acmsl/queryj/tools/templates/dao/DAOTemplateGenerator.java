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
 * Description: Is able to generate DAO implementations according to
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
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.templates.dao.DAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateFactory;
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
 * Is able to generate DAO implementations according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOTemplateGenerator
    implements  DAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(DAOTemplateGenerator generator)
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
     * Retrieves a DAOTemplateGenerator instance.
     * @return such instance.
     */
    public static DAOTemplateGenerator getInstance()
    {
        DAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new DAOTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param templateFactoryClass the template factory.
     */
    public void addTemplateFactoryClass(
        String daoName,
        String engineName,
        String engineVersion,
        String templateFactoryClass)
    {
        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager     != null)
             && (engineName           != null)
             && (templateFactoryClass != null))
        {
            t_MappingManager.addTemplateFactoryClass(
                TemplateMappingManager.DAO_TEMPLATE_PREFIX + daoName,
                engineName,
                engineVersion,
                templateFactoryClass);
        }
    }

    /**
     * Retrieves the template factory class.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass(
        String daoName,
        String engineName,
        String engineVersion)
    {
        String result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (   (t_MappingManager != null)
             && (engineName       != null))
        {
            result =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.DAO_TEMPLATE_PREFIX + daoName,
                    engineName,
                    engineVersion);
        }

        return result;
    }

    /**
     * Retrieves the template factory instance.
     * @param daoName the DAO name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected DAOTemplateFactory getTemplateFactory(
            String daoName,
            String engineName,
            String engineVersion)
        throws  QueryJException
    {
        DAOTemplateFactory result = null;

        TemplateMappingManager t_MappingManager =
            TemplateMappingManager.getInstance();

        if  (t_MappingManager != null)
        {
            Object t_TemplateFactory =
                t_MappingManager.getTemplateFactoryClass(
                    TemplateMappingManager.DAO_TEMPLATE_PREFIX + daoName,
                    engineName,
                    engineVersion);

            if  (t_TemplateFactory != null)
            {
                if  (!(t_TemplateFactory instanceof DAOTemplateFactory))
                {
                    throw
                        new QueryJException(
                            "invalid.dao.template.factory");
                }
                else 
                {
                    result = (DAOTemplateFactory) t_TemplateFactory;
                }
            }
        }

        return result;
    }

    /**
     * Generates a DAO template.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     *        declaration.
     * @param findByPrimaryKeySelectFields the find by primary key select fields.
     * @param findByPrimaryKeyFilterDeclaration the find by primary key filter
     *        declaration.
     * @param findByPrimaryKeyFilterValues the find by primary key filter values.
     * @param buildValueObjectMethod the build value object method.
     * @param buildValueObjectValueRetrieval the build value object value retrieval.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param insertParametersSpecification the specification of the insert
              method's parameters.
     * @param insertKeywordParametersSpecification the specification of the insert
              method's keyword-based parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param updateParametersSpecification the specification of the update
              method's parameters.
     * @param updateFilter the update method's filter.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteFilterDeclaration the delete filter declaration.
     * @param deleteFilterValues the delete filter values.
     * @param deleteWithFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param classEnd the class end.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    public DAOTemplate createDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  header,
        final String                  packageDeclaration,
        final String                  packageName,
        final String                  engineName,
        final String                  engineVersion,
        final String                  quote,
        final String                  basePackageName,
        final String                  repositoryName,
        final String                  projectImports,
        final String                  foreignDAOImports,
        final String                  acmslImports,
        final String                  jdkImports,
        final String                  jdkExtensionImports,
        final String                  loggingImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  classConstructor,
        final String                  findByPrimaryKeyMethod,
        final String                  findByPrimaryKeyPkJavadoc,
        final String                  findByPrimaryKeyPkDeclaration,
        final String                  findByPrimaryKeySelectFields,
        final String                  findByPrimaryKeyFilterDeclaration,
        final String                  findByPrimaryKeyFilterValues,
        final String                  buildValueObjectMethod,
        final String                  buildValueObjectValueRetrieval,
        final String                  insertMethod,
        final String                  insertParametersJavadoc,
        final String                  insertParametersDeclaration,
        final String                  insertParametersSpecification,
        final String                  insertKeywordParametersSpecification,
        final String                  updateMethod,
        final String                  updateParametersJavadoc,
        final String                  updateParametersDeclaration,
        final String                  updateParametersSpecification,
        final String                  updateFilter,
        final String                  deleteMethod,
        final String                  deletePkJavadoc,
        final String                  deletePkDeclaration,
        final String                  deleteFilterDeclaration,
        final String                  deleteFilterValues,
        final String                  deleteWithFkMethod,
        final String                  deleteWithFkPkJavadoc,
        final String                  deleteWithFkPkDeclaration,
        final String                  deleteWithFkDAODeleteRequest,
        final String                  deleteWithFkPkValues,
        final String                  classEnd)
      throws  QueryJException
    {
        DAOTemplate result = null;

        if  (   (tableTemplate   != null)
             && (metaDataManager != null)
             && (packageName     != null)
             && (engineName      != null)
             && (engineVersion   != null)
             && (quote           != null))
        {
            DAOTemplateFactory t_TemplateFactory =
                getTemplateFactory(
                    tableTemplate.getTableName(), engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createDAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        header,
                        packageDeclaration,
                        packageName,
                        engineName,
                        engineVersion,
                        quote,
                        basePackageName,
                        repositoryName,
                        projectImports,
                        foreignDAOImports,
                        acmslImports,
                        jdkImports,
                        jdkExtensionImports,
                        loggingImports,
                        javadoc,
                        classDefinition,
                        classStart,
                        classConstructor,
                        findByPrimaryKeyMethod,
                        findByPrimaryKeyPkJavadoc,
                        findByPrimaryKeyPkDeclaration,
                        findByPrimaryKeySelectFields,
                        findByPrimaryKeyFilterDeclaration,
                        findByPrimaryKeyFilterValues,
                        buildValueObjectMethod,
                        buildValueObjectValueRetrieval,
                        insertMethod,
                        insertParametersJavadoc,
                        insertParametersDeclaration,
                        insertParametersSpecification,
                        insertKeywordParametersSpecification,
                        updateMethod,
                        updateParametersJavadoc,
                        updateParametersDeclaration,
                        updateParametersSpecification,
                        updateFilter,
                        deleteMethod,
                        deletePkJavadoc,
                        deletePkDeclaration,
                        deleteFilterDeclaration,
                        deleteFilterValues,
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
                    new DAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        header,
                        packageDeclaration,
                        packageName,
                        engineName,
                        engineVersion,
                        quote,
                        basePackageName,
                        repositoryName,
                        projectImports,
                        foreignDAOImports,
                        acmslImports,
                        jdkImports,
                        jdkExtensionImports,
                        loggingImports,
                        javadoc,
                        classDefinition,
                        classStart,
                        classConstructor,
                        findByPrimaryKeyMethod,
                        findByPrimaryKeyPkJavadoc,
                        findByPrimaryKeyPkDeclaration,
                        findByPrimaryKeySelectFields,
                        findByPrimaryKeyFilterDeclaration,
                        findByPrimaryKeyFilterValues,
                        buildValueObjectMethod,
                        buildValueObjectValueRetrieval,
                        insertMethod,
                        insertParametersJavadoc,
                        insertParametersDeclaration,
                        insertParametersSpecification,
                        insertKeywordParametersSpecification,
                        updateMethod,
                        updateParametersJavadoc,
                        updateParametersDeclaration,
                        updateParametersSpecification,
                        updateFilter,
                        deleteMethod,
                        deletePkJavadoc,
                        deletePkDeclaration,
                        deleteFilterDeclaration,
                        deleteFilterValues,
                        deleteWithFkMethod,
                        deleteWithFkPkJavadoc,
                        deleteWithFkPkDeclaration,
                        deleteWithFkDAODeleteRequest,
                        deleteWithFkPkValues,
                        classEnd) {};
            }
        }

        return result;
    }

    /**
     * Generates a DAO template.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the name of the repository.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public DAOTemplate createDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  packageName,
        final String                  engineName,
        final String                  engineVersion,
        final String                  quote,
        final String                  basePackageName,
        final String                  repositoryName)
      throws  QueryJException
    {
        DAOTemplate result = null;

        if  (   (tableTemplate   != null)
             && (metaDataManager != null)
             && (packageName     != null)
             && (engineName      != null)
             && (engineVersion   != null)
             && (quote           != null))
        {
            DAOTemplateFactory t_TemplateFactory =
                getTemplateFactory(
                    tableTemplate.getTableName(), engineName, engineVersion);

            if  (t_TemplateFactory != null)
            {
                result =
                    t_TemplateFactory.createDAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName,
                        engineName,
                        engineVersion,
                        quote,
                        basePackageName,
                        repositoryName);
            }
            else 
            {
                result =
                    new DAOTemplate(
                        tableTemplate,
                        metaDataManager,
                        packageName,
                        engineName,
                        engineVersion,
                        quote,
                        basePackageName,
                        repositoryName) {};
            }
        }

        return result;
    }

    /**
     * Writes a DAO template to disk.
     * @param daoTemplate the DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     */
    public void write(
            DAOTemplate daoTemplate,
            File        outputDir)
        throws  IOException
    {
        if  (   (daoTemplate != null)
             && (outputDir   != null))
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
                    + daoTemplate.getEngineName()
                    + t_StringUtils.capitalize(
                          daoTemplate
                              .getTableTemplate()
                                  .getTableName().toLowerCase(),
                          '_')
                    + "DAO.java",
                    daoTemplate.toString());
            }
        }
    }
}
