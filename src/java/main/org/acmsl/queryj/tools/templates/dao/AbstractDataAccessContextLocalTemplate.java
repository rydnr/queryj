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
 * Description: Contains the required subtemplates used to generate the
 *              the dataAccesContext-local file for declaring
 *              the transactional behaviour.
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
 * Contains the required subtemplates used to generate the dataAccesContext-local file
 * for declaring the transactional behaviour.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractDataAccessContextLocalTemplate
    extends  AbstractTemplate
{
    /**
     * The header.
     */
    private String m__strHeader;

    /**
     * The JNDI location.
     */
    private String m__strJNDILocation;

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
     * The resource definition.
     */
    private String m__strResourceDefinition;

    /**
     * The DAO definition.
     */
    private String m__strDAODefinition;

    /**
     * The table list.
     */
    private List m__lTables;

    /**
     * Builds a <code>AbstractDataAccessContextLocalTemplate</code>
     * using given information.
     * @param header the header.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param resourceDefinition the resource definitions subtemplate.
     * @param daoDefinition the DAO definitions subtemplate.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    protected AbstractDataAccessContextLocalTemplate(
        final String header,
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String resourceDefinition,
        final String daoDefinition,
        final Project project,
        final Task task)
    {
        super(project, task);
        immutableSetHeader(header);
        immutableSetJNDILocation(jndiLocation);
        immutableSetEngineName(engineName);
        immutableSetEngineVersion(engineVersion);
        immutableSetBasePackageName(basePackageName);
        immutableSetResourceDefinition(resourceDefinition);
        immutableSetDAODefinition(daoDefinition);
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
     * Specifies the JNDI location.
     * @param location such value.
     */
    private void immutableSetJNDILocation(final String location)
    {
        m__strJNDILocation = location;
    }

    /**
     * Specifies the JNDI location.
     * @param location such value.
     */
    protected void setJNDILocation(final String location)
    {
        immutableSetJNDILocation(location);
    }

    /**
     * Retrieves the JNDI location.
     * @return such value.
     */
    public String getJNDILocation()
    {
        return m__strJNDILocation;
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
     * Specifies the resource definition subtemplate.
     * @param resourceDefinitionSubtemplate such property.
     */
    private void immutableSetResourceDefinition(final String resourceDefinitionSubtemplate)
    {
        m__strResourceDefinition = resourceDefinitionSubtemplate;
    }

    /**
     * Specifies the resource definition subtemplate.
     * @param resourceDefinitionSubtemplate such property.
     */
    protected void setResourceDefinition(final String resourceDefinitionSubtemplate)
    {
        immutableSetResourceDefinition(resourceDefinitionSubtemplate);
    }

    /**
     * Retrieves the resource definition subtemplate.
     * @return such property.
     */
    public String getResourceDefinition()
    {
        return m__strResourceDefinition;
    }

    /**
     * Specifies the DAO definition subtemplate.
     * @param daoDefinitionSubtemplate such property.
     */
    private void immutableSetDAODefinition(final String daoDefinitionSubtemplate)
    {
        m__strDAODefinition = daoDefinitionSubtemplate;
    }

    /**
     * Specifies the DAO definition subtemplate.
     * @param daoDefinitionSubtemplate such property.
     */
    protected void setDAODefinition(final String daoDefinitionSubtemplate)
    {
        immutableSetDAODefinition(daoDefinitionSubtemplate);
    }

    /**
     * Retrieves the DAO definition subtemplate.
     * @return such property.
     */
    public String getDAODefinition()
    {
        return m__strDAODefinition;
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
