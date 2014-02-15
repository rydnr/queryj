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
 * Filename: CachingSqlDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds a simple caching mechanism while decorating <sql>
 *              instances.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.Sql;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adds a simple caching mechanism while decorating <code>sql</code>
 * instances.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class CachingSqlDecorator
    extends  AbstractSqlDecorator
{
    private static final long serialVersionUID = 4468587636043277021L;

    /**
     * The cached parameters.
     */
    private List<Parameter<DecoratedString, ?>> m__cCachedParameters;

    /**
     * The cached result class.
     */
    private String m__strCachedResultClass;

    /**
     * The cached wrapped parameters check.
     */
    private Boolean m__bWrappedParametersCheck = null;

    /**
     * Creates a <code>CachingSqlDecorator</code> with given information.
     * @param sql the <code>Sql</code> to decorate.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the metadata manager.
     */
    public CachingSqlDecorator(
        @NotNull final Sql<String> sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        super(sql, customSqlProvider, metadataManager);
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedParameters(
        @NotNull final List<Parameter<DecoratedString, ?>> value)
    {
        m__cCachedParameters = value;
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected void setCachedParameters(@NotNull final List<Parameter<DecoratedString, ?>> value)
    {
        immutableSetCachedParameters(value);
    }

    /**
     * Retrieves the cached parameters.
     * @return such value.
     */
    @Nullable
    public List<Parameter<DecoratedString, ?>> getCachedParameters()
    {
        return m__cCachedParameters;
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    @NotNull
    @Override
    public List<Parameter<DecoratedString, ?>> getParameters()
    {
        List<Parameter<DecoratedString, ?>> result = getCachedParameters();

        if  (result == null)
        {
            result = super.getParameters();
            setCachedParameters(result);
        }

        return result;
    }

    /**
     * Specifies the cached result class.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedResultClass(
        final String value)
    {
        m__strCachedResultClass = value;
    }

    /**
     * Specifies the cached result class.
     * @param value the value to cache.
     */
    protected void setCachedResultClass(final String value)
    {
        immutableSetCachedResultClass(value);
    }

    /**
     * Retrieves the cached result class.
     * @return such value.
     */
    public String getCachedResultClass()
    {
        return m__strCachedResultClass;
    }

    /**
     * Retrieves the result class.
     * @return such information.
     */
    @Override
    @Nullable
    public String getResultClass()
    {
        @Nullable String result = getCachedResultClass();

        if  (result == null)
        {
            result = super.getResultClass();
            setCachedResultClass(result);
        }

        return result;
    }

    /**
     * Caches the calculation for whether the parameter list would take too much space
     * and should be wrapped.
     * @param check such calculation.
     */
    protected final void immutableSetCachedParametersShouldBeWrapped(final boolean check)
    {
        this.m__bWrappedParametersCheck = check;
    }

    /**
     * Caches the calculation for whether the parameter list would take too much space
     * and should be wrapped.
     * @param check such calculation.
     */
    @SuppressWarnings("unused")
    protected void setCachedParametersShouldBeWrapped(final boolean check)
    {
        immutableSetCachedParametersShouldBeWrapped(check);
    }

    /**
     * Retrieves the cached calculation for whether the parameter list would take too much space
     * and should be wrapped.
     * @return such information.
     */
    @Nullable
    protected Boolean getCachedParametersShouldBeWrapped()
    {
        return this.m__bWrappedParametersCheck;
    }

    /**
     * Checks whether the parameter list would take too much space and should be wrapped.
     * @return such information.
     */
    @Override
    public boolean getParametersShouldBeWrapped()
    {
        Boolean result = getCachedParametersShouldBeWrapped();

        if (result == null)
        {
            result = super.getParametersShouldBeWrapped();
            setCachedParametersShouldBeWrapped(result);
        }

        return result;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "CachingSqlDecorator{" +
               "m__bWrappedParametersCheck=" + m__bWrappedParametersCheck +
               ", m__cCachedParameters=" + m__cCachedParameters +
               ", m__strCachedResultClass='" + m__strCachedResultClass + '\'' +
               '}';
    }
}
