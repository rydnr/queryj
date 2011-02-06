//;-*- mode: java -*-
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

/**
 * Contains all information inside a "fk" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
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
    protected void setTable(final String table)
    {
        m__strTable = table;
    }

    /**
     * Retrieves the table name.
     * @return such table.
     */
    public String getTable()
    {
        return m__strTable;
    }

    /**
     * Specifies the fk field.
     * @param field the field.
     */
    protected void setField(final String field)
    {
        m__strField = field;
    }

    /**
     * Retrieves the fk field.
     * @return such field.
     */
    public String getField()
    {
        return m__strField;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(final String name, final String value)
    {
        if  ("table".equals(name))
        {
            setTable(value);
        }
        else if  ("field".equals(name))
        {
            setField(value);
        }
        else 
        {
            throw new BuildException("Attribute " + name + "is not supported");
        }
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     * @throws BuildException if the element is not supported.
     */
    public Object createDynamicElement(final String name)
    {
        throw
            new BuildException(
                "Nested elements inside <fk> are not supported");
    }
}
