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
 * Filename: ResultDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Result;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Decorator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Decorates &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface ResultDecorator<V>
    extends  Result<DecoratedString>,
             Decorator
{
    /**
     * Retrieves the result.
     * @return such element.
     */
    @NotNull
    Result<V> getResult();

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    List<Property<DecoratedString>> getProperties();

    /**
     * Retrieves the property types.
     * @return such information.
     */
    @NotNull
    List<DecoratedString> getPropertyTypes();

    /**
     * Retrieves the large-object-block properties.
     * @return such collection.
     */
    @NotNull
    List<Property<DecoratedString>> getLobProperties();

    /**
     * Retrieves whether this {@link Result} is wrapping a single {@link Property}.
     * @return <code>true</code> in such case.
     */
    boolean isWrappingASingleProperty();
}