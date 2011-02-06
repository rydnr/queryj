//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: JdbcDAOTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes JDBC DAO templates.
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
import org.acmsl.queryj.tools.templates.dao.handlers.JdbcDAOTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Writes JDBC DAO templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class JdbcDAOTemplateWritingHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>JdbcDAOTemplateWritingHandler</code>.
     */
    public JdbcDAOTemplateWritingHandler() {};

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(final Map parameters)
      throws  QueryJBuildException
    {
        writeTemplates(
            retrieveJdbcDAOTemplate(parameters),
            retrieveOutputDir(parameters),
            retrieveCharset(parameters),
            JdbcDAOTemplateGenerator.getInstance());

        return false;
    }
                
    /**
     * Writes the <code>JdbcDAO</code> templates.
     * @param jdbcDAOTemplate the JDBC DAO template.
     * @param outputDir the output dir.
     * @param charset the file encoding.
     * @param jdbcDAOTemplateGenerator the
     * <code>JdbcDAOTemplateGenerator</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition jdbcDAOTemplate != null
     * @precondition outputDir != null
     * @precondition jdbcDAOTemplateGenerator != null
     */
    protected void writeTemplates(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final File outputDir,
        final Charset charset,
        final JdbcDAOTemplateGenerator jdbcDAOTemplateGenerator)
      throws  QueryJBuildException
    {
        try 
        {
            jdbcDAOTemplateGenerator.write(jdbcDAOTemplate, outputDir, charset);
        }
        catch  (final IOException ioException)
        {
            throw
                new QueryJBuildException(
                    "Cannot write the template", ioException);
        }
    }

    /**
     * Retrieves the JDBC DAO template from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @precondition parameters != null
     */
    protected JdbcDAOTemplate retrieveJdbcDAOTemplate(final Map parameters)
    {
        return
            (JdbcDAOTemplate)
                parameters.get(TemplateMappingManager.JDBC_DAO_TEMPLATE);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(final Map parameters)
    {
        return retrieveOutputDir(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final Map parameters, final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveJdbcDAOFolder(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters));
    }
}
