/*
                        QueryJ Template Packaging Maven Plugin

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
 * Filename: JdbcSettingNotAvailableException.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: To be thrown whenever a required JDBC setting is not available
 *              at runtime.
 *
 * Date: 2014/03/30
 * Time: 22:16
 *
 */
package org.acmsl.queryj.templates.packaging.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * To be thrown whenever a required JDBC setting is not available at runtime.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/30 22:16
 */
@ThreadSafe
public class JdbcSettingNotAvailableException
    extends TemplatePackagingNonCheckedException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4549781005748769787L;

    /**
     * Enumerates the available settings.
     */
    public enum JdbcSetting
    {
        /**
         * The driver.
         */
        DRIVER("Driver"),
        /**
         * The url.
         */
        URL("Url"),
        /**
         * The user name.
         */
        USERNAME("Username"),
        /**
         * The password.
         */
        PASSWORD("Password");

        /**
         * The key.
         */
        private final String m__Key;

        /**
         * Creates a new item.
         * @param key the key.
         */
        private JdbcSetting(/* @NotNull */final String key)
        {
            m__Key = key;
        }

        /**
         * Retrieves the key.
         * @return such information.
         */
        @NotNull
        public String getKey()
        {
            return m__Key;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String toString()
        {
            return
                  "{ \"class\": \"JdbcSetting\""
                + ", \"key\": \"" + m__Key + '"'
                + " }";
        }
    }

    /**
     * Creates a {@code TemplatePackagingNonCheckedException} with given setting.
     * @param setting the JDBC setting.
     */
    public JdbcSettingNotAvailableException(@NotNull final JdbcSetting setting)
    {
        super("Jdbc" + setting.getKey() + ".not.available");
    }
}
