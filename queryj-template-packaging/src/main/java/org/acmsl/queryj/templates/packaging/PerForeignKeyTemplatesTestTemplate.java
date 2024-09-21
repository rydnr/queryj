/*
                  QueryJ's Template Packaging

    Copyright (C) 2014-today Jose San Leandro Armendariz
                             queryj@acm-sl.org

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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Author: QueryJ's Template Packaging 3.0-SNAPSHOT
 *
 * Filename: PerForeignKeyTemplatesTestTemplate.java
 *
 * Description: Used to generate sources using PerForeignKeyTemplatesTest.stg
 *
 * Generated originally by QueryJ Template Packaging's 3.0-SNAPSHOT
 * org/acmsl/queryj/templates/packaging/Template.stg
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Used to generate sources using PerForeignKeyTemplatesTest.stg.
 * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ's Template Packaging 3.0-SNAPSHOT</a>
 * Generation template: org/acmsl/queryj/templates/packaging/Template.stg
 */
@ThreadSafe
public class PerForeignKeyTemplatesTestTemplate
    extends AbstractTemplatePackagingTemplate<GlobalTemplateContext>
{
    /**
     * The serial version UID for serialization.
     */
    private static final long serialVersionUID = -1162566317L;

    /**
     * Builds a PerForeignKeyTemplatesTest using given context.
     * @param context the {@link GlobalTemplateContext}.
     */
    public PerForeignKeyTemplatesTestTemplate(@NotNull final GlobalTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return "";
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.PER_FOREIGN_KEY_TEMPLATES_TEST;
    }
}
