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
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.TableDecorator;
import org.acmsl.queryj.api.PerTableTemplateContext;

/**
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "table" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/03
 */
@SuppressWarnings("unused")
@ThreadSafe
public class TableHandler
    extends AbstractTemplateContextFillHandler<PerTableTemplateContext, TableDecorator>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -3398602147547759354L;

    /**
     * Creates a {@link TableHandler} using given context.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     */
    public TableHandler(@NotNull final PerTableTemplateContext context)
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
     * Retrieves a {@link TableDecorator} using given {@link PerTableTemplateContext context} information.
     * @param context such context.
     * @return such value.
     */
    @Nullable
    @Override
    protected TableDecorator getValue(@NotNull final PerTableTemplateContext context)
    {
        return
            decorate(
                context.getTableName(),
                context.getMetadataManager(),
                context.getDecoratorFactory(),
                context.getCustomSqlProvider());
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return the decorated table.
     */
    @Nullable
    protected TableDecorator decorate(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager, customSqlProvider);
    }
}
