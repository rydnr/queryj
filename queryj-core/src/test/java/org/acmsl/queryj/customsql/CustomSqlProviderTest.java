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
 * Filename: CustomSqlProviderTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for CustomSqlProvider.
 *
 * Date: 2014/03/08
 * Time: 18:36
 *
 */
package org.acmsl.queryj.customsql;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.SqlDAO;
import org.acmsl.queryj.metadata.SqlParameterDAO;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.metadata.SqlResultDAO;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests for CustomSqlProvider.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/03/08 18:36
 */
@PrepareForTest(
    {
        SqlDAO.class,
        SqlParameterDAO.class,
        SqlPropertyDAO.class,
        SqlResultDAO.class,
        SqlConnectionFlagsDAO.class,
        SqlStatementFlagsDAO.class,
        SqlResultSetFlagsDAO.class
    })
@RunWith(PowerMockRunner.class)
public class CustomSqlProviderTest
{
    /**
     * A semi-mocked {@link AbstractCustomSqlProvider} instance.
     */
    protected static final class SemiMockedAbstractCustomSqlProvider
        extends AbstractCustomSqlProvider
    {
        /**
         * The connection flags DAO.
         */
        private SqlConnectionFlagsDAO m__ConnectionFlagsDAO;

        /**
         * The SQL DAO.
         */
        private SqlDAO m__SqlDAO;

        /**
         * The Result DAO.
         */
        private SqlResultDAO m__SqlResultDAO;

        /**
         * The parameter DAO.
         */
        private SqlParameterDAO m__SqlParameterDAO;

        /**
         * The property DAO.
         */
        private SqlPropertyDAO m__SqlPropertyDAO;

        /**
         * The statement flags DAO.
         */
        private SqlStatementFlagsDAO m__SqlStatementFlagsDAO;

        /**
         * The ResultSet flags DAO.
         */
        private SqlResultSetFlagsDAO m__SqlResultSetFlagsDAO;

        /**
         * Creates a new instance.
         * @param sqlDAO the SQL DAO.
         * @param parameterDAO the parameter DAO.
         * @param propertyDAO the property DAO.
         * @param resultDAO the result DAO.
         * @param connectionFlagsDAO the connection flags DAO.
         * @param statementFlagsDAO the statement flags DAO.
         * @param resultSetFlagsDAO the result set flags DAO.
         */
        public SemiMockedAbstractCustomSqlProvider(
            @NotNull final SqlDAO sqlDAO,
            @NotNull final SqlParameterDAO parameterDAO,
            @NotNull final SqlPropertyDAO propertyDAO,
            @NotNull final SqlResultDAO resultDAO,
            @NotNull final SqlConnectionFlagsDAO connectionFlagsDAO,
            @NotNull final SqlStatementFlagsDAO statementFlagsDAO,
            @NotNull final SqlResultSetFlagsDAO resultSetFlagsDAO)
        {
            this.m__SqlDAO = sqlDAO;
            this.m__SqlParameterDAO = parameterDAO;
            this.m__SqlPropertyDAO = propertyDAO;
            this.m__SqlResultDAO = resultDAO;
            this.m__ConnectionFlagsDAO = connectionFlagsDAO;
            this.m__SqlStatementFlagsDAO = statementFlagsDAO;
            this.m__SqlResultSetFlagsDAO = resultSetFlagsDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlDAO getSqlDAO()
        {
            return m__SqlDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlResultDAO getSqlResultDAO()
        {
            return m__SqlResultDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlParameterDAO getSqlParameterDAO()
        {
            return m__SqlParameterDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlPropertyDAO getSqlPropertyDAO()
        {
            return m__SqlPropertyDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlConnectionFlagsDAO getSqlConnectionFlagsDAO()
        {
            return m__ConnectionFlagsDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlStatementFlagsDAO getSqlStatementFlagsDAO()
        {
            return m__SqlStatementFlagsDAO;
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public SqlResultSetFlagsDAO getSqlResultSetFlagsDAO()
        {
            return m__SqlResultSetFlagsDAO;
        }
    }

    /**
     * Tests whether different parameters give different hashes.
     */
    @Test
    public void different_parameters_give_different_hashes()
    {
        @NotNull final Parameter<String, String> t_Param1 = new ParameterElement<>("p1", 1, "p1-Name", "int", "1");

        @NotNull final Parameter<String, String> t_Param2 = new ParameterElement<>("p2", 2, "p2-Name", "int", "2");

        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        @NotNull final SqlParameterDAO parameterDAO = EasyMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final SqlPropertyDAO propertyDAO = EasyMock.createNiceMock(SqlPropertyDAO.class);
        @NotNull final SqlResultDAO resultDAO = EasyMock.createNiceMock(SqlResultDAO.class);
        @NotNull final SqlConnectionFlagsDAO connectionFlagsDAO = EasyMock.createNiceMock(SqlConnectionFlagsDAO.class);
        @NotNull final SqlStatementFlagsDAO statementFlagsDAO = EasyMock.createNiceMock(SqlStatementFlagsDAO.class);
        @NotNull final SqlResultSetFlagsDAO resultSetFlagsDAO = EasyMock.createNiceMock(SqlResultSetFlagsDAO.class);

        @NotNull final CustomSqlProvider instance =
            new SemiMockedAbstractCustomSqlProvider(
                sqlDAO,
                parameterDAO,
                propertyDAO,
                resultDAO,
                connectionFlagsDAO,
                statementFlagsDAO,
                resultSetFlagsDAO);

        @NotNull final String t_strHash1 = instance.getHash(t_Param1);
        @NotNull final String t_strHash2 = instance.getHash(t_Param2);

        Assert.assertNotSame(t_strHash1, t_strHash2);
    }


    /**
     * Tests whether equal parameters give equal hashes.
     */
    @Test
    public void equal_parameters_give_equal_hashes()
    {
        @NotNull final Parameter<String, String> t_Param1 = new ParameterElement<>("p1", 1, "p1-Name", "int", "1");

        @NotNull final Parameter<String, String> t_Param2 = new ParameterElement<>("p1", 1, "p1-Name", "int", "2");

        @NotNull final SqlDAO sqlDAO = EasyMock.createNiceMock(SqlDAO.class);
        @NotNull final SqlParameterDAO parameterDAO = EasyMock.createNiceMock(SqlParameterDAO.class);
        @NotNull final SqlPropertyDAO propertyDAO = EasyMock.createNiceMock(SqlPropertyDAO.class);
        @NotNull final SqlResultDAO resultDAO = EasyMock.createNiceMock(SqlResultDAO.class);
        @NotNull final SqlConnectionFlagsDAO connectionFlagsDAO = EasyMock.createNiceMock(SqlConnectionFlagsDAO.class);
        @NotNull final SqlStatementFlagsDAO statementFlagsDAO = EasyMock.createNiceMock(SqlStatementFlagsDAO.class);
        @NotNull final SqlResultSetFlagsDAO resultSetFlagsDAO = EasyMock.createNiceMock(SqlResultSetFlagsDAO.class);

        @NotNull final CustomSqlProvider instance =
            new SemiMockedAbstractCustomSqlProvider(
                sqlDAO,
                parameterDAO,
                propertyDAO,
                resultDAO,
                connectionFlagsDAO,
                statementFlagsDAO,
                resultSetFlagsDAO);

        @NotNull final String t_strHash1 = instance.getHash(t_Param1);
        @NotNull final String t_strHash2 = instance.getHash(t_Param2);

        Assert.assertEquals(t_strHash1, t_strHash2);
    }
}
