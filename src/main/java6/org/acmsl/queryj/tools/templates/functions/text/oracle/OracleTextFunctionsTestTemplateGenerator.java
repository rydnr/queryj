//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: OracleTextFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test Oracle's
 *              text functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.text.oracle
    .OracleTextFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTestTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate the JUnit classes to test the Database's text functions.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class OracleTextFunctionsTestTemplateGenerator
    extends  TextFunctionsTestTemplateGenerator
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleTextFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleTextFunctionsTestTemplateGenerator SINGLETON =
            new OracleTextFunctionsTestTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleTextFunctionsTestTemplateGenerator() {};

    /**
     * Retrieves a <code>OracleTextFunctionsTestTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static OracleTextFunctionsTestTemplateGenerator getOracleInstance()
    {
        return OracleTextFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a text functions template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public TextFunctionsTestTemplate createTextFunctionsTestTemplate(
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
      throws  QueryJException
    {
        OracleTextFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleTextFunctionsTestTemplate(
                    header,
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
