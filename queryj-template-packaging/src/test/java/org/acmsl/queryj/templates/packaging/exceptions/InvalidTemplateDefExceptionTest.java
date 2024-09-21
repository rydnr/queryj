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
 * Filename: InvalidTemplateDefExceptionTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for InvalidTemplateDefException.
 *
 * Date: 2014/05/25
 * Time: 20:00
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.easymock.EasyMock;
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
import java.io.File;
import java.util.Arrays;
import java.util.Locale;

/**
 * Tests for {@link org.acmsl.queryj.templates.packaging.exceptions.InvalidTemplateDefException}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/25 20:00
 */
@RunWith(JUnit4.class)
public class InvalidTemplateDefExceptionTest
{
    /**
     * Tests the template def parser errors due to invalid "name" values
     * are defined for Spanish and English.
     */
    @Test
    public void template_chain_provider_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("name");
    }

    /**
     * Tests the template def parser errors due to invalid "type" values
     * are defined for Spanish and English.
     */
    @Test
    public void per_foreign_key_templates_test_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("type");
    }

    /**
     * Tests the template def parser errors due to invalid "output" values
     * are defined for Spanish and English.
     */
    @Test
    public void per_table_templates_feature_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("output");
    }

    /**
     * Tests the template def parser errors due to invalid "filenameBuilder" values
     * are defined for Spanish and English.
     */
    @Test
    public void per_table_templates_test_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("filenameBuilder");
    }

    /**
     * Tests the template def parser errors due to invalid "metadata" sections
     * are defined for Spanish and English.
     */
    @Test
    public void template_build_handler_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("metadata");
    }

    /**
     * Tests the template def parser errors due to invalid "disabled" values
     * are defined for Spanish and English.
     */
    @Test
    public void template_factory_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("disabled");
    }

    /**
     * Tests the template def parser errors due to invalid "debug" values
     * are defined for Spanish and English.
     */
    @Test
    public void template_generator_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("debug");
    }

    /**
     * Tests the message key is defined for Spanish and English.
     */
    protected void checkExceptionMessageIsDefinedInSpanishAndEnglish(@NotNull final String type)
    {
        @NotNull final File file = EasyMock.createNiceMock(File.class);
        @NotNull final Throwable cause = EasyMock.createNiceMock(Throwable.class);

        @NotNull final InvalidTemplateDefException instance = new InvalidTemplateDefException(type, file, cause);

        for (@NotNull final Locale t_Locale : Arrays.asList(new Locale("en"), new Locale("es")))
        {
            // throws a MissingResourceException if the key is not declared.
            instance.getMessage(t_Locale);
        }
    }
}
