/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: QueryJException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents abnormal situations regarding QueryJ processing.
 *
 */
package org.acmsl.queryj;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.commons.CheckedException;

/**
 * Represents abnormal situations regarding QueryJ processing.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class QueryJException
    extends  CheckedException
{
    /**
     * A cached empty String array.
     */
    protected static final String[] EMPTY_STRING_ARRAY = new String[0];
    
    /**
     * Creates a <code>QueryJException</code> with given message.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @precondition messageKey != null
     * @precondition params != null
     */
    public QueryJException(final String messageKey, final Object[] params)
    {
        super(messageKey, params);
    }

    /**
     * Creates a <code>QueryJException</code> with given message.
     * @param messageKey the key to build the exception message.
     * @precondition messageKey != null
     */
    public QueryJException(final String messageKey)
    {
        this(messageKey, EMPTY_STRING_ARRAY);
    }

    /**
     * Creates a <code>QueryJException</code> with given cause.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @param cause the error cause.
     * @precondition messageKey != null
     * @precondition params != null
     * @precondition cause != null
     */
    public QueryJException(
        final String messageKey,
        final Object[] params,
        final Throwable cause)
    {
        super(messageKey, params, cause);
    }

    /**
     * Creates a <code>QueryJException</code> with given cause.
     * @param messageKey the key to build the exception message.
     * @param cause the error cause.
     * @precondition messageKey != null
     * @precondition cause != null
     */
    public QueryJException(final String messageKey, final Throwable cause)
    {
        this(messageKey, EMPTY_STRING_ARRAY, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    protected String retrieveExceptionsBundleName()
    {
        return "queryj-exceptions";
    }

    /**
     * Outputs a text representation of this exception.
     * @return the error description.
     */
    public String toString()
    {
        return toString(getMessage(), getCause());
    }

    /**
     * Outputs a text representation of this exception.
     * @param message the message.
     * @param cause the cause.
     * @return the error description.
     */
    public String toString(final String message, final Throwable cause)
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(super.toString());

        t_sbResult.append(":\n");
        t_sbResult.append(message);

        if  (cause != null) 
        {
            t_sbResult.append(" (");
            t_sbResult.append(cause.getMessage());
            t_sbResult.append(")");
        }

        return t_sbResult.toString();
    }
}
