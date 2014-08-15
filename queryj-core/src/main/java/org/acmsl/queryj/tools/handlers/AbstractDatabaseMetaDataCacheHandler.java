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
 * Filename: AbstractDatabaseMetaDataCacheHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Checks whether we can use the cached metadata or not.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Checks whether we can use the cached metadata or not.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractDatabaseMetaDataCacheHandler
    extends  DatabaseMetaDataRetrievalHandler
{
    /**
     * The cache file name.
     */
    public static final String CACHE_FILENAME = "/queryj_metadata_cache.ser";

    /**
     * Retrieves the cache file.
     * @param outputDir the output dir.
     * @return such file.
     */
    @NotNull
    protected File retrieveCacheFile(@NotNull final File outputDir)
    {
        @NotNull final StringBuilder t_sbFilePath = new StringBuilder();

        @NotNull final String t_strCurrentPath = outputDir.getAbsolutePath();

        t_sbFilePath.append(t_strCurrentPath);

        t_sbFilePath.append(CACHE_FILENAME);

        return new File(t_sbFilePath.toString());
    }

    /**
     * Caches given {@link MetadataManager database information} to disk.
     * @param manager the {@link MetadataManager} instance.
     * @param outputDir the output dir.
     * @throws IOException if the manager cannot be cached.
     */
    protected void cache(@NotNull final MetadataManager manager, @NotNull final File outputDir)
        throws IOException
    {
        @NotNull final File t_CacheFile = retrieveCacheFile(outputDir);

        @Nullable ObjectOutputStream t_osCache = null;
        try
        {
            t_osCache = new ObjectOutputStream(new FileOutputStream(t_CacheFile));

            t_osCache.writeObject(manager);
        }
        catch (@NotNull final FileNotFoundException fileNotFound)
        {
            // we don't care much.
        }
        finally
        {
            if (t_osCache != null)
            {
                try
                {
                    t_osCache.close();
                }
                catch (@NotNull final IOException cannotClose)
                {
                    // we don't care much.
                }
            }
        }
    }

    /**
     * Checks whether the cache is already cached in disk.
     * @param outputDir the output dir.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean isCacheAvailable(@NotNull final File outputDir)
    {
        boolean result = false;

        @NotNull final File t_File = retrieveCacheFile(outputDir);

        if (t_File.exists())
        {
            result = true;
        }

        return result;
    }

    /**
     * Retrieves the cache.
     * @param outputDir the output dir.
     * @return {@link MetadataManager} instance from disk.
     * @throws IOException if the cache is unavailable.
     * @throws ClassNotFoundException if a misconfiguration occurs.
     */
    @Nullable
    @SuppressWarnings("unused")
    protected MetadataManager retrieveCache(@NotNull final File outputDir)
        throws IOException,
               ClassNotFoundException
    {
        @Nullable MetadataManager result;

        @Nullable ObjectInputStream t_isCache = null;

        try
        {
            t_isCache = new ObjectInputStream(new FileInputStream(retrieveCacheFile(outputDir)));

            result = (MetadataManager) t_isCache.readObject();
        }
        finally
        {
            if (t_isCache != null)
            {
                try
                {
                    t_isCache.close();
                }
                catch (@NotNull final IOException cannotClose)
                {
                    // Not important.
                }
            }
        }

        return result;
    }
}
