/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information regarding the declaration of
 *              externally managed fields in QueryJ task.
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
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.AntTableElement;

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

/**
 * Contains all information regarding the declaration of
 * externally managed fields in QueryJ task.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class AntExternallyManagedFieldsElement
    implements  DynamicConfigurator
{
    /**
     * The field collection.
     */
    private Collection m__cFields;

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    private void inmutableSetFields(Collection fields)
    {
        m__cFields = fields;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    private void setFields(Collection fields)
    {
        inmutableSetFields(fields);
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
    public void setDynamicAttribute(String name, String value)
    {
        throw new BuildException("Attributes are not supported");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     * @throws BuildException if the element is not supported.
     */
    public Object createDynamicElement(String name)
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
