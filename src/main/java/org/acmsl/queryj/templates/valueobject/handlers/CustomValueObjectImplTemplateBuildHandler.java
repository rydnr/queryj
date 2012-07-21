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
 * Filename: CustomValueObjectImplTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a custom ValueObjectImpl template.
 *
 */
package org.acmsl.queryj.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.handlers.BasePerCustomResultTemplateBuildHandler;
import org.acmsl.queryj.templates.TemplateMappingManager;
import org.acmsl.queryj.templates.valueobject.CustomValueObjectImplTemplate;
import org.acmsl.queryj.templates.valueobject.CustomValueObjectImplTemplateFactory;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Builds custom ValueObjectImpl templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomValueObjectImplTemplateBuildHandler
    extends BasePerCustomResultTemplateBuildHandler
                <CustomValueObjectImplTemplate, CustomValueObjectImplTemplateFactory>
{
    /**
     * Creates a CustomValueObjectImplTemplateBuildHandler.
     */
    public CustomValueObjectImplTemplateBuildHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected CustomValueObjectImplTemplateFactory retrieveTemplateFactory()
    {
        return CustomValueObjectImplTemplateFactory.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final List<CustomValueObjectImplTemplate> templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.CUSTOM_VALUE_OBJECT_IMPL_TEMPLATES,
            templates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    protected String retrievePackage(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveValueObjectPackage(
                projectPackage);
    }

    /**
     * Checks whether the generation is allowed for given result.
     *
     * @param customResult      the custom result.
     * @param customSqlProvider the {@link org.acmsl.queryj.customsql.CustomSqlProvider} instance.
     * @param metadataManager   the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param customResultUtils the {@link org.acmsl.queryj.customsql.CustomResultUtils} instance.
     * @return <code>true</code> in such case.
     */
    @Override
    protected boolean isGenerationAllowedForResult(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return !customResultUtils.isImplicit(customResult, customSqlProvider, metadataManager);
    }
}
