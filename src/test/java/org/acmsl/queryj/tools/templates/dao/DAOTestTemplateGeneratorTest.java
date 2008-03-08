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
* Filename: DAOTestTemplateGeneratorTest.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.tools.templates.dao.
*
*/
package org.acmsl.queryj.tools.templates.dao;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator;
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
* Tests DAOTestTemplateGeneratorTest class.
* @see org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator
*/
public class DAOTestTemplateGeneratorTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator daotesttemplategenerator = null;
  // JUnitDoclet end class
  
  /**
   * The tested jdbc driver.
   */
  private static final String JDBC_DRIVER = "test.driver";

  /**
   * The tested jdbc url.
   */
  private static final String JDBC_URL = "jdbc:url:ad.dr.es.s:port:sid";

  /**
   * The tested jdbc username.
   */
  private static final String JDBC_USERNAME = "username";

  /**
   * The tested jdbc password.
   */
  private static final String JDBC_PASSWORD = "password";

  /**
  * Creates a DAOTestTemplateGeneratorTest with given name.
  * @param name such name.
  */
  public DAOTestTemplateGeneratorTest(String name)
  {
    // JUnitDoclet begin method DAOTestTemplateGeneratorTest
    super(name);
    // JUnitDoclet end method DAOTestTemplateGeneratorTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
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
    daotesttemplategenerator = createInstance();
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
    daotesttemplategenerator = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
   * Tests DAOTestTemplateGeneratorTestgetJdbcDriver()
   * @throws Exception if an unexpected situation occurs.
   * @see org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator#getJdbcDriver()
   */
  public void testGetJdbcDriver()
      throws Exception
  {
    // JUnitDoclet begin method getJdbcDriver
    assertEquals(daotesttemplategenerator.getJdbcDriver(), JDBC_DRIVER);
    // JUnitDoclet end method getJdbcDriver
  }
  
  /**
   * Tests DAOTestTemplateGeneratorTestgetJdbcUrl()
   * @throws Exception if an unexpected situation occurs.
   * @see org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator#getJdbcUrl()
   */
  public void testGetJdbcUrl()
      throws Exception
  {
    // JUnitDoclet begin method getJdbcUrl
    assertEquals(daotesttemplategenerator.getJdbcUrl(), JDBC_URL);
    // JUnitDoclet end method getJdbcUrl
  }
  
  /**
   * Tests DAOTestTemplateGeneratorTestgetJdbcUser()
   * @throws Exception if an unexpected situation occurs.
   * @see org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator#getJdbcUser()
   */
  public void testGetJdbcUser()
      throws Exception
  {
    // JUnitDoclet begin method getJdbcUser
    assertEquals(daotesttemplategenerator.getJdbcUser(), JDBC_USERNAME);
    // JUnitDoclet end method getJdbcUser
  }
  
  /**
   * Tests DAOTestTemplateGeneratorTestgetJdbcPassword()
   * @throws Exception if an unexpected situation occurs.
   * @see org.acmsl.queryj.tools.templates.dao.DAOTestTemplateGenerator#getJdbcPassword()
   */
  public void testGetJdbcPassword()
      throws Exception
  {
    // JUnitDoclet begin method getJdbcPassword
    assertEquals(daotesttemplategenerator.getJdbcPassword(), JDBC_PASSWORD);
    // JUnitDoclet end method getJdbcPassword
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
    junit.textui.TestRunner.run(DAOTestTemplateGeneratorTest.class);
    // JUnitDoclet end method testcase.main
  }
}
