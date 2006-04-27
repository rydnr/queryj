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
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.logging.QueryJLog;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;


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
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a JdbcDAOTemplateBuildHandler.
     */
    public JdbcDAOTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return
            handle(
                command.getAttributeMap(),
                JdbcDAOTemplateGenerator.getInstance());
    }
                
    /**
     * Handles given information.
     * @param parameters the attributes.
     * @param jdbcDAOTemplateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition jdbcDAOTemplateFactory != null
     */
    protected boolean handle(
        final Map parameters,
        final JdbcDAOTemplateFactory jdbcDAOTemplateFactory)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            storeJdbcDAOTemplate(
                jdbcDAOTemplateFactory.createJdbcDAOTemplate(
                    retrievePackage(parameters),
                    retrieveHeader(parameters)),
                parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return
            retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
        throws  BuildException
    {
        return
            packageUtils.retrieveJdbcDAOPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Stores the JDBC DAO template in given attribute map.
     * @param jdbcDAOTemplate the DAO template.
     * @param parameters the parameter map.
     * @throws BuildException if the template cannot be stored for any reason.
     * @precondition jdbcDAOTemplate != null
     * @precondition parameters != null
     */
    protected void storeJdbcDAOTemplate(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final Map parameters)
        throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.JDBC_DAO_TEMPLATE, jdbcDAOTemplate);
    }
}
