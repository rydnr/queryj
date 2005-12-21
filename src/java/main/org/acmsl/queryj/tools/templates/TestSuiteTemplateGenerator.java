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
 * Description: Is able to generate the JUnit suite to execute the generated
 *              test cases.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.TestSuiteTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate the JUnit suite to execute the generated test cases.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TestSuiteTemplateGenerator
    implements  TestSuiteTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TestSuiteTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final TestSuiteTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a TestSuiteTemplateGenerator instance.
     * @return such instance.
     */
    public static TestSuiteTemplateGenerator getInstance()
    {
        TestSuiteTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TestSuiteTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new TestSuiteTemplateGenerator();

            setReference(result);
        }

        return result;
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param useSubfolders whether to use subfolders.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     */
    public TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final boolean useSubfolders)
    {
        return
            createTestSuiteTemplate(
                packageName,
                suiteName,
                useSubfolders,
                PackageUtils.getInstance());
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param useSubfolders whether to use subfolders.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     * @precondition packageUtils != null
     */
    protected TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final boolean useSubfolders,
        final PackageUtils packageUtils)
    {
        return
            new TestSuiteTemplate(
                packageName,
                packageUtils.retrieveBaseTestSuitePackage(
                    packageName, useSubfolders),
                suiteName);
    }

    /**
     * Writes a test suite template to disk.
     * @param testSuiteTemplate the suite template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition testSuiteTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final TestSuiteTemplate testSuiteTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            testSuiteTemplate,
            outputDir,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a test suite template to disk.
     * @param testSuiteTemplate the suite template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition testSuiteTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final TestSuiteTemplate testSuiteTemplate,
        final File outputDir,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + testSuiteTemplate.getSuiteName()
            + "Suite.java",
            testSuiteTemplate.generate());
    }
}
