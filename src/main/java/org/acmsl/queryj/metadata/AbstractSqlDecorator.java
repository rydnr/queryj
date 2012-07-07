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
 * Filename: AbstractSqlDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <sql> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.IdentifiableElement;
import org.acmsl.queryj.customsql.ParameterRefElement;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultRef;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing Apache Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates &lt;sql&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractSqlDecorator
    extends SqlElement
    implements  SqlDecorator
{
    /**
     * The wrapped sql element.
     */
    private Sql m__Sql;

    /**
     * The custom sql provider.
     * @todo remove this.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates an <code>AbstractSqlDecorator</code> with given information.
     * @param sql the <code>Sql</code> to decorate.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     */
    public AbstractSqlDecorator(
        @NotNull final Sql sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        super(
            sql.getId(),
            sql.getDao(),
            sql.getName(),
            sql.getType(),
            sql.getImplementation(),
            sql.getValidate(),
            sql.isDynamic());
        immutableSetDescription(sql.getDescription());
        immutableSetValue(sql.getValue());
        immutableSetParameterRefs(sql.getParameterRefs());

        @Nullable ResultRef t_ResultRef = sql.getResultRef();
        if (t_ResultRef != null)
        {
            immutableSetResultRef(t_ResultRef);
        }

        immutableSetConnectionFlagsRef(sql.getConnectionFlagsRef());
        immutableSetStatementFlagsRef(sql.getStatementFlagsRef());
        immutableSetResultSetFlagsRef(sql.getResultSetFlagsRef());
        immutableSetSql(sql);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    protected final void immutableSetSql(final Sql sql)
    {
        m__Sql = sql;
    }

    /**
     * Specifies the wrapped <i>sql</i> element.
     * @param sql such instance.
     */
    @SuppressWarnings("unused")
    protected void setSql(@NotNull final Sql sql)
    {
        immutableSetSql(sql);
    }

    /**
     * Retrieves the wrapped <i>sql</i> element.
     * @return such instance.
     */
    @NotNull
    public Sql getSql()
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
    protected MetadataTypeManager getMetadataTypeManager(
        @NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getMetadataTypeManager();
    }

    /**
     * Retrieves the value, in multiple lines.
     * @return such output.
     */
    @NotNull
    public String[] getSplittedQuotedValue()
    {
        return splitAndQuote(getValue(), DecorationUtils.getInstance());
    }

    /**
     * Splits given value into several lines, quoting each one.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the splitted value.
     */
    @NotNull
    protected String[] splitAndQuote(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return
            decorationUtils.surround(
                decorationUtils.trim(
                    decorationUtils.split(
                        decorationUtils.escape(value, '\"'))),
                "\"",
                " \"");
    }

    /**
     * Retrieves the id formatted as a constant.
     * @return such information.
     */
    @NotNull
    public String getIdAsConstant()
    {
        return uppercase(normalize(getId(), DecorationUtils.getInstance()));
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     */
    @NotNull
    protected String normalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    @NotNull
    public String getIdCapitalized()
    {
        return capitalize(getId(), DecorationUtils.getInstance());
    }

    /**
     * Transforms given value to upper case.
     * @param value the value.
     * @return <code>value.toUpperCase()</code>.
     */
    @NotNull
    protected String uppercase(@NotNull final String value)
    {
        return value.toUpperCase();
    }

    /**
     * Retrieves the name, (un)capitalized.
     * @return such information.
     */
    @NotNull
    public String getNameUncapitalized()
    {
        return uncapitalize(getName(), DecorationUtils.getInstance());
    }

    /**
     * (Un)capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     */
    @NotNull
    protected String uncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     */
    @NotNull
    protected String capitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    @NotNull
    public List<Parameter> getParameters()
    {
        return
            getParameters(
                getParameterRefs(),
                getCustomSqlProvider(),
                getMetadataTypeManager());
    }

    /**
     * Retrieves the parameters.
     * @todo fix reference to customSqlProvider.
     * @param parameterRefs the parameter references.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @NotNull
    protected List<Parameter> getParameters(
        @NotNull final List<ParameterRefElement> parameterRefs,
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
    protected List<Parameter> getParameters(
        @NotNull final List<ParameterRefElement> parameterRefs,
        @NotNull final SqlParameterDAO sqlParameterDAO,
        final MetadataTypeManager metadataTypeManager)
    {
        @NotNull List<Parameter> result = new ArrayList<Parameter>();

        for (@NotNull ParameterRefElement t_ParameterRef : parameterRefs)
        {
            @Nullable Parameter t_Parameter;

            if  (t_ParameterRef != null)
            {
                t_Parameter = sqlParameterDAO.findByPrimaryKey(t_ParameterRef.getId());

                if  (t_Parameter != null)
                {
                    result.add(
                        new CachingParameterDecorator(t_Parameter, metadataTypeManager));
                }
                else
                {
                    try
                    {
                        // todo throw something.
                        Log t_Log = UniqueLogFactory.getLog(SqlDecorator.class);

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
    @Nullable
    public String getResultClass()
    {
        return getResultClass(getResultRef(), getCustomSqlProvider());
    }

    /**
     * Retrieves the result class.
     * @param resultRef the result ref.
     * @param customSqlProvider the custom sql provider.
     * @return such information.
     */
    @Nullable
    protected String getResultClass(
        @Nullable final ResultRef resultRef,
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        return getResultClass(resultRef, customSqlProvider.getSqlResultDAO());
    }

    /**
     * Retrieves the result class.
     * @param resultRef the result ref.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @return such information.
     */
    @Nullable
    protected String getResultClass(
        @Nullable final ResultRef resultRef,
        @NotNull final SqlResultDAO resultDAO)
    {
        @Nullable String result = null;

        if  (resultRef != null)
        {
            @Nullable Result t_Result = resultDAO.findByPrimaryKey(resultRef.getId());

            if  (t_Result != null)
            {
                if  (Result.MULTIPLE.equalsIgnoreCase(
                         t_Result.getMatches()))
                {
                    result = MULTIPLE_RESULT_CLASS;
                }
                else
                {
                    result = t_Result.getClassValue();
                }
            }
            else
            {
                try
                {
                    // todo throw something.
                    Log t_Log = UniqueLogFactory.getLog("custom-sql");

                    if (t_Log != null)
                    {
                        t_Log.warn(
                              "Referenced result not found:"
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
     * Retrieves the result id as constant.
     * @return such information.
     */
    @Nullable
    public String getResultIdAsConstant()
    {
        return
            getResultIdAsConstant(
                getResultRef(),
                getCustomSqlProvider(),
                DecorationUtils.getInstance());
    }

    /**
     * Retrieves the result id as constant.
     * @param resultRef the result ref.
     * @param customSqlProvider the custom sql provider.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     */
    @Nullable
    protected String getResultIdAsConstant(
        @Nullable final ResultRef resultRef,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecorationUtils decorationUtils)
    {
        return getResultIdAsConstant(resultRef, customSqlProvider.getSqlResultDAO(), decorationUtils);
    }

    /**
     * Retrieves the result id as constant.
     * @param resultRef the result ref.
     * @param resultDAO the {@link SqlResultDAO} instance.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     */
    @Nullable
    protected String getResultIdAsConstant(
        @Nullable final ResultRef resultRef,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final DecorationUtils decorationUtils)
    {
        @Nullable String result = null;

        if  (resultRef != null)
        {
            @Nullable Result t_Result = resultDAO.findByPrimaryKey(resultRef.getId());

            if  (t_Result != null)
            {
                result =
                    uppercase(normalize(t_Result.getId(), decorationUtils));
            }
            else
            {
                try
                {
                    // todo throw something.
                    Log t_Log = UniqueLogFactory.getLog("custom-sql");

                    if (t_Log != null)
                    {
                        t_Log.warn(
                              "Referenced result not found:"
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
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("sql", getSql())
                .append("customSqlProvider", getCustomSqlProvider())
                .append("metadataManager", getMetadataManager())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getSql());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param sql the sql.
     * @return such information.
     */
    protected int hashCode(@NotNull final Sql sql)
    {
        return sql.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(@NotNull final Object object)
    {
        boolean result;

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
                        getSplittedQuotedValue(),
                        t_OtherInstance.getSplittedQuotedValue())
                    .append(
                        getIdAsConstant(),
                        t_OtherInstance.getIdAsConstant())
                    .append(
                        getIdCapitalized(),
                        t_OtherInstance.getIdCapitalized())
                    .append(
                        getNameUncapitalized(),
                        t_OtherInstance.getNameUncapitalized())
                    .append(
                        getParameters(),
                        t_OtherInstance.getParameters())
                    .append(
                        getResultClass(),
                        t_OtherInstance.getResultClass())
                    .append(
                        getResultIdAsConstant(),
                        t_OtherInstance.getResultIdAsConstant())
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
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(@Nullable final IdentifiableElement object)
        throws  ClassCastException
    {
        int result;

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
                    getSplittedQuotedValue(),
                    t_OtherInstance.getSplittedQuotedValue())
                .append(
                    getIdAsConstant(),
                    t_OtherInstance.getIdAsConstant())
                .append(
                    getIdCapitalized(),
                    t_OtherInstance.getIdCapitalized())
                .append(
                    getNameUncapitalized(),
                    t_OtherInstance.getNameUncapitalized())
                .append(
                    getParameters(),
                    t_OtherInstance.getParameters())
                .append(
                    getResultClass(),
                    t_OtherInstance.getResultClass())
                .append(
                    getResultIdAsConstant(),
                    t_OtherInstance.getResultIdAsConstant())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
