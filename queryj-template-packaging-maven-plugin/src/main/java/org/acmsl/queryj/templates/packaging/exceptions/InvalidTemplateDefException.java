/*
                        QueryJ Template Packaging

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
 * Filename: InvalidTemplateDefException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The TemplateDef is invalid.
 *
 * Date: 2013/12/08
 * Time: 15:08
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.io.File;

/**
 * The {@link org.acmsl.queryj.templates.packaging.TemplateDef} is invalid.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 15:08
 */
@ThreadSafe
public class InvalidTemplateDefException
    extends QueryJNonCheckedException
{
    private static final long serialVersionUID = -2699031028498366917L;

    /**
     * Creates a new instance.
     * @param type the type.
     * @param file the file.
     * @param cause the cause.
     */
    public InvalidTemplateDefException(
        @NotNull final String type, @NotNull final File file, @NotNull final Throwable cause)
    {
        super("invalid.templatedef." + type, new Object[] { file.getAbsolutePath() }, cause);
    }
}
