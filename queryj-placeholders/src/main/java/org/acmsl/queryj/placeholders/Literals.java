/*
                        QueryJ Placeholders

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
 * Description: 
 *
 * Date: 2013/11/28
 * Time: 21:57
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Literals for QueryJ Placeholders.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/28 21:57
 */
@ThreadSafe
public class Literals
{
    public static final String CLASS_NAME = "class_name";
    public static final String RESULT = "result";
    public static final String FOREIGN_KEY = "foreign_key";
    public static final String QUERYJ_PROPERTIES = "-queryj.properties";
    public static final String HEADER = org.acmsl.queryj.Literals.HEADER;
    public static final String LOB_HANDLING_REQUIRED = "lob_handling_required";
    public static final String PACKAGE = org.acmsl.queryj.Literals.PACKAGE;
    public static final String REPOSITORY = org.acmsl.queryj.Literals.REPOSITORY;
    public static final String TIMESTAMP = "Timestamp";
}
