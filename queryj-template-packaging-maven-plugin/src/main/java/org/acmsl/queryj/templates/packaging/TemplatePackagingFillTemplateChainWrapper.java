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
 * Filename: TemplatePackagingFillTemplateChainWrapper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps a chain to provide Template Packaging's placeholders.
 *
 * Date: 2013/08/25
 * Time: 16:34
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.placeholders.AbstractFillTemplateChainWrapper;
import org.acmsl.queryj.placeholders.ClassNameHandler;
import org.acmsl.queryj.placeholders.CopyrightYearsHandler;
import org.acmsl.queryj.placeholders.CurrentYearHandler;
import org.acmsl.queryj.placeholders.FileNameHandler;
import org.acmsl.queryj.placeholders.PackageNameHandler;
import org.acmsl.queryj.placeholders.ProjectPackageHandler;
import org.acmsl.queryj.placeholders.SerialVersionUIDHandler;
import org.acmsl.queryj.placeholders.TemplateNameHandler;
import org.acmsl.queryj.placeholders.TimestampHandler;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a chain to provide Template Packaging's placeholders.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/25 16:34
 */
@ThreadSafe
public class TemplatePackagingFillTemplateChainWrapper<C extends TemplatePackagingContext>
    extends AbstractFillTemplateChainWrapper<C>
{
    /**
     * Creates a new chain wrapper.
     * @param chain the chain to wrap.
     */
    public TemplatePackagingFillTemplateChainWrapper(@NotNull final FillTemplateChain<C> chain)
    {
        super(chain);
    }

    /**
     * Retrieves the list of generic placeholder handlers.
     * @return such list.
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<FillHandler> getHandlers(@NotNull final C context)
    {
        @NotNull final List result = new ArrayList();

        result.add(new ClassNameHandler<C>(context));
        result.add(new CopyrightYearsHandler());
        result.add(new CurrentYearHandler());
        result.add(new FileNameHandler<C>(context));
        result.add(new PackageNameHandler<C>(context));
        result.add(new SerialVersionUIDHandler<C>(context));
        result.add(new TimestampHandler());
        result.add(new TemplateNameHandler<C>(context));

        return result;
    }
}
