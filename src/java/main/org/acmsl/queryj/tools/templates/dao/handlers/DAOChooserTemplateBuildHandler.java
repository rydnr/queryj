/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Builds a DAOChooser using database metadata.
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
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.DAOChooserTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOChooserTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.version.Version;
import org.acmsl.commons.version.VersionFactory;

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
 * Builds a DAOChooser using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOChooserTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a DAOChooserTemplateBuildHandler.
     */
    public DAOChooserTemplateBuildHandler() {};

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
            Map t_mAttributes = command.getAttributeMap();

            storeDAOChooserTemplate(
                buildDAOChooserTemplate(t_mAttributes),
                t_mAttributes);
        }
        
        return result;
    }

    /**
     * Builds a DAOChooser template using the information
     * stored in the attribute map.
     * @param parameters the parameter map.
     * @return the TableRepository instance.
     * @throws BuildException if the template cannot be created.
     */
    protected DAOChooserTemplate buildDAOChooserTemplate(Map parameters)
        throws  BuildException
    {
        DAOChooserTemplate result = null;

        if  (parameters != null) 
        {
            result =
                buildDAOChooserTemplate(
                    retrievePackage(parameters),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.REPOSITORY));

            if  (result != null) 
            {
                String[] t_astrTableNames =
                    (String[])
                        parameters.get(
                            TableTemplateBuildHandler.TABLE_NAMES);

                if  (t_astrTableNames != null)
                {
                    for  (int t_iTableIndex = 0;
                              t_iTableIndex < t_astrTableNames.length;
                              t_iTableIndex++)
                    {
                        result.addTable(t_astrTableNames[t_iTableIndex]);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Builds a DAOChooser template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @throws org.apache.tools.ant.BuildException whenever the template
     * information is not valid.
     */
    protected DAOChooserTemplate buildDAOChooserTemplate(
            String packageName,
            String repository)
        throws  BuildException
    {
        DAOChooserTemplate result = null;

        if  (   (packageName != null)
             && (repository  != null))
        {
            DAOChooserTemplateGenerator t_DAOChooserTemplateGenerator =
                DAOChooserTemplateGenerator.getInstance();

            if  (t_DAOChooserTemplateGenerator != null)
            {
                result =
                    t_DAOChooserTemplateGenerator.createDAOChooserTemplate(
                        packageName, repository);
            }
        }

        return result;
    }


    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveDAOChooserPackage(
                    (String)
                        parameters.get(ParameterValidationHandler.PACKAGE));
        }
        
        return result;
    }

    /**
     * Stores the DAOChooser template in given attribute map.
     * @param daoChooserTemplate the DAOChooser template.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for any reason.
     */
    protected void storeDAOChooserTemplate(
            DAOChooserTemplate daoChooserTemplate,
            Map                parameters)
        throws  BuildException
    {
        if  (   (daoChooserTemplate != null)
             && (parameters         != null))
        {
            parameters.put(
                TemplateMappingManager.DAO_CHOOSER_TEMPLATE,
                daoChooserTemplate);
        }
    }
}
