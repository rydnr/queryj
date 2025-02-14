//,-*- mode: java -*-
/*
                        QueryJ Core

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software, you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation, either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY, without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library, if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: Literals.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains literals for org.acmsl.queryj.tools.ant.jdbc package.
 *
 */
package org.acmsl.queryj.tools.ant.jdbc;

/*
 * Importing Nullable annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Contains literals for org.acmsl.queryj.tools.ant.jdbc package.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public enum Literals
{
    /**
     * String literal: "field".
     */
    FIELD("field"),

    /**
     * String literal: "Attributes are not supported".
     */
    ATTRIBUTES_ARE_NOT_SUPPORTED("Attributes are not supported"),

    /**
     * String literal: "table-name".
     */
    TABLE_NAME("table-name"),

    /**
     * String literal: "keyword".
     */
    KEYWORD("keyword"),

    /**
     * String literal: "retrieval-query".
     */
    RETRIEVAL_QUERY("retrieval-query"),

    /**
     * String literal: "name".
     */
    NAME("name"),

    /**
     * String literal: "type".
     */
    TYPE("type"),

    /**
     * String literal: "pk".
     */
    PK("pk"),

    /**
     * String literal: "fk".
     */
    FK("fk"),

    /**
     * String literal: "Attribute %s is not supported".
     */
    ATTRIBUTE_IS_NOT_SUPPORTED("Attribute %s is not supported"),

    /**
     * String literal: "Nested elements inside <fk>, %s in this case, are not supported".
     */
    NESTED_ELEMENTS_INSIDE_FK_ARE_NOT_SUPPORTED("Nested elements inside <fk>, %s in this case, are not supported"),

    /**
     * String literal: "table".
     */
    TABLE("table"),

    /**
     * String literal: "Dynamic attributes are not supported".
     */
    DYNAMIC_ATTRIBUTES_ARE_NOT_SUPPORTED("Dynamic attributes are not supported"),

    /**
     * String literal: "Invalid element %s. No elements except <fk> are supported".
     */
    NO_ELEMENTS_BUT_FK_ARE_SUPPORTED("Invalid element %s. No elements except <fk> are supported"),

    /**
     * String literal: "Elements, %s in this case, are not supported".
     */
    ELEMENTS_ARE_NOT_SUPPORTED("Elements, %s in this case, are not supported");

    private final String m__strTemplate;

    /**
     * Defines a new Literal value.
     * @param template the value.
     */
    Literals(@NotNull final String template) {
        m__strTemplate = template;
    }

    /**
     * Formats the literal with given parameters.
     * @param args the parameters.
     * @return the formatted literal.
     */
    @NotNull
    public String format(@Nullable final Object... args)
    {
        return String.format(m__strTemplate, args);
    }
}
