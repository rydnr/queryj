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
 * Description: Indicates JUnit how to test Condition classes.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package unittests.org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.Condition;
import org.acmsl.queryj.Field;
import org.acmsl.queryj.IntField;
import org.acmsl.queryj.Table;
import org.acmsl.queryj.TableAlias;
import org.acmsl.queryj.VariableCondition;

/*
 * Importing JDK classes.
 */
import java.util.Collection;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;

/**
 * Indicates JUnit how to test VariableCondition classes.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$
 */
public class VariableConditionTest
    extends  TestCase
{
    /**
     * The expected condition.
     */
    protected static final String EXPECTED_CONDITION =
        "(USERS.USERID > 10) AND ((USERS.NAME = ?) OR (USERS.NAME is null)) OR (USERS.NAME = ?)";

    /**
     * The USERS table.
     */
    protected static final UsersTable USERS = new UsersTable() {};

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public VariableConditionTest(String name)
    {
        super(name);
    }

    /**
     * Tests the condition class.
     * @see org.acmsl.queryj.Condition
     */
    public void test1Condition()
    {
        Condition t_Condition = USERS.USERID.greaterThan();

        assertTrue(t_Condition != null);

        Collection t_cVariableConditions = t_Condition.getVariableConditions();

        assertTrue(t_cVariableConditions != null);

        assertTrue(t_cVariableConditions.size() == 1);
    }

    /**
     * Tests the condition class.
     * @see org.acmsl.queryj.Condition
     */
    public void test2Condition()
    {
        Condition t_Condition =
            USERS.USERID.greaterThan().and(USERS.USERID.equals());

        assertTrue(t_Condition != null);

        Collection t_cVariableConditions = t_Condition.getVariableConditions();

        assertTrue(t_cVariableConditions != null);

        assertTrue(t_cVariableConditions.size() == 2);
    }

    /**
     * Tests the condition class.
     * @see org.acmsl.queryj.Condition
     */
    public void test3Condition()
    {
        Condition t_Condition =
            USERS.USERID.greaterThan(1).and(USERS.USERID.equals()).and(USERS.USERID.equals(1));

        assertTrue(t_Condition != null);

        Collection t_cVariableConditions = t_Condition.getVariableConditions();

        assertTrue(t_cVariableConditions != null);

        assertTrue(t_cVariableConditions.size() == 1);
    }

    /**
     * Tests the condition class.
     * @see org.acmsl.queryj.Condition
     */
    public void test3Condition2()
    {
        Condition t_Condition =
            USERS.USERID.greaterThan(1).and(USERS.USERID.equals()).and(USERS.USERID.equals());

        assertTrue(t_Condition != null);

        Collection t_cVariableConditions = t_Condition.getVariableConditions();

        assertTrue(t_cVariableConditions != null);

        assertTrue(t_cVariableConditions.size() == 2);
    }

    /**
     * Tests the condition class.
     * @see org.acmsl.queryj.Condition
     */
    public void test3Condition3()
    {
        Condition t_Condition =
            USERS.USERID.greaterThan().and(USERS.USERID.equals()).and(USERS.USERID.equals());

        assertTrue(t_Condition != null);

        Collection t_cVariableConditions = t_Condition.getVariableConditions();

        assertTrue(t_cVariableConditions != null);

        assertTrue(t_cVariableConditions.size() == 3);
    }

    /**
     * Executes the tests from command line.
     * @param args the command-line arguments. Not needed so far.
     */
    public static void main(String args[])
    {
        junit.textui.TestRunner.run(VariableConditionTest.class);
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
        public Field NAME =
            new Field("NAME", this) {};

        /**
         * All fields.
         */
        public Field[] ALL =
            new Field[] {USERID, NAME};

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

