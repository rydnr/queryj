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
 * Filename: QueryJDebuggingChainTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for QueryJDebuggingChain.
 *
 * Date: 2014/08/20
 * Time: 18:14
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing ACM S.L. Java Commons classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.stringtemplate.v4.ST;

/**
 * Tests for {@link org.acmsl.queryj.debugging.QueryJDebuggingChain}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/20 18:14
 */
@RunWith(JUnit4.class)
@ThreadSafe
public class QueryJDebuggingChainTest
{
    /**
     * Checks whether a chain with no-op handlers digests them all sequentially.
     * @throws Exception if some problem occurs.
     */
    @Test
    public void digests_the_chain_sequentially()
        throws Exception
    {
        @NotNull final QueryJCommand command = new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> preHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(preHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(preHandler);

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> fillHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(fillHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(fillHandler);

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> postHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(postHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(postHandler);

        @NotNull final TemplateDebuggingService<TemplateContext> service =
            new TemplateDebuggingService<TemplateContext>()
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public TemplateDebuggingCommand debugTemplate(
                    @NotNull final ST template, @NotNull final TemplateContext context, @NotNull final String output)
                {
                    return TemplateDebuggingCommand.NEXT;
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
            };

        @NotNull final QueryJDebuggingChain<QueryJCommandHandler<QueryJCommand>, TemplateContext> chain =
            new QueryJDebuggingChain<QueryJCommandHandler<QueryJCommand>, TemplateContext>(service)
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void addPreProcessHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(preHandler);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void fillTemplateHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(fillHandler);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void addPostProcessHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(postHandler);
                }
            };

        chain.process(command);

        EasyMock.verify(preHandler);
        EasyMock.verify(fillHandler);
        EasyMock.verify(postHandler);
    }

    /**
     * Injects a forward-only service and tests all handlers are called.
     * @throws Exception if the process fails.
     */
    @Test
    public void a_next_only_service_moves_forward_sequentially()
        throws Exception
    {
        @NotNull final QueryJCommand command = new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> preHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(preHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(preHandler);

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> fillHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(fillHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(fillHandler);

        @SuppressWarnings("unchecked")
        @NotNull final QueryJCommandHandler<QueryJCommand> postHandler =
            EasyMock.createNiceMock(QueryJCommandHandler.class);
        EasyMock.expect(postHandler.handle(command)).andReturn(false).once();
        EasyMock.replay(postHandler);

        @NotNull final TemplateDebuggingService<TemplateContext> service =
            new TemplateDebuggingService<TemplateContext>()
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public TemplateDebuggingCommand debugTemplate(
                    @NotNull final ST template, @NotNull final TemplateContext context, @NotNull final String output)
                {
                    return TemplateDebuggingCommand.NEXT;
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
            };

        @NotNull final QueryJDebuggingChain<QueryJCommandHandler<QueryJCommand>, TemplateContext> chain =
            new QueryJDebuggingChain<QueryJCommandHandler<QueryJCommand>, TemplateContext>(service)
            {
                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void addPreProcessHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(preHandler);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void fillTemplateHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(fillHandler);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected void addPostProcessHandlers(
                    @NotNull final Chain<QueryJCommand, QueryJBuildException, QueryJCommandHandler<QueryJCommand>> chain)
                {
                    chain.add(postHandler);
                }
            };

        chain.process(command);

        EasyMock.verify(preHandler);
        EasyMock.verify(fillHandler);
        EasyMock.verify(postHandler);
    }
}
