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
 * Filename: GlobalFillTemplateChainWrapper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps a chain to provide Template Packaging's global
 *              placeholders.
 *
 * Date: 2013/09/15
 * Time: 08:58
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.FillTemplateChain;
import org.acmsl.queryj.api.handlers.fillhandlers.FillHandler;
import org.acmsl.queryj.api.placeholders.AbstractFillTemplateChainWrapper;

/*
 * Importing QueryJ Placeholders.
 */
import org.acmsl.queryj.placeholders.CopyrightYearsHandler;
import org.acmsl.queryj.placeholders.CurrentYearHandler;
import org.acmsl.queryj.placeholders.FileNameHandler;
import org.acmsl.queryj.placeholders.PackageNameHandler;
import org.acmsl.queryj.placeholders.SerialVersionUIDHandler;
import org.acmsl.queryj.placeholders.TemplateNameHandler;
import org.acmsl.queryj.placeholders.TimestampHandler;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.placeholders.GlobalClassNameHandler;
import org.acmsl.queryj.templates.packaging.placeholders.GlobalTemplateDefsHandler;
import org.acmsl.queryj.templates.packaging.placeholders.JdbcDriverHandler;
import org.acmsl.queryj.templates.packaging.placeholders.JdbcPasswordHandler;
import org.acmsl.queryj.templates.packaging.placeholders.JdbcUrlHandler;
import org.acmsl.queryj.templates.packaging.placeholders.JdbcUserNameHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a chain to provide Template Packaging's global placeholders.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 08:58
 */
@ThreadSafe
public class GlobalFillTemplateChainWrapper
    extends AbstractFillTemplateChainWrapper<GlobalTemplateContext>
{
    /**
     * Creates a new instance.
     * @param chain the chain.
     */
    public GlobalFillTemplateChainWrapper(@NotNull final FillTemplateChain<GlobalTemplateContext> chain)
    {
        super(chain);
    }

    /**
     * Retrieves the list of generic placeholder handlers.
     * @return such list.
     */
    @NotNull
    @Override
    protected List<?> getHandlers(@NotNull final GlobalTemplateContext context)
    {
        @NotNull final List<FillHandler<?>> result = new ArrayList<>(9);

        result.add(new GlobalClassNameHandler(context));
        result.add(new CopyrightYearsHandler());
        result.add(new CurrentYearHandler());
        result.add(new FileNameHandler<>(context));
        result.add(new PackageNameHandler<>(context));
        result.add(new SerialVersionUIDHandler<>(context));
        result.add(new TimestampHandler());
        result.add(new TemplateNameHandler<>(context));
        result.add(new GlobalTemplateDefsHandler<>(context));
        result.add(new JdbcDriverHandler(context));
        result.add(new JdbcUrlHandler(context));
        result.add(new JdbcUserNameHandler(context));
        result.add(new JdbcPasswordHandler(context));

        return result;
    }
}