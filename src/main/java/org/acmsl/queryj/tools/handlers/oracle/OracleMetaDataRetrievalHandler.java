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
 * Filename: OracleMetaDataRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the Oracle metadata.
 *
 */
package org.acmsl.queryj.tools.handlers.oracle;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.version.VersionUtils;

/*
 * Importing some Ant classes.
 */
import org.acmsl.queryj.tools.metadata.engines.oracle.OracleMetadataManager;
import org.acmsl.queryj.tools.metadata.vo.Table;
import org.apache.tools.ant.BuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Map;

/**
 * Retrieves the Oracle metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleMetaDataRetrievalHandler
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
    protected boolean checkVendor(
        @NotNull final String productName,
        @NotNull final String productVersion,
        final int majorVersion,
        final int minorVersion)
    {
        return (productName.contains("Oracle"));

//        if  (result)
//        {
//            result = checkVersion(productVersion, VersionUtils.getInstance());
//        }
    }

    /**
     * Checks the engine version.
     * @param version the version.
     * @param versionUtils the {@link VersionUtils} instance.
     * @return <code>true</code> if the version matches or is compatible with.
     */
    @SuppressWarnings("unused")
    protected boolean checkVersion(
        @NotNull final String version, @NotNull final VersionUtils versionUtils)
    {
        return versionUtils.matches(version, "8.x");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        @NotNull final List<Table> tables,
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
        throws  BuildException
    {
        @Nullable MetadataManager result;

        try 
        {
            result =
                new OracleMetadataManager(
                    metaData,
                    new MetadataExtractionLogger(),
                    catalog,
                    schema,
                    null, // TODO: explicit tables
                    tables,
                    disableTableExtraction,
                    lazyTableExtraction,
                    caseSensitive,
                    engineName,
                    engineVersion,
                    quote);

            result.eagerlyFetchMetadata();

            storeTables(result.getTableDAO().findAllTables(), parameters);
        }
        catch  (@NotNull final RuntimeException exception)
        {
            throw exception;
        }
        catch  (@NotNull final Exception exception)
        {
            throw new BuildException(exception);
        }

        return result;
    }
}
