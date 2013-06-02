/*
                        QueryJ

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
 * Filename: CustomBaseValueObjectTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom BaseValueObject templates.
 *
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate custom BaseValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomBaseValueObjectTemplateGenerator
    extends AbstractTemplateGenerator<CustomBaseValueObjectTemplate, BasePerCustomResultTemplateContext>
    implements BasePerCustomResultTemplateGenerator<CustomBaseValueObjectTemplate, BasePerCustomResultTemplateContext>
{
    /**
     * Creates a new {@link CustomBaseValueObjectTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads to use.
     */
    public CustomBaseValueObjectTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String retrieveTemplateFileName(@NotNull final BasePerCustomResultTemplateContext context)
    {
        @NotNull String result;

        @Nullable final String t_strClassValue = context.getResult().getClassValue();

        if (t_strClassValue != null)
        {
            result =
                  "Abstract"
                + extractClassName(t_strClassValue)
                + "ValueObject.java";
        }
        else
        {
            result = "Error_in_" + context.getResult().getId();
        }

        return result;
    }

    /**
     * Extracts the class name.
     * @param classValue the class value.
     */
    @NotNull
    protected String extractClassName(@NotNull final String classValue)
    {
        return extractClassName(classValue, ValueObjectUtils.getInstance());
    }

    /**
     * Extracts the class name.
     * @param classValue the class value.
     */
    @NotNull
    protected String extractClassName(
        @NotNull final String classValue, @NotNull final ValueObjectUtils valueObjectUtils)
    {
        return valueObjectUtils.extractClassName(classValue);
    }
}
