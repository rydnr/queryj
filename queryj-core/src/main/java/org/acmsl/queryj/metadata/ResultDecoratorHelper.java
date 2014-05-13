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
 * Filename: ResultDecoratorHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/05/13
 * Time: 08:06
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.customsql.Property;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/13 08:06
 */
@ThreadSafe
public class ResultDecoratorHelper
{
    /**
     * Default constructor.
     */
    public ResultDecoratorHelper() {}

    /**
     * Singleton to avoid double-check locking.
     */
    private static final class ResultDecoratorHelperSingletonContainer
    {
        /**
         * The singleton instance.
         */
        public static final ResultDecoratorHelper SINGLETON = new ResultDecoratorHelper();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    public static ResultDecoratorHelper getInstance()
    {
        return ResultDecoratorHelperSingletonContainer.SINGLETON;
    }

    /**
     * Checks whether given {@link Property} list contains nullable items or not.
     * @param properties the properties.
     * @return {@code true} in such case.
     */
    public boolean containNullableProperties(
        @NotNull final List<Property<DecoratedString>> properties)
    {
        boolean result = false;

        for (@Nullable final Property<DecoratedString> property : properties)
        {
            if (   (property != null)
                && (property.isNullable()))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether given {@link Property} list contains not-null items or not.
     * @param properties the properties.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     * @param <V> the type.
     */
    public <V> boolean containNotNullProperties(
        @NotNull final List<Property<V>> properties,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        for (@Nullable final Property<V> property : properties)
        {
            if (property != null)
            {
                @NotNull final String type = "" + property.getType();

                if (   (!property.isNullable())
                    && (   (metadataTypeManager.isPrimitiveWrapper(type))
                        || (!metadataTypeManager.isPrimitive(type))))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
