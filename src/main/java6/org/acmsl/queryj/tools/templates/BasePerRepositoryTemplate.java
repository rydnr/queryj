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
 * Filename: BasePerRepositoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.SingularPluralFormConverter;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.PackageUtils;

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
 * Base logic for all per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerRepositoryTemplate
    extends  AbstractBasePerRepositoryTemplate
{
    /**
     * Builds a <code>BasePerRepositoryTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager. 
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param header the header.
     * @param jmx whether to support JMX or not.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public BasePerRepositoryTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final boolean jmx,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables)
    {
        super(
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            header,
            jmx,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            tables);
    }

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
        return "//BasePerRepositoryTemplate//";
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
        return
            generateOutput(
                header,
                getMetadataManager(),
                getMetadataTypeManager(),
                getCustomSqlProvider(),
                getDecoratorFactory(),
                getSubpackageName(),
                getBasePackageName(),
                getRepositoryName(),
                getEngineName(),
                getTables(),
                StringUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected abstract StringTemplateGroup retrieveGroup();

    /**
     * Retrieves the source code generated by this template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param stringUtils the StringUtils instance.
     * @param packageUtils the PackageUtils instance.
     * @return such code.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlprovider != null
     * @precondition decoratorFactory != null
     * @precondition subpackageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     * @precondition tables != null
     * @precondition stringUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final String header,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final DecoratorFactory decoratorFactory,
        final String subpackageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables,
        final StringUtils stringUtils,
        final PackageUtils packageUtils)
    {
        String result = "";

        StringTemplateGroup t_Group = retrieveGroup();

        StringTemplate t_Template = retrieveTemplate(t_Group);

        fillParameters(
            new HashMap(),
            t_Template,
            header,
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            decoratorFactory,
            subpackageName,
            basePackageName,
            repositoryName,
            engineName,
            tables,
            createTimestamp(),
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            stringUtils);

        result = t_Template.toString();

        return result;
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param header the header.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the database metadata type manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param timestamp the timestamp.
     * @param tableRepositoryName the table repository.
     * @param engineName the engine name.
     * @param tables the table names.
     * @param timestamp the timestamp.
     * @param copyrightYears the copyright years.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition template != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlProvider != null
     * @precondition decoratorFactory != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition tableRepositoryName != null
     * @precondition tables != null
     * @precondition timestamp != null
     * @precondition copyrightYears != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final Map input,
        final StringTemplate template,
        final String header,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final DecoratorFactory decoratorFactory,
        final String subpackageName,
        final String basePackageName,
        final String tableRepositoryName,
        final String engineName,
        final Collection tables,
        final String timestamp,
        final Integer[] copyrightYears,
        final StringUtils stringUtils)
    {
        template.setAttribute("input", input);

        fillHeaderParameters(input, header, copyrightYears, timestamp);

        fillCoreParameters(
            input,
            metadataManager,
            customSqlProvider,
            decoratorFactory,
            basePackageName,
            subpackageName,
            tableRepositoryName,
            engineName,
            tables,
            timestamp,
            stringUtils);
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
    protected void fillHeaderParameters(
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
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition decoratorFactory != null
     * @precondition subpackageName != null
     * @precondition basePackageName != null
     * @precondition tableRepositoryName != null
     * @precondition tables != null
     * @precondition timestamp != null
     * @precondition stringUtils != null
     */
    protected void fillCoreParameters(
        final Map input,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final DecoratorFactory decoratorFactory,
        final String subpackageName,
        final String basePackageName,
        final String tableRepositoryName,
        final String engineName,
        final Collection tables,
        final String timestamp,
        final StringUtils stringUtils)
    {
        input.put("tr_name", tableRepositoryName);

        input.put("tr_name_capitalized", capitalize(tableRepositoryName));
        input.put("tr_name_normalized", normalize(tableRepositoryName));

        input.put("base_package_name", basePackageName);

        input.put("engine_name", engineName);

        input.put(
            "engine_name_lowercased",
            lowercase(engineName, DecorationUtils.getInstance()));

        input.put(
            "dao_subpackage_name",
            retrieveDAOSubpackageName(
                basePackageName, engineName, PackageUtils.getInstance()));

        input.put(
            "tables",
            decorateTables(tables, metadataManager, decoratorFactory));

        input.put(
            "repository_dao",
            definesRepositoryScopedSql(customSqlProvider)
            ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Retrieves the DAO subpackage name.
     * @param basePackageName the base package name.
     * @param engineName the engine name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such information.
     */
    protected String retrieveDAOSubpackageName(
        final String basePackageName,
        final String engineName,
        final PackageUtils packageUtils)
    {
        return packageUtils.retrieveDAOPackage(basePackageName, engineName);
    }

    /**
     * Decorates the tables.
     * @param tables the tables.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated tables.
     * @precondition tables != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected Collection decorateTables(
        final Collection tables,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        Collection result = new ArrayList();

        Iterator t_itTableIterator = tables.iterator();

        if  (t_itTableIterator != null)
        {
            while  (t_itTableIterator.hasNext())
            {
                result.add(
                    decorate(
                        (String) t_itTableIterator.next(),
                        metadataManager,
                        decoratorFactory));
            }
        }

        return result;
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated table.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected TableDecorator decorate(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.createTableDecorator(table, metadataManager);
    }

    /**
     * Checks whether given custom SQL provider defines any repository-scope
     * SQL or not.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @return <code>true</code> in such case.
     * @precondition customSqlProvider != null
     */
    protected boolean definesRepositoryScopedSql(
        final CustomSqlProvider customSqlProvider)
    {
        boolean result = false;

        Collection t_cContents = customSqlProvider.getCollection();

        if  (t_cContents != null)
        {
            Object t_Content = null;
            Sql t_Sql = null;
            String t_strDao;
            boolean t_bMatches;

            Iterator t_itContentIterator = t_cContents.iterator();

            if  (t_itContentIterator != null)
            {
                while  (t_itContentIterator.hasNext())
                {
                    t_Content = t_itContentIterator.next();

                    if  (t_Content instanceof Sql)
                    {
                        t_Sql = (Sql) t_Content;

                        t_strDao = t_Sql.getDao();

                        if  (t_strDao == null)
                        {
                            result =
                                (t_Sql.getRepositoryScope() != null);

                            if  (result)
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}