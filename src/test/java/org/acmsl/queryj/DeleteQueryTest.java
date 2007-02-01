/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: DeleteQueryTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Indicates JUnit how to test DeleteQuery classes.
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.DeleteQuery;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;
import org.acmsl.queryj.TableAlias;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/**
 * Indicates JUnit how to test DeleteQuery classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class DeleteQueryTest
    extends  TestCase
{
    /**
     * The expected query #1.
     */
    protected static final String EXPECTED_QUERY_1 =
        "DELETE FROM USERS WHERE USERID = 1";

    /**
     * The expected query #2.
     */
    protected static final String EXPECTED_QUERY_2 =
        "DELETE FROM USERS WHERE USERID = ?";

    /**
     * The expected query #3.
     */
    protected static final String EXPECTED_QUERY_3 =
        "DELETE FROM USERS WHERE (USERS.NAME = 'myself') AND (USERS.AGE = 30)";

    /**
     * The expected query #4.
     */
    protected static final String EXPECTED_QUERY_4 =
        "DELETE FROM USERS WHERE (USERS.NAME = ?) AND (USERS.AGE = 30)";

    /**
     * The expected query #5.
     */
    protected static final String EXPECTED_QUERY_5 =
        "DELETE FROM USERS WHERE (USERS.NAME = 'myself') AND (USERS.AGE = ?)";

    /**
     * The expected query #6.
     */
    protected static final String EXPECTED_QUERY_6 =
        "DELETE FROM USERS WHERE (USERS.NAME = ?) OR (USERS.AGE = ?)";

    /**
     * The USERS table.
     */
    protected static final UsersTable USERS = new UsersTable() {};

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public DeleteQueryTest(String name)
    {
        super(name);
    }

    /**
     * Sets up the test fixture. (Called before every test case method.)
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture. (Called after every test case method.)
     */
    protected void tearDown()
    {
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString1()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(USERS.USERID.equals(1));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_1, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString2()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(USERS.USERID.equals());

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_2, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString3()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(
                USERS.NAME.equals("myself")
            .and(
                USERS.AGE.equals(30)));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_3, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString4()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(
                USERS.NAME.equals()
            .and(
                USERS.AGE.equals(30)));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_4, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString5()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(
                USERS.NAME.equals("myself")
            .and(
                USERS.AGE.equals()));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_5, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.DeleteQuery#toString()
     */
    public void testToString6()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        DeleteQuery t_Query = t_QueryFactory.createDeleteQuery();

        t_Query.deleteFrom(USERS);

        t_Query.where(
                USERS.NAME.equals()
            .or(
                USERS.AGE.equals()));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_6, t_strQuery);
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
        public IntField USERID =
            new IntField("USERID", this) {};

        /**
         * The USERS table NAME field.
         */
        public StringField NAME =
            new StringField("NAME", this) {};

        /**
         * The USERS table AGE field.
         */
        public IntField AGE =
            new IntField("AGE", this) {};

        /**
         * All fields.
         */
        public Field[] ALL =
            new Field[] {USERID, NAME, AGE};

        /**
         * The table name.
         */
        public static final String TABLE_NAME = "USERS";

        /**
         * The table alias.
         */
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
        public Field[] getAll()
        {
            return ALL;
        }
    }
}

