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
 * Filename: AbstractQueryJTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: QueryJ-specific base writing handler.
 *
 * Date: 2013/08/20
 * Time: 06:48
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.QueryJTemplate;
import org.acmsl.queryj.api.QueryJTemplateContext;
import org.acmsl.queryj.api.TemplateGeneratorThread;
import org.acmsl.queryj.api.TemplateGenerator;

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
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.CyclicBarrier;

/**
 * QueryJ-specific base writing handler.
 * @param <T> the template.
 * @param <C> the context type.
 * @param <TG> the template generator.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/20 06:48
 */
@ThreadSafe
public abstract class AbstractQueryJTemplateWritingHandler
    <T extends QueryJTemplate<C>,
     C extends QueryJTemplateContext,
     TG extends TemplateGenerator<T, C>>
    extends AbstractTemplateWritingHandler<T, C, TG>
{
    /**
     * Creates a new generator thread.
     * @param template the template.
     * @param generator the template generator.
     * @param outputDir the output dir.
     * @param rootDir the root folder.
     * @param charset the charset.
     * @param threadIndex the thread index.
     * @param barrier the barrier.
     * @param parameters the parameters.
     * @return the thread.
     */
    @NotNull
    @Override
    protected TemplateGeneratorThread<T, C, TG> buildGeneratorThread(
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
            new TemplateGeneratorThread<>(
                generator, template, outputDir, rootDir, charset, threadIndex + 1, barrier);
    }
}
