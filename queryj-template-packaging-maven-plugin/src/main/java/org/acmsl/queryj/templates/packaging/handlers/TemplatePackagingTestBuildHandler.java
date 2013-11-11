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
 * Filename: TemplatePackagingTestBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Customizes the common build handler behavior for test-only
 *              templates.
 *
 * Date: 2013/11/11
 * Time: 21:50
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.GlobalTemplateContext;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplate;
import org.acmsl.queryj.templates.packaging.TemplatePackagingTemplateFactory;

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

/**
 * Customizes the common build handler behavior for test-only templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/11 21:50
 */
@ThreadSafe
public abstract class TemplatePackagingTestBuildHandler
    <T extends TemplatePackagingTemplate<C>,
        TF extends TemplatePackagingTemplateFactory<T, C>,
        C extends GlobalTemplateContext>
    extends GlobalBuildHandler<T, TF, C>
{
    /**
     * Retrieves the output dir.
     * @param parameters the parameters.
     * @return the output dir.
     */
    @NotNull
    public File retrieveRootDir(@NotNull final QueryJCommand parameters)
    {
        @Nullable final File result =
            new QueryJCommandWrapper<File>(parameters).getSetting(OUTPUT_DIR_FOR_TESTS);

        if (result == null)
        {
            // TODO
            throw new RuntimeException("Output dir for tests missing");
        }

        return result;
    }
}
