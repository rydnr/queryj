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
 * Filename: GlobalValidationEnabledHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests GlobalValidationEnabledHandler.
 *
 * Date: 2014/03/14
 * Time: 18:34
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;

/**
 * Tests {@link GlobalValidationEnabledHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/14 18:34
 */
@RunWith(JUnit4.class)
public class GlobalValidationEnabledHandlerTest
{
    /**
     * Tests the validation is enabled if the disable_custom_sql_validation flag is disabled.
     * @throws QueryJBuildException
     */
    @Test
    public void global_validation_enabled_passes_through()
        throws QueryJBuildException
    {
        @NotNull final GlobalValidationEnabledHandler instance =
            new GlobalValidationEnabledHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        parameters.setSetting(ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION, false);

        Assert.assertFalse(instance.handle(parameters));
    }

    /**
     * Tests the validation is disabled if the disable_custom_sql_validation flag is enabled.
     * @throws QueryJBuildException
     */
    @Test
    public void global_validation_disabled_prevents_validation()
        throws QueryJBuildException
    {
        @NotNull final GlobalValidationEnabledHandler instance =
            new GlobalValidationEnabledHandler();

        @NotNull final QueryJCommand parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        parameters.setSetting(ParameterValidationHandler.DISABLE_CUSTOM_SQL_VALIDATION, true);

        Assert.assertTrue(instance.handle(parameters));
    }
}
