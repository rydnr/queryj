/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Triggered whenever an invalid template is generated.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing ACM-SL classes.
 */
import org.acmsl.commons.NonCheckedException;

/**
 * Triggered whenever an invalid template is generated.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class InvalidTemplateException
    extends  NonCheckedException
{
    /**
     * Creates a InvalidTemplateException with given message.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @precondition messageKey != null
     * @precondition params != null
     */
    public InvalidTemplateException(
        final String messageKey,
        final Object[] params)
    {
        super(messageKey, params);
    }

    /**
     * Creates a InvalidTemplateException with given cause.
     * @param messageKey the key to build the exception message.
     * @param params the parameters to build the exception message.
     * @param cause the error cause.
     * @precondition messageKey != null
     * @precondition params != null
     * @precondition cause != null
     */
    public InvalidTemplateException(
        final String messageKey,
        final Object[] params,
        final Throwable cause)
    {
        super(messageKey, params, cause);
    }

    /**
     * Retrieves the exceptions bundle.
     * @return such bundle name.
     */
    protected String retrieveExceptionsBundleName()
    {
        return "queryj-exceptions";
    }
}
