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
 * Description: Writes value object templates.
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
import org.acmsl.queryj.tools.templates.valueobject.handlers
    .ValueObjectFactoryTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.valueobject
    .ValueObjectFactoryTemplateGenerator;

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
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class ValueObjectFactoryTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * A cached empty value object template array.
     */
    public static final ValueObjectFactoryTemplate[]
        EMPTY_VALUE_OBJECT_FACTORY_TEMPLATE_ARRAY =
            new ValueObjectFactoryTemplate[0];

    /**
     * Creates a ValueObjectFactoryTemplateWritingHandler.
     */
    public ValueObjectFactoryTemplateWritingHandler() {};

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
            try 
            {
                Map attributes = command.getAttributeMap();

                ValueObjectFactoryTemplateGenerator t_ValueObjectFactoryTemplateGenerator =
                    ValueObjectFactoryTemplateGenerator.getInstance();

                ValueObjectFactoryTemplate[] t_aValueObjectFactoryTemplates =
                    retrieveValueObjectFactoryTemplates(attributes);

                File t_OutputDir = retrieveOutputDir(attributes);

                if  (   (t_OutputDir                           != null) 
                     && (t_aValueObjectFactoryTemplates        != null)
                     && (t_ValueObjectFactoryTemplateGenerator != null))
                {
                    for  (int t_iValueObjectFactoryIndex = 0;
                              t_iValueObjectFactoryIndex < t_aValueObjectFactoryTemplates.length;
                              t_iValueObjectFactoryIndex++)
                    {
                        t_ValueObjectFactoryTemplateGenerator.write(
                            t_aValueObjectFactoryTemplates[t_iValueObjectFactoryIndex], t_OutputDir);
                    }
                }
            }
            catch  (IOException ioException)
            {
                throw new BuildException(ioException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the value object factory template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected ValueObjectFactoryTemplate[] retrieveValueObjectFactoryTemplates(
            Map parameters)
        throws  BuildException
    {
        ValueObjectFactoryTemplate[] result =
            EMPTY_VALUE_OBJECT_FACTORY_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (ValueObjectFactoryTemplate[])
                    parameters.get(
                        TemplateMappingManager
                            .VALUE_OBJECT_FACTORY_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     */
    protected File retrieveOutputDir(Map parameters)
        throws  BuildException
    {
        File result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveValueObjectFolder(
                    (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR),
                    retrieveProjectPackage(parameters));
        }

        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }
}
