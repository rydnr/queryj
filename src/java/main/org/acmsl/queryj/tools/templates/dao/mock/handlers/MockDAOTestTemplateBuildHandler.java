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
 * Description: Builds a Mock DAO test template using database metadata.
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
package org.acmsl.queryj.tools.templates.dao.mock.handlers;

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
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplateGenerator;
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
 * Handles the building of Mock DAO test templates using database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$ at $Date$
 */
public class MockDAOTestTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a MockDAOTestTemplateBuildHandler.
     */
    public MockDAOTestTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            try
            {
                Map attributes = command.getAttributeMap();

                DatabaseMetaData t_MetaData =
                    retrieveDatabaseMetaData(attributes);

                DatabaseMetaDataManager t_MetaDataManager =
                    retrieveDatabaseMetaDataManager(attributes);

                String t_strPackage =
                    retrieveMockDAOTestPackage(attributes);

                MockDAOTestTemplateGenerator t_MockDAOTestTemplateGenerator =
                    MockDAOTestTemplateGenerator.getInstance();

                if  (   (t_MetaData                     != null)
                     && (t_MetaDataManager              != null)
                     && (t_MockDAOTestTemplateGenerator != null))
                {
                    TableTemplate[] t_aTableTemplates =
                        retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        MockDAOTestTemplate[] t_aMockDAOTestTemplates =
                            new MockDAOTestTemplate[t_aTableTemplates.length];

                        for  (int t_iMockDAOTestIndex = 0;
                                  t_iMockDAOTestIndex < t_aMockDAOTestTemplates.length;
                                  t_iMockDAOTestIndex++) 
                        {
                            t_aMockDAOTestTemplates[t_iMockDAOTestIndex] =
                                t_MockDAOTestTemplateGenerator
                                    .createMockDAOTestTemplate(
                                        t_aTableTemplates[t_iMockDAOTestIndex],
                                        t_MetaDataManager,
                                        t_strPackage,
                                        retrieveMockDAOPackage(
                                            t_MetaData
                                                .getDatabaseProductName(),
                                            attributes),
                                        retrieveValueObjectPackage(attributes));

                            storeTestTemplate(
                                t_aMockDAOTestTemplates[t_iMockDAOTestIndex],
                                attributes);
                        }

                        storeMockDAOTestTemplates(t_aMockDAOTestTemplates, attributes);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (QueryJException queryjException)
            {
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaData)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaDataManager result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaDataManager)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Retrieves the Mock DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveMockDAOTestPackage(final Map parameters)
      throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveMockDAOTestPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the Mock DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveMockDAOPackage(
        final String engineName, final Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveMockDAOPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveValueObjectPackage(final Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveValueObjectPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected Collection retrieveTestTemplates(Map parameters)
        throws  BuildException
    {
        Collection result = null;

        if  (parameters != null)
        {
            result =
                (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Stores the Mock DAO template collection in given attribute map.
     * @param mockDAOTestTemplates the Mock DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeMockDAOTestTemplates(
        final MockDAOTestTemplate[] mockDAOTestTemplates,
        final Map parameters)
      throws  BuildException
    {
        if  (   (mockDAOTestTemplates != null)
             && (parameters       != null))
        {
            parameters.put(
                TemplateMappingManager.MOCK_DAO_TEST_TEMPLATES,
                mockDAOTestTemplates);
        }
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected TableTemplate[] retrieveTableTemplates(
            Map parameters)
        throws  BuildException
    {
        TableTemplate[] result = EMPTY_TABLE_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (TableTemplate[])
                    parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
        }

        return result;
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplates(Collection templates, Map parameters)
        throws  BuildException
    {
        if  (   (templates  != null)
             && (parameters != null))
        {
            parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
        }
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplate(TestTemplate template, Map parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
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
}
