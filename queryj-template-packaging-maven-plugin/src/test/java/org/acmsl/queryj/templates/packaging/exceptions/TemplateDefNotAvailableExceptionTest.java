/*
                        QueryJ Template Packaging Maven Plugin

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
 * Filename: TemplateDefNotAvailableExceptionTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for TemplateDefNotAvailableException.
 *
 * Date: 2014/03/30
 * Time: 22:37
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit classes.
 */
import org.junit.Test;

/*
 * Importing JDK classes.
 */
import java.util.Arrays;
import java.util.Locale;

/**
 * Tests for {@link TemplateDefNotAvailableException}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 22:37
 */
@ThreadSafe
public class TemplateDefNotAvailableExceptionTest
{
    /**
     * Tests the message key is defined for Spanish and English.
     */
    @Test
    public void exception_message_is_defined_in_Spanish_and_English()
    {
        @NotNull final TemplateDefNotAvailableException instance = new TemplateDefNotAvailableException();

        for (@NotNull final Locale t_Locale : Arrays.asList(new Locale("en"), new Locale("es")))
        {
            // throws a MissingResourceException if the key is not declared.
            instance.getMessage(t_Locale);
        }
    }
}
