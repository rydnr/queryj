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
 * Description: Is able to generate tests on database's system functions.
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
package org.acmsl.queryj.tools.templates.functions.system;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.templates.functions.FunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Is able to generate tests on database's system functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class SystemFunctionsTestTemplate
    extends  FunctionsTestTemplate
{
    /**
     * Builds a SystemFunctionsTestTemplate using given information.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public SystemFunctionsTestTemplate(
        final String  packageName,
        final String  testedPackageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote,
        final Project project,
        final Task    task)
    {
        super(
            "system",
            "System",
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            project,
            task);
    }

    /**
     * Retrieves the test name.
     * @return such name.
     */
    public String getTestName()
    {
        return "SystemFunctionsTest";
    }
}
