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
 * Description: Builds a numeric functions test template using database metadata.
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
package org.acmsl.queryj.tools.templates.functions.numeric.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTestTemplate;
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTestTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a numeric functions test template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class NumericFunctionsTestTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * Creates a NumericFunctionsTestTemplateBuildHandler.
     */
    public NumericFunctionsTestTemplateBuildHandler() {};

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

                boolean t_bExtractFunctions =
                    retrieveExtractFunctions(attributes);

                if  (t_bExtractFunctions)
                {
                    DatabaseMetaData t_MetaData =
                        retrieveDatabaseMetaData(attributes);

                    String t_strPackage = retrievePackage(attributes);

                    String t_strTestedPackage = retrieveTestedPackage(attributes);

                    NumericFunctionsTestTemplateGenerator
                        t_NumericFunctionsTestTemplateGenerator =
                            NumericFunctionsTestTemplateGenerator.getInstance();

                    StringUtils t_StringUtils = StringUtils.getInstance();

                    if  (   (t_MetaData                              != null)
                         && (t_StringUtils                           != null)
                         && (t_NumericFunctionsTestTemplateGenerator != null))
                    {
                        String t_strQuote =
                            t_MetaData.getIdentifierQuoteString();

                        if  (t_strQuote == null)
                        {
                            t_strQuote = "\"";
                        }

                        if  (t_strQuote.equals("\""))
                        {
                            t_strQuote = "\\\"";
                        }

                        NumericFunctionsTestTemplate
                            t_NumericFunctionsTestTemplate =
                                t_NumericFunctionsTestTemplateGenerator
                                    .createNumericFunctionsTestTemplate(
                                        t_strPackage,
                                        t_strTestedPackage,
                                        t_MetaData.getDatabaseProductName(),
                                        t_MetaData.getDatabaseProductVersion(),
                                        t_strQuote,
                                        command.getProject(),
                                        command.getTask());

                        Collection t_cFunctions =
                            t_StringUtils.tokenize(
                                t_MetaData.getNumericFunctions(),
                                ",");

                        if  (t_cFunctions != null) 
                        {
                            Iterator t_itFunctions = t_cFunctions.iterator();

                            while  (   (t_itFunctions != null)
                                       && (t_itFunctions.hasNext()))
                            {
                                String t_strFunctions =
                                    (String) t_itFunctions.next();

                                t_NumericFunctionsTestTemplate.addFunction(
                                    t_strFunctions);
                            }
                        }

                        storeNumericFunctionsTestTemplate(
                            t_NumericFunctionsTestTemplate, attributes);
                    }
                }
            }
            catch  (SQLException sqlException)
            {
                throw new BuildException(sqlException);
            }
            catch  (QueryJException queryjException)
            {
                throw new BuildException(queryjException);
            }
        }
        
        return result;
    }

    /**
     * Retrieves whether the functions should be extracted or not.
     * @param parameters the parameter map.
     * @return such information.
     * @throws BuildException if such condition cannot be retrieved.
     */
    protected boolean retrieveExtractFunctions(
            Map parameters)
        throws  BuildException
    {
        boolean result = true;

        if  (parameters != null)
        {
            Boolean t_bResult =
                (Boolean)
                    parameters.get(
                        ParameterValidationHandler.EXTRACT_FUNCTIONS);

            if  (t_bResult != null)
            {
                result = t_bResult.booleanValue();
            }
        }

        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
            Map parameters)
        throws  BuildException
    {
        DatabaseMetaData result = null;

        if  (parameters != null)
        {
            result =
                (DatabaseMetaData)
                    parameters.get(
                        DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveProjectPackage(Map parameters)
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
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveTestFunctionsPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the tested package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrieveTestedPackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        if  (   (parameters     != null)
             && (t_PackageUtils != null))
        {
            result =
                t_PackageUtils.retrieveFunctionsPackage(
                    retrieveProjectPackage(parameters));
        }
        
        return result;
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected Collection retrieveTestTemplates(Map parameters)
        throws  BuildException
    {
        Collection result = null;

        if  (parameters != null)
        {
            result =
                (Collection) parameters.get(TemplateMappingManager.TEST_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Stores the numeric functions test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeNumericFunctionsTestTemplate(
            NumericFunctionsTestTemplate template,
            Map                          parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
        {
            parameters.put(
                TemplateMappingManager.NUMERIC_FUNCTIONS_TEST_TEMPLATE,
                template);

            storeTestTemplate(template, parameters);
        }
    }

    /**
     * Stores the test template collection.
     * @param templates the test templates.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplates(Collection templates, Map parameters)
        throws  BuildException
    {
        if  (   (templates  != null)
             && (parameters != null))
        {
            parameters.put(TemplateMappingManager.TEST_TEMPLATES, templates);
        }
    }

    /**
     * Stores a new test template.
     * @param testTemplate the test template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestTemplate(TestTemplate template, Map parameters)
        throws  BuildException
    {
        if  (   (template   != null)
             && (parameters != null))
        {
            Collection t_cTestTemplates = retrieveTestTemplates(parameters);

            if  (t_cTestTemplates == null) 
            {
                t_cTestTemplates = new ArrayList();
                storeTestTemplates(t_cTestTemplates, parameters);
            }

            t_cTestTemplates.add(template);
        }
    }
}
