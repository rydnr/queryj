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
 * Filename: AbstractCustomSqlProvider.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Common logic for CustomSqlProviders.
 *
 * Date: 2014/03/08
 * Time: 19:32
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.xml.SqlXmlParser;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;

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
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Common logic for {@link CustomSqlProvider}s.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/08 19:32
 */
@ThreadSafe
public abstract class AbstractCustomSqlProvider
    implements CustomSqlProvider
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -4395843851323478336L;

    /**
     * The default separator when computing hashes.
     */
    protected static final String DEFAULT_SEPARATOR = "|";

    /**
     * Computes the hash of given String.
     * @param value the value.
     * @param charset the charset.
     * @return the hash.
     */
    @NotNull
    protected String getHash(@NotNull final String value, @NotNull final String charset)
    {
        @Nullable String result = null;

        try
        {
            @NotNull final MessageDigest t_MessageDigest = MessageDigest.getInstance("SHA1");

            result =
                URLEncoder.encode(
                    DatatypeConverter.printBase64Binary(t_MessageDigest.digest(value.getBytes(charset))), charset);
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
    public <T> String getHash(@NotNull final Sql<T> sql, @NotNull final String charset)
    {
        return
            getHash(
                sql.getId(),
                sql.getName(),
                sql.getType(),
                sql.getDao(),
                sql.isValidate(),
                sql.isDynamic(),
                sql.getValue(),
                sql.getRepositoryScope(),
                sql.getImplementation(),
                sql.getParameterRefs(),
                charset,
                getSqlParameterDAO(),
                getSqlResultDAO(),
                DEFAULT_SEPARATOR);
    }

    /**
     * Retrieves the hash of the SQL whose details are given.
     * @param id the id.
     * @param name the sql name.
     * @param type the type.
     * @param dao the DAO.
     * @param validate whether to validate or not.
     * @param dynamic whether it's dynamic or not.
     * @param value the value.
     * @param repositoryScoped whether it's repository-scoped or not.
     * @param implementation the implementation value.
     * @param parameterRefs the parameter refs.
     * @param charset the charset.
     * @param parameterDAO the {@link org.acmsl.queryj.metadata.SqlParameterDAO} instance.
     * @param resultDAO the {@link org.acmsl.queryj.metadata.SqlResultDAO} instance.
     * @param separator the hash separator.
     * @param <T> the type.
     * @return such hash.
     */
    @NotNull
    protected <T> String getHash(
        @NotNull final T id,
        @NotNull final T name,
        @NotNull final T type,
        @Nullable final T dao,
        final boolean validate,
        final boolean dynamic,
        @Nullable final T value,
        @Nullable final T repositoryScoped,
        @Nullable final T implementation,
        @NotNull final List<ParameterRef> parameterRefs,
        @NotNull final String charset,
        @NotNull final SqlParameterDAO parameterDAO,
        @NotNull final SqlResultDAO resultDAO,
        @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();
        t_Builder.append(name);
        t_Builder.append(separator);

        t_Builder.append(type);
        t_Builder.append(separator);

        if (dao != null)
        {
            t_Builder.append(dao);
            t_Builder.append(separator);
        }

        t_Builder.append(validate);
        t_Builder.append(separator);

        t_Builder.append(dynamic);
        t_Builder.append(separator);

        if (value != null)
        {
            t_Builder.append(value);
            t_Builder.append(separator);
        }

        if (repositoryScoped != null)
        {
            t_Builder.append(repositoryScoped);
        }
        else
        {
            t_Builder.append("null");
        }
        t_Builder.append(separator);

        if (implementation != null)
        {
            t_Builder.append(implementation);
        }
        else
        {
            t_Builder.append("null");
        }
        t_Builder.append(separator);

        for (@Nullable final ParameterRef t_ParameterRef : parameterRefs)
        {
            if (t_ParameterRef != null)
            {
                @Nullable final Parameter<String, ?> t_Parameter = parameterDAO.findByPrimaryKey(t_ParameterRef.getId());

                if (t_Parameter != null)
                {
                    t_Builder.append(getHash(t_Parameter, charset, separator));
                    t_Builder.append(separator);
                }
            }
        }

        @Nullable final Result<String> t_Result = resultDAO.findBySqlId(id.toString());

        if (t_Result != null)
        {
            t_Builder.append(getHash(t_Result, charset, separator));
        }

        result = getHash(t_Builder.toString(), charset);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T, V> String getHash(@NotNull final Parameter<T, V> parameter, @NotNull final String charset)
    {
        return getHash(parameter, charset, DEFAULT_SEPARATOR);
    }

    /**
     * Computes the hash for given {@link Parameter parameter}.
     * @param parameter the parameter.
     * @param charset the charset.
     * @param separator the hash separator.
     * @return the hash.
     * @param <T> the type.
     * @param <V> the type of the value.
     */
    @NotNull
    protected <T, V> String getHash(
        @NotNull final Parameter<T, V> parameter, @NotNull final String charset, @NotNull final String separator)
    {
        return getHash(parameter.getIndex(), parameter.getType(), charset, separator);
    }

    /**
     * Computes the hash for given {@link Parameter parameter}.
     * @param index the parameter index.
     * @param type the parameter type.
     * @param charset the charset.
     * @param separator the hash separator.
     * @return the hash.
     * @param <T> the type.
     */
    @NotNull
    protected <T> String getHash(
        final int index, @NotNull final T type, @NotNull final String charset, @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();

        t_Builder.append(index);
        t_Builder.append(separator);
        t_Builder.append(type);

        result = getHash(t_Builder.toString(), charset);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T> String getHash(@NotNull final Result<T> result, @NotNull final String charset)
    {
        return
            getHash(
                result.getClassValue(),
                result.getPropertyRefs(),
                charset,
                getSqlPropertyDAO(),
                DEFAULT_SEPARATOR);
    }

    /**
     * Computes the hash for given {@link org.acmsl.queryj.customsql.Result result}.
     * @param result the result.
     * @param charset the charset.
     * @param separator the separator.
     * @param <T> the type.
     * @return the hash.
     */
    @NotNull
    protected <T> String getHash(
        @NotNull final Result<T> result, @NotNull final String charset, @NotNull final String separator)
    {
        return getHash(result.getClassValue(), result.getPropertyRefs(), charset, getSqlPropertyDAO(), separator);
    }

    /**
     * Computes the hash for given {@link org.acmsl.queryj.customsql.Result result}.
     * @param classValue the class value.
     * @param propertyRefs the {@link PropertyRef property refs}.
     * @param charset the charset.
     * @param propertyDAO the {@link org.acmsl.queryj.metadata.SqlPropertyDAO property DAO}.
     * @param separator the separator.
     * @param <T> the type.
     * @return the hash.
     */
    @NotNull
    protected <T> String getHash(
        @Nullable final T classValue,
        @NotNull final List<PropertyRef> propertyRefs,
        @NotNull final String charset,
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
                    t_Builder.append(getHash(t_Property, charset, separator));
                }
            }
        }

        result = getHash(t_Builder.toString(), charset);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public <T> String getHash(@NotNull final Property<T> property, @NotNull final String charset)
    {
        return
            getHash(property.getColumnName(), property.getType(), property.isNullable(), charset, DEFAULT_SEPARATOR);
    }

    /**
     * Computes the hash for given {@link Property} information.
     * @param property the property.
     * @param charset the charset.
     * @param separator the hash separator.
     * @param <T> the type.
     * @return the computed hash.
     */
    @NotNull
    protected <T> String getHash(
        @NotNull final Property<T> property, @NotNull final String charset, @NotNull final String separator)
    {
        return getHash(property.getColumnName(), property.getType(), property.isNullable(), charset, separator);
    }

    /**
     * Computes the hash for given {@link Property} information.
     * @param columnName the column name.
     * @param type the column type.
     * @param nullable whether the column allows nulls.
     * @param charset the charset.
     * @param separator the hash separator.
     * @param <T> the type.
     * @return the computed hash.
     */
    @NotNull
    protected <T> String getHash(
        @NotNull final T columnName,
        @NotNull final T type,
        final boolean nullable,
        @NotNull final String charset,
        @NotNull final String separator)
    {
        @NotNull final String result;

        @NotNull final StringBuilder t_Builder = new StringBuilder();

        t_Builder.append(columnName);
        t_Builder.append(separator);
        t_Builder.append(type);
        t_Builder.append(separator);
        t_Builder.append(nullable);

        result = getHash(t_Builder.toString(), charset);

        return result;
    }
}
