//;-*- mode: java -*-
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
 * Filename: AntFieldFkElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information inside a "field" XML element in Ant scripts,
 *              under QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Contains all information inside a "fk" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class AntFieldFkElement
    implements  DynamicConfigurator
{
    /**
     * The table name.
     */
    private String m__strTable;

    /**
     * The fk field.
     */
    private String m__strField;

    /**
     * Specifies the table name.
     * @param table the table name.
     */
    protected void setTable(@NotNull final String table)
    {
        m__strTable = table;
    }

    /**
     * Retrieves the table name.
     * @return such table.
     */
    @Nullable
    public String getTable()
    {
        return m__strTable;
    }

    /**
     * Specifies the fk field.
     * @param field the field.
     */
    protected void setField(@NotNull final String field)
    {
        m__strField = field;
    }

    /**
     * Retrieves the fk field.
     * @return such field.
     */
    @Nullable
    public String getField()
    {
        return m__strField;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    @Override
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        if  (AntTablesElement.TABLE.equals(name))
        {
            setTable(value);
        }
        else if  (AntExternallyManagedFieldsElement.FIELD_LITERAL.equals(name))
        {
            setField(value);
        }
        else 
        {
            throw new BuildException(AntFieldElement.ATTRIBUTE_LITERAL + name + "is not supported");
        }
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     */
    @NotNull
    @Override
    public Object createDynamicElement(@NotNull final String name)
    {
        throw
            new BuildException(
                "Nested elements inside <fk> are not supported");
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'AntFieldFkElement', 'field': '" + m__strField + "', 'table': '" + m__strTable + "' }";
    }
}
