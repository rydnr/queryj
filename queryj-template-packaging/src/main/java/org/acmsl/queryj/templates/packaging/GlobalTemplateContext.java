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
 * Filename: GlobalTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Context for templates requiring global access to all information
 *              available.
 *
 * Date: 2013/09/15
 * Time: 06:01
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Context for templates requiring global access to all information available.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:01
 */
@ThreadSafe
public interface GlobalTemplateContext
    extends TemplatePackagingContext
{
    /**
     * Retrieves all {@link TemplateDef}s.
     * @return such information.
     */
    @NotNull
    List<TemplateDef<String>> getTemplateDefs();

    /**
     * Retrieves the jdbc driver.
     * @return such driver.
     */
    @NotNull
    String getJdbcDriver();

    /**
     * Retrieves the jdbc url.
     * @return such url.
     */
    @NotNull
    String getJdbcUrl();

    /**
     * Retrieves the jdbc username.
     * @return such information.
     */
    @NotNull
    String getJdbcUsername();

    /**
     * Retrieves the jdbc password.
     * @return such information.
     */
    @NotNull
    String getJdbcPassword();
}
