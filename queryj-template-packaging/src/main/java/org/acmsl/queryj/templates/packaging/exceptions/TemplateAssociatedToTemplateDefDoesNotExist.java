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
 * Filename: TemplateAssociatedToTemplateDefDoesNotExist.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Error when the template described in a TemplateDef does not
 *              exist.
 *
 * Date: 2013/11/08
 * Time: 19:40
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.TemplateDef;

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
 * Error when the template described in a TemplateDef does not exist.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/08 19:40
 */
@ThreadSafe
public class TemplateAssociatedToTemplateDefDoesNotExist
    extends TemplatePackagingCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 864771092787892139L;

    /**
     * Creates an exception representing a TemplateDef pointing to a missing template.
     * @param templateDef the {@link TemplateDef}.
     * @param templateFile the template {@link File file}.
     */
    public TemplateAssociatedToTemplateDefDoesNotExist(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final File templateFile)
    {
        super(
            "template.associated.to.templatedef.does.not.exist",
            new Object[]
            {
                templateFile.getAbsolutePath(),
                templateDef.getDefName()
            });
    }

    /**
     * Creates an exception representing a TemplateDef pointing to a missing template.
     * @param templateDef the {@link TemplateDef}.
     */
    public TemplateAssociatedToTemplateDefDoesNotExist(
        @NotNull final TemplateDef<String> templateDef)
    {
        super(
            "template.associated.to.classloaded.templatedef.does.not.exist",
            new Object[]
                {
                    templateDef.getDefName()
                });
    }
}
