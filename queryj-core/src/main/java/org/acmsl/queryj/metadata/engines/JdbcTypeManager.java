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
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.metadata.TypeManager;

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
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Manages the relationship between JDBC types and Java classes / primitives.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/22 10:40
 */
@ThreadSafe
public class JdbcTypeManager
    implements TypeManager
{
    /**
     * The type mapping.
     */
    @NotNull protected static final Map<String, Integer> TYPE_MAPPING = new HashMap<>();

    /**
     * The inverse type mapping.
     */
    @NotNull protected static final Map<Integer, String> INVERSE_TYPE_MAPPING = new HashMap<>();

    /**
     * The class mapping.
     */
    @NotNull protected static final Map<Integer, Class<?>> CLASS_MAPPING = new HashMap<>();

    /**
     * The inverse class mapping.
     */
    @NotNull protected static final Map<Class<?>, Integer> INVERSE_CLASS_MAPPING = new HashMap<>();

    /**
     * The PreparedStatement methods.
     */
    @NotNull protected static final Map<Integer, Method> PREPARED_STATEMENT_METHODS = new HashMap<>();

    /**
     * The lookup packages.
     */
    @NotNull protected static final List<String> LOOKUP_PACKAGES =
        Collections.unmodifiableList(Arrays.asList("java.lang", "", "java.sql", "java.util", "java.math", "java.net"));

    /**
     * Literals.
     */
    @NotNull protected static final String ARRAY = "array";
    @NotNull protected static final String BIGINT = "bigint";
    @NotNull protected static final String BINARY = "binary";
    @NotNull protected static final String BIT = "bit";
    @NotNull protected static final String BLOB = "blob";
    @NotNull protected static final String BOOLEAN = Literals.BOOLEAN;
    @NotNull protected static final String CHAR = "char";
    @NotNull protected static final String CLOB = "clob";
    @NotNull protected static final String DATALINK = "datalink";
    @NotNull protected static final String DATE = "date";
    @NotNull protected static final String DECIMAL = "decimal";
    @NotNull protected static final String DISTINCT = "distinct";
    @NotNull protected static final String DOUBLE = Literals.DOUBLE;
    @NotNull protected static final String FLOAT = Literals.FLOAT;
    @NotNull protected static final String INTEGER = "integer";
    @NotNull protected static final String JAVA_OBJECT = "java_object";
    @NotNull protected static final String LONGNVARCHAR = "longnvarchar";
    @NotNull protected static final String LONGVARBINARY = "longvarbinary";
    @NotNull protected static final String LONGVARCHAR = "longvarchar";
    @NotNull protected static final String NCHAR = "nchar";
    @NotNull protected static final String NCLOB = "nclob";
    @NotNull protected static final String NULL = "null";
    @NotNull protected static final String NUMBER = "number";
    @NotNull protected static final String NUMERIC = "numeric";
    @NotNull protected static final String NVARCHAR = "nvarchar";
    @NotNull protected static final String OTHER = "other";
    @NotNull protected static final String REAL = "real";
    @NotNull protected static final String REF = "ref";
    @NotNull protected static final String ROWID = "rowid";
    @NotNull protected static final String SQLXML = "sqlxml";
    @NotNull protected static final String SMALLINT = Literals.SMALLINT_L;
    @NotNull protected static final String STRUCT = "struct";
    @NotNull protected static final String TIME = "time";
    @NotNull protected static final String TIMESTAMP = "timestamp";
    @NotNull protected static final String TINYINT = Literals.TINYINT_L;
    @NotNull protected static final String VARBINARY = "varbinary";
    @NotNull protected static final String VARCHAR = "varchar";

    protected static final Method SET_ARRAY_METHOD;
    protected static final Method SET_ASCII_STREAM_METHOD;
    protected static final Method SET_ASCII_STREAM_INT_METHOD;
    protected static final Method SET_ASCII_STREAM_LONG_METHOD;
    protected static final Method SET_BIG_DECIMAL_METHOD;
    protected static final Method SET_BINARY_STREAM_METHOD;
    protected static final Method SET_BINARY_STREAM_INT_METHOD;
    protected static final Method SET_BINARY_STREAM_LONG_METHOD;
    protected static final Method SET_BLOB_METHOD;
    protected static final Method SET_BLOB_INPUT_STREAM_METHOD;
    protected static final Method SET_BLOB_INPUT_STREAM_LONG_METHOD;
    protected static final Method SET_BOOLEAN_METHOD;
    protected static final Method SET_BYTE_METHOD;
    protected static final Method SET_BYTES_METHOD;
    protected static final Method SET_CHARACTER_STREAM_METHOD;
    protected static final Method SET_CHARACTER_STREAM_INT_METHOD;
    protected static final Method SET_CHARACTER_STREAM_LONG_METHOD;
    protected static final Method SET_CLOB_METHOD;
    protected static final Method SET_CLOB_READER_METHOD;
    protected static final Method SET_CLOB_READER_LONG_METHOD;
    protected static final Method SET_DATE_METHOD;
    protected static final Method SET_DATE_CALENDAR_METHOD;
    protected static final Method SET_DOUBLE_METHOD;
    protected static final Method SET_FLOAT_METHOD;
    protected static final Method SET_INT_METHOD;
    protected static final Method SET_LONG_METHOD;
    protected static final Method SET_N_CHARACTER_STREAM_METHOD;
    protected static final Method SET_N_CHARACTER_STREAM_LONG_METHOD;
    protected static final Method SET_N_CLOB_METHOD;
    protected static final Method SET_N_CLOB_READER_METHOD;
    protected static final Method SET_N_CLOB_READER_LONG_METHOD;
    protected static final Method SET_N_STRING_METHOD;
    protected static final Method SET_NULL_METHOD;
    protected static final Method SET_NULL_STRING_METHOD;
    protected static final Method SET_OBJECT_METHOD;
    protected static final Method SET_OBJECT_INT_METHOD;
    protected static final Method SET_OBJECT_INT_INT_METHOD;
    protected static final Method SET_REF_METHOD;
    protected static final Method SET_ROW_ID_METHOD;
    protected static final Method SET_SHORT_METHOD;
    protected static final Method SET_SQLXML_METHOD;
    protected static final Method SET_STRING_METHOD;
    protected static final Method SET_TIME_METHOD;
    protected static final Method SET_TIME_CALENDAR_METHOD;
    protected static final Method SET_TIMESTAMP_METHOD;
    protected static final Method SET_TIMESTAMP_CALENDAR_METHOD;
    protected static final Method SET_URL_METHOD;

    static
    {
        @Nullable Method setArrayMethod = null;
        @Nullable Method setAsciiStreamMethod = null;
        @Nullable Method setAsciiStreamIntMethod = null;
        @Nullable Method setAsciiStreamLongMethod = null;
        @Nullable Method setBigDecimalMethod = null;
        @Nullable Method setBinaryStreamMethod = null;
        @Nullable Method setBinaryStreamIntMethod = null;
        @Nullable Method setBinaryStreamLongMethod = null;
        @Nullable Method setBlobMethod = null;
        @Nullable Method setBlobInputStreamMethod = null;
        @Nullable Method setBlobInputStreamLongMethod = null;
        @Nullable Method setBooleanMethod = null;
        @Nullable Method setByteMethod = null;
        @Nullable Method setBytesMethod = null;
        @Nullable Method setCharacterStreamMethod = null;
        @Nullable Method setCharacterStreamIntMethod = null;
        @Nullable Method setCharacterStreamLongMethod = null;
        @Nullable Method setClobMethod = null;
        @Nullable Method setClobReaderMethod = null;
        @Nullable Method setClobReaderLongMethod = null;
        @Nullable Method setDateMethod = null;
        @Nullable Method setDateCalendarMethod = null;
        @Nullable Method setDoubleMethod = null;
        @Nullable Method setFloatMethod = null;
        @Nullable Method setIntMethod = null;
        @Nullable Method setLongMethod = null;
        @Nullable Method setNCharacterStreamMethod = null;
        @Nullable Method setNCharacterStreamLongMethod = null;
        @Nullable Method setNClobMethod = null;
        @Nullable Method setNClobReaderMethod = null;
        @Nullable Method setNClobReaderLongMethod = null;
        @Nullable Method setNStringMethod = null;
        @Nullable Method setNullMethod = null;
        @Nullable Method setNullStringMethod = null;
        @Nullable Method setObjectMethod = null;
        @Nullable Method setObjectIntMethod = null;
        @Nullable Method setObjectIntIntMethod = null;
        @Nullable Method setRefMethod = null;
        @Nullable Method setRowIdMethod = null;
        @Nullable Method setShortMethod = null;
        @Nullable Method setSqlXmlMethod = null;
        @Nullable Method setStringMethod = null;
        @Nullable Method setTimeMethod = null;
        @Nullable Method setTimeCalendarMethod = null;
        @Nullable Method setTimestampMethod = null;
        @Nullable Method setTimestampCalendarMethod = null;
        @Nullable Method setUrlMethod = null;

        try
        {
            setArrayMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_ARRAY, int.class, Array.class);
            setAsciiStreamMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_ASCII_STREAM, int.class, InputStream.class);
            setAsciiStreamIntMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_ASCII_STREAM, int.class, InputStream.class, int.class);
            setAsciiStreamLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_ASCII_STREAM, int.class, InputStream.class, long.class);
            setBigDecimalMethod = PreparedStatement.class.getMethod("setBigDecimal", int.class, BigDecimal.class);
            setBinaryStreamMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_BINARY_STREAM, int.class, InputStream.class);
            setBinaryStreamIntMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_BINARY_STREAM, int.class, InputStream.class, int.class);
            setBinaryStreamLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_BINARY_STREAM, int.class, InputStream.class, long.class);
            setBlobMethod =
                PreparedStatement.class.getMethod(org.acmsl.queryj.metadata.Literals.SET_BLOB, int.class, Blob.class);
            setBlobInputStreamMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_BLOB, int.class, InputStream.class);
            setBlobInputStreamLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_BLOB, int.class, InputStream.class, long.class);
            setBooleanMethod = PreparedStatement.class.getMethod("setBoolean", int.class, boolean.class);
            setByteMethod = PreparedStatement.class.getMethod("setByte", int.class, byte.class);
            setBytesMethod = PreparedStatement.class.getMethod("setBytes", int.class, new byte[0].getClass());
            setCharacterStreamMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_CHARACTER_STREAM, int.class, Reader.class);
            setCharacterStreamIntMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_CHARACTER_STREAM, int.class, Reader.class, int.class);
            setCharacterStreamLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_CHARACTER_STREAM, int.class, Reader.class, long.class);
            setClobMethod =
                PreparedStatement.class.getMethod(org.acmsl.queryj.metadata.Literals.SET_CLOB, int.class, Clob.class);
            setClobReaderMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_CLOB, int.class, Reader.class);
            setClobReaderLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_CLOB, int.class, Reader.class, long.class);
            setDateMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_DATE, int.class, java.sql.Date.class);
            setDateCalendarMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_DATE, int.class, java.sql.Date.class, Calendar.class);
            setDoubleMethod = PreparedStatement.class.getMethod("setDouble", int.class, double.class);
            setFloatMethod = PreparedStatement.class.getMethod("setFloat", int.class, float.class);
            setIntMethod = PreparedStatement.class.getMethod("setInt", int.class, int.class);
            setLongMethod = PreparedStatement.class.getMethod("setLong", int.class, long.class);
            setNCharacterStreamMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_N_CHARACTER_STREAM, int.class, Reader.class);
            setNCharacterStreamLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_N_CHARACTER_STREAM, int.class, Reader.class, long.class);
            setNClobMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_N_CLOB, int.class, NClob.class);
            setNClobReaderMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_N_CLOB, int.class, Reader.class);
            setNClobReaderLongMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_N_CLOB, int.class, Reader.class, long.class);
            setNStringMethod = PreparedStatement.class.getMethod("setNString", int.class, String.class);
            setNullMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_NULL, int.class, int.class);
            setNullStringMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_NULL, int.class, int.class, String.class);
            setObjectMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_OBJECT, int.class, Object.class);
            setObjectIntMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_OBJECT, int.class, Object.class, int.class);
            setObjectIntIntMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_OBJECT, int.class, Object.class, int.class, int.class);
            setRefMethod = PreparedStatement.class.getMethod("setRef", int.class, Ref.class);
            setRowIdMethod = PreparedStatement.class.getMethod("setRowId", int.class, RowId.class);
            setShortMethod = PreparedStatement.class.getMethod("setShort", int.class, short.class);
            setSqlXmlMethod = PreparedStatement.class.getMethod("setSQLXML", int.class, java.sql.SQLXML.class);
            setStringMethod = PreparedStatement.class.getMethod("setString", int.class, String.class);
            setTimeMethod =
                PreparedStatement.class.getMethod(org.acmsl.queryj.metadata.Literals.SET_TIME, int.class, Time.class);
            setTimeCalendarMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_TIME, int.class, Time.class, Calendar.class);
            setTimestampMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_TIMESTAMP, int.class, Timestamp.class);
            setTimestampCalendarMethod =
                PreparedStatement.class.getMethod(
                    org.acmsl.queryj.metadata.Literals.SET_TIMESTAMP, int.class, Timestamp.class, Calendar.class);
            setUrlMethod = PreparedStatement.class.getMethod("setURL", int.class, java.net.URL.class);
        }
        catch (@NotNull final NoSuchMethodException noSuchMethod)
        {
            UniqueLogFactory.getLog(JdbcTypeManager.class).fatal(noSuchMethod.getMessage(), noSuchMethod);
        }
        SET_ARRAY_METHOD = setArrayMethod;
        SET_ASCII_STREAM_METHOD = setAsciiStreamMethod;
        SET_ASCII_STREAM_INT_METHOD = setAsciiStreamIntMethod;
        SET_ASCII_STREAM_LONG_METHOD = setAsciiStreamLongMethod;
        SET_BIG_DECIMAL_METHOD = setBigDecimalMethod;
        SET_BINARY_STREAM_METHOD = setBinaryStreamMethod;
        SET_BINARY_STREAM_INT_METHOD = setBinaryStreamIntMethod;
        SET_BINARY_STREAM_LONG_METHOD = setBinaryStreamLongMethod;
        SET_BLOB_METHOD = setBlobMethod;
        SET_BLOB_INPUT_STREAM_METHOD = setBlobInputStreamMethod;
        SET_BLOB_INPUT_STREAM_LONG_METHOD = setBlobInputStreamLongMethod;
        SET_BOOLEAN_METHOD = setBooleanMethod;
        SET_BYTE_METHOD = setByteMethod;
        SET_BYTES_METHOD = setBytesMethod;
        SET_CHARACTER_STREAM_METHOD = setCharacterStreamMethod;
        SET_CHARACTER_STREAM_INT_METHOD = setCharacterStreamIntMethod;
        SET_CHARACTER_STREAM_LONG_METHOD = setCharacterStreamLongMethod;
        SET_CLOB_METHOD = setClobMethod;
        SET_CLOB_READER_METHOD = setClobReaderMethod;
        SET_CLOB_READER_LONG_METHOD = setClobReaderLongMethod;
        SET_DATE_METHOD = setDateMethod;
        SET_DATE_CALENDAR_METHOD = setDateCalendarMethod;
        SET_DOUBLE_METHOD = setDoubleMethod;
        SET_FLOAT_METHOD = setFloatMethod;
        SET_INT_METHOD = setIntMethod;
        SET_LONG_METHOD = setLongMethod;
        SET_N_CHARACTER_STREAM_METHOD = setNCharacterStreamMethod;
        SET_N_CHARACTER_STREAM_LONG_METHOD = setNCharacterStreamLongMethod;
        SET_N_CLOB_METHOD = setNClobMethod;
        SET_N_CLOB_READER_METHOD = setNClobReaderMethod;
        SET_N_CLOB_READER_LONG_METHOD = setNClobReaderLongMethod;
        SET_N_STRING_METHOD = setNStringMethod;
        SET_NULL_METHOD = setNullMethod;
        SET_NULL_STRING_METHOD = setNullStringMethod;
        SET_OBJECT_METHOD = setObjectMethod;
        SET_OBJECT_INT_METHOD = setObjectIntMethod;
        SET_OBJECT_INT_INT_METHOD = setObjectIntIntMethod;
        SET_REF_METHOD = setRefMethod;
        SET_ROW_ID_METHOD = setRowIdMethod;
        SET_SHORT_METHOD = setShortMethod;
        SET_SQLXML_METHOD = setSqlXmlMethod;
        SET_STRING_METHOD = setStringMethod;
        SET_TIME_METHOD = setTimeMethod;
        SET_TIME_CALENDAR_METHOD = setTimeCalendarMethod;
        SET_TIMESTAMP_METHOD = setTimestampMethod;
        SET_TIMESTAMP_CALENDAR_METHOD = setTimestampCalendarMethod;
        SET_URL_METHOD = setUrlMethod;

        TYPE_MAPPING.put(normalizeKey(ARRAY), Types.ARRAY);
        INVERSE_TYPE_MAPPING.put(Types.ARRAY, ARRAY);
        CLASS_MAPPING.put(Types.ARRAY, java.sql.Array.class);
        INVERSE_CLASS_MAPPING.put(java.sql.Array.class, Types.ARRAY);
        PREPARED_STATEMENT_METHODS.put(Types.ARRAY, SET_ARRAY_METHOD);

        TYPE_MAPPING.put(normalizeKey(BIGINT), Types.BIGINT);
        TYPE_MAPPING.put(normalizeKey(long.class.getSimpleName()), Types.BIGINT);
        INVERSE_TYPE_MAPPING.put(Types.BIGINT, BIGINT);
        CLASS_MAPPING.put(Types.BIGINT, long.class);
        INVERSE_CLASS_MAPPING.put(long.class, Types.BIGINT);
        INVERSE_CLASS_MAPPING.put(Long.class, Types.BIGINT);
        INVERSE_CLASS_MAPPING.put(BigInteger.class, Types.BIGINT);
        TYPE_MAPPING.put(normalizeKey(NUMBER), Types.BIGINT);
        PREPARED_STATEMENT_METHODS.put(Types.BIGINT, SET_LONG_METHOD);

        TYPE_MAPPING.put(normalizeKey(BINARY), Types.BINARY);
        INVERSE_TYPE_MAPPING.put(Types.BINARY, BINARY);
        CLASS_MAPPING.put(Types.BINARY, new byte[0].getClass());
        INVERSE_CLASS_MAPPING.put(new byte[0].getClass(), Types.BINARY);
        INVERSE_CLASS_MAPPING.put(new Byte[0].getClass(), Types.BINARY);
        PREPARED_STATEMENT_METHODS.put(Types.BINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(normalizeKey(BIT), Types.BIT);
        INVERSE_TYPE_MAPPING.put(Types.BIT, BIT);
        CLASS_MAPPING.put(Types.BIT, boolean.class);
        PREPARED_STATEMENT_METHODS.put(Types.BIT, SET_BOOLEAN_METHOD);

        TYPE_MAPPING.put(normalizeKey(BLOB), Types.BLOB);
        INVERSE_TYPE_MAPPING.put(Types.BLOB, BLOB);
        CLASS_MAPPING.put(Types.BLOB, Blob.class);
        INVERSE_CLASS_MAPPING.put(Blob.class, Types.BLOB);
        PREPARED_STATEMENT_METHODS.put(Types.BLOB, SET_BLOB_METHOD);

        TYPE_MAPPING.put(normalizeKey(BOOLEAN), Types.BOOLEAN);
        INVERSE_TYPE_MAPPING.put(Types.BOOLEAN, BOOLEAN);
        CLASS_MAPPING.put(Types.BOOLEAN, boolean.class);
        INVERSE_CLASS_MAPPING.put(boolean.class, Types.BOOLEAN);
        INVERSE_CLASS_MAPPING.put(Boolean.class, Types.BOOLEAN);
        PREPARED_STATEMENT_METHODS.put(Types.BOOLEAN, SET_BOOLEAN_METHOD);

        TYPE_MAPPING.put(normalizeKey(CHAR), Types.CHAR);
        INVERSE_TYPE_MAPPING.put(Types.CHAR, CHAR);
        TYPE_MAPPING.put(normalizeKey(String.class.getSimpleName()), Types.CHAR);
        CLASS_MAPPING.put(Types.CHAR, String.class);
        INVERSE_CLASS_MAPPING.put(String.class, Types.CHAR);
        PREPARED_STATEMENT_METHODS.put(Types.CHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(normalizeKey(CLOB), Types.CLOB);
        INVERSE_TYPE_MAPPING.put(Types.CLOB, CLOB);
        CLASS_MAPPING.put(Types.CLOB, Clob.class);
        INVERSE_CLASS_MAPPING.put(Clob.class, Types.CLOB);
        PREPARED_STATEMENT_METHODS.put(Types.CLOB, SET_CLOB_METHOD);

        TYPE_MAPPING.put(normalizeKey(DATALINK), Types.DATALINK);
        INVERSE_TYPE_MAPPING.put(Types.DATALINK, DATALINK);
        CLASS_MAPPING.put(Types.DATALINK, java.net.URL.class);
        INVERSE_CLASS_MAPPING.put(java.net.URL.class, Types.DATALINK);
        PREPARED_STATEMENT_METHODS.put(Types.DATALINK, SET_URL_METHOD);

        TYPE_MAPPING.put(normalizeKey(DATE), Types.DATE);
        INVERSE_TYPE_MAPPING.put(Types.DATE, DATE);
        CLASS_MAPPING.put(Types.DATE, Date.class);
        INVERSE_CLASS_MAPPING.put(Date.class, Types.DATE);
        PREPARED_STATEMENT_METHODS.put(Types.DATE, SET_DATE_METHOD);

        TYPE_MAPPING.put(normalizeKey(DECIMAL), Types.DECIMAL);
        INVERSE_TYPE_MAPPING.put(Types.DECIMAL, DECIMAL);
        CLASS_MAPPING.put(Types.DECIMAL, BigDecimal.class);
        PREPARED_STATEMENT_METHODS.put(Types.DECIMAL, SET_BIG_DECIMAL_METHOD);

        TYPE_MAPPING.put(normalizeKey(DISTINCT), Types.DISTINCT);
        INVERSE_TYPE_MAPPING.put(Types.DISTINCT, DISTINCT);
        CLASS_MAPPING.put(Types.DISTINCT, void.class);

        TYPE_MAPPING.put(normalizeKey(DOUBLE), Types.DOUBLE);
        INVERSE_TYPE_MAPPING.put(Types.DOUBLE, DOUBLE);
        CLASS_MAPPING.put(Types.DOUBLE, double.class);
        INVERSE_CLASS_MAPPING.put(double.class, Types.DOUBLE);
        INVERSE_CLASS_MAPPING.put(Double.class, Types.DOUBLE);
        PREPARED_STATEMENT_METHODS.put(Types.DOUBLE, SET_DOUBLE_METHOD);

        TYPE_MAPPING.put(normalizeKey(FLOAT), Types.FLOAT);
        INVERSE_TYPE_MAPPING.put(Types.FLOAT, FLOAT);
        CLASS_MAPPING.put(Types.FLOAT, double.class);
        PREPARED_STATEMENT_METHODS.put(Types.FLOAT, SET_DOUBLE_METHOD);

        TYPE_MAPPING.put(normalizeKey(INTEGER), Types.INTEGER);
        INVERSE_TYPE_MAPPING.put(Types.INTEGER, INTEGER);
        CLASS_MAPPING.put(Types.INTEGER, int.class);
        INVERSE_CLASS_MAPPING.put(int.class, Types.INTEGER);
        INVERSE_CLASS_MAPPING.put(Integer.class, Types.INTEGER);
        PREPARED_STATEMENT_METHODS.put(Types.INTEGER, SET_INT_METHOD);

        TYPE_MAPPING.put(normalizeKey(JAVA_OBJECT), Types.JAVA_OBJECT);
        INVERSE_TYPE_MAPPING.put(Types.JAVA_OBJECT, JAVA_OBJECT);
        CLASS_MAPPING.put(Types.JAVA_OBJECT, Object.class);
        INVERSE_CLASS_MAPPING.put(Object.class, Types.JAVA_OBJECT);
        PREPARED_STATEMENT_METHODS.put(Types.JAVA_OBJECT, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(normalizeKey(LONGNVARCHAR), Types.LONGNVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.LONGNVARCHAR, LONGNVARCHAR);
        CLASS_MAPPING.put(Types.LONGNVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.LONGNVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(normalizeKey(LONGVARBINARY), Types.LONGVARBINARY);
        INVERSE_TYPE_MAPPING.put(Types.LONGVARBINARY, LONGVARBINARY);
        CLASS_MAPPING.put(Types.LONGVARBINARY, new byte[0].getClass());
        PREPARED_STATEMENT_METHODS.put(Types.LONGVARBINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(normalizeKey(LONGVARCHAR), Types.LONGVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.LONGVARCHAR, LONGVARCHAR);
        CLASS_MAPPING.put(Types.LONGVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.LONGVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(normalizeKey(NCHAR), Types.NCHAR);
        INVERSE_TYPE_MAPPING.put(Types.NCHAR, NCHAR);
        CLASS_MAPPING.put(Types.NCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.NCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(normalizeKey(NCLOB), Types.NCLOB);
        INVERSE_TYPE_MAPPING.put(Types.NCLOB, NCLOB);
        CLASS_MAPPING.put(Types.NCLOB, NClob.class);
        INVERSE_CLASS_MAPPING.put(NClob.class, Types.NCLOB);
        PREPARED_STATEMENT_METHODS.put(Types.NCLOB, SET_N_CLOB_METHOD);

        TYPE_MAPPING.put(normalizeKey(NULL), Types.NULL);
        INVERSE_TYPE_MAPPING.put(Types.NULL, NULL);
        CLASS_MAPPING.put(Types.NULL, void.class);

        TYPE_MAPPING.put(normalizeKey(NUMERIC), Types.NUMERIC);
        INVERSE_TYPE_MAPPING.put(Types.NUMERIC, NUMERIC);
        CLASS_MAPPING.put(Types.NUMERIC, BigDecimal.class);
        INVERSE_CLASS_MAPPING.put(BigDecimal.class, Types.NUMERIC);
        PREPARED_STATEMENT_METHODS.put(Types.NUMERIC, SET_BIG_DECIMAL_METHOD);

        TYPE_MAPPING.put(normalizeKey(NVARCHAR), Types.NVARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.NVARCHAR, NVARCHAR);
        CLASS_MAPPING.put(Types.NVARCHAR, String.class);
        PREPARED_STATEMENT_METHODS.put(Types.NVARCHAR, SET_STRING_METHOD);

        TYPE_MAPPING.put(normalizeKey(OTHER), Types.OTHER);
        INVERSE_TYPE_MAPPING.put(Types.OTHER, OTHER);
        CLASS_MAPPING.put(Types.OTHER, void.class);
        INVERSE_CLASS_MAPPING.put(void.class, Types.OTHER);
        PREPARED_STATEMENT_METHODS.put(Types.OTHER, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(normalizeKey(REAL), Types.REAL);
        INVERSE_TYPE_MAPPING.put(Types.REAL, REAL);
        CLASS_MAPPING.put(Types.REAL, float.class);
        INVERSE_CLASS_MAPPING.put(float.class, Types.REAL);
        INVERSE_CLASS_MAPPING.put(Float.class, Types.REAL);
        PREPARED_STATEMENT_METHODS.put(Types.REAL, SET_FLOAT_METHOD);

        TYPE_MAPPING.put(normalizeKey(REF), Types.REF);
        INVERSE_TYPE_MAPPING.put(Types.REF, REF);
        CLASS_MAPPING.put(Types.REF, Ref.class);
        INVERSE_CLASS_MAPPING.put(Ref.class, Types.REF);
        PREPARED_STATEMENT_METHODS.put(Types.REF, SET_REF_METHOD);

        TYPE_MAPPING.put(normalizeKey(ROWID), Types.ROWID);
        INVERSE_TYPE_MAPPING.put(Types.ROWID, ROWID);
        CLASS_MAPPING.put(Types.ROWID, RowId.class);
        INVERSE_CLASS_MAPPING.put(RowId.class, Types.ROWID);
        PREPARED_STATEMENT_METHODS.put(Types.ROWID, SET_ROW_ID_METHOD);

        TYPE_MAPPING.put(normalizeKey(SMALLINT), Types.SMALLINT);
        INVERSE_TYPE_MAPPING.put(Types.SMALLINT, SMALLINT);
        CLASS_MAPPING.put(Types.SMALLINT, short.class);
        INVERSE_CLASS_MAPPING.put(short.class, Types.SMALLINT);
        INVERSE_CLASS_MAPPING.put(Short.class, Types.SMALLINT);
        PREPARED_STATEMENT_METHODS.put(Types.SMALLINT, SET_SHORT_METHOD);

        TYPE_MAPPING.put(normalizeKey(SQLXML), Types.SQLXML);
        INVERSE_TYPE_MAPPING.put(Types.SQLXML, SQLXML);
        CLASS_MAPPING.put(Types.SQLXML, java.sql.SQLXML.class);
        INVERSE_CLASS_MAPPING.put(java.sql.SQLXML.class, Types.SQLXML);
        PREPARED_STATEMENT_METHODS.put(Types.SQLXML, SET_SQLXML_METHOD);

        TYPE_MAPPING.put(normalizeKey(STRUCT), Types.STRUCT);
        INVERSE_TYPE_MAPPING.put(Types.STRUCT, STRUCT);
        CLASS_MAPPING.put(Types.STRUCT, Struct.class);
        INVERSE_CLASS_MAPPING.put(Struct.class, Types.STRUCT);
        PREPARED_STATEMENT_METHODS.put(Types.STRUCT, SET_OBJECT_METHOD);

        TYPE_MAPPING.put(normalizeKey(TIME), Types.TIME);
        INVERSE_TYPE_MAPPING.put(Types.TIME, TIME);
        CLASS_MAPPING.put(Types.TIME, Time.class);
        INVERSE_CLASS_MAPPING.put(Time.class, Types.TIME);
        PREPARED_STATEMENT_METHODS.put(Types.TIME, SET_TIME_METHOD);

        TYPE_MAPPING.put(normalizeKey(TIMESTAMP), Types.TIMESTAMP);
        INVERSE_TYPE_MAPPING.put(Types.TIMESTAMP, TIMESTAMP);
        CLASS_MAPPING.put(Types.TIMESTAMP, Timestamp.class);
        INVERSE_CLASS_MAPPING.put(Timestamp.class, Types.TIMESTAMP);
        PREPARED_STATEMENT_METHODS.put(Types.TIMESTAMP, SET_TIMESTAMP_METHOD);

        TYPE_MAPPING.put(normalizeKey(TINYINT), Types.TINYINT);
        INVERSE_TYPE_MAPPING.put(Types.TINYINT, TINYINT);
        CLASS_MAPPING.put(Types.TINYINT, byte.class);
        INVERSE_CLASS_MAPPING.put(byte.class, Types.TINYINT);
        INVERSE_CLASS_MAPPING.put(Byte.class, Types.TINYINT);
        PREPARED_STATEMENT_METHODS.put(Types.TINYINT, SET_BYTE_METHOD);

        TYPE_MAPPING.put(normalizeKey(VARBINARY), Types.VARBINARY);
        INVERSE_TYPE_MAPPING.put(Types.VARBINARY, VARBINARY);
        CLASS_MAPPING.put(Types.VARBINARY, new byte[0].getClass());
        PREPARED_STATEMENT_METHODS.put(Types.VARBINARY, SET_BYTES_METHOD);

        TYPE_MAPPING.put(normalizeKey(VARCHAR), Types.VARCHAR);
        INVERSE_TYPE_MAPPING.put(Types.VARCHAR, VARCHAR);
        CLASS_MAPPING.put(Types.VARCHAR, String.class);
        INVERSE_CLASS_MAPPING.put(String.class, Types.VARCHAR);
        PREPARED_STATEMENT_METHODS.put(Types.VARCHAR, SET_STRING_METHOD);
    }

    /**
     * Normalizes given key.
     * @param key the key.
     * @return the normalized key.
     */
    protected static final String normalizeKey(@NotNull final String key)
    {
        return key.toLowerCase(Locale.getDefault());
    }

    /**
     * String literal: "Cannot set parameter ".
     */
    protected static final String CANNOT_SET_PARAMETER = "Cannot set parameter ";

    /**
     * String literal: " on PreparedStatement";
     */
    protected static final String ON_PREPARED_STATEMENT = " on PreparedStatement";

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
    @Override
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
    protected Method getPreparedStatementSetterMethod(@NotNull final Class<?> type)
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
    protected Method getPreparedStatementSetterMethod(final int jdbcType)
    {
        @NotNull final Method result;

        @Nullable final Method aux = PREPARED_STATEMENT_METHODS.get(jdbcType);

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

    /**
     * Calls the correct method on {@link PreparedStatement} to set the parameter.
     * @param statement the {@link PreparedStatement}.
     * @param index the parameter index.
     * @param value the parameter value.
     * @param <T> the parameter class.
     */
    protected <T> void setPreparedStatementParameter(
        @NotNull final PreparedStatement statement, final int index, @NotNull final T value)
    {
        @NotNull final Method t_Method = getPreparedStatementSetterMethod(findOutClass(value));

        try
        {
            t_Method.invoke(statement, index, value);
        }
        catch (@NotNull final InvocationTargetException invalidMethodOrParams)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(JdbcTypeManager.class);

            if (t_Log != null)
            {
                t_Log.fatal(CANNOT_SET_PARAMETER + value + ON_PREPARED_STATEMENT, invalidMethodOrParams);
            }
        }
        catch (@NotNull final IllegalAccessException invalidMethodOrParams)
        {
            @Nullable final Log t_Log = UniqueLogFactory.getLog(JdbcTypeManager.class);

            if (t_Log != null)
            {
                t_Log.fatal(CANNOT_SET_PARAMETER + value + ON_PREPARED_STATEMENT, invalidMethodOrParams);
            }
        }
    }

    /**
     * Tries to find out the actual class of given value.
     * @param value the value.
     * @param <T> the class parameter.
     * @return the actual class.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected <T> Class<?> findOutClass(@NotNull final T value)
    {
        @Nullable Class<?> result = null;

        @NotNull final Class<?>[] t_aInterfaces = value.getClass().getInterfaces();

        for (@NotNull final Class<?> t_Interface : t_aInterfaces)
        {
            if (t_Interface.getPackage().getName().equals("java.sql"))
            {
                result = t_Interface;
                break;
            }
        }

        if (result == null)
        {
            result = value.getClass();
        }

        return result;
    }

    /**
     * Retrieves the class of given type.
     * @param type the type.
     * @return the type.
     */
    @NotNull
    @Override
    public Class<?> getClass(@NotNull final String type)
    {
        @Nullable Class<?> result = retrievePrimitiveClass(type);

        if (result == null)
        {
            result = retrieveTypeClass(type);

            /*
            if (result != null)
            {
                result = toPrimitiveIfPossible(result, type);
            }
            */
        }

        if (result == null)
        {
            result = retrievePrimitiveClass(type);
        }

        if (   (result == null)
            && (TYPE_MAPPING.containsKey(normalizeKey(type))))
        {
            result = getClass(TYPE_MAPPING.get(normalizeKey(type)));
        }

        if (result == null)
        {
            result = Object.class;
        }

        return result;
    }

    /**
     * Retrieves the primitive class associated to given type, if it's indeed
     * a primitive type. Primitive types' classes cannot be retrieved via Class.forName().
     * @param type the type.
     * @return the primitive type, or {@code null} if it's not a primitive type.
     */
    @Nullable
    protected Class<?> retrievePrimitiveClass(@NotNull final String type)
    {
        @Nullable final Class<?> result;

        switch (type)
        {
            case "boolean": result = boolean.class; break;
            case "byte": result = byte.class; break;
            case "byte[]": result = byte[].class; break;
            case "Byte[]": result = Byte[].class; break;
            case "double": result = double.class; break;
            case "float": result = float.class; break;
            case "int": result = int.class; break;
            case "long": result = long.class; break;
            case "short": result = short.class; break;
            default: result = null; break;
        }

        return result;
    }

    /**
     * Retrieves the class for given type.
     * @param type the type.
     * @return the class.
     */
    @Nullable
    public Class<?> retrieveTypeClass(@NotNull final String type)
    {
        @Nullable Class<?> result = null;

        for (@NotNull final String pkg : LOOKUP_PACKAGES)
        {
            result = retrieveTypeClass(type, pkg);

            if (result != null)
            {
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the class, assuming given type is under given package.
     * @param type the type name.
     * @param packageName the package name.
     * @return such class.
     */
    @Nullable
    protected Class<?> retrieveTypeClass(@NotNull final String type, @NotNull final String packageName)
    {
        @Nullable Class<?> result = null;

        @NotNull final String pkg = "".equals(packageName) ? packageName : packageName + ".";

        try
        {
            result = Class.forName(pkg + type);
        }
        catch  (@NotNull final ClassNotFoundException classNotFoundException)
        {
            // No luck this time.
        }

        return result;
    }

    /**
     * Retrieves the primitive type, if it's a primitive, or the same class otherwise.
     * @param typeClass the resolved class.
     * @param type the original type.
     * @return such class.
     */
    @Nullable
    protected Class<?> toPrimitiveIfPossible(
        @NotNull final Class<?> typeClass, @NotNull final String type)
    {
        @Nullable Class<?> result = typeClass;

        if  (   (!typeClass.equals(type))
             && (!typeClass.equals("java.lang.".concat(type))))
        {
            try
            {
                result = (Class) result.getDeclaredField("TYPE").get(result);
            }
            catch  (@NotNull final NoSuchFieldException noSuchFieldException)
            {
                // Nothing to do.
            }
            catch  (@NotNull final IllegalAccessException illegalAccessException)
            {
                // Nothing to do.
            }
        }

        /*
        if  (result.equals(java.util.Date.class))
        {
            result = Timestamp.class;
        }
        */

        return result;
    }

    /**
     * Checks whether given type is a primitive wrapper.
     * @param type the type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isPrimitiveWrapper(@NotNull final Class<?> type)
    {
        return toPrimitive(type) != null;
    }

    /**
     * Retrieves the primitive class for given primitive wrapper.
     * @param type the type.
     * @return the primitive class.
     */
    @Nullable
    public Class<?> toPrimitive(@NotNull final Class<?> type)
    {
        @Nullable final Class<?> result;

        switch (type.getSimpleName())
        {
            case "Boolean": result = boolean.class; break;
            case "Double": result = double.class; break;
            case "Float": result = float.class; break;
            case "Integer": result = int.class; break;
            case "Long": result = long.class; break;
            default: result = null; break;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areColumnTypesCompatible(final int first, final int second)
    {
        return areColumnTypesCompatible(first, second, true);
    }

    /**
     * Checks whether given column types are compatible.
     * @param first the first.
     * @param second the other.
     * @param checkReverse whether to check the reverse way if no direct match is found.
     * @return {@code true} if so.
     */
    protected boolean areColumnTypesCompatible(final int first, final int second, final boolean checkReverse)
    {
        boolean result = first == second;

        if (!result)
        {
            @NotNull final List<Integer> t_lCompatibleTypes = getCompatibleTypesFor(first);

            result = t_lCompatibleTypes.contains(second);
        }

        if (   (!result)
            && (checkReverse))
        {
            result = areColumnTypesCompatible(second, first, false);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSqlType(@NotNull final Class<?> classType)
    {
        final int result;

        if (INVERSE_CLASS_MAPPING.containsKey(classType))
        {
            result = INVERSE_CLASS_MAPPING.get(classType);
        }
        else
        {
            result = Types.OTHER;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSqlType(@NotNull final String type)
    {
        final int result;

        if (TYPE_MAPPING.containsKey(normalizeKey(type)))
        {
            result = TYPE_MAPPING.get(normalizeKey(type));
        }
        else
        {
            result = Types.OTHER;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public List<Integer> getCompatibleTypesFor(final int type)
    {
        @NotNull final List<Integer> result = new ArrayList<>();

        switch (type)
        {
            case Types.CHAR:
            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.VARCHAR:
                result.add(Types.CHAR);
                result.add(Types.LONGNVARCHAR);
                result.add(Types.LONGVARCHAR);
                result.add(Types.NCHAR);
                result.add(Types.NVARCHAR);
                result.add(Types.VARCHAR);
                break;
            case Types.NUMERIC:
            case Types.BIGINT:
            case Types.BIT:
            case Types.BOOLEAN:
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.INTEGER:
            case Types.REAL:
            case Types.SMALLINT:
            case Types.TINYINT:
                result.add(Types.NUMERIC);
                result.add(Types.BIGINT);
                result.add(Types.BIT);
                result.add(Types.BOOLEAN);
                result.add(Types.DECIMAL);
                result.add(Types.DOUBLE);
                result.add(Types.FLOAT);
                result.add(Types.INTEGER);
                result.add(Types.REAL);
                result.add(Types.SMALLINT);
                result.add(Types.TINYINT);
                break;
            default:
                break;
        }

        return result;
    }
}
