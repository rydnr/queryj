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
 * Description: Contains the elements required to create the DAO sources.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
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
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/**
 * Contains the elements required to create the DAO sources.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class AbstractDAOTemplate
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
     * The custom-sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

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
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The quote.
     */
    private String m__strQuote;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * The project imports.
     */
    private String m__strProjectImports;

    /**
     * The foreign DAO imports.
     */
    private String m__strForeignDAOImports;

    /**
     * The ACM-SL import statements.
     */
    private String m__strAcmslImports;

    /**
     * The JDK import statements.
     */
    private String m__strJdkImports;

    /**
     * The JDK extension import statements.
     */
    private String m__strJdkExtensionImports;

    /**
     * The Logging import statements.
     */
    private String m__strLoggingImports;

    /**
     * The class Javadoc.
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
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The find-by-primary-key method.
     */
    private String m__strFindByPrimaryKeyMethod;

    /**
     * The find-by-primary-key pk javadoc.
     */
    private String m__strFindByPrimaryKeyPkJavadoc;

    /**
     * The find-by-primary-key pk declaration.
     */
    private String m__strFindByPrimaryKeyPkDeclaration;

    /**
     * The find-by-primary-key pk values.
     */
    private String m__strFindByPrimaryKeyPkValues;

    /**
     * The process-connection-flags template.
     */
    private String m__strProcessConnectionFlags;

    /**
     * The restore-connection-flags template.
     */
    private String m__strRestoreConnectionFlags;

    /**
     * The process-statement-flags template.
     */
    private String m__strProcessStatementFlags;

    /**
     * The process-resultset-flags template.
     */
    private String m__strProcessResultSetFlags;

    /**
     * The process-resultset-flag subtemplate.
     */
    private String m__strProcessResultSetFlagSubtemplate;

    /**
     * The find-by-primary-key select fields.
     */
    private String m__strFindByPrimaryKeySelectFields;

    /**
     * The find-by-primary-key pk filter declaration.
     */
    private String m__strFindByPrimaryKeyFilterDeclaration;

    /**
     * The find-by-primary-key filter values.
     */
    private String m__strFindByPrimaryKeyFilterValues;

    /**
     * The build-value-object method.
     */
    private String m__strBuildValueObjectMethod;

    /**
     * The build-value-object value retrieval.
     */
    private String m__strBuildValueObjectValueRetrieval;

    /**
     * The insert method.
     */
    private String m__strInsertMethod;

    /**
     * The insert parameters Javadoc.
     */
    private String m__strInsertParametersJavadoc;

    /**
     * The insert parameters declaration.
     */
    private String m__strInsertParametersDeclaration;

    /**
     * The insert parameters specification.
     */
    private String m__strInsertParametersSpecification;

    /**
     * The insert keyword parameters specification.
     */
    private String m__strInsertKeywordParametersSpecification;

    /**
     * The update method.
     */
    private String m__strUpdateMethod;

    /**
     * The update parameters Javadoc.
     */
    private String m__strUpdateParametersJavadoc;

    /**
     * The update parameters declaration.
     */
    private String m__strUpdateParametersDeclaration;

    /**
     * The update parameters specification.
     */
    private String m__strUpdateParametersSpecification;

    /**
     * The update filter.
     */
    private String m__strUpdateFilter;

    /**
     * The delete method.
     */
    private String m__strDeleteMethod;

    /**
     * The delete PK javadoc.
     */
    private String m__strDeletePkJavadoc;

    /**
     * The delete PK declaration.
     */
    private String m__strDeletePkDeclaration;

    /**
     * The delete PK values.
     */
    private String m__strDeletePkValues;

    /**
     * The delete PK filter declaration.
     */
    private String m__strDeleteFilterDeclaration;

    /**
     * The delete filter values.
     */
    private String m__strDeleteFilterValues;

    /**
     * The delete with FK method.
     */
    private String m__strDeleteWithFkMethod;

    /**
     * The delete with FK PK javadoc.
     */
    private String m__strDeleteWithFkPkJavadoc;

    /**
     * The delete with FK PK declaration.
     */
    private String m__strDeleteWithFkPkDeclaration;

    /**
     * The delete with FK PK DAO delete request..
     */
    private String m__strDeleteWithFkDAODeleteRequest;

    /**
     * The delete with FK DAO FK values.
     */
    private String m__strDeleteWithFkPkValues;

    /**
     * The custom select.
     */
    private String m__strCustomSelect;

    /**
     * The custom select parameter javadoc.
     */
    private String m__strCustomSelectParameterJavadoc;

    /**
     * The custom select parameter declaration.
     */
    private String m__strCustomSelectParameterDeclaration;

    /**
     * The custom select parameter values.
     */
    private String m__strCustomSelectParameterValues;

    /**
     * The custom select result properties.
     */
    private String m__strCustomSelectResultPropertyValues;

    /**
     * The custom update or insert.
     */
    private String m__strCustomUpdateOrInsert;

    /**
     * The custom update or insert parameter javadoc.
     */
    private String m__strCustomUpdateOrInsertParameterJavadoc;

    /**
     * The custom update or insert parameter declaration.
     */
    private String m__strCustomUpdateOrInsertParameterDeclaration;

    /**
     * The custom update or insert parameter values.
     */
    private String m__strCustomUpdateOrInsertParameterValues;

    /**
     * The custom update or insert query line.
     */
    private String m__strCustomUpdateOrInsertQueryLine;

    /**
     * The custom select-for-update.
     */
    private String m__strCustomSelectForUpdate;

    /**
     * The custom select-for-update parameter javadoc.
     */
    private String m__strCustomSelectForUpdateParameterJavadoc;

    /**
     * The custom select-for-update parameter declaration.
     */
    private String m__strCustomSelectForUpdateParameterDeclaration;

    /**
     * The custom select-for-update parameter values.
     */
    private String m__strCustomSelectForUpdateParameterValues;

    /**
     * The custom select-for-update result properties.
     */
    private String m__strCustomSelectForUpdateResultPropertyValues;

    /**
     * The class end.
     */
    private String m__strClassEnd;

    /**
     * Builds a DAOTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param foreignDAOImports the foreign DAO imports.
     * @param acmslImports the ACM-SL imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstructor the class constructor.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param findByPrimaryKeyPkJavadoc the find by primary key pk javadoc.
     * @param findByPrimaryKeyPkDeclaration the find by primary key pk
     * declaration.
     * @param findByPrimaryKeyPkValues the find by primary key pk
     * values.
     * @param processConnectionFlags the <i>process connection flags</i>
     * template.
     * @param restoreConnectionFlags the <i>restore connection flags</i>
     * template.
     * @param processStatementFlags the <i>process statement flags</i>
     * template.
     * @param processResultSetFlags the <i>process resultset flags</i>
     * template.
     * @param processResultSetFlagSubtemplate the <i>process resultset flag</i>
     * subtemplate.
     * @param findByPrimaryKeySelectFields the find by primary key select
     * fields.
     * @param findByPrimaryKeyFilterDeclaration the find by primary key filter
     * declaration.
     * @param findByPrimaryKeyFilterValues the find by primary key filter
     * values.
     * @param buildValueObjectMethod the build value object method.
     * @param buildValueObjectValueRetrieval the build value object value
     * retrieval.
     * @param insertMethod the insert method.
     * @param insertParametersJavadoc the javadoc of the insert method's
     * parameters.
     * @param insertParametersDeclaration the declaration of the insert
     * method's parameters.
     * @param insertParametersSpecification the specification of the insert
     * method's parameters.
     * @param insertKeywordParametersSpecification the specification of the
     * insert method's keyword-based parameters.
     * @param updateMethod the update method.
     * @param updateParametersJavadoc the javadoc of the update method's
     * parameters.
     * @param updateParametersDeclaration the declaration of the update
     * method's parameters.
     * @param updateParametersSpecification the specification of the update
     * method's parameters.
     * @param updateFilter the update method's filter.
     * @param deleteMethod the delete method.
     * @param deletePkJavadoc the delete PK javadoc.
     * @param deletePkDeclaration the delete PK declaration.
     * @param deletePkValues the delete PK values.
     * @param deleteFilterDeclaration the delete filter declaration.
     * @param deleteFilterValues the delete filter values.
     * @param deleteWithFkMethod the delete method.
     * @param deleteWithFkPkJavadoc the delete with FK PK javadoc.
     * @param deleteWithFkPkDeclaration the delete with FK PK declaration.
     * @param deleteWithFkDAODeleteRequest the delete with FK DAO delete
     * request.
     * @param deleteWithFkPkValues the delete with FK PK values.
     * @param customSelect the custom select template.
     * @param customSelectParameterJavadoc the Javadoc for the parameters of
     * the custom selects.
     * @param customSelectParameterDeclaration the parameter declaration of the
     * custom selects.
     * @param customSelectParameterValues the parameter values of the custom
     * selects.
     * @param customSelectResultPropertyValues the properties of the result
     * set for custom selects.
     * @param customUpdateOrInsert the custom update template.
     * @param customUpdateOrInsertParameterJavadoc the Javadoc for the
     * parameters of the custom updates or inserts.
     * @param customUpdateOrInsertParameterDeclaration the parameter
     * declaration of the custom updates or inserts.
     * @param customUpdateOrInsertParameterValues the parameter values of
     * the custom updates or inserts.
     * @param customUpdateOrInsertQueryLine the custom update or insert
     * query line.
     * @param customSelectForUpdate the custom select-for-update template.
     * @param customSelectForUpdateParameterJavadoc the Javadoc for the
     * parameters of the custom select-for-update operations.
     * @param customSelectForUpdateParameterDeclaration the parameter
     * declaration of the custom select-for-update opèrations.
     * @param customSelectForUpdateParameterValues the parameter values of
     * the custom select-for-update operations.
     * @param customForUpdateSelectResultPropertyValues the properties
     * of the result* set for custom select-for-update operations.
     * @param classEnd the class end.
     */
    protected AbstractDAOTemplate(
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider       customSqlProvider,
        final String                  header,
        final String                  packageDeclaration,
        final String                  packageName,
        final String                  engineName,
        final String                  engineVersion,
        final String                  quote,
        final String                  basePackageName,
        final String                  repositoryName,
        final String                  projectImports,
        final String                  foreignDAOImports,
        final String                  acmslImports,
        final String                  jdkImports,
        final String                  jdkExtensionImports,
        final String                  loggingImports,
        final String                  javadoc,
        final String                  classDefinition,
        final String                  classStart,
        final String                  classConstructor,
        final String                  findByPrimaryKeyMethod,
        final String                  findByPrimaryKeyPkJavadoc,
        final String                  findByPrimaryKeyPkDeclaration,
        final String                  findByPrimaryKeyPkValues,
        final String                  processConnectionFlags,
        final String                  restoreConnectionFlags,
        final String                  processStatementFlags,
        final String                  processResultSetFlags,
        final String                  processResultSetFlagSubtemplate,
        final String                  findByPrimaryKeySelectFields,
        final String                  findByPrimaryKeyFilterDeclaration,
        final String                  findByPrimaryKeyFilterValues,
        final String                  buildValueObjectMethod,
        final String                  buildValueObjectValueRetrieval,
        final String                  insertMethod,
        final String                  insertParametersJavadoc,
        final String                  insertParametersDeclaration,
        final String                  insertParametersSpecification,
        final String                  insertKeywordParametersSpecification,
        final String                  updateMethod,
        final String                  updateParametersJavadoc,
        final String                  updateParametersDeclaration,
        final String                  updateParametersSpecification,
        final String                  updateFilter,
        final String                  deleteMethod,
        final String                  deletePkJavadoc,
        final String                  deletePkDeclaration,
        final String                  deletePkValues,
        final String                  deleteFilterDeclaration,
        final String                  deleteFilterValues,
        final String                  deleteWithFkMethod,
        final String                  deleteWithFkPkJavadoc,
        final String                  deleteWithFkPkDeclaration,
        final String                  deleteWithFkDAODeleteRequest,
        final String                  deleteWithFkPkValues,
        final String                  customSelect,
        final String                  customSelectParameterJavadoc,
        final String                  customSelectParameterDeclaration,
        final String                  customSelectParameterValues,
        final String                  customSelectResultPropertyValues,
        final String                  customUpdateOrInsert,
        final String                  customUpdateOrInsertParameterJavadoc,
        final String                  customUpdateOrInsertParameterDeclaration,
        final String                  customUpdateOrInsertParameterValues,
        final String                  customUpdateOrInsertQueryLine,
        final String                  customSelectForUpdate,
        final String                  customSelectForUpdateParameterJavadoc,
        final String                  customSelectForUpdateParameterDeclaration,
        final String                  customSelectForUpdateParameterValues,
        final String                  customSelectForUpdateResultPropertyValues,
        final String                  classEnd)
    {
        immutableSetTableTemplate(
            tableTemplate);

        immutableSetMetaDataManager(
            metaDataManager);

        immutableSetCustomSqlProvider(
            customSqlProvider);

        immutableSetHeader(
            header);

        immutableSetPackageDeclaration(
            packageDeclaration);

        immutableSetPackageName(
            packageName);

        immutableSetEngineName(
            engineName);

        immutableSetEngineVersion(
            engineVersion);

        immutableSetQuote(
            quote);

        immutableSetBasePackageName(
            basePackageName);

        immutableSetRepositoryName(
            repositoryName);

        immutableSetProjectImports(
            projectImports);

        immutableSetForeignDAOImports(
            foreignDAOImports);

        immutableSetAcmslImports(
            acmslImports);

        immutableSetJdkImports(
            jdkImports);

        immutableSetJdkExtensionImports(
            jdkExtensionImports);

        immutableSetLoggingImports(
            loggingImports);

        immutableSetJavadoc(
            javadoc);

        immutableSetClassDefinition(
            classDefinition);

        immutableSetClassStart(
            classStart);

        immutableSetClassConstructor(
            classConstructor);

        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);

        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);

        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);

        immutableSetFindByPrimaryKeyPkValues(
            findByPrimaryKeyPkValues);

        immutableSetProcessConnectionFlags(
            processConnectionFlags);

        immutableSetRestoreConnectionFlags(
            restoreConnectionFlags);

        immutableSetProcessStatementFlags(
            processStatementFlags);

        immutableSetProcessResultSetFlags(
            processResultSetFlags);

        immutableSetProcessResultSetFlagSubtemplate(
            processResultSetFlagSubtemplate);

        immutableSetFindByPrimaryKeySelectFields(
            findByPrimaryKeySelectFields);

        immutableSetFindByPrimaryKeyFilterDeclaration(
            findByPrimaryKeyFilterDeclaration);

        immutableSetFindByPrimaryKeyFilterValues(
            findByPrimaryKeyFilterValues);

        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);

        immutableSetBuildValueObjectValueRetrieval(
            buildValueObjectValueRetrieval);

        immutableSetInsertMethod(
            insertMethod);

        immutableSetInsertParametersJavadoc(
            insertParametersJavadoc);

        immutableSetInsertParametersDeclaration(
            insertParametersDeclaration);

        immutableSetInsertParametersSpecification(
            insertParametersSpecification);

        immutableSetInsertKeywordParametersSpecification(
            insertKeywordParametersSpecification);

        immutableSetUpdateMethod(
            updateMethod);

        immutableSetUpdateParametersJavadoc(
            updateParametersJavadoc);

        immutableSetUpdateParametersDeclaration(
            updateParametersDeclaration);

        immutableSetUpdateParametersSpecification(
            updateParametersSpecification);

        immutableSetUpdateFilter(
            updateFilter);

        immutableSetDeleteMethod(
            deleteMethod);

        immutableSetDeletePkJavadoc(
            deletePkJavadoc);

        immutableSetDeletePkDeclaration(
            deletePkDeclaration);

        immutableSetDeletePkValues(
            deletePkValues);

        immutableSetDeleteFilterDeclaration(
            deleteFilterDeclaration);

        immutableSetDeleteFilterValues(
            deleteFilterValues);

        immutableSetDeleteWithFkMethod(
            deleteWithFkMethod);

        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);

        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);

        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);

        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);

        immutableSetCustomSelect(
            customSelect);

        immutableSetCustomSelectParameterJavadoc(
            customSelectParameterJavadoc);

        immutableSetCustomSelectParameterDeclaration(
            customSelectParameterDeclaration);

        immutableSetCustomSelectParameterValues(
            customSelectParameterValues);

        immutableSetCustomSelectResultPropertyValues(
            customSelectResultPropertyValues);

        immutableSetCustomUpdateOrInsert(
            customUpdateOrInsert);

        immutableSetCustomUpdateOrInsertParameterJavadoc(
            customUpdateOrInsertParameterJavadoc);

        immutableSetCustomUpdateOrInsertParameterDeclaration(
            customUpdateOrInsertParameterDeclaration);

        immutableSetCustomUpdateOrInsertParameterValues(
            customUpdateOrInsertParameterValues);

        immutableSetCustomUpdateOrInsertQueryLine(
            customUpdateOrInsertQueryLine);

        immutableSetCustomSelectForUpdate(
            customSelectForUpdate);

        immutableSetCustomSelectForUpdateParameterJavadoc(
            customSelectForUpdateParameterJavadoc);

        immutableSetCustomSelectForUpdateParameterDeclaration(
            customSelectForUpdateParameterDeclaration);

        immutableSetCustomSelectForUpdateParameterValues(
            customSelectForUpdateParameterValues);

        immutableSetCustomSelectForUpdateResultPropertyValues(
            customSelectForUpdateResultPropertyValues);

        immutableSetClassEnd(
            classEnd);
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
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    private void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom-sql provider.
     * @return such provider.
     */
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
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
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void immutableSetEngineName(final String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(final String engineName)
    {
        immutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    public String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void immutableSetEngineVersion(final String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(final String engineVersion)
    {
        immutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    public String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    private void immutableSetQuote(final String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void setQuote(final String quote)
    {
        immutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    public String getQuote()
    {
        return m__strQuote;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    public String getRepositoryName()
    {
        return m__strRepositoryName;
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
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    private void immutableSetForeignDAOImports(final String foreignDAOImports)
    {
        m__strForeignDAOImports = foreignDAOImports;
    }

    /**
     * Specifies the foreign DAO imports.
     * @param foreignDAOImports the new foreign DAO imports.
     */
    protected void setForeignDAOImports(final String foreignDAOImports)
    {
        immutableSetForeignDAOImports(foreignDAOImports);
    }

    /**
     * Retrieves the foreign DAO imports.
     * @return such information.
     */
    public String getForeignDAOImports() 
    {
        return m__strForeignDAOImports;
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
     * Retrieves the JDK imports.
     * @return such information.
     */
    public String getJdkImports() 
    {
        return m__strJdkImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    private void immutableSetJdkExtensionImports(final String jdkExtensionImports)
    {
        m__strJdkExtensionImports = jdkExtensionImports;
    }

    /**
     * Specifies the JDK extension imports.
     * @param jdkExtensionImports the new JDK extension imports.
     */
    protected void setJdkExtensionImports(final String jdkExtensionImports)
    {
        immutableSetJdkExtensionImports(jdkExtensionImports);
    }

    /**
     * Retrieves the JDK extension imports.
     * @return such information.
     */
    public String getJdkExtensionImports() 
    {
        return m__strJdkExtensionImports;
    }


    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    private void immutableSetLoggingImports(final String loggingImports)
    {
        m__strLoggingImports = loggingImports;
    }

    /**
     * Specifies the logging imports.
     * @param loggingImports the new logging imports.
     */
    protected void setLoggingImports(final String loggingImports)
    {
        immutableSetLoggingImports(loggingImports);
    }

    /**
     * Retrieves the logging imports.
     * @return such information.
     */
    public String getLoggingImports() 
    {
        return m__strLoggingImports;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
     */
    private void immutableSetJavadoc(final String javadoc)
    {
        m__strJavadoc = javadoc;
    }

    /**
     * Specifies the javadoc.
     * @param javadoc the new javadoc.
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
     * Specifies the class constructor
     * @param constructor such source code.
     */
    private void immutableSetClassConstructor(final String constructor)
    {
        m__strClassConstructor = constructor;
    }

    /**
     * Specifies the class constructor
     * @param constructor such source code.
     */
    protected void setClassConstructor(final String constructor)
    {
        immutableSetClassConstructor(constructor);
    }

    /**
     * Retrieves the class constructor.
     * @return such source code.
     */
    public String getClassConstructor()
    {
        return m__strClassConstructor;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    private void immutableSetFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        String findByPrimaryKeyMethod)
    {
        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);
    }

    /**
     * Retrieves the find-by-primary-key method.
     * @return such method.
     */
    public String getFindByPrimaryKeyMethod()
    {
        return m__strFindByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    private void immutableSetFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        m__strFindByPrimaryKeyPkJavadoc = findByPrimaryKeyPkJavadoc;
    }

    /**
     * Specifies the find-by-primary-key pk Javadoc.
     * @param findByPrimaryKeyPkJavadoc such Javadoc.
     */
    protected void setFindByPrimaryKeyPkJavadoc(
        String findByPrimaryKeyPkJavadoc)
    {
        immutableSetFindByPrimaryKeyPkJavadoc(
            findByPrimaryKeyPkJavadoc);
    }

    /**
     * Retrieves the find-by-primary-key pk Javadoc.
     * @return such Javadoc.
     */
    public String getFindByPrimaryKeyPkJavadoc()
    {
        return m__strFindByPrimaryKeyPkJavadoc;
    }
    
    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    private void immutableSetFindByPrimaryKeyPkDeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        m__strFindByPrimaryKeyPkDeclaration =
            findByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk declaration.
     * @param findByPrimaryKeyPkDeclaration such declaration.
     */
    protected void setFindByPrimaryKeyPkdeclaration(
        String findByPrimaryKeyPkDeclaration)
    {
        immutableSetFindByPrimaryKeyPkDeclaration(
            findByPrimaryKeyPkDeclaration);
    }

    /**
     * Retrieves the find-by-primary-key pk declaration.
     * @return such declaration.
     */
    public String getFindByPrimaryKeyPkDeclaration()
    {
        return m__strFindByPrimaryKeyPkDeclaration;
    }

    /**
     * Specifies the find-by-primary-key pk values.
     * @param findByPrimaryKeyPkValues such values.
     */
    private void immutableSetFindByPrimaryKeyPkValues(
        String findByPrimaryKeyPkValues)
    {
        m__strFindByPrimaryKeyPkValues =
            findByPrimaryKeyPkValues;
    }

    /**
     * Specifies the find-by-primary-key pk values.
     * @param findByPrimaryKeyPkValues such values.
     */
    protected void setFindByPrimaryKeyPkvalues(
        String findByPrimaryKeyPkValues)
    {
        immutableSetFindByPrimaryKeyPkValues(
            findByPrimaryKeyPkValues);
    }

    /**
     * Retrieves the find-by-primary-key pk values.
     * @return such values.
     */
    public String getFindByPrimaryKeyPkValues()
    {
        return m__strFindByPrimaryKeyPkValues;
    }

    /**
     * Specifies the connection flags process template.
     * @param template such template.
     */
    private void immutableSetProcessConnectionFlags(final String template)
    {
        m__strProcessConnectionFlags = template;
    }

    /**
     * Specifies the connection flags process template.
     * @param template such template.
     */
    protected void setProcessConnectionFlags(final String template)
    {
        immutableSetProcessConnectionFlags(template);
    }

    /**
     * Retrieves the connection flags process template
     * @return such information.
     */
    public String getProcessConnectionFlags() 
    {
        return m__strProcessConnectionFlags;
    }

    /**
     * Specifies the connection flags restore template.
     * @param template such template.
     */
    private void immutableSetRestoreConnectionFlags(final String template)
    {
        m__strRestoreConnectionFlags = template;
    }

    /**
     * Specifies the connection flags restore template.
     * @param template such template.
     */
    protected void setRestoreConnectionFlags(final String template)
    {
        immutableSetRestoreConnectionFlags(template);
    }

    /**
     * Retrieves the connection flags restore template
     * @return such information.
     */
    public String getRestoreConnectionFlags() 
    {
        return m__strRestoreConnectionFlags;
    }

    /**
     * Specifies the statement flags process template.
     * @param template such template.
     */
    private void immutableSetProcessStatementFlags(final String template)
    {
        m__strProcessStatementFlags = template;
    }

    /**
     * Specifies the statement flags process template.
     * @param template such template.
     */
    protected void setProcessStatementFlags(final String template)
    {
        immutableSetProcessStatementFlags(template);
    }

    /**
     * Retrieves the statement flags process template
     * @return such information.
     */
    public String getProcessStatementFlags() 
    {
        return m__strProcessStatementFlags;
    }

    /**
     * Specifies the resultset flags process template.
     * @param template such template.
     */
    private void immutableSetProcessResultSetFlags(final String template)
    {
        m__strProcessResultSetFlags = template;
    }

    /**
     * Specifies the resultset flags process template.
     * @param template such template.
     */
    protected void setProcessResultSetFlags(final String template)
    {
        immutableSetProcessResultSetFlags(template);
    }

    /**
     * Retrieves the resultset flags process template
     * @return such information.
     */
    public String getProcessResultSetFlags() 
    {
        return m__strProcessResultSetFlags;
    }

    /**
     * Specifies the resultset flags process subtemplate.
     * @param template such template.
     */
    private void immutableSetProcessResultSetFlagSubtemplate(
        final String template)
    {
        m__strProcessResultSetFlagSubtemplate = template;
    }

    /**
     * Specifies the resultset flags process subtemplate.
     * @param template such template.
     */
    protected void setProcessResultSetFlagSubtemplate(final String template)
    {
        immutableSetProcessResultSetFlagSubtemplate(template);
    }

    /**
     * Retrieves the resultset flag process subtemplate
     * @return such information.
     */
    public String getProcessResultSetFlagSubtemplate() 
    {
        return m__strProcessResultSetFlagSubtemplate;
    }

    /**
     * Specifies the find-by-primary-key select fields.
     * @param findByPrimaryKeySelectFields such fields.
     */
    private void immutableSetFindByPrimaryKeySelectFields(
        String findByPrimaryKeySelectFields)
    {
        m__strFindByPrimaryKeySelectFields =
            findByPrimaryKeySelectFields;
    }

    /**
     * Specifies the find-by-primary-key select fields.
     * @param findByPrimaryKeySelectFields such fields.
     */
    protected void setFindByPrimaryKeySelectFields(
        String findByPrimaryKeySelectFields)
    {
        immutableSetFindByPrimaryKeySelectFields(
            findByPrimaryKeySelectFields);
    }

    /**
     * Retrieves the find-by-primary-key select fields.
     * @return such fields.
     */
    public String getFindByPrimaryKeySelectFields()
    {
        return m__strFindByPrimaryKeySelectFields;
    }

    /**
     * Specifies the find-by-primary-key filter declaration.
     * @param findByPrimaryKeyPkFilterDeclaration such declaration.
     */
    private void immutableSetFindByPrimaryKeyFilterDeclaration(
        String findByPrimaryKeyFilterDeclaration)
    {
        m__strFindByPrimaryKeyFilterDeclaration =
            findByPrimaryKeyFilterDeclaration;
    }

    /**
     * Specifies the find-by-primary-key filter declaration.
     * @param findByPrimaryKeyFilterDeclaration such declaration.
     */
    protected void setFindByPrimaryKeyFilterDeclaration(
        String findByPrimaryKeyFilterDeclaration)
    {
        immutableSetFindByPrimaryKeyFilterDeclaration(
            findByPrimaryKeyFilterDeclaration);
    }

    /**
     * Retrieves the find-by-primary-key filter declaration.
     * @return such declaration.
     */
    public String getFindByPrimaryKeyFilterDeclaration()
    {
        return m__strFindByPrimaryKeyFilterDeclaration;
    }

    /**
     * Specifies the find-by-primary-key filter values.
     * @param findByPrimaryKeyFilterValues such values.
     */
    private void immutableSetFindByPrimaryKeyFilterValues(
        String findByPrimaryKeyFilterValues)
    {
        m__strFindByPrimaryKeyFilterValues =
            findByPrimaryKeyFilterValues;
    }

    /**
     * Specifies the find-by-primary-key filter values.
     * @param findByPrimaryKeyFilterValues such values.
     */
    protected void setFindByPrimaryKeyFilterValues(
        String findByPrimaryKeyFilterValues)
    {
        immutableSetFindByPrimaryKeyFilterValues(
            findByPrimaryKeyFilterValues);
    }

    /**
     * Retrieves the find-by-primary-key filter values.
     * @return such values.
     */
    public String getFindByPrimaryKeyFilterValues()
    {
        return m__strFindByPrimaryKeyFilterValues;
    }
    
    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    private void immutableSetBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        m__strBuildValueObjectMethod = buildValueObjectMethod;
    }

    /**
     * Specifies the build-value-object method.
     * @param buildValueObjectMethod such method.
     */
    protected void setBuildValueObjectMethod(
        String buildValueObjectMethod)
    {
        immutableSetBuildValueObjectMethod(
            buildValueObjectMethod);
    }

    /**
     * Retrieves the build-value-object method.
     * @return such method.
     */
    public String getBuildValueObjectMethod()
    {
        return m__strBuildValueObjectMethod;
    }

    /**
     * Specifies the build-value-object value retrieval.
     * @param buildValueObjectValueRetrieval such method.
     */
    private void immutableSetBuildValueObjectValueRetrieval(
        String buildValueObjectValueRetrieval)
    {
        m__strBuildValueObjectValueRetrieval = buildValueObjectValueRetrieval;
    }

    /**
     * Specifies the build-value-object value retrieval.
     * @param buildValueObjectValueRetrieval such method.
     */
    protected void setBuildValueObjectValueRetrieval(
        String buildValueObjectValueRetrieval)
    {
        immutableSetBuildValueObjectValueRetrieval(
            buildValueObjectValueRetrieval);
    }

    /**
     * Retrieves the build-value-object value retrieval.
     * @return such method.
     */
    public String getBuildValueObjectValueRetrieval()
    {
        return m__strBuildValueObjectValueRetrieval;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    private void immutableSetInsertMethod(final String insertMethod)
    {
        m__strInsertMethod = insertMethod;
    }

    /**
     * Specifies the insert method.
     * @param insertMethod such method.
     */
    protected void setInsertMethod(final String insertMethod)
    {
        immutableSetInsertMethod(insertMethod);
    }

    /**
     * Retrieves the insert method.
     * @return such method.
     */
    public String getInsertMethod()
    {
        return m__strInsertMethod;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetInsertParametersJavadoc(final String javadoc)
    {
        m__strInsertParametersJavadoc = javadoc;
    }

    /**
     * Specifies the insert parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setInsertParametersJavadoc(final String javadoc)
    {
        immutableSetInsertParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the insert parameters javadoc.
     * @return such information.
     */
    public String getInsertParametersJavadoc()
    {
        return m__strInsertParametersJavadoc;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetInsertParametersDeclaration(final String declaration)
    {
        m__strInsertParametersDeclaration = declaration;
    }

    /**
     * Specifies the insert parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setInsertParametersDeclaration(final String declaration)
    {
        immutableSetInsertParametersDeclaration(declaration);
    }

    /**
     * Retrieves the insert parameters declaration.
     * @return such information.
     */
    public String getInsertParametersDeclaration()
    {
        return m__strInsertParametersDeclaration;
    }

    /**
     * Specifies the insert parameters specification.
     * @param specification such specification.
     */
    private void immutableSetInsertParametersSpecification(final String specification)
    {
        m__strInsertParametersSpecification = specification;
    }

    /**
     * Specifies the insert parameters specification.
     * @param specification such specification.
     */
    protected void setInsertParametersSpecification(final String specification)
    {
        immutableSetInsertParametersSpecification(specification);
    }

    /**
     * Retrieves the insert parameters specification.
     * @return such information.
     */
    public String getInsertParametersSpecification()
    {
        return m__strInsertParametersSpecification;
    }

    /**
     * Specifies the insert keyword-based parameters specification.
     * @param specification such specification.
     */
    private void immutableSetInsertKeywordParametersSpecification(
        String specification)
    {
        m__strInsertKeywordParametersSpecification = specification;
    }

    /**
     * Specifies the insert keyword-based parameters specification.
     * @param specification such specification.
     */
    protected void setInsertKeywordParametersSpecification(final String specification)
    {
        immutableSetInsertKeywordParametersSpecification(specification);
    }

    /**
     * Retrieves the insert keyword-based parameters specification.
     * @return such information.
     */
    public String getInsertKeywordParametersSpecification()
    {
        return m__strInsertKeywordParametersSpecification;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    private void immutableSetUpdateMethod(final String updateMethod)
    {
        m__strUpdateMethod = updateMethod;
    }

    /**
     * Specifies the update method.
     * @param updateMethod such method.
     */
    protected void setUpdateMethod(final String updateMethod)
    {
        immutableSetUpdateMethod(updateMethod);
    }

    /**
     * Retrieves the update method.
     * @return such method.
     */
    public String getUpdateMethod()
    {
        return m__strUpdateMethod;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    private void immutableSetUpdateParametersJavadoc(final String javadoc)
    {
        m__strUpdateParametersJavadoc = javadoc;
    }

    /**
     * Specifies the update parameters Javadoc.
     * @param javadoc such javadoc.
     */
    protected void setUpdateParametersJavadoc(final String javadoc)
    {
        immutableSetUpdateParametersJavadoc(javadoc);
    }

    /**
     * Retrieves the update parameters javadoc.
     * @return such information.
     */
    public String getUpdateParametersJavadoc()
    {
        return m__strUpdateParametersJavadoc;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    private void immutableSetUpdateParametersDeclaration(final String declaration)
    {
        m__strUpdateParametersDeclaration = declaration;
    }

    /**
     * Specifies the update parameters Declaration.
     * @param declaration such declaration.
     */
    protected void setUpdateParametersDeclaration(final String declaration)
    {
        immutableSetUpdateParametersDeclaration(declaration);
    }

    /**
     * Retrieves the update parameters declaration.
     * @return such information.
     */
    public String getUpdateParametersDeclaration()
    {
        return m__strUpdateParametersDeclaration;
    }

    /**
     * Specifies the update parameters Specification.
     * @param specification such specification.
     */
    private void immutableSetUpdateParametersSpecification(final String specification)
    {
        m__strUpdateParametersSpecification = specification;
    }

    /**
     * Specifies the update parameters Specification.
     * @param specification such specification.
     */
    protected void setUpdateParametersSpecification(final String specification)
    {
        immutableSetUpdateParametersSpecification(specification);
    }

    /**
     * Retrieves the update parameters specification.
     * @return such information.
     */
    public String getUpdateParametersSpecification()
    {
        return m__strUpdateParametersSpecification;
    }

    /**
     * Specifies the update filter.
     * @param updateFilter such filter.
     */
    private void immutableSetUpdateFilter(final String updateFilter)
    {
        m__strUpdateFilter = updateFilter;
    }

    /**
     * Specifies the update filter.
     * @param updateFilter such filter.
     */
    protected void setUpdateFilter(final String updateFilter)
    {
        immutableSetUpdateFilter(updateFilter);
    }

    /**
     * Retrieves the update filter.
     * @return such filter.
     */
    public String getUpdateFilter()
    {
        return m__strUpdateFilter;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteMethod(
        String deleteMethod)
    {
        m__strDeleteMethod = deleteMethod;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethod(
        String deleteMethod)
    {
        immutableSetDeleteMethod(
            deleteMethod);
    }

    /**
     * Retrieves the delete method.
     * @return such method.
     */
    public String getDeleteMethod()
    {
        return m__strDeleteMethod;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    private void immutableSetDeletePkJavadoc(
        String deletePkJavadoc)
    {
        m__strDeletePkJavadoc = deletePkJavadoc;
    }

    /**
     * Specifies the delete pk Javadoc.
     * @param deletePkJavadoc such Javadoc.
     */
    protected void setDeletePkJavadoc(
        String deletePkJavadoc)
    {
        immutableSetDeletePkJavadoc(
            deletePkJavadoc);
    }

    /**
     * Retrieves the delete pk Javadoc.
     * @return such Javadoc.
     */
    public String getDeletePkJavadoc()
    {
        return m__strDeletePkJavadoc;
    }
    
    /**
     * Specifies the delete pk declaration.
     * @param deletePkDeclaration such declaration.
     */
    private void immutableSetDeletePkDeclaration(
        String deletePkDeclaration)
    {
        m__strDeletePkDeclaration =
            deletePkDeclaration;
    }

    /**
     * Specifies the delete pk declaration.
     * @param deletePkdeclaration such declaration.
     */
    protected void setDeletePkdeclaration(
        String deletePkDeclaration)
    {
        immutableSetDeletePkDeclaration(
            deletePkDeclaration);
    }

    /**
     * Retrieves the delete pk declaration.
     * @return such declaration.
     */
    public String getDeletePkDeclaration()
    {
        return m__strDeletePkDeclaration;
    }

    /**
     * Specifies the delete pk values.
     * @param deletePkValues such values.
     */
    private void immutableSetDeletePkValues(
        String deletePkValues)
    {
        m__strDeletePkValues =
            deletePkValues;
    }

    /**
     * Specifies the delete pk values.
     * @param deletePkvalues such values.
     */
    protected void setDeletePkvalues(
        String deletePkValues)
    {
        immutableSetDeletePkValues(
            deletePkValues);
    }

    /**
     * Retrieves the delete pk values.
     * @return such values.
     */
    public String getDeletePkValues()
    {
        return m__strDeletePkValues;
    }

    /**
     * Specifies the delete filter declaration.
     * @param deletePkFilterDeclaration such declaration.
     */
    private void immutableSetDeleteFilterDeclaration(
        String deleteFilterDeclaration)
    {
        m__strDeleteFilterDeclaration =
            deleteFilterDeclaration;
    }

    /**
     * Specifies the delete filter declaration.
     * @param deleteFilterDeclaration such declaration.
     */
    protected void setDeleteFilterDeclaration(
        String deleteFilterDeclaration)
    {
        immutableSetDeleteFilterDeclaration(
            deleteFilterDeclaration);
    }

    /**
     * Retrieves the delete filter declaration.
     * @return such declaration.
     */
    public String getDeleteFilterDeclaration()
    {
        return m__strDeleteFilterDeclaration;
    }

    /**
     * Specifies the delete filter values.
     * @param deleteFilterValues such values.
     */
    private void immutableSetDeleteFilterValues(
        String deleteFilterValues)
    {
        m__strDeleteFilterValues =
            deleteFilterValues;
    }

    /**
     * Specifies the delete filter values.
     * @param deleteFilterValues such values.
     */
    protected void setDeleteFilterValues(
        String deleteFilterValues)
    {
        immutableSetDeleteFilterValues(
            deleteFilterValues);
    }

    /**
     * Retrieves the delete filter values.
     * @return such values.
     */
    public String getDeleteFilterValues()
    {
        return m__strDeleteFilterValues;
    }
    
    /**
     * Specifies the delete with FK method.
     * @param deleteWithFkMethod such method.
     */
    private void immutableSetDeleteWithFkMethod(
        String deleteWithFkMethod)
    {
        m__strDeleteWithFkMethod = deleteWithFkMethod;
    }

    /**
     * Specifies the delete with FK method.
     * @param deleteWithFkMethod such method.
     */
    protected void setDeleteWithFkMethod(
        String deleteWithFkMethod)
    {
        immutableSetDeleteWithFkMethod(
            deleteWithFkMethod);
    }

    /**
     * Retrieves the delete with FK method.
     * @return such method.
     */
    public String getDeleteWithFkMethod()
    {
        return m__strDeleteWithFkMethod;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    private void immutableSetDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        m__strDeleteWithFkPkJavadoc = deleteWithFkPkJavadoc;
    }

    /**
     * Specifies the delete with FK PK Javadoc.
     * @param deleteWithFkPkJavadoc such Javadoc.
     */
    protected void setDeleteWithFkPkJavadoc(
        String deleteWithFkPkJavadoc)
    {
        immutableSetDeleteWithFkPkJavadoc(
            deleteWithFkPkJavadoc);
    }

    /**
     * Retrieves the delete with FK PK Javadoc.
     * @return such Javadoc.
     */
    public String getDeleteWithFkPkJavadoc()
    {
        return m__strDeleteWithFkPkJavadoc;
    }
    
    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkDeclaration such declaration.
     */
    private void immutableSetDeleteWithFkPkDeclaration(
        String deleteWithFkPkDeclaration)
    {
        m__strDeleteWithFkPkDeclaration =
            deleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK PK declaration.
     * @param deleteWithFkPkdeclaration such declaration.
     */
    protected void setDeleteWithFkPkdeclaration(
        String deleteWithFkPkDeclaration)
    {
        immutableSetDeleteWithFkPkDeclaration(
            deleteWithFkPkDeclaration);
    }

    /**
     * Retrieves the delete with FK PK declaration.
     * @return such declaration.
     */
    public String getDeleteWithFkPkDeclaration()
    {
        return m__strDeleteWithFkPkDeclaration;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    private void immutableSetDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        m__strDeleteWithFkDAODeleteRequest =
            deleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK DAO delete request.
     * @param deleteWithFkDAODeleteRequest such request.
     */
    protected void setDeleteWithFkDAODeleteRequest(
        String deleteWithFkDAODeleteRequest)
    {
        immutableSetDeleteWithFkDAODeleteRequest(
            deleteWithFkDAODeleteRequest);
    }

    /**
     * Retrieves the delete with FK DAO delete request.
     * @return such request.
     */
    public String getDeleteWithFkDAODeleteRequest()
    {
        return m__strDeleteWithFkDAODeleteRequest;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    private void immutableSetDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        m__strDeleteWithFkPkValues =
            deleteWithFkPkValues;
    }

    /**
     * Specifies the delete with FK PK values.
     * @param deleteWithFkPkValues such values.
     */
    protected void setDeleteWithFkPkValues(
        String deleteWithFkPkValues)
    {
        immutableSetDeleteWithFkPkValues(
            deleteWithFkPkValues);
    }

    /**
     * Retrieves the delete with FK PK values.
     * @return such values.
     */
    public String getDeleteWithFkPkValues()
    {
        return m__strDeleteWithFkPkValues;
    }

    /**
     * Specifies the custom select template.
     * @param select such template.
     */
    private void immutableSetCustomSelect(
        final String select)
    {
        m__strCustomSelect = select;
    }

    /**
     * Specifies the custom select template.
     * @param select such template.
     */
    protected void setCustomSelect(
        final String select)
    {
        immutableSetCustomSelect(select);
    }

    /**
     * Retrieves the custom select template.
     * @return such template.
     */
    public String getCustomSelect()
    {
        return m__strCustomSelect;
    }

    /**
     * Specifies the custom select parameter Javadoc template.
     * @param select such template.
     */
    private void immutableSetCustomSelectParameterJavadoc(
        final String template)
    {
        m__strCustomSelectParameterJavadoc = template;
    }

    /**
     * Specifies the custom select parameter Javadoc template.
     * @param select such template.
     */
    protected void setCustomSelectParameterJavadoc(
        final String select)
    {
        immutableSetCustomSelectParameterJavadoc(select);
    }

    /**
     * Retrieves the custom select parameter Javadoc template.
     * @return such template.
     */
    public String getCustomSelectParameterJavadoc()
    {
        return m__strCustomSelectParameterJavadoc;
    }

    /**
     * Specifies the custom select parameter declaration template.
     * @param select such template.
     */
    private void immutableSetCustomSelectParameterDeclaration(
        final String template)
    {
        m__strCustomSelectParameterDeclaration = template;
    }

    /**
     * Specifies the custom select parameter declaration template.
     * @param select such template.
     */
    protected void setCustomSelectParameterDeclaration(
        final String select)
    {
        immutableSetCustomSelectParameterDeclaration(select);
    }

    /**
     * Retrieves the custom select parameter declaration template.
     * @return such template.
     */
    public String getCustomSelectParameterDeclaration()
    {
        return m__strCustomSelectParameterDeclaration;
    }

    /**
     * Specifies the custom select parameter values template.
     * @param select such template.
     */
    private void immutableSetCustomSelectParameterValues(
        final String template)
    {
        m__strCustomSelectParameterValues = template;
    }

    /**
     * Specifies the custom select parameter values template.
     * @param select such template.
     */
    protected void setCustomSelectParameterValues(
        final String select)
    {
        immutableSetCustomSelectParameterValues(select);
    }

    /**
     * Retrieves the custom select parameter values template.
     * @return such template.
     */
    public String getCustomSelectParameterValues()
    {
        return m__strCustomSelectParameterValues;
    }

    /**
     * Specifies the custom select result properties' values template.
     * @param select such template.
     */
    private void immutableSetCustomSelectResultPropertyValues(
        final String template)
    {
        m__strCustomSelectResultPropertyValues = template;
    }

    /**
     * Specifies the custom select result properties' values template.
     * @param select such template.
     */
    protected void setCustomSelectResultPropertyValues(
        final String select)
    {
        immutableSetCustomSelectResultPropertyValues(select);
    }

    /**
     * Retrieves the custom select result properties' values template.
     * @return such template.
     */
    public String getCustomSelectResultPropertyValues()
    {
        return m__strCustomSelectResultPropertyValues;
    }

    /**
     * Specifies the custom update or insert template.
     * @param updateOrInsert such template.
     */
    private void immutableSetCustomUpdateOrInsert(
        final String updateOrInsert)
    {
        m__strCustomUpdateOrInsert = updateOrInsert;
    }

    /**
     * Specifies the custom update or insert template.
     * @param updateOrInsert such template.
     */
    protected void setCustomUpdateOrInsert(
        final String updateOrInsert)
    {
        immutableSetCustomUpdateOrInsert(updateOrInsert);
    }

    /**
     * Retrieves the custom update or insert template.
     * @return such template.
     */
    public String getCustomUpdateOrInsert()
    {
        return m__strCustomUpdateOrInsert;
    }

    /**
     * Specifies the custom update or insert parameter Javadoc template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterJavadoc(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterJavadoc = template;
    }

    /**
     * Specifies the custom update or insert parameter Javadoc template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterJavadoc(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterJavadoc(update);
    }

    /**
     * Retrieves the custom update or insert parameter Javadoc template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterJavadoc()
    {
        return m__strCustomUpdateOrInsertParameterJavadoc;
    }

    /**
     * Specifies the custom update or insert parameter declaration template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterDeclaration(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterDeclaration = template;
    }

    /**
     * Specifies the custom update or insert parameter declaration template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterDeclaration(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterDeclaration(update);
    }

    /**
     * Retrieves the custom update or insert parameter declaration template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterDeclaration()
    {
        return m__strCustomUpdateOrInsertParameterDeclaration;
    }

    /**
     * Specifies the custom update or insert parameter values template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertParameterValues(
        final String template)
    {
        m__strCustomUpdateOrInsertParameterValues = template;
    }

    /**
     * Specifies the custom update or insert parameter values template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertParameterValues(
        final String update)
    {
        immutableSetCustomUpdateOrInsertParameterValues(update);
    }

    /**
     * Retrieves the custom update or insert parameter values template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertParameterValues()
    {
        return m__strCustomUpdateOrInsertParameterValues;
    }

    /**
     * Specifies the custom update or insert query line template.
     * @param update such template.
     */
    private void immutableSetCustomUpdateOrInsertQueryLine(
        final String template)
    {
        m__strCustomUpdateOrInsertQueryLine = template;
    }

    /**
     * Specifies the custom update or insert query line template.
     * @param update such template.
     */
    protected void setCustomUpdateOrInsertQueryLine(
        final String update)
    {
        immutableSetCustomUpdateOrInsertQueryLine(update);
    }

    /**
     * Retrieves the custom update or insert query line template.
     * @return such template.
     */
    public String getCustomUpdateOrInsertQueryLine()
    {
        return m__strCustomUpdateOrInsertQueryLine;
    }

//
    /**
     * Specifies the custom select-for-update template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdate(
        final String select)
    {
        m__strCustomSelectForUpdate = select;
    }

    /**
     * Specifies the custom select-for-update template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdate(
        final String select)
    {
        immutableSetCustomSelectForUpdate(select);
    }

    /**
     * Retrieves the custom select-for-update template.
     * @return such template.
     */
    public String getCustomSelectForUpdate()
    {
        return m__strCustomSelectForUpdate;
    }

    /**
     * Specifies the custom select-for-update parameter Javadoc template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateParameterJavadoc(
        final String template)
    {
        m__strCustomSelectForUpdateParameterJavadoc = template;
    }

    /**
     * Specifies the custom select-for-update parameter Javadoc template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateParameterJavadoc(
        final String select)
    {
        immutableSetCustomSelectForUpdateParameterJavadoc(select);
    }

    /**
     * Retrieves the custom select-for-update parameter Javadoc template.
     * @return such template.
     */
    public String getCustomSelectForUpdateParameterJavadoc()
    {
        return m__strCustomSelectForUpdateParameterJavadoc;
    }

    /**
     * Specifies the custom select-for-update parameter declaration template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateParameterDeclaration(
        final String template)
    {
        m__strCustomSelectForUpdateParameterDeclaration = template;
    }

    /**
     * Specifies the custom select-for-update parameter declaration template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateParameterDeclaration(
        final String select)
    {
        immutableSetCustomSelectForUpdateParameterDeclaration(select);
    }

    /**
     * Retrieves the custom select-for-update parameter declaration template.
     * @return such template.
     */
    public String getCustomSelectForUpdateParameterDeclaration()
    {
        return m__strCustomSelectForUpdateParameterDeclaration;
    }

    /**
     * Specifies the custom select-for-update parameter values template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateParameterValues(
        final String template)
    {
        m__strCustomSelectForUpdateParameterValues = template;
    }

    /**
     * Specifies the custom select-for-update parameter values template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateParameterValues(
        final String select)
    {
        immutableSetCustomSelectForUpdateParameterValues(select);
    }

    /**
     * Retrieves the custom select-for-update parameter values template.
     * @return such template.
     */
    public String getCustomSelectForUpdateParameterValues()
    {
        return m__strCustomSelectForUpdateParameterValues;
    }

    /**
     * Specifies the custom select-for-update result properties' values template.
     * @param select such template.
     */
    private void immutableSetCustomSelectForUpdateResultPropertyValues(
        final String template)
    {
        m__strCustomSelectForUpdateResultPropertyValues = template;
    }

    /**
     * Specifies the custom select-for-update result properties' values template.
     * @param select such template.
     */
    protected void setCustomSelectForUpdateResultPropertyValues(
        final String select)
    {
        immutableSetCustomSelectForUpdateResultPropertyValues(select);
    }

    /**
     * Retrieves the custom select-for-update result properties' values template.
     * @return such template.
     */
    public String getCustomSelectForUpdateResultPropertyValues()
    {
        return m__strCustomSelectForUpdateResultPropertyValues;
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
}
