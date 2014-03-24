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

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Contains all information regarding the tables to be managed by
 * QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class AntTablesElement
    implements  DynamicConfigurator
{
    public static final String TABLE = "table";

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
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        throw
            new BuildException(
                QueryJTask.NO_DYNAMIC_ATTRIBUTES_ARE_SUPPORTED + name + "=" + value + ")");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     */
    @Nullable
    public Object createDynamicElement(@NotNull final String name)
    {
        @Nullable final AntTableElement result;

        if  (TABLE.equals(name))
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
            throw new BuildException(name + QueryJTask.ELEMENTS_ARE_NOT_SUPPORTED);
        }

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'AntTablesElement', 'tables': '" + m__lTables + "' }";
    }
}
