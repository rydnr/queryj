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
 * Filename: SqlDecoratorHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description:  Provides some logic useful for SqlDecorators.
 *
 * Date: 2014/05/17
 * Time: 10:30
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Parameter;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides some logic useful for {@link SqlDecorator}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/17 10:30
 */
@ThreadSafe
public class SqlDecoratorHelper
{
    /**
     * Singleton implemented to avoid double-check locking.
     */
    protected static final class SqlDecoratorHelperSingletonContainer
    {
        /**
         * The singleton instance.
         */
        public static final SqlDecoratorHelper SINGLETON = new SqlDecoratorHelper();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static SqlDecoratorHelper getInstance()
    {
        return SqlDecoratorHelperSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the parameter types.
     * @param parameters the parameters.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return the parameter types.
     */
    @NotNull
    public List<DecoratedString> getParameterTypes(
        final List<Parameter<DecoratedString, ?>> parameters,
        final MetadataTypeManager metadataTypeManager)
    {
        @NotNull final List<DecoratedString> result = new ArrayList<>(parameters.size());

        for (@Nullable final Parameter<DecoratedString, ?> parameter: parameters)
        {
            if (parameter != null)
            {
                @Nullable final String importType =
                    metadataTypeManager.getImport(
                        metadataTypeManager.getJavaType(parameter.getType().getValue()));

                if (importType != null)
                {
                    @NotNull final DecoratedString value = new DecoratedString(importType);

                    if (!result.contains(value))
                    {
                        result.add(value);
                    }
                }
            }
        }

        Collections.sort(result);

        return result;
    }

}
