/*
                        QueryJ Core

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
 * Date: 2014/03/27
 * Time: 21:04
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;

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
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link AbstractTemplateContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/27 21:04
 */
@ThreadSafe
@RunWith(JUnit4.class)
public class AbstractTemplateContextTest
{
    /**
     * Tests the MetadataManager is available (survives refactorings).
     */
    @Test
    public void metadataManager_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertNotNull(instance.getMetadataManager());
    }

    /**
     * Tests the CustomSqlProvider is available (survives refactorings).
     */
    @Test
    public void customSqlProvider_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertNotNull(instance.getCustomSqlProvider());
    }

    /**
     * Tests the header setting is available (survives refactorings).
     */
    @Test
    public void header_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("header", instance.getHeader());
    }

    /**
     * Tests the DecoratorFactory is available (survives refactorings).
     */
    @Test
    public void decoratorFactory_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertNotNull(instance.getDecoratorFactory());
    }

    /**
     * Tests the packageName setting is available (survives refactorings).
     */
    @Test
    public void packageName_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("package.name", instance.getPackageName());
    }

    /**
     * Tests the base package name is available (survives refactorings).
     */
    @Test
    public void basePackageName_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("base.package.name", instance.getBasePackageName());
    }

    /**
     * Tests the repository name is available (survives refactorings).
     */
    @Test
    public void repositoryName_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("repository.name", instance.getRepositoryName());
    }

    /**
     * Tests the "implementMarkerInterfaces" setting is available (survives refactorings).
     */
    @Test
    public void implementMarkerInterfaces_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertFalse(instance.getImplementMarkerInterfaces());
    }

    /**
     * Tests the "jmx" setting is available (survives refactorings).
     */
    @Test
    public void jmx_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertFalse(instance.getImplementMarkerInterfaces());
    }

    /**
     * Tests the JNDI location is available (survives refactorings).
     */
    @Test
    public void jndiLocation_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("jndi:/location", instance.getJndiLocation());
    }

    /**
     * Tests the "disableGenerationTimestamps" setting is available (survives refactorings).
     */
    @Test
    public void disableGenerationTimestamps_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertTrue(instance.getDisableGenerationTimestamps());
    }

    /**
     * Tests the "disableNotNullAnnotations" setting is available (survives refactorings).
     */
    @Test
    public void disableNotNullAnnotations_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertTrue(instance.getDisableNotNullAnnotations());
    }

    /**
     * Tests the "disableCheckthreadAnnotations" setting is available (survives refactorings).
     */
    @Test
    public void disableCheckthreadAnnotations_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertTrue(instance.getDisableCheckthreadAnnotations());
    }

    /**
     * Tests the file name is available (survives refactorings).
     */
    @Test
    public void fileName_is_available()
    {
        @NotNull final AbstractTemplateContext instance = createContext();

        Assert.assertEquals("file.name", instance.getFileName());
    }

    /**
     * Retrieves an {@link AbstractTemplateContext}.
     * @return such context.
     */
    @NotNull
    protected AbstractTemplateContext createContext()
    {
        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        @Nullable final String header = "header";
        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);
        @NotNull final String packageName = "package.name";
        @NotNull final String basePackageName = "base.package.name";
        @NotNull final String repositoryName = "repository.name";
        final boolean implementMarkerInterfaces = false;
        final boolean jmx = false;
        @NotNull final String jndiLocation = "jndi:/location";
        final boolean disableGenerationTimestamps = true;
        final boolean disableNotNullAnnotations = true;
        final boolean disableCheckthreadAnnotations = true;
        @NotNull final String fileName = "file.name";

        return
            new AbstractTemplateContext(
                metadataManager,
                customSqlProvider,
                header,
                decoratorFactory,
                packageName,
                basePackageName,
                repositoryName,
                implementMarkerInterfaces,
                jmx,
                jndiLocation,
                disableGenerationTimestamps,
                disableNotNullAnnotations,
                disableCheckthreadAnnotations,
                fileName)
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public String getTemplateName()
                {
                    return "";
                }
            };
    }
}
