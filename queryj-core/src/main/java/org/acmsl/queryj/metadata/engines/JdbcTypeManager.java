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
 * Filename: JdbcTypeManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Manages the relationship between JDBC types and Java classes
 *              / primitives.
 *
 * Date: 2014/02/22
 * Time: 10:40
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the relationship between JDBC types and Java classes / primitives.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/22 10:40
 */
@ThreadSafe
public class JdbcTypeManager
{
    @NotNull protected static final Map<String, Integer> TYPE_MAPPING = new HashMap<>();
    @NotNull protected static final Map<Integer, String> INVERSE_TYPE_MAPPING = new HashMap<>();
    @NotNull protected static final Map<Integer, Class<?>> CLASS_MAPPING = new HashMap<>();
    @NotNull protected static final Map<Class<?>, Integer> INVERSE_CLASS_MAPPING = new HashMap<>();
    @NotNull protected static final Map<Integer, Method> PREPARED_STATEMENT_METHODS = new HashMap<>();

    /**
     * Literals.
     */
    @NotNull protected static final String ARRAY = "array";
    @NotNull protected static final String BIGINT = "bigint";
    @NotNull protected static final String BINARY = "binary";
    @NotNull protected static final String BIT = "bit";
    @NotNull protected static final String BLOB = "blob";
    @NotNull protected static final String BOOLEAN = "boolean";
    @NotNull protected static final String CHAR = "char";
    @NotNull protected static final String CLOB = "clob";
    @NotNull protected static final String DATALINK = "datalink";
    @NotNull protected static final String DATE = "date";
    @NotNull protected static final String DECIMAL = "decimal";
    @NotNull protected static final String DISTINCT = "distinct";
    @NotNull protected static final String DOUBLE = "double";
    @NotNull protected static final String FLOAT = "float";
    @NotNull protected static final String INTEGER = "integer";
    @NotNull protected static final String JAVA_OBJECT = "java_object";
    @NotNull protected static final String LONGNVARCHAR = "longnvarchar";
    @NotNull protected static final String LONGVARBINARY = "longvarbinary";
    @NotNull protected static final String LONGVARCHAR = "longvarchar";
    @NotNull protected static final String NCHAR = "nchar";
    @NotNull protected static final String NCLOB = "nclob";
    @NotNull protected static final String NULL = "null";
    @NotNull protected static final String NUMERIC = "numeric";
    @NotNull protected static final String NVARCHAR = "nvarchar";
    @NotNull protected static final String OTHER = "other";
    @NotNull protected static final String REAL = "real";
    @NotNull protected static final String REF = "ref";
    @NotNull protected static final String ROWID = "rowid";
    @NotNull protected static final String SQLXML = "sqlxml";
    @NotNull protected static final String SMALLINT = "smallint";
    @NotNull protected static final String STRUCT = "struct";
    @NotNull protected static final String TIME = "time";
    @NotNull protected static final String TIMESTAMP = "timestamp";
    @NotNull protected static final String TINYINT = "tinyint";
    @NotNull protected static final String VARBINARY = "varbinary";
    @NotNull protected static final String VARCHAR = "varchar";

    protected static final Method SET_ARRAY_METHOD;
    protected static final Method SET_ASCII_STREAM_METHOD;
    protected static final Method SET_BIG_DECIMAL_METHOD;
    protected static final Method SET_BINARY_STREAM_METHOD;
    protected static final Method SET_BLOB_METHOD;
    protected static final Method SET_BOOLEAN_METHOD;
    protected static final Method SET_BYTE_METHOD;
    protected static final Method SET_BYTES_METHOD;
    protected static final Method SET_CHARACTER_STREAM_METHOD;
    protected static final Method SET_CLOB_METHOD;
    protected static final Method SET_DATE_METHOD;
    protected static final Method SET_DOUBLE_METHOD;
    protected static final Method SET_FLOAT_METHOD;
    protected static final Method SET_INT_METHOD;
    protected static final Method SET_LONG_METHOD;
    protected static final Method SET_N_CHARACTER_STREAM_METHOD;
    protected static final Method SET_N_CLOB_METHOD;
    protected static final Method SET_N_STRING_METHOD;
    protected static final Method SET_NULL_METHOD;
    protected static final Method SET_OBJECT_METHOD;
    protected static final Method SET_REF_METHOD;
    protected static final Method SET_ROW_ID_METHOD;
    protected static final Method SET_SHORT_METHOD;
    protected static final Method SET_SQLXML_METHOD;
    protected static final Method SET_STRING_METHOD;
    protected static final Method SET_TIME_METHOD;
    protected static final Method SET_TIMESTAMP_METHOD;
    protected static final Method SET_URL_METHOD;

