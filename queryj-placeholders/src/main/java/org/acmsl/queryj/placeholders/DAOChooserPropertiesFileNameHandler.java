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
 * Filename: DAOChooserPropertiesFileNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "dao_chooser_properties_file_name" placeholders.
 *
 * Date: 6/30/12
 * Time: 9:42 PM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.TemplateContext;

/*
* Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.Locale;

/**
 * Resolves "dao_chooser_properties_file_name" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/30
 */
@ThreadSafe
@SuppressWarnings("unused")
public class DAOChooserPropertiesFileNameHandler
    extends AbstractDecoratedStringHandler<TemplateContext>
{
    /**
     * Creates a new {@link DAOChooserPropertiesFileNameHandler} associated to given
     * {@link TemplateContext}.
     * @param context the template.
     */
    public DAOChooserPropertiesFileNameHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "dao_chooser_properties_file_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "dao_chooser_properties_file_name";
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected String resolveContextValue(@NotNull final TemplateContext context)
    {
        return context.getRepositoryName().toLowerCase(Locale.US) + "-queryj.properties";
    }
}
