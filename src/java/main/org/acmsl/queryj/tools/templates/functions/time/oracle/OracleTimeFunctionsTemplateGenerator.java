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
 * Description: Is able to generate time function repositories according to
 *              Oracle database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.functions.time.TimeFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.time.TimeFunctionsTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate time function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class OracleTimeFunctionsTemplateGenerator
    extends  TimeFunctionsTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleTimeFunctionsTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setOracleReference(
        final OracleTimeFunctionsTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a OracleTimeFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    public static OracleTimeFunctionsTemplateGenerator getOracleInstance()
    {
        OracleTimeFunctionsTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (OracleTimeFunctionsTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new OracleTimeFunctionsTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a time functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     */
    public TimeFunctionsTemplate createTimeFunctionsTemplate(
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote,
        final Project project,
        final Task    task)
      throws  QueryJException
    {
        TimeFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleTimeFunctionsTemplate(
                    packageName,
                    engineName,
                    engineVersion,
                    quote,
                    project,
                    task) {};
        }

        return result;
    }
}
