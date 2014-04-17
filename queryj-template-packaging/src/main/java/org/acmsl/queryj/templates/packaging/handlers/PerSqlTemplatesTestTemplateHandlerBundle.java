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
 * Filename: PerSqlTemplatesTestTemplateHandlerBundle.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Handler bundle for PerSqlTemplatesTestTemplates.
 *
 * Date: 2014/04/17
 * Time: 10:24
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.handlers.TemplateHandlerBundle;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Handler bundle for {@link org.acmsl.queryj.templates.packaging.PerSqlTemplatesTestTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/17 10:24
 */
@ThreadSafe
public class PerSqlTemplatesTestTemplateHandlerBundle
    extends TemplateHandlerBundle<
    PerSqlTemplatesTestTemplateBuildHandler, PerSqlTemplatesTestTemplateWritingHandler>
{
    /**
     * Builds a bundle consisting of
     * {@link PerSqlTemplatesTestTemplateBuildHandler}
     * and {@link PerSqlTemplatesTestTemplateWritingHandler}.
     */
    public PerSqlTemplatesTestTemplateHandlerBundle()
    {
        super(
            new PerSqlTemplatesTestTemplateBuildHandler(),
            new PerSqlTemplatesTestTemplateWritingHandler());
    }
}