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
 * Description: Builds all templates to generate sources for each
 *              custom SQL.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomSqlTemplateFactory;
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
 * Builds all templates to generate sources for each custom SQL.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerCustomSqlTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty Sql array.
     */
    public static final SqlElement[] EMPTY_SQL_ARRAY = new SqlElement[0];

    /**
     * The key for storing the custom SQL in the parameter map.
     */
    public static final String CUSTOM_SQL = "..CustOMsqL:::";
    
    /**
     * Creates a <code>BasePerCustomSqlTemplateBuildHandler</code> instance.
     */
    public BasePerCustomSqlTemplateBuildHandler() {};

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
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
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
     * @param customSqlProvider the custom SQL provider.
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
                retrieveCustomSql(
                    parameters, customSqlProvider, metadataManager));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract BasePerCustomSqlTemplateFactory retrieveTemplateFactory();

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param sqlElements the custom SQL elements.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition sqlElements != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerCustomSqlTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final SqlElement[] sqlElements)
      throws  BuildException
    {
        boolean result = false;

        int t_iLength = (sqlElements != null) ? sqlElements.length : 0;
        
        BasePerCustomSqlTemplate[] t_aTemplates =
            new BasePerCustomSqlTemplate[t_iLength];

        try
        {
            SqlElement t_SqlElement = null;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
            {
                t_SqlElement = sqlElements[t_iIndex];
                
                t_aTemplates[t_iIndex] =
                    templateFactory.createTemplate(
                        t_SqlElement,
                        customSqlProvider,
                        metadataManager,
                        retrievePackage(t_SqlElement, engineName, parameters),
                        engineName,
                        engineVersion,
                        projectPackage,
                        repository);
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
     * @param customSql the custom SQL.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition customSql != null
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final SqlElement customSql, final String engineName, final Map parameters)
      throws  BuildException
    {
        return
            retrievePackage(
                customSql,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param customSql the custom SQL.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected abstract String retrievePackage(
        final SqlElement customSql,
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
        final BasePerCustomSqlTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     * @throws BuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected SqlElement[] retrieveCustomSql(
        final Map parameters,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
      throws  BuildException
    {
        SqlElement[] result = (SqlElement[]) parameters.get(CUSTOM_SQL);

        if  (result == null)
        {
            Collection t_cCustomSql = new ArrayList();
            
            SqlElement[] t_aSqlElements =
                retrieveCustomSqlElements(customSqlProvider, metadataManager);

            int t_iLength = (t_aSqlElements != null) ? t_aSqlElements.length : 0;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_cCustomSql.add(t_aSqlElements[t_iIndex]);
            }

            result =
                (SqlElement[]) t_cCustomSql.toArray(EMPTY_SQL_ARRAY);
        }

        return result;
    }

    /**
     * Retrieves the custom SQL elements.
     * @param customSqlProvider the custom SQL provider.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected SqlElement[] retrieveCustomSqlElements(
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
                    
                    if  (t_Item instanceof SqlElement)
                    {
                        t_cResult.add(t_Item);
                    }
                }
            }
        }
        
        return (SqlElement[]) t_cResult.toArray(EMPTY_SQL_ARRAY);
    }
}
