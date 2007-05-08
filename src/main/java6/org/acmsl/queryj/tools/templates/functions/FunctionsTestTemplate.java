/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: FunctionsTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template for creating JUnit tests associated to Database's
 *              functions.
 *
 */
package org.acmsl.queryj.tools.templates.functions;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.templates.functions.FunctionsTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

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
 * Template for creating JUnit tests associated to Database's
 * functions.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class FunctionsTestTemplate
    extends     FunctionsTemplate
    implements  TestTemplate,
                FunctionsTestTemplateDefaults
{
    /**
     * The tested package name.
     */
    private String m__strTestedPackageName;

    /**
     * The project import statements.
     */
    private String m__strProjectImports;

    /**
     * The JDK import statements.
     */
    private String m__strJUnitImports;

    /**
     * The member accessors.
     */
    private String m__strMemberAccessors;

    /**
     * The setUp-tearDown methods.
     */
    private String m__strSetUpTearDownMethods;

    /**
     * The main method.
     */
    private String m__strMainMethod;

    /**
     * The getInstance test.
     */
    private String m__strGetInstanceTest;

    /**
     * The inner table.
     */
    private String m__strInnerTable;

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
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
     * @param classConstructor the class constructor.
     * @param memberAccessors the member accessors.
     * @param setUpTearDownMethods the setUp and tearDown methods.
     * @param mainMethod the main method.
     * @param getInstanceTest the getInstance test.
     * @param innerClass the inner class.
     * @param innerTable the inner table.
     * @param classEnd the class end.
     */
    public FunctionsTestTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String classDescription,
        final String classPrefix,
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
    {
        super(
            header,
            decoratorFactory,
            classDescription,
            classPrefix,
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
            null, // singleton body
            classConstructor,
            innerClass,
            classEnd);

        immutableSetTestedPackageName(testedPackageName);
        immutableSetProjectImports(projectImports);
        immutableSetJUnitImports(junitImports);
        immutableSetMemberAccessors(memberAccessors);
        immutableSetSetUpTearDownMethods(setUpTearDownMethods);
        immutableSetMainMethod(mainMethod);
        immutableSetGetInstanceTest(getInstanceTest);
        immutableSetInnerTable(innerTable);
    }

    /**
     * Builds a FunctionsTestTemplate using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param classDescription the class description.
     * @param classPrefix the class prefix.
     * @param packageName the package name.
     * @param testedPackageName the tested package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     */
    public FunctionsTestTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final String classDescription,
        final String classPrefix,
        final String packageName,
        final String testedPackageName,
        final String engineName,
        final String engineVersion,
        final String quote)
    {
        this(
//            (header != null)
//            ?  header
//            :  FunctionsTestTemplateDefaults.DEFAULT_HEADER,
            FunctionsTestTemplateDefaults.DEFAULT_HEADER,
            decoratorFactory,
            classDescription,
            classPrefix,
            FunctionsTestTemplateDefaults.PACKAGE_DECLARATION,
            packageName,
            testedPackageName,
            engineName,
            engineVersion,
            quote,
            FunctionsTestTemplateDefaults.PROJECT_IMPORTS,
            FunctionsTestTemplateDefaults.ACMSL_IMPORTS,
            FunctionsTestTemplateDefaults.JDK_IMPORTS,
            FunctionsTestTemplateDefaults.JUNIT_IMPORTS,
            FunctionsTestTemplateDefaults.DEFAULT_JAVADOC,
            FunctionsTestTemplateDefaults.CLASS_DEFINITION,
            FunctionsTestTemplateDefaults.DEFAULT_CLASS_START,
            FunctionsTestTemplateDefaults.CLASS_CONSTRUCTOR,
            FunctionsTestTemplateDefaults.MEMBER_ACCESSORS,
            FunctionsTestTemplateDefaults.SETUP_TEARDOWN_METHODS,
            FunctionsTestTemplateDefaults.MAIN_METHOD,
            FunctionsTestTemplateDefaults.GET_INSTANCE_TEST,
            FunctionsTestTemplateDefaults.INNER_CLASS,
            FunctionsTestTemplateDefaults.INNER_TABLE,
            FunctionsTestTemplateDefaults.DEFAULT_CLASS_END);
    }

    /**
     * Specifies the tested package name.
     * @param packageName the tested package name.
     */
    private void immutableSetTestedPackageName(final String packageName)
    {
        m__strTestedPackageName = packageName;
    }

    /**
     * Specifies the tested package name.
     * @param packageName the tested package name.
     */
    protected void setTestedPackageName(final String packageName)
    {
        immutableSetTestedPackageName(packageName);
    }

    /**
     * Retrieves the tested package name.
     * @return such package name.
     */
    public String getTestedPackageName()
    {
        return m__strTestedPackageName;
    }

    /**
     * Specifies the project imports.
     * @param imports the new imports.
     */
    private void immutableSetProjectImports(final String imports)
    {
        m__strProjectImports = imports;
    }

    /**
     * Specifies the project imports.
     * @param imports the new imports.
     */
    protected void setProjectImports(final String imports)
    {
        immutableSetProjectImports(imports);
    }

    /**
     * Retrieves the project imports.
     * @return such information.
     */
    public String getProjectImports() 
    {
        return m__strProjectImports;
    }

    /**
     * Specifies the JUnit imports.
     * @param imports the new imports.
     */
    private void immutableSetJUnitImports(final String imports)
    {
        m__strJUnitImports = imports;
    }

    /**
     * Specifies the JUnit imports.
     * @param imports the new imports.
     */
    protected void setJUnitImports(final String imports)
    {
        immutableSetJUnitImports(imports);
    }

    /**
     * Retrieves the JUnit imports.
     * @return such information.
     */
    public String getJUnitImports() 
    {
        return m__strJUnitImports;
    }

    /**
     * Specifies the member accessors.
     * @param memberAccessors the member accessors.
     */
    private void immutableSetMemberAccessors(final String memberAccessors)
    {
        m__strMemberAccessors = memberAccessors;
    }

    /**
     * Specifies the member accessors.
     * @param memberAccessors the member accessors.
     */
    protected void setMemberAccessors(final String memberAccessors)
    {
        immutableSetMemberAccessors(memberAccessors);
    }

    /**
     * Retrieves the member accessors.
     * @return such information.
     */
    public String getMemberAccessors()
    {
        return m__strMemberAccessors;
    }

    /**
     * Specifies the setUp-TearDown methods.
     * @param setUpTearDownMethods the setUp-TearDown methods.
     */
    private void immutableSetSetUpTearDownMethods(final String setUpTearDownMethods)
    {
        m__strSetUpTearDownMethods = setUpTearDownMethods;
    }

    /**
     * Specifies the setUp-TearDown methods.
     * @param setUpTearDownMethods the setUp-TearDown methods.
     */
    protected void setSetUpTearDownMethods(final String setUpTearDownMethods)
    {
        immutableSetSetUpTearDownMethods(setUpTearDownMethods);
    }

    /**
     * Retrieves the setUp-TearDown methods.
     * @return such information.
     */
    public String getSetUpTearDownMethods()
    {
        return m__strSetUpTearDownMethods;
    }

    /**
     * Specifies the main method.
     * @param mainMethod the main method.
     */
    private void immutableSetMainMethod(final String mainMethod)
    {
        m__strMainMethod = mainMethod;
    }

    /**
     * Specifies the main method.
     * @param mainMethod the main method.
     */
    protected void setMainMethod(final String mainMethod)
    {
        immutableSetMainMethod(mainMethod);
    }

    /**
     * Retrieves the main method.
     * @return such information.
     */
    public String getMainMethod()
    {
        return m__strMainMethod;
    }

    /**
     * Specifies the getInstance test.
     * @param getInstanceTest the getInstance test.
     */
    private void immutableSetGetInstanceTest(final String getInstanceTest)
    {
        m__strGetInstanceTest = getInstanceTest;
    }

    /**
     * Specifies the getInstance test.
     * @param getInstanceTest the getInstance test.
     */
    protected void setGetInstanceTest(final String getInstanceTest)
    {
        immutableSetGetInstanceTest(getInstanceTest);
    }

    /**
     * Retrieves the getInstance test.
     * @return such information.
     */
    public String getGetInstanceTest()
    {
        return m__strGetInstanceTest;
    }

    /**
     * Specifies the inner table.
     * @param innerTable the inner table.
     */
    private void immutableSetInnerTable(final String innerTable)
    {
        m__strInnerTable = innerTable;
    }

    /**
     * Specifies the inner table.
     * @param innerTable the inner table.
     */
    protected void setInnerTable(final String innerTable)
    {
        immutableSetInnerTable(innerTable);
    }

    /**
     * Retrieves the inner table.
     * @return such information.
     */
    public String getInnerTable()
    {
        return m__strInnerTable;
    }

    /**
     * Indicates if the test should be considered a suite or not.
     * @return such information.
     */
    public boolean isSuite()
    {
        return false;
    }

    /**
     * Creates the special function mappings' return types.
     * @param mapping the initial mapping.
     * @return the updated mapping.
     */
    protected Map fillUpSpecialMappingsReturnTypes(Map mappings)
    {
        return mappings;
    }

    /**
     * Retrieves the special mapping's return type for given function.
     * @param function the function.
     * @return the field type.
     */
    protected String getSpecialMappingReturnType(final String function)
    {
        // Not used for unit test generation, since the comparision is done
        // via unspecific assertEquals on Field class, not derived ones.
        return null;
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getClassDescription());
    }

    /**
     * Builds the header for logging purposes.
     * @param type the type.
     * @return such header.
     * @precondition type != null
     */
    protected String buildHeader(final String type)
    {
        return "Generating " + type + " test functions template.";
    }

    /**
     * Retrieves the source code of the generated numeric functions test.
     * @param header the header.
     * @return such source code.
     */
    protected String generateOutput(final String header)
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (t_StringUtils != null) 
        {
            Object t_aEngine =
                new Object[]
                {
                    getEngineName(),
                    getEngineVersion(),
                    getClassDescription()
                };

            Object[] t_aPackageName = new Object[]{getPackageName()};

            Object[] t_aClassPrefix = new Object[]{getClassPrefix()};

            MessageFormat t_Formatter = null;

            String t_strHeader = header;

            if  (t_strHeader != null) 
            {
                t_Formatter = new MessageFormat(t_strHeader);
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strPackageDeclaration = getPackageDeclaration();

            if  (t_strPackageDeclaration != null) 
            {
                t_Formatter = new MessageFormat(t_strPackageDeclaration);
                t_sbResult.append(t_Formatter.format(t_aPackageName));
            }

            String t_strProjectImports = getProjectImports();
            String t_strTestedPackageName = getTestedPackageName();

            if  (   (t_strProjectImports    != null)
                 && (t_strTestedPackageName != null))
            {
                t_Formatter = new MessageFormat(t_strProjectImports);
                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strTestedPackageName,
                            getClassPrefix()
                        }));
            }
            
            String t_strAcmslImports = getAcmslImports();

            if  (t_strAcmslImports != null) 
            {
                t_sbResult.append(t_strAcmslImports);
            }

            String t_strJdkImports = getJdkImports();

            if  (t_strJdkImports != null) 
            {
                t_sbResult.append(t_strJdkImports);
            }

            String t_strJUnitImports = getJUnitImports();

            if  (t_strJUnitImports != null) 
            {
                t_sbResult.append(t_strJUnitImports);
            }

            String t_strJavadoc = getJavadoc();

            if  (t_strJavadoc != null) 
            {
                t_Formatter = new MessageFormat(t_strJavadoc);
                t_sbResult.append(t_Formatter.format(t_aEngine));
            }

            String t_strClassDefinition = getClassDefinition();

            if  (t_strClassDefinition != null) 
            {
                t_Formatter = new MessageFormat(t_strClassDefinition);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strClassStart = getClassStart();

            if  (t_strClassStart != null) 
            {
                t_Formatter = new MessageFormat(t_strClassStart);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strClassConstructor = getClassConstructor();

            if  (t_strClassConstructor != null) 
            {
                t_Formatter = new MessageFormat(t_strClassConstructor);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strMemberAccessors = getMemberAccessors();

            if  (t_strMemberAccessors != null) 
            {
                t_Formatter = new MessageFormat(t_strMemberAccessors);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strSetUpTearDownMethods = getSetUpTearDownMethods();

            if  (t_strSetUpTearDownMethods != null) 
            {
                t_Formatter = new MessageFormat(t_strSetUpTearDownMethods);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strMainMethod = getMainMethod();

            if  (t_strMainMethod != null) 
            {
                t_Formatter = new MessageFormat(t_strMainMethod);
                t_sbResult.append(t_Formatter.format(t_aClassPrefix));
            }

            String t_strGetInstanceTest = getGetInstanceTest();

            if  (t_strGetInstanceTest != null) 
            {
                t_Formatter = new MessageFormat(t_strGetInstanceTest);
                t_sbResult.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            t_strTestedPackageName,
                            getClassPrefix()
                        }));
            }

            String t_strDefaultFunctionMethodTest = getDefaultFunctionMethod();

            if  (t_strDefaultFunctionMethodTest != null) 
            {
                List t_lFunctions = getFunctions();

                if  (t_lFunctions != null)
                {
                    Iterator t_itFunctions = t_lFunctions.iterator();

                    MessageFormat t_TestMethodFormatter =
                        new MessageFormat(t_strDefaultFunctionMethodTest);

                    while  (t_itFunctions.hasNext()) 
                    {
                        String t_strFunction = (String) t_itFunctions.next();

                        String t_strMapping = getMapping(t_strFunction);

                        if  (t_strMapping == null) 
                        {
                            t_strMapping = getSpecialMapping(t_strFunction);

                            if  (t_strMapping == null) 
                            {
                                if  (generateWarning(null)) 
                                {
                                    logDebug(
                                        "No mapping for " + t_strFunction);
                                }

                                continue;
                            }
                            else 
                            {
                                t_TestMethodFormatter =
                                    new MessageFormat(t_strMapping);
                            }
                        }

                        String t_strJavaMethod =
                            t_StringUtils.toJavaMethod(
                                t_strFunction.toLowerCase(),
                                '_',
                                getCapitalizedWords());

                        String t_strTestJavaMethod =
                            "test" + t_StringUtils.capitalize(t_strJavaMethod, '_');

                        t_sbResult.append(
                            t_TestMethodFormatter.format(
                                new Object[]
                                {
                                    t_strJavaMethod,
                                    getTestedPackageName(),
                                    t_strTestJavaMethod,
                                    t_strFunction,
                                    getQuote(),
                                    getClassPrefix()
                                }));
                    }
                }
            }

            String t_strInnerTable = getInnerTable();

            if  (t_strInnerTable != null) 
            {
                t_sbResult.append(t_strInnerTable);
            }

            t_sbResult.append(innerClassesToString());

            String t_strClassEnd = getClassEnd();

            if  (t_strClassEnd != null) 
            {
                t_sbResult.append(t_strClassEnd);
            }
        }

        return t_sbResult.toString();
    }
}
