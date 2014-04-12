/*
                        QueryJ Template Packaging Plugin

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
 * Description: Literals for QueryJ Template Packaging Plugin.
 *
 * Date: 2013/11/30
 * Time: 11:16
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Literals for QueryJ Template Packaging Plugin.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/30 11:16
 */
@ThreadSafe
public interface Literals
{
    String TABLE_NAME = org.acmsl.queryj.Literals.TABLE_NAME;
    String PACKAGE_NAME = org.acmsl.queryj.Literals.PACKAGE_NAME;
    String ENGINE = org.acmsl.queryj.Literals.ENGINE;
    String PACKAGE_NAME_DAO = "<packageName>.dao";
}