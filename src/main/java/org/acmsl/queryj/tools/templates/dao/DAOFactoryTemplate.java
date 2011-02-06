//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

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
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOFactoryTemplate
     extends AbstractDAOFactoryTemplate
{
    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     */
    public DAOFactoryTemplate(
        final String header,
        final DecoratorFactory decoratorFactory,
        final TableTemplate tableTemplate,
        final String packageName,
        final String engineName,
        final String basePackageName,
        final String jndiDataSource)
    {
        super(
            header,
            decoratorFactory,
            tableTemplate,
            packageName,
            engineName,
            null,
            null,
            basePackageName,
            jndiDataSource);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @return such source code.
     */
    protected String generateOutput(final String header)
    {
        return
            generateOutput(
                header,
                getTableTemplate(),
                getPackageName(),
                getEngineName(),
                getBasePackageName(),
                getJNDIDataSource(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance());
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
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final String header,
        final TableTemplate tableTemplate,
        final String packageName,
        final String engineName,
        final String basePackageName,
        final String jndiDataSource,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils)
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
            jndiDataSource);

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
     */
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
        final String jndiLocation)
    {
        Map input = new HashMap();

        template.setAttribute("input", input);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName);

        input.put("copyright_years", copyrightYears);

        input.put("table_name",  tableName);
        input.put("engine_name", engineName);
        input.put("timestamp", timestamp);

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
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAOFactory.stg");
    }
}
