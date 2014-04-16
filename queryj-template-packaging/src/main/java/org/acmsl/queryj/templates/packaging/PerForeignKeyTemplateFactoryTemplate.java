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
 * Filename: PerForeignKeyTemplateFactoryTemplate.java
 *
 * Author: Jose San Leandro
 *
 * Description: Represents foreign key-specific template factory templates.
 *
 * Created: 2014/04/14 18:35
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents foreign key-specific template factory templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @param <C> the context.
 * @since 3.0
 * Created 2014/04/14
 */
@ThreadSafe
public class PerForeignKeyTemplateFactoryTemplate<C extends TemplatePackagingContext>
    extends TemplateFactoryTemplate<C>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 904196154795056613L;

    /**
     * Creates a new instance.
     * @param context the template context.
     */
    public PerForeignKeyTemplateFactoryTemplate(@NotNull final C context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return "PerForeignKeyTemplateFactory".
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.PER_FOREIGN_KEY_TEMPLATE_FACTORY;
    }
}
