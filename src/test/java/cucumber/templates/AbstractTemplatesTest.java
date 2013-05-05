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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Retrieves an empty {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     * @return such instance.
     */
    @NotNull
    protected CustomSqlProvider retrieveCustomSqlProvider()
    {
        return new SqlXmlParserImpl(new ByteArrayInputStream("".getBytes()));
    }

    /**
     * Checks the generated files compile.
     * @param outputName the name of the output file.
     * @param outputFiles the output files.
     */
    protected void checkGeneratedFilesCompile(
        @NotNull final String outputName, @NotNull final Map<String, File> outputFiles)
    {
        for (@NotNull File outputFile : outputFiles.values())
        {
            FileUtils.getInstance().copyIfPossible(outputFile, new File(outputFile.getName()));

            @Nullable org.acmsl.queryj.tools.antlr.JavaLexer t_Lexer = null;

            try
            {
                t_Lexer =
                    new org.acmsl.queryj.tools.antlr.JavaLexer(new ANTLRFileStream(outputFile.getAbsolutePath()));
            }
            catch (final IOException missingFile)
            {
                Assert.fail(missingFile.getMessage());
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
                Assert.fail(invalidClass.getMessage());
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
     * @param table the {@link org.acmsl.queryj.metadata.vo.Table}.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager retrieveMetadataManager(@NotNull final String engineName, @NotNull final String table)
    {
        @NotNull final List<String> tableNames = new ArrayList<String>(1);
        tableNames.add(table);
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

}
