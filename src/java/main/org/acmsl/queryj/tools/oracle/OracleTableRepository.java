/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armend?riz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecaba?as
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: QueryJ
 *
 * Description: Contains all tables belonging to Oracle dictionary.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 */
package org.acmsl.queryj.tools.oracle;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.oracle.OracleUserColCommentsTable;
import org.acmsl.queryj.tools.oracle.OracleUserTabColumnsTable;
import org.acmsl.queryj.tools.oracle.OracleUserTablesTable;

/**
 * Contains all tables belonging to Oracle dictionary.
 * @author <a href="http://queryj.sourceforge.net">QueryJ</a>
 * @version $Revision$
 */
public interface OracleTableRepository
{
    /**
     * The USER_TABLES table.
     */
    public static final OracleUserTablesTable USER_TABLES = OracleUserTablesTable.getInstance();

    /**
     * The USER_TAB_COLUMNS table.
     */
    public static final OracleUserTabColumnsTable USER_TAB_COLUMNS = OracleUserTabColumnsTable.getInstance();

    /**
     * The USER_COL_COMMENTS table.
     */
    public static final OracleUserColCommentsTable USER_COL_COMMENTS = OracleUserColCommentsTable.getInstance();

    /**
     * The USER_CONS_COLUMNS table.
     */
    public static final OracleUserConsColumnsTable USER_CONS_COLUMNS = OracleUserConsColumnsTable.getInstance();

    /**
     * The USER_CONSTRAINTS table.
     */
    public static final OracleUserConstraintsTable USER_CONSTRAINTS = OracleUserConstraintsTable.getInstance();
}
