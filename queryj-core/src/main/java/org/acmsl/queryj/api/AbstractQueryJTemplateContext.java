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
 * Filename: AbstractQueryJTemplateContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: QueryJ-specific template context: used when QueryJ creates
 *              templates to be consumed by itself afterwards.
 *
 * Date: 2014/04/01
 * Time: 21:26
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.exceptions.BasePackageNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.JndiLocationNotAvailableException;
import org.acmsl.queryj.api.exceptions.RepositoryNameNotAvailableException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.exceptions.CustomSqlProviderNotAvailableException;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.tools.exceptions.MetadataManagerNotAvailableException;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing some ACM S.L. Java Commons classes.
 */
import org.acmsl.commons.utils.io.FileUtils;

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
 * Importing JDK classes.
 */
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * QueryJ-specific template context: used when QueryJ creates
 * templates to be consumed by itself afterwards.
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
     * The serial version id.
     */
    private static final long serialVersionUID = -3671360539593632338L;

    /**
     * Creates an {@code AbstractTemplateContext} with given information.
     * @param pk the pk.
     * @param debug whether debugging is enabled.
     * @param command the {@link org.acmsl.queryj.QueryJCommand} instance.
     */
    protected AbstractQueryJTemplateContext(
        @NotNull final String pk, final boolean debug, @NotNull final QueryJCommand command)
    {
        super(pk, debug, command);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    @NotNull
    @Override
    public MetadataManager getMetadataManager()
    {
        return getValue(buildMetadataManagerKey(), getCommand(), new MetadataManagerNotAvailableException());
    }

    /**
     * Builds the metadata manager key.
     * @return such key.
     */
    @NotNull
    protected String buildMetadataManagerKey()
    {
        return DatabaseMetaDataRetrievalHandler.METADATA_MANAGER;
    }

    /**
     * Retrieves the custom-sql provider.
     * @return such provider.
     */
    @NotNull
    @Override
    public CustomSqlProvider getCustomSqlProvider()
    {
        return getValue(buildCustomSqlProviderKey(), getCommand(), new CustomSqlProviderNotAvailableException());
    }

    /**
     * Builds the custom-sql provider key.
     * @return such key.
     */
    @NotNull
    protected String buildCustomSqlProviderKey()
    {
        return CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER;
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
        @NotNull final QueryJCommandWrapper<String> wrapper = new QueryJCommandWrapper<>(command);

        @Nullable String result = wrapper.getSetting(Literals.HEADER);

        if (result == null)
        {
            result = retrieveHeaderFromFile(command, FileUtils.getInstance());

            if (result == null)
            {
                result = "";
            }

            wrapper.setSetting(Literals.HEADER, result);
        }

        if ("".equals(result.trim()))
        {
            result = null;
        }

        return result;
    }

    /**
     * Retrieves the header from the specified the header file.
     * @param command the {@link QueryJCommand} instance.
     * @param fileUtils the {@link FileUtils} instance.
     * @return the header.
     */
    @Nullable
    protected String retrieveHeaderFromFile(
        @NotNull final QueryJCommand command, @NotNull final FileUtils fileUtils)
    {
        @Nullable final String result;

        @Nullable final File file =
            new QueryJCommandWrapper<File>(command).getSetting(QueryJSettings.HEADER_FILE);

        if (file != null)
        {
            @Nullable final String charset =
                new QueryJCommandWrapper<String>(command).getSetting(QueryJSettings.ENCODING);

            result =
                fileUtils.readFileIfPossible(
                    file, charset != null ? Charset.forName(charset) : Charset.defaultCharset());
        }
        else
        {
            result = null;
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
        return getValue(buildBasePackageNameKey(), getCommand(), new BasePackageNameNotAvailableException());
    }

    /**
     * Retrieves the base package name key.
     * @return such information.
     */
    @NotNull
    protected String buildBasePackageNameKey()
    {
        return QueryJSettings.PACKAGE_NAME;
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    @NotNull
    @Override
    public String getRepositoryName()
    {
        return getValue(buildRepositoryNameKey(), getCommand(), new RepositoryNameNotAvailableException());
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    @NotNull
    protected String buildRepositoryNameKey()
    {
        return QueryJSettings.REPOSITORY;
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
        return getValue(buildJndiLocationKey(), getCommand(), new JndiLocationNotAvailableException());
    }

    /**
     * Retrieves the key of the JNDI location for the {@link javax.sql.DataSource}.
     * @return such key.
     */
    @NotNull
    protected String buildJndiLocationKey()
    {
        return QueryJSettings.JNDI_DATASOURCE;
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
     * @param key the key.
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
