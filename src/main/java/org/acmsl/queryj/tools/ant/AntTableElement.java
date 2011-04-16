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
 * Filename: AntTableElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information inside a "table" XML element in Ant scripts,
 *              under QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.ant.AntFieldElement;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains all information inside a "table" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class AntTableElement
    implements  DynamicConfigurator
{
    /**
     * The table name.
     * @parameter property="name"
     */
    private String m__strTableName;

    /**
     * The field collection.
     * @parameter property="fields"
     */
    private Collection m__cFields;

    /**
     * Specifies the table name.
     * @param name the table name.
     */
    protected final void immutableSetName(final String name)
    {
        m__strTableName = name;
    }

    /**
     * Specifies the table name.
     * @param name the table name.
     */
    public void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    public String getName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    protected final void immutableSetFields(final Collection fields)
    {
        m__cFields = fields;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    public void setFields(final Collection fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Retrieves the field collection.
     * @return such collection.
     */
    public Collection getFields()
    {
        return m__cFields;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(final String name, final String value)
    {
        if  ("name".equals(name))
        {
            setName(value);
        }
        else 
        {
            throw new BuildException("Attribute are supported");
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
        AntFieldElement result = null;

        if  ("field".equals(name)) 
        {
            result = new AntFieldElement();

            Collection t_cFields = getFields();

            if  (t_cFields == null)
            {
                t_cFields = new ArrayList();
                setFields(t_cFields);
            }

            t_cFields.add(result);
        }
        else 
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
