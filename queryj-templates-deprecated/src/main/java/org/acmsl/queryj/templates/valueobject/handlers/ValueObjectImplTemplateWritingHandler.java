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
 * Filename: ValueObjectImplTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ValueObject templates.
 *
 */
package org.acmsl.queryj.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.api.PerTableTemplateContext;
import org.acmsl.queryj.templates.valueobject.ValueObjectImplTemplate;
import org.acmsl.queryj.templates.valueobject.ValueObjectImplTemplateGenerator;
import org.acmsl.queryj.api.handlers.BasePerTableTemplateWritingHandler;
import org.acmsl.queryj.api.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Writes <code>ValueObjectImplTemplate</code> instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ValueObjectImplTemplateWritingHandler
    extends  BasePerTableTemplateWritingHandler
                 <ValueObjectImplTemplate, ValueObjectImplTemplateGenerator, PerTableTemplateContext>
{
    /**
     * Creates a <code>ValueObjectImplTemplateWritingHandler</code> instance.
     */
    public ValueObjectImplTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected ValueObjectImplTemplateGenerator retrieveTemplateGenerator(final boolean caching, final int threadCount)
    {
        return new ValueObjectImplTemplateGenerator(caching, threadCount);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<ValueObjectImplTemplate> retrieveTemplates(@NotNull final Map parameters)
    {
        return
            (List<ValueObjectImplTemplate>)
                parameters.get(
                    TemplateMappingManager.VALUE_OBJECT_IMPL_TEMPLATES);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected File retrieveOutputDir(
        @NotNull final File projectFolder,
        @NotNull final String projectPackage,
        final boolean useSubfolders,
        @NotNull final String tableName,
        @NotNull final String engineName,
        @NotNull final Map parameters,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveValueObjectImplFolder(
                projectFolder, projectPackage, useSubfolders);
    }
}
