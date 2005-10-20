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
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.dao.xml.XMLDAOFactoryTemplate;
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
           >Jose San Leandro</a>
 */
public class XMLDAOFactoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a XMLDAOFactoryTemplateBuildHandler.
     */
    public XMLDAOFactoryTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            try
            {
                Map attributes = command.getAttributeMap();

                String t_strBasePackage = retrieveProjectPackage(attributes);

                String t_strPackage = retrievePackage(t_strBasePackage);

                XMLDAOFactoryTemplateGenerator
                    t_XMLDAOFactoryTemplateGenerator =
                        XMLDAOFactoryTemplateGenerator.getInstance();

                if  (t_XMLDAOFactoryTemplateGenerator != null)
                {
                    TableTemplate[] t_aTableTemplates =
                        retrieveTableTemplates(attributes);

                    if  (t_aTableTemplates != null)
                    {
                        XMLDAOFactoryTemplate[] t_aXMLDAOFactoryTemplates =
                            new XMLDAOFactoryTemplate[t_aTableTemplates.length];

                        for  (int t_iXMLDAOFactoryIndex = 0;
                                    t_iXMLDAOFactoryIndex
                                  < t_aXMLDAOFactoryTemplates.length;
                                  t_iXMLDAOFactoryIndex++) 
                        {
                            t_aXMLDAOFactoryTemplates[t_iXMLDAOFactoryIndex] =
                                t_XMLDAOFactoryTemplateGenerator.createXMLDAOFactoryTemplate(
                                    t_aTableTemplates[t_iXMLDAOFactoryIndex],
                                    t_strPackage,
                                    t_strBasePackage);
                        }

                        storeXMLDAOFactoryTemplates(t_aXMLDAOFactoryTemplates, attributes);
                    }
                }
            }
            catch  (final QueryJException queryjException)
            {
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param basePackage the base package.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(final String basePackage)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (basePackage    != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveXMLDAOFactoryPackage(
                    basePackage);
        }
        
        return result;
    }

    /**
     * Stores the XML DAO factory template collection in given attribute map.
     * @param xmlDAOFactoryTemplates the XML DAO factory templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected void storeXMLDAOFactoryTemplates(
        final XMLDAOFactoryTemplate[] xmlDAOFactoryTemplates,
        final Map                      parameters)
      throws  BuildException
    {
        if  (   (xmlDAOFactoryTemplates != null)
             && (parameters              != null))
        {
            parameters.put(
                TemplateMappingManager.XML_DAO_FACTORY_TEMPLATES,
                xmlDAOFactoryTemplates);
        }
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
      throws  BuildException
    {
        TableTemplate[] result = EMPTY_TABLE_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (TableTemplate[])
                    parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
        }

        return result;
    }
}
