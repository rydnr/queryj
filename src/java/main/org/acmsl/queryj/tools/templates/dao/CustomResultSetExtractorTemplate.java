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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create CustomResultSetExtractor implementation for each
 *              custom query requiring so.
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
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.AbstractCustomResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.CustomResultSetExtractorTemplateDefaults;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.regexpplugin.Helper;
import org.acmsl.commons.regexpplugin.RegexpEngine;
import org.acmsl.commons.regexpplugin.RegexpEngineNotFoundException;
import org.acmsl.commons.regexpplugin.RegexpManager;
import org.acmsl.commons.regexpplugin.RegexpPluginMisconfiguredException;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to create CustomResultSetExtractor implementations for each
 * custom query requiring so.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class CustomResultSetExtractorTemplate
    extends  AbstractCustomResultSetExtractorTemplate
    implements  CustomResultSetExtractorTemplateDefaults
{
    /**
     * Builds a <code>CustomResultSetExtractorTemplate</code> using
     * given information.
     * @param resultElement the result element.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public CustomResultSetExtractorTemplate(
        final ResultElement resultElement,
        final CustomSqlProvider customSqlProvider,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final Project project,
        final Task task)
    {
        super(
            resultElement,
            customSqlProvider,
            tableTemplate,
            metaDataManager,
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            basePackageName,
            repositoryName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_ADDITIONAL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JDK_EXTENSION_IMPORTS,
            DEFAULT_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_EXTRACT_DATA_METHOD_WITH_SINGLE_RESULT,
            DEFAULT_EXTRACT_DATA_METHOD_WITH_MULTIPLE_RESULTS,
            DEFAULT_VALUE_OBJECT_PROPERTIES_SPECIFICATION,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getResultElement(),
                getCustomSqlProvider(),
                getTableTemplate(),
                getMetaDataManager(),
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getBasePackageName(),
                getRepositoryName(),
                getProjectImports(),
                getAdditionalImports(),
                getAcmslImports(),
                getJdkImports(),
                getJdkExtensionImports(),
                getLoggingImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getExtractDataMethodWithSingleResult(),
                getExtractDataMethodWithMultipleResult(),
                getValueObjectPropertiesSpecification(),
                getClassEnd(),
                DAOTemplateUtils.getInstance(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param projectImports the project imports.
     * @param acmslImports the ACM-SL imports.
     * @param additionalImports the additional imports.
     * @param jdkImports the JDK imports.
     * @param jdkExtensionImports the JDK extension imports.
     * @param loggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param extractDataMethodWithSingleResult the extractData method
     * with single result.
     * @param extractDataMethodWithMultupleResult the extractData method
     * with multiple result.
     * @param valueObjectPropertiesSpecification the value object
     * properties specification.
     * @param classEnd the class end.
     * @param daoTemplateUtils the <code>DAOTemplateUtils</code> instance.
     * @param metaDataUtils the MetaDataUtils instance.
     * @param stringUtils the StringUtils instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @return such code.
     * @precondition sqlElement != null
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition daoTemplateUtils != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
        final ResultElement resultElement,
        final CustomSqlProvider customSqlProvider,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String projectImports,
        final String additionalImports,
        final String acmslImports,
        final String jdkImports,
        final String jdkExtensionImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String extractDataMethodWithSingleResult,
        final String extractDataMethodWithMultipleResult,
        final String valueObjectPropertiesSpecification,
        final String classEnd,
        final DAOTemplateUtils daoTemplateUtils,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strRepositoryName =
            stringUtils.capitalize(
                repositoryName,
                '_');

        String t_strTableName = tableTemplate.getTableName();

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                t_strTableName.toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        MessageFormat t_HeaderFormatter = new MessageFormat(header);

        MessageFormat t_PackageDeclarationFormatter =
            new MessageFormat(packageDeclaration);

        MessageFormat t_ProjectImportFormatter =
            new MessageFormat(projectImports);

        MessageFormat t_JavadocFormatter = new MessageFormat(javadoc);

        MessageFormat t_ClassDefinitionFormatter =
            new MessageFormat(classDefinition);

        MessageFormat t_ExtractDataMethodFormatter =
            new MessageFormat(extractDataMethodWithSingleResult);

        if  (ResultElement.MULTIPLE.equals(resultElement.getMatches()))
        {
            t_ExtractDataMethodFormatter =
                new MessageFormat(extractDataMethodWithMultipleResult);
        }

        MessageFormat t_ValueObjectPropertiesSpecificationFormatter =
            new MessageFormat(valueObjectPropertiesSpecification);

        StringBuffer t_sbValueObjectPropertiesSpecification = new StringBuffer();

        t_sbResult.append(
            t_HeaderFormatter.format(
                new Object[]
                {
                    t_strTableName
                }));

        t_sbResult.append(
            t_PackageDeclarationFormatter.format(
                new Object[]{packageName}));

        t_sbResult.append(
            t_ProjectImportFormatter.format(
                new Object[]
                {
                    packageUtils.retrieveValueObjectPackage(
                        basePackageName),
                    t_strCapitalizedValueObjectName,
                    packageUtils.retrieveTableRepositoryPackage(
                        basePackageName),
                    t_strRepositoryName
                }));

        t_sbResult.append(acmslImports);
        t_sbResult.append(additionalImports);
        t_sbResult.append(jdkImports);
        t_sbResult.append(jdkExtensionImports);
        t_sbResult.append(loggingImports);

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    t_strValueObjectName
                }));

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        stringUtils.capitalize(
                            stringUtils.capitalize(
                                resultElement.getId(),
                                '.'),
                            '_'),
                        '-')
                }));

        t_sbResult.append(classStart);

        PropertyElement[] t_aProperties =
            retrieveProperties(
                customSqlProvider, resultElement, daoTemplateUtils);

        if  (t_aProperties != null)
        {
            for  (int t_iPropertyIndex = 0;
                      t_iPropertyIndex < t_aProperties.length;
                      t_iPropertyIndex++)
            {
                t_sbValueObjectPropertiesSpecification.append(
                    t_ValueObjectPropertiesSpecificationFormatter.format(
                        new Object[]
                        {
                            metaDataUtils.getObjectType(
                                metaDataUtils.getJavaType(
                                    t_aProperties[t_iPropertyIndex].getType())),
                            t_aProperties[t_iPropertyIndex].getColumnName()
                        }));

                if  (t_iPropertyIndex < t_aProperties.length - 1)
                {
                    t_sbValueObjectPropertiesSpecification.append(",");
                }
            }

            t_sbResult.append(
                t_ExtractDataMethodFormatter.format(
                    new Object[]
                    {
                        resultElement.getClassValue(),
                        t_sbValueObjectPropertiesSpecification
                    }));
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }

    /**
     * Retrieves the properties.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param resultElement the result element.
     * @return the properties.
     * @precondition customSqlProvider != null
     * @precondition resultElement != null
     */
    protected PropertyElement[] retrieveProperties(
        final CustomSqlProvider customSqlProvider,
        final ResultElement resultElement,
        final DAOTemplateUtils daoTemplateUtils)
    {
        return
            daoTemplateUtils.retrievePropertyElementsByResultId(
                customSqlProvider, resultElement);
    }
}
