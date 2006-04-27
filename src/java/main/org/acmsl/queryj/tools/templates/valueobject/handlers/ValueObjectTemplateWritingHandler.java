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
 * Description: Writes value object templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.handlers.ValueObjectTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectTemplateGenerator;

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
 * Writes DAO templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class ValueObjectTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty value object template array.
     */
    public static final ValueObjectTemplate[]
        EMPTY_VALUE_OBJECT_TEMPLATE_ARRAY =
            new ValueObjectTemplate[0];

    /**
     * Creates a ValueObjectTemplateWritingHandler.
     */
    public ValueObjectTemplateWritingHandler() {};

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
     * Handles given command.
     * @param attributes the attributes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition attributes != null
     */
    protected boolean handle(final Map attributes)
        throws  BuildException
    {
        return
            handle(
                retrieveValueObjectTemplates(attributes),
                retrieveOutputDir(attributes),
                ValueObjectTemplateGenerator.getInstance());
    }

    /**
     * Handles given command.
     * @param templates the templates.
     * @param outputDir the output dir.
     * @param generator the <code>ValueObjectTemplateGenerator</code>
     * instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition templates != null
     * @precondition outputDir != null
     * @precondition generator != null

     */
    protected boolean handle(
        final ValueObjectTemplate[] templates,
        final File outputDir,
        final ValueObjectTemplateGenerator generator)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            for  (int t_iValueObjectIndex = 0;
                      t_iValueObjectIndex < templates.length;
                      t_iValueObjectIndex++)
            {
                generator.write(
                    templates[t_iValueObjectIndex], outputDir);
            }
        }
        catch  (IOException ioException)
        {
            throw new BuildException(ioException);
        }

        return result;
    }

    /**
     * Retrieves the value object template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected ValueObjectTemplate[] retrieveValueObjectTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (ValueObjectTemplate[])
                parameters.get(
                    TemplateMappingManager.VALUE_OBJECT_TEMPLATES);
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
     * @param baseDir the base dir.
     * @param projectPackage the project package.
     * @param subFolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition baseDir != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final File baseDir,
        final String projectPackage,
        final boolean subFolders,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectFolder(
                baseDir, projectPackage, subFolders);
    }
}
