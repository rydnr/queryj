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
 * Filename: TableTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate table repositories according to database
 *              metadata.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate Table repositories according to database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TableTemplateGenerator
    extends  AbstractTemplateGenerator<TableTemplate, BasePerTableTemplateContext>
    implements  BasePerTableTemplateGenerator<TableTemplate, BasePerTableTemplateContext>,
                Singleton
{
    /**
     * Creates a new {@link TableRepositoryTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads to use.
     */
    public TableTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final BasePerTableTemplateContext context)
    {
        return retrieveTemplateFileName(context, TableTemplateUtils.getInstance());
    }

    /**
     * Retrieves the template's file name.
     * @param context the {@link BasePerTableTemplateContext context}.
     * @param tableTemplateUtils the {@link TableTemplateUtils} instance.
     * @return such file name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final BasePerTableTemplateContext context, @NotNull final TableTemplateUtils tableTemplateUtils)
    {
        return
            tableTemplateUtils.retrieveTableClassName(context.getTableName()) + ".java";
    }
}
