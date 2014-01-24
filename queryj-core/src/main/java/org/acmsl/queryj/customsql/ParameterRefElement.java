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
 * Filename: ParameterRefElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <parameter-ref> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * Models &lt;parameter-ref&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  <!ELEMENT parameter-ref EMPTY>
 *  <!ATTLIST parameter-ref
 *    id IDREF #REQUIRED>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ParameterRefElement
    extends  AbstractIdElement<String>
    implements ParameterRef
{
    private static final long serialVersionUID = 6158747761881841323L;

    /**
     * Creates a ParameterRefElement with given information.
     * @param id the <i>id</i> attribute.
     */
    public ParameterRefElement(@NotNull final String id)
    {
        super(id);
    }
}
