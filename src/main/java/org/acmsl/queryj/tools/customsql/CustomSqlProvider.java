//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: CustomSqlProvider.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the common interfaces of all providers of
 *              custom sql operations.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.ConnectionFlags;
import org.acmsl.queryj.tools.customsql.ConnectionFlagsRef;
import org.acmsl.queryj.tools.customsql.Parameter;
import org.acmsl.queryj.tools.customsql.ParameterRef;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.PropertyRef;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.ResultRef;
import org.acmsl.queryj.tools.customsql.ResultSetFlags;
import org.acmsl.queryj.tools.customsql.ResultSetFlagsRef;
import org.acmsl.queryj.tools.customsql.StatementFlags;
import org.acmsl.queryj.tools.customsql.StatementFlagsRef;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Defines the common interfaces of all providers of
 * custom sql operations.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
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
     */
    public Parameter resolveReference(
        final ParameterRef reference);

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     */
    public Result resolveReference(
        final ResultRef reference);

    /**
     * Resolves the property reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public Property resolveReference(
        final PropertyRef reference);

    /**
     * Adds a new property.
     * @param id the id of the property.
     * @param name the property name.
     * @param type the property type.
     */
    public void addProperty(
        final String id, final String name, final String type);

    /**
     * Adds a new result.
     * @param id the id of the result.
     * @param result the result information.
     */
    public void addResult(final String id, final Result result);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public ConnectionFlags resolveReference(
        final ConnectionFlagsRef reference);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public StatementFlags resolveReference(
        final StatementFlagsRef reference);

    /**
     * Resolves the statement-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public ResultSetFlags resolveReference(
        final ResultSetFlagsRef reference);
}

