/*
                        QueryJ Core

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
 * Filename: MySQL4xMetadataManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the information metadata stored in a MySQL database.
 *
 */
package org.acmsl.queryj.metadata.engines.mysql;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.api.exceptions.QueryJException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Manages the information metadata stored in an Oracle database.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
@ThreadSafe
public class MySQL4xMetadataManager
//    extends JdbcMetadataManager
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -9087872657546547720L;

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
     * @param caseSensitive whether the database engine is case sensitive or not.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @throws SQLException if the extraction fails.
     * @throws QueryJException if any other error occurs.
     */
    @SuppressWarnings("unused")
    public MySQL4xMetadataManager(
        @NotNull final String[] tableNames,
        @NotNull final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitive,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote)
        throws  SQLException,
                QueryJException
    {
        /*
        super(
            tableNames,
            procedureNames,
            disableTableExtraction,
            lazyTableExtraction,
            disableProcedureExtraction,
            lazyProcedureExtraction,
            metaData,
            catalog,
            schema,
            caseSensitive,
            engineName,
            engineVersion,
            quote);
            */
    }

    /**
     * Retrieves the type manager.
     * @return such instance.
     */
    @NotNull
    public MetadataTypeManager getMetadataTypeManager()
    {
        return MySQL4xMetadataTypeManager.getInstance();
    }

    /**
     * Retrieves the name identifying the manager instance.
     * @return such name.
     */
    @NotNull
    public String getName()
    {
        return "mysql4";
    }

    /**
     * Checks whether the engine requires specific CLOB handling.
     * @return <code>true</code> in such case.
     */
    public boolean requiresCustomClobHandling()
    {
        return false;
    }
}
