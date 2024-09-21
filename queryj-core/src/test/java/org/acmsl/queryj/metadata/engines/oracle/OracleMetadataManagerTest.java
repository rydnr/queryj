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
 * Filename: OracleMetadataManagerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for OracleMetadataManager.
 *
 * Date: 2014/03/18
 * Time: 09:41
 *
 */
package org.acmsl.queryj.metadata.engines.oracle;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataExtractionListener;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link org.acmsl.queryj.metadata.engines.oracle.OracleMetadataManager}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/18 09:41
 */
@RunWith(JUnit4.class)
public class OracleMetadataManagerTest
{
    /**
     * Checks whether it identifies "invalid column name" exceptions.
     */
    @Test
    public void identifies_invalid_column_name_exceptions()
    {
        @NotNull final DatabaseMetaData metadata = EasyMock.createMock(DatabaseMetaData.class);
        @NotNull final MetadataExtractionListener listener = EasyMock.createMock(MetadataExtractionListener.class);
        @NotNull final String catalog = "";
        @NotNull final String schema = "";
        @NotNull final List<String> tableNames = Arrays.asList("table1");
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables = new ArrayList<>(0);
        final boolean disableTableExtraction = true;
        final boolean lazyTableExtraction = true;
        final boolean caseSensitive = false;

        @NotNull final OracleMetadataManager instance =
            new OracleMetadataManager(
                metadata,
                listener,
                catalog,
                schema,
                tableNames,
                tables,
                disableTableExtraction,
                lazyTableExtraction,
                caseSensitive,
                new OracleEngine("12c"));

        @NotNull final SQLException invalidColumnName = new SQLException("Invalid column name", null, 17006);

        Assert.assertTrue(instance.isInvalidColumnNameException(invalidColumnName));

        Assert.assertTrue(instance.isInvalidColumnNameException(new RuntimeException("wrapper", invalidColumnName)));
    }

    /**
     * Checks whether it identifies "invalid column type" exceptions.
     */
    @Test
    public void identifies_invalid_column_type_exceptions()
    {
        @NotNull final DatabaseMetaData metadata = EasyMock.createMock(DatabaseMetaData.class);
        @NotNull final MetadataExtractionListener listener = EasyMock.createMock(MetadataExtractionListener.class);
        @NotNull final String catalog = "";
        @NotNull final String schema = "";
        @NotNull final List<String> tableNames = Arrays.asList("table1");
        @NotNull final List<Table<String, Attribute<String>, List<Attribute<String>>>> tables = new ArrayList<>(0);
        final boolean disableTableExtraction = true;
        final boolean lazyTableExtraction = true;
        final boolean caseSensitive = false;

        @NotNull final OracleMetadataManager instance =
            new OracleMetadataManager(
                metadata,
                listener,
                catalog,
                schema,
                tableNames,
                tables,
                disableTableExtraction,
                lazyTableExtraction,
                caseSensitive,
                new OracleEngine("12c"));

        @NotNull final SQLException invalidColumnType = new SQLException("Invalid column type", null, 17004);

        Assert.assertTrue(instance.isInvalidColumnTypeException(invalidColumnType));

        Assert.assertTrue(instance.isInvalidColumnTypeException(new RuntimeException("wrapper", invalidColumnType)));
    }
}
