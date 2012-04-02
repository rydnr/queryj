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
 * Filename: MySQLSystemFunctionsTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate MySQL's system function repositories.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.mysql;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.system.mysql
    .MySQLSystemFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplate;

import org.acmsl.queryj.tools.templates.functions.system
    .SystemFunctionsTemplateGenerator;

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
 * Is able to generate MySQL's system function repositories.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class MySQLSystemFunctionsTemplateGenerator
    extends  SystemFunctionsTemplateGenerator
    implements Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MySQLSystemFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MySQLSystemFunctionsTemplateGenerator SINGLETON =
            new MySQLSystemFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public MySQLSystemFunctionsTemplateGenerator() {};

    /**
     * Retrieves a <code>MySQLSystemFunctionsTemplateGenerator</code> instance.
     * @return such instance.
     */
    @NotNull
    public static MySQLSystemFunctionsTemplateGenerator getMySQLInstance()
    {
        return MySQLSystemFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a system functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     */
    @Nullable
    public SystemFunctionsTemplate createSystemFunctionsTemplate(
        @Nullable final String packageName,
        @Nullable final String engineName,
        @Nullable final String engineVersion,
        @Nullable final String quote,
        final String header)
    {
        @Nullable SystemFunctionsTemplate result = null;

        if  (   (packageName   != null)
             && (engineName    != null)
             && (engineVersion != null)
             && (quote         != null))
        {
            result =
                new MySQLSystemFunctionsTemplate(
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
