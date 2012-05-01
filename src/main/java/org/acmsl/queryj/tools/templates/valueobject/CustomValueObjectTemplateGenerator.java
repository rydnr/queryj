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
 * Filename: CustomValueObjectTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom ValueObject templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.AbstractTemplateGenerator;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;

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
 * Is able to generate custom ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomValueObjectTemplateGenerator<T extends CustomValueObjectTemplate>
    extends AbstractTemplateGenerator<T>
    implements  BasePerCustomResultTemplateFactory<T>,
                Singleton
{
    /**
     * Protected constructor to avoid accidental instantiation.
     */
    public CustomValueObjectTemplateGenerator() {}

    /**
     * Retrieves a {@link CustomValueObjectTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static CustomValueObjectTemplateGenerator getInstance()
    {
        return new CustomValueObjectTemplateGenerator();
    }

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
    @Nullable
    public T createTemplate(
        @NotNull final Result customResult,
        final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName,
        final String header)
    {
        @Nullable T result = null;

        if  (!isStandard(
                 extractClassName(customResult.getClassValue()),
                 metadataManager))
        {
            result =
                (T)
                new CustomValueObjectTemplate(
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
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @Override
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return CustomValueObjectDecoratorFactory.getInstance();
    }

    /**
     * Checks whether the given class name corresponds to
     * a standard value object or not.
     * @param className the class name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return <code>true</code> in such case.
     * @precondition className != null
     * @precondition metadataManager != null
     */
    protected boolean isStandard(
        @NotNull final String className, @NotNull final MetadataManager metadataManager)
    {
        return
            isStandard(
                className,
                metadataManager,
                ValueObjectTemplateGenerator.getInstance());
    }

    /**
     * Checks whether the given class name corresponds to
     * a standard value object or not.
     * @param className the class name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param valueObjectTemplateGenerator the
     * {@link ValueObjectTemplateGenerator} instance.
     * @return <code>true</code> in such case.
     * @precondition className != null
     * @precondition metadataManager != null
     * @precondition valueObjectTemplateGenerator != null
     */
    protected boolean isStandard(
        @NotNull final String className,
        @NotNull final MetadataManager metadataManager,
        @NotNull final ValueObjectTemplateGenerator valueObjectTemplateGenerator)
    {
        boolean result = false;

        String[] t_astrTableNames = metadataManager.getTableNames();

        String t_strVoClassName;

        for (String t_strTableName : t_astrTableNames)
        {
            if (t_strTableName != null)
            {
                t_strVoClassName =
                    valueObjectTemplateGenerator.getVoClassName(
                        t_strTableName);

                if (   (t_strVoClassName != null)
                    && ((t_strVoClassName.equals(className)))
                    || ((t_strVoClassName + "ValueObject").equals(className))
                    || ((className + "ValueObject").equals(t_strVoClassName)))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @return the class name.
     * @precondition fqcn != null
     */
    @Nullable
    public String extractClassName(@NotNull final String fqcn)
    {
        return extractClassName(fqcn, PackageUtils.getInstance());
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return the class name.
     * @precondition fqcn != null
     * @precondition packageUtils != null
     */
    @Nullable
    protected String extractClassName(
        final String fqcn, @NotNull final PackageUtils packageUtils)
    {
        return packageUtils.extractClassName(fqcn);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final  T template)
    {
        return
            extractClassName(template.getResult().getClassValue())
            + ".java";
    }
}
