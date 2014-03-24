/*
                        QueryJ Core

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
 * Filename: ResultRefElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <result-ref> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models &lt;result-ref&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  &lt;!ELEMENT result-ref EMPTY&gt;
 *  &lt;!ATTLIST result-ref
 *    id IDREF #REQUIRED&gt;
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ResultRefElement
    extends  AbstractIdElement<String>
    implements ResultRef
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4772977255913187237L;

    /**
     * Creates a ResultRefElement with given information.
     * @param id the <i>id</i> attribute.
     */
    public ResultRefElement(@NotNull final String id)
    {
        super(id);
    }
}
