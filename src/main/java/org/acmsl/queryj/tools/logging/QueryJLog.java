//;-*- mode: java -*-
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
                    Spain

 ******************************************************************************
 *
 * Filename: QueryJLog.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Minimal logging helper to unify at API level
 * all possible logging mechanisms used when executing
 * QueryJ. It extends Apache Commons-Logging's Log just
 * to make the developer confortable with the API, since
 * using it just means replacing
 * *LogFactory.getLog(..)* with
 * using the *QueryJLog*, available via
 * *AntCommand*.
 *
 * Version: $Revision$ ($Author$ at $Date$)
 *
 * $Id$
 *
 * Important Note: This class implements Apacje Commons-Logging's Log
 * interface. License details are copied verbatim below.
 *
 */
package org.acmsl.queryj.tools.logging;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Minimal logging helper to unify at API level
 * all possible logging mechanisms used when executing
 * QueryJ. It extends Apache Commons-Logging's Log just
 * to make the developer confortable with the API, since
 * using it just means replacing
 * <code>LogFactory.getLog(..)</code> with
 * using the <code>QueryJLog</code>, available via
 * <code>AntCommand</code>.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface QueryJLog
    extends  Log
{
    /**
     * The <i>trace</i> level.
     */
    public static final int TRACE = 1;

    /**
     * The <i>debug</i> level.
     */
    public static final int DEBUG = 2;

    /**
     * The <i>info</i> level.
     */
    public static final int INFO = 3;

    /**
     * The <i>warn</i> level.
     */
    public static final int WARN = 4;

    /**
     * The <i>error</i> level.
     */
    public static final int ERROR = 5;

    /**
     * The <i>fatal</i> level.
     */
    public static final int FATAL = 6;
}
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
