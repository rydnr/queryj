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

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.FileNameNotAvailableException;
import org.acmsl.queryj.api.exceptions.PackageNameNotAvailableException;
import org.acmsl.queryj.templates.packaging.exceptions.JdbcSettingNotAvailableException;
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
public class AbstractTemplatePackagingContext
    implements Serializable
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
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return getTemplateName(getCommand());
    }

    /**
     * Retrieves the template name.
     * @param command the command.
     * @return such information.
     */
    @NotNull
    protected String getTemplateName(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting("templateName");

        if (result == null)
        {
            throw new TemplateNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the file name.
     * @return such information.
     */
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
            new QueryJCommandWrapper<String>(command).getSetting("fileName");

        if (result == null)
        {
            throw new FileNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
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
            new QueryJCommandWrapper<String>(command).getSetting("packageName");

        if (result == null)
        {
            throw new PackageNameNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the root dir.
     * @return such folder.
     */
    @NotNull
    public File getRootDir()
    {
        return getRootDir(getCommand());
    }

    /**
     * Retrieves the root dir.
     * @param command the command.
     * @return such folder.
     */
    @NotNull
    protected File getRootDir(@NotNull final QueryJCommand command)
    {
        @Nullable final File result =
            new QueryJCommandWrapper<File>(command).getSetting(TemplatePackagingSettings.OUTPUT_DIR);

        if (result == null)
        {
            throw new RootDirNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the output dir.
     * @return such folder.
     */
    @NotNull
    public File getOutputDir()
    {
        return getOutputDir(getCommand());
    }

    /**
     * Retrieves the output dir.
     * @param command the command.
     * @return such folder.
     */
    @NotNull
    protected File getOutputDir(@NotNull final QueryJCommand command)
    {
        @Nullable final File result =
            new QueryJCommandWrapper<File>(command).getSetting("outputDir");

        if (result == null)
        {
            throw new OutputDirNotAvailableException();
        }

        return result;
    }

    /**
     * Retrieves the JDBC driver.
     * @return the JDBC driver.
     */
    @NotNull
    public String getJdbcDriver()
    {
        return getJdbcDriver(getCommand());
    }

    /**
     * Retrieves the JDBC driver.
     * @return the JDBC driver.
     */
    @NotNull
    protected String getJdbcDriver(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting("jdbcDriver");

        if (result == null)
        {
            throw
                new JdbcSettingNotAvailableException(
                    JdbcSettingNotAvailableException.JdbcSetting.DRIVER);
        }

        return result;
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @NotNull
    public String getJdbcUrl()
    {
        return getJdbcUrl(getCommand());
    }

    /**
     * Retrieves the JDBC url.
     * @param command the command.
     * @return the JDBC url.
     */
    @NotNull
    protected String getJdbcUrl(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting("jdbcUrl");

        if (result == null)
        {
            throw
                new JdbcSettingNotAvailableException(
                    JdbcSettingNotAvailableException.JdbcSetting.URL);
        }

        return result;
    }

    /**
     * Retrieves the JDBC user name.
     * @return the JDBC user name.
     */
    @NotNull
    public String getJdbcUsername()
    {
        return getJdbcUsername(getCommand());
    }

    /**
     * Retrieves the JDBC user name.
     * @param command the command.
     * @return the JDBC user name.
     */
    @NotNull
    protected String getJdbcUsername(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting("jdbcUserName");

        if (result == null)
        {
            throw
                new JdbcSettingNotAvailableException(
                    JdbcSettingNotAvailableException.JdbcSetting.USERNAME);
        }

        return result;
    }

    /**
     * Retrieves the JDBC password.
     * @return the JDBC password.
     */
    @NotNull
    public String getJdbcPassword()
    {
        return getJdbcPassword(getCommand());
    }

    /**
     * Retrieves the JDBC password.
     * @param command the command.
     * @return the JDBC password.
     */
    @NotNull
    protected String getJdbcPassword(@NotNull final QueryJCommand command)
    {
        @Nullable final String result =
            new QueryJCommandWrapper<String>(command).getSetting("jdbcPassword");

        if (result == null)
        {
            throw
                new JdbcSettingNotAvailableException(
                    JdbcSettingNotAvailableException.JdbcSetting.PASSWORD);
        }

        return result;
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
