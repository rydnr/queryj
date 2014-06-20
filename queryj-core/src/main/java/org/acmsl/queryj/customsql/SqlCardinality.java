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
 * Filename: SqlCardinality.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the cardinality of the query.
 *
 * Date: 2014/06/21
 * Time: 10:36
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the cardinality of the query.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/21 10:36
 */
@ThreadSafe
public enum SqlCardinality
{
    /**
     * None.
     */
    NONE("none"),

    /**
     * Just one record.
     */
    SINGLE("single"),

    /**
     * Multiple results.
     */
    MULTIPLE("multiple");

    /**
     * The m__strName.
     */
    @NotNull private final String m__strName;

    /**
     * Creates a new enum value.
     * @param value the value.
     */
    SqlCardinality(/* @NotNull fails */final String value)
    {
        this.m__strName = value;
    }

    /**
     * Retrieves the m__strName.
     * @return such information.
     */
    @NotNull
    public String getName()
    {
        return this.m__strName;
    }

    /**
     * Retrieves the cardinality associated to given text.
     * @param text the text.
     * @return the cardinality instance.
     */
    @Nullable
    public static SqlCardinality fromString(@Nullable final String text)
    {
        @Nullable SqlCardinality result = null;

        if (text != null)
        {
            for (@NotNull final SqlCardinality cardinality : SqlCardinality.values())
            {
                if (text.equalsIgnoreCase(cardinality.getName()))
                {
                    result = cardinality;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"name\": \"" + this.m__strName + "\""
            + ", \"class\": \"" + SqlCardinality.class.getSimpleName() + "\""
            + ", \"package\": \"org.acmsl.queryj.customsql\""
            + " }";
    }
}