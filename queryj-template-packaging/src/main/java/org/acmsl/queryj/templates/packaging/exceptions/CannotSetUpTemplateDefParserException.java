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
 * Filename: CannotSetUpTemplateDefParserException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The TemplateDef parser could not be configured for a given
 *              file.
 *
 * Date: 2013/12/08
 * Time: 14:58
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing QueryJ-API classes.
 */
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;

/**
 * The {@link org.acmsl.queryj.templates.packaging.TemplateDef} parser could
 * not be configured for a given file.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 14:58
 */
@ThreadSafe
public class CannotSetUpTemplateDefParserException
    extends QueryJNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -618063241782514710L;

    /**
     * Creates a new instance.
     * @param file the template def file.
     * @param cause the cause.
     */
    public CannotSetUpTemplateDefParserException(
        @NotNull final File file, @NotNull final Throwable cause)
    {
        super("cannot.setup.templatedef.parser", new Object[] { file.getAbsolutePath() }, cause);
    }

    /**
     * Creates a new instance.
     * @param cause the cause.
     */
    public CannotSetUpTemplateDefParserException(@NotNull final Throwable cause)
    {
        super("cannot.setup.templatedef.parser.for.stream", new Object[0], cause);
    }
}
