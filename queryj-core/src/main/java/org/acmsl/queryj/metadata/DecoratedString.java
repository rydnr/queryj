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
 * Filename: DecoratedString.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Decorates String objects with some alternate representation.
 *
 * Date: 5/24/12
 * Time: 4:36 AM
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.queryj.SingularPluralFormConverter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Decorates String objects with some alternate representation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
@ThreadSafe
public class DecoratedString
{
    /**
     * The actual value.
     */
    @NotNull
    private String m__strValue;

    /**
     * Creates a {@link DecoratedString} with given value.
     * @param value the value.
     */
    @SuppressWarnings("unused")
    public DecoratedString(@NotNull final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Specifies the value.
     * @param value such value.
     */
    protected final void immutableSetValue(@NotNull final String value)
    {
        this.m__strValue = value;
    }

    /**
     * Specifies the value.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setValue(@NotNull final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the value.
     * @return such value.
     */
    @NotNull
    public String getValue()
    {
        return this.m__strValue;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(getValue()).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        boolean result = false;

        if (   (obj != null)
            && (getClass() == obj.getClass()))
        {
            @NotNull final DecoratedString other = (DecoratedString) obj;
            result = new EqualsBuilder().append(this.getValue(), other.getValue()).isEquals();
        }

        return result;
    }

    /**
     * Retrieves the "capitalized" version.
     * @return the value associated to "[placeholder].capitalized".
     */
    @NotNull
    public String getCapitalized()
    {
        return capitalize(getValue(), StringUtils.getInstance());
    }

    /**
     * Retrieves the "capitalized" version.
     * @param value the original value.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such version.
     */
    @NotNull
    protected String capitalize(@NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.capitalize(value);
    }

    /**
     * Retrieves the "lowercased" version.
     * @return the value associated to "[placeholder].lowercased".
     */
    @NotNull
    public String getLowercased()
    {
        return lowercase(getValue());
    }

    /**
     * Retrieves the "lowercased" version.
     * @param value the original value.
     * @return the value associated to "[placeholder].lowercased".
     */
    @NotNull
    protected String lowercase(@NotNull final String value)
    {
        return value.toLowerCase(Locale.US);
    }

    /**
     * Retrieves the "uppercased" and "normalized" version.
     * @return the value associated to "[placeholder].normalizedUppercased".
     */
    @NotNull
    public String getNormalizedUppercased()
    {
        return uppercase(getNormalized());
    }

    /**
     * Retrieves the "uppercased" version.
     * @return the value associated to "[placeholder].uppercased".
     */
    @NotNull
    public String getUppercased()
    {
        return uppercase(getValue());
    }

    /**
     * Retrieves the "uppercased" version.
     * @param value the original value.
     * @return the value associated to "[placeholder].uppercased".
     */
    @NotNull
    protected String uppercase(@NotNull final String value)
    {
        return value.toUpperCase(Locale.US);
    }

    /**
     * Retrieves the "normalized" version.
     * @return the value associated to "[placeholder].normalized".
     */
    @NotNull
    public String getNormalized()
    {
        return normalize(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the "normalized" version.
     * @param value the original value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the value associated to "[placeholder].normalized".
     */
    @NotNull
    protected String normalize(@NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }

    /**
     * Retrieves the "uncapitalized" version.
     * @return the value associated to "[placeholder].uncapitalized".
     */
    @NotNull
    public String getUncapitalized()
    {
        return uncapitalize(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the "uncapitalized" version.
     * @param value the original value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the value associated to "[placeholder].uncapitalized".
     */
    @NotNull
    protected String uncapitalize(@NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value);
    }

    /**
     * Retrieves the lines of the string.
     * @return such lines.
     */
    @NotNull
    public List<String> getLines()
    {
        return getLines(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the lines of the string.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such lines.
     */
    @NotNull
    protected List<String> getLines(@NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return Arrays.asList(decorationUtils.split(value));
    }

    /**
     * Retrieves the VOName format.
     * @return such value.
     */
    @NotNull
    public String getVoName()
    {
        return capitalize(getSingular(), StringUtils.getInstance());
    }

    /**
     * Retrieves the singular.
     * @return the value, in singular.
     */
    @NotNull
    public String getSingular()
    {
        return getSingular(getValue(), SingularPluralFormConverter.getInstance());
    }

    /**
     * Converts given value to singular.
     * @param value the value to convert.
     * @param englishGrammarUtils the {@link EnglishGrammarUtils} instance.
     * @return the converted value.
     */
    @NotNull
    protected String getSingular(
        @NotNull final String value, @NotNull final EnglishGrammarUtils englishGrammarUtils)
    {
        return englishGrammarUtils.getSingular(value);
    }

    /**
     * Retrieves the value.
     * @return such value.
     */
    @NotNull
    @Override
    public String toString()
    {
        return getValue();
    }
}
