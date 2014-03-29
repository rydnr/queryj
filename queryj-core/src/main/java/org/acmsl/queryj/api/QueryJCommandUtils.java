/*
                        queryj

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
 * Filename: QueryJCommandUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/03/29
 * Time: 08:00
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.exceptions.MetadataManagerNotAvailableException;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/29 08:00
 */
@ThreadSafe
public class QueryJCommandUtils
    implements Singleton
{
    /**
     * Singleton implemented to avoid double-check locking.
     */
    protected static final class QueryJCommandUtilsSingletonContainer
    {
        /**
         * The singleton.
         */
        public static final QueryJCommandUtils SINGLETON = new QueryJCommandUtils();
    }

    /**
     * Default constructor. There's no real requirement to force the user
     * to use only the getInstance() method.
     */
    protected QueryJCommandUtils() {};

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static QueryJCommandUtils getInstance()
    {
        return QueryJCommandUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     */
    @NotNull
    public MetadataManager retrieveMetadataManager(@NotNull final QueryJCommand parameters)
    {
        @Nullable final MetadataManager result = retrieveMetadataManagerIfExists(parameters);

        if (result == null)
        {
            throw new MetadataManagerNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     */
    @Nullable
    public MetadataManager retrieveMetadataManagerIfExists(@NotNull final QueryJCommand parameters)
    {
        return
            new QueryJCommandWrapper<MetadataManager>(parameters)
                .getSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);
    }
}
