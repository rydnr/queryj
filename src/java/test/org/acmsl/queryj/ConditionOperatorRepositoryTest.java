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
*              unittests.org.acmsl.queryj.
*
* Last modified by: $Author$ at $Date$
*
* File version: $Revision$
*
* Project version: $Name$
*
* $Id$
*/
package unittests.org.acmsl.queryj;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.ConditionOperatorRepository;
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
* Tests ConditionOperatorRepositoryTest class.
* @version $Revision$
* @see org.acmsl.queryj.ConditionOperatorRepository
*/
public class ConditionOperatorRepositoryTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.ConditionOperatorRepository conditionoperatorrepository = null;
  // JUnitDoclet end class
  
  /**
  * Creates a ConditionOperatorRepositoryTest with given name.
  * @param name such name.
  */
  public ConditionOperatorRepositoryTest(String name)
  {
    // JUnitDoclet begin method ConditionOperatorRepositoryTest
    super(name);
    // JUnitDoclet end method ConditionOperatorRepositoryTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.ConditionOperatorRepository createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return org.acmsl.queryj.ConditionOperatorRepository.getInstance();
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
    conditionoperatorrepository = createInstance();
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
    conditionoperatorrepository = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetInstance()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getInstance()
  */
  public void testGetInstance()
  throws Exception
  {
    // JUnitDoclet begin method getInstance
    // JUnitDoclet end method getInstance
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetEquals()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getEquals()
  */
  public void testGetEquals()
  throws Exception
  {
    // JUnitDoclet begin method getEquals
    // JUnitDoclet end method getEquals
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetNotEquals()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getNotEquals()
  */
  public void testGetNotEquals()
  throws Exception
  {
    // JUnitDoclet begin method getNotEquals
    // JUnitDoclet end method getNotEquals
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetGreaterThan()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getGreaterThan()
  */
  public void testGetGreaterThan()
  throws Exception
  {
    // JUnitDoclet begin method getGreaterThan
    // JUnitDoclet end method getGreaterThan
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetLessThan()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getLessThan()
  */
  public void testGetLessThan()
  throws Exception
  {
    // JUnitDoclet begin method getLessThan
    // JUnitDoclet end method getLessThan
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetIsNull()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getIsNull()
  */
  public void testGetIsNull()
  throws Exception
  {
    // JUnitDoclet begin method getIsNull
    // JUnitDoclet end method getIsNull
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetBelongsTo()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getBelongsTo(org.acmsl.queryj.SelectQuery)
  */
  public void testGetBelongsTo()
  throws Exception
  {
    // JUnitDoclet begin method getBelongsTo
    // JUnitDoclet end method getBelongsTo
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetNotBelongsTo()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getNotBelongsTo(org.acmsl.queryj.SelectQuery)
  */
  public void testGetNotBelongsTo()
  throws Exception
  {
    // JUnitDoclet begin method getNotBelongsTo
    // JUnitDoclet end method getNotBelongsTo
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetLike()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getLike()
  */
  public void testGetLike()
  throws Exception
  {
    // JUnitDoclet begin method getLike
    // JUnitDoclet end method getLike
  }
  
  /**
  * Tests ConditionOperatorRepositoryTestgetNotLike()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.ConditionOperatorRepository#getNotLike()
  */
  public void testGetNotLike()
  throws Exception
  {
    // JUnitDoclet begin method getNotLike
    // JUnitDoclet end method getNotLike
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
    junit.textui.TestRunner.run(ConditionOperatorRepositoryTest.class);
    // JUnitDoclet end method testcase.main
  }
}
