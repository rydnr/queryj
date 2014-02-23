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
 * Filename: JdbcTypeManagerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for JdbcMetadataTypeManager.
 *
 * Date: 2014/02/22
 * Time: 10:29
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing EasyMock classes.
 */
import org.easymock.Capture;
import org.easymock.EasyMock;

/*
 * Importing PowerMock classes.
 */
import org.powermock.api.easymock.PowerMock;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Date;
import java.util.Map;

/**
 * Tests for {@link JdbcMetadataTypeManager}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/02/22 10:29
 */
@SuppressWarnings("unused")
@RunWith(JUnit4.class)
public class JdbcTypeManagerTest
{
    /**
     * Tests whether getJdbcType() works correctly for ARRAY.
     */
    @Test
    public void convertsJdbcArrayCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = java.sql.Array.class;
        final int jdbcType = Types.ARRAY;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for ARRAY.
     */
    @Test
    public void preparedStatementSetterWorksForArray()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = java.sql.Array.class;
        final int jdbcType = Types.ARRAY;
        Assert.assertEquals(JdbcTypeManager.SET_ARRAY_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_ARRAY_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for BIGINT.
     */
    @Test
    public void convertsJdbcBigIntCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = long.class;
        final int jdbcType = Types.BIGINT;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for BIGINT.
     */
    @Test
    public void preparedStatementSetterWorksForBigInt()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = long.class;
        final int jdbcType = Types.BIGINT;
        Assert.assertEquals(JdbcTypeManager.SET_LONG_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_LONG_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for BINARY.
     */
    @Test
    public void convertsJdbcBinaryCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.BINARY;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for BINARY.
     */
    @Test
    public void preparedStatementSetterWorksForBinary()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.BINARY;
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for BIT.
     */
    @Test
    public void convertsJdbcBitCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = boolean.class;
        final int jdbcType = Types.BIT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for BIT.
     */
    @Test
    public void preparedStatementSetterWorksForBit()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        final int jdbcType = Types.BIT;
        Assert.assertEquals(JdbcTypeManager.SET_BOOLEAN_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for BLOB.
     */
    @Test
    public void convertsJdbcBlobCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Blob.class;
        final int jdbcType = Types.BLOB;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for BLOB.
     */
    @Test
    public void preparedStatementSetterWorksForBlob()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Blob.class;
        Assert.assertEquals(JdbcTypeManager.SET_BLOB_METHOD, instance.getPreparedStatementSetterMethod(type));
    }

    /**
     * Tests whether getJdbcType() works correctly for BOOLEAN.
     */
    @Test
    public void convertsJdbcBooleanCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = boolean.class;
        final int jdbcType = Types.BOOLEAN;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for BOOLEAN.
     */
    @Test
    public void preparedStatementSetterWorksForBoolean()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = boolean.class;
        final int jdbcType = Types.BOOLEAN;
        Assert.assertEquals(JdbcTypeManager.SET_BOOLEAN_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BOOLEAN_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for CHAR.
     */
    @Test
    public void convertsJdbcCharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.CHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for CHAR.
     */
    @Test
    public void preparedStatementSetterWorksForChar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        final int jdbcType = Types.CHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for CLOB.
     */
    @Test
    public void convertsJdbcClobCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Clob.class;
        final int jdbcType = Types.CLOB;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for CLOB.
     */
    @Test
    public void preparedStatementSetterWorksForClob()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Clob.class;
        final int jdbcType = Types.CLOB;
        Assert.assertEquals(JdbcTypeManager.SET_CLOB_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_CLOB_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for DATALINK.
     */
    @Test
    public void convertsJdbcDataLinkCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = java.net.URL.class;
        final int jdbcType = Types.DATALINK;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for DATALINK.
     */
    @Test
    public void preparedStatementSetterWorksForDataLink()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = URL.class;
        final int jdbcType = Types.DATALINK;
        Assert.assertEquals(JdbcTypeManager.SET_URL_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_URL_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for DATE.
     */
    @Test
    public void convertsJdbcDateCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Date.class;
        final int jdbcType = Types.DATE;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for DATE.
     */
    @Test
    public void preparedStatementSetterWorksForDate()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = java.sql.Date.class;
        final int jdbcType = Types.DATE;
        Assert.assertEquals(JdbcTypeManager.SET_DATE_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_DATE_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for DECIMAL.
     */
    @Test
    public void convertsJdbcDecimalCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = BigDecimal.class;
        final int jdbcType = Types.DECIMAL;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for DECIMAL.
     */
    @Test
    public void preparedStatementSetterWorksForDecimal()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = BigDecimal.class;
        final int jdbcType = Types.DECIMAL;
        Assert.assertEquals(JdbcTypeManager.SET_BIG_DECIMAL_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BIG_DECIMAL_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for DISTINCT.
     */
    @Test
    public void convertsJdbcDistinctCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = void.class;
        final int jdbcType = Types.DISTINCT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for DOUBLE.
     */
    @Test
    public void convertsJdbcDoubleCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = double.class;
        final int jdbcType = Types.DOUBLE;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for DOUBLE.
     */
    @Test
    public void preparedStatementSetterWorksForDouble()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = double.class;
        final int jdbcType = Types.DOUBLE;
        Assert.assertEquals(JdbcTypeManager.SET_DOUBLE_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_DOUBLE_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for FLOAT.
     */
    @Test
    public void convertsJdbcFloatCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = double.class;
        final int jdbcType = Types.FLOAT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for FLOAT.
     */
    @Test
    public void preparedStatementSetterWorksForFloat()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = double.class;
        final int jdbcType = Types.FLOAT;
        Assert.assertEquals(JdbcTypeManager.SET_DOUBLE_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_DOUBLE_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for INTEGER.
     */
    @Test
    public void convertsJdbcIntegerCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = int.class;
        final int jdbcType = Types.INTEGER;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for INTEGER.
     */
    @Test
    public void preparedStatementSetterWorksForInteger()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = int.class;
        final int jdbcType = Types.INTEGER;
        Assert.assertEquals(JdbcTypeManager.SET_INT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_INT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for JAVA_OBJECT.
     */
    @Test
    public void convertsJdbcJavaObjectCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Object.class;
        final int jdbcType = Types.JAVA_OBJECT;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for JAVA_OBJECT.
     */
    @Test
    public void preparedStatementSetterWorksForJavaObject()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Object.class;
        final int jdbcType = Types.JAVA_OBJECT;
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for LONGNVARCHAR.
     */
    @Test
    public void convertsJdbcLongnvarcharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.LONGNVARCHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for LONGNVARCHAR.
     */
    @Test
    public void preparedStatementSetterWorksForLongnvarchar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.LONGNVARCHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for LONGVARBINARY.
     */
    @Test
    public void convertsJdbcLongvarbinaryCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.LONGVARBINARY;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for LONGVARBINARY.
     */
    @Test
    public void preparedStatementSetterWorksForLongnvarbinary()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.LONGVARBINARY;
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for LONGVARCHAR.
     */
    @Test
    public void convertsJdbcLongvarcharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.LONGVARCHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for LONGVARCHAR.
     */
    @Test
    public void preparedStatementSetterWorksForLongvarchar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.LONGVARCHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for NCHAR.
     */
    @Test
    public void convertsJdbcNcharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.NCHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for NCHAR.
     */
    @Test
    public void preparedStatementSetterWorksForNchar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.NCHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for NCLOB.
     */
    @Test
    public void convertsJdbcNclobCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = NClob.class;
        final int jdbcType = Types.NCLOB;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for NCLOB.
     */
    @Test
    public void preparedStatementSetterWorksForNclob()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = NClob.class;
        final int jdbcType = Types.NCLOB;
        Assert.assertEquals(JdbcTypeManager.SET_N_CLOB_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_N_CLOB_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for NULL.
     */
    @Test
    public void convertsJdbcNullCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = void.class;
        final int jdbcType = Types.NULL;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for NUMERIC.
     */
    @Test
    public void convertsJdbcNumericCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = BigDecimal.class;
        final int jdbcType = Types.NUMERIC;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for NUMERIC.
     */
    @Test
    public void preparedStatementSetterWorksForNumeric()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = BigDecimal.class;
        final int jdbcType = Types.NUMERIC;
        Assert.assertEquals(JdbcTypeManager.SET_BIG_DECIMAL_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BIG_DECIMAL_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for NVARCHAR.
     */
    @Test
    public void convertsJdbcNvarcharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.NVARCHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for NVARCHAR.
     */
    @Test
    public void preparedStatementSetterWorksForNvarchar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.NVARCHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for OTHER.
     */
    @Test
    public void convertsJdbcOtherCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = void.class;
        final int jdbcType = Types.OTHER;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for OTHER.
     */
    @Test
    public void preparedStatementSetterWorksForOther()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = void.class;
        final int jdbcType = Types.OTHER;
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for REAL.
     */
    @Test
    public void convertsJdbcRealCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = float.class;
        final int jdbcType = Types.REAL;
        Assert.assertEquals(jdbcType, instance.getJdbcType(type));
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for REAL.
     */
    @Test
    public void preparedStatementSetterWorksForReal()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = float.class;
        final int jdbcType = Types.REAL;
        Assert.assertEquals(JdbcTypeManager.SET_FLOAT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_FLOAT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for REF.
     */
    @Test
    public void convertsJdbcRefCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Ref.class;
        final int jdbcType = Types.REF;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for REF.
     */
    @Test
    public void preparedStatementSetterWorksForRef()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Ref.class;
        final int jdbcType = Types.REF;
        Assert.assertEquals(JdbcTypeManager.SET_REF_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_REF_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for ROWID.
     */
    @Test
    public void convertsJdbcRowidCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = RowId.class;
        final int jdbcType = Types.ROWID;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for ROWID.
     */
    @Test
    public void preparedStatementSetterWorksForRowid()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = RowId.class;
        final int jdbcType = Types.ROWID;
        Assert.assertEquals(JdbcTypeManager.SET_ROW_ID_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_ROW_ID_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for SMALLINT.
     */
    @Test
    public void convertsJdbcSmallintCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = short.class;
        final int jdbcType = Types.SMALLINT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for SMALLINT.
     */
    @Test
    public void preparedStatementSetterWorksForSmallint()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = short.class;
        final int jdbcType = Types.SMALLINT;
        Assert.assertEquals(JdbcTypeManager.SET_SHORT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_SHORT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for SQLXML.
     */
    @Test
    public void convertsJdbcSqlxmlCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = SQLXML.class;
        final int jdbcType = Types.SQLXML;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for SQLXML.
     */
    @Test
    public void preparedStatementSetterWorksForSqlxml()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = SQLXML.class;
        final int jdbcType = Types.SQLXML;
        Assert.assertEquals(JdbcTypeManager.SET_SQLXML_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_SQLXML_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for STRUCT.
     */
    @Test
    public void convertsJdbcStructCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Struct.class;
        final int jdbcType = Types.STRUCT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for STRUCT.
     */
    @Test
    public void preparedStatementSetterWorksForStruct()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Struct.class;
        final int jdbcType = Types.STRUCT;
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_OBJECT_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for TIME.
     */
    @Test
    public void convertsJdbcTimeCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Time.class;
        final int jdbcType = Types.TIME;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for TIME.
     */
    @Test
    public void preparedStatementSetterWorksForTime()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Time.class;
        final int jdbcType = Types.TIME;
        Assert.assertEquals(JdbcTypeManager.SET_TIME_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_TIME_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for TIMESTAMP.
     */
    @Test
    public void convertsJdbcTimestampCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Timestamp.class;
        final int jdbcType = Types.TIMESTAMP;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for TIMESTAMP.
     */
    @Test
    public void preparedStatementSetterWorksForTimestamp()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = Timestamp.class;
        final int jdbcType = Types.TIMESTAMP;
        Assert.assertEquals(JdbcTypeManager.SET_TIMESTAMP_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_TIMESTAMP_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for TINYINT.
     */
    @Test
    public void convertsJdbcTinyintCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = byte.class;
        final int jdbcType = Types.TINYINT;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for TINYINT.
     */
    @Test
    public void preparedStatementSetterWorksForTinyint()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = byte.class;
        final int jdbcType = Types.TINYINT;
        Assert.assertEquals(JdbcTypeManager.SET_BYTE_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BYTE_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for VARBINARY.
     */
    @Test
    public void convertsJdbcVarbinaryCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.VARBINARY;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for VARBINARY.
     */
    @Test
    public void preparedStatementSetterWorksForVarbinary()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = new byte[0].getClass();
        final int jdbcType = Types.VARBINARY;
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_BYTES_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether getJdbcType() works correctly for VARCHAR.
     */
    @Test
    public void convertsJdbcVarcharCorrectly()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.VARCHAR;
        Assert.assertEquals(type, instance.getClass(jdbcType));
    }

    /**
     * Tests whether getPreparedStatementSetterMethod() works correctly for VARCHAR.
     */
    @Test
    public void preparedStatementSetterWorksForVarchar()
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();
        @NotNull final Class<?> type = String.class;
        final int jdbcType = Types.VARCHAR;
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(type));
        Assert.assertEquals(JdbcTypeManager.SET_STRING_METHOD, instance.getPreparedStatementSetterMethod(jdbcType));
    }

    /**
     * Tests whether the helper method findOutClass() works.
     * @throws Exception
     */
    @Test
    public void findOutClassWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance =new JdbcTypeManager();

        @NotNull final Class<?> type = Array.class;

        @NotNull final Array t_Array = new Array()
        {
            @Override public String getBaseTypeName()
            {
                return null;
            }

            @Override public int getBaseType()
            {
                return 0;
            }

            @Override public Object getArray()
            {
                return null;
            }

            @Override public Object getArray(final Map<String, Class<?>> stringClassMap)
            {
                return null;
            }

            @Override public Object getArray(final long l, final int i)
            {
                return null;
            }

            @Override public Object getArray(final long l, final int i, final Map<String, Class<?>> stringClassMap)
            {
                return null;
            }

            @Override public ResultSet getResultSet()
            {
                return null;
            }

            @Override public ResultSet getResultSet(final Map<String, Class<?>> stringClassMap)
            {
                return null;
            }

            @Override public ResultSet getResultSet(final long l, final int i)
            {
                return null;
            }

            @Override public ResultSet getResultSet(final long l, final int i, final Map<String, Class<?>> stringClassMap)
            {
                return null;
            }

            @Override public void free()
            {
            }
        };

        Assert.assertEquals(Array.class, instance.findOutClass(t_Array));
    }

    /**
     * Tests whether the correct PreparedStatement.setXX is called for Array parameters.
     * @throws Exception
     */
    @Test
    public void setArrayInPreparedStatementViaReflectionWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();

        @NotNull final Class<?> type = Array.class;

        @NotNull final Array t_Array = PowerMock.createNiceMock(Array.class);

        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        t_Statement.setArray(1, t_Array);
        EasyMock.expectLastCall();

        EasyMock.replay(t_Array);
        EasyMock.replay(t_Statement);

        instance.setPreparedStatementParameter(t_Statement, 1, t_Array);

        EasyMock.verify(t_Statement);
        EasyMock.verify(t_Array);
    }

    /**
     * Tests whether the correct PreparedStatement.setXX is called for long parameters.
     * @throws Exception
     */
    @Test
    public void setLongInPreparedStatementViaReflectionWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();

        @NotNull final Class<?> type = long.class;
        final long value = 111L;

        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        t_Statement.setLong(1, value);
        EasyMock.expectLastCall();

        EasyMock.replay(t_Statement);

        instance.setPreparedStatementParameter(t_Statement, 1, value);

        EasyMock.verify(t_Statement);
    }

    /**
     * Tests whether the correct PreparedStatement.setXX is called for byte[] parameters.
     * @throws Exception
     */
    @Test
    public void setBytesInPreparedStatementViaReflectionWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();

        @NotNull final Class<?> type = long.class;
        @NotNull final byte[] value = new byte[0];

        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        t_Statement.setBytes(1, value);
        EasyMock.expectLastCall();

        EasyMock.replay(t_Statement);

        instance.setPreparedStatementParameter(t_Statement, 1, value);

        EasyMock.verify(t_Statement);
    }

    /**
     * Tests whether the correct PreparedStatement.setXX is called for boolean parameters.
     * @throws Exception
     */
    @Test
    public void setBooleanInPreparedStatementViaReflectionWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();

        @NotNull final Class<?> type = long.class;
        final boolean value = true;

        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        t_Statement.setBoolean(1, value);
        EasyMock.expectLastCall();

        EasyMock.replay(t_Statement);

        instance.setPreparedStatementParameter(t_Statement, 1, value);

        EasyMock.verify(t_Statement);
    }

    /**
     * Tests whether the correct PreparedStatement.setXX is called for Blob parameters.
     * @throws Exception
     */
    @Test
    public void setBlobInPreparedStatementViaReflectionWorks()
        throws Exception
    {
        @NotNull final JdbcTypeManager instance = new JdbcTypeManager();

        @NotNull final Class<?> type = Blob.class;
        @NotNull final Blob t_Value = PowerMock.createNiceMock(Blob.class);

        @NotNull final PreparedStatement t_Statement = PowerMock.createNiceMock(PreparedStatement.class);
        t_Statement.setBlob(1, t_Value);
        EasyMock.expectLastCall();

        EasyMock.replay(t_Value);
        EasyMock.replay(t_Statement);

        instance.setPreparedStatementParameter(t_Statement, 1, t_Value);

        EasyMock.verify(t_Value);
        EasyMock.verify(t_Statement);
    }
}
