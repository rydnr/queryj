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
import org.acmsl.queryj.tools.templates.dao.xml.TestStatement;
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
* Tests TestStatementTest class.
* @version $Revision$
* @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement
*/
public class TestStatementTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.templates.dao.xml.TestStatement teststatement = null;
  // JUnitDoclet end class
  
  /**
  * Creates a TestStatementTest with given name.
  * @param name such name.
  */
  public TestStatementTest(String name)
  {
    // JUnitDoclet begin method TestStatementTest
    super(name);
    // JUnitDoclet end method TestStatementTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.templates.dao.xml.TestStatement createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.templates.dao.xml.TestStatement();
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
    teststatement = createInstance();
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
    teststatement = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests TestStatementTestclose()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#close()
  */
  public void testClose()
  throws Exception
  {
    // JUnitDoclet begin method close
    // JUnitDoclet end method close
  }
  
  /**
  * Tests TestStatementTestexecute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#execute(java.lang.String)
  */
  public void testExecute()
  throws Exception
  {
    // JUnitDoclet begin method execute
    // JUnitDoclet end method execute
  }
  
  /**
  * Tests TestStatementTestgetConnection()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getConnection()
  */
  public void testGetConnection()
  throws Exception
  {
    // JUnitDoclet begin method getConnection
    // JUnitDoclet end method getConnection
  }
  
  /**
  * Tests TestStatementTestaddBatch()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#addBatch(java.lang.String)
  */
  public void testAddBatch()
  throws Exception
  {
    // JUnitDoclet begin method addBatch
    // JUnitDoclet end method addBatch
  }
  
  /**
  * Tests TestStatementTestcancel()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#cancel()
  */
  public void testCancel()
  throws Exception
  {
    // JUnitDoclet begin method cancel
    // JUnitDoclet end method cancel
  }
  
  /**
  * Tests TestStatementTestclearBatch()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#clearBatch()
  */
  public void testClearBatch()
  throws Exception
  {
    // JUnitDoclet begin method clearBatch
    // JUnitDoclet end method clearBatch
  }
  
  /**
  * Tests TestStatementTestclearWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#clearWarnings()
  */
  public void testClearWarnings()
  throws Exception
  {
    // JUnitDoclet begin method clearWarnings
    // JUnitDoclet end method clearWarnings
  }
  
  /**
  * Tests TestStatementTestexecuteBatch()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#executeBatch()
  */
  public void testExecuteBatch()
  throws Exception
  {
    // JUnitDoclet begin method executeBatch
    // JUnitDoclet end method executeBatch
  }
  
  /**
  * Tests TestStatementTestexecuteQuery()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#executeQuery(java.lang.String)
  */
  public void testExecuteQuery()
  throws Exception
  {
    // JUnitDoclet begin method executeQuery
    // JUnitDoclet end method executeQuery
  }
  
  /**
  * Tests TestStatementTestexecuteUpdate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#executeUpdate(java.lang.String)
  */
  public void testExecuteUpdate()
  throws Exception
  {
    // JUnitDoclet begin method executeUpdate
    // JUnitDoclet end method executeUpdate
  }
  
  /**
  * Tests TestStatementTestgetGeneratedKeys()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getGeneratedKeys()
  */
  public void testGetGeneratedKeys()
  throws Exception
  {
    // JUnitDoclet begin method getGeneratedKeys
    // JUnitDoclet end method getGeneratedKeys
  }
  
  /**
  * Tests TestStatementTestgetMoreResults()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getMoreResults()
  */
  public void testGetMoreResults()
  throws Exception
  {
    // JUnitDoclet begin method getMoreResults
    // JUnitDoclet end method getMoreResults
  }
  
  /**
  * Tests TestStatementTestgetResultSet()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getResultSet()
  */
  public void testGetResultSet()
  throws Exception
  {
    // JUnitDoclet begin method getResultSet
    // JUnitDoclet end method getResultSet
  }
  
  /**
  * Tests TestStatementTestgetResultSetConcurrency()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getResultSetConcurrency()
  */
  public void testGetResultSetConcurrency()
  throws Exception
  {
    // JUnitDoclet begin method getResultSetConcurrency
    // JUnitDoclet end method getResultSetConcurrency
  }
  
  /**
  * Tests TestStatementTestgetResultSetHoldability()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getResultSetHoldability()
  */
  public void testGetResultSetHoldability()
  throws Exception
  {
    // JUnitDoclet begin method getResultSetHoldability
    // JUnitDoclet end method getResultSetHoldability
  }
  
  /**
  * Tests TestStatementTestgetResultSetType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getResultSetType()
  */
  public void testGetResultSetType()
  throws Exception
  {
    // JUnitDoclet begin method getResultSetType
    // JUnitDoclet end method getResultSetType
  }
  
  /**
  * Tests TestStatementTestgetUpdateCount()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getUpdateCount()
  */
  public void testGetUpdateCount()
  throws Exception
  {
    // JUnitDoclet begin method getUpdateCount
    // JUnitDoclet end method getUpdateCount
  }
  
  /**
  * Tests TestStatementTestgetWarnings()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#getWarnings()
  */
  public void testGetWarnings()
  throws Exception
  {
    // JUnitDoclet begin method getWarnings
    // JUnitDoclet end method getWarnings
  }
  
  /**
  * Tests TestStatementTestsetCursorName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#setCursorName(java.lang.String)
  */
  public void testSetCursorName()
  throws Exception
  {
    // JUnitDoclet begin method setCursorName
    // JUnitDoclet end method setCursorName
  }
  
  /**
  * Tests TestStatementTestsetEscapeProcessing()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.dao.xml.TestStatement#setEscapeProcessing(boolean)
  */
  public void testSetEscapeProcessing()
  throws Exception
  {
    // JUnitDoclet begin method setEscapeProcessing
    // JUnitDoclet end method setEscapeProcessing
  }
  
  /**
  * Tests TestStatementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchDirection()
  throws Exception
  {
    // JUnitDoclet begin method setFetchDirection getFetchDirection
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      teststatement.setFetchDirection(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      teststatement.getFetchDirection());
    }
    // JUnitDoclet end method setFetchDirection getFetchDirection
  }
  
  /**
  * Tests TestStatementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetFetchSize()
  throws Exception
  {
    // JUnitDoclet begin method setFetchSize getFetchSize
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      teststatement.setFetchSize(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      teststatement.getFetchSize());
    }
    // JUnitDoclet end method setFetchSize getFetchSize
  }
  
  /**
  * Tests TestStatementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetMaxFieldSize()
  throws Exception
  {
    // JUnitDoclet begin method setMaxFieldSize getMaxFieldSize
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      teststatement.setMaxFieldSize(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      teststatement.getMaxFieldSize());
    }
    // JUnitDoclet end method setMaxFieldSize getMaxFieldSize
  }
  
  /**
  * Tests TestStatementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetMaxRows()
  throws Exception
  {
    // JUnitDoclet begin method setMaxRows getMaxRows
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      teststatement.setMaxRows(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      teststatement.getMaxRows());
    }
    // JUnitDoclet end method setMaxRows getMaxRows
  }
  
  /**
  * Tests TestStatementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetQueryTimeout()
  throws Exception
  {
    // JUnitDoclet begin method setQueryTimeout getQueryTimeout
    int[] t_aTests = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      teststatement.setQueryTimeout(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      teststatement.getQueryTimeout());
    }
    // JUnitDoclet end method setQueryTimeout getQueryTimeout
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
    junit.textui.TestRunner.run(TestStatementTest.class);
    // JUnitDoclet end method testcase.main
  }
}
