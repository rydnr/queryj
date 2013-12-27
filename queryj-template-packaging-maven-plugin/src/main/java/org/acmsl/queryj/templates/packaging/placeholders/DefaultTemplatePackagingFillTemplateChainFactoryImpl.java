/*
                        QueryJ's Template Packaging

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
 * Filename: DefaultTemplatePackagingFillTemplateChainFactoryImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Creates the chain to provide all placeholders used in
 *              QueryJ's Template Packaging templates.
 *
 * Date: 2013/08/25
 * Time: 16:14
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Templates Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.PerTemplateDefFillTemplateChainWrapper;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.FillTemplateChain;

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
 * Creates the chain to provide all placeholders used in
 * QueryJ's Template Packaging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/25 16:14
 */
@ThreadSafe
public class DefaultTemplatePackagingFillTemplateChainFactoryImpl
    implements DefaultTemplatePackagingFillTemplateChainFactory
{
    /**
     * Creates {@link org.acmsl.queryj.api.FillTemplateChain} instances for given context.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext} needed.
     * @return the FillTemplateChain, or <code>null</code> if the context is invalid.
     */
    @Nullable
    @Override
    public FillTemplateChain<DefaultTemplatePackagingContext> createFillChain(
        @NotNull final DefaultTemplatePackagingContext context)
    {
        return new PerTemplateDefFillTemplateChainWrapper(
            new PerTemplateDefFillTemplateChain(context));
    }
}
