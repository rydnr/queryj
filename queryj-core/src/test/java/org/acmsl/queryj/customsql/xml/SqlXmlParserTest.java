/*
                      QueryJ Core

Copyright (C) 2002-today Jose San Leandro Armendariz
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
* Filename: SqlXmlParserTest.java
*
* Author: Jose San Leandro Armendariz
*
* Description: Tests for SqlXmlParser.
*
*/
package org.acmsl.queryj.customsql.xml;

/*
* Importing QueryJ Core classes.
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

/*
 * Importing JDK classes.
 */
import java.io.ByteArrayInputStream;
import java.util.List;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests {@link SqlXmlParserTest} class.
 * @see org.acmsl.queryj.customsql.xml.SqlXmlParser
 */
@RunWith(JUnit4.class)
public class SqlXmlParserTest
{
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
        + "    matches=\"single\""
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
        + "    matches=\"multiple\""
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
        + "      class=\"com.foo.bar.dao.CustomerBean\">\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-customer-id.customer_id.property\"/>\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-customer-id.name.property\"/>\n"
        + "    </result>\n"
        + "    <result\n"
        + "      id=\"find-customer-by-person-last-name.result\"\n"
        + "      class=\"com.foo.bar.dao.CustomerBean\">\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-person.customer_id.property\"/>\n"
        + "      <property-ref\n"
        + "        id=\"find-customer-by-person.personName.property\"/>\n"
        + "    </result>\n"
        + "    <result\n"
        + "      id=\"find-customer-for-update.result\"\n"
        + "      class=\"com.foo.bar.dao.CustomerBean\">\n"
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

    /**
     * Tests SqlXmlParserTest.parse()
     * @throws Exception if an unexpected situation occurs.
     * @see org.acmsl.queryj.customsql.xml.SqlXmlParser#parse()
     */
    @Test
    public void testParse()
        throws Exception
    {
        @NotNull final SqlXmlParser t_Parser =
            new SqlXmlParserImpl(new ByteArrayInputStream(TEST_INPUT.getBytes()));
        Assert.assertNotNull(t_Parser);
        t_Parser.parse();
        final List<Sql<String>> t_lQueries = t_Parser.getQueries();
        Assert.assertNotNull(t_lQueries);

        Assert.assertEquals(4, t_lQueries.size());

        final List<Result<String>> t_lResults = t_Parser.getResults();
        Assert.assertEquals(3, t_lResults.size());

        final List<Parameter<String, ?>> t_lParameters = t_Parser.getParameters();
        Assert.assertEquals(1, t_lParameters.size());

        final List<ParameterRef> t_lParameterRefs = t_Parser.getParameterRefs();
        Assert.assertEquals(0, t_lParameterRefs.size());

        final List<Property<String>> t_lProperties = t_Parser.getProperties();
        Assert.assertEquals(6, t_lProperties.size());

        final List<PropertyRef> t_lPropertyRefs = t_Parser.getPropertyRefs();
        Assert.assertEquals(0, t_lPropertyRefs.size());

        final List<ConnectionFlags> t_lConnectionFlags = t_Parser.getConnectionFlagList();
        Assert.assertEquals(5, t_lConnectionFlags.size());

        final List<StatementFlags> t_lStatementFlags = t_Parser.getStatementFlagList();
        Assert.assertEquals(1, t_lStatementFlags.size());

        final List<ResultSetFlags> t_lResultSetFlags = t_Parser.getResultSetFlagList();
        Assert.assertEquals(1, t_lResultSetFlags.size());
    }
}
