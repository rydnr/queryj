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
 * Filename: CustomValueObjectImplTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate custom ValueObjectImpl templates.
 *
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.PerCustomResultTemplateContext;
import org.acmsl.queryj.api.PerCustomResultTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to generate custom ValueObjectImpl templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomValueObjectImplTemplateGenerator
    extends AbstractTemplateGenerator<CustomValueObjectImplTemplate, PerCustomResultTemplateContext>
    implements PerCustomResultTemplateGenerator<CustomValueObjectImplTemplate, PerCustomResultTemplateContext>
{
    /**
     * Creates a new {@link CustomBaseValueObjectTemplateGenerator} with given settings.
     * @param caching whether to enable caching.
     * @param threadCount the number of threads to use.
     */
    public CustomValueObjectImplTemplateGenerator(final boolean caching, final int threadCount)
    {
        super(caching, threadCount);
    }

    /**
     * Creates a per-<i>custom result</i> template.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces or not.
     * @param jmx whether to include JMX support.
     * @param jndiLocation the JNDI path for the {@link javax.sql.DataSource}.
     * @param disableGenerationTimestamps whether to disable generation timestamps.
     * @param disableNotNullAnnotations whether to disable NotNull annotations.
     * @param disableCheckthreadAnnotations whether to disable checkthread.org annotations or not.
     * @param fileName the file name.
     * @param customResult the custom result.
     * @return the template.
     */
    @Nullable
    public CustomValueObjectImplTemplate createTemplate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final String packageName,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final String jndiLocation,
        final boolean disableGenerationTimestamps,
        final boolean disableNotNullAnnotations,
        final boolean disableCheckthreadAnnotations,
        @NotNull final String fileName,
        @NotNull final Result customResult)
    {
        @Nullable CustomValueObjectImplTemplate result = null;

        @Nullable final String classValue = customResult.getClassValue();

        if  (  (classValue != null)
            && (!isStandard(extractClassName(classValue), metadataManager)))
        {
            result =
                new CustomValueObjectImplTemplate(
                    new PerCustomResultTemplateContext(
                        metadataManager,
                        customSqlProvider,
                        header,
                        getDecoratorFactory(),
                        packageName,
                        basePackageName,
                        repositoryName,
                        implementMarkerInterfaces,
                        jmx,
                        jndiLocation,
                        disableGenerationTimestamps,
                        disableNotNullAnnotations,
                        disableCheckthreadAnnotations,
                        fileName,
                        customResult));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String retrieveTemplateFileName(@NotNull final PerCustomResultTemplateContext context)
    {
        String result = "";

        @Nullable final String classValue = context.getResult().getClassValue();

        if (classValue != null)
        {
            result = extractClassName(classValue) + "ValueObjectImpl.java";
        }

        return result;
    }


    /**
     * Checks whether given class name corresponds to a standard ValueObject or not.
     * @param className the class name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return <code>true</code> if the class name is associated to a standard value object.
     */
    protected boolean isStandard(@NotNull final String className, @NotNull final MetadataManager metadataManager)
    {
        return isStandard(className, metadataManager, ValueObjectUtils.getInstance());

    }

    /**
     * Checks whether given class name corresponds to a standard ValueObject or not.
     * @param className the class name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param valueObjectUtils the {@link ValueObjectUtils} instance.
     * @return <code>true</code> if the class name is associated to a standard value object.
     */
    protected boolean isStandard(
        @NotNull final String className,
        @NotNull final MetadataManager metadataManager,
        @NotNull final ValueObjectUtils valueObjectUtils)
    {
        return valueObjectUtils.isStandard(className, metadataManager);

    }

    /**
     * Extracts the class name.
     * @param classValue the class value.
     */
    @NotNull
    protected String extractClassName(@NotNull final String classValue)
    {
        return extractClassName(classValue, ValueObjectUtils.getInstance());
    }

    /**
     * Extracts the class name.
     * @param classValue the class value.
     */
    @NotNull
    protected String extractClassName(
        @NotNull final String classValue, @NotNull final ValueObjectUtils valueObjectUtils)
    {
        return valueObjectUtils.extractClassName(classValue);
    }
}
