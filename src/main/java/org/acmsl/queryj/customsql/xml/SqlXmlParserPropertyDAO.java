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
 * Filename: SqlXmlParserPropertyDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: SqlXmlParser-backed SqlPropertyDAO implementation.
 *
 * Date: 6/7/12
 * Time: 8:31 AM
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyRef;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.SqlPropertyDAO;

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

/**
 * {@link SqlXmlParser}-backed {@link SqlPropertyDAO} implementation.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/07/06
 */
public class SqlXmlParserPropertyDAO
    extends AbstractSqlXmlParserDAO
    implements SqlPropertyDAO
{
    /**
     * Creates a new {@link SqlXmlParserPropertyDAO} instance.
     * @param parser the {@link SqlXmlParser} instance.
     */
    public SqlXmlParserPropertyDAO(@NotNull final SqlXmlParser parser)
    {
        super(parser);
    }

    /**
     * Retrieves the {@link Property} associated to given id.
     * @param id the property id.
     * @return the {@link Property}, or <code>null</code> if not found.
     */
    @Override
    @Nullable
    public Property findByPrimaryKey(@NotNull final String id)
    {
        return findByPrimaryKey(id, getSqlXmlParser());
    }

    /**
     * Retrieves the {@link Property} associated to given id.
     * @param id the property id.
     * @param parser the {@link SqlXmlParser} instance.
     * @return the {@link Property}, or <code>null</code> if not found.
     */
    @Nullable
    public Property findByPrimaryKey(@NotNull final String id, @NotNull final SqlXmlParser parser)
    {
        return findById(id, Property.class, parser.getProperties());
    }

    /**
     * Retrieves all {@link Property properties} used in given {@link org.acmsl.queryj.customsql.Result}.
     *
     * @param resultId the {@link org.acmsl.queryj.customsql.Result} identifier.
     * @return the list of properties associated to given {@link org.acmsl.queryj.customsql.Result}.
     */
    @NotNull
    @Override
    public List<Property> findByResult(@NotNull final String resultId)
    {
        return findByResult(resultId, getSqlXmlParser());
    }

    /**
     * Retrieves all {@link Property properties} used in given {@link org.acmsl.queryj.customsql.Result}.
     *
     * @param resultId the {@link org.acmsl.queryj.customsql.Result} identifier.
     * @param parser the {@link SqlXmlParser} instance.
     * @return the list of properties associated to given {@link org.acmsl.queryj.customsql.Result}.
     */
    @NotNull
    protected List<Property> findByResult(@NotNull final String resultId, @NotNull final SqlXmlParser parser)
    {
        List<Property> result = new ArrayList<Property>(8);

        for (@Nullable Result t_Result : parser.getResults())
        {
            if (   (t_Result != null)
                && (resultId.equalsIgnoreCase(t_Result.getId())))
            {
                for (@Nullable PropertyRef t_PropertyRef : t_Result.getPropertyRefs())
                {
                    if (t_PropertyRef != null)
                    {
                        result.add(findByPrimaryKey(t_PropertyRef.getId()));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves all {@link Property properties}.
     * @return such list.
     */
    @NotNull
    public List<Property> findAll()
    {
        return this.findAll(getSqlXmlParser(), Property.class);
    }

    /**
     * Inserts a new property.
     * @param name the property name.
     * @param tableName the table name.
     * @param type the type.
     * @param index the property index.
     * @param columnName the column name.
     * @param nullable whether it allows null or not.
     */
    @Override
    public void insert(
        @NotNull final String name,
        @NotNull final String columnName,
        final int index,
        @NotNull final String tableName,
        @NotNull final String type,
        final boolean nullable)
    {
        insert(name, columnName, index, tableName, type, nullable, getSqlXmlParser());
    }

    /**
     * Inserts a new property.
     * @param name the property name.
     * @param columnName the column name.
     * @param index the property index.
     * @param tableName the table name.
     * @param type the type.
     * @param parser the {@link SqlXmlParser} instance.
     */
    protected void insert(
        @NotNull final String name,
        @NotNull final String columnName,
        final int index,
        @NotNull final String tableName,
        @NotNull final String type,
        final boolean nullable,
        @NotNull final SqlXmlParser parser)
    {
        parser.addProperty(name, columnName, index, tableName, type, nullable);
    }
}