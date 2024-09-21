/*
                  QueryJ's Template Packaging

    Copyright (C) 2002-today Jose San Leandro Armendariz
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
 * Filename: PerForeignKeyTemplatesTestTemplateHandlerBundle.java
 *
 * Description: Handler bundle for PerForeignKeyTemplatesTestTemplates.
 *
 * Generated originally by QueryJ Template Packaging's 3.0-SNAPSHOT
 * org/acmsl/queryj/templates/packaging/TemplateHandlerBundle.stg
 *
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
 * Handler bundle for {@link org.acmsl.queryj.templates.packaging.PerForeignKeyTemplatesTestTemplate}s.
 * @author <a href="http://www.acm-sl.org/projects/queryj">QueryJ's Template Packaging 3.0-SNAPSHOT</a>
 * Generation template: org/acmsl/queryj/templates/packaging/TemplateHandlerBundle.stg

 */
@ThreadSafe
public class PerForeignKeyTemplatesTestTemplateHandlerBundle
    extends TemplateHandlerBundle<PerForeignKeyTemplatesTestTemplateBuildHandler, PerForeignKeyTemplatesTestTemplateWritingHandler>
{
    /**
     * Builds a bundle consisting of
     * {@link PerForeignKeyTemplatesTestTemplateBuildHandler}
     * and {@link PerForeignKeyTemplatesTestTemplateWritingHandler}.
     */
    public PerForeignKeyTemplatesTestTemplateHandlerBundle()
    {
        super(
            new PerForeignKeyTemplatesTestTemplateBuildHandler(),
            new PerForeignKeyTemplatesTestTemplateWritingHandler());
    }
}
