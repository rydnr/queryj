/*
                      Project tests

Copyright (C) 2003  Jose San Leandro Armend?riz
chous@acm-sl.org

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public
License as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Thanks to ACM S.L. for distributing this library under the GPL license.
Contact info: pepe@acm-sl.com
Postal Address: c/Playa de Lagoa, 1
Urb. Valdecaba?as
Boadilla del monte
28660 Madrid
Spain

******************************************************************************
*
* Filename: $RCSfile$
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.customsql.xml.
*
*/
package org.acmsl.queryj.customsql.xml;

/*
* Importing project classes.
*/
import org.acmsl.queryj.customsql.ConnectionFlags;
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterRef;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyRef;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultSetFlags;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.customsql.StatementFlags;

// JUnitDoclet begin import
import java.io.ByteArrayInputStream;
import java.util.List;
// JUnitDoclet end import

/*
* Importing JUnit classes.
*/
import junit.framework.TestCase;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
This file is part of  JUnitDoclet, a project to generate basic
test cases  from source code and  helping to keep them in sync
during refactoring.

Copyright (C) 2002-2005  ObjectFab GmbH  (http://www.objectfab.de/)

This library is  free software; you can redistribute it and/or
modify  it under the  terms of  the  GNU Lesser General Public
License as published  by the  Free Software Foundation; either
version 2.1  of the  License, or  (at your option)  any  later
version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You  should  have  received a  copy of the  GNU Lesser General
Public License along with this  library; if not, write  to the
Free  Software  Foundation, Inc.,  59 Temple Place,  Suite 330,
Boston, MA  02111-1307  USA
*/


/**
* Tests SqlXmlParserTest class.
* @see org.acmsl.queryj.customsql.xml.SqlXmlParser
*/
public class SqlXmlParserTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
    /**
     * Test input
     */
    public static final String TEST_INPUT =
          "<sql-list>\n"
        + "  <sql\n"
        + "    id=\"find-customer-by-customer-id-result\"\n"
        + "    dao=\"customer\"\n"
        + "    name=\"find-customer-by-customer-id\"\n"
        + "    type=\"select\"\n"
        + "    implementation=\"all\">\n"
        + "    <description>Finds the customer filtering by its id</description>\n"
        + "    <value>select * from customers where customer_id = ?</value>\n"
        + "    <parameter-ref id=\"long-parameter-1\"/>\n"
        + "    <result-ref id=\"find-customer-by-customer-id.result\"/>\n"
        + "  </sql>\n"
        + "  <sql\n"
        + "    id=\"find-customer-by-person-last-name\"\n"
        + "    name=\"find-customer-by-person-last-name\"\n"
        + "    dao=\"customer\"\n"
        + "    type=\"select\"\n"
        + "    implementation=\"all\">\n"
        + "    <description>Finds the customer filtering by its last name</description>\n"
        + "    <value>select c.customer_id, p.name from customers c, people p where p.last_name like ?</value>\n"
        + "    <parameter-ref id=\"long-parameter-1\"/>\n"
        + "    <result-ref id=\"find-customer-by-person-last-name.result\"/>\n"
        + "  </sql>\n"
        + "  <sql\n"
        + "    id=\"insert-test\"\n"
        + "    name=\"insert-test\"\n"
        + "    dao=\"customer\"\n"
        + "    type=\"insert\"\n"
        + "    implementation=\"all\">\n"
        + "    <description>Inserts a new customer</description>\n"
        + "    <value>insert into customers (customer_id, name) values (?, \"test\")</value>\n"
        + "    <parameter-ref id=\"long-parameter-1\"/>\n"
        + "  </sql>\n"
        + "  <sql\n"
        + "    id=\"find-customer-for-update\"\n"
        + "    dao=\"customer\"\n"
        + "    name=\"find-customer-for-update\"\n"
        + "    type=\"select-for-update\"\n"
        + "    implementation=\"all\">\n"
        + "    <description>Finds a customer for update</description>\n"
        + "    <value>select * from customers where customer_id = ? for update</value>\n"
        + "    <parameter-ref id=\"long-parameter-1\"/>\n"
        + "    <result-ref id=\"find-customer-for-update.result\"/>\n"
        + "    <connection-flags-ref id=\"connection-flag-transaction-serializable\"/>\n"
        + "    <statement-flags-ref id=\"find-customer-for-update-statement-flags\"/>\n"
        + "    <resultset-flags-ref id=\"find-customer-for-update-resultset-flags\"/>\n"
        + "  </sql>\n"
        + "\n"
        + "  <!-- SQL parameter metadata -->\n"
        + "  <parameter-list>\n"
        + "    <parameter\n"
        + "      id=\"long-parameter-1\"\n"
        + "      index=\"1\"\n"
        + "      type=\"long\"\n"
        + "      name=\"id\"/>\n"
        + "  </parameter-list>\n"
        + "\n"
        + "  <!-- Result metadata -->\n"
        + "  <result-list>\n"
        + "    <result\n"
        + "      id=\"find-customer-by-customer-id.result\"\n"
        + "      class=\"com.foo.bar.dao.CustomerBean\"\n"
        + "      matches=\"single\">\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-customer-id.customer_id.property\"/>\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-customer-id.name.property\"/>\n"
        + "    </result>\n"
        + "    <result\n"
        + "      id=\"find-customer-by-person-last-name.result\"\n"
        + "      class=\"com.foo.bar.dao.CustomerBean\"\n"
        + "      matches=\"multiple\">\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-person.customer_id.property\"/>\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-person.personName.property\"/>\n"
        + "    </result>\n"
        + "    <result\n"
        + "      id=\"find-customer-for-update.result\"\n"
        + "      class=\"com.foo.bar.dao.CustomerBean\"\n"
        + "      matches=\"single\">\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-for-update.customer_id.property\"/>\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-for-update.name.property\"/>\n"
        + "    </result>\n"
        + "  </result-list>\n"
        + "\n"
        + "  <!-- Result property metadata -->\n"
        + "  <property-list>\n"
        + "    <property\n"
        + "      id=\"find-customer-by-customer-id.customer_id.property\"\n"
        + "      column_name=\"customer_id\"\n"
        + "      type=\"long\"/>\n"
        + "    <property\n"
        + "      id=\"find-customer-by-customer-id.name.property\"\n"
        + "      column_name=\"name\"\n"
        + "      type=\"String\"/>\n"
        + "    <property\n"
        + "      id=\"find-customer-by-person.customer_id.property\"\n"
        + "      column_name=\"c.customer_id\"\n"
        + "      name=\"customerId\"\n"
        + "      type=\"long\"/>\n"
        + "    <property\n"
        + "      id=\"find-customer-by-person.personName.property\"\n"
        + "      column_name=\"p.name\"\n"
        + "      name=\"personName\"\n"
        + "      type=\"String\"/>\n"
        + "    <property\n"
        + "      id=\"find-customer-for-update.customer_id.property\"\n"
        + "      column_name=\"customer_id\"\n"
        + "      type=\"long\"/>\n"
        + "    <property\n"
        + "      id=\"find-customer-for-update.name.property\"\n"
        + "      column_name=\"name\"\n"
        + "      type=\"String\"/>\n"
        + "  </property-list>\n"
        + "\n"
        + "  <flag-list>\n"
        + "    <connection-flags\n"
        + "      id=\"connection-flag-transaction-none\"\n"
        + "      transactionisolation=\"TRANSACTION_NONE\"/>\n"
        + "    <connection-flags\n"
        + "      id=\"connection-flag-transaction-read-committed\"\n"
        + "      transactionisolation=\"TRANSACTION_READ_COMMITTED\"/>\n"
        + "    <connection-flags\n"
        + "      id=\"connection-flag-transaction-read-uncommitted\"\n"
        + "      transactionisolation=\"TRANSACTION_READ_UNCOMMITTED\"/>\n"
        + "    <connection-flags\n"
        + "      id=\"connection-flag-transaction-repeatable-read\"\n"
        + "      transactionisolation=\"TRANSACTION_REPEATABLE_READ\"/>\n"
        + "    <connection-flags\n"
        + "      id=\"connection-flag-transaction-serializable\"\n"
        + "      transactionisolation=\"TRANSACTION_TRANSACTION_SERIALIZABLE\"/>\n"
        + "    <statement-flags\n"
        + "      id=\"find-customer-for-update-statement-flags\"\n"
        + "      autogeneratedkeys=\"NO_GENERATED_KEYS\"\n"
        + "      fetchsize=\"10\"\n"
        + "      maxfieldsize=\"1000\"\n"
        + "      maxrows=\"10\"\n"
        + "      querytimeout=\"30\"\n"
        + "      fetchdirection=\"FETCH_FORWARD\"\n"
        + "      escapeprocessing=\"false\"\n"
        + "      moreresults=\"CLOSE_ALL_RESULTS\"\n"
        + "      cursorname=\"mycursor\"/>\n"
        + "    <resultset-flags\n"
        + "      id=\"find-customer-for-update-resultset-flags\"\n"
        + "      type=\"TYPE_FORWARD_ONLY\"\n"
        + "      concurrency=\"CONCUR_UPDATABLE\"\n"
        + "      holdability=\"CLOSE_CURSORS_AT_COMMIT\"/>\n"
        + "  </flag-list>\n"
        + "</sql-list>\n";

  // JUnitDoclet end class
  
  /**
  * Creates a SqlXmlParserTest with given name.
  * @param name such name.
  */
  public SqlXmlParserTest(final String name)
  {
    // JUnitDoclet begin method SqlXmlParserTest
    super(name);
    // JUnitDoclet end method SqlXmlParserTest
  }
  
  /**
  * Performs any required steps before each test.
  * @throws Exception if an unexpected situation occurs.
  */
  protected void setUp()
  throws Exception
  {
    // JUnitDoclet begin method testcase.setUp
    super.setUp();
    // JUnitDoclet end method testcase.setUp
  }
  
  /**
  * Performs any required steps after each test.
  * @throws Exception if an unexpected situation occurs.
  */
  protected void tearDown()
  throws Exception
  {
    // JUnitDoclet begin method testcase.tearDown
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests SqlXmlParserTest.parse()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.customsql.xml.SqlXmlParser#parse()
  */
  @SuppressWarnings("unused")
  public void testParse()
  throws Exception
  {
    // JUnitDoclet begin method parse
      @NotNull final SqlXmlParser t_Parser =
          new SqlXmlParserImpl(new ByteArrayInputStream(TEST_INPUT.getBytes())) {};
      assertNotNull(t_Parser);
      t_Parser.parse();
      final List<Sql> t_lQueries = t_Parser.getQueries();
      assertNotNull(t_lQueries);
      System.out.println(t_lQueries);
      assertEquals(4, t_lQueries.size());

      final List<Result> t_lResults = t_Parser.getResults();
      assertEquals(3, t_lResults.size());

      final List<Parameter> t_lParameters = t_Parser.getParameters();
      assertEquals(1, t_lParameters.size());

      final List<ParameterRef> t_lParameterRefs = t_Parser.getParameterRefs();
      assertEquals(0, t_lParameterRefs.size());

      final List<Property> t_lProperties = t_Parser.getProperties();
      assertEquals(6, t_lProperties.size());

      final List<PropertyRef> t_lPropertyRefs = t_Parser.getPropertyRefs();
      assertEquals(0, t_lPropertyRefs.size());

      final List<ConnectionFlags> t_lConnectionFlags = t_Parser.getConnectionFlagList();
      assertEquals(5, t_lConnectionFlags.size());

      final List<StatementFlags> t_lStatementFlags = t_Parser.getStatementFlagList();
      assertEquals(1, t_lStatementFlags.size());

      final List<ResultSetFlags> t_lResultSetFlags = t_Parser.getResultSetFlagList();
      assertEquals(1, t_lResultSetFlags.size());

    // JUnitDoclet end method parse
  }
  
  /**
  * JUnitDoclet moves marker to this method, if there is not match
  * for them in the regenerated code and if the marker is not empty.
  * This way, no test gets lost when regenerating after renaming.
  * Method testVault is supposed to be empty.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testVault()
  throws Exception
  {
    // JUnitDoclet begin method testcase.testVault
    // JUnitDoclet end method testcase.testVault
  }
  
  public static void main(final String[] args)
  {
    // JUnitDoclet begin method testcase.main
    junit.textui.TestRunner.run(SqlXmlParserTest.class);
    // JUnitDoclet end method testcase.main
  }
}
