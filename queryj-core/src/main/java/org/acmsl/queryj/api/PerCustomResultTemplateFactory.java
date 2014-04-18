//;-*- mode: java -*-
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
 * Filename: PerCustomResultTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create per-custom result templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratorFactory;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Factory;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Represents entities able to create per-<i>custom result</i> templates.
 * @param <T> the template type.
 * @param <C> the context type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 3.0
 */
public interface PerCustomResultTemplateFactory
    <T extends PerCustomResultTemplate<C>, C extends PerCustomResultTemplateContext>
    extends  Factory
{
    /**
     * Creates a per-<i>custom result</i> template.
     * @param command the {@link QueryJCommand}.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param packageName the package name.
     * @param customResult the custom result.
     * @param properties the properties.
     * @return the template.
     */
    @Nullable
    public T createTemplate(
        @NotNull final QueryJCommand command,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String packageName,
        @NotNull final Result<String> customResult,
        @NotNull final List<Property<String>> properties);
}
