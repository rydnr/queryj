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
import org.acmsl.queryj.tools.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataLoggingHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ExternallyManagedFieldsRetrievalHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionClosingHandler;
import org.acmsl.queryj.tools.handlers.JdbcConnectionOpeningHandler;
import org.acmsl.queryj.tools.templates.dao.DAOBundle;
import org.acmsl.queryj.tools.templates.handlers.KeywordRepositoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.handlers.ProcedureRepositoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.handlers.TableRepositoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.handlers.TestSuiteTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.functions.FunctionsBundle;
import org.acmsl.queryj.tools.templates.valueobject.handlers.ValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.tools.templates.valueobject.handlers.ValueObjectTemplateHandlerBundle;

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

/**
 * Generates QueryJ classes using Ant.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$ at $Date$
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
     * The "custom-sql-model" type.
     */
    private String m__strCustomSqlModel =
        ParameterValidationHandler.CUSTOM_SQL_MODEL_XML;

    /**
     * The "sql-xml-file" path.
     */
    private File m__SqlXmlFile;

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
    public void setDriver(final String driver)
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
    public void setUrl(final String url)
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
    public void setUsername(final String username)
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
    public void setPassword(final String password)
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
    public void setCatalog(final String catalog)
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
    public void setSchema(final String schema)
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
    public void setRepository(final String repository)
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
    public void setPackage(final String packageName)
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
    public void setClasspath(final Path classpath)
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
    public void setClasspathRef(final Reference classpathReference)
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
    public void setOutputdir(final File outputdir)
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
    public void setExtractTables(final String extractTables)
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
    protected void setExtractTablesFlag(final boolean flag)
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
    public void setExtractProcedures(final String extractProcedures)
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
    protected void setExtractProceduresFlag(final boolean flag)
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
    public void setExtractFunctions(final String extractFunctions)
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
    protected void setExtractFunctionsFlag(final boolean flag)
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
    public void setJndiDataSource(final String jndiLocation)
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
    public void setGenerateMockDAOImplementation(final String generate)
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
    protected void setGenerateMockDAOImplementationFlag(final boolean flag)
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
        final AntExternallyManagedFieldsElement externallyManagedFields)
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
     * Specifies the custom-sql model.
     * @param model the model.
     */
    public void setCustomSqlModel(final String model)
    {
        m__strCustomSqlModel = model;
    }

    /**
     * Retrieves the custom-sql model.
     * @return such model.
     */
    public String getCustomSqlModel()
    {
        return m__strCustomSqlModel;
    }

    /**
     * Specifies the sql.xml file.
     * @param file the new file.
     */
    public void setSqlXmlFile(final File file)
    {
        m__SqlXmlFile = file;
    }

    /**
     * Retrieves the sql.xml file.
     * @return such information.
     */
    public File getSqlXmlFile() 
    {
        return m__SqlXmlFile;
    }

    /**
     * Builds the chain.
     * @param chain the chain to be configured.
     * @return the updated chain.
     */
    protected Chain buildChain(final Chain chain)
    {
        Chain result = chain;

        if  (result != null) 
        {
            result.add(new ParameterValidationHandler());

            result.add(new JdbcConnectionOpeningHandler());
            result.add(new DatabaseMetaDataRetrievalHandler());
//            result.add(new OracleMetaDataRetrievalHandler());

            result.add(new CustomSqlProviderRetrievalHandler());

            result.add(new DatabaseMetaDataLoggingHandler());

            result.add(new ExternallyManagedFieldsRetrievalHandler());

            result.add(new TableTemplateHandlerBundle());

            result.add(new TableRepositoryTemplateHandlerBundle());

            result.add(new FunctionsBundle());

            result.add(new ProcedureRepositoryTemplateHandlerBundle());

            result.add(new KeywordRepositoryTemplateHandlerBundle());

            result.add(new DAOBundle());

            result.add(new ValueObjectTemplateHandlerBundle());

            result.add(new ValueObjectFactoryTemplateHandlerBundle());

            result.add(new TestSuiteTemplateHandlerBundle());

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

            String t_strCustomSqlModel = getCustomSqlModel();
            File t_SqlXmlFile = getSqlXmlFile();

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

                if  (t_strCustomSqlModel != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.CUSTOM_SQL_MODEL,
                        t_strCustomSqlModel);
                }

                if  (t_SqlXmlFile != null)
                {
                    t_mAttributes.put(
                        ParameterValidationHandler.SQL_XML_FILE,
                        t_SqlXmlFile);
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
