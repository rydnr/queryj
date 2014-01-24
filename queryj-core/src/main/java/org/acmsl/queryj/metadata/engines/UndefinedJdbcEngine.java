/*
                        QueryJ-Core

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
 * Filename: UndefinedJdbcEngine.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents undefined JDBC engines.
 *
 * Date: 2014/01/24
 * Time: 08:31
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents undefined JDBC engines.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/01/24 08:31
 */
@ThreadSafe
public class UndefinedJdbcEngine
    extends AbstractEngine<String>
{
    /**
     * Creates an undefined JDBC engine.
     * @param name the name.
     * @param version the version.
     * @param lobHandling whether it requires custom LOB handling.
     * @param supportsSequences whether it supports sequences.
     */
    public UndefinedJdbcEngine(
        @NotNull final String name,
        @NotNull final String version,
        final boolean lobHandling,
        final boolean supportsSequences)
    {
        super(name, version, lobHandling, supportsSequences);
    }

    /**
     * Creates an undefined JDBC engine.
     * @param name the name.
     * @param version the version.
     */
    public UndefinedJdbcEngine(@NotNull final String name, @NotNull final String version)
    {
        this(name, version, false, false);
    }
}
