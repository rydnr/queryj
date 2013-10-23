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
 * Filename: DefaultTemplateChainProvider.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Implements {@link TemplateChainProvider} to generate
 * the default templates.
 *
 * Date: 6/4/13
 * Time: 4:40 PM
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.templates.dao.handlers.DAOTemplateHandlerBundle;

/*
 * Importing queryj-core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.tools.TemplateChainProvider;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link TemplateChainProvider} to generate
 * the default templates.
 * @author <a href="mailto:chous@acm-sl.org>Jose San Leandro</a>
 * @since 2013/06/04
 */
@SuppressWarnings("unused")
public class DefaultTemplateChainProvider
    implements TemplateChainProvider<TemplateHandler<QueryJCommand>>
{
    /**
     * Retrieves the custom chain.
     * @return such chain.
     */
    @NotNull
    @Override
    public List<TemplateHandler<QueryJCommand>> getHandlers()
    {
        @NotNull
        final List<TemplateHandler<QueryJCommand>> result = new ArrayList<TemplateHandler<QueryJCommand>>();

        result.add(new DAOTemplateHandlerBundle());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public List<FillHandler> getFillHandlers()
    {
        @NotNull
        final List<FillHandler> result = new ArrayList<FillHandler>();

//        result.add(new CopyrightYearsHandler());

        return result;
    }
}
