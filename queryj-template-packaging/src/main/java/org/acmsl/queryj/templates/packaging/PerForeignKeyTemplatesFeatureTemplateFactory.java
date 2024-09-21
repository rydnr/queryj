/*
                        QueryJ Template Packaging

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
 * Filename: PerForeignKeyTemplatesFeatureTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Factory for PerForeignKeyTemplatesFeatureTemplates.
 *
 * Date: 2014/04/16
 * Time: 12:36
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing ACM SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.engines.Engine;
import org.acmsl.queryj.metadata.engines.EngineDecorator;

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
 * Importing StringTemplate v4 classes.
 */
import org.stringtemplate.v4.ST;

/**
 * Factory for {@link PerForeignKeyTemplatesFeatureTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 12:36
 */
@ThreadSafe
public class PerForeignKeyTemplatesFeatureTemplateFactory
    implements TemplatePackagingTemplateFactory<PerForeignKeyTemplatesFeatureTemplate, GlobalTemplateContext>,
               Singleton
{
    /**
     * Singleton instance to avoid double-locking check.
     */
    protected static final class PerForeignKeyTemplatesFeatureTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final PerForeignKeyTemplatesFeatureTemplateFactory SINGLETON =
            new PerForeignKeyTemplatesFeatureTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static PerForeignKeyTemplatesFeatureTemplateFactory getInstance()
    {
        return PerForeignKeyTemplatesFeatureTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * Generates a template.
     *
     * @param context the context.
     * @return such template.
     */
    @Nullable
    @Override
    public PerForeignKeyTemplatesFeatureTemplate createTemplate(@NotNull final GlobalTemplateContext context)
    {
        return new PerForeignKeyTemplatesFeatureTemplate(context);
    }

    /**
     * Retrieves the file name of the template.
     * @param repository the repository name.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @return the file name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String repository, @NotNull final MetadataManager metadataManager)
    {
        return retrieveTemplateFileName(repository, metadataManager.getEngine());
    }

    /**
     * Retrieves the file name of the template.
     * @param repository the repository name.
     * @param engine the {@link org.acmsl.queryj.metadata.engines.Engine} instance.
     * @return the file name.
     */
    @NotNull
    public String retrieveTemplateFileName(
        @NotNull final String repository, @NotNull final Engine<String> engine)
    {
        @NotNull final String result;

        @NotNull final ST template =
            new ST("PerForeignKeyTemplates.feature");

        template.add(Literals.REPOSITORY, new DecoratedString(repository));
        template.add(org.acmsl.queryj.Literals.ENGINE, new EngineDecorator(engine));

        result = template.render();

        return result;
    }
}
