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
 * Filename: AttributeStatementSetterDecoratorFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides custom decorators for the AttributeStatementSetter
 *              template.
 *
 * $Id :$
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.PropertyDecorator;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * Provides custom decorators for the AttributeStatementSetter template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class AttributeStatementSetterDecoratorFactory
    extends  CachingDecoratorFactory
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class AttributeStatementSetterDecoratorFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final AttributeStatementSetterDecoratorFactory SINGLETON =
            new AttributeStatementSetterDecoratorFactory();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected AttributeStatementSetterDecoratorFactory() {};

    /**
     * Retrieves a <code>AttributeStatementSetterDecoratorFactory</code> instance.
     * @return such instance.
     */
    @NotNull
    public static CachingDecoratorFactory getInstance()
    {
        return
            AttributeStatementSetterDecoratorFactorySingletonContainer
                .SINGLETON;
    }

    /**
     * Creates an <code>AttributeDecorator</code> for given
     * attribute instance.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated attribute for the concrete template.
     */
    @NotNull
    public AttributeDecorator createDecorator(
        final Attribute attribute, final MetadataManager metadataManager)
    {
        return
            new AttributeStatementSetterAttributeDecorator(
                attribute, metadataManager);
    }
}
