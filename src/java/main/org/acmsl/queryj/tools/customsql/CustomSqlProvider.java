/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
import org.acmsl.queryj.tools.customsql.ConnectionFlagsElement;
import org.acmsl.queryj.tools.customsql.ConnectionFlagsRefElement;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.ResultSetFlagsElement;
import org.acmsl.queryj.tools.customsql.ResultSetFlagsRefElement;
import org.acmsl.queryj.tools.customsql.StatementFlagsElement;
import org.acmsl.queryj.tools.customsql.StatementFlagsRefElement;

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
    public ParameterElement resolveReference(
        final ParameterRefElement reference);

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     */
    public ResultElement resolveReference(
        final ResultRefElement reference);

    /**
     * Resolves the property reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public PropertyElement resolveReference(
        final PropertyRefElement reference);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public ConnectionFlagsElement resolveReference(
        final ConnectionFlagsRefElement reference);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public StatementFlagsElement resolveReference(
        final StatementFlagsRefElement reference);

    /**
     * Resolves the statement-flags reference.
     * @param reference such reference.
     * @return the referenced property.
     */
    public ResultSetFlagsElement resolveReference(
        final ResultSetFlagsRefElement reference);
}

