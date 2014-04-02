/*
                        queryj

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
 * Filename: AbstractQueryJTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/04/01
 * Time: 21:26
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.BasePackageNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.DecoratorFactoryNotAvailableException;
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
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/01 21:26
 */
@ThreadSafe
public abstract class AbstractQueryJTemplateContext
    extends AbstractTemplateContext
    implements QueryJTemplateContext
{
    /**
     * Creates an {@link AbstractTemplateContext} with given information.
     * @param pk the pk.
     * @param command the {@link org.acmsl.queryj.QueryJCommand} instance.
     */
    protected AbstractQueryJTemplateContext(@NotNull final String pk, @NotNull final QueryJCommand command)
    {
        super(pk, command);
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
     * Retrieves the {@link org.acmsl.queryj.metadata.DecoratorFactory} instance.
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

}
