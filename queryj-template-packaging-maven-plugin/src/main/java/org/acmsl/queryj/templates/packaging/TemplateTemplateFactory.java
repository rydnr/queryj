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
 * Filename: TemplateTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template factory used in the process to build QueryJ's
 *              Template sources.
 *
 * Date: 2013/08/16
 * Time: 11:04
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
 * Template factory used in the process to build QueryJ's Template sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/16 11/04
 */
@ThreadSafe
public class TemplateTemplateFactory
    implements TemplatePackagingTemplateFactory<TemplateTemplate<DefaultTemplatePackagingContext>, DefaultTemplatePackagingContext>
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class TemplateTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TemplateTemplateFactory SINGLETON = new TemplateTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return such instance.
     */
    @NotNull
    public static TemplateTemplateFactory getInstance()
    {
        return TemplateTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * Generates a template.
     * @return such template.
     */
    @NotNull
    @Override
    public TemplateTemplate<DefaultTemplatePackagingContext> createTemplate(
        @NotNull final DefaultTemplatePackagingContext context)
    {
        return new TemplateTemplate<DefaultTemplatePackagingContext>(context);
    }
}
