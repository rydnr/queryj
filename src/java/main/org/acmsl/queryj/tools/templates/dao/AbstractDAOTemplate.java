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
     * The additional import statements.
     */
    private String m__strAdditionalImports;

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
     * The class constants.
     */
    private String m__strClassConstants;

    /**
     * The class constructor.
     */
    private String m__strClassConstructor;

    /**
     * The pk javadoc.
     */
    private String m__strPkJavadoc;

    /**
     * The attribute javadoc.
     */
    private String m__strAttributeJavadoc;

    /**
     * The attribute declaration.
     */
    private String m__strAttributeDeclaration;

    /**
     * The attribute filter.
     */
    private String m__strAttributeFilter;

    /**
     * The statement setter call.
     */
    private String m__strStatementSetterCall;

    /**
     * The find-by-primary-key method.
     */
    private String m__strFindByPrimaryKeyMethod;

    /**
     * The insert method.
     */
    private String m__strInsertMethod;

    /**
     * The insert parameters specification.
     */
    private String m__strInsertParametersSpecification;

    /**
     * The update method.
     */
    private String m__strUpdateMethod;

    /**
     * The update parameters specification.
     */
    private String m__strUpdateParametersSpecification;

    /**
     * The delete method.
     */
    private String m__strDeleteMethod;

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
     * @param additionalImports the additional imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param classConstants the class' constants.
     * @param classConstructor the class constructor.
     * @param pkJavadoc the pk javadoc subtemplate.
     * @param attributeJavadoc the attribute javadoc subtemplate.
     * @param attributeDeclaration the attribute declaration subtemplate.
     * @param attributeFilter the attribute filter subtemplate.
     * @param statementSetterCall the statement setter call subtemplate.
     * @param findByPrimaryKeyMethod the find by primary key method.
     * @param insertMethod the insert method.
     * @param insertParametersSpecification the specification of the insert
     * method's parameters.
     * @param updateMethod the update method.
     * @param updateParametersSpecification the specification of the update
     * method's parameters.
     * @param deleteMethod the delete method.
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
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final String projectImports,
        final String foreignDAOImports,
        final String additionalImports,
        final String acmslImports,
        final String jdkImports,
        final String jdkExtensionImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String classConstants,
        final String classConstructor,
        final String pkJavadoc,
        final String attributeJavadoc,
        final String attributeDeclaration,
        final String attributeFilter,
        final String statementSetterCall,
        final String findByPrimaryKeyMethod,
        final String insertMethod,
        final String insertParametersSpecification,
        final String updateMethod,
        final String updateParametersSpecification,
        final String deleteMethod,
        final String customSelect,
        final String customSelectParameterJavadoc,
        final String customSelectParameterDeclaration,
        final String customSelectParameterValues,
        final String customSelectResultPropertyValues,
        final String customUpdateOrInsert,
        final String customUpdateOrInsertParameterJavadoc,
        final String customUpdateOrInsertParameterDeclaration,
        final String customUpdateOrInsertParameterValues,
        final String customUpdateOrInsertQueryLine,
        final String customSelectForUpdate,
        final String customSelectForUpdateParameterJavadoc,
        final String customSelectForUpdateParameterDeclaration,
        final String customSelectForUpdateParameterValues,
        final String customSelectForUpdateResultPropertyValues,
        final String classEnd)
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

        immutableSetAdditionalImports(
            additionalImports);

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

        immutableSetClassConstants(
            classConstants);

        immutableSetClassConstructor(
            classConstructor);

        immutableSetPkJavadoc(pkJavadoc);

        immutableSetAttributeJavadoc(attributeJavadoc);

        immutableSetAttributeDeclaration(attributeDeclaration);

        immutableSetAttributeFilter(attributeFilter);

        immutableSetStatementSetterCall(
            statementSetterCall);

        immutableSetFindByPrimaryKeyMethod(
            findByPrimaryKeyMethod);

        immutableSetInsertMethod(
            insertMethod);

        immutableSetInsertParametersSpecification(
            insertParametersSpecification);

        immutableSetUpdateMethod(
            updateMethod);

        immutableSetUpdateParametersSpecification(
            updateParametersSpecification);

        immutableSetDeleteMethod(
            deleteMethod);

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
     * Specifies the additional imports.
     * @param additionalImports the new additional imports.
     */
    private void immutableSetAdditionalImports(final String additionalImports)
    {
        m__strAdditionalImports = additionalImports;
    }

    /**
     * Specifies the additional imports.
     * @param additionalImports the new additional imports.
     */
    protected void setAdditionalImports(final String additionalImports)
    {
        immutableSetAdditionalImports(additionalImports);
    }

    /**
     * Retrieves the additional imports.
     * @return such information.
     */
    public String getAdditionalImports() 
    {
        return m__strAdditionalImports;
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
     * Specifies the class constants.
     * @param classConstants the new class constants.
     */
    private void immutableSetClassConstants(final String classConstants)
    {
        m__strClassConstants = classConstants;
    }

    /**
     * Specifies the class constants.
     * @param classConstants the new class constants.
     */
    protected void setClassConstants(final String classConstants)
    {
        immutableSetClassConstants(classConstants);
    }

    /**
     * Retrieves the class constants.
     * @return such information.
     */
    public String getClassConstants() 
    {
        return m__strClassConstants;
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
     * Specifies the pk Javadoc.
     * @param pkJavadoc such javadoc.
     */
    private void immutableSetPkJavadoc(final String pkJavadoc)
    {
        m__strPkJavadoc = pkJavadoc;
    }

    /**
     * Specifies the pk Javadoc.
     * @param pkJavadoc such Javadoc.
     */
    protected void setPkJavadoc(final String pkJavadoc)
    {
        immutableSetPkJavadoc(pkJavadoc);
    }

    /**
     * Retrieves the pk Javadoc.
     * @return such Javadoc.
     */
    public String getPkJavadoc()
    {
        return m__strPkJavadoc;
    }
    
    /**
     * Specifies the attribute Javadoc.
     * @param attributeJavadoc such javadoc.
     */
    private void immutableSetAttributeJavadoc(final String attributeJavadoc)
    {
        m__strAttributeJavadoc = attributeJavadoc;
    }

    /**
     * Specifies the attribute Javadoc.
     * @param attributeJavadoc such Javadoc.
     */
    protected void setAttributeJavadoc(final String attributeJavadoc)
    {
        immutableSetAttributeJavadoc(attributeJavadoc);
    }

    /**
     * Retrieves the attribute Javadoc.
     * @return such Javadoc.
     */
    public String getAttributeJavadoc()
    {
        return m__strAttributeJavadoc;
    }
    
    
    /**
     * Specifies the attribute declaration.
     * @param attributeDeclaration such declaration.
     */
    private void immutableSetAttributeDeclaration(final String attributeDeclaration)
    {
        m__strAttributeDeclaration = attributeDeclaration;
    }

    /**
     * Specifies the attribute declaration.
     * @param attributeDeclaration such declaration.
     */
    protected void setAttributeDeclaration(final String attributeDeclaration)
    {
        immutableSetAttributeDeclaration(attributeDeclaration);
    }

    /**
     * Retrieves the attribute declaration.
     * @return such declaration.
     */
    public String getAttributeDeclaration()
    {
        return m__strAttributeDeclaration;
    }

    /**
     * Specifies the attribute filter.
     * @param attributeFilter such subtemplate.
     */
    private void immutableSetAttributeFilter(
        final String attributeFilter)
    {
        m__strAttributeFilter = attributeFilter;
    }

    /**
     * Specifies the attribute filter.
     * @param attributeFilter such subtemplate.
     */
    protected void setAttributeFilter(final String attributeFilter)
    {
        immutableSetAttributeFilter(attributeFilter);
    }

    /**
     * Retrieves the attribute filter.
     * @return such subtemplate.
     */
    public String getAttributeFilter()
    {
        return m__strAttributeFilter;
    }

    /**
     * Specifies the statement setter call.
     * @param statementSetterCall such subtemplate.
     */
    private void immutableSetStatementSetterCall(final String statementSetterCall)
    {
        m__strStatementSetterCall = statementSetterCall;
    }

    /**
     * Specifies the statement setter call.
     * @param statementSetterCall such subtemplate.
     */
    protected void setStatementSetterCall(final String statementSetterCall)
    {
        immutableSetStatementSetterCall(
            statementSetterCall);
    }

    /**
     * Retrieves the statement setter call.
     * @return such subtemplate.
     */
    public String getStatementSetterCall()
    {
        return m__strStatementSetterCall;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    private void immutableSetFindByPrimaryKeyMethod(
        final String findByPrimaryKeyMethod)
    {
        m__strFindByPrimaryKeyMethod = findByPrimaryKeyMethod;
    }

    /**
     * Specifies the find-by-primary-key method.
     * @param findByPrimaryKeyMethod such method.
     */
    protected void setFindByPrimaryKeyMethod(
        final String findByPrimaryKeyMethod)
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
     * Specifies the insert parameters Specification.
     * @param specification such specification.
     */
    private void immutableSetInsertParametersSpecification(
        final String specification)
    {
        m__strInsertParametersSpecification = specification;
    }

    /**
     * Specifies the insert parameters Specification.
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
     * Specifies the update parameters Specification.
     * @param specification such specification.
     */
    private void immutableSetUpdateParametersSpecification(
        final String specification)
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
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    private void immutableSetDeleteMethod(
        final String deleteMethod)
    {
        m__strDeleteMethod = deleteMethod;
    }

    /**
     * Specifies the delete method.
     * @param deleteMethod such method.
     */
    protected void setDeleteMethod(
        final String deleteMethod)
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
