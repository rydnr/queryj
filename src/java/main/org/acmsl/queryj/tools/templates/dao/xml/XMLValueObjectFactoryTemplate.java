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
 * Description: Is able to generate XML-specific value object factories.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
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
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public class XMLValueObjectFactoryTemplate
    extends  AbstractXMLValueObjectFactoryTemplate
    implements  XMLValueObjectFactoryTemplateDefaults
{
    /**
     * Builds a <code>XMLValueObjectFactoryTemplate</code> using given information.
     * @param packageName the package name.
     * @param valueObjectPackageName the value object package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     */
    public XMLValueObjectFactoryTemplate(
        final String packageName,
        final String valueObjectPackageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager)
    {
        super(
            DEFAULT_HEADER,
            PACKAGE_DECLARATION,
            packageName,
            valueObjectPackageName,
            tableTemplate,
            metadataManager,
            DEFAULT_PROJECT_IMPORTS,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_EXTRA_IMPORTS,
            DEFAULT_JAVADOC,
            CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FACTORY_METHOD,
            DEFAULT_FACTORY_METHOD_ATTRIBUTE_BUILD,
            DEFAULT_FACTORY_METHOD_VALUE_OBJECT_BUILD,
            DEFAULT_CONVERSION_METHOD_SUFFIX_FOR_NULLABLE_VALUES,
            DEFAULT_EXTRA_METHODS,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String generateOutput()
    {
        return generateOutput(getMetadataManager());
    }
    
    /**
     * Retrieves the source code of the generated field tableName.
     * @param metadataManager the metadata manager.
     * @return such source code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getTableTemplate(),
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }
 
   /**
     * Retrieves the source code of the generated field tableName.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        MessageFormat t_Formatter = new MessageFormat(getHeader());

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
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
                    getValueObjectPackageName(),
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(getAcmSlImports());
        t_sbResult.append(getJdkImports());
        t_sbResult.append(getExtraImports());

        t_Formatter = new MessageFormat(getJavadoc());

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableTemplate.getTableName()
                }));

        t_Formatter = new MessageFormat(getClassDefinition());

        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(getClassStart());

        MessageFormat t_SingletonBodyFormatter =
            new MessageFormat(getSingletonBody());

        t_sbResult.append(
            t_SingletonBodyFormatter.format(
                new Object[]
                {
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            tableTemplate.getTableName().toLowerCase()),
                        '_')
                }));

        t_sbResult.append(getExtraMethods());

        List t_lFields = tableTemplate.getFields();

        MessageFormat t_FactoryMethodFormatter =
            new MessageFormat(getFactoryMethod());

        if  (t_lFields != null)
        {
            Iterator t_itFields = t_lFields.iterator();

            StringBuffer t_sbFactoryMethodAttributeBuild = new StringBuffer();

            MessageFormat t_FactoryMethodAttributeBuildFormatter =
                new MessageFormat(getFactoryMethodAttributeBuild());

            StringBuffer t_sbFactoryMethodValueObjectBuild = new StringBuffer();

            MessageFormat t_FactoryMethodValueObjectBuildFormatter =
                new MessageFormat(getFactoryMethodValueObjectBuild());

            String t_strConversionMethodSuffixForNullableValues =
                getConversionMethodSuffixForNullableValues();

            while  (t_itFields.hasNext()) 
            {
                String t_strField = (String) t_itFields.next();

                String t_strFieldType =
                    metadataTypeManager.getNativeType(
                        metadataManager.getColumnType(
                            tableTemplate.getTableName(),
                            t_strField));

                int t_iColumnType =
                    metadataManager.getColumnType(
                        tableTemplate.getTableName(),
                        t_strField);

                boolean t_bAllowsNull = false;

                boolean t_bIsPrimaryKey =
                    metadataManager.isPartOfPrimaryKey(
                        tableTemplate.getTableName(),
                        t_strField);

                if  (!t_bIsPrimaryKey)
                {
                    t_bAllowsNull =
                        metadataManager.allowsNull(
                            tableTemplate.getTableName(),
                            t_strField);
                }

                t_strFieldType =
                    metadataTypeManager.getNativeType(t_iColumnType, t_bAllowsNull);

                if  (t_bAllowsNull)
                {
                    t_strFieldType =
                        metadataTypeManager.getSmartObjectType(t_iColumnType);
                }

                t_sbFactoryMethodAttributeBuild.append(
                    t_FactoryMethodAttributeBuildFormatter.format(
                        new Object[]
                        {
                            t_strFieldType,
                            t_strField.toLowerCase(),
                            stringUtils.capitalize(
                                t_strFieldType.toLowerCase(), '_'),
                            (t_bAllowsNull)
                            ?  t_strConversionMethodSuffixForNullableValues
                            :  "",
                            stringUtils.unCapitalize(
                                t_strField.toLowerCase(), "-")
                        }));

                t_sbFactoryMethodValueObjectBuild.append(
                    t_FactoryMethodValueObjectBuildFormatter.format(
                        new Object[]
                        {
                            stringUtils.capitalize(
                                t_strField.toLowerCase(),
                                '_'),
                            t_strField.toLowerCase()
                        }));

                if  (t_itFields.hasNext())
                {
                    t_sbFactoryMethodValueObjectBuild.append(",");
                }
            }

            t_sbResult.append(
                t_FactoryMethodFormatter.format(
                    new Object[]
                    {
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                .toLowerCase()),
                            '_'),
                        t_sbFactoryMethodAttributeBuild.toString(),
                        t_sbFactoryMethodValueObjectBuild.toString()
                    }));
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
