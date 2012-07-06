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
 * Filename: SqlXmlParserConnectionFlagsDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: 
 *
 * Date: 7/6/12
 * Time: 4:59 PM
 *
 */
package org.acmsl.queryj.customsql.xml;

import org.acmsl.queryj.customsql.ConnectionFlags;
import org.acmsl.queryj.customsql.IdentifiableElement;
import org.acmsl.queryj.customsql.SqlConnectionFlagsDAO;

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
 * {@link SqlXmlParser}-based {@link SqlConnectionFlagsDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/06
 */
public class SqlXmlParserConnectionFlagsDAO
    extends AbstractSqlXmlParserDAO
    implements SqlConnectionFlagsDAO
{
    /**
     * The default connection-flags filter.
     */
    public static final String DEFAULT_CONNECTION_FLAGS_FILTER =
        "connection-flags.default";

    /**
     * The connection-flags filter for <i>find-by-primary-key</i> operation.
     */
    public static final String FIND_BY_PRIMARY_KEY_CONNECTION_FLAGS_FILTER =
        "connection-flags.find-by-primary-key";

    /**
     * Creates a new {@link SqlXmlParserConnectionFlagsDAO} with given parser.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserConnectionFlagsDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
    }

    /**
     * Finds the connection flags for given primary key operation.
     * @return such information.
     */
    @Override
    @Nullable
    public List<ConnectionFlags> findByPrimaryKeyOperation()
    {
        return findByPrimaryKeyOperation(getSqlXmlParser());
    }

    /**
     * Finds the connection flags for given primary key operation.
     * @param parser the parser.
     * @return such information.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected List<ConnectionFlags> findByPrimaryKeyOperation(@NotNull final SqlXmlParser parser)
    {
        @Nullable List<ConnectionFlags> result =
            filterConnectionFlags(
                parser.getCollection(),
                FIND_BY_PRIMARY_KEY_CONNECTION_FLAGS_FILTER);

        if  (result.size() == 0)
        {
            result =
                filterConnectionFlags(
                    parser.getCollection(),
                    DEFAULT_CONNECTION_FLAGS_FILTER);
        }

        return result;
    }

    /**
     * Filters the connection flags from given CustomSqlProvider contents.
     * @param contents such contents.
     * @param idFilter the id filter.
     * @return the connection flags.
     */
    @NotNull
    protected List<ConnectionFlags> filterConnectionFlags(
        @NotNull final List<? extends IdentifiableElement> contents, @NotNull final String idFilter)
    {
        return this.filterItems(contents, ConnectionFlags.class, idFilter);
    }
}
