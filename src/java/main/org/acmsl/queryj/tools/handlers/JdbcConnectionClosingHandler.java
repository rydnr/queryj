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
 * Description: Closes a JDBC connection.
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
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Closes a JDBC connection.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class JdbcConnectionClosingHandler
    implements  AntCommandHandler
{
    /**
     * Creates a JdbcConnectionClosingHandler.
     */
    public JdbcConnectionClosingHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            try 
            {
                result = handle((AntCommand) command);
            }
            catch  (BuildException buildException)
            {
                LogFactory.getLog(getClass()).error(
                    "unhandled.exception",
                    buildException);
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            closeConnection(command.getAttributeMap());

            removeConnection(command.getAttributeMap());
        }
        
        return result;
    }

    /**
     * Closes the JDBC connection stored in the attribute map.
     * @param parameters the parameter map.
     * @throws BuildException if the connection cannot be closed.
     */
    protected void closeConnection(Map parameters)
        throws  BuildException
    {
        if  (parameters != null) 
        {
            closeConnection(
                (Connection)
                    parameters.get(
                        JdbcConnectionOpeningHandler.JDBC_CONNECTION));
        }
    }

    /**
     * Closes the connection.
     * @param connection the JDBC connection.
     * @throws org.apache.tools.ant.BuildException whenever the required
     * connection is not present or valid.
     */
    protected void closeConnection(Connection connection)
        throws  BuildException
    {
        if  (connection != null)
        {
            try
            {
                connection.close();
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
        }
    }

    /**
     * Removes the JDBC connection in given attribute map.
     * @param parameters the parameter map.
     * @throws BuildException if the connection cannot be removed for any reason.
     */
    protected void removeConnection(
            Map parameters)
        throws  BuildException
    {
        if  (parameters != null)
        {
            parameters.remove(JdbcConnectionOpeningHandler.JDBC_CONNECTION);
        }
    }

    /**
     * Concrete version object updated everytime it's checked-in in a
     * CVS repository.
     */
    public static final Version VERSION =
        VersionFactory.createVersion("$Revision$");

    /**
     * Retrieves the current version of this object.
     * @return the version object with such information.
     */
    public Version getVersion()
    {
        return VERSION;
    }

    /**
     * Retrieves the current version of this class. It's defined because
     * this is a utility class that cannot be instantiated.
     * @return the object with class version information.
     */
    public static Version getClassVersion()
    {
        return VERSION;
    }
}
