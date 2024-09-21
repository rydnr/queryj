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
 * Filename: JdbcMetadataTypeManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides the basic translation and management services
 *              related to database attribute types, according to strict
 *              JDBC specification.
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides the basic translation and management services
 * related to database attribute types, according to strict
 * JDBC specification.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class JdbcMetadataTypeManager
    implements  MetadataTypeManager,
                Singleton,
                Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 560475071137483642L;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class JdbcMetadataTypeManagerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final JdbcMetadataTypeManager SINGLETON =
            new JdbcMetadataTypeManager();
    }

    /**
     * The native to Java type mapping.
     */
    private Map<String, Integer> m__mNative2JavaTypeMapping;

    /**
     * Creates an empty <code>JdbcMetadataTypeManager</code>.
     */
    public JdbcMetadataTypeManager() {}

    /**
     * Retrieves a <code>JdbcMetadataTypeManager</code> instance.
     * @return such instance.
     */
    @NotNull
    public static JdbcMetadataTypeManager getInstance()
    {
        return JdbcMetadataTypeManagerSingletonContainer.SINGLETON;
    }

    /**
     * Specifies the native to Java type mapping.
     * @param map such mapping.
     */
    protected void setNative2JavaTypeMapping(final Map<String, Integer> map)
    {
        m__mNative2JavaTypeMapping = map;
    }

    /**
     * Retrieves the native to Java type mapping.
     * @return such mapping.
     */
    protected Map<String, Integer> getNative2JavaTypeMapping()
    {
        return m__mNative2JavaTypeMapping;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    @Override
    @Nullable
    public String getNativeType(final int dataType)
    {
        return getNativeType(dataType, false);
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @return the associated native type.
     */
    @Override
    @Nullable
    public String getNativeType(final int dataType, final boolean allowsNull)
    {
        @Nullable final String result;

        switch  (dataType)
        {
            case Types.DECIMAL:
                result = Literals.BIG_DECIMAL;
                break;

            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
                result = (allowsNull) ? Literals.INTEGER : "int";
                break;

            case Types.INTEGER:
            case Types.BIGINT:
            case Types.NUMERIC:
                result = (allowsNull) ? "Long" : "long";
                break;

            case Types.FLOAT:
                result = (allowsNull) ? Literals.FLOAT_C : Literals.FLOAT;
                break;

            case Types.REAL:
            case Types.DOUBLE:
                result = (allowsNull) ? Literals.DOUBLE_C : Literals.DOUBLE;
                break;

            case Types.TIME:
            case Types.DATE:
            case 11:
                result = "Date";
                break;
            case Types.TIMESTAMP:
                result = Literals.TIMESTAMP;
                break;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.CLOB:
                result = Literals.STRING;
                break;

            default:
                result = Literals.OBJECT_C;
                break;
        }

        return result;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated native type.
     */
    @Override
    public String getNativeType(
        final int dataType, final boolean allowsNull, final boolean isBool)
    {
        final String result;

        if (isBool)
        {
            if (allowsNull)
            {
                result = Literals.BOOLEAN;
            }
            else
            {
                result = Literals.BOOLEAN;
            }
        }
        else
        {
            result = getNativeType(dataType, allowsNull);
        }

        return result;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @param precision the precision.
     * @return the associated native type.
     */
    public String getNativeType(
        final int dataType,
        final boolean allowsNull,
        final boolean isBool,
        final int precision)
    {
        //TODO: Implement precision
        return getNativeType(dataType, allowsNull, isBool);
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    @Override
    public int getJavaType(@Nullable final String dataType)
    {
        return getJavaType(dataType, -1);
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param precision the expected precision.
     * @return the associated native type.
     */
    @Override
    public int getJavaType(@Nullable final String dataType, final int precision)
    {
        // TODO: Implement precision.

        final int result;

        if  (dataType != null)
        {
            Map<String, Integer> t_mNative2JavaTypesMap = getNative2JavaTypeMapping();

            if  (t_mNative2JavaTypesMap == null)
            {
                t_mNative2JavaTypesMap = buildNative2JavaTypeMap();
                setNative2JavaTypeMapping(t_mNative2JavaTypesMap);
            }

            @Nullable final Integer t_Result = t_mNative2JavaTypesMap.get(dataType);

            if  (t_Result == null)
            {
                @Nullable final Integer otherResult =
                    t_mNative2JavaTypesMap.get(dataType.toUpperCase(Locale.US));

                if (otherResult != null)
                {
                    result = otherResult;
                }
                else
                {
                    result = Types.OTHER;
                }
            }
            else
            {
                result = t_Result;
            }
        }
        else
        {
            result = Types.NULL;
        }

        return result;
    }

    /**
     * Builds the native to java type mapping.
     * @return such mapping.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    protected Map<String, Integer> buildNative2JavaTypeMap()
    {
        @NotNull final HashMap<String, Integer> result = new HashMap<>();

        @NotNull final Integer t_Numeric = Types.NUMERIC;
        @NotNull final Integer t_Integer = Types.INTEGER;
        @NotNull final Integer t_Long = Types.BIGINT;
        @NotNull final Integer t_Double = Types.REAL;
        @NotNull final Integer t_Time = Types.TIME;
        @NotNull final Integer t_Date = Types.DATE;
        @NotNull final Integer t_TimeStamp = Types.TIMESTAMP;
        @NotNull final Integer t_Text = Types.VARCHAR;

        result.put(Literals.DECIMAL, t_Numeric);
        result.put(Literals.BIG_DECIMAL, t_Numeric);
        result.put("BIT", t_Integer);
        result.put(Literals.TINYINT_U, t_Integer);
        result.put(Literals.SMALLINT, t_Integer);
        result.put("int", t_Long);
        result.put(Literals.INTEGER, t_Long);
        result.put(Literals.INTEGER_U, t_Long);
        result.put(Literals.NUMERIC_U, t_Long);
        result.put("Number", t_Long);
        result.put(Literals.NUMBER_L, t_Long);
        result.put(Literals.NUMBER_U, t_Long);
        result.put(Literals.BIGINT_U, t_Long);
        result.put("Long", t_Long);
        result.put("long", t_Long);
        result.put("REAL", t_Double);
        result.put(Literals.FLOAT_U, t_Double);
        result.put(Literals.DOUBLE_U, t_Double);
        result.put(Literals.FLOAT, t_Double);
        result.put(Literals.DOUBLE, t_Double);
        result.put("TIME", t_Time);
        result.put("DATE", t_Date);
        result.put("Date", t_Date);
        result.put(Literals.TIMESTAMP_U, t_TimeStamp);
        result.put(Literals.TIMESTAMP, t_TimeStamp);
        result.put("CHAR", t_Text);
        result.put(Literals.VARCHAR_U, t_Text);
        result.put(Literals.VARCHAR2, t_Text);
        result.put(Literals.LONGVARCHAR_U, t_Text);
        result.put(Literals.BINARY_U, t_Text);
        result.put(Literals.VARBINARY_U, t_Text);
        result.put(Literals.STRING, t_Text);
        result.put("CLOB", t_Text);

        return result;
    }

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null values or not.
     * @return the QueryJ type.
     */
    @NotNull
    @Override
    public String getQueryJFieldType(final int dataType, final boolean allowsNull)
    {
        return
            getQueryJFieldType(
                dataType, allowsNull, StringUtils.getInstance());
    }

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null values or not.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the QueryJ type.
     */
    @NotNull
    protected String getQueryJFieldType(
        final int dataType, final boolean allowsNull, @NotNull final StringUtils stringUtils)
    {
        String result = getFieldType(dataType);

        if (allowsNull)
        {
            result = stringUtils.capitalize(result, QueryJSettings.DEFAULT_LOCALE, '_');
        }

        return result;
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    @NotNull
    @Override
    public String getStatementSetterFieldType(final int dataType)
    {
        return getFieldType(dataType, true, isBoolean(dataType));
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    @NotNull
    @Override
    public String getFieldType(final int dataType)
    {
        return getFieldType(dataType, false, isBoolean(dataType));
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null or not.
     * @param isBool whether the type represents boolean values.
     * @return the QueryJ type.
     */
    @NotNull
    @Override
    public String getFieldType(final int dataType, final boolean allowsNull, final boolean isBool)
    {
        @NotNull String result = "";

        if (isBool)
        {
            if (allowsNull)
            {
                result = Literals.BOOLEAN;
            }
            else
            {
                result = Literals.BOOLEAN;
            }
        }
        else
        {
            switch (dataType)
            {
                case Types.DECIMAL:
                    result = Literals.BIG_DECIMAL;
                    break;

                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                    result = (allowsNull) ? Literals.INTEGER : "int";

                    break;

                case Types.NUMERIC:
                case Types.INTEGER:
                case Types.BIGINT:
                    result = (allowsNull) ? "Long" : "long";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = (allowsNull) ? Literals.DOUBLE_C : Literals.DOUBLE;
                    break;

                case Types.TIME:
                case Types.DATE:
                case Types.TIMESTAMP:
                case 11:
                    result = "Date";
                    break;

                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                case Types.CLOB:
                    result = Literals.STRING;
                    break;

                case Types.BLOB:
                    result = "InputStream";
                    break;

                default:
                    break;
            }
        }

        return result;
    }

    /**
     * Retrieves the setter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @param paramName the parameter name.
     * @return the associated setter method name.
     */
    @NotNull
    @Override
    public String getSetterMethod(
        final int dataType, final int paramIndex, final String paramName)
    {
        @Nullable String result = getObjectType(dataType, isBoolean(dataType));

        switch  (dataType)
        {
            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
            case 11:

                result +=
                    "(" + paramIndex
                    + ", new Timestamp(" + paramName + ".getTimeInMillis()));";
                break;

            default:
                result += "(" + paramIndex + ", " + paramName + ")";
                break;
        }

        result = "set" + result;

        return result;
    }

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @return the associated getter method name.
     */
    @NotNull
    @Override
    public String getGetterMethod(final int dataType, final int paramIndex)
    {
        return getGetterMethod(dataType, "" + paramIndex);
    }

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @return the associated getter method name.
     */
    @NotNull
    @Override
    public String getGetterMethod(final int dataType)
    {
        return getGetterMethod(dataType, null);
    }

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param param the parameter.
     * @return the associated getter method name.
     */
    @NotNull
    @Override
    public String getGetterMethod(final int dataType, @Nullable final String param)
    {
        @Nullable String result = getObjectType(dataType, isBoolean(dataType));

        switch  (dataType)
        {
            case Types.BIT:
                result = Literals.INTEGER;
                break;

            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                result = "Int";

                if (param != null)
                {
                    result += "(" + param  + ")";
                }
                break;

            default:
                if (param != null)
                {
                    result += "(" + param + ")";
                }
                break;
        }

        result = "get" + result;

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getProcedureResultType(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        switch  (dataType)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:

                result = "int";
                break;

            case Types.INTEGER:
            case Types.NUMERIC:
            case Types.BIGINT:

                result = "long";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = Literals.DOUBLE;
                break;

            default:
                result = getObjectType(dataType, isBool);
                break;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getProcedureDefaultValue(final int dataType, final boolean allowsNull)
    {
        @NotNull String result = "null";

        if (!allowsNull)
        {
            switch  (dataType)
            {
                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:

                    result = "-1";
                    break;

                case Types.INTEGER:
                case Types.NUMERIC:
                case Types.BIGINT:

                    result = "-1L";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = "-0.0d";
                    break;

                default:
                    break;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getObjectType(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        if (isBool)
        {
            result = Literals.BOOLEAN;
        }
        else
        {
            switch (dataType)
            {
                case Types.NUMERIC:
                case Types.DECIMAL:
                    result = Literals.BIG_DECIMAL;
                    break;

                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    result = Literals.INTEGER;
                    break;

                case Types.BIGINT:
                    result = "Long";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = Literals.DOUBLE_C;
                    break;

                case Types.TIME:
                case Types.DATE:
                case 11:
                    result = "Date";
                    break;

                case Types.TIMESTAMP:
                    result = Literals.TIMESTAMP;
                    break;

                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                default:
                    result = Literals.STRING;
                    break;
            }
        }

        return result;
    }

    /**
     * Retrieves the import type of given data type.
     * @param dataType the data type.
     * @return the associated type for using in import statements.
     */
    @Nullable
    @Override
    public String getImport(final int dataType)
    {
        @Nullable final String result;

        switch (dataType)
        {
            case Types.NUMERIC:
            case Types.DECIMAL:
                result = BigDecimal.class.getName();
                break;

            case Types.TIME:
            case Types.DATE:
            case 11:
                result = Date.class.getName();
                break;

            case Types.TIMESTAMP:
                result = Timestamp.class.getName();
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getObjectType(@NotNull final String dataType, final boolean isBool)
    {
        @NotNull String result = dataType;

        if (isBool)
        {
            result = Literals.BOOLEAN;
        }
        else if  (dataType.equals("int"))
        {
            result = Literals.INTEGER;
        }
        else if  (dataType.equals("long"))
        {
            result = "Long";
        }
        else if  (   (dataType.equals(Literals.FLOAT))
                  || (dataType.equals(Literals.DOUBLE)))
        {
            result = Literals.DOUBLE_C;
        }
        else if  (dataType.equals("clob"))
        {
            result = Literals.STRING;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getSmartObjectType(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        if (isBool)
        {
            result = Literals.BOOLEAN;
        }
        else
        {
            switch (dataType)
            {
                case Types.NUMERIC:
                case Types.DECIMAL:
                    result = Literals.BIG_DECIMAL;
                    break;

                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    result = Literals.INTEGER;
                    break;

                case Types.BIGINT:
                    result = "Long";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = Literals.DOUBLE_C;
                    break;

                case Types.TIME:
                case Types.DATE:
                case Types.TIMESTAMP:
                case 11:
                    result = "Date";
                    break;

                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                default:
                    result = Literals.STRING;
                    break;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getSmartObjectRetrievalType(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        if (isBool)
        {
            result = Literals.BOOLEAN;
        }
        else
        {
            switch (dataType)
            {
                case Types.NUMERIC:
                case Types.DECIMAL:
                    result = Literals.BIG_DECIMAL;
                    break;

                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    result = Literals.INTEGER;
                    break;

                case Types.BIGINT:
                    result = "Long";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = Literals.DOUBLE_C;
                    break;

                case Types.TIME:
                case Types.DATE:
                case Types.TIMESTAMP:
                case 11:
                    result = Literals.TIMESTAMP;
                    break;

                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                default:
                    result = Literals.STRING;
                    break;
            }
        }

        return result;
    }

    /**
     * Retrieves the object type of given data type when retrieving information.
     * @param dataType the data type.
     * @param isBool whether the type represents boolean values.
     * @return the associated object type.
     */
    @NotNull
    @Override
    public String getFullyQualifiedType(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        if (isBool)
        {
            result = Literals.BOOLEAN;
        }
        else
        {
            switch (dataType)
            {
                case Types.NUMERIC:
                case Types.DECIMAL:
                    result = java.math.BigDecimal.class.getName();
                    break;

                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    result = Integer.class.getName();
                    break;

                case Types.BIGINT:
                    result = Long.class.getName();
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = Double.class.getName();
                    break;

                case Types.TIME:
                case Types.DATE:
                case Types.TIMESTAMP:
                case 11:
                    result = Timestamp.class.getName();
                    break;

                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                case Types.BINARY:
                case Types.VARBINARY:
                case Types.LONGVARBINARY:
                default:
                    result = String.class.getName();
                    break;
            }
        }

        return result;
    }

    /**
     * Retrieves the default value of given data type.
     * @param dataType the data type.
     * @param isBool whether the type represents boolean values or not.
     * @return the associated default value.
     */
    @Override
    @NotNull
    public String getDefaultValue(final int dataType, final boolean isBool)
    {
        @NotNull final String result;

        if (isBool)
        {
            result = Literals.FALSE_L;
        }
        else
        {
            switch (dataType)
            {
                case Types.BIGINT:
                case Types.BIT:
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    result = "-1";
                    break;

                case Types.REAL:
                case Types.FLOAT:
                case Types.DOUBLE:
                    result = "-1.0d";
                    break;

                default:
                    result = "null";
                    break;
            }
        }

        return result;
    }

    /**
     * Retrieves the constant name of given data type.
     * @param dataType the data type.
     * @return the associated constant name.
     */
    @NotNull
    @Override
    public String getConstantName(final int dataType)
    {
        @NotNull final String result;

        switch (dataType)
        {
            case Types.ARRAY:
                result = "ARRAY";
                break;

            case Types.BIGINT:
                result = Literals.BIGINT_U;
                break;

            case Types.BINARY:
                result = Literals.BINARY_U;
                break;

            case Types.BIT:
                result = "BIT";
                break;

            case Types.BLOB:
                result = "BLOB";
                break;

            case Types.BOOLEAN:
                result = "BOOLEAN";
                break;

            case Types.CHAR:
                result = "CHAR";
                break;

            case Types.CLOB:
                result = "CLOB";
                break;

            case Types.DATALINK:
                result = "DATALINK";
                break;

            case Types.DATE:
                result = "DATE";
                break;

            case Types.DECIMAL:
                result = Literals.DECIMAL;
                break;

            case Types.DISTINCT:
                result = "DISTINCT";
                break;

            case Types.DOUBLE:
                result = Literals.DOUBLE_U;
                break;

            case Types.FLOAT:
                result = Literals.FLOAT_U;
                break;

            case Types.INTEGER:
                result = Literals.INTEGER_U;
                break;

            case Types.JAVA_OBJECT:
                result = "JAVA_OBJECT";
                break;

            case Types.LONGNVARCHAR:
                result = "LONGNVARCHAR";
                break;

            case Types.LONGVARBINARY:
                result = Literals.LONGVARBINARY_U;
                break;

            case Types.LONGVARCHAR:
                result = Literals.LONGVARCHAR_U;
                break;

            case Types.NCHAR:
                result = "NCHAR";
                break;

            case Types.NCLOB:
                result = "NCLOB";
                break;

            case Types.NULL:
                result = "NULL";
                break;

            case Types.NUMERIC:
                result = Literals.NUMERIC_U;
                break;

            case Types.NVARCHAR:
                result = "NVARCHAR";
                break;

            case Types.OTHER:
                result = "OTHER";
                break;

            case Types.REAL:
                result = "REAL";
                break;

            case Types.REF:
                result = "REF";
                break;

            case Types.ROWID:
                result = "ROWID";
                break;

            case Types.SQLXML:
                result = "SQLXML";
                break;

            case Types.SMALLINT:
                result = Literals.SMALLINT_U;
                break;

            case Types.STRUCT:
                result = "STRUCT";
                break;

            case Types.TIME:
                result = "TIME";
                break;

            case Types.TIMESTAMP:
                result = Literals.TIMESTAMP_U;
                break;

            case Types.TINYINT:
                result = Literals.TINYINT_U;
                break;

            case Types.VARBINARY:
                result = Literals.VARBINARY_U;
                break;

            case Types.VARCHAR:
                result = Literals.VARCHAR_U;
                break;

            default:
                    result = "OTHER";
                    break;
       }

        return result;
    }

    /**
     * Checks if given data type refers to a String.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * String.
     */
    @Override
    public boolean isString(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.CLOB:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    @Override
    public boolean isInteger(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.NUMERIC:
            case Types.DECIMAL:
            case Types.TINYINT:
            case Types.BIGINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents dates.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * date.
     */
    @Override
    public boolean isDate(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.DATE:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }


    /**
     * Checks whether given data type is numeric or not.
     * @param dataType the data type.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isDate(@NotNull final String dataType)
    {
        boolean result = false;

        if  ("date".equalsIgnoreCase(dataType))
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    @Override
    public boolean isTimestamp(final int dataType)
    {
        boolean result = isDate(dataType);

        if  (!result)
        {
            switch (dataType)
            {
                case 11:
                case Types.TIME:
                case Types.TIMESTAMP:
                    result = true;
                    break;

                default:
                    break;
            }
        }

        return result;
    }

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    @Override
    public boolean isTimestamp(@NotNull final String dataType)
    {
        boolean result = isDate(dataType);

        if  (!result)
        {
            if  (   ("time".equalsIgnoreCase(dataType))
                 || (Literals.TIMESTAMP.equalsIgnoreCase(dataType)))
            {
                result = true;
            }
        }

        return result;
    }

    /**
     * Checks if given data type represents objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * object.
     */
    @Override
    public boolean isObject(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents booleans.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * boolean.
     */
    @Override
    public boolean isBoolean(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.BIT:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    @Override
    public boolean isPrimitive(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = true;
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    @Override
    public boolean isPrimitive(@NotNull final String dataType)
    {
        boolean result = false;

        if  (   ("int".equals(dataType))
             || ("long".equalsIgnoreCase(dataType))
             || (Literals.BOOLEAN.equalsIgnoreCase(dataType))
             || (Literals.FLOAT.equalsIgnoreCase(dataType))
             || ("char".equalsIgnoreCase(dataType))
             || ("byte".equalsIgnoreCase(dataType))
             || (Literals.DOUBLE.equalsIgnoreCase(dataType)))
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    @Override
    public boolean isPrimitiveWrapper(@NotNull final String dataType)
    {
        final boolean result;

        if  (   (Integer.class.getSimpleName().equals(dataType))
             || (Long.class.getSimpleName().equals(dataType))
             || (Float.class.getSimpleName().equals(dataType))
             || (Double.class.getSimpleName().equals(dataType)))
        {
            result = true;
        }
        else
        {
            result = false;
        }

        return result;
    }

    /**
     * Checks if given data type represents character large objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * clob.
     */
    @Override
    public boolean isClob(final int dataType)
    {
        return (dataType == Types.CLOB);
    }

    /**
     * Checks if given data type represents character large objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Clob.
     * @param <T> the type.
     */
    @Override
    public <T> boolean isClob(@NotNull final T dataType)
    {
        return "Clob".equals(dataType);
    }

    /**
     * Checks if given data type represents binary large objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Blob.
     */
    @Override
    public boolean isBlob(final int dataType)
    {
        return (dataType == Types.BLOB);
    }

    /**
     * Checks if given data type represents binary large objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Blob.
     */
    @Override
    public boolean isBlob(@NotNull final String dataType)
    {
        return "Blob".equals(dataType);
    }

    /**
     * Checks if given data type represents large objects of any kind.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Lob.
     */
    @SuppressWarnings("unused")
    @Override
    public boolean isLob(final int dataType)
    {
        return isClob(dataType) || isBlob(dataType);
    }

    /**
     * Checks if given data type represents large objects of any kind.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Lob.
     */
    @Override
    public boolean isLob(@NotNull final String dataType)
    {
        return isClob(dataType) || isBlob(dataType);
    }

    /**
     * Checks if given data type represents numbers smaller than int.
     * @param dataType the data type.
     * @return <code>true</code> is such data type is smallint, tinyint
     * or similar.
     */
    @Override
    public boolean isNumberSmallerThanInt(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
//            case Types.INTEGER:
                result = true;
                break;

            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents numbers smaller than int.
     * @param dataType the data type.
     * @return <code>true</code> is such data type is smallint, tinyint
     * or similar.
     */
    @Override
    public boolean isNumberSmallerThanInt(@NotNull final String dataType)
    {
        boolean result = false;

        if  (   ("byte".equalsIgnoreCase(dataType))
             || ("bit".equalsIgnoreCase(dataType))
             || (Literals.TINYINT_L.equalsIgnoreCase(dataType))
             || (Literals.SMALLINT_L.equalsIgnoreCase(dataType)))
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks whether given data type is numeric or not.
     * @param dataType the data type.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isNumeric(@NotNull final String dataType)
    {
        boolean result = false;

        if  (   ("byte".equalsIgnoreCase(dataType))
             || ("bit".equalsIgnoreCase(dataType))
             || (Literals.TINYINT_L.equalsIgnoreCase(dataType))
             || (Literals.SMALLINT_L.equalsIgnoreCase(dataType))
             || ("int".equalsIgnoreCase(dataType))
             || ("long".equalsIgnoreCase(dataType))
             || (Literals.FLOAT.equalsIgnoreCase(dataType))
             || (Literals.DOUBLE.equalsIgnoreCase(dataType))
             || ("bigdecimal".equalsIgnoreCase(dataType)))
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks whether given data type is numeric or not.
     * @param dataType the data type.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isNumeric(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.DECIMAL:
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
            case Types.NUMERIC:
            case Types.FLOAT:
            case Types.REAL:
            case Types.DOUBLE:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given data type is float or not.
     * @param dataType the data type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isFloat(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.FLOAT:
            case Types.REAL:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given data type is double or not.
     * @param dataType the data type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isDouble(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.DOUBLE:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given data type is decimal or not.
     * @param dataType the data type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isDecimal(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.DECIMAL:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given data type is integer or not.
     * @param dataType the data type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isInt(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.INTEGER:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given data type is long or not.
     * @param dataType the data type.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isLong(final int dataType)
    {
        final boolean result;

        switch (dataType)
        {
            case Types.BIGINT:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks whether given type belongs to <code>java.lang</code> package or not.
     * @param type the type.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean inJavaLang(@NotNull final String type)
    {
        boolean result = type.startsWith("java.lang.");

        if (result)
        {
            result = type.lastIndexOf(".") == "java.lang.".length() - 1;
        }

        return result;
    }

    /**
     * Retrieves the JDBC type.
     * @param type the type.
     * @param length the column length.
     * @param precision the column precision.
     * @return the associated {@link java.sql.Types} constant.
     */
    @Override
    public int toJdbcType(@NotNull final String type, final int length, final int precision)
    {
        final int result;

        switch (type.toUpperCase(new Locale("US")))
        {
            case "ARRAY":
                result = Types.ARRAY;
                break;
            case Literals.BIGINT_U:
                result = Types.BIGINT;
                break;
            case Literals.BINARY_U:
                result = Types.BINARY;
                break;
            case "BIT":
                result = Types.BIT;
                break;
            case "BLOB":
                result = Types.BLOB;
                break;
            case "BOOLEAN":
                result = Types.BOOLEAN;
                break;
            case "CHAR":
                result = Types.CHAR;
                break;
            case "CLOB":
                result = Types.CLOB;
                break;
            case "DATALINK":
                result = Types.DATALINK;
                break;
            case "DATE":
                result = Types.DATE;
                break;
            case Literals.DECIMAL:
                result = Types.DECIMAL;
                break;
            case "DISTINCT":
                result = Types.DISTINCT;
                break;
            case Literals.DOUBLE_U:
                result = Types.DOUBLE;
                break;
            case Literals.FLOAT_U:
                result = Types.FLOAT;
                break;
            case Literals.INTEGER_U:
                result = Types.INTEGER;
                break;
            case "JAVA_OBJECT":
                result = Types.JAVA_OBJECT;
                break;
            case "LONGNVARCHAR":
                result = Types.LONGNVARCHAR;
                break;
            case Literals.LONGVARBINARY_U:
                result = Types.LONGVARBINARY;
                break;
            case Literals.LONGVARCHAR_U:
                result = Types.LONGVARCHAR;
                break;
            case "NCHAR":
                result = Types.NCHAR;
                break;
            case "NCLOB":
                result = Types.NCLOB;
                break;
            case "NULL":
                result = Types.NULL;
                break;
            case Literals.NUMBER_U:
                if (precision > 0)
                {
                    result = Types.DECIMAL;
                }
                else
                {
                    result = Types.BIGINT;
                }
                break;
            case Literals.NUMERIC_U:
                result = Types.NUMERIC;
                break;
            case "NVARCHAR":
                result = Types.NVARCHAR;
                break;
            case "ROWID":
                result = Types.ROWID;
                break;
            case Literals.SMALLINT_U:
                result = Types.SMALLINT;
                break;
            case "SQLXML":
                result = Types.SQLXML;
                break;
            case "STRUCT":
                result = Types.STRUCT;
                break;
            case "TIME":
                result = Types.TIME;
                break;
            case Literals.TIMESTAMP_U:
                result = Types.TIMESTAMP;
                break;
            case Literals.TINYINT_U:
                result = Types.TINYINT;
                break;
            case Literals.VARBINARY_U:
                result = Types.VARBINARY;
                break;
            case Literals.VARCHAR_U:
                result = Types.VARCHAR;
                break;
            case Literals.VARCHAR2:
                result = Types.VARCHAR;
                break;
            default:
                result = Types.OTHER;
                break;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"class\": \"" + JdbcMetadataTypeManager.class.getSimpleName() + '"'
            + ", \"native2JavaTypeMapping\": " + m__mNative2JavaTypeMapping
            + ", \"package\": \"" + JdbcMetadataTypeManager.class.getPackage().getName() + "\" }";
    }
}
