/*
                        Queryj-Core

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
 * Filename: OracleEngine.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents Oracle engines.
 *
 * Date: 2014/01/24
 * Time: 07:20
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing QueryJ-Core classses.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.metadata.engines.AbstractEngine;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents Oracle engines.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/01/24 07:20
 */
@ThreadSafe
public class OracleEngine
    extends AbstractEngine<String>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 3126277107665888316L;

    /**
     * Creates a new engine instance.
     * @param version the version.
     */
    public OracleEngine(@NotNull final String version)
    {
        super(Literals.ORACLE, version, true, true);
    }
}
