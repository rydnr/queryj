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
 * Filename: TemplateContext.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 5/20/12
 * Time: 5:46 AM
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Context information required in Template generation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/20
 */
public interface QueryJTemplateContext
    extends TemplateContext
{
    /**
     * Retrieves the {@link MetadataManager} instance.
     * @return such instance.
     */
    @NotNull
    MetadataManager getMetadataManager();

    /**
     * Retrieves the {@link CustomSqlProvider} instance.
     * @return such instance.
     */
    @NotNull
    CustomSqlProvider getCustomSqlProvider();

    /**
     * Retrieves the header.
     * @return such information.
     */
    @Nullable
    String getHeader();

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    String getPackageName();

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    @NotNull
    String getBasePackageName();

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    @NotNull
    String getRepositoryName();

    /**
     * Retrieves whether to implement marker interfaces or not.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    boolean getImplementMarkerInterfaces();

    /**
     * Retrieves whether to include support for JMX or not.
     * @return such setting.
     */
    @SuppressWarnings("unused")
    boolean isJmxSupportEnabled();

    /**
     * Retrieves the JNDI location.
     * @return such information.
     */
    @NotNull
    String getJndiLocation();

    /**
     * Retrieves whether to use generation timestamps or not.
     * @return such setting.
     */
    boolean getDisableGenerationTimestamps();

    /**
     * Retrieves whether to use NotNull annotations or not.
     * @return such setting.
     */
    boolean getDisableNotNullAnnotations();

    /**
     * Retrieves whether to use checkthread.org annotations or not.
     * @return such setting.
     */
    boolean getDisableCheckthreadAnnotations();
}
