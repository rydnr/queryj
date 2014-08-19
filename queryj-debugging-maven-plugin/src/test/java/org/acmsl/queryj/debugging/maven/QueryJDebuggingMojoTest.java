/*
                        QueryJ Debugging Maven Plugin

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
 * Filename: QueryJDebuggingMojoTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for QueryJDebuggingMojo.
 *
 * Date: 2014/08/19
 * Time: 08:44
 *
 */
package org.acmsl.queryj.debugging.maven;

/*
 * Importing JetBrains annotations.
 */
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.io.File;

/**
 * Tests for {@link org.acmsl.queryj.debugging.maven.QueryJDebuggingMojo}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/19 08:44
 */
@ThreadSafe
public class QueryJDebuggingMojoTest
    extends AbstractMojoTestCase
{
    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
        throws Exception
    {
        // required for mojo lookups to work
        super.setUp();
    }

    /**
     * @throws Exception
     */
    public void testMojoGoal()
        throws Exception
    {
        @NotNull final File testPom = new File(getBasedir(), "src/test/resources/default-test.xml" );

        @NotNull final QueryJDebuggingMojo mojo = (QueryJDebuggingMojo) lookupMojo("queryj-debugging", testPom);

        assertNotNull(mojo);
    }
}
