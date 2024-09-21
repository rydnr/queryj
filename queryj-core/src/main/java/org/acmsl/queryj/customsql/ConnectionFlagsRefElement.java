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
 * Filename: ConnectionFlagsRefElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <connection-flags-ref> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing project-specific classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Models &lt;connection-flags-ref&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  &lt;!ELEMENT connection-flags-ref EMPTY&gt;
 *  &lt;!ATTLIST connection-flags-ref
 *    id IDREF #REQUIRED&gt;
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ConnectionFlagsRefElement
    extends  AbstractIdElement<String>
    implements ConnectionFlagsRef
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 3311191419084681233L;

    /**
     * Creates a ConnectionFlagsRefElement with given information.
     * @param id the <i>id</i> attribute.
     */
    public ConnectionFlagsRefElement(@NotNull final String id)
    {
        super(id);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-1682907427, 900308273)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if  (object instanceof ConnectionFlagsRefElement)
        {
            @NotNull final ConnectionFlagsRefElement t_OtherInstance =
                (ConnectionFlagsRefElement) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                .isEquals();
        }

        return result;
    }
}
