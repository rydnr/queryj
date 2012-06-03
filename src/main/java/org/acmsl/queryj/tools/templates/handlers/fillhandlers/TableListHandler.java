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
 * Filename: TablesHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "tables" placeholders in templates.
 *
 * Date: 5/24/12
 * Time: 4:18 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Is able to resolve "tables" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
public class TableListHandler
    extends AbstractTemplateContextFillHandler<BasePerRepositoryTemplateContext, List<TableDecorator>>
{
    /**
     * Creates a {@link TableListHandler} to resolve "tables" placeholders using given
     * {@link BasePerRepositoryTemplateContext context}.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     */
    @SuppressWarnings("unused")
    public TableListHandler(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "tables".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "tables";
    }

    /**
     * Retrieves the list of decorated tables belonging to the repository.
     * @param context the {@link BasePerRepositoryTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected List<TableDecorator> getValue(@NotNull final BasePerRepositoryTemplateContext context)
    {
        return decorateTables(context.getTableNames(), context.getMetadataManager(), context.getDecoratorFactory());
    }

    /**
     * Decorates the tables.
     * @param tables the tables.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated tables.
     */
    @NotNull
    protected List<TableDecorator> decorateTables(
        @NotNull final List<String> tables,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull List<TableDecorator> result = new ArrayList<TableDecorator>(tables.size());

        for (String t_strTable: tables)
        {
            result.add(decorate(t_strTable, metadataManager,decoratorFactory));
        }

        return result;
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
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
