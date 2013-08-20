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
 * Filename: TemplatePackagingWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base writing handler for Template Packaging templates.
 *
 * Date: 2013/08/20
 * Time: 18:02
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.TemplateGeneratorThread;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.handlers.AbstractTemplateWritingHandler;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.CyclicBarrier;

/**
 * Base writing handler for Template Packaging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/20 18:02
 */
@ThreadSafe
public abstract class TemplatePackagingWritingHandler
    <T extends TemplatePackagingTemplate<C>,
     TG extends TemplatePackagingTemplateGenerator<T, C>,
     C extends TemplatePackagingContext>
    extends AbstractTemplateWritingHandler<T, TG, C>
    implements TemplatePackagingSettings
{
    /**
     * Default constructor.
     */
    public TemplatePackagingWritingHandler() {}

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
    @Override
    protected TemplateGeneratorThread<T, TG, C> buildGeneratorThread(
        @NotNull final T template,
        @NotNull final TG generator,
        @NotNull final File outputDir,
        @NotNull final File rootDir,
        @NotNull final Charset charset,
        final int threadIndex,
        @Nullable final CyclicBarrier barrier,
        @NotNull final QueryJCommand parameters)
    {
        return
            new TemplateGeneratorThread<T, TG, C>(
                generator,
                template,
                outputDir,
                rootDir,
                charset,
                threadIndex,
                barrier);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param context the context.
     * @param rootDir the root dir.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException
     *          if the output-dir retrieval process if faulty.
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final String engineName,
        @NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        return context.getOutputDir();
    }
}
