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
*              unittests.org.acmsl.queryj.tools.customsql.
*
* Last modified by: $Author$ at $Date$
*
* File version: $Revision$
*
* Project version: $Name$
*
* $Id$
*/
package unittests.org.acmsl.queryj.tools.customsql;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.customsql.SqlElement;
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
* Tests SqlElementTest class.
* @version $Revision$
* @see org.acmsl.queryj.tools.customsql.SqlElement
*/
public class SqlElementTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.customsql.SqlElement sqlelement = null;
  // JUnitDoclet end class
  
  /**
  * Creates a SqlElementTest with given name.
  * @param name such name.
  */
  public SqlElementTest(String name)
  {
    // JUnitDoclet begin method SqlElementTest
    super(name);
    // JUnitDoclet end method SqlElementTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.customsql.SqlElement createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.customsql.SqlElement("s1", "dao", "name", "select", "all");
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
    sqlelement = createInstance();
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
    sqlelement = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests SqlElementTestgetDao()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#getDao()
  */
  public void testGetDao()
  throws Exception
  {
    // JUnitDoclet begin method getDao
    // JUnitDoclet end method getDao
  }
  
  /**
  * Tests SqlElementTestgetName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#getName()
  */
  public void testGetName()
  throws Exception
  {
    // JUnitDoclet begin method getName
    // JUnitDoclet end method getName
  }
  
  /**
  * Tests SqlElementTestgetType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#getType()
  */
  public void testGetType()
  throws Exception
  {
    // JUnitDoclet begin method getType
    // JUnitDoclet end method getType
  }
  
  /**
  * Tests SqlElementTestgetImplementation()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#getImplementation()
  */
  public void testGetImplementation()
  throws Exception
  {
    // JUnitDoclet begin method getImplementation
    // JUnitDoclet end method getImplementation
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetDescription()
  throws Exception
  {
    // JUnitDoclet begin method setDescription getDescription
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setDescription(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getDescription());
    }
    // JUnitDoclet end method setDescription getDescription
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetValue()
  throws Exception
  {
    // JUnitDoclet begin method setValue getValue
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setValue(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getValue());
    }
    // JUnitDoclet end method setValue getValue
  }
  
  /**
  * Tests SqlElementTestgetParameterRefs()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#getParameterRefs()
  */
  public void testGetParameterRefs()
  throws Exception
  {
    // JUnitDoclet begin method getParameterRefs
    // JUnitDoclet end method getParameterRefs
  }
  
  /**
  * Tests SqlElementTestadd()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#add(org.acmsl.queryj.tools.customsql.ParameterRefElement)
  */
  public void testAdd()
  throws Exception
  {
    // JUnitDoclet begin method add
    // JUnitDoclet end method add
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetResultRef()
  throws Exception
  {
    // JUnitDoclet begin method setResultRef getResultRef
    org.acmsl.queryj.tools.customsql.ResultRefElement[] t_aTests =
        {new org.acmsl.queryj.tools.customsql.ResultRefElement("r1"), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setResultRef(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getResultRef());
    }
    // JUnitDoclet end method setResultRef getResultRef
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetConnectionFlagsRef()
  throws Exception
  {
    // JUnitDoclet begin method setConnectionFlagsRef getConnectionFlagsRef
    org.acmsl.queryj.tools.customsql.ConnectionFlagsRefElement[] t_aTests =
        {
            new org.acmsl.queryj.tools.customsql.ConnectionFlagsRefElement(
                "connection-flags-1"),
            null
        };
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setConnectionFlagsRef(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getConnectionFlagsRef());
    }
    // JUnitDoclet end method setConnectionFlagsRef getConnectionFlagsRef
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetStatementFlagsRef()
  throws Exception
  {
    // JUnitDoclet begin method setStatementFlagsRef getStatementFlagsRef
    org.acmsl.queryj.tools.customsql.StatementFlagsRefElement[] t_aTests =
        {
            new org.acmsl.queryj.tools.customsql.StatementFlagsRefElement(
                "statement-flags-1"),
            null
        };
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setStatementFlagsRef(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getStatementFlagsRef());
    }
    // JUnitDoclet end method setStatementFlagsRef getStatementFlagsRef
  }
  
  /**
  * Tests SqlElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetResultSetFlagsRef()
  throws Exception
  {
    // JUnitDoclet begin method setResultSetFlagsRef getResultSetFlagsRef
    org.acmsl.queryj.tools.customsql.ResultSetFlagsRefElement[] t_aTests =
        {
            new org.acmsl.queryj.tools.customsql.ResultSetFlagsRefElement(
                "resultset-flags-1"),
            null
        };
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      sqlelement.setResultSetFlagsRef(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      sqlelement.getResultSetFlagsRef());
    }
    // JUnitDoclet end method setResultSetFlagsRef getResultSetFlagsRef
  }
  
  /**
  * Tests SqlElementTesttoString()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.customsql.SqlElement#toString()
  */
  public void testToString()
  throws Exception
  {
    // JUnitDoclet begin method toString
    // JUnitDoclet end method toString
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
    junit.textui.TestRunner.run(SqlElementTest.class);
    // JUnitDoclet end method testcase.main
  }
}
