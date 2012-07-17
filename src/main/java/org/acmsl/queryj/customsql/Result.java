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
 * Filename: Result.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Models &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface Result
    extends  IdentifiableElement,
             java.lang.Comparable
{
    /**
     * The <b>none</b> value for <i>matches</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String NONE = "none";

    /**
     * The <b>single</b> value for <i>matches</i> attribute.
     */
    @SuppressWarnings("unused")
    static final String SINGLE = "single";

    /**
     * The <b>multiple</b> value for <i>multiple</i> attribute.
     */
    static final String MULTIPLE = "multiple";

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    @NotNull
    String getClassValue();

    /**
     * Retrieves the <i>matches</i> attribute.
     * @return such value.
     */
    @NotNull
    String getMatches();

    /**
     * Retrieves the &lt;property-ref&gt; elements.
     * @return such elements.
     */
    @NotNull
    List<PropertyRef> getPropertyRefs();

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     */
    public void add(@NotNull final PropertyRef propertyRef);

}
