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
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplate;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateFactory;
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
import java.util.Map;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds a keyword repository from declared keyword definition.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class KeywordRepositoryTemplateBuildHandler
    extends  BasePerRepositoryTemplateBuildHandler
{
    /**
     * Retrieves the per-repository template factory.
     * @return such instance.
     */
    @NotNull
    protected BasePerRepositoryTemplateFactory retrieveTemplateFactory()
    {
        return KeywordRepositoryTemplateGenerator.getInstance();
    }

    /**
     * Uses the factory to create the template.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param templateFactory the template factory.
     * @param packageName the package name.
     * @param projectPackage the base package.
     * @param repository the repository.
     * @param engineName the engine name.
     * @param tableNames the table names.
     * @param header the header.
     * @return the template.
     * @throws QueryJBuildException on invalid input.
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition customSqlProvider != null
     * @precondition packageName != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition engineName != null
     * @precondition tableNames != null
     * @precondition factory != null
     */
    @Nullable
    protected BasePerRepositoryTemplate createTemplate(
        @NotNull final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final BasePerRepositoryTemplateFactory templateFactory,
        final String projectPackage,
        final String packageName,
        final String repository,
        final String engineName,
        final String header,
        final Collection tableNames,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        @Nullable KeywordRepositoryTemplate result = null;

        if  (templateFactory instanceof KeywordRepositoryTemplateFactory)
        {
            result =
                (KeywordRepositoryTemplate)
                    ((KeywordRepositoryTemplateFactory) templateFactory)
                        .createTemplate(
                            metadataManager,
                            metadataTypeManager,
                            customSqlProvider,
                            packageName,
                            projectPackage,
                            repository,
                            engineName,
                            header);

            @Nullable Collection t_cFieldElements = null;

            @Nullable AntExternallyManagedFieldsElement
                t_ExternallyManagedFieldsElement =
                    retrieveExternallyManagedFieldsElement(parameters);

            if  (t_ExternallyManagedFieldsElement != null)
            {
                MetadataTypeManager t_MetadataTypeManager =
                    metadataManager.getMetadataTypeManager();

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
                            if  (!t_StringValidator.isEmpty(
                                     t_Field.getKeyword()))
                            {
                                result.addKeyword(
                                    t_Field.getKeyword(),
                                    t_MetadataTypeManager.getQueryJFieldType(
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
        else
        {
            LogFactory.getLog(BasePerRepositoryTemplateBuildHandler.class).warn(
                  "Unexpected BasePerRepository factory. "
                + "Expecting KeywordRepositoryTemplateFactory.");
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
    @NotNull
    protected AntExternallyManagedFieldsElement
        retrieveExternallyManagedFieldsElement(@NotNull final Map parameters)
    {
        return
            (AntExternallyManagedFieldsElement)
                parameters.get(
                    ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS);
    }

    /**
     * Retrieves the package name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String engineName,
        final String projectPackage,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveKeywordRepositoryPackage(
                projectPackage);
    }

    /**
     * Stores the template in given attribute map.
     * @param template the template.
     * @param parameters the parameter map.
     * @precondition template != null
     * @precondition parameters != null
     */
    protected void storeTemplate(
        final BasePerRepositoryTemplate template, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.KEYWORD_REPOSITORY_TEMPLATE,
            template);
    }
}
