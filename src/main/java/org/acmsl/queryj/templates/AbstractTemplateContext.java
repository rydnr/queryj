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
 * Filename: AbstractTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents generic templates.
 *
 */
package org.acmsl.queryj.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;


/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.List;

/**
 * Abstract implementation of {@link TemplateContext}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/05/20
 */
public abstract class AbstractTemplateContext
    implements  TemplateContext,
                Serializable
{
    private static final long serialVersionUID = 3405496681880071590L;

    /**
     * The optional header.
     */
    private String m__strHeader;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * The database metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The custom-sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * Whether to implement marker interfaces.
     */
    private boolean m__bImplementMarkerInterfaces;

    /**
     * Whether to include JMX support.
     */
    private boolean m__bJmx;

    /**
     * The JNDI path of the DataSource.
     */
    private String m__strJndiLocation;

    /**
     * Whether to disable generation timestamps.
     */
    private boolean m__bDisableGenerationTimestamps;

    /**
     * Whether to disable NotNull annotations.
     */
    private boolean m__bDisableNotNullAnnotations;

    /**
     * Whether to disable checkthread.org annotations.
     */
    private boolean m__bDisableCheckthreadAnnotations;

    /**
     * Creates an {@link AbstractTemplateContext} with given information.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param header the header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI path of the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations.
     */
    protected AbstractTemplateContext(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @Nullable final String header,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations)
    {
        immutableSetMetadataManager(metadataManager);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetHeader(header);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetPackageName(packageName);
        immutableSetBasePackageName(basePackageName);
        immutableSetRepositoryName(repositoryName);
        immutableSetImplementMarkerInterfaces(implementMarkerInterfaces);
        immutableSetJmxSupportEnabled(jmx);
        immutableSetJndiLocation(jndiLocation);
        immutableSetDisableGenerationTimestamps(disableGenerationTimestamps);
        immutableSetDisableNotNullAnnotations(disableNotNullAnnotations);
        immutableSetDisableCheckthreadAnnotations(disableCheckthreadAnnotations);
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    private void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    @NotNull
    @Override
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    private void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom-sql provider.
     * @param customSqlProvider the customsql provider.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom-sql provider.
     * @return such provider.
     */
    @NotNull
    @Override
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the header.
     * @param header the header.
     */
    protected final void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }

    /**
     * Specifies the header.
     * @param header the header.
     */
    @SuppressWarnings("unused")
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }

    /**
     * Retrieves the header.
     * @return the header.
     */
    @NotNull
    @Override
    public String getHeader()
    {
        return m__strHeader;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    protected final void immutableSetDecoratorFactory(
        @NotNull final DecoratorFactory factory)
    {
        m__DecoratorFactory = factory;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    @SuppressWarnings("unused")
    protected void setDecoratorFactory(
        @NotNull final DecoratorFactory factory)
    {
        immutableSetDecoratorFactory(factory);
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    @Override
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    @SuppressWarnings("unused")
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getPackageName()
    {
        return m__strPackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    @SuppressWarnings("unused")
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getBasePackageName()
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    @SuppressWarnings("unused")
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getRepositoryName()
    {
        return m__strRepositoryName;
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param flag such condition.
     */
    protected final void immutableSetImplementMarkerInterfaces(
        final boolean flag)
    {
        m__bImplementMarkerInterfaces = flag;
    }

    /**
     * Specifies whether to implement marker interfaces.
     * @param flag such condition.
     */
    @SuppressWarnings("unused")
    protected void setImplementMarkerInterfaces(
        final boolean flag)
    {
        immutableSetImplementMarkerInterfaces(flag);
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @return such condition.
     */
    public boolean getImplementMarkerInterfaces()
    {
        return m__bImplementMarkerInterfaces;
    }

    /**
     * Specifies whether to include JMX support.
     * @param jmx such information.
     */
    protected final void immutableSetJmxSupportEnabled(final boolean jmx)
    {
        this.m__bJmx = jmx;
    }

    /**
     * Specifies whether to include JMX support.
     * @param jmx such information.
     */
    @SuppressWarnings("unused")
    protected void setJmxSupportEnabled(final boolean jmx)
    {
        immutableSetJmxSupportEnabled(jmx);
    }

    /**
     * Retrieves whether to include JMX support.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isJmxSupportEnabled()
    {
        return m__bJmx;
    }

    /**
     * Specifies the JNDI location for the {@link javax.sql.DataSource}.
     * @param location the JNDI location.
     */
    protected final void immutableSetJndiLocation(@NotNull final String location)
    {
        this.m__strJndiLocation = location;
    }

    /**
     * Specifies the JNDI location for the {@link javax.sql.DataSource}.
     * @param location the JNDI location.
     */
    @SuppressWarnings("unused")
    protected void setJndiLocation(@NotNull final String location)
    {
        this.m__strJndiLocation = location;
    }

    /**
     * Retrieves the JNDI location for the {@link javax.sql.DataSource}.
     * @return such location.
     */
    @NotNull
    public String getJndiLocation()
    {
        return this.m__strJndiLocation;
    }

    /**
     * Specifies whether to disable generation timestamps or not.
     * @param flag such setting.
     */
    protected final void immutableSetDisableGenerationTimestamps(final boolean flag)
    {
        m__bDisableGenerationTimestamps = flag;
    }

    /**
     * Specifies whether to disable generation timestamps or not.
     * @param flag such setting.
     */
    @SuppressWarnings("unused")
    protected void setDisableGenerationTimestamps(final boolean flag)
    {
        immutableSetDisableGenerationTimestamps(flag);
    }

    /**
     * Retrieves whether to use generation timestamps or not.
     *
     * @return such setting.
     */
    @Override
    public boolean getDisableGenerationTimestamps()
    {
        return m__bDisableGenerationTimestamps;
    }

    /**
     * Specifies whether to disable NotNull annotations or not.
     * @param flag such setting.
     */
    protected final void immutableSetDisableNotNullAnnotations(final boolean flag)
    {
        m__bDisableNotNullAnnotations = flag;
    }

    /**
     * Specifies whether to disable NotNull annotations or not.
     * @param flag such setting.
     */
    @SuppressWarnings("unused")
    protected void setDisableNotNullAnnotations(final boolean flag)
    {
        immutableSetDisableNotNullAnnotations(flag);
    }

    /**
     * Retrieves whether to use NotNull annotations or not.
     *
     * @return such setting.
     */
    @Override
    public boolean getDisableNotNullAnnotations()
    {
        return m__bDisableNotNullAnnotations;
    }

    /**
     * Specifies whether to disable checkthread.org annotations or not.
     * @param flag such setting.
     */
    protected final void immutableSetDisableCheckthreadAnnotations(final boolean flag)
    {
        m__bDisableCheckthreadAnnotations = flag;
    }

    /**
     * Specifies whether to disable checkthread.org annotations or not.
     * @param flag such setting.
     */
    @SuppressWarnings("unused")
    protected void setDisableCheckthreadAnnotations(final boolean flag)
    {
        immutableSetDisableCheckthreadAnnotations(flag);
    }

    /**
     * Retrieves whether to use checkthread.org annotations or not.
     *
     * @return such setting.
     */
    @Override
    public boolean getDisableCheckthreadAnnotations()
    {
        return m__bDisableCheckthreadAnnotations;
    }

    /**
     * Concatenates given attributes.
     * @param attributes the attributes.
     * @return the CSV version of given list.
     */
    @NotNull
    protected String toCsv(@NotNull final List<Attribute> attributes)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if (result.length() > 0)
            {
                result.append(",");
            }
            if (t_Attribute != null)
            {
                result.append(t_Attribute.getName());
            }
        }

        return result.toString();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.m__strHeader).append(this.m__DecoratorFactory)
            .append(this.m__MetadataManager).append(this.m__CustomSqlProvider).append(this.m__strPackageName)
            .append(this.m__strBasePackageName).append(this.m__strRepositoryName)
            .append(this.m__bImplementMarkerInterfaces).append(this.m__bJmx).append(this.m__strJndiLocation)
            .append(this.m__bDisableGenerationTimestamps).append(this.m__bDisableNotNullAnnotations)
            .append(this.m__bDisableCheckthreadAnnotations)
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AbstractTemplateContext other = (AbstractTemplateContext) obj;

        return
            new EqualsBuilder().append(this.m__strHeader, other.m__strHeader)
            .append(this.m__DecoratorFactory, other.m__DecoratorFactory)
            .append(this.m__MetadataManager, other.m__MetadataManager)
            .append(this.m__CustomSqlProvider, other.m__CustomSqlProvider)
            .append(this.m__strPackageName, other.m__strPackageName)
            .append(this.m__strBasePackageName, other.m__strBasePackageName)
            .append(this.m__strRepositoryName, other.m__strRepositoryName)
            .append(this.m__bImplementMarkerInterfaces, other.m__bImplementMarkerInterfaces)
            .append(this.m__bJmx, other.m__bJmx).append(this.m__strJndiLocation, other.m__strJndiLocation)
            .append(this.m__bDisableGenerationTimestamps, other.m__bDisableGenerationTimestamps)
            .append(this.m__bDisableNotNullAnnotations, other.m__bDisableNotNullAnnotations)
            .append(this.m__bDisableCheckthreadAnnotations, other.m__bDisableCheckthreadAnnotations)
            .isEquals();
    }
}
