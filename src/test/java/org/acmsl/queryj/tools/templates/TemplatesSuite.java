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
* Filename: TemplatesSuite.java
*
* Author: Jose San Leandro Armend?riz
*
* Description: Executes all tests defined for package
*              org.acmsl.queryj.tools.templates.
*
*/
package org.acmsl.queryj.tools.templates;


/*
* Importing project classes.
*/

import org.acmsl.queryj.tools.templates.dao.DaoSuite;
import org.acmsl.queryj.tools.templates.functions.FunctionsSuite;
import org.acmsl.queryj.tools.templates.handlers.HandlersSuite;
import org.acmsl.queryj.tools.templates.valueobject.ValueobjectSuite;


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
* Executes all tests defined for package
* org.acmsl.queryj.tools.templates
* @see org.acmsl.queryj.tools.templates
*/
public class TemplatesSuite
// JUnitDoclet begin extends_implements
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  // JUnitDoclet end class
  
  public static TestSuite suite()
  {
    TestSuite suite;
    
    suite =
    new TestSuite("org.acmsl.queryj.tools.templates");
    
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableRepositoryTemplateTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateGeneratorTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TestSuiteTemplateGeneratorTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateGeneratorTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableTemplateGeneratorTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableRepositoryTemplateUtilsTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableRepositoryTemplateGeneratorTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableTemplateUtilsTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateUtilsTest.class);
    suite.addTestSuite(org.acmsl.queryj.tools.templates.TableTemplateTest.class);
    
    suite.addTest(org.acmsl.queryj.tools.templates.dao.DaoSuite.suite());
    suite.addTest(org.acmsl.queryj.tools.templates.functions.FunctionsSuite.suite());
    suite.addTest(org.acmsl.queryj.tools.templates.handlers.HandlersSuite.suite());
    suite.addTest(org.acmsl.queryj.tools.templates.valueobject.ValueobjectSuite.suite());
    
    
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
