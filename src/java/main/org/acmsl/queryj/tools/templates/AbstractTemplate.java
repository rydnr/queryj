/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents generic templates.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.templates.Template;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Represents generic templates.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AbstractTemplate
    implements  Template
{
    /**
     * The reference to the project, for logging purposes.
     */
    private Project m__Project;

    /**
     * The reference to the task, for logging purposes.
     */
    private Task m__Task;

    /**
     * Builds an <code>AbstractTemplate</code> using
     * given information.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public AbstractTemplate(final Project project, final Task task)
    {
        immutableSetProject(project);
        immutableSetTask(task);
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    private void immutableSetProject(final Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the project.
     * @param project the project.
     */
    protected void setProject(final Project project)
    {
        immutableSetProject(project);
    }

    /**
     * Retrieves the project.
     * @return such instance.
     */
    public Project getProject()
    {
        return m__Project;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    private void immutableSetTask(final Task task)
    {
        m__Task = task;
    }

    /**
     * Specifies the task.
     * @param task the task.
     */
    protected void setTask(final Task task)
    {
        immutableSetTask(task);
    }

    /**
     * Retrieves the task.
     * @return such instance.
     */
    public Task getTask()
    {
        return m__Task;
    }

    /**
     * Generates the source code.
     * @return such output.
     */
    public String generate()
    {
        return generate(getProject(), getTask());
    }

    /**
     * Generates the source code.
     * @param project the project.
     * @param task the task.
     * @return such output.
     * @precondition project != null
     * @precondition task != null
     */
    public final String generate(final Project project, final Task task)
    {
        project.log(
            task,
            "Generating " + getClass().getName() + ".",
            Project.MSG_VERBOSE);

        return generateOutput();
    }

    /**
     * Generates the actual source code.
     * @return such output.
     */
    protected abstract String generateOutput();
}
