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
*              unittests.org.acmsl.queryj.
*
*/
package unittests.org.acmsl.queryj;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.QueryResultSet;
// JUnitDoclet end import

/*
* Importing JUnit classes.
*/
import junit.framework.TestCase;

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
* Tests QueryResultSetTest class.
* @see org.acmsl.queryj.QueryResultSet
*/
public class QueryResultSetTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.QueryResultSet queryresultset = null;
  // JUnitDoclet end class
  
  /**
  * Creates a QueryResultSetTest with given name.
  * @param name such name.
  */
  public QueryResultSetTest(String name)
  {
    // JUnitDoclet begin method QueryResultSetTest
    super(name);
    // JUnitDoclet end method QueryResultSetTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.QueryResultSet createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.QueryResultSet(null, null);
    // JUnitDoclet end method testcase.createInstance
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
    queryresultset = createInstance();
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
    queryresultset = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests QueryResultSetTestgetBytes()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getBytes(int)
  */
  public void testGetBytes()
  throws Exception
  {
    // JUnitDoclet begin method getBytes
    // JUnitDoclet end method getBytes
  }
  
  /**
  * Tests QueryResultSetTestnext()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#next()
  */
  public void testNext()
  throws Exception
  {
    // JUnitDoclet begin method next
    // JUnitDoclet end method next
  }
  
  /**
  * Tests QueryResultSetTestprevious()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#previous()
  */
  public void testPrevious()
  throws Exception
  {
    // JUnitDoclet begin method previous
    // JUnitDoclet end method previous
  }
  
  /**
  * Tests QueryResultSetTestgetBoolean()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getBoolean(int)
  */
  public void testGetBoolean()
  throws Exception
  {
    // JUnitDoclet begin method getBoolean
    // JUnitDoclet end method getBoolean
  }
  
  /**
  * Tests QueryResultSetTestgetType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getType()
  */
  public void testGetType()
  throws Exception
  {
    // JUnitDoclet begin method getType
    // JUnitDoclet end method getType
  }
  
  /**
  * Tests QueryResultSetTestgetLong()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getLong(int)
  */
  public void testGetLong()
  throws Exception
  {
    // JUnitDoclet begin method getLong
    // JUnitDoclet end method getLong
  }
  
  /**
  * Tests QueryResultSetTestgetObject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getObject(int)
  */
  public void testGetObject()
  throws Exception
  {
    // JUnitDoclet begin method getObject
    // JUnitDoclet end method getObject
  }
  
  /**
  * Tests QueryResultSetTestclose()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#close()
  */
  public void testClose()
  throws Exception
  {
    // JUnitDoclet begin method close
    // JUnitDoclet end method close
  }
  
  /**
  * Tests QueryResultSetTestgetRef()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getRef(int)
  */
  public void testGetRef()
  throws Exception
  {
    // JUnitDoclet begin method getRef
    // JUnitDoclet end method getRef
  }
  
  /**
  * Tests QueryResultSetTestgetTime()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getTime(int)
  */
  public void testGetTime()
  throws Exception
  {
    // JUnitDoclet begin method getTime
    // JUnitDoclet end method getTime
  }
  
  /**
  * Tests QueryResultSetTestgetDate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getDate(int)
  */
  public void testGetDate()
  throws Exception
  {
    // JUnitDoclet begin method getDate
    // JUnitDoclet end method getDate
  }
  
  /**
  * Tests QueryResultSetTestfirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#first()
  */
  public void testFirst()
  throws Exception
  {
    // JUnitDoclet begin method first
    // JUnitDoclet end method first
  }
  
  /**
  * Tests QueryResultSetTestgetByte()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getByte(int)
  */
  public void testGetByte()
  throws Exception
  {
    // JUnitDoclet begin method getByte
    // JUnitDoclet end method getByte
  }
  
  /**
  * Tests QueryResultSetTestgetShort()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getShort(int)
  */
  public void testGetShort()
  throws Exception
  {
    // JUnitDoclet begin method getShort
    // JUnitDoclet end method getShort
  }
  
  /**
  * Tests QueryResultSetTestgetInt()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getInt(int)
  */
  public void testGetInt()
  throws Exception
  {
    // JUnitDoclet begin method getInt
    // JUnitDoclet end method getInt
  }
  
  /**
  * Tests QueryResultSetTestgetFloat()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getFloat(int)
  */
  public void testGetFloat()
  throws Exception
  {
    // JUnitDoclet begin method getFloat
    // JUnitDoclet end method getFloat
  }
  
  /**
  * Tests QueryResultSetTestgetDouble()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getDouble(int)
  */
  public void testGetDouble()
  throws Exception
  {
    // JUnitDoclet begin method getDouble
    // JUnitDoclet end method getDouble
  }
  
  /**
  * Tests QueryResultSetTestgetMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getMetaData()
  */
  public void testGetMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getMetaData
    // JUnitDoclet end method getMetaData
  }
  
  /**
  * Tests QueryResultSetTestgetWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getWarnings()
  */
  public void testGetWarnings()
  throws Exception
  {
    // JUnitDoclet begin method getWarnings
    // JUnitDoclet end method getWarnings
  }
  
  /**
  * Tests QueryResultSetTestclearWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#clearWarnings()
  */
  public void testClearWarnings()
  throws Exception
  {
    // JUnitDoclet begin method clearWarnings
    // JUnitDoclet end method clearWarnings
  }
  
  /**
  * Tests QueryResultSetTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchDirection()
  throws Exception
  {
    // JUnitDoclet begin method setFetchDirection getFetchDirection
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryresultset.setFetchDirection(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryresultset.getFetchDirection());
    }
    // JUnitDoclet end method setFetchDirection getFetchDirection
  }
  
  /**
  * Tests QueryResultSetTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchSize()
  throws Exception
  {
    // JUnitDoclet begin method setFetchSize getFetchSize
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryresultset.setFetchSize(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryresultset.getFetchSize());
    }
    // JUnitDoclet end method setFetchSize getFetchSize
  }
  
  /**
  * Tests QueryResultSetTestgetString()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getString(int)
  */
  public void testGetString()
  throws Exception
  {
    // JUnitDoclet begin method getString
    // JUnitDoclet end method getString
  }
  
  /**
  * Tests QueryResultSetTestgetArray()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getArray(int)
  */
  public void testGetArray()
  throws Exception
  {
    // JUnitDoclet begin method getArray
    // JUnitDoclet end method getArray
  }
  
  /**
  * Tests QueryResultSetTestgetAsciiStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getAsciiStream(int)
  */
  public void testGetAsciiStream()
  throws Exception
  {
    // JUnitDoclet begin method getAsciiStream
    // JUnitDoclet end method getAsciiStream
  }
  
  /**
  * Tests QueryResultSetTestgetBigDecimal()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getBigDecimal(int, int)
  */
  public void testGetBigDecimal()
  throws Exception
  {
    // JUnitDoclet begin method getBigDecimal
    // JUnitDoclet end method getBigDecimal
  }
  
  /**
  * Tests QueryResultSetTestgetBinaryStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getBinaryStream(int)
  */
  public void testGetBinaryStream()
  throws Exception
  {
    // JUnitDoclet begin method getBinaryStream
    // JUnitDoclet end method getBinaryStream
  }
  
  /**
  * Tests QueryResultSetTestgetBlob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getBlob(int)
  */
  public void testGetBlob()
  throws Exception
  {
    // JUnitDoclet begin method getBlob
    // JUnitDoclet end method getBlob
  }
  
  /**
  * Tests QueryResultSetTestgetClob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getClob(int)
  */
  public void testGetClob()
  throws Exception
  {
    // JUnitDoclet begin method getClob
    // JUnitDoclet end method getClob
  }
  
  /**
  * Tests QueryResultSetTestgetTimestamp()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getTimestamp(int)
  */
  public void testGetTimestamp()
  throws Exception
  {
    // JUnitDoclet begin method getTimestamp
    // JUnitDoclet end method getTimestamp
  }
  
  /**
  * Tests QueryResultSetTestgetUnicodeStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getUnicodeStream(int)
  */
  public void testGetUnicodeStream()
  throws Exception
  {
    // JUnitDoclet begin method getUnicodeStream
    // JUnitDoclet end method getUnicodeStream
  }
  
  /**
  * Tests QueryResultSetTestwasNull()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#wasNull()
  */
  public void testWasNull()
  throws Exception
  {
    // JUnitDoclet begin method wasNull
    // JUnitDoclet end method wasNull
  }
  
  /**
  * Tests QueryResultSetTestgetCharacterStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getCharacterStream(int)
  */
  public void testGetCharacterStream()
  throws Exception
  {
    // JUnitDoclet begin method getCharacterStream
    // JUnitDoclet end method getCharacterStream
  }
  
  /**
  * Tests QueryResultSetTestabsolute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#absolute(int)
  */
  public void testAbsolute()
  throws Exception
  {
    // JUnitDoclet begin method absolute
    // JUnitDoclet end method absolute
  }
  
  /**
  * Tests QueryResultSetTestafterLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#afterLast()
  */
  public void testAfterLast()
  throws Exception
  {
    // JUnitDoclet begin method afterLast
    // JUnitDoclet end method afterLast
  }
  
  /**
  * Tests QueryResultSetTestbeforeFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#beforeFirst()
  */
  public void testBeforeFirst()
  throws Exception
  {
    // JUnitDoclet begin method beforeFirst
    // JUnitDoclet end method beforeFirst
  }
  
  /**
  * Tests QueryResultSetTestcancelRowUpdates()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#cancelRowUpdates()
  */
  public void testCancelRowUpdates()
  throws Exception
  {
    // JUnitDoclet begin method cancelRowUpdates
    // JUnitDoclet end method cancelRowUpdates
  }
  
  /**
  * Tests QueryResultSetTestdeleteRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#deleteRow()
  */
  public void testDeleteRow()
  throws Exception
  {
    // JUnitDoclet begin method deleteRow
    // JUnitDoclet end method deleteRow
  }
  
  /**
  * Tests QueryResultSetTestfindColumn()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#findColumn(java.lang.String)
  */
  public void testFindColumn()
  throws Exception
  {
    // JUnitDoclet begin method findColumn
    // JUnitDoclet end method findColumn
  }
  
  /**
  * Tests QueryResultSetTestgetConcurrency()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getConcurrency()
  */
  public void testGetConcurrency()
  throws Exception
  {
    // JUnitDoclet begin method getConcurrency
    // JUnitDoclet end method getConcurrency
  }
  
  /**
  * Tests QueryResultSetTestgetCursorName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getCursorName()
  */
  public void testGetCursorName()
  throws Exception
  {
    // JUnitDoclet begin method getCursorName
    // JUnitDoclet end method getCursorName
  }
  
  /**
  * Tests QueryResultSetTestgetRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getRow()
  */
  public void testGetRow()
  throws Exception
  {
    // JUnitDoclet begin method getRow
    // JUnitDoclet end method getRow
  }
  
  /**
  * Tests QueryResultSetTestgetStatement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getStatement()
  */
  public void testGetStatement()
  throws Exception
  {
    // JUnitDoclet begin method getStatement
    // JUnitDoclet end method getStatement
  }
  
  /**
  * Tests QueryResultSetTestinsertRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#insertRow()
  */
  public void testInsertRow()
  throws Exception
  {
    // JUnitDoclet begin method insertRow
    // JUnitDoclet end method insertRow
  }
  
  /**
  * Tests QueryResultSetTestisAfterLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#isAfterLast()
  */
  public void testIsAfterLast()
  throws Exception
  {
    // JUnitDoclet begin method isAfterLast
    // JUnitDoclet end method isAfterLast
  }
  
  /**
  * Tests QueryResultSetTestisBeforeFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#isBeforeFirst()
  */
  public void testIsBeforeFirst()
  throws Exception
  {
    // JUnitDoclet begin method isBeforeFirst
    // JUnitDoclet end method isBeforeFirst
  }
  
  /**
  * Tests QueryResultSetTestisFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#isFirst()
  */
  public void testIsFirst()
  throws Exception
  {
    // JUnitDoclet begin method isFirst
    // JUnitDoclet end method isFirst
  }
  
  /**
  * Tests QueryResultSetTestisLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#isLast()
  */
  public void testIsLast()
  throws Exception
  {
    // JUnitDoclet begin method isLast
    // JUnitDoclet end method isLast
  }
  
  /**
  * Tests QueryResultSetTestlast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#last()
  */
  public void testLast()
  throws Exception
  {
    // JUnitDoclet begin method last
    // JUnitDoclet end method last
  }
  
  /**
  * Tests QueryResultSetTestmoveToCurrentRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#moveToCurrentRow()
  */
  public void testMoveToCurrentRow()
  throws Exception
  {
    // JUnitDoclet begin method moveToCurrentRow
    // JUnitDoclet end method moveToCurrentRow
  }
  
  /**
  * Tests QueryResultSetTestmoveToInsertRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#moveToInsertRow()
  */
  public void testMoveToInsertRow()
  throws Exception
  {
    // JUnitDoclet begin method moveToInsertRow
    // JUnitDoclet end method moveToInsertRow
  }
  
  /**
  * Tests QueryResultSetTestrefreshRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#refreshRow()
  */
  public void testRefreshRow()
  throws Exception
  {
    // JUnitDoclet begin method refreshRow
    // JUnitDoclet end method refreshRow
  }
  
  /**
  * Tests QueryResultSetTestrelative()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#relative(int)
  */
  public void testRelative()
  throws Exception
  {
    // JUnitDoclet begin method relative
    // JUnitDoclet end method relative
  }
  
  /**
  * Tests QueryResultSetTestrowDeleted()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#rowDeleted()
  */
  public void testRowDeleted()
  throws Exception
  {
    // JUnitDoclet begin method rowDeleted
    // JUnitDoclet end method rowDeleted
  }
  
  /**
  * Tests QueryResultSetTestrowInserted()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#rowInserted()
  */
  public void testRowInserted()
  throws Exception
  {
    // JUnitDoclet begin method rowInserted
    // JUnitDoclet end method rowInserted
  }
  
  /**
  * Tests QueryResultSetTestrowUpdated()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#rowUpdated()
  */
  public void testRowUpdated()
  throws Exception
  {
    // JUnitDoclet begin method rowUpdated
    // JUnitDoclet end method rowUpdated
  }
  
  /**
  * Tests QueryResultSetTestupdateAsciiStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateAsciiStream(int, java.io.InputStream, int)
  */
  public void testUpdateAsciiStream()
  throws Exception
  {
    // JUnitDoclet begin method updateAsciiStream
    // JUnitDoclet end method updateAsciiStream
  }
  
  /**
  * Tests QueryResultSetTestupdateBigDecimal()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateBigDecimal(int, java.math.BigDecimal)
  */
  public void testUpdateBigDecimal()
  throws Exception
  {
    // JUnitDoclet begin method updateBigDecimal
    // JUnitDoclet end method updateBigDecimal
  }
  
  /**
  * Tests QueryResultSetTestupdateBinaryStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateBinaryStream(int, java.io.InputStream, int)
  */
  public void testUpdateBinaryStream()
  throws Exception
  {
    // JUnitDoclet begin method updateBinaryStream
    // JUnitDoclet end method updateBinaryStream
  }
  
  /**
  * Tests QueryResultSetTestupdateBoolean()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateBoolean(int, boolean)
  */
  public void testUpdateBoolean()
  throws Exception
  {
    // JUnitDoclet begin method updateBoolean
    // JUnitDoclet end method updateBoolean
  }
  
  /**
  * Tests QueryResultSetTestupdateByte()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateByte(int, byte)
  */
  public void testUpdateByte()
  throws Exception
  {
    // JUnitDoclet begin method updateByte
    // JUnitDoclet end method updateByte
  }
  
  /**
  * Tests QueryResultSetTestupdateBytes()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateBytes(int, byte[])
  */
  public void testUpdateBytes()
  throws Exception
  {
    // JUnitDoclet begin method updateBytes
    // JUnitDoclet end method updateBytes
  }
  
  /**
  * Tests QueryResultSetTestupdateCharacterStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateCharacterStream(int, java.io.Reader, int)
  */
  public void testUpdateCharacterStream()
  throws Exception
  {
    // JUnitDoclet begin method updateCharacterStream
    // JUnitDoclet end method updateCharacterStream
  }
  
  /**
  * Tests QueryResultSetTestupdateDate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateDate(int, java.sql.Date)
  */
  public void testUpdateDate()
  throws Exception
  {
    // JUnitDoclet begin method updateDate
    // JUnitDoclet end method updateDate
  }
  
  /**
  * Tests QueryResultSetTestupdateDouble()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateDouble(int, double)
  */
  public void testUpdateDouble()
  throws Exception
  {
    // JUnitDoclet begin method updateDouble
    // JUnitDoclet end method updateDouble
  }
  
  /**
  * Tests QueryResultSetTestupdateFloat()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateFloat(int, float)
  */
  public void testUpdateFloat()
  throws Exception
  {
    // JUnitDoclet begin method updateFloat
    // JUnitDoclet end method updateFloat
  }
  
  /**
  * Tests QueryResultSetTestupdateInt()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateInt(int, int)
  */
  public void testUpdateInt()
  throws Exception
  {
    // JUnitDoclet begin method updateInt
    // JUnitDoclet end method updateInt
  }
  
  /**
  * Tests QueryResultSetTestupdateLong()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateLong(int, long)
  */
  public void testUpdateLong()
  throws Exception
  {
    // JUnitDoclet begin method updateLong
    // JUnitDoclet end method updateLong
  }
  
  /**
  * Tests QueryResultSetTestupdateNull()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateNull(int)
  */
  public void testUpdateNull()
  throws Exception
  {
    // JUnitDoclet begin method updateNull
    // JUnitDoclet end method updateNull
  }
  
  /**
  * Tests QueryResultSetTestupdateObject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateObject(int, java.lang.Object, int)
  */
  public void testUpdateObject()
  throws Exception
  {
    // JUnitDoclet begin method updateObject
    // JUnitDoclet end method updateObject
  }
  
  /**
  * Tests QueryResultSetTestupdateRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateRow()
  */
  public void testUpdateRow()
  throws Exception
  {
    // JUnitDoclet begin method updateRow
    // JUnitDoclet end method updateRow
  }
  
  /**
  * Tests QueryResultSetTestupdateShort()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateShort(int, short)
  */
  public void testUpdateShort()
  throws Exception
  {
    // JUnitDoclet begin method updateShort
    // JUnitDoclet end method updateShort
  }
  
  /**
  * Tests QueryResultSetTestupdateString()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateString(int, java.lang.String)
  */
  public void testUpdateString()
  throws Exception
  {
    // JUnitDoclet begin method updateString
    // JUnitDoclet end method updateString
  }
  
  /**
  * Tests QueryResultSetTestupdateTime()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateTime(int, java.sql.Time)
  */
  public void testUpdateTime()
  throws Exception
  {
    // JUnitDoclet begin method updateTime
    // JUnitDoclet end method updateTime
  }
  
  /**
  * Tests QueryResultSetTestupdateTimestamp()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateTimestamp(int, java.sql.Timestamp)
  */
  public void testUpdateTimestamp()
  throws Exception
  {
    // JUnitDoclet begin method updateTimestamp
    // JUnitDoclet end method updateTimestamp
  }
  
  /**
  * Tests QueryResultSetTestgetURL()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#getURL(int)
  */
  public void testGetURL()
  throws Exception
  {
    // JUnitDoclet begin method getURL
    // JUnitDoclet end method getURL
  }
  
  /**
  * Tests QueryResultSetTestupdateRef()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateRef(int, java.sql.Ref)
  */
  public void testUpdateRef()
  throws Exception
  {
    // JUnitDoclet begin method updateRef
    // JUnitDoclet end method updateRef
  }
  
  /**
  * Tests QueryResultSetTestupdateBlob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateBlob(int, java.sql.Blob)
  */
  public void testUpdateBlob()
  throws Exception
  {
    // JUnitDoclet begin method updateBlob
    // JUnitDoclet end method updateBlob
  }
  
  /**
  * Tests QueryResultSetTestupdateClob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateClob(int, java.sql.Clob)
  */
  public void testUpdateClob()
  throws Exception
  {
    // JUnitDoclet begin method updateClob
    // JUnitDoclet end method updateClob
  }
  
  /**
  * Tests QueryResultSetTestupdateArray()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.QueryResultSet#updateArray(int, java.sql.Array)
  */
  public void testUpdateArray()
  throws Exception
  {
    // JUnitDoclet begin method updateArray
    // JUnitDoclet end method updateArray
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
  
  public static void main(String[] args)
  {
    // JUnitDoclet begin method testcase.main
    junit.textui.TestRunner.run(QueryResultSetTest.class);
    // JUnitDoclet end method testcase.main
  }
}
