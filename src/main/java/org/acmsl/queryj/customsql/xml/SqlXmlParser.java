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
 * Filename: SqlXmlParser.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to read the contents contained in QueryJ's sql.xml files.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.ConnectionFlagsElement;
import org.acmsl.queryj.customsql.IdentifiableElement;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultSetFlagsElement;
import org.acmsl.queryj.customsql.StatementFlagsElement;
import org.acmsl.queryj.tools.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Is able to read the contents contained in QueryJ's sql.xml files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2012/07/06 07:08
 */
public interface SqlXmlParser
{
    /**
     * Retrieves the sql.xml element collection.
     * return such collection.
     */
    @NotNull
    List<? extends IdentifiableElement> getCollection();

    /**
     * Parses the sql.xml associated to this instance.
     * @throws org.acmsl.queryj.tools.QueryJBuildException if the information cannot be read.
     */
    void parse()
        throws  QueryJBuildException;

    /**
     * Resolves the parameter reference.
     * @param reference such reference.
     * @return the referenced parameter.
          */
    @Nullable
    Parameter resolveParameterReference(
        @NotNull final String reference);

    /**
     * Resolves the result reference.
     * @param reference such reference.
     * @return the referenced result.
     */
    @Nullable
    Result resolveResultReference(
        @NotNull final String reference);

    /**
     * Resolves the property reference.
     * @param reference the reference.
     * @return the referenced property.
     */
    @Nullable
    PropertyElement resolvePropertyReference(
        @NotNull final String reference);

    /**
     * Resolves the connection-flags reference.
     * @param reference such reference.
     * @return the referenced connection flags.
     */
    @SuppressWarnings("unused")
    @Nullable
    ConnectionFlagsElement resolveConnectionFlagsReference(
        @NotNull final String reference);

    /**
     * Resolves the statement-flags reference.
     * @param reference such reference.
     * @return the referenced statement flags.
     */
    @Nullable
    StatementFlagsElement resolveStatementFlagsReference(
        @NotNull final String reference);

    /**
     * Resolves the resultset-flags reference.
     * @param reference such reference.
     * @return the referenced resultset flags.
     */
    @Nullable
    ResultSetFlagsElement resolveResultSetFlagsReference(
        @NotNull final String reference);

    /**
     * Adds a new result.
     * @param id the result id.
     * @param result the <code>Result</code> instance.
     */
    void addResult(@NotNull final String id, @NotNull final Result result);

    /**
     * Adds a new property.
     * @param id the property id.
     * @param name the property name.
     * @param type the property type.
     */
    void addProperty(
        @NotNull final String id, @NotNull final String name, @NotNull final String type);
}

