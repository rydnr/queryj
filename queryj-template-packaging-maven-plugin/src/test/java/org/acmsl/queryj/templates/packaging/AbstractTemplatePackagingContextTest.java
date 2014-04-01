/*
                        QueryJ Template Packaging Plugin

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
 * Filename: AbstractTemplateContextTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractTemplateContext.
 *
 * Date: 2014/03/30
 * Time: 20:33
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.AbstractTemplateContext;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.io.File;

/**
 * Tests for {@link AbstractTemplatePackagingContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 20:33
 */
@ThreadSafe
@RunWith(JUnit4.class)
public class AbstractTemplatePackagingContextTest
{
    /**
     * Tests the TemplateName is available (survives refactorings).
     */
    @Test
    public void templateName_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getTemplateName());
    }

    /**
     * Tests the FileName is available (survives refactorings).
     */
    @Test
    public void fileName_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getFileName());
    }

    /**
     * Tests the PackageName is available (survives refactorings).
     */
    @Test
    public void packageName_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getPackageName());
    }

    /**
     * Tests the RootDir is available (survives refactorings).
     */
    @Test
    public void rootDir_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getRootDir());
    }

    /**
     * Tests the OutputDir is available (survives refactorings).
     */
    @Test
    public void outputDir_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getOutputDir());
    }

    /**
     * Tests the JDBC driver is available (survives refactorings).
     */
    @Test
    public void jdbcDriver_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getJdbcDriver());
    }

    /**
     * Tests the JDBC URL is available (survives refactorings).
     */
    @Test
    public void jdbcUrl_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getJdbcUrl());
    }

    /**
     * Tests the JDBC username is available (survives refactorings).
     */
    @Test
    public void jdbcUsername_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getJdbcUsername());
    }

    /**
     * Tests the JDBC password is available (survives refactorings).
     */
    @Test
    public void jdbcPassword_is_available()
    {
        @NotNull final AbstractTemplatePackagingContext instance = createContext();

        Assert.assertNotNull(instance.getJdbcPassword());
    }

    /**
     * Retrieves an {@link AbstractTemplateContext}.
     * @return such context.
     */
    @NotNull
    protected AbstractTemplatePackagingContext createContext()
    {
        @NotNull final QueryJCommand t_Command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration(), null);

        @NotNull final AbstractTemplatePackagingContext result =
            new AbstractTemplatePackagingContext(t_Command) {};

        @Nullable final String templateName = "template";
        result.immutableSetValue(result.buildTemplateNameKey(), templateName, t_Command);

        @NotNull final String fileName = "file.name";
        result.immutableSetValue(result.buildFileNameKey(), fileName, t_Command);

        @NotNull final String packageName = "package.name";
        result.immutableSetValue(result.buildPackageNameKey(), packageName, t_Command);

        @NotNull final File rootDir = new File(".");
        result.immutableSetValue(result.buildRootDirKey(), rootDir, t_Command);

        @NotNull final File outputDir = new File("target");
        result.immutableSetValue(result.buildOutputDirKey(), outputDir, t_Command);

        @NotNull final String jdbcDriver = "jdbc.driver";
        result.immutableSetValue(result.buildJdbcDriverKey(), jdbcDriver, t_Command);

        @NotNull final String jdbcUrl = "jdbc.url";
        result.immutableSetValue(result.buildJdbcUrlKey(), jdbcUrl, t_Command);

        @NotNull final String jdbcUsername = "jdbc.username";
        result.immutableSetValue(result.buildJdbcUserNameKey(), jdbcUsername, t_Command);

        @NotNull final String jdbcPassword = "jdbc.password";
        result.immutableSetValue(result.buildJdbcPasswordKey(), jdbcPassword, t_Command);

        return result;
    }
}
