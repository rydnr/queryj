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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains the elements required to create any template
 *              of any type, associated to each table.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Contains the elements required to create any template of any type,
 * associated to each table.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractBasePerTableTemplate<C extends BasePerTableTemplateContext>
    extends  AbstractTemplate<C>
{
    /**
     * Builds an <code>AbstractBasePerTableTemplate</code> using given
     * information.
     * @param context the {@link BasePerTableTemplateContext} instance.
     */
    protected AbstractBasePerTableTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    @NotNull
    protected String buildHeader()
    {
        return buildHeader(getTemplateName(), getTemplateContext());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        @NotNull final String templateName, @NotNull final C context)
    {
        return buildHeader(templateName, context.getTableName());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param tableName the table name.
     * @return such header.
     */
    @NotNull
    protected String buildHeader(
        @NotNull final String templateName, @NotNull final String tableName)
    {
        return "Generating " + templateName + " for " + tableName + ".";
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public abstract String getTemplateName();
}