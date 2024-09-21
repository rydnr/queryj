/*
                        QueryJ Debugging

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
 * Filename: DebuggingUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some methods dealing with debugging services.
 *
 * Date: 2014/08/20
 * Time: 13:32
 *
 */
package org.acmsl.queryj.debugging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.TemplateContext;

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
import java.lang.management.ManagementFactory;
import java.util.ServiceLoader;

/**
 * Provides some methods dealing with debugging services.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/08/20 13:32
 */
@ThreadSafe
public class DebuggingUtils
{
    /**
     * String literal: "-Xrunjdwp:transport".
     */
    public static final String XRUNJDWP_TRANSPORT = "-Xrunjdwp:transport";

    /**
     * A singleton implemented to avoid double-checking lock.
     */
    protected static class DebuggingUtilsSingletonContainer
    {
        /**
         * The singleton instance.
         */
        protected static final DebuggingUtils SINGLETON = new DebuggingUtils();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static DebuggingUtils getInstance()
    {
        return DebuggingUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Resolves the {@link TemplateDebuggingService} at runtime.
     * @return such instance, or {@code null} if none is found.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <C extends TemplateContext> TemplateDebuggingService<C> resolveTemplateDebuggingService()
    {
        @Nullable TemplateDebuggingService<C> result = null;

        @Nullable final Class<TemplateDebuggingService> serviceClass =
            TemplateDebuggingService.class;

        if (serviceClass != null)
        {
            @Nullable final ServiceLoader<TemplateDebuggingService> loader =
                ServiceLoader.load(serviceClass);

            if (loader != null)
            {
                for (@NotNull final TemplateDebuggingService<C> service : loader)
                {
                    result = service;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks whether we're in dev mode.
     * @param templateFileName the template file name.
     * @return {@code true} in such case.
     */
    public boolean isInDevMode(@NotNull final String templateFileName)
    {
        final boolean result;

        final boolean devModeDisabled = System.getProperty("queryj.devMode.disabled") != null;

        if (devModeDisabled)
        {
            result = false;
        }
        else
        {
            final boolean debug =
                ManagementFactory.getRuntimeMXBean(). getInputArguments().toString().contains(XRUNJDWP_TRANSPORT);

            result =
                (   (debug)
                    && (!templateFileName.startsWith("org/acmsl/queryj/templates/packaging")));
        }

        return result;
    }
}
