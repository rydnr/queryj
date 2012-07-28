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
 * Filename: BasePerTableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-table templates.
 *
 */
package org.acmsl.queryj.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.templates.Template;
import org.acmsl.queryj.templates.TemplateContext;
import org.acmsl.queryj.templates.TemplateGenerator;
import org.acmsl.queryj.templates.TemplateGeneratorThread;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons Logging classes.
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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Writes <i>per-table</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class AbstractTemplateWritingHandler
    <T extends Template<C>, TG extends TemplateGenerator<T,C>, C extends TemplateContext>
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * The database product name entry.
     */
    public static final String PRODUCT_NAME = "L.:ProductName::.@";

    /**
     * Creates a <code>AbstractTemplateWritingHandler</code> instance.
     */
    @ThreadSafe
    public AbstractTemplateWritingHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    @Override
    protected boolean handle(@NotNull final Map parameters)
      throws  QueryJBuildException
    {
        writeTemplates(
            parameters,
            retrieveProductName(parameters, retrieveDatabaseMetaData(parameters)),
            retrieveThreadCount(parameters),
            retrieveProjectOutputDir(parameters));

        return false;
    }

    /**
     * Writes the templates.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param threadCount the thread count.
     * @param rootDir the root dir.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    protected void writeTemplates(
        @NotNull final Map parameters,
        @NotNull final String engineName,
        final int threadCount,
        @NotNull final File rootDir)
      throws  QueryJBuildException
    {
        if (threadCount > 1)
        {
            List<Future<T>> t_lTasks =
                writeTemplatesMultithread(
                    retrieveTemplates(parameters),
                    engineName,
                    parameters,
                    retrieveCharset(parameters),
                    retrieveTemplateGenerator(retrieveCaching(parameters), retrieveThreadCount(parameters)),
                    threadCount,
                    rootDir);

            synchronized (AbstractTemplateWritingHandler.class)
            {
                List<Future<T>> t_lTrackedTasks =
                    retrieveGenerationTasks(parameters);

                if (t_lTrackedTasks == null)
                {
                    t_lTrackedTasks = t_lTasks;
                    annotateGenerationTasks(t_lTrackedTasks, parameters);
                }
                else
                {
                    t_lTrackedTasks.addAll(t_lTasks);
                }
            }
        }
        else
        {
            writeTemplatesSequentially(
                retrieveTemplates(parameters),
                engineName,
                parameters,
                retrieveCharset(parameters),
                retrieveTemplateGenerator(retrieveCaching(parameters), retrieveThreadCount(parameters)),
                rootDir);
        }
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param engineName the engine name.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param rootDir the root dir.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void writeTemplatesSequentially(
        @Nullable final List<T> templates,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        @NotNull final File rootDir)
        throws  QueryJBuildException
    {
        if (templates != null)
        {
            for  (T t_Template : templates)
            {
                if  (t_Template != null)
                {
                    try
                    {
                        templateGenerator.write(
                            t_Template,
                            retrieveOutputDir(t_Template.getTemplateContext(), rootDir, engineName, parameters),
                            rootDir,
                            charset);
                    }
                    catch (@NotNull final IOException ioException)
                    {
                        UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).warn(
                            "Error writing template", ioException);
                    }
                }
            }
        }
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param engineName the engine name.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param threadCount the number of threads to use.
     * @param rootDir the root dir.
     * @return the list if {@link Future} to keep track of the progress.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    @NotNull
    protected List<Future<T>> writeTemplatesMultithread(
        @Nullable final List<T> templates,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        final int threadCount,
        @NotNull final File rootDir)
      throws  QueryJBuildException
    {
        List<Future<T>> result;

        if (templates != null)
        {
            result = new ArrayList<Future<T>>(templates.size());

            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

            for  (int t_iIndex = 0 ; t_iIndex < templates.size(); t_iIndex++)
            {
                T t_Template = templates.get(t_iIndex);

                if  (t_Template != null)
                {
                    result.add(
                        threadPool.submit(
                            new TemplateGeneratorThread<T, TG, C>(
                                templateGenerator,
                                t_Template,
                                retrieveOutputDir(t_Template.getTemplateContext(), rootDir, engineName, parameters),
                                rootDir,
                                charset,
                                t_iIndex + 1,
                                null),
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
            result = new ArrayList<Future<T>>(0);
        }

        return result;
    }

    /**
     * Writes the templates.
     * @param templates the templates.
     * @param engineName the engine name.
     * @param parameters the parameters.
     * @param charset the file encoding.
     * @param templateGenerator the template generator.
     * @param threadCount the number of threads to use.
     * @param rootDir the root dir.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the build process cannot be performed.
     */
    @ThreadSafe
    @NotNull
    @SuppressWarnings("unused")
    protected List<Future> writeTemplatesMultithread2ndVersion(
        @Nullable final List<T> templates,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final Charset charset,
        @NotNull final TG templateGenerator,
        final int threadCount,
        @NotNull final File rootDir)
        throws  QueryJBuildException
    {
        List<Future> result;

        if (templates != null)
        {
            result = new ArrayList<Future>(templates.size());

            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

            final CyclicBarrier round = new CyclicBarrier(threadCount);

            AtomicInteger index = new AtomicInteger(0);

            int intIndex;

            for (T t_Template : templates)
            {
                if (t_Template != null)
                {
                    intIndex = index.incrementAndGet();

                    if (intIndex <= threadCount)
                    {
                        UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
                            "Starting a new thread " + intIndex + "/" + threadCount);

                        result.add(
                            threadPool.submit(
                                new TemplateGeneratorThread<T, TG, C>(
                                    templateGenerator,
                                    t_Template,
                                    retrieveOutputDir(t_Template.getTemplateContext(), rootDir, engineName, parameters),
                                    rootDir,
                                    charset,
                                    intIndex,
                                    round)));
                    }
                    else
                    {
                        UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
                            "No threads available " + intIndex + "/" + threadCount);

                        index = new AtomicInteger(0);

                        try
                        {
                            round.await();
                        }
                        catch (@NotNull final InterruptedException interrupted)
                        {
                            UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
                                "Thread pool interrupted while waiting", interrupted);

                        }
                        catch (@NotNull final BrokenBarrierException brokenBarrier)
                        {
                            UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
                                "Broken barrier", brokenBarrier);
                        }

                        UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class).info(
                            "Resetting thread pool (shutdown? " + threadPool.isShutdown() + ")");

                        round.reset();
                    }
                }
            }
        }
        else
        {
            result = new ArrayList<Future>(0);
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
    protected abstract TG retrieveTemplateGenerator(final boolean caching, final int threadCount);

    /**
     * Retrieves the templates from the attribute map.
     *
     * @param parameters the parameter map.
     * @return the template.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the template retrieval process if faulty.
     */
    @Nullable
    protected abstract List<T> retrieveTemplates(@NotNull final Map parameters)
        throws QueryJBuildException;

    /**
     * Retrieves the database product name.
     * @param parameters the parameter map.
     * @param metadata the database metadata.
     * @return the product name.
     */
    @ThreadSafe
    @SuppressWarnings("unchecked")
    public String retrieveProductName(@NotNull final Map parameters, @Nullable final DatabaseMetaData metadata)
    {
        @Nullable String result = (String) parameters.get(PRODUCT_NAME);

        if (   (result == null)
            && (metadata != null))
        {
            try
            {
                result = metadata.getDatabaseProductName();
            }
            catch  (@NotNull final SQLException sqlException)
            {
                Log t_Log = UniqueLogFactory.getLog(AbstractTemplateWritingHandler.class);

                if (t_Log != null)
                {
                    t_Log.warn(
                        "Cannot retrieve database product name, "
                        + "version or quote string",
                        sqlException);
                }
            }
        }

        if (result == null)
        {
            result = "";
        }

        parameters.put(PRODUCT_NAME, result);

        return result;
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param rootDir the root dir.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the output-dir retrieval process if faulty.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final String engineName,
        @NotNull final Map parameters)
      throws  QueryJBuildException;
}
