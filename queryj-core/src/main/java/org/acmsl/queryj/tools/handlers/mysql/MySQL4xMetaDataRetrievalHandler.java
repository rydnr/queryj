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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.metadata.vo.Table;

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
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Retrieves the MySQL 4.x metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
     */
    @Override
    protected boolean checkVendor(
        @NotNull final String productName,
        @NotNull final String productVersion,
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
     */
    protected boolean checkVersion(
        @NotNull final String version, @NotNull final VersionUtils versionUtils)
    {
        return versionUtils.matches(version, "4.x");
    }
    
    /**
     * Builds a database metadata manager.
     * @param tables the table names.
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
     */
    @NotNull
    @Override
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        @NotNull List<Table> tables,
        @NotNull String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @Nullable final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema,
        final boolean caseSensitive,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String quote)
        throws  QueryJBuildException
    {
        MetadataManager result;

        result = null;

        /*
        try
        {

            // TODO
            result =
                new MySQL4xMetadataManager(
                    tables,
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
            result.eagerlyFetchMetadata();
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
 */

        return result;
    }
}
