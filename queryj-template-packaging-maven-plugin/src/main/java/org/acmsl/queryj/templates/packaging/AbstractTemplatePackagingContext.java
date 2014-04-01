/*
                        QueryJ-Template-Packaging-Plugin

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
 * Filename: AbstractTemplatePackagingContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base context class.
 *
 * Date: 2013/09/15
 * Time: 06:53
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.api.exceptions.FileNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.PackageNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.QueryJNonCheckedException;
import org.acmsl.queryj.api.exceptions.VersionNotAvailableException;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.exceptions.JdbcSettingNotAvailableException;
import org.acmsl.queryj.templates.packaging.exceptions.JdbcSettingNotAvailableException.JdbcSetting;
import org.acmsl.queryj.templates.packaging.exceptions.OutputDirNotAvailableException;
import org.acmsl.queryj.templates.packaging.exceptions.RootDirNotAvailableException;
import org.acmsl.queryj.templates.packaging.exceptions.TemplateNameNotAvailableException;

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
import java.io.Serializable;

/**
 * Base context class.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:53
 */
@ThreadSafe
public abstract class AbstractTemplatePackagingContext
    implements TemplateContext,
               Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7291939701431286380L;

    /**
     * The command.
     */
    @NotNull
    private QueryJCommand m__Command;

    /**
     * Creates a new instance.
     * @param command the command.
     */
    public AbstractTemplatePackagingContext(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Specifies the name of the template.
     * @param command such name.
     */
    protected final void immutableSetCommand(@NotNull final QueryJCommand command)
    {
        this.m__Command = command;
    }

    /**
     * Specifies the name of the template.
     * @param command such name.
     */
    @SuppressWarnings("unused")
    protected void setCommand(@NotNull final QueryJCommand command)
    {
        immutableSetCommand(command);
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public QueryJCommand getCommand()
    {
        return this.m__Command;
    }

    /**
     * Annotates a value in the command.
     * @param key the key.
     * @param value the value.
     * @param command the command.
     * @param <T> the type.
     */
    protected final <T> void immutableSetValue(
        @NotNull final String key, @NotNull final T value, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<T>(command).setSetting(key, value);
    }

    /**
     * Retrieves the value.
     * @param key the key.
     * @param command the command.
     * @param exceptionToThrow the exception to throw.
     * @param <T> the value type.
     * @return such information.
     */
    @NotNull
    protected <T> T getValue(
        @NotNull final String key,
        @NotNull final QueryJCommand command,
        @NotNull final QueryJNonCheckedException exceptionToThrow)
    {
        @Nullable final T result =
            new QueryJCommandWrapper<T>(command).getSetting(key);

        if (result == null)
        {
            throw exceptionToThrow;
        }

        return result;
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return getValue(buildTemplateNameKey(), getCommand(), new TemplateNameNotAvailableException());
    }

    /**
     * Builds the template name key.
     * @return such information.
     */
    @NotNull
    protected String buildTemplateNameKey()
    {
        return "templateName@" + hashCode();
    }

    /**
     * Retrieves the file name.
     * @return such information.
     */
    @NotNull
    public String getFileName()
    {
        return getValue(buildFileNameKey(), getCommand(), new FileNameNotAvailableException());
    }

    /**
     * Builds a file name key.
     * @return such key.
     */
    @NotNull
    protected String buildFileNameKey()
    {
        return "fileName@" + hashCode();
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    public String getPackageName()
    {
        return getValue(buildPackageNameKey(), getCommand(), new PackageNameNotAvailableException());
    }

    /**
     * Builds the package name.
     * @return such value.
     */
    @NotNull
    protected String buildPackageNameKey()
    {
        return "packageName@" + hashCode();
    }

    /**
     * Retrieves the root dir.
     * @return such folder.
     */
    @NotNull
    public File getRootDir()
    {
        return getValue(buildRootDirKey(), getCommand(), new RootDirNotAvailableException());
    }

    /**
     * Builds the root dir key.
    * @return such key.
     */
    @NotNull
    protected String buildRootDirKey()
    {
        return TemplatePackagingSettings.OUTPUT_DIR + "@" + hashCode();
    }

    /**
     * Retrieves the output dir.
     * @return such folder.
     */
    @NotNull
    public File getOutputDir()
    {
        return getValue(buildOutputDirKey(), getCommand(), new OutputDirNotAvailableException());
    }

    /**
     * Builds the output dir key.
     * @return such key.
     */
    @NotNull
    protected String buildOutputDirKey()
    {
        return "outputDir@" + hashCode();
    }

    /**
     * Retrieves the JDBC driver.
     * @return the JDBC driver.
     */
    @NotNull
    public String getJdbcDriver()
    {
        return
            getValue(
                buildJdbcDriverKey(),
                getCommand(),
                new JdbcSettingNotAvailableException(JdbcSettingNotAvailableException.JdbcSetting.DRIVER));
    }

    /**
     * Builds the JDBC driver key.
     * @return such key.
     */
    @NotNull
    protected String buildJdbcDriverKey()
    {
        return TemplatePackagingSettings.JDBC_DRIVER;
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @NotNull
    public String getJdbcUrl()
    {
        return
            getValue(
                buildJdbcUrlKey(),
                getCommand(),
                new JdbcSettingNotAvailableException(JdbcSetting.URL));
    }

    /**
     * Builds the JDBC url key.
     * @return such key.
     */
    @NotNull
    protected String buildJdbcUrlKey()
    {
        return TemplatePackagingSettings.JDBC_URL;
    }

    /**
     * Retrieves the JDBC user name.
     * @return the JDBC user name.
     */
    @NotNull
    public String getJdbcUsername()
    {
        return
            getValue(
                buildJdbcUserNameKey(),
                getCommand(),
                new JdbcSettingNotAvailableException(JdbcSetting.USERNAME));
    }

    /**
     * Builds the JDBC user name key.
     * @return such key.
     */
    @NotNull
    protected String buildJdbcUserNameKey()
    {
        return TemplatePackagingSettings.JDBC_USERNAME;
    }

    /**
     * Retrieves the JDBC password.
     * @return the JDBC password.
     */
    @NotNull
    public String getJdbcPassword()
    {
        return
            getValue(
                buildJdbcPasswordKey(),
                getCommand(),
                new JdbcSettingNotAvailableException(JdbcSetting.PASSWORD));
    }

    /**
     * Builds the JDBC password key.
     * @return such key.
     */
    @NotNull
    protected String buildJdbcPasswordKey()
    {
        return TemplatePackagingSettings.JDBC_PASSWORD;
    }

    /**
     * Retrieves the version information.
     * @return such information.
     */
    @NotNull
    public String getVersion()
    {
        return getValue(buildVersionKey(), getCommand(), new VersionNotAvailableException());
    }

    /**
     * Builds the key for the version.
     * @return such key.
     */
    @NotNull
    protected String buildVersionKey()
    {
        return QueryJSettings.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"DefaultTemplatePackagingContext\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging\""
            + ", \"command\": " + m__Command +" }";
    }
}
