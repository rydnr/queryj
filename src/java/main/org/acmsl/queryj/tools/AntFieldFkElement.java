/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information inside a "field" XML element in Ant scripts,
 *              under QueryJ task.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

/**
 * Contains all information inside a "fk" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
    protected void setTable(String table)
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
    protected void setField(String field)
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
    public void setDynamicAttribute(String name, String value)
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
    public Object createDynamicElement(String name)
    {
        throw new BuildException("Nested elements inside <fk> are not supported");
    }
}
