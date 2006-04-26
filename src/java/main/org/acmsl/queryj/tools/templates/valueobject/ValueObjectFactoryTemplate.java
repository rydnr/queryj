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
 * Description: Is able to generate value object factories according to
 *              database metadata.
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
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
 *         >Jose San Leandro</a>
 */
public class ValueObjectFactoryTemplate
    extends  AbstractValueObjectFactoryTemplate
    implements  ValueObjectFactoryTemplateDefaults
{
    /**
     * Builds a ValueObjectFactoryTemplate using given information.
     * @param packageName the package name.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    public ValueObjectFactoryTemplate(
        final String packageName,
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory)
    {
        super(
            PACKAGE_DECLARATION,
            packageName,
            tableTemplate,
            metadataManager,
//            (header != null) ? header : DEFAULT_HEADER,
            DEFAULT_HEADER,
            decoratorFactory,
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
     * @param header the header.
     * @return such source code.
     */
    protected String generateOutput(final String header)
    {
        return generateOutput(header, getMetadataManager());
    }
    
    /**
     * Retrieves the source code of the generated value object factory.
     * @param header the header.
     * @param metadataManager the metadata manager.
     * @return such source code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getTableTemplate(),
                metadataManager,
                getPackageName(),
                header,
                getPackageDeclaration(),
                getProjectImports(),
                getJdkImports(),
                getJavadoc(),
                getClassDefinition(),
                getClassStart(),
                getSingletonBody(),
                getFactoryMethod(),
                getFactoryAliasMethod(),
                getFactoryMethodFieldJavadoc(),
                getFactoryMethodFieldDefinition(),
                getFactoryMethodValueObjectBuild(),
                getClassEnd(),
                metadataManager.getMetadataTypeManager(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated value object factory.
     * @param tableTemplate the table template.
     * @param metadataManager the metadata manager.
     * @param packageName the package name.
     * @param header the header.
     * @param packageDeclaration the package declaration.
     * @param projectImports the project imports.
     * @param jdkImports the JDK imports.
     * @param javadoc the Javadoc.
     * @param classDefinition the class definition.
     * @param classStart the class start.
     * @param singletonBody the singleton body.
     * @param factoryMethod the factory method.
     * @param factoryAliasMethod the factory alias method.
     * @param factoryMethodFieldJavadoc the field Javadoc for the factory
     * method.
     * @param factoryMethodFieldDefinition the field definition for the factory
     * method.
     * @param factoryMethodValueObjectBuild the factory method value object
     * build.
     * @param classEnd the class end.
     * @param metadataTypeManager the metadata type manager.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition header != null
     * @precondition packageDeclaration != null
     * @precondition projectImports != null
     * @precondition jdkImports != null
     * @precondition javadoc != null
     * @precondition classDefinition != null
     * @precondition classStart != null
     * @precondition singletonBody != null
     * @precondition factoryMethod != null
     * @precondition factoryAliasMethod != null
     * @precondition factoryMethodFieldJavadoc != null
     * @precondition factoryMethodFieldDefinition != null
     * @precondition factoryMethodValueObjectBuild != null
     * @precondition classEnd != null
     * @precondition metadataTypeManager != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final MetadataManager metadataManager,
        final String packageName,
        final String header,
        final String packageDeclaration,
        final String projectImports,
        final String jdkImports,
        final String javadoc,
        final String classDefinition,
        final String classStart,
        final String singletonBody,
        final String factoryMethod,
        final String factoryAliasMethod,
        final String factoryMethodFieldJavadoc,
        final String factoryMethodFieldDefinition,
        final String factoryMethodValueObjectBuild,
        final String classEnd,
        final MetadataTypeManager metadataTypeManager,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
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
                        '_')
                }));

        t_sbResult.append(jdkImports);

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

        MessageFormat t_SingletonBodyFormatter =
            new MessageFormat(singletonBody);

        t_sbResult.append(
            t_SingletonBodyFormatter.format(
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

        MessageFormat t_FactoryAliasMethodFormatter =
            new MessageFormat(factoryAliasMethod);

        if  (t_lFields != null)
        {
            Iterator t_itFields = t_lFields.iterator();

            StringBuffer t_sbFactoryMethodFieldJavadoc = new StringBuffer();

            MessageFormat t_FactoryMethodFieldJavadocFormatter =
                new MessageFormat(factoryMethodFieldJavadoc);

            StringBuffer t_sbFactoryMethodFieldDefinition = new StringBuffer();

            MessageFormat t_FactoryMethodFieldDefinitionFormatter =
                new MessageFormat(factoryMethodFieldDefinition);

            StringBuffer t_sbFactoryMethodValueObjectBuild = new StringBuffer();

            MessageFormat t_FactoryMethodValueObjectBuildFormatter =
                new MessageFormat(factoryMethodValueObjectBuild);

            while  (t_itFields.hasNext()) 
            {
                String t_strField = (String) t_itFields.next();

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

                String t_strFieldType =
                    metadataTypeManager.getNativeType(t_iColumnType, t_bAllowsNull);

                if  (t_bAllowsNull)
                {
                    t_strFieldType =
                        metadataTypeManager.getSmartObjectType(t_iColumnType);
                }

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
                            stringUtils.capitalize(
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
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
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
                        stringUtils.capitalize(
                            englishGrammarUtils.getSingular(
                                tableTemplate.getTableName()
                                .toLowerCase()),
                            '_'),
                        t_sbFactoryMethodFieldJavadoc.toString(),
                        t_sbFactoryMethodFieldDefinition.toString(),
                        t_sbFactoryMethodValueObjectBuild.toString()
                    }));
        }

        t_sbResult.append(classEnd);

        return t_sbResult.toString();
    }
}
