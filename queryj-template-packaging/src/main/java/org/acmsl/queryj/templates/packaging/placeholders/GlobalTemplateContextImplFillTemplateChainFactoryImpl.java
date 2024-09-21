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
 * Filename: GlobalTemplatePackagingFillTemplateChainFactoryImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Default implementation for
 *              GlobalTemplatePackagingFillTemplateChainFactory.
 *
 * Date: 2013/09/15
 * Time: 09:02
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.templates.packaging.GlobalFillTemplateChainWrapper;
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Default implementation for {@link GlobalTemplateContextImplFillTemplateChainFactory}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 09:02
 */
@ThreadSafe
@SuppressWarnings("unused")
public class GlobalTemplateContextImplFillTemplateChainFactoryImpl
    implements GlobalTemplateContextImplFillTemplateChainFactory
{
    /**
     * Creates {@link FillTemplateChain} instances for given context.
     * @param context the {@link GlobalTemplateContext} needed.
     * @return the {@link FillTemplateChain}.
     */
    @Nullable
    @Override
    public FillTemplateChain<GlobalTemplateContext> createFillChain(
        @NotNull final GlobalTemplateContext context)
    {
        return new GlobalFillTemplateChainWrapper(new GlobalFillTemplateChain(context));
    }
}
