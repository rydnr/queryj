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
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation to simplify fill handlers.
 * @author <a href="jose@acm-sl.org">Jose San Leandro</a>
 * @since 2010/05/13
 */
@SuppressWarnings("unused")
public abstract class AbstractFillHandler<P>
    implements FillHandler<P>
{
    /**
     * {@inheritDoc}
     */
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        boolean result;

        try
        {
            result = handle(command.getAttributeMap());
        }
        catch (@NotNull final InvalidTemplateException invalidTemplate)
        {
            throw new QueryJBuildException("Error processing template", invalidTemplate);
        }

        return result;
    }

    /**
     * Does some processing based on given command map.
     * @param commandMap the command map.
     * @return <code>false</code> if the chain can continue.
     * @throws InvalidTemplateException if some fatal error occurs.
     */
    @SuppressWarnings("unchecked")
    protected boolean handle(@NotNull final Map commandMap)
        throws InvalidTemplateException
    {
        boolean result;

        @NotNull final Map t_mLocalMap = new HashMap();

        result = handle(t_mLocalMap, getPlaceHolder());

        if (!result)
        {
            commandMap.putAll(t_mLocalMap);
        }

        return result;
    }

    /**
     * Processes input extracted from given map to fill a placeholder.
     * @param map the command map.
     * @param placeHolder the placeholder.
     */
    @SuppressWarnings("unchecked")
    public boolean handle(@NotNull final Map map, @NotNull final String placeHolder)
        throws InvalidTemplateException
    {
        boolean result = true;

        P value = getValue();

        if (value != null)
        {
            map.put(placeHolder, value);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public boolean handle(@NotNull final Command command)
    {
        boolean result = true;

        if (command instanceof QueryJCommand)
        {
            try
            {
                result = handle((QueryJCommand) command);
            }
            catch (@NotNull final QueryJBuildException invalidTemplate)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractFillHandler.class);

                if (t_Log != null)
                {
                    t_Log.error("Error processing template", invalidTemplate);
                }
            }
        }

        return result;
    }
}
