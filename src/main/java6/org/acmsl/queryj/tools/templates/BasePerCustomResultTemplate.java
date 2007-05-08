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

 *****************************************************************************
 *
 * Filename: BasePerCustomResultTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-FK templates.
 *
 * $Id$
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.ResultDecorator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.DefaultThemeUtils;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

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
 * Base logic for all templates to be processed once per custom result.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerCustomResultTemplate
    extends  AbstractBasePerCustomResultTemplate
{
    /**
     * An empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Builds a <code>BasePerCustomResultTemplate</code> using given
     * information.
     * @param result the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @precondition result != null
     * @precondition customSqlProvider != null
     */
    public BasePerCustomResultTemplate(
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            result,
            customSqlProvider,
            metadataManager,
            header,
            decoratorFactory,
            packageName,
            engineName,
            engineVersion,
            basePackageName,
            repositoryName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected abstract StringTemplateGroup retrieveGroup();

    /**
     * Retrieves the template cache.
     * @return such instance.
     */
    private TemplateCache getOwnTemplateCache()
    {
        return
            super.immutableGetTemplateCache(
                buildOwnTemplateCacheKey());
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    protected Object buildTemplateCacheKey()
    {
        return buildOwnTemplateCacheKey();
    }

    /**
     * Builds a key to store the template cache.
     * @return such key.
     */
    private Object buildOwnTemplateCacheKey()
    {
        return "//BasePerCustomResultTemplate//";
    }

    /**
     * Retrieves an item from the cache.
     * @param key the item key.
     * @return the item itself.
     * @precondition key != null
     */
    protected Object getFromCache(final Object key)
    {
        Object result = null;

        TemplateCache t_TemplateCache = getTemplateCache();

        if  (t_TemplateCache != null)
        {
            result = t_TemplateCache.get(key);
        }

        if  (result == null)
        {
            t_TemplateCache = getOwnTemplateCache();

            if  (t_TemplateCache != null)
            {
                result = t_TemplateCache.get(key);
            }
        }

        return result;
    }

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @return such code.
     */
    protected String generateOutput(final String header)
    {
        return generateOutput(header, getMetadataManager());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param metadataManager the metadata manager.
     * @return such code.
     * @precondition metadataManager != null
     */
    protected String generateOutput(
        final String header, final MetadataManager metadataManager)
    {
        return
            generateOutput(
                getResult(),
                header,
                getCustomSqlProvider(),
                metadataManager,
                getDecoratorFactory(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getBasePackageName(),
                getRepositoryName(),
                StringUtils.getInstance(),
                DefaultThemeUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code generated by this template.
     * @param customResult the custom result.
     * @param header the header.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param stringUtils the StringUtils instance.
     * @param defaultThemeUtils the <code>DefaultThemeUtils</code> instance.
     * @param packageUtils the PackageUtils instance.
     * @return such code.
     * @precondition customResult != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition stringUtils != null
     * @precondition defaultThemeUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final Result customResult,
        final String header,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName,
        final StringUtils stringUtils,
        final DefaultThemeUtils defaultThemeUtils,
        final PackageUtils packageUtils)
    {
        String result = "";

        StringTemplateGroup t_Group = retrieveGroup();

        StringTemplate t_Template = retrieveTemplate(t_Group);

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
            customResult,
            header,
            customSqlProvider,
            metadataManager,
            decoratorFactory,
            engineName,
            engineVersion,
            basePackageName,
            packageUtils.retrieveDAOSubpackage(engineName),
            createTimestamp(),
            t_strRepositoryName,
            stringUtils);

        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param result the result.
     * @param header the header.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param timestamp the timestamp.
     * @param className the class name of the DAO.
     * @param tableRepositoryName the table repository.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final Map input,
        final StringTemplate template,
        final Integer[] copyrightYears,
        final Result customResult,
        final String header,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String subpackageName,
        final String timestamp,
        final String tableRepositoryName,
        final StringUtils stringUtils)
    {
        template.setAttribute("input", input);

        fillCommonParameters(
            input,
            customResult,
            customSqlProvider,
            metadataManager,
            decoratorFactory,
            engineName,
            engineVersion);

        fillJavaHeaderParameters(input, header, copyrightYears, timestamp);

        fillPackageDeclarationParameters(
            input, basePackageName, subpackageName);

        fillProjectImportsParameters(
            input, basePackageName, subpackageName);

        fillClassParameters(
            input,
            customResult,
            customSqlProvider,
            engineName,
            engineVersion,
            timestamp,
            tableRepositoryName);
    }

    /**
     * Fills the common parameters.
     * @param input the input.
     * @param result the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @precondition input != null
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     */
    protected void fillCommonParameters(
        final Map input,
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final String engineName,
        final String engineVersion)
    {
        input.put(
            "result",
            decoratorFactory.createDecorator(
                result, customSqlProvider, metadataManager));
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
    }

    /**
     * Fills the parameters for <code>java_header</code> rule.
     * @param input the input.
     * @param header the header.
     * @param copyrightYears the copyright years.
     * @param timestamp the timestamp.
     * @precondition input != null
     * @precondition copyrightYears != null
     * @precondition timestamp != null
     */
    protected void fillJavaHeaderParameters(
        final Map input,
        final String header,
        final Integer[] copyrightYears,
        final String timestamp)
    {
        input.put("copyright_years", copyrightYears);
        input.put("timestamp", timestamp);

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
     * Fills the parameters for the <code>project_imports</code> rule.
     * @param input the input.
     * @param basePackageName the base package.
     * @param subpackageName the name of the subpackage.
     * @precondition input != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     */
    protected void fillProjectImportsParameters(
        final Map input,
        final String basePackageName,
        final String subpackageName)
    {
        input.put("base_package_name", basePackageName);
        input.put("subpackage_name", subpackageName);
    }

    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param customResult the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param tableRepositoryName the table repository name.
     * @precondition input != null
     * @precondition customResult != null
     * @precondition customSqlProvider != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     */
    protected void fillClassParameters(
        final Map input,
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final String tableRepositoryName)
    {
        input.put("engine_name", engineName);
        input.put("engine_version", engineVersion);
        input.put("timestamp", timestamp);

        input.put("tr_name", tableRepositoryName);
        input.put("tr_name_normalized", normalize(tableRepositoryName));
        input.put("tr_name_capitalized", capitalize(tableRepositoryName));
    }
}
