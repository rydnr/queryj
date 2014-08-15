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
 * Description: 
 *
 * Date: 2014/02/23
 * Time: 21:21
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/23 21:21
 */
@ThreadSafe
public class Literals
{
    public static final String SET_ARRAY = "setArray";
    public static final String SET_ASCII_STREAM = "setAsciiStream";
    public static final String SET_BINARY_STREAM = "setBinaryStream";
    public static final String SET_BLOB = "setBlob";
    public static final String SET_CHARACTER_STREAM = "setCharacterStream";
    public static final String SET_CLOB = "setClob";
    public static final String SET_DATE = "setDate";
    public static final String SET_N_CHARACTER_STREAM = "setNCharacterStream";
    public static final String SET_N_CLOB = "setNClob";
    public static final String SET_NULL = "setNull";
    public static final String SET_OBJECT = "setObject";
    public static final String SET_TIME = "setTime";
    public static final String SET_TIMESTAMP = "setTimestamp";
}
