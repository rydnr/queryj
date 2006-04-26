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
 * Description: Builds all templates to generate sources for each
 *              custom result.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds all templates to generate sources for each custom result.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerCustomResultTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty Result array.
     */
    public static final ResultElement[] EMPTY_RESULT_ARRAY = new ResultElement[0];

    /**
     * The key for storing the custom RESULT in the parameter map.
     */
    public static final String CUSTOM_RESULT = "..CustOMresult:::";
    
    /**
     * Creates a <code>BasePerCustomResultTemplateBuildHandler</code> instance.
     */
    public BasePerCustomResultTemplateBuildHandler() {};

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
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return handle(parameters, retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        final Map parameters, final DatabaseMetaData metaData)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            result =
                handle(
                    parameters,
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion(),
                    fixQuote(metaData.getIdentifierQuoteString()));
        }
        catch  (final SQLException resultException)
        {
            throw new BuildException(resultException);
        }

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  BuildException
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                retrieveMetadataManager(parameters),
                retrieveCustomSqlProvider(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom RESULT provider.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider)
      throws  BuildException
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                metadataManager,
                customSqlProvider,
                retrieveTemplateFactory(),
                retrieveProjectPackage(parameters),
                retrieveTableRepositoryName(parameters),
                retrieveHeader(parameters),
                retrieveCustomResult(
                    parameters, customSqlProvider, metadataManager));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract BasePerCustomResultTemplateFactory retrieveTemplateFactory();

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom result provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param header the header.
     * @param resultElements the custom RESULT elements.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition resultElements != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerCustomResultTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final String header,
        final ResultElement[] resultElements)
      throws  BuildException
    {
        boolean result = false;

        int t_iLength = (resultElements != null) ? resultElements.length : 0;
        
        BasePerCustomResultTemplate[] t_aTemplates =
            new BasePerCustomResultTemplate[t_iLength];

        try
        {
            ResultElement t_ResultElement = null;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
            {
                t_ResultElement = resultElements[t_iIndex];
                
                t_aTemplates[t_iIndex] =
                    templateFactory.createTemplate(
                        t_ResultElement,
                        customSqlProvider,
                        metadataManager,
                        retrievePackage(
                            t_ResultElement,
                            customSqlProvider,
                            metadataManager,
                            engineName,
                            parameters),
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository,
                        header);
            }

            storeTemplates(t_aTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param customResult the custom RESULT.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition customResult != null
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final ResultElement customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final Map parameters)
      throws  BuildException
    {
        return
            retrievePackage(
                customResult,
                customSqlProvider,
                metadataManager,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param customResult the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected abstract String retrievePackage(
        final ResultElement customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplates(
        final BasePerCustomResultTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom RESULT provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     * @throws BuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected ResultElement[] retrieveCustomResult(
        final Map parameters,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
      throws  BuildException
    {
        ResultElement[] result = (ResultElement[]) parameters.get(CUSTOM_RESULT);

        if  (result == null)
        {
            Collection t_cCustomResult = new ArrayList();
            
            ResultElement[] t_aResultElements =
                retrieveCustomResultElements(customSqlProvider, metadataManager);

            int t_iLength = (t_aResultElements != null) ? t_aResultElements.length : 0;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_cCustomResult.add(t_aResultElements[t_iIndex]);
            }

            result =
                (ResultElement[]) t_cCustomResult.toArray(EMPTY_RESULT_ARRAY);
        }

        return result;
    }

    /**
     * Retrieves the custom RESULT elements.
     * @param customSqlProvider the custom RESULT provider.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected ResultElement[] retrieveCustomResultElements(
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        Collection t_cResult = new ArrayList();
        
        Collection t_cCollection = customSqlProvider.getCollection();

        if  (t_cCollection != null)
        {
            Iterator t_itElements = t_cCollection.iterator();
            
            if  (t_itElements != null)
            {
                Object t_Item = null;

                while  (t_itElements.hasNext())
                {
                    t_Item = t_itElements.next();
                    
                    if  (t_Item instanceof ResultElement)
                    {
                        t_cResult.add(t_Item);
                    }
                }
            }
        }
        
        return (ResultElement[]) t_cResult.toArray(EMPTY_RESULT_ARRAY);
    }
}
