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
 * Description: Is able to generate the configuration file for configuring
 *              DAOChooser.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;

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
import java.util.Collection;
import java.util.Map;

/**
 * Is able to generate the configuration file for configuring
 * DAOChooser.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ConfigurationPropertiesTemplate
    extends  AbstractConfigurationPropertiesTemplate
    implements  ConfigurationPropertiesTemplateDefaults
{
    /**
     * Builds a <code>ConfigurationPropertiesTemplate</code> using given
     * information.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     */
    public ConfigurationPropertiesTemplate(
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName)
    {
        super(
            DEFAULT_HEADER,
            repository,
            engineName,
            engineVersion,
            basePackageName,
            DEFAULT_DAO_FACTORY_SETTING);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return
            buildHeader(
                getRepository(),
                DAOChooserTemplateUtils.getInstance());
    }

    /**
     * Builds the header for logging purposes.
     * @param repository the repository.
     * @param daoChooserTemplateUtils the <code>DAOChooserTemplateUtils</code>
     * instance.
     * @return such header.
     * @precondition repository != null
     * @precondition daoChooserTemplateUtils != null
     */
    protected String buildHeader(
        final String repository,
        final DAOChooserTemplateUtils daoChooserTemplateUtils)
    {
        return
              "Generating "
            + daoChooserTemplateUtils.retrievePropertiesFileName(
                  repository.toLowerCase())
            + ".";
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    protected String generateOutput()
    {
        return
            generateOutput(
                getHeader(),
                getRepository(),
                getEngineName(),
                getEngineVersion(),
                getBasePackageName(),
                getDAOFactorySetting(),
                getTables(),
                PackageUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param daoFactorySetting the DAO factory setting.
     * @param tables the tables.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition packageUtils != null
     * @precondition englishGrammarUtils != null
     * @precondition stringUtils != null
     */
    protected String generateOutput(
        final String header,
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String daoFactorySetting,
        final Collection tables,
        final PackageUtils packageUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(header);

        MessageFormat t_DAOFactorySettingFormatter =
            new MessageFormat(daoFactorySetting);

        Iterator t_itTables = tables.iterator();

        while  (   (t_itTables != null)
                && (t_itTables.hasNext()))
        {
            String t_strTable = (String) t_itTables.next();

            if  (t_strTable != null)
            {
                String t_strCapitalizedTable =
                    stringUtils.capitalize(
                        englishGrammarUtils.getSingular(
                            t_strTable.toLowerCase()),
                        '_');

                t_sbResult.append(
                    t_DAOFactorySettingFormatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTable,
                            repository,
                            t_strCapitalizedTable.toLowerCase(),
                            packageUtils.retrieveDAOFactoryPackage(
                                basePackageName,
                                engineName),
                            stringUtils.capitalize(
                                engineName,
                                '_'),
                            packageUtils.retrieveMockDAOFactoryPackage(
                                basePackageName),
                            packageUtils.retrieveXMLDAOFactoryPackage(
                                basePackageName)
                        }));
            }
        }

        return t_sbResult.toString();
    }
}
