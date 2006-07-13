//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.customsql;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractIdElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Models &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface Result
    extends  IdentifiableElement
{
    /**
     * The <b>none</b> value for <i>matches</i> attribute.
     */
    public static final String NONE = "none";

    /**
     * The <b>single</b> value for <i>matches</i> attribute.
     */
    public static final String SINGLE = "single";

    /**
     * The <b>multiple</b> value for <i>multiple</i> attribute.
     */
    public static final String MULTIPLE = "multiple";

    /**
     * Retrieves the <i>class</i> attribute.
     * @return such value.
     */
    public String getClassValue();

    /**
     * Retrieves the <i>matches</i> attribute.
     * @return such value.
     */
    public String getMatches();

    /**
     * Retrieves the &lt;property-ref&gt; elements.
     * @return such elements.
     */
    public Collection getPropertyRefs();

    /**
     * Adds a new &lt;property-ref&gt; element.
     * @param propertyRef such element.
     */
    public void add(final PropertyRefElement propertyRef);
}
