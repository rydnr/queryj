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
import org.acmsl.queryj.api.exceptions.QueryJException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some ACM-SL Commons.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Checks whether we can use the cached metadata or not.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
     */
    @Override
    protected boolean handle(
        @NotNull final Map<String, ?> parameters,
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
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    protected boolean handle(
        @NotNull final Map<String, ?> parameters, @Nullable final MetadataManager metadataManager)
        throws  QueryJBuildException
    {
        boolean result = false;

        // We only need the table names, if they're not extracted already.
        if (metadataManager != null)
        {
            @NotNull final List<Table> t_lTables = metadataManager.getTableDAO().findAllTables();

            if (   (t_lTables.size() == 0))
            {
                try
                {
                    metadataManager.eagerlyFetchMetadata();
                }
                catch (@NotNull final SQLException cannotExtractTableMetadata)
                {
                    result = true;

                    @Nullable final Log t_Log = UniqueLogFactory.getLog(DatabaseMetaDataCacheReadingHandler.class);

                    if (t_Log != null)
                    {
                        t_Log.warn("Cannot extract table metadata", cannotExtractTableMetadata);
                    }
                }
                catch (@NotNull final QueryJException otherError)
                {
                    @Nullable final Log t_Log = UniqueLogFactory.getLog(DatabaseMetaDataCacheReadingHandler.class);

                    if (t_Log != null)
                    {
                        t_Log.warn("Cannot extract table metadata", otherError);
                    }

                    result = true;
                }
            }
        }

        return result;
    }
}
