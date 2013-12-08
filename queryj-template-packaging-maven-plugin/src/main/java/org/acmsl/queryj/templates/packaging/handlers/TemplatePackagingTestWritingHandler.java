/*
                        QueryJ Template Packaging

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
 * Filename: TemplatePackagingTestWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description:  Customizes default behavior for test-related generated sources.
 *
 * Date: 2013/11/11
 * Time: 21:27
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.packaging.TemplatePackagingContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateGenerator;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.templates.packaging.exceptions.MissingOutputDirForTestsException;
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

/**
 * Customizes default behavior for test-related generated sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/11 21:27
 */
@ThreadSafe
public abstract class TemplatePackagingTestWritingHandler
    <T extends TemplatePackagingTemplate<C>,
     C extends TemplatePackagingContext,
     TG extends TemplatePackagingTemplateGenerator<T, C>>
    extends TemplatePackagingWritingHandler<T, C, TG>
{
    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    @Override
    protected File retrieveProjectOutputDir(@NotNull final QueryJCommand parameters)
    {
        @NotNull final File result;

        @Nullable final File aux = new QueryJCommandWrapper<File>(parameters).getSetting(OUTPUT_DIR_FOR_TESTS);

        if (aux == null)
        {
            throw new MissingOutputDirForTestsException();
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
     * @throws QueryJBuildException if the output-dir retrieval process fails.
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        return context.getOutputDir();
    }
}
