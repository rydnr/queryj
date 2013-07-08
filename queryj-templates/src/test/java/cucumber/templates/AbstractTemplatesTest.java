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
 * Filename: AbstractTemplatesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Generic stuff for template tests.
 *
 * Date: 2013/05/05
 * Time: 17:25
 *
 */
package cucumber.templates;

/*
 * Importing project classes.
 */
import cucumber.templates.sql.CucumberSqlDAO;
import cucumber.templates.sql.CucumberSqlParameterDAO;
import org.acmsl.queryj.templates.antlr.JavaLexer;
import org.acmsl.queryj.templates.antlr.JavaPackageVisitor;
import org.acmsl.queryj.templates.antlr.JavaParser;
import org.acmsl.queryj.templates.antlr.JavaRootClassNameVisitor;
import org.acmsl.queryj.templates.antlr.JavaVisitor;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.xml.SqlXmlParserImpl;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing ACMSL-Commons classes.
 */
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing ANTLR classes.
 */
import org.acmsl.queryj.metadata.MetadataExtractionLogger;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/*
 * Importing JDK classes.
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Generic stuff for template tests.
 * @author <a href="chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/05
 */
public abstract class AbstractTemplatesTest<G, F>
{
    /**
     * A simple mapping between template names and generators.
     */
    @NotNull protected final Map<String, G> GENERATOR_MAPPINGS = new HashMap<String, G>();

    /**
     * A simple mapping between template names and factories.
     */
    @NotNull protected final Map<String, F> FACTORY_MAPPINGS = new HashMap<String, F>();

    /**
     * The package name.
     */
    public static final String PACKAGE_NAME = "com.foo.bar";

    /**
     * The DAO package name.
     */
    public static final String DAO_PACKAGE_NAME = "com.foo.bar.dao";

    /**
     * A temporary folder for generated files.
     */
    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    /**
     * The output file.
     */
    private Map<String, File> m__mOutputFiles;

    /**
     * The tables.
     */
    private Map<String, Table> m__mTables;

    /**
     * The foreign keys.
     */
    private List<ForeignKey> m__lForeignKeys;

    /**
     * The list of SQL queries.
     */
    private List<Sql> m__lSql;

    /**
     * The SQL query parameters.
     */
    private Map<String, List<Parameter>> m__mParameters;

    /**
     * Creates an empty instance.
     */
    protected AbstractTemplatesTest()
    {
        immutableSetOutputFiles(new HashMap<String, File>());
        immutableSetTables(new HashMap<String, Table>());
        immutableSetForeignKeys(new ArrayList<ForeignKey>());
        immutableSetSqlList(new ArrayList<Sql>());
    }

    /**
     * Specifies the output file.
     * @param files such files.
     */
    @SuppressWarnings("unused")
    protected void immutableSetOutputFiles(@NotNull final Map<String, File> files)
    {
        m__mOutputFiles = files;
    }

    /**
     * Specifies the output files.
     * @param files such files.
     */
    @SuppressWarnings("unused")
    protected void setOutputFiles(@NotNull final Map<String, File> files)
    {
        immutableSetOutputFiles(files);
    }

    /**
     * Retrieves the output file.
     * @return such file.
     */
    public Map<String, File> getOutputFiles()
    {
        return m__mOutputFiles;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    protected final void immutableSetTables(@NotNull final Map<String, Table> tables)
    {
        m__mTables = tables;
    }

    /**
     * Specifies the tables.
     * @param tables the tables.
     */
    @SuppressWarnings("unused")
    protected void setTables(@NotNull final Map<String, Table> tables)
    {
        immutableSetTables(tables);
    }

    /**
     * Retrieves the tables.
     * @return such information.
     */
    @NotNull
    protected Map<String, Table> getTables()
    {
        return m__mTables;
    }

    /**
     * Specifies the foreign keys.
     * @param foreignKeys the foreign keys.
     */
    protected final void immutableSetForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        m__lForeignKeys = foreignKeys;
    }

