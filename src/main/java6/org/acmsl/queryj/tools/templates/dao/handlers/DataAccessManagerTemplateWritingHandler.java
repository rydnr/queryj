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
 * Filename: DataAccessManagerTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes the data access manager template.
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
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplate;
import org.acmsl.queryj.tools.templates.dao.DataAccessManagerTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers.DataAccessManagerTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Writes the data access manager template.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class DataAccessManagerTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a DataAccessManagerTemplateWritingHandler.
     */
    public DataAccessManagerTemplateWritingHandler() {};

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
                retrieveDataAccessManagerTemplate(parameters),
                retrieveOutputDir(parameters),
                DataAccessManagerTemplateGenerator.getInstance());
    }

    /**
     * Writes the DataAccessManager template.
     * @param template the template.
     * @param outputDir the output dir.
     * @param generator the <code>DataAccessManagerTemplateGenerator</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected boolean handle(
        final DataAccessManagerTemplate template,
        final File outputDir,
        final DataAccessManagerTemplateGenerator generator)
      throws  BuildException
    {
        boolean result = false;

        try 
        {
            generator.write(template, outputDir);
        }
        catch  (final IOException ioException)
        {
            throw new BuildException(ioException);
        }
        
        return result;
    }

    /**
     * Retrieves the data access manager template from the attribute map.
     * @param parameters the parameter map.
     * @return the repository instance.
     * @throws BuildException if the data access manager template process if faulty.
     * @precondition parameters != null
     */
    protected DataAccessManagerTemplate retrieveDataAccessManagerTemplate(
        final Map parameters)
      throws  BuildException
    {
        return
            (DataAccessManagerTemplate)
                parameters.get(
                    TemplateMappingManager.DATA_ACCESS_MANAGER_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(final Map parameters)
        throws  BuildException
    {
        return
            retrieveOutputDir(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveDataAccessManagerFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
