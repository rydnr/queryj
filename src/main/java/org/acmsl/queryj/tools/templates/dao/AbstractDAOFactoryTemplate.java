//;-*- mode: java -*-
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
 * Filename: AbstractDAOFactoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains the subtemplates used to generate DAO factories
 *              according to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Contains the subtemplates used to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractDAOFactoryTemplate<C extends BasePerTableTemplateContext>
    extends BasePerTableTemplate<C>
{
    /**
     * Builds an <code>AbstractDAOFactoryTemplate</code> using given information.
     * @param context the {@link BasePerTableTemplateContext context}.
     */
    protected AbstractDAOFactoryTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    @Override
    protected String buildHeader()
    {
        return buildHeader(getTemplateContext());
    }

    /**
     * Builds the header for logging purposes.
     * @param context the {@link TemplateContext context}.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(@NotNull final BasePerTableTemplateContext context)
    {
        return
              "Generating DAOFactory for "
            + context.getTableName() + ".";
    }
}
