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
 * Filename: CannotFindPlaceholderImplementationException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the exceptional situation when the placeholder implementation is missing,
 *              which is a bug in the way QueryJ placeholder module is built.
 *
 * Date: 6/11/13
 * Time: 7:36 PM
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
 * Represents the exceptional situation when the placeholder
 * implementation is missing, which is a bug in the way QueryJ placeholder module is built.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/11
 */
@ThreadSafe
public class CannotFindPlaceholderImplementationException
    extends QueryJBuildException
{
    private static final long serialVersionUID = -3428381021481724858L;

    /**
     * Creates an instance for given class.
     * @param contextClassName the class name of the context.
     */
    public CannotFindPlaceholderImplementationException(@NotNull final String contextClassName)
    {
        super("null.placeholder.implementation", new Object[] { contextClassName });
    }

    /**
     * Creates an instance for given class.
     * @param factoryClass the factory class.
     */
    public CannotFindPlaceholderImplementationException(@NotNull final Class<?> factoryClass)
    {
        super("cannot.find.placeholder.implementation", new Object[] { factoryClass.getName() } );
    }
}
