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
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 *Importing project classes.
*/
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.FillTemplateChain;
import org.acmsl.queryj.tools.templates.handlers.TemplateContextFillAdapterHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Sets up the chain required to provide placeholder replacements for
 * {@link org.acmsl.queryj.tools.templates.BasePerTableTemplate per-table templates}.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
public class BasePerTableFillTemplateChain
    extends FillTemplateChain<BasePerTableTemplateContext>
{
    /**
     * Creates a {@link BasePerTableFillTemplateChain} using given context.
     * @param context the {@link org.acmsl.queryj.tools.templates.BasePerTableTemplateContext context}.
     */
    public BasePerTableFillTemplateChain(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Adds additional per-table handlers.
     * @param chain the chain to be configured.
     * @param context the {@link BasePerTableTemplateContext context}.
     */
    @Override
    protected void addHandlers(@NotNull final Chain chain, @NotNull final BasePerTableTemplateContext context)
    {
        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,CustomResultsHandler,List<Result>>(
                new CustomResultsHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,DAOClassNameHandler,DecoratedString>(
                new DAOClassNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,DAOImplementationClassNameHandler,DecoratedString>(
                new DAOImplementationClassNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,ForeignKeyListHandler,List<ForeignKey>>(
                new ForeignKeyListHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,LobHandlingCheckHandler,Boolean>(
                new LobHandlingCheckHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,NonPrimaryKeyAttributesHandler,List<Attribute>>(
                new NonPrimaryKeyAttributesHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,PrimaryKeyHandler,List<Attribute>>(
                new PrimaryKeyHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,TableHandler,TableDecorator>(
                new TableHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,TableNameHandler,DecoratedString>(
                new TableNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,ValueObjectNameHandler,DecoratedString>(
                new ValueObjectNameHandler(context)));

        chain.add(
            new TemplateContextFillAdapterHandler<BasePerTableTemplateContext,TableAttributeTypeImportsHandler,List<String>>(
                new TableAttributeTypeImportsHandler(context)));
    }
}
