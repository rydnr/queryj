//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: QueryJMojo.java
 *
 * Author: Jose San Leandro Armendariz/Jose Juan.
 *
 * Description: Executes QueryJ.
 */
package org.acsml.queryj.tools.maven;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Iterator;
import java.util.List;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntTableElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.ant.QueryJTask;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/**
 * Executes QueryJ.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class QueryJMojo
    extends AbstractMojo
    implements Mojo
{
    /**
     * The driver.
     * @parameter
     */
    private String m__strDriver;
    
    /**
     * The url.
     * @parameter
     */
    private String m__strUrl;
    
    /**
     * The user name.
     * @parameter
     */
    private String m__strUsername;
    
    /**
     * The password.
     * @parameter
     */
    private String m__strPassword;
    
    /**
     * The catalog.
     * @parameter
     */
    private String m__strCatalog;
    
    /**
     * The schema.
     * @parameter
     */
    private String m__strSchema;
    
    /**
     * The repository.
     * @parameter
     */
    private String m__strRepository;
    
    /**
     * The package name.
     * @parameter
     */
    private String m__strPackageName;
    
    /**
     * The output directory.
     * @parameter
     */
    private File m__OutputDir;
    
    /**
     * The data source.
     * @parameter
     */
    private String m__strJndiDataSource;
    
    /**
     * The sql xml file.
     * @parameter
     */
    private File m__SqlXmlFile;
    
    /**
     * The customized sql model.
     * @parameter
     */
    private String m__strCustomSqlModel;
    
    /**
     * The xml dao implementation flag.
     * @parameter
     */
    private Boolean m__bGenerateXmlDAOImplementation = Boolean.TRUE;
    
    /**
     * The mock dao implementation flag.
     * @parameter
     */
    private Boolean m__bGenerateMockDAOImplementation = Boolean.TRUE;
    
    /**
     * The test generation flag.
     * @parameter
     */
    private Boolean m__bGenerateTests = Boolean.FALSE;
    
    /**
     * The header file
     * @parameter
     */
    private File m__HeaderFile;
    
    /**
     * The extract functions flag.
     * @parameter
     */
    private Boolean m__bExtractFunctions = Boolean.TRUE;
    
    /**
     * The extract procedures flag.
     * @parameter
     */
    private Boolean m__bExtractProcedures = Boolean.TRUE;
    
    /**
     * The list of external managed fields
     * @parameter
     */
    private ExternallyManagedField[] m__aExternallyManagedFields;
    
    /**
     * The grammar bundle file.
     * @parameter
     */
    private String m__strGrammarbundle;
    
    /**
     * The list of tables.
     * @parameter
     */
    private Table[] m__aTables;

    /**
     * The file encoding.
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    private String m__strEncoding;

    /**
     * Returns the driver.
     * @return such value.
     */
    protected final String immutableGetDriver()
    {
        return m__strDriver;
    }

    /**
     * Returns the driver.
     * @return such value.
     */
    protected String getDriver()
    {
        return immutableGetDriver();
    }

    /**
     * Returns the url.
     * @return such value.
     */
    protected final String immutableGetUrl()
    {
        return m__strUrl;
    }
    
    /**
     * Returns the url.
     * @return such value.
     */
    protected String getUrl()
    {
        return immutableGetUrl();
    }
    
    /**
     * Returns the user name.
     * @return such value.
     */
    protected final String immutableGetUsername() 
    {
        return m__strUsername;
    }
    
    /**
     * Returns the user name.
     * @return such value.
     */
    protected String getUsername() 
    {
        return immutableGetUsername();
    }
    
    /**
     * Returns the password.
     * @return such value.
     */
    protected final String immutableGetPassword()
    {
        return m__strPassword;
    }
    
    /**
     * Returns the password.
     * @return such value.
     */
    protected String getPassword()
    {
        return immutableGetPassword();
    }
    
    /**
     * Returns the catalog.
     * @return such value.
     */
    protected final String immutableGetCatalog()
    {
        return m__strCatalog;
    }
    
    /**
     * Returns the catalog.
     * @return such value.
     */
    protected String getCatalog()
    {
        return immutableGetCatalog();
    }
    
    /**
     * Returns the schema.
     * @return such value.
     */
    protected final String immutableGetSchema()
    {
        return m__strSchema;
    }
    
    /**
     * Returns the schema.
     * @return such value, or an empty string if not initialized.
     */
    protected String getSchema()
    {
        String result = immutableGetSchema();

        if  (result == null)
        {
            result = "";
        }

        return result;
    }
    
    /**
     * Returns the repository.
     * @return such value.
     */
    protected final String immutableGetRepository()
    {
        return m__strRepository;
    }
    
    /**
     * Returns the repository.
     * @return such value, or an empty string if not initialized.
     */
    protected String getRepository()
    {
        String result = immutableGetRepository();

        if  (result == null)
        {
            result = "";
        }

        return result;
    }
    
    /**
     * Returns the package name.
     * @return such value.
     */
    protected final String immutableGetPackageName()
    {
        return m__strPackageName;
    }
    
    /**
     * Returns the package name.
     * @return such value.
     */
    protected String getPackageName()
    {
        return immutableGetPackageName();
    }
    
    /**
     * Returns the output directory.
     * @return such directory.
     */
    protected final File immutableGetOutputDir()
    {
        return m__OutputDir;
    }
    
    /**
     * Returns the output directory.
     * @return such directory.
     */
    protected File getOutputDir()
    {
        return immutableGetOutputDir();
    }
    
    /**
     * Returns the JNDI location of the data source.
     * @return such value.
     */
    protected final String immutableGetJndiDataSource()
    {
        return m__strJndiDataSource;
    }
    
    /**
     * Returns the JNDI location of the data source.
     * @return such value.
     */
    protected String getJndiDataSource()
    {
        return immutableGetJndiDataSource();
    }
    
    /**
     * Return the sql xml file.
     * @return such file.
     */
    protected final File immutableGetSqlXmlFile()
    {
        return m__SqlXmlFile;
    }
    
    /**
     * Return the sql xml file.
     * @return such file.
     */
    protected File getSqlXmlFile()
    {
        return immutableGetSqlXmlFile();
    }
    
    /**
     * Returns the custom sql model.
     * @return such value.
     */
    protected final String immutableGetCustomSqlModel()
    {
        return m__strCustomSqlModel;
    }
    
    /**
     * Returns the custom sql model.
     * @return such value.
     */
    protected String getCustomSqlModel()
    {
        return immutableGetCustomSqlModel();
    }
    
    /**
     * Indicates if xml dao must be generated.
     * @return such condition.
     */
    protected final Boolean immutableGetGenerateXmlDAOImplementation()
    {
        return m__bGenerateXmlDAOImplementation;
    }
    
    /**
     * Indicates if xml dao must be generated.
     * @return such condition.
     */
    protected Boolean getGenerateXmlDAOImplementation()
    {
        return immutableGetGenerateXmlDAOImplementation();
    }
    
    /**
     * Indicates if mock dao must be generated.
     * @return such condition.
     */
    protected final Boolean immutableGetGenerateMockDAOImplementation()
    {
        return m__bGenerateMockDAOImplementation;
    }
    
    /**
     * Indicates if mock dao must be generated.
     * @return such condition.
     */
    protected Boolean getGenerateMockDAOImplementation()
    {
        return immutableGetGenerateMockDAOImplementation();
    }
    
    /**
     * Indicates if tests must be generated.
     * @return such condition.
     */
    protected final Boolean immutableGetGenerateTests()
    {
        return m__bGenerateTests;
    }
    
    /**
     * Indicates if tests must be generated.
     * @return such condition.
     */
    protected Boolean getGenerateTests()
    {
        return immutableGetGenerateTests();
    }
    
    /**
     * Returns the header file.
     * @return such file.
     */
    protected final File immutableGetHeaderFile()
    {
        return m__HeaderFile;
    }
    
    /**
     * Returns the header file.
     * @return such file.
     */
    protected File getHeaderFile()
    {
        return immutableGetHeaderFile();
    }
    
    /**
     * Indicates if functions must be extracted.
     * @return such condition.
     */
    protected final Boolean immutableGetExtractFunctions()
    {
        return m__bExtractFunctions;
    }
    
    /**
     * Indicates if functions must be extracted.
     * @return such condition.
     */
    protected Boolean getExtractFunctions()
    {
        return immutableGetExtractFunctions();
    }
    
    /**
     * Indicates if procedures must be extracted.
     * @return such condition.
     */
    protected final Boolean immutableGetExtractProcedures()
    {
        return m__bExtractProcedures;
    }
    
    /**
     * Indicates if procedures must be extracted.
     * @return such condition.
     */
    protected Boolean getExtractProcedures()
    {
        return immutableGetExtractProcedures();
    }
    
    /**
     * Returns the externally managed fields.
     * @return such fields.
     */
    protected final ExternallyManagedField[] immutableGetExternallyManagedFields()
    {
        return m__aExternallyManagedFields;
    }
    
    /**
     * Returns the externally managed fields.
     * @return such fields.
     */
    protected ExternallyManagedField[] getExternallyManagedFields()
    {
        return immutableGetExternallyManagedFields();
    }
    
    /**
     * Returns the grammar bundle.
     * @return such resource.
     */
    protected final String immutableGetGrammarbundle()
    {
        return m__strGrammarbundle;
    }
    
    /**
     * Returns the grammar bundle.
     * @return such resource.
     */
    protected String getGrammarbundle()
    {
        return immutableGetGrammarbundle();
    }
    
    /**
     * Returns the tables.
     * @return such information.
     */
    protected final Table[] immutableGetTables()
    {
        return m__aTables;
    }
    
    /**
     * Returns the tables.
     * @return such information.
     */
    protected Table[] getTables()
    {
        return immutableGetTables();
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     */
    protected final void immutableSetEncoding(final String encoding)
    {
        m__strEncoding = encoding;
    }

    /**
     * Specifies the encoding.
     * @param encoding the new encoding.
     */
    public void setEncoding(final String encoding)
    {
        immutableSetEncoding(encoding);
    }

    /**
     * Retrieves the encoding.
     * @return such information.
     */
    public String getEncoding()
    {
        return m__strEncoding;
    }

    /**
     * Executes QueryJ via Maven2.
     * @throws MojoExecutionException if something goes wrong.
     */
    public void execute()
        throws MojoExecutionException
    {
        getLog().info("Generating QueryJ...");
        
        File outputDirPath = getOutputDir();
        QueryJTask task = null;

        if  (outputDirPath != null)
        {
            //initialize directories
            File outputDir = outputDirPath.getAbsoluteFile();
            outputDir.mkdirs();
        
            //execute task  
            task = buildTask();

            if  (task != null)
            {
                task.execute();
            }
        }
    }
    
    /**
     * Builds the QueryJ task.
     * @return such info
     */
    protected QueryJTask buildTask()
    {
        QueryJTask result = new QueryJTask();

        Project project = new Project();

        result.setProject(project);
        
        Path path = new Path(project);
        result.setClasspath(path);
        
        result.setCatalog(getCatalog());
        result.setDriver(getDriver());
        result.setJndiDataSource(getJndiDataSource());
        result.setOutputdir(getOutputDir());
        result.setPackage(getPackageName());
        result.setPassword(getPassword());
        result.setRepository(getRepository());
        result.setSchema(getSchema());
        result.setUsername(getUsername());
        result.setUrl(getUrl());

        result.setGenerateMockDAOImplementation(
            "" + getGenerateMockDAOImplementation());
        result.setGenerateXMLDAOImplementation(
            "" + getGenerateXmlDAOImplementation());
        result.setGenerateTests(
            "" + getGenerateTests());
        
        result.setCustomSqlModel(getCustomSqlModel());
        result.setSqlXmlFile(getSqlXmlFile());
        result.setHeaderfile(getHeaderFile());
        
        result.setExtractFunctions(
            "" + getExtractFunctions());
        result.setExtractProcedures(
            "" + getExtractProcedures());
        
        getLog().info("grammar bundle is..." + getGrammarbundle());

        result.setGrammarbundle(getGrammarbundle());
        
        buildExternallyManagedFields(result);
        buildTables(result);

        result.setEncoding(getEncoding());

        return result;
    }
    
    /**
     * Builds the external managed fields list
     * @param task the task
     */
    protected void buildExternallyManagedFields(
        final QueryJTask task)
    {
        ExternallyManagedField[] array = getExternallyManagedFields();

        int count = (array == null) ? 0 : array.length;
        AntExternallyManagedFieldsElement element;
        ExternallyManagedField field;
        AntFieldElement fieldElement;

        if  (count > 0)
        {
            element = 
                (AntExternallyManagedFieldsElement) task.createDynamicElement(
                        "externally-managed-fields");
            
            for (int index = 0; index < count; index++)
            {
                field = array[index];

                if  (field != null)
                {
                    fieldElement = 
                        (AntFieldElement) element.createDynamicElement("field");
                
                    fieldElement.setDynamicAttribute("name", field.getName());

                    fieldElement.setDynamicAttribute(
                        "table-name", field.getTableName());

                    fieldElement.setDynamicAttribute(
                        "keyword", field.getKeyword());

                    fieldElement.setDynamicAttribute(
                        "retrieval-query", field.getRetrievalQuery());
                }
            }
        }
    }
    
    /**
     * Builds the table list.
     * @param task the task.
     */
    protected void buildTables(final QueryJTask task)
    {
        Table[] array = getTables();
        Table table;
        AntTablesElement element;
        AntTableElement tableElement;
        List fields;
        int fieldCount;
        Field field;
        AntFieldElement fieldElement;

        int count = (array == null) ? 0 : array.length;

        if  (count > 0)
        {
            element = 
                (AntTablesElement) task.createDynamicElement("tables");
            
            for (int index = 0; index < count; index++)
            {
                table = array[index];

                tableElement = 
                    (AntTableElement) element.createDynamicElement("table");
                
                tableElement.setDynamicAttribute(
                        "name", table.getName());
                
                fields = table.getFields();

                fieldCount = (fields == null) ? 0 : fields.size();

                for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
                {
                    field = (Field) fields.get(fieldIndex);

                    if  (field != null)
                    {
                        fieldElement = 
                            (AntFieldElement) tableElement.createDynamicElement("field");
                        fieldElement.setDynamicAttribute(
                            "name", field.getName());
                        fieldElement.setDynamicAttribute(
                            "type", field.getType());
                        fieldElement.setDynamicAttribute(
                            "pk", field.getPk());
                    }
                }
            }
        }
    }
}
