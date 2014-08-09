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
 * Filename: TemplateGeneratorThread.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Thread to make generators concurrent.
 *
 * Date: 7/10/12
 * Time: 8:42 PM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.acmsl.queryj.api.handlers.AbstractTemplateWritingHandler;
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Thread to make generators concurrent.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/10
 */
@ThreadSafe
public abstract class AbstractTemplateGeneratorThread
    <T extends Template<C>, C extends TemplateContext, TG extends TemplateGenerator<T, C>>
    extends Thread
{
    /**
     * The template generator.
     */
    private TG templateGenerator;

    /**
     * The template.
     */
    private T template;

    /**
     * The output folder.
     */
    private File outputDir;

    /**
     * The root folder.
     */
    private File rootDir;

    /**
     * The charset.
     */
    private Charset charset;

    /**
     * The thread index.
     */
    private int threadIndex;

    /**
     * The cyclic barrier.
     */
    private CyclicBarrier cyclicBarrier;

    /**
     * Creates a {@link AbstractTemplateGeneratorThread} with given information.
     * @param templateGenerator the template generator.
     * @param template the template.
     * @param outputFolder the output folder.
     * @param rootFolder the root folder.
     * @param charset the {@link Charset} instance.
     * @param threadIndex the thread index.
     * @param barrier the {@link CyclicBarrier}.
     */
    @ThreadSafe
    public AbstractTemplateGeneratorThread(
        @NotNull final TG templateGenerator,
        @NotNull final T template,
        @NotNull final File outputFolder,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        final int threadIndex,
        @Nullable final CyclicBarrier barrier)
    {
        immutableSetTemplateGenerator(templateGenerator);
        immutableSetTemplate(template);
        immutableSetOutputFolder(outputFolder);
        immutableSetRootFolder(rootFolder);
        immutableSetCharset(charset);
        immutableSetThreadIndex(threadIndex);
        immutableSetCyclicBarrier(barrier);
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    @ThreadSafe
    protected final void immutableSetTemplate(@NotNull final T template)
    {
        this.template = template;
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setTemplate(@NotNull final T template)
    {
        immutableSetTemplate(template);
    }

    /**
     * Retrieves the template.
     * @return such {@link Template}.
     */
    @ThreadSafe
    protected T getTemplate()
    {
        return template;
    }

    /**
     * Specifies the template generator.
     * @param templateGenerator the {@link TemplateGenerator} instance to use.
     */
    @ThreadSafe
    protected final void immutableSetTemplateGenerator(@NotNull final TG templateGenerator)
    {
        this.templateGenerator = templateGenerator;
    }

    /**
     * Specifies the template generator.
     * @param templateGenerator the {@link TemplateGenerator} instance to use.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setTemplateGenerator(@NotNull final TG templateGenerator)
    {
        immutableSetTemplateGenerator(templateGenerator);
    }

    /**
     * Retrieves the template generator.
     * @return such {@link TemplateGenerator}.
     */
    @ThreadSafe
    @NotNull
    protected TG getTemplateGenerator()
    {
        return templateGenerator;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    @ThreadSafe
    protected final void immutableSetOutputFolder(@NotNull final File outputFolder)
    {
        this.outputDir = outputFolder;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setOutputFolder(@NotNull final File outputFolder)
    {
        immutableSetOutputFolder(outputFolder);
    }

    /**
     * Retrieves the output folder.
     * @return such information.
     */
    @ThreadSafe
    @NotNull
    public File getOutputFolder()
    {
        return this.outputDir;
    }

    /**
     * Specifies the root folder.
     * @param rootFolder the root folder.
     */
    @ThreadSafe
    protected final void immutableSetRootFolder(@NotNull final File rootFolder)
    {
        this.rootDir = rootFolder;
    }

    /**
     * Specifies the root folder.
     * @param rootFolder the root folder.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setRootFolder(@NotNull final File rootFolder)
    {
        immutableSetRootFolder(rootFolder);
    }

    /**
     * Retrieves the root folder.
     * @return such information.
     */
    @ThreadSafe
    @NotNull
    public File getRootFolder()
    {
        return this.rootDir;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    @ThreadSafe
    protected final void immutableSetCharset(@NotNull final Charset charset)
    {
        this.charset = charset;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setCharset(@NotNull final Charset charset)
    {
        immutableSetCharset(charset);
    }

    /**
     * Retrieves the charset.
     * @return the {@link Charset} in use.
     */
    @ThreadSafe
    @NotNull
    protected Charset getCharset()
    {
        return this.charset;
    }

    /**
     * Specifies the thread index.
     * @param index such index.
     */
    @ThreadSafe
    protected final void immutableSetThreadIndex(final int index)
    {
        this.threadIndex = index;
    }

    /**
     * Specifies the thread index.
     * @param index such index.
     */
    @SuppressWarnings("unused")
    @ThreadSafe
    protected void setThreadIndex(final int index)
    {
        immutableSetThreadIndex(index);
    }

    /**
     * Retrieves the thread index.
     * @return such index.
     */
    @ThreadSafe
    public int getThreadIndex()
    {
        return this.threadIndex;
    }

    /**
     * Specifies the cyclic barrier.
     * @param barrier the barrier.
     */
    @ThreadSafe
    protected final void immutableSetCyclicBarrier(@Nullable final CyclicBarrier barrier)
    {
        this.cyclicBarrier = barrier;
    }

    /**
     * Specifies the cyclic barrier.
     * @param barrier the barrier.
     */
    @ThreadSafe
    @SuppressWarnings("unused")
    protected void setCyclicBarrier(@NotNull final CyclicBarrier barrier)
    {
        immutableSetCyclicBarrier(barrier);
    }

    /**
     * Retrieves the cyclic barrier.
     * @return such barrier
     */
    @ThreadSafe
    @Nullable
    protected CyclicBarrier getCyclicBarrier()
    {
        return this.cyclicBarrier;
    }

    /**
     * Builds the log message when the generation has succeeded.
     * @param template the template.
     * @param threadIndex the index.
     * @return the log message.
     */
    @NotNull
    protected String buildSuccessLogMessage(@NotNull final T template, final int threadIndex)
    {
        return "Thread " + template.getTemplateContext().getTemplateName() + ":" + threadIndex + " finished";
    }

    /**
     * Runs the thread.
     */
    @Override
    @ThreadSafe
    public void run()
    {
        runGenerator(
            getTemplateGenerator(),
            getTemplate(),
            getOutputFolder(),
            getRootFolder(),
            getCharset(),
            getThreadIndex(),
            getCyclicBarrier(),
            UniqueLogFactory.getLog(AbstractTemplateGeneratorThread.class));
    }

    /**
     * Runs the template generation process.
     * @param templateGenerator the template generator.
     * @param template the template.
     * @param outputDir the output folder.
     * @param rootFolder the root folder.
     * @param charset the {@link Charset} to use.
     * @param threadIndex the thread index.
     * @param barrier the cyclic barrier.
     * @param log the {@link Log} instance.
     */
    @ThreadSafe
    protected void runGenerator(
        @NotNull final TG templateGenerator,
        @NotNull final T template,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        final int threadIndex,
        @Nullable final CyclicBarrier barrier,
        @Nullable final Log log)
    {
        boolean generated = false;

        try
        {
            generated =
                templateGenerator.write(
                    template,
                    outputDir,
                    rootFolder,
                    charset);
        }
        catch (@NotNull final QueryJBuildException unknownException)
        {
            if (log != null)
            {
                log.warn(unknownException);
            }
        }
        catch (@NotNull final IOException ioException)
        {
            if (log != null)
            {
                log.warn(ioException);
            }
        }

        if (generated)
        {
            if (log != null)
            {
                log.debug(buildSuccessLogMessage(template, threadIndex));
            }
        }

        if (barrier != null)
        {
            try
            {
                barrier.await();
            }
            catch (@NotNull final InterruptedException interrupted)
            {
                if (log != null)
                {
                    log.debug("Interrupted thread", interrupted);
                }

                Thread.currentThread().interrupt();
            }
            catch (@NotNull final BrokenBarrierException brokenBarrier)
            {
                if (log != null)
                {
                    log.warn(AbstractTemplateWritingHandler.BROKEN_BARRIER_LITERAL, brokenBarrier);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return "AbstractTemplateGeneratorThread{" +
               "charset=" + charset +
               ", templateGenerator=" + templateGenerator +
               ", template=" + template +
               ", outputDir=" + outputDir +
               ", rootDir=" + rootDir +
               ", threadIndex=" + threadIndex +
               ", cyclicBarrier=" + cyclicBarrier +
               '}';
    }
}
