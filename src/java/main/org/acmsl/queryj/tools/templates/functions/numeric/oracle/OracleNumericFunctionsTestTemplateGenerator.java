/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Is able to generate the JUnit classes to test Oracle's
 *              numeric functions.
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
package org.acmsl.queryj.tools.templates.functions.numeric.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.oracle
    .OracleNumericFunctionsTestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate the JUnit classes to test the Database's numeric functions.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class OracleNumericFunctionsTestTemplateGenerator
    extends  NumericFunctionsTestTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleNumericFunctionsTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(OracleNumericFunctionsTestTemplateGenerator generator)
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
     * Retrieves a NumericFunctionsTestTemplateGenerator instance.
     * @return such instance.
     */
    public static OracleNumericFunctionsTestTemplateGenerator getOracleInstance()
    {
        OracleNumericFunctionsTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (OracleNumericFunctionsTestTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new OracleNumericFunctionsTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public NumericFunctionsTestTemplate createNumericFunctionsTestTemplate(
            String packageName,
            String engineName,
            String engineVersion,
            String quote)
        throws  QueryJException
    {
        OracleNumericFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleNumericFunctionsTestTemplate(
                    "unittests." + packageName,
                    packageName,
                    engineName,
                    engineVersion,
                    quote) {};
        }

        return result;
    }
}
