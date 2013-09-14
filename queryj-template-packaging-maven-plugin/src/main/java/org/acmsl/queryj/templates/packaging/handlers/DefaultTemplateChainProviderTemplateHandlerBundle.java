/*
                        queryj

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
 * Filename: DefaultTemplateChainProviderTemplateHandlerBundle.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Bundles handlers that take care of the generation process of
 *              DefaultTemplateChainProviderTemplates.
 *
 * Date: 2013/09/14
 * Time: 11:47
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.api.handlers.TemplateHandlerBundle;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Bundles handlers that take care of the generation process of
 * {@link org.acmsl.queryj.templates.packaging.DefaultTemplateChainProviderTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/14 11:47
 */
@ThreadSafe
public class DefaultTemplateChainProviderTemplateHandlerBundle
    extends TemplateHandlerBundle<TemplateBuildHandlerTemplateBuildHandler, TemplateBuildHandlerTemplateWritingHandler>
{
    /**
     * Creates a new bundle.
     */
    public DefaultTemplateChainProviderTemplateHandlerBundle()
    {
        super(
            new DefaultTemplateChainProviderTemplateBuildHandler(),
            new DefaultTemplateChainProviderTemplateWritingHandler());
    }
}
