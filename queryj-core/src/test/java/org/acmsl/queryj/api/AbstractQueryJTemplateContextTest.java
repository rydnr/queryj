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
 * Filename: AbstractQueryJTemplateContextTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractQueryJTemplateContext.
 *
 * Date: 2014/03/27
 * Time: 21:04
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

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
 * Tests for {@link AbstractQueryJTemplateContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/27 21:04
 */
@ThreadSafe
@RunWith(JUnit4.class)
public class AbstractQueryJTemplateContextTest
{
    /**
     * Tests the MetadataManager is available (survives refactorings).
     */
    @Test
    public void metadataManager_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertNotNull(instance.getMetadataManager());
    }

    /**
     * Tests the CustomSqlProvider is available (survives refactorings).
     */
    @Test
    public void customSqlProvider_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertNotNull(instance.getCustomSqlProvider());
    }

    /**
     * Tests the header setting is available (survives refactorings).
     */
    @Test
    public void header_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertNull(instance.getHeader());
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
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertEquals("base.package.name", instance.getBasePackageName());
    }

    /**
     * Tests the repository name is available (survives refactorings).
     */
    @Test
    public void repositoryName_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertEquals("repository.name", instance.getRepositoryName());
    }

    /**
     * Tests the "implementMarkerInterfaces" setting is available (survives refactorings).
     */
    @Test
    public void implementMarkerInterfaces_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertFalse(instance.getImplementMarkerInterfaces());
    }

    /**
     * Tests the "jmx" setting is available (survives refactorings).
     */
    @Test
    public void jmx_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertFalse(instance.getImplementMarkerInterfaces());
    }

    /**
     * Tests the JNDI location is available (survives refactorings).
     */
    @Test
    public void jndiLocation_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertEquals("jndi:/location", instance.getJndiLocation());
    }

    /**
     * Tests the "disableGenerationTimestamps" setting is available (survives refactorings).
     */
    @Test
    public void disableGenerationTimestamps_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertTrue(instance.getDisableGenerationTimestamps());
    }

    /**
     * Tests the "disableNotNullAnnotations" setting is available (survives refactorings).
     */
    @Test
    public void disableNotNullAnnotations_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

        Assert.assertTrue(instance.getDisableNotNullAnnotations());
    }

    /**
     * Tests the "disableCheckthreadAnnotations" setting is available (survives refactorings).
     */
    @Test
    public void disableCheckthreadAnnotations_is_available()
    {
        @NotNull final AbstractQueryJTemplateContext instance = createContext();

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
     * Retrieves an {@link AbstractQueryJTemplateContext}.
     * @return such context.
     */
    @NotNull
    protected AbstractQueryJTemplateContext createContext()
    {
        return createContext("file.name");
    }

    /**
     * Retrieves an {@link AbstractQueryJTemplateContext}.
     * @param pk the pk.
     * @param <T> the pk type.
     * @return such context.
     */
    @NotNull
    protected <T> AbstractQueryJTemplateContext createContext(@NotNull final T pk)
    {
        @NotNull final QueryJCommand t_Command =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration(), null);

        @NotNull final AbstractQueryJTemplateContext result =
            new AbstractQueryJTemplateContext("" + pk, false, t_Command)
            {{
                immutableSetValue(buildTemplateNameKey(), "", getCommand());
                immutableSetValue(buildFileNameKey(), "" + pk, getCommand());
            }};

        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        new QueryJCommandWrapper<MetadataManager>(t_Command).setSetting(
            DatabaseMetaDataRetrievalHandler.METADATA_MANAGER, metadataManager);

        @NotNull final CustomSqlProvider customSqlProvider = EasyMock.createNiceMock(CustomSqlProvider.class);
        new QueryJCommandWrapper<CustomSqlProvider>(t_Command).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, customSqlProvider);

        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);
        new QueryJCommandWrapper<DecoratorFactory>(t_Command)
            .setSetting(DecoratorFactory.class.getName(), decoratorFactory);

        @NotNull final String packageName = "package.name";
        new QueryJCommandWrapper<String>(t_Command).setSetting(result.buildPackageNameKey(), packageName);

        @NotNull final String basePackageName = "base.package.name";
        new QueryJCommandWrapper<String>(t_Command).setSetting(QueryJSettings.PACKAGE_NAME, basePackageName);

        @NotNull final String repositoryName = "repository.name";
        new QueryJCommandWrapper<String>(t_Command).setSetting(QueryJSettings.REPOSITORY, repositoryName);

        final boolean implementMarkerInterfaces = false;
        new QueryJCommandWrapper<Boolean>(t_Command).setSetting(
            QueryJSettings.IMPLEMENT_MARKER_INTERFACES, implementMarkerInterfaces);

        final boolean jmx = false;
        new QueryJCommandWrapper<Boolean>(t_Command).setSetting(QueryJSettings.JMX, jmx);

        @NotNull final String jndiLocation = "jndi:/location";
        new QueryJCommandWrapper<String>(t_Command).setSetting(QueryJSettings.JNDI_DATASOURCE, jndiLocation);

        final boolean disableTimestampGeneration = true;
        new QueryJCommandWrapper<Boolean>(t_Command).setSetting(
            QueryJSettings.DISABLE_TIMESTAMPS, disableTimestampGeneration);

        final boolean disableNotNullAnnotations = true;
        new QueryJCommandWrapper<Boolean>(t_Command).setSetting(
            QueryJSettings.DISABLE_NOTNULL_ANNOTATIONS, disableNotNullAnnotations);

        final boolean disableCheckthreadAnnotations = true;
        new QueryJCommandWrapper<Boolean>(t_Command).setSetting(
            QueryJSettings.DISABLE_CHECKTHREAD_ANNOTATIONS, disableCheckthreadAnnotations);

        return result;
    }
}
