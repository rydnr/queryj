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
 * Filename: MissingTemplatesExceptionTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for MissingTemplatesException.
 *
 * Date: 2014/04/15
 * Time: 18:21
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

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

/**
 * Tests for {@link MissingTemplatesException}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/15 18:21
 */
@RunWith(JUnit4.class)
public class MissingTemplatesExceptionTest
{
    /**
     * Tests the "default-template-chain-provider" exception is defined for Spanish and English.
     */
    @Test
    public void template_chain_provider_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("default-template-chain-provider");
    }

    /**
     * Tests the "per-foreign-key-templates-test" exception is defined for Spanish and English.
     */
    @Test
    public void per_foreign_key_templates_test_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("per-foreign-key-templates-test");
    }

    /**
     * Tests the "per-table-templates-feature" exception is defined for Spanish and English.
     */
    @Test
    public void per_table_templates_feature_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("per-table-templates-feature");
    }

    /**
     * Tests the "per-table-templates-test" exception is defined for Spanish and English.
     */
    @Test
    public void per_table_templates_test_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("per-table-templates-test");
    }

    /**
     * Tests the "template-build-handler" exception is defined for Spanish and English.
     */
    @Test
    public void template_build_handler_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template-build-handler");
    }

    /**
     * Tests the "template-factory" exception is defined for Spanish and English.
     */
    @Test
    public void template_factory_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template-factory");
    }

    /**
     * Tests the "template-generator" exception is defined for Spanish and English.
     */
    @Test
    public void template_generator_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template-generator");
    }

    /**
     * Tests the "template-handler-bundle" exception is defined for Spanish and English.
     */
    @Test
    public void template_handler_bundle_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template-handler-bundle");
    }

    /**
     * Tests the "template" exception is defined for Spanish and English.
     */
    @Test
    public void template_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template");
    }

    /**
     * Tests the "template-writing-handler" exception is defined for Spanish and English.
     */
    @Test
    public void template_writing_handler_message_is_defined_in_Spanish_and_English()
    {
        checkExceptionMessageIsDefinedInSpanishAndEnglish("template-writing-handler");
    }

    /**
     * Tests the message key is defined for Spanish and English.
     */
    protected void checkExceptionMessageIsDefinedInSpanishAndEnglish(@NotNull final String templateName)
    {
        @NotNull final MissingTemplatesException instance = new MissingTemplatesException(templateName);

        for (@NotNull final Locale t_Locale : Arrays.asList(new Locale("en"), new Locale("es")))
        {
            // throws a MissingResourceException if the key is not declared.
            instance.getMessage(t_Locale);
        }
    }

}
