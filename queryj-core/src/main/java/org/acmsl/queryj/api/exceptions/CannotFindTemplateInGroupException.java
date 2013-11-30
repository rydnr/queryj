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
 * Filename: CannotFindTemplateInGroupException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the exceptional situation when the template group
 * is found, but it doesn't provide the expected template.
 *
 * Date: 7/9/13
 * Time: 6:19 AM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing StringTemplate classes.
 */
import org.stringtemplate.v4.STGroup;

/**
 * Represents the exceptional situation when the template group
 * is found, but it doesn't provide the expected template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/07/09
 */
@ThreadSafe
public class CannotFindTemplateInGroupException
    extends InvalidTemplateException
{
    private static final long serialVersionUID = 6979086659119761573L;

    /**
     * Creates an instance for given class.
     * @param group the {@link STGroup} instance.
     * @param templateName the name of the template.
     */
    public CannotFindTemplateInGroupException(@NotNull final STGroup group, @NotNull final String templateName)
    {
        super("cannot.find.template.in.group", new Object[] { group.getFileName(), templateName } );
    }
}

