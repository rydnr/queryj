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
 * Filename: QueryJDebuggingMojo.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Maven Plugin for running QueryJ in debug mode.
 *
 * Date: 2014/08/19
 * Time: 08:54
 *
 */
package org.acmsl.queryj.debugging.maven;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.tools.maven.QueryJMojo;

/*
 * Importing Maven Plugin classes.
 */
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Maven Plugin for running QueryJ in debug mode.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/19 08:54
 */
@SuppressWarnings("unused")
@ThreadSafe
@Mojo( name = "queryj-debugging", defaultPhase = LifecyclePhase.TEST, threadSafe = true, executionStrategy = "once-per-session")
public class QueryJDebuggingMojo
    extends QueryJMojo
{
    /**
     * Runs QueryJ in debug mode.
     * @throws MojoExecutionException if the execution fails.
     */
    @Override
    public void execute()
        throws MojoExecutionException
    {

    }
}
