//;-*- mode: java -*-
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-FK templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.ResultDecorator;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Base logic for all per-fk templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerForeignKeyTemplate
    extends  AbstractBasePerForeignKeyTemplate
{
    /**
     * An empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Builds a <code>BasePerForeignKeyTemplate</code> using given
     * information.
     * @param foreignKey the foreign key.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public BasePerForeignKeyTemplate(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            foreignKey,
            metadataManager,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected abstract StringTemplateGroup retrieveGroup();
    
    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    protected String generateOutput()
    {
        return generateOutput(getMetadataManager());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param metadataManager the metadata manager.
     * @return such code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getForeignKey(),
                metadataManager,
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getRepositoryName(),
                metadataManager.getMetadataTypeManager(),
                getDecoratorFactory(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
                MetaLanguageUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param foreignKey the foreign key.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return such code.
     * @precondition foreignKey != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition metaLanguageUtils != null
     */
    protected String generateOutput(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final StringUtils stringUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils,
        final MetaLanguageUtils metaLanguageUtils)
    {
        String result = "";

        StringTemplateGroup t_Group = retrieveGroup();
        
        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');
        
        String t_strRepositoryName =
            stringUtils.capitalize(repositoryName, '_');

        fillParameters(
            new HashMap(),
            t_Template,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            foreignKey,
            engineName,
            engineVersion,
            basePackageName,
            packageUtils.retrieveDAOSubpackage(engineName),
            createTimestamp(),
            t_strRepositoryName,
            decoratorFactory,
            stringUtils);

        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param foreignKey the foreign key.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param timestamp the timestamp.
     * @param className the class name of the DAO.
     * @param tableRepositoryName the table repository.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition foreignKey != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     * @precondition decoratorFactory != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final Map input,
        final StringTemplate template,
        final Integer[] copyrightYears,
        final ForeignKey foreignKey,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String subpackageName,
        final String timestamp,
        final String tableRepositoryName,
        final DecoratorFactory decoratorFactory,
        final StringUtils stringUtils)
    {
        template.setAttribute("input", input);

        fillCommonParameters(input, foreignKey, engineName, engineVersion);

        fillJavaHeaderParameters(input, copyrightYears, timestamp);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName);

        fillProjectImportsParameters(
            input,
            basePackageName,
            subpackageName,
            foreignKey.getAttributes());

        fillClassParameters(
            input,
            foreignKey,
            engineName,
            engineVersion,
            timestamp,
            tableRepositoryName);
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param foreignKey the foreign key.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     */
    protected void fillCommonParameters(
        final Map input,
        final ForeignKey foreignKey,
        final String engineName,
        final String engineVersion)
    {
        input.put("table_name",  foreignKey.getSourceTableName());
        input.put("foreign_key", foreignKey);
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
    }

    /**
     * Fills the parameters for <code>java_header</code> rule.
     * @param input the input.
     * @param copyrightYears the copyright years.
     * @param timestamp the timestamp.
     * @precondition input != null
     * @precondition copyrightYears != null
     * @precondition timestamp != null
     */
    protected void fillJavaHeaderParameters(
        final Map input,
        final Integer[] copyrightYears,
        final String timestamp)
    {
        input.put("copyright_years", copyrightYears);
        input.put("timestamp", timestamp);
    }

    /**
     * Fills the parameters for <code>package_declaration</code> rule.
     * @param input the input.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     */
    protected void fillPackageDeclarationParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
    }

    /**
     * Fills the parameters for the <code>project_imports</code> rule.
     * @param input the input.
     * @param basePackageName the base package.
     * @param subpackageName the name of the subpackage.
     * @param fkAttributes the foreign-key attributes.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition tableName != null
     * @precondition fkAttributes != null
     */
    protected void fillProjectImportsParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName,
        final Collection fkAttributes)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
        input.put("fk_attributes", fkAttributes);
    }

    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param foreignKey the foreign key.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param tableRepositoryName the table repository name.
     * @precondition input != null
     * @precondition foreignKey != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     */
    protected void fillClassParameters(
        final Map input,
        final ForeignKey foreignKey,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final String tableRepositoryName)
    {
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("timestamp", timestamp);

        input.put("tr_name", tableRepositoryName);
    }

    /**
     * Retrieves the foreign key attributes.
     * @param foreignKey the foreign key.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the foreign key attributes.
     * @precondition foreignKey != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection retrieveForeignKeyAttributes(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                (String[])
                    foreignKey.getAttributes().toArray(EMPTY_STRING_ARRAY),
                foreignKey.getSourceTableName(),
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                new String[columnNames.length],
                tableName,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param columnValues the column values.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition columnValues != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String[] columnValues,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                columnValues,
                tableName,
                null,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param tableName the table name.
     * @param allowsNullAsAWhole whether given column names can be null
     * as a whole or not.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            buildAttributes(
                columnNames,
                new String[columnNames.length],
                tableName,
                allowsNullAsAWhole,
                metadataManager,
                metadataTypeManager,
                decoratorFactory);
    }

    /**
     * Builds the attributes associated to given column names.
     * @param columnNames the column names.
     * @param columnValues the column values.
     * @param tableName the table name.
     * @param allowsNullAsAWhole whether given column names can be null
     * as a whole or not.
     * @param metadataManager the <code>MetadataManager</code>
     * instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the attribute collection.
     * @precondition columnNames != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection buildAttributes(
        final String[] columnNames,
        final String[] columnValues,
        final String tableName,
        final Boolean allowsNullAsAWhole,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        int t_iLength = (columnNames != null) ? columnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            int t_iType =
                metadataManager.getColumnType(
                    tableName, columnNames[t_iIndex]);

            String t_strNativeType =
                metadataTypeManager.getNativeType(t_iType);

            boolean t_bAllowsNull = false;

            if  (allowsNullAsAWhole != null)
            {
                t_bAllowsNull = allowsNullAsAWhole.booleanValue();
            }
            else
            {
                t_bAllowsNull =
                    metadataManager.allowsNull(
                        tableName, columnNames[t_iIndex]);
            }

            String t_strFieldType =
                metadataTypeManager.getFieldType(t_iType, t_bAllowsNull);

            boolean t_bManagedExternally =
                metadataManager.isManagedExternally(
                    tableName, columnNames[t_iIndex]);

            result.add(
                decoratorFactory.createDecorator(
                    new AttributeValueObject(
                        columnNames[t_iIndex],
                        t_iType,
                        t_strNativeType,
                        t_strFieldType,
                        tableName,
                        t_bManagedExternally,
                        t_bAllowsNull,
                        columnValues[t_iIndex]),
                    metadataManager));
        }

        return result;
    }

    /**
     * Normalizes given value, in lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }
}
