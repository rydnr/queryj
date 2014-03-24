/*
                        QueryJ Maven

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
 * Filename: Table.java
 *
 * Author: Jose Juan
 *
 * Description: Models a table.
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing some JDK classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models a table.
 * @author <a href="mailto:jose.juan@ventura24.es">Jose Juan</a>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class Table
{
    /**
     * The name.
     * @parameter property="name"
     */
    private String m__strName;
    
    /**
     * The list of fields.
     * @parameter property="fields"
     */
    private List<Field> m__aFields;
    
    /**
     * Specifies the name.
     * @param name the table name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the table name.
     */
    public void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Returns the name.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetName()
    {
        return m__strName;
    }

    /**
     * Returns the name.
     * @return such value.
     */
    @Nullable
    public String getName()
    {
        return immutableGetName();
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    protected final void immutableSetFields(@NotNull final List<Field> fields)
    {
        m__aFields = fields;
    }

    /**
     * Specifies the fields.
     * @param fields the fields.
     */
    @SuppressWarnings("unused")
    public void setFields(@NotNull final List<Field> fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Returns the fields.
     * @return such values.
     */
    @Nullable
    protected final List<Field> immutableGetFields()
    {
        return m__aFields;
    }

    /**
     * Returns the fields.
     * @return such values.
     */
    @Nullable
    public List<Field> getFields()
    {
        return immutableGetFields();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            toString(
                getClass().getName(),
                getName(),
                getFields());
    }

    /**
     * Builds a formatted string with the information about
     * a table.
     * @param className the class name.
     * @param name the table name.
     * @param fields the table fields.
     * @return the formatted text.
     */
    protected String toString(
        @NotNull final String className,
        @Nullable final String name,
        @Nullable final List<Field> fields)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        result.append("{ class-name : \"");
        result.append(className);
        result.append("\", name : \"");
        result.append(name);
        result.append("\"");

        if (fields != null)
        {
            result.append(", {");
            boolean firstTime = true;

            for (@Nullable final Field field : fields)
            {
                if (!firstTime)
                {
                    result.append(", ");
                }
                firstTime = false;
                result.append(field);
            }
            result.append("}");
        }

        result.append(" }");

        return result.toString();
    }
}
