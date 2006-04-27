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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds the configuration properties.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplate;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DataAccessContextLocalTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds the configuration properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DataAccessContextLocalTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a DataAccessContextLocalTemplateBuildHandler.
     */
    public DataAccessContextLocalTemplateBuildHandler() {};

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
                buildDataAccessContextLocalTemplate(
                    parameters));
    }
                
    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param template the template.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition template != null
     */
    protected boolean handle(
        final Map parameters, final DataAccessContextLocalTemplate template)
      throws  BuildException
    {
        storeDataAccessContextLocalTemplate(template, parameters);

        return false;
    }

    /**
     * Builds a DataAccessContextLocal template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     */
    protected DataAccessContextLocalTemplate buildDataAccessContextLocalTemplate(
        final Map parameters)
      throws  BuildException
    {
        return
            buildDataAccessContextLocalTemplate(
                parameters,
                retrieveDatabaseMetaData(parameters));
    }

    /**
     * Builds a DataAccessContextLocal template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param metaData the database meta data.
     * @param repository the repository.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     * @precondition metaData != null
     * @preconditrion repository != null
     */
    protected DataAccessContextLocalTemplate buildDataAccessContextLocalTemplate(
        final Map parameters, final DatabaseMetaData metaData)
      throws  BuildException
    {
        DataAccessContextLocalTemplate result = null;

        try
        {
            result =
                buildDataAccessContextLocalTemplate(
                    parameters,
                    retrieveJNDILocation(parameters),
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion(),
                    retrieveProjectPackage(parameters),
                    retrieveTableNames(parameters),
                    retrieveHeader(parameters));
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }

        return result;
    }

    /**
     * Builds a DataAccessContextLocal template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param projectPackage the project package.
     * @param tableNames the table names.
     * @param header the header.
     * @return the template instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     * @preconditrion jndiLocation != null
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition tableNames != null
     */
    protected DataAccessContextLocalTemplate buildDataAccessContextLocalTemplate(
        final Map parameters,
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String projectPackage,
        final String[] tableNames,
        final String header)
      throws  BuildException
    {
        DataAccessContextLocalTemplate result =
            buildDataAccessContextLocalTemplate(
                jndiLocation,
                engineName,
                engineVersion,
                projectPackage,
                header);

        int t_iLength = (tableNames != null) ? tableNames.length : 0;
        
        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iLength;
                  t_iTableIndex++)
        {
            result.addTable(tableNames[t_iTableIndex]);
        }

        return result;
    }

    /**
     * Builds a DataAccessContextLocal template using given information.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @throws BuildException whenever the template
     * information is not valid.
     * @precondition jndiLocation != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     */
    protected DataAccessContextLocalTemplate buildDataAccessContextLocalTemplate(
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String header)
      throws  BuildException
    {
        return
            buildDataAccessContextLocalTemplate(
                jndiLocation,
                engineName,
                engineVersion,
                basePackageName,
                header,
                DataAccessContextLocalTemplateGenerator.getInstance());
    }

    /**
     * Builds a DataAccessContextLocal template using given information.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param header the header.
     * @param templateFactory the template factory.
     * @throws BuildException whenever the template
     * information is not valid.
     * @precondition jndiLocation != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     * @precondition templateFactory != null
     */
    protected DataAccessContextLocalTemplate buildDataAccessContextLocalTemplate(
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String header,
        final DataAccessContextLocalTemplateFactory templateFactory)
      throws  BuildException
    {
        DataAccessContextLocalTemplate result = null;

        try
        {
            result =
                templateFactory.createDataAccessContextLocalTemplate(
                    jndiLocation,
                    engineName,
                    engineVersion,
                    basePackageName,
                    header);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }

        return result;
    }

    /**
     * Retrieves the JNDI location from the attribute map.
     * @param parameters the parameter map.
     * @return the location.
     * @precondition parameters != null
     */
    protected String retrieveJNDILocation(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JNDI_DATASOURCES);
    }

    /**
     * Retrieves the table names.
     * @param parameters the parameters.
     * @return the table names.
     * @precondition parameters != null
     */
    protected String[] retrieveTableNames(final Map parameters)
    {
        return
            (String[]) parameters.get(TableTemplateBuildHandler.TABLE_NAMES);
    }

    /**
     * Stores the DataAccessContextLocal template in given attribute map.
     * @param dataAccessContextLocalTemplate the DataAccessContextLocal template.
     * @param parameters the parameter map.
     * @precondition dataAccessContextLocalTemplate != null
     * @precondition parameters != null
     */
    protected void storeDataAccessContextLocalTemplate(
        final DataAccessContextLocalTemplate dataAccessContextLocalTemplate,
        final Map parameters)
        throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.DATAACCESSCONTEXTLOCAL_TEMPLATE,
            dataAccessContextLocalTemplate);
    }
}
