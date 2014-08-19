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
 * Filename: Field.java
 *
 * Author: Jose Juan
 *
 * Description: Models a field.
 */
package org.acmsl.queryj.tools.maven;

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
 * Models a field
 * @author <a href="mailto:jose.juan@ventura24.es">Jose Juan</a>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class Field
{
    /**
     * The name.
     * @parameter property="name"
     */
    private String m__strName;
    
    /**
     * The type.
     * @parameter property="type"
     */
    private String m__strType;
    
    /**
     * The primary key.
     * @parameter property="pk"
     */
    private String m__strPk;

    /**
     * Specifies the name.
     * @param name such name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name such name.
     */
    public void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Returns the name.
     * @return such value.
     */
    @NotNull
    protected final String immutableGetName()
    {
        return m__strName;
    }

    /**
     * Returns the name.
     * @return such value.
     */
    @NotNull
    public String getName()
    {
        return immutableGetName();
    }

    /**
     * Specifies the type.
     * @param type such type.
     */
    protected final void immutableSetType(@NotNull final String type)
    {
        m__strType = type;
    }

    /**
     * Specifies the type.
     * @param type such type.
     */
    public void setType(@NotNull final String type)
    {
        immutableSetType(type);
    }

    /**
     * Returns the type.
     * @return such value.
     */
    @NotNull
    protected final String immutableGetType()
    {
        return m__strType;
    }
    
    /**
     * Returns the type.
     * @return such value.
     */
    @NotNull
    public String getType()
    {
        return immutableGetType();
    }
    
    /**
     * Specifies the pk.
     * @param pk such pk.
     */
    protected final void immutableSetPk(@NotNull final String pk)
    {
        m__strPk = pk;
    }

    /**
     * Specifies the pk.
     * @param pk such pk.
     */
    @SuppressWarnings("unused")
    public void setPk(@NotNull final String pk)
    {
        immutableSetPk(pk);
    }

    /**
     * Returns the primary key.
     * @return such value.
     */
    @Nullable
    protected final String immutableGetPk() 
    {
        return m__strPk;
    }
    
    /**
     * Returns the primary key.
     * @return such value.
     */
    @Nullable
    public String getPk() 
    {
        return immutableGetPk();
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
                getType(),
                getPk());
    }

    /**
     * Builds a formatted string with the field information.
     * @param className the class name.
     * @param name the field name.
     * @param type the field type.
     * @param pk whether it's part of the primary key or not.
     * @return the formatted text.
     */
    @NotNull
    protected String toString(
        final String className,
        final String name,
        final String type,
        final String pk)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        result.append(" { \"class\": \"");
        result.append(className);
        result.append("\", \"name\" : \"");
        result.append(name);
        result.append("\", \"type\" : \"");
        result.append(type);
        result.append("\", \"pk\" : \"");
        result.append(pk);
        result.append("\" }");

        return result.toString();
    }
}
