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
 * Description: Bundles a pair of template build and writing handlers.
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
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/**
 * Bundles a pair of template build and writing handlers.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @version $Revision$
 */
public class TemplateHandlerBundle
    extends  AntCommandHandler
{
    /**
     * The template build handler.
     */
    private TemplateBuildHandler m__TemplateBuildHandler;

    /**
     * The template writing handler.
     */
    private TemplateWritingHandler m__TemplateWritingHandler;

    /**
     * Builds a bundle with given handlers.
     * @param buildHandler the template build handler.
     * @param writingHandler the writing handler.
     * @precondition buildHandler != null
     * @precondition writingHandler != null
     */
    public TemplateHandlerBundle(
        final TemplateBuildHandler buildHandler,
        final TemplateWritingHandler writingHandler)
    {
        immutableSetTemplateBuildHandler(buildHandler);
        immutableSetTemplateWritingHandler(writingHandler);
    }

    /**
     * Specifies the template build handler.
     * @param buildHandler the template build handler.
     */
    private void immutableSetTemplateBuildHandler(
        final TemplateBuildHandler buildHandler)
    {
        m__TemplateBuildHandler = buildHandler;
    }

    /**
     * Specifies the template build handler.
     * @param buildHandler the template build handler.
     */
    protected void setTemplateBuildHandler(
        final TemplateBuildHandler buildHandler)
    {
        immutableSetTemplateBuildHandler(buildHandler);
    }

    /**
     * Retrieves the template build handler.
     * @return such handler.
     */
    public TemplateBuildHandler getTemplateBuildHandler()
    {
        return m__TemplateBuildHandler;
    }

    /**
     * Specifies the template writing handler.
     * @param writingHandler the template writing handler.
     */
    private void immutableSetTemplateWritingHandler(
        final TemplateWritingHandler writingHandler)
    {
        m__TemplateWritingHandler = writingHandler;
    }

    /**
     * Specifies the template writing handler.
     * @param writingHandler the template writing handler.
     */
    protected void setTemplateWritingHandler(
        final TemplateWritingHandler writingHandler)
    {
        immutableSetTemplateWritingHandler(writingHandler);
    }

    /**
     * Retrieves the template writing handler.
     * @return such handler.
     */
    public TemplateWritingHandler getTemplateWritingHandler()
    {
        return m__TemplateWritingHandler;
    }

    /**
     * Handles given command.
     * @param command the command.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return
            handle(
                command,
                getTemplateBuildHandler(),
                getTemplateWritingHandler());
    }

    /**
     * Handles given command.
     * @param command the command.
     * @param buildHandler the template build handler.
     * @param writingHandler the template writing handler.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws BuildException if the build process cannot be performed.
     * @precondition buildHandler != null
     * @precondition writingHandler != null
     */
    protected boolean handle(
        final AntCommand command,
        final TemplateBuildHandler buildHandler,
        final TemplateWritingHandler writingHandler)
      throws  BuildException
    {
        boolean result = false;

        result = buildHandler.handle(command);

        if  (!result)
        {
            result = writingHandler.handle(command);
        }

        return result;
    }
}
