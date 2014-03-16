/*
                        queryj

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
 * Filename: SkipValidationIfCacheExistsHandlerTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2014/03/16
 * Time: 16:36
 *
 */
package org.acmsl.queryj.customsql.handlers.customsqlvalidation;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.queryj.ConfigurationQueryJCommandImpl;
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.CustomSqlProviderTest.SemiMockedAbstractCustomSqlProvider;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.Sql.Cardinality;
import org.acmsl.queryj.customsql.SqlConnectionFlagsDAO;
import org.acmsl.queryj.customsql.SqlElement;
import org.acmsl.queryj.customsql.SqlResultSetFlagsDAO;
import org.acmsl.queryj.customsql.SqlStatementFlagsDAO;
import org.acmsl.queryj.customsql.handlers.CustomSqlCacheWritingHandler;
import org.acmsl.queryj.customsql.handlers.CustomSqlProviderRetrievalHandler;
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.easymock.EasyMock;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.nio.charset.Charset;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/16 16:36
 */
@RunWith(JUnit4.class)
public class SkipValidationIfCacheExistsHandlerTest
{
    /**
     * A temporary folder for testing hash caches.
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void skips_further_handlers_if_validation_cache_is_found()
        throws QueryJBuildException
    {
        @NotNull final SkipValidationIfCacheExistsHandler instance =
            new SkipValidationIfCacheExistsHandler();

        @NotNull final Sql<String> t_Sql =
            new SqlElement<>("sql-id", "dao", "sql-name", "select", Cardinality.SINGLE, "all", true, false, "fake sql");

        @NotNull final Result<String> t_Result = new ResultElement<>("r1", "Whatever");

        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        @NotNull final SqlParameterDAO parameterDAO = EasyMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final SqlPropertyDAO propertyDAO = EasyMock.createNiceMock(SqlPropertyDAO.class);
        @NotNull final SqlResultDAO resultDAO = EasyMock.createNiceMock(SqlResultDAO.class);
        @NotNull final SqlConnectionFlagsDAO connectionFlagsDAO = EasyMock.createNiceMock(SqlConnectionFlagsDAO.class);
        @NotNull final SqlStatementFlagsDAO statementFlagsDAO = EasyMock.createNiceMock(SqlStatementFlagsDAO.class);
        @NotNull final SqlResultSetFlagsDAO resultSetFlagsDAO = EasyMock.createNiceMock(SqlResultSetFlagsDAO.class);

        @NotNull final CustomSqlProvider t_CustomSqlProvider =
            new SemiMockedAbstractCustomSqlProvider(
                sqlDAO,
                parameterDAO,
                propertyDAO,
                resultDAO,
                connectionFlagsDAO,
                statementFlagsDAO,
                resultSetFlagsDAO);

        EasyMock.expect(resultDAO.findBySqlId(t_Sql.getId())).andReturn(t_Result).anyTimes();

        EasyMock.replay(resultDAO);

        @NotNull final QueryJCommand t_Parameters = new ConfigurationQueryJCommandImpl(new PropertiesConfiguration());

        new QueryJCommandWrapper<File>(t_Parameters).setSetting(
            CustomSqlCacheWritingHandler.CUSTOM_SQL_OUTPUT_FOLDER_FOR_HASHES, tempFolder.getRoot());
        new QueryJCommandWrapper<CustomSqlProvider>(t_Parameters).setSetting(
            CustomSqlProviderRetrievalHandler.CUSTOM_SQL_PROVIDER, t_CustomSqlProvider);
        new RetrieveQueryHandler().setCurrentSql(t_Sql, t_Parameters);
        new CheckResultSetGettersWorkForDefinedPropertiesHandler().setValidationOutcome(true, t_Sql, t_Parameters);

        new QueryJCommandWrapper<File>(t_Parameters).setSetting(
            CustomSqlCacheWritingHandler.CUSTOM_SQL_OUTPUT_FOLDER_FOR_HASHES, tempFolder.getRoot());

        @NotNull final Charset t_Charset = Charset.defaultCharset();

        @NotNull final String hash = t_CustomSqlProvider.getHash(t_Sql, t_Charset.displayName());

        @NotNull final File hashFile = new File(tempFolder.getRoot() + File.separator + hash);
        FileUtils.getInstance().writeFileIfPossible(hashFile, "", t_Charset);

        instance.handle(t_Parameters);

        Assert.assertTrue(hashFile.exists());

        Assert.assertTrue(instance.handle(t_Parameters));

        EasyMock.verify(resultDAO);
    }
}
