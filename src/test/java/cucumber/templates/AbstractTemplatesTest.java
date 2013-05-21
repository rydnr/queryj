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
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
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
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.engines.JdbcMetadataManager;
import org.acmsl.queryj.metadata.vo.Table;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

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
    protected final Map<String, G> GENERATOR_MAPPINGS = new HashMap<String, G>();

    /**
     * A simple mapping between template names and factories.
     */
    protected final Map<String, F> FACTORY_MAPPINGS = new HashMap<String, F>();

    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    /**
     * The output file.
     */
    private Map<String, File> m__mOutputFiles;

    /**
     * Creates an empty instance.
     */
    protected AbstractTemplatesTest()
    {
        immutableSetOutputFiles(new HashMap<String, File>());
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
     * Retrieves an empty {@link CustomSqlProvider} instance.
     * @return such instance.
     */
    @NotNull
    protected CustomSqlProvider retrieveCustomSqlProvider()
    {
        return new SqlXmlParserImpl(new ByteArrayInputStream("".getBytes()));
    }

    /**
     * Retrieves a {@link CustomSqlProvider} instance adapted for given result.
     * @param customResult the {@link Result}.
     * @param properties the {@link Property properties}.
     * @return such instance.
     */
    @NotNull
    protected CustomSqlProvider retrieveCustomSqlProvider(
        @NotNull final Result customResult, @NotNull final List<Property> properties)
    {
        return
            new SqlXmlParserImpl(new ByteArrayInputStream("".getBytes()))
            {
                @Override
                @NotNull
                public SqlResultDAO getSqlResultDAO()
                {
                    return
                        new SqlResultDAO()
                        {
                            @Nullable
                            @Override
                            public Result findByPrimaryKey(@NotNull final String id)
                            {
                                @Nullable Result result = null;

                                if (id.equals(customResult.getId()))
                                {
                                    result = customResult;
                                }

                                return result;
                            }

                            @Nullable
                            @Override
                            public Result findSingleMatch(@NotNull final String table)
                            {
                                return null;
                            }

                            @Nullable
                            @Override
                            public Result findMultipleMatch(@NotNull final String table)
                            {
                                return null;
                            }

                            @Nullable
                            @Override
                            public Result findBySqlId(@NotNull final String sqlId)
                            {
                                return null;
                            }

                            @NotNull
                            @Override
                            public List<Result> findByType(@NotNull final String type)
                            {
                                @NotNull final List<Result> result = new ArrayList<Result>(1);

                                if (type.equals(customResult.getClassValue()))
                                {
                                    result.add(customResult);
                                }

                                return result;
                            }

                            @NotNull
                            @Override
                            public List<Result> findAll()
                            {
                                @NotNull final List<Result> result = new ArrayList<Result>(1);

                                result.add(customResult);

                                return result;
                            }
                        };
                }

                @Override
                @NotNull
                public SqlPropertyDAO getSqlPropertyDAO()
                {
                    return
                        new SqlPropertyDAO()
                        {
                            /**
                             * Retrieves the {@link org.acmsl.queryj.customsql.Property} associated to given id.
                             *
                             * @param id the parameter id.
                             * @return the {@link org.acmsl.queryj.customsql.Property}, or <code>null</code> if
                             * not found.
                             */
                            @Nullable
                            @Override
                            public Property findByPrimaryKey(@NotNull final String id)
                            {
                                @Nullable Property result = null;

                                for (@NotNull final Property property : properties)
                                {
                                    if (id.equals(property.getId()))
                                    {
                                        result = property;
                                        break;
                                    }
                                }

                                return result;
                            }

                            /**
                             * Retrieves all {@link org.acmsl.queryj.customsql.Parameter parameters} used in given
                             * {@link org.acmsl.queryj.customsql.Result}.
                             *
                             * @param resultId the {@link org.acmsl.queryj.customsql.Result} identifier.
                             * @return the list of properties associated to given {@link org.acmsl.queryj.customsql.Result}.
                             */
                            @NotNull
                            @Override
                            public List<Property> findByResult(@NotNull final String resultId)
                            {
                                @NotNull List<Property> result = properties;

                                if (!resultId.equals(customResult.getId()))
                                {
                                    result = new ArrayList<Property>(0);
                                }

                                return result;
                            }

                            /**
                             * Inserts a new property.
                             * @param id         the property id.
                             * @param columnName the column name.
                             * @param index      the property index.
                             * @param type       the type.
                             * @param nullable   whether it allows null or not.
                             */
                            @Override
                            public void insert(@NotNull final String id, @NotNull final String columnName, final int index, @NotNull final String type, final boolean nullable)
                            {
                                properties.add(new PropertyElement(id, columnName, index, type, nullable));
                            }
                        };
                }
            };
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
            FileUtils.getInstance().copyIfPossible(outputFile, new File(outputFile.getName()));

            if (   (outputFile.getAbsolutePath().endsWith(outputName))
                && (isJava(outputFile)))
            {
                @Nullable org.acmsl.queryj.tools.antlr.JavaLexer t_Lexer = null;

                try
                {
                    t_Lexer =
                        new org.acmsl.queryj.tools.antlr.JavaLexer(new ANTLRFileStream(outputFile.getAbsolutePath()));
                }
                catch (final IOException missingFile)
                {
                    Assert.fail("Lexer error: " + missingFile.getMessage());
                }

                @NotNull final CommonTokenStream t_Tokens =
                    new CommonTokenStream(t_Lexer);

                @NotNull final org.acmsl.queryj.tools.antlr.JavaParser
                    t_Parser = new org.acmsl.queryj.tools.antlr.JavaParser(t_Tokens);

                try
                {
                    t_Parser.compilationUnit();
                }
                catch (@NotNull final Throwable invalidClass)
                {
                    Assert.fail("Parser error: " + invalidClass.getMessage());
                }

                Assert.assertNotNull(
                    "Missing package in file " + outputFile.getAbsolutePath(), t_Parser.getPackageName());
                Assert.assertEquals(
                    "Invalid package in file " + outputFile.getAbsolutePath(),
                    "com.foo.bar.dao",
                    t_Parser.getPackageName());

                Assert.assertNotNull("Missing class in file " + outputFile.getAbsolutePath(), t_Parser.getRootClass());
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

        if (exceptionToThrow != null)
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
            FileUtils.getInstance().copyIfPossible(outputFile, new File(outputFile.getName()));

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

    @NotNull
    @Override
    public String toString()
    {
        return "AbstractTemplatesTest{}";
    }
}
