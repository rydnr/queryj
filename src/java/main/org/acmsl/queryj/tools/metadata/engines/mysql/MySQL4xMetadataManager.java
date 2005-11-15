
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information metadata stored in a MySQL database.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.mysql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.tools.metadata.engines.mysql.MySQL4xMetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.QueryJException;

/*
 * Importing JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Manages the information metadata stored in an Oracle database.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MySQL4xMetadataManager
    extends  JdbcMetadataManager
{
    /**
     * Creates an empty <code>MySQL4xMetadataManager</code>.
     */
    protected MySQL4xMetadataManager()
    {
        super();
    }

    /**
     * Creates a <code>MySQL4xMetadataManager</code>, using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param disableTableExtraction <code>true</code> to disable table
     * extraction.
     * @param lazyTableExtraction if the table metadata should not be
     * retrieved from the database until the tables to extract are
     * specified.
     * @param disableProcedureExtraction <code>true</code> to disable
     * procedure extraction.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be retrieved from the database until the procedures to extract are
     * specified.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public MySQL4xMetadataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
        throws  SQLException,
                QueryJException
    {
        super(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema);
    }

    /**
     * Creates a <code>MySQL4xMetadataManager</code> using given information.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public MySQL4xMetadataManager(
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
        throws  SQLException,
                QueryJException
    {
        super(
            tableNames,
            procedureNames,
            metaData,
            catalog,
            schema);
    }

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    public MetadataTypeManager getMetadataTypeManager()
    {
        return MySQL4xMetadataTypeManager.getInstance();
    }
}
