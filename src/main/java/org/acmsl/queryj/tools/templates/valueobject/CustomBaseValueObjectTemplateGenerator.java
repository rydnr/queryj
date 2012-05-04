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
 * Filename: CustomBaseValueObjectTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom BaseValueObject templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate custom BaseValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomBaseValueObjectTemplateGenerator
    extends  CustomValueObjectTemplateGenerator
    implements  Singleton
{
    /**
     * Default constructor.
     */
    public CustomBaseValueObjectTemplateGenerator() {}

    /**
     * Generates a CustomValueObject template.
     * @param customResult the custom result.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @return the new template.
     * @precondition resultElement != null
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition basePackageName != null
     * @precondition repositoryName != null
     */
    @Override
    @Nullable
    public CustomValueObjectTemplate createTemplate(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header)
    {
        @Nullable CustomBaseValueObjectTemplate result = null;

        if  (!isStandard(
                 extractClassName(customResult.getClassValue()),
                 metadataManager))
        {
            result =
                new CustomBaseValueObjectTemplate(
                    customResult,
                    customSqlProvider,
                    metadataManager,
                    header,
                    getDecoratorFactory(),
                    packageName,
                    engineName,
                    engineVersion,
                    basePackageName,
                    repositoryName);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String retrieveTemplateFileName(@NotNull final CustomValueObjectTemplate template)
    {
        return
              "Abstract"
            + extractClassName(template.getResult().getClassValue())
            + "ValueObject.java";
    }
}
