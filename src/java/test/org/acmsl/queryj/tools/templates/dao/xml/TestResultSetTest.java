/*
                      Project tests

Copyright (C) 2003  Jose San Leandro Armend?riz
jsanleandro@yahoo.es
chousz@yahoo.com

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
Contact info: jsr000@terra.es
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
*              unittests.org.acmsl.queryj.tools.templates.dao.xml.
*
* Last modified by: $Author$ at $Date$
*
* File version: $Revision$
*
* Project version: $Name$
*
* $Id$
*/
package unittests.org.acmsl.queryj.tools.templates.dao.xml;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.templates.dao.xml.TestResultSet;
// JUnitDoclet end import

/*
* Importing JUnit classes.
*/
import junit.framework.TestCase;

/*
This file is part of  JUnitDoclet, a project to generate basic
test cases  from source code and  helping to keep them in sync
during refactoring.

Copyright (C) 2002  ObjectFab GmbH  (http://www.objectfab.de/)

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
* Tests TestResultSetTest class.
* @version $Revision$
* @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet
*/
public class TestResultSetTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.templates.dao.xml.TestResultSet testresultset = null;
  // JUnitDoclet end class
  
  /**
  * Creates a TestResultSetTest with given name.
  * @param name such name.
  */
  public TestResultSetTest(String name)
  {
    // JUnitDoclet begin method TestResultSetTest
    super(name);
    // JUnitDoclet end method TestResultSetTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.templates.dao.xml.TestResultSet createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.templates.dao.xml.TestResultSet();
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
    testresultset = createInstance();
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
    testresultset = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests TestResultSetTestgetObject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getObject(int)
  */
  public void testGetObject()
  throws Exception
  {
    // JUnitDoclet begin method getObject
    // JUnitDoclet end method getObject
  }
  
  /**
  * Tests TestResultSetTestgetBoolean()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getBoolean(int)
  */
  public void testGetBoolean()
  throws Exception
  {
    // JUnitDoclet begin method getBoolean
    // JUnitDoclet end method getBoolean
  }
  
  /**
  * Tests TestResultSetTestgetByte()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getByte(int)
  */
  public void testGetByte()
  throws Exception
  {
    // JUnitDoclet begin method getByte
    // JUnitDoclet end method getByte
  }
  
  /**
  * Tests TestResultSetTestgetShort()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getShort(int)
  */
  public void testGetShort()
  throws Exception
  {
    // JUnitDoclet begin method getShort
    // JUnitDoclet end method getShort
  }
  
  /**
  * Tests TestResultSetTestgetInt()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getInt(int)
  */
  public void testGetInt()
  throws Exception
  {
    // JUnitDoclet begin method getInt
    // JUnitDoclet end method getInt
  }
  
  /**
  * Tests TestResultSetTestgetLong()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getLong(int)
  */
  public void testGetLong()
  throws Exception
  {
    // JUnitDoclet begin method getLong
    // JUnitDoclet end method getLong
  }
  
  /**
  * Tests TestResultSetTestgetFloat()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getFloat(int)
  */
  public void testGetFloat()
  throws Exception
  {
    // JUnitDoclet begin method getFloat
    // JUnitDoclet end method getFloat
  }
  
  /**
  * Tests TestResultSetTestgetDouble()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getDouble(int)
  */
  public void testGetDouble()
  throws Exception
  {
    // JUnitDoclet begin method getDouble
    // JUnitDoclet end method getDouble
  }
  
  /**
  * Tests TestResultSetTestgetBytes()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getBytes(int)
  */
  public void testGetBytes()
  throws Exception
  {
    // JUnitDoclet begin method getBytes
    // JUnitDoclet end method getBytes
  }
  
  /**
  * Tests TestResultSetTestgetArray()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getArray(int)
  */
  public void testGetArray()
  throws Exception
  {
    // JUnitDoclet begin method getArray
    // JUnitDoclet end method getArray
  }
  
  /**
  * Tests TestResultSetTestgetURL()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getURL(int)
  */
  public void testGetURL()
  throws Exception
  {
    // JUnitDoclet begin method getURL
    // JUnitDoclet end method getURL
  }
  
  /**
  * Tests TestResultSetTestnext()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#next()
  */
  public void testNext()
  throws Exception
  {
    // JUnitDoclet begin method next
    // JUnitDoclet end method next
  }
  
  /**
  * Tests TestResultSetTestgetType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getType()
  */
  public void testGetType()
  throws Exception
  {
    // JUnitDoclet begin method getType
    // JUnitDoclet end method getType
  }
  
  /**
  * Tests TestResultSetTestprevious()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#previous()
  */
  public void testPrevious()
  throws Exception
  {
    // JUnitDoclet begin method previous
    // JUnitDoclet end method previous
  }
  
  /**
  * Tests TestResultSetTestclose()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#close()
  */
  public void testClose()
  throws Exception
  {
    // JUnitDoclet begin method close
    // JUnitDoclet end method close
  }
  
  /**
  * Tests TestResultSetTestgetRef()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getRef(int)
  */
  public void testGetRef()
  throws Exception
  {
    // JUnitDoclet begin method getRef
    // JUnitDoclet end method getRef
  }
  
  /**
  * Tests TestResultSetTestgetDate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getDate(int)
  */
  public void testGetDate()
  throws Exception
  {
    // JUnitDoclet begin method getDate
    // JUnitDoclet end method getDate
  }
  
  /**
  * Tests TestResultSetTestgetTime()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getTime(int)
  */
  public void testGetTime()
  throws Exception
  {
    // JUnitDoclet begin method getTime
    // JUnitDoclet end method getTime
  }
  
  /**
  * Tests TestResultSetTestfirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#first()
  */
  public void testFirst()
  throws Exception
  {
    // JUnitDoclet begin method first
    // JUnitDoclet end method first
  }
  
  /**
  * Tests TestResultSetTestclearWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#clearWarnings()
  */
  public void testClearWarnings()
  throws Exception
  {
    // JUnitDoclet begin method clearWarnings
    // JUnitDoclet end method clearWarnings
  }
  
  /**
  * Tests TestResultSetTestgetWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getWarnings()
  */
  public void testGetWarnings()
  throws Exception
  {
    // JUnitDoclet begin method getWarnings
    // JUnitDoclet end method getWarnings
  }
  
  /**
  * Tests TestResultSetTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchDirection()
  throws Exception
  {
    // JUnitDoclet begin method setFetchDirection getFetchDirection
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      testresultset.setFetchDirection(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      testresultset.getFetchDirection());
    }
    // JUnitDoclet end method setFetchDirection getFetchDirection
  }
  
  /**
  * Tests TestResultSetTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchSize()
  throws Exception
  {
    // JUnitDoclet begin method setFetchSize getFetchSize
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      testresultset.setFetchSize(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      testresultset.getFetchSize());
    }
    // JUnitDoclet end method setFetchSize getFetchSize
  }
  
  /**
  * Tests TestResultSetTestgetMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getMetaData()
  */
  public void testGetMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getMetaData
    // JUnitDoclet end method getMetaData
  }
  
  /**
  * Tests TestResultSetTestabsolute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#absolute(int)
  */
  public void testAbsolute()
  throws Exception
  {
    // JUnitDoclet begin method absolute
    // JUnitDoclet end method absolute
  }
  
  /**
  * Tests TestResultSetTestafterLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#afterLast()
  */
  public void testAfterLast()
  throws Exception
  {
    // JUnitDoclet begin method afterLast
    // JUnitDoclet end method afterLast
  }
  
  /**
  * Tests TestResultSetTestbeforeFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#beforeFirst()
  */
  public void testBeforeFirst()
  throws Exception
  {
    // JUnitDoclet begin method beforeFirst
    // JUnitDoclet end method beforeFirst
  }
  
  /**
  * Tests TestResultSetTestcancelRowUpdates()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#cancelRowUpdates()
  */
  public void testCancelRowUpdates()
  throws Exception
  {
    // JUnitDoclet begin method cancelRowUpdates
    // JUnitDoclet end method cancelRowUpdates
  }
  
  /**
  * Tests TestResultSetTestdeleteRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#deleteRow()
  */
  public void testDeleteRow()
  throws Exception
  {
    // JUnitDoclet begin method deleteRow
    // JUnitDoclet end method deleteRow
  }
  
  /**
  * Tests TestResultSetTestfindColumn()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#findColumn(java.lang.String)
  */
  public void testFindColumn()
  throws Exception
  {
    // JUnitDoclet begin method findColumn
    // JUnitDoclet end method findColumn
  }
  
  /**
  * Tests TestResultSetTestgetAsciiStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getAsciiStream(int)
  */
  public void testGetAsciiStream()
  throws Exception
  {
    // JUnitDoclet begin method getAsciiStream
    // JUnitDoclet end method getAsciiStream
  }
  
  /**
  * Tests TestResultSetTestgetBigDecimal()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getBigDecimal(int)
  */
  public void testGetBigDecimal()
  throws Exception
  {
    // JUnitDoclet begin method getBigDecimal
    // JUnitDoclet end method getBigDecimal
  }
  
  /**
  * Tests TestResultSetTestgetBinaryStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getBinaryStream(int)
  */
  public void testGetBinaryStream()
  throws Exception
  {
    // JUnitDoclet begin method getBinaryStream
    // JUnitDoclet end method getBinaryStream
  }
  
  /**
  * Tests TestResultSetTestgetBlob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getBlob(int)
  */
  public void testGetBlob()
  throws Exception
  {
    // JUnitDoclet begin method getBlob
    // JUnitDoclet end method getBlob
  }
  
  /**
  * Tests TestResultSetTestgetCharacterStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getCharacterStream(int)
  */
  public void testGetCharacterStream()
  throws Exception
  {
    // JUnitDoclet begin method getCharacterStream
    // JUnitDoclet end method getCharacterStream
  }
  
  /**
  * Tests TestResultSetTestgetClob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getClob(int)
  */
  public void testGetClob()
  throws Exception
  {
    // JUnitDoclet begin method getClob
    // JUnitDoclet end method getClob
  }
  
  /**
  * Tests TestResultSetTestgetConcurrency()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getConcurrency()
  */
  public void testGetConcurrency()
  throws Exception
  {
    // JUnitDoclet begin method getConcurrency
    // JUnitDoclet end method getConcurrency
  }
  
  /**
  * Tests TestResultSetTestgetCursorName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getCursorName()
  */
  public void testGetCursorName()
  throws Exception
  {
    // JUnitDoclet begin method getCursorName
    // JUnitDoclet end method getCursorName
  }
  
  /**
  * Tests TestResultSetTestgetRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getRow()
  */
  public void testGetRow()
  throws Exception
  {
    // JUnitDoclet begin method getRow
    // JUnitDoclet end method getRow
  }
  
  /**
  * Tests TestResultSetTestgetStatement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getStatement()
  */
  public void testGetStatement()
  throws Exception
  {
    // JUnitDoclet begin method getStatement
    // JUnitDoclet end method getStatement
  }
  
  /**
  * Tests TestResultSetTestgetString()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getString(int)
  */
  public void testGetString()
  throws Exception
  {
    // JUnitDoclet begin method getString
    // JUnitDoclet end method getString
  }
  
  /**
  * Tests TestResultSetTestgetTimestamp()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getTimestamp(int)
  */
  public void testGetTimestamp()
  throws Exception
  {
    // JUnitDoclet begin method getTimestamp
    // JUnitDoclet end method getTimestamp
  }
  
  /**
  * Tests TestResultSetTestgetUnicodeStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#getUnicodeStream(int)
  */
  public void testGetUnicodeStream()
  throws Exception
  {
    // JUnitDoclet begin method getUnicodeStream
    // JUnitDoclet end method getUnicodeStream
  }
  
  /**
  * Tests TestResultSetTestinsertRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#insertRow()
  */
  public void testInsertRow()
  throws Exception
  {
    // JUnitDoclet begin method insertRow
    // JUnitDoclet end method insertRow
  }
  
  /**
  * Tests TestResultSetTestisAfterLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#isAfterLast()
  */
  public void testIsAfterLast()
  throws Exception
  {
    // JUnitDoclet begin method isAfterLast
    // JUnitDoclet end method isAfterLast
  }
  
  /**
  * Tests TestResultSetTestisBeforeFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#isBeforeFirst()
  */
  public void testIsBeforeFirst()
  throws Exception
  {
    // JUnitDoclet begin method isBeforeFirst
    // JUnitDoclet end method isBeforeFirst
  }
  
  /**
  * Tests TestResultSetTestisFirst()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#isFirst()
  */
  public void testIsFirst()
  throws Exception
  {
    // JUnitDoclet begin method isFirst
    // JUnitDoclet end method isFirst
  }
  
  /**
  * Tests TestResultSetTestisLast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#isLast()
  */
  public void testIsLast()
  throws Exception
  {
    // JUnitDoclet begin method isLast
    // JUnitDoclet end method isLast
  }
  
  /**
  * Tests TestResultSetTestlast()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#last()
  */
  public void testLast()
  throws Exception
  {
    // JUnitDoclet begin method last
    // JUnitDoclet end method last
  }
  
  /**
  * Tests TestResultSetTestmoveToCurrentRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#moveToCurrentRow()
  */
  public void testMoveToCurrentRow()
  throws Exception
  {
    // JUnitDoclet begin method moveToCurrentRow
    // JUnitDoclet end method moveToCurrentRow
  }
  
  /**
  * Tests TestResultSetTestmoveToInsertRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#moveToInsertRow()
  */
  public void testMoveToInsertRow()
  throws Exception
  {
    // JUnitDoclet begin method moveToInsertRow
    // JUnitDoclet end method moveToInsertRow
  }
  
  /**
  * Tests TestResultSetTestrefreshRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#refreshRow()
  */
  public void testRefreshRow()
  throws Exception
  {
    // JUnitDoclet begin method refreshRow
    // JUnitDoclet end method refreshRow
  }
  
  /**
  * Tests TestResultSetTestrelative()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#relative(int)
  */
  public void testRelative()
  throws Exception
  {
    // JUnitDoclet begin method relative
    // JUnitDoclet end method relative
  }
  
  /**
  * Tests TestResultSetTestrowDeleted()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#rowDeleted()
  */
  public void testRowDeleted()
  throws Exception
  {
    // JUnitDoclet begin method rowDeleted
    // JUnitDoclet end method rowDeleted
  }
  
  /**
  * Tests TestResultSetTestrowInserted()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#rowInserted()
  */
  public void testRowInserted()
  throws Exception
  {
    // JUnitDoclet begin method rowInserted
    // JUnitDoclet end method rowInserted
  }
  
  /**
  * Tests TestResultSetTestrowUpdated()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#rowUpdated()
  */
  public void testRowUpdated()
  throws Exception
  {
    // JUnitDoclet begin method rowUpdated
    // JUnitDoclet end method rowUpdated
  }
  
  /**
  * Tests TestResultSetTestupdateArray()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateArray(int, java.sql.Array)
  */
  public void testUpdateArray()
  throws Exception
  {
    // JUnitDoclet begin method updateArray
    // JUnitDoclet end method updateArray
  }
  
  /**
  * Tests TestResultSetTestupdateAsciiStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateAsciiStream(int, java.io.InputStream, int)
  */
  public void testUpdateAsciiStream()
  throws Exception
  {
    // JUnitDoclet begin method updateAsciiStream
    // JUnitDoclet end method updateAsciiStream
  }
  
  /**
  * Tests TestResultSetTestupdateBigDecimal()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateBigDecimal(int, java.math.BigDecimal)
  */
  public void testUpdateBigDecimal()
  throws Exception
  {
    // JUnitDoclet begin method updateBigDecimal
    // JUnitDoclet end method updateBigDecimal
  }
  
  /**
  * Tests TestResultSetTestupdateBinaryStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateBinaryStream(int, java.io.InputStream, int)
  */
  public void testUpdateBinaryStream()
  throws Exception
  {
    // JUnitDoclet begin method updateBinaryStream
    // JUnitDoclet end method updateBinaryStream
  }
  
  /**
  * Tests TestResultSetTestupdateBlob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateBlob(int, java.sql.Blob)
  */
  public void testUpdateBlob()
  throws Exception
  {
    // JUnitDoclet begin method updateBlob
    // JUnitDoclet end method updateBlob
  }
  
  /**
  * Tests TestResultSetTestupdateBoolean()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateBoolean(int, boolean)
  */
  public void testUpdateBoolean()
  throws Exception
  {
    // JUnitDoclet begin method updateBoolean
    // JUnitDoclet end method updateBoolean
  }
  
  /**
  * Tests TestResultSetTestupdateByte()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateByte(int, byte)
  */
  public void testUpdateByte()
  throws Exception
  {
    // JUnitDoclet begin method updateByte
    // JUnitDoclet end method updateByte
  }
  
  /**
  * Tests TestResultSetTestupdateBytes()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateBytes(int, byte[])
  */
  public void testUpdateBytes()
  throws Exception
  {
    // JUnitDoclet begin method updateBytes
    // JUnitDoclet end method updateBytes
  }
  
  /**
  * Tests TestResultSetTestupdateCharacterStream()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateCharacterStream(int, java.io.Reader, int)
  */
  public void testUpdateCharacterStream()
  throws Exception
  {
    // JUnitDoclet begin method updateCharacterStream
    // JUnitDoclet end method updateCharacterStream
  }
  
  /**
  * Tests TestResultSetTestupdateClob()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateClob(int, java.sql.Clob)
  */
  public void testUpdateClob()
  throws Exception
  {
    // JUnitDoclet begin method updateClob
    // JUnitDoclet end method updateClob
  }
  
  /**
  * Tests TestResultSetTestupdateDate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateDate(int, java.sql.Date)
  */
  public void testUpdateDate()
  throws Exception
  {
    // JUnitDoclet begin method updateDate
    // JUnitDoclet end method updateDate
  }
  
  /**
  * Tests TestResultSetTestupdateDouble()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateDouble(int, double)
  */
  public void testUpdateDouble()
  throws Exception
  {
    // JUnitDoclet begin method updateDouble
    // JUnitDoclet end method updateDouble
  }
  
  /**
  * Tests TestResultSetTestupdateFloat()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateFloat(int, float)
  */
  public void testUpdateFloat()
  throws Exception
  {
    // JUnitDoclet begin method updateFloat
    // JUnitDoclet end method updateFloat
  }
  
  /**
  * Tests TestResultSetTestupdateInt()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateInt(int, int)
  */
  public void testUpdateInt()
  throws Exception
  {
    // JUnitDoclet begin method updateInt
    // JUnitDoclet end method updateInt
  }
  
  /**
  * Tests TestResultSetTestupdateLong()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateLong(int, long)
  */
  public void testUpdateLong()
  throws Exception
  {
    // JUnitDoclet begin method updateLong
    // JUnitDoclet end method updateLong
  }
  
  /**
  * Tests TestResultSetTestupdateNull()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateNull(int)
  */
  public void testUpdateNull()
  throws Exception
  {
    // JUnitDoclet begin method updateNull
    // JUnitDoclet end method updateNull
  }
  
  /**
  * Tests TestResultSetTestupdateObject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateObject(int, java.lang.Object)
  */
  public void testUpdateObject()
  throws Exception
  {
    // JUnitDoclet begin method updateObject
    // JUnitDoclet end method updateObject
  }
  
  /**
  * Tests TestResultSetTestupdateRef()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateRef(int, java.sql.Ref)
  */
  public void testUpdateRef()
  throws Exception
  {
    // JUnitDoclet begin method updateRef
    // JUnitDoclet end method updateRef
  }
  
  /**
  * Tests TestResultSetTestupdateRow()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateRow()
  */
  public void testUpdateRow()
  throws Exception
  {
    // JUnitDoclet begin method updateRow
    // JUnitDoclet end method updateRow
  }
  
  /**
  * Tests TestResultSetTestupdateShort()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateShort(int, short)
  */
  public void testUpdateShort()
  throws Exception
  {
    // JUnitDoclet begin method updateShort
    // JUnitDoclet end method updateShort
  }
  
  /**
  * Tests TestResultSetTestupdateString()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateString(int, java.lang.String)
  */
  public void testUpdateString()
  throws Exception
  {
    // JUnitDoclet begin method updateString
    // JUnitDoclet end method updateString
  }
  
  /**
  * Tests TestResultSetTestupdateTime()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateTime(int, java.sql.Time)
  */
  public void testUpdateTime()
  throws Exception
  {
    // JUnitDoclet begin method updateTime
    // JUnitDoclet end method updateTime
  }
  
  /**
  * Tests TestResultSetTestupdateTimestamp()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#updateTimestamp(int, java.sql.Timestamp)
  */
  public void testUpdateTimestamp()
  throws Exception
  {
    // JUnitDoclet begin method updateTimestamp
    // JUnitDoclet end method updateTimestamp
  }
  
  /**
  * Tests TestResultSetTestwasNull()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestResultSet#wasNull()
  */
  public void testWasNull()
  throws Exception
  {
    // JUnitDoclet begin method wasNull
    // JUnitDoclet end method wasNull
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
    junit.textui.TestRunner.run(TestResultSetTest.class);
    // JUnitDoclet end method testcase.main
  }
}
