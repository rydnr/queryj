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
 * Filename: PerTableTemplatesTestTemplateHandlerBundle.java
 *
 * Description: Handler bundle for PerTableTemplatesTestTemplates.
 *
 * Generated initially by QueryJ Template Packaging's
 * org/acmsl/templates/packaging/TemplateHandlerBundle.stg
 * at timestamp: 2013/11/10 17:48
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-API classes.
 */
import org.acmsl.queryj.api.handlers.TemplateHandlerBundle;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Handler bundle for {@link org.acmsl.queryj.templates.packaging.PerTableTemplatesTestTemplate}s.
 * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ's Template Packaging</a>
 * Generation template: org/acmsl/templates/packaging/TemplateHandlerBundle.stg
 * @since 3.0
 * Created: 2013/11/10 17:48
 */
@ThreadSafe
public class PerTableTemplatesTestTemplateHandlerBundle
    extends TemplateHandlerBundle<PerTableTemplatesTestTemplateBuildHandler, PerTableTemplatesTestTemplateWritingHandler>
{
    /**
     * Builds a bundle consisting of
     * {@link PerTableTemplatesTestTemplateBuildHandler}
     * and {@link PerTableTemplatesTestTemplateWritingHandler}.
     */
    public PerTableTemplatesTestTemplateHandlerBundle()
    {
        super(
            new PerTableTemplatesTestTemplateBuildHandler(),
            new PerTableTemplatesTestTemplateWritingHandler());
    }
}
