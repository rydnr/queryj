//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: OracleTableRepository.java
 *
 * Author: QueryJ
 *
 * Description: Contains all tables belonging to Oracle dictionary.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleUserColCommentsTable;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleUserTabColumnsTable;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleUserTablesTable;

/**
 * Contains all tables belonging to Oracle dictionary.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface OracleTableRepository
{
    /**
     * The USER_TABLES table.
     */
    public static final OracleUserTablesTable USER_TABLES =
        OracleUserTablesTable.getInstance();

    /**
     * The USER_TAB_COLUMNS table.
     */
    public static final OracleUserTabColumnsTable USER_TAB_COLUMNS =
        OracleUserTabColumnsTable.getInstance();

    /**
     * The USER_COL_COMMENTS table.
     */
    public static final OracleUserColCommentsTable USER_COL_COMMENTS =
        OracleUserColCommentsTable.getInstance();

    /**
     * The USER_CONS_COLUMNS table.
     */
    public static final OracleUserConsColumnsTable USER_CONS_COLUMNS =
        OracleUserConsColumnsTable.getInstance();

    /**
     * The USER_CONSTRAINTS table.
     */
    public static final OracleUserConstraintsTable USER_CONSTRAINTS =
        OracleUserConstraintsTable.getInstance();

    /**
     * The USER_TAB_COMMENTS table.
     */
    public static final OracleUserTabCommentsTable USER_TAB_COMMENTS =
        OracleUserTabCommentsTable.getInstance();

    /**
     * The ALL_TAB_COLUMNS table.
     */
    public static final OracleAllTabColumnsTable ALL_TAB_COLUMNS =
        OracleAllTabColumnsTable.getInstance();

}
