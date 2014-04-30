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
 * Filename: ReportMissingPropertiesHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for RetrieveResultSetColumnsHandler.
 *
 * Date: 2014/03/16
 * Time: 10:32
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.SerializablePropertiesConfiguration;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlElement;

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
 * Importing JUnit/PowerMock/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link RetrieveResultSetColumnsHandler}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 10:32
 */
@RunWith(JUnit4.class)
public class ReportMissingPropertiesHandlerTest
{
    /**
     * Checks whether it detects missing properties.
     * Throws QueryJBuildException
     */
    @Test
    public void detects_missing_properties()
        throws QueryJBuildException
    {
        @NotNull final Log t_Log = EasyMock.createNiceMock(Log.class);

        @NotNull final ReportMissingPropertiesHandler instance =
            new ReportMissingPropertiesHandler()
            {
                /**
                 * {@inheritDoc}
                 */
                @Nullable
                @Override
                protected Log retrieveLog()
                {
                    return t_Log;
                }
            };

        @NotNull final QueryJCommand t_Parameters =
            new ConfigurationQueryJCommandImpl(new SerializablePropertiesConfiguration());

        @NotNull final List<Property<String>> t_lProperties = new ArrayList<>(2);
        t_lProperties.add(new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false));
        t_lProperties.add(new PropertyElement<>("tmst", "tmst", 2, "Date", false));

        @NotNull final List<Property<String>> t_lColumns = new ArrayList<>(3);
        t_lColumns.add(new PropertyElement<>("name", "name", 1, String.class.getSimpleName(), false));
        t_lColumns.add(new PropertyElement<>("tmst", "tmst", 2, "Date", false));
        t_lColumns.add(new PropertyElement<>("flag", "flg", 3, int.class.getSimpleName(), false));

        @NotNull final Sql<String> t_Sql =
            new SqlElement<>("id", "dao", "name", "String", Cardinality.SINGLE, "all", true, false, "description");

        new QueryJCommandWrapper<List<Property<String>>>(t_Parameters)
            .setSetting(RetrieveResultPropertiesHandler.CURRENT_PROPERTIES, t_lProperties);
        new QueryJCommandWrapper<List<Property<String>>>(t_Parameters)
            .setSetting(RetrieveResultSetColumnsHandler.CURRENT_COLUMNS, t_lColumns);
        new QueryJCommandWrapper<Sql<String>>(t_Parameters).setSetting(RetrieveQueryHandler.CURRENT_SQL, t_Sql);

        t_Log.warn(EasyMock.anyObject());
        EasyMock.expectLastCall();

        EasyMock.replay(t_Log);

        Assert.assertFalse(instance.handle(t_Parameters));

        EasyMock.verify(t_Log);
    }
}
