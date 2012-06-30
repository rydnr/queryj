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
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some Apache Commons-Lang classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.queryj.metadata.DecorationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK clases.
 */
import java.util.Locale;

/**
 * Decorates String objects with some alternate representation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
public class DecoratedString
{
    /**
     * The actual value.
     */
    @NotNull
    private String value;

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
        this.value = value;
    }

    /**
     * Specifies the value.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setValue(@NotNull final String value)
    {
        this.value = value;
    }

    /**
     * Retrieves the value.
     * @return such value.
     */
    @NotNull
    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return getValue();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.value).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final DecoratedString other = (DecoratedString) obj;
        return new EqualsBuilder().append(this.value, other.value).isEquals();
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
}
