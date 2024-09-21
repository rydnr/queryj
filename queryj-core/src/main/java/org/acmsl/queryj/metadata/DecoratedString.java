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
import org.acmsl.queryj.QueryJSettings;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates String objects with some alternate representation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
@ThreadSafe
public class DecoratedString
    implements Serializable,
               Comparable<DecoratedString>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 9213626121877319923L;

    /**
     * A regex for replacing new lines.
     */
    protected static final Pattern NEWLINE_PATTERN = Pattern.compile("\\s*\\n\\s*");

    /**
     * A regex for removing the extension.
     */
    protected static final Pattern EXT_REGEX = Pattern.compile("(\\..*)$");

    /**
     * A regex matching all non-alphanumeric characters.
     */
    protected static final Pattern NON_ALPHANUMERIC_REGEX = Pattern.compile("[^\\p{Alnum}]+");

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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(getValue()).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
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
    public DecoratedString getCapitalized()
    {
        return new DecoratedString(capitalize(getValue(), StringUtils.getInstance()));
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
        return stringUtils.capitalize(value, QueryJSettings.DEFAULT_LOCALE);
    }

    /**
     * Retrieves the "lowercased" version.
     * @return the value associated to "[placeholder].lowercased".
     */
    @NotNull
    public DecoratedString getLowercased()
    {
        return new DecoratedString(lowercase(getValue()));
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
     * Retrieves the "uppercased" version.
     * @return the value associated to "[placeholder].uppercased".
     */
    @NotNull
    public DecoratedString getUppercased()
    {
        return new DecoratedString(uppercase(getValue()));
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
    public DecoratedString getNormalized()
    {
        return new DecoratedString(normalize(getValue(), DecorationUtils.getInstance()));
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
        return decorationUtils.softNormalize(value);
    }

    /**
     * Retrieves the "uncapitalized" version.
     * @return the value associated to "[placeholder].uncapitalized".
     */
    @NotNull
    public DecoratedString getUncapitalized()
    {
        return new DecoratedString(uncapitalize(getValue(), DecorationUtils.getInstance()));
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
    public List<DecoratedString> getLines()
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
    protected List<DecoratedString> getLines(@NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        @NotNull final List<DecoratedString> result;

        @NotNull final List<String> lines = Arrays.asList(decorationUtils.split(value));

        result = new ArrayList<>(lines.size());

        for (@NotNull final String line : lines)
        {
            result.add(new DecoratedString(line));
        }

        return result;
    }

    /**
     * Retrieves the VOName format.
     * @return such value.
     */
    @NotNull
    public DecoratedString getVoName()
    {
        return new DecoratedString(capitalize(getSingular().getValue(), StringUtils.getInstance()));
    }

    /**
     * Retrieves the singular.
     * @return the value, in singular.
     */
    @NotNull
    public DecoratedString getSingular()
    {
        return new DecoratedString(getSingular(getValue(), SingularPluralFormConverter.getInstance()));
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
     * Splits the value.
     * @return the value, split in lines.
     */
    @NotNull
    public DecoratedString[] getSplit()
    {
        return split(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Splits the value.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the value, split in lines.
     */
    protected DecoratedString[] split(@NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return toDecoratedString(decorationUtils.split(value));
    }

    /**
     * Escapes the double quotes in given value.
     * @return the escaped value.
     */
    @NotNull
    public DecoratedString getEscapeDoubleQuotes()
    {
        return escape(getValue(), '"', DecorationUtils.getInstance());
    }

    /**
     * Escapes the single quotes in given value.
     * @return the escaped value.
     */
    @NotNull
    public DecoratedString getEscapeSingleQuotes()
    {
        return escape(getValue(), '\'', DecorationUtils.getInstance());
    }

    /**
     * Splits the value.
     * @param value the value.
     * @param toEscape the value to escape.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the value, replacing occurrences for its escaped variants.
     */
    protected DecoratedString escape(
        @NotNull final String value,
        final char toEscape,
        @NotNull final DecorationUtils decorationUtils)
    {
        return new DecoratedString(decorationUtils.escape(value, toEscape));
    }

    /**
     * Splits the string and surrounds it with double quotes.
     * @return the result of the split.
     */
    @NotNull
    public DecoratedString[] getSplitAndSurroundByDoubleQuotes()
    {
        return surroundBy(getValue(), "\"", DecorationUtils.getInstance());
    }

    /**
     * Splits the string and surrounds it with single quotes.
     * @return the result of the split.
     */
    @NotNull
    public DecoratedString[] getSplitAndSurroundBySingleQuotes()
    {
        return surroundBy(getValue(), "'", DecorationUtils.getInstance());
    }

    /**
     * Surrounds the string.
     * @param value the string.
     * @param delimiter the delimiter.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return the converted text.
     */
    @NotNull
    protected DecoratedString[] surroundBy(
        @NotNull final String value, final String delimiter, @NotNull final DecorationUtils decorationUtils)
    {
        return
            toDecoratedString(
                decorationUtils.surround(
                    decorationUtils.trim(
                        decorationUtils.split(
                            decorationUtils.escape(value, '\"'))),
                    "\"",
                    " \""));
    }

    /**
     * Retrieves the camel-case version of the string.
     * @return the camel-case formatted string.
     */
    @NotNull
    public DecoratedString getCamelCase()
    {
        return getCamelCase(getValue(), StringUtils.getInstance());
    }

    /**
     * Retrieves the camel-case version of the string.
     * @param value the value to convert.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the camel-case formatted string.
     */
    @NotNull
    protected DecoratedString getCamelCase(@NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        @NotNull final String[] t_astrParts = stringUtils.split(value, new String[] { "_" });

        for (@NotNull final String t_strPart : t_astrParts)
        {
            result.append(new DecoratedString(t_strPart).getLowercased().getCapitalized().getValue());
        }

        return new DecoratedString(result.toString());
    }

    /**
     * Retrieves the value, after replacing new lines with single spaces.
     * @return the value, in a single line.
     */
    @NotNull
    public DecoratedString getSingleLine()
    {
        return getSingleLine(getValue());
    }

    /**
     * Retrieves the value, after replacing new lines with single spaces.
     * @param value the original value.
     * @return the value, in a single line.
     */
    @NotNull
    protected DecoratedString getSingleLine(@NotNull final String value)
    {
        return new DecoratedString(NEWLINE_PATTERN.matcher(value).replaceAll(" "));
    }

    /**
     * Transforms given texts into an array of {@code DecoratedString}s.
     * @param values the values.
     * @return the decorated array.
     */
    @NotNull
    protected DecoratedString[] toDecoratedString(@NotNull final String[] values)
    {
        @NotNull final DecoratedString[] result;

        result = new DecoratedString[values.length];

        for (int index = 0; index < values.length; index++)
        {
            result[index] = new DecoratedString(values[index]);
        }

        return result;
    }

    /**
     * Compares this instance to given DecoratedString.
     * @param decoratedString the value to compare to.
     * @return the result of the comparison.
     */
    @Override
    public int compareTo(@Nullable final DecoratedString decoratedString)
    {
        final int result;

        if (decoratedString == null)
        {
            result = 1;
        }
        else
        {
            result = getValue().compareTo(decoratedString.getValue());
        }

        return result;
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

    /**
     * Removes the extension, if applicable.
     * @return the new DecoratedString.
     */
    @NotNull
    public DecoratedString getNoExtension()
    {
        return removeExtension(getValue());
    }

    /**
     * Removes the extension, if applicable.
     * @param value the value.
     * @return the new DecoratedString.
     */
    @NotNull
    protected DecoratedString removeExtension(@NotNull final String value)
    {
        @NotNull final String result = EXT_REGEX.matcher(value).replaceAll("");

        return new DecoratedString(result);
    }

    /**
     * Removes all non-alphanumeric characters.
     * @return the shrunk string.
     */
    @NotNull
    public DecoratedString getShrink()
    {
        return getShrink(getValue());
    }

    /**
     * Removes all non-alphanumeric characters.
     * @param value the value.
     * @return the shrunk string.
     */
    @NotNull
    protected DecoratedString getShrink(@NotNull final String value)
    {
        @NotNull final String result = NON_ALPHANUMERIC_REGEX.matcher(value).replaceAll("");

        return new DecoratedString(result);
    }

    /**
     * Checks whether the value is empty.
     * @return {@code true} in such case.
     */
    public boolean isEmpty()
    {
        return isEmpty(getValue(), StringUtils.getInstance());
    }

    /**
     * Checks whether the value is empty.
     * @param value the value.
     * @param stringUtils the {@link StringUtils} instance..
     * @return {@code true} in such case.
     */
    protected boolean isEmpty(@NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.isEmpty(value);
    }

    /**
     * Checks whether the value is the literal "true".
     * @return {@code true} in such case.
     */
    public boolean isTrue()
    {
        return isTrue(getValue());
    }

    /**
     * Checks whether the value is the literal "true".
     * @param value the value.
     * @return {@code true} in such case.
     */
    protected boolean isTrue(@NotNull final String value)
    {
        return "true".equals(value);
    }

    /**
     * Checks whether the value is the literal "true".
     * @return {@code true} in such case.
     */
    public boolean isMeansTrue()
    {
        return isTrue();
    }

    /**
     * Checks whether the value is not the literal "true".
     * @return {@code true} in such case.
     */
    public boolean isFalse()
    {
        return !isTrue();
    }
}
