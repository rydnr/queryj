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
 * Description: Is able to generate value object factories according to
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
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
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
 * Is able to generate value object factories according to
 * database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class ValueObjectFactoryTemplate
    extends  AbstractValueObjectFactoryTemplate
    implements  ValueObjectFactoryTemplateDefaults
{
    /**
     * Builds a ValueObjectFactoryTemplate using given information.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     */
    public ValueObjectFactoryTemplate(
        final String                  packageName,
        final TableTemplate           tableTemplate,
        final DatabaseMetaDataManager metaDataManager)
    {
        super(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            tableTemplate,
            metaDataManager,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_FACTORY_METHOD_FIELD_JAVADOC,
            DEFAULT_FACTORY_METHOD_FIELD_DECLARATION,
            DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD,
            DEFAULT_FACTORY_ALIAS_METHOD,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code of the generated value object factory.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        EnglishGrammarUtils t_EnglishGrammarUtils =
            EnglishGrammarUtils.getInstance();

        TableTemplate t_TableTemplate = getTableTemplate();

        DatabaseMetaDataManager t_MetaDataManager = getMetaDataManager();

        MetaDataUtils t_MetaDataUtils = MetaDataUtils.getInstance();

        if  (   (t_StringUtils     != null)
             && (t_TableTemplate   != null)
             && (t_MetaDataUtils   != null)
             && (t_MetaDataManager != null))
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
                            '_')
                    }));

            t_sbResult.append(getJdkImports());

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

            MessageFormat t_SingletonBodyFormatter =
                new MessageFormat(getSingletonBody());

            t_sbResult.append(
                t_SingletonBodyFormatter.format(
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

            MessageFormat t_FactoryAliasMethodFormatter =
                    new MessageFormat(getFactoryAliasMethod());

            if  (t_lFields != null)
            {
                Iterator t_itFields = t_lFields.iterator();

                StringBuffer t_sbFactoryMethodFieldJavadoc = new StringBuffer();

                MessageFormat t_FactoryMethodFieldJavadocFormatter =
                    new MessageFormat(getFactoryMethodFieldJavadoc());

                StringBuffer t_sbFactoryMethodFieldDefinition = new StringBuffer();

                MessageFormat t_FactoryMethodFieldDefinitionFormatter =
                    new MessageFormat(getFactoryMethodFieldDefinition());

                StringBuffer t_sbFactoryMethodValueObjectBuild = new StringBuffer();

                MessageFormat t_FactoryMethodValueObjectBuildFormatter =
                    new MessageFormat(getFactoryMethodValueObjectBuild());

                while  (t_itFields.hasNext()) 
                {
                    String t_strField = (String) t_itFields.next();

                    String t_strFieldType =
                        t_MetaDataUtils.getNativeType(
                            t_MetaDataManager.getColumnType(
                                t_TableTemplate.getTableName(),
                                t_strField));

                    t_sbFactoryMethodFieldJavadoc.append(
                        t_FactoryMethodFieldJavadocFormatter.format(
                            new Object[]
                            {
                                t_strField.toLowerCase(),
                                t_strField
                            }));

                    t_sbFactoryMethodFieldDefinition.append(
                        t_FactoryMethodFieldDefinitionFormatter.format(
                            new Object[]
                            {
                                t_strFieldType,
                                t_strField.toLowerCase()
                            }));

                    t_sbFactoryMethodValueObjectBuild.append(
                        t_FactoryMethodValueObjectBuildFormatter.format(
                            new Object[]
                            {
                                t_StringUtils.capitalize(
                                    t_strField.toLowerCase(),
                                    '_'),
                                t_strField.toLowerCase()
                            }));

                    if  (t_itFields.hasNext())
                    {
                        t_sbFactoryMethodFieldDefinition.append(",");
                        t_sbFactoryMethodValueObjectBuild.append(",");
                    }
                }

                t_sbResult.append(
                    t_FactoryMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbFactoryMethodFieldJavadoc.toString(),
                            t_sbFactoryMethodFieldDefinition.toString(),
                            t_sbFactoryMethodValueObjectBuild.toString()
                        }));

                t_sbResult.append(
                    t_FactoryAliasMethodFormatter.format(
                        new Object[]
                        {
                            t_StringUtils.capitalize(
                                t_EnglishGrammarUtils.getSingular(
                                    t_TableTemplate.getTableName()
                                        .toLowerCase()),
                                '_'),
                            t_sbFactoryMethodFieldJavadoc.toString(),
                            t_sbFactoryMethodFieldDefinition.toString(),
                            t_sbFactoryMethodValueObjectBuild.toString()
                        }));
            }

            t_sbResult.append(getClassEnd());
        }

        return t_sbResult.toString();
    }
}
