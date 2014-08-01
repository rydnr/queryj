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
 * Filename: TemplateDebuggingCommand.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The defined commands when debugging templates.
 *
 * Date: 2014/07/06
 * Time: 19:02
 *
 */
package org.acmsl.queryj.tools.debugging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The defined commands when debugging templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/07/06 19:02
 */
@ThreadSafe
public enum TemplateDebuggingCommand
{
    /**
     * The reload command.
     */
    RELOAD("reload"),

    /**
     * The "next" command.
     */
    NEXT("next"),

    /**
     * The "previous" command.
     */
    PREVIOUS("previous");

    /**
     * The name.
     */
    private final String m__strName;

    /**
     * Creates a new command.
     * @param name the name.
     */
    TemplateDebuggingCommand(final String name)
    {
        this.m__strName = name;
    }

    /**
     * Retrieves the name.
     * @return such information.
     */
    @NotNull final String getName()
    {
        return this.m__strName;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"name\": \"" + this.m__strName + '"'
            + ", \"class\": \"TemplateDebuggingCommand\""
            + ", \"package\": \"org.acmsl.queryj.tools.debugging\" }";
    }
}
