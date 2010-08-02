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
 * Description: Template for creating JUnit tests to ensure generated Mock
 *              DAOs are working correctly.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some project-specific classes.
 */
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
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Template for creating JUnit tests to ensure generated Mock DAOs
 * are working correctly.
 * See <a href="bugzilla.acm-sl.org/show_bug.cgi?id=502">502</a>.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MockDAOTestTemplate
    extends  AbstractMockDAOTestTemplate
    implements  MockDAOTestTemplateDefaults
{
    /**
     * Builds a DAOTestTemplate using given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param daoPackageName the DAO's package name.
     * @param valueObjectsPackageName the value objects' package name.
     */
    public MockDAOTestTemplate(
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
            DEFAULT_LOAD_TEST,
            DEFAULT_UPDATE_TEST,
            DEFAULT_UPDATE_FILTER_VALUES,
            DEFAULT_TEST_PARAMETERS_UPDATED_VALUES,
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
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        MetadataManager t_MetadataManager = getMetadataManager();

        MetadataTypeManager t_MetadataTypeManager =
            t_MetadataManager.getMetadataTypeManager();

        String t_strCapitalizedTableName =
            t_StringUtils.capitalize(
                t_EnglishGrammarUtils.getSingular(
                    t_TableTemplate.getTableName().toLowerCase()),
                '_');
        /*
        try 
         */
        {
            MessageFormat t_Formatter = new MessageFormat(header);

            t_sbResult.append(
                t_Formatter.format(
                    new Object[] { t_TableTemplate.getTableName() }));

            t_Formatter = new MessageFormat(getPackageDeclaration());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]{getPackageName()}));

            t_Formatter = new MessageFormat(getProjectImports());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName,
                        getValueObjectPackageName()
                    }));

            t_sbResult.append(getAcmslImports());
            t_sbResult.append(getJdkImports());
            t_sbResult.append(getJUnitImports());

            t_Formatter = new MessageFormat(getJavadoc());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName
                    }));

            t_Formatter = new MessageFormat(getClassStart());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName
                    }));

            t_sbResult.append(getTestValues());

            t_sbResult.append(getTestUpdatedValues());

            t_Formatter = new MessageFormat(getConstructor());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName
                    }));

            t_sbResult.append(getInnerMethods());

            t_Formatter = new MessageFormat(getInitMethods());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName,
                          t_strCapitalizedTableName.substring(0, 1)
                              .toLowerCase()
                        + t_strCapitalizedTableName.substring(1)
                    }));

            t_Formatter = new MessageFormat(getTestSuite());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_strCapitalizedTableName
                    }));

            t_Formatter = new MessageFormat(getTestParametersValues());

            MessageFormat t_UpdateFilterValuesFormatter =
                new MessageFormat(getUpdateFilterValues());

            StringBuffer t_sbUpdateFilterValues =
                new StringBuffer();

            String[] t_astrPrimaryKeys =
                t_MetadataManager.getPrimaryKey(
                    t_TableTemplate.getTableName());

            StringBuffer t_sbFindByPrimaryKeyTestParametersValues =
                new StringBuffer();

            StringBuffer t_sbFindByPrimaryKeyParametersTypes =
                new StringBuffer();

            StringBuffer t_sbUpdateParametersTypes =
                new StringBuffer();

            MessageFormat t_RemoveFilterValuesFormatter =
                new MessageFormat(getRemoveFilterValues());

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
                                t_MetadataTypeManager.getNativeType(
                                    t_MetadataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
                                        t_astrPrimaryKeys[t_iPkIndex]))
                                .toUpperCase()
                            }));

                    t_sbUpdateFilterValues.append(
                        t_UpdateFilterValuesFormatter.format(
                            new Object[]
                            {
                                t_strCapitalizedTableName,
                                t_StringUtils.capitalize(
                                    t_astrPrimaryKeys[t_iPkIndex]
                                        .toLowerCase(),
                                    '_')
                            }));

                    t_sbRemoveFilterValues.append(
                        t_RemoveFilterValuesFormatter.format(
                            new Object[]
                            {
                                t_strCapitalizedTableName,
                                t_StringUtils.capitalize(
                                    t_astrPrimaryKeys[t_iPkIndex]
                                        .toLowerCase(),
                                    '_')
                            }));

                    t_sbFindByPrimaryKeyParametersTypes.append(
                        t_MetadataTypeManager.getNativeType(
                            t_MetadataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_astrPrimaryKeys[t_iPkIndex])));

                    if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                    {
                        t_sbFindByPrimaryKeyParametersTypes.append(",");
                    }

                    t_sbUpdateParametersTypes.append(
                        t_MetadataTypeManager.getNativeType(
                            t_MetadataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_astrPrimaryKeys[t_iPkIndex])));

                    t_sbUpdateParametersTypes.append(",");
                }
            }

            String[] t_astrColumnNames =
                t_MetadataManager.getColumnNames(
                    t_TableTemplate.getTableName());

            StringBuffer t_sbInsertTestParametersValues =
                new StringBuffer();

            StringBuffer t_sbInsertParametersTypes =
                new StringBuffer();

            MessageFormat t_TestParametersUpdatedValuesFormatter =
                new MessageFormat(getTestParametersUpdatedValues());

            StringBuffer t_sbTestParametersUpdatedValues =
                new StringBuffer();

            if  (t_astrColumnNames != null)
            {
                for  (int t_iColumnIndex = 0;
                      t_iColumnIndex < t_astrColumnNames.length;
                      t_iColumnIndex++)
                {
                    if  (!t_MetadataManager.isManagedExternally(
                             t_TableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        t_sbInsertTestParametersValues.append(
                            t_Formatter.format(
                                new Object[]
                                {
                                    t_MetadataTypeManager.getNativeType(
                                        t_MetadataManager.getColumnType(
                                            t_TableTemplate.getTableName(),
                                            t_astrColumnNames[
                                                t_iColumnIndex]))
                                    .toUpperCase()
                                }));

                        t_sbInsertParametersTypes.append(
                            t_MetadataTypeManager.getNativeType(
                                t_MetadataManager.getColumnType(
                                    t_TableTemplate.getTableName(),
                                    t_astrColumnNames[t_iColumnIndex])));

                        if  (!t_MetadataManager.isPartOfPrimaryKey(
                                 t_TableTemplate.getTableName(),
                                 t_astrColumnNames[t_iColumnIndex]))
                        {
                            t_sbTestParametersUpdatedValues.append(
                                t_TestParametersUpdatedValuesFormatter.format(
                                    new Object[]
                                    {
                                        t_MetadataTypeManager.getNativeType(
                                            t_MetadataManager.getColumnType(
                                                t_TableTemplate.getTableName(),
                                                t_astrColumnNames[
                                                    t_iColumnIndex]))
                                        .toUpperCase()
                                    }));

                            t_sbUpdateParametersTypes.append(
                                t_MetadataTypeManager.getNativeType(
                                    t_MetadataManager.getColumnType(
                                        t_TableTemplate.getTableName(),
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

            t_Formatter = new MessageFormat(getStoreTest());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName,
                        t_sbInsertParametersTypes,
                        t_sbInsertTestParametersValues
                    }));

            t_Formatter = new MessageFormat(getLoadTest());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName,
                        t_sbFindByPrimaryKeyParametersTypes,
                        t_sbFindByPrimaryKeyTestParametersValues
                    }));

            t_Formatter = new MessageFormat(getUpdateTest());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName,
                        t_sbUpdateParametersTypes,
                        t_sbUpdateFilterValues,
                        t_sbTestParametersUpdatedValues
                    }));

            t_Formatter = new MessageFormat(getRemoveTest());

            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getDAOPackageName(),
                        t_strCapitalizedTableName,
                        t_sbFindByPrimaryKeyParametersTypes,
                        t_sbRemoveFilterValues
                    }));

            t_sbResult.append(getClassEnd());
        }
        /*
        catch  (SQLException sqlException)
        {
            LogFactory.getLog(MockDAOTestTemplate.class).error(
                "database.meta.data.error",
                sqlException);
        }
         */

        return t_sbResult.toString();
    }
}
