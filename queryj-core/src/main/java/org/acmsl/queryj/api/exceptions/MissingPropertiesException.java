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
 * Filename: MissingPropertiesException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Thrown whenever the list of properties in a per-custom result
 *              context is lost somehow at runtime.
 *
 * Date: 2014/04/18
 * Time: 14:06
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Thrown whenever the list of {@link org.acmsl.queryj.customsql.Property properties} in a
 * per-custom result context is lost somehow at runtime.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/18 14:06
 */
@ThreadSafe
public class MissingPropertiesException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 1839016219904167370L;

    /**
     * Creates a new instance.
     */
    public MissingPropertiesException()
    {
        super("CustomResultProperties.not.available");
    }
}
