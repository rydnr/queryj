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
 * Filename: AbstractTemplateDefFillTemplateChainTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic for TemplateDefPerXXXFillTemplateChain tests.
 *
 * Date: 2014/06/06
 * Time: 06:59
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.AbstractFillTemplateChain;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.handlers.TemplateContextFillAdapterHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;

/*
 * Importing JetBrains annotations.
 */
import java.util.List;

/**
 * Common logic for TemplateDefPerXXXFillTemplateChain tests.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/06 06:59
 */
@ThreadSafe
public abstract class AbstractTemplateDefFillTemplateChainTest<
    C extends TemplateContext, CH extends AbstractFillTemplateChain<C>>
{
    /**
     * Checks getHandlers() include the handler to resolve
     * "templateDef" placeholder.
     */
    public void testGetHandlers(@NotNull final CH chain)
    {
        @NotNull final List<?> handlers = chain.getHandlers();

        boolean found = false;

        for (@Nullable final Object handler : handlers)
        {
            if (handler instanceof TemplateContextFillAdapterHandler)
            {
                @NotNull final TemplateContextFillAdapterHandler fillAdapterHandler =
                    (TemplateContextFillAdapterHandler) handler;

                if (fillAdapterHandler.getPlaceHolder().equals("templateDef"))
                {
                    found = true;
                    break;
                }
            }
        }

        Assert.assertTrue(found);
    }
}
