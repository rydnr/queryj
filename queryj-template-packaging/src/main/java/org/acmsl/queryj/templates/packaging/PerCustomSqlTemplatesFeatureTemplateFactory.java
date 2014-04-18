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
 * Filename: PerCustomSqlTemplatesFeatureTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Factory for PerCustomSqlTemplatesFeatureTemplates.
 *
 * Date: 2014/04/16
 * Time: 15:53
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing ACM S.L Java Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing JetBrains annotations.
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
 * Importing StringTemplate 4 classes.
 */
import org.stringtemplate.v4.ST;

/**
 * Factory for {@link PerCustomSqlTemplatesFeatureTemplate}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/04/16 15:53
 */
@ThreadSafe
public class PerCustomSqlTemplatesFeatureTemplateFactory
    implements TemplatePackagingTemplateFactory<PerCustomSqlTemplatesFeatureTemplate, GlobalTemplateContext>,
               Singleton
{
    /**
     * Singleton instance to avoid double-locking check.
     */
    protected static final class PerSqlTemplatesFeatureTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final PerCustomSqlTemplatesFeatureTemplateFactory SINGLETON =
            new PerCustomSqlTemplatesFeatureTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static PerCustomSqlTemplatesFeatureTemplateFactory getInstance()
    {
        return PerSqlTemplatesFeatureTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * Generates a template.
     *
     * @param context the context.
     * @return such template.
     */
    @Nullable
    @Override
    public PerCustomSqlTemplatesFeatureTemplate createTemplate(@NotNull final GlobalTemplateContext context)
    {
        return new PerCustomSqlTemplatesFeatureTemplate(context);
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
            new ST("PerCustomSqlTemplates.feature");

        template.add(Literals.REPOSITORY, new DecoratedString(repository));
        template.add(org.acmsl.queryj.Literals.ENGINE, new EngineDecorator(engine));

        result = template.render();

        return result;
    }
}
