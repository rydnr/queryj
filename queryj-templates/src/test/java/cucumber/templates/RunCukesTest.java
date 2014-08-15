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
 * Filename: RunCukesTest.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Runs Cucumber within JUnit.
 *
 * Date: 2013/04/12
 * Time: 9:33 AM
 *
 */
package cucumber.templates;

/*
 * Importing Cucumber classes.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/*
 * Importing JUnit classes.
 */
import org.junit.runner.RunWith;

/**
 * Runs Cucumber within JUnit.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * Created: 2013/04/12
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:target/cucumber-html-report"})
public class RunCukesTest
{
}

