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
 * Filename: TemplateUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when filling up templates.
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.dao.DAOTemplateUtils;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.ReferencedResultNotFoundException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.metadata.CachingResultDecorator;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.acmsl.queryj.metadata.TableDAO;
import org.acmsl.queryj.metadata.TableDecorator;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

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
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful methods when filling up templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class TemplateUtils
    implements  Singleton, 
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TemplateUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TemplateUtils SINGLETON = new TemplateUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TemplateUtils() {}

    /**
     * Retrieves a <code>TemplateUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static TemplateUtils getInstance()
    {
        return TemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the custom selects.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom selects.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> retrieveCustomSelects(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomSelects(
                null,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom selects.
     * @param tableName the table name, or <code>null</code> for
     * repository-wide results.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom selects.
     */
    @NotNull
    public List<Sql> retrieveCustomSelects(
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomSql(
                new String[]
                {
                    Sql.SELECT,
                },
                tableName,
                customSqlProvider,
                customSqlProvider.getSqlDAO(),
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom sql.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> retrieveCustomUpdatesOrInserts(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomUpdatesOrInserts(
                null,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom updates or inserts.
     * @param tableName the table name.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom sql.
     */
    @NotNull
    public List<Sql> retrieveCustomUpdatesOrInserts(
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomSql(
                new String[]
                {
                    Sql.INSERT,
                    Sql.UPDATE,
                    Sql.DELETE
                },
                tableName,
                customSqlProvider,
                customSqlProvider.getSqlDAO(),
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom selects.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom selects.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Sql> retrieveCustomSelectsForUpdate(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomSelectsForUpdate(
                null,
                customSqlProvider,
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom selects.
     * @param tableName the table name.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom selects.
     */
    @NotNull
    public List<Sql> retrieveCustomSelectsForUpdate(
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        return
            retrieveCustomSql(
                new String[]
                {
                    Sql.SELECT_FOR_UPDATE,
                },
                tableName,
                customSqlProvider,
                customSqlProvider.getSqlDAO(),
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom sql.
     * @param types the sql types.
     * @param tableName the table name, or <code>null</code> for
     * repository-wide results.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom sql.
     */
    @NotNull
    public List<Sql> retrieveCustomSql(
        @NotNull final String[] types,
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
    {
        @NotNull final List<Sql> result = new ArrayList<Sql>();

        boolean t_bMatches;
        String t_strDao;

        for (@Nullable final Sql t_Sql : sqlDAO.findAll())
        {
            if (t_Sql != null)
            {
                t_strDao = t_Sql.getDao();

                if (   (t_strDao != null)
                    && (tableName != null))
                {
                    t_bMatches = daoTemplateUtils.matches(tableName, t_strDao);
                }
                else
                {
                    t_bMatches = (t_Sql.getRepositoryScope() != null);
                }

                if  (t_bMatches)
                {
                    boolean t_bAdd = false;

                    for (@Nullable final String t_strCurrentType : types)
                    {
                        if  (   (t_strCurrentType != null)
                             && (t_strCurrentType.equals(t_Sql.getType())))
                        {
                            t_bAdd = true;
                            break;
                        }
                    }

                    if  (t_bAdd)
                    {
                        result.add(
                            decoratorFactory.createDecorator(
                                t_Sql,
                                    customSqlProvider,
                                    metadataManager));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the custom results.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom results.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Result> retrieveCustomResults(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
      throws QueryJBuildException
    {
        return
            retrieveCustomResults(
                null,
                customSqlProvider,
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name, or <code>null</code> for
     * repository-wide results.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom results.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @NotNull
    public List<Result> retrieveCustomResults(
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
      throws QueryJBuildException
    {
        return
            retrieveCustomResults(
                tableName,
                customSqlProvider,
                customSqlProvider.getSqlDAO(),
                customSqlProvider.getSqlResultDAO(),
                metadataManager,
                decoratorFactory,
                daoTemplateUtils);
    }

    /**
     * Retrieves the custom results.
     * @param tableName the table name, or <code>null</code> for
     * repository-wide results.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param sqlDAO the {@link SqlDAO} instance.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param daoTemplateUtils the {@link DAOTemplateUtils} instance.
     * @return the custom results.
     * @throws QueryJBuildException if there inconsistencies in the custom SQL
     * model.
     */
    @NotNull
    public List<Result> retrieveCustomResults(
        @Nullable final String tableName,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final SqlDAO sqlDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final DAOTemplateUtils daoTemplateUtils)
      throws QueryJBuildException
    {
        @NotNull final List<Result> result = new ArrayList<Result>();

        @Nullable ResultRef t_ResultRef;
        @Nullable Result t_ResultElement;
        @Nullable String t_strDao;
        boolean t_bMatches;

        if (tableName != null)
        {
            result.addAll(
                buildImplicitResults(
                    tableName,
                    metadataManager.getTableDAO(),
                    customSqlProvider,
                    metadataManager,
                    decoratorFactory));
        }

        for (@Nullable final Sql t_Sql : sqlDAO.findAll())
        {
            if  (t_Sql != null)
            {
                t_strDao = t_Sql.getDao();

                if  (   (t_strDao != null)
                     && (tableName != null))
                {
                    t_bMatches = daoTemplateUtils.matches(tableName, t_strDao);
                }
                else
                {
                    t_bMatches = (t_Sql.getRepositoryScope() != null);
                }

                if  (t_bMatches)
                {
                    t_bMatches =
                           Sql.SELECT.equals(t_Sql.getType())
                        || Sql.SELECT_FOR_UPDATE.equals(t_Sql.getType());
                }

                if (t_bMatches)
                {
                    t_ResultRef = t_Sql.getResultRef();

                    if (t_ResultRef != null)
                    {
                        t_ResultElement = resultDAO.findByPrimaryKey(t_ResultRef.getId());

                        if  (t_ResultElement != null)
                        {
                            if  (!result.contains(t_ResultElement))
                            {
                                result.add(
                                    new CachingResultDecorator(
                                        t_ResultElement,
                                        customSqlProvider,
                                        metadataManager,
                                        decoratorFactory));
                            }
                        }
                        else
                        {
                            try
                            {
                                @Nullable final Log t_Log = UniqueLogFactory.getLog(TemplateUtils.class);

                                if (t_Log != null)
                                {
                                    t_Log.error(
                                        Literals.REFERENCED_RESULT_NOT_FOUND
                                        + t_ResultRef.getId());
                                }
                            }
                            catch  (@NotNull final Throwable throwable)
                            {
                                // class-loading problem.
                                System.err.println(
                                    Literals.REFERENCED_RESULT_NOT_FOUND
                                    + t_ResultRef.getId());
                            }
                            throw new ReferencedResultNotFoundException(t_ResultRef, t_Sql);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Builds the implicit results for given table.
     * @param tableName the table name.
     * @param tableDAO the {@link TableDAO} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the implicit results.
     */
    @NotNull
    protected List<Result> buildImplicitResults(
        @NotNull final String tableName,
        @NotNull final TableDAO tableDAO,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Result> result = new ArrayList<Result>(2);

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> table =
            tableDAO.findByName(tableName);

        if (table != null)
        {
            @Nullable final TableDecorator tableDecorator =
                decoratorFactory.createTableDecorator(table.getName(), metadataManager, customSqlProvider);

            @Nullable final String classValue =
                (tableDecorator != null) ? tableDecorator.getName().getVoName().getValue() : null;

            @NotNull final Result singleResult =
                new ResultElement(
                    "_single." + tableName.toLowerCase(Locale.getDefault()) + Literals.RESULT_SUFFIX,
                    classValue,
                    Result.SINGLE);
            result.add(singleResult);

            @NotNull final Result multipleResult =
                new ResultElement(
                    "_multiple." + tableName.toLowerCase(Locale.getDefault()) + Literals.RESULT_SUFFIX,
                    classValue,
                    Result.MULTIPLE);
            result.add(multipleResult);
        }
        else
        {
            try
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(TemplateUtils.class);

                if (t_Log != null)
                {
                    t_Log.error(Literals.TABLE_NOT_FOUND + tableName);
                }
            }
            catch  (@NotNull final Throwable throwable)
            {
                // class-loading problem.
                System.err.println(Literals.TABLE_NOT_FOUND + tableName);
            }
        }

        return result;
    }
}
