/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Template for creating JUnit tests to ensure generated Mock
 *              DAOs are working correctly.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.AbstractTestTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains the subtemplates for creating JUnit tests to ensure
 * generated Mock DAOs are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public abstract class AbstractMockDAOTestTemplate
    extends  AbstractTestTemplate
{
    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The package declaration.
     */
    private String m__strPackageDeclaration;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The DAO package name.
     */
    private String m__strDAOPackageName;

    /**
     * The value object's package name.
     */
    private String m__strValueObjectPackageName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The ACM-SL imports.
     */
    private String m__strAcmslImports;

    /**
     * The JDK imports.
     */
    private String m__strJdkImports;

    /**
     * The JUnit imports.
     */
    private String m__strJUnitImports;

    /**
     * The Javadoc.
     */
    private String m__strJavadoc;

    /**
     * The class definition.
     */
    private String m__strClassDefinition;

    /**
     * The class start.
     */
    private String m__strClassStart;

    /**
     * The test values.
     */
    private String m__strTestValues;

    /**
     * The test updated values.
     */
    private String m__strTestUpdatedValues;

    /**
     * The constructor.
     */
    private String m__strConstructor;

    /**
     * The inner methods.
     */
    private String m__strInnerMethods;

    /**
     * The init methods.
     */
    private String m__strInitMethods;

    /**
     * The test suite.
     */
    private String m__strTestSuite;

    /**
     * The store test.
     */
    private String m__strStoreTest;

    /**
     * The test values subtemplate.
     */
    private String m__strTestParametersValues;

    /**
     * The load test.
     */
    private String m__strLoadTest;

    /**
     * The update test.
     */
    private String m__strUpdateTest;

    /**
     * The update filter values subtemplate.
     */
    private String m__strUpdateFilterValues;

    /**
     * The test updated values subtemplate.
     */
    private String m__strTestParametersUpdatedValues;

    /**
     * The remove test.
     */
    private String m__strRemoveTest;

    /**
     * The remove filter values subtemplate.
     */
    private String m__strRemoveFilterValues;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a DAOTestTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectPackageName the value object's package name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param junitImports the JUnit imports.
     * @param javadoc the class javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param testValues the test values.
     * @param testUpdatedValues the test updated values.
     * @param constructor the class constructor.
     * @param innerMethods the inner methods.
     * @param initMethods the init methods.
     * @param testSuite the test suite.
     * @param storeTest the store test.
     * @param testParametersValues the test values
     * subtemplate.
     * @param loadTest the load test.
     * @param updateTest the update test.
     * @param updateFilterValues the update filter values
     * subtemplate.
     * @param testParametersUpdatedValues the test updated
     * values subtemplate.
     * @param removeTest the remove test.
     * @param removeFilterValues the remove filter values
     * subtemplate.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    protected AbstractMockDAOTestTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String daoPackageName,
        final String valueObjectPackageName,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String junitImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String testValues,
        final String testUpdatedValues,
        final String constructor,
        final String innerMethods,
        final String initMethods,
        final String testSuite,
        final String storeTest,
        final String testParametersValues,
        final String loadTest,
        final String updateTest,
        final String updateFilterValues,
        final String testParametersUpdatedValues,
        final String removeTest,
        final String removeFilterValues,
        final String classEnd,
        final Project project,
        final Task task)
    {
        super(project, task);
        immutableSetTableTemplate(tableTemplate);
        immutableSetMetaDataManager(metaDataManager);
        immutableSetHeader(header);
        immutableSetPackageDeclaration(packageDeclaration);
        immutableSetPackageName(packageName);
        immutableSetDAOPackageName(daoPackageName);
        immutableSetValueObjectPackageName(valueObjectPackageName);
        immutableSetProjectImports(projectImports);
        immutableSetAcmslImports(acmslImports);
        immutableSetJdkImports(jdkImports);
        immutableSetJUnitImports(junitImports);
        immutableSetJavadoc(javadoc);
        immutableSetClassDefinition(classDefinition);
        immutableSetClassStart(classStart);
        immutableSetTestValues(testValues);
        immutableSetTestUpdatedValues(testUpdatedValues);
        immutableSetConstructor(constructor);
        immutableSetInnerMethods(innerMethods);
        immutableSetInitMethods(initMethods);
        immutableSetTestSuite(testSuite);
        immutableSetStoreTest(storeTest);
        immutableSetTestParametersValues(testParametersValues);
        immutableSetLoadTest(loadTest);
        immutableSetUpdateTest(updateTest);
        immutableSetUpdateFilterValues(updateFilterValues);
        immutableSetTestParametersUpdatedValues(testParametersUpdatedValues);
        immutableSetRemoveTest(removeTest);
        immutableSetRemoveFilterValues(removeFilterValues);
        immutableSetClassEnd(classEnd);
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    private void immutableSetTableTemplate(final TableTemplate tableTemplate)
    {
        m__TableTemplate = tableTemplate;
    }

    /**
     * Specifies the table template.
     * @param tableTemplate the table template.
     */
    protected void setTableTemplate(final TableTemplate tableTemplate)
    {
        immutableSetTableTemplate(tableTemplate);
    }

    /**
     * Retrieves the table template.
     * @return such template.
     */
    public TableTemplate getTableTemplate()
    {
        return m__TableTemplate;
    }


    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    private void immutableSetMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        m__MetaDataManager = metaDataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metaDataManager the metadata manager.
     */
    protected void setMetaDataManager(
        final DatabaseMetaDataManager metaDataManager)
    {
        immutableSetMetaDataManager(metaDataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public DatabaseMetaDataManager getMetaDataManager()
    {
        return m__MetaDataManager;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    private void immutableSetPackageDeclaration(final String packageDeclaration)
    {
        m__strPackageDeclaration = packageDeclaration;
    }

    /**
     * Specifies the package declaration.
     * @param packageDeclaration the new package declaration.
     */
    protected void setPackageDeclaration(final String packageDeclaration)
    {
        immutableSetPackageDeclaration(packageDeclaration);
    }

    /**
     * Retrieves the package declaration.
     * @return such information.
     */
    public String getPackageDeclaration() 
    {
        return m__strPackageDeclaration;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the DAO package name.
     * @return such package.
     */
    private void immutableSetDAOPackageName(final String value)
    {
        m__strDAOPackageName = value;
    }

    /**
     * Specifies the DAO package name.
     * @param value such package.
     */
    protected void setDAOPackageName(final String value)
    {
        immutableSetDAOPackageName(value);
    }

    /**
     * Retrieves the DAO package name
     * @return such package.
     */
    public String getDAOPackageName()
    {
        return m__strDAOPackageName;
    }

    /**
     * Specifies the value object's package name.
     * @return such package.
     */
    private void immutableSetValueObjectPackageName(final String value)
    {
        m__strValueObjectPackageName = value;
    }

    /**
     * Specifies the value object's package name.
     * @param value such package.
     */
    protected void setValueObjectPackageName(final String value)
    {
        immutableSetValueObjectPackageName(value);
    }

    /**
     * Retrieves the value object's package name
     * @return such package.
     */
    public String getValueObjectPackageName()
    {
        return m__strValueObjectPackageName;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    private void immutableSetProjectImports(final String projectImports)
    {
        m__strProjectImports = projectImports;
    }

    /**
     * Specifies the project imports.
     * @param projectImports the new project imports.
     */
    protected void setProjectImports(final String projectImports)
    {
        immutableSetProjectImports(projectImports);
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
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    private void immutableSetAcmslImports(final String acmslImports)
    {
        m__strAcmslImports = acmslImports;
    }

    /**
     * Specifies the ACM-SL imports.
     * @param acmslImports the new ACM-SL imports.
     */
    protected void setAcmslImports(final String acmslImports)
    {
        immutableSetAcmslImports(acmslImports);
    }

    /**
     * Retrieves the ACM-SL imports.
     * @return such information.
     */
    public String getAcmslImports() 
    {
        return m__strAcmslImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    private void immutableSetJdkImports(final String jdkImports)
    {
        m__strJdkImports = jdkImports;
    }

    /**
     * Specifies the JDK imports.
     * @param jdkImports the new JDK imports.
     */
    protected void setJdkImports(final String jdkImports)
    {
        immutableSetJdkImports(jdkImports);
    }

    /**
     * Retrieves the JDK Imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the JUnit imports.
     * @param junitImports the new JUnit imports.
     */
    private void immutableSetJUnitImports(final String junitImports)
    {
        m__strJUnitImports = junitImports;
    }

    /**
     * Specifies the JUnit imports.
     * @param junitImports the new JUnit imports.
     */
    protected void setJUnitImports(final String junitImports)
    {
        immutableSetJUnitImports(junitImports);
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
     * Specifies the Javadoc.
     * @param javadoc the new Javadoc.
     */
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the Javadoc.
     * @param javadoc the new Javadoc.
     */
    protected void setJavadoc(final String javadoc)
    {
        immutableSetJavadoc(javadoc);
    }

    /**
     * Retrieves the javadoc.
     * @return such information.
     */
    public String getJavadoc() 
    {
        return m__strJavadoc;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    private void immutableSetClassDefinition(final String classDefinition)
    {
        m__strClassDefinition = classDefinition;
    }

    /**
     * Specifies the class definition.
     * @param classDefinition the new class definition.
     */
    protected void setClassDefinition(final String classDefinition)
    {
        immutableSetClassDefinition(classDefinition);
    }

    /**
     * Retrieves the class definition.
     * @return such information.
     */
    public String getClassDefinition() 
    {
        return m__strClassDefinition;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    private void immutableSetClassStart(final String classStart)
    {
        m__strClassStart = classStart;
    }

    /**
     * Specifies the class start.
     * @param classStart the new class start.
     */
    protected void setClassStart(final String classStart)
    {
        immutableSetClassStart(classStart);
    }

    /**
     * Retrieves the class start.
     * @return such information.
     */
    public String getClassStart() 
    {
        return m__strClassStart;
    }

    /**
     * Specifies the test values.
     * @param testValues the new test values.
     */
    private void immutableSetTestValues(final String testValues)
    {
        m__strTestValues = testValues;
    }

    /**
     * Specifies the test values.
     * @param testValues the new test values.
     */
    protected void setTestValues(final String testValues)
    {
        immutableSetTestValues(testValues);
    }

    /**
     * Retrieves the test values.
     * @return such information.
     */
    public String getTestValues() 
    {
        return m__strTestValues;
    }

    /**
     * Specifies the test updated values.
     * @param testUpdatedValues the new test updated values.
     */
    private void immutableSetTestUpdatedValues(final String testUpdatedValues)
    {
        m__strTestUpdatedValues = testUpdatedValues;
    }

    /**
     * Specifies the test updated values.
     * @param testUpdatedValues the new test updated values.
     */
    protected void setTestUpdatedValues(final String testUpdatedValues)
    {
        immutableSetTestUpdatedValues(testUpdatedValues);
    }

    /**
     * Retrieves the test updated values.
     * @return such information.
     */
    public String getTestUpdatedValues() 
    {
        return m__strTestUpdatedValues;
    }

    /**
     * Specifies the constructor.
     * @param constructor the new constructor.
     */
    private void immutableSetConstructor(final String constructor)
    {
        m__strConstructor = constructor;
    }

    /**
     * Specifies the constructor.
     * @param constructor the new constructor.
     */
    protected void setConstructor(final String constructor)
    {
        immutableSetConstructor(constructor);
    }

    /**
     * Retrieves the constructor.
     * @return such information.
     */
    public String getConstructor() 
    {
        return m__strConstructor;
    }

    /**
     * Specifies the inner methods.
     * @param innerMethods the new inner methods.
     */
    private void immutableSetInnerMethods(final String innerMethods)
    {
        m__strInnerMethods = innerMethods;
    }

    /**
     * Specifies the inner methods.
     * @param innerMethods the new inner methods.
     */
    protected void setInnerMethods(final String innerMethods)
    {
        immutableSetInnerMethods(innerMethods);
    }

    /**
     * Retrieves the inner methods.
     * @return such information.
     */
    public String getInnerMethods() 
    {
        return m__strInnerMethods;
    }

    /**
     * Specifies the init methods.
     * @param initMethods the new init methods.
     */
    private void immutableSetInitMethods(final String initMethods)
    {
        m__strInitMethods = initMethods;
    }

    /**
     * Specifies the init methods.
     * @param initMethods the new init methods.
     */
    protected void setInitMethods(final String initMethods)
    {
        immutableSetInitMethods(initMethods);
    }

    /**
     * Retrieves the init methods.
     * @return such information.
     */
    public String getInitMethods() 
    {
        return m__strInitMethods;
    }

    /**
     * Specifies the test suite.
     * @param testSuite the new test suite.
     */
    private void immutableSetTestSuite(final String testSuite)
    {
        m__strTestSuite = testSuite;
    }

    /**
     * Specifies the test suite.
     * @param testSuite the new test suite.
     */
    protected void setTestSuite(final String testSuite)
    {
        immutableSetTestSuite(testSuite);
    }

    /**
     * Retrieves the test suite.
     * @return such information.
     */
    public String getTestSuite() 
    {
        return m__strTestSuite;
    }

    /**
     * Specifies the store test.
     * @param storeTest the new store test.
     */
    private void immutableSetStoreTest(final String storeTest)
    {
        m__strStoreTest = storeTest;
    }

    /**
     * Specifies the store test.
     * @param storeTest the new store test.
     */
    protected void setStoreTest(final String storeTest)
    {
        immutableSetStoreTest(storeTest);
    }

    /**
     * Retrieves the store test.
     * @return such information.
     */
    public String getStoreTest() 
    {
        return m__strStoreTest;
    }

    /**
     * Specifies the test values subtemplate.
     * @param template the template.
     */
    private void immutableSetTestParametersValues(final String template)
    {
        m__strTestParametersValues = template;
    }

    /**
     * Specifies the test values subtemplate.
     * @param template the template.
     */
    protected void setTestParametersValues(final String template)
    {
        immutableSetTestParametersValues(template);
    }

    /**
     * Retrieves the test values subtemplate.
     * @return such source code template.
     */
    public String getTestParametersValues()
    {
        return m__strTestParametersValues;
    }

    /**
     * Specifies the load test.
     * @param loadTest the new load test.
     */
    private void immutableSetLoadTest(final String loadTest)
    {
        m__strLoadTest = loadTest;
    }

    /**
     * Specifies the load test.
     * @param loadTest the new load test.
     */
    protected void setLoadTest(final String loadTest)
    {
        immutableSetLoadTest(loadTest);
    }

    /**
     * Retrieves the load test.
     * @return such information.
     */
    public String getLoadTest() 
    {
        return m__strLoadTest;
    }

    /**
     * Specifies the update test.
     * @param updateTest the new update test.
     */
    private void immutableSetUpdateTest(final String updateTest)
    {
        m__strUpdateTest = updateTest;
    }

    /**
     * Specifies the update test.
     * @param updateTest the new update test.
     */
    protected void setUpdateTest(final String updateTest)
    {
        immutableSetUpdateTest(updateTest);
    }

    /**
     * Retrieves the update test.
     * @return such information.
     */
    public String getUpdateTest() 
    {
        return m__strUpdateTest;
    }


    /**
     * Specifies the test updated values subtemplate.
     * @param template the template.
     */
    private void immutableSetTestParametersUpdatedValues(final String template)
    {
        m__strTestParametersUpdatedValues = template;
    }

    /**
     * Specifies the test updated values subtemplate.
     * @param template the template.
     */
    protected void setTestParametersUpdatedValues(final String template)
    {
        immutableSetTestParametersUpdatedValues(template);
    }

    /**
     * Retrieves the test updated values subtemplate.
     * @return such source code template.
     */
    public String getTestParametersUpdatedValues()
    {
        return m__strTestParametersUpdatedValues;
    }

    /**
     * Specifies the update filter values.
     * @param updateFilterValues the new update filter values.
     */
    private void immutableSetUpdateFilterValues(final String updateFilterValues)
    {
        m__strUpdateFilterValues = updateFilterValues;
    }

    /**
     * Specifies the update filter values.
     * @param updateFilterValues the new update filter values.
     */
    protected void setUpdateFilterValues(final String updateFilterValues)
    {
        immutableSetUpdateFilterValues(updateFilterValues);
    }

    /**
     * Retrieves the update filter values.
     * @return such information.
     */
    public String getUpdateFilterValues()
    {
        return m__strUpdateFilterValues;
    }

    /**
     * Specifies the remove test.
     * @param removeTest the new remove test.
     */
    private void immutableSetRemoveTest(final String removeTest)
    {
        m__strRemoveTest = removeTest;
    }

    /**
     * Specifies the remove test.
     * @param removeTest the new remove test.
     */
    protected void setRemoveTest(final String removeTest)
    {
        immutableSetRemoveTest(removeTest);
    }

    /**
     * Retrieves the removeTest.
     * @return such information.
     */
    public String getRemoveTest() 
    {
        return m__strRemoveTest;
    }

    /**
     * Specifies the remove filter values.
     * @param removeFilterValues the new remove filter values.
     */
    private void immutableSetRemoveFilterValues(final String removeFilterValues)
    {
        m__strRemoveFilterValues = removeFilterValues;
    }

    /**
     * Specifies the remove filter values.
     * @param removeFilterValues the new remove filter values.
     */
    protected void setRemoveFilterValues(final String removeFilterValues)
    {
        immutableSetRemoveFilterValues(removeFilterValues);
    }

    /**
     * Retrieves the remove filter values.
     * @return such information.
     */
    public String getRemoveFilterValues()
    {
        return m__strRemoveFilterValues;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    private void immutableSetClassEnd(final String classEnd)
    {
        m__strClassEnd = classEnd;
    }

    /**
     * Specifies the class end.
     * @param classEnd the new class end.
     */
    protected void setClassEnd(final String classEnd)
    {
        immutableSetClassEnd(classEnd);
    }

    /**
     * Retrieves the class end.
     * @return such information.
     */
    public String getClassEnd() 
    {
        return m__strClassEnd;
    }

    /**
     * Retrieves the test name.
     * @return such name.
     */
    public String getTestName()
    {
        return
            getTestName(
                StringUtils.getInstance(), EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the test name.
     * @param stringUtils the StringUtils instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @return such name.
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String getTestName(
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        return
              "Mock"
            + stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      getTableTemplate().getTableName().toLowerCase()),
                  '_')
            + "DAOTest";
    }

    /**
     * Indicates if the test should be considered a suite or not.
     * @return such information.
     */
    public boolean isSuite()
    {
        return true;
    }


    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getTableTemplate());
    }

    /**
     * Builds the header for logging purposes.
     * @param tableTemplate the table template.
     * @return such header.
     * @precondition tableTemplate != null
     */
    protected String buildHeader(final TableTemplate tableTemplate)
    {
        return
              "Generating MockDAOTest for "
            + tableTemplate.getTableName() + ".";
    }
}
