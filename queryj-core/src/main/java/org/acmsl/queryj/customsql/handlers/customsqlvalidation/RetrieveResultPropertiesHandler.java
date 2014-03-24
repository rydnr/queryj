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
 * Filename: RetrieveResultPropertiesHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the properties from the ResultSet and injects
 *              them into the command.
 *
 * Date: 2014/03/15
 * Time: 16:52
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing ACM SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.CustomResultWithNoPropertiesException;
import org.acmsl.queryj.api.exceptions.CustomSqlWithNoPropertiesException;
import org.acmsl.queryj.api.exceptions.InvalidColumnNameInCustomResultException;
import org.acmsl.queryj.api.exceptions.NoTableMatchingSqlException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.api.exceptions.UnsupportedCustomResultPropertyTypeException;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.PropertyRef;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.exceptions.PropertiesNotAvailableForValidationException;
import org.acmsl.queryj.customsql.handlers.CustomSqlValidationHandler;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.TypeManager;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;
import org.acmsl.queryj.metadata.vo.Attribute;
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
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the {@link Property properties} from the {@link ResultSet} and injects
 * them into the command.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/15 16:52
 */
@ThreadSafe
public class RetrieveResultPropertiesHandler
    extends AbstractQueryJCommandHandler
{
    /**
     * String literal: "Validation failed for "
     */
    protected static final String VALIDATION_FAILED_FOR = "Validation failed for ";

    /**
     * String literal: "Could not retrieve result via "
     */
    protected static final String COULD_NOT_RETRIEVE_RESULT_VIA = "Could not retrieve result via ";

    /**
     * String literal: "ResultSet."
     */
    protected static final String RESULT_SET = "ResultSet.";

    /**
     * The key to store the current properties.
     */
    protected static final String CURRENT_PROPERTIES = "current-properties";

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
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

        @NotNull final CustomSqlProvider t_CustomSqlProvider = retrieveCustomSqlProvider(command);
        @NotNull final MetadataManager t_MetadataManager = retrieveMetadataManager(command);

        @Nullable final ResultRef t_ResultRef = t_Sql.getResultRef();

        @NotNull final List<Property<String>> t_lProperties =
            retrieveProperties(
                t_Sql,
                (t_ResultRef != null)
                ? t_CustomSqlProvider.getSqlResultDAO().findByPrimaryKey(t_ResultRef.getId()) : null,
                t_CustomSqlProvider,
                t_MetadataManager,
                new JdbcTypeManager());

        setCurrentProperties(t_lProperties, command);

        return false;
    }

    /**
     * Annotates the properties into the command.
     * @param properties the {@link Property properties}.
     * @param command the command.
     */
    protected void setCurrentProperties(
        @NotNull final List<Property<String>> properties, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<List<Property<String>>>(command).setSetting(CURRENT_PROPERTIES, properties);
    }

    /**
     * Validates the result set.
     * @param sql the sql.
     * @param sqlResult the custom sql result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     */
    protected List<Property<String>> retrieveProperties(
        @NotNull final Sql<String> sql,
        @Nullable final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
        throws QueryJBuildException
    {
        @NotNull List<Property<String>> result = new ArrayList<>();

        if (sql.getId().equalsIgnoreCase("find-product-types-by-draw-type-id"))
        {
            int debug = 1;
        }

        @Nullable final String t_strTable =
            CustomResultUtils.getInstance().retrieveTable(sql, metadataManager);

        if (   (t_strTable == null)
            && (sqlResult == null))
        {
            throw new NoTableMatchingSqlException(sql);
        }

        if (sqlResult != null)
        {
            result.addAll(retrieveExplicitProperties(sqlResult, customSqlProvider.getSqlPropertyDAO()));
        }

        if  (   (result.size() == 0)
             && (t_strTable != null))
        {
            result = retrieveImplicitProperties(t_strTable, metadataManager, typeManager);
        }

        if  (result.size() == 0)
        {
            if (sqlResult != null)
            {
                throw new CustomResultWithNoPropertiesException(sqlResult, sql);
            }
            else
            {
                throw new CustomSqlWithNoPropertiesException(sql);
            }
        }

        return result;
    }

    /**
     * Creates a property from given {@link ResultSetMetaData}.
     * @param metadata the result set metadata.
     * @param index the index.
     * @return the associated {@link Property}.
     */
    @NotNull
    protected Property<String> createPropertyFrom(@NotNull final ResultSetMetaData metadata, final int index)
        throws SQLException
    {
        @NotNull final String t_strColumnName = metadata.getColumnName(index);
        @NotNull final String t_strType = metadata.getColumnTypeName(index);
        final boolean t_bNullable = (metadata.isNullable(index) == ResultSetMetaData.columnNullable);

        return new PropertyElement<>(t_strColumnName, t_strColumnName, index, t_strType, t_bNullable);
    }

    /**
     * Retrieves the properties declared for given result.
     * @param sqlResult the custom sql result.
     * @param propertyDAO the {@link org.acmsl.queryj.metadata.SqlPropertyDAO} instance.
     * @return such properties.
     */
    @NotNull
    protected List<Property<String>> retrieveExplicitProperties(
        @NotNull final Result<String> sqlResult, @NotNull final SqlPropertyDAO propertyDAO)
        throws  QueryJBuildException
    {
        @NotNull final List<Property<String>> result = new ArrayList<>();

        Property<String> t_Property;

        for (@Nullable final PropertyRef t_PropertyRef : sqlResult.getPropertyRefs())
        {
            if (t_PropertyRef != null)
            {
                t_Property = propertyDAO.findByPrimaryKey(t_PropertyRef.getId());

                if  (t_Property != null)
                {
                    result.add(t_Property);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the implicit properties declared for given result.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param typeManager the <code>MetadataTypeManager</code> instance.
     * @return such properties.
     */
    @NotNull
    protected List<Property<String>> retrieveImplicitProperties(
        @NotNull final String table,
        @NotNull final MetadataManager metadataManager,
        @NotNull final TypeManager typeManager)
        throws  QueryJBuildException
    {
        @NotNull final List<Property<String>> result = new ArrayList<>();

        @Nullable final List<Attribute<String>> t_lColumns =
            metadataManager.getColumnDAO().findAllColumns(table);

        final int t_iCount = t_lColumns.size();

        String t_strId;
        Attribute<String> t_Column;
        @Nullable Class<?> t_Type;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Column = t_lColumns.get(t_iIndex);

            if (t_Column != null)
            {
                t_strId = table + "." + t_Column.getName() + ".property";

                t_Type = typeManager.getClass(t_Column.getType());

                result.add(
                    new PropertyElement<>(
                        t_strId,
                        t_Column.getName(),
                        t_iIndex + 1,
                        t_Type.getSimpleName(),
                        t_Column.isNullable()));
            }
        }

        return result;
    }

    /**
     * Executes the <code>ResultSet.getXXX</code> method.
     * @param method the <code>ResultSet</code> getter method for given property.
     * @param resultSet the {@link ResultSet} instance.
     * @param property the property.
     * @param sqlResult the {@link Result} instance.
     * @param sql the SQL element.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    protected void invokeResultSetGetter(
        @NotNull final Method method,
        @NotNull final ResultSet resultSet,
        @NotNull final Property<String> property,
        @Nullable final Result<String> sqlResult,
        @NotNull final Sql<String> sql,
        @NotNull final MetadataManager metadataManager)
        throws QueryJBuildException
    {
        @Nullable final Log t_Log = UniqueLogFactory.getLog(CustomSqlValidationHandler.class);

        try
        {
            @NotNull final Object[] t_aParameters = new Object[1];

            t_aParameters[0] = property.getColumnName();

            method.invoke(resultSet, t_aParameters);
        }
        catch  (@NotNull final Throwable cannotRetrieveColumnValue)
        {
            if  (t_Log != null)
            {
                t_Log.warn(
                    VALIDATION_FAILED_FOR + sql.getId() + ":\n"
                    + COULD_NOT_RETRIEVE_RESULT_VIA
                    + RESULT_SET + method.getName()
                    + "("
                    + (   (property.getIndex() > 0)
                          ?  "" + property.getIndex()
                          :  property.getColumnName())
                    + ")",
                    cannotRetrieveColumnValue);
            }

            if (metadataManager.isInvalidColumnNameException(cannotRetrieveColumnValue))
            {
                throw
                    new InvalidColumnNameInCustomResultException(
                        property, sql, sqlResult, cannotRetrieveColumnValue);
            }
            else if (metadataManager.isInvalidColumnTypeException(cannotRetrieveColumnValue))
            {
                throw
                    new UnsupportedCustomResultPropertyTypeException(
                        property, sql, sqlResult, cannotRetrieveColumnValue);
            }
        }
    }

    /**
     * Retrieves the method to call.
     * @param instanceClass the instance class.
     * @param methodName the method name.
     * @return the <code>Method</code> instance.
     */
    @NotNull
    protected Method retrieveMethod(
        @NotNull final Class<?> instanceClass,
        @NotNull final String methodName,
        @NotNull final Class[] parameterClasses)
        throws  NoSuchMethodException
    {
        return instanceClass.getDeclaredMethod(methodName, parameterClasses);
    }


    /**
     * Retrieves the getter method name.
     * @param type the data type.
     * @return the associated getter method.
     */
    @NotNull
    protected String getGetterMethod(@NotNull final Class<?> type)
    {
        return new BindQueryParametersHandler().getAccessorMethod("get", type, StringUtils.getInstance());
    }

    /**
     * Retrieves the properties for current SQL.
     * @param command the command.
     * @return the properties.
     */
    @NotNull
    public List<Property<String>> retrieveCurrentProperties(final QueryJCommand command)
    {
        @Nullable final List<Property<String>> result =
            new QueryJCommandWrapper<Property<String>>(command).getListSetting(CURRENT_PROPERTIES);

        if (result == null)
        {
            @NotNull final Sql<String> t_Sql = new RetrieveQueryHandler().retrieveCurrentSql(command);

            throw new PropertiesNotAvailableForValidationException(t_Sql);
        }

        return result;
    }
}
