//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DAOFactoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOFactoryTemplate
    extends  BasePerTableTemplate
{
    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param jmx whether to support JMX.
     * @param jndiLocation the location of the datasource in JNDI.
     */
    public DAOFactoryTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName,
        final boolean jmx,
        final String jndiLocation)
    {
        super(
            tableName,
            metadataManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName,
            jmx,
            jndiLocation);
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    protected Object buildTemplateCacheKey()
    {
        return "//DAOTemplate//";
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAOFactory.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "DAOFactory";
    }

    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param customResults the custom results.
     * @param staticTable whether the table is static or not.
     * @param tableRepositoryName the table repository name.
     * @param tableName the table name.
     * @param pkAttributes the primary key attributes.
     * @param nonPkAttributes the ones not part of the primary key.
     * @param fkAttributes the foreign key attributes.
     * @param referingKeys the foreign keys of other tables pointing
     * to this one. It's expected to be
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @param attributes the attributes.
     * @param externallyManagedAttributes the attributes which are
     * managed externally.
     * @param allButExternallyManagedAttributes all but the attributes which
     * are managed externally.
     * @param lobAttributes all attributes whose type is Clob or Blob.
     * @param allButLobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param parentTable the parent table, or <code>null</code> if not
     * part of an ISA relationship.
     * @param customSelects the custom selects.
     * @param customUpdatesOrInserts the custom updates and inserts.
     * @param customSelectsForUpdate the custom selects for update.
     * @param customResults the custom results.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition input != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     * @precondition tableName != null
     * @precondition pkAttributes != null
     * @precondition nonPkAttributes != null
     * @precondition fkAttributes != null
     * @precondition attributes != null
     * @precondition externallyManagedAttributes != null
     * @precondition allButExternallyManagedAttributes != null
     * @precondition lobAttributes != null
     * @precondition allButLobAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customUpdatesOrInserts != null
     * @precondition customSelectsForUpdate != null
     * @precondition customResults != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void fillClassParameters(
        final Map input,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final boolean staticTable,
        final String tableRepositoryName,
        final String tableName,
        final Collection pkAttributes,
        final List nonPkAttributes,
        final Collection fkAttributes,
        final Map referingKeys,
        final List attributes,
        final Collection externallyManagedAttributes,
        final List allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final List allButLobAttributes,
        final Collection foreignKeys,
        final String staticAttributeName,
        final String staticAttributeType,
        final String parentTable,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super.fillClassParameters(
            input,
            voName,
            engineName,
            engineVersion,
            timestamp,
            staticTable,
            tableRepositoryName,
            tableName,
            pkAttributes,
            nonPkAttributes,
            fkAttributes,
            referingKeys,
            attributes,
            externallyManagedAttributes,
            allButExternallyManagedAttributes,
            lobAttributes,
            allButLobAttributes,
            foreignKeys,
            staticAttributeName,
            staticAttributeType,
            parentTable,
            customSelects,
            customUpdatesOrInserts,
            customSelectsForUpdate,
            customResults,
            metadataManager,
            decoratorFactory);

        input.put("jndi_location", getJndiLocation());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param repositoryName the repository name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param singularPluralFormConverter the <code>SingularPluralFormConverter</code>
     * instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition stringUtils != null
     * @precondition singularPluralFormConverter != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     * @precondition decorationUtils != null
     *
    protected String generateOutput(
        final String header,
        final TableTemplate tableTemplate,
        final String packageName,
        final String engineName,
        final String basePackageName,
        final String jndiDataSource,
        final String repositoryName,
        final StringUtils stringUtils,
        final EnglishGrammarUtils singularPluralFormConverter,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils,
        final DecorationUtils decorationUtils)
    {
        String result = "";

        String t_strTableName = tableTemplate.getTableName();

        String t_strSingularName =
            stringUtils.capitalize(
                singularPluralFormConverter.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        String t_strCapitalizedRepositoryName =
            decorationUtils.capitalize(repositoryName);

        StringTemplateGroup t_Group = retrieveGroup();

        StringTemplate t_Template = retrieveTemplate(t_Group);

        String t_strCapitalizedEngine =
            stringUtils.capitalize(engineName, '_');

        fillParameters(
            t_Template,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            header,
            t_strTableName,
            engineName,
            basePackageName,
            packageUtils.retrieveDAOSubpackage(engineName),
            createTimestamp(),
            defaultThemeUtils.buildDAOFactoryImplementationClassName(
                t_strCapitalizedEngine, t_strSingularName),
            packageUtils.retrieveDAOFactoryPackage(
                basePackageName, engineName),
            defaultThemeUtils.buildDAOClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOPackage(basePackageName),
            defaultThemeUtils.buildDAOFactoryClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOFactoryPackage(
                basePackageName),
            defaultThemeUtils.buildDAOImplementationClassName(
                t_strCapitalizedEngine, t_strSingularName),
            packageUtils.retrieveDAOPackage(
                basePackageName, engineName),
            jndiDataSource,
            t_strCapitalizedRepositoryName);

        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param header the header.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param timestamp the timestamp.
     * @param daoFactoryImplementationClassName the class name
     * of the factory implementation.
     * @param daoFactoryImplementationPackageName the package name
     * of the factory implementation.
     * @param daoClassName the class name of the DAO.
     * @param daoPackageName the DAO package.
     * @param daoFactoryClassName the class name of the
     * DAO factory.
     * @param daoFactoryPackageName the package
     * of the DAO factory.
     * @param daoImplementationClassName the class name
     * of the DAO implementation.
     * @param daoImplementationPackageName the package
     * of the DAO implementation.
     * @param jndiLocation the JNDI location.
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition tableName != null
     * @precondition engineName != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition timestamp != null
     * @precondition daoFactoryImplementationClassName != null
     * @precondition daoFactoryImplementationPackageName != null
     * @precondition daoClassName != null
     * @precondition daoPackageName != null
     * @precondition daoFactoryClassName != null
     * @precondition daoFactoryPackageName != null
     * @precondition daoImplementationClassName != null
     * @precondition daoImplementationPackageName != null
     * @precondition jndiLocation != null
     *
    protected void fillParameters(
        final StringTemplate template,
        final Integer[] copyrightYears,
        final String header,
        final String tableName,
        final String engineName,
        final String basePackageName,
        final String subpackageName,
        final String timestamp,
        final String daoFactoryImplementationClassName,
        final String daoFactoryImplementationPackageName,
        final String daoClassName,
        final String daoPackageName,
        final String daoFactoryClassName,
        final String daoFactoryPackageName,
        final String daoImplementationClassName,
        final String daoImplementationPackageName,
        final String jndiLocation,
        final String capitalizedRepositoryName)
    {
        Map input = new HashMap();

        template.setAttribute("input", input);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName);

        input.put("copyright_years", copyrightYears);

        input.put("table_name",  tableName);
        input.put("engine_name", engineName);
        input.put("timestamp", timestamp);

        input.put("tr_name_capitalized", capitalizedRepositoryName);
        
        input.put("class_name", daoFactoryImplementationClassName);

        input.put("dao_class_name", daoClassName);

        input.put("dao_package_name",  daoPackageName);

        input.put(
            "dao_factory_class_name", daoFactoryClassName);

        input.put(
            "dao_factory_package_name", daoFactoryPackageName);

        input.put(
            "dao_implementation_class_name", daoImplementationClassName);

        input.put(
            "dao_implementation_package_name", daoImplementationPackageName);

        input.put("jndi_location", jndiLocation);

        if  (   (header != null)
             && (!input.containsKey("header")))
        {
            input.put("header", processHeader(input, header));
        }
    }

    /**
     * Fills the parameters for <code>package_declaration</code> rule.
     * @param input the input.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     *
    protected void fillPackageDeclarationParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
    }
    */
}
