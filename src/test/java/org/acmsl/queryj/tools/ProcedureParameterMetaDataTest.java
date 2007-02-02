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
* Filename: ProcedureParameterMetaDataTest.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              unittests.org.acmsl.queryj.tools.
*
*/
package org.acmsl.queryj.tools;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.ProcedureParameterMetaData;
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
* Tests ProcedureParameterMetaDataTest class.
* @see org.acmsl.queryj.tools.ProcedureParameterMetaData
*/
public class ProcedureParameterMetaDataTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.ProcedureParameterMetaData procedureparametermetadata = null;
  // JUnitDoclet end class
  
  /**
  * Creates a ProcedureParameterMetaDataTest with given name.
  * @param name such name.
  */
  public ProcedureParameterMetaDataTest(String name)
  {
    // JUnitDoclet begin method ProcedureParameterMetaDataTest
    super(name);
    // JUnitDoclet end method ProcedureParameterMetaDataTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.ProcedureParameterMetaData createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return
        new org.acmsl.queryj.tools.ProcedureParameterMetaData(
            "test",
            org.acmsl.queryj.tools.ProcedureParameterMetaData.UNKNOWN_PARAMETER,
            "test parameter",
            1,
            1,
            0);
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
    procedureparametermetadata = createInstance();
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
    procedureparametermetadata = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests ProcedureParameterMetaDataTestgetDataType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.ProcedureParameterMetaData#getDataType()
  */
  public void testGetDataType()
  throws Exception
  {
    // JUnitDoclet begin method getDataType
    // JUnitDoclet end method getDataType
  }
  
  /**
  * Tests ProcedureParameterMetaDataTestgetLength()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.ProcedureParameterMetaData#getLength()
  */
  public void testGetLength()
  throws Exception
  {
    // JUnitDoclet begin method getLength
    // JUnitDoclet end method getLength
  }
  
  /**
  * Tests ProcedureParameterMetaDataTestgetNullable()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.ProcedureParameterMetaData#getNullable()
  */
  public void testGetNullable()
  throws Exception
  {
    // JUnitDoclet begin method getNullable
    // JUnitDoclet end method getNullable
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
    junit.textui.TestRunner.run(ProcedureParameterMetaDataTest.class);
    // JUnitDoclet end method testcase.main
  }
}
