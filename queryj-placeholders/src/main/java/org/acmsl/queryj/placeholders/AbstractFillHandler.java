/*
                        QueryJ Placeholders

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
 * Filename: AbstractTemplateFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Abstract implementation to simplify fill handlers.
 *
 * Date: 5/13/12
 * Time: 6:10 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.CannotProcessTemplateException;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.InvalidTemplateException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Abstract implementation to simplify fill handlers.
 * @author <a href="jose@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2010/05/13
 * @param <P> the placeholder type.
 */
@SuppressWarnings("unused")
public abstract class AbstractFillHandler<P>
    implements FillHandler<P>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 4490284647192932106L;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        final boolean result;

        try
        {
            @SuppressWarnings("unchecked")
            @NotNull final Map<String, P> t_mLocalMap = new HashMap<String, P>();

            result = handle(t_mLocalMap, getPlaceHolder());

            if (!result)
            {
                for (@NotNull final Entry<String, P> entry : t_mLocalMap.entrySet())
                {
                    new QueryJCommandWrapper<P>(command).setSetting(entry.getKey(), entry.getValue());
                }
            }
        }
        catch (@NotNull final InvalidTemplateException invalidTemplate)
        {
            throw new CannotProcessTemplateException(invalidTemplate);
        }

        return result;
    }

    /**
     * Processes input extracted from given map to fill a placeholder.
     * @param map the command map.
     * @param placeHolder the placeholder.
     * model.
     * @return {@code false} if the handler processes the chain correctly.
     * @throws QueryJBuildException if {@code getValue()} throws it.
     */
    @SuppressWarnings("unchecked")
    public boolean handle(@NotNull final Map<String, P> map, @NotNull final String placeHolder)
        throws QueryJBuildException
    {
        final boolean result = true;

        @Nullable final P value = getValue();

        if (value != null)
        {
            map.put(placeHolder, value);
        }

        return result;
    }
}
