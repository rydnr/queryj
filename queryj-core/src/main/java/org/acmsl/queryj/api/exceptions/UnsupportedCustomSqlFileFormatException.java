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
 * Filename: UnsupportedCustomSqlFileFormatException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents an error when specifying an invalid custom SQL file
 *              format.
 *
 * Date: 6/14/13
 * Time: 7:08 AM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents an error when specifying an invalid custom SQL file format.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/14
 */
@ThreadSafe
public class UnsupportedCustomSqlFileFormatException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -5216717548084486278L;

    /**
     * Creates an instance with given context.
     * @param model the model.
     */
    public UnsupportedCustomSqlFileFormatException(@NotNull final String model)
    {
        super("unsupported.custom-sql.file.format", new Object[] { model });
    }
}
