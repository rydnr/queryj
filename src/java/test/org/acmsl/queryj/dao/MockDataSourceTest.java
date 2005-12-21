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
*              org.acmsl.queryj.dao.
*
*/
package org.acmsl.queryj.dao;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.dao.MockDataSource;
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
* Tests MockDataSourceTest class.
* @see org.acmsl.queryj.dao.MockDataSource
*/
public class MockDataSourceTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.dao.MockDataSource mockdatasource = null;
  // JUnitDoclet end class
  
  /**
  * Creates a MockDataSourceTest with given name.
  * @param name such name.
  */
  public MockDataSourceTest(String name)
  {
    // JUnitDoclet begin method MockDataSourceTest
    super(name);
    // JUnitDoclet end method MockDataSourceTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.dao.MockDataSource createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.dao.MockDataSource(null, null, null, null);
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
    mockdatasource = createInstance();
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
    mockdatasource = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests MockDataSourceTestgetDriverClassName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getDriverClassName()
  */
  public void testGetDriverClassName()
  throws Exception
  {
    // JUnitDoclet begin method getDriverClassName
    // JUnitDoclet end method getDriverClassName
  }
  
  /**
  * Tests MockDataSourceTestgetURL()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getURL()
  */
  public void testGetURL()
  throws Exception
  {
    // JUnitDoclet begin method getURL
    // JUnitDoclet end method getURL
  }
  
  /**
  * Tests MockDataSourceTestgetUserName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getUserName()
  */
  public void testGetUserName()
  throws Exception
  {
    // JUnitDoclet begin method getUserName
    // JUnitDoclet end method getUserName
  }
  
  /**
  * Tests MockDataSourceTestgetPassword()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getPassword()
  */
  public void testGetPassword()
  throws Exception
  {
    // JUnitDoclet begin method getPassword
    // JUnitDoclet end method getPassword
  }
  
  /**
  * Tests MockDataSourceTestsetAutoCommit()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#setAutoCommit(boolean)
  */
  public void testSetAutoCommit()
  throws Exception
  {
    // JUnitDoclet begin method setAutoCommit
    // JUnitDoclet end method setAutoCommit
  }
  
  /**
  * Tests MockDataSourceTestisAutoCommitEnabled()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#isAutoCommitEnabled()
  */
  public void testIsAutoCommitEnabled()
  throws Exception
  {
    // JUnitDoclet begin method isAutoCommitEnabled
    // JUnitDoclet end method isAutoCommitEnabled
  }
  
  /**
  * Tests MockDataSourceTestincreaseOpenedConnections()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#increaseOpenedConnections()
  */
  public void testIncreaseOpenedConnections()
  throws Exception
  {
    // JUnitDoclet begin method increaseOpenedConnections
    // JUnitDoclet end method increaseOpenedConnections
  }
  
  /**
  * Tests MockDataSourceTestgetOpenedConnections()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getOpenedConnections()
  */
  public void testGetOpenedConnections()
  throws Exception
  {
    // JUnitDoclet begin method getOpenedConnections
    // JUnitDoclet end method getOpenedConnections
  }
  
  /**
  * Tests MockDataSourceTestincreaseCloseMethodCalls()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#increaseCloseMethodCalls()
  */
  public void testIncreaseCloseMethodCalls()
  throws Exception
  {
    // JUnitDoclet begin method increaseCloseMethodCalls
    // JUnitDoclet end method increaseCloseMethodCalls
  }
  
  /**
  * Tests MockDataSourceTestgetCloseMethodCalls()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getCloseMethodCalls()
  */
  public void testGetCloseMethodCalls()
  throws Exception
  {
    // JUnitDoclet begin method getCloseMethodCalls
    // JUnitDoclet end method getCloseMethodCalls
  }
  
  /**
  * Tests MockDataSourceTestgetCommitMethodCalls()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getCommitMethodCalls()
  */
  public void testGetCommitMethodCalls()
  throws Exception
  {
    // JUnitDoclet begin method getCommitMethodCalls
    // JUnitDoclet end method getCommitMethodCalls
  }
  
  /**
  * Tests MockDataSourceTestincreaseRollbackMethodCalls()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#increaseRollbackMethodCalls()
  */
  public void testIncreaseRollbackMethodCalls()
  throws Exception
  {
    // JUnitDoclet begin method increaseRollbackMethodCalls
    // JUnitDoclet end method increaseRollbackMethodCalls
  }
  
  /**
  * Tests MockDataSourceTestgetRollbackMethodCalls()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getRollbackMethodCalls()
  */
  public void testGetRollbackMethodCalls()
  throws Exception
  {
    // JUnitDoclet begin method getRollbackMethodCalls
    // JUnitDoclet end method getRollbackMethodCalls
  }
  
  /**
  * Tests MockDataSourceTestaddException()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#addException(java.lang.Exception)
  */
  public void testAddException()
  throws Exception
  {
    // JUnitDoclet begin method addException
    // JUnitDoclet end method addException
  }
  
  /**
  * Tests MockDataSourceTestexceptionIterator()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#exceptionIterator()
  */
  public void testExceptionIterator()
  throws Exception
  {
    // JUnitDoclet begin method exceptionIterator
    // JUnitDoclet end method exceptionIterator
  }
  
  /**
  * Tests MockDataSourceTestgetConnection()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.dao.MockDataSource#getConnection()
  */
  public void testGetConnection()
  throws Exception
  {
    // JUnitDoclet begin method getConnection
    // JUnitDoclet end method getConnection
  }
  
  /**
  * Tests MockDataSourceTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetLogWriter()
  throws Exception
  {
    // JUnitDoclet begin method setLogWriter getLogWriter
    java.io.PrintWriter[] t_aTests = {new java.io.PrintWriter(System.out), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockdatasource.setLogWriter(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockdatasource.getLogWriter());
    }
    // JUnitDoclet end method setLogWriter getLogWriter
  }
  
  /**
  * Tests MockDataSourceTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetLoginTimeout()
  throws Exception
  {
    // JUnitDoclet begin method setLoginTimeout getLoginTimeout
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      mockdatasource.setLoginTimeout(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      mockdatasource.getLoginTimeout());
    }
    // JUnitDoclet end method setLoginTimeout getLoginTimeout
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
    junit.textui.TestRunner.run(MockDataSourceTest.class);
    // JUnitDoclet end method testcase.main
  }
}
