/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
 * Description: Contains all information inside a "field" XML element in Ant
 *              scripts, under QueryJ task.
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

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains all information inside a "field" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class AntFieldElement
    implements  DynamicConfigurator
{
    /**
     * The field name.
     */
    private String m__strName;

    /**
     * The field type.
     */
    private String m__strType;

    /**
     * The field pk nature.
     */
    private String m__strPk;

    /**
     * The field pk nature.
     */
    private boolean m__bPk = false;

    /**
     * The table name.
     */
    private String m__strTableName;

    /**
     * The keyword.
     */
    private String m__strKeyword;

    /**
     * The field fk collection.
     */
    private Collection m__cFieldFks;

    /**
     * Specifies the field name.
     * @param name the field name.
     */
    protected void setName(String name)
    {
        m__strName = name;
    }

    /**
     * Retrieves the field name.
     * @return such name.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the field type.
     * @param type the field type.
     */
    protected void setType(String type)
    {
        m__strType = type;
    }

    /**
     * Retrieves the field type.
     * @return such name.
     */
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies if the field is a primary key.
     * @param pk such information.
     */
    public void setPk(String pk)
    {
        m__strPk = pk;

        setIsPk(
            (   (pk != null)
             && (   (pk.trim().toLowerCase().equals("yes")
                 || (pk.trim().toLowerCase().equals("true"))))));
    }

    /**
     * Retrieves if the field is a primary key.
     * @return such information.
     */
    public String getPk()
    {
        return m__strPk;
    }

    /**
     * Specifies if the field is a primary key.
     * @param pk such information.
     */
    protected void setIsPk(boolean pk)
    {
        m__bPk = pk;
    }

    /**
     * Retrieves if the field is a primary key.
     * @return such information.
     */
    public boolean isPk()
    {
        return m__bPk;
    }

    /**
     * Specifies the table name.
     * @param name the table name.
     */
    protected void setTableName(String name)
    {
        m__strTableName = name;
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    public String getTableName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the field's keyword.
     * @param keyword the keyword.
     */
    protected void setKeyword(String keyword)
    {
        m__strKeyword = keyword;
    }

    /**
     * Retrieves the field's keyword.
     * @return such keyword.
     */
    public String getKeyword()
    {
        return m__strKeyword;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    private void inmutableSetFieldFks(Collection fieldFks)
    {
        m__cFieldFks = fieldFks;
    }

    /**
     * Specifies the field fk collection.
     * @param fieldFks the collection
     */
    private void setFieldFks(Collection fieldFks)
    {
        inmutableSetFieldFks(fieldFks);
    }

    /**
     * Retrieves the field fk collection.
     * @return such collection.
     */
    public Collection getFieldFks()
    {
        return m__cFieldFks;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(String name, String value)
    {
        if  ("name".equals(name))
        {
            setName(value);
        }
        else if  ("type".equals(name))
        {
            setType(value);
        }
        else if  ("pk".equals(name))
        {
            setPk(value);
        }
        else if  ("table-name".equals(name))
        {
            setTableName(value);
        }
        else if  ("keyword".equals(name))
        {
            setKeyword(value);
        }
        else 
        {
            throw
                new BuildException("Attribute " + name + " is not supported");
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
        AntFieldFkElement result = null;

        if  ("fk".equals(name)) 
        {
            result = new AntFieldFkElement();

            Collection t_cFieldFks = getFieldFks();

            if  (t_cFieldFks == null)
            {
                t_cFieldFks = new ArrayList();
                setFieldFks(t_cFieldFks);
            }

            t_cFieldFks.add(result);
        }
        else 
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
