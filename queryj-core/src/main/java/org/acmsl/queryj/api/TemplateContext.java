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
 * Filename: TemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents the context used by templates to build sources.
 *
 * Date: 2013/08/15
 * Time: 08:19
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.Serializable;

/**
 * Represents the context used by templates to build sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/15 08/19
 */
@ThreadSafe
public interface TemplateContext
    extends Serializable
{
    /**
     * Retrieves the template name.
     * @return such name.
     */
    @NotNull
    String getTemplateName();

    /**
     * Retrieves the file name.
     * @return such information.
     */
    @NotNull
    String getFileName();

    /**
     * Specifies the file name.
     * @param fileName the filename.
     */
    void setFileName(@NotNull final String fileName);

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    String getPackageName();

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    void setPackageName(@NotNull final String packageName);

    /**
     * Retrieves the version.
     * @return such information.
     */
    @NotNull
    String getVersion();

    /**
     * Retrieves the decorator factory.
     * @return such {@link DecoratorFactory factory}.
     */
    @NotNull
    DecoratorFactory getDecoratorFactory();

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory factory}.
     */
    void setDecoratorFactory(@NotNull final DecoratorFactory factory);

    /**
     * Checks whether is being debugged or not.
     * @return such behavior.
     */
    boolean isDebugEnabled();
}
