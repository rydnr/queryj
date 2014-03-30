/*
                        QueryJ Template Packaging Maven Plugin

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
 * Filename: DefaultTemplatePackagingContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Default implementation for TemplatePackagingContext.
 *
 * Date: 2013/08/17
 * Time: 19:05
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.templates.packaging.exceptions.TemplateDefNotAvailableException;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Default implementation for TemplatePackagingContext.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 19:05
 */
@ThreadSafe
public class DefaultTemplatePackagingContext
    extends AbstractTemplatePackagingContext
    implements PerTemplateDefTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1034808848836900245L;

    /**
     * Creates a new instance.
     * @param command the command.
     */
    public DefaultTemplatePackagingContext(
        @NotNull final TemplateDef<String> templateDef, @NotNull final QueryJCommand command)
    {
        super(command);
        immutableSetTemplateDef(templateDef, command);
        new QueryJCommandWrapper<TemplateDef<String>>(command).setSetting("templateDef", templateDef);
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    protected final void immutableSetTemplateDef(
        @NotNull final TemplateDef<String> templateDef, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<TemplateDef<String>>(command).setSetting("templateDef", templateDef);
    }

    /**
     * Retrieves the template def.
     * @return such instance.
     */
    @Override
    @NotNull
    public TemplateDef<String> getTemplateDef()
    {
        return getTemplateDef(getCommand());
    }

    /**
     * Retrieves the template def.
     * @param command the command.
     * @return such instance.
     */
    @NotNull
    protected TemplateDef<String> getTemplateDef(@NotNull final QueryJCommand command)
    {
        @Nullable final TemplateDef<String> result =
            new QueryJCommandWrapper<TemplateDef<String>>(command).getSetting("templateDef");

        if (result == null)
        {
            throw new TemplateDefNotAvailableException();
        }

        return result;
    }
}
