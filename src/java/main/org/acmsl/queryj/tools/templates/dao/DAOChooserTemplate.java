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
 * Description: Is able to generate DAOChooser class according
 *              to database metadata.
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
import java.util.List;

/**
 * Is able to generate DAOChooser class according to database
 * metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class DAOChooserTemplate
    extends  AbstractDAOChooserTemplate
    implements DAOChooserTemplateDefaults
{
    /**
     * Builds a <code>DAOChooserTemplate</code> using given information.
     * @param packageName the package name.
     * @param repository the repository.
     */
    public DAOChooserTemplate(
        final String packageName, final String repository)
    {
        super(
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
    public String toString()
    {
        return
            toString(
                DAOChooserTemplateUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated table repository.
     * @param daoChooserTemplateUtils the <code>DAOChooserTemplateUtils</code>
     * instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition daoChooserTemplateUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     */
    protected String toString(
        final DAOChooserTemplateUtils daoChooserTemplateUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        String t_strRepository =
            stringUtils.normalize(getRepository(), '_');

        t_sbResult.append(getHeader());

        MessageFormat t_Formatter =
            new MessageFormat(getPackageDeclaration());

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    getPackageName()
                }));

        t_sbResult.append(getJdkImports());
        t_sbResult.append(getLoggingImports());
        t_sbResult.append(getJavadoc());
        t_sbResult.append(getClassDefinition());

        t_sbResult.append(getClassStart());

        StringBuffer t_sbPropertiesKeys = new StringBuffer();
        MessageFormat t_PropertiesKeysFormatter =
            new MessageFormat(getPropertiesKeys());

        StringBuffer t_sbGetDAOFactoryMethods = new StringBuffer();
        MessageFormat t_GetDAOFactoryMethodFormatter =
            new MessageFormat(getGetDAOFactoryMethods());

        List t_lTables = getTables();

        if  (t_lTables != null)
        {
            Iterator t_itTables = t_lTables.iterator();

            while  (t_itTables.hasNext()) 
            {
                String t_strTable = (String) t_itTables.next();

                if  (t_strTable != null)
                {
                    String t_strCapitalizedTable =
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                t_strTable),
                            '_');

                    String t_strUpperCaseTable =
                        (  ("" + getRepository()).toUpperCase()
                         + "_" + t_strCapitalizedTable).toUpperCase();

                    String t_strDottedTable =
                        (  ("" + getRepository()).toLowerCase()
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
                                stringUtils.capitalize(
                                    englishGrammarUtils.getSingular(
                                        t_strTable.toLowerCase()),
                                    '_')
                            }));
                }
            }
        }

        t_sbResult.append(t_sbPropertiesKeys);

        t_Formatter = new MessageFormat(getPropertiesReference());

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    daoChooserTemplateUtils.retrievePropertiesFileName(
                        ("" + getRepository()).toLowerCase())
                }));

        t_sbResult.append(getSingletonBody());

        t_sbResult.append(getPropertiesAccessors());

        t_sbResult.append(getHelperMethods());

        t_sbResult.append(t_sbGetDAOFactoryMethods);

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
