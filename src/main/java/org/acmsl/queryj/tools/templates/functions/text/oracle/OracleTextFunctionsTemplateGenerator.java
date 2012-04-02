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
 * Filename: OracleTextFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate Oracle's text function repositories.
 *
 */
package org.acmsl.queryj.tools.templates.functions.text.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.text.oracle
    .OracleTextFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.text.TextFunctionsTemplate;
import org.acmsl.queryj.tools.templates.functions.text
    .TextFunctionsTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate Oracle's text function repositories.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class OracleTextFunctionsTemplateGenerator
    extends  TextFunctionsTemplateGenerator
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleTextFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleTextFunctionsTemplateGenerator SINGLETON =
            new OracleTextFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleTextFunctionsTemplateGenerator() {};

    /**
     * Retrieves a OracleTextFunctionsTemplateGenerator instance.
     * @return such instance.
     */
    @NotNull
    public static OracleTextFunctionsTemplateGenerator getOracleInstance()
    {
        return OracleTextFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a text functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public TextFunctionsTemplate createTextFunctionsTemplate(
        @Nullable final String packageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable TextFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new OracleTextFunctionsTemplate(
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
