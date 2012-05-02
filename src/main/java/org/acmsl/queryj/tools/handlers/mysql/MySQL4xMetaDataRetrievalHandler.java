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
 * Filename: MySQL4xMetaDataRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the MySQL 4.x metadata.
 *
 */
package org.acmsl.queryj.tools.handlers.mysql;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.engines.mysql.MySQL4xMetadataManager;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.version.VersionUtils;

/*
* Importing some jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Retrieves the MySQL 4.x metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MySQL4xMetaDataRetrievalHandler
    extends  DatabaseMetaDataRetrievalHandler
{
    /**
     * Checks whether the database vendor matches this handler.
     * @param productName the product name.
     * @param productVersion the product version.
     * @param majorVersion the major version number.
     * @param minorVersion the minor version number.
     * @return <code>true</code> in case it matches.
     * @precondition product != null
     */
    @Override
    protected boolean checkVendor(
        @NotNull final String productName,
        final String productVersion,
        final int majorVersion,
        final int minorVersion)
    {
        boolean result = productName.contains("MySQL");

        if  (result)
        {
            result = checkVersion(productVersion, VersionUtils.getInstance());
        }
        
        return result;
    }

    /**
     * Checks the engine version.
     * @param version the version.
     * @param versionUtils the <code>VersionUtils</code> instance.
     * @return <code>true</code> if the version matches or is compatible with.
     * @precondition version != null
     * @precondition versionUtils != null
     */
    protected boolean checkVersion(
        final String version, @NotNull final VersionUtils versionUtils)
    {
        return versionUtils.matches(version, "4.x");
    }
    
    /**
     * Builds a database metadata manager.
     * @param tableNames the table names.
     * @param procedureNames the procedure names.
     * @param disableTableExtraction if the table metadata should not
     * be extracted.
     * @param lazyTableExtraction if the table metadata should not
     * be extracted immediately.
     * @param disableProcedureExtraction if the procedure metadata should not
     * be extracted.
     * @param lazyProcedureExtraction if the procedure metadata should not
     * be extracted immediately.
     * @param metaData the database metadata.
     * @param catalog the database catalog.
     * @param schema the database schema.
     * @return the metadata manager instance.
     * @throws QueryJBuildException whenever the required
     * parameters are not present or valid.
     * @precondition metaData != null
     */
    @Override
    @Nullable
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        @NotNull String[] tableNames,
        @NotNull String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @NotNull final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @NotNull final String schema,
        final boolean caseSensitive)
        throws  QueryJBuildException
    {
        MetadataManager result;

        try
        {
            result =
                new MySQL4xMetadataManager(
                    tableNames,
                    procedureNames,
                    disableTableExtraction,
                    lazyTableExtraction,
                    disableProcedureExtraction,
                    lazyProcedureExtraction,
                    metaData,
                    catalog,
                    schema,
                    caseSensitive);

            result.retrieveMetadata();
        }
        catch  (@NotNull final SQLException sqlException)
        {
            throw
                new QueryJBuildException(
                    "Cannot process MySQL metadata", sqlException);
        }

        catch  (@NotNull final QueryJException queryjException)
        {
            throw
                new QueryJBuildException(
                    "Cannot process MySQL metadata", queryjException);
        }

        return result;
    }
}
