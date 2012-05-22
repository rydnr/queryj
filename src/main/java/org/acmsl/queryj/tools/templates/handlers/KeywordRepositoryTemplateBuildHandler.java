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
 * Filename: KeywordRepositoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a keyword repository from declared keyword definition.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateContext;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplate;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds a keyword repository from declared keyword definition.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class KeywordRepositoryTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
                 <KeywordRepositoryTemplate, KeywordRepositoryTemplateGenerator, BasePerRepositoryTemplateContext>
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    @Override
    protected KeywordRepositoryTemplateGenerator retrieveTemplateFactory()
    {
        return KeywordRepositoryTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected KeywordRepositoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final KeywordRepositoryTemplateGenerator templateFactory,
        @NotNull final String projectPackage,
        @NotNull final String packageName,
        @NotNull final String repository,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final List<String> tableNames,
        @NotNull final String jndiLocation,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        @Nullable KeywordRepositoryTemplate result =
            templateFactory.createTemplate(
                metadataManager,
                customSqlProvider,
                packageName,
                projectPackage,
                repository,
                header,
                implementMarkerInterfaces,
                jmx,
                tableNames,
                jndiLocation);

        if (result != null)
        {
            @Nullable Collection t_cFieldElements;

            @Nullable AntExternallyManagedFieldsElement t_ExternallyManagedFieldsElement =
                retrieveExternallyManagedFieldsElement(parameters);

            if  (t_ExternallyManagedFieldsElement != null)
            {
                StringValidator t_StringValidator =
                    StringValidator.getInstance();
                
                t_cFieldElements =
                    t_ExternallyManagedFieldsElement.getFields();

                if  (   (t_cFieldElements != null)
                     && (t_cFieldElements.size() > 0))
                {
                    Iterator t_itFieldIterator =
                        t_cFieldElements.iterator();

                    while  (   (t_itFieldIterator != null)
                            && (t_itFieldIterator.hasNext()))
                    {
                        @Nullable AntFieldElement t_Field =
                            (AntFieldElement) t_itFieldIterator.next();

                        if  (t_Field != null)
                        {
                            if  (!t_StringValidator.isEmpty(t_Field.getKeyword()))
                            {
                                result.addKeyword(
                                    t_Field.getKeyword(),
                                    metadataManager.getMetadataTypeManager().getQueryJFieldType(
                                        metadataManager.getColumnType(
                                            t_Field.getTableName(),
                                            t_Field.getName()),
                                        metadataManager.isBoolean(
                                            t_Field.getTableName(),
                                            t_Field.getName())));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the externally-managed-fields XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the externally-managed-fields information.
     * @precondition parameters != null
     */
    @Nullable
    @SuppressWarnings("unchecked")
    protected AntExternallyManagedFieldsElement retrieveExternallyManagedFieldsElement(
        @NotNull final Map parameters)
    {
        return
            (AntExternallyManagedFieldsElement)
                parameters.get(
                    ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveKeywordRepositoryPackage(projectPackage);
    }

    /**
     * {@inheritDoc
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void storeTemplate(
        @NotNull final KeywordRepositoryTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.KEYWORD_REPOSITORY_TEMPLATE,
            template);
    }
}
