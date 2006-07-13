//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds JDBC DAO templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds JDBC DAO templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcDAOTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a <code>JdbcDAOTemplateBuildHandler</code> instance.
     */
    public JdbcDAOTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplates(parameters, JdbcDAOTemplateGenerator.getInstance());

        return false;
    }
                
    /**
     * Builds the <code>JdbcDAO</code> templates..
     * @param parameters the attributes.
     * @param jdbcDAOTemplateFactory the template factory.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition jdbcDAOTemplateFactory != null
     */
    protected void buildTemplates(
        final Map parameters,
        final JdbcDAOTemplateFactory jdbcDAOTemplateFactory)
      throws  QueryJBuildException
    {
        storeJdbcDAOTemplate(
            jdbcDAOTemplateFactory.createJdbcDAOTemplate(
                retrievePackage(parameters),
                retrieveHeader(parameters)),
            parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
    {
        return
            retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveJdbcDAOPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Stores the JDBC DAO template in given attribute map.
     * @param jdbcDAOTemplate the DAO template.
     * @param parameters the parameter map.
     * @precondition jdbcDAOTemplate != null
     * @precondition parameters != null
     */
    protected void storeJdbcDAOTemplate(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.JDBC_DAO_TEMPLATE, jdbcDAOTemplate);
    }
}
