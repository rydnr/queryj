//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: BasePerCustomResultTemplateBuildHandler.java
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
import org.acmsl.queryj.tools.customsql.Result;
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
    public static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

    /**
     * A cached empty BasePerCustomResultTemplate array.
     */
    public static final BasePerCustomResultTemplate[]
        EMPTY_BASEPERCUSTOMRESULTTEMPLATE_ARRAY =
            new BasePerCustomResultTemplate[0];

    /**
     * The key for storing the custom RESULT in the parameter map.
     */
    public static final String CUSTOM_RESULT = "..CustOMresult:::";

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
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
    {
        return
            handle(
                parameters,
                retrieveDatabaseProductName(parameters),
                retrieveDatabaseProductVersion(parameters),
                retrieveDatabaseIdentifierQuoteString(parameters),
                retrieveDisableSqlValidationFlag(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param disableSqlValidationFlag whether to disable SQL validation or not.
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
        final String quote,
        final boolean disableSqlValidationFlag)
    {
        boolean result = false;

//         if  (!disableSqlValidationFlag)
//         {
            result =
                handle(
                    parameters,
                    engineName,
                    engineVersion,
                    quote,
                    retrieveMetadataManager(parameters),
                    retrieveCustomSqlProvider(parameters));
//         }
        
        return result;
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
                retrieveCustomResults(
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
        final Result[] resultElements)
    {
        boolean result = false;

        int t_iLength = (resultElements != null) ? resultElements.length : 0;

        Collection t_cTemplates = new ArrayList();

        try
        {
            Result t_ResultElement = null;

            BasePerCustomResultTemplate t_Template = null;

            String t_strPackageName;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_ResultElement = resultElements[t_iIndex];

                t_strPackageName =
                    retrievePackage(
                        t_ResultElement,
                        customSqlProvider,
                        metadataManager,
                        engineName,
                        parameters);

                if  (shouldProcessResult(
                        t_ResultElement,
                        customSqlProvider,
                        metadataManager,
                        t_strPackageName,
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository,
                        header))
                {
                    t_Template =
                        templateFactory.createTemplate(
                            t_ResultElement,
                            customSqlProvider,
                            metadataManager,
                            t_strPackageName,
                            engineName,
                            engineVersion,
                            projectPackage,
                            repository,
                            header);
                
                    if  (t_Template != null)
                    {
                        t_cTemplates.add(t_Template);
                    }
                }
            }

            storeTemplates(
                (BasePerCustomResultTemplate[])
                    t_cTemplates.toArray(
                        EMPTY_BASEPERCUSTOMRESULTTEMPLATE_ARRAY),
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
        final Result customResult,
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
        final Result customResult,
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
     * Retrieves the custom results.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom RESULT provider.
     * @param metadataManager the database metadata manager.
     * @return such list.
     * @throws BuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected Result[] retrieveCustomResults(
        final Map parameters,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
      throws  BuildException
    {
        Result[] result = (Result[]) parameters.get(CUSTOM_RESULT);

        if  (result == null)
        {
            result = 
                retrieveCustomResultElements(
                    customSqlProvider, metadataManager);
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
    protected Result[] retrieveCustomResultElements(
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

                    if  (t_Item instanceof Result)
                    {
                        t_cResult.add(t_Item);
                    }
                }
            }
        }

        return (Result[]) t_cResult.toArray(EMPTY_RESULT_ARRAY);
    }

    /**
     * Checks whether the result should be processed.
     * @param customResult the custom result.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return <code>true</code> in such case.
     */
    protected boolean shouldProcessResult(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        boolean result = true;

        String t_strClassValue = customResult.getClassValue();
        
        if  (   (t_strClassValue == null)
             || (!t_strClassValue.startsWith(basePackageName)))
        {
            result = false;
        }
            
        return result;
    }
}
