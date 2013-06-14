//;-*- mode: java -*-
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
 * Filename: TestTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Identifies all test templates.
 *
 */
package org.acmsl.queryj.api;

/**
 * Identifies all test templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface TestTemplate
    extends  Template
{
    /**
     * Retrieves the test name.
     * @return such name.
     */
    public String getTestName();

    /**
     * Indicates if the test should be considered a suite or not.
     * @return such information.
     */
    public boolean isSuite();

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName();
}
