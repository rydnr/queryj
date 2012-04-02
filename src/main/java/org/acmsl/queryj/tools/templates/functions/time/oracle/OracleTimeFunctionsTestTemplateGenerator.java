//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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

 ******************************************************************************
 *
 * Filename: OracleTimeFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test Oracle's
 *              time functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.time.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.time.oracle
    .OracleTimeFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplate;

import org.acmsl.queryj.tools.templates.functions.time
    .TimeFunctionsTestTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate the JUnit classes to test the Database's time functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleTimeFunctionsTestTemplateGenerator
    extends  TimeFunctionsTestTemplateGenerator
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleTimeFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleTimeFunctionsTestTemplateGenerator SINGLETON =
            new OracleTimeFunctionsTestTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleTimeFunctionsTestTemplateGenerator() {};

    /**
     * Retrieves a <code>OracleTimeFunctionsTestTemplateGenerator</code> instance.
     * @return such instance.
     */
    @NotNull
    public static OracleTimeFunctionsTestTemplateGenerator getOracleInstance()
    {
        return OracleTimeFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a time functions template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public TimeFunctionsTestTemplate createTimeFunctionsTestTemplate(
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable OracleTimeFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleTimeFunctionsTestTemplate(
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
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }
}
