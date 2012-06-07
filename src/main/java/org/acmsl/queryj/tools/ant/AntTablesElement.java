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
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
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
    private List<AntTableElement> m__lTables;

    /**
     * Creates an empty "tables" element.
     */
    public AntTablesElement()
    {
        immutableSetTables(new ArrayList<AntTableElement>());
    }

    /**
     * Specifies the table collection.
     * @param tables the collection
     */
    private void immutableSetTables(@NotNull final List<AntTableElement> tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the table collection.
     * @param tables the collection.
     */
    private void setTables(@NotNull final List<AntTableElement> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the table collection.
     * @return such collection.
     */
    public List<AntTableElement> getTables()
    {
        return m__lTables;
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
        @Nullable AntTableElement result;

        if  ("table".equals(name)) 
        {
            result = new AntTableElement();

            List<AntTableElement> t_lTables = getTables();

            if  (t_lTables == null)
            {
                t_lTables = new ArrayList<AntTableElement>();
                setTables(t_lTables);
            }

            t_lTables.add(result);
        }
        else 
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
