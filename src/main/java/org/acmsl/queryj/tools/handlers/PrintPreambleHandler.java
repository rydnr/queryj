//;-*- mode: java -*-
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
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: PrintPreambleHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Prints the GPL preamble and the QueryJ version.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.SingularPluralFormConverter;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Prints the GPL preamble and the QueryJ version.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class PrintPreambleHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The GPL preamble.
     */
    public static final String PREAMBLE =
          "\n"
        + "    Copyright (C) 2002-2007  ACM-SL\n"
        + "                        chous@acm-sl.org\n"
        + "\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL license.\n"
        + "    Contact info: jose.sanleandro@acm-sl.com\n";

    /**
     * Creates a <code>PrintPreambleHandler</code> instance.
     */
    public PrintPreambleHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            printPreamble(command.getAttributeMap(), command.getLog());
        }
        
        return result;
    }

    /**
     * Prints the preamble.
     * @param parameters the parameter map.
     * @param log the log.
     * @return <code>false</code> if the chain should be stopped because
     * of invalid parameters.
     * @precondition log != null
     */
    public void printPreamble(final Map parameters, final QueryJLog log)
    {
        if  (parameters != null) 
        {
            if  (log != null)
            {
                StringBuffer t_sbPreamble = new StringBuffer();

                String t_strQueryJVersion = retrieveQueryJVersion(parameters);

                if  (t_strQueryJVersion != null)
                {
                    t_sbPreamble.append("\n           QueryJ ");
                    t_sbPreamble.append(t_strQueryJVersion);
                    t_sbPreamble.append("\n");
                }
                else
                {
                    new StringBuffer("\n                        QueryJ\n");
                }

                t_sbPreamble.append(PREAMBLE);

                log.setEnabled(true);
                log.info(t_sbPreamble.toString());
            }
        }
    }

    /**
     * Retrieves the QueryJ version from the map.
     * @param parameters the parameters.
     * @return such version.
     * @precondition parameters != null
     */
    protected String retrieveQueryJVersion(final Map parameters)
    {
        String result = null;

        if  (parameters != null)
        {
            result = 
                (String)
                    parameters.get(
                        ParameterValidationHandler.QUERYJ_VERSION);
        }

        return result;
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return MIN_WEIGHT;
    }
}
