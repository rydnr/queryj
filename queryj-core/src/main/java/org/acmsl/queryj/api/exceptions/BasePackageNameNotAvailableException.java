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
 * Filename: BasePackageNameNotAvailableException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents a situation in which the information about the base
 *              package name is required (and expected), but it's not available.
 *              A bug or violation of the preconditions/invariants.
 *
 * Date: 2014/03/28
 * Time: 18:30
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents a situation in which the information about the base package
 * name is required (and expected), but it's not available. A bug or
 * violation of the preconditions/invariants.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/28 18:30
 */
@ThreadSafe
public class BasePackageNameNotAvailableException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 6952674169022923793L;

    /**
     * Creates a new instance.
     */
    public BasePackageNameNotAvailableException()
    {
        super("BasePackageName.not.available");
    }
}
