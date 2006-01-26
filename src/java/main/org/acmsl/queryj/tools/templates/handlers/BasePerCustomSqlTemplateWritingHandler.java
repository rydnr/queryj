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
 * Description: Writes per-custom-sql templates.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomSqlTemplateBuildHandler;
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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes <i>per-custom-sql</i> templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class BasePerCustomSqlTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerCustomSqlTemplateWritingHandler</code> instance.
     */
    public BasePerCustomSqlTemplateWritingHandler() {};

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
        return handle(parameters, retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        final Map parameters, final DatabaseMetaData metaData)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            handle(parameters, metaData.getDatabaseProductName());
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected void handle(final Map parameters, final String engineName)
      throws  BuildException
    {
        handle(
            retrieveTemplate(parameters),
            retrieveOutputDir(engineName, parameters),
            retrieveTemplateGenerator());
    }
            
    /**
     * Handles given information.
     * @param template the template.
     * @param outputDir the output dir.
     * @param templateGenerator the template generator.
     * @throws BuildException if the build process cannot be performed.
     * @precondition template != null
     * @precondition outputDir != null
     * @precondition templateGenerator != null
     */
    protected void handle(
        final BasePerCustomSqlTemplate template,
        final File outputDir,
        final BasePerCustomSqlTemplateGenerator templateGenerator)
      throws  BuildException
    {
        try 
        {
            templateGenerator.write(template, outputDir);
        }
        catch  (final IOException ioException)
        {
            throw new BuildException(ioException);
        }
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    protected abstract BasePerCustomSqlTemplateGenerator retrieveTemplateGenerator();

    /**
     * Retrieves the template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected abstract BasePerCustomSqlTemplate retrieveTemplate(
        final Map parameters)
      throws  BuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(
        final String engineName, final Map parameters)
      throws  BuildException
    {
        return
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected abstract File retrieveOutputDir(
        final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  BuildException;
}
