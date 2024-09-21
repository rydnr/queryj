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
 * Filename: MetadataManagerNotAvailableException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents internal errors dealing with violations of the
 *              precondition that MetadataManager instance is available.
 *
 * Date: 2014/03/16
 * Time: 06:43
 *
 */
package org.acmsl.queryj.tools.exceptions;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents internal errors dealing with violations of the precondition that
 * a {@link org.acmsl.queryj.metadata.MetadataManager} instance is available.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 06:43
 */
@ThreadSafe
public class MetadataManagerNotAvailableException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4173853354255815138L;

    /**
     * Creates a new instance.
     */
    public MetadataManagerNotAvailableException()
    {
        super("MetadataManager.not.available");
    }
}
