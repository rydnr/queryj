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
 * Filename: GrammarBundleDoesNotExistException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when the grammar folder does not exist.
 *
 * Date: 6/14/13
 * Time: 6:48 AM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.io.File;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when the grammar folder does not exist.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/14
 */
@ThreadSafe
public class GrammarBundleDoesNotExistException
    extends QueryJBuildException
{
    private static final long serialVersionUID = 3846509165907903950L;

    /**
     * Creates an instance with given context.
     * @param bundleName the bundle name.
     * @param grammarFolder the grammar folder.
     */
    public GrammarBundleDoesNotExistException(
        @Nullable final String bundleName, @NotNull final File grammarFolder)
    {
        super("grammar-bundle.does.not.exist", new Object[] { bundleName, grammarFolder.getAbsolutePath() });
    }
}
