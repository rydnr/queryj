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
 * Filename: DecorationUtils.java
 *
 * Author: Jose San Leandro Armend&aacute;riz
 *
 * Description: Provides some useful decoration methods.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing jetbrains annotations.
 */
import org.acmsl.queryj.SingularPluralFormConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful decoration methods.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class DecorationUtils
    implements  Singleton,
                Utils
{
    /**
     * An empty cached String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DecorationUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DecorationUtils SINGLETON = new DecorationUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DecorationUtils() {}

    /**
     * Retrieves a <code>DecorationUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static DecorationUtils getInstance()
    {
        return DecorationUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    public String upperCase(@NotNull final String value)
    {
        return value.toUpperCase(Locale.US);
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    public String lowerCase(@NotNull final String value)
    {
        return value.toLowerCase(Locale.US);
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @return the modified version of the value.
     */
    @NotNull
    public String uncapitalize(@NotNull final String value)
    {
        return uncapitalize(value, StringUtils.getInstance());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the modified version of the value.
     */
    @NotNull
    protected String uncapitalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.unCapitalizeStart(stringUtils.capitalize(value));
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return the modified version of the value.
     */
    @NotNull
    public String capitalize(@NotNull final String value)
    {
        return capitalize(value, StringUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the modified version of the value.
     */
    @NotNull
    protected String capitalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.capitalize(value.toLowerCase(Locale.US));
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @return the normalized value.
     */
    @NotNull
    public String normalizeLowercase(@NotNull final String value)
    {
        return normalize(value).toLowerCase(Locale.US);
    }
    
    /**
     * Normalizes given value, in upper case.
     * @param value the value.
     * @return the normalized value.
     */
    @NotNull
    public String normalizeUppercase(@NotNull final String value)
    {
        return normalize(value).toUpperCase(Locale.US);
    }
    
    /**
     * Normalizes given value, in upper case.
     * @param value the value.
     * @return the normalized value.
     */
    @NotNull
    public String softNormalizeUppercase(@NotNull final String value)
    {
        return softNormalize(value).toUpperCase(Locale.US);
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @return the normalized value.
     */
    @NotNull
    public String normalize(@NotNull final String value)
    {
        return normalize(value, StringUtils.getInstance());
    }
    
    /**
     * Normalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the normalized value.
     */
    @NotNull
    protected String normalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.normalize(value, '_');
    }

    /**
     * Soft-normalizes given value.
     * @param value the value.
     * @return the normalized value.
     */
    @NotNull
    public String softNormalize(@NotNull final String value)
    {
        return softNormalize(value, StringUtils.getInstance());
    }
    
    /**
     * Soft-normalizes given value.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the normalized value.
     */
    @NotNull
    protected String softNormalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.softNormalize(value);
    }

    /**
     * Splits given value into multiple lines.
     * @param value the value.
     * @return such output.
     */
    @NotNull
    public String[] split(@NotNull final String value)
    {
        return split(value, StringUtils.getInstance());
    }
    
    /**
     * Splits given value into multiple lines.
     * @param value the value.
     * @param stringUtils the <code>StringUtils</code> instance.
     * will be the one used.
     * @return such output.
     */
    @NotNull
    public String[] split(@NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.split(value);
    }

    /**
     * Surrounds given values using given separators.
     * @param values the values.
     * @param leftSeparator the left-side separator.
     * @param rightSeparator the right-side separator.
     * @return the quoted values.
     */
    @NotNull
    public String[] surround(
        @NotNull final String[] values,
        @NotNull final String leftSeparator,
        @NotNull final String rightSeparator)
    {
        return
            surround(
                values,
                leftSeparator,
                rightSeparator,
                StringUtils.getInstance());
    }

    /**
     * Surrounds given values using given separators.
     * @param values the values.
     * @param leftSeparator the left-side separator.
     * @param rightSeparator the right-side separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the quoted values.
     */
    @NotNull
    protected String[] surround(
        @NotNull final String[] values,
        @NotNull final String leftSeparator,
        @NotNull final String rightSeparator,
        @NotNull final StringUtils stringUtils)
    {
        return stringUtils.surround(values, leftSeparator, rightSeparator);
    }
    
    /**
     * Trims given values.
     * @param values the values.
     * @return the trimmed lines.
     */
    @NotNull
    public String[] trim(@NotNull final String[] values)
    {
        return trim(values, StringUtils.getInstance());
    }

    /**
     * Trims given values.
     * @param values the values.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the trimmed lines.
     */
    @NotNull
    protected String[] trim(
        @NotNull final String[] values, @NotNull final StringUtils stringUtils)
    {
        return stringUtils.trim(values);
    }

    /**
     * Escapes given char.
     * @param value the value.
     * @param charToEscape the char to escape.
     * @return the processed value.
     */
    @NotNull
    public String escape(@NotNull final String value, final char charToEscape)
    {
        return escape(value, charToEscape, StringUtils.getInstance());
    }

    /**
     * Escapes given char.
     * @param value the value.
     * @param charToEscape the char to escape.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the processed value.
     */
    @NotNull
    protected String escape(
        @NotNull final String value,
        final char charToEscape,
        @NotNull final StringUtils stringUtils)
    {
        return stringUtils.escape(value, charToEscape);
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @return the singular.
     */
    @NotNull
    public  String getSingular(@NotNull final String word)
    {
        return getSingular(word, SingularPluralFormConverter.getInstance());
    }

    /**
     * Retrieves the singular of given word.
     * @param word the word.
     * @param singularPluralFormConverter the
     * <code>SingularPluralFormConverter</code> instance.
     * @return the singular.
     */
    @NotNull
    protected String getSingular(
        @NotNull final String word,
        @NotNull final EnglishGrammarUtils singularPluralFormConverter)
    {
        return singularPluralFormConverter.getSingular(word);
    }

    /**
     * Retrieves the capitalized version of given value,
     * using several characters as predefined separators.
     * @param value the value.
     * @return the capitalized value.
     */
    @NotNull
    public String standardCapitalize(@NotNull final String value)
    {
        return standardCapitalize(value, StringUtils.getInstance());
    }

    /**
     * Retrieves the capitalized version of given value,
     * using several characters as predefined separators.
     * @param value the value.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the capitalized value.
     */
    @NotNull
    protected String standardCapitalize(
        @NotNull final String value, @NotNull final StringUtils stringUtils)
    {
        return
            stringUtils.capitalize(
                stringUtils.capitalize(
                    stringUtils.capitalize(
                        value,
                    '.'),
                '_'),
            '-');
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006157, 587765573)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if  (object instanceof DecorationUtils)
        {
            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(object))
                .isEquals();
        }

        return result;
    }
}
