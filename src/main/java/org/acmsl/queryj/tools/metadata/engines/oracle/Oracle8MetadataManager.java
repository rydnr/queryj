//;-*- mode: java -*-
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
 * Filename: Oracle8MetadataManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information metadata stored in an Oracle8 database.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleMetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataExtractionListener;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Manages the information metadata stored in an Oracle8 database.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class Oracle8MetadataManager
    extends  OracleMetadataManager
{
    /**
     * Creates an empty <code>Oracle8MetadataManager</code>.
     * @param metadataExtractionListener the <code>MetadataExtractionListener</code> instance.
     */
    protected Oracle8MetadataManager(
        final MetadataExtractionListener metadataExtractionListener)
    {
        super(metadataExtractionListener);
    }

    /**
     * Creates an <code>Oracle8MetadataManager</code>. using given information.
     * @param metadataExtractionListener the <code>MetadataExtractionListener</code> instance.
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
    public Oracle8MetadataManager(
        final MetadataExtractionListener metadataExtractionListener,
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
            metadataExtractionListener,
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
     * Creates an <code>Oracle8MetadataManager</code> using given information.
     * @param metadataExtractionListener the <code>MetadataExtractionListener</code> instance.
     * @param tableNames explicitly specified table names.
     * @param procedureNames explicitly specified procedure names.
     * @param metaData the database meta data.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @throws SQLException if the database operation fails.
     * @throws QueryJException if an error, which is identified by QueryJ,
     * occurs.
     */
    public Oracle8MetadataManager(
        final MetadataExtractionListener metadataExtractionListener,
        final String[] tableNames,
        final String[] procedureNames,
        final DatabaseMetaData metaData,
        final String catalog,
        final String schema)
        throws  SQLException,
                QueryJException
    {
        super(
            metadataExtractionListener,
            tableNames,
            procedureNames,
            metaData,
            catalog,
            schema);
    }

    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    public String getName()
    {
        return "oracle8";
    }

    /**
     * Checks whether the engine requires specific BLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomBlobHandling()
    {
        return true;
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomClobHandling()
    {
        return true;
    }
}
