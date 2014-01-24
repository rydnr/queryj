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
import org.acmsl.queryj.customsql.ConnectionFlags;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterRef;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyRef;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultSetFlags;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.StatementFlags;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

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
    extends CustomSqlProvider
{
    /**
     * Retrieves the sql.xml {@link Sql} collection.
     * @return such collection.
     */
    @NotNull
    List<Sql<String>> getQueries();

    /**
     * Retrieves the sql.xml {@link Result} collection.
     * @return such collection.
     */
    @NotNull
    List<Result<String>> getResults();

    /**
     * Retrieves the sql.xml {@link Parameter} collection.
     * @return such collection.
     */
    @NotNull
    List<Parameter<String>> getParameters();

    /**
     * Retrieves the sql.xml {@link Parameter} collection.
     * @return such collection.
     */
    @NotNull
    List<ParameterRef> getParameterRefs();

    /**
     * Retrieves the sql.xml {@link Property} collection.
     * @return such collection.
     */
    @NotNull
    List<Property<String>> getProperties();

    /**
     * Retrieves the sql.xml {@link PropertyRef} collection.
     * @return such collection.
     */
    @NotNull
    List<PropertyRef> getPropertyRefs();

    /**
     * Retrieves the sql.xml {@link ResultSetFlags} collection.
     * @return such collection.
     */
    @NotNull
    List<ResultSetFlags> getResultSetFlagList();

    /**
     * Retrieves the sql.xml {@link StatementFlags} collection.
     * @return such collection.
     */
    @NotNull
    List<StatementFlags> getStatementFlagList();

    /**
     * Retrieves the sql.xml {@link ConnectionFlags} collection.
     * @return such collection.
     */
    @NotNull
    List<ConnectionFlags> getConnectionFlagList();

    /**
     * Parses the sql.xml associated to this instance.
     * @throws org.acmsl.queryj.api.exceptions.QueryJBuildException if the information cannot be read.
     */
    void parse()
        throws  QueryJBuildException;

    /**
     * Adds a new result.
     * @param id the result id.
     * @param result the <code>Result</code> instance.
     */
    @SuppressWarnings("unused")
    void addResult(@NotNull final String id, @NotNull final Result<String> result);

    /**
     * Adds a new property.
     * @param id the property id.
     * @param columnName the column name.
     * @param index the property index.
     * @param type the property type.
     * @param nullable whether it allows null or not.
     */
    void addProperty(
        @NotNull final String id,
        @NotNull final String columnName,
        final int index,
        @NotNull final String type,
        final boolean nullable);
}

