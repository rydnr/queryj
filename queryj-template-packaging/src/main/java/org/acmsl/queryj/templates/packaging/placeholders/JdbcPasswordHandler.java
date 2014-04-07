/*
                        queryj

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
 * Filename: JdbcPasswordHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Resolves C.jdbcPassword in templates.
 *
 * Date: 2014/02/16
 * Time: 12:20
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Placeholders.
 */
import org.acmsl.queryj.placeholders.AbstractDecoratedStringHandler;

/*
 * Importing QueryJ Template Packaging Plugin.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves {@code C.jdbcPassword} in templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/16 12:20
 */
@ThreadSafe
public class JdbcPasswordHandler
    extends AbstractDecoratedStringHandler<GlobalTemplateContext>
{
    private static final long serialVersionUID = -4010517744373892098L;

    /**
     * Creates a handler to resolve "jdbcPassword" placeholders.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     */
    public JdbcPasswordHandler(@NotNull final GlobalTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "jdbcPassword".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "jdbcPassword";
    }

    /**
     * Resolves the actual JDBC password using given {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.QueryJTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final GlobalTemplateContext context)
    {
        return context.getJdbcPassword();
    }
}
