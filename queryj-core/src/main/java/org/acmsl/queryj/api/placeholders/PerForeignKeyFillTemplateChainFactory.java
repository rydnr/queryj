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
 * Filename: PerForeignKeyFillTemplateChainFactory.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: JDK 6 Services'-compatible per-foreign-key
 *              FillTemplateChainFactory implementation.
 *
 * Date: 6/8/13
 * Time: 8:41 AM
 *
 */
package org.acmsl.queryj.api.placeholders;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;

/**
 * JDK 6 Services'-compatible per-foreign-key {@link org.acmsl.queryj.api.placeholders.FillTemplateChainFactory}
 * implementation.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/06/08
 */
@SuppressWarnings("unused")
public interface PerForeignKeyFillTemplateChainFactory
    extends FillTemplateChainFactory<PerForeignKeyTemplateContext>
{
}
