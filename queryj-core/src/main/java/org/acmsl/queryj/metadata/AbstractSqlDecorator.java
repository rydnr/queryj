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
 * Filename: AbstractSqlDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing ACM S.L. Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.ConnectionFlagsRef;
import org.acmsl.queryj.customsql.ParameterRef;
import org.acmsl.queryj.customsql.ResultSetFlagsRef;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.IdentifiableElement;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultRef;
import org.acmsl.queryj.customsql.StatementFlagsRef;
import org.acmsl.queryj.metadata.engines.JdbcTypeManager;

/*
 * Importing Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Decorates &lt;sql&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractSqlDecorator
    extends SqlElement<DecoratedString>
    implements  SqlDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 8016386784936395080L;

    /**
     * The wrapped sql element.
     */
    private Sql<String> m__Sql;

    /**
     * The custom sql provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates an <code>AbstractSqlDecorator</code> with given information.
     * @param sql the {@link Sql} to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider}, required
     * to decorate referred parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    public AbstractSqlDecorator(
        @NotNull final Sql<String> sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        super(
            new DecoratedString(sql.getId()),
            sql.getDao() != null ? new DecoratedString(sql.getDao()) : null,
            new DecoratedString(sql.getName()),
            new DecoratedString(sql.getType()),
            sql.getCardinality(),
            sql.getImplementation() != null ? new DecoratedString(sql.getImplementation()) : null,
            sql.getValidate(),
            sql.isDynamic(),
            new DecoratedString(sql.getDescription()));

        @Nullable final String t_strValue = sql.getValue();
        if (t_strValue != null)
        {
            immutableSetValue(new DecoratedString(t_strValue));
        }
        immutableSetParameterRefs(sql.getParameterRefs());

        @Nullable final ResultRef t_ResultRef = sql.getResultRef();
        if (t_ResultRef != null)
        {
            immutableSetResultRef(t_ResultRef);
        }

        @Nullable final ConnectionFlagsRef t_ConnectionFlagsRef = sql.getConnectionFlagsRef();
        if (t_ConnectionFlagsRef != null)
        {
            immutableSetConnectionFlagsRef(t_ConnectionFlagsRef);
        }

        @Nullable final StatementFlagsRef t_StatementFlagsRef = sql.getStatementFlagsRef();
        if (t_StatementFlagsRef != null)
        {
            immutableSetStatementFlagsRef(t_StatementFlagsRef);
        }

        @Nullable final ResultSetFlagsRef t_ResultSetFlagsRef = sql.getResultSetFlagsRef();
        if (t_ResultSetFlagsRef != null)
        {
            immutableSetResultSetFlagsRef(t_ResultSetFlagsRef);
        }

        immutableSetSql(sql);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    protected final void immutableSetSql(final Sql<String> sql)
    {
        m__Sql = sql;
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    @SuppressWarnings("unused")
    protected void setSql(@NotNull final Sql<String> sql)
    {
        immutableSetSql(sql);
    }

    /**
     * Retrieves the wrapped <i>sql</i> element.
     * @return such instance.
     */
    @Override
    @NotNull
    public Sql<String> getSql()
    {
        return m__Sql;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected final void immutableSetCustomSqlProvider(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom SQL provider.
     * @return such provider.
     */
    @NotNull
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such manager.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata  manager.
     * @param metadataManager such manager.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata  manager.
     * @return such manager.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the metadata type manager.
     * @return such manager.
     */
    @NotNull
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return getMetadataTypeManager(getMetadataManager());
    }

    /**
     * Retrieves the metadata type manager.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such manager.
     */
    @NotNull
    protected MetadataTypeManager getMetadataTypeManager(@NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getMetadataTypeManager();
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Parameter<DecoratedString, ?>> getParameters()
    {
        return
            getParameters(
                getParameterRefs(),
                getCustomSqlProvider(),
                getMetadataTypeManager());
    }

    /**
     * Retrieves the parameters.
     * @param parameterRefs the parameter references.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @NotNull
    protected List<Parameter<DecoratedString, ?>> getParameters(
        @NotNull final List<ParameterRef> parameterRefs,
        @NotNull final CustomSqlProvider customSqlProvider,
        final MetadataTypeManager metadataTypeManager)
    {
        return getParameters(parameterRefs, customSqlProvider.getSqlParameterDAO(), metadataTypeManager);
    }

    /**
     * Retrieves the parameters.
     * @param parameterRefs the parameter references.
     * @param sqlParameterDAO the {@link SqlParameterDAO} instance.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @NotNull
    protected List<Parameter<DecoratedString, ?>> getParameters(
        @NotNull final List<ParameterRef> parameterRefs,
        @NotNull final SqlParameterDAO sqlParameterDAO,
        final MetadataTypeManager metadataTypeManager)
    {
        @NotNull final List<Parameter<DecoratedString, ?>> result = new ArrayList<>();

        for (@Nullable final ParameterRef t_ParameterRef : parameterRefs)
        {
            @Nullable final Parameter<String, ?> t_Parameter;

            if  (t_ParameterRef != null)
            {
                t_Parameter = sqlParameterDAO.findByPrimaryKey(t_ParameterRef.getId());

                if  (t_Parameter != null)
                {
                    result.add(
                        new CachingParameterDecorator<>(t_Parameter, metadataTypeManager));
                }
                else
                {
                    try
                    {
                        final Log t_Log = UniqueLogFactory.getLog(SqlDecorator.class);

                        if (t_Log != null)
                        {
                            t_Log.warn(
                                "Referenced parameter not found:"
                                + t_ParameterRef.getId());
                        }
                    }
                    catch  (@NotNull final Throwable throwable)
                    {
                        // class-loading problem.
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the result class.
     * @return such information.
     */
    @NotNull
    public String getResultClass()
    {
        return getResultClass(getDao(), getRepositoryScope(), getCardinality(), getResultRef(), getCustomSqlProvider());
    }

    /**
     * Retrieves the result class.
     * @param dao the dao name.
     * @param repository the repository.
     * @param cardinality the cardinality.
     * @param resultRef the result ref.
     * @param customSqlProvider the custom sql provider.
     * @return such information.
     */
    @NotNull
    protected String getResultClass(
        @Nullable final DecoratedString dao,
        @Nullable final DecoratedString repository,
        @NotNull final Cardinality cardinality,
        @Nullable final ResultRef resultRef,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getResultClass(dao, repository, cardinality, resultRef, customSqlProvider.getSqlResultDAO());
    }

    /**
     * Retrieves the result class.
     * @param dao the DAO name.
     * @param repository the repository.
     * @param cardinality the cardinality.
     * @param resultRef the result ref.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @return such information.
     */
    @NotNull
    protected String getResultClass(
        @Nullable final DecoratedString dao,
        @Nullable final DecoratedString repository,
        @NotNull final Cardinality cardinality,
        @Nullable final ResultRef resultRef,
        @NotNull final SqlResultDAO resultDAO)
    {
        @NotNull final StringBuilder result = new StringBuilder();

        final boolean multiple = cardinality.equals(Cardinality.MULTIPLE);

        if (multiple)
        {
            result.append(MULTIPLE_RESULT_CLASS);
        }

        if  (resultRef == null)
        {
            if (multiple)
            {
                result.append('<');
            }
            if (dao != null)
            {
                result.append(dao.getVoName());
            }
            else if (repository != null)
            {
                result.append(repository.getVoName());
            }
            if (multiple)
            {
                result.append('>');
            }
        }
        else
        {
            @Nullable final Result<String> t_Result = resultDAO.findByPrimaryKey(resultRef.getId());

            if  (t_Result != null)
            {
                if (multiple)
                {
                    result.append('<');
                }
                result.append(t_Result.getClassValue());
                if (multiple)
                {
                    result.append('>');
                }
            }
            else
            {
                try
                {
                    final Log t_Log = UniqueLogFactory.getLog(Literals.CUSTOM_SQL);

                    if (t_Log != null)
                    {
                        t_Log.warn(
                              Literals.REFERENCED_RESULT_NOT_FOUND
                            + resultRef.getId());
                    }
                }
                catch  (@NotNull final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }

        return result.toString();
    }

    /**
     * Retrieves the result.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Result<DecoratedString> getResult()
    {
        return getResult(getResultRef(), getCustomSqlProvider());
    }

    /**
     * Retrieves the result.
     * @param resultRef the {@link ResultRef} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @return such information.
     */
    @Nullable
    protected Result<DecoratedString> getResult(
        @Nullable final ResultRef resultRef,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getResult(resultRef, customSqlProvider.getSqlResultDAO());
    }

    /**
     * Retrieves the result.
     * @param resultRef the {@link ResultRef} instance.
     * @param sqlResultDAO the {@link SqlResultDAO} instance.
     * @return such information.
     */
    @Nullable
    protected Result<DecoratedString> getResult(
        @Nullable final ResultRef resultRef,
        @NotNull final SqlResultDAO sqlResultDAO)
    {
        @Nullable Result<DecoratedString> result = null;

        if  (resultRef != null)
        {
            @Nullable final Result<String> t_Result = sqlResultDAO.findByPrimaryKey(resultRef.getId());

            if  (t_Result != null)
            {
                result = decorate(t_Result);
            }
            else
            {
                try
                {
                    final Log t_Log = UniqueLogFactory.getLog(Literals.CUSTOM_SQL);

                    if (t_Log != null)
                    {
                        t_Log.warn(
                            Literals.REFERENCED_RESULT_NOT_FOUND
                            + resultRef.getId());
                    }
                }
                catch  (@NotNull final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }

        return result;
    }

    /**
     * Decorates given {@link Result} instance.
     * @param result the instance to decorate.
     * @return the decorated instance.
     */
    @NotNull
    protected ResultDecorator decorate(@NotNull final Result<String> result)
    {
        return decorate(result, getCustomSqlProvider(), getMetadataManager(), CachingDecoratorFactory.getInstance());
    }

    /**
     * Decorates given {@link Result} instance.
     * @param result the instance to decorate.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the decorated instance.
     */
    @NotNull
    protected ResultDecorator decorate(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return new CachingResultDecorator(result, customSqlProvider, metadataManager, decoratorFactory);
    }

    /**
     * Checks whether the parameter list would take too much space and should be wrapped.
     * @return such information.
     */
    @Override
    public boolean getParametersShouldBeWrapped()
    {
        return getParametersShouldBeWrapped(getParameters());
    }

    /**
     * Checks whether the parameter list would take too much space and should be wrapped.
     * @param parameters the parameters.
     * @return such information.
     */
    protected boolean getParametersShouldBeWrapped(@NotNull final List<Parameter<DecoratedString, ?>> parameters)
    {
        final boolean result;

        int weight = 0;

        for (@Nullable final Parameter<DecoratedString, ?> parameter : parameters)
        {
            if (parameter != null)
            {
                weight += parameter.getType().getValue().length();
                weight += parameter.getName().getValue().length();
            }
        }

        result = weight > 50;

        return result;
    }

    /**
     * Checks whether the result of this query could be {@code null} or not.
     * @return such information.
     */
    public boolean isResultNullable()
    {
        return !isMultiple() && isResultNullable(getResultClass());
    }

    /**
     * Checks whether the result of this query could be {@code null} or not.
     * @param resultClass the result classe.
     * @return such information.
     */
    protected boolean isResultNullable(@NotNull final String resultClass)
    {
        final boolean result;

        @NotNull final JdbcTypeManager typeManager = new JdbcTypeManager();

        @NotNull final Class<?> clazz = typeManager.getClass(resultClass);

        result = !typeManager.isPrimitiveWrapper(clazz);

        return result;
    }

    /**
     * Retrieves the parameter types.
     * @return such list.
     */
    @NotNull
    public List<DecoratedString> getParameterTypes()
    {
        return getParameterTypes(getParameters(), getMetadataTypeManager(), SqlDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the parameter types.
     * @param parameters the parameters.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param sqlDecoratorHelper the {@link SqlDecoratorHelper} instance.
     * @return such list.
     */
    @NotNull
    protected List<DecoratedString> getParameterTypes(
        @NotNull final List<Parameter<DecoratedString, ?>> parameters,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final SqlDecoratorHelper sqlDecoratorHelper)
    {
        return sqlDecoratorHelper.getParameterTypes(parameters, metadataTypeManager);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("sql", getSql())
                .append("customSqlProvider", getCustomSqlProvider().hashCode())
                .append("metadataManager", getMetadataManager().hashCode())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getSql());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param sql the sql.
     * @return such information.
     */
    protected int hashCode(@NotNull final Sql<String> sql)
    {
        return sql.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        final boolean result;

        if  (object instanceof AbstractSqlDecorator)
        {
            @NotNull final AbstractSqlDecorator t_OtherInstance =
                (AbstractSqlDecorator) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getSql(),
                        t_OtherInstance.getSql())
                    .append(
                        getCustomSqlProvider(),
                        t_OtherInstance.getCustomSqlProvider())
                    .append(
                        getMetadataManager(),
                        t_OtherInstance.getMetadataManager())
                    .append(
                        getMetadataTypeManager(),
                        t_OtherInstance.getMetadataTypeManager())
                    .append(
                        getParameters(),
                        t_OtherInstance.getParameters())
                    .append(
                        getResultClass(),
                        t_OtherInstance.getResultClass())
                .isEquals();
        }
        else if  (object instanceof Sql)
        {
            result = object.equals(getSql());
        }
        else
        {
            result = super.equals(object);
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * object prevents it from being compared to this Object.
     */
    @Override
    public int compareTo(@Nullable final IdentifiableElement<DecoratedString> object)
        throws  ClassCastException
    {
        final int result;

        if  (object instanceof AbstractSqlDecorator)
        {
            @NotNull final AbstractSqlDecorator t_OtherInstance =
                (AbstractSqlDecorator) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getSql(),
                    t_OtherInstance.getSql())
                .append(
                    getCustomSqlProvider(),
                    t_OtherInstance.getCustomSqlProvider())
                .append(
                    getMetadataManager(),
                    t_OtherInstance.getMetadataManager())
                .append(
                    getMetadataTypeManager(),
                    t_OtherInstance.getMetadataTypeManager())
                .append(
                    getParameters(),
                    t_OtherInstance.getParameters())
                .append(
                    getResultClass(),
                    t_OtherInstance.getResultClass())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
