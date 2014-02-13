/*
                        QueryJ-Core

    Copyright (C) 2002-today Jose San Leandro Armend�riz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecaba�as
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Prints out when a handler starts and finishes in the process
 *              flow.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package aspects.org.acmsl.queryj;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.AntCommandHandler;

/*
 * Importing Jakarta Commons classes.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
           execution(boolean AntCommandHandler.handle(AntCommand))
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
