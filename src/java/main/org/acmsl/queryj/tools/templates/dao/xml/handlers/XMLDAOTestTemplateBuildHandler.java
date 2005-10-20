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
 * Description: Builds a XML DAO test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Handles the building of XML DAO test templates using database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLDAOTestTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a XMLDAOTestTemplateBuildHandler.
     */
    public XMLDAOTestTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                parameters,
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param projectPackage the project package.
     * @param useSubfolders whether to use subfolders or not.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition projectPackage != null
     */
    protected boolean handle(
        final Map parameters,
        final String projectPackage,
        final boolean useSubfolders)
      throws  BuildException
    {
        return
            handle(
                parameters,
                retrieveDatabaseMetaData(parameters),
                retrieveDatabaseMetaDataManager(parameters),
                retrieveXMLDAOTestPackage(projectPackage, useSubfolders),
                retrieveValueObjectPackage(projectPackage),
                retrieveXMLDAOPackage(projectPackage),
                retrieveTableTemplates(parameters),
                XMLDAOTestTemplateGenerator.getInstance());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metaData the database metadata.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param voPackageName the value-object package name.
     * @param xmlDAOPackageName the package name of the XML DAOs.
     * @param tableTemplates the table templates.
     * @param templateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition voPackageName != null
     * @precondition xmlDAOPackageName != null
     * @precondition tableTemplates != null
     * @precondition templateFactory != null
     */
    protected boolean handle(
        final Map parameters,
        final DatabaseMetaData metaData,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String voPackageName,
        final String xmlDAOPackageName,
        final TableTemplate[] tableTemplates,
        final XMLDAOTestTemplateFactory templateFactory)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;

            XMLDAOTestTemplate[] t_aXMLDAOTestTemplates =
                new XMLDAOTestTemplate[t_iLength];

            for  (int t_iXMLDAOTestIndex = 0;
                      t_iXMLDAOTestIndex < t_iLength;
                      t_iXMLDAOTestIndex++) 
            {
                t_aXMLDAOTestTemplates[t_iXMLDAOTestIndex] =
                    templateFactory.createXMLDAOTestTemplate(
                        tableTemplates[t_iXMLDAOTestIndex],
                        metaDataManager,
                        packageName,
                        xmlDAOPackageName,
                        voPackageName);

                storeTestTemplate(
                    t_aXMLDAOTestTemplates[t_iXMLDAOTestIndex],
                    parameters);
            }

            storeXMLDAOTestTemplates(t_aXMLDAOTestTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the XML DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveXMLDAOTestPackage(
        final String projectPackage, final boolean useSubfolders)
      throws  BuildException
    {
        return
            retrieveXMLDAOTestPackage(
                projectPackage, useSubfolders, PackageUtils.getInstance());
    }

    /**
     * Retrieves the XML DAO Test's package name from given information.
     * @param projectPackage the project package.
     * @param useSubfolders whether to use subfolders.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrieveXMLDAOTestPackage(
        final String projectPackage,
        final boolean useSubfolders,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveXMLDAOTestPackage(
                projectPackage, useSubfolders);
    }

    /**
     * Retrieves the XML DAO's package name from the project package.
     * @param projectPackage the project package.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     */
    protected String retrieveXMLDAOPackage(final String projectPackage)
        throws  BuildException
    {
        return retrieveXMLDAOPackage(projectPackage, PackageUtils.getInstance());
    }

    /**
     * Retrieves the XML DAO's package name from the project package.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrieveXMLDAOPackage(
        final String projectPackage, final PackageUtils packageUtils)
      throws  BuildException
    {
        return packageUtils.retrieveXMLDAOPackage(projectPackage);
    }

    /**
     * Retrieves the value object's package name from the project package.
     * @param projectPackage the project package.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     */
    protected String retrieveValueObjectPackage(final String projectPackage)
        throws  BuildException
    {
        return
            retrieveValueObjectPackage(
                projectPackage, PackageUtils.getInstance());
    }

    /**
     * Retrieves the value object's package name from the project package.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrieveValueObjectPackage(
        final String projectPackage, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectPackage(projectPackage);
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected Collection retrieveTestTemplates(final Map parameters)
        throws  BuildException
    {
        return (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the XML DAO template collection in given attribute map.
     * @param xmlDAOTestTemplates the XML DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition xmlDAOTestTemplates != null
     * @precondition parameters != null
     */
    protected void storeXMLDAOTestTemplates(
        final XMLDAOTestTemplate[] xmlDAOTestTemplates,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.XML_DAO_TEST_TEMPLATES,
            xmlDAOTestTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
      throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTestTemplates(
        final Collection templates, final Map parameters)
      throws  BuildException
    {
        parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTestTemplate(
        final TestTemplate template, final Map parameters)
      throws  BuildException
    {
        Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates == null) 
        {
            t_cTestTemplates = new ArrayList();
            storeTestTemplates(t_cTestTemplates, parameters);
        }

        t_cTestTemplates.add(template);
    }
}
