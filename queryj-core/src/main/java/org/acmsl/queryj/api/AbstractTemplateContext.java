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
 * Filename: AbstractTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents generic templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.BasePackageNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.DecoratorFactoryNotAvailableException;
import org.acmsl.queryj.api.exceptions.FileNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.JndiLocationNotAvailableException;
import org.acmsl.queryj.api.exceptions.PackageNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.RepositoryNameNotAvailableException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.exceptions.CustomSqlProviderNotAvailableException;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.tools.exceptions.MetadataManagerNotAvailableException;

/*
 * Importing Apache Commons Lang classes.
 */
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.util.List;

/**
 * Abstract implementation of {@link QueryJTemplateContext}.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 3.0
 * Created: 2012/05/20
 */
@ThreadSafe
public abstract class AbstractTemplateContext
    implements QueryJTemplateContext,
               Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 3405496681880071590L;

    /**
     * The package name key.
     */
    public static final String PACKAGE_NAME = "packageName";

    /**
     * The file name key.
     */
    public static final String FILE_NAME = "fileName";

    /**
     * The command.
     */
    private QueryJCommand m__Command;

    /**
     * Creates an {@link AbstractTemplateContext} with given information.
     * @param command the {@link QueryJCommand} instance.
     */
    protected AbstractTemplateContext(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Specifies the command.
     * @param command the command.
     */
    private void immutableSetCommand(@NotNull final QueryJCommand command)
    {
        m__Command = command;
    }

    /**
     * Specifies the command.
     * @param command the command.
     */
    @SuppressWarnings("unused")
    protected void setCommand(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Retrieves the command.
     * @return such command.
     */
    @NotNull
    public QueryJCommand getCommand()
    {
        return m__Command;
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    @NotNull
    @Override
    public MetadataManager getMetadataManager()
    {
        return getMetadataManager(getCommand());
    }

    /**
     * Retrieves the metadata manager.
     * @param command the command.
     * @return such manager.
     */
    @NotNull
    protected MetadataManager getMetadataManager(@NotNull final QueryJCommand command)
    {
        @Nullable final MetadataManager result =
            new QueryJCommandWrapper<MetadataManager>(command)
                .getSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER);

        if (result == null)
        {
            throw new MetadataManagerNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the custom-sql provider.
     * @return such provider.
     */
    @NotNull
    @Override
    public CustomSqlProvider getCustomSqlProvider()
    {
        return getCustomSqlProvider(getCommand());
    }

    /**
     * Retrieves the custom-sql provider.
     * @param command the command.
     * @return such provider.
     */
    @NotNull
    protected CustomSqlProvider getCustomSqlProvider(@NotNull final QueryJCommand command)
    {
        @Nullable final CustomSqlProvider result =
            new QueryJCommandWrapper<CustomSqlProvider>(command).getSetting(
                CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER);

        if (result == null)
        {
            throw new CustomSqlProviderNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the header.
     * @return the header.
     */
    @Nullable
    @Override
    public String getHeader()
    {
        return getHeader(getCommand());
    }

    /**
     * Retrieves the header.
     * @param command the command.
     * @return the header.
     */
    @Nullable
    protected String getHeader(@NotNull final QueryJCommand command)
    {
        return new QueryJCommandWrapper<String>(command).getSetting(QueryJSettings.HEADER_FILE);
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    @Override
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return getDecoratorFactory(getCommand());
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    @NotNull
    protected DecoratorFactory getDecoratorFactory(@NotNull final QueryJCommand command)
    {
        @Nullable final DecoratorFactory result =
            new QueryJCommandWrapper<DecoratorFactory>(command).getSetting(DecoratorFactory.class.getName());

        if (result == null)
        {
            throw new DecoratorFactoryNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getPackageName()
    {
        return getPackageName(getCommand());
    }

    /**
     * Retrieves the package name.
     * @param command the command.
     * @return such information.
     */
    @NotNull
    protected String getPackageName(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting(PACKAGE_NAME);

        if (result == null)
        {
            throw new PackageNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getBasePackageName()
    {
        return getBasePackageName(getCommand());
    }

    /**
     * Retrieves the base package name.
     * @param command the command.
     * @return such information.
     */
    @NotNull
    protected String getBasePackageName(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting(QueryJSettings.PACKAGE);

        if (result == null)
        {
            throw new BasePackageNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getRepositoryName()
    {
        return getRepositoryName(getCommand());
    }

    /**
     * Retrieves the repository name.
     * @param command the command.
     * @return such information.
     */
    @NotNull
    protected String getRepositoryName(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting(QueryJSettings.REPOSITORY);

        if (result == null)
        {
            throw new RepositoryNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves whether to implement marker interfaces.
     * @return such condition.
     */
    @Override
    public boolean getImplementMarkerInterfaces()
    {
        return getBooleanValue(getCommand(), QueryJSettings.IMPLEMENT_MARKER_INTERFACES);
    }

    /**
     * Retrieves whether to include JMX support.
     * @return such information.
     */
    @Override
    public boolean isJmxSupportEnabled()
    {
        return getBooleanValue(getCommand(), QueryJSettings.JMX);
    }

    /**
     * Retrieves the JNDI location for the {@link javax.sql.DataSource}.
     * @return such location.
     */
    @Override
    @NotNull
    public String getJndiLocation()
    {
        return getJndiLocation(getCommand());
    }

    /**
     * Retrieves the JNDI location for the {@link javax.sql.DataSource}.
     * @param command the command.
     * @return such location.
     */
    @NotNull
    protected String getJndiLocation(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting(QueryJSettings.JNDI_DATASOURCE);

        if (result == null)
        {
            throw new JndiLocationNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves whether to use generation timestamps or not.
     * @return such setting.
     */
    @Override
    public boolean getDisableGenerationTimestamps()
    {
        return getBooleanValue(getCommand(), QueryJSettings.DISABLE_TIMESTAMPS);
    }

    /**
     * Retrieves a boolean value from the command.
     * @param command the command.
     * @return such value.
     */
    protected boolean getBooleanValue(
        @NotNull final QueryJCommand command, @NotNull final String key)
    {
        final boolean result;

        @Nullable final Boolean aux = new QueryJCommandWrapper<Boolean>(command).getSetting(key);

        if (aux == null)
        {
            result = false;
        }
        else
        {
            result = aux;
        }

        return result;
    }

    /**
     * Retrieves whether to use NotNull annotations or not.
     * @return such setting.
     */
    @Override
    public boolean getDisableNotNullAnnotations()
    {
        return getBooleanValue(getCommand(), QueryJSettings.DISABLE_NOTNULL_ANNOTATIONS);
    }

    /**
     * Retrieves whether to use checkthread.org annotations or not.
     * @return such setting.
     */
    @Override
    public boolean getDisableCheckthreadAnnotations()
    {
        return getBooleanValue(getCommand(), QueryJSettings.DISABLE_CHECKTHREAD_ANNOTATIONS);
    }

    /**
     * Retrieves the file name.
     * @return such information.
     */
    @Override
    @NotNull
    public String getFileName()
    {
        return getFileName(getCommand());
    }

    /**
     * Retrieves the file name.
     * @param command the command.
     * @return such information.
     */
    @NotNull
    protected String getFileName(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting(FILE_NAME);

        if (result == null)
        {
            throw new FileNameNotAvailableException();
        }

        return result;
    }

    /**
     * Concatenates given attributes.
     * @param attributes the attributes.
     * @return the CSV version of given list.
     */
    @NotNull
    protected String toCsv(@NotNull final List<Attribute<String>> attributes)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        for (@Nullable final Attribute<String> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                if (result.length() > 0)
                {
                    result.append(",");
                }
                result.append(t_Attribute.getName());
            }
        }

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return
            new HashCodeBuilder().append(AbstractTemplateContext.class.getName()).append(this.m__Command).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
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

        return new EqualsBuilder().append(this.m__Command, other.m__Command).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractTemplateContext.class.getSimpleName() + '"'
            + ", \"package\": \"org.acmsl.queryj.api\""
            + ", \"command\": " + m__Command
            + " }";
    }
}
