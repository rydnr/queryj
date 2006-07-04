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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate Oracle's system function repositories.
 *
 */
package org.acmsl.queryj.tools.templates.functions.system.oracle;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.system.oracle
    .OracleSystemFunctionsTemplate;

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

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/**
 * Is able to generate Oracle's system function repositories.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class OracleSystemFunctionsTemplateGenerator
    extends  SystemFunctionsTemplateGenerator
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleSystemFunctionsTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleSystemFunctionsTemplateGenerator SINGLETON =
            new OracleSystemFunctionsTemplateGenerator();
    }

    /**
     * Public constructor to allow reflective instantiation.
     */
    public OracleSystemFunctionsTemplateGenerator() {};

    /**
     * Retrieves a <code>OracleSystemFunctionsTemplateGenerator</code> instance.
     * @return such instance.
     */
    public static OracleSystemFunctionsTemplateGenerator getOracleInstance()
    {
        return OracleSystemFunctionsTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a system functions template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition quote != null
     */
    public SystemFunctionsTemplate createSystemFunctionsTemplate(
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
      throws  QueryJException
    {
        return
            new OracleSystemFunctionsTemplate(
                header,
                getDecoratorFactory(),
                packageName,
                engineName,
                engineVersion,
                quote);
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
