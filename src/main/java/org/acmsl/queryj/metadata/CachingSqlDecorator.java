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
    /**
     * A cached empty String array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final long serialVersionUID = 4468587636043277021L;

    /**
     * The cached splitted quoted value.
     */
    private String[] m__astrCachedSplittedQuotedValue;

    /**
     * The cached id formatted as constant.
     */
    private String m__strCachedIdAsConstant;

    /**
     * The cached capitalized id.
     */
    private String m__strCachedIdCapitalized;

    /**
     * The cached uncapitalized name.
     */
    private String m__strCachedNameUncapitalized;

    /**
     * The cached parameters.
     */
    private List<Parameter> m__cCachedParameters;

    /**
     * The cached result class.
     */
    private String m__strCachedResultClass;

    /**
     * The cached result id as constant.
     */
    private String m__strCachedResultIdAsConstant;

    /**
     * Creates a <code>CachingSqlDecorator</code> with given information.
     * @param sql the <code>Sql</code> to decorate.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the metadata manager.
     */
    public CachingSqlDecorator(
        @NotNull final Sql sql,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        super(sql, customSqlProvider, metadataManager);
    }

    /**
     * Specifies the cached split quoted value.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedSplittedQuotedValue(
        @NotNull final String[] value)
    {
        m__astrCachedSplittedQuotedValue = value;
    }

    /**
     * Specifies the cached split quoted value.
     * @param value the value to cache.
     */
    protected void setCachedSplittedQuotedValue(final String[] value)
    {
        immutableSetCachedSplittedQuotedValue(value);
    }

    /**
     * Retrieves the cached split quoted value.
     * @return such value.
     */
    @Nullable
    protected final String[] immutableGetCachedSplittedQuotedValue()
    {
        return m__astrCachedSplittedQuotedValue;
    }

    /**
     * Retrieves the cached splitted quoted value.
     * @return such value.
     */
    @Nullable
    public String[] getCachedSplittedQuotedValue()
    {
        return clone(immutableGetCachedSplittedQuotedValue());
    }

    /**
     * Retrieves the value, in multiple lines.
     * @return such output.
     */
    @NotNull
    public String[] getSplittedQuotedValue()
    {
        String[] result = getCachedSplittedQuotedValue();

        if  (   (result == null)
             || (result.length == 0))
        {
            result = super.getSplittedQuotedValue();
            setCachedSplittedQuotedValue(result);
        }

        return result;
    }

    /**
     * Specifies the cached id formatted as constant.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdAsConstant(
        @NotNull final String value)
    {
        m__strCachedIdAsConstant = value;
    }

    /**
     * Specifies the cached id formatted as constant.
     * @param value the value to cache.
     */
    protected void setCachedIdAsConstant(final String value)
    {
        immutableSetCachedIdAsConstant(value);
    }

    /**
     * Retrieves the cached id formatted as constant.
     * @return such value.
     */
    public String getCachedIdAsConstant()
    {
        return m__strCachedIdAsConstant;
    }

    /**
     * Retrieves the id formatted as constant.
     * @return such information.
     */
    @NotNull
    public String getIdAsConstant()
    {
        String result = getCachedIdAsConstant();

        if  (result == null)
        {
            result = super.getIdAsConstant();
            setCachedIdAsConstant(result);
        }

        return result;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedIdCapitalized(
        final String value)
    {
        m__strCachedIdCapitalized = value;
    }

    /**
     * Specifies the cached capitalized id.
     * @param value the value to cache.
     */
    protected void setCachedIdCapitalized(final String value)
    {
        immutableSetCachedIdCapitalized(value);
    }

    /**
     * Retrieves the cached capitalized id.
     * @return such value.
     */
    public String getCachedIdCapitalized()
    {
        return m__strCachedIdCapitalized;
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    @NotNull
    public String getIdCapitalized()
    {
        String result = getCachedIdCapitalized();

        if  (result == null)
        {
            result = super.getIdCapitalized();
            setCachedIdCapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedNameUncapitalized(
        final String value)
    {
        m__strCachedNameUncapitalized = value;
    }

    /**
     * Specifies the cached uncapitalized name.
     * @param value the value to cache.
     */
    protected void setCachedNameUncapitalized(final String value)
    {
        immutableSetCachedNameUncapitalized(value);
    }

    /**
     * Retrieves the cached uncapitalized name.
     * @return such value.
     */
    public String getCachedNameUncapitalized()
    {
        return m__strCachedNameUncapitalized;
    }

    /**
     * Retrieves the name, (un)capitalized.
     * @return such information.
     */
    @NotNull
    public String getNameUncapitalized()
    {
        String result = getCachedNameUncapitalized();

        if  (result == null)
        {
            result = super.getNameUncapitalized();
            setCachedNameUncapitalized(result);
        }

        return result;
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedParameters(
        @NotNull final List<Parameter> value)
    {
        m__cCachedParameters = value;
    }

    /**
     * Specifies the cached parameters.
     * @param value the value to cache.
     */
    protected void setCachedParameters(@NotNull final List<Parameter> value)
    {
        immutableSetCachedParameters(value);
    }

    /**
     * Retrieves the cached parameters.
     * @return such value.
     */
    @Nullable
    public List<Parameter> getCachedParameters()
    {
        return m__cCachedParameters;
    }

    /**
     * Retrieves the parameters.
     * @return such information.
     */
    @NotNull
    public List<Parameter> getParameters()
    {
        List<Parameter> result = getCachedParameters();

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
     * Specifies the cached result id as constant.
     * @param value the value to cache.
     */
    protected final void immutableSetCachedResultIdAsConstant(
        final String value)
    {
        m__strCachedResultIdAsConstant = value;
    }

    /**
     * Specifies the cached result id as constant.
     * @param value the value to cache.
     */
    protected void setCachedResultIdAsConstant(final String value)
    {
        immutableSetCachedResultIdAsConstant(value);
    }

    /**
     * Retrieves the cached result id as constant.
     * @return such value.
     */
    public String getCachedResultIdAsConstant()
    {
        return m__strCachedResultIdAsConstant;
    }

    /**
     * Retrieves the result id as constant.
     * @return such information.
     */
    public String getResultIdAsConstant()
    {
        @Nullable String result = getCachedResultIdAsConstant();

        if  (result == null)
        {
            result = super.getResultIdAsConstant();
            setCachedResultIdAsConstant(result);
        }

        return result;
    }

    /**
     * Clones given String array.
     * @param array the array to clone.
     * @return the cloned array.
     */
    @NotNull
    protected String[] clone(@Nullable final String[] array)
    {
        @NotNull String[] result = EMPTY_STRING_ARRAY;

        if (array != null)
        {
            result = new String[array.length];

            System.arraycopy(array, 0, result, 0, array.length);
        }

        return result;
    }
}
