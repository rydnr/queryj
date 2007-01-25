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
 * Filename: XMLValueObjectFactoryTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes XML value object factory templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.dao.xml.handlers.XMLValueObjectFactoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplateGenerator;

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
 * Writes XML value object factory templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLValueObjectFactoryTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty value object template array.
     */
    public static final XMLValueObjectFactoryTemplate[]
        EMPTY_XML_VALUE_OBJECT_FACTORY_TEMPLATE_ARRAY =
            new XMLValueObjectFactoryTemplate[0];

    /**
     * Creates a XMLValueObjectFactoryTemplateWritingHandler.
     */
    public XMLValueObjectFactoryTemplateWritingHandler() {};

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
                retrieveXMLValueObjectFactoryTemplates(parameters),
                retrieveOutputDir(parameters),
                XMLValueObjectFactoryTemplateGenerator.getInstance());
    }
                
    /**
     * Writes the XMLValueObjectFactory templates.
     * @param templates the templates.
     * @param outputDir the output dir.
     * @param generator the <code>XMLValueObjectFactoryTemplateGenerator</code>
     * instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition templates != null
     * @precondition outputDir != null
     * @precondition generator != null
     */
    protected boolean handle(
        final XMLValueObjectFactoryTemplate[] templates,
        final File outputDir,
        final XMLValueObjectFactoryTemplateGenerator generator)
      throws  BuildException
    {
        boolean result = false;

        try 
        {
            int t_iLength = (templates != null) ? templates.length : 0;

            for  (int t_iValueObjectFactoryIndex = 0;
                      t_iValueObjectFactoryIndex < t_iLength;
                      t_iValueObjectFactoryIndex++)
            {
                generator.write(
                    templates[t_iValueObjectFactoryIndex], outputDir);
            }
        }
        catch  (final IOException ioException)
        {
            throw new BuildException(ioException);
        }
        catch  (final Throwable throwable)
        {
            throw new BuildException(throwable);
        }
        
        return result;
    }

    /**
     * Retrieves the value object factory template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected XMLValueObjectFactoryTemplate[] retrieveXMLValueObjectFactoryTemplates(
        final Map parameters)
      throws  BuildException
    {
        XMLValueObjectFactoryTemplate[] result =
            EMPTY_XML_VALUE_OBJECT_FACTORY_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (XMLValueObjectFactoryTemplate[])
                    parameters.get(
                        TemplateMappingManager
                            .XML_VALUE_OBJECT_FACTORY_TEMPLATES);
        }
        
        return result;
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
        return retrieveOutputDir(parameters, PackageUtils.getInstance());
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
            packageUtils.retrieveXMLValueObjectFactoryFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
