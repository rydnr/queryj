//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MockDAOTestTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a <code>MockDAOTestTemplateBuildHandler</code> instance.
     */
    public MockDAOTestTemplateBuildHandler() {};

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplates(parameters, retrieveDatabaseMetaData(parameters));

        return false;
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters, @NotNull final DatabaseMetaData metaData)
      throws  QueryJBuildException
    {
        try
        {
            buildTemplates(parameters, metaData.getDatabaseProductName());
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                      "Cannot retrieve database product name, "
                    + "version or quote string",
                    sqlException);
        }
    }

    /**
     * Builds the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters, final String engineName)
      throws  QueryJBuildException
    {
        buildTemplates(
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
     * Builds the <code>MockDAOTest</code> templates..
     * @param parameters the parameters.
     * @param metadataManager the database metadata manager.
     * @param tableTemplates the table templates.
     * @param valueObjectPackage such package.
     * @param mockDAOPackage such package.
     * @param mockDAOTestPackage such package.
     * @param header the header.
     * @param templateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metadataManager != null
     * @precondition tableTemplates != null
     * @precondition valueObjectPackage != null
     * @precondition mockDAOPackage != null
     * @precondition mockDAOTestPackage != null
     * @precondition templateFactory != null
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        final MetadataManager metadataManager,
        @Nullable final TableTemplate[] tableTemplates,
        final String valueObjectPackage,
        final String mockDAOPackage,
        final String mockDAOTestPackage,
        final String header,
        @NotNull final MockDAOTestTemplateFactory templateFactory)
      throws  QueryJBuildException
    {
        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;
            
        @NotNull MockDAOTestTemplate[] t_aMockDAOTestTemplates =
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

    /**
     * Retrieves the Mock DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveMockDAOTestPackage(@NotNull final Map parameters)
    {
        return
            retrieveMockDAOTestPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the Mock DAO Test's package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveMockDAOTestPackage(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
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
     * @precondition parameters != null
     */
    protected String retrieveMockDAOPackage(
        final String engineName, @NotNull final Map parameters)
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
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveMockDAOPackage(
        final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveMockDAOPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveValueObjectPackage(@NotNull final Map parameters)
    {
        return
            retrieveValueObjectPackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the value object's package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrieveValueObjectPackage(
        @NotNull final Map parameters, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveValueObjectPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @precondition parameters != null
     */
    @NotNull
    protected Collection retrieveTestTemplates(@NotNull final Map parameters)
    {
        return
            (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
    }

    /**
     * Stores the Mock DAO template collection in given attribute map.
     * @param mockDAOTestTemplates the Mock DAO templates.
     * @param parameters the parameter map.
     * @precondition mockDAOTestTemplates != null
     * @precondition parameters != null
     */
    protected void storeMockDAOTestTemplates(
        final MockDAOTestTemplate[] mockDAOTestTemplates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.MOCK_DAO_TEST_TEMPLATES,
            mockDAOTestTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(@NotNull final Map parameters)
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
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTestTemplates(
        final Collection templates, @NotNull final Map parameters)
    {
        parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTestTemplate(
        final TestTemplate template, @NotNull final Map parameters)
    {
        @NotNull Collection t_cTestTemplates = retrieveTestTemplates(parameters);

        if  (t_cTestTemplates == null) 
        {
            t_cTestTemplates = new ArrayList();
            storeTestTemplates(t_cTestTemplates, parameters);
        }

        t_cTestTemplates.add(template);
    }
}
