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
 * Filename: BasePerTableTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a per-table templates using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateFactory;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

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
 * Builds a per-table template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerTableTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
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
        boolean result = false;

        if  (shouldHandle(parameters))
        {
            result =
                handle(
                    parameters,
                    retrieveDatabaseProductName(parameters),
                    retrieveDatabaseProductVersion(parameters),
                    retrieveDatabaseIdentifierQuoteString(parameters));
        }

        return result;
    }

    /**
     * Checks whether the handler should actually perform its logic
     * or not.
     * @param parameters the parameters.
     * @return <code>true</code> in such case.
     * @precondition parameters != null
     */
    protected boolean shouldHandle(final Map parameters)
    {
        return true;
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
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                retrieveMetadataManager(parameters),
                retrieveCustomSqlProvider(parameters),
                retrieveTemplateFactory(parameters),
                retrieveProjectPackage(parameters),
                retrieveTableRepositoryName(parameters),
                retrieveJmx(parameters),
                retrieveHeader(parameters),
                retrieveJndiLocation(parameters),
                retrieveTableTemplates(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the template factory.
     * @param parameters the parameters.
     * @return such instance.
     */
    protected abstract BasePerTableTemplateFactory retrieveTemplateFactory(
        final Map parameters);

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
     * @param jmx whether to support JMX.
     * @param header the header.
     * @param jndiLocation the location of the datasource in JNDI.
     * @param tableTemplates the table templates.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition tableTemplates != null
     * @precondition packageUtils != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerTableTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final boolean jmx,
        final String header,
        final String jndiLocation,
        final TableTemplate[] tableTemplates,
        final PackageUtils packageUtils)
    {
        boolean result = false;

        int t_iLength = (tableTemplates != null) ? tableTemplates.length : 0;
        
        BasePerTableTemplate[] t_aTemplates =
            new BasePerTableTemplate[t_iLength];

        try
        {
            String t_strTableName = null;
            String t_strPackage = null;

            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
            {
                t_strTableName = tableTemplates[t_iIndex].getTableName();

                t_strPackage =
                    retrievePackage(
                        t_strTableName,
                        engineName,
                        projectPackage,
                        packageUtils);

                t_aTemplates[t_iIndex] =
                    createTemplate(
                        parameters,
                        t_strTableName,
                        engineName,
                        engineVersion,
                        quote,
                        metadataManager,
                        customSqlProvider,
                        t_strPackage,
                        templateFactory,
                        projectPackage,
                        repository,
                        jmx,
                        header,
                        jndiLocation);
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
     * Creates a template with given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param packageName the package name.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param jmx whether to support JMX.
     * @param header the header.
     * @param jndiLocation the location of the datasource in JNDI.
     * @return the new template.
     * @throws QueryJException in case the template cannot be created.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     */
    protected BasePerTableTemplate createTemplate(
        final Map parameters,
        final String tableName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final BasePerTableTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final boolean jmx,
        final String header,
        final String jndiLocation)
      throws QueryJException
    {
        return
            templateFactory.createTemplate(
                tableName,
                metadataManager,
                customSqlProvider,
                packageName,
                engineName,
                engineVersion,
                quote,
                projectPackage,
                repository,
                jmx,
                header,
                jndiLocation);
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected abstract String retrievePackage(
        final String tableName,
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
        final BasePerTableTemplate[] templates, final Map parameters);

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