    /**
     * Specifies the foreign keys.
     * @param foreignKeys the foreign keys.
     */
    @SuppressWarnings("unused")
    protected void setForeignKeys(@NotNull final List<ForeignKey> foreignKeys)
    {
        immutableSetForeignKeys(foreignKeys);
    }

    @NotNull
    protected List<ForeignKey> getForeignKeys()
    {
        return m__lForeignKeys;
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    protected final void immutableSetSqlList(@NotNull final List<Sql> sqlList)
    {
        this.m__lSql = sqlList;
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    @SuppressWarnings("unused")
    protected void setSqlList(@NotNull final List<Sql> sqlList)
    {
        immutableSetSqlList(sqlList);
    }

    /**
     * Retrieves the list of {@link Sql}.
     * @return such table.
     */
    @SuppressWarnings("unused")
    protected List<Sql> getSqlList()
    {
        return this.m__lSql;
    }

    /**
     * Specifies the SQL queries' parameters.
     * @param parameters such map.
     */
    protected final void immutableSetParameters(@NotNull final Map<String, List<Parameter>> parameters)
    {
        this.m__mParameters = parameters;
    }

    /**
     * Specifies the SQL queries' parameters.
     * @param parameters such map.
     */
    protected void setParameters(@NotNull final Map<String, List<Parameter>> parameters)
    {
        immutableSetParameters(parameters);
    }

    /**
     * Retrieves the SQL queries parameters.
     * @return such parameters.
     */
    @SuppressWarnings("unused")
    protected final Map<String, List<Parameter>> getParameters()
    {
        return m__mParameters;
    }

    /**
     * Retrieves a {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance adapted for given result.
     * @param sqlList the list of {@link Sql}.
     * @param parameters the {@link Parameter} map.
     * @return such instance.
     */
    @NotNull
    protected CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final List<Sql> sqlList,
        @NotNull final Map<String, List<Parameter>> parameters)
    {
        return
            new SqlXmlParserImpl(new ByteArrayInputStream("".getBytes()))
            {
                @Override
                @NotNull
                public SqlDAO getSqlDAO()
                {
                    return new CucumberSqlDAO(sqlList);
                }

                /**
                 * Retrieves the {@link org.acmsl.queryj.metadata.SqlParameterDAO} instance.
                 *
                 * @return such instance.
                 */
                @NotNull
                @Override
                public SqlParameterDAO getSqlParameterDAO()
                {
                    return new CucumberSqlParameterDAO(parameters);
                }
            };
    }

    /**
     * Sets up the Java parser.
     * @param javaFile the Java contents to parse.
     * @return the {@link JavaParser} instance.
     * @throws RecognitionException if the comment cannot be parsed.
     * @throws IOException if the file could not be read.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected JavaParser setUpParser(@NotNull final File javaFile)
        throws RecognitionException,
               IOException
    {
        @NotNull final JavaParser result;

        @NotNull final JavaLexer t_Lexer =
            new JavaLexer(new ANTLRFileStream(javaFile.getAbsolutePath()));

        @NotNull final CommonTokenStream t_Tokens = new CommonTokenStream(t_Lexer);

        result = new JavaParser(t_Tokens);

        return result;
    }

    /**
     * Checks the generated files compile.
     * @param outputName the name of the output file.
     * @param outputFiles the output files.
     */
    protected void checkGeneratedFilesCompile(
        @NotNull final String outputName, @NotNull final Map<String, File> outputFiles)
    {
        for (@NotNull final File outputFile : outputFiles.values())
        {
            FileUtils.getInstance().copyIfPossible(
                outputFile, new File(outputFile.getName()), Charset.defaultCharset());

            if (   (outputFile.getAbsolutePath().endsWith(outputName))
                && (isJava(outputFile)))
            {
                @Nullable JavaParser t_Parser = null;

                try
                {
                    t_Parser = setUpParser(outputFile);
                }
                catch (final IOException missingFile)
                {
                    Assert.fail("Missing file: " + missingFile.getMessage());
                }

                @Nullable ParseTree tree = null;

                try
                {
                    tree = t_Parser.compilationUnit();
                }
                catch (@NotNull final Throwable invalidClass)
                {
                    Assert.fail("Parser error: " + invalidClass.getMessage());
                }

                @NotNull final JavaVisitor<String> packageVisitor = new JavaPackageVisitor();

                @Nullable String packageName = null;

                try
                {
                    packageName = packageVisitor.visit(tree);
                }
                catch (@NotNull final Throwable invalidClass)
                {
                    Assert.fail("Parser error: " + invalidClass.getMessage());
                }

                Assert.assertNotNull(
                    "Missing package in file " + outputFile.getAbsolutePath(), packageName);
                Assert.assertEquals(
                    "Invalid package in file " + outputFile.getAbsolutePath(),
                    "com.foo.bar.dao",
                    packageName);

                @NotNull final JavaVisitor<String> rootClassVisitor = new JavaRootClassNameVisitor();

                @Nullable String rootClass = null;

                try
                {
                    rootClass = rootClassVisitor.visit(tree);
                }
                catch (@NotNull final Throwable invalidClass)
                {
                    Assert.fail("Parser error: " + invalidClass.getMessage());
                }

                Assert.assertNotNull("Missing class in file " + outputFile.getAbsolutePath(), rootClass);
            }
        }
    }

    /**
     * Checks if the output file is Java or not.
     * @param outputFile the output file.
     * @return <code>true</code> in such case.
     */
    protected boolean isJava(@NotNull final File outputFile)
    {
        return outputFile.getAbsolutePath().endsWith(".java");
    }

    /**
     * Checks if the output file is a properties or not.
     * @param outputFile the output file.
     * @return <code>true</code> in such case.
     */
    protected boolean isProperties(@NotNull final File outputFile)
    {
        return outputFile.getAbsolutePath().endsWith(".properties");
    }

    /**
     * Creates a temporary directory.
     * @param prefix the prefix to use (optional).
     * @return the temporary folder.
     * @throws java.io.IOException if the folder cannot be created.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected File createTempFolder(@Nullable final String prefix)
        throws IOException
    {
        @Nullable File result = null;

        @NotNull final String folderPrefix = (prefix != null) ? prefix : "cucumber";

        @Nullable IOException exceptionToThrow = null;

        try
        {
            result = File.createTempFile(folderPrefix, Long.toString(System.nanoTime()));
        }
        catch (@NotNull final IOException ioException)
        {
            exceptionToThrow = ioException;
        }

        if (   (exceptionToThrow == null)
            && (result != null)
            && (!result.delete()))
        {
            exceptionToThrow =
                new IOException("Could not delete temporary file: " + result.getAbsolutePath());
        }

        if (   (exceptionToThrow == null)
            && (result != null)
            && (!result.mkdirs()))
        {
            exceptionToThrow =
                new IOException("Could not create temporary file: " + result.getAbsolutePath());
        }

        if (   (exceptionToThrow != null)
            || (result == null))
        {
            throw exceptionToThrow;
        }

        return result;
    }


    /**
     * Retrieves the template generator from given template name.
     * @param template the template.
     * @return such generator.
     */
    @Nullable
    protected G retrieveTemplateGenerator(
        @NotNull final String template)
    {
        return GENERATOR_MAPPINGS.get(template);
    }

    /**
     * Retrieves the template from given template name.
     * @param template the template.
     * @return such template.
     */
    @Nullable
    protected F retrieveTemplateFactory(@NotNull final String template)
    {
        return FACTORY_MAPPINGS.get(template);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.metadata.DecoratorFactory} instance using given generator.
     * @param generator the generator to use.
     * @return the decorator factory.
     */
    @NotNull
    protected abstract DecoratorFactory retrieveDecoratorFactory(@NotNull final G generator);

    /**
     * Retrieves a {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param engineName the name of the engine.
     * @param table the {@link org.acmsl.queryj.metadata.vo.Table}.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName, @NotNull final Table table)
    {
        @NotNull final List<String> tableNames = new ArrayList<String>(1);
        tableNames.add(table.getName());
        @NotNull final List<Table> tables = new ArrayList<Table>(1);
        tables.add(table);

        return retrieveMetadataManager(engineName, tableNames, tables);
    }

    /**
     * Retrieves a {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param engineName the name of the engine.
     * @param table the {@link Table}.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName, @NotNull final String table)
    {
        @NotNull final List<String> tableNames = new ArrayList<String>(1);
        tableNames.add(table);
        @NotNull final List<Table> tables = new ArrayList<Table>(0);

        return retrieveMetadataManager(engineName, tableNames, tables);
    }

    /**
     * Retrieves a {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param engineName the name of the engine.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName)
    {
        @NotNull final List<String> tableNames = new ArrayList<String>(0);
        @NotNull final List<Table> tables = new ArrayList<Table>(0);

        return retrieveMetadataManager(engineName, tableNames, tables);
    }

    /**
     * Retrieves a {@link MetadataManager} instance.
     * @param engineName the name of the engine.
     * @param tableNames the table names.
     * @param tables the tables.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(
        @NotNull final String engineName, @NotNull final List<String> tableNames, @NotNull final List<Table> tables)
    {
        return
            new JdbcMetadataManager(
                "fake manager",
                null, // database metadata
                new MetadataExtractionLogger(), // extraction listener
                "catalog",
                "schema",
                tableNames,
                tables,
                true, // disable table extraction
                true, // lazy table extraction
                false, // case sensitive
                engineName, // engine
                "11", // engine version
                "'"); // quote
    }

    /**
     * Checks the generated properties files are valid.
     * @param outputName the name of the output file.
     * @param outputFiles the output files.
     */
    protected void checkPropertiesFiles(
        @NotNull final String outputName, @NotNull final Map<String, File> outputFiles)
    {
        for (@NotNull final File outputFile : outputFiles.values())
        {
            FileUtils.getInstance().copyIfPossible(outputFile, new File(outputFile.getName()), Charset.defaultCharset());

            if (   (outputFile.getAbsolutePath().endsWith(outputName))
                && (isProperties(outputFile)))
            {
                final Properties properties = new Properties();

                FileInputStream stream = null;

                try
                {
                    stream = new FileInputStream(outputFile);
                    properties.load(stream);
                }
                catch (final IOException e)
                {
                    Assert.fail("Cannot read file: " + e.getMessage());
                }
                finally
                {
                    if (stream != null)
                    {
                        try
                        {
                            stream.close();
                        }
                        catch (IOException e)
                        {
                            Assert.fail("Cannot read file: " +  e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieves the output file matching the name given.
     * @param fileName the file name.
     * @return the output file.
     */
    @Nullable
    protected File retrieveOutputFile(@NotNull final String fileName)
    {
        @Nullable File result = null;

        for (@NotNull final File file : getOutputFiles().values())
        {
            if (file.getAbsolutePath().endsWith(fileName))
            {
                result = file;
                break;
            }
        }

        return result;
    }

    /**
     * Reads a properties file.
     * @param file the name of the properties file.
     * @return the properties.
     */
    @Nullable
    protected Properties readPropertiesFile(@NotNull final File file)
    {
        Properties result = null;

        FileInputStream stream = null;
        try
        {
            stream = new FileInputStream(file);
            result = new Properties();
            result.load(stream);
        }
        catch (final IOException e)
        {
            Assert.fail("Cannot read file: " + e.getMessage());
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    stream.close();
                }
                catch (IOException e)
                {
                    Assert.fail("Cannot read file: " +  e.getMessage());
                }
            }
        }

        return result;
    }

    @Override
    public String toString()
    {
        @NotNull final StringBuilder result =
            new StringBuilder("AbstractTemplatesTest{ FACTORY_MAPPINGS=");

        result.append(FACTORY_MAPPINGS);
        result.append(", GENERATOR_MAPPINGS=");
        result.append(GENERATOR_MAPPINGS);
        result.append(", rootFolder=");
        result.append(rootFolder);
        result.append(", outputFiles=");
        result.append(m__mOutputFiles);
        result.append(", tables=");
        result.append(m__mTables);
        result.append(", foreignKeys=");
        result.append(m__lForeignKeys);
        result.append(", sqlList=");
        result.append(m__lSql);
        result.append(", sqlParameters=");
        result.append(m__mParameters);
        result.append("}");

        return result.toString();
    }
}
