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
 * Filename: Literals.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Literals all over QueryJ Core.
 *
 * Date: 2013/11/21
 * Time: 19:41
 *
 */
package org.acmsl.queryj;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Literals all over QueryJ Core.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/21 19:41
 */
@ThreadSafe
public interface Literals
{
    String JSON_PARENT_ATTR = " 'parent': ";
    String TEMPLATE = "Template";
    String GENERATING = "Generating ";
    String CONTEXT = "Context";
    String FILL_TEMPLATE_CHAIN_FACTORY = "FillTemplateChainFactory";
    String DEFAULT_PLACEHOLDER_PACKAGE = "org.acmsl.queryj.api.placeholders";


    String SOURCE = "source";
    String INVALID_TABLE_COMMENT = "Invalid table comment: ";
    String INVALID_COLUMN_COMMENT = "Invalid column comment: ";
    String REFERENCED_RESULT_NOT_FOUND = "Referenced result not found: ";
    String RESULT_SUFFIX = ".result";
    String TABLE_NOT_FOUND = "Table not found: ";
}
