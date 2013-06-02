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
 * Filename: CustomValueObjectTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create CustomValueObjectTemplate instances.
 *
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing some project-specific classes.
 */

import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateFactory;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;

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
 * Is able to create {@link CustomValueObjectTemplate} instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/09
 */
@ThreadSafe
public class CustomValueObjectTemplateFactory
    implements  BasePerCustomResultTemplateFactory<CustomValueObjectTemplate>,
                Singleton
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class CustomValueObjectTemplateFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomValueObjectTemplateFactory SINGLETON = new CustomValueObjectTemplateFactory();
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return such instance.
     */
    @NotNull
    public static CustomValueObjectTemplateFactory getInstance()
    {
        return CustomValueObjectTemplateFactorySingletonContainer.SINGLETON;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    public CustomValueObjectTemplate createTemplate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
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
        @NotNull final Result customResult)
    {
        @Nullable CustomValueObjectTemplate result = null;

        @Nullable final String t_strClassName = customResult.getClassValue();

        if  (   (t_strClassName != null)
             && (!isStandard(
                 extractClassName(t_strClassName),
                 metadataManager)))
        {
            result =
                new CustomValueObjectTemplate(
                    new BasePerCustomResultTemplateContext(
                        metadataManager,
                        customSqlProvider,
                        header,
                        decoratorFactory,
                        packageName,
                        basePackageName,
                        repositoryName,
                        implementMarkerInterfaces,
                        jmx,
                        jndiLocation,
                        disableGenerationTimestamps,
                        disableNotNullAnnotations,
                        disableCheckthreadAnnotations,
                        customResult));
        }

        return result;
    }

    /**
     * Checks whether given class name corresponds to a standard ValueObject or not.
     * @param className the class name.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @return <code>true</code> if the class name is associated to a standard value object.
     */
    protected boolean isStandard(@NotNull final String className, @NotNull final MetadataManager metadataManager)
    {
        return isStandard(className, metadataManager, ValueObjectUtils.getInstance());

    }

    /**
     * Checks whether given class name corresponds to a standard ValueObject or not.
     * @param className the class name.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @param valueObjectUtils the {@link org.acmsl.queryj.templates.valueobject.ValueObjectUtils} instance.
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
