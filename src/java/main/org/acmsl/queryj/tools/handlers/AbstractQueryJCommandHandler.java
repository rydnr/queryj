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
 * Description: Inside a Chain Of Responsibility, these are the chain links.
 *              This means they perform specific actions when receiving the
 *              command.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.handlers.QueryJCommandHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.patterns.CommandHandler;
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.Map;

/*
 * Importing some Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/**
 * Inside a Chain Of Responsibility, these are the chain links.
 * This means they perform specific actions when receiving the command.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public abstract class AbstractQueryJCommandHandler
    implements  QueryJCommandHandler
{
    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition command != null
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof QueryJCommand)
        {
            QueryJCommand t_Command = (QueryJCommand) command;
            
            try 
            {
                result = handle(t_Command);
            }
            catch  (final QueryJBuildException buildException)
            {
                Log t_Log =
                    UniqueLogFactory.getLog(
                        AbstractQueryJCommandHandler.class);

                if  (t_Log != null)
                {
                    t_Log.error("Chain step failed.", buildException);
                }
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final QueryJCommand command)
        throws  QueryJBuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> to avoid further processing of such command
     * by different handlers.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected abstract boolean handle(final Map parameters)
        throws  QueryJBuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param parameters the parameter map.
     * @return such folder.
     * @precondition parameters != null
     */
    protected File retrieveProjectOutputDir(final Map parameters)
    {
        return
            (File) parameters.get(ParameterValidationHandler.OUTPUT_DIR);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters !0 null
     */
    protected String retrieveProjectPackage(final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @precondition parameters != null
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
        final Map parameters)
    {
        return
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
    }


    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @precondition parameters != null
     */
    protected MetadataManager retrieveMetadataManager(
        final Map parameters)
    {
        return
            (MetadataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);
    }

    /**
     * Retrieves whether to use subfolders or not.
     * @param parameters the parameters.
     * @return such flag.
     * @precondition parameters != null
     */
    protected boolean retrieveUseSubfoldersFlag(final Map parameters)
    {
        boolean result = false;

        Object t_Flag =
            parameters.get(ParameterValidationHandler.OUTPUT_DIR_SUBFOLDERS);

        if  (   (t_Flag != null)
             && (t_Flag instanceof Boolean))
        {
            result = ((Boolean) t_Flag).booleanValue();
        }

        return result;
    }

    /**
     * Retrieves the JDBC driver from the attribute map.
     * @param parameters the parameter map.
     * @return the driver name.
     * @precondition parameters != null
     */
    protected String retrieveJdbcDriver(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_DRIVER);
    }

    /**
     * Retrieves the JDBC url from the attribute map.
     * @param parameters the parameter map.
     * @return the url name.
     * @precondition parameters != null
     */
    protected String retrieveJdbcUrl(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_URL);
    }

    /**
     * Retrieves the JDBC username from the attribute map.
     * @param parameters the parameter map.
     * @return the username name.
     * @precondition parameters != null
     */
    protected String retrieveJdbcUsername(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_USERNAME);
    }

    /**
     * Retrieves the JDBC password from the attribute map.
     * @param parameters the parameter map.
     * @return the password name.
     * @precondition parameters != null
     */
    protected String retrieveJdbcPassword(final Map parameters)
    {
        return
            (String) parameters.get(ParameterValidationHandler.JDBC_PASSWORD);
    }

    /**
     * Fixes given quote character.
     * @param quote the quote.
     * @return the correct one.
     */
    public static String fixQuote(final String quote)
    {
        String result = quote;

        if  (result == null)
        {
            result = "\"";
        }

        if  (result.equals("\""))
        {
            result = "\\\"";
        }

        return result;
    }

    /**
     * Retrieves the custom-sql provider from the attribute map.
     * @param parameters the parameter map.
     * @return the provider.
     * @precondition parameters != null
     */
    public static CustomSqlProvider retrieveCustomSqlProvider(
        final Map parameters)
    {
        return
            (CustomSqlProvider)
                parameters.get(
                    CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     * @precondition parameters != null
     */
    protected String retrieveTableRepositoryName(final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the header from the attribute map.
     * @param parameters the parameter map.
     * @return the header.
     * @precondition parameters != null
     */
    protected String retrieveHeader(final Map parameters)
    {
        return (String) parameters.get(ParameterValidationHandler.HEADER);
    }

    /**
     * Retrieves whether to implement marker interfaces or not.
     * @param parameters the parameter map.
     * @return such condition.
     * @precondition parameters != null
     */
    protected boolean retrieveImplementMarkerInterfaces(
        final Map parameters)
    {
        Boolean result =
            (Boolean)
                parameters.get(
                    ParameterValidationHandler.IMPLEMENT_MARKER_INTERFACES);

        if  (result == null)
        {
            result = Boolean.FALSE;
        }

        return result.booleanValue();
    }
}
