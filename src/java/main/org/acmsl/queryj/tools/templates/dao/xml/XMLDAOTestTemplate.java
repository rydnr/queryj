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
 * Description: Template for creating JUnit tests to ensure generated XML
 *              DAOs are working correctly.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
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
 * Template for creating JUnit tests to ensure generated XML DAOs
 * are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class XMLDAOTestTemplate
    extends  AbstractXMLDAOTestTemplate
    implements XMLDAOTestTemplateDefaults
{
    /**
     * Builds a <code>XMLDAOTestTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectsPackageName the value objects' package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public XMLDAOTestTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String daoPackageName,
        final String valueObjectsPackageName,
        final Project project,
        final Task task)
    {
        super(
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            daoPackageName,
            valueObjectsPackageName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JUNIT_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_TEST_VALUES,
            DEFAULT_TEST_UPDATED_VALUES,
            DEFAULT_CONSTRUCTOR,
            DEFAULT_INNER_METHODS,
            DEFAULT_INIT_METHODS,
            DEFAULT_TEST_SUITE,
            DEFAULT_STORE_TEST,
            DEFAULT_TEST_PARAMETERS_VALUES,
            DEFAULT_LOAD_TEST,
            DEFAULT_UPDATE_TEST,
            DEFAULT_UPDATE_FILTER_VALUES,
            DEFAULT_TEST_PARAMETERS_UPDATED_VALUES,
            DEFAULT_REMOVE_TEST,
            DEFAULT_REMOVE_FILTER_VALUES,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getDAOPackageName(),
                getValueObjectPackageName(),
                getProjectImports(),
                getAcmslImports(),
                getJdkImports(),
                getJUnitImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getTestValues(),
                getTestUpdatedValues(),
                getConstructor(),
                getInnerMethods(),
                getInitMethods(),
                getTestSuite(),
                getStoreTest(),
                getTestParametersValues(),
                getLoadTest(),
                getUpdateTest(),
                getUpdateFilterValues(),
                getTestParametersUpdatedValues(),
                getRemoveTest(),
                getRemoveFilterValues(),
                getClassEnd(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
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
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such source code.
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
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
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strCapitalizedTableName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableTemplate.getTableName().toLowerCase()),
                '_');
        /*
        try 
        {
        */
        MessageFormat t_Formatter = new MessageFormat(header);

        t_sbResult.append(
            t_Formatter.format(
                new Object[] { tableTemplate.getTableName() }));

        t_Formatter = new MessageFormat(packageDeclaration);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]{packageName}));

        t_Formatter = new MessageFormat(projectImports);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    valueObjectPackageName
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);
        t_sbResult.append(junitImports);

        t_Formatter = new MessageFormat(javadoc);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName
                }));

        t_Formatter = new MessageFormat(classDefinition);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_Formatter = new MessageFormat(classStart);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_sbResult.append(testValues);

        t_sbResult.append(testUpdatedValues);

        t_Formatter = new MessageFormat(constructor);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_sbResult.append(innerMethods);

        t_Formatter = new MessageFormat(initMethods);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName,
                    t_strCapitalizedTableName
                    .substring(0,1).toLowerCase()
                    + t_strCapitalizedTableName.substring(1)
                }));

        t_Formatter = new MessageFormat(testSuite);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_Formatter = new MessageFormat(testParametersValues);

        MessageFormat t_UpdateFilterValuesFormatter =
            new MessageFormat(updateFilterValues);

        StringBuffer t_sbUpdateFilterValues =
            new StringBuffer();

        String[] t_astrPrimaryKeys =
            metaDataManager.getPrimaryKeys(
                tableTemplate.getTableName());

        StringBuffer t_sbFindByPrimaryKeyTestParametersValues =
            new StringBuffer();

        StringBuffer t_sbFindByPrimaryKeyParametersTypes =
            new StringBuffer();

        StringBuffer t_sbUpdateParametersTypes =
            new StringBuffer();

        MessageFormat t_RemoveFilterValuesFormatter =
            new MessageFormat(removeFilterValues);

        StringBuffer t_sbRemoveFilterValues =
            new StringBuffer();

        if  (t_astrPrimaryKeys != null)
        {

            for  (int t_iPkIndex = 0;
                  t_iPkIndex < t_astrPrimaryKeys.length;
                  t_iPkIndex++) 
            {
                t_sbFindByPrimaryKeyTestParametersValues.append(
                    t_Formatter.format(
                        new Object[]
                        {
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_astrPrimaryKeys[t_iPkIndex]))
                            .toUpperCase()
                        }));

                t_sbUpdateFilterValues.append(
                    t_UpdateFilterValuesFormatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName,
                            stringUtils.capitalize(
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                '_')
                        }));

                t_sbRemoveFilterValues.append(
                    t_RemoveFilterValuesFormatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTableName,
                            stringUtils.capitalize(
                                t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                                '_')
                        }));

                t_sbFindByPrimaryKeyParametersTypes.append(
                    metaDataUtils.getNativeType(
                        metaDataManager.getColumnType(
                            tableTemplate.getTableName(),
                            t_astrPrimaryKeys[t_iPkIndex])));

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbFindByPrimaryKeyParametersTypes.append(",");
                }

                t_sbUpdateParametersTypes.append(
                    metaDataUtils.getNativeType(
                        metaDataManager.getColumnType(
                            tableTemplate.getTableName(),
                            t_astrPrimaryKeys[t_iPkIndex])));

                t_sbUpdateParametersTypes.append(",");
            }
        }

        String[] t_astrColumnNames =
            metaDataManager.getColumnNames(
                tableTemplate.getTableName());

        StringBuffer t_sbInsertTestParametersValues =
            new StringBuffer();

        StringBuffer t_sbInsertParametersTypes =
            new StringBuffer();

        MessageFormat t_TestParametersUpdatedValuesFormatter =
            new MessageFormat(testParametersUpdatedValues);

        StringBuffer t_sbTestParametersUpdatedValues =
            new StringBuffer();

        if  (t_astrColumnNames != null)
        {
            for  (int t_iColumnIndex = 0;
                  t_iColumnIndex < t_astrColumnNames.length;
                  t_iColumnIndex++)
            {
                if  (!metaDataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    t_sbInsertTestParametersValues.append(
                        t_Formatter.format(
                            new Object[]
                            {
                                metaDataUtils.getNativeType(
                                    metaDataManager.getColumnType(
                                        tableTemplate.getTableName(),
                                        t_astrColumnNames[
                                            t_iColumnIndex]))
                                .toUpperCase()
                            }));

                    t_sbInsertParametersTypes.append(
                        metaDataUtils.getNativeType(
                            metaDataManager.getColumnType(
                                tableTemplate.getTableName(),
                                t_astrColumnNames[t_iColumnIndex])));

                    if  (!metaDataManager.isPrimaryKey(
                             tableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        t_sbTestParametersUpdatedValues.append(
                            t_TestParametersUpdatedValuesFormatter.format(
                                new Object[]
                                {
                                    metaDataUtils.getNativeType(
                                        metaDataManager.getColumnType(
                                            tableTemplate.getTableName(),
                                            t_astrColumnNames[
                                                t_iColumnIndex]))
                                    .toUpperCase()
                                }));

                        t_sbUpdateParametersTypes.append(
                            metaDataUtils.getNativeType(
                                metaDataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_astrColumnNames[
                                        t_iColumnIndex])));

                        if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                        {
                            t_sbUpdateParametersTypes.append(",");
                        }
                    }

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbInsertParametersTypes.append(",");
                    }
                }
            }
        }

        t_Formatter = new MessageFormat(storeTest);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbInsertParametersTypes,
                    t_sbInsertTestParametersValues
                }));

        t_Formatter = new MessageFormat(loadTest);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbFindByPrimaryKeyParametersTypes,
                    t_sbFindByPrimaryKeyTestParametersValues
                }));

        t_Formatter = new MessageFormat(updateTest);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbUpdateParametersTypes,
                    t_sbUpdateFilterValues,
                    t_sbTestParametersUpdatedValues
                }));

        t_Formatter = new MessageFormat(removeTest);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbFindByPrimaryKeyParametersTypes,
                    t_sbRemoveFilterValues
                }));

        t_sbResult.append(classEnd);
        /*
        }
        catch  (final SQLException sqlException)
        {
            LogFactory.getLog(getClass()).error(
                "database.meta.data.error",
                sqlException);
        }
        */

        return t_sbResult.toString();
    }
}
