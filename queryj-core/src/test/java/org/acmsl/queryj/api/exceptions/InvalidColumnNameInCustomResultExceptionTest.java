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
 * Filename: InvalidColumnNameInCustomResultExceptionTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for InvalidColumnNameInCustomResultException.
 *
 * Date: 2014/03/18
 * Time: 11:07
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Tests for {@link InvalidColumnNameInCustomResultException}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/18 11:07
 */
@RunWith(JUnit4.class)
public class InvalidColumnNameInCustomResultExceptionTest
{
    @Test
    public void the_message_is_internationalized_for_implicit_results()
        throws MissingResourceException
    {
        @NotNull final Sql<String> t_Sql =
            new SqlElement<>(
                "id",
                "dao",
                "name",
                String.class.getSimpleName(),
                Cardinality.SINGLE,
                "all",
                true,
                false,
                "description");

        @NotNull final Property<String> t_Property =
            new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false);

        @NotNull final InvalidColumnNameInCustomResultException instance =
            new InvalidColumnNameInCustomResultException(t_Property, t_Sql, null, new RuntimeException("fake"));

        for (@NotNull final Locale t_Locale : Arrays.asList(new Locale("en"), new Locale("es")))
        {
            // throws a MissingResourceException if the key is not declared.
            instance.getMessage(t_Locale);
        }
    }

    @Test
    public void the_message_is_internationalized_for_explicit_results()
        throws MissingResourceException
    {
        @NotNull final Sql<String> t_Sql =
            new SqlElement<>(
                "id",
                "dao",
                "name",
                String.class.getSimpleName(),
                Cardinality.SINGLE,
                "all",
                true,
                false,
                "description");

        @NotNull final Property<String> t_Property =
            new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false);

        @NotNull final Result<String> t_Result = new ResultElement<>("result1", "VoClassName");

        @NotNull final InvalidColumnNameInCustomResultException instance =
            new InvalidColumnNameInCustomResultException(t_Property, t_Sql, t_Result, new RuntimeException("fake"));

        for (@NotNull final Locale t_Locale : Arrays.asList(new Locale("en"), new Locale("es")))
        {
            // throws a MissingResourceException if the key is not declared.
            instance.getMessage(t_Locale);
        }
    }
}
