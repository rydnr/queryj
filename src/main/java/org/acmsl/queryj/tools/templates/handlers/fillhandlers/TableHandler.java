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
 * Filename: TableHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "table" placeholders.
 *
 * Date: 6/3/12
 * Time: 10:51 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;

/**
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Resolves "table" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@SuppressWarnings("unused")
public class TableHandler
    extends AbstractTemplateContextFillHandler<BasePerTableTemplateContext, TableDecorator>
{
    /**
     * Creates a {@link TableHandler} using given context.
     * @param context the {@link BasePerTableTemplateContext context}.
     */
    public TableHandler(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "table".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "table";
    }

    /**
     * Retrieves a {@link TableDecorator} using given {@link BasePerTableTemplateContext context} information.
     * @param context such context.
     * @return such value.
     */
    @NotNull
    @Override
    protected TableDecorator getValue(@NotNull final BasePerTableTemplateContext context)
    {
        return  decorate(context.getTableName(), context.getMetadataManager(), context.getDecoratorFactory());
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated table.
     */
    @NotNull
    protected TableDecorator decorate(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager);
    }
}
