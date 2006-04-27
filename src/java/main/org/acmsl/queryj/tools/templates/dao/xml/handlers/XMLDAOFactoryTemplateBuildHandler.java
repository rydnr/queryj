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
 * Description: Builds a XML DAO factory template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
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
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Builds a XML DAO factory template.
 * @author <a href="mailto:chous@acm-sl.org"
 * >Jose San Leandro</a>
 * @version $Revision$ at $Date$ by $Author$
 */
public class XMLDAOFactoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a XMLDAOFactoryTemplateBuildHandler.
     */
    public XMLDAOFactoryTemplateBuildHandler() {};

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
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return
            handle(
                parameters,
                retrieveProjectPackage(parameters),
                retrieveTableTemplates(parameters));
    }
                
    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param projectPackageName the project package.
     * @param tableTemplates the table templates.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition projectPackageName != null
     * @precondition tableTemplates != null
     */
    protected boolean handle(
        final Map parameters,
        final String projectPackageName,
        final TableTemplate[] tableTemplates)
      throws  BuildException
    {
        return
            handle(
                parameters,
                retrievePackage(projectPackageName),
                projectPackageName,
                tableTemplates,
                retrieveHeader(parameters),
                XMLDAOFactoryTemplateGenerator.getInstance());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters to handle.
     * @param packageName the package name.
     * @param projectPackageName the project package name.
     * @param tableTemplates the table templates.
     * @param header the header.
     * @param templateFactory the template factory.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition packageName != null
     * @precondition projectPackageName != null
     * @precondition tableTemplates != null
     * @precondition templateFactory != null
     */
    protected boolean handle(
        final Map parameters,
        final String packageName,
        final String projectPackageName,
        final TableTemplate[] tableTemplates,
        final String header,
        final XMLDAOFactoryTemplateFactory templateFactory)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;

            XMLDAOFactoryTemplate[] t_aXMLDAOFactoryTemplates =
                new XMLDAOFactoryTemplate[t_iLength];

            for  (int t_iXMLDAOFactoryIndex = 0;
                      t_iXMLDAOFactoryIndex < t_iLength;
                      t_iXMLDAOFactoryIndex++) 
            {
                t_aXMLDAOFactoryTemplates[t_iXMLDAOFactoryIndex] =
                    templateFactory.createXMLDAOFactoryTemplate(
                        tableTemplates[t_iXMLDAOFactoryIndex],
                        packageName,
                        projectPackageName,
                        header);
            }

            storeXMLDAOFactoryTemplates(t_aXMLDAOFactoryTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition basePackage != null
     */
    protected String retrievePackage(final String basePackage)
        throws  BuildException
    {
        return retrievePackage(basePackage, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition basePackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String basePackage, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveXMLDAOFactoryPackage(
                basePackage);
    }

    /**
     * Stores the XML DAO factory template collection in given attribute map.
     * @param templates the XML DAO factory templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeXMLDAOFactoryTemplates(
        final XMLDAOFactoryTemplate[] templates,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.XML_DAO_FACTORY_TEMPLATES,
            templates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
