/*
                        QueryJ Core

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
 * Filename: MissingExternallyManagedFieldTableNameException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when a externally-manage field in a
 *              pom.xml or Ant build lacks the "table name" attribute.
 *
 * Date: 6/14/13
 * Time: 6:01 AM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.Field;

/*
 *Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when a externally-manage field in a pom.xml or
 * Ant build lacks the "table name" attribute.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/14
 */
@SuppressWarnings("unused")
@ThreadSafe
public class MissingExternallyManagedFieldTableNameException
    extends QueryJBuildException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8551902929244410437L;

    /**
     * Creates an instance with given {@link Field}.
     * @param field the field.
     */
    public MissingExternallyManagedFieldTableNameException(@NotNull final Field field)
    {
        super("missing.externally-managed.field.table-name", new Object[] { field.toString() });
    }
}
