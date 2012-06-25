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
*              org.acmsl.queryj.customsql.xml.
*
*/
package org.acmsl.queryj.customsql.xml;


/*
* Importing project classes.
*/



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
* Executes all tests defined for package
* org.acmsl.queryj.customsql.xml
* @see org.acmsl.queryj.customsql.xml
*/
public class XmlSuite
// JUnitDoclet begin extends_implements
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  // JUnitDoclet end class
  
  public static TestSuite suite()
  {
    TestSuite suite;
    
    suite =
    new TestSuite("org.acmsl.queryj.customsql.xml");
    
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ResultSetFlagsElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.PropertyRefElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ParameterRefElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ParameterElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.SqlXmlParserFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ConnectionFlagsRefElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ResultElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.PropertyElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.StatementFlagsRefElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ResultRefElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.SqlElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ConnectionFlagsElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.UntrimmedCallMethodRuleTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.StatementFlagsElementFactoryTest.class);
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.ResultSetFlagsRefElementFactoryTest.class);
    
    
    
    // JUnitDoclet begin method suite
    suite.addTestSuite(org.acmsl.queryj.customsql.xml.SqlXmlParserTest.class);
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
