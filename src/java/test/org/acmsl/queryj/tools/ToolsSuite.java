/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is a collection of related JUnit tests for the QueryJ classes
 *              under tools subpackage.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package unittests.org.acmsl.queryj.tools;

/*
 * Importing ACM-SL classes.
 */
import unittests.org.acmsl.queryj.tools.PackageUtilsTest;

/*
 * Importing JUnit classes
 */
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.swingui.TestRunner;

/**
 * Is a collection of related JUnit tests for the QueryJ classes under
 * tools subpackage..
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro Armendariz</a>
 * @version $Revision$
 * @testfamily JUnit
 * @testkind testsuite
 * @testsetup Default TestSuite
 * @testpackage org.acmsl.queryj.tools
 */
public class ToolsSuite
    extends  TestSuite
{
    /**
     * Default constructor.
     */
    public ToolsSuite()
    {
        addTest(suite());
    }

    /**
     * Executes the tests from command line.
     * @param args the command-line arguments. Not needed so far.
     */
    public static void main(String[] args)
    {
        TestRunner.run(ToolsSuite.class);
    }

    /**
     * Retrieves the set of tests included in this suite.
     * @return the test collection.
     */
    public static Test suite()
    {
        TestSuite result = new TestSuite("QueryJ tools suite");

        result.addTestSuite(PackageUtilsTest.class);
        
        return result;
    }
}
