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
* Filename: AntFieldElementTest.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.tools.
*
*/
package org.acmsl.queryj.tools;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.AntFieldElement;
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
* Tests AntFieldElementTest class.
* @see org.acmsl.queryj.tools.AntFieldElement
*/
public class AntFieldElementTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.AntFieldElement antfieldelement = null;
  // JUnitDoclet end class
  
  /**
  * Creates a AntFieldElementTest with given name.
  * @param name such name.
  */
  public AntFieldElementTest(String name)
  {
    // JUnitDoclet begin method AntFieldElementTest
    super(name);
    // JUnitDoclet end method AntFieldElementTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.AntFieldElement createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.AntFieldElement();
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
    antfieldelement = createInstance();
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
    antfieldelement = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests AntFieldElementTestgetName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#getName()
  */
  public void testGetName()
  throws Exception
  {
    // JUnitDoclet begin method getName
    // JUnitDoclet end method getName
  }
  
  /**
  * Tests AntFieldElementTestgetType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#getType()
  */
  public void testGetType()
  throws Exception
  {
    // JUnitDoclet begin method getType
    // JUnitDoclet end method getType
  }
  
  /**
  * Tests AntFieldElementTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetPk()
  throws Exception
  {
    // JUnitDoclet begin method setPk getPk
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      antfieldelement.setPk(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      antfieldelement.getPk());
    }
    // JUnitDoclet end method setPk getPk
  }
  
  /**
  * Tests AntFieldElementTestgetTableName()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#getTableName()
  */
  public void testGetTableName()
  throws Exception
  {
    // JUnitDoclet begin method getTableName
    // JUnitDoclet end method getTableName
  }
  
  /**
  * Tests AntFieldElementTestgetKeyword()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#getKeyword()
  */
  public void testGetKeyword()
  throws Exception
  {
    // JUnitDoclet begin method getKeyword
    // JUnitDoclet end method getKeyword
  }
  
  /**
  * Tests AntFieldElementTestgetFieldFks()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#getFieldFks()
  */
  public void testGetFieldFks()
  throws Exception
  {
    // JUnitDoclet begin method getFieldFks
    // JUnitDoclet end method getFieldFks
  }
  
  /**
  * Tests AntFieldElementTestsetDynamicAttribute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#setDynamicAttribute(java.lang.String, java.lang.String)
  */
  public void testSetDynamicAttribute()
  throws Exception
  {
    // JUnitDoclet begin method setDynamicAttribute
    // JUnitDoclet end method setDynamicAttribute
  }
  
  /**
  * Tests AntFieldElementTestcreateDynamicElement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntFieldElement#createDynamicElement(java.lang.String)
  */
  public void testCreateDynamicElement()
  throws Exception
  {
    // JUnitDoclet begin method createDynamicElement
    // JUnitDoclet end method createDynamicElement
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
    junit.textui.TestRunner.run(AntFieldElementTest.class);
    // JUnitDoclet end method testcase.main
  }
}
