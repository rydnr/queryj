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
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Is able to generate the configuration file for configuring
 * DAOChooser.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ConfigurationPropertiesTemplate
    extends  BasePerRepositoryTemplate
{
    /**
     * Builds a <code>ConfigurationPropertiesTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public ConfigurationPropertiesTemplate(
        final MetadataManager metadataManager,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables)
    {
        super(
            metadataManager,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            tables);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/ConfigurationProperties.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "ConfigurationProperties";
    }

    /**
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition metadataManager != null
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
        final String subpackageName,
        final String basePackageName,
        final String tableRepositoryName,
        final String engineName,
        final Collection tables,
        final String timestamp,
        final StringUtils stringUtils)
    {
        input.put("tr_name", tableRepositoryName);

        input.put(
            "dao_subpackage_name",
            retrieveDAOSubpackageName(
                basePackageName, engineName, PackageUtils.getInstance()));

        input.put("tables", decorateTables(tables, metadataManager));
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
     * @return the decorated tables.
     * @precondition tables != null
     * @precondition metadataManager != null
     */
    protected Collection decorateTables(
        final Collection tables, final MetadataManager metadataManager)
    {
        Collection result = new ArrayList();

        Iterator t_itTableIterator = tables.iterator();
        
        if  (t_itTableIterator != null)
        {
            while  (t_itTableIterator.hasNext())
            {
                result.add(
                    decorate(
                        (String) t_itTableIterator.next(), metadataManager));
            }
        }
        
        return result;
    }

    /**
     * Decorates given table.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated table.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    protected TableDecorator decorate(
        final String table, final MetadataManager metadataManager)
    {
        return new TableDecorator(table, metadataManager);
    }
}
