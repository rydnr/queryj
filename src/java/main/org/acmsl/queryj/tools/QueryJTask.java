/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Generates QueryJ classes using Ant.
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
package org.acmsl.queryj.tools;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.AntTablesElement;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataLoggingHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ExternallyManagedFieldsRetrievalHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionClosingHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;
import org.acmsl.queryj.tools.templates.dao.handlers
    .JdbcDAOTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .JdbcDAOTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.handlers
    .KeywordRepositoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.handlers
    .KeywordRepositoryTemplateWritingHandler;

import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers
    .ProcedureRepositoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.handlers
    .ProcedureRepositoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .BaseDAOFactoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .BaseDAOFactoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .BaseDAOTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .BaseDAOTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .ConfigurationPropertiesTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .ConfigurationPropertiesTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOChooserTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOChooserTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOFactoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOFactoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOTestTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DAOTestTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DataAccessManagerTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.handlers
    .DataAccessManagerTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.mock.handlers
    .MockDAOFactoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.mock.handlers
    .MockDAOFactoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.dao.mock.handlers
    .MockDAOTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.dao.mock.handlers
    .MockDAOTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TableRepositoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TableRepositoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TableTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TableTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TestSuiteTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.handlers
    .TestSuiteTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.numeric.handlers
    .NumericFunctionsTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.numeric.handlers
    .NumericFunctionsTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.numeric.handlers
    .NumericFunctionsTestTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.numeric.handlers
    .NumericFunctionsTestTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.system.handlers
    .SystemFunctionsTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.system.handlers
    .SystemFunctionsTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.system.handlers
    .SystemFunctionsTestTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.system.handlers
    .SystemFunctionsTestTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.text.handlers
    .TextFunctionsTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.text.handlers
    .TextFunctionsTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.text.handlers
    .TextFunctionsTestTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.text.handlers
    .TextFunctionsTestTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.time.handlers
    .TimeFunctionsTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.time.handlers
    .TimeFunctionsTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.functions.time.handlers
    .TimeFunctionsTestTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.functions.time.handlers
    .TimeFunctionsTestTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.valueobject.handlers
    .ValueObjectFactoryTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.valueobject.handlers
    .ValueObjectFactoryTemplateWritingHandler;

import org.acmsl.queryj.tools.templates.valueobject.handlers
    .ValueObjectTemplateBuildHandler;

