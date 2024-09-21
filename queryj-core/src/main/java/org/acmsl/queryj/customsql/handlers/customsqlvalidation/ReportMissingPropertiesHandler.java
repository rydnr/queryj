/*
                        QueryJ Core

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
 * Filename: ReportMissingPropertiesHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Detects and reports missing properties: the ones that are
 *              returned by the ResultSet, but are not declared.
 *
 * Date: 2014/03/16
 * Time: 10:34
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Detects and reports missing properties: the ones that are returned
 * by the {@link java.sql.ResultSet}, but are not declared.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 10:34
 */
@ThreadSafe
public class ReportMissingPropertiesHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * Asks the handler to process the command. The idea is that each
     * command handler decides if such command is suitable of being
     * processed, and if so perform the concrete actions the command
     * represents.
     *
     * @param command the command to process (or not).
     * @return <code>true</code> if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command) throws QueryJBuildException
    {
        @NotNull final List<Property<String>> t_lProperties =
            new RetrieveResultPropertiesHandler().retrieveCurrentProperties(command);

        @NotNull final List<Property<String>> t_lColumns =
            new RetrieveResultSetColumnsHandler().retrieveCurrentColumns(command);

        @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

        @Nullable final Log t_Log = retrieveLog();

        diagnoseMissingProperties(t_lProperties, t_lColumns, t_Sql, t_Log);

        return false;
    }

    /**
     * Retrieves the log.
     * @return such instance.
     */
    @Nullable
    protected Log retrieveLog()
    {
        return UniqueLogFactory.getLog(ReportMissingPropertiesHandler.class);
    }

    /**
     * Reports any undeclared property.
     * @param properties the declared properties.
     * @param columns the properties from the result set.
     * @param sql the query itself.
     * @param log the log.
     */
    protected void diagnoseMissingProperties(
        @NotNull final List<Property<String>> properties,
        @NotNull final List<Property<String>> columns,
        @NotNull final Sql<String> sql,
        @Nullable final Log log)
    {
        if (log != null)
        {
            @NotNull final List<Property<String>> t_lMissingProperties =
                detectMissingProperties(properties, columns);

            int t_iIndex = 1;

            for (@Nullable final Property<String> t_MissingProperty : t_lMissingProperties)
            {
                if  (t_MissingProperty != null)
                {
                    log.warn(
                        "Column not declared ("
                        + t_iIndex + ", "
                        + t_MissingProperty.getColumnName() + ", "
                        + t_MissingProperty.getType() + "), in sql "
                        + sql.getId());
                }

                t_iIndex++;
            }
        }
    }

    /**
     * Tries to detect the name of any missing properties.
     * @param properties the declared properties.
     * @param columns the runtime columns (which potentially refer to undeclared properties)
     * @return the list of columns not declared in the property list.
     */
    @NotNull
    protected List<Property<String>> detectMissingProperties(
        @NotNull final List<Property<String>> properties, @NotNull final List<Property<String>> columns)
    {
        @NotNull final List<Property<String>> result = new ArrayList<>();

        for (int index = 0; index < columns.size(); index++)
        {
            @Nullable final Property<String> column = columns.get(index);

            if (column != null)
            {
                if (index < properties.size())
                {
                    @Nullable final Property<String> property = properties.get(index);

                    if (property != null)
                    {
                        if (   (!column.getColumnName().equalsIgnoreCase(property.getColumnName()))
                               && (!isColumnIncluded(column.getColumnName(), properties)))
                        {
                            result.add(column);
                        }
                    }
                }
                else if (!isColumnIncluded(column.getColumnName(), properties))
                {
                    result.add(column);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given column is included in the property list.
     * @param column the column name.
     * @param properties the properties.
     * @return {@code true} in such case.
     */
    protected boolean isColumnIncluded(@NotNull final String column, @NotNull final List<Property<String>> properties)
    {
        boolean result = false;

        for (@Nullable final Property<String> property : properties)
        {
            if (   (property != null)
                && (column.equalsIgnoreCase(property.getColumnName())))
            {
                result = true;
                break;
            }
        }

        return result;
    }
}
