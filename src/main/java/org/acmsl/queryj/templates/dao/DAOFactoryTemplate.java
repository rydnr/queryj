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
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.templates.DefaultThemeUtils;

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
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     extends AbstractDAOFactoryTemplate<BasePerTableTemplateContext>
{
    private static final long serialVersionUID = -836140578744901008L;

    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param context the {@link BasePerTableTemplateContext context}.
     */
    public DAOFactoryTemplate(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected String generateOutput(@NotNull final String header, @NotNull final BasePerTableTemplateContext context)
    {
        return generateOutput(header, context, context.getMetadataManager());
    }

    /**
     * Generates the source code from given context.
     * @param header the header.
     * @param context the {@link BasePerTableTemplateContext context}.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    @NotNull
    protected String generateOutput(
        @NotNull final String header,
        @NotNull final BasePerTableTemplateContext context,
        @NotNull final MetadataManager metadataManager)
    {
        return
            generateOutput(
                header,
                context.getTableName(),
                context.getPackageName(),
                metadataManager.getEngineName(),
                context.getBasePackageName(),
                context.getJndiLocation(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param tableName the table name.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected String generateOutput(
        @NotNull final String header,
        @NotNull final String tableName,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String basePackageName,
        @NotNull final String jndiDataSource,
        @NotNull final StringUtils stringUtils,
        @NotNull final EnglishGrammarUtils englishGrammarUtils,
        @NotNull final DefaultThemeUtils defaultThemeUtils,
        @NotNull final PackageUtils packageUtils)
    {
        String result = "";

        @Nullable StringTemplateGroup t_Group = retrieveGroup();

        @Nullable StringTemplate t_Template = retrieveTemplate(t_Group);

        if (t_Template != null)
        {
            String t_strSingularName =
                stringUtils.capitalize(
                    englishGrammarUtils.getSingular(
                        tableName.toLowerCase()),
                    '_');

            String t_strCapitalizedEngine =
                stringUtils.capitalize(engineName, '_');

            fillParameters(
                t_Template,
                new Integer[]
                {
                    STARTING_YEAR,
                    retrieveCurrentYear()
                },
                header,
                tableName,
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
        }

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
     */
    @SuppressWarnings("unused,unchecked")
    protected void fillParameters(
        @NotNull final StringTemplate template,
        @NotNull final Integer[] copyrightYears,
        @NotNull final String header,
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final String basePackageName,
        @NotNull final String subpackageName,
        @NotNull final String timestamp,
        @NotNull final String daoFactoryImplementationClassName,
        @NotNull final String daoFactoryImplementationPackageName,
        @NotNull final String daoClassName,
        @NotNull final String daoPackageName,
        @NotNull final String daoFactoryClassName,
        @NotNull final String daoFactoryPackageName,
        @NotNull final String daoImplementationClassName,
        @NotNull final String daoImplementationPackageName,
        @NotNull final String jndiLocation)
    {
        @NotNull Map input = new HashMap();

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

        input.put("header", processHeader(input, header));
    }

    /**
     * Fills the parameters for <code>package_declaration</code> rule.
     * @param input the input.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     */
    @SuppressWarnings("unchecked")
    protected void fillPackageDeclarationParameters(
        @NotNull final Map input,
        @NotNull final String basePackageName,
        @NotNull final String subpackageName)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Returns "DAOFactory".
     * @return such name.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return "DAOFactory";
    }


}
