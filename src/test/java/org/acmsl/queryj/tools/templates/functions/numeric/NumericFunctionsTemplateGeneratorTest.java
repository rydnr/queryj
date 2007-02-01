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
* Filename: NumericFunctionsTemplateGeneratorTest.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.tools.templates.functions.numeric.
*
*/
package org.acmsl.queryj.tools.templates.functions.numeric;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator;
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
* Tests NumericFunctionsTemplateGeneratorTest class.
* @see org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator
*/
public class NumericFunctionsTemplateGeneratorTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator numericfunctionstemplategenerator = null;
  // JUnitDoclet end class
  
  /**
  * Creates a NumericFunctionsTemplateGeneratorTest with given name.
  * @param name such name.
  */
  public NumericFunctionsTemplateGeneratorTest(String name)
  {
    // JUnitDoclet begin method NumericFunctionsTemplateGeneratorTest
    super(name);
    // JUnitDoclet end method NumericFunctionsTemplateGeneratorTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator();
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
    numericfunctionstemplategenerator = createInstance();
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
    numericfunctionstemplategenerator = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests NumericFunctionsTemplateGeneratorTestgetInstance()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator#getInstance()
  */
  public void testGetInstance()
  throws Exception
  {
    // JUnitDoclet begin method getInstance
    // JUnitDoclet end method getInstance
  }
  
  /**
  * Tests NumericFunctionsTemplateGeneratorTestaddTemplateFactoryClass()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator#addTemplateFactoryClass(java.lang.String, java.lang.String, java.lang.String)
  */
  public void testAddTemplateFactoryClass()
  throws Exception
  {
    // JUnitDoclet begin method addTemplateFactoryClass
    // JUnitDoclet end method addTemplateFactoryClass
  }
  
  /**
  * Tests NumericFunctionsTemplateGeneratorTestcreateNumericFunctionsTemplate()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator#createNumericFunctionsTemplate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
  */
  public void testCreateNumericFunctionsTemplate()
  throws Exception
  {
    // JUnitDoclet begin method createNumericFunctionsTemplate
    // JUnitDoclet end method createNumericFunctionsTemplate
  }
  
  /**
  * Tests NumericFunctionsTemplateGeneratorTestwrite()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplateGenerator#write(org.acmsl.queryj.tools.templates.functions.numeric.NumericFunctionsTemplate, java.io.File)
  */
  public void testWrite()
  throws Exception
  {
    // JUnitDoclet begin method write
    // JUnitDoclet end method write
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
    junit.textui.TestRunner.run(NumericFunctionsTemplateGeneratorTest.class);
    // JUnitDoclet end method testcase.main
  }
}