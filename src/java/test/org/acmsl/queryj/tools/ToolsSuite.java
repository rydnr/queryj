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

import unittests.org.acmsl.queryj.tools.customsql.CustomsqlSuite;
import unittests.org.acmsl.queryj.tools.handlers.HandlersSuite;
import unittests.org.acmsl.queryj.tools.oracle.OracleSuite;
import unittests.org.acmsl.queryj.tools.templates.TemplatesSuite;


/*
/* Importing JUnit classes.
*/
import junit.framework.TestSuite;

// JUnitDoclet begin import
// JUnitDoclet end import

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
* Executes all tests defined for package
* org.acmsl.queryj.tools
* @version $Revision$
* @see org.acmsl.queryj.tools
*/
public class ToolsSuite
// JUnitDoclet begin extends_implements
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  // JUnitDoclet end class
  
  public static TestSuite suite()
  {
    TestSuite suite;
    
    suite =
    new TestSuite("unittests.org.acmsl.queryj.tools");
    
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.QueryJTaskTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntFieldFkElementTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntTableElementTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntFieldElementTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.PackageUtilsTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.ProcedureMetaDataTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntTablesElementTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntExternallyManagedFieldsElementTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.AntCommandTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.ProcedureParameterMetaDataTest.class);
    suite.addTestSuite(unittests.org.acmsl.queryj.tools.DatabaseMetaDataManagerTest.class);
    
    suite.addTest(unittests.org.acmsl.queryj.tools.customsql.CustomsqlSuite.suite());
    suite.addTest(unittests.org.acmsl.queryj.tools.handlers.HandlersSuite.suite());
    suite.addTest(unittests.org.acmsl.queryj.tools.oracle.OracleSuite.suite());
    suite.addTest(unittests.org.acmsl.queryj.tools.templates.TemplatesSuite.suite());
    
    
    // JUnitDoclet begin method suite
    // JUnitDoclet end method suite
    
    return suite;
  }
  
  public static void main(String[] args)
  {
    // JUnitDoclet begin method testsuite.main
    junit.textui.TestRunner.run(suite());
    // JUnitDoclet end method testsuite.main
  }
}
