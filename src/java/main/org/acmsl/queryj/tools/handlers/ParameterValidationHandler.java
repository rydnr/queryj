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
 * Description: Validates the parameters of an Ant task.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.AntTablesElement;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Validates the parameters of an Ant task.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public class ParameterValidationHandler
    extends  AbstractAntCommandHandler
{
    /**
     * The JDBC driver attribute name.
     */
    public static final String JDBC_DRIVER = "jdbc.driver";

    /**
     * The missing driver error message.
     */
    public static final String DRIVER_MISSING = "JDBC driver is missing.";

    /**
     * The JDBC URL attribute name.
     */
    public static final String JDBC_URL = "jdbc.url";

    /**
     * The missing url error message.
     */
    public static final String URL_MISSING = "JDBC url is missing.";

    /**
     * The JDBC username attribute name.
     */
    public static final String JDBC_USERNAME = "jdbc.username";

    /**
     * The missing username error message.
     */
    public static final String USERNAME_MISSING = "JDBC username is missing.";

    /**
     * The JDBC password attribute name.
     */
    public static final String JDBC_PASSWORD = "jdbc.password";

    /**
     * The missing password error message.
     */
    public static final String PASSWORD_MISSING = "JDBC password is missing.";

    /**
     * The JDBC catalog attribute name.
     */
    public static final String JDBC_CATALOG = "jdbc.catalog";

    /**
     * The missing catalog error message.
     */
    public static final String CATALOG_MISSING = "JDBC catalog is missing.";

    /**
     * The JDBC schema attribute name.
     */
    public static final String JDBC_SCHEMA = "jdbc.schema";

    /**
     * The missing schema error message.
     */
    public static final String SCHEMA_MISSING = "JDBC schema is missing.";

    /**
     * The repository attribute name.
     */
    public static final String REPOSITORY = "repository";

    /**
     * The missing repository error message.
     */
    public static final String REPOSITORY_MISSING = "Repository name is missing.";

    /**
     * The package attribute name.
     */
    public static final String PACKAGE = "package";

    /**
     * The missing package error message.
     */
    public static final String PACKAGE_MISSING = "Package is missing.";

    /**
     * The classpath attribute name.
     */
    public static final String CLASSPATH = "classpath";

    /**
     * The missing classpath error message.
     */
    public static final String CLASSPATH_MISSING = "Classpath not specified.";

    /**
     * The output-dir attribute name.
     */
    public static final String OUTPUT_DIR = "outputdir";

    /**
     * The missing output dir error message.
     */
    public static final String OUTPUTDIR_MISSING =
         "Output directory not specified.";

    /**
     * The missing output dir not folder error message.
     */
    public static final String OUTPUTDIR_NOT_FOLDER =
         "Specified Outputdir is not a folder.";

    /**
     * The extract-tables attribute name.
     */
    public static final String EXTRACT_TABLES = "extract.tables";

    /**
     * The extract-functions attribute name.
     */
    public static final String EXTRACT_FUNCTIONS = "extract.functions";

    /**
     * The extract-procedures attribute name.
     */
    public static final String EXTRACT_PROCEDURES = "extract.procedures";

    /**
     * The JNDI location for data sources  attribute name.
     */
    public static final String JNDI_DATASOURCES = "jndi.datasources";

    /**
     * The missing JNDI data sources error message.
     */
    public static final String JNDI_DATASOURCES_MISSING =
        "No JNDI location specified for retrieving DataSource instances.";

    /**
     * The invalid JNDI data sources error message.
     */
    public static final String JNDI_DATASOURCES_INVALID =
        "Invalid JNDI location for retrieving DataSource instances.";

    /**
     * The generate-mock-dao attribute name.
     */
    public static final String GENERATE_MOCK_DAO = "generate.mock.dao";

    /**
     * The tables element name.
     */
    public static final String TABLES = "tables";

    /**
     * The missing tables error message.
     */
    public static final String TABLES_MISSING =
         "Empty table information specified.";

    /**
     * The externally-managed-fields element name.
     */
    public static final String EXTERNALLY_MANAGED_FIELDS =
        "externally.managed.fields";

    /**
     * The missing externally-managed-fields error message.
     */
    public static final String EXTERNALLY_MANAGED_FIELDS_MISSING =
         "Empty externally-managed fields information specified.";

    /**
     * The custom-sql model.
     */
    public static final String CUSTOM_SQL_MODEL = "customSqlModel";

    /**
     * The sql.xml file.
     */
    public static final String SQL_XML_FILE = "sqlXmlFile";

    /**
     * The missing tables error message.
     */
    public static final String SQL_XML_FILE_MISSING =
         "No sql.xml file specified or it cannot be read.";

    /**
     * The custom-sql XML model.
     */
    public static final String CUSTOM_SQL_MODEL_XML = "xml";

    /**
     * Creates a ParameterValidationHandler.
     */
    public ParameterValidationHandler() {};

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
            try 
            {
                result = handle((AntCommand) command);
            }
            catch  (final BuildException buildException)
            {
                ((AntCommand) command).getProject().log(
                    ((AntCommand) command).getTask(),
                    buildException.getMessage(),
                    Project.MSG_ERR);
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
            validateParameters(command.getAttributeMap());
        }
        
        return result;
    }

    /**
     * Validates the parameters.
     * @param parameters the parameter map.
     * @return <code>false</code> if the chain should be stopped because
     * of invalid parameters.
     * @throws BuildException if the build process cannot be performed.
     */
    public void validateParameters(final Map parameters)
        throws  BuildException
    {
        if  (parameters != null) 
        {
            validateParameters(
                (String)           parameters.get(JDBC_DRIVER),
                (String)           parameters.get(JDBC_URL),
                (String)           parameters.get(JDBC_USERNAME),
                (String)           parameters.get(JDBC_PASSWORD),
                (String)           parameters.get(JDBC_CATALOG),
                (String)           parameters.get(JDBC_SCHEMA),
                (String)           parameters.get(REPOSITORY),
                (String)           parameters.get(PACKAGE),
                (Path)             parameters.get(CLASSPATH),
                (File)             parameters.get(OUTPUT_DIR),
                (Boolean)          parameters.get(EXTRACT_PROCEDURES),
                (Boolean)          parameters.get(EXTRACT_FUNCTIONS),
                (String)           parameters.get(JNDI_DATASOURCES),
                (Boolean)          parameters.get(GENERATE_MOCK_DAO),
                (AntTablesElement) parameters.get(TABLES),
                (AntExternallyManagedFieldsElement)
                    parameters.get(EXTERNALLY_MANAGED_FIELDS),
                (String)           parameters.get(CUSTOM_SQL_MODEL),
                (File)             parameters.get(SQL_XML_FILE));
        }
    }

    /**
     * Validates the parameters.
     * @param driver the JDBC driver.
     * @param url the url.
     * @param username the username.
     * @param password the password.
     * @param catalog the catalog.
     * @param schema the schema.
     * @param repository the repository.
     * @param packageName the package name.
     * @param classpath the classpath.
     * @param outputdir the output folder.
     * @param extractProcedures the extract-procedures setting.
     * @param extractFunctions the extract-functions setting.
     * @param jndiDataSources the JNDI location for data sources.
     * @param generateMockDAO the generate-mock-dao-implementation setting.
     * @param tables the table information.
     * @param externallyManagedFields the externally-managed fields
     * information.
     * @param customSqlModel the model for custom-sql information.
     * @param sqlXmlFile the sql.xml file.
     * @throws BuildException whenever the required
     * parameters are not present or valid.
     */
    protected void validateParameters(
        final String                            driver,
        final String                            url,
        final String                            username,
        final String                            password,
        final String                            catalog,
        final String                            schema,
        final String                            repository,
        final String                            packageName,
        final Path                              classpath,
        final File                              outputdir,
        final Boolean                           extractProcedures,
        final Boolean                           extractFunctions,
        final String                            jndiDataSources,
        final Boolean                           generateMockDAO,
        final AntTablesElement                  tables,
        final AntExternallyManagedFieldsElement externallyManagedFields,
        final String                            customSqlModel,
        final File                              sqlXmlFile)
      throws  BuildException
    {
        if  (driver == null) 
        {
            throw new BuildException(DRIVER_MISSING);
        }

        if  (url == null) 
        {
            throw new BuildException(URL_MISSING);
        }

        if  (username == null) 
        {
            throw new BuildException(USERNAME_MISSING);
        }

        /* Not mandatory.
        if  (password == null)
        {
            throw new BuildException(PASSWORD_MISSING);
        }
        */

        /* Not mandatory.
        if  (catalog == null) 
        {
            throw new BuildException(CATALOG_MISSING);
        }
        */

        if  (schema == null) 
        {
            throw new BuildException(SCHEMA_MISSING);
        }

        if  (repository == null) 
        {
            throw new BuildException(REPOSITORY_MISSING);
        }

        if  (packageName == null) 
        {
            throw new BuildException(PACKAGE_MISSING);
        }

        if  (classpath == null) 
        {
            throw new BuildException(CLASSPATH_MISSING);
        }

        if  (outputdir == null) 
        {
            throw new BuildException(OUTPUTDIR_MISSING);
        }

        if  (!outputdir.isDirectory())
        {
            throw new BuildException(OUTPUTDIR_NOT_FOLDER);
        }

        if  (jndiDataSources == null)
        {
            throw new BuildException(JNDI_DATASOURCES_MISSING);
        }

        if  (   (jndiDataSources.indexOf("\"") != -1)
             || (jndiDataSources.indexOf("\n") != -1))
        {
            throw new BuildException(JNDI_DATASOURCES_INVALID);
        }

        if  (   (tables != null)
             && (   (tables.getTables() == null)
                 || (tables.getTables().size() == 0)))
        {
            throw new BuildException(TABLES_MISSING);
        }

        if  (   (externallyManagedFields != null)
             && (   (externallyManagedFields.getFields() == null)
                 || (externallyManagedFields.getFields().size() == 0)))
        {
            throw new BuildException(EXTERNALLY_MANAGED_FIELDS_MISSING);
        }

        /* Not mandatory
        if  (customSqlModel == null)
        {
            throw new BuildException(CUSTOM_SQL_MODEL_MISSING);
        }
        */
        if  (   (CUSTOM_SQL_MODEL_XML.equals(customSqlModel))
             && (   (sqlXmlFile == null)
                 || (!sqlXmlFile.exists())
                 || (!sqlXmlFile.canRead())))
        {
            throw new BuildException(SQL_XML_FILE_MISSING);
        }
    }
}
