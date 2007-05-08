/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: MockDAOTestTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a Mock DAO test template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOTestTemplateFactory;
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
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
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
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
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
                retrieveDatabaseProductName(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected boolean handle(
        final Map parameters, final String engineName)
    {
        return
            handle(
                parameters,
                retrieveMetadataManager(parameters),
                retrieveTableTemplates(parameters),
                retrieveValueObjectPackage(parameters),
                retrieveMockDAOPackage(engineName, parameters),
                retrieveMockDAOTestPackage(parameters),
                retrieveHeader(parameters),
                MockDAOTestTemplateGenerator.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param tableTemplates the table templates.
     * @param valueObjectPackage such package.
     * @param mockDAOPackage such package.
     * @param mockDAOTestPackage such package.
     * @param header the header.
     * @param templateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metadataManager != null
     * @precondition tableTemplates != null
     * @precondition valueObjectPackage != null
     * @precondition mockDAOPackage != null
     * @precondition mockDAOTestPackage != null
     * @precondition templateFactory != null
     */
    protected boolean handle(
        final Map parameters,
        final MetadataManager metadataManager,
        final TableTemplate[] tableTemplates,
        final String valueObjectPackage,
        final String mockDAOPackage,
        final String mockDAOTestPackage,
        final String header,
        final MockDAOTestTemplateFactory templateFactory)
      throws  BuildException
    {
        boolean result = false;

        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;
            
        try
        {
            MockDAOTestTemplate[] t_aMockDAOTestTemplates =
                new MockDAOTestTemplate[t_iLength];

            for  (int t_iMockDAOTestIndex = 0;
                      t_iMockDAOTestIndex < t_iLength;
                      t_iMockDAOTestIndex++) 
            {
                t_aMockDAOTestTemplates[t_iMockDAOTestIndex] =
                    templateFactory.createMockDAOTestTemplate(
                        tableTemplates[t_iMockDAOTestIndex],
                        metadataManager,
                        mockDAOTestPackage,
                        mockDAOPackage,
                        valueObjectPackage,
                        header);

                storeTestTemplate(
                    t_aMockDAOTestTemplates[t_iMockDAOTestIndex],
                    parameters);
            }

            storeMockDAOTestTemplates(t_aMockDAOTestTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the Mock DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveMockDAOTestPackage(final Map parameters)
      throws  BuildException
    {
        return
            retrieveMockDAOTestPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the Mock DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveMockDAOTestPackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveMockDAOTestPackage(
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }

    /**
     * Retrieves the Mock DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveMockDAOPackage(
        final String engineName, final Map parameters)
      throws  BuildException
    {
        return
            retrieveMockDAOPackage(
                engineName, parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the Mock DAO's package name from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveMockDAOPackage(
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveMockDAOPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveValueObjectPackage(final Map parameters)
        throws  BuildException
    {
        return
            retrieveValueObjectPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveValueObjectPackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectPackage(
                retrieveProjectPackage(parameters));
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
        return
            (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the Mock DAO template collection in given attribute map.
     * @param mockDAOTestTemplates the Mock DAO templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition mockDAOTestTemplates != null
     * @precondition parameters != null
     */
    protected void storeMockDAOTestTemplates(
        final MockDAOTestTemplate[] mockDAOTestTemplates,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.MOCK_DAO_TEST_TEMPLATES,
            mockDAOTestTemplates);
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
