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
 * Filename: DAOFactoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.api.AbstractBasePerTableTemplate;
import org.acmsl.queryj.api.PerTableTemplateContext;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DAOFactoryTemplate
     extends AbstractBasePerTableTemplate<PerTableTemplateContext>
{
    private static final long serialVersionUID = -836140578744901008L;

    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     */
    public DAOFactoryTemplate(@NotNull final PerTableTemplateContext context)
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
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(@NotNull final PerTableTemplateContext context)
    {
        return
            "Generating DAOFactory for "
            + context.getTableName() + ".";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "DAOFactory".
     * @return such name.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "DAOFactory";
    }
}
