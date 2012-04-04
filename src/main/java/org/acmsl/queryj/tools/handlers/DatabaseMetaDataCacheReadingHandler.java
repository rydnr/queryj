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
 * Filename: DatabaseMetaDataCacheReadingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Checks whether we can use the cached metadata or not.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.Map;

/**
 * Checks whether we can use the cached metadata or not.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DatabaseMetaDataCacheReadingHandler
    extends  AbstractDatabaseMetaDataCacheHandler
{
    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param alreadyDone the flag indicating whether the metadata
     * extraction has already been done.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    @Override
    protected boolean handle(
        @NotNull final Map parameters,
        final boolean alreadyDone,
        @NotNull final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        boolean result = false;

        if (!alreadyDone)
        {
            result = super.handle(parameters, alreadyDone, metaData);
        }

        return result;
    }
    /**
     * Checks whether the database vendor matches this handler.
     * @param productName the product name.
     * @param productVersion the product version.
     * @param majorVersion the major version number.
     * @param minorVersion the minor version number.
     * @return <code>true</code> in case it matches.
     * @precondition product != null
     */
    protected boolean checkVendor(
        @NotNull final String productName,
        @NotNull final String productVersion,
        final int majorVersion,
        final int minorVersion)
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void storeAlreadyDoneFlag(@NotNull Map parameters)
    {
        // We want not to store the flag in this case.
    }

    /**
     * Builds a database metadata manager.
     * @param parameters the command parameters.
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
     * @throws org.apache.tools.ant.BuildException whenever the required
     * parameters are not present or valid.
     * @precondition metaData != null
     */
    @Nullable
    @Override
    protected MetadataManager buildMetadataManager(
        @NotNull final Map parameters,
        @Nullable final String[] tableNames,
        @Nullable final String[] procedureNames,
        final boolean disableTableExtraction,
        final boolean lazyTableExtraction,
        final boolean disableProcedureExtraction,
        final boolean lazyProcedureExtraction,
        @Nullable final DatabaseMetaData metaData,
        @Nullable final String catalog,
        @Nullable final String schema)
        throws  QueryJBuildException
    {
        @Nullable MetadataManager result = null;

        @NotNull File t_OutputDir = retrieveProjectOutputDir(parameters);

        try 
        {
            if (isCacheAvailable(t_OutputDir))
            {
                // TODO: perform checksum validation
                result = retrieveCache(t_OutputDir);
                result.setMetaData(metaData);
                storeAlreadyDoneFlag(parameters);
            }
        }
        catch  (@NotNull final RuntimeException exception)
        {
            throw exception;
        }
        catch  (@NotNull final Exception exception)
        {
            throw new QueryJBuildException("cannot.read.metadata.from.cache", exception);
        }

        return result;
    }
}
