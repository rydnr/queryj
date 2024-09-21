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
 * Filename: PropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Property;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Decorator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface PropertyDecorator
    extends  Property<DecoratedString>,
             Decorator
{
    /**
     * Retrieves the property.
     * @return such instance.
     */
    @NotNull
    Property<String> getProperty();

    /**
    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    boolean isNumberSmallerThanInt();

    /**
     * Retrieves the Object type of the property.
     * @return such information.
     */
    @NotNull
    public DecoratedString getObjectType();

    /**
     * Retrieves the Java type.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public DecoratedString getJavaType();

    /**
     * Checks whether its type is supported out-of-the-box.
     * @return {@code true} in such case.
     */
    public boolean isTypeSupportedOutOfTheBox();
}
