/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Represents abnormal situations regarding QueryJ processing.
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
package org.acmsl.queryj;

/**
 * Represents abnormal situations regarding QueryJ processing.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class QueryJException
    extends  Exception
{
    /**
     * Builds a QueryJ exception with a certain message.
     * @param message the message.
     */
    public QueryJException(String message)
    {
        super(message);
    }

    /**
     * Builds a QueryJ exception to wrap given one.
     * @param message the message.
     * @param cause the exception to wrap.
     */
    public QueryJException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Outputs a text representation of this exception.
     * @return the error description.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(getMessage());

        Throwable t_Cause = getCause();

        if  (t_Cause != null) 
        {
            t_sbResult.append(" (");
            t_sbResult.append(t_Cause.getMessage());
            t_sbResult.append(")");
        }

        return t_sbResult.toString();
    }
}
