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
 * Filename: MockDAOFactoryTemplateFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents entities able to create Mock DAO factory templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.mock;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.mock.MockDAOFactoryTemplate;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.jetbrains.annotations.NotNull;

/**
 * Represents entities able to create Mock DAO factory templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface MockDAOFactoryTemplateFactory
{
    /**
     * Generates a Mock DAO factory template.
     * @param tableTemplate the table template.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @return a template.
     * @throws QueryJException if the input values are invalid.
     */
    @NotNull
    public MockDAOFactoryTemplate createMockDAOFactoryTemplate(
        final TableTemplate tableTemplate,
        final String        packageName,
        final String        basePackageName)
      throws  QueryJException;
}
