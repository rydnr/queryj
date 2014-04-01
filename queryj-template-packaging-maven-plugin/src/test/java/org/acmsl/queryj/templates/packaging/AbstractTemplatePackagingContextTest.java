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

        @NotNull final String templateName = "template";
        @NotNull final String fileName = "file.name";
        @NotNull final String packageName = "package.name";
        @NotNull final File rootDir = new File(".");
        @NotNull final File outputDir = new File("target");
        @NotNull final String jdbcDriver = "jdbc.driver";
        @NotNull final String jdbcUrl = "jdbc.url";
        @NotNull final String jdbcUsername = "jdbc.username";
        @NotNull final String jdbcPassword = "jdbc.password";

        @NotNull final AbstractTemplatePackagingContext result =
            new AbstractTemplatePackagingContext(t_Command)
            {{
                immutableSetValue(buildTemplateNameKey(), templateName, t_Command);
                immutableSetValue(buildFileNameKey(), fileName, t_Command);
                immutableSetValue(buildPackageNameKey(), packageName, t_Command);
                immutableSetValue(buildRootDirKey(), rootDir, t_Command);
                immutableSetValue(buildOutputDirKey(), outputDir, t_Command);
                immutableSetValue(buildJdbcDriverKey(), jdbcDriver, t_Command);
                immutableSetValue(buildJdbcUrlKey(), jdbcUrl, t_Command);
                immutableSetValue(buildJdbcUserNameKey(), jdbcUsername, t_Command);
                immutableSetValue(buildJdbcPasswordKey(), jdbcPassword, t_Command);
            }};

        return result;
    }
}
