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
 * Description: Is able to generate the configuration file for declaring
 *              the transactional behaviour of the DAO layer.
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
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

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
 * Is able to generate the configuration file for declaring the transactional
 * behaviour of the DAO layer.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public class DataAccessContextLocalTemplate
    extends  AbstractDataAccessContextLocalTemplate
    implements  DataAccessContextLocalTemplateDefaults
{
    /**
     * Builds a <code>DataAccessContextLocalTemplate</code> using given
     * information.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     */
    public DataAccessContextLocalTemplate(
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final Project project,
        final Task task)
    {
        super(
            DEFAULT_HEADER,
            jndiLocation,
            engineName,
            engineVersion,
            basePackageName,
            DEFAULT_RESOURCE_DEFINITION,
            DEFAULT_DAO_DEFINITION,
            project,
            task);
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return "Generating dataAccessContext-local.xml.sample";
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
                getJNDILocation(),
                getEngineName(),
                getEngineVersion(),
                getBasePackageName(),
                getResourceDefinition(),
                getDAODefinition(),
                getTables(),
                PackageUtils.getInstance(),
                EnglishGrammarUtils.getInstance(),
                StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param header the header.
     * @param jndiLocation the JNDI location.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param resourceDefinition the resource definition subtemplate.
     * @param daoDefinition the DAO definition subtemplate.
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
        final String jndiLocation,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String resourceDefinition,
        final String daoDefinition,
        final Collection tables,
        final PackageUtils packageUtils,
        final EnglishGrammarUtils englishGrammarUtils,
        final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        t_sbResult.append(header);

        StringBuffer t_sbDAODefinitions = new StringBuffer();

        MessageFormat t_DAODefinitionFormatter =
            new MessageFormat(daoDefinition);

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

                t_sbDAODefinitions.append(
                    t_DAODefinitionFormatter.format(
                        new Object[]
                        {
                            t_strCapitalizedTable,
                            packageUtils.retrieveDAOPackage(
                                basePackageName,
                                engineName),
                            stringUtils.capitalize(
                                engineName,
                                '_')
                        }));
            }
        }

        MessageFormat t_ResourceDefinitionFormatter =
            new MessageFormat(resourceDefinition);

        t_sbResult.append(
            t_ResourceDefinitionFormatter.format(
                new Object[]
                {
                    jndiLocation,
                    t_sbDAODefinitions
                }));

        return t_sbResult.toString();
    }
}
