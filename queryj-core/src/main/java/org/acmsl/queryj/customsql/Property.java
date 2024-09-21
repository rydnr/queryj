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
 * Filename: Property.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Models &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface Property<T>
    extends  IdentifiableElement<T>,
             Comparable<Property<T>>
{
    /**
     * Retrieves the <i>column_name</i> attribute.
     * @return such information.
     */
    @NotNull
    T getColumnName();

    /**
     * Retrieves the <i>index</i> attribute.
     * @return such information.
     */
    int getIndex();

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such information.
     */
    @NotNull
    T getType();

    /**
     * Retrieves whether the property is nullable or not.
     * @return such condition.
     */
    boolean isNullable();
}
