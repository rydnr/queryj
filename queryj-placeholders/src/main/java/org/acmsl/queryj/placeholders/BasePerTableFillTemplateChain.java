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
 * Filename: BasePerTableFillTemplateChain.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Sets up the chain required to provide placeholder replacements
 *              for per-table templates.
 *
 * Date: 6/3/12
 * Time: 12:38 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.AbstractFillTemplateChain;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.FillAdapterHandler;
import org.acmsl.queryj.api.handlers.TemplateContextFillAdapterHandler;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratedString;
import org.acmsl.queryj.metadata.TableDecorator;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.api.PerTableTemplate per-table templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/06/03
 */
@ThreadSafe
public class BasePerTableFillTemplateChain
    extends AbstractFillTemplateChain<PerTableTemplateContext>
{
    /**
     * Creates a {@link BasePerTableFillTemplateChain} using given context.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     */
    public BasePerTableFillTemplateChain(@NotNull final PerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public QueryJCommand providePlaceholders(final boolean relevantOnly)
        throws QueryJBuildException
    {
        return new FillTemplateChainWrapper<PerTableTemplateContext>(this).providePlaceholders(relevantOnly);
    }

    /**
     * Retrieves the additional per-table handlers.
     * @param context the {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @return such handlers.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<FillHandler<?>> getHandlers(@NotNull final PerTableTemplateContext context)
    {
        @NotNull final List result = new ArrayList<FillHandler>(12);

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, CustomResultsHandler, List<Result>>(
                        new CustomResultsHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, DAOClassNameHandler, DecoratedString>(
                        new DAOClassNameHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, DAOImplementationClassNameHandler, DecoratedString>(
                        new DAOImplementationClassNameHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, DAOFactoryClassNameHandler, DecoratedString>(
                        new DAOFactoryClassNameHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, ForeignKeyListHandler,List<ForeignKey<String>>>(
                        new ForeignKeyListHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, LobHandlingTableCheckHandler,Boolean>(
                        new LobHandlingTableCheckHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, NonPrimaryKeyAttributesHandler,List<Attribute<String>>>(
                        new NonPrimaryKeyAttributesHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, PrimaryKeyHandler,List<Attribute<String>>>(
                        new PrimaryKeyHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, TableHandler,TableDecorator>(
                        new TableHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, TableNameHandler, DecoratedString>(
                        new TableNameHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, ValueObjectNameHandler, DecoratedString>(
                        new ValueObjectNameHandler(context)));

        result.add(
            (FillAdapterHandler)
                new TemplateContextFillAdapterHandler
                    <PerTableTemplateContext, TableAttributeTypeImportsHandler,List<String>>(
                        new TableAttributeTypeImportsHandler(context)));

        return result;
    }
}
