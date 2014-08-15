//;-*- mode: java -*-
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
 * Filename: PerRepositoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-repository templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents entities able to create per-repository templates.
 * @param <T> the template type.
 * @param <C> the template context type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2.0
 */
public interface PerRepositoryTemplateFactory
    <T extends AbstractBasePerRepositoryTemplate<C>, C extends PerRepositoryTemplateContext>
    extends  Factory
{
    /**
     * Creates a {@link T} instance with given information.
     * @param repository the repository name.
     * @param tableNames the table names.
     * @param context the {@link PerRepositoryTemplateContext}.
     * @param command the {@link QueryJCommand command}.
     * @return the new template.
     */
    @Nullable
    public T createTemplate(
        @NotNull final String repository,
        @NotNull final List<String> tableNames,
        @NotNull final C context,
        @NotNull final QueryJCommand command);
}
