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
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public class DAOFactoryTemplate
    extends  AbstractDAOFactoryTemplate
    implements  DAOFactoryTemplateDefaults
{
    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public DAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource,
        final Project project,
        final Task task)
    {
        super(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            tableTemplate,
            metaDataManager,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            jndiDataSource,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_EXTENSION_IMPORTS,
            DEFAULT_COMMONS_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_DATA_SOURCE_RETRIEVAL_METHOD,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getHeader(),
                getPackageDeclaration(),
                getTableTemplate(),
                getMetaDataManager(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getJNDIDataSource(),
                getProjectImports(),
                getJdkImports(),
                getExtensionImports(),
                getCommonsLoggingImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getSingletonBody(),
                getFactoryMethod(),
                getDataSourceRetrievalMethod(),
                getClassEnd(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                MetaDataUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param extensionImports the extension imports.
     * @param commonsLoggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param dataSourceRetrievalMethod the data source retrieval method.
     * @param classEnd the class end.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition metaDataUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource,
        final String projectImports,
        final String jdkImports,
        final String extensionImports,
        final String commonsLoggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String factoryMethod,
        final String dataSourceRetrievalMethod,
        final String classEnd,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final MetaDataUtils metaDataUtils,
        final PackageUtils packageUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        MessageFormat t_Formatter = new MessageFormat(header);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        t_Formatter = new MessageFormat(packageDeclaration);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    packageName
                }));

        t_Formatter = new MessageFormat(projectImports);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    packageUtils.retrieveBaseDAOPackage(
                        basePackageName),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_'),
                    packageUtils.retrieveBaseDAOFactoryPackage(
                        basePackageName),
                    packageUtils.retrieveDAOPackage(
                        basePackageName,
                        engineName),
                    engineName
                }));

        t_sbResult.append(jdkImports);
        t_sbResult.append(extensionImports);
        t_sbResult.append(commonsLoggingImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    engineName,
                    tableTemplate.getTableName()
                }));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    engineName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_Formatter = new MessageFormat(classStart);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    jndiDataSource
                }));


        MessageFormat t_SingletonBodyFormatter =
            new MessageFormat(singletonBody);

        t_sbResult.append(
            t_SingletonBodyFormatter.format(
                new Object[]
                {
                    engineName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        List t_lFields = tableTemplate.getFields();

        MessageFormat t_FactoryMethodFormatter =
            new MessageFormat(factoryMethod);

        t_sbResult.append(
            t_FactoryMethodFormatter.format(
                new Object[]
                {
                    engineName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_'),
                }));

        t_sbResult.append(dataSourceRetrievalMethod);

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
