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
 * Filename: TestSuiteTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the JUnit suite to execute the generated
 *              test cases.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TestSuiteTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate the JUnit suite to execute the generated test cases.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TestSuiteTemplateGenerator
    implements  TestSuiteTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TestSuiteTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TestSuiteTemplateGenerator SINGLETON =
            new TestSuiteTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TestSuiteTemplateGenerator() {};

    /**
     * Retrieves a {@link TestSuiteTemplateGenerator} instance.
     * @return such instance.
     */
    public static TestSuiteTemplateGenerator getInstance()
    {
        return TestSuiteTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param header the header.
     * @param useSubfolders whether to use subfolders.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     */
    public TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final String header,
        final boolean useSubfolders)
    {
        return
            createTestSuiteTemplate(
                packageName,
                suiteName,
                header,
                useSubfolders,
                PackageUtils.getInstance());
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param header the header.
     * @param useSubfolders whether to use subfolders.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     * @precondition packageUtils != null
     */
    protected TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final String header,
        final boolean useSubfolders,
        final PackageUtils packageUtils)
    {
        return
            new TestSuiteTemplate(
                header,
                getDecoratorFactory(),
                packageName,
                packageUtils.retrieveBaseTestSuitePackage(
                    packageName, useSubfolders),
                suiteName);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return CachingDecoratorFactory.getInstance();
    }

    /**
     * Writes a test suite template to disk.
     * @param testSuiteTemplate the suite template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @throws IOException if the file cannot be created.
     * @precondition testSuiteTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final TestSuiteTemplate testSuiteTemplate,
        final File outputDir,
        final Charset charset)
      throws  IOException
    {
        write(
            testSuiteTemplate,
            outputDir,
            charset,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a test suite template to disk.
     * @param testSuiteTemplate the suite template to write.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param stringUtils the {@link StringUtils} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition testSuiteTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final TestSuiteTemplate testSuiteTemplate,
        final File outputDir,
        final Charset charset,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        boolean folderCreated = outputDir.mkdirs();

        if (   (!folderCreated)
            && (!outputDir.exists()))
        {
            throw
                new IOException("Cannot create output dir: " + outputDir);
        }
        else
        {
            fileUtils.writeFile(
                  outputDir.getAbsolutePath()
                + File.separator
                + testSuiteTemplate.getSuiteName()
                + "Suite.java",
                testSuiteTemplate.generate(),
                charset);
        }
    }
}
