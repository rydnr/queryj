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
* Filename: MockConnectionTest.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.dao.
*
*/
package org.acmsl.queryj.dao;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.dao.MockConnection;
// JUnitDoclet end import

/*
* Importing JUnit classes.
*/
import junit.framework.TestCase;

/*
This file is part of  JUnitDoclet, a project to generate basic
test cases  from source code and  helping to keep them in sync
during refactoring.

Copyright (C) 2002-2007  ObjectFab GmbH  (http://www.objectfab.de/)

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
* Tests MockConnectionTest class.
* @see org.acmsl.queryj.dao.MockConnection
*/
public class MockConnectionTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.dao.MockConnection mockconnection = null;
  // JUnitDoclet end class
  
  /**
  * Creates a MockConnectionTest with given name.
  * @param name such name.
  */
  public MockConnectionTest(String name)
  {
    // JUnitDoclet begin method MockConnectionTest
    super(name);
    // JUnitDoclet end method MockConnectionTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.dao.MockConnection createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.dao.MockConnection(null, null);
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
    mockconnection = createInstance();
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
    mockconnection = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests MockConnectionTestgetConnection()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#getConnection()
  */
  public void testGetConnection()
  throws Exception
  {
    // JUnitDoclet begin method getConnection
    // JUnitDoclet end method getConnection
  }
  
  /**
  * Tests MockConnectionTestgetMockDataSource()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#getMockDataSource()
  */
  public void testGetMockDataSource()
  throws Exception
  {
    // JUnitDoclet begin method getMockDataSource
    // JUnitDoclet end method getMockDataSource
  }
  
  /**
  * Tests MockConnectionTestclearWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#clearWarnings()
  */
  public void testClearWarnings()
  throws Exception
  {
    // JUnitDoclet begin method clearWarnings
    // JUnitDoclet end method clearWarnings
  }
  
  /**
  * Tests MockConnectionTestclose()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#close()
  */
  public void testClose()
  throws Exception
  {
    // JUnitDoclet begin method close
    // JUnitDoclet end method close
  }
  
  /**
  * Tests MockConnectionTestcommit()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#commit()
  */
  public void testCommit()
  throws Exception
  {
    // JUnitDoclet begin method commit
    // JUnitDoclet end method commit
  }
  
  /**
  * Tests MockConnectionTestcreateStatement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#createStatement()
  */
  public void testCreateStatement()
  throws Exception
  {
    // JUnitDoclet begin method createStatement
    // JUnitDoclet end method createStatement
  }
  
  /**
  * Tests MockConnectionTestgetMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#getMetaData()
  */
  public void testGetMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getMetaData
    // JUnitDoclet end method getMetaData
  }
  
  /**
  * Tests MockConnectionTestgetWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#getWarnings()
  */
  public void testGetWarnings()
  throws Exception
  {
    // JUnitDoclet begin method getWarnings
    // JUnitDoclet end method getWarnings
  }
  
  /**
  * Tests MockConnectionTestisClosed()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#isClosed()
  */
  public void testIsClosed()
  throws Exception
  {
    // JUnitDoclet begin method isClosed
    // JUnitDoclet end method isClosed
  }
  
  /**
  * Tests MockConnectionTestnativeSQL()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#nativeSQL(java.lang.String)
  */
  public void testNativeSQL()
  throws Exception
  {
    // JUnitDoclet begin method nativeSQL
    // JUnitDoclet end method nativeSQL
  }
  
  /**
  * Tests MockConnectionTestprepareCall()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#prepareCall(java.lang.String)
  */
  public void testPrepareCall()
  throws Exception
  {
    // JUnitDoclet begin method prepareCall
    // JUnitDoclet end method prepareCall
  }
  
  /**
  * Tests MockConnectionTestprepareStatement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#prepareStatement(java.lang.String)
  */
  public void testPrepareStatement()
  throws Exception
  {
    // JUnitDoclet begin method prepareStatement
    // JUnitDoclet end method prepareStatement
  }
  
  /**
  * Tests MockConnectionTestrollback()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#rollback()
  */
  public void testRollback()
  throws Exception
  {
    // JUnitDoclet begin method rollback
    // JUnitDoclet end method rollback
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetAutoCommit()
  throws Exception
  {
    // JUnitDoclet begin method setAutoCommit getAutoCommit
    boolean[] t_aTests = {true, false};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setAutoCommit(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.getAutoCommit());
    }
    // JUnitDoclet end method setAutoCommit getAutoCommit
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetCatalog()
  throws Exception
  {
    // JUnitDoclet begin method setCatalog getCatalog
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setCatalog(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.getCatalog());
    }
    // JUnitDoclet end method setCatalog getCatalog
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetIsReadOnly()
  throws Exception
  {
    // JUnitDoclet begin method setReadOnly isReadOnly
    boolean[] t_aTests = {true, false};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setReadOnly(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.isReadOnly());
    }
    // JUnitDoclet end method setReadOnly isReadOnly
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetTransactionIsolation()
  throws Exception
  {
    // JUnitDoclet begin method setTransactionIsolation getTransactionIsolation
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setTransactionIsolation(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.getTransactionIsolation());
    }
    // JUnitDoclet end method setTransactionIsolation getTransactionIsolation
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetTypeMap()
  throws Exception
  {
    // JUnitDoclet begin method setTypeMap getTypeMap
    java.util.Map[] t_aTests = {new java.util.HashMap(), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setTypeMap(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.getTypeMap());
    }
    // JUnitDoclet end method setTypeMap getTypeMap
  }
  
  /**
  * Tests MockConnectionTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetHoldability()
  throws Exception
  {
    // JUnitDoclet begin method setHoldability getHoldability
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockconnection.setHoldability(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockconnection.getHoldability());
    }
    // JUnitDoclet end method setHoldability getHoldability
  }
  
  /**
  * Tests MockConnectionTestsetSavepoint()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#setSavepoint()
  */
  public void testSetSavepoint()
  throws Exception
  {
    // JUnitDoclet begin method setSavepoint
    // JUnitDoclet end method setSavepoint
  }
  
  /**
  * Tests MockConnectionTestreleaseSavepoint()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockConnection#releaseSavepoint(java.sql.Savepoint)
  */
  public void testReleaseSavepoint()
  throws Exception
  {
    // JUnitDoclet begin method releaseSavepoint
    // JUnitDoclet end method releaseSavepoint
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
    junit.textui.TestRunner.run(MockConnectionTest.class);
    // JUnitDoclet end method testcase.main
  }
}
