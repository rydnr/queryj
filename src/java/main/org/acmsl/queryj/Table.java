/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents relational tables.
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
package org.acmsl.queryj;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.TableAlias;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Represents relational tables.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class Table
{
    /**
     * The table name.
     */
    private String m__strName;

    /**
     * The alias name.
     */
    private String m__strTableAliasName;

    /**
     * The table alias reference.
     */
    private WeakReference m__TableAliasReference;

    /**
     * Builds a table using given information.
     * @param name the name.
     * @param alias the alias.
     */
    protected Table(String name, String alias)
    {
        inmutableSetName(name);

        inmutableSetTableAliasName(alias);
    }

    /**
     * Builds a table using given information.
     * @param name the name.
     */
    protected Table(String name)
    {
        this(name, null);
    }

    /**
     * Retrieves <code>all</code> fields. It's equivalent to a star in
     * a query.
     * @return such fields.
     */
    public abstract Field[] getAll();

    /**
     * Specifies the table name.
     * @param name the name.
     */
    private void inmutableSetName(String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the table name.
     * @param name the name.
     */
    protected void setName(String name)
    {
        inmutableSetName(name);
    }

    /**
     * Retrieves the table name.
     * @return such reference.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the table alias.
     * @param alias the alias.
     */
    private void inmutableSetTableAliasName(String alias)
    {
        m__strTableAliasName = alias;
    }

    /**
     * Specifies the table alias name.
     * @param alias the alias.
     */
    protected void setTableAliasName(String alias)
    {
        inmutableSetTableAliasName(alias);
    }

    /**
     * Retrieves the table alias name.
     * @return such reference.
     */
    protected String getTableAliasName()
    {
        return m__strTableAliasName;
    }

    /**
     * Specifies the table alias reference.
     * @param reference the reference.
     */
    protected void setTableAliasReference(WeakReference reference)
    {
        m__TableAliasReference = reference;
    }

    /**
     * Retrieves the table alias reference.
     * @return such reference.
     */
    protected WeakReference getTableAliasReference()
    {
        return m__TableAliasReference;
    }

    /**
     * Retrieves the table alias.
     * @return such instance.
     */
    public TableAlias getTableAlias()
    {
        TableAlias result = null;

        WeakReference t_TableAliasReference =
            getTableAliasReference();

        String t_strTableAliasName = getTableAliasName();

        if  (t_TableAliasReference != null)
        {
            result = (TableAlias) t_TableAliasReference.get();
        }

        if  (result == null)
        {
            if  (t_strTableAliasName != null)
            {
                result = new TableAlias(t_strTableAliasName, this) {};
                setTableAliasReference(new WeakReference(result));
            }
        }

        return result;
    }

    /**
     * Outputs a text version of the table.
     * @return such text.
     */
    public String toString()
    {
        StringBuffer result = new StringBuffer();

        TableAlias t_TableAlias = getTableAlias();

        if  (t_TableAlias != null) 
        {
            result.append(t_TableAlias);
        }
        else 
        {
            result.append(getName());
        }

        return result.toString();
    }
}
