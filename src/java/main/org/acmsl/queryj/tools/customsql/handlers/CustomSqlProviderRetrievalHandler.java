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
 * Description: Retrieves the CustomSqlProvider instance.
 *
 */
package org.acmsl.queryj.tools.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParserFactory;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.util.ClasspathUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/**
 * Retrieves the CustomSqlProvider instance.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class CustomSqlProviderRetrievalHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The custom-sql provider key.
     */
    public static final String CUSTOM_SQL_PROVIDER = "..custom-sql.provider..";

    /**
     * Creates a CustomSqlProviderRetrievalHandler.
     */
    public CustomSqlProviderRetrievalHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(final Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            AntCommand t_AntCommand = (AntCommand) command;

            try 
            {
                result = handle(t_AntCommand);
            }
            catch  (final BuildException buildException)
            {
                Project t_Project = t_AntCommand.getProject();

                if  (t_Project != null)
                {
                    t_Project.log(
                        t_AntCommand.getTask(),
                        buildException.getMessage(),
                        Project.MSG_ERR);
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
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            storeCustomSqlProvider(
                buildCustomSqlProvider(command, command.getAttributeMap()),
                command.getAttributeMap());
        }
        
        return result;
    }

    /**
     * Opens the JDBC connection using the information stored in the
     * attribute map.
     * @param command the command.
     * @param parameters the parameter map.
     * @return the JDBC connection.
     * @throws BuildException if the connection cannot be opened.
     * @precondition command != null
     */
    protected CustomSqlProvider buildCustomSqlProvider(
        final AntCommand command, final Map parameters)
        throws  BuildException
    {
        CustomSqlProvider result = null;

        if  (parameters != null) 
        {
            String t_strType =
                (String)
                    parameters.get(
                        ParameterValidationHandler.CUSTOM_SQL_MODEL);

            if  (t_strType != null)
            {
                if  (!ParameterValidationHandler.CUSTOM_SQL_MODEL_XML.equals(
                         t_strType))
                {
                    throw new BuildException(
                        "Custom queries can only be provided via sql.xml so far.");
                }
            }

            SqlXmlParserFactory t_Factory =
                SqlXmlParserFactory.getInstance();

            SqlXmlParser t_Parser =
                t_Factory.createSqlXmlParser(
                    (File)
                        parameters.get(
                            ParameterValidationHandler.SQL_XML_FILE));

            /*
              t_Parser.setClassLoader(
                ClasspathUtils.getDelegate(command.getTask()).getClassLoader());
            */

            t_Parser.parse();

            result = t_Parser;
        }

        return result;
    }

    /**
     * Stores the CustomSqlProvider instance.
     * @param provider the provider to store.
     * @param parameters the parameter map.
     * @throws BuildException if the provider cannot be stored for any reason.
     */
    protected void storeCustomSqlProvider(
        final CustomSqlProvider provider,
        final Map parameters)
      throws  BuildException
    {
        if  (   (provider   != null)
             && (parameters != null))
        {
            parameters.put(CUSTOM_SQL_PROVIDER, provider);
        }
    }

}
