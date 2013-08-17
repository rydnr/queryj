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
 * Filename: TemplatePackagingTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base class for all Template Packaging-specific template
 *              generators.
 *
 * Date: 2013/08/17
 * Time: 10:09
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.AbstractTemplateGenerator;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Base class for all Template Packaging-specific template generators.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 10/09
 */
@ThreadSafe
public abstract class TemplatePackagingTemplateGenerator
    <T extends TemplatePackagingTemplate<C>, C extends TemplatePackagingContext>
    extends AbstractTemplateGenerator<T>
{
    /**
     * Creates a new TemplatePackagingTemplateGenerator instance.
     * @param caching whether the generated content is being cached or not.
     * @param threadCount the number of threads to use.
     */
    protected TemplatePackagingTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }
}
