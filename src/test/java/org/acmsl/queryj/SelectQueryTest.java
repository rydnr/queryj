/*
                        QueryJ

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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: SelectQueryTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Indicates JUnit how to test SelectQuery classes.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.SelectQuery;
import org.acmsl.queryj.Table;
import org.acmsl.queryj.TableAlias;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

/**
 * Indicates JUnit how to test SelectQuery classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class SelectQueryTest
    extends  TestCase
{
    /**
     * The expected query.
     */
    protected static final String EXPECTED_QUERY =
          "SELECT USERS.USERID, USERS.NAME FROM USERS "
        + "WHERE (USERS.USERID > 10) AND ((USERS.NAME = ?) OR (USERS.NAME is null))";

    /**
     * The USERS table.
     */
    protected static final UsersTable USERS = new UsersTable() {};

    /**
     * The tested instance.
     */
    private SelectQuery m__Query;

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public SelectQueryTest(String name)
    {
        super(name);
    }

    /**
     * Specifies a new tested instance.
     * @param query the query to test.
     */
    protected void setTestedInstance(final SelectQuery query)
    {
        m__Query = query;
    }

    /**
     * Retrieves the tested instance.
     * @return such query.
     */
    protected SelectQuery getTestedInstance()
    {
        return m__Query;
    }

    /**
     * Sets up the test fixture. (Called before every test case method.)
     */
    protected void setUp()
    {
        @NotNull QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        @NotNull SelectQuery t_Query = t_QueryFactory.createSelectQuery();

        t_Query.select(USERS.USERID);
        t_Query.select(USERS.NAME);

        t_Query.from(USERS);

        t_Query.where(
                USERS.USERID.greaterThan(10)
            .and(
                    USERS.NAME.equals()
                .or(USERS.NAME.isNull())));

        setTestedInstance(t_Query);
    }

    /**
     * Tears down the test fixture. (Called after every test case method.)
     */
    protected void tearDown()
    {
        setTestedInstance(null);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.SelectQuery#toString()
     */
    public void testToString()
    {
        SelectQuery t_Query = getTestedInstance();

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY, t_strQuery);
    }

    /**
     * Executes the tests from command line.
     * @param args the command-line arguments. Not needed so far.
     */
    public static void main(String args[])
    {
        junit.textui.TestRunner.run(SelectQueryTest.class);
    }

    /**
     * Test-only table.
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
     * @version $Revision$
     */
    public static class UsersTable
        extends  Table
    {
        /**
         * The USERS table USERID field.
         */
        @NotNull
        public IntField USERID =
            new IntField("USERID", this) {};

        /**
         * The USERS table NAME field.
         */
        @NotNull
        public Field NAME =
            new Field("NAME", this) {};

        /**
         * All fields.
         */
        @NotNull
        public Field[] ALL =
            new Field[] {USERID, NAME};

        /**
         * The table name.
         */
        public static final String TABLE_NAME = "USERS";

        /**
         * The table alias.
         */
        @NotNull
        public TableAlias ALIAS =
            new TableAlias("usrs", this) {};

        /**
         * Creates a USERS table.
         */
        protected UsersTable()
        {
            super(TABLE_NAME);
        }

        /**
         * Retrieves <code>all</code> fields. It's equivalent to a
         * star in a query.
         * @return such fields.
         */
        @NotNull
        public Field[] getAll()
        {
            return ALL;
        }
    }
}

