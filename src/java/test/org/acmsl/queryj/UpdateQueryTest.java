/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Indicates JUnit how to test UpdateQuery classes.
 *
 */
package unittests.org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.QueryFactory;
import org.acmsl.queryj.UpdateQuery;
import org.acmsl.queryj.StringField;
import org.acmsl.queryj.Table;
import org.acmsl.queryj.TableAlias;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/**
 * Indicates JUnit how to test UpdateQuery classes.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 */
public class UpdateQueryTest
    extends  TestCase
{
    /**
     * The expected query #1.
     */
    protected static final String EXPECTED_QUERY_1 =
          "UPDATE USERS SET NAME = 'myself', AGE = 30 WHERE USERID = 1";

    /**
     * The expected query #2.
     */
    protected static final String EXPECTED_QUERY_2 =
          "UPDATE USERS SET NAME = ?, AGE = 30 WHERE USERID = 1";

    /**
     * The expected query #3.
     */
    protected static final String EXPECTED_QUERY_3 =
          "UPDATE USERS SET NAME = ?, AGE = ? WHERE USERID = 1";

    /**
     * The expected query #4.
     */
    protected static final String EXPECTED_QUERY_4 =
          "UPDATE USERS SET NAME = ?, AGE = ? WHERE USERID = ?";

    /**
     * The expected query #5.
     */
    protected static final String EXPECTED_QUERY_5 =
          "UPDATE USERS SET AGE = ? WHERE (USERS.NAME = ?) AND (USERS.AGE > 20)";

    /**
     * The USERS table.
     */
    protected static final UsersTable USERS = new UsersTable() {};

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public UpdateQueryTest(String name)
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
     * @see org.acmsl.queryj.UpdateQuery#toString()
     */
    public void testToString1()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();

        t_Query.update(USERS);
        t_Query.set(USERS.NAME, "myself");
        t_Query.set(USERS.AGE, 30);

        t_Query.where(USERS.USERID.equals(1));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_1, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.UpdateQuery#toString()
     */
    public void testToString2()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();

        t_Query.update(USERS);
        t_Query.set(USERS.NAME);
        t_Query.set(USERS.AGE, 30);

        t_Query.where(USERS.USERID.equals(1));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_2, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.UpdateQuery#toString()
     */
    public void testToString3()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();

        t_Query.update(USERS);
        t_Query.set(USERS.NAME);
        t_Query.set(USERS.AGE);

        t_Query.where(USERS.USERID.equals(1));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_3, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.UpdateQuery#toString()
     */
    public void testToString4()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();

        t_Query.update(USERS);
        t_Query.set(USERS.NAME);
        t_Query.set(USERS.AGE);

        t_Query.where(USERS.USERID.equals());

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_4, t_strQuery);
    }

    /**
     * Tests the toString() method
     * @see org.acmsl.queryj.UpdateQuery#toString()
     */
    public void testToString5()
    {
        QueryFactory t_QueryFactory = QueryFactory.getInstance();

        assertNotNull(t_QueryFactory);

        UpdateQuery t_Query = t_QueryFactory.createUpdateQuery();

        t_Query.update(USERS);
        t_Query.set(USERS.AGE);

        t_Query.where(USERS.NAME.equals().and(USERS.AGE.greaterThan(20)));

        assertNotNull(t_Query);

        String t_strQuery = t_Query.toString();

        assertNotNull(t_strQuery);

        assertEquals(EXPECTED_QUERY_5, t_strQuery);
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
     * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
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

