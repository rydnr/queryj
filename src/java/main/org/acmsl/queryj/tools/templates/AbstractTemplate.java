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
 * Description: Represents generic templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.Template;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Represents generic templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTemplate
    implements  Template
{
    /**
     * Builds an empty <code>AbstractTemplate</code>.
     */
    protected AbstractTemplate()
    {
    }

    /**
     * Generates the source code.
     * @return such output.
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    public String generate()
      throws  InvalidTemplateException
    {
        logHeader(buildHeader());

        return generateOutput();
    }

    /**
     * Logs a custom header.
     * @param header the header.
     */
    protected void logHeader(final String header)
    {
        Log t_Log = UniqueLogFactory.getLog(getClass());
        
        if  (t_Log != null)
        {
            t_Log.trace(header);
        }
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return "Generating " + getClass().getName() + ".";
    }

    /**
     * Generates the actual source code.
     * @return such output. 
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    protected abstract String generateOutput()
      throws  InvalidTemplateException;
}
