/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
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
 * Is able to generate the configuration file for configuring
 * DAOChooser.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class ConfigurationPropertiesTemplate
{
    /**
     * The default header.
     */
    public static final String DEFAULT_HEADER =
          "#\n"
        + "#                       QueryJ\n"
        + "#\n"
        + "#   Copyright (C) 2002  Jose San Leandro Armendariz\n"
        + "#                       jsanleandro@yahoo.es\n"
        + "#                       chousz@yahoo.com\n"
        + "#\n"
        + "#   This library is free software; you can redistribute it and/or\n"
        + "#   modify it under the terms of the GNU General Public\n"
        + "#   License as published by the Free Software Foundation; either\n"
        + "#   version 2 of the License, or any later "
        + "version.\n"
        + "#\n"
        + "#   This library is distributed in the hope that it will be "
        + "useful,\n"
        + "#   but WITHOUT ANY WARRANTY; without even the implied warranty "
        + "of\n"
        + "#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
        + "GNU\n"
        + "#   General Public License for more details.\n"
        + "#\n"
        + "#   You should have received a copy of the GNU General Public\n"
        + "#   License along with this library; if not, write to the Free "
        + "Software\n"
        + "#   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  "
        + "02111-1307  USA\n"
        + "#\n"
        + "#   Thanks to ACM S.L. for distributing this library under the GPL "
        + "license.\n"
        + "#   Contact info: jsanleandro@yahoo.es\n"
        + "#   Postal Address: c/Playa de Lagoa, 1\n"
        + "#                   Urb. Valdecabanas\n"
        + "#                   Boadilla del monte\n"
        + "#                   28660 Madrid\n"
        + "#                   Spain\n"
        + "#\n"
        + "#################################################################"
        + "#############\n"
        + "#\n"
        + "#  Filename: $" + "RCSfile: $\n"
        + "#\n"
        + "#  Author: QueryJ\n"
        + "#\n"
        + "#  Description: Specifies which DAO implementations are used.\n"
        + "#\n"
        + "#  Last modified by: $" + "Author: $ at $" + "Date: $\n"
        + "#\n"
        + "#  File version: $" + "Revision: $\n"
        + "# \n"
        + "#  Project version: $" + "Name: $\n"
        + "#\n"
        + "# $" + "Id: $\n"
        + "#";

    /**
     * The DAO factory settings.
     */
    public static final String DEFAULT_DAO_FACTORY_SETTING =
          "\n\n# {0} DAO implementation.\n"
        + "{1}.{2}.dao={3}.{4}{0}DAOFactory";

    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The DAO factory setting.
     */
    private String m__strDAOFactorySetting;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds a ConfigurationPropertiesTemplate using given information.
     * @param header the header.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param daoFactorySetting the DAO factory setting.
     */
    public ConfigurationPropertiesTemplate(
        String header,
        String repository,
        String engineName,
        String engineVersion,
        String basePackageName,
        String daoFactorySetting)
    {
        inmutableSetHeader(header);
        inmutableSetRepository(repository);
        inmutableSetEngineName(engineName);
        inmutableSetEngineVersion(engineVersion);
        inmutableSetBasePackageName(basePackageName);
        inmutableSetDAOFactorySetting(daoFactorySetting);
        inmutableSetTables(new ArrayList());
    }

    /**
     * Builds a ConfigurationPropertiesTemplate using given information.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     */
    public ConfigurationPropertiesTemplate(
        String repository,
        String engineName,
        String engineVersion,
        String basePackageName)
    {
        this(
            DEFAULT_HEADER,
            repository,
            engineName,
            engineVersion,
            basePackageName,
            DEFAULT_DAO_FACTORY_SETTING);
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void inmutableSetHeader(String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(String header)
    {
        inmutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return such information.
     */
    public String getHeader() 
    {
        return m__strHeader;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    private void inmutableSetRepository(String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(String repository)
    {
        inmutableSetRepository(repository);
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository() 
    {
        return m__strRepository;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void inmutableSetEngineName(String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(String engineName)
    {
        inmutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    public String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void inmutableSetEngineVersion(String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(String engineVersion)
    {
        inmutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    public String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void inmutableSetBasePackageName(String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(String basePackageName)
    {
        inmutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the DAO factory setting.
     * @param daoFactorySetting such property.
     */
    private void inmutableSetDAOFactorySetting(String daoFactorySetting)
    {
        m__strDAOFactorySetting = daoFactorySetting;
    }

    /**
     * Specifies the DAO factory setting.
     * @param daoFactorySetting such property.
     */
    protected void setDAOFactorySetting(String daoFactorySetting)
    {
        inmutableSetDAOFactorySetting(daoFactorySetting);
    }

    /**
     * Retrieves the DAO factory setting.
     * @return such property.
     */
    public String getDAOFactorySetting()
    {
        return m__strDAOFactorySetting;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    private void inmutableSetTables(List tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(List tables)
    {
        inmutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such collection.
     */
    public List getTables()
    {
        return m__lTables;
    }

    /**
     * Adds a new table.
     * @param table the new table.
     */
    public void addTable(String table)
    {
        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            t_lTables.add(table);
        }
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String toString()
    {
        StringBuffer t_sbResult = new StringBuffer();

        StringUtils t_StringUtils = StringUtils.getInstance();

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (t_StringUtils  != null)
             && (t_PackageUtils != null))
        {
            t_sbResult.append(getHeader());

            List t_lTables = getTables();

            if  (t_lTables != null)
            {
                MessageFormat t_DAOFactorySettingFormatter =
                    new MessageFormat(getDAOFactorySetting());

                Iterator t_itTables = t_lTables.iterator();

                while  (   (t_itTables != null)
                        && (t_itTables.hasNext()))
                {
                    String t_strTable = (String) t_itTables.next();

                    if  (t_strTable != null)
                    {
                        String t_strCapitalizedTable =
                            t_StringUtils.capitalize(
                                t_strTable,
                                '_');

                        t_sbResult.append(
                            t_DAOFactorySettingFormatter.format(
                                new Object[]
                                {
                                    t_strCapitalizedTable,
                                    getRepository(),
                                    t_strCapitalizedTable.toLowerCase(),
                                    t_PackageUtils.retrieveDAOFactoryPackage(
                                        getBasePackageName(),
                                        getEngineName()),
                                    t_StringUtils.capitalize(
                                        getEngineName(),
                                        '_')
                                    
                                }));
                    }
                }
            }
        }

        return t_sbResult.toString();
    }
}
