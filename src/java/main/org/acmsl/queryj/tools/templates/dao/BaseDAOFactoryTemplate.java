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
 * Description: Is able to generate base DAO factories according to
 *              database metadata.
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
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
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
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String                  packageName,
        final String                  projectPackageName)
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
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        StringUtils t_StringUtils = StringUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_MetaDataManager != null)
             && (t_PackageUtils    != null))
        {
            MessageFormat t_Formatter = new MessageFormat(getHeader());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getPackageDeclaration());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName()
                    }));

            t_Formatter = new MessageFormat(getProjectImports());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        getPackageName(),
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_'),
                        t_PackageUtils.retrieveDAOChooserPackage(
                            getProjectPackageName())
                    }));

            t_sbResult.append(getJdkImports());
            t_sbResult.append(getCommonsLoggingImports());

            t_Formatter = new MessageFormat(getJavadoc());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_TableTemplate.getTableName()
                    }));

            t_Formatter = new MessageFormat(getClassDefinition());
            t_sbResult.append(
                t_Formatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            t_sbResult.append(getClassStart());

            MessageFormat t_GetInstanceMethodFormatter =
                new MessageFormat(getGetInstanceMethod());

            t_sbResult.append(
                t_GetInstanceMethodFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_')
                    }));

            List t_lFields = t_TableTemplate.getFields();

            MessageFormat t_FactoryMethodFormatter =
                    new MessageFormat(getFactoryMethod());

            t_sbResult.append(
                t_FactoryMethodFormatter.format(
                    new Object[]
                    {
                        t_StringUtils.capitalize(
                            t_EnglishGrammarUtils.getSingular(
                                t_TableTemplate.getTableName().toLowerCase()),
                            '_'),
                    }));

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
