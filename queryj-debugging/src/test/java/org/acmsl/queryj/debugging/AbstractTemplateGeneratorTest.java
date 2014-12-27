/*
                        QueryJ Debugging

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
 * Filename: AbstractTemplateGeneratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractTemplateGenerator.
 *
 * Date: 2014/06/25
 * Time: 12:51
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.stringtemplate.v4.ST;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link AbstractTemplateGenerator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/25 12:51
 */
@RunWith(JUnit4.class)
public class AbstractTemplateGeneratorTest
{
    /**
     * Checks whether resolveTemplateDebuggingService() resolves the concrete
     * instance at runtime.
     */
    @Test
    public void resolveTemplateDebuggingService_resolves_an_instance_at_runtime()
    {
        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);

        @NotNull final AbstractTemplateGenerator instance =
            new AbstractTemplateGenerator(false, 1)
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public DecoratorFactory getDecoratorFactory()
                {
                    return decoratorFactory;
                }
            };

        @Nullable final TemplateDebuggingService<?> service = DebuggingUtils.getInstance().resolveTemplateDebuggingService();

//        Assert.assertNotNull(service);
    }

    /**
     * A stub class to get resolved via ServiceLoader.
     * @param <C> the context.
     */
    @SuppressWarnings("unused")
    public static class DoNothingDebuggingService<C extends TemplateContext>
        implements TemplateDebuggingService<C>
    {
        /**
         * Flag to check whether it has been called or not.
         */
        public boolean called;

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public TemplateDebuggingCommand debugTemplate(
            @NotNull final ST template, @NotNull final C context, @NotNull final String output)
        {
            this.called = true;
            return TemplateDebuggingCommand.RELOAD;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public TemplateDebuggingCommand debug(
            @NotNull final QueryJCommandHandler<QueryJCommand> handler,
            @NotNull final QueryJCommand command)
        {
            return TemplateDebuggingCommand.NEXT;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return
                  "{ \"called\": " + this.called
                + ", \"class\": \"" + DoNothingDebuggingService.class.getSimpleName() + '"'
                + ", \"package\": \"org.acmsl.queryj.debugging\" }";

        }
    }
}
