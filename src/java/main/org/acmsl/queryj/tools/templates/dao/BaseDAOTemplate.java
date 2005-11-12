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
 * Description: Is able to create DAO interfaces for each table in the
 *              persistence model.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.regexpplugin.Helper;
import org.acmsl.commons.regexpplugin.RegexpEngine;
import org.acmsl.commons.regexpplugin.RegexpEngineNotFoundException;
import org.acmsl.commons.regexpplugin.RegexpManager;
import org.acmsl.commons.regexpplugin.RegexpPluginMisconfiguredException;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create DAO interfaces for each table in the
 * persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseDAOTemplate
    extends     AbstractBaseDAOTemplate
    implements  BaseDAOTemplateDefaults
{
    /**
     * Builds a <code>BaseDAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     */
    public BaseDAOTemplate(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String valueObjectPackageName)
    {
        super(
            tableTemplate,
            metadataManager,
            customSqlProvider,
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            valueObjectPackageName,
            DEFAULT_PROJECT_IMPORTS,
            ACMSL_IMPORTS,
            JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_CONSTANT_RECORD,
            DEFAULT_CONSTANT_ARRAY,
            DEFAULT_CONSTANT_ARRAY_ENTRY,
            DEFAULT_FIND_BY_STATIC_FIELD_METHOD,
            DEFAULT_FIND_BY_STATIC_FIELD_JAVADOC,
            DEFAULT_FIND_BY_STATIC_FIELD_DECLARATION,
            DEFAULT_FIND_BY_PRIMARY_KEY_METHOD,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_JAVADOC,
            DEFAULT_FIND_BY_PRIMARY_KEY_PK_DECLARATION,
            DEFAULT_INSERT_METHOD,
            DEFAULT_INSERT_PARAMETERS_JAVADOC,
            DEFAULT_INSERT_PARAMETERS_DECLARATION,
            DEFAULT_CREATE_METHOD,
            DEFAULT_CREATE_PARAMETERS_JAVADOC,
            DEFAULT_CREATE_PARAMETERS_DECLARATION,
            DEFAULT_UPDATE_METHOD,
            DEFAULT_UPDATE_PARAMETERS_JAVADOC,
            DEFAULT_UPDATE_PARAMETERS_DECLARATION,
            DEFAULT_DELETE_METHOD,
            DEFAULT_DELETE_PK_JAVADOC,
            DEFAULT_DELETE_PK_DECLARATION,
            DEFAULT_DELETE_BY_FK_METHOD,
            DEFAULT_DELETE_FK_JAVADOC,
            DEFAULT_DELETE_FK_DECLARATION,
            DEFAULT_CUSTOM_SELECT,
            DEFAULT_CUSTOM_SELECT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_UPDATE_OR_INSERT_PARAMETER_DECLARATION,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_JAVADOC,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_RETURN_JAVADOC,
            DEFAULT_CUSTOM_SELECT_FOR_UPDATE_PARAMETER_DECLARATION,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     * @throws InvalidTemplateException if the template is invalid.
     */
    protected String generateOutput()
        throws  InvalidTemplateException
    {
        return generateOutput(getMetadataManager());
    }
    
    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     * @param metadataManager the metadata manager.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition metadataManager != null
     */
    protected String generateOutput(final MetadataManager metadataManager)
        throws  InvalidTemplateException
    {
        return
            generateOutput(
                getTableTemplate(),
                metadataManager,
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getValueObjectPackageName(),
                getProjectImports(),
                getAcmslImports(),
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getConstantRecord(),
                getConstantArray(),
                getConstantArrayEntry(),
                getFindByStaticFieldMethod(),
                getFindByStaticFieldJavadoc(),
                getFindByStaticFieldDeclaration(),
                getFindByPrimaryKeyMethod(),
                getFindByPrimaryKeyPkJavadoc(),
                getFindByPrimaryKeyPkDeclaration(),
                getInsertMethod(),
                getInsertParametersJavadoc(),
                getInsertParametersDeclaration(),
                getCreateMethod(),
                getCreateParametersJavadoc(),
                getCreateParametersDeclaration(),
                getUpdateMethod(),
                getUpdateParametersJavadoc(),
                getUpdateParametersDeclaration(),
                getDeleteMethod(),
                getDeletePkJavadoc(),
                getDeletePkDeclaration(),
                getDeleteByFkMethod(),
                getDeleteFkJavadoc(),
                getDeleteFkDeclaration(),
                getCustomSelect(),
                getCustomSelectParameterJavadoc(),
                getCustomSelectParameterDeclaration(),
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomSelectForUpdate(),
                getCustomSelectForUpdateParameterJavadoc(),
                getCustomSelectForUpdateReturnJavadoc(),
                getCustomSelectForUpdateParameterDeclaration(),
                getClassEnd(),
                metadataManager.getMetadataTypeManager(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param constantRecord the constant record subtemplate.
     * @param constantArray the constant array.
     * @param constantArrayEntry the constant array entry.
     * @param findByStaticFieldMethod the find-by-static-field method.
     * @param findByStaticFieldJavadoc the field javadoc for
     * find-by-static-field method.
     * @param findByStaticFieldDeclaration the field declaration for
     * find-by-static-field method.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's parameters.
     * @param insertParametersDeclaration the declaration of the insert method's parameters.
     * @param createMethod the create method.
     * @param createParametersJavadoc the javadoc of the create method's parameters.
     * @param createParametersDeclaration the declaration of the create method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's parameters.
     * @param updateParametersDeclaration the declaration of the update method's parameters.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deleteByFkMethod the delete by fk method.
     * @param deleteFkJavadoc the delete FK javadoc.
     * @param deleteFkDeclaration the delete FK declaration.
     * @param customSelect the custom select template.
     * @param customSelectParameterJavadoc the Javadoc for the parameters of
     * the custom selects.
     * @param customSelectParameterDeclaration the parameter declaration of the
     * custom selects.
     * @param customSelectResultPropertyValues the properties of the result
     * set for custom selects.
     * @param customUpdateOrInsert the custom update template.
     * @param customUpdateOrInsertParameterJavadoc the Javadoc for the
     * parameters of the custom updates or inserts.
     * @param customUpdateOrInsertParameterDeclaration the parameter
     * declaration of the custom updates or inserts.
     * @param customSelectForUpdate the custom-select-for-update template.
     * @param customSelectForUpdateParameterJavadoc the Javadoc for the
     * parameters of the custom-select-for-update operations.
     * @param customSelectForUpdateReturnJavadoc the Javadoc for the
     * return of the custom-select-for-update operations.
     * @param customSelectForUpdateParameterDeclaration the parameter
     * declaration of the custom-select-for-update operations.
     * @param classEnd the class end.
     * @param metadataTypeManager the metadata type manager.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such code.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String valueObjectPackageName,
        final String projectImports,
        final String acmslImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String constantRecord,
        final String constantArray,
        final String constantArrayEntry,
        final String findByStaticFieldMethod,
        final String findByStaticFieldJavadoc,
        final String findByStaticFieldDeclaration,
        final String findByPrimaryKeyMethod,
        final String findByPrimaryKeyPkJavadoc,
        final String findByPrimaryKeyPkDeclaration,
        final String insertMethod,
        final String insertParametersJavadoc,
        final String insertParametersDeclaration,
        final String createMethod,
        final String createParametersJavadoc,
        final String createParametersDeclaration,
        final String updateMethod,
        final String updateParametersJavadoc,
        final String updateParametersDeclaration,
        final String deleteMethod,
        final String deletePkJavadoc,
        final String deletePkDeclaration,
        final String deleteByFkMethod,
        final String deleteFkJavadoc,
        final String deleteFkDeclaration,
        final String customSelect,
        final String customSelectParameterJavadoc,
        final String customSelectParameterDeclaration,
        final String customUpdateOrInsert,
        final String customUpdateOrInsertParameterJavadoc,
        final String customUpdateOrInsertParameterDeclaration,
        final String customSelectForUpdate,
        final String customSelectForUpdateParameterJavadoc,
        final String customSelectForUpdateReturnJavadoc,
        final String customSelectForUpdateParameterDeclaration,
        final String classEnd,
        final MetadataTypeManager metadataTypeManager,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
      throws  InvalidTemplateException
    {
        StringBuffer t_sbResult = new StringBuffer();

        MessageFormat t_HeaderFormatter = new MessageFormat(header);

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    tableTemplate.getTableName().toLowerCase()),
                '_');

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

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
                    valueObjectPackageName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(jdkImports);

        MessageFormat t_JavadocFormatter = new MessageFormat(javadoc);

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(classDefinition);

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(classStart);

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(tableTemplate.getTableName());

        String t_strTableComment =
            metadataManager.getTableComment(tableTemplate.getTableName());

        String t_strFieldType = null;

        if  (t_strTableComment != null)
        {
            int t_iKeyIndex = t_strTableComment.lastIndexOf("@static");

            if  (t_iKeyIndex >= 0)
            {
                MessageFormat t_ConstantRecordFormatter =
                    new MessageFormat(constantRecord);

                MessageFormat t_ConstantArrayFormatter =
                    new MessageFormat(constantArray);

                MessageFormat t_ConstantArrayEntryFormatter =
                    new MessageFormat(constantArrayEntry);

                StringBuffer t_sbConstantArrayEntries =
                    new StringBuffer();

                MessageFormat t_FindByStaticFieldMethodFormatter =
                    new MessageFormat(findByStaticFieldMethod);

                MessageFormat t_FindByStaticFieldJavadocFormatter =
                    new MessageFormat(findByStaticFieldJavadoc);

                MessageFormat t_FindByStaticFieldDeclarationFormatter =
                    new MessageFormat(findByStaticFieldDeclaration);

                String t_strDescriptionColumn =
                    t_strTableComment.substring(
                        t_iKeyIndex + "@static".length()).trim();

                Map t_mProperties =
                    queryContents(
                        tableTemplate.getTableName(),
                        t_strDescriptionColumn,
                        t_astrColumnNames.length,
                        metadataManager.getMetaData());

                if  (t_mProperties != null)
                {
                    Collection t_cEntries =
                        (Collection) t_mProperties.get(buildListKey());

                    if  (t_cEntries != null)
                    {
                        Iterator t_itEntries = t_cEntries.iterator();

                        while  (t_itEntries.hasNext())
                        {
                            String t_strColumnName =
                                (String) t_itEntries.next();

                            StringBuffer t_sbRecordPropertiesSpecification =
                                new StringBuffer();

                            int t_iType = -1;

                            String t_strValue = "";

                            Collection t_cColumns = 
                                (Collection)
                                    t_mProperties.get(
                                        buildColumnKey(t_strColumnName));

                            int t_iIndex = 0;

                            if  (t_cColumns != null)
                            {
                                Iterator t_itColumns = t_cColumns.iterator();

                                while  (t_itColumns.hasNext())
                                {
                                    t_iType =
                                        metadataManager.getColumnType(
                                            tableTemplate.getTableName(),
                                            t_astrColumnNames[t_iIndex++]);

                                    t_strValue = "" + t_itColumns.next();

                                    if  (metadataTypeManager.isString(t_iType))
                                    {
                                        t_strValue = "\"" + t_strValue + "\"";
                                    }
                                    else if  (!metadataTypeManager.isInteger(
                                                  t_iType))
                                    {
                                        t_strValue = "null";
                                    }

                                    t_sbRecordPropertiesSpecification.append(
                                        t_strValue);

                                    if  (t_itColumns.hasNext())
                                    {
                                        t_sbRecordPropertiesSpecification.append(", ");
                                    }
                                }
                            }

                            t_sbResult.append(
                                t_ConstantRecordFormatter.format(
                                    new Object[]
                                    {
                                        toJavaConstant(t_strColumnName),
                                        t_strCapitalizedValueObjectName,
                                        t_sbRecordPropertiesSpecification
                                    }));

                            t_sbConstantArrayEntries.append(
                                t_ConstantArrayEntryFormatter.format(
                                    new Object[]
                                    {
                                        toJavaConstant(t_strColumnName)
                                    }));

                            if  (t_itEntries.hasNext())
                            {
                                t_sbConstantArrayEntries.append(", ");
                            }
                        }

                        t_sbResult.append(
                            t_ConstantArrayFormatter.format(
                                new Object[]
                                {
                                    t_strCapitalizedValueObjectName,
                                    t_sbConstantArrayEntries
                                }));

                        String t_strFindByStaticFieldJavadoc =
                            t_FindByStaticFieldJavadocFormatter.format(
                                new Object[]
                                {
                                    t_strDescriptionColumn.toLowerCase(),
                                    t_strDescriptionColumn
                                });

                        t_strFieldType =
                            metadataTypeManager.getNativeType(
                                metadataManager.getColumnType(
                                    tableTemplate.getTableName(),
                                    t_strDescriptionColumn),
                                metadataManager.allowsNull(
                                    tableTemplate.getTableName(),
                                    t_strDescriptionColumn));

                        String t_strFindByStaticFieldDeclaration =
                            t_FindByStaticFieldDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_strFieldType,
                                    t_strDescriptionColumn.toLowerCase()
                                });

                        t_sbResult.append(
                            t_FindByStaticFieldMethodFormatter.format(
                                new Object[]
                                {
                                    tableTemplate.getTableName(),
                                    t_strDescriptionColumn.toLowerCase(),
                                    t_strFindByStaticFieldJavadoc,
                                    t_strCapitalizedValueObjectName,
                                    stringUtils.capitalize(
                                        t_strDescriptionColumn.toLowerCase(), '_'),
                                    t_strFindByStaticFieldDeclaration
                                }));
                    }
                }
            }
        }

        MessageFormat t_FindByPrimaryKeyFormatter =
            new MessageFormat(findByPrimaryKeyMethod);

        MessageFormat t_FindByPrimaryKeyPkJavadocFormatter =
            new MessageFormat(findByPrimaryKeyPkJavadoc);

        MessageFormat t_FindByPrimaryKeyPkDeclarationFormatter =
            new MessageFormat(findByPrimaryKeyPkDeclaration);

        MessageFormat t_InsertMethodFormatter =
            new MessageFormat(insertMethod);

        MessageFormat t_InsertParametersJavadocFormatter =
            new MessageFormat(insertParametersJavadoc);

        MessageFormat t_InsertParametersDeclarationFormatter =
            new MessageFormat(insertParametersDeclaration);

        MessageFormat t_CreateMethodFormatter =
            new MessageFormat(createMethod);

        MessageFormat t_CreateParametersJavadocFormatter =
            new MessageFormat(createParametersJavadoc);

        MessageFormat t_CreateParametersDeclarationFormatter =
            new MessageFormat(createParametersDeclaration);

        MessageFormat t_UpdateMethodFormatter =
            new MessageFormat(updateMethod);

        MessageFormat t_UpdateParametersJavadocFormatter =
            new MessageFormat(updateParametersJavadoc);

        MessageFormat t_UpdateParametersDeclarationFormatter =
            new MessageFormat(updateParametersDeclaration);

        String[] t_astrPrimaryKeys =
            metadataManager.getPrimaryKey(tableTemplate.getTableName());

        MessageFormat t_DeleteMethodFormatter =
            new MessageFormat(deleteMethod);

        MessageFormat t_DeleteByFkMethodFormatter =
            new MessageFormat(deleteByFkMethod);

        MessageFormat t_FkJavadocFormatter =
            new MessageFormat(deleteFkJavadoc);
        MessageFormat t_FkDeclarationFormatter =
            new MessageFormat(deleteFkDeclaration);

        StringBuffer t_sbDeleteMethod = new StringBuffer();
        StringBuffer t_sbPkJavadoc = new StringBuffer();
        StringBuffer t_sbPkDeclaration = new StringBuffer();
        StringBuffer t_sbDeleteByFkMethod = new StringBuffer();
        
        StringBuffer t_sbInsertPkJavadoc = new StringBuffer();
        StringBuffer t_sbInsertPkDeclaration = new StringBuffer();
        StringBuffer t_sbCreatePkJavadoc = new StringBuffer();
        StringBuffer t_sbCreatePkDeclaration = new StringBuffer();

        if  (t_astrPrimaryKeys != null)
        {
            StringBuffer t_sbSelectFields = new StringBuffer();
            StringBuffer t_sbFilterDeclaration = new StringBuffer();
            StringBuffer t_sbFilterValues = new StringBuffer();

            for  (int t_iPkIndex = 0;
                      t_iPkIndex < t_astrPrimaryKeys.length;
                      t_iPkIndex++)
            {
                t_strFieldType =
                    metadataTypeManager.getNativeType(
                        metadataManager.getColumnType(
                            tableTemplate.getTableName(),
                            t_astrPrimaryKeys[t_iPkIndex]));
                String t_strPkJavadoc =
                    t_FindByPrimaryKeyPkJavadocFormatter.format(
                        new Object[]
                        {
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase(),
                            t_astrPrimaryKeys[t_iPkIndex]
                        });

                String t_strPkDeclaration =
                    t_FindByPrimaryKeyPkDeclarationFormatter.format(
                        new Object[]
                        {
                            t_strFieldType,
                            t_astrPrimaryKeys[t_iPkIndex].toLowerCase()
                        });

                t_sbPkJavadoc.append(t_strPkJavadoc);

                t_sbPkDeclaration.append(t_strPkDeclaration);

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbPkDeclaration.append(",");
                }

                if  (!metadataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrPrimaryKeys[t_iPkIndex]))
                {
                    t_sbInsertPkJavadoc.append(t_strPkJavadoc);
                    t_sbInsertPkDeclaration.append(t_strPkDeclaration);
                    t_sbCreatePkJavadoc.append(t_strPkJavadoc);
                    t_sbCreatePkDeclaration.append(t_strPkDeclaration);
                }

                if  (t_iPkIndex < t_astrPrimaryKeys.length - 1)
                {
                    t_sbInsertPkDeclaration.append(",");
                    t_sbCreatePkDeclaration.append(",");
                }
            }

            t_sbResult.append(
                t_FindByPrimaryKeyFormatter.format(
                    new Object[]
                    {
                        tableTemplate.getTableName(),
                        t_sbPkJavadoc,
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                .toLowerCase()),
                            '_'),
                        t_sbPkDeclaration
                    }));

            t_sbDeleteMethod.append(
                t_DeleteMethodFormatter.format(
                    new Object[]
                    {
                        tableTemplate.getTableName(),
                        t_sbPkJavadoc,
                        t_sbPkDeclaration
                    }));
        }

        if  (t_astrColumnNames != null)
        {
            boolean t_bAnyNotPrimaryKey = false;

            StringBuffer t_sbBuildValueObjectRetrieval     =
                new StringBuffer();
            StringBuffer t_sbInsertParametersJavadoc       =
                new StringBuffer();
            StringBuffer t_sbInsertParametersDeclaration   =
                new StringBuffer();
            StringBuffer t_sbInsertParametersSpecification =
                new StringBuffer();
            StringBuffer t_sbCreateParametersJavadoc       =
                new StringBuffer();
            StringBuffer t_sbCreateParametersDeclaration   =
                new StringBuffer();
            StringBuffer t_sbCreateParametersSpecification =
                new StringBuffer();

            StringBuffer t_sbUpdateParametersJavadoc       =
                new StringBuffer();
            StringBuffer t_sbUpdateParametersDeclaration   =
                new StringBuffer();
            StringBuffer t_sbUpdateParametersSpecification =
                new StringBuffer();

            for  (int t_iColumnIndex = 0;
                      t_iColumnIndex < t_astrColumnNames.length;
                      t_iColumnIndex++)
            {
                int t_iColumnType =
                    metadataManager.getColumnType(
                        tableTemplate.getTableName(),
                        t_astrColumnNames[t_iColumnIndex]);

                t_strFieldType =
                    metadataTypeManager.getNativeType(t_iColumnType);

                boolean t_bAllowsNull =
                    metadataManager.allowsNull(
                        tableTemplate.getTableName(),
                        t_astrColumnNames[t_iColumnIndex]);

                if  (t_bAllowsNull)
                {
                    t_strFieldType =
                        metadataTypeManager.getObjectType(t_iColumnType);
                }

                t_strFieldType =
                    metadataTypeManager.getNativeType(
                        metadataManager.getColumnType(
                            tableTemplate.getTableName(),
                            t_astrColumnNames[t_iColumnIndex]),
                        metadataManager.allowsNull(
                            tableTemplate.getTableName(),
                            t_astrColumnNames[t_iColumnIndex]));

                if  (!metadataManager.isManagedExternally(
                         tableTemplate.getTableName(),
                         t_astrColumnNames[t_iColumnIndex]))
                {
                    if  (!metadataManager.isPartOfPrimaryKey(
                             tableTemplate.getTableName(),
                             t_astrColumnNames[t_iColumnIndex]))
                    {
                        t_bAnyNotPrimaryKey = true;

                        t_sbInsertParametersJavadoc.append(
                            t_InsertParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbCreateParametersJavadoc.append(
                            t_CreateParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbUpdateParametersJavadoc.append(
                            t_UpdateParametersJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrColumnNames[t_iColumnIndex].toLowerCase(),
                                    t_astrColumnNames[t_iColumnIndex]
                                }));

                        t_sbInsertParametersDeclaration.append(
                            t_InsertParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_strFieldType,
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        t_sbCreateParametersDeclaration.append(
                            t_CreateParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_strFieldType,
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        t_sbUpdateParametersDeclaration.append(
                            t_UpdateParametersDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_strFieldType,
                                    t_astrColumnNames[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        if  (t_iColumnIndex < t_astrColumnNames.length - 1)
                        {
                            t_sbInsertParametersDeclaration.append(",");
                            t_sbCreateParametersDeclaration.append(",");
                        }
                    }
                }
            }

            if  (   (t_bAnyNotPrimaryKey)
                 && (t_sbInsertPkDeclaration.length() > 0))
            {
                t_sbInsertPkDeclaration.append(",");
                t_sbCreatePkDeclaration.append(",");
            }

            t_sbResult.append(
                t_InsertMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                    .toLowerCase()),
                            '_'),
                        t_sbInsertPkJavadoc.toString(),
                        t_sbInsertParametersJavadoc,
                        t_sbInsertPkDeclaration,
                        t_sbInsertParametersDeclaration
                    }));

            t_sbResult.append(
                t_CreateMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                    .toLowerCase()),
                            '_'),
                        t_sbCreatePkJavadoc.toString(),
                        t_sbCreateParametersJavadoc,
                        t_sbCreatePkDeclaration,
                        t_sbCreateParametersDeclaration
                    }));

            t_sbResult.append(
                t_UpdateMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                    .toLowerCase()),
                            '_'),
                        t_sbPkJavadoc.toString(),
                        t_sbUpdateParametersJavadoc,
                        t_sbPkDeclaration,
                        t_sbUpdateParametersDeclaration
                    }));
        }

        t_sbResult.append(t_sbDeleteMethod);

        String[] t_astrReferredColumns = null;

        String[] t_astrReferredTables =
            metadataManager.getReferredTables(
                tableTemplate.getTableName());

        if  (   (t_astrReferredTables != null)
             && (t_astrReferredTables.length > 0))
        {
            for  (int t_iRefTableIndex = 0;
                      t_iRefTableIndex < t_astrReferredTables.length;
                      t_iRefTableIndex++)
            {
                String[][] t_aastrForeignKey =
                    metadataManager.getForeignKeys(
                        tableTemplate.getTableName(),
                        t_astrReferredTables[t_iRefTableIndex]);

                int t_iForeignKeyLength =
                    (t_aastrForeignKey != null)
                    ?  t_aastrForeignKey.length
                    :  0;

                for  (int t_iFkIndex = 0;
                          t_iFkIndex < t_iForeignKeyLength;
                          t_iFkIndex++)
                {
                    t_astrReferredColumns = t_aastrForeignKey[t_iFkIndex];
                    
                    String t_strReferredTableName =
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_astrReferredTables[t_iRefTableIndex]
                                   .toLowerCase()),
                            '_');

                    StringBuffer t_sbFkJavadoc = new StringBuffer();
                    StringBuffer t_sbFkDeclaration = new StringBuffer();

                    int t_iLength =
                        (t_astrReferredColumns != null)
                        ?  t_astrReferredColumns.length : 0;

                    for  (int t_iColumnIndex = 0;
                              t_iColumnIndex < t_iLength;
                              t_iColumnIndex++)
                    {
                        t_sbFkJavadoc.append(
                            t_FkJavadocFormatter.format(
                                new Object[]
                                {
                                    t_astrReferredColumns[t_iColumnIndex]
                                        .toLowerCase(),
                                    t_astrReferredColumns[t_iColumnIndex]
                                }));

                        t_sbFkDeclaration.append(
                            t_FkDeclarationFormatter.format(
                                new Object[]
                                {
                                    metadataTypeManager.getNativeType(
                                        metadataManager.getColumnType(
                                            tableTemplate.getTableName(),
                                            t_astrReferredColumns[
                                                t_iColumnIndex])),
                                    t_astrReferredColumns[t_iColumnIndex]
                                        .toLowerCase()
                                }));

                        if  (t_iColumnIndex < t_iLength - 1)
                        {
                            t_sbFkDeclaration.append(",");
                        }
                    }

                    t_sbDeleteByFkMethod.append(
                        t_DeleteByFkMethodFormatter.format(
                            new Object[]
                            {
                                stringUtils.capitalize(
                                    englishGrammarUtils.getSingular(
                                        t_astrReferredTables[t_iRefTableIndex]
                                            .toLowerCase()),
                                    '_'),
                                t_sbFkJavadoc,
                                t_strReferredTableName,
                                t_sbFkDeclaration
                            }));
                }
            }
        }

        t_sbResult.append(t_sbDeleteByFkMethod);

        t_sbResult.append(buildCustomSql(metadataTypeManager));

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }

    /**
     * Builds the custom templates.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     */
    protected String buildCustomSql(
        final MetadataTypeManager metadataTypeManager)
    {
        StringBuffer result = new StringBuffer();

        CustomSqlProvider provider = getCustomSqlProvider();

        if  (provider != null)
        {
            result.append(
                buildCustomSelects(
                    provider, getTableTemplate(), metadataTypeManager));
            result.append(
                buildCustomUpdates(
                    provider, getTableTemplate(), metadataTypeManager));
            result.append(
                buildCustomInserts(
                    provider, getTableTemplate(), metadataTypeManager));
            result.append(
                buildCustomDeletes(
                    provider, getTableTemplate(), metadataTypeManager));
            result.append(
                buildCustomSelectForUpdates(
                    provider, getTableTemplate(), metadataTypeManager));
        }

        return result.toString();
    }

    /**
     * Builds the custom selects.
     * @param provider the <code>CustomSqlProvider</code> instance.
     * @param tableTemplate the table template.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomSelects(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            buildCustomSelects(
                provider,
                metadataTypeManager,
                tableTemplate.getTableName(),
                getCustomSelect(),
                getCustomSelectParameterJavadoc(),
                getCustomSelectParameterDeclaration(),
                DAOTemplateUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom selects.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param metadataTypeManager the metadata type manager.
     * @param tableName the table name.
     * @param customSelect the custom select.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return such generated code.
     * @precondition tableName != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition daoTemplateUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelects(
        final CustomSqlProvider customSqlProvider,
        final MetadataTypeManager metadataTypeManager,
        final String tableName,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final DAOTemplateUtils daoTemplateUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = null;

        if  (customSqlProvider != null)
        {
            t_cContents = customSqlProvider.getCollection();
        }

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (t_SqlElement.SELECT.equals(t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metadataTypeManager,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomSelect(
                                customSqlProvider,
                                t_SqlElement,
                                customSelect,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                stringUtils));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the parameter templates with given parameters.
     * @param provider the CustomSqlProvider instance.
     * @param parameterRefs the parameter references.
     * @param parameterJavadoc the template.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterSpecification the parameter specification.
     * @param metadataTypemanager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition parameterSpecification != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String[] buildParameterTemplates(
        final CustomSqlProvider provider,
        final Collection parameterRefs,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterSpecification,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        String[] result = new String[5];

        StringBuffer t_sbParameterJavadoc = new StringBuffer();
        StringBuffer t_sbParameterDeclaration = new StringBuffer();
        StringBuffer t_sbParameterTypeSpecification = new StringBuffer();
        StringBuffer t_sbParameterValues = new StringBuffer();
        StringBuffer t_sbParameterSpecification = new StringBuffer();

        if  (parameterRefs != null)
        {
            MessageFormat t_ParameterJavadocFormatter =
                new MessageFormat(parameterJavadoc);

            MessageFormat t_ParameterDeclarationFormatter =
                new MessageFormat(parameterDeclaration);

            MessageFormat t_ParameterSpecificationFormatter =
                new MessageFormat(parameterSpecification);

            Iterator t_itParameterRefs =
                parameterRefs.iterator();

            boolean t_bFirstParameter = true;

            while  (t_itParameterRefs.hasNext())
            {
                ParameterRefElement t_ParameterRef =
                    (ParameterRefElement) t_itParameterRefs.next();

                if  (t_ParameterRef != null)
                {
                    ParameterElement t_Parameter =
                        provider.resolveReference(t_ParameterRef);

                    if  (t_Parameter == null)
                    {
                        try
                        {
                            LogFactory.getLog("custom-sql").warn(
                                  "Referenced parameter not found:"
                                + t_ParameterRef.getId());
                        }
                        catch  (final Throwable throwable)
                        {
                            // class-loading problem.
                        }
                    }
                    else
                    {
                        String t_strName = t_Parameter.getName();

                        if  (stringValidator.isEmpty(t_strName))
                        {
                            t_strName =
                                stringUtils.toJavaMethod(
                                    t_Parameter.getId(), '-');
                        }

                        if  (!t_bFirstParameter)
                        {
                            t_sbParameterDeclaration.append(",");
                            t_sbParameterValues.append(",");
                            t_sbParameterSpecification.append(",");
                        }

                        t_sbParameterJavadoc.append(
                            t_ParameterJavadocFormatter.format(
                                new Object[]
                                {
                                    t_strName
                                }));
                        t_sbParameterDeclaration.append(
                            t_ParameterDeclarationFormatter.format(
                                new Object[]
                                {
                                    t_Parameter.getType(),
                                    t_strName
                                }));
                        t_sbParameterSpecification.append(
                            t_ParameterSpecificationFormatter.format(
                                new Object[]
                                {
                                    t_strName
                                }));
                    }
                }

                t_bFirstParameter = false;
            }
        }

        result[0] = t_sbParameterJavadoc.toString();
        result[1] = t_sbParameterDeclaration.toString();
        result[2] = t_sbParameterTypeSpecification.toString();
        result[3] = t_sbParameterValues.toString();
        result[4] = t_sbParameterSpecification.toString();

        return result;
    }

    /**
     * Builds the result property values with given input.
     * @param provider the CustomSqlProvider instance.
     * @param resultRef the result reference.
     * @param resultPropertyValues the template.
     * @return such generated code.
     * @precondition provider != null
     * @precondition resultPropertyValues != null
     */
    protected String buildResultPropertyValues(
        final CustomSqlProvider provider,
        final ResultRefElement resultRef,
        final String resultPropertyValues)
    {
        String result = "";

        if  (resultRef != null)
        {
            ResultElement t_Result = provider.resolveReference(resultRef);

            if  (t_Result == null)
            {
                try
                {
                    LogFactory.getLog("custom-sql").warn(
                          "Referenced result not found:"
                        + resultRef.getId());
                }
                catch  (final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
            else
            {
                result =
                    buildResultPropertyValues(
                        provider,
                        t_Result.getPropertyRefs(),
                        resultPropertyValues,
                        StringUtils.getInstance());
            }
        }

        return result;
    }

    /**
     * Builds the result property values with given input.
     * @param provider the CustomSqlProvider instance.
     * @param propertyRefs the property references.
     * @param resultPropertyValues the template.
     * @param stringUtils the StringUtils instance.
     * @return such generated code.
     * @precondition provider != null
     * @precondition resultPropertyValues != null
     * @precondition stringUtils != null
     */
    protected String buildResultPropertyValues(
        final CustomSqlProvider provider,
        final Collection propertyRefs,
        final String resultPropertyValues,
        final StringUtils stringUtils)
    {
        StringBuffer result = new StringBuffer();

        if  (propertyRefs != null)
        {
            MessageFormat t_ResultPropertyValuesFormatter =
                new MessageFormat(resultPropertyValues);

            Iterator t_itPropertyRefs = propertyRefs.iterator();

            boolean t_bPreviousWasTheFirst = true;

            while  (t_itPropertyRefs.hasNext())
            {
                PropertyRefElement t_PropertyRef =
                    (PropertyRefElement) t_itPropertyRefs.next();

                if  (t_PropertyRef != null)
                {
                    PropertyElement t_Property =
                        provider.resolveReference(t_PropertyRef);

                    if  (t_Property == null)
                    {
                        try
                        {
                            LogFactory.getLog("custom-sql").warn(
                                  "Referenced property not found:"
                                + t_PropertyRef.getId());
                        }
                        catch  (final Throwable throwable)
                        {
                            // class-loading problem.
                        }
                    }
                    else
                    {
                        result.append(
                            t_ResultPropertyValuesFormatter.format(
                                new Object[]
                                {
                                    stringUtils.capitalize(
                                        t_Property.getType(), '_'),
                                    (   (t_Property.getIndex() > 0)
                                     ?  ("" + t_Property.getIndex())
                                     :  "\"" + t_Property.getColumnName() + "\""),
                                    t_Property.getName()
                                }));

                        if  (t_itPropertyRefs.hasNext())
                        {
                            result.append(",");
                        }
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom select.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customSelect the custom select template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelect != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelect(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelect,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final StringUtils stringUtils)
    {
        String result = "";

        MessageFormat t_CustomSelectFormatter =
            new MessageFormat(customSelect);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }
        else
        {
            throw
                new InvalidTemplateException(
                    "invalid.result",
                    new Object[] { sqlElement.getId()});
        }

        if  (t_Result == null)
        {
            throw
                new InvalidTemplateException(
                    "invalid.result.reference",
                    new Object[] { sqlElement.getId(), t_ResultRef.getId()});
        }

        String t_strResultClass = t_Result.getClassValue();

        if  (ResultElement.MULTIPLE.equalsIgnoreCase(
                t_Result.getMatches()))
        {
            t_strResultClass = "List";
        }

        result =
            t_CustomSelectFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    t_strResultClass,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom updates.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     * @throws RegexpEngineNotFoundException if the defined
     * regexp engine cannot be found.
     * @throws RegexpPluginMisconfiguredException if
     * RegexpPlugin cannot be configured properly.
     * @precondition provider != null
     * @precondition tableTemplate != null
     */
    protected String buildCustomUpdates(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate,
        final MetadataTypeManager metadataTypeManager)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                metadataTypeManager,
                SqlElement.UPDATE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom update or insert.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param tableName the table name.
     * @param metadataTypeManager the metadata type manager.
     * @param type the type of operation.
     * @param customTemplate the custom template.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterTypeSpecification the parameter type specification.
     * @param parameterValues the parameter values.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @param helper the Helper instance.
     * @return such generated code.
     * @precondition tableName != null
     * @precondition metadataTypeManager != null
     * @precondition type != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition daoTemplateUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     * @precondition helper != null
     */
    protected String buildCustomUpdatesOrInserts(
        final CustomSqlProvider customSqlProvider,
        final String tableName,
        final MetadataTypeManager metadataTypeManager,
        final String type,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterTypeSpecification,
        final String parameterValues,
        final DAOTemplateUtils daoTemplateUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator,
        final Helper helper)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = null;

        if  (customSqlProvider != null)
        {
            t_cContents = customSqlProvider.getCollection();
        }

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (type.equals(t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metadataTypeManager,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomUpdateOrInsert(
                                customSqlProvider,
                                t_SqlElement,
                                customTemplate,
                                t_astrParameterTemplates[0],
                                t_astrParameterTemplates[1],
                                t_astrParameterTemplates[2],
                                t_astrParameterTemplates[3],
                                stringUtils,
                                helper));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom update or insert.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customTemplate the custom template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param parameterTypeSpecification the parameter type specification.
     * @param parameterValues the generared parameter values.
     * @param stringUtils the StringUtils isntance.
     * @param helper the Helper instance.
     * @return the generated code.
     * @precondition provider != null
     * @param tableName != null
     * @precondition sqlElement != null
     * @precondition customTemplate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition parameterTypeSpecification != null
     * @precondition parameterValues != null
     * @precondition stringUtils != null
     * @precondition helper != null
     */
    protected String buildCustomUpdateOrInsert(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customTemplate,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterTypeSpecification,
        final String parameterValues,
        final StringUtils stringUtils,
        final Helper helper)
    {
        String result = "";

        MessageFormat t_CustomUpdateOrInsertFormatter =
            new MessageFormat(customTemplate);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }

        result =
            t_CustomUpdateOrInsertFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom inserts.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     * @precondition metadataTypeManager != null
     */
    protected String buildCustomInserts(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                metadataTypeManager,
                SqlElement.INSERT,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom deletes.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     * @precondition metadataTypeManager != null
     */
    protected String buildCustomDeletes(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            buildCustomUpdatesOrInserts(
                provider,
                tableTemplate.getTableName(),
                metadataTypeManager,
                SqlElement.DELETE,
                getCustomUpdateOrInsert(),
                getCustomUpdateOrInsertParameterJavadoc(),
                getCustomUpdateOrInsertParameterDeclaration(),
                getCustomUpdateOrInsertParameterTypeSpecification(),
                getCustomUpdateOrInsertParameterValues(),
                DAOTemplateUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance(),
                createHelper(RegexpManager.getInstance()));
    }

    /**
     * Builds the custom select-for-update operationss.
     * @param provider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @param metadataTypeManager the metadata type manager.
     * @return such generated code.
     * @precondition provider != null
     * @precondition tableTemplate != null
     * @precondition metadataTypeManager != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider provider,
        final TableTemplate tableTemplate,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            buildCustomSelectForUpdates(
                provider,
                tableTemplate.getTableName(),
                metadataTypeManager,
                getCustomSelectForUpdate(),
                getCustomSelectForUpdateParameterJavadoc(),
                getCustomSelectForUpdateReturnJavadoc(),
                getCustomSelectForUpdateParameterDeclaration(),
                DAOTemplateUtils.getInstance(),
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Builds the custom select-for-update operations.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param tableName the table name.
     * @param metadataTypeManager the metadata type manager.
     * @param customSelectForUpdate the custom-select-for-update template.
     * @param parameterJavadoc the Javadoc template
     * of the parameters.
     * @param customSelectForUpdateReturnJavadoc the Javadoc template
     * of the return.
     * @param parameterDeclaration the parameter declaration.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return such generated code.
     * @precondition tableName != null
     * @precondition metadataTypeManager != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition daoTemplateUtils != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected String buildCustomSelectForUpdates(
        final CustomSqlProvider customSqlProvider,
        final String tableName,
        final MetadataTypeManager metadataTypeManager,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String customSelectForUpdateReturnJavadoc,
        final String parameterDeclaration,
        final DAOTemplateUtils daoTemplateUtils,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        StringBuffer result = new StringBuffer();

        Collection t_cContents = null;

        if  (customSqlProvider != null)
        {
            t_cContents = customSqlProvider.getCollection();
        }

        if  (t_cContents != null)
        {
            Iterator t_itContentIterator = t_cContents.iterator();

            while  (t_itContentIterator.hasNext())
            {
                Object t_Content = t_itContentIterator.next();

                if  (t_Content instanceof SqlElement)
                {
                    SqlElement t_SqlElement =
                        (SqlElement) t_Content;

                    if  (   (t_SqlElement.SELECT_FOR_UPDATE.equals(
                                 t_SqlElement.getType()))
                         && (daoTemplateUtils.matches(
                                 tableName, t_SqlElement.getDao())))
                    {
                        String[] t_astrParameterTemplates =
                            buildParameterTemplates(
                                customSqlProvider,
                                t_SqlElement.getParameterRefs(),
                                parameterJavadoc,
                                parameterDeclaration,
                                "",
                                metadataTypeManager,
                                stringUtils,
                                stringValidator);

                        result.append(
                            buildCustomSelectForUpdate(
                                customSqlProvider,
                                t_SqlElement,
                                customSelectForUpdate,
                                t_astrParameterTemplates[0],
                                customSelectForUpdateReturnJavadoc,
                                t_astrParameterTemplates[1],
                                stringUtils));
                    }
                }
            }
        }

        return result.toString();
    }

    /**
     * Builds the complete custom select.
     * @param provider the CustomSqlProvider instance.
     * @param sqlElement the SqlElement instance.
     * @param customSelectForUpdate the custom select-for-update template.
     * @param parameterJavadoc the generated parameter Javadoc.
     * @param returnJavadoc the generated return Javadoc.
     * @param parameterDeclaration the generated parameter declaration.
     * @param stringUtils the StringUtils isntance.
     * @return the generated code.
     * @precondition provider != null
     * @precondition sqlElement != null
     * @precondition customSelectForUpdate != null
     * @precondition parameterJavadoc != null
     * @precondition returnJavadoc != null
     * @precondition parameterDeclaration != null
     * @precondition stringUtils != null
     */
    protected String buildCustomSelectForUpdate(
        final CustomSqlProvider provider,
        final SqlElement sqlElement,
        final String customSelectForUpdate,
        final String parameterJavadoc,
        final String returnJavadoc,
        final String parameterDeclaration,
        final StringUtils stringUtils)
    {
        String result = "";

        MessageFormat t_CustomSelectForUpdateFormatter =
            new MessageFormat(customSelectForUpdate);

        ResultElement t_Result = null;

        ResultRefElement t_ResultRef = sqlElement.getResultRef();

        if  (t_ResultRef != null)
        {
            t_Result = provider.resolveReference(t_ResultRef);
        }

        String t_strCustomSelectForUpdateSubtemplate = "";

        String t_strReturn = "void";

        String t_strConditionalReturn = "";

        String t_strReturnJavadoc = "";

        result =
            t_CustomSelectForUpdateFormatter.format(
                new Object[]
                {
                    sqlElement.getId(),
                    sqlElement.getDescription(),
                    parameterJavadoc,
                    t_strReturnJavadoc,
                    t_strReturn,
                    stringUtils.unCapitalizeStart(
                        stringUtils.capitalize(
                            sqlElement.getName(), '-')),
                    parameterDeclaration
                });

        return result;
    }

    /**
     * Builds the custom select-for-update template.
     * @param template the template.
     * @param subtemplate the subtemplate.
     * @param queryObjectName the query object name.
     * @param sqlQuery the sql query.
     * @param resultType the result type.
     * @return the generated code.
     * @precondition template != null
     * @precondition subtemplate != null
     * @precondition customSelectForUpdateWithNoReturn != null
     * @precondition queryObjectName != null
     * @precondition sqlQuery != null
     * @precondition resultType != null
     */
    protected String buildCustomSelectForUpdate(
        final String template,
        final String subtemplate,
        final String queryObjectName,
        final String sqlQuery,
        final String resultType)
    {
        String result = "";

        MessageFormat t_Formatter =
            new MessageFormat(template);

        result =
            t_Formatter.format(
                new Object[]
                {
                    queryObjectName,
                    sqlQuery,
                    resultType,
                    subtemplate
                });

        return result;
    }


    /**
     * Requests a regexp helper to given RegexpManager instance.
     * @param regexpManager the RegexpManager instance.
     * @return the regexp helper.
     * @throws RegexpEngineNotFoundException if the defined
     * regexp engine cannot be found.
     * @throws RegexpPluginMisconfiguredException if
     * RegexpPlugin cannot be configured properly.
     * @precondition regexpManager != null
     */
    protected synchronized Helper createHelper(
        final RegexpManager regexpManager)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return createHelper(regexpManager.getEngine());
    }

    /**
     * Requests a regexp helper to given RegexpEngine instance.
     * @param regexpEngine the RegexpEngine instance.
     * @return the regexp helper.
     * @precondition regexpEngine != null
     */
    protected Helper createHelper(
        final RegexpEngine regexpEngine)
      throws  RegexpEngineNotFoundException,
              RegexpPluginMisconfiguredException
    {
        return regexpEngine.createHelper();
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param descriptionColumn the description column.
     * @param columnCount the number of columns.
     * @param metaData the metadata.
     * @return such contents.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition descriptionColumn != null
     * @precondition columnCount > 0
     * @precondition metaData != null
     */
    protected Map queryContents(
        final String tableName,
        final String descriptionColumn,
        final int columnCount,
        final DatabaseMetaData metaData)
      throws  InvalidTemplateException
    {
        Map result = null;

        try
        {
            result =
                queryContents(
                    tableName,
                    descriptionColumn,
                    columnCount,
                    metaData.getConnection());
        }
        catch  (final SQLException sqlException)
        {
            throw
                new InvalidTemplateException(
                    "cannot.retrieve.connection",
                    new Object[0],
                    sqlException);
        }

        return result;
    }

    /**
     * Queries the contents of given table.
     * @param tableName the table name.
     * @param descriptionColumn the description column.
     * @param columnCount the number of columns.
     * @param connection the connection.
     * @return such contents.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition descriptionColumn != null
     * @precondition columnCount > 0
     * @precondition connection != null
     */
    protected Map queryContents(
        final String tableName,
        final String descriptionColumn,
        final int columnCount,
        final Connection connection)
      throws  InvalidTemplateException
    {
        Map result = new HashMap();

        Log t_Log = UniqueLogFactory.getLog(getClass());
        
        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            String t_strColumnName = null;

            String t_strRecordName = null;

            String t_strColumnValue = null;

            if  (t_rsResults != null)
            {
                Collection t_cRecordNames = new ArrayList();

                while  (t_rsResults.next())
                {
                    Collection t_cProperties = new ArrayList();

                    ResultSetMetaData t_rsMetaData =
                        t_rsResults.getMetaData();

                    for  (int t_iIndex = 1;
                          t_iIndex <= columnCount;
                          t_iIndex++)
                    {
                        t_strColumnName =
                            t_rsMetaData.getColumnName(t_iIndex);

                        t_strColumnValue =
                            t_rsResults.getString(t_iIndex);

                        if  (descriptionColumn.equalsIgnoreCase(t_strColumnName))
                        {
                            t_strRecordName = t_strColumnValue;
                        }

                        t_cProperties.add(t_strColumnValue);
                    }

                    t_cRecordNames.add(t_strRecordName);

                    result.put(
                        buildColumnKey(t_strRecordName), t_cProperties);
                }

                result.put(
                    buildListKey(),
                    t_cRecordNames);
            }
        }
        catch  (final SQLException sqlException)
        {
            throw
                new InvalidTemplateException(
                    "cannot.retrieve.fixed.table.contents",
                    new Object[] { tableName },
                    sqlException);
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        return result;
    }

    /**
     * Builds the column key for given column value.
     * @param value the value.
     * @return the key.
     * @precondition value != null
     */
    protected Object buildColumnKey(final String value)
    {
        return "column-key|:| " + value;
    }

    /**
     * Builds the list key.
     * @return such key.
     */
    protected Object buildListKey()
    {
        return ",.,list.,.";
    }

    /**
     * Translates given column value into a Java constant.
     * @param value the value.
     * @return the constant.
     * @precondition value != null
     */
    protected String toJavaConstant(final String value)
    {
        return toJavaConstant(value, createHelper(RegexpManager.getInstance()));
    }

    /**
     * Translates given column value into a Java constant.
     * @param value the value.
     * @param helper the <code>Helper</code> instance.
     * @return the constant.
     * @precondition value != null
     * @precondition stringUtils != null
     */
    protected String toJavaConstant(
        final String value, final Helper helper)
    {
        return helper.replaceAll(value, "\\W", "_").toUpperCase();
    }
}
