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
 * Filename: AntTablesElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information regarding the tables to be managed by
 *              QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.ant.AntTableElement;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.jetbrains.annotations.Nullable;

/**
 * Contains all information regarding the tables to be managed by
 * QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class AntTablesElement
    implements  DynamicConfigurator
{
    /**
     * The table collection.
     */
    private Collection m__cTables;

    /**
     * Creates an empty "tables" element.
     */
    public AntTablesElement()
    {
        inmutableSetTables(new ArrayList());
    }

    /**
     * Specifies the table collection.
     * @param tables the collection
     */
    private void inmutableSetTables(Collection tables)
    {
        m__cTables = tables;
    }

    /**
     * Specifies the table collection.
     * @param tables the collection
     */
    private void setTables(Collection tables)
    {
        inmutableSetTables(tables);
    }

    /**
     * Retrieves the table collection.
     * @return such collection.
     */
    public Collection getTables()
    {
        return m__cTables;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(String name, String value)
    {
        throw
            new BuildException(
                "No dynamic attributes are supported (" + name + "=" + value + ")");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     * @throws BuildException if the element is not supported.
     */
    @Nullable
    public Object createDynamicElement(String name)
    {
        @Nullable AntTableElement result = null;

        if  ("table".equals(name)) 
        {
            result = new AntTableElement();

            Collection t_cTables = getTables();

            if  (t_cTables == null)
            {
                t_cTables = new ArrayList();
                setTables(t_cTables);
            }

            t_cTables.add(result);
        }
        else 
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
