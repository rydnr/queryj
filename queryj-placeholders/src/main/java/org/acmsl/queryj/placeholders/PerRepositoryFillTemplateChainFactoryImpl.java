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
 * Filename: PerCustomResultFillTemplateChainImpl.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Creates the chain to provide all per-repository
 *              placeholders.
 *
 * Date: 6/8/13
 * Time: 8:13 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing queryj-core classes.
 */
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.placeholders.PerRepositoryFillTemplateChainFactory;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates the chain to provide all per-repository placeholders.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/08
 */
@SuppressWarnings("unused")
public class PerRepositoryFillTemplateChainFactoryImpl
    implements PerRepositoryFillTemplateChainFactory
{
    /**
     * Creates {@link FillTemplateChain} instances for given context.
     * @param context the {@link org.acmsl.queryj.api.PerRepositoryTemplateContext} needed.
     * @return the FillTemplateChain, or <code>null</code> if the context is invalid.
     */
    @Nullable
    @Override
    public FillTemplateChain<PerRepositoryTemplateContext> createFillChain(
        @NotNull final PerRepositoryTemplateContext context)
    {
        return
            new FillTemplateChainWrapper<PerRepositoryTemplateContext>(
                new BasePerRepositoryFillTemplateChain(context));
    }
}
