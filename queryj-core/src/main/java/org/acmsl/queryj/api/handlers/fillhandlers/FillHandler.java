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
 * Filename: TemplateFillHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/13/12
 * Time: 6:01 AM
 *
 */
package org.acmsl.queryj.api.handlers.fillhandlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.handlers.TemplateHandler;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;

/**
 * Used to fill the template's dynamic parameters based on a chain-of-responsibility pattern,
 * for clarity sake.
 * @author <a href="mailto:jose@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2012/05/13
 * @param <P> the placeholder type.
 */
public interface FillHandler<P>
    extends TemplateHandler<QueryJCommand>,
            Serializable
{
    /**
     * Retrieves the placeholder.
     * @return such placeholder.
     */
    @NotNull
    String getPlaceHolder();

    /**
     * Retrieves the template value for that placeholder.
     * @return the dynamic value.
     * @throws QueryJBuildException if the value cannot be evaluated.
     */
    @Nullable
    P getValue()
    throws QueryJBuildException;
}
