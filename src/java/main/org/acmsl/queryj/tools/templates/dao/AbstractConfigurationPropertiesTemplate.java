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
 * Description: Contains the required subtemplates used to generate the
 *              configuration file for configuring DAOChooser.
 *
 */
package org.acmsl.queryj.tools.templates.dao;


/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.templates.AbstractTemplate;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
/**
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the required subtemplates used to generate the configuration
 * file for configuring <code>DAOChooser</code> singleton.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractConfigurationPropertiesTemplate
    extends  AbstractTemplate
{
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
     * Builds a <code>AbstractConfigurationPropertiesTemplate</code>
     * using given information.
     * @param header the header.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param daoFactorySetting the DAO factory setting.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    protected AbstractConfigurationPropertiesTemplate(
        final String header,
        final String repository,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String daoFactorySetting,
        final Project project,
        final Task task)
    {
        super(project, task);
        immutableSetHeader(header);
        immutableSetRepository(repository);
        immutableSetEngineName(engineName);
        immutableSetEngineVersion(engineVersion);
        immutableSetBasePackageName(basePackageName);
        immutableSetDAOFactorySetting(daoFactorySetting);
        immutableSetTables(new ArrayList());
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    private void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the new header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
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
    private void immutableSetRepository(final String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    protected void setRepository(final String repository)
    {
        immutableSetRepository(repository);
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
    private void immutableSetEngineName(final String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(final String engineName)
    {
        immutableSetEngineName(engineName);
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
    private void immutableSetEngineVersion(final String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(final String engineVersion)
    {
        immutableSetEngineVersion(engineVersion);
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
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
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
    private void immutableSetDAOFactorySetting(final String daoFactorySetting)
    {
        m__strDAOFactorySetting = daoFactorySetting;
    }

    /**
     * Specifies the DAO factory setting.
     * @param daoFactorySetting such property.
     */
    protected void setDAOFactorySetting(final String daoFactorySetting)
    {
        immutableSetDAOFactorySetting(daoFactorySetting);
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
    private void immutableSetTables(final List tables)
    {
        m__lTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected void setTables(final List tables)
    {
        immutableSetTables(tables);
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
    public void addTable(final String table)
    {
        List t_lTables = getTables();

        if  (t_lTables != null) 
        {
            t_lTables.add(table);
        }
    }
}