    static
    {
        @Nullable Method setArrayMethod = null;
        @Nullable Method setAsciiStreamMethod = null;
        @Nullable Method setBigDecimalMethod = null;
        @Nullable Method setBinaryStreamMethod = null;
        @Nullable Method setBlobMethod = null;
        @Nullable Method setBooleanMethod = null;
        @Nullable Method setByteMethod = null;
        @Nullable Method setBytesMethod = null;
        @Nullable Method setCharacterStreamMethod = null;
        @Nullable Method setClobMethod = null;
        @Nullable Method setDateMethod = null;
        @Nullable Method setDoubleMethod = null;
        @Nullable Method setFloatMethod = null;
        @Nullable Method setIntMethod = null;
        @Nullable Method setLongMethod = null;
        @Nullable Method setNCharacterStreamMethod = null;
        @Nullable Method setNClobMethod = null;
        @Nullable Method setNStringMethod = null;
        @Nullable Method setNullMethod = null;
        @Nullable Method setObjectMethod = null;
        @Nullable Method setRefMethod = null;
        @Nullable Method setRowIdMethod = null;
        @Nullable Method setShortMethod = null;
        @Nullable Method setSqlXmlMethod = null;
        @Nullable Method setStringMethod = null;
        @Nullable Method setTimeMethod = null;
        @Nullable Method setTimestampMethod = null;
        @Nullable Method setUrlMethod = null;

        try
        {
            setArrayMethod = PreparedStatement.class.getMethod("setArray", int.class, Array.class);
            setAsciiStreamMethod =
                PreparedStatement.class.getMethod("setAsciiStream", int.class, InputStream.class, long.class);
            setBigDecimalMethod = PreparedStatement.class.getMethod("setBigDecimal", int.class, BigDecimal.class);
            setBinaryStreamMethod =
                PreparedStatement.class.getMethod("setBinaryStream", int.class, InputStream.class, long.class);
            setBlobMethod = PreparedStatement.class.getMethod("setBlob", int.class, InputStream.class, long.class);
            setBooleanMethod = PreparedStatement.class.getMethod("setBoolean", int.class, boolean.class);
            setByteMethod = PreparedStatement.class.getMethod("setByte", int.class, byte.class);
            setBytesMethod = PreparedStatement.class.getMethod("setBytes", int.class, new byte[0].getClass());
            setCharacterStreamMethod =
                PreparedStatement.class.getMethod("setCharacterStream", int.class, Reader.class, long.class);
            setClobMethod = PreparedStatement.class.getMethod("setClob", int.class, Reader.class, long.class);
            setDateMethod =
                PreparedStatement.class.getMethod("setDate", int.class, java.sql.Date.class, Calendar.class);
            setDoubleMethod = PreparedStatement.class.getMethod("setDouble", int.class, double.class);
            setFloatMethod = PreparedStatement.class.getMethod("setFloat", int.class, float.class);
            setIntMethod = PreparedStatement.class.getMethod("setInt", int.class, int.class);
            setLongMethod = PreparedStatement.class.getMethod("setLong", int.class, long.class);
            setNCharacterStreamMethod =
                PreparedStatement.class.getMethod("setNCharacterStream", int.class, Reader.class, long.class);
            setNClobMethod = PreparedStatement.class.getMethod("setNClob", int.class, Reader.class, long.class);
            setNStringMethod = PreparedStatement.class.getMethod("setNString", int.class, String.class);
            setNullMethod = PreparedStatement.class.getMethod("setNull", int.class, int.class);
            setObjectMethod =
                PreparedStatement.class.getMethod("setObject", int.class, Object.class, int.class, int.class);
            setRefMethod = PreparedStatement.class.getMethod("setRef", int.class, Ref.class);
            setRowIdMethod = PreparedStatement.class.getMethod("setRowId", int.class, RowId.class);
            setShortMethod = PreparedStatement.class.getMethod("setShort", int.class, short.class);
            setSqlXmlMethod = PreparedStatement.class.getMethod("setSQLXML", int.class, java.sql.SQLXML.class);
            setStringMethod = PreparedStatement.class.getMethod("setString", int.class, String.class);
            setTimeMethod = PreparedStatement.class.getMethod("setTime", int.class, Time.class, Calendar.class);
            setTimestampMethod =
                PreparedStatement.class.getMethod("setTimestamp", int.class, Timestamp.class, Calendar.class);
            setUrlMethod = PreparedStatement.class.getMethod("setURL", int.class, java.net.URL.class);
        }
        catch (@NotNull final NoSuchMethodException noSuchMethod)
        {
            UniqueLogFactory.getLog(JdbcTypeManager.class).fatal(noSuchMethod.getMessage(), noSuchMethod);
        }
        SET_ARRAY_METHOD = setArrayMethod;
        SET_ASCII_STREAM_METHOD = setAsciiStreamMethod;
        SET_BIG_DECIMAL_METHOD = setBigDecimalMethod;
        SET_BINARY_STREAM_METHOD = setBinaryStreamMethod;
        SET_BLOB_METHOD = setBlobMethod;
        SET_BOOLEAN_METHOD = setBooleanMethod;
        SET_BYTE_METHOD = setByteMethod;
        SET_BYTES_METHOD = setBytesMethod;
        SET_CHARACTER_STREAM_METHOD = setCharacterStreamMethod;
        SET_CLOB_METHOD = setClobMethod;
        SET_DATE_METHOD = setDateMethod;
        SET_DOUBLE_METHOD = setDoubleMethod;
        SET_FLOAT_METHOD = setFloatMethod;
        SET_INT_METHOD = setIntMethod;
        SET_LONG_METHOD = setLongMethod;
        SET_N_CHARACTER_STREAM_METHOD = setNCharacterStreamMethod;
        SET_N_CLOB_METHOD = setNClobMethod;
        SET_N_STRING_METHOD = setNStringMethod;
        SET_NULL_METHOD = setNullMethod;
        SET_OBJECT_METHOD = setObjectMethod;
        SET_REF_METHOD = setRefMethod;
        SET_ROW_ID_METHOD = setRowIdMethod;
        SET_SHORT_METHOD = setShortMethod;
        SET_SQLXML_METHOD = setSqlXmlMethod;
        SET_STRING_METHOD = setStringMethod;
        SET_TIME_METHOD = setTimeMethod;
        SET_TIMESTAMP_METHOD = setTimestampMethod;
        SET_URL_METHOD = setUrlMethod;

        TYPE_MAPPING.put(ARRAY, Types.ARRAY);
        INVERSE_TYPE_MAPPING.put(Types.ARRAY, ARRAY);
        CLASS_MAPPING.put(Types.ARRAY, java.sql.Array.class);
        INVERSE_CLASS_MAPPING.put(java.sql.Array.class, Types.ARRAY);
        PREPARED_STATEMENT_METHODS.put(Types.ARRAY, SET_ARRAY_METHOD);

        TYPE_MAPPING.put(BIGINT, Types.BIGINT);
        INVERSE_TYPE_MAPPING.put(Types.BIGINT, BIGINT);
        CLASS_MAPPING.put(Types.BIGINT, long.class);
        INVERSE_CLASS_MAPPING.put(long.class, Types.BIGINT);
        PREPARED_STATEMENT_METHODS.put(Types.BIGINT, SET_LONG_METHOD);

        TYPE_MAPPING.put(BINARY, Types.BINARY);
        INVERSE_TYPE_MAPPING.put(Types.BINARY, BINARY);
        CLASS_MAPPING.put(Types.BINARY, new byte[0].getClass());
        INVERSE_CLASS_MAPPING.put(new byte[0].getClass(), Types.BINARY);
        PREPARED_STATEMENT_METHODS.put(Types.BINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(BIT, Types.BIT);
        INVERSE_TYPE_MAPPING.put(Types.BIT, BIT);
        CLASS_MAPPING.put(Types.BIT, boolean.class);
        PREPARED_STATEMENT_METHODS.put(Types.BIT, SET_BOOLEAN_METHOD);

        TYPE_MAPPING.put(BLOB, Types.BLOB);
        INVERSE_TYPE_MAPPING.put(Types.BLOB, BLOB);
        CLASS_MAPPING.put(Types.BLOB, Blob.class);
        INVERSE_CLASS_MAPPING.put(Blob.class, Types.BLOB);
        PREPARED_STATEMENT_METHODS.put(Types.BLOB, SET_BLOB_METHOD);

        TYPE_MAPPING.put(BOOLEAN, Types.BOOLEAN);
        INVERSE_TYPE_MAPPING.put(Types.BOOLEAN, BOOLEAN);
        CLASS_MAPPING.put(Types.BOOLEAN, boolean.class);
        INVERSE_CLASS_MAPPING.put(boolean.class, Types.BOOLEAN);
        PREPARED_STATEMENT_METHODS.put(Types.BOOLEAN, SET_BOOLEAN_METHOD);

        TYPE_MAPPING.put(CHAR, Types.CHAR);
        INVERSE_TYPE_MAPPING.put(Types.CHAR, CHAR);
        CLASS_MAPPING.put(Types.CHAR, String.class);
        INVERSE_CLASS_MAPPING.put(String.class, Types.CHAR);
        PREPARED_STATEMENT_METHODS.put(Types.CHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(CLOB, Types.CLOB);
        INVERSE_TYPE_MAPPING.put(Types.CLOB, CLOB);
        CLASS_MAPPING.put(Types.CLOB, Clob.class);
        INVERSE_CLASS_MAPPING.put(Clob.class, Types.CLOB);
        PREPARED_STATEMENT_METHODS.put(Types.CLOB, SET_CLOB_METHOD);

        TYPE_MAPPING.put(DATALINK, Types.DATALINK);
        INVERSE_TYPE_MAPPING.put(Types.DATALINK, DATALINK);
        CLASS_MAPPING.put(Types.DATALINK, java.net.URL.class);
        INVERSE_CLASS_MAPPING.put(java.net.URL.class, Types.DATALINK);
        PREPARED_STATEMENT_METHODS.put(Types.DATALINK, SET_URL_METHOD);

        TYPE_MAPPING.put(DATE, Types.DATE);
        INVERSE_TYPE_MAPPING.put(Types.DATE, DATE);
        CLASS_MAPPING.put(Types.DATE, Date.class);
        INVERSE_CLASS_MAPPING.put(Date.class, Types.DATE);
        PREPARED_STATEMENT_METHODS.put(Types.DATE, SET_DATE_METHOD);

        TYPE_MAPPING.put(DECIMAL, Types.DECIMAL);
        INVERSE_TYPE_MAPPING.put(Types.DECIMAL, DECIMAL);
        CLASS_MAPPING.put(Types.DECIMAL, BigDecimal.class);
        PREPARED_STATEMENT_METHODS.put(Types.DECIMAL, SET_BIG_DECIMAL_METHOD);

        TYPE_MAPPING.put(DISTINCT, Types.DISTINCT);
        INVERSE_TYPE_MAPPING.put(Types.DISTINCT, DISTINCT);
        CLASS_MAPPING.put(Types.DISTINCT, void.class);

        TYPE_MAPPING.put(DOUBLE, Types.DOUBLE);
        INVERSE_TYPE_MAPPING.put(Types.DOUBLE, DOUBLE);
        CLASS_MAPPING.put(Types.DOUBLE, double.class);
        INVERSE_CLASS_MAPPING.put(double.class, Types.DOUBLE);
        PREPARED_STATEMENT_METHODS.put(Types.DOUBLE, SET_DOUBLE_METHOD);

        TYPE_MAPPING.put(FLOAT, Types.FLOAT);
        INVERSE_TYPE_MAPPING.put(Types.FLOAT, FLOAT);
        CLASS_MAPPING.put(Types.FLOAT, double.class);
        PREPARED_STATEMENT_METHODS.put(Types.FLOAT, SET_DOUBLE_METHOD);

        TYPE_MAPPING.put(INTEGER, Types.INTEGER);
        INVERSE_TYPE_MAPPING.put(Types.INTEGER, INTEGER);
        CLASS_MAPPING.put(Types.INTEGER, int.class);
        INVERSE_CLASS_MAPPING.put(int.class, Types.INTEGER);
        PREPARED_STATEMENT_METHODS.put(Types.INTEGER, SET_INT_METHOD);

        TYPE_MAPPING.put(JAVA_OBJECT, Types.JAVA_OBJECT);
        INVERSE_TYPE_MAPPING.put(Types.JAVA_OBJECT, JAVA_OBJECT);
        CLASS_MAPPING.put(Types.JAVA_OBJECT, Object.class);
        INVERSE_CLASS_MAPPING.put(Object.class, Types.JAVA_OBJECT);
        PREPARED_STATEMENT_METHODS.put(Types.JAVA_OBJECT, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(LONGNVARCHAR, Types.LONGNVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.LONGNVARCHAR, LONGNVARCHAR);
        CLASS_MAPPING.put(Types.LONGNVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.LONGNVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(LONGVARBINARY, Types.LONGVARBINARY);
        INVERSE_TYPE_MAPPING.put(Types.LONGVARBINARY, LONGVARBINARY);
        CLASS_MAPPING.put(Types.LONGVARBINARY, new byte[0].getClass());
        PREPARED_STATEMENT_METHODS.put(Types.LONGVARBINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(LONGVARCHAR, Types.LONGVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.LONGVARCHAR, LONGVARCHAR);
        CLASS_MAPPING.put(Types.LONGVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.LONGVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(NCHAR, Types.NCHAR);
        INVERSE_TYPE_MAPPING.put(Types.NCHAR, NCHAR);
        CLASS_MAPPING.put(Types.NCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.NCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(NCLOB, Types.NCLOB);
        INVERSE_TYPE_MAPPING.put(Types.NCLOB, NCLOB);
        CLASS_MAPPING.put(Types.NCLOB, NClob.class);
        INVERSE_CLASS_MAPPING.put(NClob.class, Types.NCLOB);
        PREPARED_STATEMENT_METHODS.put(Types.NCLOB, SET_N_CLOB_METHOD);

        TYPE_MAPPING.put(NULL, Types.NULL);
        INVERSE_TYPE_MAPPING.put(Types.NULL, NULL);
        CLASS_MAPPING.put(Types.NULL, void.class);

        TYPE_MAPPING.put(NUMERIC, Types.NUMERIC);
        INVERSE_TYPE_MAPPING.put(Types.NUMERIC, NUMERIC);
        CLASS_MAPPING.put(Types.NUMERIC, BigDecimal.class);
        INVERSE_CLASS_MAPPING.put(BigDecimal.class, Types.NUMERIC);
        PREPARED_STATEMENT_METHODS.put(Types.NUMERIC, SET_BIG_DECIMAL_METHOD);

        TYPE_MAPPING.put(NVARCHAR, Types.NVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.NVARCHAR, NVARCHAR);
        CLASS_MAPPING.put(Types.NVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.NVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(OTHER, Types.OTHER);
        INVERSE_TYPE_MAPPING.put(Types.OTHER, OTHER);
        CLASS_MAPPING.put(Types.OTHER, void.class);
        INVERSE_CLASS_MAPPING.put(void.class, Types.OTHER);
        PREPARED_STATEMENT_METHODS.put(Types.OTHER, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(REAL, Types.REAL);
        INVERSE_TYPE_MAPPING.put(Types.REAL, REAL);
        CLASS_MAPPING.put(Types.REAL, float.class);
        INVERSE_CLASS_MAPPING.put(float.class, Types.REAL);
        PREPARED_STATEMENT_METHODS.put(Types.REAL, SET_FLOAT_METHOD);

        TYPE_MAPPING.put(REF, Types.REF);
        INVERSE_TYPE_MAPPING.put(Types.REF, REF);
        CLASS_MAPPING.put(Types.REF, Ref.class);
        INVERSE_CLASS_MAPPING.put(Ref.class, Types.REF);
        PREPARED_STATEMENT_METHODS.put(Types.REF, SET_REF_METHOD);

        TYPE_MAPPING.put(ROWID, Types.ROWID);
        INVERSE_TYPE_MAPPING.put(Types.ROWID, ROWID);
        CLASS_MAPPING.put(Types.ROWID, RowId.class);
        INVERSE_CLASS_MAPPING.put(RowId.class, Types.ROWID);
        PREPARED_STATEMENT_METHODS.put(Types.ROWID, SET_ROW_ID_METHOD);

        TYPE_MAPPING.put(SMALLINT, Types.SMALLINT);
        INVERSE_TYPE_MAPPING.put(Types.SMALLINT, SMALLINT);
        CLASS_MAPPING.put(Types.SMALLINT, short.class);
        INVERSE_CLASS_MAPPING.put(short.class, Types.SMALLINT);
        PREPARED_STATEMENT_METHODS.put(Types.SMALLINT, SET_SHORT_METHOD);

        TYPE_MAPPING.put(SQLXML, Types.SQLXML);
        INVERSE_TYPE_MAPPING.put(Types.SQLXML, SQLXML);
        CLASS_MAPPING.put(Types.SQLXML, java.sql.SQLXML.class);
        INVERSE_CLASS_MAPPING.put(java.sql.SQLXML.class, Types.SQLXML);
        PREPARED_STATEMENT_METHODS.put(Types.SQLXML, SET_SQLXML_METHOD);

        TYPE_MAPPING.put(STRUCT, Types.STRUCT);
        INVERSE_TYPE_MAPPING.put(Types.STRUCT, STRUCT);
        CLASS_MAPPING.put(Types.STRUCT, Struct.class);
        INVERSE_CLASS_MAPPING.put(Struct.class, Types.STRUCT);
        PREPARED_STATEMENT_METHODS.put(Types.STRUCT, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(TIME, Types.TIME);
        INVERSE_TYPE_MAPPING.put(Types.TIME, TIME);
        CLASS_MAPPING.put(Types.TIME, Time.class);
        INVERSE_CLASS_MAPPING.put(Time.class, Types.TIME);
        PREPARED_STATEMENT_METHODS.put(Types.TIME, SET_TIME_METHOD);

        TYPE_MAPPING.put(TIMESTAMP, Types.TIMESTAMP);
        INVERSE_TYPE_MAPPING.put(Types.TIMESTAMP, TIMESTAMP);
        CLASS_MAPPING.put(Types.TIMESTAMP, Timestamp.class);
        INVERSE_CLASS_MAPPING.put(Timestamp.class, Types.TIMESTAMP);
        PREPARED_STATEMENT_METHODS.put(Types.TIMESTAMP, SET_TIMESTAMP_METHOD);

        TYPE_MAPPING.put(TINYINT, Types.TINYINT);
        INVERSE_TYPE_MAPPING.put(Types.TINYINT, TINYINT);
        CLASS_MAPPING.put(Types.TINYINT, byte.class);
        INVERSE_CLASS_MAPPING.put(byte.class, Types.TINYINT);
        PREPARED_STATEMENT_METHODS.put(Types.TINYINT, SET_BYTE_METHOD);

        TYPE_MAPPING.put(VARBINARY, Types.VARBINARY);
        INVERSE_TYPE_MAPPING.put(Types.VARBINARY, VARBINARY);
        CLASS_MAPPING.put(Types.VARBINARY, new byte[0].getClass());
        PREPARED_STATEMENT_METHODS.put(Types.VARBINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(VARCHAR, Types.VARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.VARCHAR, VARCHAR);
        CLASS_MAPPING.put(Types.VARCHAR, String.class);
        INVERSE_CLASS_MAPPING.put(String.class, Types.VARCHAR);
        PREPARED_STATEMENT_METHODS.put(Types.VARCHAR, SET_STRING_METHOD);
    }

    /**
     * Creates a new instance.
     */
    public JdbcTypeManager()
    {
    }

    /**
     * Retrieves the JDBC type for given class.
     * @param type the type to convert.
     * @return the constant in {@link Types}.
     */
    public int getJdbcType(@NotNull final Class<?> type)
    {
        final int result;

        if (INVERSE_CLASS_MAPPING.containsKey(type))
        {
            result = INVERSE_CLASS_MAPPING.get(type);
        }
        else
        {
            result = Types.OTHER;
        }

        return result;
    }

    /**
     * Retrieves the class for given JDBC type.
     * @param jdbcType the constant in {@link Types}.
     * @return the associated class, or {@code null} if there's no match.
     */
    @Nullable
    public Class<?> getClass(final int jdbcType)
    {
        @Nullable final Class<?> result;

        if (CLASS_MAPPING.containsKey(jdbcType))
        {
            result = CLASS_MAPPING.get(jdbcType);
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * Retrieves the method to invoke to specify a parameter in a given {@link PreparedStatement}, for
     * a concrete type.
     * @param type the type.
     * @return the {@link Method}.
     */
    @NotNull
    public Method getPreparedStatementSetterMethod(@NotNull final Class<?> type)
    {
        return getPreparedStatementSetterMethod(getJdbcType(type));
    }

    /**
     * Retrieves the method to invoke to specify a parameter in a given {@link PreparedStatement}, for
     * a concrete JDBC type.
     * @param jdbcType the constant in {@link Types}.
     * @return the {@link Method}.
     */
    @NotNull
    public Method getPreparedStatementSetterMethod(final int jdbcType)
    {
        @NotNull final Method result;

        @Nullable final Method aux  = PREPARED_STATEMENT_METHODS.get(jdbcType);

        if (aux == null)
        {
            result = SET_OBJECT_METHOD;
        }
        else
        {
            result = aux;
        }

        return result;
    }
}
