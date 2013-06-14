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
 * Filename: DatabaseMetaDataCacheWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Conditionally caches database metadata to disk.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.exceptions.CannotWriteCacheException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.*;
import java.sql.DatabaseMetaData;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether we can use the cached metadata or not.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DatabaseMetaDataCacheWritingHandler
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
    @SuppressWarnings("unchecked")
    protected boolean handle(
        @NotNull final Map<String, ?> parameters,
        final boolean alreadyDone,
        @NotNull final DatabaseMetaData metaData)
        throws  QueryJBuildException
    {
        if (alreadyDone)
        {
            @Nullable final MetadataManager t_Manager =
                retrieveMetadataManager((Map<String, MetadataManager >) parameters);

            if (t_Manager != null)
            {
                @NotNull final File outputDir = retrieveProjectOutputDir((Map<String, File>) parameters);
                try
                {
                    cache(t_Manager, outputDir);
                }
                catch (@NotNull final IOException cachingFailed)
                {
                    throw new CannotWriteCacheException(outputDir, cachingFailed);
                }
            }
        }

        return false;
    }

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
        return true;
    }

}
