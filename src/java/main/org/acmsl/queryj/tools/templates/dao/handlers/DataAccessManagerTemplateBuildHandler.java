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
 * Description: Builds a data access manager using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplate;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a data access manager using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DataAccessManagerTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a DataAccessManagerTemplateBuildHandler.
     */
    public DataAccessManagerTemplateBuildHandler() {};

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
        storeDataAccessManagerTemplate(
            buildDataAccessManagerTemplate(
                parameters),
            parameters);
        
        return false;
    }

    /**
     * Builds a data access manager template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @return the TableRepository instance.
     * @throws BuildException if the template cannot be created.
     * @precondition parameters != null
     */
    protected DataAccessManagerTemplate buildDataAccessManagerTemplate(
        final Map parameters)
      throws  BuildException
    {
        DataAccessManagerTemplate result =
            buildDataAccessManagerTemplate(
                retrievePackage(parameters),
                (String)
                    parameters.get(ParameterValidationHandler.REPOSITORY),
                DataAccessManagerTemplateGenerator.getInstance());

        if  (result != null) 
        {
            String[] t_astrTableNames =
                (String[])
                    parameters.get(
                        TableTemplateBuildHandler.TABLE_NAMES);

            int t_iLength =
                (t_astrTableNames != null) ? t_astrTableNames.length : 0;
        
            for  (int t_iTableIndex = 0;
                      t_iTableIndex < t_iLength;
                      t_iTableIndex++)
            {
                result.addTable(t_astrTableNames[t_iTableIndex]);
            }
        }

        return result;
    }

    /**
     * Builds a data access manager template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @param templateFactory the template factory.
     * @throws org.apache.tools.ant.BuildException whenever the template
     * information is not valid.
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition templateFactory != null
     */
    protected DataAccessManagerTemplate buildDataAccessManagerTemplate(
        final String packageName,
        final String repository,
        final DataAccessManagerTemplateFactory templateFactory)
      throws  BuildException
    {
        return
            templateFactory.createDataAccessManagerTemplate(
                packageName, repository);
    }


    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveDataAccessManagerPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Stores the data access manager template in given attribute map.
     * @param dataAccessManagerTemplate the data access manager template.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for any reason.
     * @precondition dataAccessManagerTemplate != null
     * @precondition parameters != null
     */
    protected void storeDataAccessManagerTemplate(
        final DataAccessManagerTemplate dataAccessManagerTemplate,
        final Map parameters)
        throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.DATA_ACCESS_MANAGER_TEMPLATE,
            dataAccessManagerTemplate);
    }
}
