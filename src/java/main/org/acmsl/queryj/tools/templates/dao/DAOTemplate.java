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
 * Description: Is able to create engine-specific DAO interfaces for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.customsql.ParameterRefElement;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.ResultRefElement;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.AbstractDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateDefaults;
import org.acmsl.queryj.tools.templates.dao.DAOTemplateUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
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
 * Is able to create engine-specific DAO interfaces for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOTemplate
    extends  AbstractDAOTemplate
    implements  DAOTemplateDefaults
{
    /**
     * The table template.
     */
    private TableTemplate m__TableTemplate;

    /**
     * The database metadata manager.
     */
    private DatabaseMetaDataManager m__MetaDataManager;

    /**
     * The custom-sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * Builds a <code>DAOTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public DAOTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            tableTemplate,
            metaDataManager,
            customSqlProvider,
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
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAO.stg");
    }
    
    /**
     * Retrieves the source code generated by this template.
     * @return such code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getCustomSqlProvider(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getRepositoryName(),
                MetaDataUtils.getInstance(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance(),
                StringValidator.getInstance(),
                EnglishGrammarUtils.getInstance(),
                DAOTemplateUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param tableTemplate the table template.
     * @param metaDataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param metaDataUtils the MetaDataUtils instance.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @param stringValidator the StringValidator instance.
     * @param englishGrammarUtils the EnglishGrammarUtils instance.
     * @param daoTemplateUtils the DAOTemplateUtils instance.
     * @return such code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition metaDataUtils != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition stringValidator != null
     * @precondition englishGrammarUtils != null
     * @precondition daoTemplateUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final MetaDataUtils metaDataUtils,
        final StringUtils stringUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils,
        final StringValidator stringValidator,
        final EnglishGrammarUtils englishGrammarUtils,
        final DAOTemplateUtils daoTemplateUtils)
    {
        String result = "";

        String t_strTableName = tableTemplate.getTableName();

        String t_strSingularName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        StringTemplateGroup t_Group = retrieveGroup();
        
        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');
        
        String t_strRepositoryName =
            stringUtils.capitalize(repositoryName, '_');

        String t_strValueObjectName =
            englishGrammarUtils.getSingular(
                tableTemplate.getTableName().toLowerCase());

        String t_strCapitalizedValueObjectName =
            stringUtils.capitalize(t_strValueObjectName, '_');

        String[] t_astrPrimaryKeys =
            metaDataManager.getPrimaryKeys(tableTemplate.getTableName());

        int t_iPrimaryKeysLength =
            (t_astrPrimaryKeys != null) ? t_astrPrimaryKeys.length : 0;

        Collection t_cCustomResults = new ArrayList();

        Collection t_cForeignKeyAttributes = new ArrayList();
        
        fillParameters(
            t_Template,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            t_strTableName,
            t_strCapitalizedValueObjectName,
            engineName,
            engineVersion,
            basePackageName,
            packageUtils.retrieveDAOSubpackage(engineName),
            createTimestamp(),
            defaultThemeUtils.buildDAOImplementationClassName(
                t_strCapitalizedEngine, t_strSingularName),
            defaultThemeUtils.buildDAOClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOPackage(basePackageName),
            t_cCustomResults,
            t_cForeignKeyAttributes);
        
        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param tableName the table name.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param subpackage the subpackage.
     * @param timestamp the timestamp.
     * @param className the class name of the DAO.
     * @param baseDAOClassName the class name of the DAO interface.
     * @param baseDAOPackageName the DAO interface package.
     * @param customResults the custom results.
     * @param foreignKeyAttributes the foreign key attributes.
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition tableName != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition basePackageName != null
     * @precondition subpackage != null
     * @precondition timestamp != null
     * @precondition className != null
     * @precondition baseDAOClassName != null
     * @precondition baseDAOPackageName != null
     * @precondition customResults != null
     * @precondition foreignKeyAttributes != null
     */
    protected void fillParameters(
        final StringTemplate template,
        final Integer[] copyrightYears,
        final String tableName,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String subpackage,
        final String timestamp,
        final String className,
        final String baseDAOClassName,
        final String baseDAOPackageName,
        final Collection customResults,
        final Collection foreignKeyAttributes)
    {
        Map input = new HashMap();

        template.setAttribute("input", input);

        fillCommonParameters(input, tableName, engineName, engineVersion);

        fillJavaHeaderParameters(input, copyrightYears, timestamp);

        fillPackageDeclarationParameters(input, basePackageName, subpackage);

        fillProjectImportsParameters(
            input,
            basePackageName,
            subpackage,
            tableName,
            customResults,
            voName,
            foreignKeyAttributes);

        fillClassParameters(
            input,
            voName,
            engineName,
            engineVersion,
            timestamp,
            customResults);
        
        input.put("class_name", className);

        input.put("base_dao_class_name", baseDAOClassName);
        input.put("base_dao_package_name",  baseDAOPackageName);
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @precondition input != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     */
    protected void fillCommonParameters(
        final Map input,
        final String tableName,
        final String engineName,
        final String engineVersion)
    {
        input.put("table_name",  tableName);
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
     * @param subpackage the subpackage.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackage != null
     */
    protected void fillPackageDeclarationParameters(
        final Map input,
        final String basePackageName,
        final String subpackage)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage", subpackage);
    }

    /**
     * Fills the parameters for the <code>project_imports</code> rule.
     * @param input the input.
     * @param basePackageName the base package.
     * @param subpackage the name of the subpackage.
     * @param tableName the table name.
     * @param customResults the custom results.
     * @param voName the name of the value object.
     * @param fkAttributes the foreign-key attributes.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackage != null
     * @precondition tableName != null
     * @precondition customResults != null
     * @precondition voName != null
     * @precondition fkAttributes != null
     */
    protected void fillProjectImportsParameters(
        final Map input,
        final String basePackageName,
        final String subpackage,
        final String tableName,
        final Collection customResults,
        final String voName,
        final Collection fkAttributes)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage", subpackage);
        input.put("table_name", tableName);
        input.put("custom_results", customResults);
        input.put("vo_name", voName);
        input.put("fk_attributes", fkAttributes);
    }

    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param customResults the custom results.
     * @precondition input != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition customResults != null
     */
    protected void fillClassParameters(
        final Map input,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final Collection customResults)
    {
        input.put("vo_name", voName);
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("timestamp", timestamp);
        input.put("custom_results", customResults);
        input.put("vo_name_uppercased", voName.toUpperCase());
        input.put("static_table", "" + Boolean.TRUE); // TODO
    }

    /**
     * Provides the parameters required by
     * <code>static_table</code> rule.
     * @param input the input.
     * @param voName the value object name.
     * @param staticAttributeName the static attribute name.
     * @param staticAttributeType the static attribute type.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition voName != null
     * @precondition staticAttributeName != null
     * @precondition staticAttributeType != null
     * @precondition stringUtils != null
     */
    protected void fillStaticTableParameters(
        final Map input,
        final String voName,
        final String staticAttributeName,
        final String staticAttributeType,
        final StringUtils stringUtils)
    {
        input.put("vo_name", voName);
        input.put("static_attribute_name", staticAttributeName);
        input.put(
            "static_attribute_name_lowercased",
            staticAttributeName.toLowerCase());
        input.put(
            "static_attribute_name_capitalized",
            stringUtils.capitalize(staticAttributeName, '_'));
        input.put("static_attribute_type", staticAttributeType);
    }
}
