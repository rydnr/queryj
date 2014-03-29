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
 * Filename: QueryJCommandUtilsTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for QueryJCommandUtils.
 *
 * Date: 2014/03/29
 * Time: 07:54
 *
 */
package org.acmsl.queryj.api;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;

/*
 * Importing Apache Commons Configuration classes.
 */
import org.apache.commons.configuration.PropertiesConfiguration;

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

/**
 * Tests for {@link QueryJCommandUtils}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/29 07:54
 */
@RunWith(JUnit4.class)
public class QueryJCommandUtilsTest
{
    /**
     * Tests whether {@link QueryJCommandUtils} can retrieve the {@link MetadataManager} instance
     * from the {@link QueryJCommand}.
     */
    @Test
    public void retrieves_the_MetadataManager_instance_from_the_command()
    {
        @NotNull final QueryJCommandUtils instance = QueryJCommandUtils.getInstance();

        @NotNull final MetadataManager t_MetadataManager =
            EasyMock.createMock(MetadataManager.class);

        @NotNull final QueryJCommand t_Command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        new QueryJCommandWrapper<MetadataManager>(t_Command)
            .setSetting(DatabaseMetaDataRetrievalHandler.METADATA_MANAGER, t_MetadataManager);

        Assert.assertEquals(t_MetadataManager, instance.retrieveMetadataManager(t_Command));
    }

    /**
     * Tests whether {@link QueryJCommandUtils} can retrieve the {@link MetadataManager} instance
     * from the {@link QueryJCommand}.
     */
    @Test
    public void throws_exception_trying_to_retrieve_a_missing_MetadataManager_instance_from_the_command()
    {
        @NotNull final QueryJCommandUtils instance = QueryJCommandUtils.getInstance();

        @NotNull final QueryJCommand t_Command =
            new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        try
        {
            instance.retrieveMetadataManager(t_Command);
            Assert.fail("Should have thrown an exception");
        }
        catch (@NotNull final Throwable throwable)
        {
            // everything is fine
        }
    }
}
