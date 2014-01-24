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
 * Filename: ResultSetFlagsElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <resultSet-flags> elements in custom-sql models.
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
 * Models &lt;resultSet-flags&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 * <!ELEMENT resultset-flags EMPTY><br/>
 * <!ATTLIST resultset-flags<br/>
 *   id ID #REQUIRED<br/>
 *   type (TYPE_FORWARD_ONLY | TYPE_SCROLL_INSENSITIVE | TYPE_SCROLL_SENSITIVE) #IMPLIED<br/>
 *   concurrency (CONCUR_READ_ONLY | CONCUR_UPDATABLE) #IMPLIED<br/>
 *   holdability (HOLD_CURSORS_OVER_COMMIT | CLOSE_CURSORS_AT_COMMIT) #IMPLIED><br/>
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ResultSetFlagsElement
    extends  AbstractIdElement<String>
    implements ResultSetFlags
{
    private static final long serialVersionUID = 864474291474631746L;

    /**
     * The <i>type</i> attribute.
     */
    public String m__strType;

    /**
     * The <i>concurrency</i> attribute.
     */
    public String m__strConcurrency;

    /**
     * The <i>holdability</i> attribute.
     */
    public String m__strHoldability;

    /**
     * Creates a ResultSetFlagsElement with given information.
     * @param id the <i>id</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param concurrency the <i>concurrency</i> attribute.
     * @param holdability the <i>holdability</i> attribute.
     */
    public ResultSetFlagsElement(
        @NotNull final String id,
        @NotNull final String type,
        @NotNull final String concurrency,
        @NotNull final String holdability)
    {
        super(id);
        immutableSetType(type);
        immutableSetConcurrency(concurrency);
        immutableSetHoldability(holdability);
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetType(@NotNull final String value)
    {
        m__strType = value;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setType(@NotNull final String value)
    {
        immutableSetType(value);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies the <i>concurrency</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetConcurrency(@NotNull final String value)
    {
        m__strConcurrency = value;
    }

    /**
     * Specifies the <i>concurrency</i> attribute.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setConcurrency(@NotNull final String value)
    {
        immutableSetConcurrency(value);
    }

    /**
     * Retrieves the <i>concurrency</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public String getConcurrency()
    {
        return m__strConcurrency;
    }

    /**
     * Specifies the <i>holdability</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetHoldability(@NotNull final String value)
    {
        m__strHoldability = value;
    }

    /**
     * Specifies the <i>holdability</i> attribute.
     * @param value such value.
     */
    @SuppressWarnings("unused")
    protected void setHoldability(@NotNull final String value)
    {
        immutableSetHoldability(value);
    }

    /**
     * Retrieves the <i>holdability</i> attribute.
     * @return such value.
     */
    @Override
    @Nullable
    public String getHoldability()
    {
        return m__strHoldability;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            toString(
                getId(),
                getType(),
                getConcurrency(),
                getHoldability());
    }

    /**
     * Provides a text information about this instance.
     * @param id the <i>id</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param concurrency the <i>concurrency</i> attribute.
     * @param holdability the <i>holdability</i> attribute.
     * @return such information.
     */
    @NotNull
    protected String toString(
        @NotNull final String id,
        @Nullable final String type,
        @Nullable final String concurrency,
        @Nullable final String holdability)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "type=" + type + "]"
            + "[" + "concurrency=" + concurrency + "]"
            + "[" + "holdability=" + holdability + "]";
    }
}
