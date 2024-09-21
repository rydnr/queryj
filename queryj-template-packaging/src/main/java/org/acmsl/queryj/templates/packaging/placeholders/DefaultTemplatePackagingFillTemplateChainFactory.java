/*
                        QueryJ's Template Packaging

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
 * Filename: DefaultTemplatePackagingFillTemplateChainFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2013/08/25
 * Time: 16:03
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ-Core classes;
 */
import org.acmsl.queryj.api.placeholders.FillTemplateChainFactory;

/*
 * Importing QueryJ's Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.DefaultTemplatePackagingContext;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Chain factory for placeholders specific to QueryJ's Template Packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/25 16:03
 */
@SuppressWarnings("unused")
@ThreadSafe
public interface DefaultTemplatePackagingFillTemplateChainFactory
    extends FillTemplateChainFactory<DefaultTemplatePackagingContext>
{
}
