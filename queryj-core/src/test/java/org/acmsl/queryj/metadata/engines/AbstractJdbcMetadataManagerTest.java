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
 * Filename: AbstractJdbcMetadataManagerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractJdbcMetadataManager.
 *
 * Date: 2014/06/13
 * Time: 19:40
 *
 */
package org.acmsl.queryj.metadata.engines;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.api.MetaLanguageUtils;
import org.acmsl.queryj.metadata.MetadataExtractionListener;
import org.acmsl.queryj.metadata.engines.oracle.OracleEngine;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.AttributeIncompleteValueObject;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link AbstractJdbcMetadataManager}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/13 19:40
 */
@RunWith(JUnit4.class)
public class AbstractJdbcMetadataManagerTest
{
    /**
     * Checks whether cloneAttribute() parses the column comment to check
     * if it uses a sequence or not.
     */
    @Test
    public void cloneAttribute_parses_column_comment_looking_for_sequence()
    {
        @NotNull final DatabaseMetaData metadata = EasyMock.createNiceMock(DatabaseMetaData.class);
        @NotNull final MetadataExtractionListener metadataExtractionListener =
            EasyMock.createNiceMock(MetadataExtractionListener.class);

        @NotNull final AbstractJdbcMetadataManager instance =
            new JdbcMetadataManager(
                "name",
                metadata,
                metadataExtractionListener,
                null, //catalog,
                null, //schema,
                new ArrayList<String>(0),
                new ArrayList<Table<String, Attribute<String>, List<Attribute<String>>>>(0),
                true, // disableTableExtraction,
                true, // final boolean lazyTableExtraction,
                true, // final boolean caseSensitive,
                new OracleEngine("12"));

        @NotNull final Attribute<String> attribute =
            new AttributeIncompleteValueObject(
                "name",
                Types.BIGINT,
                "long",
                "table",
                "The pk of the table. @oraseq MYSEQ",
                1,
                10,
                1,
                false,
                null);

        @NotNull final Attribute<String> clonedAttribute =
            instance.cloneAttribute(attribute, MetaLanguageUtils.getInstance());

        @Nullable final String sequence = clonedAttribute.getSequence();

        Assert.assertNotNull(sequence);
        Assert.assertEquals("MYSEQ", sequence);
    }
}
