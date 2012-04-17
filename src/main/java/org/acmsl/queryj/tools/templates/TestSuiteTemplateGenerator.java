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
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to generate the JUnit suite to execute the generated test cases.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TestSuiteTemplateGenerator<T extends TestSuiteTemplate>
    extends  AbstractTemplateGenerator<T>
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
    protected TestSuiteTemplateGenerator() {}

    /**
     * Retrieves a {@link TestSuiteTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static TestSuiteTemplateGenerator getInstance()
    {
        return TestSuiteTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param header the header.
     * @param useSubFolders whether to use sub folders.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     */
    @NotNull
    public TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final String header,
        final boolean useSubFolders)
    {
        return
            createTestSuiteTemplate(
                packageName,
                suiteName,
                header,
                useSubFolders,
                PackageUtils.getInstance());
    }

    /**
     * Generates a test suite template.
     * @param packageName the package name.
     * @param suiteName the suite name.
     * @param header the header.
     * @param useSubFolders whether to use sub folders.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return a template.
     * @precondition packageName != null
     * @precondition suiteName != null
     * @precondition packageUtils != null
     */
    @NotNull
    protected TestSuiteTemplate createTestSuiteTemplate(
        final String packageName,
        final String suiteName,
        final String header,
        final boolean useSubFolders,
        @NotNull final PackageUtils packageUtils)
    {
        return
            new TestSuiteTemplate(
                header,
                getDecoratorFactory(),
                packageName,
                packageUtils.retrieveBaseTestSuitePackage(
                    packageName, useSubFolders),
                suiteName);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return template.getSuiteName() + "Suite.java";
    }
}
