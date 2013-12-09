/*
                  QueryJ's Template Packaging

    Copyright (C) 2013-today Jose San Leandro Armendariz
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
 * Author: QueryJ's Template Packaging
 *
 * Filename: PerTableTemplatesTestTemplate.java
 *
 * Description: Used to generate sources using PerTableTemplatesTest.stg
 *
 * Generated initially by QueryJ Template Packaging's
 * org/acmsl/templates/packaging/Template.stg
 * at timestamp: 2013/11/10 17:48
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
 * Used to generate sources using PerTableTemplatesTest.stg.
 * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ's Template Packaging</a>
 * Generation template: org/acmsl/templates/packaging/Template.stg
 * @since 3.0
 * Created: 2013/11/10 17:48
 */
@ThreadSafe
public class PerTableTemplatesTestTemplate
    extends AbstractTemplatePackagingTemplate<GlobalTemplateContext>
{
    /**
     * The serial version UID for serialization.
     */
    private static final long serialVersionUID = -1256902502;

    /**
     * Builds a PerTableTemplatesTest using given context.
     * @param context the {@link GlobalTemplateContext}.
     */
    public PerTableTemplatesTestTemplate(@NotNull final GlobalTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template name.
     * @return "PerTablesTemplatesTest";
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return Literals.PER_TABLE_TEMPLATES_TEST;
    }
}
