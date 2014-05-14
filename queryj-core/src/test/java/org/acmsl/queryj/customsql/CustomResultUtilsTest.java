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
 * Filename: CustomResultUtilsTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for CustomResultUtils.
 *
 * Date: 2014/05/14
 * Time: 11:08
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.TableDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.Arrays;

/**
 * Tests for {@link CustomResultUtils}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/14 11:08
 */
@RunWith(JUnit4.class)
public class CustomResultUtilsTest
{
    /**
     * Checks whether retrieveTable() uses the result's class name.
     */
    @Test
    public void retrieveTable_uses_the_result_class_name()
    {
        @NotNull final CustomResultUtils instance = CustomResultUtils.getInstance();

        @NotNull final Result<String> result = new ResultElement<>("myResultId", "MyTable");
        @NotNull final String tableName = "my_table";
        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        @NotNull final TableDAO tableDAO = EasyMock.createNiceMock(TableDAO.class);

        EasyMock.expect(metadataManager.getTableDAO()).andReturn(tableDAO).anyTimes();
        EasyMock.expect(tableDAO.findAllTableNames()).andReturn(Arrays.asList(tableName)).anyTimes();

        EasyMock.replay(metadataManager);
        EasyMock.replay(tableDAO);

        @Nullable final String retrievedTableName = instance.retrieveTable(result, metadataManager);

        Assert.assertNotNull(retrievedTableName);
        Assert.assertEquals(tableName, retrievedTableName);
    }
}
