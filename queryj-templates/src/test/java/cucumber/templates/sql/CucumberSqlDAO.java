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
 * Filename: CucumberSqlDAO.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Cucumber-specific {@link SqlDAO} implementation.
 *
 * Date: 6/24/13
 * Time: 8:43 PM
 *
 */
package cucumber.templates.sql;

/*
 * Importing QueryJ-core classes.
 */
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.SqlDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.List;

/**
 * Cucumber-specific {@link SqlDAO} implementation.
 * @author <a href="mailto:jose.sanleandro@ventura24.es">Jose San Leandro</a>
 * @since 2013/06/24
 */
public class CucumberSqlDAO
    implements SqlDAO
{
    /**
     * The data table.
     */
    private List<Sql> m__lSql;

    /**
     * Creates an instance with given information.
     * @param sqlList such information.
     */
    public CucumberSqlDAO(@NotNull final List<Sql> sqlList)
    {
        immutableSetSqlList(sqlList);
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    protected final void immutableSetSqlList(@NotNull final List<Sql> sqlList)
    {
        this.m__lSql = sqlList;
    }

    /**
     * Specifies the {@link Sql} list.
     * @param sqlList such list.
     */
    @SuppressWarnings("unused")
    protected void setSqlList(@NotNull final List<Sql> sqlList)
    {
        immutableSetSqlList(sqlList);
    }

    /**
     * Retrieves the list of {@link Sql}.
     * @return such table.
     */
    protected List<Sql> getSqlList()
    {
        return this.m__lSql;
    }

    /**
     * Checks whether it contains repository-scoped SQL or not.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean containsRepositoryScopedSql()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finds a given {@link org.acmsl.queryj.customsql.Sql} by its id.
     *
     * @param id the id.
     * @return the associated {@link org.acmsl.queryj.customsql.Sql}, or <code>null</code> if not found.
     */
    @Nullable
    @Override
    public Sql findByPrimaryKey(@NotNull final String id)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all {@link org.acmsl.queryj.customsql.Sql} associated to a given DAO (table).
     *
     * @param table the table.
     * @return the associated {@link org.acmsl.queryj.customsql.Sql} instances.
     */
    @NotNull
    @Override
    public List<Sql> findByDAO(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all <i>selects-for-update</i> for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>selects-for-update</i> queries.
     */
    @NotNull
    @Override
    public List<Sql> findSelectsForUpdate(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all <i>insert</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>insert</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findInserts(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all <i>update</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>update</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findUpdates(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all <i>delete</i> {@link org.acmsl.queryj.customsql.Sql sentences} for a given DAO (table).
     *
     * @param table the table.
     * @return all matching <i>delete</i> sentences.
     */
    @NotNull
    @Override
    public List<Sql> findDeletes(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all custom SQL.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql> findAll()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all SQL matching given result id.
     *
     * @param resultId the result id.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql> findByResultId(@NotNull final String resultId)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all SQL matching given type.
     *
     * @param type the type.
     * @return the list of matching {@link org.acmsl.queryj.customsql.Sql}.
     */
    @NotNull
    @Override
    public List<Sql> findByType(@NotNull final String type)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all repository-scoped SQL.
     *
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql> findAllRepositoryScopedSql()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all plain selects.
     *
     * @param table the name of the table.
     * @return such list.
     */
    @NotNull
    @Override
    public List<Sql> findSelects(@NotNull final String table)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves all dynamic queries.
     *
     * @param tableName the name of the table.
     * @return such list.
     */
    @Override
    public List<Sql> findDynamic(@NotNull final String tableName)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString()
    {
        return "CucumberSqlDAO{" +
               "sqlList=" + m__lSql +
               '}';
    }
}
