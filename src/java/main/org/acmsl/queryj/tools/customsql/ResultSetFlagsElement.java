/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <resultSet-flags> elements in custom-sql models.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

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
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class ResultSetFlagsElement
    extends  AbstractIdElement
{
    /**
     * The <b>TYPE_FORWARD_ONLY</b> value for
     * <i>type</i> attribute.
     */
    public static final String TYPE_FORWARD_ONLY = "TYPE_FORWARD_ONLY";

    /**
     * The <b>TYPE_SCROLL_INSENSITIVE</b> value for
     * <i>type</i> attribute.
     */
    public static final String TYPE_SCROLL_INSENSITIVE = "TYPE_SCROLL_INSENSITIVE";

    /**
     * The <b>TYPE_SCROLL_SENSITIVE</b> value for
     * <i>type</i> attribute.
     */
    public static final String TYPE_SCROLL_SENSITIVE = "TYPE_SCROLL_SENSITIVE";

    /**
     * The <b>CONCUR_READ_ONLY</b> value for
     * <i>concurrency</i> attribute.
     */
    public static final String CONCUR_READ_ONLY = "CONCUR_READ_ONLY";

    /**
     * The <b>CONCUR_UPDATABLE</b> value for
     * <i>concurrency</i> attribute.
     */
    public static final String CONCUR_UPDATABLE = "CONCUR_UPDATABLE";

    /**
     * The <b>HOLD_CURSORS_OVER_COMMIT</b> value for
     * <i>holdability</i> attribute.
     */
    public static final String HOLD_CURSORS_OVER_COMMIT = "HOLD_CURSORS_OVER_COMMIT";

    /**
     * The <b>CLOSE_CURSORS_AT_COMMIT</b> value for
     * <i>holdability</i> attribute.
     */
    public static final String CLOSE_CURSORS_AT_COMMIT = "CLOSE_CURSORS_AT_COMMIT";

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
     * @precondition id != null
     */
    public ResultSetFlagsElement(
        final String id,
        final String type,
        final String concurrency,
        final String holdability)
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
    protected final void immutableSetType(final String value)
    {
        m__strType = value;
    }

    /**
     * Specifies the <i>type</i> attribute.
     * @param value such value.
     */
    protected void setType(final String value)
    {
        immutableSetType(value);
    }

    /**
     * Retrieves the <i>type</i> attribute.
     * @return such value.
     */
    public String getType()
    {
        return m__strType;
    }

    /**
     * Specifies the <i>concurrency</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetConcurrency(final String value)
    {
        m__strConcurrency = value;
    }

    /**
     * Specifies the <i>concurrency</i> attribute.
     * @param value such value.
     */
    protected void setConcurrency(final String value)
    {
        immutableSetConcurrency(value);
    }

    /**
     * Retrieves the <i>concurrency</i> attribute.
     * @return such value.
     */
    public String getConcurrency()
    {
        return m__strConcurrency;
    }

    /**
     * Specifies the <i>holdability</i> attribute.
     * @param value such value.
     */
    protected final void immutableSetHoldability(final String value)
    {
        m__strHoldability = value;
    }

    /**
     * Specifies the <i>holdability</i> attribute.
     * @param value such value.
     */
    protected void setHoldability(final String value)
    {
        immutableSetHoldability(value);
    }

    /**
     * Retrieves the <i>holdability</i> attribute.
     * @return such value.
     */
    public String getHoldability()
    {
        return m__strHoldability;
    }

    /**
     * Provides a text information about this instance.
     * @return such information.
     */
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
    protected String toString(
        final String id,
        final String type,
        final String concurrency,
        final String holdability)
    {
        return
              getClass().getName()
            + "[" + "id=" + id + "]"
            + "[" + "concurrency=" + concurrency + "]"
            + "[" + "holdability=" + holdability + "]";
    }
}
