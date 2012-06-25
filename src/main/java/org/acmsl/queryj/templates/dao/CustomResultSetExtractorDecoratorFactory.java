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
 * Filename: CustomResultSetExtractorDecoratorFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides custom decorators for the CustomResultSetExtractor
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

/**
 * Provides custom decorators for the CustomResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomResultSetExtractorDecoratorFactory
    extends  CachingDecoratorFactory
{
    private static final long serialVersionUID = 1470687956486903790L;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CustomResultSetExtractorDecoratorFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CustomResultSetExtractorDecoratorFactory SINGLETON =
            new CustomResultSetExtractorDecoratorFactory();
    }
    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomResultSetExtractorDecoratorFactory() {};

    /**
     * Retrieves a <code>CustomResultSetExtractorDecoratorFactory</code>
     * instance.
     * @return such instance.
     */
    @NotNull
    public static CachingDecoratorFactory getInstance()
    {
        return
            CustomResultSetExtractorDecoratorFactorySingletonContainer
                .SINGLETON;
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    @NotNull
    public PropertyDecorator createDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
    {
        return
            new CustomResultSetExtractorPropertyDecorator(
                property, metadataManager);
    }
}
