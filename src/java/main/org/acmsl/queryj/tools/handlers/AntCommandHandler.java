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
 * Description: Inside a Chain Of Responsibility, these are the chain links.
 *              This means they perform specific actions when receiving the
 *              command.
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
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.CommandHandler;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/**
 * Inside a Chain Of Responsibility, these are the chain links.
 * This means they perform specific actions when receiving the command.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @version $Revision$
 */
public interface AntCommandHandler
    extends  CommandHandler
{
    /**
     * Handles given command.
     * @param command the command.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(AntCommand command)
        throws  BuildException;
}
