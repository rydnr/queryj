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
 * Filename: OracleSystemFunctionsTestTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit classes to test Oracle's
 *              system functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.templates.functions.system.SystemFunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.system.SystemFunctionsTestTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate the JUnit classes to test Oracle database system functions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleSystemFunctionsTestTemplateGenerator<T extends OracleSystemFunctionsTestTemplate>
    extends  SystemFunctionsTestTemplateGenerator<T>
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleSystemFunctionsTestTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleSystemFunctionsTestTemplateGenerator SINGLETON =
            new OracleSystemFunctionsTestTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleSystemFunctionsTestTemplateGenerator() {}

    /**
     * Retrieves a {@link OracleSystemFunctionsTestTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static OracleSystemFunctionsTestTemplateGenerator getOracleInstance()
    {
        return OracleSystemFunctionsTestTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a system functions template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public SystemFunctionsTestTemplate createSystemFunctionsTestTemplate(
        @Nullable final String packageName,
        final String testedPackageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable OracleSystemFunctionsTestTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleSystemFunctionsTestTemplate(
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
}
