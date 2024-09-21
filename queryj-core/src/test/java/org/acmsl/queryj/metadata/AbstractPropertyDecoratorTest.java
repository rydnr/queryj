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
 * Filename: AbstractPropertyDecoratorTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for AbstractPropertyDecorator.
 *
 * Date: 2014/05/14
 * Time: 10:32
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.metadata.engines.JdbcMetadataTypeManager;

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
 * Tests for {@link AbstractPropertyDecorator}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/14 10:32
 */
@RunWith(JUnit4.class)
public class AbstractPropertyDecoratorTest
{
    @SuppressWarnings("unchecked")
    @Test
    public void getJavaType_supports_boolean_properties()
    {
        @NotNull final MetadataManager metadataManager = EasyMock.createNiceMock(MetadataManager.class);
        @NotNull final MetadataTypeManager metadataTypeManager = JdbcMetadataTypeManager.getInstance();
        EasyMock.expect(metadataManager.getMetadataTypeManager()).andReturn(metadataTypeManager).anyTimes();
        EasyMock.replay(metadataManager);

        @NotNull final Property<String> property =
            new PropertyElement<>("id", "columnName", 1, "boolean", true);

        @NotNull final AbstractPropertyDecorator instance =
            new AbstractPropertyDecorator(property, metadataManager) {};


        Assert.assertEquals("boolean", instance.getJavaType().getValue());

        EasyMock.verify(metadataManager);
    }
}
