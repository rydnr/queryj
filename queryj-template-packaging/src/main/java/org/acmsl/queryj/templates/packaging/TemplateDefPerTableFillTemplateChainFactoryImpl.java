/*
                        QueryJ Template Packaging

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
 * Filename: TemplateDefPerTableFillTemplateChainFactoryImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Creates the chain to provide all per-table placeholders, with
 *              access to the TemplateDef information.
 *
 * Date: 2014/05/31
 * Time: 08:35
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.PerTableTemplateContext;

/*
 * Importing QueryJ Placeholders classes.
 */
import org.acmsl.queryj.placeholders.FillTemplateChainWrapper;
import org.acmsl.queryj.placeholders.PerTableFillTemplateChainFactoryImpl;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Creates the chain to provide all per-table placeholders, with access to the
 * {@link TemplateDef} information.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/31 08:35
 */
@ThreadSafe
public class TemplateDefPerTableFillTemplateChainFactoryImpl
    extends PerTableFillTemplateChainFactoryImpl<TemplateDefPerTableTemplateContext>
{
    /**
     * Creates {@link org.acmsl.queryj.api.FillTemplateChain} instances for given context.
     * @param context the {@link PerTableTemplateContext} needed.
     * @return the FillTemplateChain, or {@code null} if the context is invalid.
     */
    @Nullable
    @Override
    public FillTemplateChain<TemplateDefPerTableTemplateContext> createFillChain(
        @NotNull final TemplateDefPerTableTemplateContext context)
    {
        return
            new FillTemplateChainWrapper<>(
                new TemplateDefPerTableFillTemplateChain(context));
    }
}
