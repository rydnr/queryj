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
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
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
* Tests DatabaseMetaDataManagerTest class.
* @see org.acmsl.queryj.tools.DatabaseMetaDataManager
*/
public class DatabaseMetaDataManagerTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  org.acmsl.queryj.tools.DatabaseMetaDataManager databasemetadatamanager = null;
  // JUnitDoclet end class
  
  /**
  * Creates a DatabaseMetaDataManagerTest with given name.
  * @param name such name.
  */
  public DatabaseMetaDataManagerTest(String name)
  {
    // JUnitDoclet begin method DatabaseMetaDataManagerTest
    super(name);
    // JUnitDoclet end method DatabaseMetaDataManagerTest
  }
  
  /**
  * Creates an instance of the tested class.
  * @return such instance.
  
  */
  public org.acmsl.queryj.tools.DatabaseMetaDataManager createInstance()
  throws Exception
  {
    // JUnitDoclet begin method testcase.createInstance
      org.acmsl.queryj.tools.DatabaseMetaDataManager result = null;

      try
      {
          result =
              new org.acmsl.queryj.tools.DatabaseMetaDataManager(
                  null,
                  null,
                  true,
                  true,
                  true,
                  true,
                  null,
                  null,
                  null);
      }
      catch  (final Exception exception)
      {
          System.err.println(
                "Cannot create fake database metadata manager:"
              + exception);
      }

      return result;
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
    databasemetadatamanager = createInstance();
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
    databasemetadatamanager = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getMetaData()
  */
  public void testGetMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getMetaData
    // JUnitDoclet end method getMetaData
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetTableNames()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getTableNames()
  */
  public void testGetTableNames()
  throws Exception
  {
    // JUnitDoclet begin method getTableNames
    // JUnitDoclet end method getTableNames
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestaddColumnNames()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#addColumnNames(java.lang.String, java.lang.String[])
  */
  public void testAddColumnNames()
  throws Exception
  {
    // JUnitDoclet begin method addColumnNames
    // JUnitDoclet end method addColumnNames
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetColumnNames()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getColumnNames(java.lang.String)
  */
  public void testGetColumnNames()
  throws Exception
  {
    // JUnitDoclet begin method getColumnNames
    // JUnitDoclet end method getColumnNames
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetColumnType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getColumnType(java.lang.String, java.lang.String)
  */
  public void testGetColumnType()
  throws Exception
  {
    // JUnitDoclet begin method getColumnType
    // JUnitDoclet end method getColumnType
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestaddColumnType()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#addColumnType(java.lang.String, java.lang.String, int)
  */
  public void testAddColumnType()
  throws Exception
  {
    // JUnitDoclet begin method addColumnType
    // JUnitDoclet end method addColumnType
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestaddPrimaryKey()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#addPrimaryKey(java.lang.String, java.lang.String)
  */
  public void testAddPrimaryKey()
  throws Exception
  {
    // JUnitDoclet begin method addPrimaryKey
    // JUnitDoclet end method addPrimaryKey
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetPrimaryKeys()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getPrimaryKeys(java.lang.String)
  */
  public void testGetPrimaryKeys()
  throws Exception
  {
    // JUnitDoclet begin method getPrimaryKeys
    // JUnitDoclet end method getPrimaryKeys
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestisPrimaryKey()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#isPrimaryKey(java.lang.String, java.lang.String)
  */
  public void testIsPrimaryKey()
  throws Exception
  {
    // JUnitDoclet begin method isPrimaryKey
    // JUnitDoclet end method isPrimaryKey
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestaddForeignKey()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#addForeignKey(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
  */
  public void testAddForeignKey()
  throws Exception
  {
    // JUnitDoclet begin method addForeignKey
    // JUnitDoclet end method addForeignKey
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetReferredTables()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getReferredTables(java.lang.String)
  */
  public void testGetReferredTables()
  throws Exception
  {
    // JUnitDoclet begin method getReferredTables
    // JUnitDoclet end method getReferredTables
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetForeignKey()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getForeignKey(java.lang.String, java.lang.String)
  */
  public void testGetForeignKey()
  throws Exception
  {
    // JUnitDoclet begin method getForeignKey
    // JUnitDoclet end method getForeignKey
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetReferredKey()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getReferredKey(java.lang.String, java.lang.String)
  */
  public void testGetReferredKey()
  throws Exception
  {
    // JUnitDoclet begin method getReferredKey
    // JUnitDoclet end method getReferredKey
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestaddExternallyManagedField()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#addExternallyManagedField(java.lang.String, java.lang.String)
  */
  public void testAddExternallyManagedField()
  throws Exception
  {
    // JUnitDoclet begin method addExternallyManagedField
    // JUnitDoclet end method addExternallyManagedField
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetExternallyManagedFields()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getExternallyManagedFields(java.lang.String)
  */
  public void testGetExternallyManagedFields()
  throws Exception
  {
    // JUnitDoclet begin method getExternallyManagedFields
    // JUnitDoclet end method getExternallyManagedFields
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestisManagedExternally()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#isManagedExternally(java.lang.String, java.lang.String)
  */
  public void testIsManagedExternally()
  throws Exception
  {
    // JUnitDoclet begin method isManagedExternally
    // JUnitDoclet end method isManagedExternally
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetKeyword()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getKeyword(java.lang.String, java.lang.String)
  */
  public void testGetKeyword()
  throws Exception
  {
    // JUnitDoclet begin method getKeyword
    // JUnitDoclet end method getKeyword
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetProceduresMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getProceduresMetaData()
  */
  public void testGetProceduresMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getProceduresMetaData
    // JUnitDoclet end method getProceduresMetaData
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetProcedureParametersMetaData()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getProcedureParametersMetaData(org.acmsl.queryj.tools.ProcedureMetaData)
  */
  public void testGetProcedureParametersMetaData()
  throws Exception
  {
    // JUnitDoclet begin method getProcedureParametersMetaData
    // JUnitDoclet end method getProcedureParametersMetaData
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetTask()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getTask()
  */
  public void testGetTask()
  throws Exception
  {
    // JUnitDoclet begin method getTask
    // JUnitDoclet end method getTask
  }
  
  /**
  * Tests DatabaseMetaDataManagerTestgetProject()
  * @throws Exception if an unexpected situation occurs.
  * @see org.acmsl.queryj.tools.DatabaseMetaDataManager#getProject()
  */
  public void testGetProject()
  throws Exception
  {
    // JUnitDoclet begin method getProject
    // JUnitDoclet end method getProject
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
    junit.textui.TestRunner.run(DatabaseMetaDataManagerTest.class);
    // JUnitDoclet end method testcase.main
  }
}
