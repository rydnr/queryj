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
 * Filename: Sha256NotSupportedException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the situation in which the SHA-256 algorithm is not available in this system.
 *              It's used to generate hashes of templates.
 *
 * Date: 6/11/13
 * Time: 7:43 PM
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
 * Represents the situation in which the SHA-256 algorithm is not available in this system.
 * It's used to generate hashes of templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/11
 */
@ThreadSafe
public class Sha256NotSupportedException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 8162425885862823886L;

    /**
     * Builds a exception wrapping the underlying cause.
     * @param cause the cause.
     */
    public Sha256NotSupportedException(@NotNull final Throwable cause)
    {
        super("sha256.not.supported", new Object[0], cause);
    }
}
