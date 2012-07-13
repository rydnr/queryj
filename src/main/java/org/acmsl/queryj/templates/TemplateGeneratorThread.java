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
package org.acmsl.queryj.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;

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

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Thread to make generators concurrent.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/10
 */
public class TemplateGeneratorThread
    <T extends Template<C>, TG extends TemplateGenerator<T,C>, C extends TemplateContext>
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
     * Creates a {@link TemplateGeneratorThread} with given information.
     * @param templateGenerator the template generator.
     * @param template the template.
     * @param outputFolder the output folder.
     * @param charset the {@link Charset} instance.
     * @param threadIndex the thread index.
     * @param barrier the {@link CyclicBarrier}.
     */
    public TemplateGeneratorThread(
        @NotNull final TG templateGenerator,
        @NotNull final T template,
        @NotNull final File outputFolder,
        @NotNull final Charset charset,
        final int threadIndex,
        @NotNull final CyclicBarrier barrier)
    {
        immutableSetTemplateGenerator(templateGenerator);
        immutableSetTemplate(template);
        immutableSetOutputFolder(outputFolder);
        immutableSetCharset(charset);
        immutableSetThreadIndex(threadIndex);
        immutableSetCyclicBarrier(barrier);
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    protected final void immutableSetTemplate(@NotNull final T template)
    {
        this.template = template;
    }

    /**
     * Specifies the template.
     * @param template the {@link Template} instance to use.
     */
    @SuppressWarnings("unused")
    protected void setTemplate(@NotNull final T template)
    {
        immutableSetTemplate(template);
    }

    /**
     * Retrieves the template.
     * @return such {@link Template}.
     */
    protected T getTemplate()
    {
        return template;
    }

    /**
     * Specifies the template generator.
     * @param templateGenerator the {@link TemplateGenerator} instance to use.
     */
    protected final void immutableSetTemplateGenerator(@NotNull final TG templateGenerator)
    {
        this.templateGenerator = templateGenerator;
    }

    /**
     * Specifies the template generator.
     * @param templateGenerator the {@link TemplateGenerator} instance to use.
     */
    @SuppressWarnings("unused")
    protected void setTemplateGenerator(@NotNull final TG templateGenerator)
    {
        immutableSetTemplateGenerator(templateGenerator);
    }

    /**
     * Retrieves the template generator.
     * @return such {@link TemplateGenerator}.
     */
    @NotNull
    protected TG getTemplateGenerator()
    {
        return templateGenerator;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    protected final void immutableSetOutputFolder(@NotNull final File outputFolder)
    {
        this.outputDir = outputFolder;
    }

    /**
     * Specifies the output folder.
     * @param outputFolder the output folder.
     */
    @SuppressWarnings("unused")
    protected void setOutputFolder(@NotNull final File outputFolder)
    {
        immutableSetOutputFolder(outputFolder);
    }

    /**
     * Retrieves the output folder.
     * @return such information.
     */
    @NotNull
    public File getOutputFolder()
    {
        return this.outputDir;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    protected final void immutableSetCharset(@NotNull final Charset charset)
    {
        this.charset = charset;
    }

    /**
     * Specifies the charset.
     * @param charset the {@link Charset} to use.
     */
    @SuppressWarnings("unused")
    protected void setCharset(@NotNull final Charset charset)
    {
        immutableSetCharset(charset);
    }

    /**
     * Retrieves the charset.
     * @return the {@link Charset} in use.
     */
    @NotNull
    protected Charset getCharset()
    {
        return this.charset;
    }

    /**
     * Specifies the thread index.
     * @param index such index.
     */
    protected final void immutableSetThreadIndex(final int index)
    {
        this.threadIndex = index;
    }

    /**
     * Specifies the thread index.
     * @param index such index.
     */
    @SuppressWarnings("unused")
    protected void setThreadIndex(final int index)
    {
        immutableSetThreadIndex(index);
    }

    /**
     * Retrieves the thread index.
     * @return such index.
     */
    public int getThreadIndex()
    {
        return this.threadIndex;
    }

    /**
     * Specifies the cyclic barrier.
     * @param barrier the barrier.
     */
    protected final void immutableSetCyclicBarrier(@NotNull final CyclicBarrier barrier)
    {
        this.cyclicBarrier = barrier;
    }

    /**
     * Specifies the cyclic barrier.
     * @param barrier the barrier.
     */
    @SuppressWarnings("unused")
    protected void setCyclicBarrier(@NotNull final CyclicBarrier barrier)
    {
        immutableSetCyclicBarrier(barrier);
    }

    /**
     * Retrieves the cyclic barrier.
     * @return such barrier
     */
    @NotNull
    protected CyclicBarrier getCyclicBarrier()
    {
        return this.cyclicBarrier;
    }

    public void run()
    {
        runGenerator(
            getTemplateGenerator(),
            getTemplate(),
            getOutputFolder(),
            getCharset(),
            getThreadIndex(),
            getCyclicBarrier(),
            UniqueLogFactory.getLog(TemplateGeneratorThread.class));
    }

    /**
     * Runs the template generation process.
     * @param templateGenerator the template generator.
     * @param outputDir the output folder.
     * @param charset the {@link Charset} to use.
     * @param threadIndex the thread index.
     * @param barrier the cyclic barrier.
     * @param log the {@link Log} instance.
     */
    protected void runGenerator(
        @NotNull final TG templateGenerator,
        @NotNull final T template,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        final int threadIndex,
        @NotNull final CyclicBarrier barrier,
        @NotNull final Log log)
    {
        try
        {
            templateGenerator.write(
                template,
                outputDir,
                charset);
        }
        catch (@NotNull final QueryJBuildException unknownException)
        {
            log.warn(unknownException);
        }
        catch (@NotNull final IOException ioException)
        {
            log.warn(ioException);
        }

        UniqueLogFactory.getLog(TemplateGeneratorThread.class).info(
            "Thread " + threadIndex + " finished");

        try
        {
            barrier.await();
        }
        catch (@NotNull final InterruptedException interrupted)
        {
            log.debug("Interrupted thread", interrupted);

            Thread.currentThread().interrupt();
        }
        catch (@NotNull final BrokenBarrierException brokenBarrier)
        {
            log.warn("Broken barrier", brokenBarrier);
        }
    }
}
