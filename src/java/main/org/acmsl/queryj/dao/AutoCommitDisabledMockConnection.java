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
    Lesser General Public License for more details.

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
 * Description: Mock connections with auto commit disabled.
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
package org.acmsl.queryj.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.dao.MockConnection;
import org.acmsl.queryj.dao.MockDataSource;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A Mock connection with auto commit disabled.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendariz</a>
 * @version $Revision$
 */
public class AutoCommitDisabledMockConnection
    extends MockConnection
{
    /**
     * Constructs a MockConnection using both objects.
     * @param connection the actual connection to wrap.
     * @param mockDataSource the data source to be notified of events.
     */
    public AutoCommitDisabledMockConnection(
            Connection connection,
            MockDataSource mockDataSource)
        throws  SQLException
    {
        super(connection, mockDataSource);
        connection.setAutoCommit(false);
    }
}

