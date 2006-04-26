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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate JUnit templates test the Database's
 *              numeric functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.TemplateFactory;
import org.acmsl.queryj.tools.templates.functions.numeric
    .NumericFunctionsTestTemplate;

/**
 * Is able to generate JUnit templates test the Database's
 * numeric functions.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public interface NumericFunctionsTestTemplateFactory
    extends  TemplateFactory
{
    /**
     * Generates a numeric functions template.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param projectImports the JDK imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param testFunctionMethod the test function method.
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public NumericFunctionsTestTemplate createNumericFunctionsTestTemplate(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String junitImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstructor,
        final String memberAccessors,
        final String setUpTearDownMethods,
        final String mainMethod,
        final String getInstanceTest,
        final String innerClass,
        final String innerTable,
        final String classEnd)
      throws  QueryJException;

    /**
     * Generates a numeric functions template.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param header the header.
     * @return a template.
     * @throws QueryJException if the template factory is invalid.
     */
    public NumericFunctionsTestTemplate createNumericFunctionsTestTemplate(
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String header)
      throws  QueryJException;
}
