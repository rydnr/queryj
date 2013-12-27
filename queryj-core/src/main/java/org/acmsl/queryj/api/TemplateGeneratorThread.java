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
 * Filename: QueryJTemplateGeneratorThread.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: TemplateGeneratorThread bound to QueryJ-specific classes.
 *
 * Date: 2013/08/15
 * Time: 09:00
 *
 */
package org.acmsl.queryj.api;

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
 * {@link AbstractTemplateGeneratorThread} bound to QueryJ-specific classes.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/15 09/00
 */
@ThreadSafe
public class TemplateGeneratorThread
    <T extends Template<C>, C extends TemplateContext, TG extends TemplateGenerator<T, C>>
    extends AbstractTemplateGeneratorThread<T, C, TG>
{
    /**
     * Creates a new thread with given information.
     * @param templateGenerator the template generator.
     * @param template the template.
     * @param outputFolder the output folder.
     * @param rootFolder the root folder.
     * @param charset the charset.
     * @param threadIndex the thread index.
     * @param barrier the barrier.
     */
    public TemplateGeneratorThread(
        @NotNull final TG templateGenerator,
        @NotNull final T template,
        @NotNull final File outputFolder,
        @NotNull final File rootFolder,
        @NotNull final Charset charset,
        final int threadIndex,
        @Nullable final CyclicBarrier barrier)
    {
        super(templateGenerator, template, outputFolder, rootFolder, charset, threadIndex, barrier);
    }
}
