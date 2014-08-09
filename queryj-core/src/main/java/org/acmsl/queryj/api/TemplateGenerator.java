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
 * Filename: AbstractTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Marker for template generators.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.IOException;
import java.io.File;
import java.nio.charset.Charset;

/**
 * Marker for template generators.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface TemplateGenerator<T extends Template<C>, C extends TemplateContext>
{
    /**
     * Writes a template to disk.
     * @param template the table template to write.
     * @param outputDir the output folder.
     * @param rootFolder the root folder.
     * @param charset the file encoding.
     * @return <code>true</code> if it actually generated the file.
     * @throws IOException if the template cannot be written.
     * @throws QueryJBuildException if any other error occurs.
     */
    boolean write(
        @NotNull final T template,
        @NotNull final File outputDir,
        @NotNull final File rootFolder,
        @NotNull final Charset charset)
        throws  IOException,
                QueryJBuildException;

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    @NotNull
    DecoratorFactory getDecoratorFactory();
}
