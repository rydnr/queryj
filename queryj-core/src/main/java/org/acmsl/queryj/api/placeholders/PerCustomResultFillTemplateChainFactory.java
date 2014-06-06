/*
                        QueryJ

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
 * Filename: PerCustomResultFillTemplateChainFactory.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: JDK 6 Services'-compatible per-custom-result
 *              FillTemplateChainFactory implementation.
 *
 * Date: 6/8/13
 * Time: 7:56 AM
 *
 */
package org.acmsl.queryj.api.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.PerCustomResultTemplateContext;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * JDK 6 Services'-compatible per-custom-result {@link FillTemplateChainFactory}
 * implementation.
 * @param <C> the template context.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/08
 */
@SuppressWarnings("unused")
@ThreadSafe
public interface PerCustomResultFillTemplateChainFactory<C extends PerCustomResultTemplateContext>
    extends FillTemplateChainFactory<C>
{
}
