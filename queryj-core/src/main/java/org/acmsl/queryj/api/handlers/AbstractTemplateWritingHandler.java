/*
                        QueryJ Core

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
 * Filename: BasePerTableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-table templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.Template;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.TemplateGenerator;
import org.acmsl.queryj.api.TemplateGeneratorThread;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Writes <i>per-table</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the template type.
 * @param <C> the template context type.
 * @param <TG> the template generator type.
 */
@ThreadSafe
public abstract class AbstractTemplateWritingHandler
    <T extends Template<C>,
     C extends TemplateContext,
     TG extends TemplateGenerator<T, C>>
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * The database product name entry.
     */
    public static final String PRODUCT_NAME = "L.:ProductName::.@";

    /**
     * A literal.
     */
    public static final String BROKEN_BARRIER_LITERAL = "Broken barrier";

    /**
     * Creates a <code>AbstractTemplateWritingHandler</code> instance.
     */
    public AbstractTemplateWritingHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        writeTemplates(
            parameters,
            retrieveThreadCount(parameters),
            retrieveProjectOutputDir(parameters));

        return false;
    }

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param threadCount the thread count.
     * @param rootDir the root dir.
     * @throws QueryJBuildException if the templates cannot be written.
     */
    protected void writeTemplates(
        @NotNull final QueryJCommand parameters,
        final int threadCount,
        @NotNull final File rootDir)
      throws  QueryJBuildException
    {
        if (threadCount > 1)
        {
            @NotNull final List<Future<T>> t_lTasks =
                writeTemplatesMultithread(
                    retrieveTemplates(parameters),
                    parameters,
                    retrieveCharset(parameters),
                    retrieveTemplateGenerator(
                        retrieveCaching(parameters),
                        retrieveThreadCount(parameters)),
                    threadCount,
                    rootDir);

            synchronized (AbstractTemplateWritingHandler.class)
            {
                @NotNull final List<Future<T>> t_lTrackedTasks =
                    retrieveGenerationTasks(parameters);

                t_lTrackedTasks.addAll(t_lTasks);
            }
        }
        else
        {
            writeTemplatesSequentially(
                retrieveTemplates(parameters),
                parameters,
                retrieveCharset(parameters),
                retrieveTemplateGenerator(
                    retrieveCaching(parameters),
                    retrieveThreadCount(parameters)),
                rootDir);
        }
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param rootDir the root dir.
     * @throws QueryJBuildException if the templates cannot be written.
     */
    @SuppressWarnings("unused")
    protected void writeTemplatesSequentially(
        @Nullable final List<T> templates,
        @NotNull final QueryJCommand parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        @NotNull final File rootDir)
        throws  QueryJBuildException
    {
        if (templates != null)
        {
            for  (@Nullable final T t_Template : templates)
            {
                if  (t_Template != null)
                {
                    try
                    {
                        templateGenerator.write(
                            t_Template,
                            retrieveOutputDir(t_Template.getTemplateContext(), rootDir, parameters),
                            rootDir,
                            charset);
                    }
                    catch (@NotNull final IOException ioException)
                    {
                        @Nullable final Log t_Log = UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class);

                        if (t_Log != null)
                        {
                            t_Log.warn("Error writing template", ioException);
                        }
                    }
                }
            }
        }
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param threadCount the number of threads to use.
     * @param rootDir the root dir.
     * @return the list if {@link Future} to keep track of the progress.
     * @throws QueryJBuildException if the templates cannot be written.
     */
    @NotNull
    protected List<Future<T>> writeTemplatesMultithread(
        @Nullable final List<T> templates,
        @NotNull final QueryJCommand parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        final int threadCount,
        @NotNull final File rootDir)
      throws  QueryJBuildException
    {
        @NotNull final List<Future<T>> result;

        if (templates != null)
        {
            result = new ArrayList<>(templates.size());

            @NotNull final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

            for  (int t_iIndex = 0 ; t_iIndex < templates.size(); t_iIndex++)
            {
                @Nullable final T t_Template = templates.get(t_iIndex);

                if  (t_Template != null)
                {
                    result.add(
                        threadPool.submit(
                            buildGeneratorThread(
                                t_Template,
                                templateGenerator,
                                retrieveOutputDir(t_Template.getTemplateContext(), rootDir, parameters),
                                rootDir,
                                charset,
                                t_iIndex + 1,
                                null,
                                parameters),
                            t_Template));
                }
            }

//            try
//            {
//                threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
//            }
//            catch (@NotNull final InterruptedException interrupted)
//            {
//                UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
//                    "Interrupted while waiting for the threads to finish", interrupted);
//            }
        }
        else
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Creates a new generator thread.
     * @param template the template.
     * @param generator the template generator.
     * @param outputDir the output dir.
     * @param rootDir the root folder.
     * @param charset the charset.
     * @param threadIndex the thread index.
     * @param barrier the cyclic barrier.
     * @param parameters the parameters.
     * @return the thread.
     */
    @NotNull
    protected abstract TemplateGeneratorThread<T, C, TG> buildGeneratorThread(
        @NotNull final T template,
        @NotNull final TG generator,
        @NotNull final File outputDir,
        @NotNull final File rootDir,
        @NotNull final Charset charset,
        final int threadIndex,
        @Nullable final CyclicBarrier barrier,
        @NotNull final QueryJCommand parameters);

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param engineName the engine name.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param threadCount the number of threads to use.
     * @param rootDir the root dir.
     * @return the futures for the concurrent threads.
     * @throws QueryJBuildException if the templates cannot be written.
     */
    @NotNull
    @SuppressWarnings("unused")
    protected List<Future<?>> writeTemplatesMultithread2ndVersion(
        @Nullable final List<T> templates,
        @NotNull final String engineName,
        @NotNull final QueryJCommand parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        final int threadCount,
        @NotNull final File rootDir)
      throws  QueryJBuildException
    {
        @NotNull final List<Future<?>> result;

        if (templates != null)
        {
            result = new ArrayList<>(templates.size());

            @NotNull final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

            @NotNull final CyclicBarrier round = new CyclicBarrier(threadCount);

            @NotNull AtomicInteger index = new AtomicInteger(0);

            int intIndex;

            @Nullable final Log t_Log =
                UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class);

            for (@Nullable final T t_Template : templates)
            {
                if (t_Template != null)
                {
                    intIndex = index.incrementAndGet();

                    if (intIndex <= threadCount)
                    {
                        if (t_Log != null)
                        {
                            t_Log.info(
                                "Starting a new thread " + intIndex + "/" + threadCount);
                        }

                        result.add(
                            threadPool.submit(
                                (Runnable)
                                    buildGeneratorThread(
                                        t_Template,
                                        templateGenerator,
                                        retrieveOutputDir(
                                            t_Template.getTemplateContext(), rootDir, parameters),
                                        rootDir,
                                        charset,
                                        intIndex,
                                        round,
                                        parameters)));
                    }
                    else
                    {
                        if (t_Log != null)
                        {
                            t_Log.info(
                                "No threads available " + intIndex + "/" + threadCount);
                        }

                        index = new AtomicInteger(0);

                        try
                        {
                            round.await();
                        }
                        catch (@NotNull final InterruptedException interrupted)
                        {
                            if (t_Log != null)
                            {
                                t_Log.info(
                                    "Thread pool interrupted while waiting", interrupted);
                            }
                        }
                        catch (@NotNull final BrokenBarrierException brokenBarrier)
                        {
                            if (t_Log != null)
                            {
                                t_Log.info(BROKEN_BARRIER_LITERAL, brokenBarrier);
                            }
                        }

                        if (t_Log != null)
                        {
                            t_Log.info(
                                "Resetting thread pool (shutdown? " + threadPool.isShutdown() + ")");
                        }

                        round.reset();
                    }
                }
            }
        }
        else
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves the template generator.
     * @param caching whether to enable template caching.
     * @param threadCount the number of threads to use.
     * @return such instance.
     */
    @NotNull
    public abstract TG retrieveTemplateGenerator(final boolean caching, final int threadCount);

    /**
     * Retrieves the templates from the command.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException if the templates cannot be retrieved.
     */
    @NotNull
    public abstract List<T> retrieveTemplates(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException;

    /**
     * Retrieves the generation tasks.
     * @param parameters the parameter map.
     * @return such list.
     */
    @NotNull
    protected List<Future<T>> retrieveGenerationTasks(
        @NotNull final QueryJCommand parameters)
    {
        @NotNull final List<Future<T>> result;

        @Nullable final List<Future<T>> aux =
            new QueryJCommandWrapper<List<Future<T>>>(parameters).getSetting(buildGenerationTasksKey());

        if (aux == null)
        {
            result = new ArrayList<>(0);
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param rootDir the root dir.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws QueryJBuildException if the output dir is unavailable.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
      throws  QueryJBuildException;
}
