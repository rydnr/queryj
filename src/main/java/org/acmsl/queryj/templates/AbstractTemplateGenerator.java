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
 * Filename: AbstractTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic for template generators.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.io.FileUtils;

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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Common logic for template generators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractTemplateGenerator<N extends Template<C>, C extends TemplateContext>
    implements TemplateGenerator<N, C>
{
    /**
     * Whether to enable template caching.
     */
    private boolean m__bCaching = true;

    /**
     * The thread count.
     */
    private int m__iThreadCount = 1;

    /**
     * Creates an {@link AbstractTemplateGenerator} with given settings.
     * @param caching whether to support caching or not.
     * @param threadCount the number of threads to use.
     */
    protected AbstractTemplateGenerator(final boolean caching, final int threadCount)
    {
        immutableSetCaching(caching);
        immutableSetThreadCount(threadCount);
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    protected final void immutableSetCaching(final boolean flag)
    {
        m__bCaching = flag;
    }

    /**
     * Specifies whether to cache templates or not.
     * @param flag such setting.
     */
    @SuppressWarnings("unused")
    protected void setCaching(final boolean flag)
    {
        immutableSetCaching(flag);
    }

    /**
     * Retrieves whether to cache templates or not.
     * @return such setting.
     */
    public boolean isCaching()
    {
        return m__bCaching;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    protected final void immutableSetThreadCount(final int count)
    {
        m__iThreadCount = count;
    }

    /**
     * Specifies the thread count.
     * @param count such value.
     */
    @SuppressWarnings("unused")
    protected void setThreadCount(final int count)
    {
        immutableSetThreadCount(count);
    }

    /**
     * Retrieves the thread count.
     * @return such value.
     */
    @SuppressWarnings("unused")
    public int getThreadCount()
    {
        return m__iThreadCount;
    }

    /**
     * Writes a table template to disk.
     * @param template the table template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     */
    public void write(
        @NotNull final N template,
        @NotNull final File outputDir,
        @NotNull final Charset charset)
        throws  IOException
    {
        write(
            isCaching(),
            getThreadCount(),
            template,
            retrieveTemplateFileName(template.getTemplateContext()),
            outputDir,
            charset,
            FileUtils.getInstance());
    }

    /**
     * Writes a table template to disk.
     * @param caching whether to use caching or not.
     * @param threadCount the maximum number of threads to use.
     * @param template the table template to write.
     * @param fileName the template's file name.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     */
    protected void write(
        final boolean caching,
        final int threadCount,
        @NotNull final N template,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils)
        throws  IOException
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        final AtomicInteger threadsCreated = new AtomicInteger(0);

        final CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int index = 0; index < threadCount; index++)
        {
            threadPool.execute(
                new TemplateGeneratorThread<N,C>(
                    template,
                    caching,
                    fileName,
                    outputDir,
                    charset,
                    fileUtils,
                    doneLatch,
                    threadsCreated));
        }

        try
        {
            doneLatch.await();
        }
        catch (@NotNull final InterruptedException interruptedException)
        {
            UniqueLogFactory.getLog(AbstractTemplateGenerator.class).warn(
                "Generation thread interrupted", interruptedException);
        }
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

}