import org.acmsl.queryj.tools.templates.valueobject.handlers
    .ValueObjectTemplateWritingHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Chain;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Map;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Generates QueryJ classes using Ant.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class QueryJTask
    extends     ChainTask
    implements  DynamicConfigurator
{
    /**
     * The driver.
     */
    private String m__strDriver;

    /**
     * The url.
     */
    private String m__strUrl;

    /**
     * The username.
     */
    private String m__strUsername;

    /**
     * The password.
     */
    private String m__strPassword;

    /**
     * The catalog.
     */
    private String m__strCatalog;

    /**
     * The schema.
     */
    private String m__strSchema;

    /**
     * The repository.
     */
    private String m__strRepository;

    /**
     * The package.
     */
    private String m__strPackage;

    /**
     * The classpath.
     */
    private Path m__Classpath;

    /**
     * The output folder.
     */
    private File m__Outputdir;

    /**
     * The "extract-tables" value.
     */
    private String m__strExtractTables;

    /**
     * The "extract-tables" flag.
     */
    private boolean m__bExtractTables = true;

    /**
     * The "extract-procedures" value.
     */
    private String m__strExtractProcedures;

    /**
     * The "extract-procedures" flag.
     */
    private boolean m__bExtractProcedures = true;

    /**
     * The "extract-functions" value.
     */
    private String m__strExtractFunctions;

    /**
     * The "extract-functions" flag.
     */
    private boolean m__bExtractFunctions = true;

    /**
     * The JNDI location for the data sources.
     */
    private String m__strJNDIDataSources;

    /**
     * The "generate-mock-dao-implementation" value.
     */
    private String m__strGenerateMockDAOImplementation;

    /**
     * The "generate-mock-dao-implementation" flag.
     */
    private boolean m__bGenerateMockDAOImplementation = true;

    /**
     * The nested tables.
     */
    private AntTablesElement m__Tables;

    /**
     * The externally-managed fields.
     */
    private AntExternallyManagedFieldsElement m__ExternallyManagedFields;

    /**
     * Creates a QueryJTask.
     */
    public QueryJTask() {};

    /**
     * Specifies the driver.
     * @param driver the new driver.
     */
    public void setDriver(String driver)
    {
        m__strDriver = driver;
    }

    /**
     * Retrieves the driver.
     * @return such information.
     */
    public String getDriver() 
    {
        return m__strDriver;
    }

    /**
     * Specifies the url.
     * @param url the new url.
     */
    public void setUrl(String url)
    {
        m__strUrl = url;
    }

    /**
     * Retrieves the url.
     * @return such information.
     */
    public String getUrl() 
    {
        return m__strUrl;
    }

    /**
     * Specifies the username.
     * @param username the new username.
     */
    public void setUsername(String username)
    {
        m__strUsername = username;
    }

    /**
     * Retrieves the username.
     * @return such information.
     */
    public String getUsername() 
    {
        return m__strUsername;
    }

    /**
     * Specifies the password.
     * @param password the new password.
     */
    public void setPassword(String password)
    {
        m__strPassword = password;
    }

    /**
     * Retrieves the password.
     * @return such information.
     */
    public String getPassword() 
    {
        return m__strPassword;
    }

    /**
     * Specifies the catalog.
     * @param catalog the new catalog.
     */
    public void setCatalog(String catalog)
    {
        m__strCatalog = catalog;
    }

    /**
     * Retrieves the catalog.
     * @return such information.
     */
    public String getCatalog() 
    {
        return m__strCatalog;
    }

    /**
     * Specifies the schema.
     * @param schema the new schema.
     */
    public void setSchema(String schema)
    {
        m__strSchema = schema;
    }

    /**
     * Retrieves the schema.
     * @return such information.
     */
    public String getSchema() 
    {
        return m__strSchema;
    }

    /**
     * Specifies the repository.
     * @param repository the new repository.
     */
    public void setRepository(String repository)
    {
        m__strRepository = repository;
    }

    /**
     * Retrieves the repository.
     * @return such information.
     */
    public String getRepository() 
    {
        return m__strRepository;
    }

    /**
     * Specifies the package.
     * @param packageName the new package.
     */
    public void setPackage(String packageName)
    {
        m__strPackage = packageName;
    }

    /**
     * Retrieves the package.
     * @return such information.
     */
    public String getPackage() 
    {
        return m__strPackage;
    }

    /**
     * Specifies the classpath.
     * @param classpath the new classpath.
     */
    public void setClasspath(Path classpath)
    {
        m__Classpath = classpath;
    }

    /**
     * Creates the classpath structure.
     * @return such path.
     */ 
    public Path createClasspath()
    {
        Path t_Classpath = getClasspath();

        if  (t_Classpath == null)
        {
            t_Classpath = new Path(getProject());

            setClasspath(t_Classpath);
        }

        return t_Classpath.createPath();
    }

    /**
     * Reference to the classpath.
     * @param classpathReference the reference to the class path.
     */
    public void setClasspathRef(Reference classpathReference)
    {
        Path t_Path = createClasspath();

        if  (t_Path != null) 
        {
            t_Path.setRefid(classpathReference);
        }
    }

    /**
     * Retrieves the classpath.
     * @return such information.
     */
    public Path getClasspath() 
    {
        return m__Classpath;
    }

    /**
     * Specifies the outputdir.
     * @param outputdir the new outputdir.
     */
    public void setOutputdir(File outputdir)
    {
        m__Outputdir = outputdir;
    }

    /**
     * Retrieves the outputdir.
     * @return such information.
     */
    public File getOutputdir() 
    {
        return m__Outputdir;
    }

    /**
     * Specifies whether to extract the tables.
     * @param extractTables the procedure extraction setting.
     */
    public void setExtractTables(String extractTables)
    {
        m__strExtractTables = extractTables;
        setExtractTablesFlag(
            (   (extractTables == null)
             || (   (extractTables.trim().toLowerCase().equals("yes"))
                 || (extractTables.trim().toLowerCase().equals("true")))));
    }

    /**
     * Retrieves whether to extract the tables.
     * @return such setting.
     */
    public String getExtractTables()
    {
        return m__strExtractTables;
    }

    /**
     * Specifies the "extract-tables" flag.
     * @param flag such flag.
     */
    protected void setExtractTablesFlag(boolean flag)
    {
        m__bExtractTables = flag;
    }

    /**
     * Retrieves the "extract-tables" flag.
     * @return such flag.
     */
    protected boolean getExtractTablesFlag()
    {
        return m__bExtractTables;
    }

    /**
     * Specifies whether to extract the procedures.
     * @param extractProcedures the procedure extraction setting.
     */
    public void setExtractProcedures(String extractProcedures)
    {
        m__strExtractProcedures = extractProcedures;
        setExtractProceduresFlag(
            (   (extractProcedures == null)
             || (   (extractProcedures.trim().toLowerCase().equals("yes"))
                 || (extractProcedures.trim().toLowerCase().equals("true")))));
    }

    /**
     * Retrieves whether to extract the procedures.
     * @return such setting.
     */
    public String getExtractProcedures()
    {
        return m__strExtractProcedures;
    }

    /**
     * Specifies the "extract-procedures" flag.
     * @param flag such flag.
     */
    protected void setExtractProceduresFlag(boolean flag)
    {
        m__bExtractProcedures = flag;
    }

    /**
     * Retrieves the "extract-procedures" flag.
     * @return such flag.
     */
    protected boolean getExtractProceduresFlag()
    {
        return m__bExtractProcedures;
    }

    /**
     * Specifies whether to extract the functions.
     * @param extractFunctions the function extraction setting.
     */
    public void setExtractFunctions(String extractFunctions)
    {
        m__strExtractFunctions = extractFunctions;
        setExtractFunctionsFlag(
            (   (extractFunctions == null)
             || (   (extractFunctions.trim().toLowerCase().equals("yes"))
                 || (extractFunctions.trim().toLowerCase().equals("true")))));
    }

    /**
     * Retrieves whether to extract the functions.
     * @return such setting.
     */
    public String getExtractFunctions()
    {
        return m__strExtractFunctions;
    }

    /**
     * Specifies the "extract-functions" flag.
     * @param flag such flag.
     */
    protected void setExtractFunctionsFlag(boolean flag)
    {
        m__bExtractFunctions = flag;
    }

    /**
     * Retrieves the "extract-functions" flag.
     * @return such flag.
     */
    protected boolean getExtractFunctionsFlag()
    {
        return m__bExtractFunctions;
    }

    /**
     * Specifices the JNDI location for the data sources.
     * @param jndiLocation the JNDI location.
     */
    public void setJndiDataSource(String jndiLocation)
    {
        m__strJNDIDataSources = jndiLocation;
    }

    /**
     * Retrieves the JNDI location for the data sources.
     * @return such information.
     */
    public String getJndiDataSource()
    {
        return m__strJNDIDataSources;
    }

    /**
     * Specifies whether to generate Mock DAO implementations.
     * @param generate such setting.
     */
    public void setGenerateMockDAOImplementation(String generate)
    {
        m__strGenerateMockDAOImplementation = generate;

        setGenerateMockDAOImplementationFlag(
            (   (generate == null)
             || (   (generate.trim().toLowerCase().equals("yes"))
                 || (generate.trim().toLowerCase().equals("true")))));
    }

    /**
     * Retrieves whether to generate Mock DAO implementations.
     * @return such setting.
     */
    public String getGenerateMockDAOImplementation()
    {
        return m__strGenerateMockDAOImplementation;
    }

    /**
     * Specifies the "generate-mock-dao-implementation" flag.
     * @param flag such flag.
     */
    protected void setGenerateMockDAOImplementationFlag(boolean flag)
    {
        m__bGenerateMockDAOImplementation = flag;
    }

    /**
     * Retrieves the "generate-mock-dao-implementation" flag.
     * @return such flag.
     */
    protected boolean getGenerateMockDAOImplementationFlag()
    {
        return m__bGenerateMockDAOImplementation;
    }

    /**
     * Specifies the "tables" nested element.
     * @param tables the tables xml element.
     */
    protected void setTables(AntTablesElement tables)
    {
        m__Tables = tables;
    }

    /**
     * Retrieves the "tables" nested element.
     * @return such information.
     */
    public AntTablesElement getTables()
    {
        return m__Tables;
    }

    /**
     * Specifies the "externally-managed-fields" nested element.
     * @param externallyManagedFields the externally-managed-fields xml
     * element.
     */
    protected void setExternallyManagedFields(
        AntExternallyManagedFieldsElement externallyManagedFields)
    {
        m__ExternallyManagedFields = externallyManagedFields;
    }

    /**
     * Retrieves the "externally-managed-fields" nested element.
     * @return such information.
     */
    public AntExternallyManagedFieldsElement getExternallyManagedFields()
    {
        return m__ExternallyManagedFields;
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    protected Chain buildChain(Chain chain)
    {
        Chain result = chain;

        if  (result != null) 
        {
            result.add(new ParameterValidationHandler());

            result.add(new JdbcConnectionOpeningHandler());
            result.add(new DatabaseMetaDataRetrievalHandler());
//            result.add(new OracleMetaDataRetrievalHandler());

            result.add(new DatabaseMetaDataLoggingHandler());

            result.add(new ExternallyManagedFieldsRetrievalHandler());

            result.add(new TableTemplateBuildHandler());
            result.add(new TableTemplateWritingHandler());

            result.add(new TableRepositoryTemplateBuildHandler());
            result.add(new TableRepositoryTemplateWritingHandler());

            result.add(new TimeFunctionsTemplateBuildHandler());
            result.add(new TimeFunctionsTestTemplateBuildHandler());
            result.add(new TimeFunctionsTemplateWritingHandler());
            result.add(new TimeFunctionsTestTemplateWritingHandler());

            result.add(new NumericFunctionsTemplateBuildHandler());
            result.add(new NumericFunctionsTemplateWritingHandler());
            result.add(new NumericFunctionsTestTemplateBuildHandler());
            result.add(new NumericFunctionsTestTemplateWritingHandler());

            result.add(new TextFunctionsTemplateBuildHandler());
            result.add(new TextFunctionsTestTemplateBuildHandler());
            result.add(new TextFunctionsTemplateWritingHandler());
            result.add(new TextFunctionsTestTemplateWritingHandler());

            result.add(new SystemFunctionsTemplateBuildHandler());
            result.add(new SystemFunctionsTestTemplateBuildHandler());
            result.add(new SystemFunctionsTemplateWritingHandler());
            result.add(new SystemFunctionsTestTemplateWritingHandler());

            result.add(new ProcedureRepositoryTemplateBuildHandler());
            result.add(new ProcedureRepositoryTemplateWritingHandler());

            result.add(new KeywordRepositoryTemplateBuildHandler());
            result.add(new KeywordRepositoryTemplateWritingHandler());

            result.add(new DAOTemplateBuildHandler());
            result.add(new DAOTemplateWritingHandler());

            result.add(new DAOFactoryTemplateBuildHandler());
            result.add(new DAOFactoryTemplateWritingHandler());

            result.add(new ValueObjectTemplateBuildHandler());
            result.add(new ValueObjectTemplateWritingHandler());

            result.add(new ValueObjectFactoryTemplateBuildHandler());
            result.add(new ValueObjectFactoryTemplateWritingHandler());

            result.add(new BaseDAOTemplateBuildHandler());
            result.add(new BaseDAOTemplateWritingHandler());

            result.add(new BaseDAOFactoryTemplateBuildHandler());
            result.add(new BaseDAOFactoryTemplateWritingHandler());

            result.add(new DataAccessManagerTemplateBuildHandler());
            result.add(new DataAccessManagerTemplateWritingHandler());

            result.add(new DAOChooserTemplateBuildHandler());
            result.add(new DAOChooserTemplateWritingHandler());

            result.add(new ConfigurationPropertiesTemplateBuildHandler());
            result.add(new ConfigurationPropertiesTemplateWritingHandler());

            result.add(new JdbcDAOTemplateBuildHandler());
            result.add(new JdbcDAOTemplateWritingHandler());

            result.add(new DAOTestTemplateBuildHandler());
            result.add(new DAOTestTemplateWritingHandler());

            result.add(new MockDAOTemplateBuildHandler());
            result.add(new MockDAOTemplateWritingHandler());

            result.add(new MockDAOFactoryTemplateBuildHandler());
            result.add(new MockDAOFactoryTemplateWritingHandler());

            result.add(new TestSuiteTemplateBuildHandler());
            result.add(new TestSuiteTemplateWritingHandler());

            result.add(new JdbcConnectionClosingHandler());
        }
        
        return result;
    }

    /**
     * Performs any clean up whenever an error occurs.
     * @param buildException the error that triggers this clean-up.
     * @param command the command.
     */
    protected void cleanUpOnError(
        BuildException buildException, AntCommand command)
    {
        log("Performing clean up");
        new JdbcConnectionClosingHandler().handle(command);
    }

    /**
     * Builds the command.
     * @param command the command to be initialized.
     * @return the initialized command.
     */
    protected AntCommand buildCommand(AntCommand command)
    {
        AntCommand result = command;

        if  (result != null) 
        {
            Map t_mAttributes = result.getAttributeMap();

            String t_strDriver     = getDriver();
            String t_strUrl        = getUrl();
            String t_strUsername   = getUsername();
            String t_strPassword   = getPassword();
            String t_strCatalog    = getCatalog();
            String t_strSchema     = getSchema();
            String t_strRepository = getRepository();
            String t_strPackage    = getPackage();
            Path   t_Classpath     = getClasspath();
            File   t_Outputdir     = getOutputdir();

            boolean t_bExtractTables = getExtractTablesFlag();
            boolean t_bExtractProcedures = getExtractProceduresFlag();
            boolean t_bExtractFunctions = getExtractFunctionsFlag();
            String t_strJNDIDataSources = getJndiDataSource();

            boolean t_bGenerateMockDAOImplementation =
                getGenerateMockDAOImplementationFlag();

            AntTablesElement t_Tables = getTables();

            AntExternallyManagedFieldsElement t_ExternallyManagedFields =
                getExternallyManagedFields();

            if  (t_mAttributes != null)
            {
                if  (t_strDriver != null) 
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_DRIVER,
                        t_strDriver);
                }

                if  (t_strUrl != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_URL,
                        t_strUrl);
                }

                if  (t_strUsername != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_USERNAME,
                        t_strUsername);
                }

                if  (t_strPassword != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_PASSWORD,
                        t_strPassword);
                }

                if  (t_strCatalog != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_CATALOG,
                        t_strCatalog);
                }

                if  (t_strSchema != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JDBC_SCHEMA,
                        t_strSchema);
                }

                if  (t_strRepository != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.REPOSITORY,
                        t_strRepository);
                }

                if  (t_strPackage != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.PACKAGE,
                        t_strPackage);
                }

                if  (t_Classpath != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.CLASSPATH,
                        t_Classpath);
                }

                if  (t_Outputdir != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.OUTPUT_DIR,
                        t_Outputdir);
                }

                t_mAttributes.put(
                    ParameterValidationHandler.EXTRACT_TABLES,
                    (t_bExtractTables
                     ?  Boolean.TRUE
                     :  Boolean.FALSE));

                t_mAttributes.put(
                    ParameterValidationHandler.EXTRACT_PROCEDURES,
                    (t_bExtractProcedures
                     ?  Boolean.TRUE
                     :  Boolean.FALSE));

                t_mAttributes.put(
                    ParameterValidationHandler.EXTRACT_FUNCTIONS,
                    (t_bExtractFunctions
                     ?  Boolean.TRUE
                     :  Boolean.FALSE));

                if  (t_strJNDIDataSources != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.JNDI_DATASOURCES,
                        t_strJNDIDataSources);
                }

                t_mAttributes.put(
                    ParameterValidationHandler.GENERATE_MOCK_DAO,
                    (t_bGenerateMockDAOImplementation
                     ?  Boolean.TRUE
                     :  Boolean.FALSE));

                if  (t_Tables != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.TABLES,
                        t_Tables);
                }

                if  (t_ExternallyManagedFields != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS,
                        t_ExternallyManagedFields);
                }
            }
        }
        
        return result;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public void setDynamicAttribute(String name, String value)
    {
        throw
            new BuildException(
                "No dynamic attributes are supported (" + name + "=" + value + ")");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     * @throws BuildException if the element is not supported.
     */
    public Object createDynamicElement(String name)
    {
        Object result = null;

        if  ("tables".equals(name))
        {
            AntTablesElement t_ateResult = null;

            if  (!getExtractTablesFlag())
            {
                throw new BuildException("Table extraction is disabled.");
            }
            else
            {
                t_ateResult = new AntTablesElement();
            }

            setTables(t_ateResult);

            result = t_ateResult;
        }
        else if  ("externally-managed-fields".equals(name))
        {
            AntExternallyManagedFieldsElement t_aemfeResult =
                new AntExternallyManagedFieldsElement();

            setExternallyManagedFields(t_aemfeResult);

            result = t_aemfeResult;
        }
        else
        {
            throw new BuildException(name + " elements are not supported");
        }

        return result;
    }
}
