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
import org.acmsl.queryj.api.AbstractTemplateContext;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.exceptions.JdbcSettingNotAvailableException;
import org.acmsl.queryj.templates.packaging.exceptions.JdbcSettingNotAvailableException.JdbcSetting;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.Serializable;

/**
 * Base context class.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:53
 */
@ThreadSafe
public abstract class AbstractTemplatePackagingContext
    extends AbstractTemplateContext
    implements Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7291939701431286380L;

    /**
     * Creates a new instance.
     * @param pk the pk.
     * @param command the command.
     */
    public AbstractTemplatePackagingContext(@NotNull final String pk, @NotNull final QueryJCommand command)
    {
        super(pk, command);
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
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"DefaultTemplatePackagingContext\""
            + ", \"package\": \"org.acmsl.queryj.templates.packaging\""
            + ", \"parent\": " + super.toString() +" }";
    }
}
