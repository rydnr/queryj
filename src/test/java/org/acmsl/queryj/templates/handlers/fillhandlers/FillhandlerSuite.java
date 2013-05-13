/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                              chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: FillhandlerSuite.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Executes all tests defined for package
 * org.acmsl.queryj.templates.handlers.fillhandlers

 *
 * Date: 5/13/13
 * Time: 9:24 PM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;


import junit.framework.TestSuite;

/**
 * Executes all tests defined for package
 * org.acmsl.queryj.templates.handlers.fillhandlers
 * @see org.acmsl.queryj.templates.handlers.fillhandlers
 */
public class FillhandlerSuite
// JUnitDoclet begin extends_implements
// JUnitDoclet end extends_implements
{
    // JUnitDoclet begin class
    // JUnitDoclet end class

    public static TestSuite suite()
    {
        TestSuite suite;

        suite =
            new TestSuite("org.acmsl.queryj.templates.handlers.fillhandlers");

        suite.addTestSuite(org.acmsl.queryj.templates.handlers.fillhandlers.DecoratedStringTest.class);

        // JUnitDoclet begin method suite
        // JUnitDoclet end method suite

        return suite;
    }

    public static void main(final String[] args)
    {
        // JUnitDoclet begin method testsuite.main
        junit.textui.TestRunner.run(suite());
        // JUnitDoclet end method testsuite.main
    }
}
