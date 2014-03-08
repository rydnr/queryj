/*
                        queryj

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
 * Filename: AbstractCustomSqlProvider.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/03/08
 * Time: 19:32
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/08 19:32
 */
@ThreadSafe
public abstract class AbstractCustomSqlProvider
    implements CustomSqlProvider
{
    /**
     * The default separator when computing hashes.
     */
    protected static final String DEFAULT_SEPARATOR = "|";

    /**
     * Computes the hash of given String.
     * @param value the value.
     * @return the hash.
     */
    @NotNull
    protected String getHash(@NotNull final String value)
    {
        @Nullable String result = null;

        try
        {
            @NotNull final MessageDigest t_MessageDigest = MessageDigest.getInstance("SHA1");
            result = new String(t_MessageDigest.digest(value.getBytes("UTF-8")), "UTF-8");
        }
        catch (@NotNull final NoSuchAlgorithmException noSuchAlgorithm)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(SqlXmlParser.class);

            if (t_Log != null)
            {
                t_Log.fatal("Cannot use SHA1 for computing hashes", noSuchAlgorithm);
            }
        }
        catch (@NotNull final UnsupportedEncodingException unsupportedEncoding)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(SqlXmlParser.class);

            if (t_Log != null)
            {
                t_Log.fatal("Cannot use UTF-8 for encoding hashes", unsupportedEncoding);
            }
        }

        if (result == null)
        {
            result = "";
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T> String getHash(@NotNull final Sql<T> sql)
    {
        return
            getHash(
                sql.getId(),
                sql.getType(),
                sql.isValidate(),
                sql.isDynamic(),
                sql.getValue(),
                sql.getParameterRefs(),
                getSqlParameterDAO(),
                getSqlResultDAO(),
                DEFAULT_SEPARATOR);
    }


    /**
     * Retrieves the hash of the SQL whose details are given.
     * @param id the sql id.
     * @param type the type.
     * @param validate whether to validate or not.
     * @param dynamic whether it's dynamic or not.
     * @param value the value.
     * @param parameterRefs the parameter refs.
     * @param parameterDAO the {@link org.acmsl.queryj.metadata.SqlParameterDAO} instance.
     * @param resultDAO the {@link org.acmsl.queryj.metadata.SqlResultDAO} instance.
     * @param separator the hash separator.
     * @param <T> the type.
     * @return such hash.
     */
    @NotNull
    protected <T> String getHash(
        @NotNull final T id,
        @NotNull final T type,
        final boolean validate,
        final boolean dynamic,
        @Nullable final T value,
        @NotNull final List<ParameterRef> parameterRefs,
        @NotNull final SqlParameterDAO parameterDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();
        t_Builder.append(id);
        t_Builder.append(separator);

        t_Builder.append(type);
        t_Builder.append(separator);

        t_Builder.append(validate);
        t_Builder.append(separator);

        t_Builder.append(dynamic);
        t_Builder.append(separator);

        if (value != null)
        {
            t_Builder.append(value);
            t_Builder.append(separator);
        }

        for (@Nullable final ParameterRef t_ParameterRef : parameterRefs)
        {
            if (t_ParameterRef != null)
            {
                @Nullable final Parameter t_Parameter = parameterDAO.findByPrimaryKey(t_ParameterRef.getId());

                if (t_Parameter != null)
                {
                    t_Builder.append(getHash(t_Parameter));
                    t_Builder.append(separator);
                }
            }
        }

        @Nullable final Result t_Result = resultDAO.findBySqlId(id.toString());

        if (t_Result != null)
        {
            t_Builder.append(getHash(t_Result));
        }

        result = getHash(t_Builder.toString());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T, V> String getHash(@NotNull final Parameter<T, V> parameter)
    {
        return getHash(parameter, DEFAULT_SEPARATOR);
    }

    /**
     * Computes the hash for given {@link Parameter parameter}.
     * @param parameter the parameter.
     * @param separator the hash separator.
     * @param <T> the type.
     * @param <V> the type of the value.
     */
    @NotNull
    protected <T, V> String getHash(@NotNull final Parameter<T, V> parameter, @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();

        t_Builder.append(parameter.getIndex());
        t_Builder.append(separator);
        t_Builder.append(parameter.getType());

        result = getHash(t_Builder.toString());

        return result;
    }

    /**
     * Computes the hash for given {@link org.acmsl.queryj.customsql.Result result}.
     *
     * @param result the result.
     * @param <T>    the type.
     * @return the hash.
     */
    @NotNull
    @Override
    public <T> String getHash(@NotNull final Result<T> result)
    {
        return getHash(result.getClassValue(), result.getPropertyRefs(), getSqlPropertyDAO(), DEFAULT_SEPARATOR);
    }

    /**
     * Computes the hash for given {@link org.acmsl.queryj.customsql.Result result}.
     * @param classValue the class value.
     * @param propertyRefs the {@link PropertyRef property refs}.
     * @param propertyDAO the {@link org.acmsl.queryj.metadata.SqlPropertyDAO property DAO}.
     * @param separator the separator.
     * @param <T> the type.
     * @return the hash.
     */
    @NotNull
    protected <T> String getHash(
        @Nullable final T classValue,
        @NotNull final List<PropertyRef> propertyRefs,
        @NotNull final SqlPropertyDAO propertyDAO,
        @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();

        if (classValue != null)
        {
            t_Builder.append(classValue);
            t_Builder.append(separator);
        }

        for (@Nullable final PropertyRef t_PropertyRef : propertyRefs)
        {
            if (t_PropertyRef != null)
            {
                @Nullable final Property<String> t_Property = propertyDAO.findByPrimaryKey(t_PropertyRef.getId());

                if (t_Property != null)
                {
                    t_Builder.append(t_Property.getColumnName());
                    t_Builder.append(separator);
                    t_Builder.append(t_Property.getType());
                    t_Builder.append(separator);
                }
            }
        }

        result = getHash(t_Builder.toString());

        return result;
    }
}
