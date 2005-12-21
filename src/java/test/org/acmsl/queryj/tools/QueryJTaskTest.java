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
*              org.acmsl.queryj.tools.
*
*/
package org.acmsl.queryj.tools;

/*
* Importing project classes.
*/
// JUnitDoclet begin import
import org.acmsl.queryj.tools.QueryJTask;
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
* Tests QueryJTaskTest class.
* @see org.acmsl.queryj.tools.QueryJTask
*/
public class QueryJTaskTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.QueryJTask queryjtask = null;
  // JUnitDoclet end class
  
  /**
  * Creates a QueryJTaskTest with given name.
  * @param name such name.
  */
  public QueryJTaskTest(String name)
  {
    // JUnitDoclet begin method QueryJTaskTest
    super(name);
    // JUnitDoclet end method QueryJTaskTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.QueryJTask createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
    return new org.acmsl.queryj.tools.QueryJTask();
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
    queryjtask = createInstance();
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
    queryjtask = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetDriver()
  throws Exception
  {
    // JUnitDoclet begin method setDriver getDriver
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setDriver(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getDriver());
    }
    // JUnitDoclet end method setDriver getDriver
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetUrl()
  throws Exception
  {
    // JUnitDoclet begin method setUrl getUrl
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setUrl(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getUrl());
    }
    // JUnitDoclet end method setUrl getUrl
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetUsername()
  throws Exception
  {
    // JUnitDoclet begin method setUsername getUsername
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setUsername(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getUsername());
    }
    // JUnitDoclet end method setUsername getUsername
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetPassword()
  throws Exception
  {
    // JUnitDoclet begin method setPassword getPassword
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setPassword(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getPassword());
    }
    // JUnitDoclet end method setPassword getPassword
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetCatalog()
  throws Exception
  {
    // JUnitDoclet begin method setCatalog getCatalog
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setCatalog(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getCatalog());
    }
    // JUnitDoclet end method setCatalog getCatalog
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetSchema()
  throws Exception
  {
    // JUnitDoclet begin method setSchema getSchema
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setSchema(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getSchema());
    }
    // JUnitDoclet end method setSchema getSchema
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetRepository()
  throws Exception
  {
    // JUnitDoclet begin method setRepository getRepository
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setRepository(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getRepository());
    }
    // JUnitDoclet end method setRepository getRepository
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetPackage()
  throws Exception
  {
    // JUnitDoclet begin method setPackage getPackage
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setPackage(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getPackage());
    }
    // JUnitDoclet end method setPackage getPackage
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetClasspath()
  throws Exception
  {
    // JUnitDoclet begin method setClasspath getClasspath
    org.apache.tools.ant.types.Path[] t_aTests = {new org.apache.tools.ant.types.Path(null), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setClasspath(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getClasspath());
    }
    // JUnitDoclet end method setClasspath getClasspath
  }
  
  /**
  * Tests QueryJTaskTestcreateClasspath()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#createClasspath()
  */
  public void testCreateClasspath()
  throws Exception
  {
    // JUnitDoclet begin method createClasspath
    // JUnitDoclet end method createClasspath
  }
  
  /**
  * Tests QueryJTaskTestsetClasspathRef()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#setClasspathRef(org.apache.tools.ant.types.Reference)
  */
  public void testSetClasspathRef()
  throws Exception
  {
    // JUnitDoclet begin method setClasspathRef
    // JUnitDoclet end method setClasspathRef
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetOutputdir()
  throws Exception
  {
    // JUnitDoclet begin method setOutputdir getOutputdir
    java.io.File[] t_aTests = {new java.io.File("queryj"), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setOutputdir(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getOutputdir());
    }
    // JUnitDoclet end method setOutputdir getOutputdir
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetExtractTables()
  throws Exception
  {
    // JUnitDoclet begin method setExtractTables getExtractTables
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setExtractTables(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getExtractTables());
    }
    // JUnitDoclet end method setExtractTables getExtractTables
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetExtractProcedures()
  throws Exception
  {
    // JUnitDoclet begin method setExtractProcedures getExtractProcedures
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setExtractProcedures(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getExtractProcedures());
    }
    // JUnitDoclet end method setExtractProcedures getExtractProcedures
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetExtractFunctions()
  throws Exception
  {
    // JUnitDoclet begin method setExtractFunctions getExtractFunctions
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setExtractFunctions(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getExtractFunctions());
    }
    // JUnitDoclet end method setExtractFunctions getExtractFunctions
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetJndiDataSource()
  throws Exception
  {
    // JUnitDoclet begin method setJndiDataSource getJndiDataSource
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setJndiDataSource(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getJndiDataSource());
    }
    // JUnitDoclet end method setJndiDataSource getJndiDataSource
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetGenerateMockDAOImplementation()
  throws Exception
  {
    // JUnitDoclet begin method setGenerateMockDAOImplementation getGenerateMockDAOImplementation
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setGenerateMockDAOImplementation(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getGenerateMockDAOImplementation());
    }
    // JUnitDoclet end method setGenerateMockDAOImplementation getGenerateMockDAOImplementation
  }
  
  /**
  * Tests QueryJTaskTestgetTables()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#getTables()
  */
  public void testGetTables()
  throws Exception
  {
    // JUnitDoclet begin method getTables
    // JUnitDoclet end method getTables
  }
  
  /**
  * Tests QueryJTaskTestgetExternallyManagedFields()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#getExternallyManagedFields()
  */
  public void testGetExternallyManagedFields()
  throws Exception
  {
    // JUnitDoclet begin method getExternallyManagedFields
    // JUnitDoclet end method getExternallyManagedFields
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetCustomSqlModel()
  throws Exception
  {
    // JUnitDoclet begin method setCustomSqlModel getCustomSqlModel
    java.lang.String[] t_aTests = {"", " ", "a", "A", "?", "?", "0123456789", "012345678901234567890", "\n", null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setCustomSqlModel(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getCustomSqlModel());
    }
    // JUnitDoclet end method setCustomSqlModel getCustomSqlModel
  }
  
  /**
  * Tests QueryJTaskTest accessor methods.
  * @throws Exception if an unexpected situation occurs.
  */
  public void testSetGetSqlXmlFile()
  throws Exception
  {
    // JUnitDoclet begin method setSqlXmlFile getSqlXmlFile
    java.io.File[] t_aTests = {new java.io.File("a"), null};
    
    for (int t_iTestIndex = 0; t_iTestIndex < t_aTests.length; t_iTestIndex++)
    {
      queryjtask.setSqlXmlFile(t_aTests[t_iTestIndex]);
      assertEquals(
      t_aTests[t_iTestIndex],
      queryjtask.getSqlXmlFile());
    }
    // JUnitDoclet end method setSqlXmlFile getSqlXmlFile
  }
  
  /**
  * Tests QueryJTaskTestsetDynamicAttribute()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#setDynamicAttribute(java.lang.String, java.lang.String)
  */
  public void testSetDynamicAttribute()
  throws Exception
  {
    // JUnitDoclet begin method setDynamicAttribute
    // JUnitDoclet end method setDynamicAttribute
  }
  
  /**
  * Tests QueryJTaskTestcreateDynamicElement()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.QueryJTask#createDynamicElement(java.lang.String)
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
    junit.textui.TestRunner.run(QueryJTaskTest.class);
    // JUnitDoclet end method testcase.main
  }
}
