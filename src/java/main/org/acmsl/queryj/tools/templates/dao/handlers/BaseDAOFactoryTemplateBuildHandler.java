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
 * Description: Builds a base DAO factory templates using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

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
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.BaseDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Builds base DAO factory templates using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public class BaseDAOFactoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a BaseDAOFactoryTemplateBuildHandler.
     */
    public BaseDAOFactoryTemplateBuildHandler() {};

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
        return
            handle(
                command.getAttributeMap(),
                command.getProject(),
                command.getTask());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(
        final Map parameters,
        final Project project,
        final Task task)
      throws  BuildException
    {
        return
            handle(
                parameters,
                retrieveDatabaseMetaDataManager(parameters),
                retrievePackage(parameters),
                retrieveProjectPackage(parameters),
                BaseDAOFactoryTemplateGenerator.getInstance(),
                retrieveTableTemplates(parameters),
                project,
                task);
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param projectPackage the project package.
     * @param templateFactory the template factory.
     * @param tableTemplates the table templates.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition templateFactory != null
     * @precondition tableTemplates != null
     */
    protected boolean handle(
        final Map parameters,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String projectPackage,
        final BaseDAOFactoryTemplateFactory templateFactory,
        final TableTemplate[] tableTemplates,
        final Project project,
        final Task task)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            BaseDAOFactoryTemplate[] t_aBaseDAOFactoryTemplates =
                new BaseDAOFactoryTemplate[tableTemplates.length];

            for  (int t_iBaseDAOFactoryIndex = 0;
                      t_iBaseDAOFactoryIndex < t_aBaseDAOFactoryTemplates.length;
                      t_iBaseDAOFactoryIndex++) 
            {
                t_aBaseDAOFactoryTemplates[t_iBaseDAOFactoryIndex] =
                    templateFactory.createBaseDAOFactoryTemplate(
                        tableTemplates[t_iBaseDAOFactoryIndex],
                        metaDataManager,
                        packageName,
                        projectPackage,
                        project,
                        task);
            }

            storeBaseDAOFactoryTemplates(
                t_aBaseDAOFactoryTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
        final Map parameters)
        throws  BuildException
    {
        return
            (DatabaseMetaDataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
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
            packageUtils.retrieveBaseDAOFactoryPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Stores the base DAO factory template collection in given attribute map.
     * @param baseDAOFactoryTemplates the base DAO factory templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition baseDAOFactoryTemplates != null
     * @precondition parameters != null
     */
    protected void storeBaseDAOFactoryTemplates(
        final BaseDAOFactoryTemplate[] baseDAOFactoryTemplates,
        final Map parameters)
        throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.BASE_DAO_FACTORY_TEMPLATES,
            baseDAOFactoryTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
