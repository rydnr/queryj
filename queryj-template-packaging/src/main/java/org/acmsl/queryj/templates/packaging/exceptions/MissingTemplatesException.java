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
 * Filename: MissingTemplatesException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: The list of already-built templates are not found.
 *
 * Date: 2013/12/08
 * Time: 16:44
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * The list of already-built templates are not found.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 16:44
 */
@ThreadSafe
public class MissingTemplatesException
    extends TemplatePackagingNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -9045757439117602937L;

    /**
     * Creates a new instance.
     * @param templateType the type of template.
     */
    public MissingTemplatesException(@NotNull final String templateType)
    {
        super("missing." + templateType + ".templates");
    }
}
