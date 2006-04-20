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
 * Description: Is able to generate numeric function repositories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.FunctionsTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate numeric function repositories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class NumericFunctionsTemplate
    extends  FunctionsTemplate
{
    /**
     * Builds a <code>NumericFunctionsTemplate</code> using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param classConstructor the class constructor.
     * @param innerClass the inner class.
     * @param classEnd the class end.
     */
    public NumericFunctionsTemplate(
        final DecoratorFactory decoratorFactory,
        final String  header,
        final String  packageDeclaration,
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote,
        final String  acmslImports,
        final String  jdkImports,
        final String  javadoc,
        final String  classDefinition,
        final String  classStart,
        final String  singletonBody,
        final String  classConstructor,
        final String  innerClass,
        final String  classEnd)
    {
        super(
            decoratorFactory,
            "numeric",
            "Numeric",
            header,
            packageDeclaration,
            packageName,
            engineName,
            engineVersion,
            quote,
            acmslImports,
            jdkImports,
            javadoc,
            classDefinition,
            classStart,
            singletonBody,
            classConstructor,
            innerClass,
            classEnd);
    }

    /**
     * Builds a <code>NumericFunctionsTemplate</code> using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public NumericFunctionsTemplate(
        final DecoratorFactory decoratorFactory,
        final String  packageName,
        final String  engineName,
        final String  engineVersion,
        final String  quote)
    {
        super(
            decoratorFactory,
            "numeric",
            "Numeric",
            packageName,
            engineName,
            engineVersion,
            quote);
    }
}
