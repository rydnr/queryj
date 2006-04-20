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
 * Description: Is able to generate the JUnit classes to test MySQL's
 *              system functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.system.mysql
    .MySQLSystemFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTestTemplateGenerator;

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
 * Is able to generate the JUnit classes to test the Database's system functions.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class MySQLSystemFunctionsTestTemplateGenerator
    extends  SystemFunctionsTestTemplateGenerator
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Public constructor to allow reflective instantiation.
     */
    public MySQLSystemFunctionsTestTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final MySQLSystemFunctionsTestTemplateGenerator generator)
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
     * Retrieves a SystemFunctionsTestTemplateGenerator instance.
     * @return such instance.
     */
    public static MySQLSystemFunctionsTestTemplateGenerator getMySQLInstance()
    {
        MySQLSystemFunctionsTestTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result =
                (MySQLSystemFunctionsTestTemplateGenerator)
                    reference.get();
        }

        if  (result == null) 
        {
            result =
                new MySQLSystemFunctionsTestTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a system functions test template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @return a template.
     * @throws QueryJException if the input information is invalid.
     */
    public SystemFunctionsTestTemplate createSystemFunctionsTestTemplate(
        final String  packageName,
        final String  testedPackageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote)
      throws  QueryJException
    {
        SystemFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLSystemFunctionsTestTemplate(
                    getDecoratorFactory(),
                    packageName,
                    testedPackageName,
                    engineName,
                    engineVersion,
                    quote);
        }

        return result;
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }
}
