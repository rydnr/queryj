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
*              unittests.org.acmsl.queryj.tools.
*
* Last modified by: $Author$ at $Date$
*
* File version: $Revision$
*
* Project version: $Name$
*
* $Id$
*/
package unittests.org.acmsl.queryj.tools;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.AntCommand;
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
* Tests AntCommandTest class.
* @version $Revision$
* @see org.acmsl.queryj.tools.AntCommand
*/
public class AntCommandTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.AntCommand antcommand = null;
  // JUnitDoclet end class
  
  /**
  * Creates a AntCommandTest with given name.
  * @param name such name.
  */
  public AntCommandTest(String name)
  {
    // JUnitDoclet begin method AntCommandTest
    super(name);
    // JUnitDoclet end method AntCommandTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.AntCommand createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.AntCommand();
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
    antcommand = createInstance();
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
    antcommand = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests AntCommandTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetAttributeMap()
  throws Exception
  {
    // JUnitDoclet begin method setAttributeMap getAttributeMap
    java.util.Map[] t_aTests = {new java.util.HashMap(), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      antcommand.setAttributeMap(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      antcommand.getAttributeMap());
    }
    // JUnitDoclet end method setAttributeMap getAttributeMap
  }
  
  /**
  * Tests AntCommandTestsetAttribute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntCommand#setAttribute(java.lang.String, java.lang.Object)
  */
  public void testSetAttribute()
  throws Exception
  {
    // JUnitDoclet begin method setAttribute
    // JUnitDoclet end method setAttribute
  }
  
  /**
  * Tests AntCommandTestgetAttribute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntCommand#getAttribute(java.lang.String)
  */
  public void testGetAttribute()
  throws Exception
  {
    // JUnitDoclet begin method getAttribute
    // JUnitDoclet end method getAttribute
  }
  
  /**
  * Tests AntCommandTestcontains()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntCommand#contains(java.lang.String)
  */
  public void testContains()
  throws Exception
  {
    // JUnitDoclet begin method contains
    // JUnitDoclet end method contains
  }
  
  /**
  * Tests AntCommandTestgetProject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntCommand#getProject()
  */
  public void testGetProject()
  throws Exception
  {
    // JUnitDoclet begin method getProject
    // JUnitDoclet end method getProject
  }
  
  /**
  * Tests AntCommandTestgetTask()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.AntCommand#getTask()
  */
  public void testGetTask()
  throws Exception
  {
    // JUnitDoclet begin method getTask
    // JUnitDoclet end method getTask
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
    junit.textui.TestRunner.run(AntCommandTest.class);
    // JUnitDoclet end method testcase.main
  }
}
