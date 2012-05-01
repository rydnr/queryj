package org.acmsl.queryj.tools.templates;/*
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
 * Filename: STTemplate.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: StringTemplate-specific templates.
 *
 * Date: 4/30/12
 * Time: 4:40 PM
 *
 */

/*
 * Importing some StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * <a href="http://www.stringtemplate.org>StringTemplate</a>-specific templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 */
public interface STTemplate
    extends  Template
{
    /**
     * Retrieves the template group.
     * @return such group.
     */
    public StringTemplateGroup retrieveGroup();
}
