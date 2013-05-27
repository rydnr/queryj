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
 * Filename: SqlXmlParserParameterDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: SqlXmlParser-backed SqlParameterDAO implementation.
 *
 * Date: 6/6/12
 * Time: 8:31 AM
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.metadata.SqlParameterDAO;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * {@link SqlXmlParser}-backed {@link SqlParameterDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/06
 */
@ThreadSafe
public class SqlXmlParserParameterDAO
    extends AbstractSqlXmlParserDAO
    implements SqlParameterDAO
{
    /**
     * Creates a new {@link SqlXmlParserParameterDAO} with given {@link SqlXmlParser}.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserParameterDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Parameter} associated to given id.
     *
     * @param id the parameter id.
     * @return the {@link org.acmsl.queryj.customsql.Parameter}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Parameter findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getSqlXmlParser());
    }

    /**
     * Retrieves the {@link org.acmsl.queryj.customsql.Parameter} associated to given id.
     *
     * @param id the parameter id.
     * @return the {@link org.acmsl.queryj.customsql.Parameter}, or <code>null</code> if not found.
     */
    @Nullable
    protected Parameter findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        return findById(id, Parameter.class, parser.getParameters());
    }

    /**
     * Retrieves all {@link org.acmsl.queryj.customsql.Parameter parameters} used in given {@link org.acmsl.queryj.customsql.Sql}.
     *
     * @param sqlId the {@link org.acmsl.queryj.customsql.Sql} identifier.
     * @return the list of parameters required by given {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Parameter> findBySql(@NotNull final String sqlId)
    {
        // TODO: I need a SqlDAO instance.
        return new ArrayList<Parameter>(0);
    }


    /**
     * Retrieves all parameters.
     * @return such list.
     */
    @NotNull
    public List<Parameter> findAll()
    {
        return findAll(getSqlXmlParser(), Parameter.class);
    }
}
