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
 * Filename: GlobalValidationEnabledHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Handler to stop the validation process altogether if it's
 *              disabled globally.
 *
 * Date: 2014/03/14
 * Time: 18:39
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Handler to stop the validation process altogether if it's disabled globally.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/14 18:39
 */
@ThreadSafe
public class GlobalValidationEnabledHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return retrieveDisableCustomSqlValidation(command);
    }

    /**
     * Retrieves whether to disable custom sql validation at all or not.
     * @param settings the settings.
     * @return <code>true</code> in such case.
     */
    protected boolean retrieveDisableCustomSqlValidation(@NotNull final QueryJCommand settings)
    {
        return settings.getBooleanSetting(ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION, false);
    }
}
