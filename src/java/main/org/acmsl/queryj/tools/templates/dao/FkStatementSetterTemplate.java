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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create FkStatementSetter implementation for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.AbstractFkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateDefaults;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
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
 * Is able to create FkStatementSetter implementations for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class FkStatementSetterTemplate
    extends  AbstractFkStatementSetterTemplate
    implements  FkStatementSetterTemplateDefaults
{
    /**
     * Builds a <code>FkStatementSetterTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param foreignKey the foreign key.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public FkStatementSetterTemplate(
        final TableTemplate tableTemplate,
        final String foreignKey,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final Project project,
        final Task task)
    {
        super(
            tableTemplate,
            foreignKey,
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
            DEFAULT_CLASS_CONSTRUCTOR,
            DEFAULT_PARAMETER_SETTER_CALL,
            DEFAULT_PARAMETER_ACCESSOR,
            DEFAULT_SET_VALUES_METHOD,
            DEFAULT_PARAMETER_GETTER_CALL,
            DEFAULT_PARAMETER_JAVADOC,
            DEFAULT_PARAMETER_DECLARATION,
            DEFAULT_PARAMETER_SPECIFICATION,
            DEFAULT_CLASS_END,
            project,
            task);
    }

    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    protected String generateOutput()
      throws  InvalidTemplateException
    {
        return
            generateOutput(
                getTableTemplate(),
                getForeignKey(),
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
                getClassConstructor(),
                getParameterSetterCall(),
                getParameterAccessor(),
                getSetValuesMethod(),
                getParameterGetterCall(),
                getParameterJavadoc(),
                getParameterDeclaration(),
                getParameterSpecification(),
                getClassEnd(),
                getProject(),
                getTask(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param foreignKey the foreign key.
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
     * @param classConstructor the class constructor.
     * @param parameterSetterCall the parameter setter call.
     * @param parameterAccessor the parameter accessor.
     * @param setValuesMethod the setValues method.
     * @param parameterGetterCall the parameter getter call.
     * @param parameterJavadoc the parameter Javadoc.
     * @param parameterDeclaration the parameter declaration.
     * @param parameterSpecification the parameter specification.
     * @param classEnd the class end.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @param metaDataUtils the MetaDataUtils instance.
     * @param stringUtils the StringUtils instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @return such code.
     * @throws InvalidTemplateException if the template cannot be generated.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final String foreignKey,
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
        final String classConstructor,
        final String parameterSetterCall,
        final String parameterAccessor,
        final String setValuesMethod,
        final String parameterGetterCall,
        final String parameterJavadoc,
        final String parameterDeclaration,
        final String parameterSpecification,
        final String classEnd,
        final Project project,
        final Task task,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils)
      throws  InvalidTemplateException
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

        MessageFormat t_ClassStartFormatter = new MessageFormat(classStart);

        MessageFormat t_ClassConstructorFormatter =
            new MessageFormat(classConstructor);

        MessageFormat t_ParameterSetterCallFormatter =
            new MessageFormat(parameterSetterCall);

        MessageFormat t_ParameterAccessorFormatter =
            new MessageFormat(parameterAccessor);

        MessageFormat t_SetValuesMethodFormatter =
            new MessageFormat(setValuesMethod);

        MessageFormat t_ParameterGetterCallFormatter =
            new MessageFormat(parameterGetterCall);

        MessageFormat t_ParameterJavadocFormatter =
            new MessageFormat(parameterJavadoc);

        MessageFormat t_ParameterDeclarationFormatter =
            new MessageFormat(parameterDeclaration);

        MessageFormat t_ParameterSpecificationFormatter =
            new MessageFormat(parameterSpecification);

        StringBuffer t_sbValueObjectPropertiesSpecification =
            new StringBuffer();

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

        String t_strCapitalizedReferredTable =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    metaDataManager.getReferredTable(
                        tableTemplate.getTableName(),
                        foreignKey).toLowerCase()),
                '_');

        String t_strCapitalizedParameterName =
            stringUtils.capitalize(
                foreignKey.toLowerCase(),
                '_');

        t_sbResult.append(
            t_JavadocFormatter.format(
                new Object[]
                {
                    t_strValueObjectName,
                    foreignKey
                }));

        t_sbResult.append(
            t_ClassDefinitionFormatter.format(
                new Object[]
                {
                    t_strCapitalizedValueObjectName,
                    t_strCapitalizedReferredTable
                }));

        StringBuffer t_sbParameterSetterCalls = new StringBuffer();
        StringBuffer t_sbParameterAccessors = new StringBuffer();
        StringBuffer t_sbParameterGetterCalls = new StringBuffer();
        StringBuffer t_sbParameterJavadocs = new StringBuffer();
        StringBuffer t_sbParameterDeclarations = new StringBuffer();
        StringBuffer t_sbParameterSpecifications = new StringBuffer();

        String t_strParameterName = "";
        String t_strParameterType = "";
        String t_strPropertyName = "";
        String t_strPropertyType = "";

        t_strParameterName =
            stringUtils.unCapitalizeStart(
                t_strCapitalizedParameterName);

        t_strParameterType =
            metaDataUtils.getFieldType(
                metaDataManager.getColumnType(
                    t_strTableName,
                    foreignKey),
                project,
                task);

        t_strPropertyName = foreignKey.toUpperCase();

        t_strPropertyType =
            metaDataUtils.getObjectType(
                metaDataManager.getColumnType(
                    t_strTableName,
                    foreignKey));

        t_sbParameterSetterCalls.append(
            t_ParameterSetterCallFormatter.format(
                new Object[]
                {
                    t_strCapitalizedParameterName,
                    t_strParameterName
                }));

        t_sbParameterAccessors.append(
            t_ParameterAccessorFormatter.format(
                new Object[]
                {
                    t_strPropertyName,
                    t_strParameterType,
                    t_strParameterName,
                    t_strCapitalizedParameterName
                }));

        t_sbParameterGetterCalls.append(
            t_ParameterGetterCallFormatter.format(
                new Object[]
                {
                    t_strCapitalizedParameterName
                }));

        t_sbParameterJavadocs.append(
            t_ParameterJavadocFormatter.format(
                new Object[]
                {
                    t_strParameterName,
                    foreignKey.toUpperCase()
                }));

        t_sbParameterDeclarations.append(
            t_ParameterDeclarationFormatter.format(
                new Object[]
                {
                    t_strParameterType,
                    t_strParameterName
                }));

        t_sbParameterSpecifications.append(
            t_ParameterSpecificationFormatter.format(
                new Object[]
                {
                    t_strPropertyType,
                    t_strRepositoryName,
                    t_strTableName.toUpperCase(),
                    t_strPropertyName,
                    t_strParameterName
                }));

        t_sbResult.append(
            t_ClassStartFormatter.format(
                new Object[]
                {
                    t_sbParameterAccessors
                }));

        t_sbResult.append(
            t_ClassConstructorFormatter.format(
                new Object[]
                {
                    t_strCapitalizedValueObjectName,
                    t_strCapitalizedReferredTable,
                    t_sbParameterJavadocs,
                    t_sbParameterDeclarations,
                    t_sbParameterSetterCalls
                }));


        t_sbResult.append(
            t_SetValuesMethodFormatter.format(
                new Object[]
                {
                    t_sbParameterGetterCalls,
                    t_sbParameterJavadocs,
                    t_sbParameterDeclarations,
                    t_sbParameterSpecifications
                }));

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}