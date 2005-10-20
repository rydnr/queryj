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
 * Description: Is able to generate base DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

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
 * Is able to generate base DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class BaseDAOFactoryTemplate
    extends  AbstractBaseDAOFactoryTemplate
    implements  BaseDAOFactoryTemplateDefaults
{
    /**
     * Builds a BaseDAOFactoryTemplate using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param projectPackageName the project package name.
     */
    public BaseDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String projectPackageName)
    {
        super(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            tableTemplate,
            metaDataManager,
            packageName,
            projectPackageName,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_COMMONS_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_GET_INSTANCE_METHOD,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_CLASS_END);
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
                getProjectPackageName(),
                getProjectImports(),
                getJdkImports(),
                getCommonsLoggingImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getGetInstanceMethod(),
                getFactoryMethod(),
                getClassEnd(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param projectPackageName the project package name.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param commonsLoggingImports the commons-logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param getInstanceMethod the getInstance method.
     * @param factoryMethod the factory method.
     * @param classEnd the class end.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String projectPackageName,
        final String projectImports,
        final String jdkImports,
        final String commonsLoggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String getInstanceMethod,
        final String factoryMethod,
        final String classEnd,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
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
                    packageName,
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_'),
                    packageUtils.retrieveDAOChooserPackage(
                        projectPackageName)
                }));

        t_sbResult.append(jdkImports);
        t_sbResult.append(commonsLoggingImports);

        t_Formatter = new MessageFormat(javadoc);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        t_Formatter = new MessageFormat(classDefinition);
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(classStart);

        MessageFormat t_GetInstanceMethodFormatter =
            new MessageFormat(getInstanceMethod);

        t_sbResult.append(
            t_GetInstanceMethodFormatter.format(
                new Object[]
                {
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
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_'),
                }));

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
