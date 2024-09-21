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
 * Filename: BasePerRepositoryTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-repository templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.PerRepositoryTemplateGenerator;
import org.acmsl.queryj.api.AbstractBasePerRepositoryTemplate;
import org.acmsl.queryj.api.PerRepositoryTemplateContext;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.metadata.engines.Engine;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes <i>per-repository</i> templates.
 * @param <T> the template type.
 * @param <C> the context type.
 * @param <G> the generator type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class BasePerRepositoryTemplateWritingHandler
    <T extends AbstractBasePerRepositoryTemplate<C>,
     C extends PerRepositoryTemplateContext,
     G extends PerRepositoryTemplateGenerator<T, C>>
    extends    AbstractQueryJTemplateWritingHandler<T, C, G>
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerRepositoryTemplateWritingHandler</code> instance.
     */
    public BasePerRepositoryTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    public List<T> retrieveTemplates(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        @NotNull final List<T> result;

        @Nullable final T template = retrieveTemplate(parameters);

        if (template == null)
        {
            result = new ArrayList<>(0);
        }
        else
        {
            result = new ArrayList<>(1);
            result.add(template);
        }

        return result;
    }

    /**
     * Retrieves the template from the command.
     * @param parameters the parameters.
     * @return the template.
     * @throws QueryJBuildException if the template is unavailable.
     */
    @Nullable
    protected abstract T retrieveTemplate(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException;

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected File retrieveOutputDir(
        @NotNull final C context,
        @NotNull final File rootDir,
        @NotNull final QueryJCommand parameters)
        throws QueryJBuildException
    {
        return
            retrieveOutputDir(
                retrieveTableRepositoryName(parameters),
                context,
                rootDir,
                retrieveMetadataManager(parameters).getEngine(),
                parameters);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param repository the repository name.
     * @param context the context.
     * @param projectFolder the project folder.
     * @param engine the engine.
     * @param parameters the parameter map.
     * @return such folder.
     */
    @NotNull
    protected abstract File retrieveOutputDir(
        @NotNull final String repository,
        @NotNull final C context,
        @NotNull final File projectFolder,
        @NotNull final Engine<String> engine,
        @NotNull final QueryJCommand parameters);
}
