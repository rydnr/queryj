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
 * Description: Writes XML DAO test templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLDAOTestTemplateBuildHandler;
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
 * Writes XML DAO test templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLDAOTestTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a XMLDAOTestTemplateWritingHandler.
     */
    public XMLDAOTestTemplateWritingHandler() {};

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
                retrieveXMLDAOTestTemplates(parameters),
                retrieveOutputDir(parameters),
                XMLDAOTestTemplateGenerator.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param templates the templates.
     * @param outputDir the output dir.
     * @param generator the generator.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition templates != null
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected boolean handle(
        final Map parameters,
        final XMLDAOTestTemplate[] templates,
        final File outputDir,
        final XMLDAOTestTemplateGenerator generator)
      throws  BuildException
    {
        boolean result = false;

        try 
        {
            int t_iLength = (templates != null) ? templates.length : 0;

            for  (int t_iXMLDAOTestIndex = 0;
                      t_iXMLDAOTestIndex < t_iLength;
                      t_iXMLDAOTestIndex++)
            {
                generator.write(templates[t_iXMLDAOTestIndex], outputDir);
            }
        }
        catch  (final IOException ioException)
        {
            throw new BuildException(ioException);
        }
        
        return result;
    }

    /**
     * Retrieves the dao test templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws BuildException if the template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected XMLDAOTestTemplate[] retrieveXMLDAOTestTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (XMLDAOTestTemplate[])
                parameters.get(
                    TemplateMappingManager.XML_DAO_TEST_TEMPLATES);
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
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                PackageUtils.getInstance());
    }
        
    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project package.
     * @param subFolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition projectFolder != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final File projectFolder,
        final String projectPackage,
        final boolean subFolders,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveXMLDAOTestFolder(
                projectFolder,
                projectPackage,
                subFolders);
    }
}
