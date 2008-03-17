//;-*- mode: java -*-
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
 * Filename: XMLDAOTestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Template for creating JUnit tests to ensure generated XML
 *              DAOs are working correctly.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

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
import java.util.Locale;
import java.util.Map;

/**
 * Template for creating JUnit tests to ensure generated XML DAOs
 * are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public class XMLDAOTestTemplate
    extends  AbstractXMLDAOTestTemplate
    implements XMLDAOTestTemplateDefaults
{
    /**
     * Builds a <code>XMLDAOTestTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectsPackageName the value objects' package name.
     */
    public XMLDAOTestTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String daoPackageName,
        final String valueObjectsPackageName)
    {
        super(
            tableTemplate,
            metadataManager,
//            (header != null) ? header : DEFAULT_HEADER,
            DEFAULT_HEADER,
            decoratorFactory,
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
            DEFAULT_NULLABLE_INSERTED_VALUES_CONVERSION,
            DEFAULT_LOAD_TEST,
            DEFAULT_UPDATE_TEST,
            DEFAULT_UPDATE_FILTER_VALUES,
            DEFAULT_TEST_PARAMETERS_UPDATED_VALUES,
            DEFAULT_NULLABLE_UPDATED_VALUES_CONVERSION,
            DEFAULT_REMOVE_TEST,
            DEFAULT_REMOVE_FILTER_VALUES,
            DEFAULT_CLASS_END);
    }

    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
     * @param header the header.
     * @return such source code.
     */
    protected String generateOutput(final String header)
    {
        return generateOutput(header, getMetadataManager());
    }
    
    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
     * @param header the header.
     * @param metadataManager the metadata manager.
     * @return such source code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getTableTemplate(),
                metadataManager,
                header,
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
                getNullableInsertedValuesConversion(),
                getLoadTest(),
                getUpdateTest(),
                getUpdateFilterValues(),
                getTestParametersUpdatedValues(),
                getNullableUpdatedValuesConversion(),
                getRemoveTest(),
                getRemoveFilterValues(),
                getClassEnd(),
                metadataManager.getMetadataTypeManager(),
                StringUtils.getInstance(),
                SingularPluralFormConverter.getInstance());
    }

    /**
     * Produces a text version of the template, weaving the
     * dynamic parts with the static skeleton.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
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
     * @param nullableInsertedValuesConversion the conversion
     * for nullable values in <code>insert</code> method..
     * @param loadTest the load test.
     * @param updateTest the update test.
     * @param updateFilterValues the update filter values
     * subtemplate.
     * @param testParametersUpdatedValues the test updated
     * values subtemplate.
     * @param nullableUpdatedValuesConversion the conversion
     * for nullable values in <code>update</code> method..
     * @param removeTest the remove test.
     * @param removeFilterValues the remove filter values
     * subtemplate.
     * @param classEnd the class end.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param singularPluralFormConverter the <code>SingularPluralFormConverter</code>
     * instance.
     * @return such source code.
     * @precondition metadataManager
     * @precondition metadataTypeManager != null
     * @precondition stringUtils != null
     * @precondition singularPluralFormConverter != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
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
        final String nullableInsertedValuesConversion,
        final String loadTest,
        final String updateTest,
        final String updateFilterValues,
        final String testParametersUpdatedValues,
        final String nullableUpdatedValuesConversion,
        final String removeTest,
        final String removeFilterValues,
        final String classEnd,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils,
        final EnglishGrammarUtils singularPluralFormConverter)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Locale t_Locale = Locale.getDefault();

        String t_strCapitalizedTableName =
            stringUtils.capitalize(
                singularPluralFormConverter.getSingular(
                    tableTemplate.getTableName().toLowerCase(t_Locale)),
                '_');

        MessageFormat t_HeaderFormatter = new MessageFormat(header);

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[] { tableTemplate.getTableName() }));

        MessageFormat t_PackageDeclarationFormatter =
            new MessageFormat(packageDeclaration);

        t_sbResult.append(
            t_PackageDeclarationFormatter.format(
                new Object[]{packageName}));

        MessageFormat t_ProjectImportsFormatter =
            new MessageFormat(projectImports);

        t_sbResult.append(
            t_ProjectImportsFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    valueObjectPackageName
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);
        t_sbResult.append(junitImports);

        MessageFormat t_JavadocFormatter = new MessageFormat(javadoc);

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName
                }));

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(classDefinition);

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        MessageFormat t_ClassStartFormatter = new MessageFormat(classStart);

        t_sbResult.append(
            t_ClassStartFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_sbResult.append(testValues);

        t_sbResult.append(testUpdatedValues);

        MessageFormat t_ConstructorFormatter = new MessageFormat(constructor);

        t_sbResult.append(
            t_ConstructorFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        t_sbResult.append(innerMethods);

        MessageFormat t_InitMethodsFormatter = new MessageFormat(initMethods);

        t_sbResult.append(
            t_InitMethodsFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName,
                    t_strCapitalizedTableName.substring(0,1).toLowerCase(
                        t_Locale)
                    + t_strCapitalizedTableName.substring(1)
                }));

        MessageFormat t_TestSuiteFormatter = new MessageFormat(testSuite);

        t_sbResult.append(
            t_TestSuiteFormatter.format(
                new Object[]
                {
                    t_strCapitalizedTableName
                }));

        MessageFormat t_TestParametersValuesFormatter =
            new MessageFormat(testParametersValues);

        MessageFormat t_NullableInsertedValuesConversionFormatter =
            new MessageFormat(nullableInsertedValuesConversion);

        MessageFormat t_UpdateFilterValuesFormatter =
            new MessageFormat(updateFilterValues);

        MessageFormat t_NullableUpdatedValuesConversionFormatter =
            new MessageFormat(nullableUpdatedValuesConversion);

        StringBuffer t_sbUpdateFilterValues =
            new StringBuffer();

        String[] t_astrPrimaryKeys =
            metadataManager.getPrimaryKey(
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

        int t_iColumnType;

        int t_iLength = (t_astrPrimaryKeys != null) ? t_astrPrimaryKeys.length : 0;

        for  (int t_iPkIndex = 0;
                  t_iPkIndex < t_iLength;
                  t_iPkIndex++) 
        {
            t_iColumnType =
                metadataManager.getColumnType(
                    tableTemplate.getTableName(),
                    t_astrPrimaryKeys[t_iPkIndex]);

            t_sbFindByPrimaryKeyTestParametersValues.append(
                t_TestParametersValuesFormatter.format(
                    new Object[]
                    {
                        metadataTypeManager.getNativeType(t_iColumnType)
                            .toUpperCase(t_Locale)
                    }));

            t_sbUpdateFilterValues.append(
                t_UpdateFilterValuesFormatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName,
                        stringUtils.capitalize(
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(
                                t_Locale),
                            '_')
                    }));

            t_sbRemoveFilterValues.append(
                t_RemoveFilterValuesFormatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName,
                        stringUtils.capitalize(
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(
                                t_Locale),
                            '_')
                    }));

            t_sbFindByPrimaryKeyParametersTypes.append(
                metadataTypeManager.getNativeType(t_iColumnType));

            if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
            {
                t_sbFindByPrimaryKeyParametersTypes.append(",");
                t_sbFindByPrimaryKeyTestParametersValues.append(",");
                t_sbRemoveFilterValues.append(",");
                t_sbUpdateFilterValues.append(",");
            }

            t_sbUpdateParametersTypes.append(
                metadataTypeManager.getNativeType(t_iColumnType));

            t_sbUpdateParametersTypes.append(",");
        }

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(
                tableTemplate.getTableName());

        t_iLength = (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        StringBuffer t_sbInsertTestParametersValues =
            new StringBuffer();

        StringBuffer t_sbInsertParametersTypes =
            new StringBuffer();

        MessageFormat t_TestParametersUpdatedValuesFormatter =
            new MessageFormat(testParametersUpdatedValues);

        StringBuffer t_sbTestParametersUpdatedValues =
            new StringBuffer();

        boolean t_bFirstNonPkColumn = true;

        for  (int t_iColumnIndex = 0;
                  t_iColumnIndex < t_iLength;
                  t_iColumnIndex++)
        {
            t_iColumnType =
                metadataManager.getColumnType(
                    tableTemplate.getTableName(),
                    t_astrColumnNames[t_iColumnIndex]);

            boolean t_bAllowsNull = false;

            t_bAllowsNull =
                metadataManager.allowsNull(
                    tableTemplate.getTableName(),
                    t_astrColumnNames[t_iColumnIndex]);

            if  (!metadataManager.isManagedExternally(
                     tableTemplate.getTableName(),
                     t_astrColumnNames[t_iColumnIndex]))
            {
                String t_strTestValue =
                    metadataTypeManager.getNativeType(t_iColumnType);

                Object[] t_aParams =
                    new Object[] { t_strTestValue.toUpperCase(t_Locale) };

                MessageFormat t_Formatter = t_TestParametersValuesFormatter;

                if  (   (t_bAllowsNull)
                     && (metadataTypeManager.isPrimitive(t_iColumnType)))
                {
                    t_Formatter =
                        t_NullableInsertedValuesConversionFormatter;

                    t_aParams =
                        new Object[]
                        {
                            metadataTypeManager.getSmartObjectType(
                                t_iColumnType, false),
                            t_strTestValue.toUpperCase(t_Locale)
                        };
                }

                t_sbInsertTestParametersValues.append(
                    t_Formatter.format(t_aParams));

                t_sbInsertParametersTypes.append(t_strTestValue);

                if  (!metadataManager.isPartOfPrimaryKey(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    if  (t_bFirstNonPkColumn)
                    {
                        t_sbUpdateFilterValues.append(",");
                        t_bFirstNonPkColumn = false;
                    }

                    if  (   (t_bAllowsNull)
                         && (metadataTypeManager.isPrimitive(t_iColumnType)))
                    {
                        t_sbTestParametersUpdatedValues.append(
                            t_NullableUpdatedValuesConversionFormatter.format(
                                t_aParams));
                    }
                    else
                    {
                        t_sbTestParametersUpdatedValues.append(
                            t_TestParametersUpdatedValuesFormatter.format(
                                new Object[]
                                {
                                    t_strTestValue.toUpperCase(t_Locale)
                                }));
                    }

                    t_sbUpdateParametersTypes.append(t_strTestValue);

                    if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                    {
                        t_sbUpdateParametersTypes.append(",");
                        t_sbTestParametersUpdatedValues.append(",");
                    }
                }

                if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                {
                    t_sbInsertParametersTypes.append(",");
                    t_sbInsertTestParametersValues.append(",");
                }
            }
        }

        MessageFormat t_StoreTestFormatter = new MessageFormat(storeTest);

        t_sbResult.append(
            t_StoreTestFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbInsertParametersTypes,
                    t_sbInsertTestParametersValues
                }));

        MessageFormat t_LoadTestFormatter = new MessageFormat(loadTest);

        t_sbResult.append(
            t_LoadTestFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbFindByPrimaryKeyParametersTypes,
                    t_sbFindByPrimaryKeyTestParametersValues
                }));

        MessageFormat t_UpdateTestFormatter = new MessageFormat(updateTest);

        t_sbResult.append(
            t_UpdateTestFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbUpdateParametersTypes,
                    t_sbUpdateFilterValues,
                    t_sbTestParametersUpdatedValues
                }));

        MessageFormat t_RemoveTestFormatter = new MessageFormat(removeTest);

        t_sbResult.append(
            t_RemoveTestFormatter.format(
                new Object[]
                {
                    daoPackageName,
                    t_strCapitalizedTableName,
                    t_sbFindByPrimaryKeyParametersTypes,
                    t_sbRemoveFilterValues
                }));

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
