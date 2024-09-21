/*
                        QueryJ Debugging Ant Task

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
 * Filename: QueryJDebuggingTask.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Ant task to run QueryJ in debug mode.
 *
 * Date: 2014/08/20
 * Time: 10:30
 *
 */
package org.acmsl.queryj.debugging.ant;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing QueryJ Ant Task classes.
 */
import org.acmsl.queryj.tools.ant.QueryJTask;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Ant task to run QueryJ in debug mode.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/20 10:30
 */
@SuppressWarnings("unused")
@ThreadSafe
public class QueryJDebuggingTask
    extends QueryJTask
{
    /**
     * Creates a new task.
     * @param log the {@link Log} to use.
     */
    public QueryJDebuggingTask(@NotNull final Log log)
    {
        super(log);
    }

    /**
     * Runs QueryJ in debug mode.
     * @param command the command.
     * @throws BuildException if the process fails.
     */
    @Override
    protected void execute(@NotNull final QueryJCommand command)
        throws BuildException
    {
        super.execute(command);
    }
}
