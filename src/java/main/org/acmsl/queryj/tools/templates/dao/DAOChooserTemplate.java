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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAOChooser class according
 *              to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;

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
import java.util.Iterator;
import java.util.Collection;

/**
 * Is able to generate DAOChooser class according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOChooserTemplate
    extends  AbstractDAOChooserTemplate
    implements DAOChooserTemplateDefaults
{
    /**
     * Builds a <code>DAOChooserTemplate</code> using given information.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public DAOChooserTemplate(
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String repository)
    {
        super(
            decoratorFactory,
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            repository,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_LOGGING_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_PROPERTIES_KEYS,
            DEFAULT_PROPERTIES_REFERENCE,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_PROPERTIES_ACCESSORS,
            DEFAULT_HELPER_METHODS,
            DEFAULT_GETDAOFACTORY_METHOD,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getHeader(),
                getPackageDeclaration(),
                getPackageName(),
                getRepository(),
                getJdkImports(),
                getLoggingImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getPropertiesKeys(),
                getPropertiesReference(),
                getSingletonBody(),
                getPropertiesAccessors(),
                getHelperMethods(),
                getGetDAOFactoryMethods(),
                getClassEnd(),
                getTables(),
                DAOChooserTemplateUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param packageName the package name.
     * @param repository the repository.
     * @param jdkImports the JDK imports.
     * @param loggingImports the logging imports.
     * @param javadoc the class Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param propertiesKeys the properties' keys template.
     * @param propertiesReference the properties reference template.
     * @param singletonBody the singleton body.
     * @param propertiesAccessors the properties accessors template.
     * @param helperMethods the helper methods.
     * @param getDAOFactoryMethods the getDAOFactory methods.
     * @param classEnd the class end.
     * @param tables the tables.
     * @param daoChooserTemplateUtils the <code>DAOChooserTemplateUtils</code>
     * instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition tables != null
     * @precondition daoChooserTemplateUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final String header,
        final String packageDeclaration,
        final String packageName,
        final String repository,
        final String jdkImports,
        final String loggingImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String propertiesKeys,
        final String propertiesReference,
        final String singletonBody,
        final String propertiesAccessors,
        final String helperMethods,
        final String getDAOFactoryMethods,
        final String classEnd,
        final Collection tables,
        final DAOChooserTemplateUtils daoChooserTemplateUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(header);

        MessageFormat t_Formatter =
            new MessageFormat(packageDeclaration);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    packageName
                }));

        t_sbResult.append(jdkImports);
        t_sbResult.append(loggingImports);
        t_sbResult.append(javadoc);
        t_sbResult.append(classDefinition);

        t_sbResult.append(classStart);

        StringBuffer t_sbPropertiesKeys = new StringBuffer();
        MessageFormat t_PropertiesKeysFormatter =
            new MessageFormat(propertiesKeys);

        StringBuffer t_sbGetDAOFactoryMethods = new StringBuffer();
        MessageFormat t_GetDAOFactoryMethodFormatter =
            new MessageFormat(getDAOFactoryMethods);

        Iterator t_itTables = tables.iterator();

        while  (t_itTables.hasNext()) 
        {
            String t_strTable = (String) t_itTables.next();

            if  (t_strTable != null)
            {
                String t_strCapitalizedTable =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTable.toLowerCase()),
                        '_');

                String t_strUpperCaseTable =
                    (  ("" + repository).toUpperCase()
                       + "_" + t_strCapitalizedTable).toUpperCase();

                String t_strDottedTable =
                    (  ("" + repository).toLowerCase()
                       + "." + t_strCapitalizedTable).toLowerCase();

                t_sbPropertiesKeys.append(
                    t_PropertiesKeysFormatter.format(
                        new Object[]
                        {
                            t_strUpperCaseTable,
                            t_strDottedTable
                        }));

                t_sbGetDAOFactoryMethods.append(
                    t_GetDAOFactoryMethodFormatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTable,
                            t_strUpperCaseTable,
                            t_strCapitalizedTable
                        }));
            }
        }

        t_sbResult.append(t_sbPropertiesKeys);

        t_Formatter = new MessageFormat(propertiesReference);

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoChooserTemplateUtils.retrievePropertiesFileName(
                        ("" + repository).toLowerCase())
                }));

        t_sbResult.append(singletonBody);

        t_sbResult.append(propertiesAccessors);

        t_sbResult.append(helperMethods);

        t_sbResult.append(t_sbGetDAOFactoryMethods);

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
