/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Indicates JUnit how to test InsertQuery classes.
 *
 */
package unittests.org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.InsertQuery;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;
import org.acmsl.queryj.TableAlias;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/**
 * Indicates JUnit how to test InsertQuery classes.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class InsertQueryTest
    extends  TestCase
{
    /**
     * The expected query #1.
     */
    protected static final String EXPECTED_QUERY_1 =
          "INSERT INTO USERS ( NAME, AGE ) VALUES ( 'myself', 30 )";

    /**
     * The expected query #2.
     */
    protected static final String EXPECTED_QUERY_2 =
          "INSERT INTO USERS ( NAME, AGE ) VALUES ( ?, 30 )";

    /**
     * The expected query #3.
     */
    protected static final String EXPECTED_QUERY_3 =
          "INSERT INTO USERS ( NAME, AGE ) VALUES ( 'myself', ? )";

    /**
     * The USERS table.
     */
    protected static final UsersTable USERS = new UsersTable() {};

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public InsertQueryTest(String name)
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
     * @see org.acmsl.queryj.InsertQuery#toString()
     */
    public void testToString1()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        InsertQuery t_Query = t_QueryFactory.createInsertQuery();

        t_Query.insertInto(USERS);
        t_Query.value(USERS.NAME, "myself");
        t_Query.value(USERS.AGE, 30);

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(t_strQuery, EXPECTED_QUERY_1);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.InsertQuery#toString()
     */
    public void testToString2()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        InsertQuery t_Query = t_QueryFactory.createInsertQuery();

        t_Query.insertInto(USERS);
        t_Query.value(USERS.NAME);
        t_Query.value(USERS.AGE, 30);

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(t_strQuery, EXPECTED_QUERY_2);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.InsertQuery#toString()
     */
    public void testToString3()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        InsertQuery t_Query = t_QueryFactory.createInsertQuery();

        t_Query.insertInto(USERS);
        t_Query.value(USERS.NAME, "myself");
        t_Query.value(USERS.AGE);

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(t_strQuery, EXPECTED_QUERY_3);
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

