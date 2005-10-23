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
 * Description: Is able to generate DAO factories according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
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
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

/*
 * Importing some JDK classes.
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate DAO factories according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOFactoryTemplate
    extends  AbstractDAOFactoryTemplate
    implements  DAOFactoryTemplateDefaults
{
    /**
     * The starting year.
     */
    public static final Integer STARTING_YEAR = new Integer(2002);

    /**
     * The timestamp formatter.
     */
    public static final DateFormat TIMESTAMP_FORMATTER =
        new SimpleDateFormat("HH:ss yyyy/MM/dd");
    
    /**
     * Builds a <code>DAOFactoryTemplate</code> using given information.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     */
    public DAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource)
    {
        super(
            tableTemplate,
            metaDataManager,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            jndiDataSource);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getTableTemplate(),
                getMetaDataManager(),
                getPackageName(),
                getEngineName(),
                getEngineVersion(),
                getQuote(),
                getBasePackageName(),
                getJNDIDataSource(),
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                MetaDataUtils.getInstance(),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param tableTemplate the table template.
     * @param metaDataManager the metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param jndiDataSource the JNDI location of the data source.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such source code.
     * @precondition tableTemplate != null
     * @precondition metaDataManager != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition metaDataUtils != null
     * @precondition packageUtils != null
     */
    protected String generateOutput(
        final TableTemplate tableTemplate,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String jndiDataSource,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final MetaDataUtils metaDataUtils,
        final PackageUtils packageUtils)
    {
        String result = "";

        String t_strTableName = tableTemplate.getTableName();

        String t_strSingularName =
            stringUtils.capitalize(
                englishGrammarUtils.getSingular(
                    t_strTableName.toLowerCase()),
                '_');

        StringTemplateGroup group =
            retrieveGroup(
                "/org/acmsl/queryj/dao/DAOFactory.st",
                "/org/acmsl/queryj/queryj.st");

        StringTemplate source = group.getInstanceOf("source");

        fillParameters(
            source,
            new Integer[]
            {
                STARTING_YEAR,
                new Integer(retrieveCurrentYear())
            },
            t_strTableName,
            engineName,
            createTimestamp(),
            buildDAOClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOPackage(basePackageName),
            buildDAOFactoryClassName(t_strSingularName),
            packageUtils.retrieveBaseDAOFactoryPackage(
                basePackageName),
            buildDAOFactoryImplementationClassName(
                stringUtils.capitalize(engineName, '_'),
                t_strSingularName),
            packageUtils.retrieveDAOPackage(
                basePackageName, engineName),
            jndiDataSource);
        
        result = source.toString();

        return result;
    }
    
    /**
     * Fills the template parameters.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param timestamp the timestamp.
     * @param daoClassName the class name of the DAO.
     * @param daoPackageName the DAO package.
     * @param daoFactoryClassName the class name of the
     * DAO factory.
     * @precondition daoFactoryPackageName the package
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
     * @precondition timestamp != null
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
        final String tableName,
        final String engineName,
        final String timestamp,
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

        input.put(
            "copyright_years", copyrightYears);

        input.put("table_name",  tableName);
        input.put("engine_name", engineName);
        input.put("timestamp", timestamp);
        
        input.put("package", daoPackageName);

        input.put(
            "dao_class_name", daoClassName);
        
        input.put("dao_package_name",  daoPackageName);
        
        input.put(
            "dao_factory_class_name", daoFactoryClassName);
        
        input.put(
            "dao_factory_package_name", daoFactoryPackageName);
        
        input.put(
            "dao_implementation_class_name",
            daoImplementationClassName);

        input.put(
            "dao_implementation_package_name",
            daoImplementationPackageName);

        input.put("jndi_location", jndiLocation);
    }

    /**
     * Configures given <code>StringTemplate</code> instance.
     * @param stringTemplate such template.
     * @precondition stringTemplate != null
     */
    protected void configure(final StringTemplate stringTemplate)
    {
        stringTemplate.setPassThroughAttributes(true);
        stringTemplate.setLintMode(true);
    }

    /**
     * Retrieves the current year.
     * @return such value.
     */
    protected int retrieveCurrentYear()
    {
        return retrieveYear(Calendar.getInstance());
    }
    
    /**
     * Retrieves the year defined in given date.
     * @param calendar the calendar.
     * @return such value.
     * @precondition calendar != null
     */
    protected int retrieveYear(final Calendar calendar)
    {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Creates a timestamp.
     * @return such timestamp.
     */
    protected String createTimestamp()
    {
        return createTimestamp(new Date(), TIMESTAMP_FORMATTER);
    }
    
    /**
     * Formats given date.
     * @param date the date.
     * @param formatter the formatter.
     * @return the formatted date.
     * @precondition date != null
     * @precondition formatter != null
     */
    protected String createTimestamp(
        final Date date, final DateFormat formatter)
    {
        return formatter.format(date);
    }

    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     */
    protected StringTemplateGroup retrieveGroup(
        final String path, final String theme)
    {
        StringTemplateGroup result = null;
        
        InputStream themeInput =
            getClass().getResourceAsStream(theme);

        InputStream groupInput =
            getClass().getResourceAsStream(path);

        StringTemplateGroup themeGroup =
            new StringTemplateGroup(
                new InputStreamReader(themeInput),
                AngleBracketTemplateLexer.class);

        result =
            new StringTemplateGroup(
                new InputStreamReader(groupInput),
                AngleBracketTemplateLexer.class);

        result.setSuperGroup(theme);

        return result;
    }

    /**
     * Builds the base DAO name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition tableName != null
     */
    protected String buildDAOClassName(final String tableName)
    {
        return tableName + "DAO";
    }

    /**
     * Builds the base DAO factory name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition tableName != null
     */
    protected String buildDAOFactoryClassName(
        final String tableName)
    {
        return buildDAOClassName(tableName) + "Factory";
    }

    /**
     * Builds the base DAO factory implementation name.
     * @param engineName the engine name.
     * @param tableName the table name, in singular form.
     * @return such name.
     * @precondition engineName != null
     * @precondition tableName != null
     */
    protected String buildDAOFactoryImplementationClassName(
        final String engineName, final String tableName)
    {
        return engineName + buildDAOFactoryClassName(tableName);
    }
}
