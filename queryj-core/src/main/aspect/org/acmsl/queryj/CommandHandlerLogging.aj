/*
                        QueryJ Core

    Copyright (C) 2002-today Jose San Leandro Armendariz
                        queryj@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: CommandHandlerLogging.aj
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Prints out when a handler starts and finishes in the process
 *              flow.
 *
 */
package org.acmsl.queryj;

/**
 * Prints out when a handler starts and finishes in the process
 * flow.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 */
public aspect CommandHandlerLogging
{
    /**
     * The tracing pointcut.
     */
    pointcut tracePoint(Object obj):
           execution(boolean QueryJCommandHandler.handle(QueryJCommand))
        && this(obj);

    /**
     * Before reaching tracing pointcut.
     */
    before(Object obj) : tracePoint(obj)
    {
        System.out.println(
            "Entering " + obj.getClass());
    }

    /**
     * After reaching tracing pointcut.
     */
    after(Object obj) : tracePoint(obj)
    {
        System.out.println(
            "Exiting " + obj.getClass());
    }
}
