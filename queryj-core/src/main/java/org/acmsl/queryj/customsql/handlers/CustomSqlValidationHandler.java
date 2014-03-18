//;-*- mode: java -*-
/*
                        QueryJ-Core

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
 * Filename: CustomSqlValidationHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Validates any custom sql queries.
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Validates any custom sql queries.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomSqlValidationHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates a <code>CustomSqlValidationHandler</code> instance.
     */
    public CustomSqlValidationHandler() {}

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> in case the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
      throws  QueryJBuildException
    {
        new CustomSqlValidationChain().process(parameters);

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"class\": \"" + CustomSqlValidationHandler.class.getSimpleName()
            + ", \"package\": \"org.acmsl.queryj.customsql.handlers\" }";
    }
}
