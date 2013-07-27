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
 * Filename: CustomRowMapperDecoratorFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides custom decorators for the CustomRowMapperTemplate
 *              template.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.PropertyDecorator;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides custom decorators for the {@link CustomRowMapperTemplate} template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CustomRowMapperDecoratorFactory
    extends  CachingDecoratorFactory
{
    private static final long serialVersionUID = -9156594021731628694L;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    protected static class CustomRowMapperDecoratorFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomRowMapperDecoratorFactory SINGLETON =
            new CustomRowMapperDecoratorFactory();
    }
    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomRowMapperDecoratorFactory() {};

    /**
     * Retrieves a {@link CustomRowMapperDecoratorFactory}
     * instance.
     * @return such instance.
     */
    @NotNull
    public static CachingDecoratorFactory getInstance()
    {
        return
            CustomRowMapperDecoratorFactorySingletonContainer
                .SINGLETON;
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the attribute.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return the decorated property for the concrete template.
     */
    @NotNull
    public PropertyDecorator createDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
    {
        return new CustomRowMapperPropertyDecorator(property, metadataManager);
    }
}
