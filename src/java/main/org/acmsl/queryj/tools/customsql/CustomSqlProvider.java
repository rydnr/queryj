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
 * Description: Defines the common interfaces of all providers of
 *              custom sql operations.
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
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Defines the common interfaces of all providers of
 * custom sql operations.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public interface CustomSqlProvider
{
    /**
     * Retrieves the custom sql element collection.
     * return such collection.
     */
    public Collection getCollection();

    /**
     * Resolves the parameter reference.
     * @param reference such reference.
     * @return the referenced parameter.
     * @precondition reference != null
     */
    public ParameterElement resolveReference(
        final ParameterRefElement reference);

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     * @precondition reference != null
     */
    public ResultElement resolveReference(
        final ResultRefElement reference);

    /**
     * Resolves the property reference.
     * @param reference such reference.
     * @return the referenced property.
     * @precondition reference != null
     */
    public PropertyElement resolveReference(
        final PropertyRefElement reference);
}

