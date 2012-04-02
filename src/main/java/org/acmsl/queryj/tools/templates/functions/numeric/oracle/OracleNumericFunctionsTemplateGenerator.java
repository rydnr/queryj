//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 the License, or any later version.

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
 * Filename: OracleNumericFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate Oracle's numeric function repositories.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTemplateGenerator;

import org.acmsl.queryj.tools.templates.functions.numeric.oracle
    .OracleNumericFunctionsTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate Oracle's numeric function repositories.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleNumericFunctionsTemplateGenerator
    extends  NumericFunctionsTemplateGenerator
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleNumericFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleNumericFunctionsTemplateGenerator SINGLETON =
            new OracleNumericFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleNumericFunctionsTemplateGenerator() {};

    /**
     * Retrieves a <code>OracleNumericFunctionsTemplateGenerator</code> instance.
     * @return such instance.
     */
    @NotNull
    public static OracleNumericFunctionsTemplateGenerator getOracleInstance()
    {
        return OracleNumericFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public NumericFunctionsTemplate createNumericFunctionsTemplate(
        @Nullable final String packageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable NumericFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleNumericFunctionsTemplate(
                    header,
                    getDecoratorFactory(),
                    packageName,
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
